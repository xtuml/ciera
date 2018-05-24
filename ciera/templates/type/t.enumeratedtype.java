package ${self.package};

public enum ${self.name} {

    UNINITIALIZED_ENUM( -1 )${enumerators}

    private final int value;

    ${self.name}( int value ) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
