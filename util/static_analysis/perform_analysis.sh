#!/bin/bash
pypy ~/bin/gen_erate -nopersist -import ~/git/mc/schema/sql/xtumlmc_schema.sql -import initialized.sql -import $1 -arch ref_integrity.arc
