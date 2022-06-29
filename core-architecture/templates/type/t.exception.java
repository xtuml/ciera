class ${self.name} extends ActionException {

    private static final long serialVersionUID = 1l;

    \
.if (public_type)
public \
.end if
${self.name}() {
    }

    \
.if (public_type)
public \
.end if
${self.name}(String message) {
        super(message);
    }

    \
.if (public_type)
public \
.end if
${self.name}(Throwable cause) {
        super(cause);
    }

    \
.if (public_type)
public \
.end if
${self.name}(Object data) {
        super(data);
    }

}
