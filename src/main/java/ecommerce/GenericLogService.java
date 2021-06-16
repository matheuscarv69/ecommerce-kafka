package ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Properties;
import java.util.regex.Pattern;

public class GenericLogService {

    public static void main(String[] args) {

        // cria um consumer do kafka
        var consumer = new KafkaConsumer<String, String>(properties());

        // fazendo uso de Expression Language para dizer que esse consumer ira
        // escutar todos os Topicos que iniciem com ECOMMERCE
        consumer.subscribe(Pattern.compile("ECOMMERCE.*"));

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
                    System.out.println("LOG");
                    System.out.println("Topic: " + registry.topic());
                    System.out.println("key: " + registry.key());
                    System.out.println("value: " + registry.value());
                    System.out.println("partition: " + registry.partition());
                    System.out.println("offset: " + registry.offset());
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
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, GenericLogService.class.getSimpleName());

        return properties;
    }

}
