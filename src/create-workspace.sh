#!/bin/bash
CIERA_VERSION=2.0.1-SNAPSHOT
BP_LOC=$HOME/git/bridgepoint
MC_LOC=$HOME/git/mc
if [[ -z ${BPHOME+x} ]]; then
  echo "BPHOME environment variable not set"
  exit 1
fi
echo "-------------------------------------"
echo "Preparing workspace..."
find . -name target -exec rm -rf {} \; &> /dev/null
if [[ $# == 1 ]]; then
  export WORKSPACE=$1
else
  export WORKSPACE=`mktemp -d`
fi
mkdir -p $WORKSPACE/runtime
unzip -q $HOME/.m2/repository/io/ciera/runtime/$CIERA_VERSION/runtime-$CIERA_VERSION.jar -d $WORKSPACE/runtime
$BPHOME/bridgepoint -nosplash -data $WORKSPACE -application org.eclipse.cdt.managedbuilder.core.headlessbuild -import $BP_LOC/src/org.xtuml.bp.ui.marking
$BPHOME/bridgepoint -nosplash -data $WORKSPACE -application org.eclipse.cdt.managedbuilder.core.headlessbuild -import $MC_LOC/model/mcooa
$BPHOME/bridgepoint -nosplash -data $WORKSPACE -application org.eclipse.cdt.managedbuilder.core.headlessbuild -importAll $PWD
echo "Done."
echo "-------------------------------------"
echo "Run this command to set up workspace:"
echo
echo "  export WORKSPACE=$WORKSPACE"
echo
echo "-------------------------------------"
