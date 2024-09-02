package businessrouter.webelements;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;
public class BrAdvancedFirmwareUpdateElements {
    public SelenideElement firmwarefilename  = $("#upgradeFile");
    public SelenideElement firmwarebrowsebutton   = $("#browse_btn");
    public SelenideElement firmwarecancelbutton   = $("#cancel_btn");
    public SelenideElement firmwareuploadbutton   = $("#upload_btn");
    public SelenideElement firmwareuploadyesbutton= $("#yes_btn");
    public SelenideElement dialogyesbutton        = $x("//*[@class=\"ant-confirm-btns\"]/button[2]");
    public SelenideElement downloadfirmware = $x("//*[@id=\"navBar_5\"]/i");
    public SelenideElement downloadbutton = $x("//*[@id=\"topicsListUl\"]/li[10]/a");
    public SelenideElement firmwareversion = $x("//div[@class=\"logo\"]/../div[2]//span[@class=\"text-green\"]");
    
}
