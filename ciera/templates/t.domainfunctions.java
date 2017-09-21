package ${package_name};

${import_block}

.if ( "" == extends_class )
public class ${class_name} {
.else
public class ${class_name} extends ${extends_class} {
.end if

${function_block}

}
