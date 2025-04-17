package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

public class PRXFirmwareElement extends MyCommonAPIs {
    final static Logger logger = Logger.getLogger("PRXFirmwareElement");
  
    public static SelenideElement PRXloginpassword = $x("//*[@id=\"password\"]/input");
    public static SelenideElement PRXloginbtn = $x("//*[@class=\"btn-logIn primary-btn\"]");
    
    public static SelenideElement Switchname = $x("//*[@name=\"uname\"]");
    public static SelenideElement Switchpassword = $x("//*[@name=\"pwd\"]");
    public static SelenideElement SwitchLogin = $x("//*[@id=\"login_button\"]");
    public static SelenideElement Maintenance = $x("//*[text() = 'Maintenance']");
    public static SelenideElement Upgrade = $x("//*[@aid =\"Upgrade\"]");
    public static SelenideElement HTTPFileUpgrade = $x("//*[@aid=\"lvl1_HTTPFileUpgrade\"]");
    public static SelenideElement Browse = $x("//*[@id=\"fakeInputForm\"]/../input");
    public static SelenideElement apply = $x("(//*[@id=\"1_5_1_button_link\"])[1]");
    public static SelenideElement image = $x("//*[@id=\"1_2_2\"]/select");
    
    
    public static SelenideElement Administrationddropdoen = $x("//*[@id=\"sidebar\"]/div[8]/button");
    public static SelenideElement FWtab                   = $x("//*[@id=\"administration\"]/ul/li[2]");
    
    public static SelenideElement ChooseFile              = $x("//*[@id=\"imageFile\"]");
    public static SelenideElement FWupgradebutton         = $x("//*[@id=\"updateButton\"]");
    public static SelenideElement FWupgradebutton2        = $x("(//*[@id=\"updateButton\"])[2]");

    public SelenideElement enterDevice(String text) {
        //SelenideElement device = $x("//td[text()='" + text + "']/../td[15]/p//i[1]");
        SelenideElement device = $x("//td[text()='" + text + "']/../td[9]/p//i[2]");
        return device;
    }
        
         public SelenideElement editModule(String text) {
        //SelenideElement Ssid = $x("//td[text()='" + text + "']/../td[15]/p");
        SelenideElement Ssid = $x("//td[text()='" + text + "']/../td[9]/p");
        return Ssid;
    }
    }
