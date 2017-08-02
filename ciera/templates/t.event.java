package ${package_name};

${import_block}

public class ${event_name} extends Event {
    
    private static final int eventId = ${event_id};
    private static final int eventNumber = ${event_num};
    private static final int classNumber = ${class_num};
    private static final String eventName = "${raw_event_name}";

.if ( has_data )
    ${data_members_block}

    public ${event_name}( ${data_params} ) {
        ${data_init_block}
    }

    public ${event_name}( EventTarget t, boolean ts, ${data_params} ) {
        super( t, ts );
        ${data_init_block}
    }
.else
    public ${event_name}() {
    }

    public ${event_name}( EventTarget t, boolean ts ) {
        super( t, ts );
    }
.end if
    
    @Override
    public int getEventId() {
        return eventId;
    }

    @Override
    public int getEventNumber() {
        return eventNumber;
    }

    @Override
    public int getClassNumber() {
        return classNumber;
    }

    @Override
    public String getEventName() {
        return eventName;
    }

.if ( has_data )
    @Override
    public Object getData( String id ) throws XtumlException {
        ${data_access_block}
        else throw new SameDataException( "Event does not contain required data." );
    }

.end if
}
