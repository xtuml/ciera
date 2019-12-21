#!/bin/bash
CLASSPATH=~/.m2/repository/io/ciera/io.ciera.runtime/1.1.9/io.ciera.runtime-1.1.9.jar:~/.m2/repository/org/antlr/antlr4-runtime/4.7.1/antlr4-runtime-4.7.1.jar:~/.m2/repository/io/ciera/pei/1.0.0-SNAPSHOT/pei-1.0.0-SNAPSHOT.jar
java -cp $CLASSPATH pei.PeiApplication < /dev/null
