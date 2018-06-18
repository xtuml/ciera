        case "${self.name}":
            return new Template<${self.comp_name}>( population ) {
                @Override
                public void evaluate() throws XtumlException ${body}
            };
