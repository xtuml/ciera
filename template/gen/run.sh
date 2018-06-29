#!/bin/bash
CP=/Users/levi/xtuml/m6150.2018-06-20-1323_10129-build/BridgePoint.app/Contents/Eclipse/plugins/antlr-4.7-complete.jar:/Users/levi/git/ciera/template/build/classes
java -cp "$CP" io.ciera.template.rsl.parser.RSLParseUtil templates
