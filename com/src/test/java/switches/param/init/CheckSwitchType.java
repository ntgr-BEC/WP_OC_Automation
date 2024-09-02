package switches.param.init;

/**
 * 通过型号检查是那种switches,并且把参数传回
 * 
 * @author Administrator
 *
 */
public class CheckSwitchType {
	public String getCheckResult(String model) {
		String switchestype = "";
		if (AppManageSwitchesInit.isAppManageSwitches(model)) {
			switchestype = "appmanageswitches";
		} else if (SmartSwitchesInit.isSmartSwitches(model)) {
			switchestype = "smartswitches";
		} else if (NewGuiManagedSwitchesInit.isNewGuiManagedSwitches(model)) {
			switchestype = "newguimanagedswitches";
		}
		return switchestype;
	}

	public String getSwitchPassword(String model) {
		String password = "";
		if (AppManageSwitchesInit.isAppManageSwitches(model)) {
			password = "password";
		} else if (SmartSwitchesInit.isSmartSwitches(model)) {
			password = "password";
		} else if (NewGuiManagedSwitchesInit.isNewGuiManagedSwitches(model)) {
			password = "";
		}
		return password;
	}

	public String getSwitchUsername(String model) {
		String userName = "";
		if (AppManageSwitchesInit.isAppManageSwitches(model)) {
			userName = "admin";
		} else if (SmartSwitchesInit.isSmartSwitches(model)) {
			userName = "admin";
		} else if (NewGuiManagedSwitchesInit.isNewGuiManagedSwitches(model)) {
			userName = "admin";
		}
		return userName;
	}

}
