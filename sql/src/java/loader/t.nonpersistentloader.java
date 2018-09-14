if ( 0==strcmp("boolean",self->type_name) ) {
T_b(", false");
} else if ( 0==strcmp("int",self->type_name) ) {
T_b(", 0");
} else if ( 0==strcmp("double",self->type_name) ) {
T_b(", 0d");
} else {
T_b(", new ");
T_b(self->type_name);
T_b("()");
}
