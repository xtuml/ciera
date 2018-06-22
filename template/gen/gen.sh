#!/bin/bash
CP=/Users/levi/xtuml/m6150.2018-06-20-1323_10129-build/BridgePoint.app/Contents/Eclipse/plugins/antlr-4.7-complete.jar:../summit/bin:../cairn/bin
java -Xmx500M -cp "$CP" org.antlr.v4.Tool $PWD/grammar/RSLLexer.g4 -o $PWD/src-gen/io/ciera/template/rsl/parser -listener -no-visitor -encoding UTF-8
java -Xmx500M -cp "$CP" org.antlr.v4.Tool $PWD/grammar/RSL.g4      -o $PWD/src-gen/io/ciera/template/rsl/parser -listener -no-visitor -encoding UTF-8
TMP=`mktemp`
find src-gen -name *.java >> $TMP
find javasrc -name *.java >> $TMP
mkdir -p build/classes
javac -classpath "$CP" -d build/classes @$TMP
