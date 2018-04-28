if ( is_else_if ) {
T_b("/* Block number: ");
T_b(self->block_number);
T_b(" */ ");
T_b(statements);
T_b("");
} else {
T_b("{ // Block number: ");
T_b(self->block_number);
T_b("\n");
T_b(statements);
T_b(indent);
T_b("}");
T_b("\n");
}
