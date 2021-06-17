package ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

public class FraudDetectorService {

    public static void main(String[] args) {

        // cria um consumer do kafka
        var consumer = new KafkaConsumer<String, String>(properties());

        // informa qual topico esse consumer vai escutar - recebe uma lista de topicos, mas
        // nao eh utilizado dessa forma, um consumer escuta um topico na maioria das vezes
        consumer.subscribe(Collections.singletonList("ECOMMERCE_NEW_ORDER"));

        // while para o consumer ficar rodando constantemente
        while (true) {

            // Esse poll eh a funcao utilizada para mandar o consumer escutar o topico
            // recebe um tempo, ele eh a duracao que o consumer deve escutar o topico
            // vai escutar o topico por 100 milisecundos
            var records = consumer.poll(Duration.ofMillis(100));

            if (!records.isEmpty()) {
                System.out.println("Found " + records.count() + " registries");

                records.forEach(registry -> {
                    System.out.println("---------------------");
                    System.out.println("Processesing new order, checking for fraud");
                    System.out.println("key: " + registry.key());
                    System.out.println("value: " + registry.value());
                    System.out.println("partition: " + registry.partition());
                    System.out.println("offset: " + registry.offset());

                    // simulando uma fraude
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        System.out.println("fraud simulation");
                    }
                    System.out.println("Order processed");
                });
            }

        }

    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // Eh preciso criar um grupo para o consumer poder receber todas as mensagens do producer
        // Eh possivel ter outro consumer com o mesmo id, porem o kafka vai fazer um balanceamento
        // entre a quantidade de mensagens
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, FraudDetectorService.class.getSimpleName());
        // configura um id para o consumer
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, FraudDetectorService.class.getSimpleName() + "-" + UUID.randomUUID().toString());
        // Essa prop serve para o poll ir commitando uma mensagem por vez, pois quando ele fica livre sem
        // essa prop o kafka faz o rebalanceamento enquanto ainda esta processando as mensagens, assim esse consumer
        // para de receber as mensagens pois ele ja esta diferente de como quando foi iniciado.
        // Com essa prop o poll lá em cima irá processar de uma em uma as mensagens sequencialmente
        // Previne de executar a mesma mensagem duas vezes
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");

        return properties;
    }

}
