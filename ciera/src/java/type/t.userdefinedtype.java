T_b("package ");
T_b(self->package);
T_b(";");
T_b("\n");
T_b(imports);
T_b("\n");
T_b("public class ");
T_b(self->name);
T_b(" extends ");
T_b(extends_type);
T_b("");
if ( generic ) {
T_b("<");
T_b(self->name);
T_b(">");
}
T_b(" ");
T_b("{");
T_b("\n");
T_b("}");
T_b("\n");
