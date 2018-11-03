#!/bin/bash
ANTLR=../../../release/antlr-4.7.1-complete.jar
java -Xmx500M -jar $ANTLR $PWD/../grammar/RSLLexer.g -o $PWD/../src-gen/io/ciera/tool/templateengine/parser -listener -no-visitor -encoding UTF-8
java -Xmx500M -jar $ANTLR $PWD/../grammar/RSL.g      -o $PWD/../src-gen/io/ciera/tool/templateengine/parser -listener -no-visitor -encoding UTF-8
