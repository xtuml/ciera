throw new ${exception_name}(${data_expression}\
.if (cause != "")
  .if (data_expression != "")
, \
  .end if
${cause}\
.end if
);
