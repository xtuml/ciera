#!/bin/bash
CIERA_VERSION=1.1.11
CLASSPATH=~/.m2/repository/io/ciera/runtime/1.1.11/runtime-1.1.11.jar:~/.m2/repository/org/antlr/antlr4-runtime/4.7.1/antlr4-runtime-4.7.1.jar:~/.m2/repository/io/ciera/pei/1.0.0-SNAPSHOT/pei-1.0.0-SNAPSHOT.jar
if [[ $# == 1 ]]; then
  java -cp $CLASSPATH pei.PeiApplication < $1
else
  java -cp $CLASSPATH pei.PeiApplication < /dev/null
fi
