/**
 *
 */
package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;

import java.util.Map;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DevicesDashPageElements;
import webportal.webelements.WiredGroupPortConfigPageElement;

/**
 * @author zhenwei.li
 */
public class WiredGroupPortConfigPage extends WiredGroupPortConfigPageElement {
    /**
     *
     */
    Logger        logger;
    public String rateLimitValue;
    public String stromControlValue;
    
    public WiredGroupPortConfigPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefGroupPortConfig);
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }
    
    public WiredGroupPortConfigPage(boolean noPage) {
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("initex...");
    }
    
    public void gotoPage() {
        open(URLParam.hrefGroupPortConfig, true);
        waitElement(cbEnablePort);
    }
    
    /**
     * @param isEnable
     *                 true to enable, false to disable
     */
    public void enablePorts(boolean isEnable) {
        logger.info(String.format(": <%s>", isEnable));
        for (SelenideElement se : getPorts(WebportalParam.getSwitchLagIndex(false, false))) {
            se.click();
        }
        setSelected(cbEnablePort, isEnable, true);
    }
    
    public void enableAllPort() {
        logger.info("enableAllPort");
        clickAllPort(0);
        clickAllPort(1);
        setSelected(cbEnablePort, true, true);
        clickButton(0);
        MyCommonAPIs.sleepi(10);
    }
    
    /**
     * Enable lag port lag1/lag2
     */
    public void enableLagPort() {
        logger.info("enableLagPort");
        clickPort(WebportalParam.sw1LagPort1, 0);
        clickPort(WebportalParam.sw1LagPort2, 0);
        clickPort(WebportalParam.sw2LagPort1, 1);
        clickPort(WebportalParam.sw2LagPort2, 1);
        setSelected(cbEnablePort, true, true);
        clickButton(0);
        clickBoxLastButton();
        sleepsync();
    }
    
    /**
     * in this page port default is enable
     *
     * @param devicseName
     * @param portarray
     */
    public void multiSetting(Map<String, String[]> portarray, Map<String, String> settingMap) {
        for (String ss : portarray.keySet()) {
            for (String sss : portarray.get(ss)) {
                System.out.println(ss + ": " + sss);
            }
        }
        int nSize = portarray.size();
        for (String key : portarray.keySet()) {
            String deviceName = key;
            String[] portName = portarray.get(key);
            for (int i = 0; i < portName.length; i++) {
                clickPort(deviceName, portName[i]).click();
                //$(sMyDevicesLink).hover(); //fail here, comment it, not sure the purpose of this action
            }
        }
        batchSetFromMap(settingMap);
        saveButton.click();
        sleep(2000);
        clickBoxLastButton();
        sleepsync();
        sleep(3 * nSize, "wait port to be setting");
    }
    
    /**
     * choice all Ports except manage port
     *
     * @param portarray
     * @param settingMap
     */
    public int multiSettingAllPorts(Map<String, String> settingMap) {
        MyCommonAPIs.sleep(3000);
        int nPort = allPorts.size();
        for (SelenideElement portsElement : allPorts) {
            portsElement.click();
            MyCommonAPIs.sleep(1 * 1000);
            loGo.hover();
        }
        
        clickPort(WebportalParam.sw1deveiceName, WebportalParam.sw1ManagePort).click();
        if (WebportalParam.enaTwoSwitchMode) {
            clickPort(WebportalParam.sw2deveiceName, WebportalParam.sw2ManagePort).click();
        }
        batchSetFromMap(settingMap);
        saveButton.click();
        
        sleepsync();
        sleep(3 * nPort, "wait port to be configured");
        return allPorts.size() - 2;
    }
    
    public void batchEnablePort(Map<String, String[]> portarray) {
        for (String ss : portarray.keySet()) {
            for (String sss : portarray.get(ss)) {
                System.out.println(ss + ": " + sss);
            }
        }
        int nSize = portarray.size();
        for (String key : portarray.keySet()) {
            String deviceName = key;
            String[] portName = portarray.get(key);
            for (int i = 0; i < portName.length; i++) {
                clickPort(deviceName, portName[i]).click();
            }
        }
        saveButton.click();
        sleepsync();
        sleep(3 * nSize, "wait port to be enabled");
    }
    
    public void batchSetPortSpeed(Map<String, String[]> portarray, String portSpeed) {
        for (String ss : portarray.keySet()) {
            for (String sss : portarray.get(ss)) {
                System.out.println(ss + ": " + sss);
            }
        }
        int nSize = portarray.size();
        for (String key : portarray.keySet()) {
            String deviceName = key;
            String[] portName = portarray.get(key);
            for (int i = 0; i < portName.length; i++) {
                clickPort(deviceName, portName[i]).click();
            }
        }
        portSpeedSelectSet(portSpeed);
        saveButton.click();
        sleepsync();
        sleep(3 * nSize, "wait port to be speed");
    }
    
    public void batchSetPortDuplexMode(Map<String, String[]> portarray, String duplexMode) {
        for (String ss : portarray.keySet()) {
            for (String sss : portarray.get(ss)) {
                System.out.println(ss + ": " + sss);
            }
        }
        int nSize = portarray.size();
        for (String key : portarray.keySet()) {
            String deviceName = key;
            String[] portName = portarray.get(key);
            for (int i = 0; i < portName.length; i++) {
                clickPort(deviceName, portName[i]).click();
            }
        }
        duplexModeSelectSet(duplexMode);
        saveButton.click();
        sleepsync();
        sleep(3 * nSize, "wait port to be duplex mode");
    }
    
    public void batchSetFromMap(Map<String, String> settingMap) {
        for (String ss : settingMap.keySet()) {
            logger.info(ss + ": " + settingMap.get(ss));
        }
        if (settingMap.get("Enable_Port") != null) {
            if (settingMap.get("Enable_Port").equals("OFF")) {
                enablePortButton.click();
            }
        }
        if (settingMap.get("Duplex_Mode") != null) {
            duplexModeSelectSet(settingMap.get("Duplex_Mode"));
        }
        if (settingMap.get("Port_Speed") != null) {
            portSpeedSelectSet(settingMap.get("Port_Speed"));
        }
        WebDriver driver = WebDriverRunner.getWebDriver();
        Actions actions = new Actions(driver);
        if (settingMap.get("Egress_Rate_Limit") != null) {
            if (settingMap.get("Egress_Rate_Limit").equalsIgnoreCase("random")) {
                actions.clickAndHold(egressSlider).moveByOffset(-100, 0).release();
            } else {
                String value = settingMap.get("Egress_Rate_Limit");
                if (value.equalsIgnoreCase("max") || value.equalsIgnoreCase("100")) {
                    setSlider(egressSlider, true);
                } else if (value.equalsIgnoreCase("min") || value.equalsIgnoreCase("0")) {
                    setSlider(egressSlider, false);
                } else {
                    System.out.println("rate value error");
                }
            }
            actions.perform();
//            actions.release();
            MyCommonAPIs.sleepi(1);
            rateLimitValue = getText(egressValue);
        }
        if (settingMap.get("Storm_Rate_Limit") != null) {
            if (settingMap.get("Storm_Rate_Limit").equalsIgnoreCase("random")) {
                actions.clickAndHold(stormSlider).moveByOffset(-100, 0).release();
                logger.info("set Storm Rate Limit");
            } else {
                String value = settingMap.get("Storm_Rate_Limit");
                if (value.equalsIgnoreCase("max") || value.equalsIgnoreCase("100")) {
                    setSlider(stormSlider, true);
                } else if (value.equalsIgnoreCase("min") || value.equalsIgnoreCase("0")) {
                    setSlider(stormSlider, false);
                } else {
                    System.out.println("Storm Rate value error");
                }
            }
            actions.perform();
//            actions.release();
            MyCommonAPIs.sleepi(1);
            stromControlValue = getText(stormControlRateValue);
        }
        if (settingMap.get("Default_VLAN") != null) {
            defaultVlanSelect.selectOption(settingMap.get("Default_VLAN"));
        }
        
        MyCommonAPIs.takess("check config on group port config page");
    }
    
    /**
     * @param portId
     *               1~n
     */
    public void setPortsWithRateLimitStormControl(String portId, String egressVal, String stormVal) {
        for (String sNo : DevicesDashPageElements.mapDeviceList.keySet()) {
            clickPortOnSwitch(DevicesDashPageElements.mapDeviceList.get(sNo).get("Name"), portId);
        }
        setSlider(stormSlider.getSearchCriteria(), stormVal);
        setSlider(egressSlider.getSearchCriteria(), egressVal);
        MyCommonAPIs.takess("check config on group port config page");
        saveButton.click();
        waitReady();
    }
}
