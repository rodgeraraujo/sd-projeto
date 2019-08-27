#!/usr/bin/env bash
echo "Executando async-share"
cd async-share
mvn clean install
cd ..
clear

echo "async-client"
cd async-clientapp
mvn clean compile assembly:single
cd ..
clear

echo "async-sender-pull"
cd async-sender-pull
mvn clean compile assembly:single
mv target/async-sender-0.0.1-SNAPSHOT-jar-with-dependencies.jar ../async-clientapp/target/
cd ..
clear

echo "async-serverapp"
cd async-serverapp
mvn clean compile assembly:single
cd ..
clear

echo "async-receiver-pull"
cd async-receiver-pull
mvn clean compile assembly:single
mv target/async-receiver-0.0.1-SNAPSHOT-jar-with-dependencies.jar ../async-serverapp/target/
cd ..
clear