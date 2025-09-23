package org.example.taskservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

@Service
@EnableKafka
public class UserEventConsumer {
    private static final String TOPIC = "user-delete-events";

    public UserEventConsumer(){
        Properties properties = new Properties();

        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("group.id", "task-service-group");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("auto.offset.reset", "earliest");

    }

    @Autowired
    private TaskService taskService;

    @KafkaListener(topics = TOPIC, groupId = "task-service-group")
    public void consumeUserDeleteEvent(ConsumerRecord<String, String> record) throws JsonProcessingException {
        String message = record.value();

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> mapRecord = objectMapper.readValue(message, Map.class);

        System.out.println("Получено событие: " + mapRecord.get("event"));
        taskService.updateDeleteUserTasks(mapRecord.get("username").toString(), mapRecord.get("event").toString());
    }
}
