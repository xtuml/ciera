.if (multiplicity_many)
${selector_name}().forEach(o -> o.${accessor_name}(${attribute_name}));
.else
${selector_name}().${accessor_name}(${attribute_name});
.end if
