package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;

import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class RadiusElement extends MyCommonAPIs {
    Logger        logger              = Logger.getLogger("RadiusElement");
    static String cbEnableAuthElement = null;

    public SelenideElement cbEnableAuth() {
        String priqa = "#onOffAuthStatusRadius";
        String maintqa = "#onOffAuthStatus";
        if (cbEnableAuthElement == null) {
            if ($(priqa).exists()) {
                cbEnableAuthElement = priqa;
            } else {
                cbEnableAuthElement = maintqa;
            }
        }
        return $(cbEnableAuthElement);
    }
    
    public SelenideElement txtIp         = $("#primaryAdd");
    public SelenideElement txtPort       = $("#primaryPort");
    public SelenideElement txtSecret     = $("#primarySecret");
    public SelenideElement txtIpSec      = $("#secondaryAdd");
    public SelenideElement txtPortSec    = $("#secondaryPort");
    public SelenideElement txtSecretSec  = $("#secondarySecret");
    public SelenideElement txtReauthTime = $("#divToolTipInnerSliderRateLimit");
    public SelenideElement cbEnableAcc   = $("#onOffAccounting");
    
    //AddedByPratik
    public SelenideElement enableRadiusServer           = $x("(//span[@class='cstmSlider cstmRound'])[3]");
    public SelenideElement inputPrimaryIPAddress        = $x("//input[@id='primaryAdd']");
    public SelenideElement inputSecurityPassword        = $x("//input[@id='primarySecret']");
    public SelenideElement saveRadiusSettings           = $x("//button[text()='Save' and @id='_buSaveBtnTwo']");
    public SelenideElement radiusServerEnableSuccessMsg = $x("//*[text()='Radius Settings configured successfully. It might take some time to reflect.']");

}
