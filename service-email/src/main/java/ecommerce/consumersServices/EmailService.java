package ecommerce.consumersServices;

import ecommerce.consumers.KafkaService;
import ecommerce.model.Email;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.HashMap;

public class EmailService {

    public static void main(String[] args) {
        var emailService = new EmailService();
        try (var service = new KafkaService(EmailService.class.getSimpleName(),
                "ECOMMERCE_SEND_EMAIL",
                emailService::parse,
                Email.class,
                new HashMap<>())) {

            service.run();
        }
    }

    private void parse(ConsumerRecord<String, Email> record) {
        System.out.println("---------------------");
        System.out.println("Send email");
        System.out.println("key: " + record.key());
        System.out.println("value: " + record.value());
        System.out.println("partition: " + record.partition());
        System.out.println("offset: " + record.offset());


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("send email simulation");
        }
        System.out.println("Email sent");
    }

}