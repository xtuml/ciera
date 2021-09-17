#!/bin/bash
MAVEN_REPO=/home/levi/.m2/repository
CIERA_VERSION=2.4.1-SNAPSHOT
STRATUS_VERSION=1.0.1-SNAPSHOT
CLASSPATH=$MAVEN_REPO/io/ciera/runtime/$CIERA_VERSION/runtime-$CIERA_VERSION.jar:$MAVEN_REPO/org/antlr/antlr4-runtime/4.9.1/antlr4-runtime-4.9.1.jar:$MAVEN_REPO/org/xtuml/stratus/$STRATUS_VERSION/stratus-$STRATUS_VERSION.jar:$MAVEN_REPO/io/ciera/tool-core-masl/$CIERA_VERSION/tool-core-masl-$CIERA_VERSION.jar
java -cp $CLASSPATH io.ciera.tool.CoreMaslTool $@
