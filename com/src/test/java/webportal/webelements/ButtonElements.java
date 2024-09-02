package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class ButtonElements extends MyCommonAPIs {
    public static String          saveString = WebportalParam.ntgrLanguageTranslate("switchBtn", "save", WebportalParam.BrowserLanguage);
    public static SelenideElement SAVEBUTTON = $(Selectors.byXpath("(//button[text()='" + saveString + "'])[last()]"));

    public static void saveButton() {
        takess("save screenshot before click save");
        SAVEBUTTON.click();
    }

    public static SelenideElement SAVEBUTTON1        = $x("//button[@id='saveEditVlan']");
    public SelenideElement        publicOptionButton = $(".icon-overflow");
    public SelenideElement        reLoadIcon         = $("[data-target*=reset] span");
    public SelenideElement        preset             = $x("//*[@id=\"divColMdSwitchBtn\"]/div[1]/span");
    public SelenideElement        reBootIcon         = $("[data-target*=reboot] span");
    public SelenideElement        deleteButton       = $("[data-target*=delete] span");
    public static String          NoDontRebootString = WebportalParam.ntgrLanguageTranslate("switchBtn", "dontRebootDevice",
            WebportalParam.BrowserLanguage);
    public SelenideElement        NoDontReboot       = $(Selectors.byText(NoDontRebootString));
    public static String          NoDontReloadString = WebportalParam.ntgrLanguageTranslate("im5.6Keys", "No, don't factory reset.",
            WebportalParam.BrowserLanguage);
    public SelenideElement        NoDontReload       = $(Selectors.byText(NoDontReloadString));
    public static String          nextString         = WebportalParam.ntgrLanguageTranslate("common", "next", WebportalParam.BrowserLanguage);
    public SelenideElement        nextButton         = $(Selectors.byText(nextString));
    public SelenideElement        shareButton        = $("[data-target*=Share] span");
    public SelenideElement        obShareButton      = $("#aOpenShareDiagApBtn span");
    public SelenideElement        shareEmailAddress  = $("#share_email_id");
    public SelenideElement        shareError         = $(".colorRed.shareError");
    public static String          sendString         = WebportalParam.ntgrLanguageTranslate("shareModal", "send", WebportalParam.BrowserLanguage);
    public SelenideElement        sendButton         = $(Selectors.byText(sendString));
    public static String          okString           = WebportalParam.ntgrLanguageTranslate("macAcl", "ok", WebportalParam.BrowserLanguage);
    public SelenideElement        okButton           = $(Selectors.byText("OK"));

    public static SelenideElement NEXT = $x("//*[@id=\"addDeviceModal\"]/div/div/div[3]/button[2]");

    public SelenideElement testVPNButton = $("#divBlackPurpleTwoSwitchBtn .testVpnServerIcom");

    public ButtonElements() {
        // TODO Auto-generated constructor stub

    }

}
