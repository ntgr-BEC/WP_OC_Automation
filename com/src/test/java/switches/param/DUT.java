package switches.param;

import switches.param.init.CheckSwitchType;
import util.XMLManagerForTest;

public class DUT extends SwitchParam {

	public DUT() {
		XMLManagerForTest xmlManager = new XMLManagerForTest();
		CheckSwitchType checkSwitchType = new CheckSwitchType();
		IPADDRESS = xmlManager.getValueFromWebPortAndDut("DUT", "Ip_Address");
		MODEL = xmlManager.getValueFromWebPortAndDut("DUT", "Model");
		PASSWORD = checkSwitchType.getSwitchPassword(MODEL);
		SwitchesType = checkSwitchType.getCheckResult(MODEL);
	}
}
