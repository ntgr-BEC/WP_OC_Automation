package webportal.webelements;

import static com.codeborne.selenide.Selenide.$x;

import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

public class DeviceOoklaSpeedtestElement extends MyCommonAPIs{

    Logger logger = Logger.getLogger("DeviceOoklaSpeedtestElement");
    public static SelenideElement selectAllDevice         = $x("//*[@name='selectAllDevice']/../i"); 
    public SelenideElement selectOneDevice(String devicseName) {
        SelenideElement Device = $x("//*[text()='" + devicseName + "']/../../..//i");
        return Device;
    }

    public SelenideElement selectOneDeviceTestResult(String devicseName) {
        SelenideElement Device = $x("(//*[text()='" + devicseName + "']/../../../../../td[2]//span)[last()]");
        return Device;
    }    

    public SelenideElement selectOneDeviceTestTimestamp(String devicseName) {
        SelenideElement Device = $x("(//*[text()='" + devicseName + "']/../../../../../td[2]//p)[last()]");
        return Device;
    }    
        
    public SelenideElement selectOneinput(String devicseName) {
        SelenideElement Device = $x("//*[text()='" + devicseName + "']/../../..//input");
        return Device;
    }
    public static SelenideElement btnRuntest     = $x("//*[@class='loginBtnSection fontSize16']");
    public static SelenideElement elementUpload = $x("//*/tr/td[1]");
    public static SelenideElement elementDownload      = $x("//*/tr/td[2]");
    public static SelenideElement elementLatency      = $x("//*/tr/td[3]");
    public static SelenideElement elementwebportaltime      = $x("//*[@id=\"divCOnSecLed\"]/div[2]/div/div[3]/div/div/div/div[1]/h6[2]");
}
