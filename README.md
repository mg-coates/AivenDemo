# AivenDemo
Repo for Aiven "Big Spoke Transport" java demo code.

The demo publishes JSON messages to an Aiven Kafka instance, representing data from a fleet of refrigerated trucks.
These are then consumed by Apache Flink, which is used to identify trucks which are more than 2 degrees outside of the expected -18.0.
Data is then published to either an OnTarget, or Alerts topic depending on whether it's in or out of range.

Configuration for Kafka, Flink, and metrics via InfluxDB & Grafana have been performed via the Aiven UI, and as such are not provided here.

3 main classes exist in the project:
KafkaMessageProducer
produces demo messages to a kafka topic as configured in app.properties

KafkaAlertConsumer
subscribes to the alerts topic 

KafkaOnTargetConsumer
subscribes to the OnTarget topic

Kafka connection details are held in kafka.properties
