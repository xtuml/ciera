package ${self.package};

${imports}

.if (self.public)
public \
.end if
${type_body}\
