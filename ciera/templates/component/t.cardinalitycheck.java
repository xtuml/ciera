        if ( !(${link_parameter_name}.${selector_name}() instanceof IEmptyInstance) ) throw new ModelIntegrityException( "Cannot relate more than one instance across association." );
