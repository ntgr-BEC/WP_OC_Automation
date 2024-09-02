package orbi.webelements;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;
public class OrbiAdministrationFirmwareUpdateElement {
        public SelenideElement manualupdate = $x("//*[@id=\"manual\"]/div[2]/b/span");
 
        public SelenideElement firmwarefilename  = $x("//*[@name='mtenFWUpload']"); // Fit for old and new arch
        public SelenideElement firmwarebrowsebutton   = $("#browse");
        public SelenideElement firmwarecancelbutton   = $("#cancel_btn");
        public SelenideElement firmwareuploadbutton   = $("#Upload");
        public SelenideElement firmwareuploadyesbutton= $x("//*[@name='Yes']");
        public SelenideElement dialogyesbutton        = $x("//*[@class=\"ant-confirm-btns\"]/button[2]");
        public SelenideElement downloadfirmware = $x("//*[@id=\"navBar_5\"]/i");
        public SelenideElement downloadbutton = $x("//*[@id=\"topicsListUl\"]/li[10]/a");
        public SelenideElement firmwareversion = $x("//div[@class=\"logo\"]/../div[2]//span[@class=\"text-green\"]");
        
    
}
