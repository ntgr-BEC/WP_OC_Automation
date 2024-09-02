package switches.param.init;

import java.util.Arrays;
import java.util.List;

public class NewGuiManagedSwitchesInit{
	static List<String> switchesmodel = Arrays.asList("M5300-28G3", "M5300-52G3", "GC510P");

	public NewGuiManagedSwitchesInit() {
	}

	public static boolean isNewGuiManagedSwitches(String model) {
		boolean result = switchesmodel.contains(model);
		return result;
	}
}
