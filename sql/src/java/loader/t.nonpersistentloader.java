if ( 0==strcmp("boolean",self->type_name) ) {
T_b(", false");
} else if ( 0==strcmp("int",self->type_name) ) {
T_b(", 0");
} else if ( 0==strcmp("double",self->type_name) ) {
T_b(", 0d");
} else {
if ( self->load_value ) {
T_b(", new ");
T_b(self->type_name);
T_b("(values.get(");
T_b(T_s(value_index));
T_b("))");
} else {
T_b(", new ");
T_b(self->type_name);
T_b("()");
}
}
