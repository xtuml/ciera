    @Override
    public ${type_name} ${self.name}() throws XtumlException {
.if ( multiplicity_many )
        ${type_name} aset = new ${type_name}Impl();
.else
        ${type_name}Set aset = new ${type_name}SetImpl();
.end if
        aset.addAll(this.${fromlink}${tolink});
        aset.remove(this);
        return aset${addany};
    }
