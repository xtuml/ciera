.if (not_empty prev_key)
..thenComparing(\
.end if
Comparator.comparing(${self.class_name}::${accessor})\
.if (self.descending)
..reversed()\
.end if
.if (not_empty prev_key)
)\
.end if
