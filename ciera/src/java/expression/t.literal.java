if ( is_string ) {
T_b("new XtumlString( \"");
T_b(self->value);
T_b("\" )");
} else {
T_b(self->value);
T_b("");
}
