.if ( index == 1 )
            ${form_class} ${formvar} = population.${form_class}_instances().getByInstanceId( UniqueId.deserialize(instids.get(0)) );
.end if
	        ${vtyp}${partvar} = population.${part_class}_instances().getByInstanceId( UniqueId.deserialize(instids.get(${index})) );
            population.relate_${rel_name}( ${formvar}, ${partvar} );
