.if ("many" == self.multiplicity)
((${self.cast})\
.end if
${root_expression_body}\
.if (sorted)
..sorted((_a, _b) -> {try {int comp = \
  .if (use_util)
${util_name}.compareTo(_a.${accessor}(), _b.${accessor}())\
  .else
_a.${accessor}().compareTo(_b.${accessor}())\
  .end if
;return comp == 0 ? _a.compareTo(_b) : comp;} catch (XtumlException _e) {return 0;}}
  .if (sort_descending)
, false\
  .end if
)\
.end if
.if ("one" == self.multiplicity)
..oneWhere(selected -> ${where_expression_body})\
.elif ("any" == self.multiplicity)
..anyWhere(selected -> ${where_expression_body})\
.else
..where(selected -> ${where_expression_body}))\
.end if
