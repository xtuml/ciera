..sorted(Comparator.comparing(${element_type_name}::${accessor}).thenComparing(Comparator.naturalOrder()))\
.if (sort_descending)
..reversed()\
.end if
