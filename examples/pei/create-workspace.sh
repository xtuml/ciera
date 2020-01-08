#!/bin/bash
echo "Preparing workspace..."
export WORKSPACE=`mktemp -d`
$BPHOME/bridgepoint -nosplash -data $WORKSPACE -application org.eclipse.cdt.managedbuilder.core.headlessbuild -importAll $PWD/../../src/runtime
$BPHOME/bridgepoint -nosplash -data $WORKSPACE -application org.eclipse.cdt.managedbuilder.core.headlessbuild -importAll $PWD
echo "Run this command to set up workspace:"
echo
echo "export WORKSPACE=$WORKSPACE"
echo
