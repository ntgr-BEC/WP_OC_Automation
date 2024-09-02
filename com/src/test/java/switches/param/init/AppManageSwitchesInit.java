package switches.param.init;

import java.util.Arrays;
import java.util.List;

public class AppManageSwitchesInit {
	static List<String> switchesmodel = Arrays.asList("GC110", "GC110P", "GC510P","GC510PP");

	public AppManageSwitchesInit() {

	}

	public static boolean isAppManageSwitches(String model) {
		boolean result = switchesmodel.contains(model);
		return result;
	}
}
