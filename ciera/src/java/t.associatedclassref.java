if ( declaration ) {
T_b("    ");
T_b(target_type_name);
T_b(" ");
T_b(ref_name);
T_b(";");
T_b("\n");
} else if ( initialize ) {
if ( is_many ) {
T_b("        ");
T_b(ref_name);
T_b(" = new ");
T_b(target_type_name);
T_b("();");
T_b("\n");
} else {
T_b("        ");
T_b(ref_name);
T_b(" = ");
T_b(target_type_name);
T_b(".empty");
T_b(target_type_name);
T_b(";");
T_b("\n");
}
}
