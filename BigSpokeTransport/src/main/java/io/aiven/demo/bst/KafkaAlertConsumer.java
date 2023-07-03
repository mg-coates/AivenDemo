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
public class KafkaAlertConsumer extends KafkaClient {

    private static final String TOPIC = "alertTopic";
    private static final Logger logger = LoggerFactory.getLogger(KafkaAlertConsumer.class);

    public static void main(String[] args) {
        KafkaAlertConsumer consumer = new KafkaAlertConsumer();
        consumer.run();
    }

    public void run() {
        String topicName = appProps.getProperty(TOPIC);
        Consumer<String, String> consumer = new KafkaConsumer<>(kafkaProps);
        consumer.subscribe(Arrays.asList(topicName));
         while(true){
            ConsumerRecords<String, String> records =
                    consumer.poll(Duration.ofMillis(100));

            for (ConsumerRecord<String, String> record : records){
                logger.info("Truck Temperature outside Range: " + record.value());
                //logger.info("Key: " + record.key() + ", Value: " + record.value());
                //logger.info("Partition: " + record.partition() + ", Offset:" + record.offset());
            }
        }
    }

}
