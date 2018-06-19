if ( 0!=strcmp("",oal) ) {
T_b(indent);
T_b("// ");
T_b(oal);
T_b("\n");
}
T_b(indent);
T_b("for ( Iterator<");
T_b(iterator_type);
T_b("> _");
T_b(self->iterator_name);
T_b("_iter = ");
T_b(iterable_expr);
T_b(".iterator(); _");
T_b(self->iterator_name);
T_b("_iter.hasNext(); ) ");
T_b(control_block);
T_b("");
