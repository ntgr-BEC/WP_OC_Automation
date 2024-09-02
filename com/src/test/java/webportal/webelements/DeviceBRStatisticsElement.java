package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;

import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

public class DeviceBRStatisticsElement extends MyCommonAPIs {
    Logger logger = Logger.getLogger("DeviceBRStatisticsElement");

    public SelenideElement txtTemp  = $("#divStDetStatics #divOvrFlwStatics:first-child div:nth-child(1) p");
    public SelenideElement txtCpu   = $("#divStDetStatics #divOvrFlwStatics:first-child div:nth-child(2) p");
    public SelenideElement txtWanTX = $("#divStDetStatics #divOvrFlwStatics:first-child div:nth-child(3) p");
    public SelenideElement txtWanRX = $("#divStDetStatics #divOvrFlwStatics:last-child div:nth-child(3) p");
    public SelenideElement txtLanTX = $("#divStDetStatics #divOvrFlwStatics:last-child div:nth-child(3) p");
    public SelenideElement txtLanRX = $("#divStDetStatics #divOvrFlwStatics:last-child div:nth-child(3) p");
}
