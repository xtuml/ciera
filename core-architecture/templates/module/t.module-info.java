${imports}\

module ${self.name} {

.if (exports != "")
    ${exports}\

.end if
.if (provides_domains != "")
    ${provides_domains}\

.end if
.if (uses_domains != "")
    ${uses_domains}\

.end if
.if (dependencies != "")
    ${dependencies}\
.end if
    requires transitive io.ciera.runtime;

}
