package tracking.shared;

import java.util.HashMap;
import java.util.Map;

public enum GoalSpan {

	Distance("Distance", 0),
	Time("Time", 1);

	private final int value;
	private final String name;
	private static Map<Integer, GoalSpan> map = new HashMap<>();

	static {
		for (GoalSpan unitEnum : GoalSpan.values()) {
			map.put(unitEnum.value, unitEnum);
		}
	}

	private GoalSpan(final String name, final int value) {
         this.name = name;
         this.value = value;
     }

	public String toString() {
		return name;
	}

	public int getValue() {
		return value;
	}

	public static GoalSpan valueOf(int value) {
		return map.get(value);
	}

}
