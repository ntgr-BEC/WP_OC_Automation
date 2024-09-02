package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

/**
 * @author Netgear
 */

public class DevicesApPortConfiqSettingPageElement extends MyCommonAPIs {
    public SelenideElement lbPVID = $("#divSwitchPrtSettng select");
}
