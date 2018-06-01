T_b("    ");
T_b("public void ");
T_b(self->msg_name);
T_b("(");
T_b(parameter_list);
T_b(") throws XtumlException ");
if ( inbound ) {
T_b(body);
T_b("\n");
} else {
T_b("{");
T_b("\n");
T_b("        ");
T_b("if ( satisfied() ) send( new Message( \"");
T_b(self->msg_name);
T_b("\"");
T_b(invocation_parameter_list);
T_b(" ) );");
T_b("\n");
T_b("        ");
T_b("else ");
T_b(body);
T_b("");
T_b("    ");
T_b("}");
T_b("\n");
}
