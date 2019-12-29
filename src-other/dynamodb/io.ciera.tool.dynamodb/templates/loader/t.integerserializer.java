.if ( "" == cast )
        item = item.withNumber("${self.attr_name}", $l{self.class_name}_inst.${self.value});
.else
        item = item.withNumber("${self.attr_name}", (${cast}$l{self.class_name}_inst.${self.value})\
  .if ( "" != self.value2 )
..${self.value2}\
  .end if
);
.end if
