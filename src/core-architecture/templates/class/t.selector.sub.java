public R${rel_num}Subtype R${rel_num}_Subtype() {
    if (R${rel_num}_subtype == null) {
        throw new InstancePopulationException("Cannot get generic subtype across R${rel_num}", getDomain(), this);
    } else {
        return R${rel_num}_subtype;
    }
}

