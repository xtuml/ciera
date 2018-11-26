.if ( self.primary_key )
        item = item.withPrimaryKey("componentId", population.getId(), "${self.attr_name}", $l{self.class_name}_inst.${self.value}.serialize());
.else
        item = item.withString("${self.attr_name}", $l{self.class_name}_inst.${self.value}.serialize());
.end if
