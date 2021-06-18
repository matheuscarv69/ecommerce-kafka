package ecommerce.consumersServices;

import ecommerce.consumers.KafkaService;
import ecommerce.model.Order;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.HashMap;

public class FraudDetectorService {

    public static void main(String[] args) {
        var fraudService = new FraudDetectorService();
        try (var service = new KafkaService<>(FraudDetectorService.class.getSimpleName(),
                "ECOMMERCE_NEW_ORDER",
                fraudService::parse,
                Order.class,
                new HashMap<>())) {

            service.run();
        }
    }

    private void parse(ConsumerRecord<String, Order> record) {
        System.out.println("---------------------");
        System.out.println("Processesing new order, checking for fraud");
        System.out.println("key: " + record.key());
        System.out.println("value: " + record.value());
        System.out.println("partition: " + record.partition());
        System.out.println("offset: " + record.offset());

        // simulando uma fraude
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("fraud simulation");
        }
        System.out.println("Order processed");
    }

}
