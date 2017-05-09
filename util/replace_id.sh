#!/bin/bash

TMPFILE=`mktemp`
find ../model/javaooa/models -name *.xtuml -print >> $TMPFILE
find ../model/ciera/models -name *.xtuml -print >> $TMPFILE

TMPFILE2=`mktemp`
while read f; do
  echo "Processing '$f' phase 1."
  while grep -q "^.*TRACE::log.*id:[0-9][0-9]*.*$" "$f"; do
    sed -E '1,/^.*TRACE::log.*id:[0-9][0-9]*.*$/s/(^.*TRACE::log.*id:)([0-9][0-9]*)(.*$)/\1levi\3/g' "$f" > $TMPFILE2
    cp $TMPFILE2 "$f"
  done
done < $TMPFILE

COUNTER=1
while read f; do
  echo "Processing '$f' phase 2."
  while grep -q "^.*TRACE::log.*id:levi.*$" "$f"; do
    sed -E '1,/^.*TRACE::log.*id:levi.*$/s/(^.*TRACE::log.*id:)(levi)(.*$)/\1'$COUNTER'\3/g' "$f" > $TMPFILE2
    cp $TMPFILE2 "$f"
    COUNTER=$[$COUNTER +1]
  done
done < $TMPFILE
