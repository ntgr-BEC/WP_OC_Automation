package orbi.webelements;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;
public class OrbiSatelliteFirmwareUpdateElement {
        public SelenideElement manualupdate = $x("//*[@id=\"manual\"]/div[2]/b/span");
 
        public SelenideElement firmwarefilename  = $("#updateFile");
        public SelenideElement firmwarebrowsebutton   = $x("//input[@id='updateFile']/..//a");
        public SelenideElement firmwarecancelbutton   = $("#cancel_btn");
        public SelenideElement firmwareuploadbutton   = $x("//*[@id='uploadBt' or @id='Upload']");
        public SelenideElement incorrectfirmwaremsg   = $x("//*[@id='pageMsg']");
        public SelenideElement incorrectfirmwaremsgsxk50   = $x("//div[@class='el-message-box__message']");
        
        public SelenideElement OKbutton = $("#okBt");
        public SelenideElement OKbuttonsxk50 = $x("//div[@class='el-message-box__btns']/button");
        public SelenideElement firmwareuploadconfirmyesbutton= $x("//*[@name='yes']");
        public SelenideElement firmwareuploadconfirmnobutton= $x("//*[@name='no']");
        public SelenideElement firmwareuploadyesbutton = $x("//*[@name='yes' or @id='yes']");
        public SelenideElement firmwareuploadyesbuttonsxk50 = $x("//div[@class='el-message-box__btns']/button[2]");
        public SelenideElement dialogyesbutton        = $x("//*[@class=\"ant-confirm-btns\"]/button[2]");
        
        public SelenideElement firmwareversion = $x("//ul[@class='version clearfix']/li[2]/strong");
        public SelenideElement firmwareversionsxk50 = $x("//*[@class='basic-info']/li[2]/b");
}
