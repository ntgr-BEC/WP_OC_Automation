package businessrouter.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;

public class BrAdvancedBackupSettingsElements {
    public SelenideElement backupbutton         = $("#bak_backup_BackUp");
    public SelenideElement restorefile          = $("#bak_backup_inputs");
    public SelenideElement restorebrowsebutton  = $("#bak_backup_Browse");
    public SelenideElement restorbutton         = $("#bak_backup_Restore");
    public SelenideElement factorydefaultbutton = $("#bak_backup_Erase");
    public SelenideElement factoryapplybutton   = $("#backup_factory_apply");
    public SelenideElement restorewrongmessage  = $x("//div[@class=\"ant-message-notice\"]//span[text()=\"Assign the correct file. The file format is *.cfg\"]");
    public SelenideElement dialogyesbutton      = $x("//*[@class=\"ant-confirm-btns\"]/button[2]");
    public SelenideElement restoretext          = $x("//*[@class=\"title-middle m-b-16\"]");
    
}
