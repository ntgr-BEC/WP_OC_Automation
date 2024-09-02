package switches.webelements;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import switches.param.SwitchParam;
import switches.param.init.AppManageSwitchesInit;
import switches.param.init.NewGuiManagedSwitchesInit;
import switches.param.init.SmartSwitchesInit;

public class CatlogPageElements {
	public static SwitchParam switches;
	public CatlogPageElements() {
		if (NewGuiManagedSwitchesInit.isNewGuiManagedSwitches(SwitchParam.MODEL)) {
			newGUImanageSwitchese();
		} else if (AppManageSwitchesInit.isAppManageSwitches(SwitchParam.MODEL)) {
			appManageSwitches();
		} else if (SmartSwitchesInit.isSmartSwitches(SwitchParam.MODEL)) {
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
	public  SelenideElement logoutButton=$("#logframe");
	public  SelenideElement switching= $(Selectors.byText("Switching"));
	public  SelenideElement ports= $(Selectors.byText("Ports"));
	public  SelenideElement portConfiguration= $(Selectors.byText("Port Configuration"));
	public  SelenideElement system= $(Selectors.byText("System"));
	public  SelenideElement management= $(Selectors.byText("Management"));
	public  SelenideElement systemInformation= $(Selectors.byText("System Information"));
}
