R${rel_num}Subtype R${rel_num}_Subtype() {
    if (isActive()) {
        if (R${rel_num}_subtype != null) {
            return R${rel_num}_subtype;
        } else {
            throw new InstancePopulationException("Cannot get generic subtype across R${rel_num}", getDomain(), this);
        }
    } else {
        throw new DeletedInstanceException("Cannot navigate relationship '${self.name}' of deleted instance", getDomain(), this);
    }
}

