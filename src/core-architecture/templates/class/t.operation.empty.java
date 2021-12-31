@Override
public ${type_name} ${self.name}(${parameter_list}) {
    throw new EmptyInstanceException("Cannot invoke operation '${self.name}' on empty instance.", null, this);
}
