package ${self.package};

${imports}

public class ${self.name} extends \
.if (empty sm)
AbstractObjectInstance\
.else
AbstractDynamicObjectInstance\
.end if
.if (empty sm)
 implements ObjectInstance\
.else
 implements DynamicObjectInstance\
.end if
.if (supertypes != "")
${supertypes}\
.end if
 {

    public static final ${self.name} EMPTY = new Empty${self.name}();

    // event ID declarations
    ${event_declarations}\

    // attributes
    ${attribute_decls}

    // relationships
    ${relationship_decls}

    // constructors
    private ${self.name}() {}

    public ${self.name}(${self.comp_name} domain) {
        super(UniqueId.random(), domain);
.if (not_empty sm)
        setCurrentState(${self.name}StateMachine.States.${init_state.name});
        setStateMachine(new ${self.name}StateMachine(domain, this));
.end if
    }

    public ${self.name}(UniqueId instanceId, ${self.comp_name} domain\
.if (not_empty sm)
, Enum<?> initialState\
.end if
${attribute_initializer_params}) {
        super(instanceId, domain);
        ${attribute_initializers}\
.if (not_empty sm)
        setCurrentState(initialState);
        setStateMachine(new ${self.name}StateMachine(domain, this));
.end if
    }

    // attribute accessors
    ${attribute_accessors}

    // operations
    ${operations}

    // events
    ${event_definitions}

    // selections
    ${selectors}

.if (subtype_selections != "")
    @Override
    public Set<ObjectInstance> getSubtypeInstances() {
        return Set.of(${subtype_selections});
    }

.end if
    @Override
    public ${self.comp_name} getDomain() {
        return (${self.comp_name}) super.getDomain();
    }

    @Override
    public ${self.name} self() {
        return this;
    }

.if (self.comparator != "")
    @Override
    @SuppressWarnings("unchecked")
    public int compareTo(${self.name} o) {
        try {
            Object val = ${self.comparator}();
            int cmpval = ((Comparable<Object>)val).compareTo(o.${self.comparator}());
            return cmpval != 0 ? cmpval : super.compareTo(o);
        } catch (Exception e) {
            return super.compareTo(o);
        }
    }

.end if

    // subtype interfaces
    ${subtype_interfaces}

    // empty class
    private static final class Empty${self.name} extends ${self.name} {

        // attributes
        ${empty_attributes}

        // operations
        ${empty_operations}

        // selections
        ${empty_selectors}

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public UniqueId getInstanceId() {
            throw new EmptyInstanceException("Cannot get instance ID of empty instance", null, this);
        }

        @Override
        public ${self.comp_name} getDomain() {
            throw new EmptyInstanceException("Cannot get domain of empty instance", null, this);
        }

        @Override
        public ExecutionContext getContext() {
            throw new EmptyInstanceException("Cannot get execution context of empty instance", null, this);
        }

        @Override
        public String toString() {
            return "EMPTY_$u{self.name}";
        }

    }

}
