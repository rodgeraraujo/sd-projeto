#!/usr/bin/env bash

java -jar async-sender-jar-with-dependencies.jar&

sleep 3

java -jar async-clientapp-jar-with-dependencies.jar
