#!/bin/bash
VERSION=v1.0.0.prerelease
VERSION_DATE=`date "+%Y-%m-%d %H:%M:%S"`
mkdir -p ../src-gen/io/ciera/runtime
echo "version=$VERSION" > ../src-gen/io/ciera/runtime/io.ciera.runtime.properties
echo "version_date=$VERSION_DATE" >> ../src-gen/io/ciera/runtime/io.ciera.runtime.properties
