package ecommerce;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // cria um producer do kafka, recebe um objeto Properties (criado lá no final)
        var producer = new KafkaProducer<String, String>(properties());

        // cria uma mensagem
        var value = "132123,67523, 1234";

        // objeto de registro, ele diz qual o topico e qual a mensagem,
        // porem ele recebe no tipo key, value
        // mas aqui estamos mandando o mesmo texto para os dois
        var record = new ProducerRecord<>("ECOMMERCE_NEW_ORDER", value, value);

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

        var email = "Thanks you for your order! We are processing your order!";
        var emailRecord = new ProducerRecord<>("ECOMMERCE_SEND_EMAIL", email, email);
        producer.send(emailRecord, callback).get();


    }

    // Properties usado para criacao do producer
    private static Properties properties() {
        var properties = new Properties();
        // define a propriedade de local do kafka (localhost:9092)
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        // define a maneira como se deve ser deserializada a chave que será recebida no recod
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // define a maneira como se deve ser deserializada o valor que será recebida no recod
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return properties;
    }
}
