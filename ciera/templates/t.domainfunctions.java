package ${package_name};

.if ( throws_exception )
import ciera.exceptions.XtumlException;
.end if
${import_block}

public class ${simple_package_name} {
  ${function_block}
}
