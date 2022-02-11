${iterable_expr}\
.if ((self.reversed) and (iterable_is_collection))
..stream().collect(ArrayDeque<${iterator_type}>::new, ArrayDeque::addFirst, ArrayDeque::addAll)\
.elif ((self.reversed) and (not iterable_is_collection))
..collect(ArrayDeque<${iterator_type}>::new, ArrayDeque::addFirst, ArrayDeque::addAll)\
.elif ((not self.reversed) and (not iterable_is_collection))
..collect(Collectors.toList())\
.end if
