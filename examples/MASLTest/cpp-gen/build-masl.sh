#!/bin/bash
DOMAIN=Test
MASLBASE="docker run -it -v $PWD:/workspace levistarrett/masl-base"
MASLMC="docker run -v $PWD:/workspace levistarrett/masl-compiler"
$MASLMC -mod /workspace/masl/$DOMAIN/$DOMAIN.mod -output /workspace/cpp-gen/src/$DOMAIN
#$MASLBASE bash -c "cd cpp-gen && cmake . -DCMAKE_INSTALL_PREFIX=/workspace/cpp-gen"
#$MASLBASE bash -c "cd cpp-gen && make install"
