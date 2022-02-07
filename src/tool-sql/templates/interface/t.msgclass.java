package ${pkg};

  public class ${self.name}Msg {
    private String messageName;
${data_block}
    public ${self.name}Msg() {
    }
    public ${self.name}Msg( String messageName${parmlist} ) {
      this.messageName = messageName;
${parm_block}\
    }
${code_block}\
    public void setMessageName( String messageName ) {
	  this.messageName = messageName;
    }
    public String getMessageName() {
	  return messageName;
    }
}