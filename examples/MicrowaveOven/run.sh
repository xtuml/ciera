#!/bin/bash
CIERA_VERSION=2.6.2
CLASSPATH=$HOME/.m2/repository/io/ciera/runtime/$CIERA_VERSION/runtime-$CIERA_VERSION.jar:$HOME/.m2/repository/io/ciera/MicrowaveOven/1.0.0-SNAPSHOT/MicrowaveOven-1.0.0-SNAPSHOT.jar
java -version
java -cp $CLASSPATH microwaveoven.MicrowaveOvenApplication $@
