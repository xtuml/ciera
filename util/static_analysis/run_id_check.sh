#!/bin/bash
if [[ $# == 1 ]]; then
  pypy ~/bin/gen_erate -nopersist -import ~/git/mc/schema/sql/xtumlmc_schema.sql -import id_check_model.sql -import $1 -arch id_check.arc
fi
