package ${self.package};

${imports}

public class ${self.name} extends ${extends_type}\
.if ( generic )
<${self.name}>\
.end if
 implements IXtumlType<${self.name}> {

    @Override
    public ${self.name} value() {
        return this;
    }

}
