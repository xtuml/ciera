#!/bin/bash
GEN_ERATE=~/bin/gen_erate.pyz
OOA_SCHEMA=$BPHOME/tools/mc/schema/sql/xtumlmc_schema.sql
if [[ $# > 0 ]]; then
  IMPORT_FILES=
  ARCHETYPE=
  if [[ $1 == "id_check" || $1 == "model_check" ]]; then
    ARCHETYPE="-arch $1.arc"
    for FILE in ${@:2}; do
      IMPORT_FILES="$IMPORT_FILES -import $FILE"
    done
  else
    ARCHETYPE="-arch id_check.arc -arch model_check.arc"
    for FILE in $@; do
      IMPORT_FILES="$IMPORT_FILES -import $FILE"
    done
  fi
  pypy $GEN_ERATE -nopersist -import $OOA_SCHEMA -import id_check_model.sql $IMPORT_FILES $ARCHETYPE
else
  echo "Usage: $0 [id_check|model_check] <model files>"
  echo "       $0 <model files>"
fi

