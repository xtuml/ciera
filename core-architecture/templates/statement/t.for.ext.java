.if (self.label != "")
${self.label}:
.end if
for (final ${iterator_type} _${iterator_name} : ${iterable_expr}) {
    ${iterator_name} = _${iterator_name};
    ${control_block}\
}
