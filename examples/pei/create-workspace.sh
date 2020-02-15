#!/bin/bash
CIERA_VERSION=2.0.1-SNAPSHOT
if [[ -z ${BPHOME+x} ]]; then
  echo "BPHOME environment variable not set"
  exit 1
fi
echo "-------------------------------------"
echo "Preparing workspace..."
export WORKSPACE=`mktemp -d`
mkdir $WORKSPACE/runtime
unzip -q $HOME/.m2/repository/io/ciera/runtime/$CIERA_VERSION/runtime-$CIERA_VERSION.jar -d $WORKSPACE/runtime
$BPHOME/bridgepoint -nosplash -data $WORKSPACE -application org.eclipse.cdt.managedbuilder.core.headlessbuild -import $WORKSPACE/runtime
$BPHOME/bridgepoint -nosplash -data $WORKSPACE -application org.eclipse.cdt.managedbuilder.core.headlessbuild -import $PWD
echo "Done."
echo "-------------------------------------"
echo "Run this command to set up workspace:"
echo
echo "  export WORKSPACE=$WORKSPACE"
echo
echo "-------------------------------------"
