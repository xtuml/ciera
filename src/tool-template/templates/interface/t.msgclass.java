package ${pkg};

  public class ${self.name}Msg {
.if ( not self.to_provider )
    private String messageName;
.end if;
${data_block}
    public ${self.name}Msg() {
    }
.if ( parmlist != "" )
    public ${self.name}Msg( ${parmlist} ) {
${parm_block}\
    }
.end if
${code_block}\
.if ( not self.to_provider )
    public void setMessageName( String messageName ) {
	  this.messageName = messageName;
    }
    public String getMessageName() {
	  return messageName;
    }
.end if
}