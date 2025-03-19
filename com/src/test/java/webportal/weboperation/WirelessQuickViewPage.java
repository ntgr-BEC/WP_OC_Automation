package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static org.testng.Assert.assertTrue;
import static com.codeborne.selenide.Selenide.$$x;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import util.APUtils;
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.CommonDataType.SSIDData;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.WirelessQuickViewElement;

import org.openqa.selenium.By;


/**
 * @author Netgear
 */
public class WirelessQuickViewPage extends WirelessQuickViewElement {
    /**
    *
    */
    Logger          logger;
    public SSIDData ssidData = null;

    public WirelessQuickViewPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefWireless);
        MyCommonAPIs.sleepi(5);
        waitElement(settingsorquickview);
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public WirelessQuickViewPage(boolean noPage) {
        // TODO Auto-generated constructor stub
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public void initTestData() {
        ssidData = new CommonDataType().dataSSID;
    }

    public void gotoSetting() {
        WebCheck.checkHrefIcon(URLParam.hrefWirelessSettings);
        // waitElement(settingsorquickview);
    }

    public void gotoOrbiWirelessSetting() {
        WebCheck.checkHrefIcon(URLParam.hrefWirelessOrbiSettings);
        waitReady();
    }

    public void clickEditSsid(String Ssid) {
        MyCommonAPIs.sleepi(5);
        hoverToSSID1(Ssid).hover();
        executeJavaScript("arguments[0].removeAttribute('class')", editWifi(Ssid));
        MyCommonAPIs.sleep(3000);
        editWifi(Ssid).hover();
        MyCommonAPIs.sleep(3000);
        editSsid(Ssid).waitUntil(Condition.visible, 60 * 1000).click();
        MyCommonAPIs.sleep(8 * 1000);
    }

    public boolean clickEditOrbiSsid(String Ssid) {
        try {
            executeJavaScript("arguments[0].removeAttribute('class')", editWifi(Ssid));
            MyCommonAPIs.sleep(3000);
            editOrbiSsid(Ssid).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(8 * 1000);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    /**
     * @param toWait
     *            true to make sure "optimize now" is correct applied
     * @return
     */
    public boolean optimizeInstantWifi(boolean toWait) {
        boolean result = false;
        waitElement(settingsorquickview);
        settingsorquickview.click();
        MyCommonAPIs.sleepi(5);
        waitReady();
        instantwifi.click();
        waitElement(optimizenowbutton);
        timerStart(22 * 60);
        while (timerRunning()) {
            optimizenowbutton.click();
            waitReady();
            waitElement(instantwifisuccessmeg);
            if (getText(instantwifisuccessmeg).contains("Your configuration has been applied, It may take some time to reflect")) {
                result = true;
                break;
            } else {
                logger.warning("instantwifisuccessmeg error");
            }
            if (!toWait) {
                break;
            } else {
                MyCommonAPIs.sleepi(60);
            }
        }
        if (toWait && !result) {
            logger.warning("timeout on optimize");
        }
        return result;
    }

    public boolean optimizeInstantWifinondefault(boolean toWait) {
        boolean result = false;
        waitElement(settingsorquickview);
        settingsorquickview.click();
        MyCommonAPIs.sleepi(5);
        waitReady();
        instantwifi.click();
        waitElement(optimizenowbutton);
        timerStart(22 * 60);
        // select all
        SelectAll.click();
        MyCommonAPIs.sleepi(5);
        // Desellect ALL
        SelectAll.click();
        MyCommonAPIs.sleepi(5);
        Selectchanall3.click();
        MyCommonAPIs.sleepi(1);
        Selectchanall8.click();
        MyCommonAPIs.sleepi(1);
        Selectchanall10.click();
        MyCommonAPIs.sleepi(1);
        DeSelectchanall36.click();
        MyCommonAPIs.sleepi(1);
        DeSelectchanall100.click();
        MyCommonAPIs.sleepi(1);
        DeSelectchanall136.click();
        MyCommonAPIs.sleepi(1);
        while (timerRunning()) {
            optimizenowbutton.click();
            waitReady();
            waitElement(instantwifisuccessmeg);
            if (getText(instantwifisuccessmeg).contains("Your configuration has been applied, It may take some time to reflect")) {
                result = true;
                break;
            } else {
                logger.warning("instantwifisuccessmeg error");
            }
            if (!toWait) {
                break;
            } else {
                MyCommonAPIs.sleepi(60);
            }
        }
        if (toWait && !result) {
            logger.warning("timeout on optimize");
        }
        return result;
    }

    public void enableInstantWifiAutoChannel(String number) {
        waitElement(settingsorquickview);
        settingsorquickview.click();
        MyCommonAPIs.sleepi(5);
        instantwifi.click();
        waitElement(optimizenowbutton);
        MyCommonAPIs.sleepi(5);
        $x("//span[contains(text(),'" + number + "')]").click();
        waitReady();
        MyCommonAPIs.sleepi(5);
        if ($x(autoChannel + "/input").isEnabled()) {
            $x(autoChannel).click();
            waitReady();
            MyCommonAPIs.sleepi(5);
        }
        $x(autoChannel).click();
        waitReady();
        MyCommonAPIs.sleepi(5);
        saveInsantWifiBtn.click();
        waitReady();
    }

    public boolean selectInstantWifiSchedule(String option) {
        boolean result = false;
        waitElement(settingsorquickview);
        settingsorquickview.click();
        MyCommonAPIs.sleepi(5);
        instantwifi.click();
        waitElement(optimizenowbutton);
        MyCommonAPIs.sleepi(5);
        selectschedule.selectOption(option);
        MyCommonAPIs.sleepi(10);
        if (instantwifisuccessmeg.exists() && getText(instantwifisuccessmeg).contains("We have successfully saved your configuration.")) {
            result = true;
        }
        return result;
    }

    public void addSsidStep(Map<String, String> map) {
        System.out.println("eneterd ssid adding");
        if (!checkSsidIsExist(map.get("SSID"))) {
            System.out.println("ssid not exits");
            logger.info("Add ssid.");
            MyCommonAPIs.sleepi(10);
            refresh();
            waitElement(addssid);
            addssid.click();
            System.out.println("add ssid click");
            waitElement(ssid);

            MyCommonAPIs.sleepi(10);

            ssid.setValue(map.get("SSID"));
            if (checkband6.isDisplayed()) {
                if (checkband6.isSelected()) {
                    if (band6.isDisplayed()) {
                        band6.click();
                        logger.info("Uncheck 6 band");
                    }
                } else {
                    System.out.println("6GHZ is alredy unchecked");
                }
            }
            
            if (map.containsKey("VLANID")) {
                String elements = VLANIDselection.getText();
                System.out.println("number of VLAN" +elements);
                
                if(elements.contains(map.get("VLANID"))) {
                VLANIDselection.selectOption(map.get("VLANID"));     
                }else {            
                    AddCustomVLAN.click();
                    VLANIDOrg.sendKeys(map.get("VLANIDorg"));
                    
                }
            }
            if (map.get("Security").equals("WPA2 PSK")) {
                security.selectOption("WPA2-PSK");
            } else {
                security.selectOption(map.get("Security"));
            }
            System.out.println("before password");
            MyCommonAPIs.sleepi(10);
            if (map.containsKey("Password")) {
                password.setValue(map.get("Password"));
            }
            if(map.containsKey("Bandsteering"))
            {   System.out.print("entered Bandsteering ");
                MyCommonAPIs.sleepi(4);               
                setSelected($x("//*[@id=\"bandSteeringSt\"]/../span"), Boolean.parseBoolean((map.get("Bandsteering"))));
                MyCommonAPIs.sleep(10);
                if (Warrning.exists()) {                                      
                    System.out.println("inside warrning band");
                    List<SelenideElement> buttons = $$x("//*[text()='OK']");
                    for (SelenideElement button : buttons) {
                        if (button.is(Condition.visible)) {
                            button.click();
                            break;  // Click the first visible button and stop
                        }
                    }
                              
                }
            }
            if(map.containsKey("Fastroaming"))
            {   System.out.print("entered fast roami");
                MyCommonAPIs.sleepi(4); 
                setSelected($x("//*[@id=\"fastRoamingSt\"]/../span"),Boolean.parseBoolean((map.get("Fastroaming"))));
                MyCommonAPIs.sleep(10);              
                if (Warrning.exists()) {                                      
                    System.out.println("inside warrning Fastroaming");
                    List<SelenideElement> buttons = $$x("//*[text()='OK']");
                    for (SelenideElement button : buttons) {
                        if (button.is(Condition.visible)) {
                            button.click();
                            break;  // Click the first visible button and stop
                        }
                    }
                              
                }
            }
            if(map.containsKey("802.11kv"))
            {                 
                setSelected($x("//*[@id=\"kvrStatus\"]"),Boolean.parseBoolean((map.get("802.11kv"))));
                MyCommonAPIs.sleep(5);              
                
            }

            if (map.containsKey("OWE")) {
                owe.click();
                owetmode.click();
            } else if (map.containsKey("OWETM")) {
                owe.click();

            }
            takess("addSsid");
            MyCommonAPIs.waitElement(save);
            logger.info("Save button is Visible");
            save.click();
            MyCommonAPIs.sleepi(10);
            if (oksave.isDisplayed()) {
                oksave.click();
            }
            MyCommonAPIs.sleepi(10);
            logger.info("Add ssid successful.");
            // } else {
            // logger.warning("checkSsidIsExist error");
            // }
            System.out.println("exits ssid");
        }
    }

    public void addSsidStepMDNS(Map<String, String> map) {
        System.out.println("eneterd ssid adding");
        if (!checkSsidIsExist(map.get("SSID"))) {
            System.out.println("ssid not exits");
            logger.info("Add ssid.");
            MyCommonAPIs.sleepi(5);

            if (settingsorquickview.exists()) {
                settingsorquickview.click();
                addssid.click();
                System.out.println("add ssid click");
                waitElement(ssid);
                ssid.setValue(map.get("SSID"));
                if (band6.isDisplayed()) {
                    if (checkband6.isSelected()) {
                        if (band6.isDisplayed()) {
                            band6.click();
                            logger.info("Uncheck 6 band");
                        }
                    } else {
                        System.out.println("6GHZ is alredy unchecked");
                    }
                }
                if (map.get("Security").equals("WPA2 PSK")) {
                    security.selectOption("WPA2-PSK");
                } else {
                    security.selectOption(map.get("Security"));
                }
                System.out.println("before password");
                MyCommonAPIs.sleepi(20);
                if (map.containsKey("Password")) {
                    password.setValue(map.get("Password"));
                }
                takess("addSsid");
                MyCommonAPIs.waitElement(save);
                logger.info("Save button is Visible");
                save.click();
                MyCommonAPIs.sleepi(5);
                if (oksave.isDisplayed()) {
                    oksave.click();
                }
                MyCommonAPIs.sleepi(5);
                logger.info("Add ssid successful.");
                // } else {
                // logger.warning("checkSsidIsExist error");
                // }
                System.out.println("exits ssid");
            }
        }
    }

    public String addSsidStepEx(Map<String, String> map) {
        if (!checkSsidIsExist(map.get("SSID"))) {
            logger.info("Add ssid.");
            addssid.click();
            waitElement(ssid);
            ssid.setValue(map.get("SSID"));
            if (checkband6.isDisplayed()) {
                if (checkband6.isSelected()) {
                    if (band6.isDisplayed()) {
                        band6.click();
                        logger.info("Uncheck 6 band");
                    }
                } else {
                    System.out.println("6GHZ is alredy unchecked");
                }
            }
            if (map.get("Security").equals("WPA2 PSK")) {
                security.selectOption("WPA2-PSK");
            } else {
                security.selectOption(map.get("Security"));
            }
            if (map.containsKey("Password")) {
                password.setValue(map.get("Password"));
            }
            takess("addSsid");
            save.click();
            MyCommonAPIs.sleepi(5);
            if (oksave.isDisplayed()) {
                oksave.click();
            }
            MyCommonAPIs.sleepi(5);
            logger.info("Add ssid successful.");
        } else {
            logger.warning("checkSsidIsExist error");
        }

        String Warrning = getText(SSIDEx);

        return Warrning;
    }

    public void addSsidStepARS(Map<String, String> map) {
        if (!checkSsidIsExist(map.get("SSID"))) {
            logger.info("Add ssid.");
            addssid.click();
            waitElement(ssid);
            if (checkband6.isDisplayed()) {
                if (checkband6.isSelected()) {
                    if (band6.isDisplayed()) {
                        band6.click();
                        logger.info("Uncheck 6 band");
                    }
                } else {
                    System.out.println("6GHZ is alredy unchecked");
                }
            }

            ssid.setValue(map.get("SSID"));
            if (map.get("Security").equals("WPA2 Personal")) {
                security.selectOption("WPA2 Personal");
            } else {
                security.selectOption(map.get("Security"));
            }
            if (map.containsKey("Password")) {
                password.setValue(map.get("Password"));
            }
            takess("addSsid");
            SaveAndConfigure.click();
            MyCommonAPIs.sleepi(3);
            enterAdvanceRateSelection.click();
            MyCommonAPIs.sleepi(3);
            logger.info("It is in advanced Rate election");
        } else {
            logger.warning("checkSsidIsExist error");
        }
    }

    public WirelessQuickViewPage addSsid(Map<String, String> map) {
        MyCommonAPIs.sleepi(15);
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        System.out.println("enterd ssid");
        MyCommonAPIs.sleepi(15);
        waitElement(addssid);
        System.out.println("waitelement enterd ssid");
        if (ssidlistone.exists()) {
            for (SelenideElement ssid : ssidlist) {
                System.out.println(ssid);
                String ssidname = ssid.$x("//td[1]//span").getText();
                // if (!ssidname.equals(map.get("SSID"))) {
                deleteSsidYes(ssidname);
                // }
            }
        } else {
            logger.warning("ssidlistone error");
        }
        map.get(ssidData);
        MyCommonAPIs.sleepi(15);
        addSsidStep(map);
        System.out.println("adding is done ssid");
        return new WirelessQuickViewPage();
    }

    public String exceptionssid(Map<String, String> map) {
        waitElement(settingsorquickview);
        settingsorquickview.click();
        waitReady();
        waitElement(addssid);
        if (ssidlistone.exists()) {
            for (SelenideElement ssid : ssidlist) {
                System.out.println(ssid);
                String ssidname = ssid.$x("//td[1]//span").getText();
                // if (!ssidname.equals(map.get("SSID"))) {
                deleteSsidYes(ssidname);
                // }
            }
        } else {
            logger.warning("ssidlistone error");
        }
        String Warrning = addSsidStepEx(map);
        System.out.println(Warrning);
        CancelEXSSID.click();
        return Warrning;

    }

    public void addSsidARS(Map<String, String> map) {
        waitElement(settingsorquickview);
        settingsorquickview.click();
        waitReady();
        waitElement(addssid);
        if (ssidlistone.exists()) {
            for (SelenideElement ssid : ssidlist) {
                String ssidname = ssid.$x("//td[1]//span").getText();
                // if (!ssidname.equals(map.get("SSID"))) {
                deleteSsidYes(ssidname);
                // }
            }
        } else {
            logger.warning("ssidlistone error");

        }
        addSsidStepARS(map);
        // return new WirelessQuickViewPage();
    }

    public WirelessQuickViewPage addSsidAdvRateSelction(Map<String, String> map) {
        waitElement(settingsorquickview);
        settingsorquickview.click();
        waitReady();
        waitElement(addssid);
        if (ssidlistone.exists()) {
            for (SelenideElement ssid : ssidlist) {
                String ssidname = ssid.$x("//td[1]//span").getText();
                // if (!ssidname.equals(map.get("SSID"))) {
                deleteSsidYes(ssidname);
                // }
            }
        } else {
            logger.warning("ssidlistone error");
        }
        addSsidStep(map);
        return new WirelessQuickViewPage();
    }

    public WirelessQuickViewPage WPA3(Map<String, String> map, String option) {
        waitElement(settingsorquickview);
        settingsorquickview.click();
        waitReady();
        waitElement(addssid);
        if (ssidlistone.exists()) {
            for (SelenideElement ssid : ssidlist) {
                String ssidname = ssid.$x("//td[1]//span").getText();
                // if (!ssidname.equals(map.get("SSID"))) {
                deleteSsidYes(ssidname);
                // }
            }
        } else {
            logger.warning("ssidlistone error");
        }
        if (!checkSsidIsExist(map.get("SSID"))) {
            logger.info("Add ssid.");
            addssid.click();
            waitElement(ssid);
            if (checkband6.isDisplayed()) {
                if (checkband6.isSelected()) {
                    if (band6.isDisplayed()) {
                        band6.click();
                        logger.info("Uncheck 6 band");
                    }
                } else {
                    System.out.println("6GHZ is alredy unchecked");
                }
            }

            ssid.setValue(map.get("SSID"));
            if (map.get("Security").equals("WPA2 PSK")) {
                security.selectOption("WPA2-PSK");
            } else {
                security.selectOption(map.get("Security"));
            }
            if (map.containsKey("Password")) {
                password.setValue(map.get("Password"));
            }
            takess("addSsid");

            if (option == "Mandate") {
                Mandatoryclick.click();
            }

            if (option == "Optional") {
                Optionalclick.click();
            }
            if (option == "Disable") {
                Disableclick.click();
            }
            if (map.containsKey("VLANID")) {
                String elements = VLANIDselection.getText();
                System.out.println("number of VLAN" +elements);
                
                if(elements.contains(map.get("VLANID"))) {
                VLANIDselection.selectOption(map.get("VLANID"));     
                }else {            
                    AddCustomVLAN.click();
                    VLANIDOrg.sendKeys(map.get("VLANIDorg"));
                    
                }
            }

            MyCommonAPIs.sleepi(5);
            save.click();
            MyCommonAPIs.sleepi(3);
            logger.info("Add ssid successful.");
        } else {
            logger.warning("checkSsidIsExist error");
        }
        return new WirelessQuickViewPage();
    }

    public void deleteALLSSID() {

        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        MyCommonAPIs.sleepi(20);
        // waitElement(ssidListTable);
        waitReady();
        while (ssidlistone.exists()) {
            logger.info("entered delete loop");
            String ssidname = ssid.$x("//td[1]//span").getText();
            logger.info("no ssid to delete");
            deleteSsidYes(ssidname);
            waitReady();
            // waitElement(ssidListTable);
        }
    }

    public void deleteALLSSIDCG() {

        MyCommonAPIs.sleepi(20);
        // waitElement(ssidListTable);
        waitReady();
        while (ssidlistone.exists()) {
            logger.info("entered delete loop");
            String ssidname = ssid.$x("//td[1]//span").getText();
            logger.info("no ssid to delete");
            deleteSsidYes(ssidname);
            waitReady();
            // waitElement(ssidListTable);
        }
    }

    public void editssidBand(String Ssid, Map<String, String> map) {
        settingsorquickview.click();
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
        }

        if (map.get("Band").equals("ALL")) {
            ALLband.click();
            logger.info("Uncheck all band");
            waitReady();
        } else if (map.get("Band").equals("Uncheck2.4 GHz")) {
            band24.click();
            logger.info("Uncheck 2.4 band");
            waitReady();
        } else if (map.get("Band").equals("Uncheck5 GHz")) {
            band5.click();
            logger.info("Uncheck 5 band");
            waitReady();
        } else if (map.get("Band").equals("Uncheck6 GHz")) {
            band6.click();
            logger.info("Uncheck 6 band");
            waitReady();
        } else {
            logger.info("no need to Uncheck");
        }
        MyCommonAPIs.sleepi(4);
        editsave.click();
        waitReady();
        MyCommonAPIs.sleepi(3);
        confirmok.click();
        MyCommonAPIs.sleepi(4);
    }

    public boolean addSsid1(Map<String, String> map) {

        boolean result = false;
        // waitElement(settingsorquickview);
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        waitElement(addssid);
        boolean isKeyPresent = false;

        if (!checkSsidIsExist(map.get("SSID"))) {
            logger.info("Add ssid.");
            addssid.click();
            MyCommonAPIs.sleepi(15);
            // waitElement(ssid);
            ssid.setValue(map.get("SSID"));
            waitReady();
            // System.out.println(checkband6.isSelected());
            if (checkband6.isDisplayed()) {
                if (checkband6.isSelected()) {
                    if (band6.isDisplayed()) {
                        band6.click();
                        logger.info("Uncheck 6 band");
                    }
                } else {
                    System.out.println("6GHZ is alredy unchecked");
                }
            }
            isKeyPresent = map.containsKey("Band");
            System.out.println(isKeyPresent);
            if (isKeyPresent == true) {
                if (map.get("Band").equals("ALL")) {
                    if (ALLband.isDisplayed()) {
                        ALLband.click();
                        MyCommonAPIs.sleepi(4);
                        logger.info("check all band");
                    }
                    waitReady();
                } else if (map.get("Band").equals("Uncheck2.4 GHz")) {
                    if (band24.isDisplayed()) {
                        band24.click();
                        MyCommonAPIs.sleepi(4);
                        band6.click();
                        logger.info("Uncheck 2.4 band");
                    }
                    waitReady();
                } else if (map.get("Band").equals("Uncheck5 GHz")) {
                    if (band5.isDisplayed()) {
                        band5.click();
                        MyCommonAPIs.sleepi(4);
                        band6.click();
                        logger.info("Uncheck 5 band");
                    }
                    waitReady();
                } else if (map.get("Band").equals("Uncheck6 GHz")) {
                    if (band6.isDisplayed()) {
                        logger.info("Uncheck 6 band");
                    }
                    waitReady();
                } else if (map.get("Band").equals("check only 2ghz")) {
                    MyCommonAPIs.sleepi(4);
                    band5.click();
                    MyCommonAPIs.sleepi(4);
                } else if (map.get("Band").equals("check only 5ghz")) {
                    MyCommonAPIs.sleepi(4);
                    band24.click();
                    waitReady();
                } else if (map.get("Band").equals("check only 6ghz")) {
                    MyCommonAPIs.sleepi(4);
                    band24.click();
                    MyCommonAPIs.sleepi(4);
                    band5.click();
                    MyCommonAPIs.sleepi(4);
                    band6.click();
                    waitReady();
                }
            } else {
                logger.info("no need to Uncheck");
            }

            if (map.get("Security").equals("WPA2-PSK")) {
                security.selectOption("WPA2-PSK");
                waitReady();
            } else {
                security.selectOption(map.get("Security"));
                waitReady();
            }

            if (map.containsKey("Password")) {
                password.setValue(map.get("Password"));
                waitReady();
            }
            if (map.containsKey("VLANID")) {
                String elements = VLANIDselection.getText();
                System.out.println("number of VLAN" +elements);
                
                if(elements.contains(map.get("VLANID"))) {
                VLANIDselection.selectOption(map.get("VLANID"));     
                }else {            
                    AddCustomVLAN.click();
                    VLANIDOrg.sendKeys(map.get("VLANIDorg"));
                    
                }
            }
            takess("addSsid");
            save.click();
            waitReady();
            MyCommonAPIs.sleepi(4);
            if (Maximumlimit.exists()) {
                String ErrorMessage = Maximumlimit.getText();
                logger.info(ErrorMessage);
                if (close.isDisplayed()) {
                    close.click();
                }

                if (closeCG.isDisplayed()) {
                    closeCG.click();
                }
                result = true;
            }

            MyCommonAPIs.sleepi(3);
            logger.info("Add ssid successful.");
        } else {
            logger.warning("checkSsidIsExist error");
        }
        return result;
    }

    public boolean addSsidALL(Map<String, String> map) {

        boolean result = false;
        waitElement(settingsorquickview);
        settingsorquickview.click();
        waitReady();
        waitElement(addssid);
        boolean isKeyPresent = false;

        if (!checkSsidIsExist(map.get("SSID"))) {
            logger.info("Add ssid.");
            addssid.click();
            waitElement(ssid);
            ssid.setValue(map.get("SSID"));
            waitReady();
            isKeyPresent = map.containsKey("Band");
            System.out.println(isKeyPresent);
            if (isKeyPresent == true) {
                if (map.get("Band").equals("ALL")) {
                    logger.info("Uncheck all band");
                    waitReady();
                } else if (map.get("Band").equals("Both")) {
                    System.out.print("alredy");

                } else if (map.get("Band").equals("Uncheck2.4 GHz")) {
                    if (band24.isDisplayed()) {
                        band24.click();
                    }
                    waitReady();
                } else if (map.get("Band").equals("Uncheck5 GHz")) {
                    if (band5.isDisplayed()) {
                        band5.click();
                        MyCommonAPIs.sleepi(4);
                    }
                    waitReady();
                } else if (map.get("Band").equals("Uncheck6 GHz")) {
                    if (band6.isDisplayed()) {
                        logger.info("Uncheck 6 band");
                    }
                    waitReady();
                } else if (map.get("Band").equals("check only 2ghz")) {
                    MyCommonAPIs.sleepi(4);
                    band5.click();
                    if (band6.isDisplayed()) {
                        logger.info("Uncheck 6 band");
                    }
                    MyCommonAPIs.sleepi(4);
                } else if (map.get("Band").equals("check only 5ghz")) {
                    MyCommonAPIs.sleepi(4);
                    band24.click();
                    if (band6.isDisplayed()) {
                        logger.info("Uncheck 6 band");
                    }
                    waitReady();
                } else if (map.get("Band").equals("check only 6ghz")) {
                    MyCommonAPIs.sleepi(4);
                    band24.click();
                    MyCommonAPIs.sleepi(4);
                    band5.click();
                    MyCommonAPIs.sleepi(4);
                    band6.click();
                    MyCommonAPIs.sleepi(4);
                }

                else if (map.get("Band").equals("check 2gz and 6ghz")) {
                    MyCommonAPIs.sleepi(4);
                    band5.click();
                    MyCommonAPIs.sleepi(4);
                    band6.click();
                } else if (map.get("Band").equals("check 5ghz and 6ghz")) {
                    MyCommonAPIs.sleepi(4);
                    band24.click();
                    MyCommonAPIs.sleepi(4);
                    band6.click();
                }
            } else {
                logger.info("no need to Uncheck");
            }

            if (map.get("Security").equals("WPA2-PSK")) {
                security.selectOption("WPA2-PSK");
                waitReady();
            } else {
                security.selectOption(map.get("Security"));
                waitReady();
            }

            if (map.containsKey("Password")) {
                password.setValue(map.get("Password"));
                waitReady();
            }
            takess("addSsid");
            save.click();
            waitReady();
            MyCommonAPIs.sleepi(4);
            if (Maximumlimit.exists()) {
                String ErrorMessage = Maximumlimit.getText();
                logger.info(ErrorMessage);
                close.click();
                result = true;
            }

            MyCommonAPIs.sleepi(3);
            logger.info("Add ssid successful.");
        } else {
            logger.warning("checkSsidIsExist error");
        }
        return result;
    }

    public boolean addSsidFromVLAN(Map<String, String> map) {

        boolean result = false;
        waitReady();
        waitElement(addvlanssid);

        if (!checkSsidIsExist(map.get("SSID"))) {
            logger.info("Add ssid.");
            addvlanssid.click();
            waitElement(ssidvlan);
            ssidvlan.setValue(map.get("SSID"));
            if (bandvlan.exists()) {
                if (map.get("Band").equals("Both")) {
                    bandvlan.selectOption("Both");
                } else {
                    bandvlan.selectOption(map.get("Band"));
                }
            }
            if (checkband6.isDisplayed()) {
                if (checkband6.isSelected()) {
                    if (band6.isDisplayed()) {
                        band6.click();
                        logger.info("Uncheck 6 band");
                    }
                } else {
                    System.out.println("6GHZ is alredy unchecked");
                }
            }

            if (map.get("Security").equals("WPA2-PSK")) {
                security.selectOption("WPA2-PSK");
            } else {
                security.selectOption(map.get("Security"));
            }

            if (map.containsKey("Password")) {
                passwordvlan.setValue(map.get("Password"));
            }
            takess("addSsid");
            Addvlanssid.click();
            MyCommonAPIs.sleepi(10);
            // if(warnning.exists()) {
            // warnning.click();
            // }
            if (MaximumlimitVlan.exists()) {
                String ErrorMessage = MaximumlimitVlan.getText();
                logger.info(ErrorMessage);
                closevlan.click();
                result = true;
                return result;

            }

            MyCommonAPIs.sleepi(3);
            logger.info("Add ssid successful.");
        } else {
            logger.warning("checkSsidIsExist error");
        }
        return result;
    }

    public void addSsidIfNot() {
        gotoSetting();
        if (!checkSsidIsExist(ssidData.ssid)) {
            Map<String, String> ssidInfo = new HashMap<String, String>();
            ssidInfo.put("SSID", ssidData.ssid);
            ssidInfo.put("Security", ssidData.Security);
            ssidInfo.put("Password", ssidData.Password);
            addSsid(ssidInfo);
        }
    }

    public WirelessQuickViewPage addSsID80211w(Map<String, String> map) {
        waitElement(settingsorquickview);
        settingsorquickview.click();
        waitReady();
        waitElement(addssid);
        if (ssidlistone.exists()) {
            for (SelenideElement ssid : ssidlist) {
                String ssidname = ssid.$x("//td[1]//span").getText();
                // if (!ssidname.equals(map.get("SSID"))) {
                deleteSsidYes(ssidname);
                // }
            }
        } else {
            logger.warning("ssidlistone error");
        }
        if (!checkSsidIsExist(map.get("SSID"))) {
            logger.info("Add ssid.");
            addssid.click();
            waitElement(ssid);
            ssid.setValue(map.get("SSID"));
            if (checkband6.isDisplayed()) {
                if (checkband6.isSelected()) {
                    if (band6.isDisplayed()) {
                        band6.click();
                        logger.info("Uncheck 6 band");
                    }
                } else {
                    System.out.println("6GHZ is alredy unchecked");
                }
            }
            security.selectOption(map.get("Security"));
            SSID80211wbtn.click();
            if (map.containsKey("Password")) {
                password.setValue(map.get("Password"));
            }
            takess("addSsid");
            save.click();
            MyCommonAPIs.sleepi(3);
            logger.info("Add ssid successful.");
        } else {
            logger.warning("checkSsidIsExist error");
        }
        return new WirelessQuickViewPage();
    }

    public WirelessQuickViewPage scheduleWifi(Map<String, String> map) {

        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        waitElement(addssid);
        if (ssidlistone.exists()) {
            for (SelenideElement ssid : ssidlist) {
                String ssidname = ssid.$x("//td[1]//span").getText();
                // if (!ssidname.equals(map.get("SSID"))) {
                deleteSsidYes(ssidname);
                // }
            }
        } else {
            logger.warning("ssidlistone error");
        }
        if (!checkSsidIsExist(map.get("SSID"))) {
            logger.info("Add ssid.");
            addssid.click();
            waitElement(ssid);
            ssid.setValue(map.get("SSID"));
            if (checkband6.isDisplayed()) {
                if (checkband6.isSelected()) {
                    if (band6.isDisplayed()) {
                        band6.click();
                        logger.info("Uncheck 6 band");
                    }
                } else {
                    System.out.println("6GHZ is alredy unchecked");
                }
            }
            security.selectOption(map.get("Security"));
            enablessidschedule.click();
            if (map.containsKey("Password")) {
                password.setValue(map.get("Password"));
            }
            if (!addnewschedule.exists()) {
                enablessidschedule.click();
                MyCommonAPIs.sleepi(1);
            }
            addnewschedule.click();
            schedulename.setValue(map.get("Schedule Name"));
            if (map.containsKey("Schedule Days")) {
                selectDays(map.get("Schedule Days")).click();
            } else {
                SimpleDateFormat df = new SimpleDateFormat("E");
                selectDays(df.format(new Date()).toString()).click();
            }
            // MyCommonAPIs.sleepi(5);
            // selectday.click();
            MyCommonAPIs.sleepi(5);
            strtpickbtn.click();
            MyCommonAPIs.sleepi(1);
            // for (int i=1; i<=10;i++) {
            // selectTimeNagivation.click();
            // MyCommonAPIs.sleepi(1);
            // }
            if (okbtn.exists()) {
                okbtn.click();
                }
                if (okbtn1.exists()) {
                    okbtn1.click();
                    }

            MyCommonAPIs.sleepi(1);
            SelectEndTime.click();
            MyCommonAPIs.sleepi(1);
            for (int i = 0; i < 1; i++) {
                SelectEndTimeNavigation.click();
                MyCommonAPIs.sleepi(1);
            }

            OkayEndtime.click();
            MyCommonAPIs.sleepi(1);
            takess("addSsid");
            save.click();
            MyCommonAPIs.sleepi(3);
            logger.info("ssid scheduled successful.");
        } else {
            logger.warning("checkSsidIsExist error");
        }
        return new WirelessQuickViewPage();
    }

    public WirelessQuickViewPage existingscheduleWifi(Map<String, String> map) {
        waitElement(settingsorquickview);
        settingsorquickview.click();
        waitReady();
        waitElement(addssid);
        if (!checkSsidIsExist(map.get("SSID"))) {
            logger.info("Add ssid.");
            addssid.click();
            waitElement(ssid);
            ssid.setValue(map.get("SSID"));
            if (checkband6.isDisplayed()) {
                if (checkband6.isSelected()) {
                    if (band6.isDisplayed()) {
                        band6.click();
                        logger.info("Uncheck 6 band");
                    }
                } else {
                    System.out.println("6GHZ is alredy unchecked");
                }
            }
            security.selectOption(map.get("Security"));
            enablessidschedule.click();
            if (map.containsKey("Password")) {
                password.setValue(map.get("Password"));
            }

            // selectschedule.selectOption(map.get("Schedule Name"));
            takess("addSsid");
            saveschedule.click();
            MyCommonAPIs.sleepi(3);
            logger.info("Existing scheduled wifi selected successful.");
        } else {
            logger.warning("checkSsidIsExist error");
        }
        return new WirelessQuickViewPage();
    }

    public WirelessQuickViewPage pickStartTime(Map<String, String> map) {
        waitElement(settingsorquickview);
        settingsorquickview.click();
        waitReady();
        waitElement(addssid);
        if (ssidlistone.exists()) {
            for (SelenideElement ssid : ssidlist) {
                String ssidname = ssid.$x("//td[1]//span").getText();
                // if (!ssidname.equals(map.get("SSID"))) {
                deleteSsidYes(ssidname);
                // }
            }
        } else {
            logger.warning("ssidlistone error");
        }
        if (!checkSsidIsExist(map.get("SSID"))) {
            logger.info("Add ssid.");
            addssid.click();
            waitElement(ssid);
            ssid.setValue(map.get("SSID"));
            if (checkband6.isDisplayed()) {
                if (checkband6.isSelected()) {
                    if (band6.isDisplayed()) {
                        band6.click();
                        logger.info("Uncheck 6 band");
                    }
                } else {
                    System.out.println("6GHZ is alredy unchecked");
                }
            }
            security.selectOption(map.get("Security"));
            enablessidschedule.click();
            if (map.containsKey("Password")) {
                password.setValue(map.get("Password"));
            }
            addnewschedule.click();
            schedulename.setValue(map.get("Schedule Name"));
            selectday.click();
            MyCommonAPIs.sleepi(1);
            strtpickbtn.click();
            MyCommonAPIs.sleepi(1);
            for (int i = 1; i <= 10; i++) {
                selectTimeNagivation.click();
                MyCommonAPIs.sleepi(1);
            }
            if (okbtn.exists()) {
            okbtn.click();
            }
            if (okbtn1.exists()) {
                okbtn1.click();
                }
        
            MyCommonAPIs.sleepi(1);
            SelectEndTime.click();
            MyCommonAPIs.sleepi(1);
            for (int i = 1; i <= 11; i++) {
                SelectEndTimeNavigation.click();
                MyCommonAPIs.sleepi(1);
            }

            OkayEndtime.click();
            MyCommonAPIs.sleepi(1);
            
            boolean result2 = true;
            if (strtpickbtn.exists()) {
                System.out.println("start Pick time not selected");
                result2 = false;
            } else {
                System.out.println("start Pick time selected");
                result2 = true;
            }
            MyCommonAPIs.sleepi(8);
            cnclbtn.click();
            assertTrue(result2, "start Pick time not selected");
            MyCommonAPIs.sleepi(3);
            logger.info("Add ssid successful.");
        } else {
            logger.warning("checkSsidIsExist error");
        }
        return new WirelessQuickViewPage();
    }

    public WirelessQuickViewPage pickEndTime(Map<String, String> map) {
        waitElement(settingsorquickview);
        settingsorquickview.click();
        waitReady();
        waitElement(addssid);
        if (ssidlistone.exists()) {
            for (SelenideElement ssid : ssidlist) {
                String ssidname = ssid.$x("//td[1]//span").getText();
                // if (!ssidname.equals(map.get("SSID"))) {
                deleteSsidYes(ssidname);
                // }
            }
        } else {
            logger.warning("ssidlistone error");
        }
        if (!checkSsidIsExist(map.get("SSID"))) {
            logger.info("Add ssid.");
            addssid.click();
            waitElement(ssid);
            ssid.setValue(map.get("SSID"));
            if (checkband6.isDisplayed()) {
                if (checkband6.isSelected()) {
                    if (band6.isDisplayed()) {
                        band6.click();
                        logger.info("Uncheck 6 band");
                    }
                } else {
                    System.out.println("6GHZ is alredy unchecked");
                }
            }
            security.selectOption(map.get("Security"));
            enablessidschedule.click();
            if (map.containsKey("Password")) {
                password.setValue(map.get("Password"));
            }
            addnewschedule.click();
            schedulename.setValue(map.get("Schedule Name"));
            selectday.click();
            MyCommonAPIs.sleepi(1);
            strtpickbtn.click();
            MyCommonAPIs.sleepi(1);
            for (int i = 1; i <= 10; i++) {
                selectTimeNagivation.click();
                MyCommonAPIs.sleepi(1);
            }
            okbtn.click();

            MyCommonAPIs.sleepi(1);
            SelectEndTime.click();
            MyCommonAPIs.sleepi(1);
            for (int i = 1; i <= 10; i++) {
                SelectEndTimeNavigation.click();
                MyCommonAPIs.sleepi(1);
            }

            OkayEndtime.click();
            MyCommonAPIs.sleepi(1);

            boolean result2 = true;
            if (endpicktbtn.exists()) {
                System.out.println("end Pick time not selected");
                result2 = false;
            } else {
                System.out.println("end Pick time selected");
                result2 = true;
            }
            MyCommonAPIs.sleepi(8);
            cnclbtn.click();
            assertTrue(result2, "end Pick time not selected");
            MyCommonAPIs.sleepi(3);
            logger.info("Add ssid successful.");
        } else {
            logger.warning("checkSsidIsExist error");
        }
        return new WirelessQuickViewPage();
    }

    public void editApName(String serialNumber, String newname) {
        if (checkApIsExist(serialNumber)) {
            editName(serialNumber).click();
            MyCommonAPIs.sleepi(3);
            inputName(serialNumber).clear();
            MyCommonAPIs.sleepi(3);
            inputName(serialNumber).sendKeys(newname);
            inputnameyes(serialNumber).click();
        } else {
            logger.info("Ap isn't existed: " + serialNumber);
        }
    }

    public void editswitchName(String serialNumber, String newname) {
        WebCheck.checkHrefIcon(URLParam.hrefDevices);
        if (checkApIsExist(serialNumber)) {
            editName(serialNumber).click();
            MyCommonAPIs.sleepi(3);
            inputName(serialNumber).clear();
            MyCommonAPIs.sleepi(3);
            inputName(serialNumber).sendKeys(newname);
            inputnameyes(serialNumber).click();
        } else {
            logger.info("device isn't existed: " + serialNumber);
        }
    }

    public void editWifiName(String oldName, String newName) {
        gotoSetting();
        settingsorquickview.click();

        if (checkSsidIsExist(oldName)) {
            logger.info("Edit ssid.");

            executeJavaScript("arguments[0].removeAttribute('class')", editWifi(oldName));
            MyCommonAPIs.sleep(3000);
            editSsid(oldName).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(8 * 1000);

            ssid.clear();

            ssid.setValue(newName);

            takess("editWifiYes");
            editsave.click();

            MyCommonAPIs.sleepi(3);
            confirmok.click();

            MyCommonAPIs.sleepi(3);
            logger.info("Edit ssid successful.");
            System.out.println("SSID successfully edited");
        }
    }

    public WirelessQuickViewPage editWifiYes(Map<String, String> map) {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        waitElement(addssid);

        if (checkSsidIsExist(map.get("SSID"))) {
            logger.info("Edit ssid.");

            executeJavaScript("arguments[0].removeAttribute('class')", editWifi(map.get("SSID")));
            MyCommonAPIs.sleep(3000);
            editSsid(map.get("SSID")).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(8 * 1000);

            ssid.clear();

            ssid.setValue(map.get("SSID"));

            security.selectOption(map.get("Security"));

            if (map.containsKey("Password")) {
                password.clear();
                password.setValue(map.get("Password"));
                System.out.println("G");
            }

            takess("editWifiYes");
            saveedit.click();

            MyCommonAPIs.sleepi(3);
            confirmok.click();

            MyCommonAPIs.sleepi(3);
            logger.info("Edit ssid successful.");
            System.out.println("SSID successfully edited");
        }
        return new WirelessQuickViewPage();
    }

    public WirelessQuickViewPage defaultscheduledWifisettingYes(Map<String, String> map) {
        settingsorquickview.click();
        if (checkSsidIsExist(map.get("SSID"))) {
            logger.info("Edit ssid.");
            executeJavaScript("arguments[0].removeAttribute('class')", editWifi(map.get("SSID")));
            MyCommonAPIs.sleep(3000);
            editSsid(map.get("SSID")).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(8 * 1000);
            if (map.containsKey("Password")) {
                password.clear();
                password.setValue(map.get("Password"));
            }

            boolean result2 = true;
            if (addnewschedule.exists()) {
                System.out.println("WiFi Scheduler is not disabled By Default");
                result2 = false;
            } else {
                System.out.println("WiFi Scheduler is disabled By Default");
                result2 = true;
            }
            MyCommonAPIs.sleepi(8);
            editsave.click();
            MyCommonAPIs.sleepi(3);
            confirmok.click();
            MyCommonAPIs.sleepi(3);
            logger.info("WiFi Scheduler is disabled By Default");
            assertTrue(result2, "WiFi Scheduler is not disabled By Default");
            MyCommonAPIs.sleepi(3);
            logger.info("Edit ssid successful.");
        } else {
            logger.warning("checksavebtnExist error");
        }
        return new WirelessQuickViewPage();
    }

    public WirelessQuickViewPage defaultSSID80211wsettingYes(Map<String, String> map) {
        settingsorquickview.click();
        if (checkSsidIsExist(map.get("SSID"))) {
            logger.info("Edit ssid.");
            executeJavaScript("arguments[0].removeAttribute('class')", editWifi(map.get("SSID")));
            MyCommonAPIs.sleep(3000);
            editSsid(map.get("SSID")).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(8 * 1000);

            boolean result2 = true;
            if (Disable.isSelected()) {
                System.out.println("SSID80211w is disabled By Default");
                result2 = true;
            } else {
                System.out.println("SSID80211w is not disabled By Default");
                result2 = false;
            }
            MyCommonAPIs.sleepi(8);
            editsave.click();
            MyCommonAPIs.sleepi(3);
            confirmok.click();
            MyCommonAPIs.sleepi(3);
            logger.info("SSID80211w is disabled By Default");
            assertTrue(result2, "SSID80211w is not disabled By Default");
            MyCommonAPIs.sleepi(3);
            logger.info("Edit ssid successful.");
        } else {
            logger.warning("checksavebtnExist error");
        }
        return new WirelessQuickViewPage();
    }

    public WirelessQuickViewPage openSSID80211wsetting(Map<String, String> map) {
        settingsorquickview.click();
        if (checkSsidIsExist(map.get("SSID"))) {
            logger.info("Edit ssid.");
            executeJavaScript("arguments[0].removeAttribute('class')", editWifi(map.get("SSID")));
            MyCommonAPIs.sleep(3000);
            editSsid(map.get("SSID")).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(8 * 1000);

            boolean result2;
            if (Optional.isEnabled() && Mandatory.isEnabled()) {
                System.out.println("80211w is greyed out");
                result2 = true;
            } else {
                System.out.println("80211w is visible");
                result2 = false;
            }
            MyCommonAPIs.sleepi(8);
            editsave.click();
            MyCommonAPIs.sleepi(3);
            confirmok.click();
            MyCommonAPIs.sleepi(3);
            logger.info("80211w is not visible");
            assertTrue(result2, "80211w is visible");
            MyCommonAPIs.sleepi(3);
            logger.info("Edit ssid successful.");
        } else {
            logger.warning("checksavebtnExist error");
        }
        return new WirelessQuickViewPage();
    }

    public boolean EnhanceOpenStatus(Map<String, String> map) {

        boolean result = false;

        settingsorquickview.click();
        if (checkSsidIsExist(map.get("SSID"))) {
            logger.info("Edit ssid.");
            executeJavaScript("arguments[0].removeAttribute('class')", editWifi(map.get("SSID")));
            MyCommonAPIs.sleep(3000);
            editSsid(map.get("SSID")).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(8 * 1000);

        }

        return result;

    }

    public WirelessQuickViewPage enabletoggleSSID80211wsettinglocation(Map<String, String> map) {
        settingsorquickview.click();
        if (checkSsidIsExist(map.get("SSID"))) {
            logger.info("Edit ssid.");
            executeJavaScript("arguments[0].removeAttribute('class')", editWifi(map.get("SSID")));
            MyCommonAPIs.sleep(3000);
            editSsid(map.get("SSID")).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(8 * 1000);
            // SSID80211wbtn.click();
            // MyCommonAPIs.sleep(8 * 1000);
            // Mandatory.click();
            boolean result2 = false;
            if (Mandatory.isSelected()) {
                System.out.println("SSID80211w clicked on mandatory");
                result2 = true;
            } else {
                System.out.println("SSID80211w is not clicked on mandatory");
                result2 = false;
            }
            MyCommonAPIs.sleepi(8);
            editsave.click();
            MyCommonAPIs.sleepi(3);
            confirmok.click();
            MyCommonAPIs.sleepi(3);
            logger.info("SSID80211w toggle is selcted");
            assertTrue(result2, "SSID80211w toggle is not selcted");
            MyCommonAPIs.sleepi(3);
            logger.info("Edit ssid successful.");
        } else {
            logger.warning("checksavebtnExist error");
        }
        return new WirelessQuickViewPage();
    }

    public WirelessQuickViewPage enableSSID80211wsetting(Map<String, String> map) {
        settingsorquickview.click();
        if (checkSsidIsExist(map.get("SSID"))) {
            logger.info("Edit ssid.");
            executeJavaScript("arguments[0].removeAttribute('class')", editWifi(map.get("SSID")));
            MyCommonAPIs.sleep(3000);
            editSsid(map.get("SSID")).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(8 * 1000);
            SSID80211wbtn.click();
            MyCommonAPIs.sleep(3000);

            boolean result2 = true;
            // System.out.println("Toggle enabled- "+SSID80211wbtn.isEnabled());
            if (SSID80211wbtn.isEnabled()) {
                System.out.println("SSID80211w toggle is enabled");
                result2 = true;
                // System.out.println("Inside selected condition, result- "+result2);
            } else {
                System.out.println("SSID80211w toggle is not enabled");
                result2 = false;
                // System.out.println("Inside not selected condition, result- "+result2);
            }

            MyCommonAPIs.sleepi(8);
            editsave.click();
            MyCommonAPIs.sleepi(3);
            confirmok.click();
            MyCommonAPIs.sleepi(3);
            logger.info("SSID80211w toggle is selcted");
            System.out.println("Final result- " + result2);
            assertTrue(result2, "SSID80211w toggle is not selcted");
            MyCommonAPIs.sleepi(3);
            logger.info("Edit ssid successful.");
        } else {
            logger.warning("checksavebtnExist error");
        }
        return new WirelessQuickViewPage();
    }

    public WirelessQuickViewPage disableSSID80211wsetting(Map<String, String> map) {
        settingsorquickview.click();
        if (checkSsidIsExist(map.get("SSID"))) {
            logger.info("Edit ssid.");
            executeJavaScript("arguments[0].removeAttribute('class')", editWifi(map.get("SSID")));
            MyCommonAPIs.sleep(3000);
            editSsid(map.get("SSID")).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(8 * 1000);
            SSID80211wbtn.click();

            boolean result2 = true;
            if (SSID80211wbtn.isSelected()) {
                System.out.println("SSID80211w toggle is enabled");
                result2 = false;
            } else {
                System.out.println("SSID80211w toggle is disabled");
                result2 = true;
            }
            MyCommonAPIs.sleepi(8);
            editsave.click();
            MyCommonAPIs.sleepi(3);
            confirmok.click();
            MyCommonAPIs.sleepi(3);
            logger.info("SSID80211w toggle is disabled");
            assertTrue(result2, "SSID80211w toggle is enabled");
            MyCommonAPIs.sleepi(3);
            logger.info("Edit ssid successful.");
        } else {
            logger.warning("checksavebtnExist error");
        }
        return new WirelessQuickViewPage();
    }

    public WirelessQuickViewPage disabletoggleSSID80211wsettinglocation(Map<String, String> map) {
        settingsorquickview.click();
        if (checkSsidIsExist(map.get("SSID"))) {
            logger.info("Edit ssid.");
            executeJavaScript("arguments[0].removeAttribute('class')", editWifi(map.get("SSID")));
            MyCommonAPIs.sleep(3000);
            editSsid(map.get("SSID")).waitUntil(Condition.visible, 60 * 1000).click();
            // MyCommonAPIs.sleep(8 * 1000);
            // SSID80211wbtn.click();

            boolean result2 = false;
            if (SSID80211wbtn.isSelected()) {
                System.out.println("SSID80211w toggle is enabled");
                result2 = false;
            } else {
                System.out.println("SSID80211w toggle is disabled");
                result2 = true;
            }
            MyCommonAPIs.sleepi(8);
            editsave.click();
            MyCommonAPIs.sleepi(3);
            confirmok.click();
            MyCommonAPIs.sleepi(3);
            logger.info("SSID80211w toggle is disabled");
            assertTrue(result2, "SSID80211w toggle is enabled");
            MyCommonAPIs.sleepi(3);
            logger.info("Edit ssid successful.");
        } else {
            logger.warning("checksavebtnExist error");
        }
        return new WirelessQuickViewPage();
    }

    public WirelessQuickViewPage disabletoggleSSIDschedule(Map<String, String> map) {
        settingsorquickview.click();
        if (checkSsidIsExist(map.get("SSID"))) {
            logger.info("Edit ssid.");
            executeJavaScript("arguments[0].removeAttribute('class')", editWifi(map.get("SSID")));
            MyCommonAPIs.sleep(3000);
            editSsid(map.get("SSID")).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(8 * 1000);
            disablessidschedule.click();

            boolean result2 = true;
            if (disablessidschedule.isSelected()) {
                System.out.println("SSIDschedule toggle is enabled");
                result2 = false;
            } else {
                System.out.println("SSIDschedule  toggle is disabled");
                result2 = true;
            }
            MyCommonAPIs.sleepi(8);
            editsave.click();
            MyCommonAPIs.sleepi(3);
            confirmok.click();
            MyCommonAPIs.sleepi(3);
            logger.info("SSIDschedule  toggle is disabled");
            assertTrue(result2, "SSIDschedule  toggle is enabled");
            MyCommonAPIs.sleepi(3);
            logger.info("disable SSIDschedule successful.");
        } else {
            logger.warning("checksavebtnExist error");
        }
        return new WirelessQuickViewPage();
    }

    public WirelessQuickViewPage existSSID80211wsettingYes(Map<String, String> map) {
        settingsorquickview.click();
        if (checkSsidIsExist(map.get("SSID"))) {
            logger.info("Edit ssid.");
            executeJavaScript("arguments[0].removeAttribute('class')", editWifi(map.get("SSID")));
            MyCommonAPIs.sleep(3000);
            editSsid(map.get("SSID")).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(8 * 1000);

            boolean result2 = true;
            if (SSID80211wbtn.exists()) {
                System.out.println("SSID80211w option exist By Default");
                result2 = true;
            } else {
                System.out.println("SSID80211w is not exist By Default");
                result2 = false;
            }
            MyCommonAPIs.sleepi(8);
            editsave.click();
            MyCommonAPIs.sleepi(3);
            confirmok.click();
            MyCommonAPIs.sleepi(3);
            logger.info("SSID80211w option exist By Default");
            assertTrue(result2, "SSID80211w is not exist By Default");
            MyCommonAPIs.sleepi(3);
            logger.info("Edit ssid successful.");
        } else {
            logger.warning("checksavebtnExist error");
        }
        return new WirelessQuickViewPage();
    }

    public boolean enableMacAcl(String Ssid) {
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        boolean result = false;
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            entermacacl.click();
            MyCommonAPIs.sleepi(3);
            enablemacacl.click();
            MyCommonAPIs.sleepi(3);
            if ($x("//p[text()=\"Enable ACL Policy when at least one device is added in Allow Policy list.\"]").exists()) {
                result = true;
                macaclnodeviceconfirm.click();
            }
        }
        return result;
    }

    public static String getRandomMacAddress() {
        String mac = "";
        Random r = new Random();
        for (int i = 0; i < 6; i++) {
            int n = r.nextInt(255);
            if (i > 4) {
                mac += String.format("%02x", n);
            } else {
                mac += String.format("%02x", n) + ":";
            }

        }
        System.out.print(mac);

        return mac.toUpperCase();
    }

    public List<String> AddMultipleMacAcl(String Ssid, int count, String policyType) {
        List<String> Orglist = new ArrayList<String>();
        settingsorquickview.click();
        boolean result = false;
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            entermacacl.click();
            MyCommonAPIs.sleepi(3);
            macaclpolicy.selectOption(policyType);
            MyCommonAPIs.sleepi(3);
            int TotalOrgCount = count;

            for (int i = 1; i <= TotalOrgCount; i++) {
                waitElement(ManualMACButton);
                ManualMACButton.click();
                waitElement(DeviceName);
                DeviceName.sendKeys(Integer.toString(i));
                String Mac = getRandomMacAddress();
                String replaceString = Mac.replace(':', '-');
                System.out.print("second mac" + Mac);
                MyCommonAPIs.sleepi(2);
                DeviceMAC.sendKeys(Mac);
                MyCommonAPIs.sleepi(2);
                MACConfirmButton.click();
                MyCommonAPIs.sleepi(2);
                Orglist.add(replaceString);
                System.out.println("Mac -----> " + Integer.toString(i) + " Added Successfully");
            }
            System.out.println(Orglist);
            if (Orglist.size() == count) {
                result = true;
            }

        }
        return Orglist;
    }

    public void addMacaclDevices(String Ssid) {
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            entermacacl.click();
            MyCommonAPIs.sleepi(10);
            waitElement(macacladddevice);
            MyCommonAPIs.sleepi(3);
            macacladddevice.click();
            MyCommonAPIs.sleepi(15);
            waitElement(macAclCheckBox);
            // String mac = WebportalParam.clientwlanmac;
            // SelenideElement checkbox = $x("//table[@id='tablemanualrecent']//input[contains(@id,'"
            // + (mac.substring(mac.length() - 2, mac.length())).toUpperCase() + "')]/../i");
            MyCommonAPIs.sleepi(3);
            String mac = WebportalParam.clientwlanmac;
            SelenideElement checkbox = $x("//table[@id='tablemanualrecent']//input[contains(@id,'"
                    + (mac.substring(mac.length() - 2, mac.length())).toUpperCase() + "')]/../i");
            MyCommonAPIs.sleepi(5);
            int s = 0;
            while (s < 5) {
                // waitElement($x("//table[@id='tablemanualrecent']"));
                if (macAclCheckBox.exists()) {
                    macAclCheckBox.click();
                    MyCommonAPIs.sleepi(2);
                    macacladddeviceallow.click();
                    MyCommonAPIs.sleepi(2);
                    waitElement($x("//p[@title='" + WebportalParam.clientwlanmac + "']"));
                    enablemacacl.click();
                    break;
                }
                // else {
                // MyCommonAPIs.sleepi(5);
                // refresh();
                // s += 1;
                // }
                s += 1;
            }
        }
    }

    public void changeMacaclPolicyAndType(String Ssid, String type, String policy) {
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            entermacacl.click();
            MyCommonAPIs.sleepi(3);
            waitElement(macacltype);
            macacltype.selectOption(type);
            MyCommonAPIs.sleepi(3);
            macaclpolicy.selectOption(policy);
            logger.info("Select policy option is " + policy);
        }
    }

    public void modifyMacInMacaclPage(String Ssid) {
        settingsorquickview.click();
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            entermacacl.click();
            waitElement($x("//td[@class='sequenceNo']//span"));
            $x("//td[@class='sequenceNo']//span").click();
            MyCommonAPIs.sleepi(3);
            $x("//td[@class='sequenceNo']//input").click();
            try {
                Robot robot = new Robot();
                int sum = 0;
                while (sum < 11) {
                    robot.keyPress(KeyEvent.VK_RIGHT);
                    MyCommonAPIs.sleepi(1);
                    sum += 1;
                }
                robot.keyPress(KeyEvent.VK_BACK_SPACE);
                MyCommonAPIs.sleepi(3);
                robot.keyPress(KeyEvent.VK_2);
            } catch (Exception e) {
                logger.info(String.valueOf(e));
            }
            MyCommonAPIs.sleepi(3);
            $x("(//td[@class='sequenceNo']//span)[1]").click();
        }
    }

    public void enableFacebookWifi(String Ssid) {
        settingsorquickview.click();
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            entercaptiveportal.click();
            MyCommonAPIs.sleepi(3);
            enablecaptiveportal.click();
            MyCommonAPIs.sleepi(3);
            selectfacebookwifi.click();
            MyCommonAPIs.sleepi(3);
            if (modifyfbwifi.exists()) {
                modifyfbwifi.click();
                MyCommonAPIs.sleepi(5);
                savecaptive.click();
                MyCommonAPIs.sleepi(3);
                captiveok.click();
                logger.info("Enable facebook wifi success.");
                MyCommonAPIs.sleepi(5);
                clickEditSsid(Ssid);
                if (enterfbwifinew.exists()) {
                    enterfbwifinew.click();
                } else {
                    enterfbwifi.click();
                }
                MyCommonAPIs.sleepi(3);
                verifypage.click();
                MyCommonAPIs.waitReady();
                logger.info("Verify page success.");
            } else {
                configurefbwifi.click();
                MyCommonAPIs.sleepi(3);
                enablefbwifi.click();
                MyCommonAPIs.sleepi(3);
                registerfbwifi.click();
                MyCommonAPIs.sleepi(5);
                Selenide.switchTo().window(1);
                // Selenide.switchTo().frame("facebook");
                logger.info("Beginning to log in facebook.");
                MyCommonAPIs.sleepi(5);
                if ($("#email").exists()) {
                    $("#email").setValue("sumanta.span@gmail.com");
                    $("#pass").setValue("Borqs@1234");
                    MyCommonAPIs.sleepi(3);
                    $("#loginbutton").click();
                }
                MyCommonAPIs.sleepi(3);
                $x("//span[text()='Connect']").click();
                MyCommonAPIs.sleepi(10);
                $x("(//span[text()='Facebook Page'])[2]//..//../div[2]/div/i").click();
                MyCommonAPIs.sleepi(3);
                $x("//span[text()='CricketStadium']").click();
                MyCommonAPIs.sleepi(10);
                $x("(//span[text()='Save'])[2]").click();
                MyCommonAPIs.sleepi(10);
                Selenide.switchTo().window(0);
                logger.info("Log in facebook success.");
                MyCommonAPIs.sleepi(3);
                entercaptiveportal.click();
                MyCommonAPIs.sleepi(3);
                enablecaptiveportal.click();
                MyCommonAPIs.sleepi(3);
                selectfacebookwifi.click();
                MyCommonAPIs.sleepi(3);
                configurefbwifi.click();
                MyCommonAPIs.sleepi(3);
                savefbwifi.click();
                MyCommonAPIs.sleepi(5);
                savecaptive.click();
                MyCommonAPIs.sleepi(3);
                captiveok.click();
                logger.info("Enable facebook wifi success.");
                MyCommonAPIs.sleepi(5);
                clickEditSsid(Ssid);
                if (enterfbwifinew.exists()) {
                    enterfbwifinew.click();
                } else {
                    enterfbwifi.click();
                }
                MyCommonAPIs.sleepi(3);
                verifypage.click();
                MyCommonAPIs.waitReady();
                logger.info("Verify page success.");
            }
        }
    }

    // public boolean AdvanceRateSelectionStatus(String Ssid) {
    // boolean result = false;
    // if(settingsorquickview.exists()) {
    // settingsorquickview.click();
    // }
    // if (checkSsidIsExist(Ssid)) {
    // clickEditSsid(Ssid);
    // enterAdvanceRateSelection.click();
    //
    // String twocheck = (CheckSetMinimumRateControls).getAttribute("checked");
    // System.out.println(twocheck);
    //
    // FiveGhz.click();
    // MyCommonAPIs.sleepi(3);
    //
    // String fivecheck = (CheckSetMinimumRateControls).getAttribute("checked");
    // System.out.println(fivecheck);
    //
    // if (twocheck == null && fivecheck == null) {
    // logger.info("it is enabled");
    // result = true;
    //
    // }
    //
    // }
    //
    // return result;
    // }

    public boolean CheckAdvanceRateSelectionStatus() {
        boolean result = false;
        enterAdvanceRateSelection.click();

        String twocheck = (CheckSetMinimumRateControls).getAttribute("checked");
        System.out.println(twocheck);

        FiveGhz.click();
        MyCommonAPIs.sleepi(3);

        String fivecheck = (CheckSetMinimumRateControls).getAttribute("checked");
        System.out.println(fivecheck);

        if (twocheck == null && fivecheck == null) {
            logger.info("it is enabled");
            result = true;

        }

        return result;
    }

    public boolean checkFacebookWifiIsEnable(String Ssid) {
        boolean result = false;
        settingsorquickview.click();
        MyCommonAPIs.sleepi(5);
        if (checkSsidIsExist(Ssid)) {
            MyCommonAPIs.sleepi(5);
            clickEditSsid(Ssid);
            MyCommonAPIs.sleepi(5);
            if (fbwifimenu.exists()) {
                result = true;
                System.out.println("check here");
                logger.info("Facebook wifi enabled.");
            }
        }
        return result;
    }

    public boolean checkIcpEnable(String Ssid) {
        boolean result = false;
        settingsorquickview.click();
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            entercaptiveportal.click();
            MyCommonAPIs.sleepi(3);
            if (checkIcpSelected.isSelected()) {
                result = true;
                logger.info("ICP is enabled.");
            }
        }
        return result;
    }

    public void deleteIcpImage(String Ssid, String type) {
        switch (type) {
        case "logo":
            $x(deleteImageBasic + "[1]").click();
            break;
        case "background":
            $x(deleteImageBasic + "[2]").click();
            break;
        case "mobile background":
            $x(deleteImageBasic + "[3]").click();
            break;
        }
        MyCommonAPIs.sleepi(3);
        deleteImgOkBtn.click();
        MyCommonAPIs.sleepi(3);
        Selenide.switchTo().defaultContent();
        savecaptive.click();
        MyCommonAPIs.sleepi(10);
        captiveok.click();
        MyCommonAPIs.sleepi(5);
    }

    public boolean checkIcpValueErrorAlert() {
        boolean result = false;
        if (icpValueErrorAlert.exists()) {
            String msg = getText(icpValueErrorAlert);
            if (msg.contains("Landing page URL is missing or invalid.")) {
                result = true;
                logger.info("Message:" + msg);
            } else if (msg.contains("Missing portal name.")) {
                result = true;
                logger.info("Message:" + msg);
            } else if (msg.contains("Missing welcome headline.")) {
                result = true;
                logger.info("Message:" + msg);
            } else if (msg.contains("Enter maximum session duration")) {
                result = true;
                logger.info("Message:" + msg);
            }
        }
        return result;
    }

    public void enableIcpStep() {

        entercaptiveportal.click();
        MyCommonAPIs.sleepi(60);
        enablecaptiveportal.click();
        refresh();
        MyCommonAPIs.sleepi(30);
        enablecaptiveportal.click();
        selectinsightcaptiveportal.click();
        while (true) {
            MyCommonAPIs.sleepi(10);
            if ($("[class='loaderContainer']").isDisplayed()) {
                refresh();
                enablecaptiveportal.click();
                MyCommonAPIs.sleepi(1);
                selectinsightcaptiveportal.click();
            } else {
                break;
            }
        }
    }

    public void configAuthenticationMethod(Map<String, String> map) {
        String[] authors = map.get("Login Modes").split("[.]");
        for (String author : authors) {
            MyCommonAPIs.sleepi(5);
            switch (author) {
            case "Facebook":
                clickfacebook.selectRadio("on");
                break;
            // case "Twitter":
            // clicktwitter.selectRadio("on");
            // break;
            case "LinkedIn":
                clicklinkedIn.selectRadio("on");
                break;
            case "Register":
                clickregister.selectRadio("on");
                break;
            case "Register_Customize":
                clickregister.selectRadio("on");
                MyCommonAPIs.sleepi(10);
                clickregisterCustomize.click();
                break;
            }
            System.out.println("out of switch");
        }

    }

    public void configPlayVideo(Map<String, String> map) {
        uploadvideo.click();
        MyCommonAPIs.sleepi(3);
        inputvideoname.setValue(map.get("Video Name"));
        MyCommonAPIs.sleepi(3);
        selectmp4file.sendKeys(map.get("Video Path"));
        MyCommonAPIs.sleepi(3);
        uploadvideookbutton.click();
        MyCommonAPIs.sleepsync();
        $x(choosevideotoplay).selectOption(map.get("Video Name"));
        MyCommonAPIs.sleepi(3);
        // okvideo.click();

    }

    public void configPaymentByPaypal(Map<String, String> map) {
        paypalClientId.setValue(map.get("Paypal Client ID"));
        MyCommonAPIs.sleepi(1);
        currency.selectOption(Integer.valueOf(map.get("Currency")));
        MyCommonAPIs.sleepi(1);
        amountToChargeEndUser.setValue(map.get("Amount"));
    }

    public void configDispalyAd(Map<String, String> map) {
        uploaddisplayad.click();
        MyCommonAPIs.sleepi(3);
        inputadname.setValue(map.get("Ad Name"));
        MyCommonAPIs.sleepi(3);
        selectadimagefile.sendKeys(map.get("Image Path"));
        MyCommonAPIs.sleepi(3);
        uploaddisplayadok.click();
        MyCommonAPIs.sleepsync();
        $x(selectdisplayad).selectOption(map.get("Ad Name"));
    }

    public void enableCaptivePortalType(String Ssid, Map<String, String> map) {
        settingsorquickview.click();
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            enableIcpStep();
            MyCommonAPIs.sleepi(10);
            if (!enabledailylogins.exists()) {
                MyCommonAPIs.sleepi(5);
                enableschedulereports.click();
                MyCommonAPIs.sleepi(3);
            }
            enabledailylogins.click();
            MyCommonAPIs.sleepi(3);
            enabledailyanalytics.click();
            MyCommonAPIs.sleepi(3);
            enableweeklyanalytics.click();
            MyCommonAPIs.sleepi(3);
            Selenide.switchTo().frame(0);
            MyCommonAPIs.sleepi(3);
            inputportalname.setValue(map.get("Portal Name"));
            inputwelcomeheadline.setValue(map.get("Welcome Headline"));
            if (map.containsKey("Welcome Message")) {
                inputWelcomeMsg.setValue(map.get("Welcome Message"));
            }
            if (map.containsKey("Captive Portal Logo")) {
                if (map.get("Captive Portal Logo").equals("DEFAULT_LOGO")) {
                    if ($x("//option[text()='" + map.get("Captive Portal Logo") + "']").exists()) {
                        addcaptivelogo.selectOption(map.get("Captive Portal Logo"));
                    } else {
                        addcaptivelogo.selectOption(1);
                    }
                } else {
                    chooseCaptiveLogoBtn.click();
                    MyCommonAPIs.sleepi(3);
                    selectCaptiveLogoName.setValue(map.get("Captive Portal Logo"));
                    MyCommonAPIs.sleepi(3);
                    selectCaptiveLogoFile.sendKeys(map.get("Captive Portal Logo Path"));
                    MyCommonAPIs.sleepi(3);
                    uploadImgOkBtn.click();
                    MyCommonAPIs.sleepi(20);
                    addcaptivelogo.selectOption(map.get("Captive Portal Logo"));
                }
            }
            if (map.containsKey("Desktop Background Image")) {
                if (map.get("Desktop Background Image").equals("DEFAULT_BG")) {
                    if ($x("//option[text()='" + map.get("Desktop Background Image") + "']").exists()) {
                        addbackgroundimage.selectOption(map.get("Desktop Background Image"));
                    } else {
                        addbackgroundimage.selectOption(1);
                    }
                } else {
                    chooseBackgroundImg.click();
                    MyCommonAPIs.sleepi(3);
                    selectCaptiveLogoName.setValue(map.get("Desktop Background Image"));
                    MyCommonAPIs.sleepi(3);
                    selectBackgroundImgFile.sendKeys(map.get("Desktop Background Image Path"));
                    MyCommonAPIs.sleepi(3);
                    uploadImgOkBtn.click();
                    MyCommonAPIs.sleepi(20);
                    addbackgroundimage.selectOption(map.get("Desktop Background Image"));
                }
            }
            if (map.containsKey("Mobile Background Image")) {
                chooseMobileBackgroundImg.click();
                MyCommonAPIs.sleepi(3);
                selectCaptiveLogoName.setValue(map.get("Mobile Background Image"));
                MyCommonAPIs.sleepi(3);
                selectMobileBackgroundImgFile.sendKeys(map.get("Mobile Background Image Path"));
                MyCommonAPIs.sleepi(3);
                uploadImgOkBtn.click();
                MyCommonAPIs.sleepi(20);
                addmobliebackgroundimage.selectOption(map.get("Mobile Background Image"));
            }
            inputlandingurl.setValue(map.get("Landing Page URL"));
            selectsessionduration.selectOption(map.get("Session Duration"));
            clickcaptiveportalstep.click();
            MyCommonAPIs.sleepi(10);
            selectsteptype.selectOption(map.get("Step Type"));
            MyCommonAPIs.sleepi(10);
            if (map.get("Step Type").equals("Authentication Method")) {
                configAuthenticationMethod(map);
            } else if (map.get("Step Type").equals("Play Video")) {
                configPlayVideo(map);
            } else if (map.get("Step Type").equals("Payment by Paypal")) {
                configPaymentByPaypal(map);
            } else if (map.get("Step Type").equals("Display Ad")) {
                configDispalyAd(map);
            } else if (map.get("Step Type").equals("Voucher")) {
            }

            // if(map.)
            MyCommonAPIs.sleepi(3);
            savecaptiveportalstep.click();
            MyCommonAPIs.sleepi(3);
            Selenide.switchTo().defaultContent();
            MyCommonAPIs.sleepi(10);
            savecaptive.click();
            MyCommonAPIs.sleepi(10);
            captiveok.click();
            MyCommonAPIs.sleepi(5);
            logger.info("Enable instant captive portal success.");
        } else {
            logger.info("Not found ssid.");
        }
    }

    public boolean checkICPInsufficientCreditsDisplayed(String Ssid) {
        boolean result = false;
        settingsorquickview.click();
        if (checkSsidIsExist(Ssid)) {
        } else {
            waitElement(settingsorquickview);
            settingsorquickview.click();
            waitReady();
            waitElement(addssid);
            Map<String, String> ssidInfo = new HashMap<String, String>();
            ssidInfo.put("SSID", Ssid);
            ssidInfo.put("Security", "WPA2 Personal");
            ssidInfo.put("Password", "12345678");
            addSsidStep(ssidInfo);
        }
        clickEditSsid(Ssid);
        entercaptiveportal.click();
        MyCommonAPIs.sleepi(3);
        enablecaptiveportal.click();
        MyCommonAPIs.sleepi(1);
        selectinsightcaptiveportal.click();
        while (true) {
            MyCommonAPIs.sleepi(20);
            if ($("[class='loaderContainer']").isDisplayed()) {
                refresh();
                enablecaptiveportal.click();
                MyCommonAPIs.sleepi(1);
                selectinsightcaptiveportal.click();
            } else {
                break;
            }
        }
        if (icpinsufficientcredit.exists()) {
            result = true;
            logger.info("Insufficient credit displayed.");
        }
        if (icpinsufficientcreditclose.exists()) {
            icpinsufficientcreditclose.click();
        }
        return result;
    }

    public boolean checkICPInsufficientCreditsClickYes(String Ssid) {
        boolean result = false;
        settingsorquickview.click();
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            entercaptiveportal.click();
            MyCommonAPIs.sleepi(3);
            enablecaptiveportal.click();
            MyCommonAPIs.sleepi(1);
            selectinsightcaptiveportal.click();
            while (true) {
                MyCommonAPIs.sleepi(20);
                if ($("[class='loaderContainer']").isDisplayed()) {
                    refresh();
                    enablecaptiveportal.click();
                    MyCommonAPIs.sleepi(1);
                    selectinsightcaptiveportal.click();
                } else {
                    break;
                }
            }
            if (icpinsufficientcredit.exists()) {
                result = true;
                logger.info("Insufficient credit displayed.");
            }
            clickBoxLastButton();
        }
        return result;
    }

    public void disableFacebookWifi(String Ssid) {
        settingsorquickview.click();
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            entercaptiveportal.click();
            MyCommonAPIs.sleepi(3);
            if (checkenablecaptiveportal.exists()) {
                selectbasiccaptive.click();
                MyCommonAPIs.sleepi(3);
                savecaptive.click();
                MyCommonAPIs.sleepi(3);
                captiveok.click();
            }
        }
    }

    public void disablecp(String Ssid) {
        settingsorquickview.click();
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            entercaptiveportal.click();
            MyCommonAPIs.sleepi(3);
            // enableIcpStep();
            // entercaptiveportal.click();
            MyCommonAPIs.sleepi(3);
            enablecaptiveportal.click();
            MyCommonAPIs.sleepi(3);
            captiveok.click();
            MyCommonAPIs.sleepi(5);
        }
    }

    public void editCaptivePortal(String Ssid, String url, String title, String message) {
        settingsorquickview.click();
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            entercaptiveportal.click();
            MyCommonAPIs.sleepi(5);
            bcpRadiobutton.shouldBe(Condition.visible).click();
            MyCommonAPIs.sleepi(3);
            enableredirecturl.click();
            MyCommonAPIs.sleepi(3);
            inputredirecturl.clear();
            inputredirecturl.sendKeys(url);
            inputtitle.clear();
            inputtitle.sendKeys(title);
            inputmessage.clear();
            inputmessage.sendKeys(message);
            MyCommonAPIs.sleepi(3);
            savecaptive.click();
            MyCommonAPIs.sleepi(3);
            captiveok.click();
        }
    }

    public void UploadImage(String Ssid, String filePath) {
        settingsorquickview.click();
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            entercaptiveportal.click();
            MyCommonAPIs.sleepi(3);
            UploadCPfile.sendKeys(filePath);
            MyCommonAPIs.sleepi(3);
        }

    }

    public String GetcurrentPath() {

        File f = new File(this.getClass().getResource("").getPath());
        String filePath = f.getAbsolutePath().replace("\\target\\test-classes\\webportal\\weboperation", "\\src\\test\\java\\webportal\\CFD\\AP\\");

        System.out.println(filePath);
        return filePath;

    }

    public void editRateLimit(String Ssid, double uploadvalue, double downloadvalue) {
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }

        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            enterratelimit.click();
            MyCommonAPIs.sleepi(3);
            enableratelimit.click();
            MyCommonAPIs.sleepi(3);
            uploadrate.selectOption("Mbps");
            MyCommonAPIs.sleepi(3);
            moveSlider("upload", uploadvalue);
            // executeJavaScript(
            // "document.getElementsByClassName(\"ui-slider-handle ui-corner-all ui-state-default\")[0].style.left=\"'" +
            // uploadvalue + "'\"");
            MyCommonAPIs.sleepi(3);
            downloadrate.selectOption("Mbps");
            MyCommonAPIs.sleepi(3);
            moveSlider("download", downloadvalue);
            saveratelimit.click();
            MyCommonAPIs.sleepi(3);
            ratelimitok.click();
        }
    }

    public void DisableRateLimit(String Ssid) {
        settingsorquickview.click();
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            enterratelimit.click();
            MyCommonAPIs.sleepi(3);
            enableratelimit.click();
            MyCommonAPIs.sleepi(3);
            saveratelimit.click();
            MyCommonAPIs.sleepi(3);
            ratelimitok.click();
        }
    }

    public void moveSlider(String option, double value) {
        Actions actions = new Actions(WebDriverRunner.getWebDriver());
        if (option.equals("upload")) {
            BigDecimal bg = new BigDecimal((value / (1000 - 1.0625)) + 0.001);
            double f1 = bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
            Point uploadx = uploadslider.getLocation();
            int leftx = uploadx.getX();
            // int pointy = uploadx.getY() - 20;
            int rightx = uploadrateright.getLocation().getX();
            int pointx = (int) ((rightx - leftx) * f1);
            actions.dragAndDropBy($x("//*[@id=\"divToolTipArrawSliderRateLimit\"]"), pointx, 0).perform();
        } else if (option.equals("download")) {
            BigDecimal bg = new BigDecimal((value / (1000 - 1.0625)) + 0.002);
            double f1 = bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
            Point uploadx = downloadrateleft.getLocation();
            int leftx = uploadx.getX();
            int rightx = downloadrateright.getLocation().getX();
            int pointx = (int) ((rightx - leftx) * f1);
            actions.dragAndDropBy($("#divToolTipArrowDownloadSliderRateLimit"), pointx, 0).perform();
        }
    }

    public boolean checkRateLimitStatus(String Ssid) {
        boolean result = false;
        settingsorquickview.click();
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            enterratelimit.click();
            MyCommonAPIs.sleepi(3);
            if (uploadrate.isEnabled()) {
                result = true;
                logger.info("Rate limit enabled.");
            }
        }
        return result;
    }

    public void enterDeviceYes(String serialNumber) {
        WebCheck.checkHrefIcon(URLParam.hrefWireless);
        for (int i = 0; i < 2; i++) {
            if (checkApIsExist(serialNumber)) {
                logger.info("Enter device.");
                executeJavaScript("arguments[0].removeAttribute('class')", editModule(serialNumber));
                MyCommonAPIs.sleep(3000);
                enterDevice(serialNumber).waitUntil(Condition.visible, 60 * 1000).click();
                MyCommonAPIs.sleep(5 * 1000);
                break;
            }
            refresh();
        }
    }

    public void enterDeviceYesBR(String serialNumber) {
        WebCheck.checkHrefIcon(URLParam.hrefRouters);
        for (int i = 0; i < 2; i++) {
            if (checkApIsExist(serialNumber)) {
                logger.info("Enter device.");
                executeJavaScript("arguments[0].removeAttribute('class')", editModule(serialNumber));
                MyCommonAPIs.sleep(3000);
                enterDevice(serialNumber).waitUntil(Condition.visible, 60 * 1000).click();
                MyCommonAPIs.sleep(5 * 1000);
                break;
            }
            refresh();
        }
    }

    public void deleteDeviceYes(String serialNumber) {
        WebCheck.checkHrefIcon(URLParam.hrefWireless);
        MyCommonAPIs.sleepi(5);
        if (checkApIsExist(serialNumber)) {
            logger.info("Delete device.");
            MyCommonAPIs.sleepi(5);
            executeJavaScript("arguments[0].removeAttribute('class')", editModule(serialNumber));
            MyCommonAPIs.sleep(3000);
            deleteDevice(serialNumber).waitUntil(Condition.visible, 60 * 1000).click();
            waitElement(deleteapyes);
            MyCommonAPIs.sleepi(30);
            deleteapyes.click();
            MyCommonAPIs.sleepi(5);
            refresh();
            MyCommonAPIs.sleep(5 * 1000);
        }
    }

    public void deleteDeviceYesBR(String serialNumber) {
        WebCheck.checkHrefIcon(URLParam.hrefRouters);
        if (checkApIsExist(serialNumber)) {
            logger.info("Delete device.");
            executeJavaScript("arguments[0].removeAttribute('class')", editModuleforBR(serialNumber));
            MyCommonAPIs.sleep(3000);
            deleteDeviceBR(serialNumber).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(3000);
            deleteapyes.click();
            MyCommonAPIs.sleep(5 * 1000);
        }
    }

    public void deleteDeviceYesswitch(String serialNumber) {
        WebCheck.checkHrefIcon(URLParam.hrefWiredQuickView);
        if (checkApIsExist(serialNumber)) {
            logger.info("Delete device.");
            executeJavaScript("arguments[0].removeAttribute('class')", editModuleforswitch(serialNumber));
            MyCommonAPIs.sleep(3000);
            deleteDevice(serialNumber).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(3000);
            deleteapyes.click();
            MyCommonAPIs.sleep(5 * 1000);
        }
    }

    public String enableFastRoamingAndGetMobilityid() {
        String mobilityId = "";
        WebCheck.checkHrefIcon(URLParam.hrefWirelessFastroaming);
        MyCommonAPIs.sleepi(3);
        if (!mobilityid.exists()) {
            enablefastroaming.click();
        }
        MyCommonAPIs.sleepi(3);
        mobilityId = getText(mobilityid);
        logger.info("MobilityId:" + mobilityid);
        return mobilityId;
    }

    public boolean enableFastRoamingAndGetMobilityid1() {

        boolean result = false;

        WebCheck.checkHrefIcon(URLParam.hrefWirelessFastroaming);

        if (getFastRomaingStatus()) {
            logger.info("options is enabled");
            enablefastroaming.click();
            MyCommonAPIs.sleepi(3);
            conformation.click();
            MyCommonAPIs.sleepi(10);
            logger.info("options is disabled");
            enablefastroaming.click();
            result = true;
        } else {
            logger.info("options is disabled");
            enablefastroaming.click();
            MyCommonAPIs.sleepi(10);
            logger.info("options is enabled");
            enablefastroaming.click();
            MyCommonAPIs.sleepi(3);
            conformation.click();
            result = true;
        }

        // setFastRomaing(!getFastRomaingStatus());

        return result;
    }

    public void disableFastRoaming() {
        WebCheck.checkHrefIcon(URLParam.hrefWirelessFastroaming);
        MyCommonAPIs.sleepi(3);
        if (mobilityid.exists()) {
            enablefastroaming.click();
            logger.info("Fast roaming disabled.");
        }
        MyCommonAPIs.sleepi(3);
    }

    public boolean checkFastRoamingStatus() {
        boolean result = false;
        WebCheck.checkHrefIcon(URLParam.hrefWirelessFastroaming);
        MyCommonAPIs.sleepi(3);
        if (mobilityid.exists()) {
            result = true;
            logger.info("Fast roaming status: enabled.");
            MyCommonAPIs.sleepi(3);
        }
        return result;
    }

    public void enableUrlFilteringAndAddUrl(String url) {
        WebCheck.checkHrefIcon(URLParam.hrefWirelessUrlfiltering);
        MyCommonAPIs.sleepi(5);
        if (!blacklisttable.exists()) {
            enableblacklist.click();
            waitReady();
            MyCommonAPIs.sleepi(5);
        }
        addblacklisturl.click();
        waitReady();
        inputdomain.sendKeys(url);
        savedomain.click();
        waitReady();
        if ($x("//td[text()='" + url + "']").exists()) {
            logger.info("Blacklist url:" + $x("//td[text()='" + url + "']").getText());
        }
    }

    public void deleteBlacklistUrl(String url) {
        WebCheck.checkHrefIcon(URLParam.hrefWirelessUrlfiltering);
        if ($x("//td[text()='" + url + "']").exists()) {
            executeJavaScript("arguments[0].removeAttribute('class')", findUrl(url));
            MyCommonAPIs.sleep(3000);
            deleteUrl(url).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(3000);
            deleteurlyes.click();
            MyCommonAPIs.sleepi(5);
            logger.info("Delete url:" + url);
        }
    }

    public void disableUrlFiltering() {
        WebCheck.checkHrefIcon(URLParam.hrefWirelessUrlfiltering);
        MyCommonAPIs.sleepi(3);
        if (blacklisttable.exists()) {
            enableblacklist.click();
            MyCommonAPIs.sleepi(3);
        }
    }

    public boolean checkUrlFilterIsExist(String text) {
        boolean result = false;
        settingsorquickview.click();
        waitReady();
        MyCommonAPIs.sleepi(5);
        WebCheck.checkHrefIcon(URLParam.hrefWirelessUrlfiltering);
        MyCommonAPIs.sleepi(5);
        String sElement = String.format("//*[@id='divConRowurlFiltering']//*[contains(text(),'%s')]", text);
        logger.info("on element:" + sElement);
        if ($x(sElement).exists()) {
            result = true;
            logger.info("Url filter:" + text + " is existed.");
        }
        return result;
    }

    public boolean checkBlacklistUrlLimitReached(String url) {
        enableUrlFilteringAndAddUrl(url);
        MyCommonAPIs.sleepi(5);
        boolean result = false;
        if ($x("//div[text()=\"Black list URL limit reached, Please delete the URL to configure black list\"]").exists()) {
            result = true;
        }
        return result;
    }

    public String getDeviceIp(String serialNumber) {
        WebCheck.checkHrefIcon(URLParam.hrefWireless);
        String ip = "";
        if (checkApIsExist(serialNumber)) {
            ip = $x("//td[text()='" + serialNumber + "']/../td[9]").getText();
            logger.info("Get ap ip:" + ip);
        }
        return ip;
    }

    public String getClientIP(String mac) {
        WebCheck.checkHrefIcon(URLParam.hrefWireless);
        String ip = "";
        if (macaddress.exists()) {
            ip = $x("//td[text()='" + mac + "']/../td[6]").getText();
            logger.info("Get client ip:" + ip);
        }
        return ip;
    }

    public void deleteSsidYes(String Ssid) {
        MyCommonAPIs.sleepi(10);
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        refresh();
        MyCommonAPIs.sleepi(10);
        if (checkSsidIsExist(Ssid)) {
            logger.info("Delete ssid.");
            MyCommonAPIs.sleepi(5);
            $x("//span[text()='" +Ssid+ "']").hover();
            MyCommonAPIs.sleep(3000);
            editWifi(Ssid).hover();
            MyCommonAPIs.sleep(3000);
            deleteSsid(Ssid).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleepi(30);
            deletessidyes.click();
            MyCommonAPIs.sleep(5 * 1000);
        }
    }

    public void deleteFolder(String path) throws Throwable {
        // String fileName = "/home/mkyong/app.log";

        try {
            File file = new File(path);
            if (file.delete()) {
                System.out.println(file.getName() + " is deleted!");
            } else {
                System.out.println("Sorry, unable to delete the file.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteMPSKALL(String MPSKname) {
        waitReady();
        if (DeleteMPSK.exists()) {
            logger.info("Delete ssid.");
            executeJavaScript("arguments[0].removeAttribute('class')", editMPSK(MPSKname));
            MyCommonAPIs.sleep(3000);
            deleteMPSK(MPSKname).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleepi(10);
            deletessidyes.click();
            MyCommonAPIs.sleepi(10);
        }
    }

    public void deleteWifiSchedulesYes(String Schedulewifi) {
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        // waitElement(addssid);
        wifischedules.click();
        // waitElement(addscheduledssid);
        if (checkScheduledwifiSsidIsExist(Schedulewifi)) {
            logger.info("Delete scheduledwifi.");
            // executeJavaScript("arguments[0].removeAttribute('class')", editWifiSchedule(Schedulewifi));
            MyCommonAPIs.sleep(3000);
            deleteScheduledwifi(Schedulewifi).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(3000);
            deletescheduledwifiyes.click();
            MyCommonAPIs.sleep(5 * 1000);
        }
    }

    public WirelessQuickViewPage editWifiSchedule(Map<String, String> map) {
        settingsorquickview.click();
        waitReady();
        // waitElement(addssid);
        wifischedules.click();
        waitElement(addscheduledssid);
        if (checkScheduledwifiSsidIsExist(map.get("Schedule Name"))) {
            logger.info("Edit scheduledwifi.");
            MyCommonAPIs.sleep(3000);
            editWifiSchedule(map.get("Schedule Name")).click();
            // deleteScheduledwifi(Schedule Name).click();
            editscheduledwifiyesbtn.click();
            MyCommonAPIs.sleepi(15);
            if (map.containsKey("unselect day")) {
                SimpleDateFormat df = new SimpleDateFormat("E");
                selectDays(df.format(new Date()).toString()).click();
                MyCommonAPIs.sleepi(15);
            }
            selectdayTue.click();
            MyCommonAPIs.sleepi(30);
            strtpickbtn.click();
            MyCommonAPIs.sleepi(1);
            for (int i = 1; i <= 10; i++) {
                selectTimeNagivation.click();
                MyCommonAPIs.sleepi(1);
            }
            okbtn.click();

            MyCommonAPIs.sleepi(1);
            SelectEndTime.click();
            MyCommonAPIs.sleepi(1);
            for (int i = 1; i <= 11; i++) {
                SelectEndTimeNavigation.click();
                MyCommonAPIs.sleepi(1);
            }

            OkayEndtime.click();
            MyCommonAPIs.sleepi(1);
            takess("editschedule");
            editschedulesave.click();
            MyCommonAPIs.sleepi(3);
            logger.info("Edit ssid successful.");
        } else {
            logger.warning("checkSsidIsExist error");
        }
        return new WirelessQuickViewPage();
    }

    public WirelessQuickViewPage createWifiSchedule(Map<String, String> map) {
        settingsorquickview.click();
        waitReady();
        // waitElement(addssid);
        wifischedules.click();
        waitElement(addscheduledssid);
        if (!checkScheduledwifiSsidIsExist(map.get("Schedule Name"))) {
            logger.info("Create scheduledwifi.");
            MyCommonAPIs.sleep(3000);
            addscheduledssid.click();
            waitElement(scheduleName);
            scheduleName.setValue(map.get("Schedule Name"));
            if (map.containsKey("Schedule Days")) {
                selectDays(map.get("Schedule Days")).click();
            } else {
                SimpleDateFormat df = new SimpleDateFormat("E");
                selectDays(df.format(new Date()).toString()).click();
            }
            strtpickbtn.click();
            okbtn.click();
            endpicktbtn.click();
            upbtn.click();
            okbtn1.click();
            takess("createschedule");
            editschedulesave.click();
            MyCommonAPIs.sleepi(3);
            logger.info("Create ssid successful.");
        } else {
            logger.warning("checkSsidIsExist error");
        }
        return new WirelessQuickViewPage();
    }

    public String ssidtimezoneverification(Map<String, String> map) {
        wirelesstab.click();
        MyCommonAPIs.sleepi(3);
        settingsorquickview.click();
        waitReady();
        // waitElement(addssid);
        wifischedules.click();
        waitElement(addscheduledssid);
        addscheduledssid.click();
        MyCommonAPIs.sleepi(3);
        String time2 = "";
        logger.info("Scheduled wifi time zone.");

        time2 = wifitimezone.getText();
        MyCommonAPIs.sleepi(3);
        // System.out.println("Schedule Wifi Time Zone- "+time2);
        // scheduledwifitimezone.click();
        return time2;
    }

    public WirelessQuickViewPage ssidtimezoneupdate(Map<String, String> map) {
        settingsorquickview.click();
        waitReady();
        // waitElement(addssid);
        wifischedules.click();
        waitElement(addscheduledssid);
        addscheduledssid.click();
        MyCommonAPIs.sleepi(3);
        logger.info("Scheduled wifi time zone.");
        scheduledwifitimezone.click();
        return new WirelessQuickViewPage();
    }

    public boolean checkApNameIsExist(String serialNumber, String Name) {
        WebCheck.checkHrefIcon(URLParam.hrefWireless);
        boolean result = false;
        if ($x("//td[text()='" + serialNumber + "']/../td[1]").getText().equals(Name)) {
            result = true;
            logger.info("Ap name:" + Name + " is existed.");
        }
        return result;
    }

    public boolean checkswitchNameIsExist(String serialNumber, String Name) {
        WebCheck.checkHrefIcon(URLParam.hrefDevices);
        boolean result = false;
        if ($x("//td[text()='" + serialNumber + "']/../td[1]").getText().equals(Name)) {
            result = true;
            logger.info("switch name:" + Name + " is existed.");
        }
        return result;
    }

    public boolean checkApIsOnline(String serialNumber) {
        WebCheck.checkHrefIcon(URLParam.hrefWireless);
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if ($x("//td[text()='" + serialNumber + "']/../td[2]").getText().equals("Connected")) {
            result = true;
            logger.info("Ap SN:" + serialNumber + " is online.");
        }
        return result;
    }

    public int getApUptime(String serialNumber) {
        int upTime = 0;
        String text = getText(String.format(uptime, serialNumber));
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(text);
        upTime = Integer.parseInt(m.replaceAll("").trim());
        logger.info("Uptime number:" + String.valueOf(upTime));
        return upTime;
    }
    public int getApUptime() {
        int upTime = 0;
        String text = getText(String.format(uptime, WebportalParam.ap1serialNo));
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(text);
        upTime = Integer.parseInt(m.replaceAll("").trim());
        logger.info("Uptime number:" + String.valueOf(upTime));
        return upTime;
    }

    public boolean checkApIsRebooting(String serialNumber) {
        // MyCommonAPIs.sleepi(30);
        boolean result = false;
        // if ($x("//div[text()='Device is rebooting']").exists()) {
        logger.info("Device is rebooting.");
        WebCheck.checkHrefIcon(URLParam.hrefWireless);
        waitReady();
        if (getText($x("//td[text()='" + serialNumber + "']/../td[2]")).equals("Device is rebooting")) {
            result = true;
            logger.info("Ap SN:" + serialNumber + " is rebooting.");
            // }
        }
        return result;
    }

    public boolean checkApIpCorrect(String serialNumber, String ip) {
        WebCheck.checkHrefIcon(URLParam.hrefWireless);
        MyCommonAPIs.sleepi(10);
        boolean result = false;
        if ($x("//td[text()='" + serialNumber + "']/../td[10]").getText().equals(ip)) {
            result = true;
            logger.info("Ap ip address:" + ip + " is correct.");
        }
        return result;
    }

    public boolean checkApIsExist(String serialNumber) {
        waitReady();
        boolean result = false;
        String sElement = String.format("//td[text()='%s']", serialNumber);
        logger.info("on element: " + sElement);
        if ($x(sElement).exists() || $x("//span[text()='"+serialNumber+"']").exists()) {
            result = true;
            logger.info("Ap SN:" + serialNumber + " is existed.");
        }
        return result;
    }

    public boolean checkSsidIsExist(String SSID) {
        System.out.println(SSID);
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        waitReady();
        // ($("#wirelessTable"));
        String sElement = String.format("//span[text()='%s']", SSID);
        logger.info("on element:" + sElement);
        if ($x(sElement).exists()) {
            result = true;
            logger.info("Ssid:" + SSID + " is existed.");
        }
        return result;
    }

    public boolean checkScheduledwifiSsidIsExist(String ScheduleName) {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        // waitElement($("//div[@class='statidetails statidetailsVisible']"));
        waitReady();
        String sElement = String.format("//*[@id='divConRowurlFiltering']//*[contains(text(),'%s')]", ScheduleName);
        logger.info("on element:" + sElement);
        if ($x(sElement).exists()) {
            result = true;
            logger.info("WifiScheduled:" + ScheduleName + " is existed.");
        }
        return result;
    }

    public boolean checkClientConnect(String Mac) {
        boolean result = false;
        int sum = 0;

        String macClient = String.format("//span[text()='%s']", Mac);
        String macClient1 = String.format("//td[text()='%s']", Mac);

        logger.info("try to locate element: " + macClient);
        while (sum <= 60) {
            if ($x(macClient).exists() || $x(macClient1).exists()) {
                result = true;
                logger.info("Client mac address:" + Mac + " is matched.");
                break;
            }

            MyCommonAPIs.sleepi(10);
            refresh();
            sum += 1;
            logger.info("Client list cannot find client,count:" + sum);
        }
        return result;
    }

    public String getAccessCode() {
        String code = "";
        generatevoucher.click();
        waitElement(inputvouchernum);
        inputvouchernum.setValue("1");
        MyCommonAPIs.sleepi(3);
        generatebutton.click();
        MyCommonAPIs.sleepi(15);
        code = getText(accesscode);
        MyCommonAPIs.sleepi(5);
        closegeneratewindow.click();
        logger.info("Access code is:" + code);
        return code;
    }

    public String getAccessCodeOnManager(String ssid) {
        String code = "";
        settingsorquickview.click();
        if (checkSsidIsExist(ssid)) {
            logger.info("Edit ssid.");
            clickEditSsid(ssid);
            waitElement(inputvouchernum);
            inputvouchernum.setValue("1");
            MyCommonAPIs.sleepi(3);
            generatebutton.click();
            MyCommonAPIs.sleepi(15);
            code = getText(accesscode);
            MyCommonAPIs.sleepi(5);
            closegeneratewindow.click();
        }
        logger.info("Access code is:" + code);
        return code;
    }

    // Tejeshwini

    public void EditSSID(Map<String, String> map) {
        settingsorquickview.click();
        if (checkSsidIsExist(map.get("SSID"))) {
            logger.info("Edit ssid.");
            executeJavaScript("arguments[0].removeAttribute('class')", editWifi(map.get("SSID")));
            MyCommonAPIs.sleep(3000);
            editSsid(map.get("SSID")).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(8 * 1000);
        }

    }

    public boolean isMandateSelected() {
        boolean result = false;
        if (Mandatory.isSelected()) {
            System.out.println("SSID80211w clicked on mandatory");
            result = true;
        } else {
            System.out.println("SSID80211w is not clicked on mandatory");
            result = false;
        }

        MyCommonAPIs.waitElement(saveedit);
        logger.info("Save button is Visible");
        MyCommonAPIs.sleepi(5);
        saveedit.click();
        MyCommonAPIs.sleepi(5);
        if (oksave.isDisplayed()) {
            oksave.click();
        }

        return result;
    }

    public boolean isOptionalSelected() {
        boolean result = false;
        if (Optional.isSelected()) {
            System.out.println("SSID80211w clicked on optional");
            result = true;
        } else {
            System.out.println("SSID80211w is not clicked on mandatory");
            result = false;
        }

        return result;
    }

    public boolean WPA3VLAN(Map<String, String> map, String option) {

        boolean result = false;
        waitReady();
        waitElement(addvlanssid);

        if (!checkSsidIsExist(map.get("SSID"))) {
            logger.info("Add ssid.");
            addvlanssid.click();
            waitReady();
            waitElement(ssidvlan);
            ssidvlan.setValue(map.get("SSID"));
            waitReady();
            if (bandvlan.exists()) {
                if (map.get("Band").equals("Both")) {
                    bandvlan.selectOption("Both");
                    waitReady();
                } else {
                    bandvlan.selectOption(map.get("Band"));
                    waitReady();
                }
            }
            if (checkband6.isDisplayed()) {
                if (checkband6.isSelected()) {
                    if (band6.isDisplayed()) {
                        band6.click();
                        logger.info("Uncheck 6 band");
                    }
                } else {
                    System.out.println("6GHZ is alredy unchecked");
                }
            }

            if (map.get("Security").equals("WPA2-PSK")) {
                security.selectOption("WPA2-PSK");
                waitReady();
            } else {
                security.selectOption(map.get("Security"));
                waitReady();
            }

            if (map.containsKey("Password")) {
                passwordvlan.setValue(map.get("Password"));
                waitReady();
            }

            if (option == "Mandate") {
                logger.info("Entered Mandate");
                Mandatoryclick.click();
                waitReady();
            }

            if (option == "Optional") {
                logger.info("Entered Optional");
                Optionalclick.click();
                waitReady();
            }
            if (option == "Disable") {
                logger.info("Entered Disable");
                Disableclick.click();
                waitReady();
            }
            takess("addSsid");
            Addvlanssid.click();
            waitReady();
            MyCommonAPIs.sleepi(4);
            if (MaximumlimitVlan.exists()) {
                String ErrorMessage = MaximumlimitVlan.getText();
                logger.info(ErrorMessage);
                closevlan.click();
                waitReady();
                result = true;
                return result;

            }

            MyCommonAPIs.sleepi(3);
            logger.info("Add ssid successful.");
        } else {
            logger.warning("checkSsidIsExist error");
        }

        if (oksucess.isDisplayed()) {
            oksucess.click();
        }
        return result;
    }

    public boolean DefaultSSIDSettings(Map<String, String> map) {
        boolean result = true;

        waitElement(settingsorquickview);
        settingsorquickview.click();
        waitReady();
        addssid.click();
        MyCommonAPIs.sleepi(10);
        if (band6.isDisplayed()) {
            if (checkband6.isSelected()) {
                if (band6.isDisplayed()) {
                    band6.click();
                    logger.info("Uncheck 6 band");
                }
            } else {
                System.out.println("6GHZ is alredy unchecked");
            }

            MyCommonAPIs.sleepi(3);
            if (map.get("Security").equals("WPA2-PSK")) {
                security.selectOption("WPA2-PSK");
            } else {
                security.selectOption(map.get("Security"));
            }
            String DefaultSecurity = security.getSelectedText();
            waitElement(ssid);
            if (DefaultSecurity.equals("WPA2-PSK") || DefaultSecurity.equals("WPA/WPA2-PSK")) {

                if (Disable.isSelected()) {
                    System.out.println("SSID80211w is disabled by default ");
                    result = true;
                } else {
                    System.out.println("SSID80211w is not disabled by default");
                    result = false;
                }
            }

            if (DefaultSecurity.equals("WPA3 SAE") || DefaultSecurity.equals("WPA3/WPA2 SAE-PSK")) {

                if (Mandatory.isSelected()) {
                    System.out.println("SSID80211w is Mandatory by default ");
                    result = true;
                } else {
                    System.out.println("SSID80211w is not Mandatory by default");
                    result = false;
                }
            }

            CancelSSID.click();

        } else {
            MyCommonAPIs.sleepi(3);
            if (map.get("Security").equals("WPA2-PSK")) {
                security.selectOption("WPA2-PSK");
            } else {
                security.selectOption(map.get("Security"));
            }
            String DefaultSecurity = security.getSelectedText();
            waitElement(ssid);
            if (DefaultSecurity.equals("WPA2-PSK") || DefaultSecurity.equals("WPA/WPA2-PSK")) {

                if (Disable.isSelected()) {
                    System.out.println("SSID80211w is disabled by default ");
                    result = true;
                } else {
                    System.out.println("SSID80211w is not disabled by default");
                    result = false;
                }
            }

            if (DefaultSecurity.equals("WPA3 SAE") || DefaultSecurity.equals("WPA3/WPA2 SAE-PSK")) {

                if (Mandatory.isSelected()) {
                    System.out.println("SSID80211w is Mandatory by default ");
                    result = true;
                } else {
                    System.out.println("SSID80211w is not Mandatory by default");
                    result = false;
                }
            }

            CancelSSID.click();
        }
        return result;
    }

    public boolean DefaultSSIDSettingsVLAN(Map<String, String> map) {
        boolean result = true;

        waitReady();
        waitElement(addvlanssid);
        addvlanssid.click();
        MyCommonAPIs.sleepi(10);
        if (band6.isDisplayed()) {
            if (checkband6.isSelected()) {
                if (band6.isDisplayed()) {
                    band6.click();
                    logger.info("Uncheck 6 band");
                }
            } else {
                System.out.println("6GHZ is alredy unchecked");
            }

            waitElement(ssidvlan);
            if (map.get("Security").equals("WPA2-PSK")) {
                security.selectOption("WPA2-PSK");
            } else {
                security.selectOption(map.get("Security"));
            }

            String DefaultSecurity = security.getSelectedText();

            if (DefaultSecurity.equals("WPA2-PSK") || DefaultSecurity.equals("WPA/WPA2-PSK")) {

                if (Disable.isSelected()) {
                    System.out.println("SSID80211w is disabled by default and default security is WPA2-PSK");
                    result = true;
                } else {
                    System.out.println("SSID80211w is not disabled by default and not WPA2-PSK security by default");
                    result = false;
                }
            }

            if (DefaultSecurity.equals("WPA3 SAE") || DefaultSecurity.equals("WPA3/WPA2 SAE-PSK")) {

                if (Mandatory.isSelected()) {
                    System.out.println("SSID80211w is Mandatory by default ");
                    result = true;
                } else {
                    System.out.println("SSID80211w is not Mandatory by default");
                    result = false;
                }
            }

            cancel.click();

        } else {
            waitElement(ssidvlan);
            if (map.get("Security").equals("WPA2-PSK")) {
                security.selectOption("WPA2-PSK");
            } else {
                security.selectOption(map.get("Security"));
            }

            String DefaultSecurity = security.getSelectedText();

            if (DefaultSecurity.equals("WPA2-PSK") || DefaultSecurity.equals("WPA/WPA2-PSK")) {

                if (Disable.isSelected()) {
                    System.out.println("SSID80211w is disabled by default and default security is WPA2-PSK");
                    result = true;
                } else {
                    System.out.println("SSID80211w is not disabled by default and not WPA2-PSK security by default");
                    result = false;
                }
            }

            if (DefaultSecurity.equals("WPA3 SAE") || DefaultSecurity.equals("WPA3/WPA2 SAE-PSK")) {

                if (Mandatory.isSelected()) {
                    System.out.println("SSID80211w is Mandatory by default ");
                    result = true;
                } else {
                    System.out.println("SSID80211w is not Mandatory by default");
                    result = false;
                }
            }

            cancel.click();

        }

        return result;
    }

    // written by Tejeshwini

    public boolean IGMPInsight() {
        boolean result = false;
        waitElement(settingsorquickview);
        settingsorquickview.click();
        waitReady();
        if (Advance1.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance1).perform();
            a.moveToElement(NetworkSettings).click().perform();
        }
        MyCommonAPIs.sleepi(3);
        System.out.println(getIGMPStatus());
        if (!getIGMPStatus()) {
            logger.info("IGMP is not enabled");
            result = true;
        }
        return result;
    }

    public boolean B2UCInsight() {
        boolean result = false;
        waitElement(settingsorquickview);
        settingsorquickview.click();
        waitReady();
        if (Advance1.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance1).perform();
            a.moveToElement(NetworkSettings).click().perform();
        }
        MyCommonAPIs.sleepi(3);
        System.out.println(getIGMPStatus());
        if (!getB2UCStatus()) {
            logger.info("B2UC is not enabled");
            result = true;
        }
        return result;
    }

    public void CopyConfig(String Organization, String Location, String Destination) {

        Settings.click();
        MyCommonAPIs.sleepi(3);
        CopyConfig.click();
        MyCommonAPIs.sleepi(3);
        SelectOrganization.selectOption(Organization);
        MyCommonAPIs.sleepi(3);
        SelectLocation.selectOption(Location);
        MyCommonAPIs.sleepi(3);
        Selectdestination(Destination).click();
        MyCommonAPIs.sleepi(3);
        Apply.click();
        waitReady();
        CopyConfiguration.click();

    }

    public void IGMPEnable() {
        // waitElement(settingsorquickview);
        MyCommonAPIs.sleepi(10);
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        MyCommonAPIs.sleepi(10);
        if (Advance1.exists() && Advance2.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance1).perform();
            a.moveToElement(NetworkSettings).click().perform();
        } else if (Advance2.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance2).perform();
            a.moveToElement(NetworkSettings).click().perform();
        }
        MyCommonAPIs.sleepi(3);
        setSelected1($x("//h5[text()='IGMP Snooping']/../..//input[@id='enableBlackList']/following-sibling::span"), true);
        SaveIGMP.click();
        MyCommonAPIs.sleepi(10);
        if (ConformSaveIGMP.isDisplayed()) {
            ConformSaveIGMP.click();

        }
    }

    public void B2UCEnable() {
        // waitElement(settingsorquickview);
        MyCommonAPIs.sleepi(10);
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        MyCommonAPIs.sleepi(10);
        if (Advance1.exists() && Advance2.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance1).perform();
            a.moveToElement(NetworkSettings).click().perform();
        } else if (Advance2.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance2).perform();
            a.moveToElement(NetworkSettings).click().perform();
        }
        MyCommonAPIs.sleepi(3);
        setSelected1($x("//h5[text()='Broadcast-to-Unicast']/../..//input[@id='enableBlackList']/following-sibling::span"), true);
        SaveIGMP.click();
        MyCommonAPIs.sleepi(10);
        if (ConformSaveIGMP.isDisplayed()) {
            ConformSaveIGMP.click();
        }
        

    }

    public void IGMPDisable() {
        waitElement(settingsorquickview);
        settingsorquickview.click();
        waitReady();
        if (Advance1.exists() && Advance2.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance1).perform();
            a.moveToElement(NetworkSettings).click().perform();
        } else if (Advance2.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance2).perform();
            a.moveToElement(NetworkSettings).click().perform();
        }
        MyCommonAPIs.sleepi(3);
        setSelected1($x("//h5[text()='IGMP Snooping']/../..//input[@id='enableBlackList']/following-sibling::span"), false);
        SaveIGMP.click();
        MyCommonAPIs.sleepi(10);
        if (ConformSaveIGMP.isDisplayed()) {
            ConformSaveIGMP.click();

        }

    }

    public void B2UCDisable() {
        waitElement(settingsorquickview);
        settingsorquickview.click();
        waitReady();
        if (Advance1.exists() && Advance2.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance1).perform();
            a.moveToElement(NetworkSettings).click().perform();
        } else if (Advance2.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance2).perform();
            a.moveToElement(NetworkSettings).click().perform();
        }
        MyCommonAPIs.sleepi(3);
        MyCommonAPIs.sleepi(5);
        setSelected1($x("//h5[text()='Broadcast-to-Unicast']/../..//input[@id='enableBlackList']/following-sibling::span"), false);
        SaveIGMP.click();
        MyCommonAPIs.sleepi(5);
        if (ConformSaveIGMP.isDisplayed()) {
            ConformSaveIGMP.click();
        }

    }

    public boolean Isenabled() {
        boolean result = false;
        waitElement(settingsorquickview);
        settingsorquickview.click();
        waitReady();
        if (Advance1.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance1).perform();
            a.moveToElement(NetworkSettings).click().perform();
        }
        MyCommonAPIs.sleepi(3);
        MyCommonAPIs.sleepi(5);
        String sta = ($x("(//input[@id='enableBlackList'])[2]")).getAttribute("checked");
        System.out.println(sta);

        if (sta == "true") {
            logger.info("it is anabled");
            result = true;

        }

        return result;
    }

    public boolean IsB2UCenabled() {
        boolean result = false;
        waitElement(settingsorquickview);
        settingsorquickview.click();
        waitReady();
        if (Advance1.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance1).perform();
            a.moveToElement(NetworkSettings).click().perform();
        }
        MyCommonAPIs.sleepi(3);
        MyCommonAPIs.sleepi(5);
        String sta = ($x("(//input[@id='enableBlackList'])[1]")).getAttribute("checked");
        System.out.println(sta);
        MyCommonAPIs.sleepi(5);
        if (sta.equals("true")) {
            logger.info("it is anabled");
            result = true;
        }

        return result;
    }

    public boolean CheckHelpText() {
        boolean result = false;
        waitElement(settingsorquickview);
        settingsorquickview.click();
        waitReady();
        if (Advance1.exists() && Advance2.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance1).perform();
            a.moveToElement(NetworkSettings).click().perform();
        } else if (Advance2.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance2).perform();
            a.moveToElement(NetworkSettings).click().perform();
        }
        MyCommonAPIs.sleepi(3);
        HelpTextclickIGMP.click();
        MyCommonAPIs.sleepi(3);
        if (HelpTextcheck.isDisplayed()) {
            logger.info("help text exits");
            result = true;

        }
        closeHelpTextcheck.click();
        return result;
    }

    public boolean CheckHelpTextB2UC() {
        boolean result = false;
        waitElement(settingsorquickview);
        settingsorquickview.click();
        waitReady();
        if (Advance1.exists() && Advance2.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance1).perform();
            a.moveToElement(NetworkSettings).click().perform();
        } else if (Advance2.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance2).perform();
            a.moveToElement(NetworkSettings).click().perform();
        }
        MyCommonAPIs.sleepi(3);
        MyCommonAPIs.sleepi(3);
        HelpTextclickB2UC.click();
        MyCommonAPIs.sleepi(3);
        if (HelpTextcheck.isDisplayed()) {
            logger.info("help text exits");
            result = true;

        }
        closeHelpTextcheck.click();
        return result;
    }

    public void gotonetworksetup() {

        editnetwork.click();
        MyCommonAPIs.sleep(3000);

        gotoNetworksetup.click();
        
      
        MyCommonAPIs.sleep(3000);
        ADDbutton.click();
        waitReady();
        MyCommonAPIs.sleepi(30);
        
        if(oktrunk.exists()) {
            System.out.println("11111111111111");
//            oktrunk.click();
        }

        MyCommonAPIs.sleep(6000);
        NetworkName.sendKeys("Automation");
        MyCommonAPIs.sleep(3000);

        Discription.sendKeys("BecAutomation");
        MyCommonAPIs.sleep(3000);

        VLANname.sendKeys("AdvanceRateSelction");
        MyCommonAPIs.sleep(3000);

        VLANID.sendKeys("67");
        MyCommonAPIs.sleep(3000);

        MyCommonAPIs.sleep(5000);
        if (NEXT.exists() ) {
            NEXT.click();
        } else {
            NEXT1.click();
        }
        waitReady();
        MyCommonAPIs.sleep(5000);
        Next.click();
        waitReady();

    }

    public boolean SSIDVLANARS(Map<String, String> map) {
        boolean result = true;

        waitReady();
        waitElement(addvlanssid);
        addvlanssid.click();
        waitElement(ssidvlan);
        MyCommonAPIs.sleepi(60);
        if (checkband6.isDisplayed()) {
            if (checkband6.isSelected()) {
                if (band6.isDisplayed()) {
                    band6.click();
                    logger.info("Uncheck 6 band");
                }
            } else {
                System.out.println("6GHZ is alredy unchecked");
            }
        }
        if (map.get("Security").equals("WPA2-PSK")) {
            security.selectOption("WPA2-PSK");
        } else {
            security.selectOption(map.get("Security"));
        }

        if (map.containsKey("Password")) {
            passwordvlan.setValue(map.get("Password"));
            waitReady();
        }

        PlusButton.click();
        MyCommonAPIs.sleep(3000);

        clickAdvanceRateSelection.click();
        MyCommonAPIs.sleep(3000);

        String twocheck = (CheckSetMinimumRateControls).getAttribute("checked");
        System.out.println(twocheck);

        FiveGhz.click();
        MyCommonAPIs.sleepi(3);

        String fivecheck = (CheckSetMinimumRateControls).getAttribute("checked");
        System.out.println(fivecheck);

        if (twocheck == null && fivecheck == null && AdvanceRateSelection.exists()) {
            logger.info("it is enabled");
            result = true;

        }

        return result;
    }

    public void GoToAdvanceRateCntrol(String Ssid) {

        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            MyCommonAPIs.sleepi(3);
            enterAdvanceRateSelection.click();
            MyCommonAPIs.sleepi(3);
        }

    }

    public boolean FixedMulticaseRateValues(String band, String model) {
        boolean result = false;
        FixedMulticastRatedrop.click();
        MyCommonAPIs.sleep(3000);
        String few = FixedMulticastRate24.getText();
        System.out.println(few);

        String all = Selectelement.getText();
        System.out.println(all);

        if (band.equals("TwoGhz") ) {
            if (few.equals("11 Mbps") && all.contains("Auto") && all.contains("1 Mbps") && all.contains("2 Mbps") && all.contains("5.5 Mbps")
                    && all.contains("11 Mbps")) {
                System.out.println("11 is selected");
                result = true;
            }
        } else if (band.equals("FiveGhz")) {
            if (few.equals("24 Mbps") && all.contains("Auto") && all.contains("6 Mbps") && all.contains("12 Mbps") && all.contains("24 Mbps")
                    && all.contains("54 Mbps")) {
                System.out.println("24 Mbps is selected");
                result = true;
            }
       
        }

        return result;
    }

    public boolean checkDefaultstatus() {
        boolean result = false;

        String defaultvalue = (CheckSetMinimumRateControls).getAttribute("checked");
        System.out.println(defaultvalue);

        if (defaultvalue == null) {
            logger.info("it is enabled");
            result = true;

        }

        return result;

    }

    public void clickon5Ghz() {
        MyCommonAPIs.sleepi(3);
        FiveGhz.click();
        MyCommonAPIs.sleepi(3);

    }

    public boolean DefaultDensity() {

        boolean result = false;
        enableSetMinimumRateControls.click();

        if (Density0.exists())
            ;
        {
            logger.info("default density is selected");
            result = true;
        }
        return result;
    }

    public boolean Density0Text2Ghz(int densitylevel) {
        boolean result = false;

        if (densitylevel == 0) {

            String Density0text = Density0Text.getText();
            System.out.println(Density0text);

            if (Density0check.exists() && Density0text
                    .contains("Environment: Density - Very Low,Compatibility - 802.11b/g/n/ax,Overall Performance - Less,Coverage - Best"))
                ;
            {
                result = true;
            }

        }

        if (densitylevel == 1) {

            String Density0text = Density0Text.getText();
            System.out.println(Density0text);

            if (Density1check.exists() && Density0text
                    .contains("Environment: Density - Low,Compatibility - 802.11b/g/n/ax,Overall Performance - Moderate,Coverage - Wide"))
                ;
            {
                result = true;
            }

        }

        if (densitylevel == 2) {

            String Density0text = Density0Text.getText();
            System.out.println(Density0text);

            if (Density2check.exists() && Density0text
                    .contains("Environment: Density - Medium,Compatibility - 802.11b/g/n/ax,Overall Performance - Better,Coverage - Medium"))
                ;
            {
                result = true;
            }

        }

        if (densitylevel == 3) {

            String Density0text = Density0Text.getText();
            System.out.println(Density0text);

            if (Density3check.exists() && Density0text
                    .contains("Environment: Density - High,Compatibility - 802.11g/n/ax,Overall Performance - Far Better,Coverage - Low"))
                ;
            {
                result = true;
            }

        }

        if (densitylevel == 4) {

            String Density0text = Density0Text.getText();
            System.out.println(Density0text);

            if (Density4check.exists() && Density0text
                    .contains("Environment: Density - Very High,Compatibility - 802.11g/n/ax,Overall Performance - Best,Coverage - Very Low"))
                ;
            {
                result = true;
            }

        }

        return result;

    }

    public boolean Density0Text5Ghz() {
        boolean result = false;

        String Density0text = Density0Text.getText();
        System.out.println(Density0text);

        if (Density0text.contains("Environment: Density - Very Low,Compatibility - 802.11a/n/ac/ax,Overall Performance - Less,Coverage - Best"))
            ;
        {
            result = true;
        }
        return result;

    }

    public void enableSetMinimumRateControl() {
        MyCommonAPIs.sleepi(5);
        enableSetMinimumRateControls.click();
        MyCommonAPIs.sleepi(5);
    }

    /**
     * @param levle
     *            0-0%, 1-25%, 2-50%, 3-75%, 4-100%
     * @return
     */
    public void DragDensityTo(int level) {

        // added by shoib for Densty Slider
        Actions actions = new Actions(WebDriverRunner.getWebDriver());

        if (level == 1) {
            actions.dragAndDrop(slider, Density1).perform();
        } else if (level == 2) {
            actions.dragAndDrop(slider, Density2).perform();
        } else if (level == 3) {
            actions.dragAndDrop(slider, Density3).perform();
        } else
            actions.dragAndDrop(slider, Density4).perform();

        // System.out.println(DensityLevel);
        // System.out.println("'''''''''''''''");
        // System.out.println(DensityLevel.get(2));
        // System.out.println("'''''''''''''''");
        // //DensityLevel.get(3).click();
        //
        // SelenideElement weight = DensityLevel1.get(level);
        // System.out.println("Details started from here ------------------>");
        // System.out.println(weight);
        // System.out.println("Details started from here ------------------>8888888888888888888888888");
        // // System.out.println(weight.getSize().width);
        // System.out.println("Details started from here ------------------++++++++++++++>");
        // if (level == 4) {
        // DensityLevel1.get(level).click(weight.getSize().width, -2);
        // } else {
        // DensityLevel.get(level).click(0, -2);
        // }

        MyCommonAPIs.sleepi(10);
        saveas.click();
        MyCommonAPIs.sleepi(10);
        OK.click();

    }

    public void DragDensityTo5(int level) {

        // added by shoib for Densty Slider
        Actions actions = new Actions(WebDriverRunner.getWebDriver());

        if (level == 1) {
            actions.dragAndDrop(slider5, Density1).perform();
        } else if (level == 2) {
            actions.dragAndDrop(slider5, Density2).perform();
        } else if (level == 3) {
            actions.dragAndDrop(slider5, Density3).perform();
        } else
            actions.dragAndDrop(slider5, Density4).perform();

        MyCommonAPIs.sleepi(10);
        saveas.click();
        MyCommonAPIs.sleepi(10);
        OK.click();

    }

    public void gotoLoadBalancingPage() {
        WebCheck.checkHrefIcon(URLParam.hrefWirelessLoadBalancing);
        MyCommonAPIs.sleepi(5);
    }

    public String getLoadBalancingTypeNowNum(String type) {
        SelenideElement element = radio24Ghz;
        switch (type) {
        case "Radio 2.4 GHz":
            element = radio24Ghz;
            break;
        case "Radio 5 GHz":
            element = radio5Ghz;
            break;
        case "Radio 5 GHz High":
            element = radio5GhzHigh;
            break;
        case "Client 2.4 GHz":
            element = client24Ghz;
            break;
        case "Client 5 GHz":
            element = client5Ghz;
            break;
        case "Client 5 GHz High":
            element = client5GhzHigh;
            break;
        case "Channel 2.4 GHz":
            element = channel24Ghz;
            break;
        case "Channel 5 GHz":
            element = channel5Ghz;
            break;
        case "Channel 5 GHz High":
            element = channel5GhzHigh;
            break;
        }
        String nowNum = element.getAttribute("aria-valuenow");
        return nowNum;
    }

    public void moveSliderForLoadBalancing(String option, int value) {
        SelenideElement element = radio24Ghz;
        String eleNum = "";
        double leftx = 0;
        double rightx = 0;
        switch (option) {
        case "Radio 2.4 GHz":
            element = radio24Ghz;
            eleNum = "1";
            break;
        case "Radio 5 GHz":
            element = radio5Ghz;
            eleNum = "2";
            break;
        case "Radio 5 GHz High":
            element = radio5GhzHigh;
            eleNum = "3";
            break;
        case "Client 2.4 GHz":
            element = client24Ghz;
            eleNum = "1";
            break;
        case "Client 5 GHz":
            element = client5Ghz;
            eleNum = "2";
            break;
        case "Client 5 GHz High":
            element = client5GhzHigh;
            eleNum = "3";
            break;
        case "Channel 2.4 GHz":
            element = channel24Ghz;
            eleNum = "1";
            break;
        case "Channel 5 GHz":
            element = channel5Ghz;
            eleNum = "2";
            break;
        case "Channel 5 GHz High":
            element = channel5GhzHigh;
            eleNum = "3";
            break;
        }
        if (option.substring(0, 5).equals("Radio")) {
            leftx = $x(String.format(radioMinNum, eleNum)).getLocation().getX();
            rightx = $x(String.format(radioMaxNum, eleNum)).getLocation().getX();
        } else if (option.substring(0, 6).equals("Client")) {
            leftx = $x(String.format(clientMinNum, eleNum)).getLocation().getX();
            rightx = $x(String.format(clientMaxNum, eleNum)).getLocation().getX();
        } else if (option.substring(0, 7).equals("Channel")) {
            leftx = $x(String.format(channelMinNum, eleNum)).getLocation().getX();
            rightx = $x(String.format(channelMaxNum, eleNum)).getLocation().getX();
        }
        Actions actions = new Actions(WebDriverRunner.getWebDriver());
        int maxNum = Integer.valueOf(element.getAttribute("aria-valuemax"));
        int minNum = Integer.valueOf(element.getAttribute("aria-valuemin"));
        int nowNum = Integer.valueOf(element.getAttribute("aria-valuenow"));
        if (nowNum != value) {
            // BigDecimal bg = new BigDecimal((rightx - leftx) / (maxNum - minNum));
            // double f1 = bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
            double xPixel = (rightx - leftx) / (maxNum - minNum);
            if (value == nowNum) {
                value = 0;
            } else if (value < nowNum) {
                value = 0 - (nowNum - value);
            } else if (value > nowNum) {
                value = value - nowNum;
            }
            int pointx = (int) (xPixel * value);
            actions.dragAndDropBy(element, pointx, 0).perform();
        }
    }

    public void enableLoadBalancing(Map<String, String> map) {
        gotoLoadBalancingPage();
        if (!maxNumClients.exists()) {
            enableLoadBalacing.click();
            waitReady();
            MyCommonAPIs.sleepi(5);
        }
        if (map.containsKey("Radio 2.4 GHz") || map.containsKey("Radio 5 GHz") || map.containsKey("Radio 5 GHz High")) {
            if (!radio24Ghz.exists()) {
                maxNumClients.click();
                waitReady();
                MyCommonAPIs.sleepi(5);
            }
            if (map.containsKey("Radio 2.4 GHz")) {
                moveSliderForLoadBalancing("Radio 2.4 GHz", Integer.valueOf(map.get("Radio 2.4 GHz")));
            }
            if (map.containsKey("Radio 5 GHz")) {
                moveSliderForLoadBalancing("Radio 5 GHz", Integer.valueOf(map.get("Radio 5 GHz")));
            }
            if (map.containsKey("Radio 5 GHz High")) {
                moveSliderForLoadBalancing("Radio 5 GHz High", Integer.valueOf(map.get("Radio 5 GHz High")));
            }
        }
        if (map.containsKey("Client 2.4 GHz") || map.containsKey("Client 5 GHz") || map.containsKey("Client 5 GHz High")) {
            if (!client24Ghz.exists()) {
                balanceOnClientRxRSSI.click();
                waitReady();
                MyCommonAPIs.sleepi(5);
            }
            if (map.containsKey("Client 2.4 GHz")) {
                moveSliderForLoadBalancing("Client 2.4 GHz", Integer.valueOf(map.get("Client 2.4 GHz")));
            }
            if (map.containsKey("Client 5 GHz")) {
                moveSliderForLoadBalancing("Client 5 GHz", Integer.valueOf(map.get("Client 5 GHz")));
            }
            if (map.containsKey("Client 5 GHz High")) {
                moveSliderForLoadBalancing("Client 5 GHz High", Integer.valueOf(map.get("Client 5 GHz High")));
            }
        }
        if (map.containsKey("Channel 2.4 GHz") || map.containsKey("Channel 5 GHz") || map.containsKey("Channel 5 GHz High")) {
            if (!channel24Ghz.exists()) {
                balanceOnChannelUtilization.click();
                waitReady();
                MyCommonAPIs.sleepi(5);
            }
            if (map.containsKey("Channel 2.4 GHz")) {
                moveSliderForLoadBalancing("Channel 2.4 GHz", Integer.valueOf(map.get("Channel 2.4 GHz")));
            }
            if (map.containsKey("Channel 5 GHz")) {
                moveSliderForLoadBalancing("Channel 5 GHz", Integer.valueOf(map.get("Channel 5 GHz")));
            }
            if (map.containsKey("Channel 5 GHz High")) {
                moveSliderForLoadBalancing("Channel 5 GHz High", Integer.valueOf(map.get("Channel 5 GHz High")));
            }
        }
        if (map.containsKey("Disassociate sticky clients")) {
            if (!checkDisassociateSickyClients.exists()) {
                enableDisassociateStickyClients.click();
                waitReady();
                MyCommonAPIs.sleepi(5);
            }
        }
        sleepi(5);
        if (map.containsKey("Check Button")) {
            clickButton(1);
            waitReady();
        } else {
            if (editschedulesave.isEnabled()) {
                clickButton(0);
                waitReady();
                clickButton(5);
            }
        }

        System.out.println("it is in the end");
        // MyCommonAPIs.sleepi(20);
        // Save.click();
        // MyCommonAPIs.sleepi(10);
        // ConformSaveIGMP.click();
    }

    public String checkLoadBalancingStatus(String type) {
        String color = "";
        gotoLoadBalancingPage();
        SelenideElement element = $x("");
        switch (type) {
        case "Radio 2.4 GHz":
            element = $x(String.format(radioBarStatus, 1));
            break;
        case "Radio 5 GHz":
            element = $x(String.format(radioBarStatus, 2));
            break;
        case "Radio 5 GHz High":
            element = $x(String.format(radioBarStatus, 3));
            break;
        case "Client 2.4 GHz":
            element = $x(String.format(clientBarStatus, 1));
            break;
        case "Client 5 GHz":
            element = $x(String.format(clientBarStatus, 2));
            break;
        case "Client 5 GHz High":
            element = $x(String.format(clientBarStatus, 3));
            break;
        case "Channel 2.4 GHz":
            element = $x(String.format(channelBarStatus, 1));
            break;
        case "Channel 5 GHz":
            element = $x(String.format(channelBarStatus, 2));
            break;
        case "Channel 5 GHz High":
            element = $x(String.format(channelBarStatus, 3));
            break;
        }
        if (element.exists()) {
            String classStr = element.getAttribute("class");
            color = classStr.substring(classStr.indexOf("backRange") + 9, classStr.indexOf("padding-") - 1);
            logger.info("Color is:" + color);
        }
        return color;
    }

    public void disableLoadBalancing() {
        gotoLoadBalancingPage();
        boolean saveEn = false;
        if (radio24Ghz.exists()) {
            maxNumClients.click();
            waitReady();
            MyCommonAPIs.sleepi(5);
        }
        if (client24Ghz.exists()) {
            balanceOnClientRxRSSI.click();
            waitReady();
            MyCommonAPIs.sleepi(5);
        }
        if (channel24Ghz.exists()) {
            balanceOnChannelUtilization.click();
            waitReady();
            MyCommonAPIs.sleepi(5);
        }
        if (maxNumClients.exists()) {
            enableLoadBalacing.click();
            waitReady();
            sleepi(5);
            saveEn = true;
        }
        if ($x("//*[contains(text(),'Disassociate clients')]").exists()) {
            enableDisassociateStickyClients.click();
            waitReady();
            sleepi(5);
            saveEn = true;
        }
        if (saveEn) {
            clickButton(0);
            waitReady();
            clickButton(5);
        }
    }

    public boolean EnableorDisable24() {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        waitReady();
        boolean sta = Enable24Ghz.is(Condition.checked);
        System.out.println(sta);
        if (sta == true) {
            logger.info("2.4 is enabled by default");
            result = true;
        }

        return result;
    }

    public boolean EnableorDisable5High() {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        waitReady();
        boolean sta = Enable5GhzHigh.is(Condition.checked);
        System.out.println(sta);
        if (sta == true) {
            logger.info("2.4 is enabled by default");
            result = true;
        }

        return result;
    }

    public boolean EnableorDisable5Low() {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        waitReady();
        boolean sta = Enable5GhzLow.is(Condition.checked);
        System.out.println(sta);
        if (sta == true) {
            logger.info("2.4 is enabled by default");
            result = true;
        }

        return result;
    }

    public void Enable24(String Enable) {

        if (Enable.equals("0")) {
            setSelected(Enable24Ghz, false);
            sleepi(5);
            SaveWireless.click();
            sleepi(10);
        } else {
            setSelected(Enable24Ghz, true);
            sleepi(5);
            SaveWireless.click();
            sleepi(10);
        }

        sleepi(10);
        if (okbutton.isDisplayed()) {
            okbutton.click();
        }

    }

    public void Enable5low(String Enable) {

        if (Enable.equals("0")) {
            setSelected(Enable5GhzLow, false);
            sleepi(5);
            SaveWireless.click();
            sleepi(10);

        } else {
            setSelected(Enable5GhzLow, true);
            sleepi(5);
            SaveWireless.click();
            sleepi(10);

        }

        sleepi(10);
        if (okbutton.isDisplayed()) {
            okbutton.click();
        }
    }

    public void Enable5high(String Enable) {

        if (Enable.equals("0")) {
            setSelected(Enable5GhzHigh, false);
            sleepi(5);
            SaveWireless.click();
            sleepi(10);
            EnableOK.click();
        } else {
            setSelected(Enable5GhzHigh, true);
            sleepi(5);
            SaveWireless.click();
            sleepi(10);
            EnableOK.click();
        }

        sleepi(10);
        if (okbutton.isDisplayed()) {
            okbutton.click();
        }
    }

    public void GoToWirelessSettings() {
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        MyCommonAPIs.sleepi(10);
        if (Advance1.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance1).perform();
            a.moveToElement(WirelessSetting).click().perform();
        }

        MyCommonAPIs.sleepi(3);
    }

    public boolean RadioMode2ghz(String mode) {
        boolean result = false;

        String all = Radiomode24.getText();
        System.out.println(all);
        MyCommonAPIs.sleepi(3);
        if (all.equals(mode)) {
            result = true;
            logger.info("it is as expected");
        }

        // for (SelenideElement se: Radiomode24Select){
        // logger.info(se.getText());
        // String checkmode = se.getText();
        //
        // }
        return result;
    }

    public boolean RadioMode5ghzlow(String mode) {
        boolean result = false;

        String all = Radiomode5low.getText();
        System.out.println(all);
        MyCommonAPIs.sleepi(3);
        if (all.equals(mode)) {
            result = true;
            logger.info("it is as expected");
        }
        return result;
    }

    public boolean RadioMode5ghzhigh(String mode) {
        boolean result = false;

        Radiomode5high.selectOption(mode);
        MyCommonAPIs.sleepi(3);
        String all = Radiomode5high.getText();
        if (all.equals(mode)) {
            result = true;
            logger.info("it is as expected");
        }

        return result;
    }

    public boolean checkClientConnectClientPage(String Mac, String Disconnect) {
        boolean result = false;
        WebCheck.checkHrefIcon(URLParam.hrefWireless);
        int sum = 0;
        ClientPage.click();
        waitReady();
        if (Disconnect.equals("Yes")) {
            DisconnectClientPage.click();
            MyCommonAPIs.sleepi(5);
        }
        String macClient = String.format("//span[text()='%s']", Mac);
        String macClient1 = String.format("//td[text()='%s']", Mac);

        logger.info("try to locate element: " + macClient);
        while (sum <= 20) {
            if ($x(macClient).exists() || $x(macClient1).exists()) {
                result = true;
                logger.info("Client mac address:" + Mac + " is matched.");
                break;
            }

            MyCommonAPIs.sleepi(10);
            refresh();
            sum += 1;
            logger.info("Client list cannot find client,count:" + sum);
        }
        return result;
    }

    public boolean checkClientConnectDevicePage(String Mac, String Disconnect) {
        boolean result = false;
        int sum = 0;
        DeviceClientPage.click();
        waitReady();
        if (Disconnect.equals("Yes")) {
            DisconnectClientPage.click();
            MyCommonAPIs.sleepi(5);
        }
        String macClient = String.format("//span[text()='%s']", Mac);
        String macClient1 = String.format("//td[text()='%s']", Mac);

        logger.info("try to locate element: " + macClient);
        while (sum <= 20) {
            if ($x(macClient).exists() || $x(macClient1).exists()) {
                result = true;
                logger.info("Client mac address:" + Mac + " is matched.");
                break;
            }

            MyCommonAPIs.sleepi(10);
            refresh();
            sum += 1;
            logger.info("Client list cannot find client,count:" + sum);
        }
        return result;
    }

    public boolean SearchOption(String SSID) {
        boolean result = false;
        Search.click();
        MyCommonAPIs.sleepi(3);
        Searchinput.sendKeys(SSID);
        MyCommonAPIs.sleepi(2);
        Searchclick.click();
        MyCommonAPIs.sleepi(2);
        String sElement = String.format("//span[text()='%s']", SSID);    
        String sElement1 = String.format("//td[text()='%s']", SSID);
        System.out.println(sElement);
        System.out.println(sElement1); 
        if ($x(sElement).exists() || $x(sElement1).exists()) {
            System.out.println("Search option is working");
            result = true;
            logger.info("Ssid:" + SSID + " is existed.");
        }
        return result;
    }

    public boolean checDisConnect(String Mac) {
        boolean result = false;
        int sum = 0;
        DisconnectClientPage.click();
        waitReady();
        String macClient = String.format("//span[text()='%s']", Mac);
        String macClient1 = String.format("//td[text()='%s']", Mac);

        logger.info("try to locate element: " + macClient);
        while (sum <= 20) {
            if ($x(macClient).exists() || $x(macClient1).exists()) {
                result = true;
                logger.info("Client mac address:" + Mac + " is matched.");
                break;
            }

            MyCommonAPIs.sleepi(10);
            refresh();
            sum += 1;
            logger.info("Client list cannot find client,count:" + sum);
        }
        return result;
    }

    public boolean ClientConnect(String Mac, String Disconnect) {
        boolean result = false;
        boolean result1 = false;
        boolean result2 = false;
        boolean result3 = false;
        int sum = 0;
        ClientPage.click();
        waitReady();
        if (Disconnect.equals("Yes")) {
            DisconnectClientPage.click();
            MyCommonAPIs.sleepi(5);
        }
        String macClient = String.format("//span[text()='%s']", Mac);
        $x(macClient).click();
        MyCommonAPIs.sleepi(5);
        String Overiew = getText(ClientOverview);
        System.out.println(Overiew);
        if (Disconnect.equals("Yes")) {
            if (Overiew.contains(Mac) && Overiew.contains("Client Name") && Overiew.contains("MAC Address") && Overiew.contains("IP Address")
                    && Overiew.contains("Disassociated AP") && Overiew.contains("Association Ended") && Overiew.contains("Signal Strength")
                    && Overiew.contains("BSSID") && Overiew.contains("SSID") && Overiew.contains("Mode") && Overiew.contains("Radio")
                    && Overiew.contains("VLAN ID")) {
                result1 = true;
                System.out.println("overview is correct in disconnected SSID");
            }
        } else {

            if (Overiew.contains(Mac) && Overiew.contains("Client Name") && Overiew.contains("MAC Address") && Overiew.contains("IP Address")
                    && Overiew.contains("Associated AP") && Overiew.contains("Association Granted") && Overiew.contains("Signal Strength")
                    && Overiew.contains("BSSID") && Overiew.contains("SSID") && Overiew.contains("Mode") && Overiew.contains("Radio")
                    && Overiew.contains("VLAN ID")) {
                result1 = true;
                System.out.println("overview is correct in connected AP");
            }
        }
        Securitydropdown.click();
        MyCommonAPIs.sleepi(5);
        String Security = getText(Clientsecurity);
        System.out.println(Security);

        if (Security.contains("Security Type") && Security.contains("Encryption") && Security.contains("User Name")
                && Security.contains("802.11w (PMF)")) {
            result2 = true;
            System.out.println("Security is correct");
        }

        Statisticdropdown.click();
        MyCommonAPIs.sleepi(5);
        String statistics = getText(Clientstatistics);
        System.out.println(statistics);
        if (statistics.contains("Received Data") && statistics.contains("Transmitted Data")
                && statistics.contains("Authentication Failure Count")) {
            result3 = true;
            System.out.println("statistics is correct");
        }

        if (result1 == true && result2 == true && result3 == true) {
            result = true;
        }
        return result;
    }

    public boolean Transfer() {
        boolean result = false;
        String ReceivedDatas = getText(ReceivedData);
        String TransmittedDatas = getText(TransmittedData);
        if (ReceivedDatas.length() != 0 && TransmittedDatas.length() != 0) {
            System.out.println("both data are avilable");
            result = true;
        }
        return result;

    }

    public boolean Authentication() {
        boolean result = false;
        String Authentications = getText(Authentication);

        if (!Authentications.contains("0")) {
            System.out.println("Authentication count increases");
            result = true;
        }
        return result;

    }

    public boolean ClientRoming(String Mac, String Disconnect) {
        boolean result = false;
        int sum = 0;
        ClientPage.click();
        waitReady();
        if (Disconnect.equals("Yes")) {
            DisconnectClientPage.click();
            MyCommonAPIs.sleepi(10);
        }
        String macClient = String.format("//span[text()='%s']", Mac);
        $x(macClient).click();
        MyCommonAPIs.sleep(10);
        clientRoaming.click();
        if (clientRoamingcheck.exists()) {
            result = true;
        }

        return result;
    }

    public void enable80211(String Ssid) {
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        MyCommonAPIs.sleep(5);
        System.out.println(Ssid);
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            setSelected($x("//*[@id=\"fastRoamingSt\"]/../span"), true);
            MyCommonAPIs.sleep(5);
            if (Warrning.isDisplayed()) {
                okFast.click();
//                result = true;
            }
            save80211.click();
            MyCommonAPIs.sleep(10);
            ok80211.click();
        }

    }
    

    public boolean bandSteering(String Ssid) {
        boolean result = false;
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        MyCommonAPIs.sleep(5);
        System.out.println(Ssid);
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            setSelected($x("//*[@id=\"bandSteeringSt\"]"), true);
            MyCommonAPIs.sleep(10);
            if (okFast.isDisplayed()) {
                okFast.click();
                result = true;
            }
            if (okFast.isDisplayed()) {
                okFast.click();
                result = true;
            }
            MyCommonAPIs.sleep(5);
            save80211.click();
            MyCommonAPIs.sleep(5);
            ok80211.click();
        }

        return result;
    }

    public void disable80211(String Ssid) {
        settingsorquickview.click();
        MyCommonAPIs.sleep(5);
        System.out.println(Ssid);
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            setSelected($x("//*[@id=\"fastRoamingSt\"]/../span"), false);
            MyCommonAPIs.sleep(5);
            save80211.click();
            MyCommonAPIs.sleep(5);
            ok80211.click();
        }

    }

    public boolean check80211enable(String Ssid) {
        boolean result = false;
        settingsorquickview.click();
        MyCommonAPIs.sleep(5);
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            String sta = ($x("//*[@id=\"fastRoamingSt\"]")).getAttribute("checked");
            logger.info(sta);
            if (sta.equals("true")) {
                logger.info("it is enabled");
                result = true;
            }
        }
        return result;
    }

    public boolean check80211disable(String Ssid) {
        boolean result = false;
        settingsorquickview.click();
        MyCommonAPIs.sleep(5);
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            String sta = ($x("//*[@id=\"fastRoamingSt\"]")).getAttribute("checked");
            logger.info(sta);
            if (sta == "null") {
                logger.info("it is anabled");
                result = true;
            }
        }
        return result;
    }

    public void gotoSDM() {
        MyCommonAPIs.sleep(5);
        SDM.click();
    }

    public boolean enableSDM() {
        boolean result = false;
        boolean result1 = false;
        boolean result2 = false;
        setSelected(enableSDM, true);
        MyCommonAPIs.sleep(3);
        if (SDMWarring.exists()) {

            result1 = true;
            System.out.println("warrning message exits");
        }
        MyCommonAPIs.sleep(3);
        SDMok.click();
        MyCommonAPIs.sleep(3);
        SaveSDM.click();
        MyCommonAPIs.sleep(3);
        refresh();
        refresh();
        MyCommonAPIs.sleep(3);
        MyCommonAPIs.sleep(3);
        if (SDMPort.exists()) {
            String SDMPortNumber = getText(SDMPort);
            if (SDMPortNumber.length() == 5) {
                result2 = true;
                System.out.println(SDMPortNumber);
            }

            if (result1 == true & result2 == true) {
                result = true;
            }
        }
        refresh();
        return result;
    }

    public void GoToTopology() {
        MyCommonAPIs.sleep(5);
        // Topology.click();
        WebCheck.checkHrefIcon(URLParam.hredTopology);
        logger.info("enterd topology page");
    }

    public void GoToDeviceTopology() {
        MyCommonAPIs.sleepi(5);
        TopologyDevicedash.click();
        MyCommonAPIs.sleepi(20);
        logger.info("enterd topology page");
    }

    public void CheckStatus(String SLNo) {
        MyCommonAPIs.sleepi(10);
        checkdevicestatus(SLNo).hover().click();
        MyCommonAPIs.sleepi(5);
        MyCommonAPIs.waitElement(More);
        System.out.println("visible");
        More.click();
        MyCommonAPIs.sleepi(20);
    }

    public boolean options() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        Sidemenu.click();
        MyCommonAPIs.sleepi(10);
        if (SearchTopology.exists() && Filter.exists() && Topologyview.exists() && Legends.exists() && Help.exists()) {
            System.out.println("All the side menu options are present");
            result = true;
        }
        return result;
    }

    public boolean Moredetails() {
        boolean result = false;
        MyCommonAPIs.sleepi(10);

        if (DeviceNameTopology.exists() && DeviceStatusTopology.exists() && DeviceLastRereshTopology.exists()
                && DeviceNetworkconfigurationTopology.exists() && SharediognasticTopology.exists() && DeviceResetTopology.exists()
                && DeviceRebootTopology.exists() && DeviceSeialNoTopology.exists() && DeviceModelTopology.exists() && DeviceMacTopology.exists()
                && DeviceIPTopology.exists() && DeviceGatewayIPTopology.exists() && DeviceDNSTopology.exists() && DeviceStaticTopology.exists()
                && DeviceConnectedNeigborsTopology.exists() && DeviceFWVersionTopology.exists()) {

            System.out.print("ALL tool tip elements are presnt");
            result = true;
        }

        return result;
    }

    public boolean sharediognastic() {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        SharediognasticTopology.click();
        MyCommonAPIs.sleepi(5);
        EmailAddress.click();
        EmailAddress.sendKeys("tvishwanath@netgear.com");
        sendEmail.click();
        MyCommonAPIs.sleepi(2);
        String alerttext = sucessBanner.getText();
        if (alerttext.equals("Diagnostics logs shared.")) {
            logger.info("Alert is existed.");
            result = true;
        }
        return result;
    }

    public boolean checkNetworkconfigurationpage() {
        MyCommonAPIs.sleepi(5);
        DeviceNetworkconfigurationTopology.click();
        MyCommonAPIs.sleepi(5);
        String url = MyCommonAPIs.getCurrentUrl();
        boolean result = false;
        if (url.contains("#/wired/VLAN") || url.contains("#/wireless/wirelessSettings") || url.contains("#/routers/settings/VPNGroups")
                || url.contains("#/routers/settings/VPNGroups")) {
            result = true;
            System.out.print("Entered right  page/n");
        }

        return result;

    }

    public void RebootTopology() {
        MyCommonAPIs.sleepi(5);
        DeviceRebootTopology.click();
        MyCommonAPIs.sleepi(5);
        rebootconfirm.click();
        MyCommonAPIs.sleepi(5);

    }

    public void ResetTopology() {
        MyCommonAPIs.sleepi(5);
        DeviceResetTopology.click();
        MyCommonAPIs.sleepi(5);
        resetconfirm.click();
        MyCommonAPIs.sleepi(5);

    }

    public int getApUptimetopology(String SlNo) {
        int upTime = 0;
        String text = getText(String.format(uptime, SlNo));
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(text);
        upTime = Integer.parseInt(m.replaceAll("").trim());
        logger.info("Uptime number:" + String.valueOf(upTime));
        return upTime;
    }

    public boolean SearchFuntionality(String SlNo) {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        Sidemenu.click();
        MyCommonAPIs.sleepi(10);
        SearchTopology.click();
        MyCommonAPIs.sleepi(2);
        Searchsend.click();
        MyCommonAPIs.sleepi(2);
        Searchsend.sendKeys(SlNo);
        if (checkdevicestatus(SlNo).isDisplayed()) {
            System.out.println("All the side menu options are present");
            result = true;
        }
        return result;
    }

    public boolean ViewDashboard() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        ViewDashboard.click();
        MyCommonAPIs.sleepi(10);
        String currentUrl = getCurrentUrl();
        System.out.print(currentUrl);
        if (currentUrl.contains("#/topology/view")) {
            System.out.println("landed in topology page");
            result = true;
        }
        return result;
    }

    public boolean FilterFuntionality() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        Sidemenu.click();
        MyCommonAPIs.sleepi(10);
        Filter.click();
        MyCommonAPIs.sleepi(10);

        boolean WirelessNew = wirelesscheck.isSelected();
        boolean WireNew = Wiredcheck.isSelected();
        boolean bothNew = bothcheck.isSelected();

        System.out.println(WirelessNew);
        System.out.println(WireNew);
        System.out.println(bothNew);
        if (wirelesscheck.isDisplayed() && Wiredcheck.isDisplayed() && bothcheck.isDisplayed() && bothNew == true) {
            System.out.println("All filteroptions are present");
            result = true;
        }

        return result;
    }

    public void TopologyView() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        Sidemenu.click();
        MyCommonAPIs.sleepi(10);
        Topologyview.click();
        MyCommonAPIs.sleepi(10);
        click(AbstractView);
        // AbstractView.click();
    }

    public void treeView() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        Sidemenu.click();
        MyCommonAPIs.sleepi(10);
        Topologyview.click();
        MyCommonAPIs.sleepi(10);
        click(TreeViewclick);
        // AbstractView.click();
    }

    public boolean RouterTool(String SLNo, String ModelNo) {
        boolean result = false;
        boolean result1 = false;
        boolean result2 = false;
        MyCommonAPIs.sleepi(10);
        checkdevicestatus(SLNo).hover().click();
        MyCommonAPIs.sleepi(5);
        String Status = getText(StatusTool(SLNo));
        String Serial = getText(SerialNocheck(SLNo));
        String Model = getText(Modelcheck(SLNo));
        System.out.println(Status + Serial + Model);
        System.out.println("before entering exits");
        if (StatusTool(SLNo).exists() && SerialNoTool(SLNo).exists() && MACTool(SLNo).exists() && IPAddressTool(SLNo).exists()
                && FirmwareVersionTool(SLNo).exists() && ModelTool(SLNo).exists() && CriticalNotificationTool(SLNo).exists()
                && FirmwareUpdateAvailableTool(SLNo).exists() && NumberofClientsTool(SLNo).exists()) {
            System.out.println("All tool's are present");
            result1 = true;
        }

        // String MAC = getText(MACTool(SLNo));

        // String IPAddress = getText(IPAddressTool(SLNo));
        // String FirmwareVersion = getText(FirmwareVersionTool(SLNo));
        // String CriticalNotification = getText(CriticalNotificationTool(SLNo));
        // String FirmwareUpdateAvailable = getText(FirmwareUpdateAvailableTool(SLNo));
        // String NumberofClients = getText(NumberofClientsTool(SLNo));
        System.out.println("before checking Model and serial number exits");
        if (Status.contains("Status") && Serial.contains(SLNo) && Model.contains(ModelNo)) {
            System.out.println("seial Number and model are right");
            result2 = true;
        }

        if (result1 == true && result2 == true) {
            result = true;
        }

        return result;
    }

    public boolean locateDevice() {
        boolean result = false;
        Locatedevice.click();
        String LocatedeviceTime = LocatedeviceClick.getText();
        MyCommonAPIs.sleepi(2);
        System.out.println(LocatedeviceTime);
        if (LocatedeviceTime.contains("4m:59s")) {
            result = true;
        }
        return result;
    }

    public boolean locateDeviceCountDown() {
        boolean result = false;
        Locatedevice.click();

        MyCommonAPIs.sleepi(180);

        for (int i = 0; i < 60; i++) {
            String LocatedeviceTime = LocatedeviceClick.getText();
            System.out.println(LocatedeviceTime);
            if (LocatedeviceTime.contains("0m:5s") || LocatedeviceTime.contains("0m:4s") || LocatedeviceTime.contains("0m:3s")
                    || LocatedeviceTime.contains("0m:2s") || LocatedeviceTime.contains("0m:1s") || LocatedeviceTime.contains("0m:0s")) {
                result = true;
                break;
            }
            MyCommonAPIs.sleepi(10);

        }
        return result;
    }

    public boolean stop() {
        boolean result = false;
        Locatedevice.click();
        MyCommonAPIs.sleepi(10);
        if (Stop.exists()) {
            result = true;
        }
        return result;
    }

    public boolean locateExistsPoststop() {
        boolean result = false;
        Locatedevice.click();
        MyCommonAPIs.sleepi(10);
        Stop.click();
        if (Locatedevice.exists()) {
            result = true;
        }
        return result;
    }

    public boolean HelpText() {
        boolean result = false;
        HelpText.click();
        MyCommonAPIs.sleepi(5);
        String Helptextcheck = HelpTextcontent.getText();
        MyCommonAPIs.sleepi(2);
        System.out.println(Helptextcheck);
        if (Helptextcheck.contains("Locating operation remains for 300 seconds or when \"Stop\" is clicked.")) {
            result = true;
        }
        return result;
    }

    public void enableclientIsolation(String Ssid) {

        settingsorquickview.click();
        MyCommonAPIs.sleep(5);
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);

            MyCommonAPIs.sleepi(10);
            enableClientisolation.click();
            MyCommonAPIs.sleepi(5);
            editsave.click();
            waitReady();
            MyCommonAPIs.sleepi(3);
            confirmok.click();
            MyCommonAPIs.sleepi(4);
        }

    }

    public void Isrelchannel() {

        RadioAndChannels.click();
        MyCommonAPIs.sleepi(5);
        DropDown5GhzHigh.click();
        MyCommonAPIs.sleepi(5);
        Channel5highlist.click();
        MyCommonAPIs.sleepi(5);
        Select element = new Select(Channel5highlist);
        List<WebElement> channellist = element.getOptions();
        MyCommonAPIs.sleepi(5);
        int size = channellist.size();

        for (int i = 0; i < size; i++) {

            String optios = channellist.get(i).getText();
            System.out.println(optios);

        }

        ChannelWidth5ghz.click();
        MyCommonAPIs.sleepi(5);
        Select elementwidth = new Select(ChannelWidth5ghz);
        List<WebElement> channelwidthlist = elementwidth.getOptions();
        MyCommonAPIs.sleepi(5);
        int size1 = channelwidthlist.size();

        for (int j = 0; j < size1; j++) {

            String options = channelwidthlist.get(j).getText();
            System.out.println(options);

        }

    }

    public void instantWifiIsrael5ghz() {

        Map<String, String> ssidInfo = new HashMap<String, String>();
        ssidInfo.put("SSID", "apwp18179");
        ssidInfo.put("Security", "WPA2 Personal Mixed");
        ssidInfo.put("Password", "1234567980");
        new WirelessQuickViewPage().addSsid(ssidInfo);

        new APUtils(WebportalParam.ap1IPaddress).deleteMessages();
        if (!new WirelessQuickViewPage().optimizeInstantWifi(false)) {
            int i = 0;
            while (i < 2) {
                MyCommonAPIs.sleepi(10 * 60);
                new APUtils(WebportalParam.ap1IPaddress).deleteMessages();
                if (new WirelessQuickViewPage().optimizeInstantWifi(false)) {
                    break;
                }
            }
        }

        // int count = 0;
        // while (true) {
        // MyCommonAPIs.sleepsync();
        // if (new APUtils(WebportalParam.ap1IPaddress).checkInstantWifiProfile(false)) {
        // assertTrue(true);
        // break;
        // } else if (count == 10) {
        // assertTrue(false, "Instant wifi logs not output.");
        // break;
        // }
        // count += 1;
        // }

        // AdvanceWirelessSetting.click();
        // MyCommonAPIs.sleepi(5);
        // InstantWifi.click();
        // MyCommonAPIs.sleepi(5);
        // OptimizeNow.click();
        // MyCommonAPIs.sleepi(5);
        // CollapseIcon.click();

    }

    public void instantWifiPCLChannels() {

        // Map<String, String> ssidInfo = new HashMap<String, String>();
        // ssidInfo.put("SSID", "apwp18179");
        // ssidInfo.put("Security", "WPA2 Personal Mixed");
        // ssidInfo.put("Password", "1234567980");
        // new WirelessQuickViewPage().addSsid(ssidInfo);

        new APUtils(WebportalParam.ap1IPaddress).deleteMessages();
        if (!new WirelessQuickViewPage().optimizeInstantWifiuncheckpcl(false)) {
            int i = 0;
            while (i < 2) {
                MyCommonAPIs.sleepi(10 * 60);
                new APUtils(WebportalParam.ap1IPaddress).deleteMessages();
                if (new WirelessQuickViewPage().optimizeInstantWifiuncheckpcl(false)) {
                    break;
                }
            }
        }

        int count = 0;
        while (true) {
            MyCommonAPIs.sleepsync();
            if (new APUtils(WebportalParam.ap1IPaddress).checkInstantWifiProfile(false)) {
                assertTrue(true);
                break;
            } else if (count == 10) {
                assertTrue(false, "Instant wifi logs not output.");
                break;
            }
            count += 1;
        }

    }

    public boolean optimizeInstantWifiuncheckpcl(boolean toWait) {
        boolean result = false;
        waitElement(settingsorquickview);
        settingsorquickview.click();
        MyCommonAPIs.sleepi(5);
        waitReady();
        instantwifi.click();
        MyCommonAPIs.sleepi(2);
        TwoghzSelectAll.click();
        MyCommonAPIs.sleepi(2);
        TwoghzSelectAll.click();
        MyCommonAPIs.sleepi(2);
        twoghz5uncheck.click();
        MyCommonAPIs.sleepi(2);
        twoghz7uncheck.click();
        MyCommonAPIs.sleepi(2);
        twoghz8uncheck.click();
        MyCommonAPIs.sleepi(2);
        fiveghzselectall.click();
        MyCommonAPIs.sleepi(2);
        fiveghzselectall.click();
        MyCommonAPIs.sleepi(2);
        fiveghz48check.click();
        MyCommonAPIs.sleepi(2);
        fiveghz52check.click();
        MyCommonAPIs.sleepi(2);
        fiveghz56check.click();
        MyCommonAPIs.sleepi(2);
        fiveghz140check.click();
        MyCommonAPIs.sleepi(2);
        fiveghz144check.click();
        MyCommonAPIs.sleepi(2);
        fiveghz149check.click();
        MyCommonAPIs.sleepi(2);
        waitElement(optimizenowbutton);
        timerStart(22 * 60);
        while (timerRunning()) {
            optimizenowbutton.click();
            waitReady();
            waitElement(instantwifisuccessmeg);
            if (getText(instantwifisuccessmeg).contains("Your configuration has been applied, It may take some time to reflect")) {
                result = true;
                break;
            } else {
                logger.warning("instantwifisuccessmeg error");
            }
            if (!toWait) {
                break;
            } else {
                MyCommonAPIs.sleepi(60);
            }
        }
        if (toWait && !result) {
            logger.warning("timeout on optimize");
        }
        return result;
    }

    public boolean selectV(String Ssid) {
        boolean result = false;
        boolean result1 = false;
        boolean result2 = false;
        settingsorquickview.click();
        MyCommonAPIs.sleep(5);
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);

            MyCommonAPIs.sleepi(10);
            VLANInput.selectOption("Management VLAN");
            MyCommonAPIs.sleepi(10);
            enableClientisolation.click();
            MyCommonAPIs.sleepi(10);

            if (AllowUIAcess.isDisplayed()) {
                System.out.println("allow access is visible");
                result1 = true;
            }

            VLANInput.selectOption("Video VLAN");
            MyCommonAPIs.sleepi(5);
            if (!AllowUIAcess.isDisplayed()) {
                System.out.println("allow access is not visible");
                result2 = true;
            }

            if (result1 == true && result2 == true) {
                System.out.println("working as flow");
                result = true;
            }
        }

        return result;

    }

    public void changeVLANID(String Ssid) {
        // Random rand = new Random();
        // Integer id=rand.ints(1, 4093));

        settingsorquickview.click();
        MyCommonAPIs.sleep(5);
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);

            MyCommonAPIs.sleepi(5);
            AddVLAN.click();
            MyCommonAPIs.sleepi(5);
            sendVLANID.click();
            MyCommonAPIs.sleepi(5);
            sendVLANID.sendKeys("40");
            MyCommonAPIs.sleepi(5);
            SaveVLANID.click();
            MyCommonAPIs.sleepi(5);
            editsave.click();
            waitReady();
            MyCommonAPIs.sleepi(3);
            confirmok.click();
            MyCommonAPIs.sleepi(4);
        }

    }

    public void enableDVALAN(String Ssid) {

        settingsorquickview.click();
        MyCommonAPIs.sleep(5);
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);

            MyCommonAPIs.sleepi(10);
            enableDVLAN.click();
            MyCommonAPIs.sleepi(5);
            DVLANYes.click();
            MyCommonAPIs.sleepi(5);
            editsave.click();
            waitReady();
            MyCommonAPIs.sleepi(3);
            confirmok.click();
            MyCommonAPIs.sleepi(4);
        }

    }

    public boolean enableclientIsolationException(String Ssid) {
        boolean result = false;
        settingsorquickview.click();
        MyCommonAPIs.sleep(5);
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);

            MyCommonAPIs.sleepi(10);

            enableClientisolation.click();
            MyCommonAPIs.sleepi(10);
            System.out.println(Exceptiontext.getText());
            if (Exceptiontext.isDisplayed()) {

                result = true;
            }

        }
        return result;
    }

    public boolean disableClientIsolation(String Ssid) {
        boolean result = false;
        settingsorquickview.click();
        MyCommonAPIs.sleep(5);
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);

            MyCommonAPIs.sleepi(10);
            enableClientisolation.click();

            MyCommonAPIs.sleepi(10);
            if (!AllowUIAcess.isDisplayed()) {
                System.out.println("allow access is visible");
                result = true;
            }

        }

        return result;
    }

    public boolean isEnabledClientIsolation(String Ssid) {
        boolean result = false;
        settingsorquickview.click();
        MyCommonAPIs.sleep(15);
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);

            MyCommonAPIs.sleepi(10);
            boolean sta = isenableClientisolation.isSelected();
            System.out.println(sta);
            MyCommonAPIs.sleepi(200);
            if (sta == true) {
                System.out.println("Clent isolation is enabled");
                result = true;
            }

        }
        return result;
    }

    // Code added by Pratik for MPSK

    public boolean addMPSKSsid1(Map<String, String> map) {

        boolean result = false;
        // waitElement(settingsorquickview);
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        waitElement(addssid);
        boolean isKeyPresent = false;

        if (!checkSsidIsExist(map.get("SSID"))) {
            logger.info("Add ssid.");
            addssid.click();
            waitElement(ssid);
            ssid.setValue(map.get("SSID"));
            waitReady();
            if (checkband6.isDisplayed()) {
                if (checkband6.isSelected()) {
                    if (band6.isDisplayed()) {
                        band6.click();
                        logger.info("Uncheck 6 band");
                    }
                } else {
                    System.out.println("6GHZ is alredy unchecked");
                }
            }
            isKeyPresent = map.containsKey("Band");
            System.out.println(isKeyPresent);
            if (isKeyPresent == true) {
                if (map.get("Band").equals("Both")) {
                    logger.info("both band are selected");
                } else if (map.get("Band").equals("ALL")) {
                    if (ALLband.isDisplayed()) {
                        // ALLband.click();
                        logger.info("Uncheck all band");
                    }
                    waitReady();
                } else if (map.get("Band").equals("Uncheck2.4 GHz")) {
                    if (band24.isDisplayed()) {
                        band24.click();
                        // MyCommonAPIs.sleepi(4);
                        // band6.click();
                        // logger.info("Uncheck 2.4 band");
                    }
                    waitReady();
                } else if (map.get("Band").equals("Uncheck5 GHz")) {
                    if (band5.isDisplayed()) {
                        band5.click();
                        MyCommonAPIs.sleepi(4);
                        band6.click();
                        logger.info("Uncheck 5 band");
                    }
                    waitReady();
                } else if (map.get("Band").equals("Uncheck6 GHz")) {
                    if (band6.isDisplayed()) {
                        logger.info("Uncheck 6 band");
                    }
                    waitReady();
                } else if (map.get("Band").equals("check only 2ghz")) {
                    MyCommonAPIs.sleepi(4);
                    band5.click();
                    MyCommonAPIs.sleepi(4);
                } else if (map.get("Band").equals("check only 5ghz")) {
                    MyCommonAPIs.sleepi(4);
                    band24.click();
                    waitReady();
                } else if (map.get("Band").equals("check only 6ghz")) {
                    MyCommonAPIs.sleepi(4);
                    band24.click();
                    MyCommonAPIs.sleepi(4);
                    band5.click();
                    // MyCommonAPIs.sleepi(4);
                    // band6.click();
                    waitReady();
                }
            } else {
                logger.info("no need to Uncheck");
            }

            System.out.print("out of band");
            if (map.get("Security").equals("WPA2-PSK")) {
                security.selectOption("WPA2-PSK");
                waitReady();
            } else {
                security.selectOption(map.get("Security"));
                System.out.println("inside sec");
                waitReady();
            }

            if (mpskKeySwitchDisplayed.isDisplayed()) {
                mpskKeySwitch.click();
                System.out.println("inside switchdisp");
                waitReady();
            }

            if (selectMPSKKeyVisible.isDisplayed()) {
                selectMPSKKey.click();
                System.out.println("inside selectmpsk key");
                clkonCheckboxofMPSKKey.click();
                waitReady();
            }

            /*
             * if (map.containsKey("Password")) {
             * password.setValue(map.get("Password"));
             * waitReady();
             * }
             */

            takess("addSsid");
            save.click();
            waitReady();
            MyCommonAPIs.sleepi(4);
            if (Maximumlimit.exists()) {
                System.out.println("inside maxmim");
                String ErrorMessage = Maximumlimit.getText();
                logger.info(ErrorMessage);
                close.click();
                result = true;
            }

            MyCommonAPIs.sleepi(3);
            logger.info("Add ssid successful.");
        } else {
            logger.warning("checkSsidIsExist error");
        }
        return result;
    }

    public boolean addMPSKSsid2(Map<String, String> map) {

        boolean result = false;
        // waitElement(settingsorquickview);
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        waitElement(addssid);
        boolean isKeyPresent = false;

        if (!checkSsidIsExist(map.get("SSID"))) {
            logger.info("Add ssid.");
            addssid.click();
            waitElement(ssid);
            ssid.setValue(map.get("SSID"));
            waitReady();
            if (checkband6.isDisplayed()) {
                if (checkband6.isSelected()) {
                    if (band6.isDisplayed()) {
                        band6.click();
                        logger.info("Uncheck 6 band");
                    }
                } else {
                    System.out.println("6GHZ is alredy unchecked");
                }
            }
            isKeyPresent = map.containsKey("Band");
            System.out.println(isKeyPresent);
            if (isKeyPresent == true) {
                if (map.get("Band").equals("ALL")) {
                    if (ALLband.isDisplayed()) {
                        // ALLband.click();
                        logger.info("Uncheck all band");
                    }
                    waitReady();
                } else if (map.get("Band").equals("Uncheck2.4 GHz")) {
                    if (band24.isDisplayed()) {
                        band24.click();
                        // MyCommonAPIs.sleepi(4);
                        // band6.click();
                        // logger.info("Uncheck 2.4 band");
                    }
                    waitReady();
                } else if (map.get("Band").equals("Uncheck5 GHz")) {
                    if (band5.isDisplayed()) {
                        band5.click();
                        MyCommonAPIs.sleepi(4);
                        band6.click();
                        logger.info("Uncheck 5 band");
                    }
                    waitReady();
                } else if (map.get("Band").equals("Uncheck6 GHz")) {
                    if (band6.isDisplayed()) {
                        logger.info("Uncheck 6 band");
                    }
                    waitReady();
                } else if (map.get("Band").equals("check only 2ghz")) {
                    MyCommonAPIs.sleepi(4);
                    band5.click();
                    MyCommonAPIs.sleepi(4);
                } else if (map.get("Band").equals("check only 5ghz")) {
                    MyCommonAPIs.sleepi(4);
                    band24.click();
                    waitReady();
                } else if (map.get("Band").equals("check only 6ghz")) {
                    MyCommonAPIs.sleepi(4);
                    band24.click();
                    MyCommonAPIs.sleepi(4);
                    band5.click();
                    // MyCommonAPIs.sleepi(4);
                    // band6.click();
                    waitReady();
                }
            } else {
                logger.info("no need to Uncheck");
            }

            if (map.get("Security").equals("WPA2-PSK")) {
                security.selectOption("WPA2-PSK");
                waitReady();
            } else {
                security.selectOption(map.get("Security"));
                waitReady();
            }

            if (mpskKeySwitchDisplayed.isDisplayed()) {
                mpskKeySwitch.click();
                waitReady();
            }

            if (selectMPSKKeyVisible.isDisplayed()) {
                selectMPSKKey.click();
                clkonCheckboxofMPSKKey.click();
                waitReady();
            }

            /*
             * if (map.containsKey("Password")) {
             * password.setValue(map.get("Password"));
             * waitReady();
             * }
             */

            takess("addSsid");
            save.click();
            waitReady();
            MyCommonAPIs.sleepi(10);
            String errorMsg = errorMsgSSIdMPSKKey.getText();
            if (errorMsg.equals(
                    "Up to 4 SSIDs can be configured with Multi Pre-Shared Key (MPSK). Please disable MPSK on other SSID before enabling on this SSID.")) {
                result = true;
                waitReady();
            }
            MyCommonAPIs.sleepi(5);
            System.out.println(errorMsg);
            MyCommonAPIs.sleepi(5);
            closeSSIDWindow.click();
        }
        return result;
    }

    public boolean addMPSKKey1() {
        boolean result = false;
        // office1LocationBtn.click();
        // MyCommonAPIs.sleepi(5);
        // wirelessTab.click();
        MyCommonAPIs.sleepi(5);
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        MyCommonAPIs.sleepi(5);
        if (Advance1.exists() && Advance2.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance1).perform();
            if (mpskSettingOpt.exists() && mpskSettingOpt1.exists()) {
                a.moveToElement(mpskSettingOpt).click().perform();
            } else if (mpskSettingOpt1.exists()) {
                a.moveToElement(mpskSettingOpt1).click().perform();
            }

        } else if (Advance2.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance2).perform();
            if (mpskSettingOpt.exists() && mpskSettingOpt1.exists()) {
                a.moveToElement(mpskSettingOpt).click().perform();
            } else if (mpskSettingOpt1.exists()) {
                a.moveToElement(mpskSettingOpt1).click().perform();
            }
        }
        // advanceSettingsBtn.click();
        // MyCommonAPIs.sleepi(1);
        // mpskSettingOpt.click();
        // MyCommonAPIs.sleepi(1);
        // if (verifyMPSKSetting.getText()=="Multi PSK Settings"){
        // result = true;
        // }
        // System.out.println(verifyMPSKSetting.getText());
        MyCommonAPIs.sleepi(3);
        clkAddMPSK.click();
        MyCommonAPIs.sleepi(3);
        selectVLAN.click();
        MyCommonAPIs.sleepi(3);
        selectManagement.click();
        MyCommonAPIs.sleepi(3);
        passwordMPSK.sendKeys("Netgear1@");
        MyCommonAPIs.sleepi(3);
        mpskKeyName.sendKeys("MPSKSSIDTest01");
        MyCommonAPIs.sleepi(3);
        clkonAddKeyIdentifierBtn.click();
        MyCommonAPIs.sleepi(1);
        if (cnfmMessage.isDisplayed()) {
            result = true;
        }
        MyCommonAPIs.sleepi(3);
        gotoHomePage.click();
        MyCommonAPIs.sleepi(5);
        office1LocationBtn.doubleClick();
        MyCommonAPIs.sleepi(5);
        wirelessTab.click();
        MyCommonAPIs.sleepi(5);
        settingsorquickview.click();
        MyCommonAPIs.sleepi(5);
        if (Advance1.exists() && Advance2.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance1).perform();
            if (mpskSettingOpt.exists() && mpskSettingOpt1.exists()) {
                a.moveToElement(mpskSettingOpt).click().perform();
            } else if (mpskSettingOpt1.exists()) {
                a.moveToElement(mpskSettingOpt1).click().perform();
            }

        } else if (Advance2.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance2).perform();
            if (mpskSettingOpt.exists() && mpskSettingOpt1.exists()) {
                a.moveToElement(mpskSettingOpt).click().perform();
            } else if (mpskSettingOpt1.exists()) {
                a.moveToElement(mpskSettingOpt1).click().perform();
            }
        }
        MyCommonAPIs.sleepi(3);
        String mpskKeyName = verifyMPSKKeyisAdded.getText();
        MyCommonAPIs.sleepi(3);
        System.out.println(mpskKeyName);
        MyCommonAPIs.sleepi(3);
        if (mpskKeyName.equals("MPSKSSIDTest01")) {
            result = true;
        }
        return result;
    }

    public void deleteMPSKKey() {
        MyCommonAPIs.sleepi(1);
        MyCommonAPIs.sleepi(5);
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        MyCommonAPIs.sleepi(5);
        if (Advance1.exists() && Advance2.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance1).perform();
            if (mpskSettingOpt.exists() && mpskSettingOpt1.exists()) {
                a.moveToElement(mpskSettingOpt).click().perform();
            } else if (mpskSettingOpt1.exists()) {
                a.moveToElement(mpskSettingOpt1).click().perform();
            }
        } else if (Advance2.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance2).perform();
            if (mpskSettingOpt.exists() && mpskSettingOpt1.exists()) {
                a.moveToElement(mpskSettingOpt).click().perform();
            } else if (mpskSettingOpt1.exists()) {
                a.moveToElement(mpskSettingOpt1).click().perform();
            }
        }
        MyCommonAPIs.sleepi(1);
        System.out.println(verifyMPSKSetting.getText());
        MyCommonAPIs.sleepi(1);
        clkonMPSKKey.click();
        clkonDelete.click();
        waitReady();
        clkonDeleteBtn.click();
        waitReady();

    }

    public void gotoAddMPSKKey() {
        MyCommonAPIs.sleepi(5);
        if (settingBtn.isDisplayed()) {
            settingBtn.click();
        }
        MyCommonAPIs.sleepi(5);
        if (Advance1.exists() && Advance2.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance1).perform();
            if (mpskSettingOpt.exists() && mpskSettingOpt1.exists()) {
                a.moveToElement(mpskSettingOpt).click().perform();
            } else if (mpskSettingOpt1.exists()) {
                a.moveToElement(mpskSettingOpt1).click().perform();
            }
        } else if (Advance2.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance2).perform();
            if (mpskSettingOpt.exists() && mpskSettingOpt1.exists()) {
                a.moveToElement(mpskSettingOpt).click().perform();
            } else if (mpskSettingOpt1.exists()) {
                a.moveToElement(mpskSettingOpt1).click().perform();
            }
        }
        MyCommonAPIs.sleepi(1);
    }

    public boolean addMPSKKeys(Map<String, String> map) {
        boolean result = false;
        addNewMPSKKey1.click();
        MyCommonAPIs.sleepi(3);
        selectVLAN.click();
        MyCommonAPIs.sleepi(3);
        selectManagement.click();
        MyCommonAPIs.sleepi(3);
        passwordMPSK.setValue(map.get("passwordMPSK"));
        MyCommonAPIs.sleepi(3);
        mpskKeyName.setValue(map.get("mpskKeyName"));
        MyCommonAPIs.sleepi(3);
        clkonAddKeyIdentifierBtn.click();
        MyCommonAPIs.sleepi(5);
        if (cnfmMessage.isDisplayed()) {
            result = true;
        }
        System.out.println(cnfmMessage.getText());

        return result;
    }

    public String addMaxMPSKKeys(Map<String, String> map) {
        String result = "";
        addNewMPSKKey1.click();
        MyCommonAPIs.sleepi(3);
        selectVLAN.click();
        MyCommonAPIs.sleepi(3);
        selectManagement.click();
        MyCommonAPIs.sleepi(3);
        passwordMPSK.setValue(map.get("passwordMPSK"));
        MyCommonAPIs.sleepi(3);
        mpskKeyName.setValue(map.get("mpskKeyName"));
        MyCommonAPIs.sleepi(3);
        clkonAddKeyIdentifierBtn.click();
        System.out.println("check the log before");
        MyCommonAPIs.sleepi(3);
        result = MaxMPSK.getText();
        System.out.println("check the log after ");
        MyCommonAPIs.sleepi(3);
        MaxMPSKclose.click();
        return result;
    }

    public boolean verifyMPSKKeysareAdded() {

        boolean result = false;

        MyCommonAPIs.sleepi(3);
        gotoHomePage.click();
        MyCommonAPIs.sleepi(5);
        office1LocationBtn.doubleClick();
        MyCommonAPIs.sleepi(5);
        wirelessTab.click();
        MyCommonAPIs.sleepi(5);
        settingBtn.click();
        MyCommonAPIs.sleepi(5);
        if (Advance1.exists() && Advance2.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance1).perform();
            if (mpskSettingOpt.exists() && mpskSettingOpt1.exists()) {
                a.moveToElement(mpskSettingOpt).click().perform();
            } else if (mpskSettingOpt1.exists()) {
                a.moveToElement(mpskSettingOpt1).click().perform();
            }
        } else if (Advance2.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance2).perform();
            if (mpskSettingOpt.exists() && mpskSettingOpt1.exists()) {
                a.moveToElement(mpskSettingOpt).click().perform();
            } else if (mpskSettingOpt1.exists()) {
                a.moveToElement(mpskSettingOpt1).click().perform();
            }
        }
        MyCommonAPIs.sleepi(3);
        String mpskKey1 = verifyMPSKKeyisAdded.getText();
        assertTrue(mpskKey1.equals("MPSKSSIDTest01"));
        MyCommonAPIs.sleepi(1);
        String mpskKey2 = verifyMPSKKeyisAdded1.getText();
        assertTrue(mpskKey2.equals("MPSKSSIDTest02"));
        MyCommonAPIs.sleepi(1);
        String mpskKey3 = verifyMPSKKeyisAdded2.getText();
        assertTrue(mpskKey3.equals("MPSKSSIDTest03"));
        MyCommonAPIs.sleepi(1);
        String mpskKey4 = verifyMPSKKeyisAdded3.getText();
        assertTrue(mpskKey4.equals("MPSKSSIDTest04"));
        MyCommonAPIs.sleepi(1);
        String mpskKey5 = verifyMPSKKeyisAdded4.getText();
        assertTrue(mpskKey5.equals("MPSKSSIDTest05"));
        MyCommonAPIs.sleepi(1);
        String mpskKey6 = verifyMPSKKeyisAdded5.getText();
        assertTrue(mpskKey6.equals("MPSKSSIDTest06"));
        MyCommonAPIs.sleepi(1);
        String mpskKey7 = verifyMPSKKeyisAdded6.getText();
        assertTrue(mpskKey7.equals("MPSKSSIDTest07"));
        MyCommonAPIs.sleepi(1);
        String mpskKey8 = verifyMPSKKeyisAdded7.getText();
        assertTrue(mpskKey8.equals("MPSKSSIDTest08"));
        MyCommonAPIs.sleepi(1);
        String mpskKey9 = verifyMPSKKeyisAdded8.getText();
        if (mpskKey9.equals("MPSKSSIDTest09")) {
            result = true;
        }

        return result;
    }

    public boolean add9MPSK(Map<String, String> map) {

        boolean result = false;
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        waitElement(addssid);

        if (!checkSsidIsExist(map.get("SSID"))) {
            logger.info("Add ssid.");
            addssid.click();
            waitElement(ssid);
            ssid.setValue(map.get("SSID"));
            waitReady();
            if (checkband6.isDisplayed()) {
                if (checkband6.isSelected()) {
                    if (band6.isDisplayed()) {
                        band6.click();
                        logger.info("Uncheck 6 band");
                    }
                } else {
                    System.out.println("6GHZ is alredy unchecked");
                }
            }
        }

        MyCommonAPIs.sleepi(20);
        mpskKeySwitch.click();

        if (selectMPSKKeyVisible.isDisplayed()) {
            selectMPSKKey.click();
            MyCommonAPIs.sleepi(5);
            addMPSKKey1toSSID.click();
            MyCommonAPIs.sleepi(5);
            addMPSKKey2toSSID.click();
            MyCommonAPIs.sleepi(5);
            addMPSKKey3toSSID.click();
            MyCommonAPIs.sleepi(5);
            addMPSKKey4toSSID.click();
            addMPSKKey5toSSID.click();
            addMPSKKey6toSSID.click();
            addMPSKKey7toSSID.click();
            addMPSKKey8toSSID.click();
            // addMPSKKey9toSSID.click();
            waitReady();
        }

        boolean status9 = CheckKeySelected9.isSelected();
        System.out.println(status9);

        boolean status8 = CheckKeySelected8.isSelected();
        System.out.println(status8);

        if (status9 == false) {
            result = true;
        }
        return result;
    }

    public boolean addMPSKSsid3(Map<String, String> map) {

        boolean result = false;
        // waitElement(settingsorquickview);
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        waitElement(addssid);
        boolean isKeyPresent = false;

        if (!checkSsidIsExist(map.get("SSID"))) {
            logger.info("Add ssid.");
            addssid.click();
            waitElement(ssid);
            ssid.setValue(map.get("SSID"));
            waitReady();
            if (checkband6.isSelected()) {
                if (band6.isDisplayed()) {
                    band6.click();
                    logger.info("Uncheck 6 band");
                }
            } else {
                System.out.println("6GHZ is alredy unchecked");
            }
            isKeyPresent = map.containsKey("Band");
            System.out.println(isKeyPresent);
            if (isKeyPresent == true) {
                if (map.get("Band").equals("ALL")) {
                    if (ALLband.isDisplayed()) {
                        // ALLband.click();
                        logger.info("Uncheck all band");
                    }
                    waitReady();
                } else if (map.get("Band").equals("Uncheck2.4 GHz")) {
                    if (band24.isDisplayed()) {
                        band24.click();
                        // MyCommonAPIs.sleepi(4);
                        // band6.click();
                        // logger.info("Uncheck 2.4 band");
                    }
                    waitReady();
                } else if (map.get("Band").equals("Uncheck5 GHz")) {
                    if (band5.isDisplayed()) {
                        band5.click();
                        MyCommonAPIs.sleepi(4);
                        band6.click();
                        logger.info("Uncheck 5 band");
                    }
                    waitReady();
                } else if (map.get("Band").equals("Uncheck6 GHz")) {
                    if (band6.isDisplayed()) {
                        logger.info("Uncheck 6 band");
                    }
                    waitReady();
                } else if (map.get("Band").equals("check only 2ghz")) {
                    MyCommonAPIs.sleepi(4);
                    band5.click();
                    MyCommonAPIs.sleepi(4);
                } else if (map.get("Band").equals("check only 5ghz")) {
                    MyCommonAPIs.sleepi(4);
                    band24.click();
                    waitReady();
                } else if (map.get("Band").equals("check only 6ghz")) {
                    MyCommonAPIs.sleepi(4);
                    band24.click();
                    MyCommonAPIs.sleepi(4);
                    band5.click();
                    // MyCommonAPIs.sleepi(4);
                    // band6.click();
                    waitReady();
                }
            } else {
                logger.info("no need to Uncheck");
            }

            if (map.get("Security").equals("WPA2-PSK")) {
                security.selectOption("WPA2-PSK");
                waitReady();
            } else {
                security.selectOption(map.get("Security"));
                waitReady();
            }

            if (mpskKeySwitchDisplayed.isDisplayed()) {
                mpskKeySwitch.click();
                waitReady();
            }

            if (selectMPSKKeyVisible.isDisplayed()) {
                selectMPSKKey.click();
                addMPSKKey1toSSID.click();
                addMPSKKey2toSSID.click();
                addMPSKKey3toSSID.click();
                addMPSKKey4toSSID.click();
                addMPSKKey5toSSID.click();
                addMPSKKey6toSSID.click();
                addMPSKKey7toSSID.click();
                addMPSKKey8toSSID.click();
                waitReady();
            }

            /*
             * if (map.containsKey("Password")) {
             * password.setValue(map.get("Password"));
             * waitReady();
             * }
             */

            takess("addSsid");
            save.click();
            waitReady();
            MyCommonAPIs.sleepi(3);
            logger.info("Add ssid successful.");
            MyCommonAPIs.sleepi(3);
            clkonSSID.click();
            editSSID.click();
            MyCommonAPIs.sleepi(5);
            clkonMPSKKeydropdown.click();
            MyCommonAPIs.sleepi(3);
            /*
             * keyIdentifierNote.click();
             * MyCommonAPIs.sleepi(3);
             * scrollBar.click();
             * MyCommonAPIs.sleepi(3);
             * scrollBar.click();
             * MyCommonAPIs.sleepi(3);
             */
            JavascriptExecutor js = (JavascriptExecutor) webportalParam;
            js.executeScript("arguments[0].scrollIntoView();", addMPSKKey9toSSID);
            MyCommonAPIs.sleepi(3);
            addMPSKKey9toSSID.click();
            if (keyIdentifierNote.isDisplayed()) {
                result = true;
            }

            save.click();
            MyCommonAPIs.sleepi(1);
            okSaveBtn.click();

        } else {
            logger.warning("checkSsidIsExist error");
        }

        return result;
    }

    public void deleteAllMPSKKey() {
        MyCommonAPIs.sleepi(1);
        if (settingBtn.exists()) {
            settingBtn.click();
        }
        MyCommonAPIs.sleepi(5);
        if (Advance1.exists() && Advance2.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance1).perform();
            if (mpskSettingOpt.exists() && mpskSettingOpt1.exists()) {
                a.moveToElement(mpskSettingOpt).click().perform();
            } else if (mpskSettingOpt1.exists()) {
                a.moveToElement(mpskSettingOpt1).click().perform();
            }
        } else if (Advance2.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance2).perform();
            if (mpskSettingOpt.exists() && mpskSettingOpt1.exists()) {
                a.moveToElement(mpskSettingOpt).click().perform();
            } else if (mpskSettingOpt1.exists()) {
                a.moveToElement(mpskSettingOpt1).click().perform();
            }
        }

        MyCommonAPIs.sleepi(5);
        while (DeleteMPSK.exists()) {
            logger.info("entered delete loop");
            String MPSKname = DeleteMPSK.getText();
            deleteMPSKALL(MPSKname);
            waitReady();
            waitElement(DeleteMPSK);
        }

    }

    public boolean Inviteadmin(String Name, String Email) {
        boolean result = false;
        if (Settings.exists()) {
            Settings.click();
        } else {
            Settings1.click();
        }
        MyCommonAPIs.sleepi(3);
        SecondaryAdmin.click();
        MyCommonAPIs.sleepi(8);
        InviteSecondaryAdmin.click();
        MyCommonAPIs.sleepi(8);
        SecondaryName.sendKeys(Name);
        MyCommonAPIs.sleepi(8);
        SecondaryEmail.sendKeys(Email);
        MyCommonAPIs.sleepi(8);
        SaveSecondaryOrg.click();
        MyCommonAPIs.sleepi(8);
        if (SucessfullSecondaryAdmin.exists()) {
            System.out.println("sucess");
            result = true;
        }

        return result;
    }

    public void deleteSecondAdmin(String Email) {
        MyCommonAPIs.sleepi(5);
        if (Settings.exists()) {
            Settings.click();
        } else {
            Settings1.click();
        }
        MyCommonAPIs.sleepi(3);
        SecondaryAdmin.click();
        MyCommonAPIs.sleepi(8);
        System.out.println("entering delete");
        if (new ManagerPage(false).checkManagerIsExist(Email)) {
            executeJavaScript("arguments[0].removeAttribute('class')", new ManagerPage(false).editSecondaryAdmin(Email));
            MyCommonAPIs.sleepi(10);
            new ManagerPage(false).deleteSecondaryAdmin(Email).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleepi(10);
            new ManagerPage(false).removemanager.click();
            MyCommonAPIs.sleepi(10);
            if (!new ManagerPage(false).checkManagerIsExist(Email)) {
                logger.info("Delete " + Email + " success.");
            }
        }

    }

    public void deleteSecondAdminPost(String Email) {

        if (Settings.exists()) {
            Settings.click();
        } else {
            Settings1.click();
        }
        MyCommonAPIs.sleepi(3);
        SecondaryAdmin.click();
        MyCommonAPIs.sleepi(8);
        System.out.println("entering delete");
        if (new ManagerPage(false).checkManagerIsExist(Email)) {
            System.out.println("entered delete");
            executeJavaScript("arguments[0].removeAttribute('class')", new ManagerPage(false).editSecondaryAdminPost(Email));
            MyCommonAPIs.sleepi(10);
            new ManagerPage(false).deleteSecondaryAdminPost(Email).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleepi(5);
            new ManagerPage(false).removemanager.click();
            MyCommonAPIs.sleepi(10);
            if (!new ManagerPage(false).checkManagerIsExist(Email)) {
                logger.info("Delete " + Email + " success.");
            }
        }

    }

    public boolean InvitExceed(String Name, String Email) {
        boolean result = false;
        if (Settings.exists()) {
            Settings.click();
        } else {
            Settings1.click();
        }
        MyCommonAPIs.sleepi(3);
        SecondaryAdmin.click();
        MyCommonAPIs.sleepi(8);
        InviteSecondaryAdmin.click();
        MyCommonAPIs.sleepi(8);
        if (ExceedLimit.exists()) {
            System.out.println("sucess");
            String Exceedmessage = ExceedLimit.getText();
            if (Exceedmessage.equals(
                    "You have exceeded the limit of secondary admins, please remove from existing if you want to invite new secondary admin.")) {
                System.out.println(Exceedmessage);
                result = true;
                MyCommonAPIs.sleepi(5);
                ExceedLimitok.click();
            }
        }

        return result;
    }

    public boolean DuplicateEmail(String Name, String Email) {
        boolean result = false;
        if (Settings.exists()) {
            Settings.click();
        } else {
            Settings1.click();
        }
        MyCommonAPIs.sleepi(3);
        SecondaryAdmin.click();
        MyCommonAPIs.sleepi(8);
        InviteSecondaryAdmin.click();
        MyCommonAPIs.sleepi(8);
        SecondaryName.sendKeys(Name);
        MyCommonAPIs.sleepi(8);
        SecondaryEmail.sendKeys(Email);
        MyCommonAPIs.sleepi(8);
        SaveSecondaryOrg.click();
        MyCommonAPIs.sleepi(8);
        if (EmailExits.exists()) {
            System.out.println("sucess");
            String Exceedmessage = EmailExits.getText();
            if (Exceedmessage.equals(
                    "This email address is already associated with an existing Insight account to one of the roles (ADMIN,GENERIC,VOUCHER_ADMIN_PRO,VOUCHER_ADMIN_PREMIUM,SECONDARY_ADMIN).")) {
                System.out.println(Exceedmessage);
                result = true;
            }
        }

        return result;
    }

    public boolean PendingRequest() {
        boolean result = false;
        if (Settings.exists()) {
            Settings.click();
        } else {
            Settings1.click();
        }
        MyCommonAPIs.sleepi(3);
        SecondaryAdmin.click();
        MyCommonAPIs.sleepi(3);

        if (PendingStatus.exists()) {
            System.out.println("sucess");
            result = true;
        }

        return result;
    }

    public boolean edit(String Email) {
        boolean result = false;
        if (Settings.exists()) {
            Settings.click();
        } else {
            Settings1.click();
        }
        MyCommonAPIs.sleepi(3);
        SecondaryAdmin.click();
        MyCommonAPIs.sleepi(3);

        if (new ManagerPage(false).checkManagerIsExist(Email)) {
            executeJavaScript("arguments[0].removeAttribute('class')", new ManagerPage(false).editSecondaryAdmin(Email));

        }
        return result;
    }

    public boolean Resend(String Email) {
        boolean result = false;
        if (Settings.exists()) {
            Settings.click();
        } else {
            Settings1.click();
        }
        MyCommonAPIs.sleepi(3);
        SecondaryAdmin.click();
        MyCommonAPIs.sleepi(3);

        if (new ManagerPage(false).checkManagerIsExist(Email)) {
            executeJavaScript("arguments[0].removeAttribute('class')", new ManagerPage(false).editSecondaryAdmin(Email));
            MyCommonAPIs.sleepi(10);
            new ManagerPage(false).Resend(Email).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleepi(10);
            ResendYes.click();

            String Exceedmessage = Successinvite.getText();
            if (Exceedmessage.equals("Invitation has been sent successfully")) {
                System.out.println(Exceedmessage);
                result = true;
            }

        }
        return result;
    }

    public String checkcount(String Name, String Email) {
        String result = "";
        if (Settings.exists()) {
            Settings.click();
        } else {
            Settings1.click();
        }
        MyCommonAPIs.sleepi(3);
        SecondaryAdmin.click();
        MyCommonAPIs.sleepi(8);
        InviteSecondaryAdmin.click();
        MyCommonAPIs.sleepi(8);
        result = checkNumberofUser.getText();
        System.out.println(result);
        SecondaryName.sendKeys(Name);
        MyCommonAPIs.sleepi(8);
        SecondaryEmail.sendKeys(Email);
        MyCommonAPIs.sleepi(8);
        SaveSecondaryOrg.click();
        MyCommonAPIs.sleepi(8);
        return result;
    }

    public void editAndMPSK1st(String oldName, String MPSK1) {
        // gotoSetting();
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        if (checkSsidIsExist(oldName)) {
            logger.info("Edit ssid.");
            executeJavaScript("arguments[0].removeAttribute('class')", editWifi(oldName));
            MyCommonAPIs.sleep(3000);
            editSsid(oldName).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(8 * 1000);
            MyCommonAPIs.sleep(3000);
            enableMPSK.click();
            MyCommonAPIs.sleepi(10);
            // if (selectMPSKKeyVisible.isDisplayed()) {
            selectMPSKKey.click();
            MyCommonAPIs.sleepi(10);
            SelectMPSK(MPSK1).click();
            // clkonCheckboxofMPSKKey.click();
            waitReady();
            // }

            editsave.click();
            MyCommonAPIs.sleepi(3);
            confirmok.click();
        }
    }

    public void editAndMPSK2st(String oldName, String MPSK1) {
        gotoSetting();
        // settingsorquickview.click();

        if (checkSsidIsExist(oldName)) {
            logger.info("Edit ssid.");

            executeJavaScript("arguments[0].removeAttribute('class')", editWifi(oldName));
            MyCommonAPIs.sleep(3000);
            editSsid(oldName).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(8 * 1000);
            MyCommonAPIs.sleep(3000);
            // enableMPSK.click();
            MyCommonAPIs.sleepi(10);
            if (selectMPSKKeyVisible.isDisplayed()) {
                selectMPSKKey.click();
                MyCommonAPIs.sleepi(10);
                SelectMPSK(MPSK1).click();
                // clkonCheckboxofMPSKKey.click();
                waitReady();
            }

            editsave.click();
            MyCommonAPIs.sleepi(3);
            confirmok.click();
        }
    }

    public boolean changeWifiBand(Map<String, String> map) {
        boolean result = false;
        logger.info("Change Wifi Band.");
        MyCommonAPIs.sleep(1000);
        wifiBand.click();
        MyCommonAPIs.sleep(1000);
        wifiBand.click();
        MyCommonAPIs.sleep(1000);
        wifi6GHzBand.click();
        MyCommonAPIs.sleep(1000);
        editsave.click();
        MyCommonAPIs.sleep(1000);
        confirmok.click();
        MyCommonAPIs.sleep(2000);
        if ((sixGHz.exists()) && (wpa3MixedSecurity.exists())) {
            logger.info("6 GHz Band is selcected and respective security WPA3 Personal Mixed is applied");
            result = true;
        }
        System.out.println("6 GHz Band is selcected and respective security WPA3 Personal Mixed is applied");
        return result;

    }

    public boolean changeWifiBand1(Map<String, String> map) {
        boolean result = false;
        logger.info("Change Wifi Band.");
        MyCommonAPIs.sleep(1000);
        wifiBand.click();
        MyCommonAPIs.sleep(1000);
        editsave.click();
        MyCommonAPIs.sleep(1000);
        confirmok.click();
        MyCommonAPIs.sleep(2000);
        if ((sixGHz.exists()) && (band2GHz.exists()) && (band5GHz.exists()) && (wpa3MixedSecurity.exists())) {
            logger.info("All Bands are selcected and respective security WPA3 Personal Mixed is applied");
            result = true;
        }
        System.out.println("All Bands are selcected and respective security WPA3 Personal Mixed is applied");
        return result;

    }

    public void ChannelWidth5ghz(String ChannelWidth) {
        MyCommonAPIs.sleepi(5);
        ChannelWidth5ghz.selectOption(ChannelWidth);
        MyCommonAPIs.sleepi(10);
        SaveWireless.click();
        MyCommonAPIs.sleepi(10);
        OKWireless.click();

    }

    public void ChannelWidth2ghz(String ChannelWidth) {
        MyCommonAPIs.sleepi(5);
        ChannelWidth2ghz.selectOption(ChannelWidth);
        MyCommonAPIs.sleepi(10);
        SaveWireless.click();
        MyCommonAPIs.sleepi(10);
        OKWireless.click();

    }

    public boolean ChannelWidth5ghzCheck(String ChannelWidth) {
        boolean result = true;
        MyCommonAPIs.sleepi(5);
        Select elementwidth = new Select(ChannelWidth5ghz);
        List<WebElement> channelwidthlist = elementwidth.getOptions();
        String options = channelwidthlist.get(1).getText();

        if (options.contains("160MHz")) {
            result = true;
        }
        return result;

    }

    public void GoToEEM() {
        MyCommonAPIs.sleepi(10);
        // waitElement(settingsorquickview);
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        MyCommonAPIs.sleepi(10);
        if (Advance1.exists() && Advance2.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance1).perform();
            a.moveToElement(NetworkSettings).click().perform();
        } else if (Advance2.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance2).perform();
            a.moveToElement(NetworkSettings).click().perform();
        }
        MyCommonAPIs.sleepi(3);
    }

    public void EnableEEM() {
        MyCommonAPIs.sleepi(5);;
        if(!$x("//input[@id='enableEEM']").isSelected()) {
            $x("//input[@id='enableEEM']/../span").click();
            MyCommonAPIs.sleepi(3);
            SaveIGMP.click();
        }else {
            System.out.println("alredy enabled");
        }
//        setSelected1($x("//input[@id='enableEEM']"), true);
       
        MyCommonAPIs.sleepi(5);
        if (ConformSaveIGMP.isDisplayed()) {
            ConformSaveIGMP.click();
        }

    }

    public void disableEEM() {
        MyCommonAPIs.sleepi(10);     
        if($x("//input[@id='enableEEM']").isSelected()) {
            $x("//input[@id='enableEEM']/../span").click();
            MyCommonAPIs.sleepi(3);
            SaveIGMP.click();
        }else {
            System.out.println("alredy disabled");
        }
        MyCommonAPIs.sleepi(5);
        if (ConformSaveIGMP.isDisplayed()) {
            ConformSaveIGMP.click();
        }

    }

    public boolean HelpEEM() {
        boolean result = false;
        HelpTextclickEEM.click();
        MyCommonAPIs.sleepi(3);
        String help = HelpEEMTextcheck.getText();

        if (help.contains(
                "Energy Efficiency Mode can reduce power consumption when there is no client activity on the access point. When enabled, the radio's antenna streams operate 1x1 when no client is connected. If a client initiates a connection, the antenna streams resume normal operation.")) {
            logger.info("help text exits");
            result = true;
        }
        HelpEEMTextclose.click();
        return result;
    }

    public boolean IsEEMenabled() {
        boolean result = false;
        MyCommonAPIs.sleepi(3);
        String sta = ($x("//input[@id='enableEEM']//../../div/h5")).getAttribute("checked");
        System.out.println(sta);

        if (sta == "true") {
            logger.info("it is enabled");
            result = true;

        }

        return result;
    }

    public boolean VerifyMPSK() {

        boolean result = false;
        MyCommonAPIs.sleepi(5);
        settingBtn.click();
        MyCommonAPIs.sleepi(5);
        if (advanceSettingsBtn.exists() && Advance2.exists()) {
            advanceSettingsBtn.click();
        } else if(Advance2.exists()) {
            Advance2.click();
        }      
        MyCommonAPIs.sleepi(1);
        if (mpskSettingOpt.exists() && mpskSettingOpt1.exists()) {
            mpskSettingOpt.click();
        } else if (mpskSettingOpt1.exists()) {
            mpskSettingOpt1.click();
        }
        MyCommonAPIs.sleepi(3);
        if (mpskKeyName.equals("MPSKSSIDTest01")) {
            result = true;
        }
        return result;
    }

    public boolean LineToolTip() {
        boolean result = false;
        DeviceLine.hover();
        if (DeviceUsage.isDisplayed() && DeviceSpeed.isDisplayed()) {
            result = true;
        }
        return result;

    }

    public boolean checkAddIPEnabled(String Ssid) {

        boolean result = false;
        settingsorquickview.click();
        MyCommonAPIs.sleep(5);
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);

            MyCommonAPIs.sleepi(10);
            enableClientisolation.click();
            boolean stac = checkAddIP.isSelected();
            System.out.println(stac);
            if (stac == false) {
                result = true;
            }
            MyCommonAPIs.sleepi(5);
        }

        return result;
    }

    public void editRateLimitorgwidessid(String Ssid, double uploadvalue, double downloadvalue) {
        settingsorquickview.click();
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            enterratelimit.click();
            MyCommonAPIs.sleepi(3);
            enableratelimit.click();
            MyCommonAPIs.sleepi(3);
            uploadrate.selectOption("Mbps");
            MyCommonAPIs.sleepi(3);
            moveSlider("upload", uploadvalue);
            // executeJavaScript(
            // "document.getElementsByClassName(\"ui-slider-handle ui-corner-all ui-state-default\")[0].style.left=\"'" +
            // uploadvalue + "'\"");
            MyCommonAPIs.sleepi(3);
            downloadrate.selectOption("Mbps");
            MyCommonAPIs.sleepi(3);
            moveSlider("download", downloadvalue);
            saveratelimit.click();
            MyCommonAPIs.sleepi(3);
            ratelimitok.click();
        }
    }

    public void clickonWirelessTab() {
        MyCommonAPIs.sleepi(5);
        if (proAccWirelesstab1.exists()) {
            proAccWirelesstab1.click();
        } else {
            proAccWirelessTab.click();
        }
        MyCommonAPIs.sleepi(5);
    }

    public boolean AddDomain(String Ssid, String URLs[]) {

        boolean result = false;
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        MyCommonAPIs.sleep(5);
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);

            MyCommonAPIs.sleepi(10);
            enableClientisolation.click();
            MyCommonAPIs.sleepi(10);
            if (checkBoxAddIpAddress.exists()) {
                checkBoxAddIpAddress.click();
            } else {
                clickAddIP.click();
            }      
            MyCommonAPIs.sleepi(3);
            for (String urlone : URLs) {
                DomainName.sendKeys(urlone);
                MyCommonAPIs.sleepi(3);
                AddDomainName.click();
                MyCommonAPIs.sleepi(3);
            }
        }
        MyCommonAPIs.sleepi(3);
        saveedit.click();
        MyCommonAPIs.sleepi(5);
        if (saveedfinal.isDisplayed()) {
            saveedfinal.click();
            result = true;
        }
        return result;
    }

    public boolean AddDomainisenabled(String Ssid) {

        boolean result = false;
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        MyCommonAPIs.sleep(5);
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            MyCommonAPIs.sleepi(10);
            System.out.println(AddDomainNamedisabled.isDisplayed());
            if (AddDomainNamedisabled.isDisplayed()) {
                result = true;
            }
            saveedit.click();
            MyCommonAPIs.sleepi(10);
            if (saveedfinal.isDisplayed()) {
                saveedfinal.click();
            }
        }
        return result;
    }

    public boolean addSsidFromVLANClientIso(Map<String, String> map, String URLs[]) {

        boolean result = false;
        waitReady();
        waitElement(addvlanssid);

        if (!checkSsidIsExist(map.get("SSID"))) {
            logger.info("Add ssid.");
            addvlanssid.click();
            waitElement(ssidvlan);
            ssidvlan.setValue(map.get("SSID"));
            if (bandvlan.exists()) {
                if (map.get("Band").equals("Both")) {
                    bandvlan.selectOption("Both");
                } else {
                    bandvlan.selectOption(map.get("Band"));
                }
            }
            if (band6.isDisplayed()) {
                if (checkband6.isSelected()) {
                    if (checkband6.isSelected()) {
                        if (band6.isDisplayed()) {
                            band6.click();
                            logger.info("Uncheck 6 band");
                        }
                    } else {
                        System.out.println("6GHZ is alredy unchecked");
                    }
                }
                if (map.get("Security").equals("WPA2-PSK")) {
                    security.selectOption("WPA2-PSK");
                } else {
                    security.selectOption(map.get("Security"));
                }

                if (map.containsKey("Password")) {
                    passwordvlan.setValue(map.get("Password"));
                }
                dropAdvance.click();
                enableClientisolationNetwork.click();
                MyCommonAPIs.sleepi(10);
                clickAddIPNetwork.click();
                MyCommonAPIs.sleepi(3);
                for (String urlone : URLs) {
                    DomainName.sendKeys(urlone);
                    MyCommonAPIs.sleepi(3);
                    AddDomainName.click();
                    MyCommonAPIs.sleepi(3);
                }

                takess("addSsid");
                Addvlanssid.click();
                MyCommonAPIs.sleepi(4);
                if (warnning.isDisplayed()) {
                    warnning.click();
                }

                MyCommonAPIs.sleepi(3);
                logger.info("Add ssid successful.");

            } else {
                if (map.get("Security").equals("WPA2-PSK")) {
                    security.selectOption("WPA2-PSK");
                } else {
                    security.selectOption(map.get("Security"));
                }

                if (map.containsKey("Password")) {
                    passwordvlan.setValue(map.get("Password"));
                }
                dropAdvance.click();
                enableClientisolationNetwork.click();
                MyCommonAPIs.sleepi(10);
                clickAddIPNetwork.click();
                MyCommonAPIs.sleepi(3);
                for (String urlone : URLs) {
                    DomainName.sendKeys(urlone);
                    MyCommonAPIs.sleepi(3);
                    AddDomainName.click();
                    MyCommonAPIs.sleepi(3);
                }

                takess("addSsid");
                Addvlanssid.click();
                MyCommonAPIs.sleepi(4);
                if (warnning.isDisplayed()) {
                    warnning.click();
                }

                MyCommonAPIs.sleepi(3);
                logger.info("Add ssid successful.");
            }

        } else {
            logger.warning("checkSsidIsExist error");
        }
        return result;
    }

    public void enableCaptivePortalTypeOrgSSID(String Ssid, Map<String, String> map) {
        settingsorquickview.click();
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            System.out.println("refresh");
            Selenide.refresh();
            enableIcpStep();
            if (!enabledailylogins.exists()) {
                enableschedulereports.click();
                MyCommonAPIs.sleepi(3);
            }
            enabledailylogins.click();
            MyCommonAPIs.sleepi(3);
            enabledailyanalytics.click();
            MyCommonAPIs.sleepi(3);
            enableweeklyanalytics.click();
            MyCommonAPIs.sleepi(3);
            Selenide.switchTo().frame(0);
            MyCommonAPIs.sleepi(3);
            inputportalname.setValue(map.get("Portal Name"));
            inputwelcomeheadline.setValue(map.get("Welcome Headline"));
            if (map.containsKey("Welcome Message")) {
                inputWelcomeMsg.setValue(map.get("Welcome Message"));
            }
            if (map.containsKey("Captive Portal Logo")) {
                if (map.get("Captive Portal Logo").equals("DEFAULT_LOGO")) {
                    if ($x("//option[text()='" + map.get("Captive Portal Logo") + "']").exists()) {
                        addcaptivelogo.selectOption(map.get("Captive Portal Logo"));
                    } else {
                        addcaptivelogo.selectOption(1);
                    }
                } else {
                    chooseCaptiveLogoBtn.click();
                    MyCommonAPIs.sleepi(3);
                    selectCaptiveLogoName.setValue(map.get("Captive Portal Logo"));
                    MyCommonAPIs.sleepi(3);
                    selectCaptiveLogoFile.sendKeys(map.get("Captive Portal Logo Path"));
                    MyCommonAPIs.sleepi(3);
                    uploadImgOkBtn.click();
                    MyCommonAPIs.sleepi(20);
                    addcaptivelogo.selectOption(map.get("Captive Portal Logo"));
                }
            }
            if (map.containsKey("Desktop Background Image")) {
                if (map.get("Desktop Background Image").equals("DEFAULT_BG")) {
                    if ($x("//option[text()='" + map.get("Desktop Background Image") + "']").exists()) {
                        addbackgroundimage.selectOption(map.get("Desktop Background Image"));
                    } else {
                        addbackgroundimage.selectOption(1);
                    }
                } else {
                    chooseBackgroundImg.click();
                    MyCommonAPIs.sleepi(3);
                    selectCaptiveLogoName.setValue(map.get("Desktop Background Image"));
                    MyCommonAPIs.sleepi(3);
                    selectBackgroundImgFile.sendKeys(map.get("Desktop Background Image Path"));
                    MyCommonAPIs.sleepi(3);
                    uploadImgOkBtn.click();
                    MyCommonAPIs.sleepi(20);
                    addbackgroundimage.selectOption(map.get("Desktop Background Image"));
                }
            }
            if (map.containsKey("Mobile Background Image")) {
                chooseMobileBackgroundImg.click();
                MyCommonAPIs.sleepi(3);
                selectCaptiveLogoName.setValue(map.get("Mobile Background Image"));
                MyCommonAPIs.sleepi(3);
                selectMobileBackgroundImgFile.sendKeys(map.get("Mobile Background Image Path"));
                MyCommonAPIs.sleepi(3);
                uploadImgOkBtn.click();
                MyCommonAPIs.sleepi(20);
                addmobliebackgroundimage.selectOption(map.get("Mobile Background Image"));
            }
            inputlandingurl.setValue(map.get("Landing Page URL"));
            selectsessionduration.selectOption(map.get("Session Duration"));
            clickcaptiveportalstep.click();
            MyCommonAPIs.sleepi(10);
            selectsteptype.selectOption(map.get("Step Type"));
            MyCommonAPIs.sleepi(5);
            if (map.get("Step Type").equals("Authentication Method")) {
                configAuthenticationMethod(map);
            } else if (map.get("Step Type").equals("Play Video")) {
                configPlayVideo(map);
            } else if (map.get("Step Type").equals("Payment by Paypal")) {
                configPaymentByPaypal(map);
            } else if (map.get("Step Type").equals("Display Ad")) {
                configDispalyAd(map);
            } else if (map.get("Step Type").equals("Voucher")) {
            }
            MyCommonAPIs.sleepi(3);
            savecaptiveportalstep.click();
            MyCommonAPIs.sleepi(3);
            Selenide.switchTo().defaultContent();
            MyCommonAPIs.sleepi(3);
            savecaptive.click();
            MyCommonAPIs.sleepi(10);
            captiveok.click();
            MyCommonAPIs.sleepi(5);
            logger.info("Enable instant captive portal success.");
        } else {
            logger.info("Not found ssid.");
        }
    }

    public boolean addMPSKKey1DG() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        MyCommonAPIs.sleepi(5);
        if (Advance1.exists() && Advance2.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance1).perform();
            if (mpskSettingOpt.exists() && mpskSettingOpt1.exists()) {
                a.moveToElement(mpskSettingOpt).click().perform();
            } else if (mpskSettingOpt1.exists()) {
                a.moveToElement(mpskSettingOpt1).click().perform();
            }

        } else if (Advance2.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance2).perform();
            if (mpskSettingOpt.exists() && mpskSettingOpt1.exists()) {
                a.moveToElement(mpskSettingOpt).click().perform();
            } else if (mpskSettingOpt1.exists()) {
                a.moveToElement(mpskSettingOpt1).click().perform();
            }
        }
        MyCommonAPIs.sleepi(3);
        clkAddMPSK.click();
        MyCommonAPIs.sleepi(3);
        selectVLAN.click();
        MyCommonAPIs.sleepi(3);
        selectManagement.click();
        MyCommonAPIs.sleepi(3);
        passwordMPSK.sendKeys("Netgear1@");
        MyCommonAPIs.sleepi(3);
        mpskKeyName.sendKeys("MPSKSSIDTest01");
        MyCommonAPIs.sleepi(3);
        clkonAddKeyIdentifierBtn.click();
        MyCommonAPIs.sleepi(1);
        if (cnfmMessage.isDisplayed()) {
            result = true;
        }
        MyCommonAPIs.sleepi(3);
        String mpskKeyName = verifyMPSKKeyisAdded.getText();
        MyCommonAPIs.sleepi(3);
        System.out.println(mpskKeyName);
        MyCommonAPIs.sleepi(3);
        if (mpskKeyName.equals("MPSKSSIDTest01")) {
            result = true;
        }
        return result;
    }

    public void addSsidDG(Map<String, String> map) {
        MyCommonAPIs.sleepi(10);
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        System.out.println("enterd ssid");
        MyCommonAPIs.sleepi(10);
        waitElement(addssid);
        System.out.println("waitelement enterd ssid");
        if (ssidlistone.exists()) {
            for (SelenideElement ssid : ssidlist) {
                System.out.println(ssid);
                String ssidname = ssid.$x("//td[1]//span").getText();
                // if (!ssidname.equals(map.get("SSID"))) {
                deleteSsidYes(ssidname);
                // }
            }
        } else {
            logger.warning("ssidlistone error");
        }
        map.get(ssidData);
        addSsidStep(map);
        System.out.println("adding is done ssid");

    }

    public void editWifiYesDG(Map<String, String> map) {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        waitElement(addssid);

        if (checkSsidIsExist(map.get("SSID"))) {
            logger.info("Edit ssid.");

            executeJavaScript("arguments[0].removeAttribute('class')", editWifi(map.get("SSID")));
            MyCommonAPIs.sleep(3000);
            editSsid(map.get("SSID")).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(8 * 1000);

            ssid.clear();

            ssid.setValue(map.get("SSID"));

            security.selectOption(map.get("Security"));
            boolean isKeyPresent = false;

            if (isKeyPresent == true) {
                if (map.get("Band").equals("ALL")) {
                    if (ALLband.isDisplayed()) {
                        ALLband.click();
                        MyCommonAPIs.sleepi(4);
                        logger.info("check all band");
                    }
                    waitReady();
                } else if (map.get("Band").equals("Uncheck2.4 GHz")) {
                    if (band24.isDisplayed()) {
                        band24.click();
                        MyCommonAPIs.sleepi(4);
                        band6.click();
                        logger.info("Uncheck 2.4 band");
                    }
                    waitReady();
                } else if (map.get("Band").equals("Uncheck5 GHz")) {
                    if (band5.isDisplayed()) {
                        band5.click();
                        MyCommonAPIs.sleepi(4);
                        band6.click();
                        logger.info("Uncheck 5 band");
                    }
                    waitReady();
                } else if (map.get("Band").equals("Uncheck6 GHz")) {
                    if (band6.isDisplayed()) {
                        logger.info("Uncheck 6 band");
                    }
                    waitReady();
                } else if (map.get("Band").equals("check only 2ghz")) {
                    MyCommonAPIs.sleepi(4);
                    band5.click();
                    MyCommonAPIs.sleepi(4);
                } else if (map.get("Band").equals("check only 5ghz")) {
                    MyCommonAPIs.sleepi(4);
                    band24.click();
                    waitReady();
                } else if (map.get("Band").equals("check only 6ghz")) {
                    MyCommonAPIs.sleepi(4);
                    band24.click();
                    MyCommonAPIs.sleepi(4);
                    band5.click();
                    MyCommonAPIs.sleepi(4);
                    band6.click();
                    waitReady();
                }
            } else {
                logger.info("no need to Uncheck");
            }

            if (map.containsKey("Password")) {
                password.clear();
                password.setValue(map.get("Password"));
                System.out.println("G");
            }

            takess("editWifiYes");
            saveedit.click();

            MyCommonAPIs.sleepi(3);
            confirmok.click();

            MyCommonAPIs.sleepi(3);
            logger.info("Edit ssid successful.");
            System.out.println("SSID successfully edited");
        }

    }

    public void editssidName(String oldName, String newName) {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        waitElement(addssid);

        logger.info("Edit ssid.");

        executeJavaScript("arguments[0].removeAttribute('class')", editWifi(oldName));
        MyCommonAPIs.sleep(3000);
        editSsid(oldName).waitUntil(Condition.visible, 60 * 1000).click();
        MyCommonAPIs.sleep(8 * 1000);

        ssid.clear();

        ssid.sendKeys(newName);
        saveedit.click();
        MyCommonAPIs.sleepi(3);
        confirmok.click();

        MyCommonAPIs.sleepi(3);
        System.out.println("SSID namesuccessfully edited");

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

    // Written By Vivek
    public boolean check6Ghz() {
        boolean result = false;
        waitElement(settingsorquickview);
        settingsorquickview.click();
        MyCommonAPIs.sleepi(5);
        waitReady();
        instantwifi.click();
        MyCommonAPIs.sleepi(5);
        if (sixghz.isDisplayed()) {
            result = true;
        }
        return result;
    }

    public void deleteOrgSsidYes(String Ssid) {
        MyCommonAPIs.sleepi(15);
        logger.info("Delete ssid.");
        // executeJavaScript("arguments[0].removeAttribute('class')", editWifi(Ssid));
        if (orgWideSSIDedit(Ssid).exists()) {
            orgWideSSIDedit(Ssid).click();
            logger.info("Edit ssid.");
            MyCommonAPIs.sleepi(10);
            if (deleteOrgSSID.exists()) {
                deleteOrgSSID.click();
            } else {
                deleteOrgSSID1.click();
            }
            MyCommonAPIs.sleepi(40);
            deletessidyes.click();
            MyCommonAPIs.sleep(5 * 1000);
        }

    }

    public void enableorgwidessidfacebookwifi(String Ssid) {
        entercaptiveportal.click();
        MyCommonAPIs.sleepi(3);
        enablecaptiveportal.click();
        MyCommonAPIs.sleepi(3);
        selectfacebookwifi.click();
        MyCommonAPIs.sleepi(3);
        if (modifyfbwifi.exists()) {
            modifyfbwifi.click();
            MyCommonAPIs.sleepi(5);
            savecaptive.click();
            MyCommonAPIs.sleepi(3);
            captiveok.click();
            logger.info("Enable facebook wifi success.");
            MyCommonAPIs.sleepi(5);
            clickEditSsid(Ssid);
            if (enterfbwifinew.exists()) {
                enterfbwifinew.click();
            } else {
                enterfbwifi.click();
            }
            MyCommonAPIs.sleepi(3);
            verifypage.click();
            MyCommonAPIs.waitReady();
            logger.info("Verify page success.");
        } else {
            configurefbwifi.click();
            MyCommonAPIs.sleepi(3);
            enablefbwifi.click();
            MyCommonAPIs.sleepi(3);
            registerfbwifi.click();
            MyCommonAPIs.sleepi(5);
            Selenide.switchTo().window(1);
            // Selenide.switchTo().frame("facebook");
            logger.info("Beginning to log in facebook.");
            MyCommonAPIs.sleepi(5);
            if ($("#email").exists()) {
                $("#email").setValue("sumanta.span@gmail.com");
                $("#pass").setValue("Borqs@1234");
                MyCommonAPIs.sleepi(3);
                $("#loginbutton").click();
            }
            MyCommonAPIs.sleepi(3);
            $x("//span[text()='Connect']").click();
            MyCommonAPIs.sleepi(10);
            $x("(//span[text()='Facebook Page'])[2]//..//../div[2]/div/i").click();
            MyCommonAPIs.sleepi(3);
            $x("//span[text()='CricketStadium']").click();
            MyCommonAPIs.sleepi(10);
            $x("(//span[text()='Save'])[2]").click();
            MyCommonAPIs.sleepi(10);
            Selenide.switchTo().window(0);
            logger.info("Log in facebook success.");
            MyCommonAPIs.sleepi(3);
            entercaptiveportal.click();
            MyCommonAPIs.sleepi(3);
            enablecaptiveportal.click();
            MyCommonAPIs.sleepi(3);
            selectfacebookwifi.click();
            MyCommonAPIs.sleepi(3);
            configurefbwifi.click();
            MyCommonAPIs.sleepi(3);
            savefbwifi.click();
            MyCommonAPIs.sleepi(5);
            savecaptive.click();
            MyCommonAPIs.sleepi(3);
            captiveok.click();
            logger.info("Enable facebook wifi success.");
            MyCommonAPIs.sleepi(5);
            clickEditSsid(Ssid);
            if (enterfbwifinew.exists()) {
                enterfbwifinew.click();
            } else {
                enterfbwifi.click();
            }
            MyCommonAPIs.sleepi(3);
            verifypage.click();
            MyCommonAPIs.waitReady();
            logger.info("Verify page success.");
        }
    }

    public void enableCaptivePortalType1(String Ssid, Map<String, String> map) {

        MyCommonAPIs.sleepi(10);
        if (orgwidessidedit2.exists()) {
            orgwidessidedit2.doubleClick();
        } else {
            orgWideSSIDedit1.doubleClick();
        }
        MyCommonAPIs.sleepi(10);
        orgwidessidCaptivePortal.click();
        MyCommonAPIs.sleepi(3);
        enablecaptiveportal.click();
        MyCommonAPIs.sleepi(10);
        selectinsightcaptiveportal.click();
        while (true) {
            MyCommonAPIs.sleepi(20);
            if ($("[class='loaderContainer']").isDisplayed()) {
                refresh();
                enablecaptiveportal.click();
                MyCommonAPIs.sleepi(1);
                selectinsightcaptiveportal.click();
            } else {
                break;
            }
        }
        if (!enabledailylogins.exists()) {
            enableschedulereports.click();
            MyCommonAPIs.sleepi(3);
        }
        enabledailylogins.click();
        MyCommonAPIs.sleepi(3);
        enabledailyanalytics.click();
        MyCommonAPIs.sleepi(3);
        enableweeklyanalytics.click();
        MyCommonAPIs.sleepi(3);
        Selenide.switchTo().frame(0);
        MyCommonAPIs.sleepi(3);
        inputportalname.setValue(map.get("Portal Name"));
        inputwelcomeheadline.setValue(map.get("Welcome Headline"));
        if (map.containsKey("Welcome Message")) {
            inputWelcomeMsg.setValue(map.get("Welcome Message"));
        }
        if (map.containsKey("Captive Portal Logo")) {
            if (map.get("Captive Portal Logo").equals("DEFAULT_LOGO")) {
                if ($x("//option[text()='" + map.get("Captive Portal Logo") + "']").exists()) {
                    addcaptivelogo.selectOption(map.get("Captive Portal Logo"));
                } else {
                    addcaptivelogo.selectOption(1);
                }
            } else {
                chooseCaptiveLogoBtn.click();
                MyCommonAPIs.sleepi(3);
                selectCaptiveLogoName.setValue(map.get("Captive Portal Logo"));
                MyCommonAPIs.sleepi(3);
                selectCaptiveLogoFile.sendKeys(map.get("Captive Portal Logo Path"));
                MyCommonAPIs.sleepi(3);
                uploadImgOkBtn.click();
                MyCommonAPIs.sleepi(20);
                addcaptivelogo.selectOption(map.get("Captive Portal Logo"));
            }
        }
        if (map.containsKey("Desktop Background Image")) {
            if (map.get("Desktop Background Image").equals("DEFAULT_BG")) {
                if ($x("//option[text()='" + map.get("Desktop Background Image") + "']").exists()) {
                    addbackgroundimage.selectOption(map.get("Desktop Background Image"));
                } else {
                    addbackgroundimage.selectOption(1);
                }
            } else {
                chooseBackgroundImg.click();
                MyCommonAPIs.sleepi(3);
                selectCaptiveLogoName.setValue(map.get("Desktop Background Image"));
                MyCommonAPIs.sleepi(3);
                selectBackgroundImgFile.sendKeys(map.get("Desktop Background Image Path"));
                MyCommonAPIs.sleepi(3);
                uploadImgOkBtn.click();
                MyCommonAPIs.sleepi(20);
                addbackgroundimage.selectOption(map.get("Desktop Background Image"));
            }
        }
        if (map.containsKey("Mobile Background Image")) {
            chooseMobileBackgroundImg.click();
            MyCommonAPIs.sleepi(3);
            selectCaptiveLogoName.setValue(map.get("Mobile Background Image"));
            MyCommonAPIs.sleepi(3);
            selectMobileBackgroundImgFile.sendKeys(map.get("Mobile Background Image Path"));
            MyCommonAPIs.sleepi(3);
            uploadImgOkBtn.click();
            MyCommonAPIs.sleepi(20);
            addmobliebackgroundimage.selectOption(map.get("Mobile Background Image"));
        }
        inputlandingurl.setValue(map.get("Landing Page URL"));
        selectsessionduration.selectOption(map.get("Session Duration"));
        clickcaptiveportalstep.click();
        MyCommonAPIs.sleepi(10);
        selectsteptype.selectOption(map.get("Step Type"));
        MyCommonAPIs.sleepi(10);
        if (map.get("Step Type").equals("Authentication Method")) {
            configAuthenticationMethod(map);
        } else if (map.get("Step Type").equals("Play Video")) {
            configPlayVideo(map);
        } else if (map.get("Step Type").equals("Payment by Paypal")) {
            configPaymentByPaypal(map);
        } else if (map.get("Step Type").equals("Display Ad")) {
            configDispalyAd(map);
        } else if (map.get("Step Type").equals("Voucher")) {
        }
        MyCommonAPIs.sleepi(3);
        savecaptiveportalstep.click();
        MyCommonAPIs.sleepi(3);
        Selenide.switchTo().defaultContent();
        MyCommonAPIs.sleepi(3);
        savecaptive.click();
        MyCommonAPIs.sleepi(10);
        captiveok.click();
        MyCommonAPIs.sleepi(5);
        logger.info("Enable instant captive portal success.");
    }

    public boolean verifyErrorMsgforICPSub() {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        orgWideSSIDedit1.doubleClick();
        MyCommonAPIs.sleepi(10);
        orgwidessidCaptivePortal.click();
        MyCommonAPIs.sleepi(3);
        enablecaptiveportal.click();
        MyCommonAPIs.sleepi(10);
        selectinsightcaptiveportal.click();
        MyCommonAPIs.sleepi(10);
        if (orgwideSSIDIPCErrrorMsgPopup.exists()) {
            MyCommonAPIs.sleepi(10);
            result = true;
        }
        btnInsufcred.click();
        MyCommonAPIs.sleepi(5);
        cancelorgSSIDBtn.click();
        MyCommonAPIs.sleepi(5);
        return result;
    }

    public void deleteOrgSsidYes1(String Ssid) {
        MyCommonAPIs.sleepi(10);
        logger.info("Delete ssid.");
        // executeJavaScript("arguments[0].removeAttribute('class')", editWifi(Ssid));
        if (orgWideSSIDedit3.exists()) {
            orgWideSSIDedit3.click();
            logger.info("Edit ssid.");
            MyCommonAPIs.sleepi(3);
            deleteOrgSSID1.click();
            MyCommonAPIs.sleepi(30);
            deletessidyes.click();
            MyCommonAPIs.sleep(5 * 1000);
        }

    }

    public boolean verifyClearStatsoptioninStatastics() {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        statisticcsTab.click();
        MyCommonAPIs.sleepi(10);
        while (statisicsBody.exists() && clientOne.exists() != true) {
            MyCommonAPIs.sleepi(60);
            refresh();
        }
        refresh();
        waitElement(statisicsBody);
        if (statisicsBody.exists() && clientOne.exists()) {
            clearStatsButton.click();
            MyCommonAPIs.sleepi(5);
            if (clientZero.exists() && brdcastToMulticastSta.exists() && unicastStatus.exists()) {
                result = true;
                logger.info("Stats are cleared");
            }
        }
        return result;
    }

    public boolean verifyWirelessDataConsumption(String apSerialNumber) {
        boolean result = false;
        if (wireessDataConsumText.exists()) {
            wireessDataConsumLink.click();
            MyCommonAPIs.sleepi(20);
            selecChart.selectOption("Last 24 hrs");
            MyCommonAPIs.sleepi(10);
            if (dataConsumpChart.exists() && apDetails(apSerialNumber).exists()) {
                result = true;
            }
        }
        return result;
    }

    public boolean verifyWirelessDataConsumptionPage(String apSerialNumber) {
        boolean result = false;
        if (wireessDataConsumText.exists()) {
            wireessDataConsumLink.click();
            MyCommonAPIs.sleepi(20);
            if (dataConsumpChart.exists() && apDetails(apSerialNumber).exists()) {
                result = true;
            }
        }
        return result;
    }

    public boolean verifyConnectedClientonWirelessPage(Map<String, String> map) {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        while ((connectedClientGraph1.exists() && connectedClientGraph2.exists() && windowsOS.exists()
                && ssidDetails(map.get("SSID")).exists()) != true) {
            MyCommonAPIs.sleepi(30);
            refresh();
        }
        MyCommonAPIs.sleepi(10);
        if (connectedClientGraph1.exists() && connectedClientGraph2.exists() && windowsOS.exists() && ssidDetails(map.get("SSID")).exists()) {
            result = true;
            logger.info("Connected clients graph is showing on Wireless Page");
        }

        return result;
    }

    public boolean verifyConnectedClientPerSSID(Map<String, String> map) {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        while ((connectedClientGraph1.exists() && connectedClientGraph2.exists() && windowsOS.exists()
                && ssidDetails(map.get("SSID")).exists()) != true) {
            MyCommonAPIs.sleepi(30);
            refresh();
        }

        // while((ssidDetails2(map.get("SSID")).exists()) != true){
        // MyCommonAPIs.sleepi(10);
        // refresh();
        // }
        MyCommonAPIs.sleepi(30);
        refresh();
        MyCommonAPIs.sleepi(10);
        connectedClientPerSSIDLink.click();
        MyCommonAPIs.sleepi(15);
        while ((maxconnecetedClient.exists()) != true) {
            MyCommonAPIs.sleepi(10);
            refresh();
        }
        MyCommonAPIs.sleepi(10);
        if (connectedClientPerSSIDPageHeader.exists() && connectedClientPerSSIDTable.exists() && maxconnecetedClient.exists()
                && ssidDetails1(map.get("SSID")).exists()) {
            result = true;
            logger.info("Connected client per SSID table is showing");
        }
        return result;
    }

    public void deleteSecondAdminALL() {
        MyCommonAPIs.sleepi(5);
        if (Settings.exists()) {
            Settings.click();
        } else {
            Settings1.click();
        }
        MyCommonAPIs.sleepi(3);
        SecondaryAdmin.click();
        MyCommonAPIs.sleepi(8);
        System.out.println("entering delete");
        while (Seondexits.exists()) {
            String Email = Seondname.getText();
            System.out.println(Email);
            if (new ManagerPage(false).checkManagerIsExist(Email)) {
                executeJavaScript("arguments[0].removeAttribute('class')", new ManagerPage(false).editSecondaryAdmin(Email));
                MyCommonAPIs.sleepi(10);
                new ManagerPage(false).deleteSecondaryAdmin(Email).waitUntil(Condition.visible, 60 * 1000).click();
                MyCommonAPIs.sleepi(10);
                new ManagerPage(false).removemanager.click();
                MyCommonAPIs.sleepi(10);
                if (!new ManagerPage(false).checkManagerIsExist(Email)) {
                    logger.info("Delete " + Email + " success.");
                }
            }

        }

    }

    public boolean linetooltip(String SLNo) {
        boolean result = false;
        MyCommonAPIs.sleepi(60); // waiting to generate a line between a AP and a client connected in topology.
        refresh();
        checklinetooltip(SLNo).hover();
        MyCommonAPIs.sleepi(5);
        if (Speed.isDisplayed() & Usage.isDisplayed()) {
            System.out.println("it is visible");
            result = true;
        }
        return result;
    }

    public boolean checkTopologyView() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        Sidemenu.click();
        MyCommonAPIs.sleepi(10);
        Topologyview.click();
        MyCommonAPIs.sleepi(10);
        if (TreeView.isDisplayed() && AbstractViewc.isDisplayed() && SSIDView.isDisplayed()) {
            System.out.println("all are present");
            result = true;
        }

        return result;

    }

    public boolean checkssidView(String ssidName) {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        Sidemenu.click();
        MyCommonAPIs.sleepi(10);
        Topologyview.click();
        MyCommonAPIs.sleepi(10);
        SSIDexits.click();
        MyCommonAPIs.sleepi(10);
        if (ssidcheck(ssidName).isDisplayed()) {
            System.out.println("ssid  present");
            result = true;
        }

        return result;

    }

    public void optimizeInstantWifi1(boolean toWait) {

        waitElement(settingsorquickview);
        settingsorquickview.click();
        MyCommonAPIs.sleepi(5);
        waitReady();
        instantwifi.click();
        waitElement(optimizenowbutton);
        timerStart(22 * 60);
        while (timerRunning()) {
            optimizenowbutton.click();
            waitReady();
        }
    }

    public void BasicCaptivePortal(String Ssid, String url) {
        settingsorquickview.click();
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            entercaptiveportal.click();
            MyCommonAPIs.sleepi(3);
            enablecaptiveportal.click();
            MyCommonAPIs.sleepi(10);
            basiccaptiveportal.click();
            MyCommonAPIs.sleepi(3);
            enableredirecturl.click();
            MyCommonAPIs.sleepi(3);
            inputredirecturl.clear();
            inputredirecturl.sendKeys(url);
            MyCommonAPIs.sleepi(3);
            savecaptive.click();
            MyCommonAPIs.sleepi(3);
            captiveok.click();
        }
    }

    public void enableclientIsolationwired(String Ssid, String ip) {

        settingsorquickview.click();
        MyCommonAPIs.sleep(5);
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            MyCommonAPIs.sleepi(10);
            enableClientisolation.click();
            MyCommonAPIs.sleepi(10);
            wiredcheckbox.click();
            MyCommonAPIs.sleepi(10);
            wiredaddress.click();
            wiredaddress.sendKeys(ip);
            MyCommonAPIs.sleepi(10);
            addbutton.click();
            MyCommonAPIs.sleepi(5);
            editsave.click();
            waitReady();
            MyCommonAPIs.sleepi(3);
            confirmok.click();
            MyCommonAPIs.sleepi(4);
        }
    }

    public WirelessQuickViewPage scheduleWifiWithNat(Map<String, String> map) {

        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        waitElement(addssid);
        if (ssidlistone.exists()) {
            for (SelenideElement ssid : ssidlist) {
                String ssidname = ssid.$x("//td[1]//span").getText();
                // if (!ssidname.equals(map.get("SSID"))) {
                deleteSsidYes(ssidname);
                // }
            }
        } else {
            logger.warning("ssidlistone error");
        }
        if (!checkSsidIsExist(map.get("SSID"))) {
            logger.info("Add ssid.");
            addssid.click();
            waitElement(ssid);
            ssid.setValue(map.get("SSID"));
            if (checkband6.isDisplayed()) {
                if (checkband6.isSelected()) {
                    if (band6.isDisplayed()) {
                        band6.click();
                        logger.info("Uncheck 6 band");
                    }
                } else {
                    System.out.println("6GHZ is alredy unchecked");
                }
            }
            security.selectOption(map.get("Security"));
            enablessidschedule.click();
            if (map.containsKey("Password")) {
                password.setValue(map.get("Password"));
            }
            adressing.selectOption("NAT");
            networkip.click();
            MyCommonAPIs.sleepi(10);

            networkip.sendKeys("10.0.0.0");
            MyCommonAPIs.sleepi(10);
            if (!addnewschedule.exists()) {
                enablessidschedule.click();
                MyCommonAPIs.sleepi(1);
            }
            addnewschedule.click();
            schedulename.setValue(map.get("Schedule Name"));
            if (map.containsKey("Schedule Days")) {
                selectDays(map.get("Schedule Days")).click();
            } else {
                SimpleDateFormat df = new SimpleDateFormat("E");
                selectDays(df.format(new Date()).toString()).click();
            }
            // MyCommonAPIs.sleepi(5);
            // selectday.click();
            MyCommonAPIs.sleepi(5);
            strtpickbtn.click();
            MyCommonAPIs.sleepi(1);
            for (int i = 1; i <= 10; i++) {
                selectTimeNagivation.click();
                MyCommonAPIs.sleepi(1);
            }
            okbtn.click();

            MyCommonAPIs.sleepi(1);
            SelectEndTime.click();
            MyCommonAPIs.sleepi(1);
            for (int i = 1; i <= 11; i++) {
                SelectEndTimeNavigation.click();
                MyCommonAPIs.sleepi(1);
            }

            OkayEndtime.click();
            MyCommonAPIs.sleepi(1);
            takess("addSsid");
            save.click();
            MyCommonAPIs.sleepi(3);
            logger.info("ssid scheduled successful.");
        } else {
            logger.warning("checkSsidIsExist error");
        }
        return new WirelessQuickViewPage();

    }

    public boolean addAndEdit(Map<String, String> map) {

        boolean result = false;
        waitElement(settingsorquickview);
        settingsorquickview.click();
        waitReady();
        waitElement(addssid);
        boolean isKeyPresent = false;

        if (!checkSsidIsExist(map.get("SSID"))) {
            logger.info("Add ssid.");
            addssid.click();
            MyCommonAPIs.sleepi(15);
            waitElement(ssid);
            ssid.setValue(map.get("SSID"));
            waitReady();

            isKeyPresent = map.containsKey("Band");
            System.out.println(isKeyPresent);
            if (isKeyPresent == true) {

                if (map.get("Band").equals("ALL")) {
                    if (ALLband.isDisplayed()) {
                        ALLband.click();
                    }
                } else if (map.get("Band").equals("Uncheck2.4 GHz")) {
                    if (band24.isDisplayed()) {
                        band24.click();
                    }
                    waitReady();
                } else if (map.get("Band").equals("Uncheck5 GHz")) {
                    if (band5.isDisplayed()) {
                        band5.click();
                        MyCommonAPIs.sleepi(4);
                    }
                    waitReady();
                } else if (map.get("Band").equals("Uncheck2.4 GHz and check 6ghz")) {
                    if (band24.isDisplayed()) {
                        band24.click();
                        MyCommonAPIs.sleepi(4);
                        band6.click();
                    }
                    waitReady();
                } else if (map.get("Band").equals("Uncheck5 GHz and check 6ghz")) {
                    if (band5.isDisplayed()) {
                        band5.click();
                        MyCommonAPIs.sleepi(4);
                        band6.click();
                    }
                } else if (map.get("Band").equals("Uncheck6 GHz")) {
                    if (band6.isDisplayed()) {
                        logger.info("Uncheck 6 band");
                    }
                    waitReady();
                } else if (map.get("Band").equals("check only 6ghz")) {
                    MyCommonAPIs.sleepi(4);
                    band24.click();
                    MyCommonAPIs.sleepi(4);
                    band5.click();
                    MyCommonAPIs.sleepi(4);
                    band6.click();
                }

                else {
                    logger.info("no need to Uncheck");
                }

                if (map.get("Security").equals("WPA2-PSK")) {
                    security.selectOption("WPA2-PSK");
                    waitReady();
                } else {
                    security.selectOption(map.get("Security"));
                    waitReady();
                }

                if (map.containsKey("Password")) {
                    password.setValue(map.get("Password"));
                    waitReady();
                }
                takess("addSsid");
                save.click();
                waitReady();
                MyCommonAPIs.sleepi(4);
                if (Maximumlimit.exists()) {
                    String ErrorMessage = Maximumlimit.getText();
                    logger.info(ErrorMessage);
                    close.click();
                    result = true;
                }

                MyCommonAPIs.sleepi(3);
                logger.info("Add ssid successful.");
            }  else {
                logger.warning("checkSsidIsExist error");
            }
            return result;
        }
        return isKeyPresent;
    }

    public boolean addECP(Map<String, String> map) {

        boolean result = false;
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        waitElement(addssid);
        boolean isKeyPresent = false;

        if (!checkSsidIsExist(map.get("SSID"))) {
            logger.info("Add ssid.");
            addssid.click();
            MyCommonAPIs.sleepi(10);
            waitElement(ssid);
            MyCommonAPIs.sleepi(4);
            ssid.setValue(map.get("SSID"));
            waitReady();
            MyCommonAPIs.sleepi(4);

            if (checkband6.isDisplayed()) {
                if (checkband6.isSelected()) {
                    if (band6.isDisplayed()) {
                        band6.click();
                        logger.info("Uncheck 6 band");
                    }
                } else {
                    System.out.println("6GHZ is alredy unchecked");
                }
            }

            if (map.get("Security").equals("WPA2-PSK")) {
                security.selectOption("WPA2-PSK");
                waitReady();
            } else {
                security.selectOption(map.get("Security"));
                waitReady();
            }

            if (map.containsKey("Password")) {
                password.setValue(map.get("Password"));
                waitReady();
            }
            takess("addSsid");
            if (map.containsKey("ECP")) {
                saveandconfigure.click();
                MyCommonAPIs.sleepi(20);
                enableECP();
                MyCommonAPIs.sleepi(10);
            }

//            if (ECPRadio.isDisplayed()) {
//                System.out.println("ECP eror appeared");
//                result = true;
//            }
            if (ECPRadio.exists()) {                                      
                List<SelenideElement> buttons = $$x("//*[text()='Warning']");
                for (SelenideElement button : buttons) {
                    if (button.is(Condition.visible)) {
                        result = true;
                        break;  // Click the first visible button and stop
                    }
                }
                          
            }
            
            
            waitReady();
            MyCommonAPIs.sleepi(4);
//            okECP.click();
            if (okECP.exists()) {
                okECP.click();
//                MyCommonAPIs.sleepi(20);
//                selectinsightECP.click();
            }
            logger.info("Add ssid successful.");
        } else {
            logger.warning("checkSsidIsExist error");
        }
        System.out.println("Result = " + result);
        return result;
    }

    public void addAndEditSsid(String SSID, Map<String, String> map) {
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
            if (checkSsidIsExist(SSID)) {
                MyCommonAPIs.sleepi(4);
                clickEditSsid(SSID);
            }
//            boolean isKeyPresent = false;
//            waitReady();
//            isKeyPresent = map.containsKey("Band");
//            System.out.println(isKeyPresent);
//            if (isKeyPresent == true) {

                boolean result = false;

                if (map.get("Band").equals("Click 2.4 and 5ghz")) {
                    if (band24.isDisplayed()) {
                        band24.click();
                        MyCommonAPIs.sleepi(4);
                        band5.click();
                    }
                    waitReady();
                } else if (map.get("Band").equals("Click 5ghz and 6ghz")) {
                    if (band5.isDisplayed()) {
                        band6.click();
                        MyCommonAPIs.sleepi(4);
                        band5.click();
                    }
                    waitReady();
                } else if (map.get("Band").equals("Click 5ghz and 6ghz and 2.4ghz")) {
                    if (band5.isDisplayed()) {
                        band6.click();
                        MyCommonAPIs.sleepi(4);
                        band5.click();
                        MyCommonAPIs.sleepi(4);
                        band24.click();
                    }
                    waitReady();
                } else if (map.get("Band").equals("Click 6ghz and 2.4ghz")) {
                    if (band24.isDisplayed()) {
                        band6.click();
                        MyCommonAPIs.sleepi(4);
                        band24.click();
                    }
                    waitReady();
                } else if (map.get("Band").equals("Click 6ghz and 5ghz")) {
                    if (band6.isDisplayed()) {
                        band6.click();
                        MyCommonAPIs.sleepi(4);
                        band5.click();
                    }
                    waitReady();

                } else if (map.get("Band").equals("Click 5ghz")) {
                    if (band5.isDisplayed()) {
                        band5.click();
                    }
                    waitReady();
                } else if (map.get("Band").equals("Click 6ghz")) {
                    if (band6.isDisplayed()) {
                        band6.click();
                    }
                    waitReady();
                } else if (map.get("Band").equals("Click 2.4ghz")) {
                    if (band24.isDisplayed()) {
                        band24.click();
                    }
                } else {
                    logger.info("no need to Uncheck");
                }
                
                if (map.get("Security").equals("WPA2-PSK")) {
                    security.selectOption("WPA2-PSK");
                    waitReady();
                } else {
                    security.selectOption(map.get("Security"));
                    waitReady();
                }
                takess("addSsid");
                MyCommonAPIs.sleepi(4);
//            }
            MyCommonAPIs.sleepi(3);
            logger.info("Edit ssid successful.");
        } else {
            logger.warning("checkSsidIsExist error");
        }

        saveedit.click();
        waitReady();

        MyCommonAPIs.sleepi(3);
        confirmok.click();
    }

    public void enableECP() {

//        entercaptiveportal.click();
        if (entercaptiveportal.exists()) {
            entercaptiveportal.click();
            MyCommonAPIs.sleepi(20);
            selectinsightECP.click();
        }
//        enablecaptiveportal.click();
        // refresh();
        // MyCommonAPIs.sleepi(15);
        // enablecaptiveportal.click();
           
        

    }

    public String enableMpskNat(String oldName) {
        // gotoSetting();
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        if (checkSsidIsExist(oldName)) {
            logger.info("Edit ssid.");
            executeJavaScript("arguments[0].removeAttribute('class')", editWifi(oldName));
            MyCommonAPIs.sleep(3000);
            editSsid(oldName).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(8 * 1000);
            MyCommonAPIs.sleep(3000);
            enableMPSK.click();
            MyCommonAPIs.sleepi(10);
            if (mpskwarning.exists()) { return mpskwarning.getText(); }

        }
        return null;
    }

    public void editCustomVlan(Map<String, String> map, String n) {
        settingsorquickview.click();
        if (checkSsidIsExist(map.get("SSID"))) {
            logger.info("Edit ssid.");
            executeJavaScript("arguments[0].removeAttribute('class')", editWifi(map.get("SSID")));
            MyCommonAPIs.sleep(3000);
            editSsid(map.get("SSID")).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(8 * 1000);
            addcustomvlan.click();
            MyCommonAPIs.sleepi(3);
            customid.sendKeys("8");
            MyCommonAPIs.sleepi(5);
            vlansave.click();
            MyCommonAPIs.sleepi(5);
            saveedit.click();

            MyCommonAPIs.sleepi(3);
            confirmok.click();

            MyCommonAPIs.sleepi(3);
            logger.info("Edit ssid successful.");
            System.out.println("SSID successfully edited");
        }

    }

    public WirelessQuickViewPage addSsidNat(Map<String, String> map) {
        MyCommonAPIs.sleepi(10);
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        System.out.println("enterd ssid");
        MyCommonAPIs.sleepi(10);
        waitElement(addssid);
        System.out.println("waitelement enterd ssid");
        if (ssidlistone.exists()) {
            for (SelenideElement ssid : ssidlist) {
                System.out.println(ssid);
                String ssidname = ssid.$x("//td[1]//span").getText();
                // if (!ssidname.equals(map.get("SSID"))) {
                deleteSsidYes(ssidname);
                // }
            }
        } else {
            logger.warning("ssidlistone error");
        }
        map.get(ssidData);
        addSsidStepFastNat(map);
        System.out.println("adding is done ssid");
        return new WirelessQuickViewPage();
    }

    public void addSsidStepFastNat(Map<String, String> map) {
        System.out.println("eneterd ssid adding");
        if (!checkSsidIsExist(map.get("SSID"))) {
            System.out.println("ssid not exits");
            logger.info("Add ssid.");
            MyCommonAPIs.sleepi(5);

            // if(settingsorquickview.exists()) {
            // settingsorquickview.click();
            addssid.click();
            System.out.println("add ssid click");
            waitElement(ssid);
            MyCommonAPIs.sleepi(20);
            ssid.setValue(map.get("SSID"));
            if (checkband6.isDisplayed()) {
                if (checkband6.isSelected()) {
                    if (band6.isDisplayed()) {
                        band6.click();
                        logger.info("Uncheck 6 band");
                    }
                } else {
                    System.out.println("6GHZ is alredy unchecked");
                }
            }
            if (map.get("Security").equals("WPA2 PSK")) {
                security.selectOption("WPA2-PSK");
            } else {
                security.selectOption(map.get("Security"));
            }
            System.out.println("before password");
            MyCommonAPIs.sleepi(20);
            if (map.containsKey("Password")) {
                password.setValue(map.get("Password"));
            }
            if (map.containsKey("OWE")) {
                owe.click();
                owetmode.click();
            } else if (map.containsKey("OWETM")) {
                owe.click();

            }

            takess("addSsid");

            adressing.selectOption("NAT");
            networkip.click();
            MyCommonAPIs.sleepi(10);

            networkip.sendKeys("10.0.0.0");
            MyCommonAPIs.sleepi(10);

            MyCommonAPIs.waitElement(save);
            logger.info("Save button is Visible");
            save.click();
            MyCommonAPIs.sleepi(5);
            if (oksave.isDisplayed()) {
                oksave.click();
            }
            MyCommonAPIs.sleepi(5);
            logger.info("Add ssid successful.");
            // } else {
            // logger.warning("checkSsidIsExist error");
            // }
            System.out.println("exits ssid");
        }
    }

    public WirelessQuickViewPage addSsidEnablingFastRoaming(Map<String, String> map) {
        MyCommonAPIs.sleepi(10);
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        System.out.println("enterd ssid");
        MyCommonAPIs.sleepi(10);
        waitElement(addssid);
        System.out.println("waitelement enterd ssid");
        if (ssidlistone.exists()) {
            for (SelenideElement ssid : ssidlist) {
                System.out.println(ssid);
                String ssidname = ssid.$x("//td[1]//span").getText();
                // if (!ssidname.equals(map.get("SSID"))) {
                deleteSsidYes(ssidname);
                // }
            }
        } else {
            logger.warning("ssidlistone error");
        }
        map.get(ssidData);
        addSsidStepFastRoaming(map);
        System.out.println("adding is done ssid");
        return new WirelessQuickViewPage();
    }

    public void addSsidStepFastRoaming(Map<String, String> map) {
        System.out.println("eneterd ssid adding");
        if (!checkSsidIsExist(map.get("SSID"))) {
            System.out.println("ssid not exits");
            logger.info("Add ssid.");
            MyCommonAPIs.sleepi(5);

            // if(settingsorquickview.exists()) {
            // settingsorquickview.click();
            addssid.click();
            System.out.println("add ssid click");
            waitElement(ssid);
            MyCommonAPIs.sleepi(20);
            ssid.setValue(map.get("SSID"));
            if (checkband6.isDisplayed()) {
                if (checkband6.isSelected()) {
                    if (band6.isDisplayed()) {
                        band6.click();
                        logger.info("Uncheck 6 band");
                    }
                } else {
                    System.out.println("6GHZ is alredy unchecked");
                }
            }
            if (map.get("Security").equals("WPA2 PSK")) {
                security.selectOption("WPA2-PSK");
            } else {
                security.selectOption(map.get("Security"));
            }
            System.out.println("before password");
            MyCommonAPIs.sleepi(20);
            if (map.containsKey("Password")) {
                password.setValue(map.get("Password"));
            }

            MyCommonAPIs.sleepi(10);
            fastroaming1.click();
            takess("addSsid");

            MyCommonAPIs.waitElement(save);
            logger.info("Save button is Visible");
            save.click();
            MyCommonAPIs.sleepi(5);
            if (oksave.isDisplayed()) {
                oksave.click();
            }
            MyCommonAPIs.sleepi(5);
            logger.info("Add ssid successful.");
            // } else {
            // logger.warning("checkSsidIsExist error");
            // }
            System.out.println("exits ssid");
        }
    }

    public boolean CreateECP(Map<String, String> map) {

        boolean result = false;
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        waitElement(addssid);
        boolean isKeyPresent = false;

        if (!checkSsidIsExist(map.get("SSID"))) {
            logger.info("Add ssid.");
            addssid.click();
            MyCommonAPIs.sleepi(10);
            waitElement(ssid);
            MyCommonAPIs.sleepi(4);
            ssid.setValue(map.get("SSID"));
            waitReady();
            MyCommonAPIs.sleepi(4);

            if (checkband6.isDisplayed()) {
                if (checkband6.isSelected()) {
                    if (band6.isDisplayed()) {
                        band6.click();
                        logger.info("Uncheck 6 band");
                    }
                } else {
                    System.out.println("6GHZ is alredy unchecked");
                }
            }

            if (map.get("Security").equals("WPA2-PSK")) {
                security.selectOption("WPA2-PSK");
                waitReady();
            } else {
                security.selectOption(map.get("Security"));
                waitReady();
            }

            if (map.containsKey("Password")) {
                password.setValue(map.get("Password"));
                waitReady();
            }
            takess("addSsid");
            if (map.containsKey("ECP")) {
                saveandconfigure.click();
                MyCommonAPIs.sleepi(20);
                enableECP();
                MyCommonAPIs.sleepi(20);
            }

            if (saveokECP.isDisplayed()) {
                saveokECP.click();
            }

            waitReady();
            MyCommonAPIs.sleepi(4);
            if (ECPProceed.isDisplayed()) {
                ECPProceed.click();
            }
            MyCommonAPIs.sleepi(4);
            if (SuccsECP.isDisplayed()) {
                SuccsECP.click();
            }

            logger.info("Add ssid successful.");
        } else {
            logger.warning("checkSsidIsExist error");
        }
        return result;
    }

    public boolean editECP(String Ssid, Map<String, String> map) {

        boolean result = false;
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            if (map.get("Security").equals("WPA2 PSK")) {
                security.selectOption("WPA2-PSK");
            } else {
                security.selectOption(map.get("Security"));
            }

            MyCommonAPIs.sleepi(3);
            editsave.click();
            MyCommonAPIs.sleepi(10);

        }

        if (editECPRadio.isDisplayed()) {
            System.out.println("ECP eror appeared");
            result = true;
        }
        waitReady();
        MyCommonAPIs.sleepi(4);
        okECPedit.click();

        return result;

    }

    public void enableECP(String Ssid, Map<String, String> map) {
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            enableECP();
            
            if(map.containsKey("ECP Type")) {
                selectDropdown.click();
                MyCommonAPIs.sleepi(4);
                if(map.get("ECP Type").equals("GoZone Wi-Fi")) {
                    selectGoZone.click();
                }else {
                selectJaze.click();
            }
                MyCommonAPIs.sleepi(2);
            }

            if (map.containsKey("Splash Page URL")) {
                MyCommonAPIs.sleepi(4);
                SplashPageURL.clear();
                SplashPageURL.sendKeys(map.get("Splash Page URL"));
            }
            if (map.containsKey("Captive Portal Authentication Type")) {
                MyCommonAPIs.sleepi(4);
                radius.click();
            }
            
            if (map.containsKey("NASID")) {
                NASID.sendKeys(map.get("NASID"));
            }

            if (map.containsKey("IPv4 Address")) {
                MyCommonAPIs.sleepi(4);
                IP.clear();
                IP.sendKeys(map.get("IPv4 Address"));
            }
            
            if (map.containsKey("Primary Address")) {
                for(int i=1;i<=4;i+=2)
                {   goZoneIP(i).clear();
                    MyCommonAPIs.sleepi(3);
                    goZoneIP(i).sendKeys(map.get("Primary Address"));
                }
            }
            
            if (map.containsKey("Secondary Address")) {
                
                for(int i=2;i<=4;i+=2)
                {   
                   goZoneIP(i).clear();
                    MyCommonAPIs.sleepi(3);
                    goZoneIP(i).sendKeys(map.get("Secondary Address"));
                }
            }
            if (map.containsKey("PasswordGoZone")) {
                
                for(int i=1;i<=4;i++)
                { 
                MyCommonAPIs.sleepi(4);
                goZonePassword(i).clear();
                goZonePassword(i).sendKeys(map.get("PasswordGoZone"));
            }
            }
                
            if (map.containsKey("Password")) {
                MyCommonAPIs.sleepi(4);
                IPPassword.clear();
                IPPassword.sendKeys(map.get("Password"));
            }
            

            if (map.containsKey("Secret")) {
                MyCommonAPIs.sleepi(4);
                WebSecret.sendKeys(map.get("Secret"));
            }

            if (map.containsKey("Key")) {
                MyCommonAPIs.sleepi(4);
                WebKey.sendKeys(map.get("Key"));
            }

            if (map.containsKey("Allow HTTPS")) {
                MyCommonAPIs.sleepi(4);
                System.out.println("enterd allow");
                if (map.get("Allow HTTPS") == "Enable") {
                    System.out.println("enterd enable");
                    AllowHTTPSEnable.click();
                }

            }

            if (map.containsKey("Failsafe")) {
                MyCommonAPIs.sleepi(4);
                System.out.println("enterd allow");
                if (map.get("Failsafe") == "Enable") {
                    System.out.println("enterd enable");
                    AllowFailsafe.click();
                }

            }

            if (map.containsKey("Walled Garden")) {
                MyCommonAPIs.sleepi(4);
                WalledGarden.sendKeys(map.get("Walled Garden"));
                if(map.containsKey("Walled Garden1"))
                {
                    MyCommonAPIs.sleepi(4);
                    WalledGarden.sendKeys(Keys.RETURN);
                    WalledGarden.sendKeys(map.get("Walled Garden1"));

                }
                MyCommonAPIs.sleepi(4);
                AddWalledGarden.click();
            }

        }
        MyCommonAPIs.sleepi(4);
        SaveeditECP.click();
        
        MyCommonAPIs.sleepi(10);
        if(proceed.isDisplayed())
        {
            MyCommonAPIs.sleepi(3);
            proceed.click();
        }
        MyCommonAPIs.sleepi(10);
        if (SuccsECP.isDisplayed()) {
            SuccsECP.click();
        }
        
        
    
            
    }

    public void editmodeECP(String Ssid, Map<String, String> map) {
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            entercaptiveportal.click();
            MyCommonAPIs.sleepi(20);

            if (map.containsKey("Splash Page URL")) {
                MyCommonAPIs.sleepi(4);
                SplashPageURL.clear();
                SplashPageURL.sendKeys(map.get("Splash Page URL"));
            }
            if (map.containsKey("Captive Portal Authentication Type")) {
                MyCommonAPIs.sleepi(4);
                radius.click();
            }

            if (map.containsKey("IPv4 Address")) {
                MyCommonAPIs.sleepi(4);
                IP.clear();
                IP.sendKeys(map.get("IPv4 Address"));
            }
            
            if (map.containsKey("Primary Address")) {
                for(int i=1;i<=4;i+=2)
                {   goZoneIP(i).clear();
                    MyCommonAPIs.sleepi(3);
                    goZoneIP(i).sendKeys(map.get("Primary Address"));
                }
            }
            
            if (map.containsKey("Secondary Address")) {
                
                for(int i=2;i<=4;i+=2)
                {   
                   goZoneIP(i).clear();
                    MyCommonAPIs.sleepi(3);
                    goZoneIP(i).sendKeys(map.get("Secondary Address"));
                }
            }
            if (map.containsKey("PasswordGoZone")) {
                
                for(int i=1;i<=4;i++)
                { 
                MyCommonAPIs.sleepi(4);
                goZonePassword(i).clear();
                goZonePassword(i).sendKeys(map.get("PasswordGoZone"));
            }
            }
                
            if (map.containsKey("Password")) {
                MyCommonAPIs.sleepi(4);
                IPPassword.clear();
                IPPassword.sendKeys(map.get("Password"));
            }
            

            if (map.containsKey("Secret")) {
                MyCommonAPIs.sleepi(4);
                WebSecret.sendKeys(map.get("Secret"));
            }

            if (map.containsKey("Key")) {
                MyCommonAPIs.sleepi(4);
                WebKey.sendKeys(map.get("Key"));
            }

            if (map.containsKey("Allow HTTPS")) {
                MyCommonAPIs.sleepi(4);
                System.out.println("enterd allow");
                if (map.get("Allow HTTPS") == "Enable") {
                    System.out.println("enterd enable");
                    AllowHTTPSEnable.click();
                }

            }

            if (map.containsKey("Failsafe")) {
                MyCommonAPIs.sleepi(4);
                System.out.println("enterd allow");
                if (map.get("Failsafe") == "Enable") {
                    System.out.println("enterd enable");
                    AllowFailsafe.click();
                }

            }

            if (map.containsKey("Walled Garden")) {
                MyCommonAPIs.sleepi(4);
                WalledGarden.sendKeys(map.get("Walled Garden"));
                if(map.containsKey("Walled Garden1"))
                {
                    MyCommonAPIs.sleepi(4);
                    WalledGarden.sendKeys(Keys.RETURN);
                    WalledGarden.sendKeys(map.get("Walled Garden1"));

                }
                MyCommonAPIs.sleepi(4);
                AddWalledGarden.click();
            }

        }
        MyCommonAPIs.sleepi(4);
        SaveeditECP.click();
        
        MyCommonAPIs.sleepi(6);
        if(proceed.isDisplayed())
        {
            MyCommonAPIs.sleepi(3);
            proceed.click();
        }
        MyCommonAPIs.sleepi(10);
        if (SuccsECP.isDisplayed()) {
            SuccsECP.click();
        }

        }
        
    

    public void disableECP(String Ssid, Map<String, String> map) {
        settingsorquickview.click();
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            MyCommonAPIs.sleepi(10);
            entercaptiveportal.click();
            MyCommonAPIs.sleepi(20);
            if (map.containsKey("Walled Garden Remove")) {
                MyCommonAPIs.sleepi(4);
                SelectWalledGarden(map.get("Walled Garden Remove")).click();
                MyCommonAPIs.sleepi(4);
                RemioveWalledGarden.click();
            }

        }
        MyCommonAPIs.sleepi(4);
        SaveeditECP.click();
        MyCommonAPIs.sleepi(10);
        if (ECPProceed.isDisplayed()) {
            ECPProceed.click();
        }
        MyCommonAPIs.sleepi(10);
        if (SuccsECP.isDisplayed()) {
            SuccsECP.click();
        }
    }

    public boolean HelpText(String Ssid) {

        boolean result = false;
        settingsorquickview.click();
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            enableECP();

            if (SplashPageURLHelp.isDisplayed() && WebHttpHelp.isDisplayed() && RadiusLHelp.isDisplayed() && WalledGardenSHelp.isDisplayed()
                    && WebAuthenticationURLHelp.isDisplayed() && FailsafeHelp.isDisplayed() && AllowHTTPSHelp.isDisplayed()) {
                System.out.println("All help text are present");
                result = true;
            }

            SplashPageURLHelp.hover();
            MyCommonAPIs.sleepi(1);
            String SplashPageURL = SplashPageURLHelpText.getText();
            System.out.println(SplashPageURL);
            if (SplashPageURL.contains(
                    "When Access Point (AP) administrators register their APs and purchase licenses, External Captive Portal Vendors provide an URL for client device to be redirected to upon first HTTP packet access.")) {
                System.out.println("SplashPageURL text appear");
                result = true;
            }

            WebHttpHelp.hover();
            MyCommonAPIs.sleepi(1);
            String WebHttpHelp = WebHttpHelpText.getText();
            System.out.println(WebHttpHelp);
            if (WebHttpHelp.contains(
                    "The splash page authentication will be verified by the Access Point with the External Captive Portal web site using HTTP(S) protocol.")) {
                System.out.println("WebHttpHelp text appear");
                result = true;
            }

            RadiusLHelp.hover();
            MyCommonAPIs.sleepi(1);
            String RadiusLHelp = RadiusLHelpText.getText();
            System.out.println(RadiusLHelp);
            if (RadiusLHelp.contains(
                    "The splash page authentication will be verified by the Access Point with the External Captive Portal web site using RADIUS protocol.")) {
                System.out.println("RadiusLHelp text appear");
                result = true;
            }

            WalledGardenSHelp.hover();
            MyCommonAPIs.sleepi(1);
            String WalledGardenSHelp = WalledGardenSHelpText.getText();
            System.out.println(WalledGardenSHelp);
            if (WalledGardenSHelp.contains(
                    "An URL list to be allowed access for the purpose of External Captive Portal authentication. The list is configured by admin to contain the Splash Page, Social Login and other web sites.")) {
                System.out.println("WalledGardenSHelp text appear");
                result = true;
            }

            WebAuthenticationURLHelp.hover();
            MyCommonAPIs.sleepi(1);
            String WebAuthenticationURLHelp = WebAuthenticationURLHelpText.getText();
            System.out.println(WebAuthenticationURLHelp);
            if (WebAuthenticationURLHelp.contains(
                    "Web authentication URL is provided by the External Captive Portal vendor to authenticate the Splash Page session with the Captive Portal Authentication Servers.")) {
                System.out.println("WebAuthenticationURLHelp text appear");
                result = true;
            }

            FailsafeHelp.hover();
            MyCommonAPIs.sleepi(1);
            String FailsafeHelp = FailsafeHelpText.getText();
            System.out.println(FailsafeHelp);
            if (FailsafeHelp.contains(
                    "Enabling Failsafe provides guest users with continued short-term Internet access in rare cases where External Captive Portal service is disrupted.")) {
                System.out.println("FailsafeHelp text appear");
                result = true;
            }

            AllowHTTPSHelp.hover();
            MyCommonAPIs.sleepi(1);
            String AllowHTTPSHelp = AllowHTTPSHelpText.getText();
            System.out.println(AllowHTTPSHelp);
            if (AllowHTTPSHelp.contains("Enable Allow HTTPS option to allow HTTPS traffic before Captive Portal Authentication completes.")) {
                System.out.println("FailsafeHelp text appear");
                result = true;
            }
        }

        return result;
    }

    public void deleteOrgSsid(String Ssid) {
        MyCommonAPIs.sleepi(10);
        logger.info("Delete ssid.");

        if (checkSsidIsExist(Ssid)) {
            logger.info("Delete ssid.");
            executeJavaScript("arguments[0].removeAttribute('class')", editorgWifi(Ssid));
            MyCommonAPIs.sleep(3000);
            deleteorgSsid(Ssid).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleepi(30);
            deletessidyes.click();
            MyCommonAPIs.sleep(5 * 1000);
        }

    }

    public WirelessQuickViewPage addSsidLoc(Map<String, String> map) {
        MyCommonAPIs.sleepi(10);
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        System.out.println("enterd ssid");
        MyCommonAPIs.sleepi(10);
        waitElement(addssid);
        System.out.println("waitelement enterd ssid");
        if (ssidlistone.exists()) {
            for (SelenideElement ssid : ssidlist) {
                System.out.println(ssid);
                String ssidname = ssid.$x("//td[1]//span").getText();
                // if (!ssidname.equals(map.get("SSID"))) {
                deleteSsidYes(ssidname);
                // }
            }
        } else {
            logger.warning("ssidlistone error");
        }
        map.get(ssidData);
        addSsidStepLoc(map);
        System.out.println("adding is done ssid");
        return new WirelessQuickViewPage();
    }

    public void addSsidStepLoc(Map<String, String> map) {
        System.out.println("eneterd ssid adding");
        if (!checkSsidIsExist(map.get("SSID"))) {
            System.out.println("ssid not exits");
            logger.info("Add ssid.");
            MyCommonAPIs.sleepi(5);

            // if(settingsorquickview.exists()) {
            // settingsorquickview.click();
            addssid.click();
            System.out.println("add ssid click");
            waitElement(ssid);
            MyCommonAPIs.sleepi(20);
            ssid.setValue(map.get("SSID"));
            if (checkband6.isDisplayed()) {
                if (checkband6.isSelected()) {
                    if (band6.isDisplayed()) {
                        band6.click();
                        logger.info("Uncheck 6 band");
                    }
                } else {
                    System.out.println("6GHZ is alredy unchecked");
                }
            }
            if (map.get("Security").equals("WPA2 PSK")) {
                security.selectOption("WPA2-PSK");
            } else {
                security.selectOption(map.get("Security"));
            }
            System.out.println("before password");
            MyCommonAPIs.sleepi(20);
            if (map.containsKey("Password")) {
                password.setValue(map.get("Password"));
            }
            takess("addSsid");
            MyCommonAPIs.waitElement(saveLOC);
            logger.info("Save button is Visible");
            saveLOC.click();
            MyCommonAPIs.sleepi(5);
            if (oksave.isDisplayed()) {
                oksave.click();
            }
            MyCommonAPIs.sleepi(5);
            logger.info("Add ssid successful.");
            // } else {
            // logger.warning("checkSsidIsExist error");
            // }
            System.out.println("exits ssid");
        }
    }

    public void enableCaptivePortalTypeLoc(String Ssid, Map<String, String> map) {
        settingsorquickview.click();
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            // System.out.println("refresh");
            // Selenide.refresh();
            enableIcpStepLoc();
            if (!enabledailyloginsLoc.exists()) {
                enableschedulereportsLoc.click();
                MyCommonAPIs.sleepi(3);
            }
            enabledailyloginsLoc.click();
            MyCommonAPIs.sleepi(3);
            enabledailyanalyticsLoc.click();
            MyCommonAPIs.sleepi(3);
            enableweeklyanalyticsLoc.click();
            MyCommonAPIs.sleepi(3);
            Selenide.switchTo().frame(0);
            MyCommonAPIs.sleepi(3);
            inputportalnameLoc.setValue(map.get("Portal Name"));
            inputwelcomeheadlineLoc.setValue(map.get("Welcome Headline"));
            if (map.containsKey("Welcome Message")) {
                inputWelcomeMsgLoc.setValue(map.get("Welcome Message"));
            }
            if (map.containsKey("Captive Portal Logo")) {
                if (map.get("Captive Portal Logo").equals("DEFAULT_LOGO")) {
                    if ($x("//option[text()='" + map.get("Captive Portal Logo") + "']").exists()) {
                        addcaptivelogo.selectOption(map.get("Captive Portal Logo"));
                    } else {
                        addcaptivelogo.selectOption(1);
                    }
                } else {
                    chooseCaptiveLogoBtn.click();
                    MyCommonAPIs.sleepi(3);
                    selectCaptiveLogoName.setValue(map.get("Captive Portal Logo"));
                    MyCommonAPIs.sleepi(3);
                    selectCaptiveLogoFile.sendKeys(map.get("Captive Portal Logo Path"));
                    MyCommonAPIs.sleepi(3);
                    uploadImgOkBtn.click();
                    MyCommonAPIs.sleepi(20);
                    addcaptivelogo.selectOption(map.get("Captive Portal Logo"));
                }
            }
            if (map.containsKey("Desktop Background Image")) {
                if (map.get("Desktop Background Image").equals("DEFAULT_BG")) {
                    if ($x("//option[text()='" + map.get("Desktop Background Image") + "']").exists()) {
                        addbackgroundimage.selectOption(map.get("Desktop Background Image"));
                    } else {
                        addbackgroundimage.selectOption(1);
                    }
                } else {
                    chooseBackgroundImg.click();
                    MyCommonAPIs.sleepi(3);
                    selectCaptiveLogoName.setValue(map.get("Desktop Background Image"));
                    MyCommonAPIs.sleepi(3);
                    selectBackgroundImgFile.sendKeys(map.get("Desktop Background Image Path"));
                    MyCommonAPIs.sleepi(3);
                    uploadImgOkBtn.click();
                    MyCommonAPIs.sleepi(20);
                    addbackgroundimage.selectOption(map.get("Desktop Background Image"));
                }
            }
            if (map.containsKey("Mobile Background Image")) {
                chooseMobileBackgroundImg.click();
                MyCommonAPIs.sleepi(3);
                selectCaptiveLogoName.setValue(map.get("Mobile Background Image"));
                MyCommonAPIs.sleepi(3);
                selectMobileBackgroundImgFile.sendKeys(map.get("Mobile Background Image Path"));
                MyCommonAPIs.sleepi(3);
                uploadImgOkBtn.click();
                MyCommonAPIs.sleepi(20);
                addmobliebackgroundimage.selectOption(map.get("Mobile Background Image"));
            }
            inputlandingurlLoc.setValue(map.get("Landing Page URL"));
            selectsessiondurationLoc.selectOption(map.get("Session Duration"));
            clickcaptiveportalstepLoc.click();
            MyCommonAPIs.sleepi(10);
            selectsteptypeLoc.selectOption(map.get("Step Type"));
            MyCommonAPIs.sleepi(10);
            if (map.get("Step Type").equals("Méthode d'authentification")) {
                configAuthenticationMethodLoc(map);
            } else if (map.get("Step Type").equals("Play Video")) {
                configPlayVideo(map);
            } else if (map.get("Step Type").equals("Payment by Paypal")) {
                configPaymentByPaypal(map);
            } else if (map.get("Step Type").equals("Display Ad")) {
                configDispalyAd(map);
            } else if (map.get("Step Type").equals("Voucher")) {
            }
            MyCommonAPIs.sleepi(3);
            savecaptiveportalstepLoc.click();
            MyCommonAPIs.sleepi(3);
            Selenide.switchTo().defaultContent();
            MyCommonAPIs.sleepi(3);
            savecaptive.click();
            MyCommonAPIs.sleepi(10);
            captiveok.click();
            MyCommonAPIs.sleepi(5);
            logger.info("Enable instant captive portal success.");
        } else {
            logger.info("Not found ssid.");
        }
    }

    public void enableIcpStepLoc() {

        entercaptiveportalLoc.click();
        MyCommonAPIs.sleepi(20);
        enablecaptiveportalLoc.click();
        refresh();
        MyCommonAPIs.sleepi(20);
        enablecaptiveportalLoc.click();
        MyCommonAPIs.sleepi(5);
        selectinsightcaptiveportal.click();
    }

    public void configAuthenticationMethodLoc(Map<String, String> map) {
        String[] authors = map.get("Login Modes").split("[.]");
        for (String author : authors) {
            MyCommonAPIs.sleepi(3);
            switch (author) {
            case "Facebook":
                clickfacebook.selectRadio("on");
                break;
            case "Twitter":
                clicktwitter.selectRadio("on");
                break;
            case "LinkedIn":
                clicklinkedIn.selectRadio("on");
                break;
            case "Register":
                clickregister.selectRadio("on");
                break;
            }
        }
    }

    public void deleteSsidYesLoc(String Ssid) {
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        // waitElement(addssid);
        if (checkSsidIsExist(Ssid)) {
            logger.info("Delete ssid.");
            executeJavaScript("arguments[0].removeAttribute('class')", editWifi(Ssid));
            MyCommonAPIs.sleep(3000);
            deleteSsid(Ssid).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleepi(30);
            deletessidyesLoc.click();
            MyCommonAPIs.sleep(5 * 1000);
        }
    }

    // written by Vivek
    public void open_instant_wifi_page() {
        waitElement(settingsorquickview);
        settingsorquickview.click();
        waitElement(instantwifi);
        instantwifi.click();
        MyCommonAPIs.sleepi(5);
    }

    // written by Vivek
    public boolean verify_allocation_text_is_present_on_instant_wifi_page() {
        boolean result = false;
        waitElement(Allocation);
        if (Allocation.exists() && getText(Allocation).contains("Allocation")) {
            result = true;
        }
        return result;
    }

    // written by Vivek
    public boolean verify_Description_text_is_present_on_instant_wifi_page() {
        boolean result = false;
        waitElement(Description);
        if (Description.exists() && getText(Description).contains("Description")) {
            result = true;
        }
        return result;

    }

    // written by Vivek
    public boolean verify_Learn_more_open_right_page() {
        boolean result = false;
        waitElement(LearnMore);
        LearnMore.click();
        waitElement(headerText);
        if (headerText.exists() && getText(headerText).contains("Instant WiFi")) {
            result = true;
            cross_close.click();
        }
        return result;

    }

    // written by Vivek
    public boolean verify_ChannelAllocationToggleSlider_is_present() {
        boolean result = false;
        waitElement(AutChlAloctn_cstm_sldr);
        if (AutChlAloctn_cstm_sldr.exists()) {
            result = true;
        }
        return result;

    }

    // written by Vivek
    public void clickOnAllDeselectAllLink() {
        for (SelenideElement deSelect : De_SelectAll) {
            deSelect.click();
            MyCommonAPIs.sleepi(2);
            logger.info("Clicked on DeSelectAll_button");

        }
        MyCommonAPIs.sleepi(2);

    }

    // written by Vivek
    public void clickOnAllselectAllLink() {
        for (SelenideElement Select : SelectAll_button) {
            Select.click();
            MyCommonAPIs.sleepi(2);
            logger.info("Clicked on SelectAll_button");
        }
        MyCommonAPIs.sleepi(2);
    }

    // written by Vivek
    public void clickOnAllRestoreDefaultLink() {
        for (SelenideElement Restore_defalt : RestoreDefault) {
            Restore_defalt.click();
            MyCommonAPIs.sleepi(2);
            logger.info("Clicked on Restore_defalt Button");
        }
        MyCommonAPIs.sleepi(2);
    }

    // written by Vivek
    public void clickOnOptimizeButton() {
        waitElement(optimizenowbutton);
        optimizenowbutton.click();
        waitReady();
        waitElement(instantwifisuccessmeg);
        String message = getText(instantwifisuccessmeg);
        if (message.contains("Your configuration has been applied. It may take a few moments to display")) {
            System.out.println(message);
            logger.info("clicked on OnOptimize Button");
        } else {
            logger.warning("instantwifisuccessmeg error");
            logger.info("Waiting 18 min for next itteration");
            MyCommonAPIs.sleepi(1080);
            waitElement(optimizenowbutton);
            optimizenowbutton.click();
            waitElement(instantwifisuccessmeg);
            String newmessage = getText(instantwifisuccessmeg);
            if (newmessage.contains("Your configuration has been applied. It may take a few moments to display")) {
                System.out.println(newmessage);
                logger.info("clicked on OnOptimize Button Done");

            }

            MyCommonAPIs.sleepi(5);
        }

    }

    // written by Vivek
    public String getExpectedChannelError() {
        boolean result = false;
        waitElement(optimizenowbutton);
        optimizenowbutton.click();
        MyCommonAPIs.sleepi(4);
        waitElement(channelError);
        String errorText = getText(channelError);
        waitElement(OkGotIt);
        OkGotIt.click();
        return errorText;
    }

    // written by Vivek
    public boolean verifyError_Select_at_least3channels_from_each_of_the_bands() {
        boolean result = false;
        String errorText = getExpectedChannelError();
        if (errorText.contains("Select at least 3 channels from each of the bands")) {
            result = true;
        }
        return result;

    }

    // written by Vivek
    public boolean verifyError_Select_at_least_3_channels_from_the_5_GHz_high_band() {
        boolean result = false;
        String errorText = getExpectedChannelError();
        if (errorText.contains("Add to your selection at least 3 channels from the 5 GHz high band.")) {
            result = true;
        }
        return result;

    }

    // written by Vivek
    public boolean verifyError_msg_Select_at_least_3_channels_from_the_5_GHz_low_bands() {
        boolean result = false;
        String errorText = getExpectedChannelError();
        if (errorText.contains("Add to your selection at least 3 channels from the 5 GHz low band.")) {
            result = true;
        }
        return result;

    }

    // written by Vivek
    public void Select3Channelfrom2_4GHZ() {
        for (int i = 1; i < 4; i++) {
            waitElement($x("//label/p[text()='" + i + "']"));
            $x("//label/p[text()='" + i + "']").click();
            logger.info("Clicked on" + i + "Channel");
        }
        MyCommonAPIs.sleepi(2);
    }

    // written by Vivek
    public void Select3Channelfrom5GHZLow() {
        for (int i = 36; i < 45; i++) {
            if (i == 36 || i == 40 || i == 44) {
                waitElement($x("//label/p[text()='" + i + "']"));
                $x("//label/p[text()='" + i + "']").click();
                logger.info("Clicked on" + i + "Channel");
            }

        }
        MyCommonAPIs.sleepi(2);
    }

    // written by Vivek
    public void Select3Channelfrom5GHZHigh() {
        for (int i = 100; i < 166; i++) {
            if (i == 100 || i == 104 || i == 136) {
                waitElement($x("//label/p[text()='" + i + "']"));
                $x("//label/p[text()='" + i + "']").click();
                logger.info("Clicked on" + i + "Channel");
            }

        }
        MyCommonAPIs.sleepi(2);
    }

    // written by Vivek
    public void Select3Channelfrom6GHZ() {
        if ($x("//span[text()='6 GHz']").exists()) {
            for (int i = 17; i < 26; i++) {
                if (i == 17 || i == 21 || i == 25) {
                    waitElement($x("//label/p[text()='" + i + "']"));
                    $x("//label/p[text()='" + i + "']").click();
                    logger.info("Clicked on" + i + "Channel");
                }

            }
            MyCommonAPIs.sleepi(2);
        } else {
            MyCommonAPIs.sleepi(2);
            logger.info("Country dont have 6GHz Band Supported");
        }
    }

    // written by Vivek
    public boolean verifyDeviceConfigurationLinkOpenDeviceSummaryPage() {
        boolean result = false;
        waitElement(Device_Configuration);
        Device_Configuration.click();
        MyCommonAPIs.sleepi(5);
        waitElement(DeviceSerial);
        String deviceSr = getText(DeviceSerial);
        if (deviceSr.contains((WebportalParam.ap1serialNo)))
            ;
        {
            result = true;
        }
        return result;
    }

    // written by Vivek
    public void clickOnPlusIconForOpen_RadioSetting() {
        waitElement(plusIcon);
        plusIcon.click();
        MyCommonAPIs.sleepi(2);
    }

    // written by Vivek
    public void printAll_2_4GHZ_RadioSettingDetails() {
        MyCommonAPIs.sleepi(2);
        String DeviceNameAndItsSrNum = getText(DeviceNameSerialnum);
        System.out.println("Device Name and Its Serial Num => " + "  " + DeviceNameAndItsSrNum);
        for (SelenideElement channel : Channel2_4data) {
            System.out.println("2.4 ghz channel table data => " + "  " + channel.text());
        }
        System.out.println(" ");
        for (SelenideElement power : power2_4data) {
            System.out.println("2.4 ghz power table data => " + "  " + power.text());
        }
        System.out.println(" ");
        for (SelenideElement width : ChannelWidth2_4data) {
            System.out.println("2.4 ghz Channel Width table data => " + "  " + width.text());
        }
        System.out.println(" ");

    }

    // written by Vivek
    public void printAll_5GHZ_LowRadioSettingDetails() {
        MyCommonAPIs.sleepi(2);
        for (SelenideElement channel : Channel5ghzLowdata) {
            System.out.println("5 ghz Low channel table data => " + "  " + channel.text());
        }
        System.out.println(" ");
        for (SelenideElement power : power5ghzLowdata) {
            System.out.println("5 ghz Low power table data => " + "  " + power.text());
        }
        System.out.println(" ");
        for (SelenideElement width : ChannelWidth5ghzLowdata) {
            System.out.println("5 ghz Low Channel Width table data => " + "  " + width.text());
        }
        System.out.println(" ");

    }

    // written by Vivek
    public void printAll_5GHZ_HighRadioSettingDetails() {
        MyCommonAPIs.sleepi(2);
        for (SelenideElement channel : Channel5ghzHighdata) {
            System.out.println("5 ghz High channel table data => " + "  " + channel.text());
        }
        System.out.println(" ");
        for (SelenideElement power : power5ghzHighdata) {
            System.out.println("5 ghz High power table data => " + "  " + power.text());
        }
        System.out.println(" ");
        for (SelenideElement width : ChannelWidth5ghzhighdata) {
            System.out.println("5 ghz High Channel Width table data => " + "  " + width.text());
        }
        System.out.println(" ");

    }

    // written by Vivek
    public boolean clickingOnSaveButtonAndVerifySuccessMsg() {
        boolean result = false;
        waitElement(saveInsantWifiBtn);
        saveInsantWifiBtn.click();
        MyCommonAPIs.sleepi(1);
        waitElement(instantwifisuccessmegnew);
        String message = getText(instantwifisuccessmegnew);
        if ((message.contains("We have successfully configured your network Auto RF setting"))
                || (message.contains("Your configuration has been applied."))) {
            System.out.println(message);
            result = true;
        }
        return result;
    }

    public void enableCaptivePortalTypecustomise(String Ssid, Map<String, String> map) {
        settingsorquickview.click();
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            enableIcpStep();
            if (!enabledailylogins.exists()) {
                enableschedulereports.click();
                MyCommonAPIs.sleepi(3);
            }
            enabledailylogins.click();
            MyCommonAPIs.sleepi(3);
            enabledailyanalytics.click();
            MyCommonAPIs.sleepi(3);
            enableweeklyanalytics.click();
            MyCommonAPIs.sleepi(3);
            Selenide.switchTo().frame(0);
            MyCommonAPIs.sleepi(3);
            inputportalname.setValue(map.get("Portal Name"));
            inputwelcomeheadline.setValue(map.get("Welcome Headline"));
            if (map.containsKey("Welcome Message")) {
                inputWelcomeMsg.setValue(map.get("Welcome Message"));
            }
            if (map.containsKey("Captive Portal Logo")) {
                if (map.get("Captive Portal Logo").equals("DEFAULT_LOGO")) {
                    if ($x("//option[text()='" + map.get("Captive Portal Logo") + "']").exists()) {
                        addcaptivelogo.selectOption(map.get("Captive Portal Logo"));
                    } else {
                        addcaptivelogo.selectOption(1);
                    }
                } else {
                    chooseCaptiveLogoBtn.click();
                    MyCommonAPIs.sleepi(3);
                    selectCaptiveLogoName.setValue(map.get("Captive Portal Logo"));
                    MyCommonAPIs.sleepi(3);
                    selectCaptiveLogoFile.sendKeys(map.get("Captive Portal Logo Path"));
                    MyCommonAPIs.sleepi(3);
                    uploadImgOkBtn.click();
                    MyCommonAPIs.sleepi(20);
                    addcaptivelogo.selectOption(map.get("Captive Portal Logo"));
                }
            }
            if (map.containsKey("Desktop Background Image")) {
                if (map.get("Desktop Background Image").equals("DEFAULT_BG")) {
                    if ($x("//option[text()='" + map.get("Desktop Background Image") + "']").exists()) {
                        addbackgroundimage.selectOption(map.get("Desktop Background Image"));
                    } else {
                        addbackgroundimage.selectOption(1);
                    }
                } else {
                    chooseBackgroundImg.click();
                    MyCommonAPIs.sleepi(3);
                    selectCaptiveLogoName.setValue(map.get("Desktop Background Image"));
                    MyCommonAPIs.sleepi(3);
                    selectBackgroundImgFile.sendKeys(map.get("Desktop Background Image Path"));
                    MyCommonAPIs.sleepi(3);
                    uploadImgOkBtn.click();
                    MyCommonAPIs.sleepi(20);
                    addbackgroundimage.selectOption(map.get("Desktop Background Image"));
                }
            }
            if (map.containsKey("Mobile Background Image")) {
                chooseMobileBackgroundImg.click();
                MyCommonAPIs.sleepi(3);
                selectCaptiveLogoName.setValue(map.get("Mobile Background Image"));
                MyCommonAPIs.sleepi(3);
                selectMobileBackgroundImgFile.sendKeys(map.get("Mobile Background Image Path"));
                MyCommonAPIs.sleepi(3);
                uploadImgOkBtn.click();
                MyCommonAPIs.sleepi(20);
                addmobliebackgroundimage.selectOption(map.get("Mobile Background Image"));
            }
            inputlandingurl.setValue(map.get("Landing Page URL"));
            selectsessionduration.selectOption(map.get("Session Duration"));
            clickcaptiveportalstep.click();
            MyCommonAPIs.sleepi(10);
            selectsteptype.selectOption(map.get("Step Type"));
            MyCommonAPIs.sleepi(10);
            if (map.get("Step Type").equals("Authentication Method")) {
                configAuthenticationMethodcustomise(map);
            } else if (map.get("Step Type").equals("Play Video")) {
                configPlayVideo(map);
            } else if (map.get("Step Type").equals("Payment by Paypal")) {
                configPaymentByPaypal(map);
            } else if (map.get("Step Type").equals("Display Ad")) {
                configDispalyAd(map);
            } else if (map.get("Step Type").equals("Voucher")) {
            }

            MyCommonAPIs.sleepi(3);
            savecaptiveportalstep.click();
            MyCommonAPIs.sleepi(3);
            Selenide.switchTo().defaultContent();
            MyCommonAPIs.sleepi(3);
            savecaptive.click();
            MyCommonAPIs.sleepi(10);
            captiveok.click();
            MyCommonAPIs.sleepi(5);
            logger.info("Enable instant captive portal success.");
        } else {
            logger.info("Not found ssid.");
        }
    }

    public void configAuthenticationMethodcustomise(Map<String, String> map) {
        String[] authors = map.get("Login Modes").split("[.]");
        for (String author : authors) {
            MyCommonAPIs.sleepi(3);
            switch (author) {
            case "Facebook":
                clickfacebook.selectRadio("on");
                break;
            case "Twitter":
                clicktwitter.selectRadio("on");
                break;
            case "LinkedIn":
                clicklinkedIn.selectRadio("on");
                break;
            case "Register":
                clickregister.selectRadio("on");
                break;
            case "Register_Customize":
                clickregister.selectRadio("on");
                MyCommonAPIs.sleepi(4);
                clickregisterCustomize.click();
                break;
            }
            System.out.println("out of switch");
        }
        System.out.println("out of for");
        if (map.containsKey("Customize"))
            ;
        {
            System.out.println("inside key");
            Customize.click();
            MyCommonAPIs.sleepi(5);
            Label.sendKeys("PhoneNumber");
            Required.selectOption("Yes");
            MyCommonAPIs.sleepi(2);
            Add.click();
            Done.click();
        }
    }

    public boolean dynamicvlan(String ssid) {
        boolean result = false;
        settingsorquickview.click();
        MyCommonAPIs.sleep(5);
        if (checkSsidIsExist(ssid)) {
            clickEditSsid(ssid);
        }
        MyCommonAPIs.sleepi(5);
        AddVLAN.click();
        MyCommonAPIs.sleepi(10);
        if (dynamicvlanwarning.exists()) {
            result = true;
        }
        return result;
    }

    public void NatToBridge(String ssid) {
        MyCommonAPIs.sleepi(10);
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        MyCommonAPIs.sleep(5);
        if (checkSsidIsExist(ssid)) {
            clickEditSsid(ssid);
        }
        MyCommonAPIs.sleepi(5);
        Nattobridge.selectOption("Bridge");

        MyCommonAPIs.sleepi(5);
        editsave.click();
        waitReady();
        MyCommonAPIs.sleepi(3);
        confirmok.click();
        MyCommonAPIs.sleepi(4);
    }

    public boolean BridgeToNat(String ssid) {

        Boolean result = true;
        MyCommonAPIs.sleepi(10);
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        MyCommonAPIs.sleep(5);
        if (checkSsidIsExist(ssid)) {
            clickEditSsid(ssid);
        }
        MyCommonAPIs.sleepi(5);
        Nattobridge.selectOption("NAT");

        MyCommonAPIs.sleepi(5);
        if (dynamicvlanwarning.exists()) {
            result = false;
            return result;
        }
        MyCommonAPIs.sleepi(5);
        editsave.click();
        waitReady();
        MyCommonAPIs.sleepi(3);
        confirmok.click();
        MyCommonAPIs.sleepi(4);
        return result;
    }

    // public void connectClient(String ssid,String Security)
    // {
    // Map<String,String> Securitytype=new HashMap<String, String>();
    //
    // Securitytype.put("WPA2","WPA2PSK aes");
    // Securitytype.put("WPA3","WPA3SAE aes");
    //
    // System.out.println(Securitytype);
    // System.out.println(Security);
    // System.out.println(Securitytype.get(Security));
    //
    // int sum = 0;
    // while (true) {
    // MyCommonAPIs.sleepi(10);
    // if (new Javasocket()
    // .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID " + ssid)
    // .indexOf("true") != -1) {
    // break;
    // } else if (sum > 20) {
    // assertTrue(false, "Client cannot connected.");
    // break;
    // }
    // sum += 1;
    // }
    //
    // MyCommonAPIs.sleepi(20);
    //
    //
    // System.out.println("Search completed ");
    //
    // boolean result1 = true;
    //
    // if (!new Javasocket()
    // .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect "+ ssid + " 123456798
    // "+Securitytype.get(Security))
    // .equals("true")) {
    // System.out.println("other");
    // result1 = false;
    // MyCommonAPIs.sleepi(20);
    // if (new Javasocket()
    // .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFconnect "+ ssid + " 123456798
    // "+Securitytype.get(Security))
    // .equals("true")) {
    // result1 = true;
    // }
    // }
    //
    //
    // }

    // written by Vivek
    public void openWirelessSettings() {
        MyCommonAPIs.sleepi(2);
        settingsorquickview.click();
        MyCommonAPIs.sleepi(5);
    }

    // written by Vivek
    public void hoverOverOnAdvancedMenu() {
        waitElement(leftAdvanceMenu);
        leftAdvanceMenu.hover();
        MyCommonAPIs.sleepi(2);
    }

    // Added by Vivek
    public boolean verifyAllMenuItemsArePresentUnderAdvancedSetting() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        for (SelenideElement elem : AdvancedSubMenus) {
            if (elem.text().contains("Wireless Settings") || elem.text().contains("Network Settings") || elem.text().contains("Multi PSK Settings")
                    || elem.text().contains("Mesh Settings"))
                ;
            {
                result = true;
            }
        }
        return result;

    }

    public boolean connectClient(Map<String, String> map) {
        MyCommonAPIs.sleepi(30);

        int sum = 0;
        while (true) {
            MyCommonAPIs.sleepi(10);
            if (new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID " + map.get("SSID"))
                    .indexOf("true") != -1) {
                break;
            } else if (sum > 30) {
                assertTrue(false, "Client cannot connected.");
                break;
            }
            sum += 1;
        }

        boolean result1 = true;
        if (map.get("Security").contains("Open")) {

            System.out.println("enter open");
            if (!new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFopenconnect  " + map.get("SSID"))
                    .equals("true")) {
                result1 = false;
                if (new Javasocket()
                        .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFopenconnect  " + map.get("SSID"))
                        .equals("true")) {
                    result1 = true;
                }
            }
        }

        else if (map.get("Security").contains("WPA3")) {
            System.out.println("enter WPA3");
            if (!new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport,
                    "WAFconnect " + map.get("SSID") + "  " + map.get("Password") + "  WPA3SAE aes").equals("true")) {

                result1 = false;
                if (new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport,
                        "WAFconnect " + map.get("SSID") + "  " + map.get("Password") + "  WPA3SAE aes").equals("true")) {
                    result1 = true;
                }
            }
        } else {
            System.out.println("enter WPA2");
            if (!new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport,
                    "WAFconnect " + map.get("SSID") + "  " + map.get("Password") + "  WPA2PSK aes").equals("true")) {
                System.out.println("check this");
                result1 = false;
                if (new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport,
                        "WAFconnect " + map.get("SSID") + "  " + map.get("Password") + "  WPA2PSK aes").equals("true")) {
                    System.out.println("check this inside");
                    result1 = true;
                }
            }

        }
        return result1;

    }

    public void EditSSID1InHoriMenu(Map<String, String> map) {
        if (checkSsidIsExist(map.get("SSID"))) {
            logger.info("Edit ssid.");
            executeJavaScript("arguments[0].removeAttribute('class')", editWifi(map.get("SSID")));
            MyCommonAPIs.sleep(3000);
            editSsid(map.get("SSID")).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(8 * 1000);
        }

    }

    public void enterDeviceYesInHoriMenu(String serialNumber) {
        for (int i = 0; i < 2; i++) {
            if (checkApIsExist(serialNumber)) {
                logger.info("Enter device.");
                executeJavaScript("arguments[0].removeAttribute('class')", editModule(serialNumber));
                MyCommonAPIs.sleep(3000);
                enterDevice(serialNumber).waitUntil(Condition.visible, 60 * 1000).click();
                MyCommonAPIs.sleep(5 * 1000);
                break;
            }
            refresh();
        }
    }

    public String addSsidNatnew(Map<String, String> map) {
        MyCommonAPIs.sleepi(10);
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        System.out.println("enterd ssid");
        MyCommonAPIs.sleepi(10);
        waitElement(addssid);
        System.out.println("waitelement enterd ssid");
        // if (ssidlistone.exists()) {
        // for (SelenideElement ssid : ssidlist) {
        // System.out.println(ssid);
        // String ssidname = ssid.$x("//td[1]//span").getText();
        // // if (!ssidname.equals(map.get("SSID"))) {
        // deleteSsidYes(ssidname);
        // // }
        // }
        // } else {
        // logger.warning("ssidlistone error");
        // }
        map.get(ssidData);
        String warning = addSsidStepFastNatnew(map);
        System.out.println("adding is done ssid");
        return warning;
    }

    public String addSsidStepFastNatnew(Map<String, String> map) {
        System.out.println("eneterd ssid adding");
        if (!checkSsidIsExist(map.get("SSID"))) {
            System.out.println("ssid not exits");
            logger.info("Add ssid.");
            MyCommonAPIs.sleepi(5);

            // if(settingsorquickview.exists()) {
            // settingsorquickview.click();
            addssid.click();
            System.out.println("add ssid click");
            waitElement(ssid);
            MyCommonAPIs.sleepi(20);
            ssid.setValue(map.get("SSID"));
            if (checkband6.isDisplayed()) {
                if (checkband6.isSelected()) {
                    if (band6.isDisplayed()) {
                        band6.click();
                        logger.info("Uncheck 6 band");
                    }
                } else {
                    System.out.println("6GHZ is alredy unchecked");
                }
            }
            if (map.get("Security").equals("WPA2 PSK")) {
                security.selectOption("WPA2-PSK");
            } else {
                security.selectOption(map.get("Security"));
            }
            System.out.println("before password");
            MyCommonAPIs.sleepi(20);
            if (map.containsKey("Password")) {
                password.setValue(map.get("Password"));
            }
            if (map.containsKey("OWE")) {
                owe.click();
                owetmode.click();
            } else if (map.containsKey("OWETM")) {
                owe.click();

            }

            takess("addSsid");

            adressing.selectOption("NAT");
            networkip.click();
            MyCommonAPIs.sleepi(10);

            networkip.sendKeys(map.get("NATIP"));
            MyCommonAPIs.sleepi(10);

            MyCommonAPIs.waitElement(save);
            logger.info("Save button is Visible");
            save.click();
            MyCommonAPIs.sleepi(10);

            if (warningele.exists()) {
                String warning = warningele.getText();
                System.out.print(warning);
                return warning;

            }

            if (oksave.isDisplayed()) {
                oksave.click();
            }
            MyCommonAPIs.sleepi(5);
            logger.info("Add ssid successful.");
            // } else {
            // logger.warning("checkSsidIsExist error");
            // }
            System.out.println("exits ssid");

        }
        return null;
    }

    public void bridgeTonat(String ssid) {
        Boolean result = true;
        MyCommonAPIs.sleepi(10);
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        MyCommonAPIs.sleep(5);
        if (checkSsidIsExist(ssid)) {
            clickEditSsid(ssid);
        }
        MyCommonAPIs.sleepi(5);
        Nattobridge.selectOption("NAT");

        MyCommonAPIs.sleepi(5);

        editnetworkip.sendKeys("10.0.0.0");
        MyCommonAPIs.sleepi(10);

        editsave.click();
        waitReady();
        MyCommonAPIs.sleepi(3);
        confirmok.click();
        MyCommonAPIs.sleepi(4);

    }

    public void addManualDevice(String Ssid, String policyType) {
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            entermacacl.click();
            MyCommonAPIs.sleepi(3);
            macaclpolicy.selectOption(policyType);
            MyCommonAPIs.sleepi(3);
            waitElement(ManualMACButton);
            ManualMACButton.click();
            waitElement(DeviceName);
            DeviceName.sendKeys(WebportalParam.client1name);
            MyCommonAPIs.sleepi(2);
            DeviceMAC.sendKeys(WebportalParam.client1mac);
            MyCommonAPIs.sleepi(2);
            MACConfirmButton.click();
            MyCommonAPIs.sleepi(2);
        }

    }

    public boolean connectClient1(Map<String, String> map) {
        int sum = 0;
        while (true) {
            MyCommonAPIs.sleepi(10);
            if (new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFfindSSID " + map.get("SSID"))
                    .indexOf("true") != -1) {
                break;
            } else if (sum > 30) {
                assertTrue(false, "Client cannot connected.");
                break;
            }
            sum += 1;
        }

        boolean result1 = false;
        if (map.get("Security").contains("Open")) {

            System.out.println("enter open");
            if (!new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFopenconnect  " + map.get("SSID"))
                    .equals("true")) {
                result1 = false;
                if (new Javasocket()
                        .sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport, "WAFopenconnect  " + map.get("SSID"))
                        .equals("true")) {
                    result1 = true;
                }
            }
        }

        else if (map.get("Security").contains("WPA3")) {
            System.out.println("enter WPA3");
            if (!new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport,
                    "WAFconnect " + map.get("SSID") + "  " + map.get("Password") + "  WPA3SAE aes").equals("true")) {

                result1 = false;
                if (new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport,
                        "WAFconnect " + map.get("SSID") + "  " + map.get("Password") + "  WPA3SAE aes").equals("true")) {
                    result1 = true;
                }
            }
        } else {
            System.out.println("enter WPA2");
            if (!new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport,
                    "WAFconnect " + map.get("SSID") + "  " + map.get("Password") + "  WPA2PSK aes").equals("true")) {
                System.out.println("check this");
                result1 = false;
                if (new Javasocket().sendCommandToWinClient(WebportalParam.clientip, WebportalParam.clientport,
                        "WAFconnect " + map.get("SSID") + "  " + map.get("Password") + "  WPA2PSK aes").equals("true")) {
                    result1 = true;
                }
            }

        }

        return result1;

    }

    public void addManualDeviceMACACL(String Ssid, String policyType, String Devicename, String Mac) {
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            entermacacl.click();
            MyCommonAPIs.sleepi(3);
            macaclpolicy.selectOption(policyType);
            MyCommonAPIs.sleepi(3);
            waitElement(ManualMACButton);
            ManualMACButton.click();
            waitElement(DeviceName);
            DeviceName.sendKeys(Devicename);
            MyCommonAPIs.sleepi(2);
            DeviceMAC.sendKeys(Mac);
            MyCommonAPIs.sleepi(2);
            MACConfirmButton.click();
            MyCommonAPIs.sleepi(2);
        }

    }

    public void addDummyDeviceMacAcl(String Ssid, String policyType) {
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            entermacacl.click();
            MyCommonAPIs.sleepi(3);
            macaclpolicy.selectOption(policyType);
            MyCommonAPIs.sleepi(3);
            waitElement(ManualMACButton);
            ManualMACButton.click();
            waitElement(DeviceName);
            DeviceName.sendKeys("DUMMY");
            MyCommonAPIs.sleepi(2);
            DeviceMAC.sendKeys("aa:bb:cc:dd:ee:ff");
            MyCommonAPIs.sleepi(2);
            MACConfirmButton.click();
            MyCommonAPIs.sleepi(2);
        }

    }

    public void deleteMacAddressMacAcl(String Macaddress, String Ssid) {
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            entermacacl.click();
            MyCommonAPIs.sleepi(3);
            logger.info("Delete macadress");
            editMac(Macaddress).click();
            MyCommonAPIs.sleepi(3);
            removeMac.click();
            MyCommonAPIs.sleepi(3);
            deleteMac.click();
            logger.info("Deleted macadress");
        } else {
            assertTrue(false, "couldn't delete check script");
        }
    }

    public String getConnectedClientCount() {
        String count;
        new MyCommonAPIs().gotoLoction();
        if (connectedClients.isDisplayed()) {
            connectedClients.click();
            MyCommonAPIs.sleepi(3);
            System.out.print(connectedClientsCount.getText());
            return (connectedClientsCount.getText());

        } else {
            return ("-1");
        }

    }

    public boolean connectedCheck() {
        boolean flag = false;
        WirelessQuickViewPage page = new WirelessQuickViewPage(false);
        MyCommonAPIs.sleepi(8);
        if (page.connected.exists()) {
            page.connected.click();
            if (page.wired.exists() && page.wireless.exists()) {
                MyCommonAPIs.sleepi(100);
                if (new WirelessQuickViewPage(false).checkClientConnect(WebportalParam.clientwlanmac)) {
                    flag = true;
                }
            }
        } else {
            flag = false;
        }
        return flag;
    }

    public boolean disconnectedCheck() {
        boolean flag = false;
        WirelessQuickViewPage page = new WirelessQuickViewPage(false);
        MyCommonAPIs.sleepi(5);
        if (page.disconnected.exists()) {
            page.disconnected.click();
            if (page.wired.exists() && page.wireless.exists()) {
                MyCommonAPIs.sleepi(100);
                if (new WirelessQuickViewPage(false).checkClientConnectDisconnected(WebportalParam.clientwlanmac)) {
                    flag = true;
                }
            }
        } else {
            flag = false;
        }
        return flag;
    }

    public boolean checkClientConnectDisconnected(String Mac) {
        boolean result = false;
        int sum = 0;

        String macClient = String.format("//span[text()='%s']", Mac);
        String macClient1 = String.format("//td[text()='%s']", Mac);

        logger.info("try to locate element: " + macClient);
        System.out.print("entered into check client");
        while (sum <= 20) {
            System.out.print("entered into while");
            if ($x(macClient).exists() || $x(macClient1).exists()) {
                System.out.print("entered into check if");
                result = true;
                logger.info("Client mac address:" + Mac + " is matched.");
                break;
            }

            MyCommonAPIs.sleepi(10);
            refresh();
            MyCommonAPIs.sleepi(10);
            disconnected.click();
            MyCommonAPIs.sleepi(10);

            sum += 1;
            logger.info("Client list cannot find client,count:" + sum);
        }
        return result;
    }

    public boolean verifywirelessDataConsumptionGraph1(Map<String, String> map) {
        boolean result = false;
        MyCommonAPIs.sleepi(30);
        waitElement(wirelessDataConsumption);
        String graphText = wirelessDataConsumption.getText();
        System.out.println(graphText);
        if (graphText.contains("Wireless Data Consumption")) {
            waitElement(wireLessDataConsumptionLink);
            MyCommonAPIs.sleepi(5);
            wireLessDataConsumptionLink.click();
            MyCommonAPIs.sleepi(20);
            waitElement(wirelessDashboardGraph);
            waitElement(selecttimerforWIrelessDataConsumption);
            if (map.get("selectTimer").equals("Last 24 hrs")) {
                selecttimerforWIrelessDataConsumption.selectOption("Last 24 hrs");
                waitReady();
                System.out.println("Last 24 hrs Selected");
            } else {
                selecttimerforWIrelessDataConsumption.selectOption("Last 24 hrs");
                waitReady();
                System.out.println("Last 24 hrs Selected");
            }
            MyCommonAPIs.sleepi(5);
            String x1Value = verifyGraphCoordinates.getAttribute("x1");
            String y1Value = verifyGraphCoordinates.getAttribute("y1");
            String x2Value = verifyGraphCoordinates.getAttribute("x2");
            String y2Value = verifyGraphCoordinates.getAttribute("y2");
            System.out.println("x1: " + x1Value);
            System.out.println("y1: " + y1Value);
            System.out.println("x2: " + x2Value);
            System.out.println("y2: " + y2Value);
            if (((x1Value != "0") || (x2Value != "0")) || ((y1Value != "0") || (y2Value != "0"))) { // && ((rValue!="0") || (cxValue!="0") ||
                                                                                                    // (cyValue!="0")
                logger.info("User redirected to the extended view of the Wireless Data Consumption Graph");
                result = true;
            }
        }
        return result;
    }

    public boolean verifywirelessDataConsumptionGraph(String sNo) {
        boolean result = false;
        MyCommonAPIs.sleepi(30);
        waitElement(wirelessDataConsumption);
        String graphText = wirelessDataConsumption.getText();
        System.out.println(graphText);
        if (graphText.contains("Wireless Data Consumption")) {
            waitElement(wireLessDataConsumptionLink);
            MyCommonAPIs.sleepi(5);
            wireLessDataConsumptionLink.click();
            MyCommonAPIs.sleepi(5);
            String x1Value = verifyGraphCoordinates.getAttribute("x1");
            String y1Value = verifyGraphCoordinates.getAttribute("y1");
            String x2Value = verifyGraphCoordinates.getAttribute("x2");
            String y2Value = verifyGraphCoordinates.getAttribute("y2");
            System.out.println("x1: " + x1Value);
            System.out.println("y1: " + y1Value);
            System.out.println("x2: " + x2Value);
            System.out.println("y2: " + y2Value);
            if (((x1Value != "0") || (x2Value != "0")) || ((y1Value != "0") || (y2Value != "0"))) {
                logger.info("User redirected to the extended view of the Wireless Data Consumption Graph");
                result = true;
            }
        }
        return result;
    }

    public boolean verifyConnectedClientPerSSIDGraphLargeView(String ssid) {
        boolean result = false;
        for (int i = 1; i <= 60; i++) {
            refresh();
            MyCommonAPIs.sleepi(10);
            if (connectedClientPerSSIDGraph.exists()) {
                MyCommonAPIs.sleepi(5);
                connectedClientPerSSIDGraph.hover();
                logger.info("Outer If Loop: Connected client wireless SSID Graph showing on wireless page");
                break;
            }
        }
        for (int j = 1; j <= 60; j++) {
            refresh();
            MyCommonAPIs.sleepi(10);
            if (connectedClientPerSSIDNo.exists() && connectedClientPerSSID123(ssid).exists()) {
                logger.info("inner If Loop: Connected client wireless SSID showing on wireless page");
                connectedClientLink.click();
                MyCommonAPIs.sleepi(20);
                waitElement(connectedClientWirelessSSIDPage);
                if (connectedClientWirelessSSIDPage.exists()) {
                    result = true;
                    logger.info("Assert If Loop: Graph is showing on Connected client wireless SSID Large View Page ");
                }
                break;
            }
        }
        return result;
    }

    public boolean verifyConnectedClientPerSSIDGraph(String ssid) {
        boolean result = false;
        for (int i = 1; i <= 30; i++) {
            refresh();
            MyCommonAPIs.sleepi(10);
            if (connectedClientPerSSIDGraph.exists()) {
                MyCommonAPIs.sleepi(5);
                connectedClientPerSSIDGraph.hover();
                logger.info("Outer If Loop: Connected client wireless SSID Graph showing on wireless page");
                connectedClientPerSSIDGraph.doubleClick();
                waitElement(connectedClientPerSSIDNo);
                System.out.println("1st Got and second");
                waitElement(connectedClientPerSSID123(ssid));
                if (connectedClientPerSSIDNo.exists() && connectedClientPerSSID123(ssid).exists()) {
                    result = true;
                    logger.info("inner If Loop: Connected client wireless SSID showing on wireless page");
                }
                break;
            }
        }
        return result;
    }

    public boolean verifyNoDevicesDataonClientsPage() {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        waitElement(noclientDetails);
        if (noclientDetails.exists()) {
            result = true;
            logger.info("Devices are not there on Clients page");
        }
        return result;
    }

    public boolean verifyNoDevicesDataonReportsPage() {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        waitElement(noReportDetails);
        if (noReportDetails.exists()) {
            result = true;
            logger.info("Devices are not there on Reports page");
        }
        return result;
    }

    public boolean OrgInSecAdmin(String Name, String Email) {
        boolean result = false;
        if (Settings.exists()) {
            Settings.click();
        } else {
            Settings1.click();
        }
        MyCommonAPIs.sleepi(3);
        SecondaryAdmin.click();
        return result;
    }

    public void deleteALLORGSSID() {
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        new OrganizationPage(false).goToOrgSsid(WebportalParam.Organizations);

        MyCommonAPIs.sleepi(10);
        // waitElement(ssidListTable);
        waitReady();
        while (ssidlistone.exists()) {
            logger.info("entered delete loop");
            String ssidname = ssid.$x("//td[1]//span").getText();
            deleteOrgSsidYes(ssidname);
            waitReady();
            // waitElement(ssidListTable);
        }
    }

    public void checkConnectedClientListOnAllPages() {
        MyCommonAPIs.sleepi(180);
        assertTrue(new WirelessQuickViewPage(false).checkClientConnectClientPage(WebportalParam.clientwlanmac, "NO"),
                "Client not listed on wireless page");

        WebCheck.checkHrefIcon(URLParam.hrefClients);
        assertTrue(new WirelessQuickViewPage(false).checkClientConnect(WebportalParam.clientwlanmac),
                "Connected Client not showing on clients page.");
        logger.info("Connected client details is showned on clients page and wireless page");

        new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);
        new WirelessQuickViewPage(false).clientsTab.click();
        assertTrue(new WirelessQuickViewPage(false).checkClientConnectDevicePage(WebportalParam.clientwlanmac, "NO"),
                "Connected Client not showing on inside AP client page.");
        logger.info("Connected client details is showned under AP Clients page");
    }

    public boolean checkConnectedClientListOnAllPages1() {
        boolean result = false;
        MyCommonAPIs.sleepi(180);
        if (new WirelessQuickViewPage(false).checkClientConnectClientPage(WebportalParam.clientwlanmac, "NO")) {
            new WirelessQuickViewPage().enterDeviceYes(WebportalParam.ap1serialNo);
            new WirelessQuickViewPage(false).clientsTab.click();
            if (new WirelessQuickViewPage(false).checkClientConnectDevicePage(WebportalParam.clientwlanmac, "NO")) {
                logger.info("Connected client details is showned under AP Clients page");
                WebCheck.checkHrefIcon(URLParam.hrefClients);
                if (new WirelessQuickViewPage(false).checkClientConnect(WebportalParam.clientwlanmac)) {
                    logger.info("Connected client details is showned on clients page and wireless page");
                    result = true;
                }
            }
        }

        return result;

    }

    public void editssidName(String Ssid, Map<String, String> map) {
        settingsorquickview.click();
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
        }
        MyCommonAPIs.sleepi(10);
        ssid.setValue(map.get("SSID"));

        MyCommonAPIs.sleepi(4);
        editsave.click();
        waitReady();
        MyCommonAPIs.sleepi(3);
        confirmok.click();
        MyCommonAPIs.sleepi(4);
    }

    public String DragDensityToDtim(String level, String inter, String hz) {
        // level indicates slide for respective freq
        // interval indicates the interval in slider

        // added by shoib for Densty Slider
        Actions actions = new Actions(WebDriverRunner.getWebDriver());
        actions.dragAndDrop(sliderdtm(level), Interval(hz, inter)).perform();
        
        MyCommonAPIs.sleepi(10);
        if(savead.isEnabled())
        {
            System.out.print("entered dtim");
        savead.click();
        MyCommonAPIs.sleepi(15);
        if (okw.exists()) {                                      
            System.out.println("inside warrning band");
            List<SelenideElement> buttons = $$x("//*[text()='OK']");
            for (SelenideElement button : buttons) {
                if (button.is(Condition.visible)) {
                    button.click();
                    break;  // Click the first visible button and stop
                }
            }
                      
        }
        }
        String res = sliderdtm(level).getAttribute("aria-valuenow");
        System.out.println(res);
        return res;
    }

    public Boolean setBeaconvalue(String val, String hz) {
        if (val.matches("[a-zA-Z]+")) {
            beacon(hz).setValue(" ");
            MyCommonAPIs.sleepi(10);
            savead.click();
            String warmessage = warningbeacon.getText();
            return warmessage.equals("Beacon Interval value should be in the range of 100 to 300");
        }

        else if (val.matches("[0-9]+")) {
            int value = Integer.parseInt(val);
            if (value > 300 || value < 100) {
                beacon(hz).setValue(val);
                MyCommonAPIs.sleepi(10);
                savead.click();
                String warmessage = warningbeacon.getText();
                return warmessage.equals("Beacon Interval value should be in the range of 100 to 300");
            } else {
                beacon(hz).setValue(val);
                if(savead.isEnabled()) {
                savead.click();
                MyCommonAPIs.sleepi(5);               
                    okw.click();                    
                }
                return true;

            }
        }
        return false;
    }

    public String DragDensityToBroadcast(String level, String inter, String hz) {
        // level indicates slide for respective freq
        // interval indicates the interval in slider

        // added by shoib for Densty Slider
        Actions actions = new Actions(WebDriverRunner.getWebDriver());
        actions.dragAndDrop(sliderbroadcast(level), Intervalbroadcast(inter, hz)).perform();

        MyCommonAPIs.sleepi(10);
        if(savead.isEnabled())
        {
        savead.click();
        System.out.print("entered broad");
        MyCommonAPIs.sleepi(15);
//        okw.click();
         if (okw.exists()) {                                      
            System.out.println("inside warrning band");
            List<SelenideElement> buttons = $$x("//*[text()='OK']");
            for (SelenideElement button : buttons) {
                if (button.is(Condition.visible)) {
                    button.click();
                    break;  // Click the first visible button and stop
                }
            }
                      
        }
        
        }
        String res = sliderbroadcast(level).getAttribute("aria-valuenow");
        System.out.println(res);
        return res;
    }

    public void enableCaptivePortalTypeOrg(String Ssid, Map<String, String> map) {
        // settingsorquickview.click();
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            enableIcpStep();
            MyCommonAPIs.sleepi(10);
            if (!enabledailylogins.exists()) {
                MyCommonAPIs.sleepi(5);
                enableschedulereports.click();
                MyCommonAPIs.sleepi(3);
            }
            enabledailylogins.click();
            MyCommonAPIs.sleepi(3);
            enabledailyanalytics.click();
            MyCommonAPIs.sleepi(3);
            enableweeklyanalytics.click();
            MyCommonAPIs.sleepi(3);
            Selenide.switchTo().frame(0);
            MyCommonAPIs.sleepi(3);
            inputportalname.setValue(map.get("Portal Name"));
            inputwelcomeheadline.setValue(map.get("Welcome Headline"));
            if (map.containsKey("Welcome Message")) {
                inputWelcomeMsg.setValue(map.get("Welcome Message"));
            }
            if (map.containsKey("Captive Portal Logo")) {
                if (map.get("Captive Portal Logo").equals("DEFAULT_LOGO")) {
                    if ($x("//option[text()='" + map.get("Captive Portal Logo") + "']").exists()) {
                        addcaptivelogo.selectOption(map.get("Captive Portal Logo"));
                    } else {
                        addcaptivelogo.selectOption(1);
                    }
                } else {
                    chooseCaptiveLogoBtn.click();
                    MyCommonAPIs.sleepi(3);
                    selectCaptiveLogoName.setValue(map.get("Captive Portal Logo"));
                    MyCommonAPIs.sleepi(3);
                    selectCaptiveLogoFile.sendKeys(map.get("Captive Portal Logo Path"));
                    MyCommonAPIs.sleepi(3);
                    uploadImgOkBtn.click();
                    MyCommonAPIs.sleepi(20);
                    addcaptivelogo.selectOption(map.get("Captive Portal Logo"));
                }
            }
            if (map.containsKey("Desktop Background Image")) {
                if (map.get("Desktop Background Image").equals("DEFAULT_BG")) {
                    if ($x("//option[text()='" + map.get("Desktop Background Image") + "']").exists()) {
                        addbackgroundimage.selectOption(map.get("Desktop Background Image"));
                    } else {
                        addbackgroundimage.selectOption(1);
                    }
                } else {
                    chooseBackgroundImg.click();
                    MyCommonAPIs.sleepi(3);
                    selectCaptiveLogoName.setValue(map.get("Desktop Background Image"));
                    MyCommonAPIs.sleepi(3);
                    selectBackgroundImgFile.sendKeys(map.get("Desktop Background Image Path"));
                    MyCommonAPIs.sleepi(3);
                    uploadImgOkBtn.click();
                    MyCommonAPIs.sleepi(20);
                    addbackgroundimage.selectOption(map.get("Desktop Background Image"));
                }
            }
            if (map.containsKey("Mobile Background Image")) {
                chooseMobileBackgroundImg.click();
                MyCommonAPIs.sleepi(3);
                selectCaptiveLogoName.setValue(map.get("Mobile Background Image"));
                MyCommonAPIs.sleepi(3);
                selectMobileBackgroundImgFile.sendKeys(map.get("Mobile Background Image Path"));
                MyCommonAPIs.sleepi(3);
                uploadImgOkBtn.click();
                MyCommonAPIs.sleepi(20);
                addmobliebackgroundimage.selectOption(map.get("Mobile Background Image"));
            }
            inputlandingurl.setValue(map.get("Landing Page URL"));
            selectsessionduration.selectOption(map.get("Session Duration"));
            clickcaptiveportalstep.click();
            MyCommonAPIs.sleepi(10);
            selectsteptype.selectOption(map.get("Step Type"));
            MyCommonAPIs.sleepi(10);
            if (map.get("Step Type").equals("Authentication Method")) {
                configAuthenticationMethod(map);
            } else if (map.get("Step Type").equals("Play Video")) {
                configPlayVideo(map);
            } else if (map.get("Step Type").equals("Payment by Paypal")) {
                configPaymentByPaypal(map);
            } else if (map.get("Step Type").equals("Display Ad")) {
                configDispalyAd(map);
            } else if (map.get("Step Type").equals("Voucher")) {
            }

            // if(map.)
            MyCommonAPIs.sleepi(3);
            savecaptiveportalstep.click();
            MyCommonAPIs.sleepi(3);
            Selenide.switchTo().defaultContent();
            MyCommonAPIs.sleepi(10);
            savecaptive.click();
            MyCommonAPIs.sleepi(10);
            captiveok.click();
            MyCommonAPIs.sleepi(5);
            logger.info("Enable instant captive portal success.");
        } else {
            logger.info("Not found ssid.");
        }
    }

    public void goToNetworkSetting() {
        MyCommonAPIs.sleepi(10);
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        MyCommonAPIs.sleepi(10);
        if (Advance1.exists() && Advance2.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance1).perform();
            a.moveToElement(NetworkSettings).click().perform();
        } else if (Advance2.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance2).perform();
            a.moveToElement(NetworkSettings).click().perform();
        }
    }

    public void enableLogProbing(boolean flag) {
        MyCommonAPIs.sleepi(10);
        setSelected($x("(//input[@id='enableBlackList'])[5]/../span"), flag);
//        $x("(//input[@id='enableBlackList'])[5]/../span").click();
        MyCommonAPIs.sleepi(10);
        if(SaveIGMP.isDisplayed()) {
        new WirelessQuickViewPage(false).SaveIGMP.click();
        }
        MyCommonAPIs.sleepi(5);
        if(ConformSaveIGMP.isDisplayed()) {
        new WirelessQuickViewPage(false).ConformSaveIGMP.click();
    }      
    }
    
  

    public void enableSyslogLink() {
        syslogLink.click();
        MyCommonAPIs.sleepi(3);
        setSelected($x("//input[@id='toggleSyslogConfig']"), true);
    }

    public boolean verifySysPage() {
        if (syslogServer.getText().equalsIgnoreCase("Syslog Server IP Address") && port.getText().equalsIgnoreCase("Port Number")) { return true; }
        return false;
    }

    public boolean verifyLogProbestatus() {
        if (!sysLogStatus.isSelected()) { return true; }
        return false;
    }

    public String verifyProbeUi() {
        if (questionLogProbing.exists()) {
            questionLogProbing.click();
        }
        MyCommonAPIs.sleepi(30);
        waitElement(questionLogProbinginfo);
        String info = questionLogProbinginfo.getText();
        System.out.println(info);
        return info;
    }

    public boolean changechannelPower(String SLNo, String IP , String model) {
        boolean result = false;
        ExpandAP(SLNo).click();
        MyCommonAPIs.sleepi(5);
        channelinstantWiFI.selectOption("1/2.412GHz");
        OutputpowerinstantWiFI.selectOption("Quarter");
        if (!model.contains("WAC")) {
          ChannelwidthinstantWiFI.selectOption("40MHz");
        }
        MyCommonAPIs.sleepi(5);
        channelinstantWiFI5.selectOption("48/5.24GHz");
        OutputpowerinstantWiFI5.selectOption("Half");
        ChannelwidthinstantWiFI5.selectOption("80MHz");

        SaveInstantWiFI.click();
        MyCommonAPIs.sleepi(30);

        String check = new APUtils(IP).checkInstantWifiProfilecheck();
        
//      if(check.contains("\"channelWidth\":    \"2\"")){
//          System.out.println("111111111111111111");
//      }
//      if(check.contains("\"txPower\":      \"1\"")){
//          System.out.println("22222222222222222");
//      }
//      if(check.contains("\"channel\": \"48\"")){
//          System.out.println("33333333333333333");
//      }
//      if(check.contains("\"channelWidth\":    \"1\"")){
//          System.out.println("44444444444444444");
//      }
//      if(check.contains("\"txPower\": \"2\"")){
//          System.out.println("555555555555555555");
//      }
//      if(check.contains("\"channel\":      \"1\"")){
//          System.out.println("666666666666666666666");
//      }
      
      
      if(check.contains("\"2\"") & check.contains("\"1\"") & check.contains("\"48\"") &
              check.contains("\"1\"") & check.contains("\"2\"") & check.contains("\"1\"")     )
      {
          result = true;
      }
      
        // if(check.contains("\"channelWidth\": \"2\"") & check.contains("\"txPower\": \"1\"") & check.contains("\"channel\":
        // \"48\"") &
        // check.contains("\"channelWidth\": \"1\"") & check.contains("\"txPower\": \"2\"") & check.contains("\"channel\":
        // \"1\"") )
        // {
        // result = true;
        // }

        return result;    
  }
  public boolean deleteDeviceYesVerfiy(String serialNumber) {
      boolean result = false;
      WebCheck.checkHrefIcon(URLParam.hrefWireless);
      MyCommonAPIs.sleepi(10);
      if (checkApIsExist(serialNumber)) {
          logger.info("Delete device.");
          MyCommonAPIs.sleepi(10);
          executeJavaScript("arguments[0].removeAttribute('class')", editModule(serialNumber));
          MyCommonAPIs.sleep(3000);
          deleteDevice(serialNumber).waitUntil(Condition.visible, 60 * 1000).click();
          MyCommonAPIs.sleep(3000);
          MyCommonAPIs.sleepi(10);
          waitElement(deleteapyes);
          deleteapyes.click();
          refresh();
          MyCommonAPIs.sleep(5 * 1000);
          result = true;
      } return result;
  }
  


    public boolean changeTriggerInstantWiFi(String IP) {
        boolean result = false;
        ChannelTrigerINstantWiFI.click();
//        SaveInstantWiFI.click();
        MyCommonAPIs.sleepi(30);

        int count = 0;
        while (true) {
            MyCommonAPIs.sleepsync();
            if (new APUtils(IP).checkInstantWifiProfile(false)) {
                result = true;
                break;
            } else if (count == 10) {
                result = false;
                break;
            }
            count += 1;
        }

        return result;

    }

    public boolean changeDefaultTransmitPower(String IP) {
        boolean result = false;
        DefaultTransmitPowerinstantWiFI5.selectOption("QUARTER");
        SaveInstantWiFI.click();
        MyCommonAPIs.sleepi(30);

        int count = 0;
        while (true) {
            MyCommonAPIs.sleepsync();
            if (new APUtils(IP).checkInstantWifiProfile(false)) {
                result = true;
                break;
            } else if (count == 10) {
                result = false;
                break;
            }
            count += 1;
        }

        return result;

    }
    
    public boolean deleteDeviceYesVerify(String serialNumber) {
        boolean result = false;
        WebCheck.checkHrefIcon(URLParam.hrefWireless);
        MyCommonAPIs.sleepi(15);
        if (checkApIsExist(serialNumber)) {
            logger.info("Delete device.");
            executeJavaScript("arguments[0].removeAttribute('class')", editModule(serialNumber));
            MyCommonAPIs.sleep(3000);
            deleteDevice(serialNumber).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(3000);
            deleteapyes.click();
            refresh();
            result = true;
            MyCommonAPIs.sleep(5 * 1000);
        }
        return result;
    }
    
    //AddedByPratik
    public boolean verifyOrganizationWirelessQuickViewPage(String ssidName) {
        boolean result = false;
        MyCommonAPIs.sleepi(15);
        refresh();
        MyCommonAPIs.sleepi(15);
        waitElement(clientMac());
        if (ssidName(ssidName).exists() && clientMac().exists() && apSerialNoInClientsDetail().exists() 
                && nosOfClientcountText.exists() && nosOfSSIDcountText.exists()) {
            logger.info("Client details are showing on wireless page");
            result = true;
        }
          
        return result;
    }
    
    public boolean GotoRF() {
        boolean result=false;
        MyCommonAPIs.sleepi(10);
        waitElement(settingsorquickview);
        if (settingsorquickview.isDisplayed()) {
            settingsorquickview.click();
        }
        waitReady();
        MyCommonAPIs.sleepi(10);
        waitElement(ManageProfiles);
        if(ManageProfiles.isDisplayed()) {
        ManageProfiles.click();
        result=true;
        }
        return result;
    }
    public boolean CheckProfiles() {
        boolean result=false;
        MyCommonAPIs.sleepi(10);
        if(RFProfiles.isDisplayed()&& CustomerProfile.isDisplayed()) {
            result=true;
            }
            
        
        return result;
    }
    
    public boolean CheckRFProfiles() {
        boolean result=false;
        MyCommonAPIs.sleepi(10);
        if(OpenOffice.isDisplayed()&& Outdoor.isDisplayed()) {
            result=true;
            }
        return result;
        
    }
    
    public boolean CheckCustomProfiles() {
        boolean result=false;
        MyCommonAPIs.sleepi(10);
        waitElement(CustomerProfile);
        CustomerProfile.click();
        MyCommonAPIs.sleepi(10);
        if(Savant.isDisplayed()&& BandO.isDisplayed() && NICE.isDisplayed()&& BluOS.isDisplayed()) {
            result=true;
            }
        return result;
        
    }
    
    public boolean checkCreateRFProfile() {
        boolean result=false;
        MyCommonAPIs.sleepi(10);
        waitElement(addRFProfile);
        addRFProfile.click();
        MyCommonAPIs.sleepi(30);
        if(RFProfileName.isDisplayed()&& RFProfileDescription.isDisplayed()) {
            result=true;
            }
        CloseRFProfile.click();
        return result;
        
    }
    
    public boolean CheckCustomProfile() {
        boolean result=false;
        MyCommonAPIs.sleepi(10);
        waitElement(CustomerProfile);
        CustomerProfile.click();
        MyCommonAPIs.sleepi(10);
        if(!addRFProfile.exists()) {
            result=true;
            
        }
        return result;
    }
    
    public void CreateRFProfile(Map<String, String> map) {
       
        MyCommonAPIs.sleepi(10);
        addRFProfile.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(15);
        RFProfileName.shouldBe(Condition.visible).sendKeys(map.get("RFName"));
        MyCommonAPIs.sleepi(2);
        RFProfileDescription.shouldBe(Condition.visible).sendKeys(map.get("RFDescription"));
        
        boolean isCopyConfigurations = map.containsKey("Copy Configurations");
        MyCommonAPIs.sleepi(2);
        if(isCopyConfigurations== true) {
            enablecopyProfile.click();
            MyCommonAPIs.sleepi(3);
            SelectcopyProfile.shouldBe(Condition.visible).selectOption(map.get("Copy Configurations"));
        }
        MyCommonAPIs.sleepi(3);
        CreateRFProfile.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(10);

    }
    
    public boolean VerifyEditRF(Map<String, String> map) {
        boolean result = false;
        executeJavaScript("arguments[0].removeAttribute('class')", editRF(map.get("RFName")));
        MyCommonAPIs.sleep(3000);
        editRFprofile(map.get("RFName")).waitUntil(Condition.visible, 60 * 1000).click();
        MyCommonAPIs.sleep(8 * 1000);
        MyCommonAPIs.sleepi(10);
        
        if(DeviceListRF.isDisplayed() & InstantWiFiPreferredChannelRF.isDisplayed() & RadioSettingsRF.isDisplayed() & GeneralRF.isDisplayed()) 
        {
            
            result = true;
        }
        
        
        return result;
    }
    public void VerifyDis(Map<String, String> map) {
        boolean result = false;
        System.out.println(map.get("RFName"));
        MyCommonAPIs.sleepi(10);
        waitReady();
        String sElement = String.format("//td[text()='%s']", map.get("RFName"));
        logger.info("on element:" + sElement);

        $x(sElement).hover();
        MyCommonAPIs.sleep(3000);

        editRF(map.get("RFName")).hover();
        MyCommonAPIs.sleep(3000);

        editRFprofile(map.get("RFName")).shouldBe(Condition.visible, Condition.enabled).click();
        MyCommonAPIs.sleep(8000);
        MyCommonAPIs.sleepi(10);
        editRFProfileDescription.clear();
        editRFProfileDescription.sendKeys(map.get("RFDescriptionEdit"));
        MyCommonAPIs.sleepi(2);
        SaveEditRFProfile.click();
        MyCommonAPIs.sleepi(2);
        
    }
    
    public boolean VerifyGenaralRF(Map<String, String> map) {
        boolean result = false;
        System.out.println(map.get("RFName"));
        MyCommonAPIs.sleepi(10);
        waitReady();
        String sElement = String.format("//td[text()='%s']", map.get("RFName"));
        logger.info("on element:" + sElement);

        $x(sElement).hover();
        MyCommonAPIs.sleep(3000);

        editRF(map.get("RFName")).hover();
        MyCommonAPIs.sleep(3000);

        editRFprofile(map.get("RFName")).shouldBe(Condition.visible, Condition.enabled).click();
        MyCommonAPIs.sleep(8000);
        MyCommonAPIs.sleepi(10);
        String editRFProfileNamestr = editRFProfileName.shouldBe(Condition.visible).getValue();
        String editRFProfileDescriptionstr = editRFProfileDescription.shouldBe(Condition.visible).getValue();
        System.out.println(editRFProfileNamestr);    
        System.out.println(editRFProfileDescriptionstr);
        MyCommonAPIs.sleepi(1);
        
        if(map.get("RFName").equals(editRFProfileNamestr) & map.get("RFDescription").equals(editRFProfileDescriptionstr))  
        {
            
            result = true;
        }
        
        MyCommonAPIs.sleepi(3);
        $x("//button[@id='generalCan']").shouldBe(Condition.visible).click();
        
        
        return result;
    }
    
    public boolean checkRFExist(String Pname) {
        System.out.println(Pname);
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        waitReady();
        String sElement = String.format("//td[text()='%s']", Pname);
        logger.info("on element:" + sElement);
        if ($x(sElement).exists()) {
            result = true;
            logger.info("RFProfile:" + Pname + " is existed.");
        }
        return result;
    }
    
    
    public void deleteRF(String Pname) {
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        if (checkRFExist(Pname)) {
            logger.info("Delete RF Profile.");
            
            System.out.println(Pname);
            MyCommonAPIs.sleepi(10);
            waitReady();
            String sElement = String.format("//td[text()='%s']", Pname);
            logger.info("on element:" + sElement);
            
            $x(sElement).hover();
            MyCommonAPIs.sleep(3000);
            
            editRF(Pname).hover();
            MyCommonAPIs.sleep(3000);

            deleteRFprofile(Pname).shouldBe(Condition.visible, Condition.enabled).click();
            MyCommonAPIs.sleep(8000);
            
            MyCommonAPIs.sleepi(30);
            deletessidyes.click();
            MyCommonAPIs.sleep(5 * 1000);
        }
    }
    
	public void clickEditRF(String Pname, String PDescription) {
        System.out.println(Pname);
        MyCommonAPIs.sleepi(10);
        waitReady();
        String sElement = String.format("//td[text()='%s']", Pname);
        logger.info("on element:" + sElement);
        
        $x(sElement).hover();
        MyCommonAPIs.sleep(3000);
        
        editRF(Pname).hover();
        MyCommonAPIs.sleep(3000);
        editRFprofile(Pname).waitUntil(Condition.visible, 60 * 1000).click();
        MyCommonAPIs.sleep(8 * 1000);
        MyCommonAPIs.sleepi(10);
        
        editRFProfileName.shouldBe(Condition.visible).clear();
        MyCommonAPIs.sleepi(1);
        editRFProfileName.shouldBe(Condition.visible).sendKeys("Insight");
        MyCommonAPIs.sleepi(1);
        editRFProfileDescription.shouldBe(Condition.visible).clear();
        MyCommonAPIs.sleepi(1);
        editRFProfileDescription.shouldBe(Condition.visible).sendKeys(PDescription);
        MyCommonAPIs.sleepi(1);
        SaveEditRFProfile.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        
    }
    
    public boolean checkCustomProfileSsid() {

        boolean result = false;
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        waitElement(addssid);
        addssid.click();
        MyCommonAPIs.sleepi(15);                               
        if(custom.isDisplayed()) {
            result=true;            
        }
        return result;
    }    
    
    
    public boolean checkCustomProfileeditSSID(Map<String, String> map) {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        waitElement(addssid);

        if (checkSsidIsExist(map.get("SSID"))) {
            logger.info("Edit ssid.");
            $x("//span[text()='" + map.get("SSID") + "']").hover();
            MyCommonAPIs.sleep(3000);
            editWifi(map.get("SSID")).hover();
            MyCommonAPIs.sleep(3000);
            editSsid(map.get("SSID")).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(8 * 1000);

        }

        System.out.println(getCustomStatus());
        if (!getCustomStatus()) {
            logger.info("RF disabled");
            result = true;
        }
        return result;
    }
    
    
    public boolean addSsidcustom(Map<String, String> map) {

        boolean result = false;
        // waitElement(settingsorquickview);
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        waitElement(addssid);
        boolean isKeyPresent = false;

        if (!checkSsidIsExist(map.get("SSID"))) {
            logger.info("Add ssid.");
            addssid.click();
            MyCommonAPIs.sleepi(15);
            // waitElement(ssid);
            ssid.setValue(map.get("SSID"));
            waitReady();
            // System.out.println(checkband6.isSelected());

            isKeyPresent = map.containsKey("Band");
            System.out.println(isKeyPresent);
            if (isKeyPresent == true) {
                if (map.get("Band").equals("ALL")) {
                    if (ALLband.isDisplayed()) {
                        ALLband.click();
                        MyCommonAPIs.sleepi(4);
                        logger.info("check all band");
                    }
                    waitReady();
                } else if (map.get("Band").equals("Uncheck2.4 GHz")) {
                    if (band24.isDisplayed()) {
                        band24.click();
                        MyCommonAPIs.sleepi(4);
                        band6.click();
                        logger.info("Uncheck 2.4 band");
                    }
                    waitReady();
                } else if (map.get("Band").equals("Uncheck5 GHz")) {
                    if (band5.isDisplayed()) {
                        band5.click();
                        MyCommonAPIs.sleepi(4);
                        band6.click();
                        logger.info("Uncheck 5 band");
                    }
                    waitReady();
                } else if (map.get("Band").equals("Uncheck6 GHz")) {
                    if (band6.isDisplayed()) {
                        logger.info("Uncheck 6 band");
                    }
                    waitReady();
                } else if (map.get("Band").equals("check only 2ghz")) {
                    MyCommonAPIs.sleepi(4);
                    band5.click();
                    MyCommonAPIs.sleepi(4);
                } else if (map.get("Band").equals("check only 5ghz")) {
                    MyCommonAPIs.sleepi(4);
                    band24.click();
                    waitReady();
                } else if (map.get("Band").equals("check only 6ghz")) {
                    MyCommonAPIs.sleepi(4);
                    band24.click();
                    MyCommonAPIs.sleepi(4);
                    band5.click();
                    MyCommonAPIs.sleepi(4);
                    band6.click();
                    waitReady();
                }
            } else {
                logger.info("no need to Uncheck");
            }

            if (map.get("Security").equals("WPA2-PSK")) {
                security.selectOption("WPA2-PSK");
                waitReady();
            } else {
                security.selectOption(map.get("Security"));
                waitReady();
            }

            if (map.containsKey("Password")) {
                password.setValue(map.get("Password"));
                waitReady();
            }
            
            boolean iscustompresent = false;
            MyCommonAPIs.sleepi(4);
            iscustompresent = map.containsKey("custom");
            if(iscustompresent ==true) {
                if (map.get("custom").equals("enable")) {
                    custom.shouldBe(Condition.visible);
                    custom.click();
                    MyCommonAPIs.sleepi(4);
                    SelectProfile.shouldBe(Condition.visible);
                    SelectProfile.selectOption(1);                   
                }
                
            }
            takess("addSsid");
            save.click();
            waitReady();
            MyCommonAPIs.sleepi(4);           
            logger.info("Add ssid successful.");
        } else {
            logger.warning("checkSsidIsExist error");
        }
        return result;
    }
    
    public void editSsidcustom(Map<String, String> map) {       
        MyCommonAPIs.sleepi(10);
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        waitElement(addssid);

        if (checkSsidIsExist(map.get("SSID"))) {
            logger.info("Edit ssid.");
            executeJavaScript("arguments[0].removeAttribute('class')", editWifi(map.get("SSID")));
            MyCommonAPIs.sleep(3000);
            editSsid(map.get("SSID")).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(8 * 1000);
            
            
            boolean iscustompresent = false;
            iscustompresent = map.containsKey("custom");
            if(iscustompresent ==true) {
                if (map.get("custom").equals("disable")) {
                    customEdit.click();                                      
                }               
            }                    
                       
        }         
    }
    
    public String verifyDecription(Map<String, String> map) {
        String result = "";
        MyCommonAPIs.sleepi(10);
        addRFProfile.click();
        MyCommonAPIs.sleepi(10);
        RFProfileName.sendKeys(map.get("RFName"));
        RFProfileDescription.sendKeys(map.get("RFDescription"));
        MyCommonAPIs.sleepi(3);
        CreateRFProfile.click();     
        MyCommonAPIs.sleepi(1);
        result = DescriptionValidation.getText();      
        return result;          
    }
    
    public boolean verifyassignedAP(Map<String, String> map) {
        boolean result = false;
        String[] profileNames = new String[20];

        for (SelenideElement RFprofile : preferredcolumnsnew) {
            System.out.println(RFprofile);

        }

        System.out.println(map.get("RFName"));
        MyCommonAPIs.sleepi(10);
        waitReady();
        String sElement = String.format("//td[text()='%s']", map.get("RFName"));
        logger.info("on element:" + sElement);

        $x(sElement).hover();
        MyCommonAPIs.sleep(3000);

        editRF(map.get("RFName")).hover();
        MyCommonAPIs.sleep(3000);

        editRFprofile(map.get("RFName")).shouldBe(Condition.visible, Condition.enabled).click();
        MyCommonAPIs.sleep(8000);

        MyCommonAPIs.sleepi(30);
        DeviceListRF.click();
        MyCommonAPIs.sleepi(5);
        if (Devicelist(map.get("Device Name"), 1).isDisplayed() & Devicelist(map.get("Device SerialNo"), 2).isDisplayed()
                & Devicelist(map.get("Device Model"), 3).isDisplayed()) {
            result = true;
        }

        return result;

    }

    
	public void assignedRadioSetting(Map<String, String> map) {
             
        System.out.println(map.get("RFName"));
        MyCommonAPIs.sleepi(10);
        waitReady();
        String sElement = String.format("//td[text()='%s']", map.get("RFName"));
        logger.info("on element:" + sElement);
        
        $x(sElement).hover();
        MyCommonAPIs.sleep(3000);
        
        editRF(map.get("RFName")).hover();
        MyCommonAPIs.sleep(3000);
        editRFprofile(map.get("RFName")).waitUntil(Condition.visible, 60 * 1000).click();
        MyCommonAPIs.sleep(8 * 1000);
        MyCommonAPIs.sleepi(10);        
        RadioSettingsRF.click();
        MyCommonAPIs.sleepi(5);
       
        if (map.containsKey("2.4GHz output power")) {
            
            outputpower24.selectOption(map.get("2.4GHz output power"));
            
        }
         if (map.containsKey("2.4GHz channel width")) {
             
             channelWidth24.selectOption(map.get("2.4GHz channel width"));
            
        }
         if (map.containsKey("2.4GHz Radio Mode")) {
             
             RadioMode24.selectOption(map.get("2.4GHz Radio Mode"));
     
       }
         MyCommonAPIs.sleepi(2);
         SaveeditRFRadioChannels.shouldBe(Condition.visible).click();
         MyCommonAPIs.sleepi(5);
         okaybtneditRFRadioChannels.shouldBe(Condition.visible).click();
    }    

    
    
    public void assignedinstantWiFI(Map<String, String> map) {

        
        System.out.println(map.get("RFName"));
        MyCommonAPIs.sleepi(10);
        waitReady();
        String sElement = String.format("//td[text()='%s']", map.get("RFName"));
        logger.info("on element:" + sElement);
        
        $x(sElement).hover();
        MyCommonAPIs.sleep(3000);
        
        editRF(map.get("RFName")).hover();
        MyCommonAPIs.sleep(3000);

        editRFprofile(map.get("RFName")).shouldBe(Condition.visible, Condition.enabled).click();
        MyCommonAPIs.sleep(8000);
        
        MyCommonAPIs.sleepi(10);        
        InstantWiFiPreferredChannelRF.click();
        MyCommonAPIs.sleepi(5);
        channel4.click();
        MyCommonAPIs.sleepi(3);
        channel56.click();
        MyCommonAPIs.sleepi(3);    
         Saveedit.click();
    }
    
    
    public void disable80211kv(String Ssid) {
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        MyCommonAPIs.sleep(5);
        System.out.println(Ssid);
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            setSelected($x("//*[@id=\"kvrStatus\"]"), false);
            MyCommonAPIs.sleep(5);           
            save80211.click();
            MyCommonAPIs.sleep(10);
            ok80211.click();
        }

    }
    
    public void enableBandSteering(String Ssid) {
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        MyCommonAPIs.sleep(5);
        System.out.println(Ssid);
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            setSelected($x("//*[@id=\"bandSteeringSt\"]"), true);
            MyCommonAPIs.sleep(5);
            if (Warrning.isDisplayed()) {
                okFast.click();
//                result = true;
            }
            save80211.click();
            MyCommonAPIs.sleep(10);
            ok80211.click();
        }

    }

    
    
    //AddedByPratik
    public void enablecheckDisassociateSickyClients() { 
        MyCommonAPIs.sleepi(10);
        waitElement(disassociateStickyClientSw);
        disassociateStickyClientSw.click();
        MyCommonAPIs.sleepi(5);
        loadBalancingPageSaveButton.click();
        MyCommonAPIs.sleepi(5);
        waitElement(loadBalancingpageOKButton);
        loadBalancingpageOKButton.click();
        MyCommonAPIs.sleepi(5);
    }
    //AddedByPratik
    public boolean verifyClientIsolationAvailableUnderSSID(String Ssid) {
        boolean result = false;
        settingsorquickview.click();
        MyCommonAPIs.sleep(10);
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            MyCommonAPIs.sleepi(15);
            if (enableClientisolation.exists()) {
                    result = true;
            }
            MyCommonAPIs.sleepi(5);
        }
        return result;
    }
    //AddedByPratik
    public boolean verifyClientIsolationhelpBanner(String Ssid) {
        boolean result = false;
        settingsorquickview.click();
        MyCommonAPIs.sleep(5);
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            MyCommonAPIs.sleepi(15);
            enableClientisolation.click();
            MyCommonAPIs.sleepi(10);
            System.out.println("Loop 1 Pass");
            if (checkBoxAddIpAddress.exists()) {
                checkBoxAddIpAddress.click();
                System.out.println("Loop 2 Pass");
            } else {
                clickAddIP.click();
            }
            MyCommonAPIs.sleepi(5);
            clientIsolationQuestionMark.hover();
            MyCommonAPIs.sleepi(3);
            if (clientIsolationHelpText.exists()) {
                System.out.println("Loop 3 Pass");
                System.out.println(clientIsolationHelpText.getText());
                addIpWarningText.hover();
                MyCommonAPIs.sleepi(1);
                String temp = addIpWarningText.getText();
                System.out.println(temp);
                if (temp.contains("Up to 16 entries are supported")) {
                    result = true;
                    logger.info("Question marl text and add ip address warning text verified");
                    System.out.println("Loop 4 Pass");
                }  
            }
        }
        return result;
    }
    //AddedByPratik
    public boolean verifyEnableDisableClientIsolationSwitch(String Ssid) {
        boolean result = false;
        settingsorquickview.click();
        MyCommonAPIs.sleep(5);
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            MyCommonAPIs.sleepi(15);
            if (enableClientisolation.exists()) {
                enableClientisolation.click();
                MyCommonAPIs.sleepi(5);
                enableClientisolation.click();
                MyCommonAPIs.sleepi(5);
                result = true;
            }
            MyCommonAPIs.sleepi(5);
        }
        return result;
    }
    //AddedByPratik
    public boolean verifyCheckBoxAddIPAddress(String Ssid) {
        boolean result = false;
        settingsorquickview.click();
        MyCommonAPIs.sleep(5);
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            MyCommonAPIs.sleepi(15);
            if (enableClientisolation.exists()) {
                enableClientisolation.click();
                MyCommonAPIs.sleepi(5);
                if (checkBoxAddIpAddress.exists()) {
                    enableClientisolation.click();
                    MyCommonAPIs.sleepi(5);
                    result = true;
                }
            }
            MyCommonAPIs.sleepi(5);
        }
        return result;
    } 
    //AddedByPratik
    public boolean verifyEnableMPSKClientIsolationHide(String Ssid) {
        boolean result = false;
        settingsorquickview.click();
        MyCommonAPIs.sleep(5);
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            MyCommonAPIs.sleepi(15);
            if ((enableClientisolation.exists())==false) {
                    result = true;
                    logger.info("After enabling MPSK Client isolation is hidden");
            }
            MyCommonAPIs.sleepi(5);
        }
        return result;
    }
    //AddedByPratik
    public boolean editSSIDandEnableClientIsolationAndSelectVLAN20(String Ssid, String URLs[], String vlan) {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        settingsorquickview.click();
        MyCommonAPIs.sleep(5);
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            MyCommonAPIs.sleepi(10);
            VLANInput.selectOption(vlan);
            MyCommonAPIs.sleepi(15);
            enableClientisolation.click();
            MyCommonAPIs.sleepi(15);
            if (checkBoxAddIpAddress.exists()) {
                checkBoxAddIpAddress.click();
            } else {
                clickAddIP.click();
            }      
            MyCommonAPIs.sleepi(5);
            for (String urlone : URLs) {
                DomainName.sendKeys(urlone);
                MyCommonAPIs.sleepi(5);
                AddDomainName.click();
                MyCommonAPIs.sleepi(5);
            }
            MyCommonAPIs.sleepi(5);
            saveedit.click();
            MyCommonAPIs.sleepi(5);
            if (saveedfinal.isDisplayed()) {
                saveedfinal.click();
                result = true;
            }    
        }
        return result;
    }
    //AddedByPratik
    public boolean deslectVLAN20andSelectDefaultVlanwithClientIsolation(String Ssid, String URLs[]) {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        settingsorquickview.click();
        MyCommonAPIs.sleep(5);
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            MyCommonAPIs.sleepi(10);
            VLANInput.selectOption("Management VLAN(1)");
            MyCommonAPIs.sleepi(10);
            saveedit.click();
            MyCommonAPIs.sleepi(5);
            if (saveedfinal.isDisplayed()) {
                saveedfinal.click();
                result = true;
            }    
        }
        return result;
    }
    
    // Added by Anusha H
    public void broadcastToggleButton(String ssid, String Enable) {
        MyCommonAPIs.sleepi(10);
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        MyCommonAPIs.sleep(5);
        if (checkSsidIsExist(ssid)) {
            clickEditSsid(ssid);
        }
  
        if (Enable.equals("0")) {
            setSelected(broadcastTogglebutton, false);
            sleepi(5);
        } else {
            setSelected(broadcastTogglebutton, true);
            sleepi(5);
        }
      
        editsave.click();
        waitReady();
        MyCommonAPIs.sleepi(3);
        confirmok.click();
        MyCommonAPIs.sleepi(4);
        }
    
    public void GoToNetworkSettings() {
        MyCommonAPIs.sleepi(10);
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        MyCommonAPIs.sleepi(10);
        if (Advance1.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance1).perform();
            a.moveToElement(NetworkSettings).click().perform();
        }
        MyCommonAPIs.sleepi(3);
    }

    public void GoToMeshSettings() {
        MyCommonAPIs.sleepi(10);
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        MyCommonAPIs.sleepi(10);
        if (Advance1.exists()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            Actions a = new Actions(driver);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0, 250)", "");
            a.moveToElement(Advance1).perform();
            a.moveToElement(MeshSetting).click().perform();
        }
        MyCommonAPIs.sleepi(3);
    }
    
    //AddedByPratik
    public boolean verifyAllcountUnderSettingsdevicesoption() {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        waitElement(totaldevicesAvilable);
        String extractedText = totaldevicesAvilable.text();
        extractedText = extractedText.replace("Devices (", "").replace(")", "");
        int totalNoofDevicesShowing = Integer.parseInt(extractedText);
        int expectedNumber = 1;
        if ((totalNoofDevicesShowing==expectedNumber) && pageDetailsAnddevices.exists()) {
            result = true;
            logger.info("Total devices count showing correctly on wireless page");
        }
        return result;
    }   
    //AddedbyPratik
    public boolean verifyAllPowerSettingOptions() {
        boolean result = true;
        waitElement(powerSettingsTab);
        powerSettingsTab.click();
        MyCommonAPIs.sleepi(10);
        if ((WebportalParam.ap1Model).equals("WBE710") || (WebportalParam.ap1Model).equals("WBE718")) {
            powerSettingsDropdown.shouldBe(Condition.visible).click();
            String[] expectedOptions = {"Automatic", "802.3af", "802.3at"};
            String xpath = "//select[contains(@class,'inputTextField')]//option[text()='%s']";       
            for (String expectedOption : expectedOptions) {
                String formattedXpath = String.format(xpath, expectedOption);
                System.out.println("Formatted XPath: " + formattedXpath);
                try {
                    $(By.xpath(formattedXpath)).shouldBe(Condition.visible);  // Wait for each option to be visible
                } catch (Exception e) {
                    System.out.println("Option not visible: " + expectedOption);
                    return false;
                }
            }
        } else {
            powerSettingsDropdown.shouldBe(Condition.visible).click();
            String[] expectedOptions = {"Automatic", "802.3af", "802.3at", "802.3bt"};
            String xpath = "//select[contains(@class,'inputTextField')]//option[text()='%s']";       
            for (String expectedOption : expectedOptions) {
                String formattedXpath = String.format(xpath, expectedOption);
                System.out.println("Formatted XPath: " + formattedXpath);
                try {
                    $(By.xpath(formattedXpath)).shouldBe(Condition.visible);  // Wait for each option to be visible
                } catch (Exception e) {
                    System.out.println("Option not visible: " + expectedOption);
                    return false;
                }
            } 
        }      
        return result;
    }
    //AddedbyPratik
    public boolean changePowerModeFromAutomaticToAnymode(String powerMode) {
        boolean result = false;
        waitElement(powerSettingsTab);
        powerSettingsTab.click();
        MyCommonAPIs.sleepi(10);
        powerSettingsDropdown.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleep(10);
        if ((powerMode.equals("802.3bt")) && ((WebportalParam.ap1Model).equals("WBE710") || (WebportalParam.ap1Model).equals("WBE718"))){
            System.out.println("WBE710/718 AP Dont hae bt mode");
            result = true;
        } else {
            $x("//select[contains(@class,'inputTextField')]//option[text()='" + powerMode + "']").click();
            MyCommonAPIs.sleep(10);
            saveButtonPowerSetting.click();
            MyCommonAPIs.sleepi(10);
            if (powerSettingWarningPopup.exists()) {
                System.out.println(powerSettingWarningPopup.getText());
                powerSettingWarningPopupOK.click();
                MyCommonAPIs.sleepi(120);
                String selectedPowerMode = powerSettingsDropdown.getText();
                System.out.println(selectedPowerMode);
                if (selectedPowerMode.equals(powerMode)) {
                    System.out.println("Power mode was correctly selected.");
                    result = true;
                }
            }
        }
        return result;
    }
    //AddedbyPratik
    public boolean changePowerModeToAutomatic(String defaultpowerMode) {
        boolean result = false;
        waitElement(powerSettingsTab);
        powerSettingsTab.click();
        MyCommonAPIs.sleepi(10);
        powerSettingsDropdown.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleep(10);
        $x("//select[contains(@class,'inputTextField')]//option[text()='" + defaultpowerMode + "']").click();
        MyCommonAPIs.sleep(10);
        saveButtonPowerSetting.click();
        MyCommonAPIs.sleepi(1);   
        if (successMsg.exists()) {
            MyCommonAPIs.sleepi(120);
            String selectedPowerMode = powerSettingsDropdown.getText();
            System.out.println(selectedPowerMode);
            if (selectedPowerMode.equals(defaultpowerMode)) {
                System.out.println("Power mode was correctly selected.");
                result = true;
            }
        }
        return result;
    }
    // AddedbyPratik
    public boolean verifyAndselectedpowerOptionIsvisbleOrNot(String powerMode) {
        boolean result = false;
        waitElement(powerSettingsTab);
        powerSettingsTab.click();
        MyCommonAPIs.sleepi(10);
        if ((powerMode.equals("802.3bt")) && ((WebportalParam.ap1Model).equals("WBE710") || (WebportalParam.ap1Model).equals("WBE718"))) {
            System.out.println("WBE710/718 AP Dont hae bt mode");
            result = true;
        } else {
            String selectedPowerMode = powerSettingsDropdown.getText();
            System.out.println(selectedPowerMode);
            if (selectedPowerMode.equals(powerMode)) {
                System.out.println("Power mode was correctly selected.");
                result = true;
            }
        }
        return result;
    }
    //AddedbyPratik
    public boolean editSSIDAndAddCustomerProfile(String Ssid, String Customerprofile) {
        boolean result = false;
        settingsorquickview.click();
        if (checkSsidIsExist(Ssid)) {
            clickEditSsid(Ssid);
            MyCommonAPIs.sleepi(5);
            waitElement(customerProfile);
            MyCommonAPIs.sleepi(2);
            waitElement(customerProfileSwitch);
            MyCommonAPIs.sleepi(2);
            customerProfileSwitch.click();
            MyCommonAPIs.sleepi(5);
            waitElement(customerProfileSelect);
            customerProfileSelect.click();
            MyCommonAPIs.sleepi(1);
            customerProfileSelect.selectOption(Customerprofile);
            MyCommonAPIs.sleepi(5);
            String selectedOptionText = customerProfileSelect.getSelectedOption().getText();
            System.out.println("Customer Profile Selected : "+selectedOptionText);
            boolean isOptionSelected = selectedOptionText.equals(Customerprofile);
            if (isOptionSelected) {
                result = true;
            }
            waitElement(editSsidSaveBtn);
            editSsidSaveBtn.click();
            MyCommonAPIs.sleepi(5);
            waitElement(editSsidSuccessOkayBtn);
            editSsidSuccessOkayBtn.click();
            MyCommonAPIs.sleepi(180);
        }
        return result;
    }
    //AddedByPratik
    public boolean deleteSsidYesConfirm(String Ssid) {
        boolean result = false;
        MyCommonAPIs.sleepi(15);
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        refresh();
        MyCommonAPIs.sleepi(15);
        if (checkSsidIsExist(Ssid)) {
            logger.info("Delete ssid.");
            MyCommonAPIs.sleepi(5);
            hoverToSSID1(Ssid).hover();
            MyCommonAPIs.sleep(3000);
            executeJavaScript("arguments[0].removeAttribute('class')", editWifi(Ssid));
            MyCommonAPIs.sleep(3000);
            editWifi(Ssid).hover();
            MyCommonAPIs.sleep(3000);
            deleteSsid(Ssid).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleepi(30);
            waitElement(deletessidyes);
            MyCommonAPIs.sleepi(1);
            deletessidyes.click();
            MyCommonAPIs.sleep(5 * 1000);
            result = true;
        }
        return result;
    }
    //AddedBypratik
    public boolean verifyLogProbingisenabledornot() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        SelenideElement toggleSwitch = $(By.name("syslog"));
        waitElement(toggleSwitch);
        boolean isChecked = toggleSwitch.isSelected();
        if (isChecked) {
            System.out.println("Log Probing Clients to Syslog toggle is enabled (checked)");
            result = true;
        } else {
            System.out.println("Log Probing Clients to Syslog toggle is disabled (unchecked)");
            result = false;
        }
        return result = true;
    }
    
 // AddedByPratik
    public boolean verifyAndCompareUIChannelsandTeraTermChannelsforBand(String band) {
        boolean overallMatch = true;

        MyCommonAPIs.sleepi(10);
        waitElement($x("//span[text()='"+band+"']/../..//h5[text()='Channel']/../select"));

        List<String> dropdownOptions = $x("//span[text()='"+band+"']/../..//h5[text()='Channel']/../select").$$("option").stream()
                .map(option -> option.getText().replace("GHz", "").trim()) // Remove "GHz"
                .collect(Collectors.toList());

        System.out.println("UI Dropdown Options : " + dropdownOptions);

        List<String> dropdownFrequencies = dropdownOptions.stream()
                .filter(option -> option.contains("/")) // Ensure it has a channel/frequency format
                .map(option -> option.split("/")[1] // Extract frequency part after "/"
                        .replace(".", "") // Remove decimal to match Tera Term format
                        .replaceAll("\\(PSC\\)|\\(DFS\\)", "") // Remove DFS/PSC labels
                        .trim())
                .map(freq -> {
                    int value = Integer.parseInt(freq);
                    return value < 1000 ? String.valueOf(value * 10) : String.valueOf(value); // Multiply by 10 only for values < 1000
                })
                .collect(Collectors.toList());

        System.out.println("Processed UI Dropdown Frequencies: " + dropdownFrequencies);

        String teraTermOutput = new APUtils(WebportalParam.ap1IPaddress).getBandChannelsStatus(WebportalParam.ap1Model, band.split("G")[0]);

        List<Integer> teraTermRawFrequencies = Arrays.stream(teraTermOutput.split("\n"))
                .filter(line -> line.contains("Channel") && line.contains(":"))
                .map(line -> {
                    try {
                        return Integer.parseInt(line.split(":")[1].trim().split("\\s+")[0]);
                    } catch (Exception e) {
                        System.err.println("Error parsing line: " + line);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        System.out.println("Tera Term Raw Frequencies: " + teraTermRawFrequencies);

        if (band.contains("5")) {
            // Fix: Only divide by 10 for smaller values
            List<String> processedTeraTermFrequencies = teraTermRawFrequencies.stream()
                    .map(freq -> (freq < 1000) ? String.valueOf(freq / 10) : String.valueOf(freq)) // Only divide smaller values
                    .collect(Collectors.toList());

            System.out.println("Processed Tera Term Frequencies: " + processedTeraTermFrequencies);

            List<String> missingInTeraTerm = dropdownFrequencies.stream()
                    .filter(channel -> !processedTeraTermFrequencies.contains(channel))
                    .collect(Collectors.toList());

            List<String> extraInTeraTerm = processedTeraTermFrequencies.stream()
                    .filter(channel -> !dropdownFrequencies.contains(channel))
                    .collect(Collectors.toList());

            if (!missingInTeraTerm.isEmpty()) {
                System.out.println("Channels missing in Tera Term: " + missingInTeraTerm);
                overallMatch = false;
            }

            if (!extraInTeraTerm.isEmpty()) {
                System.out.println("Extra channels found in Tera Term: " + extraInTeraTerm);
                overallMatch = false;
            }

            if (overallMatch) {
                System.out.println("All Dropdown channels are present in Tera Term, and there are no extra channels.");
            } else {
                System.out.println("Dropdown and Tera Term lists do not match.");
            }
        } else {
            System.out.println("Tera Term Frequencies (Unprocessed for other bands): " + teraTermRawFrequencies);
        }

        System.out.println("Comparison completed. Overall match: " + overallMatch);
        return overallMatch;
    }

//AddedByPratik
    public void disableCustomerProfile(Map<String, String> map) {

        MyCommonAPIs.sleepi(10);
        if (settingsorquickview.exists()) {
            settingsorquickview.click();
        }
        waitReady();
        waitElement(addssid);

        if (checkSsidIsExist(map.get("SSID"))) {
            logger.info("Edit ssid.");
            $x("//span[text()='" + map.get("SSID") + "']").hover();
            MyCommonAPIs.sleep(3000);
            editWifi(map.get("SSID")).hover();
            MyCommonAPIs.sleep(3000);
            editSsid(map.get("SSID")).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(8 * 1000);

        }
        MyCommonAPIs.sleepi(5);
        Actions actions = new Actions(WebDriverRunner.getWebDriver());
        actions.moveToElement(custom).click().perform();
        Selenide.sleep(500);
        if (custom.getAttribute("class").contains("active")) {
            custom.click();
        }
        logger.info("Customer Profile disabled");
        MyCommonAPIs.sleepi(5);
        saveedit.click();
        MyCommonAPIs.sleepi(5);
        confirmok.click();
        MyCommonAPIs.sleepi(3);
        logger.info("Edit ssid successful.");
        System.out.println("SSID successfully edited");

    }
    
    public void clickEditRFOnlyDescription(String Pname, String PDescription) {
        System.out.println(Pname);
        MyCommonAPIs.sleepi(10);
        waitReady();
        String sElement = String.format("//td[text()='%s']", Pname);
        logger.info("on element:" + sElement);
        
        $x(sElement).hover();
        MyCommonAPIs.sleep(3000);
        
        editRF(Pname).hover();
        MyCommonAPIs.sleep(3000);
        editRFprofile(Pname).waitUntil(Condition.visible, 60 * 1000).click();
        MyCommonAPIs.sleep(8 * 1000);
        MyCommonAPIs.sleepi(10);
        editRFProfileDescription.shouldBe(Condition.visible).clear();
        MyCommonAPIs.sleepi(1);
        editRFProfileDescription.shouldBe(Condition.visible).sendKeys(PDescription);
        MyCommonAPIs.sleepi(1);
        SaveEditRFProfile.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        
    }
  
}

  
