if ( self->primitive ) {
T_b("selected.");
T_b(self->part_accessor);
T_b(" == form.");
T_b(self->form_accessor);
T_b("");
} else {
if ( self->is_string ) {
T_b("StringUtil.equality( selected.");
T_b(self->part_accessor);
T_b(", form.");
T_b(self->form_accessor);
T_b(" )");
} else {
T_b("selected.");
T_b(self->part_accessor);
T_b(".equality( form.");
T_b(self->form_accessor);
T_b(" )");
}
}
