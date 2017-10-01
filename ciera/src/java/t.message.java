if ( 0==strcmp("declaration",location) ) {
T_b("    ");
T_b("public void ");
T_b(message_name);
T_b("(");
T_b(parameter_list);
T_b(");");
T_b("\n");
} else if ( 0==strcmp("definition",location) ) {
T_b("	  ");
T_b("@Override");
T_b("\n");
T_b("    ");
T_b("public void ");
T_b(message_name);
T_b("(");
T_b(parameter_list);
T_b(") {");
T_b("\n");
if ( inbound ) {
} else {
T_b("		    ");
T_b("send( new ");
T_b(message_class);
T_b("( \"");
T_b(message_name);
T_b("\", getPeerID(), getID()");
T_b(invocation_parameter_list);
T_b(" ) );");
T_b("\n");
}
T_b("	  ");
T_b("}");
T_b("\n");
} else if ( 0==strcmp("switch",location) ) {
T_b("                ");
T_b("case \"");
T_b(message_name);
T_b("\":");
T_b("\n");
T_b("					          ");
T_b(message_name);
T_b("(");
T_b(message_parameter_list);
T_b(");");
T_b("\n");
T_b("					          ");
T_b("break;");
T_b("\n");
}
