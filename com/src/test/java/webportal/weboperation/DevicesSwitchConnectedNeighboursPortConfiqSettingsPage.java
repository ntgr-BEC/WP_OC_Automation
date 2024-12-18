/**
 *
 */
package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.ButtonElements;
import webportal.webelements.DevicesSwitchConnectedNeighboursPortConfiqSettingsPageElement;

/**
 * @author zheli
 */
public class DevicesSwitchConnectedNeighboursPortConfiqSettingsPage extends DevicesSwitchConnectedNeighboursPortConfiqSettingsPageElement {
    Logger logger;
    
    public DevicesSwitchConnectedNeighboursPortConfiqSettingsPage() {
        WebCheck.checkHrefIcon(URLParam.hrefDevicesSwitchConnectedNeighborsPortConfigSettings);
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
        for (int i = 0; i < 10; i++) { // when poe port is disabled by time-range
            if (WebportalParam.sw1Model.contains("P") && lbPoEStand.exists() && lbPoEStand.getValue().equals("")) {
                refresh();
            } else {
                break;
            }
        }
        MyCommonAPIs.waitReady();
    }
    
    public void modifyPortDescription(String description) {
        portDescription.setValue(description);
        ButtonElements.saveButton();
    }
    
    public String getPortDescriptionMaxLength() {
        MyCommonAPIs.waitReady();
        return portDescription.getAttribute("maxlength");
    }
    
    public String getPortDescription() {
        MyCommonAPIs.waitReady();
        return portDescription.getValue();
    }
    
    public void enablePort() {
        setSelected(portCheck, true, true);
        clickSave();
        sleep(2000);
        clickBoxLastButton();
    }
    
    public void disablePort() {
        setSelected(portCheck, false, true);
        clickSave();
        sleep(2000);
        clickBoxLastButton();
    }
    
    public boolean isEnable() {
        boolean result = false;
        if (portCheck.attr("checked") != null) {
            result = true;
        }
        return result;
    }
    
    public String getDuplexMode() {
        MyCommonAPIs.waitReady();
        return WebportalParam.getNLocText(deplexModeSelect.getSelectedText());
    }
    
    public String getPortSpeed() {
        MyCommonAPIs.waitReady();
        return WebportalParam.getNLocText(portSpeedSelect.getSelectedText());
    }
    
    /**
     * @param  s1
     *            The Right english text
     * @param  s2
     *            The other language text that was not translated
     * @return
     */
    public boolean cmpPortSpeed(String s1, String s2) {
        logger.info(String.format("%s: %s", s1, s2));
        String sSpeed = s1.split(" ")[0];
        if (s2.contains(sSpeed + "0"))
            return false;
        if (s2.contains(sSpeed))
            return true;
        return false;
    }
    
    public void modifyMaxFrameSize(String value) {
        maxFrameSlider.scrollTo();
        WebDriver driver = WebDriverRunner.getWebDriver();
        Actions action = new Actions(driver);
        if (value.equalsIgnoreCase("max") || value.equalsIgnoreCase("9216")) {
            setSlider(maxFrameSlider, true);
        } else if (value.equalsIgnoreCase("min") || value.equalsIgnoreCase("1518")) {
            setSlider(maxFrameSlider, false);
        } else {
            System.out.println("frame size error");
        }
        Action actions = action.build();
        actions.perform();
        ButtonElements.saveButton();
        MyCommonAPIs.sleepi(60);
    }
    
    public String modifyMaxFrameSizeRandom() {
        String sGet = setSlider(maxFrameSlider.getSearchCriteria(), "5555", 1, true);
        clickSave();
        sleep(2000);
        clickBoxLastButton();
        return sGet;
    }
    
    public String getMaxFrameSize() {
        MyCommonAPIs.sleepi(30);
        return getText(frameSizeValue);
    }
    
    public String modifyStormControlRateRandom() {
        rateLimitPlusButton.click();
        setSlider(stormSlider.getSearchCriteria(), "50");
        ButtonElements.saveButton();
        String stromControlRate = stormControlRateValue.getText();
        logger.info(stromControlRate);
        sleep(10, "modifyStormControlRateRandom: wait apply");
        return stromControlRate;
    }
    
    /**
     * modify Storm Control Rate 100 or 0
     *
     * @param value
     */
    public void modifyStormControlRate(String value) {
        rateLimitPlusButton.click();
        if (value.equalsIgnoreCase("max") || value.equalsIgnoreCase("100")) {
            setSlider(stormSlider.getSearchCriteria(), "100");
        } else if (value.equalsIgnoreCase("min") || value.equalsIgnoreCase("0")) {
            setSlider(stormSlider.getSearchCriteria(), "0");
        } else {
            setSlider(stormSlider.getSearchCriteria(), value);
        }
        
        ButtonElements.saveButton();
        sleep(10, "modifyStormControlRate: wait apply");
    }
    
    public String getStormControlRateValue() {
        MyCommonAPIs.waitReady();
        rateLimitPlusButton.click();
        return getText(stormControlRateValue);
    }
    
    public String modifyEgressRateRandom() {
        rateLimitPlusButton.click();
        setSlider(egressSlider.getSearchCriteria(), "50");
        String value = getText(egressValue);
        ButtonElements.saveButton();
        sleep(10, "modifyEgressRateRandom: wait apply");
        return value;
    }
    
    /**
     * modify Egress Rate Rate 100 or 0
     *
     * @param value
     */
    public void modifyEgressRate(String value) {
        rateLimitPlusButton.click();
        if (value.equalsIgnoreCase("max") || value.equalsIgnoreCase("100")) {
            setSlider(egressSlider.getSearchCriteria(), "100");
        } else if (value.equalsIgnoreCase("min") || value.equalsIgnoreCase("0")) {
            setSlider(egressSlider.getSearchCriteria(), "0");
        } else {
            System.out.println("rate value error");
        }
        ButtonElements.saveButton();
        sleep(10, "modifyEgressRateRandom: wait apply");
    }
    
    public String getEgressRateValue() {
        MyCommonAPIs.waitReady();
        rateLimitPlusButton.click();
        return getText(egressValue);
    }
    
    public String getPOEValue() {
        setExpand(btnPowerManagement, false);
        if ($(txtPowerLimitNew).exists())
            return MyCommonAPIs.getValue(txtPowerLimitNew);
        else
            return MyCommonAPIs.getText(txtPowerLimitValue);
    }
    
    public void clickSave() {
        clickButton(0);
        if (!WebportalParam.enableBatch) {
            sleepsync();
        }
    }
    
    /**
     * @param val
     *            0 to default(Detected Class), otherwise:
     *            class0 - 15.4, class1 - 4.0w, class2 - 7.0w, class3 - 15.4w, class4-30.0w, class5-45.0w, class6-60.0w,
     *            3-30 is allowed
     */
    public void setPOEValue(String val) {
        logger.info(String.format("setPOEValue: %s", val));
        setExpand(btnPowerManagement, false);
        // to fix 60W/30W issue
        lbClass.selectOption(1);
        lbClass.selectOption(0);
        if (val.equals("0")) {
            lbClass.selectOption(WebportalParam.getLocText("Detected Class"));
        } else {
            if (val.toLowerCase().contains("class")) {
                lbClass.selectOptionContainingText(val);
            } else {
                lbClass.selectOption(sUserDefined);
                setText(txtPowerLimitNew, val);
            }
        }
        clickSave();
        sleep(2000);
        clickBoxLastButton();
    }
    
    /**
     * @param enable
     *               true to enable, false to disable
     */
    public void setPoEPort(boolean enable) {
        setExpand(btnPowerManagement, false);
        setSelected(cbEnablePoe, enable, true);
        clickSave();
        sleep(2000);
        clickBoxLastButton();
    }
    
    /**
     * enable/disable port 1 & 2 or 3 for poe
     *
     * @param is2port
     *                 ture to select 2 ports, false to select 3 ports
     * @param toenable
     *                 true to enable, false to disable
     */
    public void setPorts(boolean is2port, boolean toenable) {
        logger.info(String.format("setPorts: %s-%s", is2port, toenable));
        setExpand(btnPowerManagement, false);
        clickPort(1, false);
        clickPort(2, false);
        if (!is2port) {
            clickPort(3, false);
        }
        
        setSelected(cbEnablePoe, toenable, true);
        clickSave();
        clickBoxLastButton();
        MyCommonAPIs.sleepi(10);
    }
    
    public void goBatchPortConf() {
        MyCommonAPIs.waitReady();
        if (btnBatchConf.exists()) {
            btnBatchConf.click();
            btnBatchConfYes.click();
        }
    }
    
    public void setPortSpeed(String speed) {
        logger.info("set " + speed);
        if (speed.toLowerCase().contains("auto")) {
            portSpeedSelect.selectOption(0);
        } else {
            portSpeedSelect.selectOption(WebportalParam.getLocText(speed));
        }
    }
    
    public void setDeplexMode(String mode) {
        logger.info("set " + mode);
        if (mode.toLowerCase().contains("auto")) {
            deplexModeSelect.selectOption(0);
        } else {
            deplexModeSelect.selectOption(WebportalParam.getLocText(mode));
        }
    }
    
    public boolean isPoEHasClass(String classLevel) {
        if (!txtPowerMaxValue.isDisplayed()) {
            setExpand(btnPowerManagement, false);
        }
        String sGet = MyCommonAPIs.getText(txtPowerMaxValue);
        if (sGet.contains("15"))
            return false;
        return true;
    }
    
    /**
     * @param mode
     *             0-auto, 1-auth, 2-unauth
     */
    public void setPortConfigMode(int mode) {
        logger.info("set " + mode);
        lbselect.selectOption(mode);
        takess("check port mode before save");
        clickSave();
        sleep(2000);
        clickBoxLastButton();
    }
    
    public void setPortPVID(String pvid) {
        logger.info("set " + pvid);
        lbPvidSelect.selectOptionContainingText(pvid);
        clickSave();
        sleep(2000);
        clickBoxLastButton();
    }
    
    public List<String> getVlanList() {
        vlanIdSelect.click();
        MyCommonAPIs.sleepi(1);
        return MyCommonAPIs.getTexts(vlanIdOption);
    }
    
    /**
     * @param  vlanList
     *                    split by , (ex: 25,35)
     * @param  portMode
     *                    0 - access, 1 - trunk, 2 - delete
     * @return            Note: will do on two switch
     */
    public void setPortVlan(String vlanList, int portMode) {
        logger.info(vlanList + "/" + portMode);
        vlanIdSelect.click();
        MyCommonAPIs.sleepi(1);
        for (String s : vlanList.split(",")) {
            SelenideElement ele1 = $x(String.format(vlanIdStr, s));
            ele1.scrollIntoView(true);
            ele1.click();
            MyCommonAPIs.sleepi(1);
            if (portMode == 1) {
                tag.click();
                MyCommonAPIs.sleepi(5);
//                SelenideElement ele2 = $x(String.format(vlanRadioStr, s));
//                ele2.scrollIntoView(true);
//                ele2.click();
                MyCommonAPIs.sleepi(1);
            }
        }
        vlanIdSelect.click();
        MyCommonAPIs.sleepi(1);
        ButtonElements.saveButton();
        MyCommonAPIs.sleepi(180);
    }
    
}
