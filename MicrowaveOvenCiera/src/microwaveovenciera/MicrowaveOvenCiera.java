package microwaveovenciera;

import ciera.application.XtumlApplication;
import microwaveovenciera.components.microwaveoven.microwaveoven.Beeper;
import microwaveovenciera.components.microwaveoven.microwaveoven.Door;
import microwaveovenciera.components.microwaveoven.microwaveoven.InternalLight;
import microwaveovenciera.components.microwaveoven.microwaveoven.MagnetronTube;
import microwaveovenciera.components.microwaveoven.microwaveoven.Oven;
import microwaveovenciera.components.microwaveoven.microwaveoven.Turntable;

public class MicrowaveOvenCiera extends XtumlApplication {

    @Override
    public void setup() {
        createThreadPool( 1 );
        getThreadFromPool( 0 ).addDefaultTarget( Beeper.class );
        getThreadFromPool( 0 ).addDefaultTarget( Door.class );
        getThreadFromPool( 0 ).addDefaultTarget( InternalLight.class );
        getThreadFromPool( 0 ).addDefaultTarget( MagnetronTube.class );
        getThreadFromPool( 0 ).addDefaultTarget( Oven.class );
        getThreadFromPool( 0 ).addDefaultTarget( Turntable.class );
    }
    
    public static void main( String[] args ) {
        XtumlApplication.app = new MicrowaveOvenCiera();
        XtumlApplication.app.setup();
        XtumlApplication.app.start();
    }

}
