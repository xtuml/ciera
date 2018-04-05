T_b("    ");
T_b("@Override");
T_b("\n");
T_b("    ");
T_b("public void relate( int relNum, IModelInstance one, IModelInstance other, IModelInstance link ) throws XtumlException {");
T_b("\n");
T_b("        ");
T_b("if ( null == one || null == other || null == link ) throw new BadArgumentException( \"Null instances passed.\" );");
T_b("\n");
T_b("        ");
T_b("if ( one instanceof IEmptyInstance || other instanceof IEmptyInstance || link instanceof IEmptyInstance ) throw new EmptyInstanceException( \"Cannot relate empty instances.\" );");
T_b("\n");
T_b(associative_relates);
T_b("        ");
if ( 0!=strcmp("",associative_relates) ) {
T_b("else ");
}
T_b("throw new InstancePopulationException( \"Relationship '\" + relNum + \"' between '\" + one.getClass().getName() + \"', '\" + other.getClass().getName() + \"', and '\" + link.getClass().getName() + \"' not supported.\" );");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
T_b("    ");
T_b("@Override");
T_b("\n");
T_b("    ");
T_b("public void relate( int relNum, IModelInstance one, IModelInstance other ) throws XtumlException {");
T_b("\n");
T_b("        ");
T_b("if ( null == one || null == other  ) throw new BadArgumentException( \"Null instances passed.\" );");
T_b("\n");
T_b("        ");
T_b("if ( one instanceof IEmptyInstance || other instanceof IEmptyInstance ) throw new EmptyInstanceException( \"Cannot relate empty instances.\" );");
T_b("\n");
T_b(other_relates);
T_b("        ");
if ( 0!=strcmp("",other_relates) ) {
T_b("else ");
}
T_b("throw new InstancePopulationException( \"Relationship '\" + relNum + \"' between '\" + one.getClass().getName() + \"' and '\" + other.getClass().getName() + \"' not supported.\" );");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
