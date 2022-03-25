.if (ref_primitive)
if (${selector_name}().notEmpty() && ${selector_name}().${accessor_name}() != ${self.attribute_name}) {
.else
if (${selector_name}().notEmpty() && !${selector_name}().${accessor_name}().equals(${self.attribute_name})) {
.end if
    throw new ReferentialAttributeException("Inconsistent combined referential attribute values: ${self.attribute_name}", getDomain(), this);
}
