.if ( "" == cast )
        item = item.withString("${self.attr_name}", $l{self.class_name}_inst.${self.value});
.else
        item = item.withString("${self.attr_name}", ${cast}$l{self.class_name}_inst.${self.value});
.end if
