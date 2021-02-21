#!/bin/bash
MASLBASE="docker run -it -v $PWD:/workspace levistarrett/masl-base"
MASLMC="docker run -v $PWD:/workspace levistarrett/masl-compiler"
$MASLMC -mod /workspace/masl/HelloWorld/HelloWorld.mod -output /workspace/cpp-gen/src/HelloWorld
$MASLMC -domainpath /workspace/masl/HelloWorld -prj /workspace/masl/HelloWorld_proc/HelloWorld_proc.prj -output /workspace/cpp-gen/src/HelloWorld_proc
$MASLBASE bash -c "cd cpp-gen && cmake . -DCMAKE_INSTALL_PREFIX=/workspace/cpp-gen"
$MASLBASE bash -c "cd cpp-gen && make install"
