${imports}\

module ${self.name} {

.if (exports != "")
    ${exports}\

.end if
.if (opens != "")
    ${opens}\

.end if
.if (domains != "")
    ${domains}\

.end if
.if (applications != "")
    ${applications}\

.end if
.if (dependencies != "")
    ${dependencies}\
.end if
    requires transitive io.ciera.runtime;

}
