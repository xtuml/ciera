package ${self.package};

${imports}

public class ${self.name} extends ActionException {

    private static final long serialVersionUID = 1l;

    public ${self.name}() {
        super();
    }

    public ${self.name}(String message) {
        super(message);
    }

    public ${self.name}(Object data) {
        super(data);
    }

}
