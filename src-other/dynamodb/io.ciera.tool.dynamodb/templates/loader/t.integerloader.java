.if ( self.is_long )
LongUtil.deserialize(\
.else
IntegerUtil.deserialize(\
.end if
values.getInt("${self.attr_name}"))\
