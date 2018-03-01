.if ( unconditional )
            ${self.selector_name}().set${referring_attribute_capital_name}( ${self.attribute_name} ); 
.else
            ${self.referring_attribute_class_name} $l{self.referring_attribute_class_name}\
  .if ( "" != self->phrase )
_$l_{self.phrase}_$l{self.class_name}\
  .end if            
 = ${self.selector_name}();
            if ( !($l{self.referring_attribute_class_name}\
  .if ( "" != self->phrase )
_$l_{self.phrase}_$l{self.class_name}\
  .end if            
 instanceof IEmptyInstance) ) $l{self.referring_attribute_class_name}\
  .if ( "" != self->phrase )
_$l_{self.phrase}_$l{self.class_name}\
  .end if            
..set${referring_attribute_capital_name}( ${self.attribute_name} ); 
.end if
