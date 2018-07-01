#!/bin/bash
cd ../src
FILES=`find . -name *.java`
cd ../src-gen
for f in $FILES; do
  stat $f &> /dev/null
  if [[ $? == 0 ]]; then
    mv $f $f.orig
  fi
done
