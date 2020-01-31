#!/bin/bash
if [[ -z ${BPHOME+x} ]]; then
  echo "BPHOME environment variable not set"
  exit 1
fi
echo "-------------------------------------"
echo "Preparing workspace..."
export WORKSPACE=`mktemp -d`
$BPHOME/bridgepoint -nosplash -data $WORKSPACE -application org.eclipse.cdt.managedbuilder.core.headlessbuild -importAll $PWD/../../src/runtime
$BPHOME/bridgepoint -nosplash -data $WORKSPACE -application org.eclipse.cdt.managedbuilder.core.headlessbuild -importAll $PWD
echo "Done."
echo "-------------------------------------"
echo "Run this command to set up workspace:"
echo
echo "  export WORKSPACE=$WORKSPACE"
echo
echo "-------------------------------------"
