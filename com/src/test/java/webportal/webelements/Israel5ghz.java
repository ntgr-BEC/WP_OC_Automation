package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class Israel5ghz extends MyCommonAPIs {
    
    public static SelenideElement devices   = $x("//a[@href='/#/devices/dash']");
    public SelenideElement connectedStatus = $x("//span[@id='spnDevStDevIddevicesDash1']");
    public SelenideElement        editTabHover     = $x("//p[@id='ptdNomrginDevIddevicesDash0']");
    public SelenideElement        editTab     = $("//i[@class='zIndex0' and @data-tooltip='Edit']");
    public SelenideElement        radioChannels      = $("//a[text()='Radio and Channels' and @class='anchorundefined']");
    public static SelenideElement fiveghzHigh   = $x("/html/body/div[2]/div/div[2]/div[3]/div[2]/div[4]/div/div[2]/div/div[2]/div/div/div/form/div[3]/div/div/div/h3");
    public SelenideElement        channel         = $("/html/body/div[2]/div/div[2]/div[3]/div[2]/div[4]/div/div[2]/div/div[2]/div/div/div/form/div[3]/div/div/div/div/div/div/div[3]/select");


}


