static final class ${self.name} extends AbstractEvent implements Event {

    private static final long serialVersionUID = ${self.serial_version_id}L;

    ${self.name}(Object... data) {
        super($u_{self.name}, data);
    }

}
