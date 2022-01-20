#!/bin/bash
CIERA_VERSION=3.0.0-SNAPSHOT
CLASSPATH=$HOME/.m2/repository/io/ciera/runtime/$CIERA_VERSION/runtime-$CIERA_VERSION.jar:$HOME/.m2/repository/io/ciera/runtime-util/$CIERA_VERSION/runtime-util-$CIERA_VERSION.jar:$HOME/.m2/repository/org/json/json/20201115/json-20201115.jar:$HOME/.m2/repository/io/ciera/GPS_Watch/1.0.0-SNAPSHOT/GPS_Watch-1.0.0-SNAPSHOT.jar
PROPS=-Dio.ciera.runtime.logLevel="FINEST"
java -version
java $PROPS -cp $CLASSPATH gps_watch.GPS_WatchApplication $@
