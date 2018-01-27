package ${package};

${imports}

public class ${name} extends ${extends} {

    private static final int relNum = ${num};

    // one class
    private UniqueId \
.if ( "" != one_phrase )
$_l{one_phrase}_\
.end if
${one_class_name};

    // other class
    private UniqueId \
.if ( "" != other_phrase )
$_l{other_phrase}_\
.end if
${other_class_name};

    public ${name}( UniqueId \
.if ( "" != one_phrase )
$_l{one_phrase}_\
.end if
${one_class_name}, UniqueId \
.if ( "" != other_phrase )
$_l{other_phrase}_\
.end if
${other_class_name} ) throws XtumlException {
        this.\
.if ( "" != one_phrase )
$_l{one_phrase}_\
.end if
${one_class_name} = \
.if ( "" != one_phrase )
$_l{one_phrase}_\
.end if
${one_class_name};
        this.\
.if ( "" != other_phrase )
$_l{other_phrase}_\
.end if
${other_class_name} = \
.if ( "" != other_phrase )
$_l{other_phrase}_\
.end if
${other_class_name};
    }

    @Override
    public UniqueId getOne() {
        return \
.if ( "" != one_phrase )
$_l{one_phrase}_\
.end if
${one_class_name};
    }

    @Override
    public UniqueId getOther() {
        return \
.if ( "" != other_phrase )
$_l{other_phrase}_\
.end if
${other_class_name};
    }

    @Override
    public int getNumber() {
        return relNum;
    }

    @Override
    public void delete() {
    }

}
