if (R${rel.num}_subtype != null) {
    throw new InvalidRelationshipException("Cannot delete instance with existing active relationships: ${self.name}", getDomain(), this);
}
