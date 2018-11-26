.if ( "" == cast )
        if (!"".equals($l{self.class_name}_inst.${self.value}.serialize())) {
            item = item.withString("${self.attr_name}", $l{self.class_name}_inst.${self.value}.serialize());
        }
.else
        if (!"".equals((${cast}$l{self.class_name}_inst.${self.value}).serialize())) {
            item = item.withString("${self.attr_name}", (${cast}$l{self.class_name}_inst.${self.value}).serialize());
        }
.end if
