package ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.Closeable;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

public class KafkaService implements Closeable {

    private final KafkaConsumer<String, String> consumer;
    private final ConsumerFunction parse;

    public KafkaService(String groupId,
                        String topic,
                        ConsumerFunction parse) {
        this.parse = parse;

        // cria um consumer do kafka
        this.consumer = new KafkaConsumer<>(properties(groupId));

        // informa qual topico esse consumer vai escutar - recebe uma lista de topicos, mas
        // nao eh utilizado dessa forma, um consumer escuta um topico na maioria das vezes
        consumer.subscribe(Collections.singletonList(topic));
    }

    public void run() {
        // while para o consumer ficar rodando constantemente
        while (true) {

            // Esse poll eh a funcao utilizada para mandar o consumer escutar o topico
            //recebe um tempo, ele eh a duracao que o consumer deve escutar o topico
            // vai escutar o topico por 100 milisecundos
            var records = consumer.poll(Duration.ofMillis(100));

            if (!records.isEmpty()) {
                System.out.println("Found " + records.count() + " registries");

                records.forEach(record -> {
                    parse.consume(record);
                });
            }
        }
    }

    private static Properties properties(String groupId) {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // Eh preciso criar um grupo para o consumer poder receber todas as mensagens do producer
        // Eh possivel ter outro consumer com o mesmo id, porem o kafka vai fazer um balanceamento
        // entre a quantidade de mensagens
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
        // Previne de executar a mesma mensagem duas vezes
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
        return properties;
    }

    /**
     * Fecha a porta ao terminar de consumir
     */
    @Override
    public void close() {
        consumer.close();
    }
}
