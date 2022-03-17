if (${self.name}_inst.notEmpty()) {
    throw new InvalidRelationshipException("Cannot delete instance with existing active relationships: ${self.name}", getDomain(), this);
}
