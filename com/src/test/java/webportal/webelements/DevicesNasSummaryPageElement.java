/**
 * 
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

/**
 * @author zheli
 *
 */
public class DevicesNasSummaryPageElement {
    public SelenideElement connectedNeighbors = $(Selectors.byText("Connected Neighbors"));

    public SelenideElement portChoice(String text) {
        SelenideElement port = $(Selectors.byXpath("//span[text()='" + text + "'][@class='ethernet-count']"));
        return port;
    }

    public SelenideElement reloadIcon         = $("[data-tooltip=\"Reload\"]");
    public SelenideElement yesReload          = $(Selectors.byText("Yes, reload"));
    public SelenideElement name               = $x("//*[@id=\"divPgeHtml\"]//div[2]/div[1]/div[1]/div[2]");
    public SelenideElement serialNumber       = $x("//*[@id=\"divPgeHtml\"]//div[2]/div[1]/div[2]/div[2]");
    public SelenideElement model              = $x("//*[@id=\"divPgeHtml\"]//div[2]/div[1]/div[3]/div[2]");
    public SelenideElement baseMACAddress     = $x("//*[@id=\"divPgeHtml\"]//div[2]/div[1]/div[4]/div[2]");
    public SelenideElement upTime             = $x("//*[@id=\"divPgeHtml\"]//div[2]/div[2]/div/div[5]/div[2]");
    public SelenideElement vlanInUse          = $x("//*[@id=\"divPgeHtml\"]//div[2]/div[2]/div/div[6]/div[2]");
    public SelenideElement traffic            = $x("//*[@id=\"divPgeHtml\"]//div[2]/div[2]/div/div[7]/div[2]");
    public SelenideElement iPAddress          = $x("//*[@id=\"divPgeHtml\"]//div[2]/div[2]/div/div[8]/div[2]");
    public SelenideElement firmware           = $x("//*[@id=\"divPgeHtml\"]//div[2]/div[2]/div/div[9]/div[2]");
    public SelenideElement bootcode           = $x("//*[@id=\"divPgeHtml\"]//div[2]/div[2]/div/div[10]/div[2]");
    public SelenideElement dateAndTime        = $x("//*[@id=\"divPgeHtml\"]//div[2]/div[2]/div/div[11]/div[2]");
    public SelenideElement diskConsume        = $("#divChrtIconColRaidVolDet0");
    public SelenideElement nasName            = $("#divCustm2NsaSumm3");
    public SelenideElement nasModel           = $("#divCustmNsaSum6");
    public SelenideElement nasSerialNumber    = $("#divSnCustmNsaSumm9");
    public SelenideElement nasAntivirus       = $("#divDevAntivirusNsaSumm15");
    public SelenideElement nasFirmwareVersion = $("#divFirware2VirdnsaSumm27");
    public SelenideElement nasTempatatureDisk = $("#disktr0");
    public SelenideElement nasSystemTemp      = $("#divSystemTempdnsaSumm30");
    public SelenideElement nasCpuTemp         = $("#divCpuTempdnsaSumm33");
    public SelenideElement nasStorageUsed     = $("#divStrUsednsaSumm22");
    public SelenideElement nasImage           = $("#imgNasIconNsaSumm");
    public SelenideElement nasCapacity        = $("#divUlVolDet0");
    public SelenideElement nasReboot          = $("#aDropDownThreeNas");
    public SelenideElement nasRebootYes       = $x(
            "//div[@class='modal custom-modal-padding fade inputModal rebootModal in']//button[text()='Yes, continue']");
    public SelenideElement rebootSucessDialog = $("#showNotification");
    public SelenideElement nasDelete          = $("#aDropDownFourNas");
    public SelenideElement nasDeleteYes       = $x(
            "//div[@class='modal custom-modal-padding fade inputModal deleteModal in']//button[text()='Yes, continue']");
    public SelenideElement deleteSucess       = $(".dataTables_empty");
    public SelenideElement diskOverview       = $("#hDiskOvrNsaSumm");
    public SelenideElement deviceDetails      = $("#hDevdetNsaSumm");
    public SelenideElement next               = $x("//button[text()='NEXT']");
    public SelenideElement usbInfo            = $("#divColVolDet1");
    public SelenideElement shareInfo          = $("#ulChrtListRaidVolDet0");

}
