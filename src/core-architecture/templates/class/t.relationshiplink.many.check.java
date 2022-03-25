if (!${self.name}_set.isEmpty()) {
    throw new InvalidRelationshipException("Cannot delete instance with existing active relationships: ${self.name}", getDomain(), this);
}
