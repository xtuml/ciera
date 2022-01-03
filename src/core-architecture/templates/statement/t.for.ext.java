for (final ${iterator_type} _${self.iterator_name} : ${iterable_expr}\
.if (self.reversed)
..stream().collect(ArrayDeque<${iterator_type}>::new, ArrayDeque::addFirst, ArrayDeque::addAll)\
.end if
) {
    ${self.iterator_name} = _${self.iterator_name};
    ${control_block}
}
