package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;

import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

public class DeviceBRPortConfigElement extends MyCommonAPIs {
    Logger logger = Logger.getLogger("DeviceBRPortConfigElement");

    public SelenideElement portDesc = $("#primaryPort");
    public SelenideElement lbPVID   = $("#selVlanIdSettng");
}
