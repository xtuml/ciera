package ${package};

${imports}

public class ${name} extends ${extends} {

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

    // link class
    private UniqueId ${link_class_name};

}
