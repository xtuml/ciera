T_b("package ");
T_b(package_name);
T_b(";");
T_b("\n");
T_b(import_block);
T_b("\n");
if ( 0==strcmp("",extends_class) ) {
T_b("public class ");
T_b(class_name);
T_b(" {");
T_b("\n");
} else {
T_b("public class ");
T_b(class_name);
T_b(" extends ");
T_b(extends_class);
T_b(" {");
T_b("\n");
}
T_b(function_block);
T_b("\n");
T_b("}");
T_b("\n");
