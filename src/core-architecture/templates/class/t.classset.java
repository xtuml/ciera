public static final class Set extends TreeSet<${self.class_name}> {

    private static final long serialVersionUID = 1l;

    public Set() {}

    public Set(Collection<? extends ${self.class_name}> c) {
        super(c);
    }

    public Set(Comparator<? super ${self.class_name}> comparator) {
        super(comparator);
    }

    public Set(SortedSet<${self.class_name}> s) {
        super(s);
    }

    // attribute setters
    ${attribute_setters}

    // selections
    ${selectors}

    public ${self.class_name} any() {
        return stream().findAny().orElse(${self.class_name}.EMPTY);
    }

    public ${self.class_name} any(Predicate<${self.class_name}> where) {
        return stream().filter(where).findAny().orElse(${self.class_name}.EMPTY);
    }

    public Set where(Predicate<${self.class_name}> where) {
        return new Set(stream().filter(where).collect(Collectors.toSet()));
    }
}
