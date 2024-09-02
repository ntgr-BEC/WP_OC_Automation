package switches.webelements;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import switches.param.SwitchParam;

public class SwitchingPortsPortconfigurationElements {
	public SwitchingPortsPortconfigurationElements() {
		if ("newguimanagedswitches".equals(SwitchParam.SwitchesType)) {
			newGUImanageSwitchese();
		} else if ("newguimanagedswitches".equals(SwitchParam.SwitchesType)) {
			appManageSwitches();
		} else if ("smartswitches".equals(SwitchParam.SwitchesType)) {
			smartswitches();
		}{
		}
	}
	public void appManageSwitches() {
	}
	public void newGUImanageSwitchese() {
	}
	public void smartswitches() {
		
	}
	public SelenideElement adminMode= $("select[name=v_g_1_2_6]");
	public SelenideElement portChoice(String text) {
		SelenideElement port=$(Selectors.byXpath("//td[text()='" + text + "']/../td[1]/input"));
		return  port;
	}
	
}
