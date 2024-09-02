package switches.param;

import switches.param.init.CheckSwitchType;
import util.XMLManagerForTest;

public class SW1 extends SwitchParam {

	public SW1() {
		XMLManagerForTest xmlManager = new XMLManagerForTest();
		CheckSwitchType checkSwitchType = new CheckSwitchType();
		IPADDRESS = xmlManager.getValueFromWebPortAndDut("SW1", "Ip_Address");
		LOGINNAME = "admin";
		MODEL = xmlManager.getValueFromWebPortAndDut("SW1", "Model");
		PASSWORD ="Netgear1@";
		
		SwitchesType = checkSwitchType.getCheckResult(MODEL);
	}
}
