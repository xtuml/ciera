T_b("package ");
T_b(package);
T_b(";");
T_b("\n");
T_b(imports);
T_b("\n");
T_b("public class ");
T_b(name);
T_b(" extends ");
T_b(extends);
T_b(" {");
T_b("\n");
T_b("    ");
T_b("private static final int relNum = ");
T_b(num);
T_b(";");
T_b("\n");
T_b("    ");
T_b("// one class");
T_b("\n");
T_b("    ");
T_b("private UniqueId ");
if ( 0!=strcmp("",one_phrase) ) {
T_b(T_l(T_underscore(one_phrase)));
T_b("_");
}
T_b(one_class_name);
T_b(";");
T_b("\n");
T_b("    ");
T_b("// other class");
T_b("\n");
T_b("    ");
T_b("private UniqueId ");
if ( 0!=strcmp("",other_phrase) ) {
T_b(T_l(T_underscore(other_phrase)));
T_b("_");
}
T_b(other_class_name);
T_b(";");
T_b("\n");
T_b("    ");
T_b("public ");
T_b(name);
T_b("( UniqueId ");
if ( 0!=strcmp("",one_phrase) ) {
T_b(T_l(T_underscore(one_phrase)));
T_b("_");
}
T_b(one_class_name);
T_b(", UniqueId ");
if ( 0!=strcmp("",other_phrase) ) {
T_b(T_l(T_underscore(other_phrase)));
T_b("_");
}
T_b(other_class_name);
T_b(" ) {");
T_b("\n");
T_b("        ");
T_b("this.");
if ( 0!=strcmp("",one_phrase) ) {
T_b(T_l(T_underscore(one_phrase)));
T_b("_");
}
T_b(one_class_name);
T_b(" = ");
if ( 0!=strcmp("",one_phrase) ) {
T_b(T_l(T_underscore(one_phrase)));
T_b("_");
}
T_b(one_class_name);
T_b(";");
T_b("\n");
T_b("        ");
T_b("this.");
if ( 0!=strcmp("",other_phrase) ) {
T_b(T_l(T_underscore(other_phrase)));
T_b("_");
}
T_b(other_class_name);
T_b(" = ");
if ( 0!=strcmp("",other_phrase) ) {
T_b(T_l(T_underscore(other_phrase)));
T_b("_");
}
T_b(other_class_name);
T_b(";");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
T_b("    ");
T_b("@Override");
T_b("\n");
T_b("    ");
T_b("public UniqueId getOne() {");
T_b("\n");
T_b("        ");
T_b("return ");
if ( 0!=strcmp("",one_phrase) ) {
T_b(T_l(T_underscore(one_phrase)));
T_b("_");
}
T_b(one_class_name);
T_b(";");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
T_b("    ");
T_b("@Override");
T_b("\n");
T_b("    ");
T_b("public UniqueId getOther() {");
T_b("\n");
T_b("        ");
T_b("return ");
if ( 0!=strcmp("",other_phrase) ) {
T_b(T_l(T_underscore(other_phrase)));
T_b("_");
}
T_b(other_class_name);
T_b(";");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
T_b("    ");
T_b("@Override");
T_b("\n");
T_b("    ");
T_b("public int getNumber() {");
T_b("\n");
T_b("        ");
T_b("return relNum;");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
T_b("}");
T_b("\n");
