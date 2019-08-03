                case "${self.evt_class_name}":
                    new ${class_name}(population.getRunContext(), ${population_id}\
.if ( "" != attribute_loaders )
, ${attribute_loaders}\
.end if
).to(${event_handle}, population.${self.class_name}_instances().anyWhere(selected -> selected.getInstanceId().equality(${event_target})));
                    break;
