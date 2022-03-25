.if (multiplicity_many)
${selector_name}().forEach(o -> o.${accessor_name}(${attribute_name}));
.else
if (${selector_name}().notEmpty()) {
    ${selector_name}().${accessor_name}(${attribute_name});
}
.end if
