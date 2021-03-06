package ecommerce.producersServices;

import ecommerce.model.Email;
import ecommerce.model.Order;
import ecommerce.producers.KafkaDispatcher;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException,
            InterruptedException {

        // cria um producer do kafka, recebe um objeto Properties
        try (var orderDispatcher = new KafkaDispatcher<Order>()) {
            try (var emailDispatcher = new KafkaDispatcher<Email>()) {

                // For para enviar 10 mensagens e verificarmos se o balaceamento esta funcionando
                for (var i = 0; i < 10; i++) {
                    // Key utilizada para o kafka conseguir fazer o balanceamento de carga
                    // ele irá balencear em qual partition a mensagem irá cair
                    var userId = UUID.randomUUID().toString();
                    var orderId = UUID.randomUUID().toString();
                    var amount = Math.random() * 5000 + 1;

                    var order = new Order(userId, orderId, new BigDecimal(amount));

                    orderDispatcher.send("ECOMMERCE_NEW_ORDER", userId, order);

                    var email = new Email("Congratulations!", "Thanks you for your order! We are processing your order!");
                    emailDispatcher.send("ECOMMERCE_SEND_EMAIL", userId, email);
                }
            }
        }
    }

}
