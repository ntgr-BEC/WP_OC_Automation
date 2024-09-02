/**
 *
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;

/**
 * @author Lavi
 */
public class WiredLAGPageElement extends MyCommonAPIs {
    final static Logger logger = Logger.getLogger("WiredLAGPageElement");

    public SelenideElement    addLagIcon      = $x("//*[@id='divModalLAGTop']/span");
    public String             sSwitchesChoice = ".in .col-sm-4";
    public ElementsCollection switchesChoice  = $$(sSwitchesChoice);

    public SelenideElement txtSwitchSelection(String dutName) {
        return $x(String.format("//div[@class='planblk']/div[text()='%s']/../../..", dutName));
    }

    public void selectSwitchsOnCreateLag() {
        txtSwitchSelection(WebportalParam.sw1deveiceName).click();
        txtSwitchSelection(WebportalParam.sw2deveiceName).click();
    }
    
    public void selectSwitchsOnCreateLag(String dev1, String dev2) {
        txtSwitchSelection(dev1).click();
        if (dev2 != null) {
            txtSwitchSelection(dev2).click();
        }
    }

    public SelenideElement        addLAGName     = $("#LAGName");
    public SelenideElement        cbEnableAdmin  = $("#addLAGEnable");
    public SelenideElement        cbEnableStatic = $("#addLAGStaticLag");
    public boolean                bEnableAdmin   = true;
    public boolean                bEnableStatic  = true;
    public static SelenideElement saveLagName    = $(Selectors.byText(WebportalParam.getLocText("Save and Continue")));

    // for edit lag page
    public SelenideElement editLagName   = $("#inpLagNameLagEdit");
    public SelenideElement editLagEnable = $("#enable");
    public SelenideElement editLagStatic = $("#static");

    public String             secPorts   = ".box-scroller li:nth-child(%s)";
    public SelenideElement    txtLagInfo = $("#divovrFlwLag0 h3 span");
    public SelenideElement    txtLagId   = $(".LagBlock .subtitle span");
    public ElementsCollection secPort4   = $$(".box-scroller li:nth-child(4)");
    public ElementsCollection secPort5   = $$(".box-scroller li:nth-child(5)");
    public SelenideElement    btnEdit    = $(".edit-btn");
    public String             sBtnEdit   = "//h3/span[contains(text(), '%s')]/..//button";
    public String             sLagName   = "h3[id*=hovrFlwLag] span:first-child";

    public String getDeviceString(String devName) {
        return String.format("//h5[contains(text(),'%s')]/../../../..", devName);
    }

    /**
     * @param  devName
     * @param  portId
     *                 1~n
     * @return
     */
    public SelenideElement getPort(String devName, String portId) {
        return getDevicePort(getDeviceString(devName), portId);
    }
    
    /**
     * @param devName
     * @param portId
     *                1~n
     */
    public void clickPortOnSwitch(String devName, String portId) {
        logger.info(String.format(": <%s>-%s>", devName, portId));
        if (devName != null) {
            getPort(devName, portId).click();
        }
    }
    
    public int getLagCount() {
        return $$(sLagName).size();
    }

    public List<String> getLagList() {
        List<String> lsRet = new ArrayList<String>();

        for (SelenideElement se : $$(sLagName)) {
            String s = getText(se);
            lsRet.add(s.split("-")[0].trim().toLowerCase());
        }
        return lsRet;
    }

    /**
     * TODO: need to have an index for lag
     *
     * @return
     */
    public String getLagPortNo() {
        String s = getText(txtLagInfo);
        Pattern p = Pattern.compile("- (\\d+)", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(s);
        assertTrue(m.find(), "match group id in lag name");
        logger.info(String.format(": <%s>", m.group(1)));
        return m.group(1);
    }

    /**
     * FIXME if 1st switch is not sw1?
     *
     * @return
     */
    public String getLagID() {
        String sRet = txtLagId.getText();
        logger.info(String.format(": <%s>", sRet));
        return sRet;
    }

    // public ElementsCollection getPorts(int portIndex) {
    // return $$(String.format(secPorts, portIndex));
    // }

    public boolean isPortsEnable(int portIndex) {
        for (SelenideElement se : getPorts(portIndex)) {
            if (!se.getAttribute("class").toLowerCase().contains("blue")) {
                logger.info(String.format(": check <%s>", se));
                return false;
            }
        }
        return true;
    }

    public boolean isPortsInactive(int portIndex) {
        for (SelenideElement se : getPorts(portIndex)) {
            String sClass = se.$(".ethernet-block").getAttribute("class").toLowerCase();
            if (!sClass.contains("gray")) {
                logger.info(String.format(": check <%s> - > <%s>", se, sClass));
                return false;
            }
        }
        return true;
    }

    // lag edit page
    public SelenideElement txtLagName   = $("#inpLagNameLagEdit");
    public SelenideElement cbAdminMode  = $("input[name*=adminMode]");
    public SelenideElement cbStaticLcap = $("input[id*=static]");

    public boolean getAdminMode() {
        return cbAdminMode.is(Condition.checked);
    }

    public boolean getLcapMode() {
        return cbStaticLcap.is(Condition.checked);
    }

    public void shutdownLag(boolean isshutdown) {
        setSelected(cbAdminMode, !isshutdown, true);
        saveLagButton.click();
    }

    //
    public SelenideElement        firstEditButton   = $("#btnEditovrFlwLag0");
    public SelenideElement        deleteLagButton   = $("#btnDelLagEdit");
    public SelenideElement        cancelLagButton   = $("#ancDelLagEdit");
    public SelenideElement        saveLagButton     = $("#btnSaveLagEdit");
    public static SelenideElement yesContinueButton = $(Selectors.byText(WebportalParam.getLocText("Yes, Continue")));

}
