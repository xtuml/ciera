package ui.shared;

import org.xtuml.bp.core.ComponentInstance_c;

import tracking.shared.Indicator;
import tracking.shared.Unit;

public interface IUIToProvider {

	public void setTime(ComponentInstance_c senderReceiver, int time);
	public void setData(ComponentInstance_c senderReceiver, Float value, Unit unit);
	public void setIndicator(ComponentInstance_c senderReceiver, Indicator indicator);

}
