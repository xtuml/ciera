if ( enclose ) {
T_b("{");
T_b("\n");
T_b(statements);
T_b(indent);
T_b("}");
T_b("\n");
} else {
if ( new_line ) {
T_b(" ");
T_b(" ");
T_b("\n");
}
T_b(statements);
T_b("");
}
