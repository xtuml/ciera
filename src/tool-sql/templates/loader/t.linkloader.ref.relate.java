${form_class} form = population.${form_class}_instances().getById1( UniqueId.deserialize(instids.get(0)) );
${part_class} part = population.${part_class}_instances().getById1( UniqueId.deserialize(instids.get(${index})) );
relate_${rel_name}( form, part );