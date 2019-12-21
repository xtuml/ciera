#!/bin/bash
CLASSPATH=~/.m2/repository/io/ciera/io.ciera.runtime/1.1.9/io.ciera.runtime-1.1.9.jar:~/.m2/repository/org/json/json/20180813/json-20180813.jar:~/.m2/repository/io/ciera/GPS_Watch/1.0.0-SNAPSHOT/GPS_Watch-1.0.0-SNAPSHOT.jar
java -cp $CLASSPATH gps_watch.GPS_WatchApplication
