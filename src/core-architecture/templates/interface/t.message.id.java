.if (names_list != "")
@Message.Names(names={${names_list}})
.end if
.if (types_list != "")
@Message.Types(types={${types_list}})
.end if
public static final int $u_{self.name} = ${self.id};

