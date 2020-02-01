import gps_watch.GPS_WatchApplication;
import io.ciera.runtime.summit.application.tasks.ReceivedMessageTask;
import io.ciera.runtime.summit.exceptions.XtumlException;

public class Test {
    public static void main(String[] args) {
        // set up app
        GPS_WatchApplication app = new GPS_WatchApplication();
        app.setup(args, null);
        app.initialize();
        Thread t = new Thread(app);
        t.start();

        // send start signal
        app.Tracking().getRunContext().execute(new ReceivedMessageTask() {
            @Override
            public void run() throws XtumlException {
                app.Tracking().UI().startStopPressed();
            }
        });
    }
}
