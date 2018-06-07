package ${self.package};

${imports}

import io.ciera.instanceloading.IInstanceLoader;
import io.ciera.summit.exceptions.XtumlException;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

public class ${self.name} implements IInstanceLoader {

    private ${self.comp_name} population;

    public ${self.name}( ${self.comp_name} population ) {
        this.population = population;
    }

    public void insert( String tableName, List<Object> values ) {}
    public void finish() {}

    public void serialize( OutputStream stream ) throws XtumlException {
        PrintStream out = new PrintStream( stream );
${instance_serializers}        out.flush();
    }

}
