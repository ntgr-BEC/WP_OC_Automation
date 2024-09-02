package switches.webelements;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

import switches.param.SwitchParam;

public class SystemInfomationPageElements {
	public SystemInfomationPageElements() {
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
	public SelenideElement localModel= $("#cloudModeDisable");
	public SelenideElement cloudModel= $("#cloudModeEnable");
	public SelenideElement oKButton=$("[aid=AlertMsgButton_1]");
	public SelenideElement applyButton=$("#btn_Apply");
}
