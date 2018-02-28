package ${package};

${imports}

public class ${name} extends ${extends} {

    private static final int relNum = ${num};

    // one class
    private UniqueId \
.if ( "" != other_phrase )
${one_class_name}_$_l{other_phrase}_${other_class_name};
.else
${one_class_name};
.end if

    // other class
    private UniqueId \
.if ( "" != one_phrase )
${other_class_name}_$_l{one_phrase}_${one_class_name};
.else
${other_class_name};
.end if

    // link class
    private UniqueId ${link_class_name};

    public ${name}( UniqueId \
.if ( "" != other_phrase )
${one_class_name}_$_l{other_phrase}_${other_class_name}\
.else
${one_class_name}\
.end if
, UniqueId \
.if ( "" != one_phrase )
${other_class_name}_$_l{one_phrase}_${one_class_name}\
.else
${other_class_name}\
.end if
, UniqueId ${link_class_name} ) throws XtumlException {
        this.\
.if ( "" != other_phrase )
${one_class_name}_$_l{other_phrase}_${other_class_name} = \
.else
${one_class_name} = \
.end if
.if ( "" != other_phrase )
${one_class_name}_$_l{other_phrase}_${other_class_name};
.else
${one_class_name};
.end if
        this.\
.if ( "" != one_phrase )
${other_class_name}_$_l{one_phrase}_${one_class_name} = \
.else
${other_class_name} = \
.end if
.if ( "" != one_phrase )
${other_class_name}_$_l{one_phrase}_${one_class_name};
.else
${other_class_name};
.end if
        this.${link_class_name} = ${link_class_name};
    }

    @Override
    public UniqueId getOne() {
        return \
.if ( "" != other_phrase )
${one_class_name}_$_l{other_phrase}_${other_class_name};
.else
${one_class_name};
.end if
    }

    @Override
    public UniqueId getOther() {
        return \
.if ( "" != one_phrase )
${other_class_name}_$_l{one_phrase}_${one_class_name};
.else
${other_class_name};
.end if
    }

    @Override
    public UniqueId getLink() {
        return ${link_class_name};
    }

    @Override
    public int getNumber() {
        return relNum;
    }

    @Override
    public void delete() {
    }

}
