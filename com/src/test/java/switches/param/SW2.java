package switches.param;

import switches.param.init.CheckSwitchType;
import util.XMLManagerForTest;

public class SW2 extends SwitchParam {

	public SW2() {
		XMLManagerForTest xmlManager = new XMLManagerForTest();
		CheckSwitchType checkSwitchType = new CheckSwitchType();
		IPADDRESS = xmlManager.getValueFromWebPortAndDut("SW2", "Ip_Address");
		LOGINNAME = "admin";
		PASSWORD = "";
		MODEL = xmlManager.getValueFromWebPortAndDut("SW2", "Model");
		SwitchesType = checkSwitchType.getCheckResult(MODEL);
	}
}
