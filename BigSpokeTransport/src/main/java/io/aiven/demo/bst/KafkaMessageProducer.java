package io.aiven.demo.bst;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.*;

import java.util.UUID;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaMessageProducer extends KafkaClient {

    private static final String TOPIC = "publishTopic";
    private static final String KEY_FORMAT = "{\"key\": \"%s\"}";

    private static final Logger logger = LoggerFactory.getLogger(KafkaMessageProducer.class);
    private static final String THREAD_SLEEP_PROP = "threadSleep";
    private static final String MAX_LOOPS_PROP = "maxLoops";
    private static final String FLEET_SIZE_PROP = "fleetSize";
    private static final String TARGET_TEMP_PROP = "targetTemp";
    private static final String VARIATION_PROP = "variation";

    private int maxLoops;
    private int threadSleep;
    private int fleetSize;
    private float targetTemp;
    private float variation;
    private final Random random = new Random();
    // get application config
    // create instance for properties to access producer configs

    public static void main(String[] args) {
        KafkaMessageProducer producer = new KafkaMessageProducer();
        producer.run();
    }

    public void run() {
        
        //Assign topicName to string variable
        String topicName = appProps.getProperty(TOPIC);

        ArrayList<TruckDTO> fleet = initialiseFleet();
        Producer<String, String> producer = new KafkaProducer<>(kafkaProps);
        
        for (int i = 0; i < maxLoops; i++) {
            fleet.forEach(truck -> {
                // update the trucks current temp
                truck.setCurrentTemp(boundedRandomFloat());

                try {
                    producer.send(new ProducerRecord<>(topicName,
                            randomJSONKeyGenerator(), mapper.writeValueAsString(truck)));
                    logger.info("Message sent successfully", mapper.writeValueAsString(truck));
                } catch (JsonProcessingException ex) {
                    logger.error(null, ex);
                }
                try {
                    Thread.sleep(threadSleep);
                } catch (InterruptedException ex) {
                    logger.error(null, ex);
                }
            });
        }
        producer.close();

    }

    private String randomJSONKeyGenerator() {
        return String.format(KEY_FORMAT, UUID.randomUUID());
    }

    private ArrayList<TruckDTO> initialiseFleet() {
        ArrayList<TruckDTO> fleet = new ArrayList(fleetSize);
        TruckDTO truck;
        for (int i = 0; i < fleetSize; i++) {
            truck = new TruckDTO(targetTemp, boundedRandomFloat());
            fleet.add(truck);
        }
        return fleet;
    }

    protected void initialiseProperties() {
        super.initialiseProperties();
        maxLoops = Integer.parseInt(appProps.getProperty(MAX_LOOPS_PROP));
        threadSleep = Integer.parseInt(appProps.getProperty(THREAD_SLEEP_PROP));
        fleetSize = Integer.parseInt(appProps.getProperty(FLEET_SIZE_PROP));
        targetTemp = Float.parseFloat(appProps.getProperty(TARGET_TEMP_PROP));
        variation = Float.parseFloat(appProps.getProperty(VARIATION_PROP));
    }
    
    /*
    returns a random float within +- variation of the targetTemp
     */
    private float boundedRandomFloat() {
        float currentTemp = random.nextFloat() * variation * 2 - variation + targetTemp;
        currentTemp = Math.round(currentTemp * 10) / 10.0f;
        return currentTemp;
    }
}
