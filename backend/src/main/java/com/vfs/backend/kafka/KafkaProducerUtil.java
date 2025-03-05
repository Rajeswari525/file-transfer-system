package com.vfs.backend.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vfs.backend.dto.KafkaEventDto;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class KafkaProducerUtil {
    private final KafkaProducer<String, String> producer;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public KafkaProducerUtil(@Value("${spring.kafka.bootstrap-servers}") String bootstrapServers) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        this.producer = new KafkaProducer<>(props);
    }

    public void sendMessage(String topic, KafkaEventDto event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            producer.send(new ProducerRecord<>(topic, message));
            producer.flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

//    Optional.ofNullable(event.getFailureReason()).ifPresent(reason -> {
//        System.out.println("Failure Reason: " + reason);
//    });

}
