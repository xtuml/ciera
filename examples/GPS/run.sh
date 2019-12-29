#!/bin/bash
CIERA_VERSION=1.1.11
CLASSPATH=~/.m2/repository/io/ciera/runtime/$CIERA_VERSION/runtime-$CIERA_VERSION.jar:~/.m2/repository/org/json/json/20180813/json-20180813.jar:~/.m2/repository/io/ciera/GPS_Watch/1.0.0-SNAPSHOT/GPS_Watch-1.0.0-SNAPSHOT.jar
java -cp $CLASSPATH gps_watch.GPS_WatchApplication
