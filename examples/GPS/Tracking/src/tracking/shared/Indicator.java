package tracking.shared;

import java.util.HashMap;
import java.util.Map;

public enum Indicator {

	Blank("Blank", 0),
	Down("Down", 1),
	Flat("Flat", 2),
	Up("Up", 3);

	private final int value;
	private final String name;
	private static Map<Integer, Indicator> map = new HashMap<>();

	static {
		for (Indicator unitEnum : Indicator.values()) {
			map.put(unitEnum.value, unitEnum);
		}
	}

	private Indicator(final String name, final int value) {
         this.name = name;
         this.value = value;
     }

	public String toString() {
		return name;
	}

	public int getValue() {
		return value;
	}

	public static Indicator valueOf(int value) {
		return map.get(value);
	}

}
