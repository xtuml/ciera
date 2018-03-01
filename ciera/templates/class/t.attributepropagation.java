.if ( unconditional )
            ${selector_name}().set${referring_attribute_capital_name}( ${attribute_name} ); 
.else
            ${referring_attribute_class_name} $l{referring_attribute_class_name}\
  .if ( "" != phrase )
_$l_{phrase}_$l{class_name}\
  .end if            
 = ${selector_name}();
            if ( !($l{referring_attribute_class_name}\
  .if ( "" != phrase )
_$l_{phrase}_$l{class_name}\
  .end if            
 instanceof IEmptyInstance) ) $l{referring_attribute_class_name}\
  .if ( "" != phrase )
_$l_{phrase}_$l{class_name}\
  .end if            
..set${referring_attribute_capital_name}( ${attribute_name} ); 
.end if
