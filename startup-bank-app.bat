@echo off
echo Starting ZooKeeper...
start "" cmd /k "C:\Kafka\kafka_2.13-3.9.0\bin\windows\zookeeper-server-start.bat C:\Kafka\kafka_2.13-3.9.0\config\zookeeper.properties"
timeout /t 10

echo Starting Kafka...
start "" cmd /k "C:\Kafka\kafka_2.13-3.9.0\bin\windows\kafka-server-start.bat C:\Kafka\kafka_2.13-3.9.0\config\server.properties"
echo Kafka and ZooKeeper startup initiated.
