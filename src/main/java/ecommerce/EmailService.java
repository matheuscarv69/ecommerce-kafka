package ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class EmailService {

    public static void main(String[] args) {
        var emailService = new EmailService();
        var service = new KafkaService(FraudDetectorService.class.getSimpleName(), "ECOMMERCE_SEND_EMAIL", emailService::parse);
        service.run();
    }

    private void parse(ConsumerRecord<String, String> record) {
        System.out.println("---------------------");
        System.out.println("Send email");
        System.out.println("key: " + record.key());
        System.out.println("value: " + record.value());
        System.out.println("partition: " + record.partition());
        System.out.println("offset: " + record.offset());

        // simulando uma fraude
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("send email simulation");
        }
        System.out.println("Email sent");
    }

}