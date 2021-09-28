#!/bin/bash
CIERA_VERSION=2.6.1
CLASSPATH=$HOME/.m2/repository/io/ciera/runtime/$CIERA_VERSION/runtime-$CIERA_VERSION.jar:$HOME/.m2/repository/org/json/json/20201115/json-20201115.jar:$HOME/.m2/repository/com/googlecode/lanterna/lanterna/3.1.1/lanterna-3.1.1.jar:$HOME/.m2/repository/io/ciera/GPS_Watch/1.0.0-SNAPSHOT/GPS_Watch-1.0.0-SNAPSHOT.jar
java -version
java -cp $CLASSPATH gui.Gui $@
