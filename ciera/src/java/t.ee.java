T_b("package ");
T_b(package_name);
T_b(";");
T_b("\n");
T_b(import_block);
T_b("\n");
if ( is_builtin ) {
T_b("public class ");
T_b(ee_kl);
T_b(" extends Default");
T_b(ee_kl);
T_b(" {");
T_b("\n");
} else {
T_b("public class ");
T_b(ee_kl);
T_b(" {");
T_b("\n");
}
T_b(bridges_block);
T_b("\n");
T_b("}");
T_b("\n");
