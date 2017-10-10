#!/bin/bash
GEN_ERATE=~/xtuml/$BPVER/BridgePoint.app/Contents/Eclipse/tools/mc/bin/gen_erate.pyz
OOA_SCHEMA=~/xtuml/$BPVER/BridgePoint.app/Contents/Eclipse/tools/mc/schema/sql/xtumlmc_schema.sql
if [[ $# == 1 ]]; then
  pypy $GEN_ERATE -nopersist -import $OOA_SCHEMA \
                                  -import id_check_model.sql \
                                  -import $1 \
                                  -arch id_check.arc \
                                  -arch model_check.arc
elif [[ $# == 2 ]]; then
  if [[ $1 == "id_check" ]]; then
    pypy $GEN_ERATE -nopersist -import $OOA_SCHEMA \
                                    -import id_check_model.sql \
                                    -import $2 \
                                    -arch id_check.arc
  elif [[ $1 == "model_check" ]]; then
    pypy $GEN_ERATE -nopersist -import $OOA_SCHEMA \
                                    -import id_check_model.sql \
                                    -import $2 \
                                    -arch model_check.arc
  fi
else
  echo "Usage: $0 [id_check|model_check] <model file>"
  echo "       $0 <model file>"
fi
