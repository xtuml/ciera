#!/bin/bash
CP=/Users/levi/xtuml/m6150.2018-06-03-1933_10129-build/BridgePoint.app/Contents/Eclipse/plugins/antlr-4.7-complete.jar:../template/bin
java -cp "$CP" io.ciera.template.rsl.parser.test.TestRSL
