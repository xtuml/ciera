T_b("        ");
T_b("if ( !(");
T_b(link_parameter_name);
T_b(".");
T_b(selector_name);
T_b("() instanceof IEmptyInstance) ) throw new ModelIntegrityException( \"Cannot relate more than one instance across association.\" );");
T_b("\n");
