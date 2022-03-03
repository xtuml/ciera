package ${self.package};

${imports}

public class ${self.name} {

    private final Domain domain;

    public ${self.name}(Domain domain) {
        this.domain = domain;
.if (self.native_library != "")
        System.loadLibrary("${self.native_library}");
.end if
    }

    ${utility_functions}

}
