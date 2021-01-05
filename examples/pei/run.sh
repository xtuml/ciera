#!/bin/bash
CIERA_VERSION=2.3.1
CLASSPATH=$HOME/.m2/repository/io/ciera/runtime/$CIERA_VERSION/runtime-$CIERA_VERSION.jar:$HOME/.m2/repository/org/antlr/antlr4-runtime/4.7.1/antlr4-runtime-4.7.1.jar:$HOME/.m2/repository/io/ciera/pei/1.0.0-SNAPSHOT/pei-1.0.0-SNAPSHOT.jar
java -cp $CLASSPATH pei.PeiApplication $@
