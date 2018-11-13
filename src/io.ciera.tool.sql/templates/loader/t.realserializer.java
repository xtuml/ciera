.if ( "" == cast )
        out.printf( "%f", $l{self.class_name}_inst.${self.value} );
.else
        out.printf( "%f", (${cast}$l{self.class_name}_inst.${self.value}) );
.end if
