#!/bin/bash
CP=/Users/levi/xtuml/m6150.2018-06-03-1933_10129-build/BridgePoint.app/Contents/Eclipse/plugins/antlr-4.7-complete.jar:../template/bin:../cairn/bin:../summit/bin
java -Xmx500M -cp "$CP" org.antlr.v4.Tool $PWD/../template/grammar/RSLLexer.g4 -o $PWD/../template/src-gen/io/ciera/template/rsl/parser -listener -no-visitor -encoding UTF-8
java -Xmx500M -cp "$CP" org.antlr.v4.Tool $PWD/../template/grammar/RSL.g4      -o $PWD/../template/src-gen/io/ciera/template/rsl/parser -listener -no-visitor -encoding UTF-8
TMP=`mktemp`
find ../template/src-gen -name *.java >> $TMP
find src                 -name *.java >> $TMP
find ../template/javasrc -name *.java >> $TMP
javac -classpath "$CP" -d ../template/bin @$TMP
