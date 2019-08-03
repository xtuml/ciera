.if ( "" == cast )
        if (!"".equals($l{self.class_name}_inst.${self.value})) {
            item = item.withString("${self.attr_name}", $l{self.class_name}_inst.${self.value});
        }
.else
        if (!"".equals(${cast}$l{self.class_name}_inst.${self.value})) {
            item = item.withString("${self.attr_name}", ${cast}$l{self.class_name}_inst.${self.value});
        }
.end if
