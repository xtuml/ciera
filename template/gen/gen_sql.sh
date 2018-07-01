#!/bin/bash
CP=/Users/levi/xtuml/m6150.2018-09-12-1306_10129-build/BridgePoint.app/Contents/Eclipse/plugins/antlr-4.7.1-complete.jar
java -Xmx500M -cp "$CP" org.antlr.v4.Tool $PWD/../grammar/SQL.g4      -o $PWD/../src-gen/io/ciera/sql/parser -listener -no-visitor -encoding UTF-8
