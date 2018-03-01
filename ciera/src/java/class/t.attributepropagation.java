if ( unconditional ) {
T_b("            ");
T_b(self->selector_name);
T_b("().set");
T_b(referring_attribute_capital_name);
T_b("( ");
T_b(self->attribute_name);
T_b(" ); ");
T_b("\n");
} else {
T_b("            ");
T_b(self->referring_attribute_class_name);
T_b(" ");
T_b(T_l(self->referring_attribute_class_name));
T_b("");
if ( 0!=strcmp("",self->phrase) ) {
T_b("_");
T_b(T_underscore(T_l(self->phrase)));
T_b("_");
T_b(T_l(self->class_name));
T_b("");
}
T_b(" ");
T_b("= ");
T_b(self->selector_name);
T_b("();");
T_b("\n");
T_b("            ");
T_b("if ( !(");
T_b(T_l(self->referring_attribute_class_name));
T_b("");
if ( 0!=strcmp("",self->phrase) ) {
T_b("_");
T_b(T_underscore(T_l(self->phrase)));
T_b("_");
T_b(T_l(self->class_name));
T_b("");
}
T_b(" ");
T_b("instanceof IEmptyInstance) ) ");
T_b(T_l(self->referring_attribute_class_name));
T_b("");
if ( 0!=strcmp("",self->phrase) ) {
T_b("_");
T_b(T_underscore(T_l(self->phrase)));
T_b("_");
T_b(T_l(self->class_name));
T_b("");
}
T_b(".set");
T_b(referring_attribute_capital_name);
T_b("( ");
T_b(self->attribute_name);
T_b(" ); ");
T_b("\n");
}
