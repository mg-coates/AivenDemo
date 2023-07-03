/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.aiven.demo.bst;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import java.util.Arrays;
import java.time.Duration;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mcoates
 */
public class KafkaTopicConsumer extends KafkaClient {

    private static final String TOPIC = "alertTopic";
    private static final Logger logger = LoggerFactory.getLogger(KafkaTopicConsumer.class);
    private String topicName;

    public static void main(String[] args) {
        if (args.length!=1) {
            logger.error("You must include a single argument for the Kafka topic to follow");
            return;
        }
        KafkaTopicConsumer consumer = new KafkaTopicConsumer(args[0]);
        consumer.run();
    }

    public KafkaTopicConsumer(String topicName) {
        this.topicName = topicName;
    }

    public void run() {
        Consumer<String, String> consumer = new KafkaConsumer<>(kafkaProps);
        consumer.subscribe(Arrays.asList(topicName));
        while (true) {
            ConsumerRecords<String, String> records
                    = consumer.poll(Duration.ofMillis(100));

            for (ConsumerRecord<String, String> record : records) {
                logger.info("Truck Temperature outside Range: " + record.value());
            }
        }
    }

}
