#!/bin/bash
CIERA_VERSION=1.1.11
CLASSPATH=~/.m2/repository/io/ciera/runtime/$CIERA_VERSION/runtime-$CIERA_VERSION.jar:~/.m2/repository/io/ciera/MicrowaveOven/1.0.0-SNAPSHOT/MicrowaveOven-1.0.0-SNAPSHOT.jar
java -cp $CLASSPATH microwaveoven.MicrowaveOvenApplication
