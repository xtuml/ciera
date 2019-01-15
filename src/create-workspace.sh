#!/bin/bash
echo "Preparing workspace..."
export WORKSPACE=`mktemp -d`
GIT_FORK=leviathan747
GIT_BRANCH=ciera
GIT_HOME=`mktemp -d`
git clone https://github.com/$GIT_FORK/bridgepoint.git --branch $GIT_BRANCH --depth 1 $GIT_HOME/bridgepoint
git clone https://github.com/$GIT_FORK/mc.git --branch $GIT_BRANCH --depth 1 $GIT_HOME/mc
$BPHOME/bridgepoint -nosplash -data $WORKSPACE -application org.eclipse.cdt.managedbuilder.core.headlessbuild -importAll $GIT_HOME/bridgepoint/src/org.xtuml.bp.ui.marking/
$BPHOME/bridgepoint -nosplash -data $WORKSPACE -application org.eclipse.cdt.managedbuilder.core.headlessbuild -importAll $GIT_HOME/mc/model/mcooa/
$BPHOME/bridgepoint -nosplash -data $WORKSPACE -application org.eclipse.cdt.managedbuilder.core.headlessbuild -importAll $PWD
echo "Run this command to set up workspace:"
echo
echo "export WORKSPACE=$WORKSPACE"
echo
