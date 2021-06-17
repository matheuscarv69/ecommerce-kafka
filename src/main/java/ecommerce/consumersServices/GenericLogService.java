package ecommerce.consumersServices;

import ecommerce.consumersServices.core.KafkaService;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.regex.Pattern;

public class GenericLogService {

    public static void main(String[] args) {

        var logService = new GenericLogService();

        // cria um consumer do kafka
        try (var service = new KafkaService(GenericLogService.class.getSimpleName(),
                Pattern.compile("ECOMMERCE.*"),
                logService::parse)) {

            service.run();
        }

    }

    private void parse(ConsumerRecord<String, String> record) {
        System.out.println("---------------------");
        System.out.println("LOG");
        System.out.println("Topic: " + record.topic());
        System.out.println("key: " + record.key());
        System.out.println("value: " + record.value());
        System.out.println("partition: " + record.partition());
        System.out.println("offset: " + record.offset());
    }
}
