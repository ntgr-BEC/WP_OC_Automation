package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import java.util.logging.Logger;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class WiredSpanningTreeElement extends MyCommonAPIs {
    Logger logger = Logger.getLogger("WiredSpanningTreeElement");

    public ElementsCollection rbSpanTreeMode = $$("#stpModeBtns input");
    public SelenideElement    btnOK          = $("#successSpanningTree");
    public SelenideElement    btnNewOK       = $(".stpSuccessModal button");
    public SelenideElement    btnErrorOK     = $(".stpErrorModal button");

    public SelenideElement cbEnable  = $("#stpNetworkStatusSwitch");
    public SelenideElement lbSTPMode = $("#divSelectMode select");

    public ElementsCollection cbLags   = $$(".SwichInline span");
    public ElementsCollection flagLags = $$(".SwichInline div[class*=swichport]");

    static public String             sSelectAll    = WebportalParam.getLocText("Select All");
    static public String             sDeSelectAll  = WebportalParam.getLocText("Deselect All");
    static public ElementsCollection btnSelectAlls = $$("div[id*=divovrflGroupPrt] button");

    public void clearAllPorts() {
        for (SelenideElement se : btnSelectAlls) {
            se.click();
            se.click();
        }
    }

    public void clearAllLags() {
        for (SelenideElement se : flagLags) {
            if (se.getAttribute("class").contains("selected")) {
                se.click();
            }
        }
    }

    public boolean isEnable() {
        return cbEnable.isSelected();
    }

}
