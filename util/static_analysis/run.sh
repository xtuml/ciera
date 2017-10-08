#!/bin/bash
pypy ~/bin/gen_erate -nopersist -import ~/git/mc/schema/sql/xtumlmc_schema.sql -import initialized.sql -import ../../ciera/gen/code_generation/a.xtuml -arch ref_integrity.arc
