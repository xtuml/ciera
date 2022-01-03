for (final ${iterator_type} ${iterator_name} : ${iterable_expr}\
.if (self.reversed)
..stream().collect(ArrayDeque<${iterator_type}>::new, ArrayDeque::addFirst, ArrayDeque::addAll)\
.end if
) ${control_block}
