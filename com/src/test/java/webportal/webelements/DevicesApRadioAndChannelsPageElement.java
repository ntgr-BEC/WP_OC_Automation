package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

/**
 * @author Netgear
 */

public class DevicesApRadioAndChannelsPageElement extends MyCommonAPIs {
    public SelenideElement twoPointFourGHzChannel      = $x("(//div[@id='divChnRadiChnl'])[1]//select");
    public SelenideElement twoPointFourGHzChannelWidth = $x("(//div[@id='divOnOdSetRadiChnl'])[1]//select");
    public SelenideElement twoPointFourGHzOutputPower  = $x("(//div[@id='divSettRadiChnl'])[1]//select");
    public SelenideElement openFiveGHzTier             = $x("(//h3[@id='hServRadiChnl'])[2]");
    public SelenideElement fiveGHzChannel              = $x("(//div[@id='divChnRadiChnl'])[2]//select");
    public SelenideElement fiveGHzChannelWidth         = $x("(//div[@id='divOnOdSetRadiChnl'])[2]//select");
    public SelenideElement fiveGHzOutputPower          = $x("(//div[@id='divSettRadiChnl'])[2]//select");
    public SelenideElement openFiveGHzHighTier         = $x("(//h3[@id='hServRadiChnl'])[3]");
    public SelenideElement fiveGHzHighChannel          = $x("(//div[@id='divChnRadiChnl'])[3]//select");
    public SelenideElement fiveGHzHighChannelWidth     = $x("(//div[@id='divOnOdSetRadiChnl'])[3]//select");
    public SelenideElement fiveGHzHighOutputPower      = $x("(//div[@id='divSettRadiChnl'])[3]//select");
    public SelenideElement yesRunBtn                   = $x("//button[text()='Yes, run']");
    public SelenideElement saveBtn                     = $("#btnSaveUpdteRadiChnl");
}
