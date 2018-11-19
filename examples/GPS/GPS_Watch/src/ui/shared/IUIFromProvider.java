package ui.shared;

import org.xtuml.bp.core.ComponentInstance_c;

import tracking.shared.GoalCriteria;
import tracking.shared.GoalSpan;

public interface IUIFromProvider {
	
	public void setTargetPressed(ComponentInstance_c senderReceiver);
	public void startStopPressed(ComponentInstance_c senderReceiver);
	public void lapResetPressed(ComponentInstance_c senderReceiver);
	public void lightPressed(ComponentInstance_c senderReceiver);
	public void modePressed(ComponentInstance_c senderReceiver);
	public void newGoalSpec(ComponentInstance_c senderReceiver, GoalSpan spanType, GoalCriteria criteriaType, float span, float maximum, float minimum, int sequenceNumber);

}
