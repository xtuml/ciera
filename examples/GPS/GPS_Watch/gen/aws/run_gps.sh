#!/bin/bash
export CLASSPATH=
java -Dsqlite4java.library.path=code_generation/dependencies gps_watch.GPS_WatchAsyncApplication $@
