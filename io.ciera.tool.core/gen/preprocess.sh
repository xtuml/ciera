#!/bin/bash
mkdir code_generation/xtuml
mv code_generation/*.sql code_generation/xtuml
for f in code_generation/xtuml/*.sql; do
  mv -- "$f" "${f%.sql}.xtuml"
done
