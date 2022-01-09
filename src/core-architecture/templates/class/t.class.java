package ${self.package};

${imports}

public class ${self.name} extends \
.if (empty sm)
ObjectInstance\
.else
DynamicObjectInstance\
.end if
.if (supertypes != "")
 implements ${supertypes}\
.end if
 {

    public static final ${self.name} EMPTY = new Empty${self.name}();

    // attributes
    ${attribute_decls}

    // relationships
    ${relationship_decls}

    // constructors
    private ${self.name}() {}

    public ${self.name}(${self.comp_name} domain) {
        super(UniqueId.random(), domain);
.if (not_empty sm)
        setStateMachine(new ${self.name}StateMachine(domain, ${self.name}StateMachine.States.${init_state.name}, this));
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
        setStateMachine(new ${self.name}StateMachine(domain, initialState, this));
.end if
    }

    // attribute accessors
    ${attribute_accessors}

    // operations
    ${operations}

    // selections
    ${selectors}

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

    // set class
    public static final class Set extends TreeSet<${self.name}> {

        private static final long serialVersionUID = 1l;

        public Set() {}

        public Set(Collection<? extends ${self.name}> c) {
            super(c);
        }

        public Set(Comparator<? super ${self.name}> comparator) {
            super(comparator);
        }

        public Set(SortedSet<${self.name}> s) {
            super(s);
        }

        // attribute setters
        ${set_setters}

        // selections
        ${set_selectors}

        public ${self.name} any() {
            return stream().findAny().orElse(${self.name}.EMPTY);
        }

        public ${self.name} any(Predicate<${self.name}> where) {
            return stream().filter(where).findAny().orElse(${self.name}.EMPTY);
        }

        public Set where(Predicate<${self.name}> where) {
            return new Set(stream().filter(where).collect(Collectors.toSet()));
        }
    }

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
