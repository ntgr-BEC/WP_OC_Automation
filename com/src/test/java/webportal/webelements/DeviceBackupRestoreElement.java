package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.logging.Logger;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class DeviceBackupRestoreElement extends MyCommonAPIs {
    Logger logger = Logger.getLogger("DeviceBackupRestoreElement");

    public String          sTableDevices   = "#CnfgBkupTable tbody";
    SelenideElement        btnCreateBackup = $(".VpnCol button");
    SelenideElement        btnCreate       = $(".locationBarIcons .addIcon span");
    public SelenideElement btnUpload       = $("img[src*=upload-icon]");

    public String          sBackupName  = sTableDevices + " td:first-child";
    public SelenideElement txtRowName   = $(sBackupName);
    public SelenideElement txtRowStatus = $(".ProBarBlock");

    public static SelenideElement deleteBackupTitle = $x("//h4[text()='" + WebportalParam.getLocText("Delete Backup") + "']");

    public void clickCreate() {
        if (btnCreate.exists()) {
            btnCreate.click();
        } else {
            btnCreateBackup.click();
        }
        waitReady();
    }

    public void waitBackupRestore() {
        txtRowStatus.waitWhile(Condition.exist, 300 * 1000);
        logger.info("backup/restore is done");
    }

    public SelenideElement txtCreateBackupName = $("#name");
    public SelenideElement txtCreateBackupDesc = $("#description");
    public SelenideElement txtUploadName       = $("#uploadName");
    public SelenideElement txtUploadDesc       = $("#uploadDesc");
    public SelenideElement txtBackupName       = $("#backupName");
    public SelenideElement txtBackupDesc       = $("#backupDescription");
    public SelenideElement btnDeviceOffline    = $(".devOfflinePopup.in button");
    public SelenideElement btnChoseFile        = $(".in #smart-form-addNet input");

}
