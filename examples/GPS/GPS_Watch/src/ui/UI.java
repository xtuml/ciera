package ui;

import org.xtuml.bp.core.ComponentInstance_c;

import io.ciera.runtime.summit.application.ApplicationExecutor;
import io.ciera.runtime.summit.exceptions.XtumlException;
import tracking.shared.Indicator;
import tracking.shared.Unit;
import ui.shared.IUIFromProvider;
import ui.shared.IUIToProvider;

public class UI extends gps_watch.UI implements IUIToProvider {
	
	private IUIFromProvider peer;
	
	public UI(IUIFromProvider peer) {
		super(null, new ApplicationExecutor("VerifierExecutor"), 0);
		this.peer = peer;
		try {
		    initialize();
		} catch (XtumlException e) { /* do nothing */ }
	}

	@Override
	public void setTime(ComponentInstance_c senderReceiver, int time) {
		try {
		    setTime(time);
		} catch (XtumlException e) { /* do nothing */ }
	}

	@Override
	public void setData(ComponentInstance_c senderReceiver, Float value, Unit unit) {
		try {
		    setData(value.doubleValue(), unit);
		} catch (XtumlException e) { /* do nothing */ }
	}

	@Override
	public void setIndicator(ComponentInstance_c senderReceiver, Indicator indicator) {
		try {
		    setIndicator(indicator);
		} catch (XtumlException e) { /* do nothing */ }
	}
	
	@Override
	public gps_watch.ui.UIUI UI() {
		return new VerifierUIPort(this);
	}
	
	private class VerifierUIPort extends gps_watch.ui.UIUI {

        public VerifierUIPort(gps_watch.UI context) {
			super(context, null);
		}
		
        @Override
		public void setTargetPressed() throws XtumlException {
        	peer.setTargetPressed(null);
		}

        @Override
		public void lightPressed() throws XtumlException {
        	peer.lightPressed(null);
		}

        @Override
        public void startStopPressed() throws XtumlException {
        	peer.startStopPressed(null);
		}

        @Override
	    public void modePressed() throws XtumlException {
        	peer.modePressed(null);
		}

        @Override
	    public void lapResetPressed() throws XtumlException {
        	peer.lapResetPressed(null);
		}
	}

}