.if ( "" == cast )
        out.printf( "%d", $l{self.class_name}_inst.${self.value} ? 1 : 0 );
.else
        out.printf( "%d", (${cast}$l{self.class_name}_inst.${self.value}) ? 1 : 0 );
.end if
