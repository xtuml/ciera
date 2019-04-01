        case "${self.name}":
            return new Template<${self.comp_name}>( population ) {
                @Override
                public void evaluate(Object ... symbols) throws XtumlException ${body}
            };
