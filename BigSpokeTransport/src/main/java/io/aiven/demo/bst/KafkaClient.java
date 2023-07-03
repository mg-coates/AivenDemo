/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.aiven.demo.bst;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mcoates
 */
public class KafkaClient {

    private static final Logger logger = LoggerFactory.getLogger(KafkaClient.class);
    protected static final String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    protected static final String appConfigPath = rootPath + "app.properties";
    // get application config
    protected Properties appProps = new Properties();
    protected static final String kafkaConfigPath = rootPath + "kafkaClient.properties";
    // create instance for properties to access producer configs
    protected Properties kafkaProps = new Properties();
    protected ObjectMapper mapper;
 
    
    public KafkaClient() {        
        initialiseProperties();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    protected void initialiseProperties() {
        try {
            appProps.load(new FileInputStream(appConfigPath));
        } catch (IOException ex) {
            logger.error(appConfigPath + " could not be loaded", ex);
        }
        try {
            kafkaProps.load(new FileInputStream(kafkaConfigPath));
        } catch (IOException ex) {
            logger.error(kafkaConfigPath + " could not be loaded", ex);
        }
        logger.info(kafkaProps.toString());

    }

}
