.if (self.label != "")
${self.label}:
.end if
.if (self.external_loop_variable)
for (final ${iterator_type} _${iterator_name} : ${iterable_expr}) {
    ${iterator_name} = _${iterator_name};
.else
for (final ${iterator_type} ${iterator_name} : ${iterable_expr}) {
.end if
    ${control_block}\
}
