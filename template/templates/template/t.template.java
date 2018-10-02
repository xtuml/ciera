        case "${self.name}":
            return new Template<${self.comp_name}>( population ) {
                @Override
                public void evaluate( SymbolTable symbolTable ) throws XtumlException ${body}
            };
