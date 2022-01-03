for (final ${iterator_type} _${iterator_name} : ${iterable_expr}\
.if (self.reversed)
..stream().collect(ArrayDeque<${iterator_type}>::new, ArrayDeque::addFirst, ArrayDeque::addAll)\
.end if
) {
    ${iterator_name} = _${iterator_name};
    ${control_block}
}
