package ${self.package};

.if (imports != "")
${imports}\


.end if
public interface ${self.name} extends Domain {
.if (ports != "")

    ${ports}
.end if
}
