if ( is_else_if ) {
T_b(statements);
T_b("");
} else {
T_b("{");
T_b("\n");
T_b(self->prefix);
T_b(statements);
T_b("");
if ( include_suffix ) {
T_b(self->suffix);
T_b("");
}
T_b(indent);
T_b("}");
T_b("\n");
}
