if ( multiplicity_many ) {
T_b("    ");
T_b("default public void add");
T_b(self->name);
T_b("( ");
T_b(inst_type_name);
T_b(" inst ) {}");
T_b("\n");
T_b("    ");
T_b("default public void remove");
T_b(self->name);
T_b("( ");
T_b(inst_type_name);
T_b(" inst ) {}");
T_b("\n");
} else {
T_b("    ");
T_b("default public void set");
T_b(self->name);
T_b("( ");
T_b(inst_type_name);
T_b(" inst ) {}");
T_b("\n");
}
T_b("    ");
T_b("public ");
T_b(type_name);
T_b(" ");
T_b(self->name);
T_b("() throws XtumlException;");
T_b("\n");
