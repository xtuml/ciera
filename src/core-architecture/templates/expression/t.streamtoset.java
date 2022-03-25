.if (ordered_set)
${base_expr}.collect(Collectors.toCollection(LinkedHashSet::new))\
.else
${base_expr}.collect(Collectors.toSet())\
.end if
