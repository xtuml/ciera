.if ( "" == cast )
        out.printf( "%s", $l{self.class_name}_inst.${self.value}.serialize() );
.else
        out.printf( "%s", (${cast}$l{self.class_name}_inst.${self.value}).serialize() );
.end if
