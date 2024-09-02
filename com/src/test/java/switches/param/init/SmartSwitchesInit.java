package switches.param.init;

import java.util.Arrays;
import java.util.List;

public class SmartSwitchesInit {
	static List<String> switchesmodel = Arrays.asList("S3300", "S3300-52x", "S3300-28x");

	public SmartSwitchesInit() {
	}

	public static boolean isSmartSwitches(String model) {
		boolean result = switchesmodel.contains(model);
		return result;
	}
}
