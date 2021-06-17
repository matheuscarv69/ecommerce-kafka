package ecommerce.producersServices.core;

import ecommerce.gson.GsonSerializer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.Closeable;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaDispatcher<T> implements Closeable {

    private final KafkaProducer<String, T> producer;

    public KafkaDispatcher() {
        this.producer = new KafkaProducer<>(properties());
    }

    public void send(String topic, String key, T value) throws ExecutionException, InterruptedException {
        // objeto de registro, ele diz qual o topico e qual a mensagem,
        // porem ele recebe no tipo key, value
        // mas aqui estamos mandando o mesmo texto para os dois
        var record = new ProducerRecord<>(topic, key, value);

        // envia o registro para o kafka
        // pode receber um callback como usado abaixo
        // para printar na tela se o envio foi com sucesso ou nao
        Callback callback = (data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }

            System.out.println("Sucesso enviado: "
                    + data.topic()
                    + ":::partition "
                    + data.partition()
                    + "/ offset "
                    + data.offset()
                    + "/ timestamp"
                    + data.timestamp());
        };

        // por default o metodo send eh assincrono, porem precisamos
        // esperar com que ele envie o registro para o kafka e possamos
        // ver uma mensagem de sucesso, para isso usamos o metodo get
        // ele deixa o comportamento do send como sincrono (pode-se dizer)
        producer.send(record, callback).get();
    }

    // Properties usado para criacao do producer
    private static Properties properties() {
        var properties = new Properties();
        // define a propriedade de local do kafka (localhost:9092)
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        // define a maneira como se deve ser deserializada a chave que será recebida no recod
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // define a maneira como se deve ser deserializada o valor que será recebida no recod
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName());

        return properties;
    }

    /**
     * Fecha a porta ao terminar de enviar os emails
     */
    @Override
    public void close() {
        producer.close();
    }
}
