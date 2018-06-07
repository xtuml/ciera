package ${self.package};

${imports}

import io.ciera.instanceloading.IPopulationLoader;
import io.ciera.summit.exceptions.XtumlException;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

public class ${self.name} implements IPopulationLoader {

    private ${self.comp_name} population;

    public ${self.name}( ${self.comp_name} population ) {
        this.population = population;
    }

    public void insert( String tableName, List<Object> values ) {
        System.out.println( tableName );
        for ( Object val : values ) System.out.println( "  " + val.toString() + ": " + val.getClass().getName() );
    }
    public void finish() {}

    public void serialize( OutputStream stream ) throws XtumlException {
        PrintStream out = new PrintStream( stream );
${instance_serializers}        out.flush();
    }

}
