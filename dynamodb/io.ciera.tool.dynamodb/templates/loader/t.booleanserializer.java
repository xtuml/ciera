.if ( "" == cast )
        item = item.withBoolean("${self.attr_name}", $l{self.class_name}_inst.${self.value});
.else
        item = item.withBoolean("${self.attr_name}", (${cast}$l{self.class_name}_inst.${self.value}));
.end if
