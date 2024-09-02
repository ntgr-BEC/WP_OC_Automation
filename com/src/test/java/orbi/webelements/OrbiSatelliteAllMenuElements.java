package orbi.webelements;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;
public class OrbiSatelliteAllMenuElements {
        public SelenideElement status = $("#status");
        public SelenideElement connecteddevice = $("#connectedDevice");
        public SelenideElement firmwareupdate = $("#fwUpdate");
        public SelenideElement logoutbutton = $("#logout");
        public SelenideElement logoutgoodbymsg = $x("//div[@class='middleMsg']");
        public SelenideElement logoutgoodbymsgnewsxk50 = $x("//div[@class='logout-goodbye']");
        public SelenideElement SatelliteOkButton = $("#extenderBt");
        
        //foot
        public SelenideElement FootOnlineSupport = $x("//a[contains(@href,'http://support.netgear.com') and not(contains(@href,'product'))]"); // Change for compatibility
        public SelenideElement FootDocumentation = $x("//*[@id=\"fancyFooter\"]//a[@href=\"http://downloadcenter.netgear.com/\"]");
        public SelenideElement SearchHelpInput = $("#footerSearchField");
        public SelenideElement SearchButton = $("#searchBtn");
        public SelenideElement FootGNUGPL   = $x("//*[@id=\"fancyFooter\"]//a[@href=\"license.htm\"]");
        //Language
        public SelenideElement curLanguage = $("#curLang");
        public SelenideElement LanguageSelectOption(String OptionValue) {
            SelenideElement ele = $x("//select[@id=\"language\"]/option[@value=\""+OptionValue+"\"]");
            return ele;
        }

}
