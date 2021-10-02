#!/bin/bash
CIERA_VERSION=2.6.2-SNAPSHOT
CLASSPATH=$HOME/.m2/repository/io/ciera/runtime/$CIERA_VERSION/runtime-$CIERA_VERSION.jar:$HOME/.m2/repository/org/xtuml/ALU/1.0.0-SNAPSHOT/ALU-1.0.0-SNAPSHOT.jar
java -cp $CLASSPATH org.xtuml.calculator.ALUApplication $@
