package ${self.package};

${imports}

public class ${self.name} {

    public ${self.name}(Domain domain) {
.if (self.native_library != "")
        System.loadLibrary("${self.native_library}");
.end if
    }
.if (utility_types != "")

    ${utility_types}\
.end if
.if (utility_functions != "")

    ${utility_functions}\
.end if

}
