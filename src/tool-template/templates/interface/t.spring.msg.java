package ${pkg};

//Spring generic message
public class SpringMsg {
    private String messageName;
    private String payload;

    public SpringMsg() {
    }
    public SpringMsg( String messageName, String payload ) {
        this.payload = payload;
        this.messageName = messageName;
    }
    public void setMessageName( String messageName ) {
        this.messageName = messageName;
    }
    public String getMessageName() {
        return messageName;
    }
    public void setPayload( String payload ) {
        this.payload = payload;
    }
    public String getPayload() {
        return payload;
    }
}