package ecommerce;

import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // cria um producer do kafka, recebe um objeto Properties
        try (var dispatcher = new KafkaDispatcher()) {

            // For para enviar 10 mensagens e verificarmos se o balaceamento esta funcionando
            for (var i = 0; i < 10; i++) {
                // Key utilizada para o kafka conseguir fazer o balanceamento de carga
                // ele irá balencear em qual partition a mensagem irá cair
                var key = UUID.randomUUID().toString();

                // cria uma mensagem
                var value = key + "132123,67523, 1234";

                dispatcher.send("ECOMMERCE_NEW_ORDER", key, value);

                var email = "Thanks you for your order! We are processing your order!";
                dispatcher.send("ECOMMERCE_SEND_EMAIL", key, email);
            }
        }
    }

}
