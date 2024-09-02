package webportal.webelements;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.List;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class DevicesApMeshTopologyPageElement extends MyCommonAPIs {
    
    public static SelenideElement meshTopology               = $x("//div/a[text()='" + WebportalParam.getLocText("Mesh Topology") + "']");
    public SelenideElement        meshRootAp                 = $x("//img[contains(@src,'root-apIcon')]");
    public SelenideElement        meshExtenderAp             = $x("//img[contains(@src,'satellite-ap')]");
    public static SelenideElement extenderApChNum            = $x(
            "(//span[text()='" + WebportalParam.getLocText("Extender AP") + "' and @class='fontSize13']/..//span)[4]");
    public SelenideElement        advanceSettings            = $x("//span[@class='configurationIcon']");
    public String                 sMeshViewGrahButton        = ".icon-pool";
    public String                 sMeshViewListButton        = ".icon-page-setting";
    public String                 meshModeSelect             = "//div[@class='CustomRadioBlock Inline-Radio']";
    public SelenideElement        meshModeHelp               = $x(meshModeSelect + "//..//span");
    public static SelenideElement meshModeHelpWindow         = $x(
            "//div[contains(@style,'display: block')]//h4[text()='" + WebportalParam.getLocText("Mesh Mode") + "']");
    public SelenideElement        closeMeshModeHelpWindow    = $x("//div[contains(@style,'display: block')]//button");
    public static String          backhaulSettingsSelect     = "//h5[text()='" + WebportalParam.getLocText("Backhaul Settings")
            + "']/..//div[@class='CustomRadioBlock Inline-Radio mb-10']";
    public static String          rootApSelection            = "//h5[text()='" + WebportalParam.getLocText("Root AP Selection")
            + "']/..//div[@class='CustomRadioBlock Inline-Radio mb-10']";
    public String                 primaryPreferredRootDev    = "//input[@name='firstSelectedRoot']";
    public static SelenideElement primaryPreferredBackhaul   = $x(
            "(//h5[text()='" + WebportalParam.getLocText("Preferred Backhaul") + "'])[1]/../select");
    public String                 secondaryPreferredRoot     = "//input[@name='secondSelectedRoot']";
    public static SelenideElement secondaryPreferredBackhaul = $x(
            "(//h5[text()='" + WebportalParam.getLocText("Preferred Backhaul") + "'])[2]/../select");
    public SelenideElement        meshModeOk                 = $x("(//div[contains(@class,'AdvanceMeshModalImg')]/..//button)[2]");
    public SelenideElement        meshSave                   = $x("//button[@class='btn saveBtn']");
    public static SelenideElement meshConfirm                = $x("//button[text()='" + WebportalParam.getLocText("Confirm") + "']");

    public String sMeshListViewTable = ".RootTbleCutomize";
    public String sGraphAPHilight    = ".satelliteBlock .MeshApBox";
    public String sGraphAPName       = ".satelliteBlock h6";
    public String sListAPHilight     = ".RootTbleCutomize tr td:first-child img";
    public String sListAPName        = ".RootTbleCutomize tr td:first-child .mb-0";

    public int getHilightAPIndexGraph() {
        int pos = 0;
        waitElement(sGraphAPHilight);
        for (SelenideElement se : $$(sGraphAPHilight)) {
            if (se.getAttribute("class").contains("selected"))
                return pos;
            pos++;
        }
        return pos;
    }
    
    public int getHilightAPIndexList() {
        int pos = 0;
        waitElement(sListAPHilight);
        for (SelenideElement se : $$(sListAPHilight)) {
            if (se.getAttribute("src").contains("selected"))
                return pos;
            pos++;
        }
        return pos;
    }
    
    public List<String> getAPNameGraph() {
        waitElement(sGraphAPName);
        return getTexts(sGraphAPName);
    }

    public List<String> getAPNameList() {
        waitElement(sListAPName);
        return getTexts(sListAPName);
    }
    
}
