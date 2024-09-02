/**
 *
 */
package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static org.testng.Assert.assertTrue;

import java.util.Map;
import java.util.logging.Logger;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.ButtonElements;
import webportal.webelements.DevicesDashPageElementsNas;

/**
 * @author zheli
 *
 */
public class DevicesDashPageNas extends DevicesDashPageElementsNas {
    Logger logger;

    /**
     *
     */
    public DevicesDashPageNas() {
        WebCheck.checkHrefIcon("/#/devices/dash");
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
    }

    public void addNewDevice(Map<String, String> map) {
        addDevice.click();
        serialNo.sendKeys(map.get("Serial Number"));
        Selenide.sleep(5 * 1000);
        addDeviceBtn.click();
        deviceName.waitUntil(Condition.appear, 30000);
        if (deviceName.isDisplayed()) {
            deviceName.sendKeys(map.get("Device Name"));
            ButtonElements.SAVEBUTTON.click();
        }
    }

    public String showErrorMessage() {
        String messsage = showErrorMsg.getText();
        addDeviceCancel.click();
        return messsage;
    }

    public DevicesSwitchSummaryPage enterDevicesSwitchSummary(String serialNumber) {
        // executeJavaScript("arguments[0].removeAttribute('class')",
        // editModule(serialNumber));
        // Selenide.sleep(3000);
        // editDevice(serialNumber).waitUntil(Condition.visible, 60*1000).click();
        enterDeviceSummary(serialNumber).doubleClick();
        Selenide.sleep(5000);
        return new DevicesSwitchSummaryPage();
    }

    public DevicesSwitchSummaryPage enterDevicesSwitchSummary(String serialNumber, int nas) {
        // executeJavaScript("arguments[0].removeAttribute('class')",
        // editModule(serialNumber));
        // Selenide.sleep(3000);
        // editDevice(serialNumber).waitUntil(Condition.visible, 60*1000).click();
        enterDeviceSummary(serialNumber).doubleClick();
        Selenide.sleep(15000);
        return new DevicesSwitchSummaryPage();
    }

    public DevicesNasSummaryPage enterDevicesNasSummary(String serialNumber) {
        // executeJavaScript("arguments[0].removeAttribute('class')",
        // editModule(serialNumber));
        // Selenide.sleep(3000);
        // editDevice(serialNumber).waitUntil(Condition.visible, 60*1000).click();
        enterDeviceSummary(serialNumber).doubleClick();
        Selenide.sleep(15000);
        return new DevicesNasSummaryPage();
    }

    public void editDeviceName(String serialNumber, String newName) {
        editDeviceNameModule(serialNumber).hover();
        editDeiveNameIcon(serialNumber).click();
        Selenide.sleep(2 * 1000);
        editDeviceNameText(serialNumber).setValue(newName);
        Selenide.sleep(2 * 1000);
        editDeviceNameConfirm(serialNumber).click();
    }

    public String getNasStatus() {
        return deviceStatus.getText();
    }

    public String getErrorInfo() {
        String info = "";
        for (int i = 0; i < 10; i++) {
            info = errorInfo.getText();
            if (info.isEmpty()) {
                Selenide.sleep(1000);
            } else {
                break;
            }
        }

        errorOK.click();
        return info;
    }

    public String getDeviceName(String serialNumber) {
        return devicesName(serialNumber).getText();
    }

    public String getDeviceStatus(String serialNumber) {
        return devicesStatus(serialNumber).getText();
    }

    public void deleteDeviceNo(String serialNumber) {
        executeJavaScript("arguments[0].removeAttribute('class')", editModule(serialNumber));
        Selenide.sleep(3000);
        deleteDevice(serialNumber).waitUntil(Condition.visible, 60 * 1000).click();
        noDontDelete.click();
    }

    public void deleteDeviceYes(String serialNumber) {
        executeJavaScript("arguments[0].removeAttribute('class')", editModule(serialNumber));
        Selenide.sleep(3000);
        deleteDevice(serialNumber).waitUntil(Condition.visible, 60 * 1000).click();
        Selenide.sleep(3000);
        deleteConfirm.click();
        Selenide.sleep(5 * 1000);
    }

    public void openPoEDevice() {
        MyCommonAPIs.waitElement(sDeviceModel);
        boolean devFound = false;
        int devIndex = 0;
        for (SelenideElement se : $$x(sDeviceModel)) {
            String sMode = se.getText();
            logger.info(sMode);
            if (sMode.contains("P")) {
                WebportalParam.sw1IPaddress = $$x(sDeviceIp).get(devIndex).getText();
                devFound = true;
                se.click();
                se.doubleClick();
            }
            devIndex++;
        }
        assertTrue(devFound, "There must be at least one PoE device");
    }

    public String confirmDeviceExist() {
        addDevice.click();
        serialNo.sendKeys(WebportalParam.nas1SerialNo);
        Selenide.sleep(3 * 1000);
        addDeviceBtn.click();
        return addagain.getText();

    }

    public String confirmNoDevice() {
        return nodevice.getText();
    }
}
