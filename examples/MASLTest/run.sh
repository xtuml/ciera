#!/bin/bash
CIERA_VERSION=3.0.0-SNAPSHOT
CLASSPATH=$HOME/.m2/repository/io/ciera/runtime-api/$CIERA_VERSION/runtime-api-$CIERA_VERSION.jar:$HOME/.m2/repository/io/ciera/runtime/$CIERA_VERSION/runtime-$CIERA_VERSION.jar:$HOME/.m2/repository/org/xtuml/MASLTest/1.0.0-SNAPSHOT/MASLTest-1.0.0-SNAPSHOT.jar
PROPS=-Dio.ciera.runtime.logLevel=FINEST
java $PROPS -cp $CLASSPATH testapplication.TestApplication $@
