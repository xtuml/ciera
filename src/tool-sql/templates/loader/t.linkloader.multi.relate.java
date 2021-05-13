.if ( declare )
            ${part_class} ${partvar} = population.${part_class}_instances().getByInstanceId( UniqueId.deserialize(instids.get(1)) );
.end if
            ${form_class} ${formvar} = population.${form_class}_instances().getByInstanceId( UniqueId.deserialize(instids.get(0)) );
            if ( ! ${formvar}.isEmpty() ) {
            	System.out.printf("Relating %s  %s  %s \n", ${rel_name}, ${formvar}, ${partvar} )
                population.relate_${rel_name}( ${formvar}, ${partvar} ); 
                break;
            }