package ${package_name};

${import_block}

public class ${class_name}InstanceStateMachine extends InstanceStateMachine {
    
${states_block}

    private static final String[] stateNames = new String[] {
${state_names_block}
    };
    
    public ${class_name}InstanceStateMachine() {
        sem = new StateEventMatrix( new int[][]{
${state_rows}
        }) {
            @Override
            public String getStateName( int state ) {
                return stateNames[state];
            }
        };
    }

    @Override
    public void stateActivity(int stateNum, Event e) throws XtumlException {
${state_activity_switch_block}
        else throw new StateMachineException( "State does not exist." );
    }
    
${state_activity_block}

}
