package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

/**
 * @author Netgear
 */

public class DevicesApSummaryPageElement extends MyCommonAPIs {
    public SelenideElement Dropdown           = $x("//*[@id=\"divColMdAptn\"]/div[1]/span");
    public SelenideElement reboot             = $("#divColMdAptn > div.moreIcon.HeaderAdminBlock.open > ul > li:nth-child(2)");
    public SelenideElement rebootconfirm      = $x("//button[text()='Continue']");
    public SelenideElement reset              = $("#divColMdAptn > div.moreIcon.HeaderAdminBlock.open > ul > li:nth-child(1)");
    public SelenideElement resetconfirm       = $x("//button[text()='Yes, reset']");
    public SelenideElement resetconfirmnew    = $x("//button[text()='Yes, reset.']");
    public SelenideElement share              = $("#divBackPurpleEightApBtn");
    public SelenideElement shareemail         = $("#share_email_id");
    public SelenideElement send               = $x("//button[text()='Send']");
    public SelenideElement alerttext          = $x("//div[@class='alert alert-success alert-dismissable']");
    public SelenideElement name               = $("#pdevIdAPSumm");
    public SelenideElement editname           = $("#ddinpEditAPSumm");
    public SelenideElement edityes            = $("#btnIdAPSumm");
    public SelenideElement editno             = $("#btnSpnAPSumm");
    public SelenideElement selectChannelChart = $("#selChrtInpTrfChrt");
    public SelenideElement channelChart24GHz  = $x("(//select[@class='form-control inputTextField'])[2]");
    public SelenideElement select5Ghz         = $x("(//*[@id=\"icon2PlusNsaAccordHeadSettng\"])[2]"); 
    public SelenideElement select24Ghz         = $x("(//*[@id=\"hServRadiChnl\"])[1]");
    public SelenideElement select50Ghz         = $x("(//*[@id=\"hServRadiChnl\"])[2]");
    public SelenideElement saveBtn            = $x("//*[@id=\"btnSaveUpdteRadiChnl\"]");
    public SelenideElement successMessage     = $x("//*[text()='Your configuration has been applied. It may take some time to reflect']");
    public SelenideElement channelChart5GHz   = $x("(//div[@id='divChnRadiChnl']/select)[2]");
    public SelenideElement radioAndChannelsOp = $x("//div[contains(@class,'divactiveLeftMenu')]//a[text()='Radio and Channels']");
    public SelenideElement maximazie24GHZBand = $x("//span[@id='spnNsaAccordHeadSettng']//i[@class='icon icon-icon-collapse']/../..//span[text()='2.4GHz']");

    public SelenideElement portChoice(String text) {
        SelenideElement port = $(Selectors.byXpath("//span[text()='" + text + "'][@class='ethernet-count']"));
        return port;
    }
}
