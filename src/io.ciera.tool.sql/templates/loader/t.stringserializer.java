.if ( "" == cast )
        out.printf( "'%s'", $l{self.class_name}_inst.${self.value}.toString().replaceAll( "'", "''" ) );
.else
        out.printf( "'%s'", (${cast}$l{self.class_name}_inst.${self.value}).toString().replaceAll( "'", "''" ) );
.end if
