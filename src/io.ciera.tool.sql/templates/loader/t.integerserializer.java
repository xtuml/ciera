.if ( "" == cast )
        out.printf( "%d", $l{self.class_name}_inst.${self.value} );
.else
        out.printf( "%d", (${cast}$l{self.class_name}_inst.${self.value})\
  .if ( "" != self.value2 )
..${self.value2}\
  .end if
 );
.end if
