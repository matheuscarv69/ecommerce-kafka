package ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class FraudDetectorService {

    public static void main(String[] args) {
        var fraudService = new FraudDetectorService();
        var service = new KafkaService(FraudDetectorService.class.getSimpleName(),
                "ECOMMERCE_NEW_ORDER",
                fraudService::parse);

        service.run();
    }

    private void parse(ConsumerRecord<String, String> record) {
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
