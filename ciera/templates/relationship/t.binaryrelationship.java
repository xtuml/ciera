    private class ${self.name} extends ${extends} {
    
        private static final int relNum = $t{num};
    
        // one class
        private UniqueId ${self.one_class_name}\
.if ( "" != self->other_phrase )
_$_l{self.other_phrase}_${self.other_class_name}\
.end if
;
    
        // other class
        private UniqueId ${self.other_class_name}\
.if ( "" != self->one_phrase )
_$_l{self.one_phrase}_${self.one_class_name}\
.end if
;
    
        public ${self.name}( UniqueId ${self.one_class_name}\
.if ( "" != self->other_phrase )
_$_l{self.other_phrase}_${self.other_class_name}\
.end if
, UniqueId ${self.other_class_name}\
.if ( "" != self->one_phrase )
_$_l{self.one_phrase}_${self.one_class_name}\
.end if
 ) throws XtumlException {
            this.${self.one_class_name}\
.if ( "" != self->other_phrase )
_$_l{self.other_phrase}_${self.other_class_name}\
.end if
 = ${self.one_class_name}\
.if ( "" != self->other_phrase )
_$_l{self.other_phrase}_${self.other_class_name}\
.end if
;
            this.${self.other_class_name}\
.if ( "" != self->one_phrase )
_$_l{self.one_phrase}_${self.one_class_name}\
.end if
 = ${self.other_class_name}\
.if ( "" != self->one_phrase )
_$_l{self.one_phrase}_${self.one_class_name}\
.end if
;
        }
    
        @Override
        public UniqueId getOne() {
            return ${self.one_class_name}\
.if ( "" != self->other_phrase )
_$_l{self.other_phrase}_${self.other_class_name}\
.end if
;
        }
    
        @Override
        public UniqueId getOther() {
            return ${self.other_class_name}\
.if ( "" != self->one_phrase )
_$_l{self.one_phrase}_${self.one_class_name}\
.end if
;
        }
    
        @Override
        public int getNumber() {
            return relNum;
        }
    
        @Override
        public void delete() {
        }
    
    }
