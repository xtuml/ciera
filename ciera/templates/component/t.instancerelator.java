        ${test} ( $t{rel_num} == relNum && ${link_parameter_type_check} ) {
${cardinality_checks}            if ( !addRelationship( new ${relationship_name}( ${link_parameter_ids} ) ) ) throw new ModelIntegrityException( "Instances already related." );
        }
