#!/bin/bash
CP=/Users/levi/xtuml/m6150.2018-09-12-1306_10129-build/BridgePoint.app/Contents/Eclipse/plugins/antlr-4.7.1-complete.jar:/Users/levi/git/ciera/template/build/classes
java -cp "$CP" io.ciera.template.rsl.parser.RSLParseUtil $1
