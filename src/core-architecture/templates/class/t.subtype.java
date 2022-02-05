public static interface R${self.num}Subtype extends ObjectInstance {

    // referential attribute setters
    ${ref_attr_setters}\

    // relationship modifiers
    public void setR${self.num}_is_a_${self.part_name}(${self.part_name} inst);
    public void clearR${self.num}_is_a_${self.part_name}();

    // supertype selector
    public ${self.part_name} ${supertype_selector}();

}
