package switches.weboperation;

import java.util.List;

import switches.publiclibrary.SwitchesButton;
import switches.webelements.SwitchingPortsPortconfigurationElements;

public class SwitchingPortsPortconfiguration extends SwitchingPortsPortconfigurationElements {

	public SwitchingPortsPortconfiguration() {
		CatlogPage catlogPage = new CatlogPage();
		catlogPage.switchingPortsPortConfigutation();
		
	}

	public void setPortConfiguration(String port) {
		portChoice(port);
		adminMode.selectOption("Enable");
		SwitchesButton.apply();
	}
	public void linkUpPort(List<String> portList) {
		for(String port : portList) {
			portChoice(port).click();
		}
		adminMode.selectOption("Enable");
		SwitchesButton.apply();
	}
}
