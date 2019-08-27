#!/usr/bin/env bash

java -jar async-serverapp-jar-with-dependencies.jar&

sleep 3

java -jar async-receiver-jar-with-dependencies.jar
