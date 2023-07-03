# AivenDemo
Repo for Aiven "Big Spoke Transport" java demo code.

The demo publishes JSON messages to an Aiven Kafka instance, representing data from a fleet of refrigerated trucks.
These are then consumed by Apache Flink, which is used to identify trucks which are more than 2 degrees outside of the expected -18.0.
Data is then published to either an OnTarget, or Alerts topic depending on whether it's in or out of range.

Configuration for Kafka, Flink, and metrics via InfluxDB & Grafana have been performed via the Aiven UI, and as such are not provided here.

2 main classes exist in the project:
KafkaMessageProducer
produces demo messages to a kafka topic as configured in app.properties
mvn compile exec:java -Dexec.mainClass="io.aiven.demo.bst.KafkaMessageProducer"

KafkaTopicConsumer
To subscribe to the alerts topic 
mvn compile exec:java -Dexec.mainClass="io.aiven.demo.bst.KafkaTopicConsumer" -Dexec.args="trucks.refrigerated.alert"

& to subscribe to the OnTarget topic
mvn compile exec:java -Dexec.mainClass="io.aiven.demo.bst.KafkaTopicConsumer" -Dexec.args="trucks.refrigerated.ontarget"

Kafka connection details are held in kafka.properties
