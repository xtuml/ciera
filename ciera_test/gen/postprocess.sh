#!/bin/bash
while read f; do
  stat "../src-gen/${f:7}" &> /dev/null
  if [[ 0 == $? ]]; then
    mv "../src-gen/${f:7}" "../src-gen/${f:7}.orig"
  fi
done <<< $(find ../src -name "*\.java" -print0)
