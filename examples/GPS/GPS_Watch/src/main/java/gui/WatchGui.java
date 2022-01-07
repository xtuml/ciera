package gui;

import tracking.shared.Indicator;
import tracking.shared.Unit;

public interface WatchGui {

	public void setData(double value, Unit unit);

	public void setTime(int time);

	public void setIndicator(Indicator value);

	public void display();

}
