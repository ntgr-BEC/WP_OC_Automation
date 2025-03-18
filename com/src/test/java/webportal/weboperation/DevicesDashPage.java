package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import org.apache.commons.lang3.RandomStringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import util.MyCommonAPIs;
import util.RunCommand;
import util.SwitchCLIUtils;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.publicstep.WebCheck;
import webportal.webelements.ButtonElements;
import webportal.webelements.DevicesDashPageElements;
import webportal.webelements.WirelessQuickViewElement;

/**
 * @author zheli
 */
public class DevicesDashPage extends DevicesDashPageElements {
    Logger logger;
    String sConn  = "Connected";
    String sConn1 = "Connected (PoE 802.3af Only)";
    String sMang1 = "Unmanaged";
    String rebooting = "Device is rebooting";
    String wiatingToConnect ="Waiting to connect";
    String configurationInProgress  = "Configuration in progress";
    String noofConnectedclient = ""; 

    /**
     *
     */
    public DevicesDashPage() {
        WebCheck.checkHrefIcon(URLParam.hrefDevices);
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
        refresh();
        reloadDeviceList();
    }
    
    public void GoToDevicesDashPage() {
        WebCheck.checkHrefIcon(URLParam.hrefDevices);
    }

    public DevicesDashPage(boolean noPage) {
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("initex...");
    }

    public void gotoPage() {
        WebCheck.checkHrefIcon(URLParam.hrefDevices);
        reloadDeviceList();
    }

    /**
     * sometimes list is empty -- bug
     */
    public void reloadDeviceList() {
        MyCommonAPIs.waitReady();
        if (!$(deviceTable).exists()) {
            WebCheck.checkHrefIcon(URLParam.hrefWiredQuickView);
            MyCommonAPIs.waitReady();
            WebCheck.checkHrefIcon(URLParam.hrefDevices);
        }
        MyCommonAPIs.waitReady();
        try {
            for (int ic = 1; ic <= $$x(sDeviceName).size(); ic++) {
                String sDevName = getText($x(String.format("(%s)[%s]", sDeviceName, ic)));
                String sDevStatus = getText($x(String.format("(%s)[%s]", sDeviceStatus, ic)));
                String sDevModel = getText($x(String.format("(%s)[%s]", sDeviceModel, ic)));
                String sFirmwareVersion = getText($x(String.format("(%s)[%s]", sDeviceFW, ic)));
                String sUptime = getText($x(String.format("(%s)[%s]", sDeviceUptime, ic)));
                String sSWIP = getText($x(String.format("(%s)[%s]", sDeviceIp, ic)));
                String sSWSerialNo = getText($x(String.format("(%s)[%s]", sDeviceSerialNo, ic)));

                sDevStatus = WebportalParam.getNLocText(sDevStatus);
                logger.info(String.format("== Device*Status == %s-%s-%s-%s-%s", sDevName, sDevStatus, sFirmwareVersion, sSWIP, sUptime));
                if (updateDeviceList(sSWSerialNo, sDevStatus, sDevName, sDevModel, sSWIP)) {
                    // it's time to update ip in case of changed
                    if (sSWSerialNo.equals(WebportalParam.sw1serialNo)) {
                        WebportalParam.sw1IPaddress = sSWIP;
                        SwitchCLIUtils.setSwitchIp(false);
                    } else if (WebportalParam.enaTwoSwitchMode && sSWSerialNo.equals(WebportalParam.sw2serialNo)) {
                        WebportalParam.sw2IPaddress = sSWIP;
                    } else if (sSWSerialNo.equals(WebportalParam.br1serialNo)) {
                        WebportalParam.br1IPaddress = sSWIP;
                    } else if (sSWSerialNo.equals(WebportalParam.ob1serialNo)) {
                        // WebportalParam.ob1IPaddress = sSWIP; // configure a fixed ap mode ip before the test
                    } else if (sSWSerialNo.equals(WebportalParam.ap1serialNo)) {
                        WebportalParam.ap1IPaddress = sSWIP;
                    } else if (sSWSerialNo.equals(WebportalParam.ap2serialNo)) {
                        WebportalParam.ap2IPaddress = sSWIP;
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
            takess();
        }

        // to support new UI
        getFieldPos(0);
    }

    public void AddNewDevice(String sNo, String Mac) {
        addNewDevice(sNo, null, Mac);
    }

    public void addNewDevice(String sNo, String sName, String Smac) {
        logger.info("device info: " + sNo + "/" + sName);
        if (devicesName(sNo).exists())
            return;
        clickAddDevice();
        MyCommonAPIs.waitElement(addDeviceBtn);
        serialNo.sendKeys(sNo);
        MyCommonAPIs.sleepi(2);
        addDeviceBtn.click();
        MyCommonAPIs.sleep(4 * 1000);
        macAddress.sendKeys(Smac);
        // serialNo.sendKeys(sNo);
        // macAddress.sendKeys(Smac);

        MyCommonAPIs.sleepi(10);
        // addDeviceBtn.click();
        // MyCommonAPIs.waitElementNot(addDeviceBtn);
        if (sName != null) {
            if (deviceName.exists()) {
                deviceName.clear();
                deviceName.sendKeys(sName);
            }
        }
//        clickBoxLastButton();
        waitElement(addDeviceNextButtton);
        addDeviceNextButtton.click();
        MyCommonAPIs.sleepi(15);
        viewDevices1.click();
        // MyCommonAPIs.waitElement(viewDevices);
    }

    public void addDeviceAirBridge(String[] devNos, String[] devNames) {
        logger.info("device count: " + devNos.length);
        WirelessAirBridgeGroupsPage page = new WirelessAirBridgeGroupsPage();
        clickAddDevice();
        for (int i = 0; i < devNos.length; i++) {
            // set serialNo
            MyCommonAPIs.waitElement(addDeviceBtn);
            serialNo.sendKeys(devNos[i]);
            addDeviceBtn.click();
            MyCommonAPIs.waitElementNot(addDeviceBtn);

            if (1 == getCheckPointStep()) {
                if (page.btnDeviceSelectGroup.exists()) {
                    setCheckPointResult(false);
                } else {
                    setCheckPointResult(true);
                }
                return;
            }
            // check airbridge group for new or existed
            if (!page.btnDeviceSelectGroup.exists()) {
                if (i == 0) {
                    page.btnNextOrCreateNew.click();
                    page.createGroup("ABG-" + RandomStringUtils.randomAlphanumeric(8), false);
                }
            } else {
                if (!deviceName.exists()) {
                    page.btnNextOrCreateNew.click();
                }
            }

            // set device name
            if (deviceName.exists()) {
                if (devNames[i] != null) {
                    deviceName.clear();
                    deviceName.sendKeys(devNames[i]);
                }
            }
            clickBoxLastButton();

            if (2 == getCheckPointStep()) {
                setCheckPointResult(lnLearnMore.exists());
                return;
            }

            // add more device ?
            if ((devNos.length - 1) == i) {
                btnNextStep.click();
                waitReady();
                break;
            } else {
                btnAddMoreDevice.click();
            }
        }
        // view devices
        viewDevices.click();
        waitReady();
    }

    public void enablessh() {
        WebDriver driver = WebDriverRunner.getWebDriver();
        String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL, "t");
        driver.findElement(By.linkText("https://10.208.230.33")).sendKeys(selectLinkOpeninNewTab);
        Selenide.switchTo().window(1);
    }

    public void addNewdummyDevice(Map<String, String> map) {

        for (String ss : map.keySet()) {
            logger.info(ss + ": " + map.get(ss));
            logger.info("Check the print statement");
        }

        open(URLParam.hrefDevices, true);
        refresh();
        MyCommonAPIs.sleepi(10);
        clickAddDevice();
        waitElement(addDeviceBtn);
        MyCommonAPIs.sleepi(10);
        serialNo.sendKeys(map.get("Serial Number1"));
        MyCommonAPIs.sleepi(10);
        addDeviceBtn.click();
        MyCommonAPIs.sleep(4 * 1000);
        macAddress.sendKeys(map.get("MAC Address1"));
        MyCommonAPIs.waitReady();
//        if (errorOK.exists()) {
//            errorOK.click();
//            logger.info("Clicked on error device");
//        }
        ButtonElements.NEXT.click();
        MyCommonAPIs.sleepi(15);
//        clickBoxLastButton();
//        MyCommonAPIs.waitReady();
//        clickBoxLastButton();
//        MyCommonAPIs.sleepi(5);

        if (viewDevices1.exists()) {
            viewDevices1.click();
            logger.info("Clicked on View device");
        }

        else if (ShowErrorMsg.exists()) {
            String ErrorMessage = ShowErrorMsg.getText();
            logger.info(ErrorMessage);
            close.click();
        }

    }

    public boolean addNewdummyDeviceError(Map<String, String> map) {

        boolean result = false;
        for (String ss : map.keySet()) {
            logger.info(ss + ": " + map.get(ss));
            logger.info("Check the print statement");
        }

        open(URLParam.hrefDevices, true);
        clickAddDevice();
        waitElement(addDeviceBtn);
        serialNo.sendKeys(map.get("Serial Number1"));
        MyCommonAPIs.sleepi(10);
        addDeviceBtn.click();
        MyCommonAPIs.sleep(4 * 1000);
        macAddress.sendKeys(map.get("MAC Address1"));

        MyCommonAPIs.waitReady();
        // if (errorOK.exists()) {
        // errorOK.click();
        // logger.info("Clicked on error device");
        // }
        ButtonElements.NEXT.click();
        MyCommonAPIs.sleepi(15);
//        clickBoxLastButton();
//        MyCommonAPIs.sleep(5 * 1000);
//        MyCommonAPIs.waitReady();
//        MyCommonAPIs.sleepi(5);
//        clickBoxLastButton();
//        MyCommonAPIs.sleepi(5);
      

        if (wiresslessDoesnotMatch.exists()) {
            String ErrorMessage = ShowErrorMsg.getText();
            logger.info(ErrorMessage);
            result = true;
            close.click();
        }

        return result;
    }

    public void addNewDevice(Map<String, String> map) {
        MyCommonAPIs.sleepi(10);
        if (devicesName(map.get("Serial Number")).exists())
            return;
        checkAddDevice(map);
    }

    public Boolean checkAddDevice(Map<String, String> map) {
        boolean result = true;
        for (String ss : map.keySet()) {
            logger.info(ss + ": " + map.get(ss));
        }

        clickAddDevice();
        MyCommonAPIs.sleepi(5);
        waitElement(goDeviceBtn);
        serialNo.sendKeys(map.get("Serial Number"));
        MyCommonAPIs.sleepi(5);
        goDeviceBtn.click();
        MyCommonAPIs.sleepi(20);
        //deviceName.sendKeys(map.get("Serial Number"));
        if (map.get("MAC Address1") != null) {
            macAddress.sendKeys(map.get("MAC Address1"));
        } else if (map.get("MAC Address") != null){
            macAddress.sendKeys(map.get("MAC Address"));
        }else {
            macAddress.sendKeys(map.get("Mac Address"));
        }
        //waitElementNot(goDeviceBtn);
        //MyCommonAPIs.waitReady();
        //if (errorOK.exists() && errorOK.isDisplayed()) { // add errorOK check display
            //errorOK.click();
        //}

//        if (deviceName.exists()) {
//            MyCommonAPIs.sleep(3 * 1000);
//            // deviceName.clear();
//            deviceName.sendKeys(Keys.chord(Keys.CONTROL, "a"));
//            MyCommonAPIs.sleepi(1);
//            deviceName.sendKeys(Keys.DELETE);
//            MyCommonAPIs.sleepi(1);
//            logger.info("clear number");
//            deviceName.sendKeys(map.get("Serial Number"));
//            logger.info("enter the name number");
//        }
        waitElement(addDeviceNextButtton);
        addDeviceNextButtton.click();
        MyCommonAPIs.sleepi(15);
        if (addDeviceErrorMsg.exists()) {
            result = false;
            // dismiss box right away
            getText(addDeviceErrorMsg);
            clickBoxFirstButton();
        } else if (!viewDevices.exists()) {
            MyCommonAPIs.waitReady();
            MyCommonAPIs.sleepi(5);
            if (!deviceName.exists()) {
                clickBoxLastButton();
                logger.info("add switch...");
                MyCommonAPIs.sleep(6 * 1000);
            }
            String url = MyCommonAPIs.getCurrentUrl();
            if (url.contains("/setupScreen")) {
                gotoPage();
            } else {
                MyCommonAPIs.waitReady();
                deviceName.waitUntil(Condition.appear, 10000);
                // add by dallas
                MyCommonAPIs.sleep(3 * 1000);
                // deviceName.clear();
                deviceName.sendKeys(Keys.chord(Keys.CONTROL, "a"));
                MyCommonAPIs.sleepi(1);
                deviceName.sendKeys(Keys.DELETE);
                MyCommonAPIs.sleepi(1);
                deviceName.sendKeys(map.get("Device Name"));
                addDeviceNextButtton.click();
//                ButtonElements.NEXT.click();
                MyCommonAPIs.sleepi(10);
                if (addDeviceErrorMsg.exists()) {
                    result = false;
                    // dismiss box right away
                    getText(addDeviceErrorMsg);
                    clickBoxFirstButton();
                } else {
                    waitViewDevices();
                }
            }
        } else {
        waitElement(viewDevices);
            if (viewDevices.exists()) {
                result = true;
                waitViewDevices();
            }
        }
            
        return result;
    }

    public void waitViewDevices() {
        int i = 0;
        while (i < 3) {
            MyCommonAPIs.sleepi(10);
            if (viewDevices.exists()) {
                viewDevices.click();
                break;
            }
            i += 1;
        }
    }

    public String showErrorMessage() {
        String messsage = showErrorMsg.getText();
        clickBoxFirstButton();
        return WebportalParam.getNLocText(messsage);
    }

    public void enterDevice(String serialNumber) {
        // executeJavaScript("arguments[0].removeAttribute('class')",
        // editModule(serialNumber));
        // MyCommonAPIs.sleep(3000);
        if (WebportalParam.enableDebug && $("a[data-target*='.Share']").exists())
            return;

        boolean clicked = false;
        for (int i = 0; i < 2; i++) {
            enterDeviceSummary(serialNumber).hover();
            for (SelenideElement t : $$("p[id*=ptdNomrginDevIddevicesDash] img[src*=edit]")) {
                if (t.isDisplayed()) {
                    t.click();
                    clicked = true;
                    break;
                }
            }
            waitReady();
            if (clicked) {
                break;
            }
        }
        waitReady();
        MyCommonAPIs.sleep(5000);
    }

    public DevicesSwitchSummaryPage enterDevicesSwitchSummary(String serialNumber) {
        enterDevice(serialNumber);
        return new DevicesSwitchSummaryPage();
    }

    public DevicesSwitchSummaryPage enterDevicesSwitchSummary(String serialNumber, boolean nopage) {
        enterDevice(serialNumber);
        return new DevicesSwitchSummaryPage(nopage);
    }

    public DevicesSwitchStatisticsPage enterDevicesSwitchStatistics(String serialNumber) {
        enterDevice(serialNumber);
        return new DevicesSwitchStatisticsPage();
    }

    /**
     * @param serialNumber
     * @param devtype
     *            0 - switch, 1 - br, 2 - nas, 3 - orbi, 4 - ap
     * @return
     */
    public DevicesSwitchSummaryPage enterDevicesSwitchSummary(String serialNumber, int devtype) {
        enterDevice(serialNumber);
        return new DevicesSwitchSummaryPage(devtype);
    }

    /**
     * @param serialNumber
     * @param newName
     * @return will return error iff
     */
    public String editDeviceName(String serialNumber, String newName) {
        String errText = "";
        logger.info(serialNumber + ": change device name to new: " + newName);

        // make sure we have edit it correctly
        for (int i = 0; i < 4; i++) {
            try {
//                if (!editDeviceName.exists()) {
                    System.out.println("aaaaaaaaaaaaaa");
                    editDeviceNameModule(serialNumber).hover();
                    editDeiveNameIcon(serialNumber).click();
//                }
                if (!editDeviceName.getValue().equals(newName)) {
                    System.out.println("bbbbbbbbbbbbbbbbbbbbb");
//                    editDeviceName.clear();
                    editDeviceName1(serialNumber).click();
                    editDeviceName1(serialNumber).clear();                   
                    editDeviceName1(serialNumber).sendKeys(newName);
//                    editDeviceName.sendKeys(Keys.chord(Keys.CONTROL, "a"));
                    System.out.println("11111");
//                    editDeviceName.setValue(newName);
//                    editDeviceName.sendKeys(newName);
                    System.out.println("22222");
                    editDeviceNameConfirm(serialNumber).click();
                    System.out.println("33333");
                    sleep(4, "to wait value is reload");
                    MyCommonAPIs.waitReady();
                } else {
                    editDeviceNameCancel(serialNumber).click();
                }
                break;
            } catch (Throwable e) {
                System.out.println(e);
                // TODO: handle exception
                logger.info("try again in edit");
                refresh();
                reloadDeviceList();
            }
        }

        MyCommonAPIs.waitReady();
        errText = getErrorInfo();
        refresh();
        // make sure page has load new value from device
        if ((errText.length() == 0) || errText.contains("offline")) {
            for (int i = 0; i < 30; i++) {
                sleep(10, "try to avoid old value");
                try {
                    if (getDeviceName(serialNumber).equals(newName)) {
                        break;
                    }
                } catch (Throwable e) {
                    // TODO: handle exception
                    logger.info("try again in view(page will refresh itself)");
                }
                refresh();
            }
        }
        takess("capture after edit name: " + newName);
        return errText;
    }

    public String getErrorInfo() {
        String info = "";
        if (errorInfo.isDisplayed()) {
            takess("check changing device name error");
            info = WebportalParam.getNLocText(errorInfo.getText());
            errorOK.click();
            if (info.toLowerCase().contains("offline")) {
                sleep(300, "wait device online");
            }
        }
        try {
            if (btnCancelEditName.exists()) {
                btnCancelEditName.click();
            }
        } catch (Throwable e) {
            takess("check can not cancel device name error");
        }
        return info;
    }

    /**
     * @param type
     *            1-ap, 2-sw, 3-nas, 4-br
     * @return device serialNumber
     */
    public String getDeviceNo(String modelPrefix) {
        String devName = null;
        waitElement(sDeviceModel);
        waitReady();
        for (SelenideElement se : $$x(sDeviceModel)) {
            if (getText(se).startsWith(modelPrefix)) {
                devName = getText(se);
                break;
            }
        }

        assertTrue(devName != null, "must find one device no for: " + modelPrefix);
        return getTextTable(deviceTable, devName, 3);
    }

    /**
     * @param type
     *            1-ap, 2-sw, 3-nas, 4-br
     * @return devices serialNumber or 0
     */
    public List<String> getDevicesNo(String modelPrefix) {
        String devName = null;
        List<String> devNames = new ArrayList<String>();
        waitElement(sDeviceModel);
        waitReady();
        for (SelenideElement se : $$x(sDeviceModel)) {
            if (getText(se).startsWith(modelPrefix)) {
                devName = getText(se);
                devNames.add(getTextTable(deviceTable, devName, 3));
            }
        }

        return devNames;
    }

    /**
     * @return The first found AP serialNumber
     */
    public String getAPDevice() {
        try {
            return getDeviceNo("WAC");
        } catch (Throwable e) {
            return getDeviceNo("WBC");
        }
    }

    /**
     * @return The first found Switch serialNumber
     */
    public String getSWDevice() {
        try {
            return getDeviceNo("GC");
        } catch (Throwable e) {
            return getDeviceNo("GS");
        }
    }

    /**
     * @return All Switch serialNumber
     */
    public List<String> getSWDevices() {
        try {
            return getDevicesNo("GC");
        } catch (Throwable e) {
            return getDevicesNo("GS");
        }
    }

    /**
     * @return The first found nas serialNumber
     */
    public String getNASDevice() {
        return getDeviceNo("ReadyNAS");
    }

    /**
     * @return The first found br serialNumber
     */
    public String getBRDevice() {
        return getDeviceNo("BR");
    }

    /**
     * @return The first found br serialNumber
     */
    public String getOBDevice() {
        String toRet = null;
        try {
            toRet = getDeviceNo("SRR");
        } catch (Throwable e) {
            try {
                toRet = getDeviceNo("SRS");
            } catch (Throwable ee) {
                toRet = getDeviceNo("RBS");
            }
        }
        return toRet;
    }

    public List<String> getDevicesInfo() {
        return getTextsTable(deviceTable);
    }

    /**
     * @param serialNumber
     * @return return "" if device was not found
     */
    public String getDeviceName(String serialNumber) {
        try {
            return getText(devicesName(serialNumber));
        } catch (Throwable e) {
            takess();
            e.printStackTrace();
            return "";
        }
    }

    public String getDeviceMacInfo(String serialNumber) {
        return getTextTable(deviceTable, serialNumber, deviceMacIndex);
    }

    public String getDeviceMacInfo2(String serialNumber) {
        return $x(String.format(sDeviceMacViaSN, serialNumber)).getText();
    }

    public String getDeviceStatus(String serialNumber) {
        if ((devicesStatus(serialNumber)).exists() && (devicesStatus1(serialNumber)).exists()){
            return WebportalParam.getNLocText(getText(devicesStatus(serialNumber)));
        } else if (devicesStatus(serialNumber).exists()) {
            return WebportalParam.getNLocText(getText(devicesStatus(serialNumber)));
        } else {
            return WebportalParam.getNLocText(getText(devicesStatus1(serialNumber)));
        }
    }

    public String getDeviceStatusunmanged(String serialNumber) {
        System.out.println("2inn");
        String check = WebportalParam.getNLocText(getText(devicesStatusUnmanaged(serialNumber)));
        System.out.println(check);
        return WebportalParam.getNLocText(getText(devicesStatusUnmanaged(serialNumber)));
    }

    /**
     * @param serialNumber
     * @return true for connected, false for other status
     */
    public boolean isDeviceConnected(String serialNumber) {
        refresh();
        if (getDeviceStatus(serialNumber).equals(sConn) || getDeviceStatus(serialNumber).equals(sConn1))
            return true;
        else
            return false;
    }

    public boolean isDeviceUnmanged(String serialNumber) {
        System.out.println("1inn");
        if (getDeviceStatusunmanged(serialNumber).equals(sMang1))
            return true;
        else
            return false;
    }

    public String getDeviceModel(String serialNumber) {
        return getText(devicesModel(serialNumber));
    }

    public String getDeviceIP(String serialNumber) {
        return getText(devicesIP(serialNumber));
    }

    public String getDeviceIP2(String serialNumber) {
        return $x(String.format(sDeviceIpViaSN, serialNumber)).getText();
    }

    public String getDeviceUptime(String serialNumber, boolean getInt) {
        String sDate = "";
        if ((devicesUptime(serialNumber)).exists() && (devicesUptime1(serialNumber)).exists()){
            sDate = getText(devicesUptime(serialNumber));
        } else if (devicesStatus(serialNumber).exists()) {
            sDate = getText(devicesUptime(serialNumber));
        } else {
            sDate = getText(devicesUptime1(serialNumber));
        }
        if (getInt) {
            char[] charfield = sDate.toCharArray();
            String clean = "";
            for (int i = 0; i < charfield.length; i++) {
                if (Character.isDigit(charfield[i])) {
                    clean += charfield[i];
                }
            }
            logger.info(clean);
            return clean;
        }
        return sDate;
    }

    public void deleteDeviceNo(String serialNumber) {
        hoverDevice(serialNumber).hover();
        MyCommonAPIs.sleep(3000);
        deleteDevice(serialNumber).waitUntil(Condition.visible, 60 * 1000).click();
        MyCommonAPIs.sleepi(5);
        deleteConfirm.click();
        MyCommonAPIs.sleepi(5);
    }

    public void deleteDeviceYes(String serialNumber) {
        logger.info("before add device");
        MyCommonAPIs.sleep(3000);
        if (addDevice1.exists()) {
            addDevice1.hover();
        }
        // addDevice1.hover();
        MyCommonAPIs.sleep(3000);
        logger.info("after add device");
        hoverDevice(serialNumber).hover();
        logger.info("after hover device");
        MyCommonAPIs.sleep(3000);
        deleteDevice(serialNumber).waitUntil(Condition.visible, 60 * 1000).click();
        MyCommonAPIs.sleep(3000);
        deleteConfirm.click();
        waitReady();
        MyCommonAPIs.sleep(5 * 1000);
    }

    public DevicesSwitchSummaryPage openSW1() {
        return enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
    }

    public DevicesSwitchSummaryPage openBR1() {
        return enterDevicesSwitchSummary(WebportalParam.br1serialNo, 1);
    }

    public DevicesSwitchSummaryPage openOB1() {
        return enterDevicesSwitchSummary(WebportalParam.ob1serialNo, 3);
    }

    public DevicesSwitchSummaryPage openOB2() {
        return enterDevicesSwitchSummary(WebportalParam.ob2serialNo, 3);
    }

    /**
     * Open sw1 if it's PoE, open sw2 if it's PoE while sw1 is not PoE, open any PoE if sw1 & sw2 are not PoE
     */
    public void openPoEDevice() {
        if (WebportalParam.sw1Model.contains("P")) {
            enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
        } else if (WebportalParam.enaTwoSwitchMode && WebportalParam.sw2Model.contains("P")) {
            enterDevicesSwitchSummary(WebportalParam.sw2serialNo);
            WebportalParam.updateSwitchOneOption(true, WebportalParam.sw2IPaddress);
        } else {
            // FIXME: open first PoE device, it should be switch right?
            MyCommonAPIs.waitElement(sDeviceModel);
            MyCommonAPIs.sleepi(5);
            boolean devFound = false;
            int devIndex = 0;
            for (SelenideElement se : $$x(sDeviceModel)) {
                String sMode = se.getText();
                logger.info(sMode);
                if (sMode.contains("P")) {
                    WebportalParam.updateSwitchOneOption(true, $$x(sDeviceIp).get(devIndex).getText());
                    devFound = true;
                    se.click();
                    se.doubleClick();
                    break;
                }
                devIndex++;
            }
            assertTrue(devFound, "There must be at least one PoE device");
        }
        waitReady();
    }

    /**
     * @param bySerialNo
     *            true by serial no, false by mode
     */
    public void openBRDevice(boolean bySerialNo) {
        String sele = sDeviceSerialNo;
        String smatch = WebportalParam.br1serialNo;
        if (!bySerialNo) {
            sele = sDeviceModel;
            smatch = "BR";
        }
        MyCommonAPIs.waitElement(sele);
        MyCommonAPIs.sleepi(5);
        boolean devFound = false;
        for (SelenideElement se : $$x(sele)) {
            String sSerialNo = se.getText();
            logger.info(sSerialNo);
            if (sSerialNo.contains(smatch)) {
                devFound = true;
                se.click();
                se.doubleClick();
                break;
            }
        }
        assertTrue(devFound, "There must be at least one BR device");
        waitReady();
    }

    /**
     * @param sNo
     */
    public void rebootDevice(String sNo) {
        for (int i = 0; i < 2; i++) {
            if ($x(sDeviceSerialNo).exists()) {
                break;
            }
            refresh();
        }
        for (SelenideElement se : $$x(sDeviceSerialNo)) {
            String sSerialNo = getText(se);
            if (getText(se).equalsIgnoreCase(sNo)) {
                if (addDevice1.exists()) {
                    addDevice1.hover();
                }
                se.hover();
                sleepi(1);
                for (SelenideElement se1 : $$(".rebootDeviceIcon")) {
                    if (se1.isDisplayed()) {
                        logger.info(sNo);
                        se1.click();
                        sleepi(4);
                        rebootconfirm.click();
                        clickBoxLastButton();
                        MyCommonAPIs.sleepi(60);
                        break;
                    }
                }
                break;
            }
        }
    }

    public void NorebootDevice(String sNo) {
        for (int i = 0; i < 2; i++) {
            if ($x(sDeviceSerialNo).exists()) {
                break;
            }
            refresh();
        }
        for (SelenideElement se : $$x(sDeviceSerialNo)) {
            String sSerialNo = getText(se);
            if (getText(se).equalsIgnoreCase(sNo)) {
                if (addDevice1.exists()) {
                    addDevice1.hover();
                }
                se.hover();
                sleepi(1);
                for (SelenideElement se1 : $$(".rebootDeviceIcon")) {
                    if (se1.isDisplayed()) {
                        logger.info(sNo);
                        se1.click();
                        sleepi(4);
                        break;
                    }
                }
                break;
            }
        }
    }
    
    /**
     * reboot switch if telnet is failed
     */
    public void rebootSwitchDevice() {
        boolean needsleep = false;
        if (MyCommonAPIs.getCmdOutput("show clock", false).equals("error")) {
            rebootDevice(WebportalParam.sw1serialNo);
            needsleep = true;
        }
        if (WebportalParam.enaTwoSwitchMode && MyCommonAPIs.getCmdOutput("show clock", true).equals("error")) {
            rebootDevice(WebportalParam.sw2serialNo);
            needsleep = true;
        }
        if (needsleep) {
            sleep(120, "sw reboot");
        }
    }

    /**
     * wait all devices online up to 10m
     */
    public void waitAllSwitchDevicesConnected() {
        logger.info("checking start");
        MyCommonAPIs.timerStart(10 * 60);
        boolean timeout = true;
        while (MyCommonAPIs.timerRunning()) {
            if (getDeviceStatus(WebportalParam.sw1serialNo).equals(sConn)
                    && (!WebportalParam.enaTwoSwitchMode || getDeviceStatus(WebportalParam.sw2serialNo).equals(sConn))) {
                timeout = false;
                break;
            }

            refresh();
            sleep(15, "next.");
        }
        if (timeout) {
            logger.info("checking timeout");
            takess();
        }
    }

    /**
     * wait one devices re-online up to 15m
     *
     * @return true means device is re-online
     */
    public boolean waitDevicesReConnected(String sNo) {
        String sCur = null;
        MyCommonAPIs.sleepi(120);
        MyCommonAPIs.timerStart(30 * 30);
        boolean timeout = true;
        while (MyCommonAPIs.timerRunning()) {
            try {
                refresh();
                sleep(10);
                if (isDeviceConnected(sNo)) { // PRJCBUGEN-23605
                    if (sCur == null) {
                        sCur = getDeviceUptime(sNo, false);
                        logger.info("checking start123: " + sCur);
                        sleep(30, "sync.");
                        new RunCommand().enableSSH4AP();
                    }
                    refresh();
                    if (!getDeviceUptime(sNo, false).equals(sCur)) {
                        timeout = false;
                        break;

                    }
                } else {
                    sCur = null;
                }
            } catch (Throwable e) {
                e.printStackTrace();
                takess();
                refresh();
            }
            sleep(30, "next."); // change from 15 to 30, frequent refresh would lead to block with error page
        }

        if (timeout) {
            logger.info("checking timeout");
            takess();
        }
        return !timeout;
    }
    
    public boolean waitDevicesReConnectedPR(String sNo) {
        String sCur = null;
        MyCommonAPIs.sleepi(30);
        MyCommonAPIs.timerStart(30 * 30);
        boolean timeout = true;
        while (MyCommonAPIs.timerRunning()) {
            try {
                refresh();
                sleep(10);
                if (isDeviceConnected(sNo)) { // PRJCBUGEN-23605
                    if (sCur == null) {
                        sCur = getDeviceUptime(sNo, false);
                        logger.info("checking start123: " + sCur);
                        sleep(30, "sync.");                        
                    }
                    refresh();
                    if (!getDeviceUptime(sNo, false).equals(sCur)) {
                        timeout = false;
                        break;

                    }
                } else {
                    sCur = null;
                }
            } catch (Throwable e) {
                e.printStackTrace();
                takess();
                refresh();
            }
            sleep(30, "next."); // change from 15 to 30, frequent refresh would lead to block with error page
        }

        if (timeout) {
            logger.info("checking timeout");
            takess();
        }
        return !timeout;
    }

    /**
     * wait one devices re-online up to 15m after reboot
     */
    public void waitDevicesReboot(String sNo) {
        boolean timeout = true;
        MyCommonAPIs.timerStart(15 * 60);
        int devUptimePre = Integer.parseInt(getDeviceUptime(sNo, true));
        rebootDevice(sNo);
        while (MyCommonAPIs.timerRunning()) {
            int devUptimeAfter = Integer.parseInt(getDeviceUptime(sNo, true));
            if (devUptimeAfter < devUptimePre) {
                timeout = false;
                break;
            }
            sleep(10, "next.");
            refresh();
            reloadDeviceList();
        }

        assertTrue(!timeout, "Device should be rebooted");
    }

    public void waitDevicesOnline(String sNo) {
        boolean timeout = true;
        MyCommonAPIs.timerStart(7 * 60);
        while (MyCommonAPIs.timerRunning()) {
            try {
                if (isDeviceConnected(sNo)) {
                    timeout = false;
                    break;
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
            sleep(10, "go next.");
            refresh();
            reloadDeviceList();
        }

        assertTrue(!timeout, "Device should be online in time");
    }

    // add by bingke.xue at 2019/5/30
    public boolean waitDevicesIPvalid(String sNo) {
        String sCur = null;
        MyCommonAPIs.timerStart(20 * 60);
        boolean timeout = true;
        while (MyCommonAPIs.timerRunning()) {
            try {
                if (sCur == null) {
                    sCur = getDeviceIP(sNo);
                    logger.info("checking start: " + sCur);
                    sleep(30, "sync.");
                }
                refresh();
                if (!getDeviceIP(sNo).equals("NA") && !getDeviceIP(sNo).equals("0.0.0.0") && !getDeviceIP(sNo).equals("-")) {
                    timeout = false;
                    break;
                }
            } catch (Throwable e) {
                e.printStackTrace();
                takess();
                refresh();
            }

            sleep(15, "next.");
        }

        if (timeout) {
            logger.info("checking timeout");
            takess();
        }
        return !timeout;
    }

    public void enterDeviceViadeviceSceen(String serialNumber) {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(false);
        gotoAllLocations();
        WebCheck.checkHrefIcon(URLParam.hrefDevices);
        new WirelessQuickViewPage(false).editName(serialNumber).doubleClick();

    }

    public void checkDeviceInAdminAccount() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(false);
        gotoAllLocations();
        WebCheck.checkHrefIcon(URLParam.hrefDevices);
        if (getDeviceName(WebportalParam.ap1serialNo).equals("")) {
            Map<String, String> devInfo = new HashMap<String, String>();
            devInfo.put("Serial Number", WebportalParam.ap1serialNo);
            devInfo.put("Device Name", WebportalParam.ap1deveiceName);
            devInfo.put("MAC Address1", WebportalParam.ap1macaddress);
            if (checkAddDevice(devInfo)) {
            } else {
                UserManage userManage = new UserManage();
                userManage.logout();
                webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
                new AccountPage().enterLocation(WebportalParam.location1);

                deleteDeviceYes(WebportalParam.ap1serialNo);
                userManage.logout();
                webportalLoginPage.defaultLogin();

                new AccountPage().enterLocation(WebportalParam.location1);
                addNewDevice(devInfo);
            }
            reloadDeviceList();
            waitDevicesReConnected(WebportalParam.ap1serialNo);
            enableApSsh();
        }
    }

    // Added by Tejeshwini K V

    public void checkDeviceInAdminAccountBR() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(false);
        gotoAllLocations();
        WebCheck.checkHrefIcon(URLParam.hrefDevices);
        if (getDeviceName(WebportalParam.br1serialNo).equals("")) {
            Map<String, String> devInfo = new HashMap<String, String>();
            devInfo.put("Serial Number", WebportalParam.br1serialNo);
            devInfo.put("Device Name", WebportalParam.br1deveiceName);
            devInfo.put("MAC Address", WebportalParam.br1deveiceMac);
            if (checkAddDevice(devInfo)) {
                System.out.println("Sumanta");
            } else {
                UserManage userManage = new UserManage();
                userManage.logout();
                webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
                new AccountPage().enterLocation(WebportalParam.location1);

                deleteDeviceYes(WebportalParam.br1serialNo);
                userManage.logout();
                webportalLoginPage.defaultLogin();

                new AccountPage().enterLocation(WebportalParam.location1);
                addNewDevice(devInfo);
            }
            reloadDeviceList();
            waitDevicesReConnected(WebportalParam.br1serialNo);
        }
    }

    // Added by Tejeshwini K V

    public void checkDeviceInAdminAccountorbi() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(false);
        gotoAllLocations();
        WebCheck.checkHrefIcon(URLParam.hrefDevices);
        if (getDeviceName(WebportalParam.ob1serialNo).equals("")) {
            Map<String, String> devInfo = new HashMap<String, String>();
            devInfo.put("Serial Number", WebportalParam.ob1serialNo);
            devInfo.put("Device Name", WebportalParam.ob1deveiceName);
            devInfo.put("MAC Address1", WebportalParam.ob1deveiceMac);
            if (checkAddDevice(devInfo)) {
            } else {
                UserManage userManage = new UserManage();
                userManage.logout();
                webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
                new AccountPage().enterLocation(WebportalParam.location2);

                deleteDeviceYes(WebportalParam.ob1serialNo);
                userManage.logout();
                webportalLoginPage.defaultLogin();

                new AccountPage().enterLocation(WebportalParam.location2);
                addNewDevice(devInfo);
            }
            reloadDeviceList();
            waitDevicesReConnected(WebportalParam.ob1serialNo);
        }
    }

    // Added by Tejeshwini

    public void checkDeviceInAdminAccountswitch() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(false);
        gotoAllLocations();
        WebCheck.checkHrefIcon(URLParam.hrefDevices);
        if (getDeviceName(WebportalParam.sw1serialNo).equals("")) {
            Map<String, String> devInfo = new HashMap<String, String>();
            devInfo.put("Serial Number", WebportalParam.sw1serialNo);
            devInfo.put("Device Name", WebportalParam.sw1deveiceName);
            devInfo.put("MAC Address1", WebportalParam.sw1MacAddress);
            if (checkAddDevice(devInfo)) {
            } else {
                UserManage userManage = new UserManage();
                userManage.logout();
                webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
                new AccountPage().enterLocation(WebportalParam.location1);

                deleteDeviceYes(WebportalParam.sw1serialNo);
                userManage.logout();
                webportalLoginPage.defaultLogin();

                new AccountPage().enterLocation(WebportalParam.location1);
                addNewDevice(devInfo);
            }
            reloadDeviceList();
            waitDevicesReConnected(WebportalParam.sw1serialNo);
        }
    }

    public void checkDeviceInNormalAccount(String account) {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(false);
        gotoAllLocations();
        new AccountPage().enterLocation(WebportalParam.location1);
        if (getDeviceName(WebportalParam.ap1serialNo).equals("")) {
            Map<String, String> devInfo = new HashMap<String, String>();
            devInfo.put("Serial Number", WebportalParam.ap1serialNo);
            devInfo.put("Device Name", WebportalParam.ap1deveiceName);
            devInfo.put("MAC Address1", WebportalParam.ap1macaddress);
            if (checkAddDevice(devInfo)) {
            } else {
                UserManage userManage = new UserManage();
                userManage.logout();
                webportalLoginPage.defaultLogin();
                new AccountPage().enterLocation(WebportalParam.location1);

                deleteDeviceYes(WebportalParam.ap1serialNo);
                userManage.logout();
                if (account.equals("admin")) {
                    webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
                } else if (account.equals("manager")) {
                    webportalLoginPage.loginByUserPassword(WebportalParam.managerName, WebportalParam.managerPassword);
                }

                new AccountPage().enterLocation(WebportalParam.location1);
                addNewDevice(devInfo);
            }
            reloadDeviceList();
            waitDevicesReConnected(WebportalParam.ap1serialNo);
            enableApSsh();
        }
    }
    
    
    public void checkDeviceInNormalAccountGEN(String account, Map<String, String> map) {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(false);
        gotoAllLocations();
        new AccountPage().enterLocation(WebportalParam.location1);
        if (getDeviceName(map.get("Serial Number")).equals("")) {
            Map<String, String> devInfo = new HashMap<String, String>();
            devInfo.put("Serial Number", map.get("Serial Number"));
            devInfo.put("Device Name", map.get("Device Name"));
            devInfo.put("MAC Address1", map.get("MAC Address1"));
            if (checkAddDevice(devInfo)) {
            } else {
                UserManage userManage = new UserManage();
                userManage.logout();
                webportalLoginPage.defaultLogin();
                new AccountPage().enterLocation(WebportalParam.location1);

                deleteDeviceYes(map.get("Serial Number"));
                userManage.logout();
                if (account.equals("admin")) {
                    webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
                } else if (account.equals("manager")) {
                    webportalLoginPage.loginByUserPassword(WebportalParam.managerName, WebportalParam.managerPassword);
                }

                new AccountPage().enterLocation(WebportalParam.location1);
                addNewDevice(devInfo);
            }
            reloadDeviceList();
            waitDevicesReConnectedPR(map.get("Serial Number"));
            enableApSsh();
        }
    }

    // add by sjena at 2020/02/20
    public void checkDeviceInNormalAccountSwitch(String account) {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(false);
        gotoAllLocations();
        new AccountPage().enterLocation(WebportalParam.location1);
        if (getDeviceName(WebportalParam.sw1serialNo).equals("")) {
            Map<String, String> devInfo = new HashMap<String, String>();
            devInfo.put("Serial Number", WebportalParam.sw1serialNo);
            devInfo.put("Device Name", WebportalParam.sw1deveiceName);
            if (checkAddDevice(devInfo)) {
            } else {
                UserManage userManage = new UserManage();
                userManage.logout();
                webportalLoginPage.defaultLogin();
                new AccountPage().enterLocation(WebportalParam.location1);

                deleteDeviceYes(WebportalParam.sw1serialNo);
                userManage.logout();
                if (account.equals("admin")) {
                    webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
                } else if (account.equals("manager")) {
                    webportalLoginPage.loginByUserPassword(WebportalParam.managerName, WebportalParam.managerPassword);
                }

                new AccountPage().enterLocation(WebportalParam.location1);
                addNewDevice(devInfo);
            }
            reloadDeviceList();
            waitDevicesReConnected(WebportalParam.sw1serialNo);
        }
    }

    public void checkDeviceInNormalAccountBR(String account) {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(false);
        gotoAllLocations();
        new AccountPage().enterLocation(WebportalParam.location1);
        if (getDeviceName(WebportalParam.br1serialNo).equals("")) {
            Map<String, String> devInfo = new HashMap<String, String>();
            devInfo.put("Serial Number", WebportalParam.br1serialNo);
            devInfo.put("Device Name", WebportalParam.br1serialNo);
            if (checkAddDevice(devInfo)) {
            } else {
                UserManage userManage = new UserManage();
                userManage.logout();
                webportalLoginPage.defaultLogin();
                new AccountPage().enterLocation(WebportalParam.location1);

                deleteDeviceYes(WebportalParam.br1serialNo);
                userManage.logout();
                if (account.equals("admin")) {
                    webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
                } else if (account.equals("manager")) {
                    webportalLoginPage.loginByUserPassword(WebportalParam.managerName, WebportalParam.managerPassword);
                }

                new AccountPage().enterLocation(WebportalParam.location1);
                addNewDevice(devInfo);
            }
            reloadDeviceList();
            waitDevicesReConnected(WebportalParam.br1serialNo);
        }
    }

    public void checkDeviceInNormalAccountOrbi(String account) {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(false);
        gotoAllLocations();
        new AccountPage().enterLocation(WebportalParam.location2);
        if (getDeviceName(WebportalParam.ob1serialNo).equals("")) {
            Map<String, String> devInfo = new HashMap<String, String>();
            devInfo.put("Serial Number", WebportalParam.ob1serialNo);
            devInfo.put("Device Name", WebportalParam.ob1deveiceName);
            if (checkAddDevice(devInfo)) {
            } else {
                UserManage userManage = new UserManage();
                userManage.logout();
                webportalLoginPage.defaultLogin();
                new AccountPage().enterLocation(WebportalParam.location2);

                deleteDeviceYes(WebportalParam.ob1serialNo);
                userManage.logout();
                if (account.equals("admin")) {
                    webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
                } else if (account.equals("manager")) {
                    webportalLoginPage.loginByUserPassword(WebportalParam.managerName, WebportalParam.managerPassword);
                }

                new AccountPage().enterLocation(WebportalParam.location2);
                addNewDevice(devInfo);
            }
            reloadDeviceList();
            waitDevicesReConnected(WebportalParam.ob1serialNo);
        }
    }
    // add by bingke.xue at 2019/07/03

    public boolean ownerAccountHaveNoAddDeviceAccess() {
        boolean result = false;
        if (!addDevice.exists()) {
            result = true;
        }
        return result;
    }

    // add by dallas.zhao at 2019/12/2
    public void filterSelectDevice(String[] devices) {
        refresh();
        filtericon.click();
        MyCommonAPIs.sleepi(3);
        for (String device : devices) {
            switch (device) {
            case ("switch"):
                filterswitchicon.click();
                MyCommonAPIs.sleepi(1);
                break;
            case ("nas"):
                filternasicon.click();
                MyCommonAPIs.sleepi(1);
                break;
            case ("ap"):
                filterapicon.click();
                MyCommonAPIs.sleepi(1);
                break;
            }
        }
        filterapplybutton.click();
    }

    public void searchDevices(String number) {
        refresh();
        search.click();
        MyCommonAPIs.sleepi(3);
        inputsearchtext.setValue(number);
        searchClick.click();
        MyCommonAPIs.sleepi(10);
    }

    public void clickSortDevicesName() {
        refresh();
        sortTableName.click();
        MyCommonAPIs.sleepi(3);
    }

    public boolean checkSortDevicesName(String name1, String name2) {
        boolean result = false;
        String devicesNameTable = "//tbody[@id='tbdydevicesDash']/";
        if (sortTableName.getAttribute("class").contains("sorting_asc")) {
            if (getText($x(devicesNameTable + "tr[1]/td[1]")).equals(name1) && getText($x(devicesNameTable + "tr[2]/td[1]")).equals(name2)) {
                result = true;
                logger.info("Sort is correct.");
            }
        } else if (sortTableName.getAttribute("class").contains("sorting_desc")) {
            if (getText($x(devicesNameTable + "tr[1]/td[1]")).equals(name2) && getText($x(devicesNameTable + "tr[2]/td[1]")).equals(name1)) {
                result = true;
                logger.info("Sort is correct.");
            }
        }
        return result;
    }

    public void checkDutInNormalAccount(String account, String serialNo, String deveiceName, String Macaddress) {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(false);
        gotoAllLocations();
        new AccountPage().enterLocation(WebportalParam.location1);
        if (getDeviceName(serialNo).equals("")) {
            Map<String, String> devInfo = new HashMap<String, String>();
            devInfo.put("Serial Number", serialNo);
            devInfo.put("Device Name", deveiceName);
            devInfo.put("MAC Address", Macaddress);
            if (checkAddDevice(devInfo)) {
            } else {
                UserManage userManage = new UserManage();
                userManage.logout();
                webportalLoginPage.defaultLogin();
                new AccountPage().enterLocation(WebportalParam.location1);

                deleteDeviceYes(serialNo);
                userManage.logout();
                if (account.equals("admin")) {
                    webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
                } else if (account.equals("manager")) {
                    webportalLoginPage.loginByUserPassword(WebportalParam.managerName, WebportalParam.managerPassword);
                }

                new AccountPage().enterLocation(WebportalParam.location1);
                addNewDevice(devInfo);
            }
            reloadDeviceList();
            waitDevicesReConnected(serialNo);
        }
    }

    public void checkDutInAdminAccount(String serialNo, String deveiceName, String deveiceMac) {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(false);
        gotoAllLocations();
        WebCheck.checkHrefIcon(URLParam.hrefDevices);
        if (getDeviceName(serialNo).equals("")) {
            Map<String, String> devInfo = new HashMap<String, String>();
            devInfo.put("Serial Number", serialNo);
            devInfo.put("Device Name", deveiceName);
            devInfo.put("MAC Address", deveiceMac);
            if (checkAddDevice(devInfo)) {
            } else {
                UserManage userManage = new UserManage();
                userManage.logout();
                webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
                new AccountPage().enterLocation(WebportalParam.location1);

                deleteDeviceYes(serialNo);
                userManage.logout();
                webportalLoginPage.defaultLogin();

                new AccountPage().enterLocation(WebportalParam.location1);
                addNewDevice(devInfo);
            }
            reloadDeviceList();
            waitDevicesReConnected(serialNo);
        }
    }

    public void checkDutInProAccount(String account, String serialNo, String deveiceName) {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(false);
        gotoAllLocations();
        new AccountPage().enterLocation(WebportalParam.location1);
        if (getDeviceName(serialNo).equals("")) {
            Map<String, String> devInfo = new HashMap<String, String>();
            devInfo.put("Serial Number", serialNo);
            devInfo.put("Device Name", deveiceName);
            if (checkAddDevice(devInfo)) {
            } else {
                UserManage userManage = new UserManage();
                userManage.logout();

                if (account.equals("admin")) {
                    webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
                } else if (account.equals("manager")) {
                    webportalLoginPage.loginByUserPassword(WebportalParam.managerName, WebportalParam.managerPassword);
                }
                new AccountPage().enterLocation(WebportalParam.location1);

                deleteDeviceYes(serialNo);
                userManage.logout();

                webportalLoginPage.defaultLogin();
                new AccountPage().enterLocation(WebportalParam.location1);
                addNewDevice(devInfo);
            }
            reloadDeviceList();
            waitDevicesReConnected(serialNo);
        }
    }

    public String checkNumberOfDevices() {

        String result = "No device exits or no device exits";
        new MyCommonAPIs().open(URLParam.hrefaccount, true);

        if (DeviceCount.exists()) {
            if (getText(DeviceCount).equals("1")) {
                result = "Onedevice";
            }

            if (getText(DeviceCount).equals("2")) {
                result = "Twodevice";
            }
            if (getText(DeviceCount).equals("3")) {
                result = "Threedevice";

            }
            if (getText(DeviceCount).equals("4")) {
                result = "Fourdevice";

            }
            if (getText(DeviceCount).equals("5")) {
                result = "Fivedevice";
            }
        }
        System.out.println(result);
        return result;

    }
    

    public String checkNumberOfDevicesOrganization() {
        MyCommonAPIs.sleepi(20);
        String result = "No device exits or no device exits";
        new MyCommonAPIs().open(URLParam.hreforganization, true);
        MyCommonAPIs.sleepi(20);
//        String DeviceCount = DeviceCountOrg.getText();
//        System.out.println(DeviceCount);
        if (DeviceCountOrg.exists()) {
            if (getText(DeviceCountOrg).equals("1")) {
                result = "Onedevice";
            }

            if (getText(DeviceCountOrg).equals("2")) {
                result = "Twodevice";
            }
            if (getText(DeviceCountOrg).equals("3")) {
                result = "Threedevice";

            }
            if (getText(DeviceCountOrg).equals("4")) {
                result = "Fourdevice";

            }
            if (getText(DeviceCountOrg).equals("5")) {
                result = "Fivedevice";

            }
            if (getText(DeviceCountOrg).equals("1000")) {
                result = "OneThousand";

            }
            System.out.println(result);
        }else {
            
            String name = getText(OrgName);
            System.out.println(OrgName);
            String rowindex=dropdownOrganizationElementNew(name).getAttribute("aria-rowindex");
            
            if (getText(offlineDevicecount(rowindex)).equals("1")) {
                result = "Onedevice";
            }

            if (getText(offlineDevicecount(rowindex)).equals("2")) {
                result = "Twodevice";
            }
            if (getText(offlineDevicecount(rowindex)).equals("3")) {
                result = "Threedevice";

            }
            if (getText(offlineDevicecount(rowindex)).equals("4")) {
                result = "Fourdevice";

            }
            if (getText(offlineDevicecount(rowindex)).equals("5")) {
                result = "Fivedevice";

            }
            if (getText(offlineDevicecount(rowindex)).equals("1000")) {
                result = "OneThousand";

            }
            System.out.println(result);
        
            ;
        }
        return result;

    }

    /**
     * Must be called in dashboard page
     */
    public void checkDUT4InstantMesh() {
        assertTrue(getDeviceCount() > 1, "At least 2 APs are added");

        boolean checkAPMeshRoot = false;
        boolean checkAPMeshExtender = false;
        for (String s : getDevicesInfo()) {
            if (s.contains(WebportalParam.getLocText("im5.6Keys", "root"))) {
                checkAPMeshRoot = true;
            }
            if (s.contains(WebportalParam.getLocText("im5.6Keys", "satellite"))) {
                checkAPMeshExtender = true;
            }
        }
        assertTrue(checkAPMeshRoot, "At least one AP is root");
        assertTrue(checkAPMeshExtender, "At least one AP is Extender");
    }

    public String checkDevicesCredits(Map<String, String> map) {
        String text = "";
        clickAddDevice();
        waitElement(addDeviceBtn);
        serialNo.sendKeys(map.get("Serial Number"));
        MyCommonAPIs.sleepi(3);
        addDeviceBtn.click();
        MyCommonAPIs.sleepi(3);
        macAddress.sendKeys(map.get("MAC Address"));
        MyCommonAPIs.sleepi(5);
//        waitElementNot(addDeviceBtn);
//        MyCommonAPIs.waitReady();
        next.click();
        MyCommonAPIs.sleepi(5);
        if (errorOK.exists()) {
            errorOK.click();
        }
        if (deviceName.exists()) {
            MyCommonAPIs.sleep(3 * 1000);
            deviceName.clear();
            deviceName.sendKeys(map.get("Device Name"));
        }
        clickBoxLastButton();
        MyCommonAPIs.sleep(5 * 1000);
        MyCommonAPIs.waitReady();
        clickBoxLastButton();
        MyCommonAPIs.sleepi(15);
        if (addDeviceErrorMsg.exists()) {
            text = getText(addDeviceErrorMsg);
            clickBoxFirstButton();
        }
        return text;
    }

    public void enableApSsh() {
        new RunCommand().enableSSHByVbs();
    }

    public void addNewDeviceforDeleteNotification(Map<String, String> map) {

        for (String ss : map.keySet()) {
            logger.info(ss + ": " + map.get(ss));
            logger.info("Check the print statement");
        }

        open(URLParam.hrefDevices, true);
        clickAddDevice();
        waitElement(addDeviceBtn);
        serialNo.sendKeys(map.get("Serial Number1"));
        MyCommonAPIs.sleepi(10);
        addDeviceBtn.click();
        MyCommonAPIs.sleep(4 * 1000);
        macAddress.sendKeys(map.get("MAC Address"));
        MyCommonAPIs.waitReady();
        if (errorOK.exists()) {
            errorOK.click();
            logger.info("Clicked on error device");
        }
        clickBoxLastButton();
        MyCommonAPIs.waitReady();
        clickBoxLastButton();
        MyCommonAPIs.sleepi(5);

        if (viewDevices1.exists()) {
            viewDevices1.click();
            logger.info("Clicked on View device");
        }

        else if (ShowErrorMsg.exists()) {
            String ErrorMessage = ShowErrorMsg.getText();
            logger.info(ErrorMessage);
            close.click();
        }

    }

    // Code Added for Seach filter Choose Devices by Pratik

    public boolean searchFilterNAS() {

        boolean result = false;

        MyCommonAPIs.sleepi(5);
        devicesFilter.click();
        MyCommonAPIs.sleepi(5);

        if (filterDeviceVerification.exists()) {
            logger.info("Entered in to Devices filter");
            selectNAS.click();
            searchFilterApplyButton.click();
            logger.info("NAS is selected from Devices filter");
            MyCommonAPIs.sleepi(1);
            if (noDeviceAvailable.exists()) {
                result = true;
                logger.info("NAS Filter applied sucessfully because NAS is not added into the acount showing no devices available.");
            }
            MyCommonAPIs.sleepi(5);
            devicesFilter.click();
            MyCommonAPIs.sleepi(5);
            selectNAS.click();
            MyCommonAPIs.sleepi(1);
            searchFilterApplyButton.click();
            MyCommonAPIs.sleepi(1);
            devicesFilter.click();
            MyCommonAPIs.sleepi(1);

        }

        return result;

    }

    public int checkNumberOfDevicesOrganization11() {

        int result = 0;
        new MyCommonAPIs().open(URLParam.hreforganization, true);
        MyCommonAPIs.sleepi(20);
        String DeviceCount = DeviceCountOrg.getText();
        System.out.println(DeviceCount);
        if (DeviceCountOrg.exists()) {
            if (getText(DeviceCountOrg).equals("1")) {
                result = 1;
            }
            if (getText(DeviceCountOrg).equals("2")) {
                result = 2;
            }
            if (getText(DeviceCountOrg).equals("3")) {
                result = 3;
            }
            if (getText(DeviceCountOrg).equals("4")) {
                result = 4;
            }
            if (getText(DeviceCountOrg).equals("5")) {
                result = 5;
            }
            if (getText(DeviceCountOrg).equals("6")) {
                result = 6;
            }
            if (getText(DeviceCountOrg).equals("7")) {
                result = 7;
            }
            if (getText(DeviceCountOrg).equals("8")) {
                result = 8;
            }
            if (getText(DeviceCountOrg).equals("9")) {
                result = 9;
            }
            if (getText(DeviceCountOrg).equals("10")) {
                result = 10;
            }
            if (getText(DeviceCountOrg).equals("11")) {
                result = 11;
            }
            if (getText(DeviceCountOrg).equals("12")) {
                result = 12;
            }
            System.out.println(result);
        }
        return result;
    }

    public String checkNumberOfDevicesOrganization1() {
        String result = "No device exits or no device exits";
        new MyCommonAPIs().open(URLParam.hreforganization, true);
        MyCommonAPIs.sleepi(20);
        String DeviceCount = DeviceCountOrg.getText();
        System.out.println(DeviceCount);
        if (DeviceCountOrg.exists()) {
            if (getText(DeviceCountOrg).equals("1")) {
                result = "Onedevice";
            }

            if (getText(DeviceCountOrg).equals("2")) {
                result = "Twodevice";
            }
            if (getText(DeviceCountOrg).equals("3")) {
                result = "Threedevice";
            }
            if (getText(DeviceCountOrg).equals("4")) {
                result = "Fourdevice";
            }
            if (getText(DeviceCountOrg).equals("5")) {
                result = "Fivedevice";
            }
            if (getText(DeviceCountOrg).equals("6")) {
                result = "Sixdevice";
            }
            if (getText(DeviceCountOrg).equals("7")) {
                result = "Sevendevice";
            }
            if (getText(DeviceCountOrg).equals("8")) {
                result = "Eightdevice";
            }
            if (getText(DeviceCountOrg).equals("9")) {
                result = "Ninedevice";
            }
            if (getText(DeviceCountOrg).equals("10")) {
                result = "Tendevice";
            }
            if (getText(DeviceCountOrg).equals("11")) {
                result = "Elevendevice";
            }
            if (getText(DeviceCountOrg).equals("12")) {
                result = "Twelvedevice";
            }
            System.out.println(result);
        }
        return result;

    }

    public boolean addNewdummyDevice1(Map<String, String> map) {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        orgAddDevice.click();
        MyCommonAPIs.sleepi(5);
        if (orgAddSingleDevice.exists() && orgAddMultipleDevice.exists()) {
        orgAddSingleDevice.click();
        MyCommonAPIs.sleepi(5);
        selectLocation.selectOption(map.get("Select Location"));
        MyCommonAPIs.sleepi(5);
        serialNo.sendKeys(map.get("Serial Number1"));
        MyCommonAPIs.sleepi(10);
        addDeviceBtn.click();
        MyCommonAPIs.sleep(4 * 1000);
        macAddress.sendKeys(map.get("MAC Address1"));
        MyCommonAPIs.waitReady();
        if (errorOK.exists()) {
            errorOK.click();
            logger.info("Clicked on error device");
        }
        clickBoxLastButton();
        MyCommonAPIs.waitReady();
        clickBoxLastButton();
        MyCommonAPIs.sleepi(5);

        if (viewDevices1.exists()) {
            viewDevices1.click();
            logger.info("Clicked on View device");
            result = true;
        }

        else if (ShowErrorMsg.exists()) {
            String ErrorMessage = ShowErrorMsg.getText();
            logger.info(ErrorMessage);
            close.click();
            result = true;
        }
        
        }
        return result;
    }

    public boolean searchFilterselectOrbiPro() {

        boolean result = false;

        MyCommonAPIs.sleepi(5);
        devicesFilter.click();
        MyCommonAPIs.sleepi(5);

        if (filterDeviceVerification.exists()) {
            logger.info("Entered in to Devices filter");
            selectOrbiPro.click();
            MyCommonAPIs.sleepi(3);
            searchFilterApplyButton.click();
            logger.info("Orbi Pro is selected from Devices filter");
            MyCommonAPIs.sleepi(3);
            if (coommanDeviceParameter.exists() && orbi.exists()) {
                result = true;
                logger.info("Orbi Pro Filter applied sucessfully.");
            }
            MyCommonAPIs.sleepi(5);
            devicesFilter.click();
            MyCommonAPIs.sleepi(5);
            selectOrbiPro.click();
            MyCommonAPIs.sleepi(1);
            searchFilterApplyButton.click();
            MyCommonAPIs.sleepi(1);

        }

        return result;

    }

    public boolean searchFilterselectSwitch() {

        boolean result = false;

        MyCommonAPIs.sleepi(5);
        devicesFilter.click();
        MyCommonAPIs.sleepi(5);

        if (filterDeviceVerification.exists()) {
            logger.info("Entered in to Devices filter");
            selectSwitch.click();
            searchFilterApplyButton.click();
            logger.info("Switch is selected from Devices filter");
            MyCommonAPIs.sleepi(1);
            if (coommanDeviceParameter.exists() && switch1.exists()) {
                result = true;
                logger.info("Switch Filter applied sucessfully.");
            }
            MyCommonAPIs.sleepi(5);
            devicesFilter.click();
            MyCommonAPIs.sleepi(5);
            selectSwitch.click();
            MyCommonAPIs.sleepi(1);
            searchFilterApplyButton.click();
            MyCommonAPIs.sleepi(1);
            devicesFilter.click();
            MyCommonAPIs.sleepi(1);

        }

        return result;

    }

    public boolean searchFilterselectBusinessRouter() {

        boolean result = false;

        MyCommonAPIs.sleepi(5);
        devicesFilter.click();
        MyCommonAPIs.sleepi(5);

        if (filterDeviceVerification.exists()) {
            logger.info("Entered in to Devices filter");
            selectBusinessRouter.click();
            searchFilterApplyButton.click();
            logger.info("BR is selected from Devices filter");
            MyCommonAPIs.sleepi(1);
            if (coommanDeviceParameter.exists() && businessRouter.exists()) {
                result = true;
                logger.info("BR Filter applied sucessfully.");
            }
            MyCommonAPIs.sleepi(5);
            devicesFilter.click();
            MyCommonAPIs.sleepi(1);
            selectBusinessRouter.click();
            MyCommonAPIs.sleepi(1);
            searchFilterApplyButton.click();
            MyCommonAPIs.sleepi(1);
            devicesFilter.click();
            MyCommonAPIs.sleepi(1);

        }

        return result;

    }

    public boolean searchFilterselectAccessPoint() {

        boolean result = false;

        MyCommonAPIs.sleepi(5);
        devicesFilter.click();
        MyCommonAPIs.sleepi(5);

        if (filterDeviceVerification.exists()) {
            logger.info("Entered in to Devices filter");
            selectAccessPoint.click();
            searchFilterApplyButton.click();
            logger.info("AP is selected from Devices filter");
            MyCommonAPIs.sleepi(1);
            if (coommanDeviceParameter.exists() && ap.exists()) {
                result = true;
                logger.info("AP Filter applied sucessfully.");
            }
            MyCommonAPIs.sleepi(5);
            devicesFilter.click();
            MyCommonAPIs.sleepi(5);
            selectAccessPoint.click();
            MyCommonAPIs.sleepi(1);
            searchFilterApplyButton.click();
            MyCommonAPIs.sleepi(1);
            devicesFilter.click();
            MyCommonAPIs.sleepi(1);

        }

        return result;

    }

    public boolean searchFilterselectAirBridge() {

        boolean result = false;

        MyCommonAPIs.sleepi(5);
        devicesFilter.click();
        MyCommonAPIs.sleepi(5);

        if (filterDeviceVerification.exists()) {
            logger.info("Entered in to Devices filter");
            selectAirBridge.click();
            searchFilterApplyButton.click();
            logger.info("Airbridge is selected from Devices filter");
            MyCommonAPIs.sleepi(1);
            if (noDeviceAvailable.exists()) {
                result = true;
                logger.info("Airbridge Filter applied sucessfully because Airbridge is not added into the acount showing no devices available.");
            }
            MyCommonAPIs.sleepi(5);
            devicesFilter.click();
            MyCommonAPIs.sleepi(5);
            selectAirBridge.click();
            MyCommonAPIs.sleepi(1);
            searchFilterApplyButton.click();
            MyCommonAPIs.sleepi(1);
            devicesFilter.click();
            MyCommonAPIs.sleepi(1);

        }

        return result;

    }

    public boolean searchFilterselectMeshAP() {

        boolean result = false;

        MyCommonAPIs.sleepi(5);
        devicesFilter.click();
        MyCommonAPIs.sleepi(5);

        if (filterDeviceVerification.exists()) {
            logger.info("Entered in to Devices filter");
            selectMeshAP.click();
            searchFilterApplyButton.click();
            logger.info("Airbridge is selected from Devices filter");
            MyCommonAPIs.sleepi(1);
            if (coommanDeviceParameter.exists() && ap.exists()) {
                result = true;
                logger.info("AP Mesh Filter applied sucessfully.");
            }
            MyCommonAPIs.sleepi(5);
            devicesFilter.click();
            MyCommonAPIs.sleepi(5);
            selectMeshAP.click();
            MyCommonAPIs.sleepi(1);
            searchFilterApplyButton.click();
            MyCommonAPIs.sleepi(1);
            devicesFilter.click();
            MyCommonAPIs.sleepi(1);

        }

        return result;

    }

    // Added by Vivek
    public void addNewdummyDeviceProAccount(Map<String, String> map) {

        for (String ss : map.keySet()) {
            logger.info(ss + ": " + map.get(ss));
            logger.info("Check the print statement");
        }

        open(URLParam.hrefDevices, true);
        MyCommonAPIs.sleepi(3);
        refresh();
        MyCommonAPIs.sleepi(2);
        refresh();
        MyCommonAPIs.sleepi(2);
        clickProAddDevice();
        waitElement(addDeviceBtn);
        serialNo.sendKeys(map.get("Serial Number1"));
        MyCommonAPIs.sleepi(10);
        addDeviceBtn.click();
        MyCommonAPIs.sleep(4 * 1000);
        macAddress.sendKeys(map.get("MAC Address1"));
        MyCommonAPIs.waitReady();
//        if (errorOK.exists()) {
//            errorOK.click();
//            logger.info("Clicked on error device");
//        }
        ButtonElements.NEXT.click();
        MyCommonAPIs.sleepi(15);
//        clickBoxLastButton();
//        MyCommonAPIs.waitReady();
//        clickBoxLastButton();
//        MyCommonAPIs.sleepi(5);

        if (viewDevices1.exists()) {
            viewDevices1.click();
            logger.info("Clicked on View device");
        }

        else if (ShowErrorMsg.exists()) {
            String ErrorMessage = ShowErrorMsg.getText();
            logger.info(ErrorMessage);
            close.click();
        }
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

    public void checkDeviceInAdminAccount1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(false);
        gotoAllLocations();
        WebCheck.checkHrefIcon(URLParam.hrefDevices);
        if (getDeviceName(WebportalParam.ap1serialNo).equals("")) {
            Map<String, String> devInfo = new HashMap<String, String>();
            devInfo.put("Serial Number", WebportalParam.ap1serialNo);
            devInfo.put("Device Name", WebportalParam.ap1deveiceName);
            devInfo.put("MAC Address1", WebportalParam.ap1macaddress);
            if (checkAddDevice(devInfo)) {
            } else {
                UserManage userManage = new UserManage();
                userManage.logout();
                webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
                new AccountPage().enterLocation(WebportalParam.location1);

                deleteDeviceYes(WebportalParam.ap1serialNo);
                userManage.logout();
                webportalLoginPage.defaultLogin();

                new AccountPage().enterLocation(WebportalParam.location1);
                addNewDevice(devInfo);
            }
            reloadDeviceList();
            waitDevicesReConnected(WebportalParam.ap1serialNo);
        }
    }

    public void checkDeviceInAdminAccount2() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(false);
        gotoAllLocations();
        WebCheck.checkHrefIcon(URLParam.hrefDevices);
        if (getDeviceName(WebportalParam.ob1serialNo).equals("")) {
            Map<String, String> devInfo = new HashMap<String, String>();
            devInfo.put("Serial Number", WebportalParam.ob1serialNo);
            devInfo.put("Device Name", WebportalParam.ob1deveiceName);
            devInfo.put("MAC Address1", WebportalParam.ob1deveiceMac);
            if (checkAddDevice(devInfo)) {
            } else {
                UserManage userManage = new UserManage();
                userManage.logout();
                webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
                new AccountPage().enterLocation(WebportalParam.location1);

                deleteDeviceYes(WebportalParam.ob1serialNo);
                userManage.logout();
                webportalLoginPage.defaultLogin();

                new AccountPage().enterLocation(WebportalParam.location1);
                addNewDevice(devInfo);
            }
            reloadDeviceList();
            waitDevicesReConnected(WebportalParam.ob1serialNo);
        }
    }

public void checkDeviceInAdminAccount3() {
    WebportalLoginPage webportalLoginPage = new WebportalLoginPage(false);
    gotoAllLocations();
    WebCheck.checkHrefIcon(URLParam.hrefDevices);
    if (getDeviceName(WebportalParam.sw1serialNo).equals("")) {
        Map<String, String> devInfo = new HashMap<String, String>();
        devInfo.put("Serial Number", WebportalParam.sw1serialNo);
        devInfo.put("Device Name", WebportalParam.sw1deveiceName);
        devInfo.put("MAC Address1", WebportalParam.sw1MacAddress);
        if (checkAddDevice(devInfo)) {
        } else {
            UserManage userManage = new UserManage();
            userManage.logout();
            webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
            new AccountPage().enterLocation(WebportalParam.location1);
            
            deleteDeviceYes(WebportalParam.sw1serialNo);
            userManage.logout();
            webportalLoginPage.defaultLogin();
            
            new AccountPage().enterLocation(WebportalParam.location1);
            addNewDevice(devInfo);
        }
        reloadDeviceList();
        waitDevicesReConnected(WebportalParam.sw1serialNo);
    }
}

    // Added by vivek
    public void clickOnFirstLocation() {
//        firstLocation.click();
        MyCommonAPIs.sleepi(5);
    }

    // Added by vivek
    public boolean verifyDeviceIsOnboarded(String apSerialNumber) {
        boolean result = false;
        String actualvalue = getDeviceName(apSerialNumber);
        if (actualvalue.equals(apSerialNumber)) {
            logger.info(apSerialNumber);
            result = true;
        }
        return result;

    }
    //added by Pratik
    public boolean moveDeviceAndVerify(String serialNumber) {
        boolean result = false;
        MyCommonAPIs.sleep(3000);
        $x("//td[text()='"+serialNumber+"']").shouldBe(Condition.visible).hover();
        MyCommonAPIs.sleep(3000);
        editModule(serialNumber).hover();
        MyCommonAPIs.sleep(3000);
        moveMultipleDevicesfromOneLocation(serialNumber).waitUntil(Condition.visible, 60 * 1000).click();
        MyCommonAPIs.sleepi(10);
        waitElement(yesButton);
        yesButton.click();
        MyCommonAPIs.sleepi(10);
        selectOrg.click();
        MyCommonAPIs.sleepi(10);
        waitElement(selectSecondOrg);
        selectSecondOrg.click();
        MyCommonAPIs.sleepi(10);
        waitElement(moveButton);
        moveButton.click();
        waitElement(successMsg);
        if (successMsg.exists()) {
            result = true; 
        }
        return result;
    }
    public boolean pagination() {
        boolean result = false;
        if(firstpage.isDisplayed() && previous.isDisplayed() && Secondpage.isDisplayed() && Next.isDisplayed()) {
            result = true;
        }
        
        return result;
    }

    
    public boolean verifyNoOfClientsinDeviceDashboardPage() {
        boolean result = false;
        waitElement(deviceDashboardClientNo);
        if (deviceDashboardClientNo.exists()==false) {
            for (int i = 0; i<=5; i++) {
                refresh();
               if (deviceDashboardClientNo.exists()) {
                   System.out.println(deviceDashboardClientNo.getText());
                   noofConnectedclient = deviceDashboardClientNo.getText();
                   System.out.println(noofConnectedclient);
                   result = true;
                   break;
               }
            }
        } else {
            if (deviceDashboardClientNo.exists()) {
                System.out.println(deviceDashboardClientNo.getText());
                noofConnectedclient = deviceDashboardClientNo.getText();
                System.out.println(noofConnectedclient);
                result = true;
            }
            
        }
        
        return result; 
    }
    public boolean DeviceCount(String Num) {
        boolean result = false;
        String count = "";
        if(NoofDevices.isDisplayed()) {
            count = NoofDevices.getText();
        }if (NoofDevicesWireless.isDisplayed()) {
            count = NoofDevicesWireless.getText();
        }if(NoofDevicesWired.isDisplayed()) {
            count = NoofDevicesWired.getText();
        }
        System.out.println(count);
        if(count.equals("Devices ("+Num+")")) {
            result = true;
        }
        
        return result;

    }
    public boolean DeviceSearch(String serialNumber) {
        boolean result = false;
        
        DevicesSearch.click();
        MyCommonAPIs.sleepi(3);
        SendSearch.sendKeys(serialNumber);
        MyCommonAPIs.sleepi(3);
        ClickSearch.click();
        MyCommonAPIs.sleepi(10);
        if(devicesName(serialNumber).isDisplayed());
        {
            result= true;
        }
        
        return result;

    }
    
    public boolean DeviceNext() {
        boolean result = false;
        
        Next.click();
        MyCommonAPIs.sleepi(10);    
        if(SelectSecondpage.isDisplayed() & devicesName("4XU44441067FB").isDisplayed()) {
            result = true;
        }
        
        return result;

    }
    public boolean moveDevicewithinSameOrgAndVerify(String serialNumber) {
        boolean result = false;
        MyCommonAPIs.sleep(3000);
        $x("//td[text()='"+serialNumber+"']").shouldBe(Condition.visible).hover();
        MyCommonAPIs.sleep(3000);
        editModule(serialNumber).hover();
        MyCommonAPIs.sleep(3000);
        moveMultipleDevicesfromOneLocation(serialNumber).waitUntil(Condition.visible, 60 * 1000).click();
        MyCommonAPIs.sleep(10);
        waitElement(yesButton);
        yesButton.click();
        MyCommonAPIs.sleep(10);
        selectLocationDropdown.click();
        MyCommonAPIs.sleep(2);
        waitElement(selectSecondloc);
        selectSecondloc.click();
        MyCommonAPIs.sleep(10);
        waitElement(moveButton);
        moveButton.click();
        waitElement(successMsg);
        if (successMsg.exists()) {
            result = true; 
        }
        return result;
    }
    public boolean moveDevicewithinSamePremiumAccAndVerify(String serialNumber) {
        boolean result = false;
        executeJavaScript("arguments[0].removeAttribute('class')", editModule(serialNumber));
        MyCommonAPIs.sleep(3000);
        moveDevice.waitUntil(Condition.visible, 60 * 1000).click();
        MyCommonAPIs.sleepi(15);
        waitElement(yesButton);
        yesButton.click();
        MyCommonAPIs.sleepi(15);
        selectloc.click();
        MyCommonAPIs.sleepi(5);
        waitElement(selectSecondOrg);
        selectSecondOrg.click();
        MyCommonAPIs.sleepi(10);
        waitElement(moveButton);
        moveButton.click();
        waitElement(successMsg);
        if (successMsg.exists()) {
            result = true; 
        }
        return result;
    }
    public boolean moveDevicetoOrg1orloc1AndVerify(String serialNumber) {
        boolean result = false;
        MyCommonAPIs.sleep(3000);
        $x("//td[text()='"+serialNumber+"']").shouldBe(Condition.visible).hover();
        MyCommonAPIs.sleep(3000);
        editModule(serialNumber).hover();
        MyCommonAPIs.sleep(3000);
        moveMultipleDevicesfromOneLocation(serialNumber).waitUntil(Condition.visible, 60 * 1000).click();
        MyCommonAPIs.sleepi(10);
        waitElement(yesButton);
        yesButton.click();
        MyCommonAPIs.sleepi(10);
        waitElement(clickOnLocationDropdown);
        clickOnLocationDropdown.click();
        MyCommonAPIs.sleepi(2);
        waitElement(Selectlocation1);
        Selectlocation1.click();
        MyCommonAPIs.sleepi(1);
        waitElement(moveButton);
        moveButton.click();
        MyCommonAPIs.sleepi(1);
        waitElement(successMsg);
        if (successMsg.exists()) {
            result = true; 
        }
        return result;
    }
    public boolean moveDevicetoLoc1(String serialNumber) {
        boolean result = false;
        executeJavaScript("arguments[0].removeAttribute('class')", editModule(serialNumber));
        MyCommonAPIs.sleep(3000);
        moveDevice.waitUntil(Condition.visible, 60 * 1000).click();
        MyCommonAPIs.sleep(10);
        waitElement(yesButton);
        yesButton.click();
        MyCommonAPIs.sleep(10);
        waitElement(moveButton);
        moveButton.click();
        waitElement(successMsg);
        if (successMsg.exists()) {
            result = true; 
        }
        return result;
    }
    public boolean moveOfflineDeviceAndVerify(String serialNumber) {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        hoverDevice(serialNumber).hover();
        MyCommonAPIs.sleepi(5);
        if (moveDevice.exists() ||  moveDevice1.exists()) {
            moveDevice.waitUntil(Condition.visible, 60 * 1000).click();
        } else {
            moveDevice.waitUntil(Condition.visible, 60 * 1000).click();
        }
        MyCommonAPIs.sleep(10);
        waitElement(yesButton);
        yesButton.click();
        MyCommonAPIs.sleep(10);
        selectOrg.click();
        MyCommonAPIs.sleep(10);
        waitElement(selectSecondOrg);
        selectSecondOrg.click();
        MyCommonAPIs.sleep(10);
        waitElement(moveButton);
        moveButton.click();
        waitElement(successMsg);
        if (successMsg.exists()) {
            result = true; 
        }
        return result;
    }
    
    public void openLocationFromotherOrg () {
        waitElement(selectLocationfromAnotherOrg);
        selectLocationfromAnotherOrg.click();
    }
    
    public boolean isDeviceRebooting(String serialNumber) {
        boolean result = false;
        for (int i=0; i<=17; i++) {
            refresh();
            MyCommonAPIs.sleepi(10);
        if (getDeviceStatus(serialNumber).equals(rebooting)) {
            logger.info("Entered loop Rebooting");
            System.out.println("Entered loop");
            result=true;
            System.out.println(i+" Round completed Rebooting:-"+result);
            break;
        }  
    }
        return result;
    }
    
    public boolean isDeviceResetConfiguration(String serialNumber) {
        boolean result = false;
        for (int i=0; i<=30; i++) {
            refresh();
            MyCommonAPIs.sleepi(10);
        if (getDeviceStatus(serialNumber).equals(configurationInProgress)) {
            logger.info("Entered loop Configuration in progress");
            System.out.println("Entered loop Configuration in progress");
            result=true;
            System.out.println(i+" Round completed:-"+result);
            break;
        }  
    }
        return result;
    }
    
    public boolean noDevicesAvailableonoffice1Location () {
        boolean result = false;
        if (noDevicesAvailable.exists()) {
            result = true;
            logger.info("No devices available on office1 location");
            System.out.println("No devices available on office1 location");
        }
        return result;
    }
    
    public void enterIntoAPGotoStatasticsTab() {
        waitElement(enterIntoAP);
        enterIntoAP.doubleClick();
        MyCommonAPIs.sleepi(10);
        waitElement(statasticsTab);
        statasticsTab.click();
        MyCommonAPIs.sleepi(10);
    }
    
    public boolean verifyStatasticsPage() {
        boolean result = false;
        for (int i=1; i<=30;i++) {
            refresh();
            MyCommonAPIs.sleepi(10);
            if (statasticsRow.exists()) {
                logger.info("Entered loop Conf for checking stats in Statastics Page");
                System.out.println("Entered loop Conf for checking stats in Statastics Page");
                System.out.println(i+" Round completed:-");
                break;
            }  
        }
            MyCommonAPIs.sleepi(10);
            waitElement(clearStatsBtn);
            clearStatsBtn.click();
            for (int j=1; j<=6;j++) {
                refresh();
                MyCommonAPIs.sleepi(10);
                if (statastcsClientsAfterClearStats.exists() && broadcastAndMulticastZeroData.exists() && unicastZeroData.exists()) {
                    logger.info("Entered loop Conf for checking clear stats in Statastics Page");
                    System.out.println("Entered loop Conf for checking clear stats in Statastics Page");
                    System.out.println(j+" Round completed:-");
                    result = true;
                    break;
                }
            }
        return result;
    }
    
    public void deleteDevice1(String Device) {
        WebCheck.checkHrefIcon(URLParam.hrefDevices);
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
        MyCommonAPIs.sleep(10);
        hoverDevice(Device).hover();
        MyCommonAPIs.sleep(3000);
        deleteDevice(Device).waitUntil(Condition.visible, 60 * 1000).click();
        MyCommonAPIs.sleep(10);
        waitElement(deleteButtonConfm);
        deleteButtonConfm.click();
        MyCommonAPIs.sleep(10);
    }
    public boolean RefreshButton(String serialNumber) {
        boolean result = false;
        
        if(RefreshButton.isDisplayed()) {
            RefreshButton.click();
            String ststus = getDeviceStatus(serialNumber);
            System.out.println(ststus);
            RefreshButton.click();
            String ststus1 = getDeviceStatus(serialNumber);
            System.out.println(ststus1);
            result = true;
        }
       
       
        return result;

    }
	
	 // Added by vivek
    public void DobleClickOnDeviceStatus() {
        MyCommonAPIs.sleepi(3);
        Selenide.refresh();
        MyCommonAPIs.sleepi(3);
        DeviceStatusemt.hover();
        DeviceStatusemt.doubleClick();
        MyCommonAPIs.sleepi(5);    
    }
    
 // Added by vivek
    public void openDeviceLoc() {
        MyCommonAPIs.sleepi(3);
        locImg.click();
        MyCommonAPIs.sleepi(3);
        device.click();
        MyCommonAPIs.sleepi(3);
        
    }
    
    // Added by vivek
    public boolean VerifyUserCanSeeThePwd() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);    
        if(AdminPwdTxt.isDisplayed()) {
            result = true;
        } 
        return result;
    }
    
    // Added by vivek
    public boolean VerifyUserAbleToPwdReadOnly(String pwd) {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        eyeHideShowIcon.click();
        MyCommonAPIs.sleepi(2);  
        logger.info("passwordData =>> : " + passwordData.getAttribute("value"));
        if(passwordData.getAttribute("value").contains(pwd)) {
            result = true;
        } 
        return result;
    }
    
 // Added by vivek
    public boolean VerifySupportUserNotAbleToSeeThePwd(String pwd) {
        boolean result = true;
        MyCommonAPIs.sleepi(5);
        eyeHideShowIcon.click();
        MyCommonAPIs.sleepi(2);
        logger.info("passwordData for Support Admin User =>> : " + passwordData.text());
        if(passwordData.text().contains(pwd)) {
            result = false;
        } 
        return result;
    } 
    //AddedByPratik
    public boolean verifySettingPageFilterAccessPoint() {
        
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        waitElement(premiumAccGoToMainPage);
        premiumAccGoToMainPage.click();
        MyCommonAPIs.sleepi(10);
        refresh();
        MyCommonAPIs.sleepi(10);
        waitElement(settingsIconOnHomePage);
        settingsIconOnHomePage.click();
        MyCommonAPIs.sleepi(2);
        waitElement(myDevicesoption);
        myDevicesoption.hover();
        myDevicesoption.click();
        MyCommonAPIs.sleepi(30);
        if (alldevices4Number.exists() && oneLocation.exists() && settingPagetext.exists() && locationsText.exists() && office1Text.exists() ) {
            logger.info("Landed successfully on Settings==>MyDevices page");
            plusIconMyDevices.click();
            MyCommonAPIs.sleepi(2);
            waitElement(settingsPageFilter);
            settingsPageFilter.click();
            MyCommonAPIs.sleepi(2);
            waitElement(settingsPageFilterAP);
            settingsPageFilterAP.click();
            MyCommonAPIs.sleepi(1);
            waitElement(settingsfilteroffice1loc);
            settingsfilteroffice1loc.click();
            MyCommonAPIs.sleepi(1);
            waitElement(settingsFilterApplyBtn);
            settingsFilterApplyBtn.click();
            MyCommonAPIs.sleepi(5);
            if ((settingsFilterAPWAC.exists() || settingsFilterAPWAX.exists()) && 
                    ((settingsFilterOrbi.exists() || settingsFilterSwitch.exists() || settingsFilterBR500.exists() || settingsFilterNoDevices.exists())!=true)) {
                result = true;
                logger.info("Settings==>MyDevices Filter Access point and verifed AP showing only");
                waitElement(settingsPageFilter);
                settingsPageFilter.click();
                MyCommonAPIs.sleepi(1);
                waitElement(settingsPageFilterAP);
                settingsPageFilterAP.click();
                MyCommonAPIs.sleepi(1);
                waitElement(settingsfilteroffice1loc);
                settingsfilteroffice1loc.click();
                MyCommonAPIs.sleepi(1);
                waitElement(settingsFilterApplyBtn);
                settingsFilterApplyBtn.click();
                MyCommonAPIs.sleepi(1);
            }
            
        }
        return result;
    }
    // Added by Pratik
    public void  onboardDummySwitch(String serialNo, String deveiceName, String deveiceMac) {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(false);
        gotoAllLocations();
        WebCheck.checkHrefIcon(URLParam.hrefDevices);
        if (getDeviceName(serialNo).equals("")) {
            Map<String, String> devInfo = new HashMap<String, String>();
            devInfo.put("Serial Number", serialNo);
            devInfo.put("Device Name", deveiceName);
            devInfo.put("MAC Address", deveiceMac);
            if (checkAddDevice(devInfo)) {
            } else {
                UserManage userManage = new UserManage();
                userManage.logout();
                webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
                new AccountPage().enterLocation(WebportalParam.location1);

                deleteDeviceYes(serialNo);
                userManage.logout();
                webportalLoginPage.defaultLogin();

                new AccountPage().enterLocation(WebportalParam.location1);
                addNewDevice(devInfo);
            }
            reloadDeviceList();
        }
    }
    
    public void checkDeviceInAdminAccountGen(String SerialNo, String MacAdd, String deviceName) {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(false);
        gotoAllLocations();
        WebCheck.checkHrefIcon(URLParam.hrefDevices);
        if (getDeviceName(SerialNo).equals("")) {
            Map<String, String> devInfo = new HashMap<String, String>();
            devInfo.put("Serial Number", SerialNo);
            devInfo.put("Device Name", deviceName);
            devInfo.put("MAC Address1", MacAdd);
            if (checkAddDevice(devInfo)) {
            } else {
                UserManage userManage = new UserManage();
                userManage.logout();
                webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
                new AccountPage().enterLocation(WebportalParam.location1);

                deleteDeviceYes(SerialNo);
                userManage.logout();
                webportalLoginPage.defaultLogin();

                new AccountPage().enterLocation(WebportalParam.location1);
                addNewDevice(devInfo);
            }
            reloadDeviceList();
            waitDevicesReConnectedPR(SerialNo);
            enableApSsh();
        }
    }
//AddedByPratik
public boolean verifySettingPageFilterbyLocation() {
        
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        waitElement(premiumAccGoToMainPage);
        premiumAccGoToMainPage.click();
        MyCommonAPIs.sleepi(10);
        refresh();
        MyCommonAPIs.sleepi(10);
        waitElement(settingsIconOnHomePage);
        settingsIconOnHomePage.click();
        MyCommonAPIs.sleepi(2);
        waitElement(myDevicesoption);
        myDevicesoption.hover();
        myDevicesoption.click();
        MyCommonAPIs.sleepi(30);
        if (alldevices4Number.exists() && oneLocation.exists() && settingPagetext.exists() && locationsText.exists() && office1Text.exists() ) {
            logger.info("Landed successfully on Settings==>MyDevices page");
            plusIconMyDevices.click();
            MyCommonAPIs.sleepi(2);
            waitElement(settingsPageFilter);
            settingsPageFilter.click();
            MyCommonAPIs.sleepi(2);
            waitElement(settingsfilteroffice1loc);
            settingsfilteroffice1loc.click();
            MyCommonAPIs.sleepi(1);
            waitElement(settingsFilterApplyBtn);
            settingsFilterApplyBtn.click();
            MyCommonAPIs.sleepi(5);
            if ((settingsFilterAPWAC.exists() || settingsFilterAPWAX.exists()) && 
                    settingsFilterOrbi.exists() && settingsFilterSwitch.exists() && settingsFilterBR500.exists() && ((settingsFilterNoDevices.exists())!=true)) {
                result = true;
                logger.info("Settings==>MyDevices==>filterLocation Showing All Devices");
                waitElement(settingsPageFilter);
                settingsPageFilter.click();
                MyCommonAPIs.sleepi(1);
                waitElement(settingsfilteroffice1loc);
                settingsfilteroffice1loc.click();
                MyCommonAPIs.sleepi(1);
                waitElement(settingsFilterApplyBtn);
                settingsFilterApplyBtn.click();
                MyCommonAPIs.sleepi(1);
            }
            
        }
        return result;
    }

public boolean verifySettingPageFilterOrbi() {
    
    boolean result = false;
    MyCommonAPIs.sleepi(10);
    waitElement(premiumAccGoToMainPage);
    premiumAccGoToMainPage.click();
    MyCommonAPIs.sleepi(10);
    refresh();
    MyCommonAPIs.sleepi(10);
    waitElement(settingsIconOnHomePage);
    settingsIconOnHomePage.click();
    MyCommonAPIs.sleepi(2);
    waitElement(myDevicesoption);
    myDevicesoption.hover();
    myDevicesoption.click();
    MyCommonAPIs.sleepi(30);
    if (alldevices4Number.exists() && oneLocation.exists() && settingPagetext.exists() && locationsText.exists() && office1Text.exists() ) {
        logger.info("Landed successfully on Settings==>MyDevices page");
        plusIconMyDevices.click();
        MyCommonAPIs.sleepi(2);
        waitElement(settingsPageFilter);
        settingsPageFilter.click();
        MyCommonAPIs.sleepi(2);
        waitElement(settingsPageFilterOrbi);
        settingsPageFilterOrbi.click();
        MyCommonAPIs.sleepi(1);
        waitElement(settingsfilteroffice1loc);
        settingsfilteroffice1loc.click();
        MyCommonAPIs.sleepi(1);
        waitElement(settingsFilterApplyBtn);
        settingsFilterApplyBtn.click();
        MyCommonAPIs.sleepi(5);
        if (settingsFilterOrbi.exists() && 
                ((settingsFilterAPWAC.exists() || settingsFilterAPWAX.exists() || settingsFilterSwitch.exists() || settingsFilterBR500.exists() || settingsFilterNoDevices.exists())!=true)) {
            result = true;
            logger.info("Settings==>MyDevices Filter Orbi and verifed Orbi showing only");
            waitElement(settingsPageFilter);
            settingsPageFilter.click();
            MyCommonAPIs.sleepi(1);
            waitElement(settingsPageFilterOrbi);
            settingsPageFilterOrbi.click();
            MyCommonAPIs.sleepi(1);
            waitElement(settingsfilteroffice1loc);
            settingsfilteroffice1loc.click();
            MyCommonAPIs.sleepi(1);
            waitElement(settingsFilterApplyBtn);
            settingsFilterApplyBtn.click();
            MyCommonAPIs.sleepi(1);
        }
        
    }
    return result;

}

public boolean verifySettingPageFilterBusinessrouter() {
    
    boolean result = false;
    MyCommonAPIs.sleepi(10);
    waitElement(premiumAccGoToMainPage);
    premiumAccGoToMainPage.click();
    MyCommonAPIs.sleepi(10);
    refresh();
    MyCommonAPIs.sleepi(10);
    waitElement(settingsIconOnHomePage);
    settingsIconOnHomePage.click();
    MyCommonAPIs.sleepi(2);
    waitElement(myDevicesoption);
    myDevicesoption.hover();
    myDevicesoption.click();
    MyCommonAPIs.sleepi(30);
    if (alldevices4Number.exists() && oneLocation.exists() && settingPagetext.exists() && locationsText.exists() && office1Text.exists() ) {
        logger.info("Landed successfully on Settings==>MyDevices page");
        plusIconMyDevices.click();
        MyCommonAPIs.sleepi(2);
        waitElement(settingsPageFilter);
        settingsPageFilter.click();
        MyCommonAPIs.sleepi(2);
        waitElement(settingsPageFilterBR500);
        settingsPageFilterBR500.click();
        MyCommonAPIs.sleepi(1);
        waitElement(settingsfilteroffice1loc);
        settingsfilteroffice1loc.click();
        MyCommonAPIs.sleepi(1);
        waitElement(settingsFilterApplyBtn);
        settingsFilterApplyBtn.click();
        MyCommonAPIs.sleepi(5);
        if (settingsFilterBR500.exists() && 
                ((settingsFilterOrbi.exists() || settingsFilterSwitch.exists() || settingsFilterAPWAC.exists() || settingsFilterAPWAX.exists() || settingsFilterNoDevices.exists())!=true)) {
            result = true;
            logger.info("Settings==>MyDevices Filter BR500 and verifed BR500 showing only");
            waitElement(settingsPageFilter);
            settingsPageFilter.click();
            MyCommonAPIs.sleepi(1);
            waitElement(settingsPageFilterBR500);
            settingsPageFilterBR500.click();
            MyCommonAPIs.sleepi(1);
            waitElement(settingsfilteroffice1loc);
            settingsfilteroffice1loc.click();
            MyCommonAPIs.sleepi(1);
            waitElement(settingsFilterApplyBtn);
            settingsFilterApplyBtn.click();
            MyCommonAPIs.sleepi(1);
        }
        
    }
    return result;
}

public boolean verifySettingPageFilterSwitch() {
    
    boolean result = false;
    MyCommonAPIs.sleepi(10);
    waitElement(premiumAccGoToMainPage);
    premiumAccGoToMainPage.click();
    MyCommonAPIs.sleepi(10);
    refresh();
    MyCommonAPIs.sleepi(10);
    waitElement(settingsIconOnHomePage);
    settingsIconOnHomePage.click();
    MyCommonAPIs.sleepi(2);
    waitElement(myDevicesoption);
    myDevicesoption.hover();
    myDevicesoption.click();
    MyCommonAPIs.sleepi(30);
    if (alldevices4Number.exists() && oneLocation.exists() && settingPagetext.exists() && locationsText.exists() && office1Text.exists() ) {
        logger.info("Landed successfully on Settings==>MyDevices page");
        plusIconMyDevices.click();
        MyCommonAPIs.sleepi(2);
        waitElement(settingsPageFilter);
        settingsPageFilter.click();
        MyCommonAPIs.sleepi(2);
        waitElement(settingsPageFilterSwitch);
        settingsPageFilterSwitch.click();
        MyCommonAPIs.sleepi(1);
        waitElement(settingsfilteroffice1loc);
        settingsfilteroffice1loc.click();
        MyCommonAPIs.sleepi(1);
        waitElement(settingsFilterApplyBtn);
        settingsFilterApplyBtn.click();
        MyCommonAPIs.sleepi(5);
        if (settingsFilterSwitch.exists() && 
                ((settingsFilterOrbi.exists() || settingsFilterAPWAC.exists() || settingsFilterAPWAX.exists() || settingsFilterBR500.exists() || settingsFilterNoDevices.exists())!=true)) {
            result = true;
            logger.info("Settings==>MyDevices Filter Switch and verifed Switch showing only");
            waitElement(settingsPageFilter);
            settingsPageFilter.click();
            MyCommonAPIs.sleepi(1);
            waitElement(settingsPageFilterSwitch);
            settingsPageFilterSwitch.click();
            MyCommonAPIs.sleepi(1);
            waitElement(settingsfilteroffice1loc);
            settingsfilteroffice1loc.click();
            MyCommonAPIs.sleepi(1);
            waitElement(settingsFilterApplyBtn);
            settingsFilterApplyBtn.click();
            MyCommonAPIs.sleepi(1);
        }
        
    }
    return result;
}

public boolean verifySettingPageFilterAirbridge() {
    
    boolean result = false;
    MyCommonAPIs.sleepi(10);
    waitElement(premiumAccGoToMainPage);
    premiumAccGoToMainPage.click();
    MyCommonAPIs.sleepi(10);
    refresh();
    MyCommonAPIs.sleepi(10);
    waitElement(settingsIconOnHomePage);
    settingsIconOnHomePage.click();
    MyCommonAPIs.sleepi(2);
    waitElement(myDevicesoption);
    myDevicesoption.hover();
    myDevicesoption.click();
    MyCommonAPIs.sleepi(30);
    if (alldevices4Number.exists() && oneLocation.exists() && settingPagetext.exists() && locationsText.exists() && office1Text.exists() ) {
        logger.info("Landed successfully on Settings==>MyDevices page");
        plusIconMyDevices.click();
        MyCommonAPIs.sleepi(2);
        waitElement(settingsPageFilter);
        settingsPageFilter.click();
        MyCommonAPIs.sleepi(2);
        waitElement(settingsPageFilrAirbridge);
        settingsPageFilrAirbridge.click();
        MyCommonAPIs.sleepi(1);
        waitElement(settingsfilteroffice1loc);
        settingsfilteroffice1loc.click();
        MyCommonAPIs.sleepi(1);
        waitElement(settingsFilterApplyBtn);
        settingsFilterApplyBtn.click();
        MyCommonAPIs.sleepi(5);
        if (settingsFilterNoDevices.exists()) {
            result = true;
            logger.info("Settings==>MyDevices Filter Airbridge and verifed not any devices are showing becasue airbridge is not added");
            waitElement(settingsPageFilter);
            settingsPageFilter.click();
            MyCommonAPIs.sleepi(1);
            waitElement(settingsPageFilrAirbridge);
            settingsPageFilrAirbridge.click();
            MyCommonAPIs.sleepi(1);
            waitElement(settingsfilteroffice1loc);
            settingsfilteroffice1loc.click();
            MyCommonAPIs.sleepi(1);
            waitElement(settingsFilterApplyBtn);
            settingsFilterApplyBtn.click();
            MyCommonAPIs.sleepi(1);
        }
        
    }
    return result;
}
            public boolean RFcheck() {
                MyCommonAPIs.sleepi(10);
                boolean result = false;                
                if(AssignRFProfile.isDisplayed() & UnassignRFProfile.isDisplayed()) {
                    result = true;
                }
                               
                return result;
     
            }
            
            public void AssignRF(String SLNo, String RFName) {
                MyCommonAPIs.sleepi(5); 
                waitElement(SelectDevice(SLNo));
                SelectDevice(SLNo).shouldBe(Condition.visible).click();
                MyCommonAPIs.sleepi(2);
                waitElement(AssignRFProfile);
                AssignRFProfile.shouldBe(Condition.visible).click();
                MyCommonAPIs.sleepi(2);
                waitElement(SelectRF);
                SelectRF.shouldBe(Condition.visible).selectOption(RFName);
                MyCommonAPIs.sleepi(2);
                waitElement(SaveRF);
                SaveRF.shouldBe(Condition.visible).click();
                MyCommonAPIs.sleepi(10);
                
            }
            
            public void UNAssignRF(String SLNo) {
                MyCommonAPIs.sleepi(5); 
                waitElement(SelectDevice(SLNo));
                SelectDevice(SLNo).shouldBe(Condition.visible).click();
                MyCommonAPIs.sleepi(2);
                waitElement(UnassignRFProfile);
                UnassignRFProfile.shouldBe(Condition.visible).click();
                MyCommonAPIs.sleepi(10);
                waitElement(yesButtonUnassignRFProfile);
                yesButtonUnassignRFProfile.shouldBe(Condition.visible).click();
                MyCommonAPIs.sleepi(10);
                
            }
            
	public void AssignRFMulti(String SLNo, String SLno2,String RFName) {
                MyCommonAPIs.sleepi(5); 
                waitElement(SelectDevice(SLNo));
                SelectDevice(SLNo).click();
                MyCommonAPIs.sleepi(5); 
                waitElement(SelectDevice(SLno2));
                SelectDevice(SLno2).click();
                MyCommonAPIs.sleepi(2);
                waitElement(AssignRFProfile);
                AssignRFProfile.click();
                MyCommonAPIs.sleepi(2);
                waitElement(SelectRF);
                SelectRF.selectOption(RFName);
                MyCommonAPIs.sleepi(2);
                waitElement(SaveRF);
                SaveRF.click();
                MyCommonAPIs.sleepi(10);
                
            }
        //AddedByPratik
            public boolean isAllDeviceRebooting(String serialNumberap1, String serialNumberap2, String serialNumberap3, String serialNumbersw1) {
                boolean result = false;
                for (int i=0; i<=12; i++) {
                    refresh();
                    MyCommonAPIs.sleepi(10);
                if ((getDeviceStatus(serialNumberap1).equals(rebooting) || getDeviceStatus(serialNumberap1).equals(wiatingToConnect)) && 
                        (getDeviceStatus(serialNumberap2).equals(rebooting) || getDeviceStatus(serialNumberap2).equals(wiatingToConnect)) && 
                        (getDeviceStatus(serialNumberap3).equals(rebooting) || getDeviceStatus(serialNumberap3).equals(wiatingToConnect)) && 
                        (getDeviceStatus(serialNumbersw1).equals(rebooting) || getDeviceStatus(serialNumbersw1).equals(wiatingToConnect))) {
                    logger.info("Entered loop Rebooting");
                    System.out.println("Entered loop");
                    result=true;
                    System.out.println(i+" Round completed Rebooting:-"+result);
                    break;
                }  
            }
                return result;
            }
            //AddedByPratik 
            public void deleteSwitch(String serialNumber) {
                MyCommonAPIs.sleepi(10);
                executeJavaScript("arguments[0].removeAttribute('class')", editModule(serialNumber));
                MyCommonAPIs.sleep(3000);
                delteDevicesfromOneLocation(serialNumber).waitUntil(Condition.visible, 60 * 1000).click();
                MyCommonAPIs.sleepi(10);
                waitElement(new WirelessQuickViewElement().deleteapyes);
                MyCommonAPIs.sleepi(30);
                new WirelessQuickViewElement().deleteapyes.click();
                MyCommonAPIs.sleepi(5);
            }
            
            //AddedByPratik
            public void checkDeviceInAdminAccountswitch2() {
                WebportalLoginPage webportalLoginPage = new WebportalLoginPage(false);
                gotoAllLocations();
                WebCheck.checkHrefIcon(URLParam.hrefDevices);
                if (getDeviceName(WebportalParam.sw2serialNo).equals("")) {
                    Map<String, String> devInfo = new HashMap<String, String>();
                    devInfo.put("Serial Number", WebportalParam.sw2serialNo);
                    devInfo.put("Device Name", WebportalParam.sw2deveiceName);
                    devInfo.put("MAC Address1", WebportalParam.sw2MacAddress);
                    if (checkAddDevice(devInfo)) {
                    } else {
                        UserManage userManage = new UserManage();
                        userManage.logout();
                        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
                        new AccountPage().enterLocation(WebportalParam.location1);

                        deleteDeviceYes(WebportalParam.sw2serialNo);
                        userManage.logout();
                        webportalLoginPage.defaultLogin();

                        new AccountPage().enterLocation(WebportalParam.location1);
                        addNewDevice(devInfo);
                    }
                    reloadDeviceList();
                    waitDevicesReConnected(WebportalParam.sw2serialNo);
                }
            }

            public boolean disablemanagedUnmanagedSwitch(String srno) {
                boolean result = false;
                MyCommonAPIs.sleepi(10);
                scrollToMac(WebportalParam.ap5macaddress).scrollIntoView(true);
                MyCommonAPIs.sleepi(1);
                waitElement(enableDisableManagedSwitch(srno));
                enableDisableManagedSwitch(srno).click();
                MyCommonAPIs.sleepi(10);
                yesButtonforSwitch.click();
                MyCommonAPIs.sleepi(1);
                waitElement(confirmmationMessage);
                if (confirmmationMessage.exists()) {
                    String confmMsg = confirmmationMessage.getText();
                    System.out.println(confmMsg);
                    MyCommonAPIs.sleepi(1);
                    if (unmanagedStatus(srno).exists()) {
                        logger.info("Managed Unmanaged switch is working fine for AP");
                        result = true;
                        enableDisableManagedSwitch(srno).click();
                        MyCommonAPIs.sleepi(10);
                        yesbuttonforunmanagetoman.click();
                        MyCommonAPIs.sleepi(1);
                    }
                }
                return result;
            }
            
            
            public void enablemanagedUnmanagedSwitch(String srno) {
                boolean result = false;
                MyCommonAPIs.sleepi(10);
                scrollToMac(WebportalParam.ap5macaddress).scrollIntoView(true);
                MyCommonAPIs.sleepi(1);
                waitElement(enableDisableManagedSwitch(srno));
                enableDisableManagedSwitch(srno).click();
                MyCommonAPIs.sleepi(10);
                yesbuttonforunmanagetoman.click();
                MyCommonAPIs.sleepi(1);
            }
            //AddedByPratik
            public boolean verifyManageUnmanageswiutchIsthereafternewdeviceonboard(String srno) {
                boolean result = false;
                MyCommonAPIs.sleepi(5);
                if (enableDisableManagedSwitch(srno).exists() || unmanagedStatus(srno).exists()) {
                    result = true;
                    logger.info("Test case passed successfully");
                }
                return result;
            }            
            
            
          //AddedByPratik
            public boolean disablemanagedSwitch(String srno) {
                boolean result = false;
                MyCommonAPIs.sleepi(10);
                scrollToMac(WebportalParam.ap5macaddress).scrollIntoView(true);
                MyCommonAPIs.sleepi(1);
                waitElement(enableDisableManagedSwitch(srno));
                enableDisableManagedSwitch(srno).click();
                MyCommonAPIs.sleepi(10);
                yesButtonforSwitch.click();
                MyCommonAPIs.sleepi(1);
                waitElement(confirmmationMessage);
                if (confirmmationMessage.exists()) {
                    String confmMsg = confirmmationMessage.getText();
                    System.out.println(confmMsg);
                    MyCommonAPIs.sleepi(1);
                    if (unmanagedStatus(srno).exists()) {
                        logger.info("Managed Unmanaged switch is working fine for AP");
                        result = true;    
                        MyCommonAPIs.sleepi(1);
                    }
                }
                return result;
            }
          
            //AddedByPratik
            public boolean verifyAllcountUnderSettingsdevicesoption() {
                boolean result = false;
                MyCommonAPIs.sleepi(10);
                waitElement(settingsIconOnHomePage);
                settingsIconOnHomePage.click();
                MyCommonAPIs.sleepi(2);
                waitElement(devicesOptionUnderSetting);
                devicesOptionUnderSetting.hover();
                devicesOptionUnderSetting.click();
                MyCommonAPIs.sleepi(30);
                waitElement(totaldevicesAvilable);
                String extractedText = totaldevicesAvilable.text();
                extractedText = extractedText.replace("Devices (", "").replace(")", "");
                int totalNoofDevicesShowing = Integer.parseInt(extractedText);
                int expectedNumber = 4;
                if ((totalNoofDevicesShowing==expectedNumber) && pageDetailsAnddevices.exists()) {
                    result = true;
                    logger.info("Total devices count showing correctly on devices page under settings");
                }
                return result;
            }
            
            //AddedByPratik
            public boolean verifyAllcountUnderOrgSettingsdevicestab() {
                boolean result = false;
                waitElement(totaldevicesAvilable);
                String extractedText = totaldevicesAvilable.text();
                extractedText = extractedText.replace("Devices(", "").replace(")", "");
                int totalNoofDevicesShowing = Integer.parseInt(extractedText);
                int expectedNumber = 4;
                if ((totalNoofDevicesShowing==expectedNumber) && pageDetailsAnddevices.exists()) {
                    result = true;
                    logger.info("Total devices count showing correctly on devices page under settings");
                }
                return result;
            }
            
            //AddedByPratik
            public boolean verifyAllcountUnderDevicesdashPage() {
                boolean result = false;
                MyCommonAPIs.sleepi(10);
                waitElement(totaldevicesAvilable);
                String extractedText = totaldevicesAvilable.text();
                extractedText = extractedText.replace("Devices (", "").replace(")", "");
                int totalNoofDevicesShowing = Integer.parseInt(extractedText);
                int expectedNumber = 4;
                if ((totalNoofDevicesShowing==expectedNumber) && pageDetailsAnddevices.exists()) {
                    result = true;
                    logger.info("Total devices count showing correctly on devices page under settings");
                }
                return result;
            }
            
            //AddedByPratik
            public boolean verifypowerModeonDevicedashPageAPstatus(String powerMode) {
                boolean result = false;
                open(WebportalParam.serverUrl+URLParam.hrefDevices);
                MyCommonAPIs.sleepi(10);
                waitElement(wbeAPAFPowemodeStatus(powerMode));
                if (wbeAPAFPowemodeStatus(powerMode).exists()) {
                    System.out.println(wbeAPAFPowemodeStatus(powerMode).getText());
                    result = true;
                    wbeAPAFPowemodeStatus(powerMode).doubleClick();
                    MyCommonAPIs.sleepi(10);
                }
                return result;
            }
            
            public void deleteAllDevices() {
                
                MyCommonAPIs.sleepi(10);
                waitReady();
                while (deviceList.exists()) {
                    logger.info("entered delete loop");
                    String devicename = deviceNameExt.text();
                    System.out.print(devicename);
                    logger.info("no ssid to delete");
                    deleteDeviceYes(devicename);
                    waitReady();
                }
            }
            
            public String validateOnboardingError(Map<String, String> map) {
                String text = "";
                clickAddDevice();
                waitElement(addDeviceBtn);
                serialNo.sendKeys(map.get("Serial Number"));
                MyCommonAPIs.sleepi(3);
                addDeviceBtn.click();
                MyCommonAPIs.sleepi(3);
                macAddress.sendKeys(map.get("MAC Address"));
                MyCommonAPIs.sleepi(5);
                next.click();
                MyCommonAPIs.sleepi(5);
                if (addDeviceErrorMsg.exists()) {
                    text = getText(addDeviceErrorMsg);
                    clickBoxFirstButton();
                }
                return text;
            }
            
            
            public String GenaraterandomSerial(String Prefix) {
                String serial="";
                Random random = new Random();
           
                long randomNumber = random.longs(1000000000L, 10000000000L).findFirst().getAsLong();
                System.out.println("Random 10-digit number: " + randomNumber);
                
                serial = Prefix +randomNumber;
                
                System.out.println("Serial Number is " + serial);
//                String Serial = String.valueOf(random.ints(1000000000,9999999999).findFirst().getAsInt());
                
                return serial;
            }
}
    
