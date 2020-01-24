.if ("many" == self.multiplicity)
((${self.cast})\
.end if
${root_expression_body}\
.if (sorted)
..sorted((a, b) -> {try {int comp = \
  .if (use_util)
${util_name}.compareTo(a.${accessor}(), b.${accessor}())\
  .else
a.${accessor}().compareTo(b.${accessor}())\
  .end if
;return comp == 0 ? a.compareTo(b) : comp;} catch (XtumlException e) {return 0;}}
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
