package tracking.shared;

import java.util.HashMap;
import java.util.Map;

public enum GoalCriteria {

	HeartRate("HeartRate", 0),
	Pace("Pace", 1);

	private final int value;
	private final String name;
	private static Map<Integer, GoalCriteria> map = new HashMap<>();

	static {
		for (GoalCriteria unitEnum : GoalCriteria.values()) {
			map.put(unitEnum.value, unitEnum);
		}
	}

	private GoalCriteria(final String name, final int value) {
         this.name = name;
         this.value = value;
     }

	public String toString() {
		return name;
	}

	public int getValue() {
		return value;
	}

	public static GoalCriteria valueOf(int value) {
		return map.get(value);
	}

}
