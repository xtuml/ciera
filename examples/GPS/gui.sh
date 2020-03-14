#!/bin/bash
CIERA_VERSION=2.1.0
CLASSPATH=$HOME/.m2/repository/io/ciera/runtime/$CIERA_VERSION/runtime-$CIERA_VERSION.jar:$HOME/.m2/repository/org/json/json/20180813/json-20180813.jar:$HOME/.m2/repository/com/googlecode/lanterna/lanterna/3.0.1/lanterna-3.0.1.jar:$HOME/.m2/repository/io/ciera/GPS_Watch/1.0.0-SNAPSHOT/GPS_Watch-1.0.0-SNAPSHOT.jar
java -cp $CLASSPATH gui.Gui $@
