package com.fts.fileTransfer.kafka;

import com.amazonaws.lambda.thirdparty.com.fasterxml.jackson.databind.ObjectMapper;
import com.fts.fileTransfer.dto.KafkaEventDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Component
public class KafkaConsumerUtil {
    private final KafkaConsumer<String, String> consumer;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${spring.kafka.bootstrap-servers}")
    public String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    public String groupId;

    public KafkaConsumerUtil(String topic) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("group.id", groupId);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        this.consumer = new KafkaConsumer<>(props);
        this.consumer.subscribe(Collections.singletonList(topic));
    }

    public KafkaEventDto consumeMessage() {
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
        for (ConsumerRecord<String, String> record : records) {
            try {
                return objectMapper.readValue(record.value(), KafkaEventDto.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
