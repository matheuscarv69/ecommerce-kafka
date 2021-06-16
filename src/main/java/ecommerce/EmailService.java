package ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class EmailService {

    public static void main(String[] args) {

        // cria um consumer do kafka
        var consumer = new KafkaConsumer<String, String>(properties());

        // informa qual topico esse consumer vai escutar - recebe uma lista de topicos, mas
        // nao eh utilizado dessa forma, um consumer escuta um topico na maioria das vezes
        consumer.subscribe(Collections.singletonList("ECOMMERCE_SEND_EMAIL"));

        // while para o consumer ficar rodando constantemente
        while (true) {

            // Esse poll eh a funcao utilizada para mandar o consumer escutar o topico
            //recebe um tempo, ele eh a duracao que o consumer deve escutar o topico
            // vai escutar o topico por 100 milisecundos
            var records = consumer.poll(Duration.ofMillis(100));

            if (!records.isEmpty()) {
                System.out.println("Found " + records.count() + " registries");

                records.forEach(registry -> {
                    System.out.println("---------------------");
                    System.out.println("Send email");
                    System.out.println("key: " + registry.key());
                    System.out.println("value: " + registry.value());
                    System.out.println("partition: " + registry.partition());
                    System.out.println("offset: " + registry.offset());

                    // simulando uma fraude
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("send email simulation");
                    }
                    System.out.println("Email sent");
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
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, EmailService.class.getSimpleName());

        return properties;
    }

}
