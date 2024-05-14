package ${self.package};

${imports}

public class ${self.name} implements IXtumlType {

    // constructors
    public ${self.name}() {
${attribute_initializers}    }

    // attributes
${attributes}

    @Override
    public String serialize() throws XtumlException {
        final JSONObject json = new JSONObject();
        ${attribute_serializers}
        return json.toString();
    }

    public static ${self.name} deserialize(Object o) throws XtumlException {
        if (o instanceof ${self.name}) {
            return (${self.name}) o;
        } else if (o instanceof String) {
            final ${self.name} inst = new ${self.name}();
            final JSONObject json = new JSONObject((String) o);
            ${attribute_deserializers}        }
        throw new XtumlException("Cannot deserialize value: " + o);
    }


}
