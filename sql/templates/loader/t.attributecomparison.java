.if ( self->primitive )
selected.${self.part_accessor} == form.${self.form_accessor}\
.else
selected.${self.part_accessor}.equality( form.${self.form_accessor} )\
.end if
