#!/bin/bash
ANTLR=../../../release/antlr-4.7.1-complete.jar
java -Xmx500M -jar $ANTLR $PWD/../grammar/SQL.g -o $PWD/../src-gen/io/ciera/runtime/instanceloading/sql/parser -listener -no-visitor -encoding UTF-8
