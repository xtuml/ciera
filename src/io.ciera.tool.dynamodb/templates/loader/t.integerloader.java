.if ( self.is_long )
LongUtil.deserialize(\
.else
IntegerUtil.deserialize(\
.end if
inst.get("${self.attr_name}").getN())\
