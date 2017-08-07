T_b("package ");
T_b(package_name);
T_b(";");
T_b("\n");
T_b(import_block);
T_b("\n");
T_b("public class ");
T_b(event_name);
T_b(" extends Event {");
T_b("\n");
T_b("   ");
T_b(" ");
T_b("\n");
T_b("    ");
T_b("private static final int eventId = ");
T_b(event_id);
T_b(";");
T_b("\n");
T_b("    ");
T_b("private static final int eventNumber = ");
T_b(event_num);
T_b(";");
T_b("\n");
T_b("    ");
T_b("private static final int classNumber = ");
T_b(class_num);
T_b(";");
T_b("\n");
T_b("    ");
T_b("private static final String eventName = \"");
T_b(raw_event_name);
T_b("\";");
T_b("\n");
if ( has_data ) {
T_b("    ");
T_b(data_members_block);
T_b("\n");
T_b("    ");
T_b("public ");
T_b(event_name);
T_b("( ");
T_b(data_params);
T_b(" ) {");
T_b("\n");
T_b("        ");
T_b(data_init_block);
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
T_b("    ");
T_b("public ");
T_b(event_name);
T_b("( EventTarget t, boolean ts, ");
T_b(data_params);
T_b(" ) {");
T_b("\n");
T_b("        ");
T_b("super( t, ts );");
T_b("\n");
T_b("        ");
T_b(data_init_block);
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
} else {
T_b("    ");
T_b("public ");
T_b(event_name);
T_b("() {");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
T_b("    ");
T_b("public ");
T_b(event_name);
T_b("( EventTarget t, boolean ts ) {");
T_b("\n");
T_b("        ");
T_b("super( t, ts );");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
}
T_b("   ");
T_b(" ");
T_b("\n");
T_b("    ");
T_b("@Override");
T_b("\n");
T_b("    ");
T_b("public int getEventId() {");
T_b("\n");
T_b("        ");
T_b("return eventId;");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
T_b("    ");
T_b("@Override");
T_b("\n");
T_b("    ");
T_b("public int getEventNumber() {");
T_b("\n");
T_b("        ");
T_b("return eventNumber;");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
T_b("    ");
T_b("@Override");
T_b("\n");
T_b("    ");
T_b("public int getClassNumber() {");
T_b("\n");
T_b("        ");
T_b("return classNumber;");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
T_b("    ");
T_b("@Override");
T_b("\n");
T_b("    ");
T_b("public String getEventName() {");
T_b("\n");
T_b("        ");
T_b("return eventName;");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
if ( has_data ) {
T_b("    ");
T_b("@Override");
T_b("\n");
T_b("    ");
T_b("public Object getData( String id ) throws XtumlException {");
T_b("\n");
T_b("        ");
T_b(data_access_block);
T_b("\n");
T_b("        ");
T_b("else throw new SameDataException( \"Event does not contain required data.\" );");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
}
T_b("}");
T_b("\n");
