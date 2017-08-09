package ${package_name};

${import_block}

.if ( is_builtin )
public class ${ee_kl} extends Default${ee_kl} {
.else
public class ${ee_kl} {
.end if

${bridges_block}

}
