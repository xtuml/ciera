if ( ! self->is_class_based ) {
T_b("    ");
T_b("@Override");
T_b("\n");
}
T_b("    ");
T_b("public ");
T_b(modifiers);
T_b(type_name);
T_b(" ");
T_b(self->name);
T_b("(");
T_b(parameter_list);
T_b(") throws XtumlException ");
T_b(body);
T_b("\n");
