.if ( index == 1 )
    ${form_class} ${formvar} = population.${form_class}_instances().getByInstanceId( UniqueId.deserialize(instids.get(0)) );
.end if
.if ( newvar )
    ${part_class} ${partvar} = population.${part_class}_instances().getByInstanceId( UniqueId.deserialize(instids.get(${index})) );
.else
	${partvar} = population.${part_class}_instances().getByInstanceId( UniqueId.deserialize(instids.get(${index})) );
.end if
    population.relate_${rel_name}( ${formvar}, ${partvar} );