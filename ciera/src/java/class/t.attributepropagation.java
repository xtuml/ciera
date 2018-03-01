if ( unconditional ) {
T_b("            ");
T_b(selector_name);
T_b("().set");
T_b(referring_attribute_capital_name);
T_b("( ");
T_b(attribute_name);
T_b(" ); ");
T_b("\n");
} else {
T_b("            ");
T_b(referring_attribute_class_name);
T_b(" ");
T_b(T_l(referring_attribute_class_name));
T_b("");
if ( 0!=strcmp("",phrase) ) {
T_b("_");
T_b(T_underscore(T_l(phrase)));
T_b("_");
T_b(T_l(class_name));
T_b("");
}
T_b(" ");
T_b("= ");
T_b(selector_name);
T_b("();");
T_b("\n");
T_b("            ");
T_b("if ( !(");
T_b(T_l(referring_attribute_class_name));
T_b("");
if ( 0!=strcmp("",phrase) ) {
T_b("_");
T_b(T_underscore(T_l(phrase)));
T_b("_");
T_b(T_l(class_name));
T_b("");
}
T_b(" ");
T_b("instanceof IEmptyInstance) ) ");
T_b(T_l(referring_attribute_class_name));
T_b("");
if ( 0!=strcmp("",phrase) ) {
T_b("_");
T_b(T_underscore(T_l(phrase)));
T_b("_");
T_b(T_l(class_name));
T_b("");
}
T_b(".set");
T_b(referring_attribute_capital_name);
T_b("( ");
T_b(attribute_name);
T_b(" ); ");
T_b("\n");
}
