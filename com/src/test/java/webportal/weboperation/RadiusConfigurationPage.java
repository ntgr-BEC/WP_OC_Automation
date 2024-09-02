package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;

import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.RadiusConfigurationElement;

public class RadiusConfigurationPage extends RadiusConfigurationElement {
    Logger               logger         = Logger.getLogger("RadiusConfigurationPage");
    String               ip1            = "10.1.1.1";
    String               ip2            = "10.1.1.2";
    public static String sw1DeviceXpath = "";
    public static String sw2DeviceXpath = "";
    static boolean       sw1InTop       = true;
    
    public RadiusConfigurationPage() {
        logger.info("init...");
    }
    
    public void gotoPage() {
        WebCheck.checkUrl(URLParam.hrefRadiusConfiguration);
        // if (!WebportalParam.skipIssueCheck)
        // throw new RuntimeException("PRJCBUGEN-28956");
    }
    
    /**
     * @param  isSW1
     *               true for sw1
     * @return
     */
    public String getDeviceXpathX(boolean isSW1) {
        if (sw1DeviceXpath.isEmpty()) {
            sw1DeviceXpath = getDeviceXpath(WebportalParam.sw1deveiceName);
            if (WebportalParam.enaTwoSwitchMode) {
                sw2DeviceXpath = getDeviceXpath(WebportalParam.sw2deveiceName);
            }
        }
        
        if (isSW1)
            return sw1DeviceXpath;
        else
            return sw2DeviceXpath;
    }
    
    /**
     * @param btnIndex
     *                 from 0 to 1, "port mode", "select all"
     * @param isSW1
     *                 true for sw1
     */
    public void clickBtn(int btnIndex, boolean isSW1) {
        logger.info(btnIndex + " / " + isSW1);
        String sPath = String.format("%s//button", getDeviceXpathX(isSW1));
        $$x(sPath).get(btnIndex).click();
    }
    
    /**
     * To enable radius option for switch
     *
     * @param isEnable
     *                 true to enable
     * @param isSW1
     *                 true for sw1
     */
    public void setEnable(boolean isEnable, boolean isSW1) {
        logger.info(isEnable + " / " + isSW1);
        String sPath = String.format("%s//input", getDeviceXpathX(isSW1));
        setSelected($x(sPath), isEnable, true);
        waitReady();
        if (isEnable && btnSave.isDisplayed()) {
            btnSave.click();
        }
    }

    public void setEnable(boolean isEnable) {
        logger.info("to: " + isEnable);
        for (SelenideElement se : $$(btnStatus)) {
            setSelected(se, isEnable);
        }
        waitReady();
        if (isEnable && btnSave.isDisplayed()) {
            btnSave.click();
        }
    }

    public void setEnable(String devName, boolean isEnable) {
        logger.info(devName);
        setSelected($x(getCheckBox(devName)), isEnable, true);
        waitReady();
        if (isEnable && btnSave.isDisplayed()) {
            btnSave.click();
        }
    }

    public void setAllPorttoAuto() {
        for (int devPos = 0; devPos < btnAllSelectAll.size(); devPos++) {
            btnAllSelectAll.get(devPos).click();
            btnAllPortMode.get(devPos).click();
            waitReady();
            selectMode(0);
            btnSave.click();
            waitReady();
            if (btnSave.exists()) {
                btnSave.click();
            }
        }
    }
    
    /**
     * set manager port to auth on sw1 or sw2
     *
     * @param isSW1
     *              true for sw1
     */
    public void setManagerPort(boolean isSW1) {
        if (isSW1) {
            clickPort(WebportalParam.sw1ManagePort, 0);
        } else {
            clickPort(WebportalParam.sw2ManagePort, 1);
        }
        clickBtn(0, isSW1);
        selectMode(1);
        btnSave.click();
        waitReady();
    }
    
    /**
     * Need to call save after setup all
     *
     * @param devName
     *                name must be same as model
     */
    public void setManagerPort(String devName) {
        logger.info(devName);
        getPort(devName, WebportalParam.getSwitchUplinkPort(devName)).click();
        getButton(devName, 0).click();
        selectMode(1);
        btnSave.click();
        waitReady();
    }
    
    /**
     * Need to call save after setup all
     *
     * @param devName
     *                name must be same as model
     */
    public void setManagerPort(String devName, String devModel) {
        logger.info(devName);
        getPort(devName, WebportalParam.getSwitchUplinkPort(devModel)).click();
        getButton(devName, 0).click();
        selectMode(1);
        btnSave.click();
        waitReady();
    }
    
    /**
     * @param devName
     * @param portId
     *                1~n
     * @param option
     *                see selectMode
     */
    public void setOtherPort(String devName, String portId, int option) {
        logger.info(devName + "/" + portId + "/" + option);
        click(getPort(devName, portId));
        getButton(devName, 0).click();
        selectMode(option);
        btnSave.click();
        waitReady();
        if (btnSave.isDisplayed()) {
            btnSave.click();
        }
        waitReady();
    }
    
    /**
     * @param mode
     *             set sw1 port 4/5(lag conf) to 0-auto, 1-auth, 2-unauth
     */
    public void set2PortsMode(int mode) {
        clickPort(WebportalParam.sw1LagPort1, 0);
        clickPort(WebportalParam.sw1LagPort2, 0);
        clickBtn(0, true);
        selectMode(mode);
        btnSave.click();
        waitReady();
        if (btnSave.isDisplayed()) {
            btnSave.click();
        }
        waitReady();
        clickButton(0);
    }
    
    /**
     * enable radius for sw1 or sw2
     *
     * @param isSW1
     *                 true for sw1
     * @param isEnable
     *                 true to enable
     */
    public void enableRadius(boolean isSW1, boolean isEnable) {
        logger.info(isEnable + " / " + isSW1);
        setEnable(isEnable, isSW1);
        setManagerPort(isSW1);
        clickButton(0);
    }
    
    /**
     * disable radius for all switch
     * TODO: cloud has a bug that it will config port first, then disable status, it will make manger port shutdown
     *
     * @param clearPort
     *                  true to set all port with auto mode
     */
    public void disableRadius(boolean clearPort) {
        try {
            gotoPage();
            setEnable(false);
            clickButton(0);
            // after disable we can remove all ports safely
            if (clearPort) {
                setAllPorttoAuto();
                clickButton(0);
            }
        } catch (Throwable e) {
            takess();
            e.printStackTrace();
        }
    }
    
}
