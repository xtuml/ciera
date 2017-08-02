package ${package_name};

.if ( is_builtin )
import ciera.util.ees.Default${ee_kl};
.end if

public class ${ee_kl} extends Default${ee_kl} {
    ${bridges_block}
}
