/**
 *
 */
package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static org.testng.Assert.assertTrue;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.WiredQuickViewElement;

/**
 * @author Netgear
 */
public class WiredQuickViewPage extends WiredQuickViewElement {
    /**
     *
     */
    Logger logger;
    
    public WiredQuickViewPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefWiredQuickView);
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }
    
    public WiredQuickViewPage(boolean noPage) {
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("initex...");
    }
    
    public void openQuickView() {
        open(URLParam.hrefWiredQuickView, true);
        MyCommonAPIs.waitElement(txtDevicesTable);
    }
    
    public void openDevice(String name) {
        int pos = 0;
        for (SelenideElement se : txtDeviceSwitchName) {
            String sName = se.getText();
            logger.info(String.format("check for: %s", sName));
            if (sName.equals(name)) {
                WebportalParam.updateSwitchOneOption(true, txtDeviceSwitchIp.get(pos).getText());
                se.click();
                se.doubleClick();
                MyCommonAPIs.waitReady();
                break;
            }
            pos++;
        }
    }
    
    /**
     * Open first poe switch
     */
    public void openPOEDevice() {
        if (btnDetailedView.exists()) {
            click(btnDetailedView);
        }
        
        assertTrue(txtPoeSwitchName.size() > 0, "There must be at least one POE Switch");
        openDevice(txtPoeSwitchName.get(0).getText());
    }

    public void enterDeviceYes(String serialNumber) {
        for (int i = 0; i < 2; i++) {
            if (checkswitchIsExist(serialNumber)) {
                logger.info("Enter device.");
                executeJavaScript("arguments[0].removeAttribute('class')", editModule(serialNumber));
                MyCommonAPIs.sleep(3000);
                enterDevice(serialNumber).waitUntil(Condition.visible, 60 * 1000).click();
                waitReady();
                MyCommonAPIs.sleep(5 * 1000);
                break;
            }
            refresh();
        }
    }

    public boolean checkswitchIsExist(String serialNumber) {
        waitReady();
        boolean result = false;
        String sElement = String.format("//td[text()='%s']", serialNumber);
        logger.info("on element: " + sElement);
        if ($x(sElement).exists()) {
            result = true;
            logger.info("SW SN:" + serialNumber + " is existed.");
        }
        return result;
    }

    public int getSwitchUptime(String DeviceserialNo) {
        int upTime = 0;
        String text = getText(String.format(uptime, DeviceserialNo));
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(text);
        upTime = Integer.parseInt(m.replaceAll("").trim());
        logger.info("Uptime number:" + String.valueOf(upTime));
        return upTime;
    }
    
    public boolean lastknowninfoverify() {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if (lastKnownInfo.exists() && lastKnownInfostarsym.exists() && upTimedeviceDashVerify.exists() && upTimedeviceDashVerify1.exists()) {
            result = true;
            logger.info("Device last known information is verified.");
        }
        return result;
    }
  //AddedByPratikforMoveDevice
    public boolean verifyNoDevicesDataonWiredPage() {
        boolean result = false;
        MyCommonAPIs.sleepi(30);
        noDevicesonWiredPage.hover();
        if (noDevicesonWiredPage.exists() && noDevicesonWiredPage1.exists()) {
            logger.info("1st if loop");
            Selenide.executeJavaScript("window.scrollTo(0, document.body.scrollHeight);");
            if (noDevicesonWiredPage2.exists()) {
                result = true;
                logger.info("Devices are not there on wired page");
            }
        }
        return result;
    }
    
    public void editApName(String serialNumber, String newname) {
        if (checkswitchIsExist(serialNumber)) {
            editName(serialNumber).click();
            MyCommonAPIs.sleepi(3);
            inputName(serialNumber).clear();
            MyCommonAPIs.sleepi(3);
            inputName(serialNumber).sendKeys(newname);
            inputnameyes(serialNumber).click();
        } else {
            logger.info("SW isn't existed: " + serialNumber);
        }
    }
    
}
