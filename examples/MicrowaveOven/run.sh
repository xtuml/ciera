#!/bin/bash
CIERA_VERSION=3.0.0-SNAPSHOT
CLASSPATH=$HOME/.m2/repository/io/ciera/runtime/$CIERA_VERSION/runtime-$CIERA_VERSION.jar:$HOME/.m2/repository/io/ciera/MicrowaveOven/2.0.0-SNAPSHOT/MicrowaveOven-2.0.0-SNAPSHOT.jar
java -version
java -cp $CLASSPATH microwaveoven.MicrowaveOvenApplication $@
