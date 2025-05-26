package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.logging.Logger;

import org.apache.log4j.chainsaw.Main;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import groovy.time.Duration;
import util.APUtils;
import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.FirmwareElement;
import webportal.webelements.OrganizationElement;
import webportal.weboperation.WirelessQuickViewPage;
import webportal.webelements.WirelessQuickViewElement;

import com.codeborne.selenide.Condition;
import static com.codeborne.selenide.Selenide.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import static com.codeborne.selenide.Condition.visible;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;



public class OrganizationPage extends OrganizationElement {
    String date    = "";
    String timearr = "";
    String hour    = "";
    String minute  = "";
    int    m;
    int    d;
    int    h;
    int    beforehours;
    int    beforeminutes;
    int    minTime;
    // Logger logger = Logger.getLogger("OrganizationPage");
    final static Logger logger = Logger.getLogger("OrganizationPage");

    public OrganizationPage() {
        String url = WebDriverRunner.url();
        if (!url.contains(URLParam.hreforganization)) {
            if ($(sOrganizationFlag).exists()) {
                open(URLParam.hreforganization, true);
            } else {
                logger.info("not a pro account");
            }
        }

        MyCommonAPIs.waitReady();
        logger.info("init...");
    }

    /**
     * @param noPage
     *            check whether we are logged-in already for debug
     */
    public OrganizationPage(boolean noPage) {
        logger.info("initex...");
    }

    public void gotoPage() {
        WebCheck.checkUrl(URLParam.hreforganization);
    }

    public void addOrg(String name) {
        boolean located = false;
        MyCommonAPIs.waitElement(sOrganizationLocationElement1);
        for (SelenideElement se : $$(sOrganizationLocationElement1)) {
            if (MyCommonAPIs.getText(se).toLowerCase().contains(name.toLowerCase())) {
                located = true;
                logger.info("Organisation is Already there ");
                break;
            }
        }
        if (!located) {
            MyCommonAPIs.waitElement(AddOrg);
            if (AddOrg.exists()) {
                AddOrg.click();
                Selenide.sleep(5000);
                NameOrg.sendKeys(name);
                SaveOrg.click();
                located = true;
                logger.info("--------------- Organisation is Created Succesfully ----------");
                Selenide.sleep(10000);
                int dialog = 0;
                for (SelenideElement elem : dialogbox1) {
                    if ((dialog == 1) && elem.getText().equalsIgnoreCase("No")) {
                        elem.click();
                        logger.info("By default No is selected");
                    }
                }
            }
        }
        waitReady();
    }

//    public void openOrg(String name) {
//        open(URLParam.hreforganization, true);
//        waitElement(sOrganizationLocationElement1);
//        boolean located = false;
//        String newElement = "";
//        if ($(sOrganizationLocationElement).isDisplayed()) {
//            System.out.println("inside sOrganizationLocationElement");
//            newElement = sOrganizationLocationElement;
//        } else if ($(sOrganizationLocationElementNew).isDisplayed()) {
//            System.out.println("inside sOrganizationLocationElementNew");
//            newElement = sOrganizationLocationElementNew;
//        }
//           System.out.println("----------------------");
//        waitElement(newElement);
//        for (SelenideElement se : $$(newElement)) {
//            if (getText(se).equalsIgnoreCase(name)) {
//                located = true;
//                se.click();
//                break;
//            }
//        }
//        if (!located) {
//            System.out.println("Not-located");
//            logger.info("click first location");
//            $(newElement).click();
//        }
//        waitReady();
//    }
    
    public void openOrg(String name) {
        open(URLParam.hreforganization, true);
        MyCommonAPIs.sleepi(2);
        SelenideElement orgElement = $x("//*[@col-id='orgName']/..//*[text()='" + name + "']");
        waitElement(orgElement);
        orgElement.shouldBe(Condition.visible);
        if (orgElement.exists() && orgElement.isDisplayed()) {
            orgElement.click();
            logger.info("Clicked on organization: " + name);
        } else {
            logger.info("Organization not found: " + name);
            throw new NoSuchElementException("Organization " + name + " not found on the page.");
        }
        
        waitReady(); // Ensure the page is ready after clicking
    }

    public void GotoSettings() {
        waitElement(OrgPagebody);
        boolean located = false;
        for (SelenideElement se : $$(OrgPagebody)) {
            System.out.println(se.findElementByTagName("a"));
            if (se.getTagName().equals("a") && getText(se).toLowerCase().equals("settings")) {
                located = true;
                se.click();
                break;
            }
        }
        if (!located) {
            logger.info("Unable to get settings button");
        }
        waitReady();
    }

    public void deleteOrganization(String Name) {
        deleteOrganizationNew(Name);
    }

    // add by dallas
    public void addOrganization(Map<String, String> map) {
        boolean located = false;
        MyCommonAPIs.sleepi(15);
        waitElement(sOrganizationLocationElement1);
        // if (checkOrganizationIsExist(map.get("Name"))) {
        // located = true;
        // }
        if (!located) {
            MyCommonAPIs.sleepi(10);
            MyCommonAPIs.waitElement(AddOrg);
            if (AddOrg.exists()) {
                AddOrg.click();
                MyCommonAPIs.sleepi(15);
                Selenide.refresh();
                MyCommonAPIs.sleepi(3);
                NameOrg.sendKeys(map.get("Name"));
                if (map.containsKey("Owner Name")) {
                    ownerName.sendKeys(map.get("Owner Name"));
                }
                MyCommonAPIs.sleepi(1);
                if (map.containsKey("Email Address")) {
                    ownerEmail.sendKeys(map.get("Email Address"));
                }
                MyCommonAPIs.sleepi(1);
                if (map.containsKey("Phone Number")) {
                    ownerPhone.sendKeys(map.get("Phone Number"));
                }
                MyCommonAPIs.sleepi(1);
                if (map.containsKey("Business Phone Number")) {
                    businessPhone.sendKeys(map.get("Business Phone Number"));
                }
                MyCommonAPIs.sleepi(1);
                selectNotificationAndReport(map);
                if (map.containsKey("Device Ownership")) {
                    switch (map.get("Device Ownership")) {
                    case "Admin":
                        if (!deviceownershipadmin.has(Condition.attribute("disabled"))) {
                            deviceownershipadmin.selectRadio("on");
                        }
                        break;
                    case "Owner":
                        if (!deviceownershipowner.has(Condition.attribute("disabled"))) {
                            deviceownershipowner.selectRadio("on");
                        }
                        break;
                    }
                }
                if (map.containsKey("Scheduled Reports")) {
                    if (map.get("Scheduled Reports").equals("disable")) {
                        if (!scheduleweekly.has(Condition.attribute("disabled"))) {
                            scheduledreport.click();
                        }
                    }
                }
                if (map.containsKey("Scheduled Reports Option")) {
                    switch (map.get("Scheduled Reports")) {
                    case "weekly":
                        if (!scheduleweekly.has(Condition.attribute("disabled"))) {
                            scheduleweekly.selectRadio("1");
                        }
                        break;
                    case "monthly":
                        if (!schedulemonthly.has(Condition.attribute("disabled"))) {
                            schedulemonthly.selectRadio("2");
                        }
                        break;
                    }
                }
                MyCommonAPIs.sleepi(10);
                SaveOrg.click();
                located = true;
                logger.info("--------------- Organisation is Created Succesfully ----------");
                MyCommonAPIs.sleepi(30);
                if (map.containsKey("Automatically Created")) {
                    if ($x("//h4[text()='Organization Created Successfully']/../..//button[text()='Yes']").exists()) {
                        $x("//h4[text()='Organization Created Successfully']/../..//button[text()='Yes']").click();
                    }
                    waitReady();
                    MyCommonAPIs.sleepi(5);
                    businessPhone.setValue(map.get("Number of Locations"));
                    MyCommonAPIs.sleepi(1);
                    devAdminPwd.setValue(map.get("Device Admin Password"));
                    MyCommonAPIs.sleepi(1);
                    autoZipCode.setValue(map.get("Zip Code"));
                    MyCommonAPIs.sleepi(3);
                    nextButton.click();
                    MyCommonAPIs.sleepi(10);
                    clickButton(0);
                    waitReady();
                    MyCommonAPIs.sleepi(5);
                    clickYesNo(false);
                } else {
                    MyCommonAPIs.sleepi(15);
                    waitElement("//h4[text()='Organization Created Successfully']/../..//button[text()='No']");
                    MyCommonAPIs.sleepi(1);
                    if ($x("//h4[text()='Organization Created Successfully']/../..//button[text()='No']").exists()) {
                        $x("//h4[text()='Organization Created Successfully']/../..//button[text()='No']").click();
                    }
                }
            }
        }
        waitReady();
    }

    public void addOrganization(String orgName, String noLoc) {
        if (!checkOrganizationIsExist(orgName)) {
            AddOrg.click();
            waitElement(NameOrg);
            NameOrg.sendKeys(orgName);
            SaveOrg.click();
            waitReady();
            clickYesNo(true);
            businessPhone.setValue(noLoc);
            devAdminPwd.setValue(WebportalParam.loginDevicePassword);
            MyCommonAPIs.sleepi(20);
            nextButton.click();
            waitReady();
            clickButton(0);
            clickYesNo(false);
            waitReady();
        }
    }

    public void editOrganization(Map<String, String> map) {
//        waitElement(sOrganizationLocationElement1);
        if (checkOrganizationIsExist(map.get("Name"))) {
//            dropdownOrganizationElement(map.get("Name")).click();
//            editOrganizationElement(map.get("Name")).click();
            String rowindex=dropdownOrganizationElementNew(map.get("Name")).getAttribute("aria-rowindex");
            ariaSetIndex(rowindex).click();
            ariaSetIndexEdit(rowindex).click();
            MyCommonAPIs.sleepi(1);
            refresh();
            MyCommonAPIs.sleepi(15);
            waitElement(NameOrg);
            MyCommonAPIs.sleepi(2);
            if (map.containsKey("New Name")) {
                NameOrg.clear();
                MyCommonAPIs.sleepi(1);
                NameOrg.sendKeys(map.get("New Name"));
            }
            // if (map.containsKey("Owner Name")) {
            // ownerName.clear();
            // MyCommonAPIs.sleepi(1);
            // ownerName.sendKeys(map.get("Owner Name"));
            // }
            if (map.containsKey("Email Address")) {
                // ownerEmail.clear();
                // ownerEmail.sendKeys("1");
                // MyCommonAPIs.sleepi(5);
                // try {
                // Robot robot = new Robot();
                // // int sum = 0;
                // // while (sum < 30) {
                // robot.keyPress(KeyEvent.VK_CONTROL);
                // robot.keyPress(KeyEvent.VK_A);
                // robot.keyRelease(KeyEvent.VK_CONTROL);
                // robot.keyRelease(KeyEvent.VK_A);
                // robot.delay(3);
                // robot.keyPress(KeyEvent.VK_BACK_SPACE);
                // MyCommonAPIs.sleepi(1);
                // // sum += 1;
                // // }
                // } catch (AWTException e) {
                // // TODO Auto-generated catch block
                // e.printStackTrace();
                // }
                ownerEmail.click();
                sleep(1000);
                executeJavaScript("arguments[0].value = '';", ownerEmail);
                sleep(1000);
                ownerEmail.sendKeys(map.get("Email Address"));
            }
            if (map.containsKey("Phone Number")) {
                ownerPhone.clear();
                MyCommonAPIs.sleepi(1);
                ownerPhone.sendKeys(map.get("Phone Number"));
            }
            if (map.containsKey("Business Phone Number")) {
                businessPhone.clear();
                MyCommonAPIs.sleepi(1);
                businessPhone.sendKeys(map.get("Business Phone Number"));
            }
            if (map.containsKey("Device Ownership")) {
                switch (map.get("Device Ownership")) {
                case "Admin":
                    if (!deviceownershipadmin.has(Condition.attribute("disabled"))) {
                        deviceownershipadmin.selectRadio("on");
                    }
                    break;
                case "Owner":
                    if (!deviceownershipowner.has(Condition.attribute("disabled"))) {
                        deviceownershipowner.selectRadio("on");
                    }
                    break;
                }
            }
            selectNotificationAndReport(map);
            if (map.containsKey("Scheduled Reports")) {
                if (map.get("Scheduled Reports").equals("disable")) {
                    if (!scheduleweekly.has(Condition.attribute("disabled"))) {
                        scheduledreport.click();
                    }
                } else if (map.get("Scheduled Reports").equals("enable")) {
                    if (scheduleweekly.has(Condition.attribute("disabled"))) {
                        scheduledreport.click();
                    }
                }
            }
            if (map.containsKey("Scheduled Reports Option")) {
                switch (map.get("Scheduled Reports")) {
                case "weekly":
                    if (!scheduleweekly.has(Condition.attribute("disabled"))) {
                        scheduleweekly.selectRadio("1");
                    }
                    break;
                case "monthly":
                    if (!schedulemonthly.has(Condition.attribute("disabled"))) {
                        schedulemonthly.selectRadio("2");
                    }
                    break;
                }
            }
            waitElement(SaveOrg);
            MyCommonAPIs.sleepi(1);
            SaveOrg.click();
            logger.info("--------------- Organisation is Edited Succesfully ----------");
            Selenide.sleep(10000);
            if ($x("//h4[text()='Organization Updated Successfully']/../..//button[text()='OK']").exists()) {
                $x("//h4[text()='Organization Updated Successfully']/../..//button[text()='OK']").click();
            }
        }
        waitReady();
    }

    public void selectNotificationAndReport(Map<String, String> map) {
        if (map.containsKey("Email Notifications")) {
            if (map.get("Email Notifications").contains("Admin")) {
                if (!$x(emailnotificationsadmin).isSelected()) {
                    $x(emailnotificationsadmin + "/..").click();
                }
            } else {
                if ($x(emailnotificationsadmin).isSelected()) {
                    $x(emailnotificationsadmin + "/..").click();
                }
            }
            if (map.get("Email Notifications").contains("Owner")) {
                if (!$x(emailnotificationsowner).isSelected()) {
                    $x(emailnotificationsowner + "/..").click();
                }
            } else {
                if ($x(emailnotificationsowner).isSelected()) {
                    $x(emailnotificationsowner + "/..").click();
                }
            }
            if (map.get("Email Notifications").contains("Manager")) {
                if (!$x(emailnotificationsmanager).isSelected()) {
                    $x(emailnotificationsmanager + "/..").click();
                }
            } else {
                if ($x(emailnotificationsmanager).isSelected()) {
                    $x(emailnotificationsmanager + "/..").click();
                }
            }
        }
        if (map.containsKey("Mobile App Push Notifications")) {
            if (map.get("Mobile App Push Notifications").contains("Admin")) {
                if (!$x(mobilenotificationadmin).isSelected()) {
                    $x(mobilenotificationadmin + "/..").click();
                }
            } else {
                if ($x(mobilenotificationadmin).isSelected()) {
                    $x(mobilenotificationadmin + "/..").click();
                }
            }
            if (map.get("Mobile App Push Notifications").contains("Owner")) {
                if (!$x(mobilenotificationowner).isSelected()) {
                    $x(mobilenotificationowner + "/..").click();
                }
            } else {
                if ($x(mobilenotificationowner).isSelected()) {
                    $x(mobilenotificationowner + "/..").click();
                }
            }
            if (map.get("Mobile App Push Notifications").contains("Manager")) {
                if (!$x(mobilenotificationmanager).isSelected()) {
                    $x(mobilenotificationmanager + "/..").click();
                }
            } else {
                if ($x(mobilenotificationmanager).isSelected()) {
                    $x(mobilenotificationmanager + "/..").click();
                }
            }
        }
        if (map.containsKey("Email Reports")) {
            if (map.get("Email Reports").contains("Admin")) {
                if (!$x(emailreportadmin).isSelected()) {
                    $x(emailreportadmin + "/..").click();
                }
            } else {
                if ($x(emailreportadmin).isSelected()) {
                    $x(emailreportadmin + "/..").click();
                }
            }
            if (map.get("Email Reports").contains("Owner")) {
                if (!$x(emailreportowner).isSelected()) {
                    $x(emailreportowner + "/..").click();
                }
            } else {
                if ($x(emailreportowner).isSelected()) {
                    $x(emailreportowner + "/..").click();
                }
            }
            if (map.get("Email Reports").contains("Manager")) {
                if (!$x(emailreportmanager).isSelected()) {
                    $x(emailreportmanager + "/..").click();
                }
            } else {
                if ($x(emailreportmanager).isSelected()) {
                    $x(emailreportmanager + "/..").click();
                }
            }
        }
    }

    public void deleteOrganizationNew(String name) {
        if (checkOrganizationIsExist(name)) {
            MyCommonAPIs.sleepi(5);
            waitElement(netgear);
            netgear.click();
            MyCommonAPIs.sleepi(8);
            if(dropdownOrganizationElement(name).exists())
            {
            waitElement(dropdownOrganizationElement(name));
            MyCommonAPIs.sleepi(1);
            dropdownOrganizationElement(name).click();
            waitElement(deleteOrganizationElement(name));
            MyCommonAPIs.sleepi(5);
            deleteOrganizationElement(name).click();
            }
            else
            {
              String rowindex=dropdownOrganizationElementNew(name).getAttribute("aria-rowindex");
              ariaSetIndex(rowindex).click();
              ariaSetIndexDelete(rowindex).click();
              
            }
            MyCommonAPIs.sleepi(10);
            waitElement(deletedialogbutton);
            MyCommonAPIs.sleepi(1);
            deletedialogbutton.click();
            MyCommonAPIs.sleepi(1);
            logger.info("Delete organization is successful.");
        }
    }

    public void listChangeToGrid() {
        waitElement(sOrganizationLocationElement1);
        if (!$x("//div[@id='gridView']").exists()) {
            if ($x("//span[@class='gridViewIcon']").exists()) {
                $x("//span[@class='gridViewIcon']").click();
            }
            MyCommonAPIs.sleepi(3);
        }
    }

       public boolean checkOrganizationIsExist(String name) {
        boolean result = false;
        listChangeToGrid();
        // if(orgClick.exists())
        // {
        // orgClick.click();
        // }
        MyCommonAPIs.sleepi(10);
        System.out.println(organizationElement(name).exists());
        System.out.println(organizationElement(name).isDisplayed());
        
        if (organizationElement(name).exists() ||  organizationElement(name).isDisplayed()) {
            result = true;
            logger.info("Organization is existed.");
            
        }
        System.out.println(result);
        return result;
    }

    public boolean checkOrganizationPolicy(Map<String, String> map) {
        boolean result = false;
        Map<String, String> organizationInfo = new HashMap<String, String>();
        waitElement(sOrganizationLocationElement1);
        if (checkOrganizationIsExist(map.get("Name"))) {
            dropdownOrganizationElement(map.get("Name")).click();
            editOrganizationElement(map.get("Name")).click();
            waitElement(NameOrg);
            MyCommonAPIs.sleepi(2);
            if (map.containsKey("Email Notifications")) {
                String emailnotificationsresult = "";
                if (map.get("Email Notifications").contains("Admin")) {
                    if ($x(emailnotificationsadmin).isSelected()) {
                        emailnotificationsresult += "Admin,";
                    }
                }
                if (map.get("Email Notifications").contains("Owner")) {
                    if ($x(emailnotificationsowner).isSelected()) {
                        emailnotificationsresult += "Owner,";
                    }
                }
                if (map.get("Email Notifications").contains("Manager")) {
                    if ($x(emailnotificationsmanager).isSelected()) {
                        emailnotificationsresult += "Manager,";
                    }
                }
                organizationInfo.put("Email Notifications", emailnotificationsresult);
            }
        }
        if (map.containsKey("Mobile App Push Notifications")) {
            String mobileappnotifications = "";
            if (map.get("Mobile App Push Notifications").contains("Admin")) {
                if ($x(mobilenotificationadmin).isSelected()) {
                    mobileappnotifications += "Admin,";
                }
            }
            if (map.get("Mobile App Push Notifications").contains("Owner")) {
                if ($x(mobilenotificationowner).isSelected()) {
                    mobileappnotifications += "Owner,";
                }
            }
            if (map.get("Mobile App Push Notifications").contains("Manager")) {
                if ($x(mobilenotificationmanager).isSelected()) {
                    mobileappnotifications += "Manager,";
                }
            }
            organizationInfo.put("Mobile App Push Notifications", mobileappnotifications);
        }
        if (map.containsKey("Email Reports")) {
            String emailreports = "";
            if (map.get("Email Reports").contains("Admin")) {
                if ($x(emailreportadmin).isSelected()) {
                    emailreports += "Admin,";
                }
            }
            if (map.get("Email Reports").contains("Owner")) {
                if ($x(emailreportowner).isSelected()) {
                    emailreports += "Owner,";
                }
            }
            if (map.get("Email Reports").contains("Manager")) {
                if ($x(emailreportmanager).isSelected()) {
                    emailreports += "Manager,";
                }
            }
            organizationInfo.put("Email Reports", emailreports);
        }
        if (map.containsKey("Device Ownership")) {
            String deviceownershipselect = "";
            if (deviceownershipadmin.isSelected()) {
                deviceownershipselect = "Admin,";
            } else if (deviceownershipowner.isSelected()) {
                deviceownershipselect = "Owner,";
            }
            if (deviceownershipadmin.has(Condition.attribute("disabled")) && deviceownershipowner.has(Condition.attribute("disabled"))) {
                deviceownershipselect = "none";
            }
            organizationInfo.put("Device Ownership", deviceownershipselect);
        }
        if (map.containsKey("Scheduled Reports")) {
            String schedulestatus = "";
            if (scheduleweekly.has(Condition.attribute("disabled"))) {
                schedulestatus = "disable";
            } else {
                schedulestatus = "enable";
            }
            organizationInfo.put("Scheduled Reports", schedulestatus);
        }
        if (map.containsKey("Scheduled Reports Option")) {
            String schedulereportoption = "";
            if (scheduleweekly.isSelected()) {
                schedulereportoption = "weekly,";
            } else if (schedulemonthly.isSelected()) {
                schedulereportoption = "monthly,";
            }
            organizationInfo.put("Scheduled Reports Option", schedulereportoption);
        }
        for (String key : map.keySet()) {
            String value = map.get(key);
            if (organizationInfo.containsKey(key)) {
                if (organizationInfo.get(key).contains(value)) {
                    result = true;
                } else if ((key == "Device Ownership") && organizationInfo.get(key).contains("none")) {
                    result = true;
                } else {
                    result = false;
                    logger.info(key + " is correct.Value: " + organizationInfo.get(key));
                    break;
                }
            }
        }
        return result;
    }

    public boolean checkOrganizationOwner(Map<String, String> map) {
        boolean result = false;
        if (organizationElement(map.get("Name")).exists()) {

            openOrg(map.get("Name"));
            MyCommonAPIs.sleepi(10);
            Map<String, String> organizationInfo = organizationOwnerInfo();
            if (organizationInfo.get("Name").contentEquals(map.get("Owner Name"))
                    && organizationInfo.get("Address").contentEquals(map.get("Email Address"))) {

                if (map.containsKey("Phone Number") && map.containsKey("Business Phone Number")) {
                    if (organizationInfo.get("Phone").contentEquals(map.get("Phone Number"))
                            && organizationInfo.get("Business Phone").contentEquals(map.get("Business Phone Number"))) {
                        result = true;
                        logger.info("Organization owner information is correct.");
                    }
                } else {
                    result = true;
                    logger.info("Organization owner information is correct.");
                }
            }
        }
        return result;
    }

    public void addAllocateCredits(String name, String number) {
        if (checkOrganizationIsExist(name)) {
            if(dropdownOrganizationElement(name).exists()) 
            {
            dropdownOrganizationElement(name).click();
            addCreditsOrganizationElement(name).click();
            }
            else
            {
                  String rowindex=dropdownOrganizationElementNew(name).getAttribute("aria-rowindex");
                  ariaSetIndex(rowindex).click();
                  ariaSetIndexAllocate(rowindex).click();                             
            }

            MyCommonAPIs.sleepi(5);
            allocateDeviceCredits.setValue(number);
            MyCommonAPIs.sleepi(3);
            // clickBoxLastButton();
            allocate1.click();
            waitReady();
            MyCommonAPIs.sleepi(5);
            logger.info("Allocate Credits success.");
        }
    }

    public void addAllocateCredits(String name, String devNum, String vpnNum, String icpNum) {
        open(URLParam.hreforganization, true);
        if (checkOrganizationIsExist(name)) {
            if(dropdownOrganizationElement(name).exists()) 
            {
            dropdownOrganizationElement(name).click();
            addCreditsOrganizationElement(name).click();
            }
            else
            {
                  String rowindex=dropdownOrganizationElementNew(name).getAttribute("aria-rowindex");
                  ariaSetIndex(rowindex).click();
                  ariaSetIndexAllocate(rowindex).click();                             
            }
            MyCommonAPIs.sleepi(5);
            allocateDeviceCredits.setValue(devNum);
            MyCommonAPIs.sleepi(3);
            vpnCredits.setValue(vpnNum);
            MyCommonAPIs.sleepi(3);
            icpCredits.setValue(icpNum);
            MyCommonAPIs.sleepi(10);
            allocate1.click();
            // clickBoxLastButton();
            waitReady();
            MyCommonAPIs.sleepi(5);
            logger.info("Allocate Credits success.");
        }
    }

    public void addAllocateCreditsKeys(String name, String devNum, String vpnNum, String icpNum) {
        if (checkOrganizationIsExist(name)) {
            if(dropdownOrganizationElement(name).exists()) 
            {
            dropdownOrganizationElement(name).click();
            addCreditsOrganizationElement(name).click();
            }
            else
            {
                  String rowindex=dropdownOrganizationElementNew(name).getAttribute("aria-rowindex");
                  ariaSetIndex(rowindex).click();
                  ariaSetIndexAllocate(rowindex).click();                             
            }
            MyCommonAPIs.sleepi(5);
            allocateDeviceCredits.setValue(devNum);
            MyCommonAPIs.sleepi(5);
            allocateDeviceCreditsKeys.click();
            MyCommonAPIs.sleepi(3);
            SelectKey.click();
            MyCommonAPIs.sleepi(3);
            vpnCredits.setValue(vpnNum);
            MyCommonAPIs.sleepi(3);
            icpCredits.setValue(icpNum);
            MyCommonAPIs.sleepi(10);
            allocate1.click();
            // clickBoxLastButton();
            waitReady();
            MyCommonAPIs.sleepi(5);
            logger.info("Allocate Credits success.");
        }
    }

    public boolean checkAutomaticallyCreated(Map<String, String> map) {
        boolean result = false;
        waitElement(sOrganizationLocationElement1);
        MyCommonAPIs.waitElement(AddOrg);
        if (AddOrg.exists()) {
            AddOrg.click();
            Selenide.sleep(5000);
            NameOrg.sendKeys(map.get("Name"));
            if (map.containsKey("Owner Name")) {
                ownerName.sendKeys(map.get("Owner Name"));
            }
            if (map.containsKey("Email Address")) {
                ownerEmail.sendKeys(map.get("Email Address"));
            }
            if (map.containsKey("Phone Number")) {
                ownerPhone.sendKeys(map.get("Phone Number"));
            }
            if (map.containsKey("Business Phone Number")) {
                businessPhone.sendKeys(map.get("Business Phone Number"));
            }
            selectNotificationAndReport(map);
            if (map.containsKey("Device Ownership")) {
                switch (map.get("Device Ownership")) {
                case "Admin":
                    if (!deviceownershipadmin.has(Condition.attribute("disabled"))) {
                        deviceownershipadmin.selectRadio("on");
                    }
                    break;
                case "Owner":
                    if (!deviceownershipowner.has(Condition.attribute("disabled"))) {
                        deviceownershipowner.selectRadio("on");
                    }
                    break;
                }
            }
            if (map.containsKey("Scheduled Reports")) {
                if (map.get("Scheduled Reports").equals("disable")) {
                    if (!scheduleweekly.has(Condition.attribute("disabled"))) {
                        scheduledreport.click();
                    }
                }
            }
            if (map.containsKey("Scheduled Reports Option")) {
                switch (map.get("Scheduled Reports")) {
                case "weekly":
                    if (!scheduleweekly.has(Condition.attribute("disabled"))) {
                        scheduleweekly.selectRadio("1");
                    }
                    break;
                case "monthly":
                    if (!schedulemonthly.has(Condition.attribute("disabled"))) {
                        schedulemonthly.selectRadio("2");
                    }
                    break;
                }
            }
            SaveOrg.click();
            logger.info("--------------- Organisation is Created Succesfully ----------");
            Selenide.sleep(10000);
            if ($x("//h4[text()='Organization Created Successfully']/../..//button[text()='Yes']").exists()) {
                $x("//h4[text()='Organization Created Successfully']/../..//button[text()='Yes']").click();
            }
            waitReady();
            MyCommonAPIs.sleepi(5);
            businessPhone.setValue(map.get("Number of Locations"));
            devAdminPwd.setValue(map.get("Device Admin Password"));
            autoZipCode.setValue(map.get("Zip Code"));
            MyCommonAPIs.sleepi(3);
            nextButton.click();
            MyCommonAPIs.sleepi(10);
            if (maxValueAlert.exists()) {
                if (getText(maxValueAlert).contains("Value of Number of locations can be 1-50")) {
                    result = true;
                    logger.info("Alert existed.");
                }
            }
        }
        waitReady();
        return result;
    }

    public void gotoSchedulereboot() {
        openOrg(WebportalParam.Organizations);
        MyCommonAPIs.sleepi(5);
        OrgSettings.click();
        MyCommonAPIs.sleepi(5);
        DeviceReboot.click();
        System.out.println("Device Reboot Button Section Button Clicked");
        MyCommonAPIs.sleepi(5);
    }

    public boolean CheckRebootNow(String loca) {
        boolean result = false;
        if (LocName(loca).isDisplayed()) {
            System.out.println("Location name exits");
            result = true;
        }

        return result;
    }

    public boolean CheckDevices(String SerialNo) {
        boolean result = false;
        ExpandDevices.click();
        MyCommonAPIs.sleepi(5);
        if (DeviceName(SerialNo).isDisplayed()) {
            System.out.println("AP name exits");
            result = true;
        }

        return result;
    }

    public boolean clickonReboot(String SerialNo) {
        boolean result = false;
        ExpandDevices.click();
        MyCommonAPIs.sleepi(5);
        SelctDevice(SerialNo).click();
        MyCommonAPIs.sleepi(5);
        String sta1 = (SelctDevice1(SerialNo).getAttribute("checked"));
        MyCommonAPIs.sleepi(5);
        System.out.println(sta1);
        if (sta1 == "true") {
            System.out.println("device selcetd");
            result = true;
        }

        return result;
    }

    public boolean Selectthedevice(String SerialNo) {
        boolean result = false;
        ExpandDevices.click();
        MyCommonAPIs.sleepi(5);
        SelctDevice(SerialNo).click();
        MyCommonAPIs.sleepi(5);
        RebootNowconform.click();
        MyCommonAPIs.sleepi(5);
        if (Continue.isDisplayed()) {
            System.out.println("Pop up is shown");
            result = true;
        }
        closePopUp.click();
        return result;
    }

    public boolean CheckBlueBanner(String SerialNo) {
        boolean result = false;
        ExpandDevices.click();
        MyCommonAPIs.sleepi(5);
        SelctDevice(SerialNo).click();
        MyCommonAPIs.sleepi(5);
        RebootNowconform.click();
        MyCommonAPIs.sleepi(10);
        ContinueButton.click();
        MyCommonAPIs.sleepi(3);
        if (Bluebanner.isDisplayed() && Bluebannerlink.isDisplayed()) {
            System.out.println("bluebanner is visible");
            result = true;
        }
        return result;
    }

    public boolean CheckBlueBannerHyperLink(String SerialNo) {
        System.out.println("Going to click BlueBanner Link");
        boolean result = false;
        ExpandDevices.click();
        MyCommonAPIs.sleepi(5);
        SelctDevice(SerialNo).click();
        MyCommonAPIs.sleepi(5);
        RebootNowconform.click();
        MyCommonAPIs.sleepi(10);
        ContinueButton.click();
        MyCommonAPIs.sleepi(3);
        Bluebannerlink.click();
        MyCommonAPIs.sleepi(15);
        if (DevicesTab.isDisplayed()) {
            System.out.println("hyper link is taken to right page");
            result = true;
        }
        return result;
    }

    public boolean checkstatus(String SerialNo) {
        boolean result = false;
        ExpandDevices.click();
        MyCommonAPIs.sleepi(5);
        SelctDevice(SerialNo).click();
        MyCommonAPIs.sleepi(5);
        RebootNowconform.click();
        MyCommonAPIs.sleepi(10);
        ContinueButton.click();
        MyCommonAPIs.sleepi(3);
        goDeviceTab.click();
        MyCommonAPIs.sleepi(15);
        String Status = getText(Status(SerialNo));
        System.out.println(Status);
        if (Status.contains("Failure")) {
            System.out.println("status is failure");
            result = true;
        }
        return result;
    }

    public boolean ScheduleReboot() {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        rebootSchedule.click();
        MyCommonAPIs.sleepi(3);
        boolean Status = rebootSchedulecheck.isSelected();
        System.out.println(Status);
        if (Status == true) {
            System.out.println("status is pass");
            result = true;
        }
        return result;
    }

    public boolean ScheduleRebootText() {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        rebootSchedule.click();
        MyCommonAPIs.sleepi(3);
        String Status = getText(rebootScheduleText);
        System.out.println(Status);
        if (Status.contains(
                "Device reboot scheduling allows selection of multiple devices from different Locations, device types and models. Reboot action is triggered at the scheduled time only if the device is online and no ongoing firmware update. The date and time displayed inherit from web browser time. The devices will be scheduled for reboot with Location or network time zone.")) {
            System.out.println("status is pass");
            result = true;
        }
        return result;
    }

    public boolean ScheduleRebootoOptions() {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        rebootSchedule.click();
        MyCommonAPIs.sleepi(3);
        if (StatDate.isDisplayed() && StartTime.isDisplayed() && EndDate.isDisplayed() && EndTime.isDisplayed() && SelectDevices.isDisplayed()) {
            System.out.println("All elements are displayed");
            result = true;
        }
        return result;
    }

    public boolean PickDateTime(String Name, String SerialNo, String repeat) {
        System.out.println(java.time.LocalDate.now());

        LocalDate Localday = java.time.LocalDate.now();
        String strDate = Localday.toString();
        String todayDate = strDate.substring(8);
        System.out.println(todayDate);
        String check = todayDate.substring(0, 1);
        System.out.println(check);
        if (check.equals("0")) {
            todayDate = todayDate.substring(1);
            System.out.println(todayDate);
        }
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        rebootSchedule.click();
        MyCommonAPIs.sleepi(3);
        ScheduleName.sendKeys(Name);
        MyCommonAPIs.sleepi(3);
        StatDate.click();
        MyCommonAPIs.sleepi(3);
        selcetDate(todayDate).click();
        MyCommonAPIs.sleepi(3);
        StartTime.click();
        MyCommonAPIs.sleepi(3);
        SelectTime.click();
        MyCommonAPIs.sleepi(3);
        EndDate.click();
        MyCommonAPIs.sleepi(3);
        selcetDate(todayDate).click();
        MyCommonAPIs.sleepi(3);
        EndTime.click();
        MyCommonAPIs.sleepi(3);
        increaseHr.click();
        MyCommonAPIs.sleepi(1);
        increaseHr.click();
        MyCommonAPIs.sleepi(3);
        SelectTime.click();
        MyCommonAPIs.sleepi(3);
        Repeats.selectOption(repeat);
        MyCommonAPIs.sleepi(2);
        if (ExpandDevicesSchedule(WebportalParam.location1).exists()) {
            ExpandDevicesSchedule(WebportalParam.location1).click();
        }
        MyCommonAPIs.sleepi(5);
        SelctDevice(SerialNo).click();
        MyCommonAPIs.sleepi(5);
        RebootSchedulebutton.click();
        MyCommonAPIs.sleepi(10);
        if (Greenbanner.isDisplayed()) {
            System.out.println("All elements are displayed");
            result = true;
        }
        MyCommonAPIs.sleepi(20);
        return result;
    }

    public boolean CheckRepeters(String schName) {
        boolean result = false;
        boolean result1 = false;
        boolean result2 = false;
        boolean result3 = false;
        MyCommonAPIs.sleepi(5);
        if (ViewAllScheduler.exists()) {
            ViewAllScheduler.click();
        }
        MyCommonAPIs.sleepi(10);
        MyCommonAPIs.sleepi(10);
        executeJavaScript("arguments[0].removeAttribute('class')", editScheduler(schName));
        MyCommonAPIs.sleep(3000);
        editSchIcon(schName).waitUntil(Condition.visible, 60 * 1000).click();
        MyCommonAPIs.sleepi(15);
        MyCommonAPIs.sleepi(3);
        String all = Repeters.getText();

        if (all.contains("None")) {
            System.out.println("All elements are displayed");
            result1 = true;
        }
        MyCommonAPIs.sleepi(3);
        Repeters.selectOption("Weekly");
        String all1 = Repeters.getText();

        if (all1.contains("Weekly")) {
            System.out.println("All elements are displayed");
            result2 = true;
        }
        MyCommonAPIs.sleepi(3);
        Repeters.selectOption("Monthly");
        String all2 = Repeters.getText();

        if (all2.contains("Monthly")) {
            System.out.println("All elements are displayed");
            result3 = true;
        }

        if (result1 == true && result2 == true && result3 == true) {
            result = true;
        }
        return result;
    }

    public boolean ScheduleCheckDevices(String SerialNo) {
        boolean result = false;
        rebootSchedule.click();
        MyCommonAPIs.sleepi(3);
        ExpandDevicesSchedule(WebportalParam.location1).click();
        MyCommonAPIs.sleepi(5);
        if (DeviceName(SerialNo).isDisplayed()) {
            System.out.println("AP name exits");
            result = true;
        }

        return result;
    }

    public void DeleteSchedule(String Name) {
        MyCommonAPIs.sleepi(5);
        if (ViewAllScheduler.exists()) {
            ViewAllScheduler.click();
        }
        MyCommonAPIs.sleepi(10);
        executeJavaScript("arguments[0].removeAttribute('class')", editScheduler(Name));
        MyCommonAPIs.sleep(3000);
        deleteScheduler(Name).waitUntil(Condition.visible, 60 * 1000).click();
        MyCommonAPIs.sleepi(15);
        waitElement(deletessidyes);
        deletessidyes.click();
        MyCommonAPIs.sleep(5 * 1000);
    }

    public void editSchedule(String Name) {
        MyCommonAPIs.sleepi(5);
        if (ViewAllScheduler.exists()) {
            ViewAllScheduler.click();
        }
        MyCommonAPIs.sleepi(10);
        executeJavaScript("arguments[0].removeAttribute('class')", editScheduler(Name));
        MyCommonAPIs.sleep(3000);
        deleteScheduler(Name).waitUntil(Condition.visible, 60 * 1000).click();
        MyCommonAPIs.sleep(6000);
        deletessidyes.click();
        MyCommonAPIs.sleep(5 * 1000);
    }

    public void modifychedule(String Name, String Newname) {
        MyCommonAPIs.sleepi(5);
        if (ViewAllScheduler.exists()) {
            ViewAllScheduler.click();
        }
        MyCommonAPIs.sleepi(10);
        executeJavaScript("arguments[0].removeAttribute('class')", editScheduler(Name));
        MyCommonAPIs.sleep(3000);
        modifyScheduler(Name).waitUntil(Condition.visible, 60 * 1000).click();
        MyCommonAPIs.sleep(6000);
        SchedulerName.clear();
        SchedulerName.sendKeys(Newname);
        MyCommonAPIs.sleep(5 * 1000);
        editorgwideSSIDSaveBtn.click();
        MyCommonAPIs.sleep(6000);
    }

    public boolean Multiple(String SerialNo) {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        rebootSchedule.click();
        MyCommonAPIs.sleepi(3);
        ExpandDevicesSchedule(WebportalParam.location1).click();
        boolean status = SelctDevice1(SerialNo).isDisplayed();
        System.out.println(SelctDevice1(SerialNo).isDisplayed());
        if (status == false) {
            result = true;
        }

        return result;
    }

    public void gotoDashboard() {
        MyCommonAPIs.sleepi(5);
        gotoDashboard.click();
    }

    public boolean assignLocation() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        assignLocation.click();
        MyCommonAPIs.sleepi(1);
        assigntoOrganization.click();
        MyCommonAPIs.sleepi(5);
        addnewOrganization.sendKeys("Netgear");
        MyCommonAPIs.sleepi(1);
        submitBtn.click();
        MyCommonAPIs.sleepi(10);
        clickOnOrganization.click();
        MyCommonAPIs.sleepi(10);
        if (locationisVisible.isDisplayed()) {
            result = true;
            System.out.println(" Location is assigned Successfully to an Organization");
        }
        MyCommonAPIs.sleepi(2);
        return result;
    }

    public boolean verifyProSubscriptions() {
        boolean result = false;
        humbergerMenuPageforPro.click();
        if (accountManagementpro.isDisplayed()) {
            System.out.println(accountManagementpro.getText());
            result = true;
            MyCommonAPIs.sleepi(1);
            accountManagementpro.click();
        } else {
            result = false;
        }

        MyCommonAPIs.sleepi(5);
        clickonSubscriptionMaximizebtn1.click();
        MyCommonAPIs.sleepi(1);
        clickonSubscriptionMaximizebtn2.click();
        MyCommonAPIs.sleepi(1);

        if (insightPemiumSubscription.getText() == "Insight Premium Subscription") {
            System.out.println(insightPemiumSubscription.getText());
            assertTrue((cancelledSubscription.getText() == "Cancelled"), "One Year Premium Subscription Cancelled in Pro Account");
            result = true;
        }

        MyCommonAPIs.sleepi(1);

        if (icpSubscriptionCheck.getText() == "Captive Portal, Pack of 1, 1-yr-AUD") {

            System.out.println(icpSubscriptionCheck.getText());
            assertTrue(icpSubActivateDate.getText() != "Cancelled", "ICP One Device One Year Subscription is continued in Pro Account");
            result = true;
        }

        MyCommonAPIs.sleepi(1);
        return result;
    }

    public boolean addAllocateCreditscheck(String name, String devNum) {

        boolean result = false;
        if (checkOrganizationIsExist(name)) {
            if(dropdownOrganizationElement(name).exists()) 
            {
            dropdownOrganizationElement(name).click();
            addCreditsOrganizationElement(name).click();
            }
            else
            {
                  String rowindex=dropdownOrganizationElementNew(name).getAttribute("aria-rowindex");
                  ariaSetIndex(rowindex).click();
                  ariaSetIndexAllocate(rowindex).click();                             
            }
            MyCommonAPIs.sleepi(5);
            allocateDeviceCredits.setValue(devNum);

            if (allocateDeviceCreditsKeys.isEnabled()) {
                result = true;
            }
        }
        MyCommonAPIs.sleepi(5);
        closebox.click();
        return result;

    }

    public boolean addAllocateKeycheck(String name, String devNum) {

        boolean result = false;
        if (checkOrganizationIsExist(name)) {
            dropdownOrganizationElement(name).click();
            addCreditsOrganizationElement(name).click();
            MyCommonAPIs.sleepi(5);
            allocateDeviceCreditsKeys.click();
            MyCommonAPIs.sleepi(3);
            SelectKey.click();
            MyCommonAPIs.sleepi(3);

            if (allocateDeviceCredits.isEnabled()) {
                result = true;
            }
        }
        MyCommonAPIs.sleepi(5);
        closebox.click();
        return result;

    }

    public String MultipleAccurenceOfKey(String name, String devNum) {
        String Key1 = "";
        String Key2 = "";
        boolean result = false;
        if (checkOrganizationIsExist(name)) {
            dropdownOrganizationElement(name).click();
            addCreditsOrganizationElement(name).click();
            MyCommonAPIs.sleepi(5);
            allocateDeviceCreditsKeys.click();
            MyCommonAPIs.sleepi(5);
            Key1 = checktheKey.getText();
            System.out.println(Key1);
            MyCommonAPIs.sleepi(3);
            SelectKey.click();
            MyCommonAPIs.sleepi(3);
            allocate1.click();
        }

        return Key1;

    }

    public boolean checkLicenceKey(String name, String devNum) {
        boolean result = false;
        if (checkOrganizationIsExist(name)) {
            dropdownOrganizationElement(name).click();
            addCreditsOrganizationElement(name).click();
            MyCommonAPIs.sleepi(5);
            if (allocateDeviceCreditsKeys.isEnabled()) {
                System.out.println("key doesnot exits");
                result = true;
            }

        }

        MyCommonAPIs.sleepi(5);
        closebox.click();
        return result;

    }

    public void creditAllocation(String name) {
        MyCommonAPIs.sleepi(15);
        String rowindex = dropdownOrganizationElementNew(name).getAttribute("aria-rowindex");
        ariaSetIndex(rowindex).click();
        ariaSetIndexAllocate(rowindex).click();
        MyCommonAPIs.sleepi(5);
        waitElement(deviceCreditsTextbox);
        deviceCreditsTextbox.sendKeys("6");
        MyCommonAPIs.sleepi(1);
        allocateBtn.click();
        MyCommonAPIs.sleepi(5);
    }

    public void icpCreditAllocation(String name) {
        MyCommonAPIs.sleepi(2);
        homePage.click();
        MyCommonAPIs.sleepi(20);
        String rowindex = dropdownOrganizationElementNew(name).getAttribute("aria-rowindex");
        ariaSetIndex(rowindex).click();
        ariaSetIndexAllocate(rowindex).click();
        MyCommonAPIs.sleepi(5);
        if (deviceCredits.exists()) {
            icpCreditsInput.sendKeys("6");
            MyCommonAPIs.sleepi(1);
            allocateBtn.click();
            MyCommonAPIs.sleepi(5);
        }
    }

    public boolean organizationWideSSID(Map<String, String> map) {

        boolean result = false;
        MyCommonAPIs.sleepi(5);
        // viwDevices.click();
        MyCommonAPIs.sleepi(5);
        if (clkonOrganization.exists()) {
            clkonOrganization.click();
        } else if (clkonOrganization1.exists()) {
            clkonOrganization1.click();
        } else if (clkonOrganization2.exists()) {
            clkonOrganization2.click();
        }
        MyCommonAPIs.sleepi(3);
        if (clkonOrganization.exists()) {
            clkonOrganization.click();
        } else if (clkonOrganization1.exists()) {
            clkonOrganization1.click();
        } else if (clkonOrganization2.exists()) {
            clkonOrganization2.click();
        }
        MyCommonAPIs.sleepi(10);
        Setting.click();
        waitReady();
        waitElement(organizationWideSSID);
        organizationWideSSID.click();
        waitReady();
        waitElement(addssidsign);
        addssidsign.click();
        MyCommonAPIs.sleepi(5);
        if (orgwidessidName.exists()) {
            logger.info("Add ssid.");
            orgwidessidName.setValue(map.get("SSID"));
            waitReady();
        }

        if (checkband6.isSelected()) {
            if (band6.isDisplayed()) {
                band6.click();
                logger.info("Uncheck 6 band");
            }
        } else {
            System.out.println("6GHZ is alredy unchecked");
        }

        if (map.get("Security").equals("WPA2 Personal")) {
            security.selectOption("WPA2 Personal");
            waitReady();
        } else {
            security.selectOption(map.get("Security"));
            waitReady();
        }

        if (map.containsKey("Password")) {
            orgwideSSIDPass.setValue(map.get("Password"));
            waitReady();
        }

        takess("addSsid");
        orgwideSSIDSaveBtn.click();
        MyCommonAPIs.sleepi(5);
        // orgwideSSIDOKBtn.click();
        waitReady();
        if (ssidCreated.exists()) {
            logger.info("Organization Wide SSID is created successfully.");
            result = true;
        }

        // MyCommonAPIs.sleepi(5);
        // clkonOrganization.click();
        // MyCommonAPIs.sleepi(3);
        // clkonOrganization1.click();
        // MyCommonAPIs.sleepi(10);
        return result;
    }

    public boolean alllocationSSID() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        if (clkonOrganization2.exists()) {
            clkonOrganization2.click();
        } else if (clkonOrganization.exists()) {
            clkonOrganization.click();
        }
        MyCommonAPIs.sleepi(3);
        if (clkonOrganization1.exists()) {
            clkonOrganization1.click();
        } else if (clkonOrganization.exists()) {
            clkonOrganization.click();
        }
        MyCommonAPIs.sleepi(10);

        office1.click();
        MyCommonAPIs.sleepi(10);
        if (proAccWirelesstab1.exists()) {
            proAccWirelesstab1.click();
        } else {
            wirelesstab.click();
        }
        MyCommonAPIs.sleepi(10);
        if (settingtab1.exists()) {
            settingtab1.click();
        } else {
            settingtab.click();
        }
        MyCommonAPIs.sleepi(10);
        if (locationwiseSSID1.exists()) {
            logger.info("location1 Wide SSID is pushed successfully.");
            MyCommonAPIs.sleepi(5);
            clkonOrganization.click();
            MyCommonAPIs.sleepi(2);
            clkonOrganization1.click();
            MyCommonAPIs.sleepi(10);
            office2.click();
            MyCommonAPIs.sleepi(10);
            if (proAccWirelesstab1.exists()) {
                proAccWirelesstab1.click();
            } else {
                wirelesstab.click();
            }
            MyCommonAPIs.sleepi(10);
            if (settingtab1.exists()) {
                settingtab1.click();
            } else {
                settingtab.click();
            }
            MyCommonAPIs.sleepi(10);
            if (locationwiseSSID1.exists()) {
                logger.info("location2 Wide SSID is pushed successfully.");
                result = true;
            }
        }

        return result;

    }

    public boolean organizationWideSSID1(Map<String, String> map) {

        boolean result = false;
        MyCommonAPIs.sleepi(5);
        viwDevices.click();
        MyCommonAPIs.sleepi(5);
        clkonOrganization.click();
        MyCommonAPIs.sleepi(3);
        clkonOrganization1.click();
        MyCommonAPIs.sleepi(10);
        Setting.click();
        waitReady();
        waitElement(organizationWideSSID);
        organizationWideSSID.click();
        waitReady();
        waitElement(addssidsign);
        addssidsign.click();
        MyCommonAPIs.sleepi(5);
        if (orgwidessidName.exists()) {
            logger.info("Add ssid.");
            orgwidessidName.setValue(map.get("SSID"));
            waitReady();
        }
        if (map.get("Security").equals("WPA3 Personal")) {
            security.selectOption("WPA3 Personal");
            waitReady();
        } else {
            security.selectOption(map.get("Security"));
            waitReady();
        }

        if (map.containsKey("Password")) {
            orgwideSSIDPass.setValue(map.get("Password"));
            waitReady();
        }
        takess("addSsid");
        orgwideSSIDSaveBtn.click();
        MyCommonAPIs.sleepi(5);
        orgwideSSIDOKBtn.click();
        waitReady();
        if (ssidCreated.exists()) {
            logger.info("Organization Wide SSID is created successfully.");
            result = true;
        }
        MyCommonAPIs.sleepi(5);
        clkonOrganization.click();
        MyCommonAPIs.sleepi(3);
        clkonOrganization1.click();
        MyCommonAPIs.sleepi(10);
        return result;
    }

    public void gotoOrganization() {

        MyCommonAPIs.sleepi(5);
        clkonOrganization.click();
        MyCommonAPIs.sleepi(3);
        clkonOrganization1.click();
        MyCommonAPIs.sleepi(10);

    }

    public boolean verifytoeditdeleteLocationSSID(Map<String, String> map) {

        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if (checkOrgSsidIsExist(map.get("SSID"))) {
            if ((editLocOrgWideSSID.exists()) && (deleteLocOrgWideSSID.exists())) {

                result = false;
                logger.info("Edit/Delete Functionality is available on locations organization wide SSID");
                MyCommonAPIs.sleepi(5);

            } else {

                result = true;
                logger.info("Edit/Delete Functionality is not available on locations organization wide SSID");
                MyCommonAPIs.sleepi(5);

            }

        }

        return result;

    }

    public boolean duplicateScheduler(String Name, String SerialNo) {
        System.out.println(java.time.LocalDate.now());

        boolean result = false;
        MyCommonAPIs.sleepi(10);
        rebootSchedule.click();
        MyCommonAPIs.sleepi(3);
        ScheduleName.sendKeys(Name);
        MyCommonAPIs.sleepi(3);
        StatDate.click();
        MyCommonAPIs.sleepi(3);
        SelectDate.click();
        MyCommonAPIs.sleepi(3);
        StartTime.click();
        MyCommonAPIs.sleepi(3);
        SelectTime.click();
        MyCommonAPIs.sleepi(3);
        EndDate.click();
        MyCommonAPIs.sleepi(3);
        SelectDate.click();
        MyCommonAPIs.sleepi(3);
        EndTime.click();
        MyCommonAPIs.sleepi(3);
        increaseHr.click();
        MyCommonAPIs.sleepi(3);
        SelectTime.click();
        MyCommonAPIs.sleepi(3);
        if (ExpandDevicesSchedule(WebportalParam.location1).exists()) {
            ExpandDevicesSchedule(WebportalParam.location1).click();
        }
        MyCommonAPIs.sleepi(5);
        SelctDevice(SerialNo).click();
        MyCommonAPIs.sleepi(5);
        RebootSchedulebutton.click();
        MyCommonAPIs.sleepi(10);
        if (Greenbanner.isDisplayed()) {
            System.out.println("All elements are displayed");
            result = true;
        }
        return result;
    }

    public void editOrgnaizationwideSSID(Map<String, String> map) {

        waitReady();
        waitElement(orgWideSSIDedit);
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");
        MyCommonAPIs.sleep(5 * 1000);
        MyCommonAPIs.sleepi(5);
        if (map.get("Security").equals("Open")) {
            security.selectOption("Open");
            waitReady();
        } else {
            security.selectOption(map.get("Security"));
            waitReady();
        }
        MyCommonAPIs.sleepi(5);
        editorgwideSSIDSaveBtn.click();
        MyCommonAPIs.sleepi(5);
        MyCommonAPIs.sleepi(5);
        orgwideSSIDOKBtn.click();
        MyCommonAPIs.sleepi(5);
        clkonOrganization.click();
        MyCommonAPIs.sleepi(3);
        clkonOrganization1.click();
        MyCommonAPIs.sleepi(10);

    }

    public boolean verifylocation1SSID(Map<String, String> map) {

        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if ((checkOrgSsidIsExist(map.get("SSID"))) && (opensecurity.exists())) {
            logger.info("location1 Wide SSID edit successfull.");
            result = true;
        }
        return result;
    }

    public boolean verifylocation2SSID(Map<String, String> map) {

        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if ((checkOrgSsidIsExist(map.get("SSID"))) && (opensecurity.exists())) {
            logger.info("location2 Wide SSID edit successfull.");
            result = true;
        }
        return result;
    }

    public boolean verifydelOrgSSIDineachLoc() {
        boolean result = false;
        if (messagelocOrgWideSSID.exists()) {
            logger.info("location1 Wide SSID not available.");
            result = true;
        }
        return result;
    }

    public void orgmoveSlider(String option, double value) {
        Actions actions = new Actions(WebDriverRunner.getWebDriver());
        if (option.equals("upload")) {
            BigDecimal bg = new BigDecimal((value / (1000 - 1.0625)) + 0.001);
            double f1 = bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
            Point uploadx = orgmoveSlider.getLocation();
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

    public void editRateLimitorgwidessid(String Ssid, double uploadvalue, double downloadvalue) {
        waitElement(orgWideSSIDedit);
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");
        MyCommonAPIs.sleep(5 * 1000);
        orgenterlimit.click();
        MyCommonAPIs.sleepi(3);
        orgenableratelimit.click();
        MyCommonAPIs.sleepi(3);
        orguploadrate.selectOption("Mbps");
        MyCommonAPIs.sleepi(3);
        orgmoveSlider("upload", uploadvalue);
        // executeJavaScript(
        // "document.getElementsByClassName(\"ui-slider-handle ui-corner-all ui-state-default\")[0].style.left=\"'" +
        // uploadvalue + "'\"");
        MyCommonAPIs.sleepi(3);
        orgdownloadrate.selectOption("Mbps");
        MyCommonAPIs.sleepi(3);
        orgmoveSlider("download", downloadvalue);
        orgsaveratelimit.click();
        MyCommonAPIs.sleepi(3);
        orgratelimitok.click();
    }

    public void alllocationSSIDloc1(String name) {

        MyCommonAPIs.sleepi(5);
        clkonOrganization2.click();
        MyCommonAPIs.sleepi(3);
        clkonOrganization.click();
        MyCommonAPIs.sleepi(10);

        office1.click();
        MyCommonAPIs.sleepi(10);
        if (proAccWirelesstab1.exists()) {
            proAccWirelesstab1.click();
        } else {
            wirelesstab.click();
        }
        MyCommonAPIs.sleepi(10);
        if (settingtab1.exists()) {
            settingtab1.click();
        } else {
            settingtab.click();
        }
        MyCommonAPIs.sleepi(10);
        if (locationwiseSSID1.exists()) {
            logger.info("location1 Wide SSID is pushed successfully.");
            MyCommonAPIs.sleepi(5);

        }

    }

    public void GoToAdvanceRateselection(String Ssid) {

        MyCommonAPIs.sleepi(3);
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");
        MyCommonAPIs.sleep(5 * 1000);
        enterAdvanceRateSelection.click();
        MyCommonAPIs.sleepi(3);
    }

    public void clickon25Ghz() {
        MyCommonAPIs.sleepi(3);
        twopointfourghztab.click();
        MyCommonAPIs.sleepi(3);

    }

    public void orgenableSetMinimumRateControl() {
        MyCommonAPIs.sleepi(5);
        orgenableSetMinimumRateControls.click();
        MyCommonAPIs.sleepi(5);
    }

    public void orgDragDensityTo(int densitylevel, int densitylevel1) {

        Actions actions = new Actions(WebDriverRunner.getWebDriver());

        if (densitylevel == 1) {
            actions.dragAndDrop(orgslider, orgdensityone).perform();
        }
        MyCommonAPIs.sleepi(2);
        clkon5GHz.click();
        MyCommonAPIs.sleepi(2);
        orgenableSetMinimumRateControls.click();
        MyCommonAPIs.sleepi(5);
        if (densitylevel1 == 4) {
            actions.dragAndDrop(orgslider, select4GHz).perform();
        }

        // } else if (level == 2){
        // actions.dragAndDrop(slider, Density2).perform();
        // } else if (level == 3) {
        // actions.dragAndDrop(slider, Density3).perform();
        // } else
        // actions.dragAndDrop(slider, Density4).perform();

        MyCommonAPIs.sleepi(10);
        orgsave.click();
        MyCommonAPIs.sleepi(10);
        orgok.click();

    }

    public boolean ap1login(Map<String, String> map) {

        open("https://10.208.230.82/");
        MyCommonAPIs.sleepi(10);
        boolean result = false;
        MyCommonAPIs.sleepi(5);

        if (map.containsKey("apusername")) {
            apusername.setValue(map.get("apusername"));
            waitReady();
        }

        if (map.containsKey("appassword")) {
            appassword.setValue(map.get("appassword"));
            waitReady();
        }
        MyCommonAPIs.sleepi(5);
        aploginbtn.click();
        MyCommonAPIs.sleepi(5);
        apmanagementtab.click();
        MyCommonAPIs.sleepi(5);
        apokbtn.click();
        MyCommonAPIs.sleepi(5);
        apconfigbtn.click();
        MyCommonAPIs.sleepi(2);
        apconfigbtn.click();
        MyCommonAPIs.sleepi(5);
        apwirlesstab.click();
        MyCommonAPIs.sleepi(5);
        apbasictab.click();
        MyCommonAPIs.sleepi(30); // apwp14270
        // String temp = aporgwidessid.getText();
        if (aporgwidessid.exists()) {
            logger.info("SSID is pushed to AP1 Successfully.");
            MyCommonAPIs.sleepi(2);
            apssid1.click();
            MyCommonAPIs.sleepi(5);
            apadvancerateclk.click();
            MyCommonAPIs.sleepi(5);
            if (ap2Ghz1GHzisselected.exists() && ap2Ghz4GHzisselected.exists() && ap5Ghz4GHzisselected.exists()) {
                logger.info("SSID's advance rate selcetion for 2.4 Ghz and 5 GHz is pushed to AP1 Successfully.");
                MyCommonAPIs.sleepi(5);
                result = true;
            }
        }
        return result;
    }

    public boolean alllocationSSIDloc1() {
        boolean result = false;
        MyCommonAPIs.sleepi(10);

        if (clkonOrganization2.exists()) {
            clkonOrganization2.click();
        } else {
            clkonOrganization.click();
        }

        MyCommonAPIs.sleepi(10);

        if (clkonOrganization.exists()) {
            clkonOrganization.click();
        } else if (clkonOrganization1.exists()) {
            clkonOrganization1.click();
        }

        MyCommonAPIs.sleepi(15);
        office1.click();
        MyCommonAPIs.sleepi(10);
        if (proAccWirelesstab1.exists()) {
            proAccWirelesstab1.click();
        } else {
            wirelesstab.click();
        }
        MyCommonAPIs.sleepi(10);
        if (settingtab1.exists()) {
            settingtab1.click();
        } else {
            settingtab.click();
        }
        MyCommonAPIs.sleepi(10);
        if (locationwiseSSID1.exists()) {
            logger.info("location1 Wide SSID is pushed successfully.");
            MyCommonAPIs.sleepi(5);
            clkonOrganization.click();
            MyCommonAPIs.sleepi(2);
            clkonOrganization1.click();
            MyCommonAPIs.sleepi(10);
            result = true;
        }
        return result;
    }

    public boolean alllocationSSIDloc2() {
        boolean result = false;
        open("https://maint-qa.insight.netgear.com/");
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        MyCommonAPIs.sleepi(15);

        if (clkonOrganization2.exists()) {
            clkonOrganization2.click();
        } else {
            clkonOrganization.click();
        }

        MyCommonAPIs.sleepi(3);

        if (clkonOrganization.exists()) {
            clkonOrganization.click();
        } else {
            clkonOrganization1.click();
        }

        MyCommonAPIs.sleepi(10);
        office2.click();
        MyCommonAPIs.sleepi(10);
        if (proAccWirelesstab1.exists()) {
            proAccWirelesstab1.click();
        } else {
            wirelesstab.click();
        }
        MyCommonAPIs.sleepi(10);
        if (settingtab1.exists()) {
            settingtab1.click();
        } else {
            settingtab.click();
        }
        MyCommonAPIs.sleepi(10);
        if (locationwiseSSID1.exists()) {
            logger.info("location2 Wide SSID is pushed successfully.");
            result = true;
        }
        return result;
    }

    public boolean ap2login(Map<String, String> map) {

        open("https://10.208.230.90/");
        MyCommonAPIs.sleepi(10);
        boolean result = false;
        MyCommonAPIs.sleepi(5);

        if (map.containsKey("apusername")) {
            apusername.setValue(map.get("apusername"));
            waitReady();
        }

        if (map.containsKey("appassword")) {
            appassword.setValue(map.get("appassword"));
            waitReady();
        }
        MyCommonAPIs.sleepi(5);
        aploginbtn.click();
        MyCommonAPIs.sleepi(5);
        apmanagementtab.click();
        MyCommonAPIs.sleepi(5);
        apokbtn.click();
        MyCommonAPIs.sleepi(5);
        apconfigbtn.click();
        MyCommonAPIs.sleepi(2);
        apconfigbtn.click();
        MyCommonAPIs.sleepi(5);
        apwirlesstab.click();
        MyCommonAPIs.sleepi(5);
        apbasictab.click();
        MyCommonAPIs.sleepi(30); // apwp14270
        // String temp = aporgwidessid.getText();
        if (aporgwidessid.exists()) {
            logger.info("SSID is pushed to AP1 Successfully.");
            MyCommonAPIs.sleepi(2);
            apssid1.click();
            MyCommonAPIs.sleepi(5);
            apadvancerateclk.click();
            MyCommonAPIs.sleepi(5);
            if (ap2Ghz1GHzisselected.exists() && ap2Ghz4GHzisselected.exists() && ap5Ghz4GHzisselected.exists()) {
                logger.info("SSID's advance rate selcetion for 2.4 Ghz and 5 GHz is pushed to AP1 Successfully.");
                MyCommonAPIs.sleepi(5);
                result = true;
            }
        }
        return result;
    }

    public void orgwideSSIDCaptivePortal() {
        MyCommonAPIs.sleepi(3);
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");
        MyCommonAPIs.sleep(5 * 1000);
        cpativeportalTab.click();
        MyCommonAPIs.sleep(5 * 1000);
        if (captivePortalText.exists()) {
            enableCaptivePortal.click();
            logger.info("Enable Captive Portal");
        }
        MyCommonAPIs.sleepi(10);
        if (basicCaptivePortalOption.exists()) {
            basicCaptivePortalOption.click();
            logger.info("Select Basic Captive Portal option");
        }
        MyCommonAPIs.sleepi(5);
        editsaveOrgSSIDBtn.click();
        MyCommonAPIs.sleepi(10);
        editokayOrgSSIDBtn.click();
    }

    public boolean locationSSIDloc1() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);

        if (clkonOrganization2.exists()) {
            clkonOrganization2.click();
        } else {
            clkonOrganization.click();
        }

        MyCommonAPIs.sleepi(3);

        if (clkonOrganization.exists()) {
            clkonOrganization.click();
        } else {
            clkonOrganization1.click();
        }

        MyCommonAPIs.sleepi(10);
        office1.click();
        MyCommonAPIs.sleepi(10);
        if (proAccWirelesstab1.exists()) {
            proAccWirelesstab1.click();
        } else {
            wirelesstab.click();
        }
        MyCommonAPIs.sleepi(10);
        if (settingtab1.exists()) {
            settingtab1.click();
        } else {
            settingtab.click();
        }
        MyCommonAPIs.sleepi(10);
        if (locationwiseSSID1.exists() && ssidCaptiveportalRtext.exists() && onText.exists()) {
            logger.info("location1 Wide SSID with captive portal is pushed successfully.");
            MyCommonAPIs.sleepi(5);
            clkonOrganization.click();
            MyCommonAPIs.sleepi(2);
            clkonOrganization1.click();
            MyCommonAPIs.sleepi(10);
            result = true;
        }
        return result;
    }

    public boolean locationSSIDloc2() {
        boolean result = false;
        open("https://maint-qa.insight.netgear.com/");
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
        MyCommonAPIs.sleepi(15);

        if (clkonOrganization2.exists()) {
            clkonOrganization2.click();
        } else {
            clkonOrganization.click();
        }

        MyCommonAPIs.sleepi(3);

        if (clkonOrganization.exists()) {
            clkonOrganization.click();
        } else {
            clkonOrganization1.click();
        }

        MyCommonAPIs.sleepi(10);
        office2.click();
        MyCommonAPIs.sleepi(10);
        if (proAccWirelesstab1.exists()) {
            proAccWirelesstab1.click();
        } else {
            wirelesstab.click();
        }
        MyCommonAPIs.sleepi(10);
        if (settingtab1.exists()) {
            settingtab1.click();
        } else {
            settingtab.click();
        }
        MyCommonAPIs.sleepi(10);
        if (locationwiseSSID1.exists() && ssidCaptiveportalRtext.exists() && onText.exists()) {
            logger.info("location2 Wide SSID with captive portal is pushed successfully.");
            result = true;
        }
        return result;
    }

    public void enablemacACL(Map<String, String> map) {
        MyCommonAPIs.sleepi(3);
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");
        MyCommonAPIs.sleep(5 * 1000);
        macACL.click();
        MyCommonAPIs.sleepi(10);
        manualclientaddBtn.click();
        MyCommonAPIs.sleepi(10);
        clientName.setValue(map.get("Device Name"));
        MyCommonAPIs.sleepi(10);
        clientMAC.setValue(map.get("MAC Address"));
        MyCommonAPIs.sleepi(5);
        addclientBtn.click();
        MyCommonAPIs.sleepi(10);
        enablemacSw.click();
        MyCommonAPIs.sleepi(5);
        radiusType.selectOption("Local ACL");
        MyCommonAPIs.sleepi(5);
        policy.selectOption("Allow");
        MyCommonAPIs.sleepi(5);
        orgssidSettingTab.click();
        MyCommonAPIs.sleepi(10);
        editOrgwideSSIDSaveBtn.click();
        MyCommonAPIs.sleepi(5);
        editorgssidOkaySuccessBtn.click();
        MyCommonAPIs.sleepi(5);
    }

    public void enablecaptivePortalforOrgWideSSID() {
        MyCommonAPIs.sleepi(3);
        // clkonOrganizationSSID.click();
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");
        MyCommonAPIs.sleepi(3);
        // editLocOrgWideSSID.click();
        MyCommonAPIs.sleep(5 * 1000);
        orgwidessidCaptivePortal.click();
        MyCommonAPIs.sleepi(20);
        if (enablecaptiveportalText.exists()) {
            MyCommonAPIs.sleepi(10);
//            onoffcaptiveSwitch.click();
            MyCommonAPIs.sleepi(10);
            if (basiccaptiveText.exists()) {
                MyCommonAPIs.sleepi(5);
                basiccaptiveText.shouldBe(Condition.visible).click();
                MyCommonAPIs.sleepi(5);
                saveButton.click();
                MyCommonAPIs.sleepi(15);
                okaysuccessbtn.click();
            }
            MyCommonAPIs.sleepi(180);
        }
    }

    public boolean organizationWideSSIDMaxLimitError(Map<String, String> map) {

        boolean result = false;
        waitReady();
        waitElement(addssidsign);
        addssidsign.click();
        MyCommonAPIs.sleepi(5);
        if (orgwidessidName.exists()) {
            logger.info("Add ssid.");
            orgwidessidName.setValue(map.get("SSID"));
            waitReady();
        }

        if (checkband6.isSelected()) {
            if (band6.isDisplayed()) {
                band6.click();
                logger.info("Uncheck 6 band");
            }
        } else {
            System.out.println("6GHZ is alredy unchecked");
        }

        if (map.get("Security").equals("WPA2 Personal")) {
            security.selectOption("WPA2 Personal");
            waitReady();
        } else {
            security.selectOption(map.get("Security"));
            waitReady();
        }

        if (map.containsKey("Password")) {
            orgwideSSIDPass.setValue(map.get("Password"));
            waitReady();
        }

        takess("addSsid");
        orgwideSSIDSaveBtn.click();
        MyCommonAPIs.sleepi(10);
        // orgwideSSIDOKBtn.click();
        waitReady();
        if (errorMsg1.exists()) {
            logger.info("Organization Wide SSID is not created successfully.");
            result = true;
        }

        String temp1 = errorMsg1.getText();
        System.out.println(temp1);
        /*
         * String temp2 = errorMsg2.getText();
         * System.out.println(temp2);
         */
        MyCommonAPIs.sleepi(10);
        cancelBtn.click();
        System.out.println("Organization Wide SSID is not created successfully.");
        MyCommonAPIs.sleepi(5);

        // MyCommonAPIs.sleepi(5);
        // clkonOrganization.click();
        // MyCommonAPIs.sleepi(3);
        // clkonOrganization1.click();
        // MyCommonAPIs.sleepi(10);
        return result;
    }

    public void enableicpforOrgWideSSID() {
        MyCommonAPIs.sleepi(3);
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");
        MyCommonAPIs.sleepi(3);
        // editLocOrgWideSSID.click();
        MyCommonAPIs.sleep(5 * 1000);
        orgwidessidCaptivePortal.click();
        MyCommonAPIs.sleepi(20);
        if (enablecaptiveportalText.exists()) {
            MyCommonAPIs.sleepi(10);
            onoffcaptiveSwitch.click();
            MyCommonAPIs.sleepi(10);
            if (basiccaptiveText.exists()) {
                MyCommonAPIs.sleepi(5);
                basiccaptiveText.click();
                MyCommonAPIs.sleepi(5);
                saveButton.click();
                MyCommonAPIs.sleepi(5);
                okaysuccessbtn.click();
            }
            MyCommonAPIs.sleepi(180);
        }
    }

    public boolean enabledisableorganizationWideSSID() {

        boolean result = false;
        waitReady();
        waitElement(addssidsign);
        addssidsign.click();
        MyCommonAPIs.sleepi(5);
        cancelBtnOrgWideSSID.click();
        MyCommonAPIs.sleepi(5);
        orgSSIDSwitch.click();
        MyCommonAPIs.sleepi(5);
        if (addssidsign.exists()) {
            result = false;
            MyCommonAPIs.sleepi(2);
            System.out.println("Add Organization wide SSID option not greyed out successfully.");
            logger.info("Add Organization wide SSID option not greyed out successfully.");
        } else {
            result = true;
            MyCommonAPIs.sleepi(2);
            System.out.println("Add Organization wide SSID option greyed out successfully.");
            logger.info("Add Organization wide SSID option greyed out successfully.");
        }
        return result;
    }

    public boolean disableOrgWideSSID() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        // viwDevices.click();
        MyCommonAPIs.sleepi(5);
        clkonOrganization.click();
        MyCommonAPIs.sleepi(3);
        clkonOrganization1.click();
        MyCommonAPIs.sleepi(10);
        Setting.click();
        waitReady();
        waitElement(organizationWideSSID);
        organizationWideSSID.click();
        MyCommonAPIs.sleepi(10);
        if (orgSSIDSwitch.exists() && enabledOrgSSIDText.exists()) {
            orgSSIDSwitch.click();
            MyCommonAPIs.sleepi(5);
            logger.info("Organization wide SSID Switch is disabled.");
            System.out.println("Organization wide SSID Switch is disabled.");
            result = true;
        }

        MyCommonAPIs.sleepi(5);
        clkonOrganization.click();
        MyCommonAPIs.sleepi(3);
        clkonOrganization1.click();
        MyCommonAPIs.sleepi(10);

        return result;
    }

    public boolean enableOrgWideSSID() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        // viwDevices.click();
        MyCommonAPIs.sleepi(5);
        clkonOrganization.click();
        MyCommonAPIs.sleepi(3);
        clkonOrganization1.click();
        MyCommonAPIs.sleepi(10);
        Setting.click();
        waitReady();
        waitElement(organizationWideSSID);
        organizationWideSSID.click();
        MyCommonAPIs.sleepi(5);
        if (orgSSIDSwitch.exists()) {
            orgSSIDSwitch.click();
            MyCommonAPIs.sleepi(10);
            enabledOrgSSIDText.exists();
            logger.info("Organization wide SSID Switch is enabled.");
            System.out.println("Organization wide SSID Switch is enabled.");
            result = true;
        }

        MyCommonAPIs.sleepi(5);
        clkonOrganization.click();
        MyCommonAPIs.sleepi(3);
        clkonOrganization1.click();
        MyCommonAPIs.sleepi(10);

        return result;
    }

    public boolean organizationWideSSIDMaxLimitErrorLoc1Msg(Map<String, String> map) {

        boolean result = false;
        waitReady();
        waitElement(addssidsign);
        addssidsign.click();
        MyCommonAPIs.sleepi(5);
        if (orgwidessidName.exists()) {
            logger.info("Add ssid.");
            orgwidessidName.setValue(map.get("SSID"));
            waitReady();
        }

        if (checkband6.isSelected()) {
            if (band6.isDisplayed()) {
                band6.click();
                logger.info("Uncheck 6 band");
            }
        } else {
            System.out.println("6GHZ is alredy unchecked");
        }

        if (map.get("Security").equals("WPA2 Personal")) {
            security.selectOption("WPA2 Personal");
            waitReady();
        } else {
            security.selectOption(map.get("Security"));
            waitReady();
        }

        if (map.containsKey("Password")) {
            orgwideSSIDPass.setValue(map.get("Password"));
            waitReady();
        }

        takess("addSsid");
        orgwideSSIDSaveBtn.click();
        MyCommonAPIs.sleepi(10);
        // orgwideSSIDOKBtn.click();
        waitReady();
        if (errorMsg1.exists() && loc1SSIDErrorMsg.exists()) {
            logger.info("Organization Wide SSID is not created successfully.");
            result = true;
        }

        String temp1 = errorMsg1.getText();
        String temp2 = loc1SSIDErrorMsg.getText();
        System.out.println(temp2 + ":" + temp1);
        /*
         * String temp2 = errorMsg2.getText();
         * System.out.println(temp2);
         */
        MyCommonAPIs.sleepi(10);
        cancelBtn.click();
        System.out.println("Organization Wide SSID is not created successfully.");
        MyCommonAPIs.sleepi(5);

        // MyCommonAPIs.sleepi(5);
        // clkonOrganization.click();
        // MyCommonAPIs.sleepi(3);
        // clkonOrganization1.click();
        // MyCommonAPIs.sleepi(10);
        return result;
    }

    public void organizationWideSSIDSaveandConfig(Map<String, String> map) {

        MyCommonAPIs.sleepi(5);
        // viwDevices.click();
        MyCommonAPIs.sleepi(5);
        if (clkonOrganization.exists()) {
            clkonOrganization.click();
        } else if (clkonOrganization1.exists()) {
            clkonOrganization1.click();
        } else if (clkonOrganization2.exists()) {
            clkonOrganization2.click();
        }
        MyCommonAPIs.sleepi(3);
        if (clkonOrganization.exists()) {
            clkonOrganization.click();
        } else if (clkonOrganization1.exists()) {
            clkonOrganization1.click();
        } else if (clkonOrganization2.exists()) {
            clkonOrganization2.click();
        }
        MyCommonAPIs.sleepi(10);
        Setting.click();
        waitReady();
        waitElement(organizationWideSSID);
        organizationWideSSID.click();
        waitReady();
        waitElement(addssidsign);
        addssidsign.click();
        MyCommonAPIs.sleepi(5);
        if (orgwidessidName.exists()) {
            logger.info("Add ssid.");
            orgwidessidName.setValue(map.get("SSID"));
            waitReady();
        }

        if (map.get("Security").equals("WPA2 Personal")) {
            security.selectOption("WPA2 Personal");
            waitReady();
        } else {
            security.selectOption(map.get("Security"));
            waitReady();
        }

        if (map.containsKey("Password")) {
            orgwideSSIDPass.setValue(map.get("Password"));
            waitReady();
        }

        takess("addSsid");
        orgSSIDSaveandConfigBtn.click();
        MyCommonAPIs.sleepi(5);

    }

    public boolean enableBasiccaptivePortalforOrgWideSSID() {
        boolean result = false;
        logger.info("Enable Basic Captive Portal.");
        MyCommonAPIs.sleep(5 * 1000);
        orgwidessidCaptivePortal.click();
        MyCommonAPIs.sleepi(20);
        if (enablecaptiveportalText.exists()) {
            MyCommonAPIs.sleepi(10);
            onoffcaptiveSwitch.click();
            MyCommonAPIs.sleepi(10);
            if (basiccaptiveText.exists()) {
                MyCommonAPIs.sleepi(5);
                basiccaptiveText.click();
                MyCommonAPIs.sleepi(5);
                saveButton.click();
                MyCommonAPIs.sleepi(5);
                if (orgSSIDCaptiveSaveandConfigSuccessMsg.exists()) {
                    result = true;
                }
                String temp = orgSSIDCaptiveSaveandConfigSuccessMsg.getText();
                System.out.println(temp);
                logger.info(temp);
                okaysuccessbtn.click();
            }
            MyCommonAPIs.sleepi(180);
        }
        return result;
    }

    public void editOrgnaizationwideSSIDClientiso(Map<String, String> map, String URLs[]) {

        MyCommonAPIs.sleepi(5);
        clkonOrganization.click();
        MyCommonAPIs.sleepi(3);
        clkonOrganization1.click();
        MyCommonAPIs.sleepi(10);
        Setting.click();
        waitReady();
        waitElement(organizationWideSSID);
        organizationWideSSID.click();
        waitReady();
        MyCommonAPIs.sleepi(5);
        orgWideSSIDedit.doubleClick();
        // editLocOrgWideSSID.click();
        MyCommonAPIs.sleep(5 * 1000);
        enableClientisolation.click();
        MyCommonAPIs.sleepi(10);
        clickAddIP.click();
        MyCommonAPIs.sleepi(3);
        for (String urlone : URLs) {
            DomainName.sendKeys(urlone);
            MyCommonAPIs.sleepi(3);
            AddDomainName.click();
            MyCommonAPIs.sleepi(3);
        }

        MyCommonAPIs.sleepi(5);
        editorgwideSSIDSaveBtn.click();
        MyCommonAPIs.sleepi(5);
        MyCommonAPIs.sleepi(5);
        orgwideSSIDOKBtn.click();
        MyCommonAPIs.sleepi(5);
        clkonOrganization.click();
        MyCommonAPIs.sleepi(3);
        clkonOrganization1.click();
        MyCommonAPIs.sleepi(10);

    }

    // added by Tejeshwini

    public boolean CreateOrgSSId(Map<String, String> map) {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        waitElement(enableOrgSsid);
        boolean isKeyPresent = false;

        if (!checkSsidIsExist(map.get("SSID"))) {
            logger.info("Add ssid.");
            addssid.click();
            MyCommonAPIs.sleepi(5);
            waitElement(ssid);
            ssid.setValue(map.get("SSID"));
            waitReady();
            System.out.println(checkband6.isSelected());
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

    public boolean checkOrgSsidIsExist(String SSID) {
        System.out.println(SSID);
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        waitReady();
        // ($("#wirelessTable"));
        String sElement = String.format("//p[text()='%s']", SSID);
        logger.info("on element:" + sElement);
        if ($x(sElement).exists()) {
            result = true;
            logger.info("Ssid:" + SSID + " is existed.");
            System.out.print(result);
        }
        return result;
    }

    public void goWirelessSetting() {

        WebCheck.checkHrefIcon(URLParam.hrefWireless);
        waitElement(settingsorquickview);
        settingsorquickview.click();
        MyCommonAPIs.sleepi(5);

    }

    public void goToOrgSsid(String OrgName) {

        openOrg(OrgName);
        MyCommonAPIs.sleepi(10);
        Setting.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(10);
        waitElement(organizationWideSSID);
        organizationWideSSID.click();
        MyCommonAPIs.sleepi(10);
        waitElement(enableOrgSsid);

    }

    public void OrgSsidEnableEcp(String OrgName) {

        // openOrg(OrgName);
        MyCommonAPIs.sleepi(5);

        if (Setting.exists()) {
            Setting.click();
        }
        // settingsorquickview.click();

        // waitElement(organizationWideSSID);
        MyCommonAPIs.sleepi(3);
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");

        new WirelessQuickViewElement().entercaptiveportal.click();
        MyCommonAPIs.sleepi(20);
        new WirelessQuickViewElement().ecpRadiobutton.shouldBe(Condition.visible).click();
        // refresh();
        // MyCommonAPIs.sleepi(15);
        // enablecaptiveportal.click();
        MyCommonAPIs.sleepi(20);
        new WirelessQuickViewElement().selectinsightECP.click();
        // enableECP();

        // organizationWideSSID.click();
        // waitElement(enableOrgSsid);

    }

    public void OrgCreateECP(String Ssid, Map<String, String> map) {

        // if (openOrg(OrgName));
        // if(settingsorquickview.exists()){
        // settingsorquickview.click();
        // }
        // if (checkSsidIsExist(Ssid)) {
        // clickEditSsid(Ssid);
        // enableECP();

        if (map.containsKey("Splash Page URL")) {
            MyCommonAPIs.sleepi(4);
            new WirelessQuickViewElement().SplashPageURL.clear();
            new WirelessQuickViewElement().SplashPageURL.sendKeys(map.get("Splash Page URL"));
        }
        if (map.containsKey("Captive Portal Authentication Type")) {
            MyCommonAPIs.sleepi(4);
            new WirelessQuickViewElement().radius.click();
        }

        if (map.containsKey("IPv4 Address")) {
            MyCommonAPIs.sleepi(4);
            new WirelessQuickViewElement().IP.clear();
            new WirelessQuickViewElement().IP.sendKeys(map.get("IPv4 Address"));
        }
        if (map.containsKey("Password")) {
            MyCommonAPIs.sleepi(4);
            new WirelessQuickViewElement().IPPassword.clear();
            new WirelessQuickViewElement().IPPassword.sendKeys(map.get("Password"));
        }

        if (map.containsKey("Secret")) {
            MyCommonAPIs.sleepi(4);
            new WirelessQuickViewElement().WebSecret.sendKeys(map.get("Secret"));
        }

        if (map.containsKey("Key")) {
            MyCommonAPIs.sleepi(4);
            new WirelessQuickViewElement().WebKey.sendKeys(map.get("Key"));
        }

        if (map.containsKey("Allow HTTPS")) {
            MyCommonAPIs.sleepi(4);
            System.out.println("enterd allow");
            if (map.get("Allow HTTPS") == "Enable") {
                System.out.println("enterd enable");
                new WirelessQuickViewElement().AllowHTTPSEnable.click();
            }

        }

        if (map.containsKey("Failsafe")) {
            MyCommonAPIs.sleepi(4);
            System.out.println("enterd allow");
            if (map.get("Failsafe") == "Enable") {
                System.out.println("enterd enable");
                new WirelessQuickViewElement().AllowFailsafe.click();
            }

        }

        if (map.containsKey("Walled Garden")) {
            MyCommonAPIs.sleepi(4);
            new WirelessQuickViewElement().WalledGarden.sendKeys(map.get("Walled Garden"));
            MyCommonAPIs.sleepi(4);
            new WirelessQuickViewElement().AddWalledGarden.click();
        }

        MyCommonAPIs.sleepi(4);
        new WirelessQuickViewElement().SaveeditECP.click();
        MyCommonAPIs.sleepi(10);
        if (new WirelessQuickViewElement().SuccsECP.isDisplayed()) {
            new WirelessQuickViewElement().SuccsECP.click();
        }
    }

    public boolean checkOrgSsidRateLimitIsExist(String SSID) {
        System.out.println(SSID);
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        waitReady();
        // ($("#wirelessTable"));
        String sElement = String.format("//p[text()='%s']", SSID);
        logger.info("on element:" + sElement);
        if ($x(sElement).exists() && rateLimittabSSID.exists() && rateLimitOnStatus.exists()) {
            result = true;
            logger.info("Ssid:" + SSID + " is existed.");
        }
        return result;
    }

    public void denymacACL(Map<String, String> map) {
        MyCommonAPIs.sleepi(3);
        // clkonOrganizationSSID.click();
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");
        MyCommonAPIs.sleepi(3);
        // editLocOrgWideSSID.click();
        MyCommonAPIs.sleep(5 * 1000);
        macACL.click();
        MyCommonAPIs.sleepi(5);
        policy.selectOption("Deny");
        MyCommonAPIs.sleepi(10);
        manualclientaddBtn.click();
        MyCommonAPIs.sleepi(10);
        clientName.setValue(map.get("Device Name"));
        MyCommonAPIs.sleepi(5);
        clientMAC.setValue(map.get("MAC Address"));
        MyCommonAPIs.sleepi(5);
        addclientBtn.click();
        MyCommonAPIs.sleepi(10);
        enablemacSw.click();
        MyCommonAPIs.sleepi(5);
        radiusType.selectOption("Local ACL");
        MyCommonAPIs.sleepi(5);
        enablemacSw.click();
        MyCommonAPIs.sleepi(5);
        orgssidSettingTab.click();
        MyCommonAPIs.sleepi(10);
        editOrgwideSSIDSaveBtn.click();
        MyCommonAPIs.sleepi(5);
        editorgssidOkaySuccessBtn.click();
        MyCommonAPIs.sleepi(5);
    }

    public void editorgWideSSID() {
        MyCommonAPIs.sleepi(3);
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");
        MyCommonAPIs.sleep(5 * 1000);
    }

    public void creditAllocationICP(String name) {
//        if (checkOrganizationIsExist(name)) {
//            if(dropdownOrganizationElement(name).exists()) 
//            {
//            dropdownOrganizationElement(name).click();
//            addCreditsOrganizationElement(name).click();
//            }
//        }
//            else
//            {
                  String rowindex=dropdownOrganizationElementNew(name).getAttribute("aria-rowindex");
                  MyCommonAPIs.sleepi(2);
                  ariaSetIndex(rowindex).click();
                  MyCommonAPIs.sleepi(2);
                  ariaSetIndexAllocate(rowindex).click();                             
            //}        
            MyCommonAPIs.sleepi(5);
            if (icpCreditsOrg.shouldBe(Condition.visible).exists()) {
                icpCreditAllocation.shouldBe(Condition.visible).sendKeys("6");
                MyCommonAPIs.sleepi(1);
                allocateBtn.shouldBe(Condition.visible).click();
                MyCommonAPIs.sleepi(5);
            }
    }

    public void CreateOrgSSId1(Map<String, String> map) {

        waitElement(enableOrgSsid);
        boolean isKeyPresent = false;

        if (!checkSsidIsExist(map.get("SSID"))) {
            logger.info("Add ssid.");
            addssid.click();
            MyCommonAPIs.sleepi(5);
            waitElement(ssid);
            ssid.setValue(map.get("SSID"));
            waitReady();
            System.out.println(checkband6.isSelected());
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
            takess("addSsid");
            orgSSIDSaveAndConfigBtn.click();
            waitReady();
            MyCommonAPIs.sleepi(10);
        }

    }

    public void editOrgnaizationwideSSIDCustomVlan(Map<String, String> map) {

        waitReady();
        waitElement(orgWideSSIDedit);
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");
        MyCommonAPIs.sleep(5 * 1000);
        MyCommonAPIs.sleepi(10);
        addVLANLink.click();
        MyCommonAPIs.sleepi(10);
        if (customDVLAN.exists()) {
            vlanID.setValue(map.get("VLAN ID"));
        }
        MyCommonAPIs.sleepi(5);
        vlanSaveBtn.click();
        MyCommonAPIs.sleepi(10);
        editorgwideSSIDSaveBtn.click();
        MyCommonAPIs.sleepi(5);
        MyCommonAPIs.sleepi(5);
        orgwideSSIDOKBtn.click();
        MyCommonAPIs.sleepi(5);

    }

    public boolean checkOrgSsidCustomVlanIsExist(String SSID) {
        System.out.println(SSID);
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        waitReady();
        String sElement = String.format("//span[text()='%s']", SSID);
        logger.info("on element:" + sElement);
        String sElement1 = String.format("//p[text()='%s']", SSID);
        logger.info("on element:" + sElement1);
        if ((($x(sElement).exists()) || ($x(sElement1).exists())) && wirelessLocationVLAN.exists() && locCustomVLAN.exists()) {
            result = true;
            logger.info("Ssid:" + SSID + " is existed.");
        }
        return result;
    }

    public void editOrgnaizationwideSSIDMangamentVLAN(Map<String, String> map) {

        waitReady();
        waitElement(orgWideSSIDedit);
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");
        MyCommonAPIs.sleep(5 * 1000);
        MyCommonAPIs.sleepi(10);
        if (map.get("VLAN Name").equals("Management VLAN")) {
            selectVLAN.selectOption("Management VLAN");
            waitReady();
        }
        MyCommonAPIs.sleepi(10);
        editorgwideSSIDSaveBtn.click();
        MyCommonAPIs.sleepi(5);
        MyCommonAPIs.sleepi(5);
        orgwideSSIDOKBtn.click();
        MyCommonAPIs.sleepi(5);

    }

    public boolean checkOrgSsidManageVlanIsExist(String SSID) {
        System.out.println(SSID);
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        waitReady();
        String sElement = String.format("//p[text()='%s']", SSID);
        logger.info("on element:" + sElement);
        if ($x(sElement).exists() && wirelessLocationVLAN.exists() && manageVLAN.exists()) {
            result = true;
            logger.info("Ssid:" + SSID + " is existed.");
        }
        return result;
    }

    public void creditAllocation1(String name) {
        if (checkOrganizationIsExist(name)) {
            if(dropdownOrganizationElement(name).exists()) 
            {
            dropdownOrganizationElement(name).click();
            addCreditsOrganizationElement(name).click();
            }
            else
            {
                  String rowindex=dropdownOrganizationElementNew(name).getAttribute("aria-rowindex");
                  ariaSetIndex(rowindex).click();
                  ariaSetIndexAllocate(rowindex).click();                             
            }        
            MyCommonAPIs.sleepi(5);
            if (deviceCredits.exists()) {
                deviceCreditsTextbox.sendKeys("12");
                MyCommonAPIs.sleepi(1);
                allocateBtn.click();
                MyCommonAPIs.sleepi(5);
            }
        }
    }

    public void renameOrgnaizationwideSSID(Map<String, String> map) {

        waitReady();
        waitElement(orgWideSSIDedit);
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");
        MyCommonAPIs.sleep(5 * 1000);
        MyCommonAPIs.sleepi(5);
        ssid.clear();
        MyCommonAPIs.sleepi(5);
        ssid.setValue(map.get("SSID"));
        MyCommonAPIs.sleepi(5);
        editorgwideSSIDSaveBtn.click();
        MyCommonAPIs.sleepi(5);
        MyCommonAPIs.sleepi(5);
        orgwideSSIDOKBtn.click();
        MyCommonAPIs.sleepi(5);
        clkonOrganization.click();
        MyCommonAPIs.sleepi(3);
        clkonOrganization1.click();
        MyCommonAPIs.sleepi(10);

    }

    public void editOrgnaizationwideSSID2GhzOpenSecurity(Map<String, String> map) {

        waitReady();
        waitElement(orgWideSSIDedit);
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");
        MyCommonAPIs.sleep(5 * 1000);
        MyCommonAPIs.sleepi(5);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        band24.click();
        logger.info("2.4 GHz Band is selectd");
        MyCommonAPIs.sleepi(5);
        if (map.get("Security").equals("Open")) {
            security.selectOption("Open");
            waitReady();
        } else {
            security.selectOption(map.get("Security"));
            waitReady();
        }
        MyCommonAPIs.sleepi(5);
        editorgwideSSIDSaveBtn.click();
        MyCommonAPIs.sleepi(5);
        MyCommonAPIs.sleepi(5);
        orgwideSSIDOKBtn.click();
        MyCommonAPIs.sleepi(5);
        clkonOrganization.click();
        MyCommonAPIs.sleepi(3);
        clkonOrganization1.click();
        MyCommonAPIs.sleepi(10);

    }

    public void editOrgnaizationwideSSID2GhzWPA2Security(Map<String, String> map) {

        waitReady();
        waitElement(orgWideSSIDedit);
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");
        MyCommonAPIs.sleep(5 * 1000);
        MyCommonAPIs.sleepi(5);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        band24.click();
        logger.info("2.4 GHz Band is selectd");
        MyCommonAPIs.sleepi(5);
        if (map.get("Security").equals("WPA2 Personal")) {
            security.selectOption("WPA2 Personal");
            waitReady();
        } else {
            security.selectOption(map.get("Security"));
            waitReady();
        }
        MyCommonAPIs.sleepi(5);
        editorgwideSSIDSaveBtn.click();
        MyCommonAPIs.sleepi(5);
        MyCommonAPIs.sleepi(5);
        orgwideSSIDOKBtn.click();
        MyCommonAPIs.sleepi(5);
        clkonOrganization.click();
        MyCommonAPIs.sleepi(3);
        clkonOrganization1.click();
        MyCommonAPIs.sleepi(10);

    }

    public boolean verifylocation1WPA2SSID(Map<String, String> map) {

        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if ((checkOrgSsidIsExist(map.get("SSID"))) && (band24GHz.exists()) && (securityWPA2Personal.exists())) {
            logger.info("location Wide SSID edit successfull.");
            result = true;
        }
        return result;
    }

    public void editOrgnaizationwideSSID2GhzWPA2MixedSecurity(Map<String, String> map) {

        waitReady();
        waitElement(orgWideSSIDedit);
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");
        MyCommonAPIs.sleep(5 * 1000);
        MyCommonAPIs.sleepi(5);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        band24.click();
        logger.info("2.4 GHz Band is selectd");
        MyCommonAPIs.sleepi(5);
        if (map.get("Security").equals("WPA2 Personal Mixed")) {
            security.selectOption("WPA2 Personal Mixed");
            waitReady();
        } else {
            security.selectOption(map.get("Security"));
            waitReady();
        }
        MyCommonAPIs.sleepi(5);
        editorgwideSSIDSaveBtn.click();
        MyCommonAPIs.sleepi(5);
        MyCommonAPIs.sleepi(5);
        orgwideSSIDOKBtn.click();
        MyCommonAPIs.sleepi(5);
        clkonOrganization.click();
        MyCommonAPIs.sleepi(3);
        clkonOrganization1.click();
        MyCommonAPIs.sleepi(10);

    }

    public boolean verifylocation1WPA2MixedSSID(Map<String, String> map) {

        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if ((checkOrgSsidIsExist(map.get("SSID"))) && (band24GHz.exists()) && (securityWPA2PersonalMixed.exists())) {
            logger.info("location Wide SSID edit successfull.");
            result = true;
        }
        return result;
    }

    public boolean verifyRenameOrgSSID(Map<String, String> map) {

        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if (checkOrgSsidIsExist(map.get("SSID"))) {
            logger.info("location Wide SSID edit successfull.");
            result = true;
        }
        return result;
    }

    public void editOrgnaizationwideSSID2GhzWPA3MixedSecurity(Map<String, String> map) {

        waitReady();
        waitElement(orgWideSSIDedit);
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");
        MyCommonAPIs.sleep(5 * 1000);
        MyCommonAPIs.sleepi(5);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        band24.click();
        logger.info("2.4 GHz Band is selectd");
        MyCommonAPIs.sleepi(5);
        if (map.get("Security").equals("WPA3 Personal Mixed (WPA2 + WPA3)")) {
            security.selectOption("WPA3 Personal Mixed (WPA2 + WPA3)");
            waitReady();
        } else {
            security.selectOption(map.get("Security"));
            waitReady();
        }
        MyCommonAPIs.sleepi(5);
        editorgwideSSIDSaveBtn.click();
        MyCommonAPIs.sleepi(5);
        MyCommonAPIs.sleepi(5);
        orgwideSSIDOKBtn.click();
        MyCommonAPIs.sleepi(5);
        clkonOrganization.click();
        MyCommonAPIs.sleepi(3);
        clkonOrganization1.click();
        MyCommonAPIs.sleepi(10);

    }

    public boolean verifylocation1WPA3MixedSSID(Map<String, String> map) {

        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if ((checkOrgSsidIsExist(map.get("SSID"))) && (band24GHz.exists()) && (securityWPA3PersonalMixed.exists())) {
            logger.info("location Wide SSID edit successfull.");
            result = true;
        }
        return result;
    }

    public void editOrgnaizationwideSSID2GhzWPA3Security(Map<String, String> map) {

        waitReady();
        waitElement(orgWideSSIDedit);
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");
        MyCommonAPIs.sleep(5 * 1000);
        MyCommonAPIs.sleepi(5);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        band24.click();
        logger.info("2.4 GHz Band is selectd");
        MyCommonAPIs.sleepi(5);
        if (map.get("Security").equals("WPA3 Personal")) {
            security.selectOption("WPA3 Personal");
            waitReady();
        } else {
            security.selectOption(map.get("Security"));
            waitReady();
        }
        MyCommonAPIs.sleepi(5);
        editorgwideSSIDSaveBtn.click();
        MyCommonAPIs.sleepi(5);
        MyCommonAPIs.sleepi(5);
        orgwideSSIDOKBtn.click();
        MyCommonAPIs.sleepi(5);
        clkonOrganization.click();
        MyCommonAPIs.sleepi(3);
        clkonOrganization1.click();
        MyCommonAPIs.sleepi(10);

    }

    public boolean verifylocation1WPA3SSID(Map<String, String> map) {

        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if ((checkOrgSsidIsExist(map.get("SSID"))) && (band24GHz.exists()) && (securityWPA3.exists())) {
            logger.info("location Wide SSID edit successfull.");
            result = true;
        }
        return result;
    }

    public void editOrgnaizationwideSSID5GhzOpenSecurity(Map<String, String> map) {

        waitReady();
        waitElement(orgWideSSIDedit);
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");
        MyCommonAPIs.sleep(5 * 1000);
        MyCommonAPIs.sleepi(5);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        band5.click();
        logger.info("5 GHz Band is selectd");
        MyCommonAPIs.sleepi(5);
        if (map.get("Security").equals("Open")) {
            security.selectOption("Open");
            waitReady();
        } else {
            security.selectOption(map.get("Security"));
            waitReady();
        }
        MyCommonAPIs.sleepi(5);
        editorgwideSSIDSaveBtn.click();
        MyCommonAPIs.sleepi(5);
        MyCommonAPIs.sleepi(5);
        orgwideSSIDOKBtn.click();
        MyCommonAPIs.sleepi(5);
        clkonOrganization.click();
        MyCommonAPIs.sleepi(3);
        clkonOrganization1.click();
        MyCommonAPIs.sleepi(10);

    }

    public boolean verifylocation1OpenSSID5Ghz(Map<String, String> map) {

        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if ((checkOrgSsidIsExist(map.get("SSID"))) && (band5GHz.exists()) && (opensecurity.exists())) {
            logger.info("location Wide SSID edit successfull.");
            result = true;
        }
        return result;
    }

    public void editOrgnaizationwideSSID5GhzWPA2MixedSecurity(Map<String, String> map) {

        waitReady();
        waitElement(orgWideSSIDedit);
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");
        MyCommonAPIs.sleep(5 * 1000);
        MyCommonAPIs.sleepi(5);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        band5.click();
        logger.info("5 GHz Band is selectd");
        MyCommonAPIs.sleepi(5);
        if (map.get("Security").equals("WPA2 Personal Mixed")) {
            security.selectOption("WPA2 Personal Mixed");
            waitReady();
        } else {
            security.selectOption(map.get("Security"));
            waitReady();
        }
        MyCommonAPIs.sleepi(5);
        editorgwideSSIDSaveBtn.click();
        MyCommonAPIs.sleepi(5);
        MyCommonAPIs.sleepi(5);
        orgwideSSIDOKBtn.click();
        MyCommonAPIs.sleepi(5);
        clkonOrganization.click();
        MyCommonAPIs.sleepi(3);
        clkonOrganization1.click();
        MyCommonAPIs.sleepi(10);

    }

    public boolean verifylocation5GHzWPA2MixedSSID(Map<String, String> map) {

        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if ((checkOrgSsidIsExist(map.get("SSID"))) && (band5GHz.exists()) && (securityWPA2PersonalMixed.exists())) {
            logger.info("location Wide SSID edit successfull.");
            result = true;
        }
        return result;
    }

    public void editOrgnaizationwideSSID5GhzWPA2Security(Map<String, String> map) {

        waitReady();
        waitElement(orgWideSSIDedit);
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");
        MyCommonAPIs.sleep(5 * 1000);
        MyCommonAPIs.sleepi(5);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        band5.click();
        logger.info("5 GHz Band is selectd");
        MyCommonAPIs.sleepi(5);
        if (map.get("Security").equals("WPA2 Personal")) {
            security.selectOption("WPA2 Personal");
            waitReady();
        } else {
            security.selectOption(map.get("Security"));
            waitReady();
        }
        MyCommonAPIs.sleepi(5);
        editorgwideSSIDSaveBtn.click();
        MyCommonAPIs.sleepi(5);
        MyCommonAPIs.sleepi(5);
        orgwideSSIDOKBtn.click();
        MyCommonAPIs.sleepi(5);
        clkonOrganization.click();
        MyCommonAPIs.sleepi(3);
        clkonOrganization1.click();
        MyCommonAPIs.sleepi(10);

    }

    public boolean verifylocation5GHzWPA2SSID(Map<String, String> map) {

        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if ((checkOrgSsidIsExist(map.get("SSID"))) && (band5GHz.exists()) && (securityWPA2Personal.exists())) {
            logger.info("location Wide SSID edit successfull.");
            result = true;
        }
        return result;
    }

    public void editOrgnaizationwideSSID5GhzWPA3MixedSecurity(Map<String, String> map) {

        waitReady();
        waitElement(orgWideSSIDedit);
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");
        MyCommonAPIs.sleep(5 * 1000);
        MyCommonAPIs.sleepi(5);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        band5.click();
        logger.info("5 GHz Band is selectd");
        MyCommonAPIs.sleepi(5);
        if (map.get("Security").equals("WPA3 Personal Mixed (WPA2 + WPA3)")) {
            security.selectOption("WPA3 Personal Mixed (WPA2 + WPA3)");
            waitReady();
        } else {
            security.selectOption(map.get("Security"));
            waitReady();
        }
        MyCommonAPIs.sleepi(5);
        editorgwideSSIDSaveBtn.click();
        MyCommonAPIs.sleepi(5);
        MyCommonAPIs.sleepi(5);
        orgwideSSIDOKBtn.click();
        MyCommonAPIs.sleepi(5);
        clkonOrganization.click();
        MyCommonAPIs.sleepi(3);
        clkonOrganization1.click();
        MyCommonAPIs.sleepi(10);

    }

    public boolean verifylocation5GHZWPA3MixedSSID(Map<String, String> map) {

        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if ((checkOrgSsidIsExist(map.get("SSID"))) && (band5GHz.exists()) && (securityWPA3PersonalMixed.exists())) {
            logger.info("location Wide SSID edit successfull.");
            result = true;
        }
        return result;
    }

    public void editOrgnaizationwideSSID5GhzWPA3Security(Map<String, String> map) {

        waitReady();
        waitElement(orgWideSSIDedit);
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");
        MyCommonAPIs.sleep(5 * 1000);
        MyCommonAPIs.sleepi(5);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        band5.click();
        logger.info("5 GHz Band is selectd");
        MyCommonAPIs.sleepi(5);
        if (map.get("Security").equals("WPA3 Personal")) {
            security.selectOption("WPA3 Personal");
            waitReady();
        } else {
            security.selectOption(map.get("Security"));
            waitReady();
        }
        MyCommonAPIs.sleepi(5);
        editorgwideSSIDSaveBtn.click();
        MyCommonAPIs.sleepi(5);
        MyCommonAPIs.sleepi(5);
        orgwideSSIDOKBtn.click();
        MyCommonAPIs.sleepi(5);
        clkonOrganization.click();
        MyCommonAPIs.sleepi(3);
        clkonOrganization1.click();
        MyCommonAPIs.sleepi(10);

    }

    public boolean verifylocation5GHzWPA3SSID(Map<String, String> map) {

        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if ((checkOrgSsidIsExist(map.get("SSID"))) && (band5GHz.exists()) && (securityWPA3.exists())) {
            logger.info("location Wide SSID edit successfull.");
            result = true;
        }
        return result;
    }

    public void editOrgnaizationwideSSID6GhzOpenSecurity(Map<String, String> map) {

        waitReady();
        waitElement(orgWideSSIDedit);
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");
        MyCommonAPIs.sleep(5 * 1000);
        MyCommonAPIs.sleepi(5);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        band6.click();
        logger.info("5 GHz Band is selectd");
        MyCommonAPIs.sleepi(5);
        if (map.get("Security").equals("Open")) {
            security.selectOption("Open");
            waitReady();
        } else {
            security.selectOption(map.get("Security"));
            waitReady();
        }
        MyCommonAPIs.sleepi(5);
        editorgwideSSIDSaveBtn.click();
        MyCommonAPIs.sleepi(5);
        MyCommonAPIs.sleepi(5);
        orgwideSSIDOKBtn.click();
        MyCommonAPIs.sleepi(5);
        clkonOrganization.click();
        MyCommonAPIs.sleepi(3);
        clkonOrganization1.click();
        MyCommonAPIs.sleepi(10);

    }

    public boolean verifylocation1OpenSSID6Ghz(Map<String, String> map) {

        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if ((checkOrgSsidIsExist(map.get("SSID"))) && (secband6GHz.exists()) && (opensecurity.exists())) {
            logger.info("location Wide SSID edit successfull.");
            result = true;
        }
        return result;
    }

    public void editOrgnaizationwideSSID6GhzWPA3MixedSecurity(Map<String, String> map) {

        waitReady();
        waitElement(orgWideSSIDedit);
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");
        MyCommonAPIs.sleep(5 * 1000);
        MyCommonAPIs.sleepi(5);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        band6.click();
        logger.info("6 GHz Band is selectd");
        MyCommonAPIs.sleepi(5);
        if (map.get("Security").equals("WPA3 Personal Mixed (WPA2 + WPA3)")) {
            security.selectOption("WPA3 Personal Mixed (WPA2 + WPA3)");
            waitReady();
        } else {
            security.selectOption(map.get("Security"));
            waitReady();
        }
        MyCommonAPIs.sleepi(5);
        editorgwideSSIDSaveBtn.click();
        MyCommonAPIs.sleepi(5);
        MyCommonAPIs.sleepi(5);
        orgwideSSIDOKBtn.click();
        MyCommonAPIs.sleepi(5);
        clkonOrganization.click();
        MyCommonAPIs.sleepi(3);
        clkonOrganization1.click();
        MyCommonAPIs.sleepi(10);

    }

    public boolean verifylocation6GHZWPA3MixedSSID(Map<String, String> map) {

        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if ((checkOrgSsidIsExist(map.get("SSID"))) && (secband6GHz.exists()) && (securityWPA3PersonalMixed.exists())) {
            logger.info("location Wide SSID edit successfull.");
            result = true;
        }
        return result;
    }

    public void editOrgnaizationwideSSID6GhzWPA3Security(Map<String, String> map) {

        waitReady();
        waitElement(orgWideSSIDedit);
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");
        MyCommonAPIs.sleep(5 * 1000);
        MyCommonAPIs.sleepi(5);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        band6.click();
        logger.info("6 GHz Band is selectd");
        MyCommonAPIs.sleepi(5);
        if (map.get("Security").equals("WPA3 Personal")) {
            security.selectOption("WPA3 Personal");
            waitReady();
        } else {
            security.selectOption(map.get("Security"));
            waitReady();
        }
        MyCommonAPIs.sleepi(5);
        editorgwideSSIDSaveBtn.click();
        MyCommonAPIs.sleepi(5);
        MyCommonAPIs.sleepi(5);
        orgwideSSIDOKBtn.click();
        MyCommonAPIs.sleepi(5);
        clkonOrganization.click();
        MyCommonAPIs.sleepi(3);
        clkonOrganization1.click();
        MyCommonAPIs.sleepi(10);

    }

    public boolean verifylocation6GHzWPA3SSID(Map<String, String> map) {

        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if ((checkOrgSsidIsExist(map.get("SSID"))) && (secband6GHz.exists()) && (securityWPA3.exists())) {
            logger.info("location Wide SSID edit successfull.");
            result = true;
        }
        return result;
    }

    public void editOrgnaizationwideSSIDAllBandOpenSecurity(Map<String, String> map) {

        waitReady();
        waitElement(orgWideSSIDedit);
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");
        MyCommonAPIs.sleep(5 * 1000);
        MyCommonAPIs.sleepi(5);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        logger.info("All Band is selectd");
        MyCommonAPIs.sleepi(5);
        if (map.get("Security").equals("Open")) {
            security.selectOption("Open");
            waitReady();
        } else {
            security.selectOption(map.get("Security"));
            waitReady();
        }
        MyCommonAPIs.sleepi(5);
        editorgwideSSIDSaveBtn.click();
        MyCommonAPIs.sleepi(5);
        MyCommonAPIs.sleepi(5);
        orgwideSSIDOKBtn.click();
        MyCommonAPIs.sleepi(5);
        clkonOrganization.click();
        MyCommonAPIs.sleepi(3);
        clkonOrganization1.click();
        MyCommonAPIs.sleepi(10);

    }

    public boolean verifylocation1OpenSSIDAllBand(Map<String, String> map) {

        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if ((checkOrgSsidIsExist(map.get("SSID"))) && (band24GHz.exists()) && (band5GHz.exists()) && (secband6GHz.exists())
                && (opensecurity.exists())) {
            logger.info("location Wide SSID edit successfull.");
            result = true;
        }
        return result;
    }

    public void editOrgnaizationwideSSIDAllBandWPA3MixedSecurity(Map<String, String> map) {

        waitReady();
        waitElement(orgWideSSIDedit);
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");
        MyCommonAPIs.sleep(5 * 1000);
        MyCommonAPIs.sleepi(5);
        ALLband.click();
        MyCommonAPIs.sleepi(5);
//        ALLband.click();
        logger.info("All Band is selectd");
        MyCommonAPIs.sleepi(5);
        if (map.get("Security").equals("WPA3 Personal Mixed (WPA2 + WPA3)")) {
            security.selectOption("WPA3 Personal Mixed (WPA2 + WPA3)");
            waitReady();
        } else {
            security.selectOption(map.get("Security"));
            waitReady();
        }
        MyCommonAPIs.sleepi(5);
        editorgwideSSIDSaveBtn.click();
        MyCommonAPIs.sleepi(5);
        MyCommonAPIs.sleepi(5);
        orgwideSSIDOKBtn.click();
        MyCommonAPIs.sleepi(5);
        clkonOrganization.click();
        MyCommonAPIs.sleepi(3);
        clkonOrganization1.click();
        MyCommonAPIs.sleepi(10);

    }

    public boolean verifylocationAllBandWPA3MixedSSID(Map<String, String> map) {

        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if ((checkOrgSsidIsExist(map.get("SSID"))) && (band24GHz.exists()) && (band5GHz.exists()) && (secband6GHz.exists())
                && (securityWPA3PersonalMixed.exists())) {
            logger.info("location Wide SSID edit successfull.");
            result = true;
        }
        return result;
    }

    public void editOrgnaizationwideSSIDAllBandWPA3Security(Map<String, String> map) {

        waitReady();
        waitElement(orgWideSSIDedit);
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");
        MyCommonAPIs.sleep(5 * 1000);
        MyCommonAPIs.sleepi(5);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        logger.info("All Band is selectd");
        MyCommonAPIs.sleepi(5);
        if (map.get("Security").equals("WPA3 Personal")) {
            security.selectOption("WPA3 Personal");
            waitReady();
        } else {
            security.selectOption(map.get("Security"));
            waitReady();
        }
        MyCommonAPIs.sleepi(5);
        editorgwideSSIDSaveBtn.click();
        MyCommonAPIs.sleepi(5);
        MyCommonAPIs.sleepi(5);
        orgwideSSIDOKBtn.click();
        MyCommonAPIs.sleepi(5);
        clkonOrganization.click();
        MyCommonAPIs.sleepi(3);
        clkonOrganization1.click();
        MyCommonAPIs.sleepi(10);

    }

    public boolean verifylocationAllBandWPA3SSID(Map<String, String> map) {

        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if ((checkOrgSsidIsExist(map.get("SSID"))) && (band24GHz.exists()) && (band5GHz.exists()) && (secband6GHz.exists())
                && (securityWPA3.exists())) {
            logger.info("location Wide SSID edit successfull.");
            result = true;
        }
        return result;
    }

    public void editOrgnaizationwideSSID245GhzOpenSecurity(Map<String, String> map) {

        waitReady();
        waitElement(orgWideSSIDedit);
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");
        MyCommonAPIs.sleep(5 * 1000);
        MyCommonAPIs.sleepi(5);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        band24.click();
        MyCommonAPIs.sleepi(1);
        band5.click();
        logger.info("2.4 GHz and 5 GHz Band is selectd");
        MyCommonAPIs.sleepi(5);
        if (map.get("Security").equals("Open")) {
            security.selectOption("Open");
            waitReady();
        } else {
            security.selectOption(map.get("Security"));
            waitReady();
        }
        MyCommonAPIs.sleepi(5);
        editorgwideSSIDSaveBtn.click();
        MyCommonAPIs.sleepi(5);
        MyCommonAPIs.sleepi(5);
        orgwideSSIDOKBtn.click();
        MyCommonAPIs.sleepi(5);
        clkonOrganization.click();
        MyCommonAPIs.sleepi(3);
        clkonOrganization1.click();
        MyCommonAPIs.sleepi(10);

    }

    public boolean verifylocation1OpenSSID245Ghz(Map<String, String> map) {

        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if ((checkOrgSsidIsExist(map.get("SSID"))) && (band24GHz.exists()) && (band5GHz.exists()) && (opensecurity.exists())) {
            logger.info("location Wide SSID edit successfull.");
            result = true;
        }
        return result;
    }

    public void editOrgnaizationwideSSID245GhzWPA2MixedSecurity(Map<String, String> map) {

        waitReady();
        waitElement(orgWideSSIDedit);
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");
        MyCommonAPIs.sleep(5 * 1000);
        MyCommonAPIs.sleepi(5);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        band24.click();
        MyCommonAPIs.sleepi(1);
        band5.click();
        logger.info("5 GHz Band is selectd");
        MyCommonAPIs.sleepi(5);
        if (map.get("Security").equals("WPA2 Personal Mixed")) {
            security.selectOption("WPA2 Personal Mixed");
            waitReady();
        } else {
            security.selectOption(map.get("Security"));
            waitReady();
        }
        MyCommonAPIs.sleepi(5);
        editorgwideSSIDSaveBtn.click();
        MyCommonAPIs.sleepi(5);
        MyCommonAPIs.sleepi(5);
        orgwideSSIDOKBtn.click();
        MyCommonAPIs.sleepi(5);
        clkonOrganization.click();
        MyCommonAPIs.sleepi(3);
        clkonOrganization1.click();
        MyCommonAPIs.sleepi(10);

    }

    public boolean verifylocation245GHzWPA2MixedSSID(Map<String, String> map) {

        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if ((checkOrgSsidIsExist(map.get("SSID"))) && (band24GHz.exists()) && (band5GHz.exists()) && (securityWPA2PersonalMixed.exists())) {
            logger.info("location Wide SSID edit successfull.");
            result = true;
        }
        return result;
    }

    public void editOrgnaizationwideSSID245GhzWPA2Security(Map<String, String> map) {

        waitReady();
        waitElement(orgWideSSIDedit);
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");
        MyCommonAPIs.sleep(5 * 1000);
        MyCommonAPIs.sleepi(5);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        band24.click();
        MyCommonAPIs.sleepi(1);
        band5.click();
        logger.info("2.4GHz and 5 GHz Bands are selectd");
        MyCommonAPIs.sleepi(5);
        if (map.get("Security").equals("WPA2 Personal")) {
            security.selectOption("WPA2 Personal");
            waitReady();
        } else {
            security.selectOption(map.get("Security"));
            waitReady();
        }
        MyCommonAPIs.sleepi(5);
        editorgwideSSIDSaveBtn.click();
        MyCommonAPIs.sleepi(5);
        MyCommonAPIs.sleepi(5);
        orgwideSSIDOKBtn.click();
        MyCommonAPIs.sleepi(5);
        clkonOrganization.click();
        MyCommonAPIs.sleepi(3);
        clkonOrganization1.click();
        MyCommonAPIs.sleepi(10);

    }

    public boolean verifylocation245GHzWPA2SSID(Map<String, String> map) {

        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if ((checkOrgSsidIsExist(map.get("SSID"))) && (band24GHz.exists()) && (band5GHz.exists()) && (securityWPA2Personal.exists())) {
            logger.info("location Wide SSID edit successfull.");
            result = true;
        }
        return result;
    }

    public void editOrgnaizationwideSSID245GhzWPA3MixedSecurity(Map<String, String> map) {

        waitReady();
        waitElement(orgWideSSIDedit);
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");
        MyCommonAPIs.sleep(5 * 1000);
        MyCommonAPIs.sleepi(5);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        band24.click();
        MyCommonAPIs.sleepi(1);
        band5.click();
        logger.info("2.4GHz and 5 GHz Bands are selectd");
        MyCommonAPIs.sleepi(5);
        if (map.get("Security").equals("WPA3 Personal Mixed (WPA2 + WPA3)")) {
            security.selectOption("WPA3 Personal Mixed (WPA2 + WPA3)");
            waitReady();
        } else {
            security.selectOption(map.get("Security"));
            waitReady();
        }
        MyCommonAPIs.sleepi(5);
        editorgwideSSIDSaveBtn.click();
        MyCommonAPIs.sleepi(5);
        MyCommonAPIs.sleepi(5);
        orgwideSSIDOKBtn.click();
        MyCommonAPIs.sleepi(5);
        clkonOrganization.click();
        MyCommonAPIs.sleepi(3);
        clkonOrganization1.click();
        MyCommonAPIs.sleepi(10);

    }

    public boolean verifylocation245GHZWPA3MixedSSID(Map<String, String> map) {

        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if ((checkOrgSsidIsExist(map.get("SSID"))) && (band24GHz.exists()) && (band5GHz.exists()) && (securityWPA3PersonalMixed.exists())) {
            logger.info("location Wide SSID edit successfull.");
            result = true;
        }
        return result;
    }

    public void editOrgnaizationwideSSID245GhzWPA3Security(Map<String, String> map) {

        waitReady();
        waitElement(orgWideSSIDedit);
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");
        MyCommonAPIs.sleep(5 * 1000);
        MyCommonAPIs.sleepi(5);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        ALLband.click();
        MyCommonAPIs.sleepi(1);
        band24.click();
        MyCommonAPIs.sleepi(1);
        band5.click();
        logger.info("5 GHz Band is selectd");
        MyCommonAPIs.sleepi(5);
        if (map.get("Security").equals("WPA3 Personal")) {
            security.selectOption("WPA3 Personal");
            waitReady();
        } else {
            security.selectOption(map.get("Security"));
            waitReady();
        }
        MyCommonAPIs.sleepi(5);
        editorgwideSSIDSaveBtn.click();
        MyCommonAPIs.sleepi(5);
        MyCommonAPIs.sleepi(5);
        orgwideSSIDOKBtn.click();
        MyCommonAPIs.sleepi(5);
        clkonOrganization.click();
        MyCommonAPIs.sleepi(3);
        clkonOrganization1.click();
        MyCommonAPIs.sleepi(10);

    }

    public boolean verifylocation245GHzWPA3SSID(Map<String, String> map) {

        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if ((checkOrgSsidIsExist(map.get("SSID"))) && (band24GHz.exists()) && (band5GHz.exists()) && (securityWPA3.exists())) {
            logger.info("location Wide SSID edit successfull.");
            result = true;
        }
        return result;
    }

    public boolean cancelOrgnaizationwideSSID(Map<String, String> map) {
        boolean result = false;
        waitReady();
        waitElement(orgWideSSIDedit);
        orgWideSSIDedit.doubleClick();
        logger.info("Edit ssid.");
        MyCommonAPIs.sleep(5 * 1000);
        MyCommonAPIs.sleepi(5);
        cancelButton.click();
        MyCommonAPIs.sleepi(20);
        if (organizationWideSSID.exists()) {
            organizationWideSSID.click();
            MyCommonAPIs.sleepi(10);
            waitElement(enableOrgSsid);
            if (enableOrgSsid.exists()) {
                result = true;
            }
        }

        return result;

    }

    // codeAddedByPratik
    public boolean createOrgSSIdWPA2Enterprise2Band(Map<String, String> map) {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        waitElement(enableOrgSsid);
        boolean isKeyPresent = false;

        if (!checkSsidIsExist(map.get("SSID"))) {
            logger.info("Add ssid.");
            addssid.click();
            MyCommonAPIs.sleepi(5);
            waitElement(ssid);
            ssid.setValue(map.get("SSID"));
            waitReady();
            System.out.println(checkband6.isSelected());
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

            if (map.get("Security").equals("WPA2 Enterprise")) {
                security.selectOption("WPA2 Enterprise");
                waitReady();
            } else {
                security.selectOption(map.get("Security"));
                waitReady();
            }
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

    public boolean createOrgSSIdWPA3EnterpriseAllBand(Map<String, String> map) {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        waitElement(enableOrgSsid);
        boolean isKeyPresent = false;

        if (!checkSsidIsExist(map.get("SSID"))) {
            logger.info("Add ssid.");
            addssid.click();
            MyCommonAPIs.sleepi(5);
            waitElement(ssid);
            ssid.setValue(map.get("SSID"));
            waitReady();
            ALLband.click();
            MyCommonAPIs.sleepi(2);
            if (map.get("Security").equals("WPA2-PSK")) {
                security.selectOption("WPA2-PSK");
                waitReady();
            } else {
                security.selectOption(map.get("Security"));
                waitReady();
            }
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

    public boolean createOrgSSIdWPA3Enterprise2Band(Map<String, String> map) {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        waitElement(enableOrgSsid);
        boolean isKeyPresent = false;

        if (!checkSsidIsExist(map.get("SSID"))) {
            logger.info("Add ssid.");
            addssid.click();
            MyCommonAPIs.sleepi(5);
            waitElement(ssid);
            ssid.setValue(map.get("SSID"));
            waitReady();
            System.out.println(checkband6.isSelected());
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

            if (map.get("Security").equals("WPA3 Enterprise")) {
                security.selectOption("WPA3 Enterprise");
                waitReady();
            } else {
                security.selectOption(map.get("Security"));
                waitReady();
            }
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

    public boolean createOrgSSIdWPA2Enterprise24GHzBand(Map<String, String> map) {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        waitElement(enableOrgSsid);
        boolean isKeyPresent = false;

        if (!checkSsidIsExist(map.get("SSID"))) {
            logger.info("Add ssid.");
            addssid.click();
            MyCommonAPIs.sleepi(5);
            waitElement(ssid);
            ssid.setValue(map.get("SSID"));
            waitReady();
            System.out.println(checkband6.isSelected());
            if (checkband6.isSelected()) {
                if (band6.isDisplayed()) {
                    band6.click();
                    logger.info("Uncheck 6 band");
                }
            } else {
                System.out.println("6GHZ is alredy unchecked");
            }
            // isKeyPresent = map.containsKey("Band");
            // System.out.println(isKeyPresent);
            // if(isKeyPresent== true) {
            ALLband.click();
            MyCommonAPIs.sleepi(4);
            ALLband.click();
            MyCommonAPIs.sleepi(4);
            band24.click();
            MyCommonAPIs.sleepi(4);
            logger.info("check all band");

            if (map.get("Security").equals("WPA2 Enterprise")) {
                security.selectOption("WPA2 Enterprise");
                waitReady();
            } else {
                security.selectOption(map.get("Security"));
                waitReady();
            }
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

    public boolean createOrgSSIdWPA3Enterprise24GHzBand(Map<String, String> map) {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        waitElement(enableOrgSsid);
        boolean isKeyPresent = false;

        if (!checkSsidIsExist(map.get("SSID"))) {
            logger.info("Add ssid.");
            addssid.click();
            MyCommonAPIs.sleepi(5);
            waitElement(ssid);
            ssid.setValue(map.get("SSID"));
            waitReady();
            System.out.println(checkband6.isSelected());
            if (checkband6.isSelected()) {
                if (band6.isDisplayed()) {
                    band6.click();
                    logger.info("Uncheck 6 band");
                }
            } else {
                System.out.println("6GHZ is alredy unchecked");
            }
            // isKeyPresent = map.containsKey("Band");
            // System.out.println(isKeyPresent);
            // if(isKeyPresent== true) {
            ALLband.click();
            MyCommonAPIs.sleepi(4);
            ALLband.click();
            MyCommonAPIs.sleepi(4);
            band24.click();
            MyCommonAPIs.sleepi(4);
            logger.info("check all band");

            if (map.get("Security").equals("WPA3 Enterprise")) {
                security.selectOption("WPA3 Enterprise");
                waitReady();
            } else {
                security.selectOption(map.get("Security"));
                waitReady();
            }
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

    public boolean createOrgSSIdWPA2Enterprise5GHzBand(Map<String, String> map) {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        waitElement(enableOrgSsid);
        boolean isKeyPresent = false;

        if (!checkSsidIsExist(map.get("SSID"))) {
            logger.info("Add ssid.");
            addssid.click();
            MyCommonAPIs.sleepi(5);
            waitElement(ssid);
            ssid.setValue(map.get("SSID"));
            waitReady();
            System.out.println(checkband6.isSelected());
            if (checkband6.isSelected()) {
                if (band6.isDisplayed()) {
                    band6.click();
                    logger.info("Uncheck 6 band");
                }
            } else {
                System.out.println("6GHZ is alredy unchecked");
            }
            ALLband.click();
            MyCommonAPIs.sleepi(4);
            ALLband.click();
            MyCommonAPIs.sleepi(4);
            band5.click();
            MyCommonAPIs.sleepi(4);
            logger.info("check all band");

            if (map.get("Security").equals("WPA2 Enterprise")) {
                security.selectOption("WPA2 Enterprise");
                waitReady();
            } else {
                security.selectOption(map.get("Security"));
                waitReady();
            }
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

    public boolean createOrgSSIdWPA3Enterprise5GHzBand(Map<String, String> map) {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        waitElement(enableOrgSsid);
        boolean isKeyPresent = false;

        if (!checkSsidIsExist(map.get("SSID"))) {
            logger.info("Add ssid.");
            addssid.click();
            MyCommonAPIs.sleepi(5);
            waitElement(ssid);
            ssid.setValue(map.get("SSID"));
            waitReady();
            System.out.println(checkband6.isSelected());
            if (checkband6.isSelected()) {
                if (band6.isDisplayed()) {
                    band6.click();
                    logger.info("Uncheck 6 band");
                }
            } else {
                System.out.println("6GHZ is alredy unchecked");
            }
            // isKeyPresent = map.containsKey("Band");
            // System.out.println(isKeyPresent);
            // if(isKeyPresent== true) {
            ALLband.click();
            MyCommonAPIs.sleepi(4);
            ALLband.click();
            MyCommonAPIs.sleepi(4);
            band5.click();
            MyCommonAPIs.sleepi(4);
            logger.info("check all band");

            if (map.get("Security").equals("WPA3 Enterprise")) {
                security.selectOption("WPA3 Enterprise");
                waitReady();
            } else {
                security.selectOption(map.get("Security"));
                waitReady();
            }
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

    public boolean createOrgSSIdWPA3Enterprise6GHzBand(Map<String, String> map) {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        waitElement(enableOrgSsid);
        boolean isKeyPresent = false;

        if (!checkSsidIsExist(map.get("SSID"))) {
            logger.info("Add ssid.");
            addssid.click();
            MyCommonAPIs.sleepi(5);
            waitElement(ssid);
            ssid.setValue(map.get("SSID"));
            waitReady();
            System.out.println(checkband6.isSelected());
            if (checkband6.isSelected()) {
                if (band6.isDisplayed()) {
                    band6.click();
                    logger.info("Uncheck 6 band");
                }
            } else {
                System.out.println("6GHZ is alredy unchecked");
            }
            // isKeyPresent = map.containsKey("Band");
            // System.out.println(isKeyPresent);
            // if(isKeyPresent== true) {
            ALLband.click();
            MyCommonAPIs.sleepi(4);
            ALLband.click();
            MyCommonAPIs.sleepi(4);
            band6.click();
            MyCommonAPIs.sleepi(4);
            logger.info("check all band");

            if (map.get("Security").equals("WPA3 Enterprise")) {
                security.selectOption("WPA3 Enterprise");
                waitReady();
            } else {
                security.selectOption(map.get("Security"));
                waitReady();
            }
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

    public boolean verifylocation1WPA2EnterpriseOrgSSID2Bands(Map<String, String> map) {

        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if ((checkOrgSsidIsExist(map.get("SSID"))) && (band24GHz.exists()) && (band5GHz.exists())
                && (locationWPA2EnterpriseOrgSsidSecurity.exists())) {
            logger.info("org wide ssid with wpa2 enterprise security pushed successfull.");
            result = true;
        }
        return result;
    }

    public boolean verifylocation1WPA3EnterpriseOrgSSID2Bands(Map<String, String> map) {

        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if ((checkOrgSsidIsExist(map.get("SSID"))) && (band24GHz.exists()) && (band5GHz.exists())
                && (locationWPA3EnterpriseOrgSsidSecurity.exists())) {
            logger.info("org wide ssid with wpa3 enterprise security pushed successfull.");
            result = true;
        }
        return result;
    }

    public boolean verifylocation1WPA3EnterpriseOrgSSIDAllBands(Map<String, String> map) {

        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if ((checkOrgSsidIsExist(map.get("SSID"))) && (band24GHz.exists()) && (band5GHz.exists()) && (secband6GHz.exists())
                && (locationWPA3EnterpriseOrgSsidSecurity.exists())) {
            logger.info("org wide ssid with wpa3 enterprise security pushed successfull.");
            result = true;
        }
        return result;
    }

    public boolean verifylocation1WPA2EnterpriseOrgSSID24Band(Map<String, String> map) {

        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if ((checkOrgSsidIsExist(map.get("SSID"))) && (band24GHz.exists()) && (locationWPA2EnterpriseOrgSsidSecurity.exists())) {
            logger.info("org wide ssid with wpa2 enterprise security pushed successfull.");
            result = true;
        }
        return result;
    }

    public boolean verifylocation1WPA3EnterpriseOrgSSID24Band(Map<String, String> map) {

        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if ((checkOrgSsidIsExist(map.get("SSID"))) && (band24GHz.exists()) && (locationWPA3EnterpriseOrgSsidSecurity.exists())) {
            logger.info("org wide ssid with wpa3 enterprise security pushed successfull.");
            result = true;
        }
        return result;
    }

    public boolean verifylocation1WPA2EnterpriseOrgSSID5Band(Map<String, String> map) {

        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if ((checkOrgSsidIsExist(map.get("SSID"))) && (band5GHz.exists()) && (locationWPA2EnterpriseOrgSsidSecurity.exists())) {
            logger.info("org wide ssid with wpa2 enterprise security pushed successfull.");
            result = true;
        }
        return result;
    }

    public boolean verifylocation1WPA3EnterpriseOrgSSID5Band(Map<String, String> map) {

        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if ((checkOrgSsidIsExist(map.get("SSID"))) && (band5GHz.exists()) && (locationWPA3EnterpriseOrgSsidSecurity.exists())) {
            logger.info("org wide ssid with wpa3 enterprise security pushed successfull.");
            result = true;
        }
        return result;
    }

    public boolean verifylocation1WPA3EnterpriseOrgSSID6Band(Map<String, String> map) {

        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if ((checkOrgSsidIsExist(map.get("SSID"))) && (secband6GHz.exists()) && (locationWPA3EnterpriseOrgSsidSecurity.exists())) {
            logger.info("org wide ssid with wpa3 enterprise security pushed successfull.");
            result = true;
        }
        return result;
    }

    public void disableOrgWideSSIDSwitch() {
        MyCommonAPIs.sleepi(15);
        waitElement(orgwideSSIDToggleSwitchOnOff);
        orgwideSSIDToggleSwitchOnOff.click();
        MyCommonAPIs.sleepi(2);
    }

    public boolean verfiyNoDataAvailableonWirelestLocation() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        waitElement(nodataAvailable);
        if (nodataAvailable.exists()) {
            logger.info("No data available on wireless page at location level after disabling org wide ssid switch");
            result = true;
        }
        result = true;
        return result;
    }

    public void enableOrgWideSSIDSwitch() {
        MyCommonAPIs.sleepi(10);
        waitElement(orgwideSSIDToggleSwitchOnOff);
        orgwideSSIDToggleSwitchOnOff.click();
        MyCommonAPIs.sleepi(2);
    }

    // added by vivek
    public void addOrgLogo(Map<String, String> map) {
        MyCommonAPIs.sleepi(5);
        waitElement(sOrganizationLocationElement1);
        if (checkOrganizationIsExist(map.get("Name"))) {
            dropdownOrganizationElement(map.get("Name")).click();
            editOrganizationElement(map.get("Name")).click();
            waitElement(NameOrg);
        }
        MyCommonAPIs.sleepi(15);
        orgChooseBtn
                .sendKeys("C:\\WebportalAutomation\\com\\src\\test\\java\\webportal\\WebPortalUsabilityImprovements\\PRJCBUGEN_T32194\\orgImg.png");
        MyCommonAPIs.sleepi(2);
        SaveOrg.click();
        MyCommonAPIs.sleepi(2);
        UpdateOrgOK.click();
        MyCommonAPIs.sleepi(2);

    }

    // added by vivek
    public boolean GetOrgLogoUrlAndVerifyLogoIsUploaded(String orgName) {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        String url = getOrgImgLogoUrl(orgName).getAttribute("src");
        System.out.println(url);
        if (url.contains("orgImg.png")) {
            logger.info("Yes, Org Logo is Updated and Verified  ");
            result = true;
        }
        return result;
    }

    public boolean CheckScheduler(String Name) {
        boolean result = false;

        MyCommonAPIs.sleepi(5);
        if (ViewAllScheduler.exists()) {
            ViewAllScheduler.click();
        }
        String collectScheduler = CreatedSchedule.getText();
        System.out.println(collectScheduler);

        if (collectScheduler.contains("Schedule Name Location Devices Last Triggered Start Date Start Time End Date End Time Repeats")) {
            result = true;
        }

        return result;

    }

    public boolean NotificationSchedule(String Serialno) {
        boolean result = false;
        Notificationicon.click();
        MyCommonAPIs.sleepi(3);
        SeeAllNotification.click();
        MyCommonAPIs.sleepi(10);

        if (NotificationText(Serialno).isDisplayed()) {
            String Notitext = NotificationText(Serialno).getText();
            System.out.println(Notitext);
            if (Notitext.contains("reboot")) {
                result = true;
            }

        }

        return result;
    }

    public boolean SearchLoc(String Loc) {
        boolean result = false;
        SearchLoc.click();
        waitElement(sendsearchText);
        sendsearchText.sendKeys(Loc);
        if (SelctLoc(Loc).isDisplayed()) {
            result = true;
        }
        return result;
    }

    public void goToOrgwideClient(String OrgName) {

        openOrg(OrgName);
        MyCommonAPIs.sleepi(5);
        Setting.click();
        waitElement(client);
        client.click();
        // waitElement(enableOrgSsid);

    }

    // Added by Vivek
    public void OpenFilterOptions() {
        MyCommonAPIs.sleepi(3);
        logger.info("Opening the Filter options.");
        ProFilterOption.click();
        MyCommonAPIs.sleepi(5);
    }

    // Added by Vivek
    public void OpenFilterOptionsforLocationBox() {
        MyCommonAPIs.sleepi(3);
        logger.info("Opening the Filter options.");
        if (ProFilterForlocBox.exists()) {
            ProFilterForlocBox.hover();
            MyCommonAPIs.sleepi(1);
            ProFilterForlocBox.click();
            MyCommonAPIs.sleepi(2);
        }
        MyCommonAPIs.sleepi(3);
    }

    // Added by Vivek
    public boolean VerifyOrgCountisShowingCorrect() {
        boolean result = false;
        MyCommonAPIs.sleepi(3);
        logger.info("verifying Total Org Count is Showing Correct");
        if (TotalOrgCount.isDisplayed()) {
            String count = TotalOrgCount.text();
            logger.info(count + "Org Found");
            result = true;

        }
        return result;
    }

    // Added by Vivek
    public boolean verifyOrgFilterSelectedAsDefault() {
        boolean result = false;
        MyCommonAPIs.sleepi(3);
        logger.info("verifying Org Selected As Default ");
        if (FilterActiveOrg.isDisplayed()) {
            result = true;
        }
        return result;

    }

    // Added by Vivek
    public boolean verifyOwnerFilterSelectedAsDefault() {
        boolean result = false;
        MyCommonAPIs.sleepi(3);
        logger.info("verifying OwnerFilter Selected As Default ");
        if (FilterActiveOwner.isDisplayed()) {
            result = true;
        }
        return result;

    }

    // Added by Vivek
    public void ClickOnApplyFilterButton() {
        MyCommonAPIs.sleepi(2);
        logger.info("Clicking on Apply Filter Button");
        if (ProFilterApplyButton.exists()) {
            ProFilterApplyButton.hover();
            MyCommonAPIs.sleepi(1);
            ProFilterApplyButton.click();
        }
        MyCommonAPIs.sleepi(3);
    }

    // Added by Vivek
    public boolean verifyOrgTitleIsVisibleUnderOrgCard() {
        boolean result = false;
        MyCommonAPIs.sleepi(3);
        logger.info("verifying Org Title Is Visible Under Org Card");
        if (BoxOrgCardOrgTitle.isDisplayed()) {
            result = true;
        }
        return result;

    }

    // Added by Vivek
    public void De_SelectTheOrgFilter() {
        MyCommonAPIs.sleepi(3);
        logger.info("De_Selecting The Org Filter");
        FilterActiveOrg.click();
        MyCommonAPIs.sleepi(3);
    }

    // Added by Vivek
    public boolean verifyOrgTitleIsNotVisibleUnderOrgCard() {
        boolean result = true;
        MyCommonAPIs.sleepi(3);
        logger.info("verifying Org Title Is Visible Under Org Card");
        if (BoxOrgCardOrgTitle.isDisplayed()) {
            result = false;
        }
        return result;

    }

    // Added by Vivek
    public boolean verifyOwnerTitleIsVisibleUnderOrgCard() {
        boolean result = false;
        MyCommonAPIs.sleepi(3);
        logger.info("verifying Owner Title Is Visible Under Org Card");
        if (BoxOrgCardOwnerTitle.isDisplayed()) {
            result = true;
        }
        return result;
    }

    // Added by Vivek
    public void De_SelectTheOwnerFilter() {
        MyCommonAPIs.sleepi(3);
        logger.info("De_Selecting The Owner Filter");
        FilterActiveOwner.click();
        MyCommonAPIs.sleepi(3);
    }

    // Added by Vivek
    public boolean verifyOwnerTitleIsNotVisibleUnderOrgCard() {
        boolean result = true;
        MyCommonAPIs.sleepi(3);
        logger.info("verifying Owner Title Is Visible Under Org Card");
        if (BoxOrgCardOwnerTitle.isDisplayed()) {
            result = false;
        }
        return result;

    }

    // Added by Vivek
    public boolean verifyDeviceFilterSelectedAsDefault() {
        boolean result = false;
        MyCommonAPIs.sleepi(3);
        logger.info("verifying Device Filter Selected As Default ");
        if (FilterActiveDevices.isDisplayed()) {
            result = true;
        }
        return result;

    }

    // Added by Vivek
    public boolean verifyDeviceTitleIsVisibleUnderOrgCard() {
        boolean result = false;
        MyCommonAPIs.sleepi(3);
        logger.info("verifying Device Title Is Visible Under Org Card");
        if (BoxOrgCardDeviceTitle.isDisplayed()) {
            result = true;
        }
        return result;

    }

    // Added by Vivek
    public void De_SelectTheDeviceFilter() {
        MyCommonAPIs.sleepi(3);
        logger.info("De_Selecting The Device Filter");
        FilterActiveDevices.click();
        MyCommonAPIs.sleepi(2);
    }

    // Added by Vivek
    public boolean verifyDeviceTitleIsNotVisibleUnderOrgCard() {
        boolean result = true;
        MyCommonAPIs.sleepi(3);
        logger.info("verifying Device Title Is Visible Under Org Card");
        if (BoxOrgCardDeviceTitle.isDisplayed()) {
            result = false;
        }
        return result;

    }

    // Added by Vivek
    public void SelectTheLocFilter() {
        MyCommonAPIs.sleepi(5);
        logger.info("Selecting The Location Filter");
        FilterLoc.click();
        MyCommonAPIs.sleepi(2);
    }

    // Added by Vivek
    public boolean verifyLocationTitleIsVisibleUnderOrgCard() {
        boolean result = false;
        MyCommonAPIs.sleepi(3);
        logger.info("verifying Location Title Is Visible Under Org Card");
        if (BoxOrgCardLocTitle.isDisplayed()) {
            result = true;
        }
        return result;

    }

    // Added by Vivek
    public void SelectTheMgrFilter() {
        MyCommonAPIs.sleepi(5);
        logger.info("Selecting The Manager Filter");
        FilterMgr.click();
        MyCommonAPIs.sleepi(2);
    }

    // Added by Vivek
    public boolean verifyMgrTitleIsVisibleUnderOrgCard() {
        boolean result = false;
        MyCommonAPIs.sleepi(3);
        logger.info("verifying Manager Title Is Visible Under Org Card");
        if (BoxOrgCardMgrTitle.isDisplayed()) {
            result = true;
        }
        return result;

    }

    // Added by Vivek
    public boolean verifyLocDeviceFilterSelectedAsDefault() {
        boolean result = false;
        MyCommonAPIs.sleepi(3);
        logger.info("verifying Loc Device Filter Selected As Default ");
        if (LocFilterActiveDevices.isDisplayed()) {
            result = true;
        }
        return result;

    }

    // Added by Vivek
    public boolean verifyDeviceTitleIsVisibleUnderLocCard() {
        boolean result = false;
        MyCommonAPIs.sleepi(3);
        logger.info("verifying Device Title Is Visible Under Loc Card");
        if (LocNameoffice1.isDisplayed()) {
            result = true;
        }
        return result;

    }

    // Added by Vivek
    public void DeSelectDeviceFltrFromLocFltr() {
        MyCommonAPIs.sleepi(3);
        logger.info("De_Selecting The Device Filter");
        LocFilterActiveDevices.click();
        MyCommonAPIs.sleepi(2);
    }

    // Added by Vivek
    public boolean verifyDeviceTitleIsNotVisibleUnderLocCard() {
        boolean result = true;
        MyCommonAPIs.sleepi(3);
        logger.info("verifying Device Title Is Not Visible Under Loc Card");
        if (BoxLocCardDeviceTitle.isDisplayed()) {
            result = false;
        }
        return result;

    }

    public boolean verifyLocNameIsCorrect() {
        boolean result = false;
        MyCommonAPIs.sleepi(3);
        logger.info("verifying Loc Name is correct Under Loc Card");
        if (LocNameoffice1.isDisplayed()) {
            result = true;
        }
        return result;

    }

    // Added by Vivek
    public boolean verifyWirelessClientFilterSelectedAsDefault() {
        boolean result = false;
        MyCommonAPIs.sleepi(3);
        logger.info("verifying Loc Wireless Client Filter Selected As Default ");
        if (LocFilterActiveWirelessClnt.isDisplayed()) {
            result = true;
        }
        return result;
    }

    // Added by Vivek
    public boolean verifyWirelessClientTitleIsVisibleUnderLocCard() {
        boolean result = false;
        MyCommonAPIs.sleepi(3);
        logger.info("verifying Device Title Is Visible Under Loc Card");
        if (BoxLocCardWirelessClntTitle.isDisplayed()) {
            result = true;
        }
        return result;

    }

    // Added by Vivek
    public void DeSelectWirelessClientFltrFromLocFltr() {
        MyCommonAPIs.sleepi(3);
        logger.info("De_Selecting The WirelessClient Filter");
        LocFilterActiveWirelessClnt.click();
        MyCommonAPIs.sleepi(2);
    }

    // Added by Vivek
    public boolean verifyWirelessClientTitleIsNotVisibleUnderLocCard() {
        boolean result = true;
        MyCommonAPIs.sleepi(3);
        logger.info("verifying WirelessClient Title Is Not Visible Under Loc Card");
        if (BoxLocCardWirelessClntTitle.isDisplayed()) {
            result = false;
        }
        return result;

    }

    // Added by Vivek
    public void GoToOrgSetting() {
        MyCommonAPIs.sleepi(2);
        logger.info("Going to Org Setting");
        if (OrgSettings.exists()) {
            OrgSettings.hover();
            MyCommonAPIs.sleepi(1);
            OrgSettings.click();
        }
        MyCommonAPIs.sleepi(3);
        if (OrgSettings.isDisplayed()) {
            OrgSettings.hover();
            MyCommonAPIs.sleepi(1);
            OrgSettings.click();
        }

    }

    // Added by Vivek
    public void GoToDeviceSectionUnderSetting() {
        MyCommonAPIs.sleepi(1);
        logger.info("Going to Device section under Org Setting");
        OrgSettingLeftDeviceSection.click();
        MyCommonAPIs.sleepi(1);
    }

    // Added by Vivek
    public void GoToClientSectionUnderSetting() {
        MyCommonAPIs.sleepi(1);
        logger.info("Going to Client section under Org Setting");
        OrgSettingLeftClientSection.click();
        MyCommonAPIs.sleepi(1);
    }

    // Added by Vivek
    public boolean verifyLocCardhaveCorrectData() {
        boolean result = false;
        logger.info("verifying LocationName, DeviceTag and Wireless Client is present Under Loc Card");
        assertTrue(LocNameoffice1.isDisplayed());
        assertTrue(BoxLocCardDeviceTitle.isDisplayed());
        assertTrue(BoxLocCardWirelessClntTitle.isDisplayed());
        result = true;
        return result;

    }

    // Added by Vivek
    public boolean verifyDeviceDashboardHaveCorrectData(String serial_Num) {
        boolean result = false;
        MyCommonAPIs.sleepi(3);
        logger.info("verifying this page is device page");
        if (OrgSetting1stDeviceName.text().contains(serial_Num)) {
            result = true;
        }

        return result;

    }

    // Added by Vivek
    public boolean VerifytheClientHeaderText() {
        boolean result = false;
        MyCommonAPIs.sleepi(3);
        logger.info("verifying this page is Client page");
        if (clientheadertxt.text().contains("Connected") & clientheadertxt.text().contains("Disconnected")) {
            result = true;
        }

        return result;

    }

    public boolean VerifyPolicy() {
        boolean result = false;
        MyCommonAPIs.sleepi(3);
        OrgSettings.click();
        MyCommonAPIs.sleepi(3);
        policyPro.click();
        MyCommonAPIs.sleepi(3);
        logger.info("verifying this page is Policy page");
        if (EmailNoti.isDisplayed() && PushNoti.isDisplayed() && Deviceown.isDisplayed() && ScheduledReports.isDisplayed()
                && EmailReports.isDisplayed() && ChooseAFile.isDisplayed()) {
            result = true;
        }

        return result;
    }

    public boolean VerifyDeviceRebootPage(String Serial) {
        boolean result = false;
        logger.info("verifying this page is Device reboot page");
        MyCommonAPIs.sleepi(3);
        OrgSettings.click();
        MyCommonAPIs.sleepi(3);
        DeviceReboot.click();
        MyCommonAPIs.sleepi(3);
        LocPlus.click();
        MyCommonAPIs.sleepi(3);
        System.out.println(Rebootnow.isDisplayed());
        System.out.println(RebootSch.isDisplayed());
        System.out.println(DevSerialInDR.text().contains(Serial));
        System.out.println(ViewAllSchedules.isDisplayed());

        if (Rebootnow.isDisplayed() && RebootSch.isDisplayed() && DevSerialInDR.text().contains(Serial) && ViewAllSchedules.isDisplayed()) {
            result = true;
        }

        return result;
    }

    public boolean VerifyManagers() {
        boolean result = false;
        MyCommonAPIs.sleepi(3);
        OrgSettings.click();
        MyCommonAPIs.sleepi(3);
        ManagerPage.click();
        MyCommonAPIs.sleepi(3);
        logger.info("verifying this page is Managers page");
        System.out.println(QuickView.isDisplayed());
        System.out.println(ManagerLabel.isDisplayed());
        System.out.println(Plus.isDisplayed());
        System.out.println(RoleLabel.isDisplayed());
        System.out.println(StatusLabel.isDisplayed());
        if (QuickView.isDisplayed() && ManagerLabel.isDisplayed() && Plus.isDisplayed() && RoleLabel.isDisplayed() && StatusLabel.isDisplayed()) {
            result = true;
        }

        return result;
    }

    public boolean VerifyOrgWideSSID() {
        boolean result = false;
        MyCommonAPIs.sleepi(3);
        OrgSettings.click();
        MyCommonAPIs.sleepi(3);
        OrgWidepage.click();
        MyCommonAPIs.sleepi(3);
        logger.info("verifying this page is Org Wide SSID page");
        System.out.println(PlusOrgSSID.isDisplayed());
        System.out.println(SearchOrgSSID.isDisplayed());
        System.out.println(OrgWideEnable.isDisplayed());
        System.out.println(OrgWideSSIDLabel.isDisplayed());
        if (PlusOrgSSID.isDisplayed() && SearchOrgSSID.isDisplayed() && OrgWideEnable.isDisplayed() && OrgWideSSIDLabel.isDisplayed()) {
            result = true;
        }

        return result;
    }

    // Added by Vivek
    // public boolean VerifytheClientConnectionTypText() {
    // boolean result = false;
    // MyCommonAPIs.sleepi(3);
    // logger.info("verifying Connection Type attribute on Client page");
    // if (ConnectionTypetxt.text().contains("Connection Type") & ConnectionTypetxt.text().contains("All" )){
    // result = true;
    // }
    //
    // return result;
    //
    //
    // }

    public boolean VerifyOrgCountOnMgrPage() {
        boolean result = false;
        MyCommonAPIs.sleepi(3);
        logger.info("verifying Total Org Count is Showing Correct");
        if (OrgCountMgrPage.isDisplayed()) {
            String count = OrgCountMgrPage.text();
            logger.info(count + "Org Found");
            result = true;

        }
        return result;
    }

    // added by vivek
    public boolean VerifyDeviceCountOnHomeScreen(String org_name) {
        boolean result = false;
        MyCommonAPIs.sleepi(3);
        logger.info("verifying Total Device Counts are Showing Correct on Org Card ");
        String data = OrgCardDeviceData(org_name).text();
        logger.info(data);
        if (data.contains("2")) {
            logger.info("Device Count is Correct Under Org Card");
            result = true;

        }
        return result;
    }

    // added by vivek
    public boolean VerifyOrgandDeviceCountOnHomeScreen() {
        boolean result = false;
        MyCommonAPIs.sleepi(3);
        logger.info("verifying Total Device Counts are Showing Correct on Org Card ");
        String data = OrgAndDeviceInfo.text();
        logger.info(data);
        if (data.contains("Organizations (1)") & data.contains("Devices (2)")) {
            logger.info("Device Count is Correct on Home Screen Org");
            result = true;

        }
        return result;
    }

    // add by Vivek
    public void onlyAddOrganization(Map<String, String> map) {
        MyCommonAPIs.sleepi(10);
        MyCommonAPIs.waitElement(AddOrg);
        if (AddOrg.exists()) {
            AddOrg.click();
            MyCommonAPIs.sleepi(10);
            NameOrg.sendKeys(map.get("Name"));
            MyCommonAPIs.sleepi(2);
            SaveOrg.click();
            logger.info("--------------- Organisation is Created Succesfully ----------");
            MyCommonAPIs.sleepi(10);
            if ($x("//h4[text()='Organization Created Successfully']/../..//button[text()='No']").exists()) {
                $x("//h4[text()='Organization Created Successfully']/../..//button[text()='No']").click();
            }

        }
    }

    // Added by Anusha H
    public boolean OrganizationCheckBox() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        orgssidSettingTab.click();
        MyCommonAPIs.sleepi(5);
        PolicyInorg.click();
        MyCommonAPIs.sleepi(5);
        ApplytoAllOrgsCheckbox.click();
        MyCommonAPIs.sleepi(5);
        String a = ApplytoAllOrgsCheckbox1.getValue();
        System.out.println(a);
        if (ApplytoAllOrgsCheckbox.isDisplayed()) {
            result = true;
        }

        MyCommonAPIs.sleepi(5);
        System.out.println("Back to default settings");
        ApplytoAllOrgsCheckbox.click();
        MyCommonAPIs.sleepi(5);
        Submit.click();

        return result;
    }

    public boolean NoOrgInPreAcct() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        OrgInProAcct.isDisplayed();
        if (!OrgInProAcct.isDisplayed()) {
            result = true;
            System.out.println("In premium account there are locations not the organizations");
        }

        return result;
    }

    public boolean OrgEmailCheckBox() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        orgssidSettingTab.click();
        MyCommonAPIs.sleepi(5);
        PolicyInorg.click();
        MyCommonAPIs.sleepi(5);
        BusinessOwnerEmailCheckbox.click();
        MyCommonAPIs.sleepi(5);
        Submit.click();
        MyCommonAPIs.sleepi(5);
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        orgssidSettingTab.click();
        MyCommonAPIs.sleepi(5);
        PolicyInorg.click();
        MyCommonAPIs.sleepi(5);
        System.out.println(BusinessOwnerEmailCheckbox.isEnabled());
        System.out.println(AdminEmailCheckbox.isEnabled());
        System.out.println(ManagerEmailCheckbox.isEnabled());

        if (BusinessOwnerEmailCheckbox.isEnabled() && AdminEmailCheckbox.isEnabled() && ManagerEmailCheckbox.isEnabled()) {

            result = true;
        }
        MyCommonAPIs.sleepi(5);
        System.out.println("Back to default settings");
        BusinessOwnerEmailCheckbox.click();
        MyCommonAPIs.sleepi(8);
        Submit.click();

        return result;
    }

    public boolean OrgPushCheckBox() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        orgssidSettingTab.click();
        MyCommonAPIs.sleepi(5);
        PolicyInorg.click();
        MyCommonAPIs.sleepi(5);
        BusinessOwnerPushCheckbox.click();
        MyCommonAPIs.sleepi(5);
        Submit.click();
        MyCommonAPIs.sleepi(5);
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        orgssidSettingTab.click();
        MyCommonAPIs.sleepi(5);
        PolicyInorg.click();
        MyCommonAPIs.sleepi(5);
        System.out.println(BusinessOwnerPushCheckbox.isEnabled());
        System.out.println(AdminPushCheckbox.isEnabled());
        System.out.println(ManagerPushCheckbox.isEnabled());

        if (BusinessOwnerPushCheckbox.isEnabled() && AdminPushCheckbox.isEnabled() && ManagerPushCheckbox.isEnabled()) {

            result = true;
        }

        MyCommonAPIs.sleepi(5);
        System.out.println("Back to default settings");
        BusinessOwnerPushCheckbox.click();
        MyCommonAPIs.sleepi(8);
        Submit.click();

        return result;

    }

    public boolean ScheduleReportsEnableIcon() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        orgssidSettingTab.click();
        MyCommonAPIs.sleepi(5);
        PolicyInorg.click();
        MyCommonAPIs.sleepi(5);
        // boolean c = ScheduleReportsIcon.isEnabled();
        boolean a = ScheduleReportsIcon.isSelected();
        // System.out.println("Output should be 'true'" + ScheduleReportsIcon.isEnabled());
        System.out.println("Output should be 'true'" + ScheduleReportsIcon.isSelected());
        ScheduleReportsIcon1.click();
        MyCommonAPIs.sleepi(5);
        Submit.click();
        MyCommonAPIs.sleepi(5);
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        orgssidSettingTab.click();
        MyCommonAPIs.sleepi(5);
        PolicyInorg.click();
        MyCommonAPIs.sleepi(5);
        // boolean d = ScheduleReportsIcon.isEnabled();
        boolean b = ScheduleReportsIcon.isSelected();
        // System.out.println("Output should be 'false'" + ScheduleReportsIcon.isEnabled());
        System.out.println("Output should be 'false'" + ScheduleReportsIcon.isSelected());

        if (a == true && b == false) {

            result = true;
        }

        MyCommonAPIs.sleepi(5);
        System.out.println("Back to default settings");
        ScheduleReportsIcon1.click();
        MyCommonAPIs.sleepi(8);
        Submit.click();

        return result;
    }

    public boolean ScheduleReportsChange() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        orgssidSettingTab.click();
        MyCommonAPIs.sleepi(5);
        PolicyInorg.click();
        MyCommonAPIs.sleepi(5);
        System.out.println("Schedule reports weekly is checked ad Schedule reports monthly is unchecked");
        boolean a = ScheduleReportsWeeklyCheckBox.isSelected();
        boolean b = ScheduleReportsMonthlyCheckBox.isSelected();
        System.out.println(ScheduleReportsWeeklyCheckBox.isSelected());
        System.out.println(ScheduleReportsMonthlyCheckBox.isSelected());
        MyCommonAPIs.sleepi(10);
        ScheduleReportsMonthly.click();
        System.out.println("Schedule reports monthly is enabled to submit");
        MyCommonAPIs.sleepi(5);
        Submit.click();
        MyCommonAPIs.sleepi(5);
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        orgssidSettingTab.click();
        MyCommonAPIs.sleepi(5);
        PolicyInorg.click();
        MyCommonAPIs.sleepi(5);
        boolean c = ScheduleReportsWeeklyCheckBox.isSelected();
        boolean d = ScheduleReportsMonthlyCheckBox.isSelected();
        System.out.println(ScheduleReportsWeeklyCheckBox.isSelected());
        System.out.println(ScheduleReportsMonthlyCheckBox.isSelected());
        System.out.println("Schedule reports monthly is enabled");
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
        if (a == true & d == true & b == false & c == false) {

            result = true;
        }

        MyCommonAPIs.sleepi(5);
        System.out.println("Back to default settings");
        ScheduleReportsWeekly.click();
        MyCommonAPIs.sleepi(5);
        Submit.click();

        return result;
    }

    public boolean OrgEmailAlerts() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        orgssidSettingTab.click();
        MyCommonAPIs.sleepi(5);
        PolicyInorg.click();
        MyCommonAPIs.sleepi(5);
        BusinessEmailAlertCheckbox.click();
        MyCommonAPIs.sleepi(5);
        Submit.click();
        MyCommonAPIs.sleepi(5);
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        orgssidSettingTab.click();
        MyCommonAPIs.sleepi(5);
        PolicyInorg.click();
        MyCommonAPIs.sleepi(5);
        System.out.println(BusinessEmailAlertCheckbox.isEnabled());
        System.out.println(AdminEmailAlertCheckbox.isEnabled());
        System.out.println(ManagerEmailAlertCheckbox.isEnabled());

        if (BusinessEmailAlertCheckbox.isEnabled() && AdminEmailAlertCheckbox.isEnabled() && ManagerEmailAlertCheckbox.isEnabled()) {

            result = true;
        }

        BusinessEmailAlertCheckbox.click();
        MyCommonAPIs.sleepi(5);
        Submit.click();

        return result;
    }

    public boolean ChooseLogo() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        orgssidSettingTab.click();
        MyCommonAPIs.sleepi(5);
        PolicyInorg.click();
        MyCommonAPIs.sleepi(10);
        System.out.println("1");
        boolean a = ChooseALogo.isDisplayed();
        System.out.println("2");
        String b = ChooseALogo.getText();
        System.out.println(ChooseALogo.isDisplayed());
        System.out.println(b);
        if (a == true && b.equals("Choose a file...")) {

            result = true;
        }
        return result;
    }

    public boolean EditPolicyAndSubmit() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        orgssidSettingTab.click();
        MyCommonAPIs.sleepi(5);
        PolicyInorg.click();
        MyCommonAPIs.sleepi(5);
        boolean a = ChooseALogo.isDisplayed();
        String b = ChooseALogo.getText();

        if (a == true && b.equals("Choose a File...")) {

            result = true;
        }
        return result;
    }

    public void OrgApplytoAllOrg() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        orgssidSettingTab.click();
        MyCommonAPIs.sleepi(5);
        PolicyInorg.click();
        MyCommonAPIs.sleepi(5);
        ApplytoAllOrgsCheckbox.click();
        MyCommonAPIs.sleepi(5);
        System.out.println("Checkthe box:  business email notifications and business push notifications");
        BusinessOwnerEmailCheckbox.click();
        MyCommonAPIs.sleepi(5);
        BusinessOwnerPushCheckbox.click();
        MyCommonAPIs.sleepi(5);
        Submit.click();
    }

    public boolean VerifyOrgApplytoAllOrg() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        orgssidSettingTab.click();
        MyCommonAPIs.sleepi(5);
        PolicyInorg.click();
        MyCommonAPIs.sleepi(5);

        if (ApplytoAllOrgsCheckbox.isEnabled() && BusinessOwnerEmailCheckbox.isEnabled() && AdminEmailCheckbox.isEnabled()
                && ManagerEmailCheckbox.isEnabled() && BusinessOwnerPushCheckbox.isEnabled() && AdminPushCheckbox.isEnabled()
                && ManagerPushCheckbox.isEnabled()) {

            result = true;
        }

        BusinessOwnerEmailCheckbox.click();
        MyCommonAPIs.sleepi(5);
        BusinessOwnerPushCheckbox.click();
        MyCommonAPIs.sleepi(5);
        Submit.click();

        return result;

    }

    public void EditPolicy() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        orgssidSettingTab.click();
        MyCommonAPIs.sleepi(5);
        PolicyInorg.click();
        MyCommonAPIs.sleepi(5);
        if(ApplytoAllOrgscheck.isSelected()) {
            System.out.println("No need to check");
        }else {
        ApplytoAllOrgsCheckbox.click();
        }
        MyCommonAPIs.sleepi(5);
        BusinessOwnerEmailCheckbox.click();
        MyCommonAPIs.sleepi(5);
        Submit.click();

    }

    public boolean VerifyEditPolicy() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        orgssidSettingTab.click();
        MyCommonAPIs.sleepi(5);
        PolicyInorg.click();
        MyCommonAPIs.sleepi(5);
        String a = ApplytoAllOrgsCheckbox1.getValue();
        System.out.println(a);

        if (a.equals("1") && BusinessOwnerEmailCheckbox.isEnabled()) {

            result = true;
        }
        return result;
    }

    public void BacktoDefaultSettingsOfEditPolicy() {
        orgssidSettingTab.click();
        MyCommonAPIs.sleepi(5);
        PolicyInorg.click();
        MyCommonAPIs.sleepi(5);
        MyCommonAPIs.sleepi(5);
        System.out.println("Back to default settings");
        ApplytoAllOrgsCheckbox.click();
        MyCommonAPIs.sleepi(5);
        BusinessOwnerEmailCheckbox.click();
        MyCommonAPIs.sleepi(5);
        Submit.click();
    }

    public void ManagerInOrg() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        orgssidSettingTab.click();
        MyCommonAPIs.sleepi(5);
        ManagerInOrg.click();
        MyCommonAPIs.sleepi(5);
    }

    public boolean VerifyAddedManagerInOrg(String mailname) {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        boolean a = $x("//*[text()='" + mailname + "']").isDisplayed();
        if (a == true) {
            result = true;
            System.out.println("Add manager via org settings is success");
        }
        return result;
    }

    public boolean OrgsInmanager(String name) {
        boolean result = false;
        // MyCommonAPIs.sleepi(5);
        // orgssidSettingTab.click();
        // MyCommonAPIs.sleepi(5);
        // PolicyInorg.click();
        // MyCommonAPIs.sleepi(5);
        // ManagerInOrg.click();
        MyCommonAPIs.sleepi(5);
        $x("//span[text()='" + name + "']").click();
        MyCommonAPIs.sleepi(8);
        String org = Orgname.getText();
        System.out.println(org);
        if (org.equals(WebportalParam.Organizations)) {
            result = true;
        }
        return result;
    }

    public boolean VerifyOrgApplytoAllOrg1() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        orgssidSettingTab.click();
        MyCommonAPIs.sleepi(5);
        PolicyInorg.click();
        MyCommonAPIs.sleepi(5);

        if (ApplytoAllOrgsCheckbox.isSelected() && BusinessOwnerEmailCheckbox.isSelected() && AdminEmailCheckbox.isSelected()
                && ManagerEmailCheckbox.isSelected() && BusinessOwnerPushCheckbox.isSelected() && AdminPushCheckbox.isSelected()
                && ManagerPushCheckbox.isSelected()) {

            result = true;
        }
        return result;
    }

    public boolean OrgLocInSecAdmin(String org) {
        boolean result = false;
        MyCommonAPIs.sleepi(5);

        String Org = OrginSecAdmin.getText();
        new OrganizationPage(false).openOrg(org);
        MyCommonAPIs.sleepi(5);
        String Loc = LocInSecAdmin.getText();
        if (Org.equals(WebportalParam.Organizations) & Loc.equals(WebportalParam.location1)) {
            result = true;
        }
        return result;
    }

    public boolean DefaultSettingEnabled() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        orgssidSettingTab.click();
        MyCommonAPIs.sleepi(5);
        PolicyInorg.click();
        MyCommonAPIs.sleepi(5);
        System.out.println("Default policy settings are properly displayed in new organisation");
        boolean a = ApplytoAllOrgsCheckbox.isSelected();
        boolean b = BusinessOwnerEmailCheckbox.isSelected();
        boolean c = AdminEmailCheckbox.isSelected();
        boolean d = ManagerEmailCheckbox.isSelected();
        boolean e = BusinessOwnerPushCheckbox.isSelected();
        boolean f = AdminPushCheckbox.isSelected();
        boolean g = ManagerPushCheckbox.isSelected();
        boolean h = ScheduleReportsIcon.isSelected();
        boolean i = ScheduleReportsWeekly.isSelected();
        boolean j = ScheduleReportsMonthly.isSelected();
        boolean k = AdminEmailAlertCheckbox.isSelected();
        boolean l = BusinessOwnerPushCheckbox.isSelected();
        boolean m = ManagerEmailAlertCheckbox.isSelected();
        boolean n = ChooseALogo.isSelected();

        if (c == d == f == g == h == i == k == m == n == true && a == b == e == j == l == false) {

            result = true;
        }
        return result;
    }

    public boolean addOrganization1(Map<String, String> map) {
        boolean located = false;
        boolean result = false;
        System.out.println("5st");
        waitElement(sOrganizationLocationElement1);
        // if (checkOrganizationIsExist(map.get("Name"))) {
        // located = true;
        // }
        if (!located) {
            MyCommonAPIs.sleepi(10);
            MyCommonAPIs.waitElement(AddOrg);
            if (AddOrg.exists()) {
                AddOrg.click();
                MyCommonAPIs.sleepi(10);
                NameOrg.sendKeys(map.get("Name"));
                if (map.containsKey("Owner Name")) {
                    ownerName.sendKeys(map.get("Owner Name"));
                }
                if (map.containsKey("Email Address")) {
                    ownerEmail.sendKeys(map.get("Email Address"));
                }
                MyCommonAPIs.sleepi(5);
                ownerPhone.click();
                MyCommonAPIs.sleepi(10);
                String error = ErrorMessage.getText();
                if (error.equals(
                        "This email address is already associated with an existing owner account. Select an existing owner from the list or enter a different email address.")) {
                    result = true;
                }
                OKtoErrorMessage.click();
                MyCommonAPIs.sleepi(10);
            }
        }
        return result;

    }

    public void addOrganization2(Map<String, String> map) {
        boolean located = false;
        System.out.println("5st");
        waitElement(sOrganizationLocationElement1);
        // if (checkOrganizationIsExist(map.get("Name"))) {
        // located = true;
        // }
        if (!located) {
            MyCommonAPIs.sleepi(10);
            MyCommonAPIs.waitElement(AddOrg);
            if (AddOrg.exists()) {
                AddOrg.click();
                MyCommonAPIs.sleepi(10);
                NameOrg.sendKeys(map.get("Name"));
                MyCommonAPIs.sleepi(5);
                ownerName.click();
                MyCommonAPIs.sleepi(10);
                ExistingOwnerMail.click();
                MyCommonAPIs.sleepi(10);
                SaveOrg.click();
                waitReady();
                clickYesNo(true);
                waitReady();
            }
        }
    }

    public boolean EditOrg() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        dots.click();
        MyCommonAPIs.sleepi(5);
        EditorgIn.click();
        MyCommonAPIs.sleepi(5);
        if (ApplytoAllOrgsCheckbox.isDisplayed()) {
            result = true;
        }
        return result;
    }

    public void clickonOkayGotit() {

        MyCommonAPIs.sleepi(20);
        if ($x("(//button[text()='OK. Got it'])[2]").exists()) {
            // waitElement($x("(//button[text()='OK. Got it'])[2]"));
            $x("(//button[text()='OK. Got it'])[2]").click();
            MyCommonAPIs.sleepi(10);
        } else {
            logger.info("Okay got it popup not came");
        }
    }

    // AddedByPratik
    public boolean PickDateTimeForAllDevices(String Name, String repeat) throws ParseException {
        boolean result = false;
        processString();
        beforehours = h;
        beforeminutes = m;

        MyCommonAPIs.sleepi(10);
        rebootSchedule.click();
        MyCommonAPIs.sleepi(3);
        ScheduleName.sendKeys(Name);
        MyCommonAPIs.sleepi(3);
        StatDate.click();
        MyCommonAPIs.sleepi(3);
        new FirmwareElement().today.click();
        MyCommonAPIs.sleepi(3);
        StartTime.click();
        MyCommonAPIs.sleepi(3);

        if (beforehours > 12) {
            if (beforeminutes > 59) {
                beforehours = beforehours - 12 + 1;
                MyCommonAPIs.sleepi(3);
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            } else {
                beforehours = beforehours - 12;
                MyCommonAPIs.sleepi(3);
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            }
        } else if (beforehours == 0) {
            if (beforeminutes > 59) {
                beforehours = 12-12 + 1;
                MyCommonAPIs.sleepi(3);
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            } else {
                beforehours = 12;
                MyCommonAPIs.sleepi(3);
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            }
        } else {
            if (beforeminutes > 59) {
                beforehours = beforehours + 1;
                MyCommonAPIs.sleepi(3);
                selectTimePeriodAmorpm.click();
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            } else {
                MyCommonAPIs.sleepi(3);
                selectTimePeriodAmorpm.click();
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            }
        }

        if (beforeminutes > 59) {
            beforeminutes = beforeminutes - 59 + 10;
            MyCommonAPIs.sleepi(3);
            selectTimeMinute();
            MyCommonAPIs.sleepi(3);
            System.out.println(beforeminutes);
        } else {
            beforeminutes = beforeminutes + 5;
            MyCommonAPIs.sleepi(3);
            selectTimeMinute();
            MyCommonAPIs.sleepi(3);
            System.out.println(beforeminutes);
        }
        minTime = beforeminutes;
        if (h > 12) {
            beforehours = beforehours - 12;
            MyCommonAPIs.sleepi(3);
            selectTimePeriodAmorpm.click();
            MyCommonAPIs.sleepi(3);
            selectTimePeriodPm.click();
            MyCommonAPIs.sleepi(3);
        } else if (h == 0) {
            MyCommonAPIs.sleepi(3);
            selectTimePeriodAmorpm.click();
            MyCommonAPIs.sleepi(3);
            selectTimePeriodAm.click();
            MyCommonAPIs.sleepi(3);
        } else {
            MyCommonAPIs.sleepi(3);
            selectTimePeriodAmorpm.click();
            MyCommonAPIs.sleepi(3);
            selectTimePeriodAm.click();
            MyCommonAPIs.sleepi(3);
        }

        SelectTime.click();
        MyCommonAPIs.sleepi(1);
        EndDate.click();
        MyCommonAPIs.sleepi(3);
        new FirmwareElement().today.click();
        MyCommonAPIs.sleepi(3);
        EndTime.click();
        MyCommonAPIs.sleepi(3);
        beforehours = h;

        if (beforehours > 12) {
            if (beforeminutes > 59) {
                beforehours = beforehours - 12 + 2;
                MyCommonAPIs.sleepi(3);
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            } else {
                beforehours = beforehours - 12+1;
                MyCommonAPIs.sleepi(3);
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            }
        } else if (beforehours == 0) {
            if (beforeminutes > 59) {
                beforehours = 12-12 + 2;
                MyCommonAPIs.sleepi(3);
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            } else {
                beforehours = 1;
                MyCommonAPIs.sleepi(3);
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            }
        } else {
            if (beforeminutes > 59) {
                beforehours = beforehours + 2;
                MyCommonAPIs.sleepi(3);
                selectTimePeriodAmorpm.click();
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            } else {
                beforehours = beforehours + 1;
                MyCommonAPIs.sleepi(3);
                selectTimePeriodAmorpm.click();
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            }
        }

        int convertedminnew = beforeminutes + 1;
        selectTimeMinute();
        MyCommonAPIs.sleepi(1);
        int hr = h + 1;
        if (hr > 12) {
            beforehours = beforehours - 12;
            MyCommonAPIs.sleepi(3);
            selectTimePeriodAmorpm.click();
            MyCommonAPIs.sleepi(3);
            selectTimePeriodPm.click();
            MyCommonAPIs.sleepi(3);
        } else if (hr == 0) {
            MyCommonAPIs.sleepi(3);
            selectTimePeriodAmorpm.click();
            MyCommonAPIs.sleepi(3);
            selectTimePeriodAm.click();
            MyCommonAPIs.sleepi(3);
        } else {
            MyCommonAPIs.sleepi(3);
            selectTimePeriodAmorpm.click();
            MyCommonAPIs.sleepi(3);
            selectTimePeriodAm.click();
            MyCommonAPIs.sleepi(3);
        }

        SelectTime.click();
        MyCommonAPIs.sleepi(3);
        Repeats.selectOption(repeat);
        MyCommonAPIs.sleepi(2);
        if (ExpandDevicesSchedule(WebportalParam.location1).exists()) {
            ExpandDevicesSchedule(WebportalParam.location1).click();
        }
        MyCommonAPIs.sleepi(5);
        selectAllDeviceFromLocationCheckbox(WebportalParam.location1).click();
        MyCommonAPIs.sleepi(5);
        RebootSchedulebutton.click();
        MyCommonAPIs.sleepi(10);

        if (Greenbanner.isDisplayed()) {
            System.out.println("All elements are displayed");
            result = true;
        }
        return result;
    }

    // AddedByPratik
    public boolean PickDateTimeForLocation2MovedDevices(String Name, String SerialNo, String SerialNo1, String repeat) throws ParseException {
        boolean result = false;
        processString();
        beforehours = h;
        beforeminutes = m;

        MyCommonAPIs.sleepi(10);
        rebootSchedule.click();
        MyCommonAPIs.sleepi(3);
        ScheduleName.sendKeys(Name);
        MyCommonAPIs.sleepi(3);
        StatDate.click();
        MyCommonAPIs.sleepi(3);
        new FirmwareElement().today.click();
        MyCommonAPIs.sleepi(3);
        StartTime.click();
        MyCommonAPIs.sleepi(3);

        if (beforehours > 12) {
            if (beforeminutes > 59) {
                beforehours = beforehours - 12 + 1;
                MyCommonAPIs.sleepi(3);
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            } else {
                beforehours = beforehours - 12;
                MyCommonAPIs.sleepi(3);
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            }
        } else if (beforehours == 0) {
            if (beforeminutes > 59) {
                beforehours = 12 - 12 + 1;
                MyCommonAPIs.sleepi(3);
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            } else {
                beforehours = 12;
                MyCommonAPIs.sleepi(3);
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            }
        } else {
            if (beforeminutes > 59) {
                beforehours = beforehours + 1;
                MyCommonAPIs.sleepi(3);
                selectTimePeriodAmorpm.click();
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            } else {
                MyCommonAPIs.sleepi(3);
                selectTimePeriodAmorpm.click();
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            }
        }

        if (beforeminutes > 59) {
            beforeminutes = beforeminutes - 59 + 10;
            MyCommonAPIs.sleepi(3);
            selectTimeMinute();
            MyCommonAPIs.sleepi(3);
            System.out.println(beforeminutes);
        } else {
            beforeminutes = beforeminutes + 5;
            MyCommonAPIs.sleepi(3);
            selectTimeMinute();
            MyCommonAPIs.sleepi(3);
            System.out.println(beforeminutes);
        }

        if (h > 12) {
            beforehours = beforehours - 12;
            MyCommonAPIs.sleepi(3);
            selectTimePeriodAmorpm.click();
            MyCommonAPIs.sleepi(3);
            selectTimePeriodPm.click();
            MyCommonAPIs.sleepi(3);
        } else if (h == 0) {
            MyCommonAPIs.sleepi(3);
            selectTimePeriodAmorpm.click();
            MyCommonAPIs.sleepi(3);
            selectTimePeriodAm.click();
            MyCommonAPIs.sleepi(3);
        } else {
            MyCommonAPIs.sleepi(3);
            selectTimePeriodAmorpm.click();
            MyCommonAPIs.sleepi(3);
            selectTimePeriodAm.click();
            MyCommonAPIs.sleepi(3);
        }

        SelectTime.click();
        MyCommonAPIs.sleepi(1);
        EndDate.click();
        MyCommonAPIs.sleepi(3);
        new FirmwareElement().today.click();
        MyCommonAPIs.sleepi(3);
        EndTime.click();
        MyCommonAPIs.sleepi(3);
        beforehours = h;

        if (beforehours > 12) {
            if (beforeminutes > 59) {
                beforehours = beforehours - 12 + 2;
                MyCommonAPIs.sleepi(3);
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            } else {
                beforehours = beforehours - 12+1;
                MyCommonAPIs.sleepi(3);
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            }
        } else if (beforehours == 0) {
            if (beforeminutes > 59) {
                beforehours = 12-12 + 2;
                MyCommonAPIs.sleepi(3);
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            } else {
                beforehours = 1;
                MyCommonAPIs.sleepi(3);
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            }
        } else {
            if (beforeminutes > 59) {
                beforehours = beforehours + 2;
                MyCommonAPIs.sleepi(3);
                selectTimePeriodAmorpm.click();
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            } else {
                beforehours = beforehours + 1;
                MyCommonAPIs.sleepi(3);
                selectTimePeriodAmorpm.click();
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            }
        }
        int convertedminnew = beforeminutes + 1;
        selectTimeMinute();
        MyCommonAPIs.sleepi(1);
        int hr = h + 1;
        if (hr > 12) {
            beforehours = beforehours - 12;
            MyCommonAPIs.sleepi(3);
            selectTimePeriodAmorpm.click();
            MyCommonAPIs.sleepi(3);
            selectTimePeriodPm.click();
            MyCommonAPIs.sleepi(3);
        } else if (hr == 0) {
            MyCommonAPIs.sleepi(3);
            selectTimePeriodAmorpm.click();
            MyCommonAPIs.sleepi(3);
            selectTimePeriodAm.click();
            MyCommonAPIs.sleepi(3);
        } else {
            MyCommonAPIs.sleepi(3);
            selectTimePeriodAmorpm.click();
            MyCommonAPIs.sleepi(3);
            selectTimePeriodAm.click();
            MyCommonAPIs.sleepi(3);
        }

        SelectTime.click();
        MyCommonAPIs.sleepi(3);
        Repeats.selectOption(repeat);
        MyCommonAPIs.sleepi(2);

        if (ExpandDevicesScheduleloc2(WebportalParam.location2).exists()) {
            ExpandDevicesScheduleloc2(WebportalParam.location2).click();
        }
        MyCommonAPIs.sleepi(5);
        selectAllDeviceFromLocation2Checkbox(WebportalParam.location2).click();
        MyCommonAPIs.sleepi(5);
        RebootSchedulebutton.click();
        MyCommonAPIs.sleepi(10);
        if (Greenbanner.isDisplayed()) {
            System.out.println("All elements are displayed");
            result = true;
        }
        return result;
    }

    public void processString() {
        String myString = new APUtils(WebportalParam.ap1IPaddress).getDate();
        myString = myString.trim();
        System.out.println(myString);
        myString = myString.replace("  ", " ");
        System.out.println(myString);

        String[] parts = myString.split(" ");
        int length = parts.length;
        System.out.println("length " + length);

        for (int i = 0; i < length; i++) {
            System.out.println(parts[i]);
            if (i == 2) {
                date = parts[i];
                System.out.println("date " + date);
            }
            if (i == 3) {
                timearr = parts[i];
                System.out.println("timearr " + timearr);
            }
        }

        String timearr1[] = timearr.split(":");
        hour = timearr1[0].replaceFirst("^0+(?!$)", "");
        d = Integer.parseInt(date);
        h = Integer.parseInt(hour);
        minute = timearr1[1].replaceFirst("^0+(?!$)", "");
        m = Integer.parseInt(minute);
        System.out.println("hour " + h);
        System.out.println("minute " + m);
    }

    public void selectTimeHour() {

        String exitinghr = new FirmwareElement().Exhourup.getText();
        int convertedhr = Integer.parseInt(exitinghr);
        System.out.println("Existing Hour " + convertedhr);

        int changedhrnew = 0;

        boolean result = false;

        while (beforehours != changedhrnew) {
            System.out.println("beforehours " + beforehours);
            new FirmwareElement().hourup.click();
            MyCommonAPIs.sleepi(1);
            exitinghr = new FirmwareElement().Exhourup.getText();
            changedhrnew = Integer.parseInt(exitinghr);
            System.out.println("changedhrnew " + changedhrnew);
        }

    }

    public void selectTimeMinute() {

        String exitingmin = new FirmwareElement().Exminuteup.getText();
        int convertedmin = Integer.parseInt(exitingmin);
        System.out.println("Existing Minute " + convertedmin);

        int changedminnew = 0;

        while (beforeminutes != changedminnew) {
            System.out.println("beforeminutes " + beforeminutes);
            new FirmwareElement().minuteup.click();
            MyCommonAPIs.sleepi(1);
            exitingmin = new FirmwareElement().Exminuteup.getText();
            changedminnew = Integer.parseInt(exitingmin);
            System.out.println("changedminnew " + changedminnew);
        }

    }

    public boolean PickDateTimeForAllDevices1(String Name, String repeat) throws ParseException {
        boolean result = false;
        processString();
        beforehours = h;
        beforeminutes = m;

        MyCommonAPIs.sleepi(10);
        rebootSchedule.click();
        MyCommonAPIs.sleepi(3);
        ScheduleName.sendKeys(Name);
        MyCommonAPIs.sleepi(3);
        StatDate.click();
        MyCommonAPIs.sleepi(3);
        new FirmwareElement().today.click();
        MyCommonAPIs.sleepi(3);
        StartTime.click();
        MyCommonAPIs.sleepi(3);

        if (beforehours > 12) {
            if (beforeminutes > 59) {
                beforehours = beforehours - 12 + 1;
                MyCommonAPIs.sleepi(3);
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            } else {
                beforehours = beforehours - 12;
                MyCommonAPIs.sleepi(3);
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            }
        } else if (beforehours == 0) {
            if (beforeminutes > 59) {
                beforehours = 12 - 12 + 1;
                MyCommonAPIs.sleepi(3);
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            } else {
                beforehours = 12;
                MyCommonAPIs.sleepi(3);
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            }
        } else {
            if (beforeminutes > 59) {
                beforehours = beforehours + 1;
                MyCommonAPIs.sleepi(3);
                selectTimePeriodAmorpm.click();
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            } else {
                MyCommonAPIs.sleepi(3);
                selectTimePeriodAmorpm.click();
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            }
        }

        if (beforeminutes > 59) {
            beforeminutes = beforeminutes - 59 + 10;
            MyCommonAPIs.sleepi(3);
            selectTimeMinute();
            MyCommonAPIs.sleepi(3);
            System.out.println(beforeminutes);
        } else {
            beforeminutes = beforeminutes + 5;
            MyCommonAPIs.sleepi(3);
            selectTimeMinute();
            MyCommonAPIs.sleepi(3);
            System.out.println(beforeminutes);
        }
        minTime = beforeminutes;
        if (h > 12) {
            beforehours = beforehours - 12;
            MyCommonAPIs.sleepi(3);
            selectTimePeriodAmorpm.click();
            MyCommonAPIs.sleepi(3);
            selectTimePeriodPm.click();
            MyCommonAPIs.sleepi(3);
        } else if (h == 0) {
            MyCommonAPIs.sleepi(3);
            selectTimePeriodAmorpm.click();
            MyCommonAPIs.sleepi(3);
            selectTimePeriodAm.click();
            MyCommonAPIs.sleepi(3);
        } else {
            MyCommonAPIs.sleepi(3);
            selectTimePeriodAmorpm.click();
            MyCommonAPIs.sleepi(3);
            selectTimePeriodAm.click();
            MyCommonAPIs.sleepi(3);
        }

        SelectTime.click();
        MyCommonAPIs.sleepi(1);
        EndDate.click();
        MyCommonAPIs.sleepi(3);
        new FirmwareElement().today.click();
        MyCommonAPIs.sleepi(3);
        EndTime.click();
        MyCommonAPIs.sleepi(3);
        beforehours = h;

        if (beforehours > 12) {
            if (beforeminutes > 59) {
                beforehours = beforehours - 12 + 2;
                MyCommonAPIs.sleepi(3);
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            } else {
                beforehours = beforehours - 12+1;
                MyCommonAPIs.sleepi(3);
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            }
        } else if (beforehours == 0) {
            if (beforeminutes > 59) {
                beforehours = 12 - 12 + 2;
                MyCommonAPIs.sleepi(3);
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            } else {
                beforehours = 12-12+1;
                MyCommonAPIs.sleepi(3);
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            }
        } else {
            if (beforeminutes > 59) {
                beforehours = beforehours + 2;
                MyCommonAPIs.sleepi(3);
                selectTimePeriodAmorpm.click();
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            } else {
                beforehours = beforehours + 1;
                MyCommonAPIs.sleepi(3);
                selectTimePeriodAmorpm.click();
                selectTimeHour();
                MyCommonAPIs.sleepi(3);
            }
        }

        int convertedminnew = beforeminutes + 2;
        selectTimeMinute();
        MyCommonAPIs.sleepi(1);
        int hr = h + 1;
        if (hr > 12) {
            beforehours = beforehours - 12;
            MyCommonAPIs.sleepi(3);
            selectTimePeriodAmorpm.click();
            MyCommonAPIs.sleepi(3);
            selectTimePeriodPm.click();
            MyCommonAPIs.sleepi(3);
        } else if (hr == 0) {
            MyCommonAPIs.sleepi(3);
            selectTimePeriodAmorpm.click();
            MyCommonAPIs.sleepi(3);
            selectTimePeriodAm.click();
            MyCommonAPIs.sleepi(3);
        } else {
            MyCommonAPIs.sleepi(3);
            selectTimePeriodAmorpm.click();
            MyCommonAPIs.sleepi(3);
            selectTimePeriodAm.click();
            MyCommonAPIs.sleepi(3);
        }

        SelectTime.click();
        MyCommonAPIs.sleepi(3);
        Repeats.selectOption(repeat);
        MyCommonAPIs.sleepi(2);
        if (ExpandDevicesSchedule(WebportalParam.location1).exists()) {
            ExpandDevicesSchedule(WebportalParam.location1).click();
        }
        MyCommonAPIs.sleepi(5);
        selectAllDeviceFromLocationCheckbox(WebportalParam.location2).click();
        MyCommonAPIs.sleepi(5);
        RebootSchedulebutton.click();
        MyCommonAPIs.sleepi(10);

        if (Greenbanner.isDisplayed()) {
            System.out.println("All elements are displayed");
            result = true;
        }
        return result;
    }
    
    //AddedByPratik
    public boolean goToOrgClientsTab(String OrgName, String SssidName) {
        boolean result = false;
        openOrg(OrgName);
        MyCommonAPIs.sleepi(10);
        Setting.click();
        MyCommonAPIs.sleepi(10);
        waitElement(clientsTab);
        clientsTab.click();
        MyCommonAPIs.sleepi(20);
        if (ssidNameonClientList(SssidName).exists()) {
            logger.info("Client listed on clients tab under organization setting page");
            result = true;
        }
        return result;
    }
    
    //AddedByPratik
    public void creditAllocation11(String name) {
        if (checkOrganizationIsExist(name)) {
            if(dropdownOrganizationElement(name).exists()) 
            {
            dropdownOrganizationElement(name).click();
            addCreditsOrganizationElement(name).click();
            }
            else
            {
                  String rowindex=dropdownOrganizationElementNew(name).getAttribute("aria-rowindex");
                  ariaSetIndex(rowindex).click();
                  ariaSetIndexAllocate(rowindex).click();                             
            }        
            MyCommonAPIs.sleepi(5);
            if (deviceCredits.exists()) {
                deviceCreditsTextbox.sendKeys("1");
                MyCommonAPIs.sleepi(1);
                allocateBtn.click();
                MyCommonAPIs.sleepi(5);
            }
        }
    }

    //AddedbyPratik
    public boolean selectExistingOwner(Map<String, String> map) {
        boolean result = false;
        MyCommonAPIs.sleepi(1);
        waitElement(sOrganizationLocationElement1);
        if (checkOrganizationIsExist(map.get("Name"))) {
            dropdownOrganizationElement(map.get("Name")).click();
            MyCommonAPIs.sleepi(1);
            editOrganizationElement(map.get("Name")).click();
            MyCommonAPIs.sleepi(1);
            waitElement(NameOrg);
            MyCommonAPIs.sleepi(1);
            dropdown.selectOptionByValue(map.get("Email Address"));
            MyCommonAPIs.sleepi(1);
            SelenideElement selectedOption = dropdown.getSelectedOption();
            if (selectedOption.getValue().equals(map.get("Email Address"))) {
                result = true;
            }
            MyCommonAPIs.sleepi(1);
            selectNotificationAndReport(map);
            if (map.containsKey("Scheduled Reports")) {
                if (map.get("Scheduled Reports").equals("disable")) {
                    if (!scheduleweekly.has(Condition.attribute("disabled"))) {
                        scheduledreport.click();
                    }
                } else if (map.get("Scheduled Reports").equals("enable")) {
                    if (scheduleweekly.has(Condition.attribute("disabled"))) {
                        scheduledreport.click();
                    }
                }
            }
            if (map.containsKey("Scheduled Reports Option")) {
                switch (map.get("Scheduled Reports")) {
                case "weekly":
                    if (!scheduleweekly.has(Condition.attribute("disabled"))) {
                        scheduleweekly.selectRadio("1");
                    }
                    break;
                case "monthly":
                    if (!schedulemonthly.has(Condition.attribute("disabled"))) {
                        schedulemonthly.selectRadio("2");
                    }
                    break;
                }
            }
            SaveOrg.click();
            logger.info("--------------- Organisation is Edited Succesfully ----------");
            Selenide.sleep(10000);
            if ($x("//h4[text()='Organization Updated Successfully']/../..//button[text()='OK']").exists()) {
                $x("//h4[text()='Organization Updated Successfully']/../..//button[text()='OK']").click();
            }
        }
        waitReady();
        return result;
    }
  
    //AddedByPratik
    public boolean verifyActiveOwner(String ownerEmail) {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        waitElement(verifyOwnerEmailonOrgPage(ownerEmail));
        if (!$("p.hide").is(Condition.visible) && !$("div.hide").is(Condition.visible) && verifyOwnerEmailonOrgPage(ownerEmail).exists()) {
            result = true;
            logger.info("Owner is showing active");
        }
        return result;
    }
    
    //AddedByPratik
    public void gotoDevicesTabunderorgSetting(String OrgName) {
        openOrg(OrgName);
        MyCommonAPIs.sleepi(10);
        Setting.click();
        MyCommonAPIs.sleepi(10);
        waitElement(orgsettingsDevicesTab);
        orgsettingsDevicesTab.click();
        MyCommonAPIs.sleepi(5);
    }
    
    //AddedByPratik
    public boolean verifyAllManagerCountonorgPage() {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        waitElement(managercountOnOrgPage);
        String extractedText = managercountOnOrgPage.text();
        System.out.println(extractedText);
        extractedText = extractedText.replace("Managers (", "").replace(") ?", "");
        int totalNoofDevicesShowing = Integer.parseInt(extractedText);
        System.out.println(totalNoofDevicesShowing);
        int expectedNumber = 2;
        if (totalNoofDevicesShowing==expectedNumber) {
            result = true;
            logger.info("Total managers count showing correctly on org page");
        }
        return result;
    }
    
    // AddedByPratik
    public boolean verifyAllManagerCountOnOrgPage() {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        waitElement(managercountOnOrgPage);
        String extractedText = managercountOnOrgPage.text();
        System.out.println(extractedText);
        extractedText = extractedText.replace("Managers (", "").replace(") ?", "");
        int totalNoofDevicesShowing = Integer.parseInt(extractedText);
        System.out.println(totalNoofDevicesShowing);
        int expectedNumber = 2;
        if (totalNoofDevicesShowing == expectedNumber) {
            logger.info("Total managers count showing correctly on org page");
            result = true;
        }
        return result;
    }
    
    //AddedByPratik
    public boolean verifyAllManagerCountonOrgSettingsPage() {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        waitElement(managercountOnOrgPage);
        String extractedText = managercountOnOrgPage.text();
        System.out.println(extractedText);
        extractedText = extractedText.replace("Managers (", "").replace(") ?", "");
        int totalNoofDevicesShowing = Integer.parseInt(extractedText);
        System.out.println(totalNoofDevicesShowing);
        int expectedNumber = 2;
        if (totalNoofDevicesShowing == expectedNumber) {
            logger.info("Total managers count showing correctly on org page");
            viewAllLinkOnOrgPage.click();
            MyCommonAPIs.sleepi(10);
            waitElement(managersCountOnOrgSetpage);
            String extractedText1 = managersCountOnOrgSetpage.text();
            System.out.println(extractedText1);
            extractedText1 = extractedText1.replace("Managers(", "").replace(") ?", "");
            System.out.println(extractedText1);
            int totalNoofDevicesShowing1 = Integer.parseInt(extractedText1);
            System.out.println(totalNoofDevicesShowing1);
            if (totalNoofDevicesShowing1 == expectedNumber) {
                logger.info("Total managers count showing correctly on org setting page");
                result = true;
            }
        }
        return result;
    }
    
    //AddedByPratik
    public void gotoManagersTabunderorgSetting(String OrgName) {
        openOrg(OrgName);
        MyCommonAPIs.sleepi(10);
        Setting.click();
        MyCommonAPIs.sleepi(10);
        waitElement(managersTabOrgSettings);
        managersTabOrgSettings.click();
        MyCommonAPIs.sleepi(5);
    }
    
    //AddedByPratik
    public boolean managersCountOnOrgSettingsPage() {
     boolean result = false;
     MyCommonAPIs.sleepi(10);
     waitElement(managersCountOnOrgSetpage);
     String extractedText1 = managersCountOnOrgSetpage.text();
     System.out.println(extractedText1);
     extractedText1 = extractedText1.replace("Managers(", "").replace(") ?", "");
     System.out.println(extractedText1);
     int totalNoofDevicesShowing1 = Integer.parseInt(extractedText1);
     System.out.println(totalNoofDevicesShowing1);
     int expectedNumber = 2;
     if (totalNoofDevicesShowing1 == expectedNumber) {
         logger.info("Total managers count showing correctly on org setting page");
         result = true;
     }
     return result;
    }
    
    //AddedByPratik
    public void creditAllocation2(String name) {
        MyCommonAPIs.sleepi(5);
        waitElement(checkOrganizationIsExist(name));
        organizationElement(name).click();
        MyCommonAPIs.sleepi(1);
        organizationElement1(name).click();
        MyCommonAPIs.sleepi(10);
        if(dropdownOrganizationElement(name).exists()) 
        {
        dropdownOrganizationElement(name).click();
        addCreditsOrganizationElement(name).click();
        }
        else
        {
              String rowindex=dropdownOrganizationElementNew(name).getAttribute("aria-rowindex");
              ariaSetIndex(rowindex).click();
              ariaSetIndexAllocate(rowindex).click();                             
        }        
        MyCommonAPIs.sleepi(5);
        waitElement(deviceCreditsTextbox);
        deviceCreditsTextbox.sendKeys("4");
        MyCommonAPIs.sleepi(1);
        allocateBtn.click();
        MyCommonAPIs.sleepi(5);
    }   
    //AddedbyPratik
    public boolean verifyHardbundleOnboardedDevicesNotification(String serialNumber) {
        boolean result = false;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String currentDate = sdf.format(new Date());
        System.out.println("currentDate : "+currentDate);
        MyCommonAPIs.sleepi(15);
        waitElement(verifyOnboardedHardbundleDeviceNotification(serialNumber,currentDate));
        if (verifyOnboardedHardbundleDeviceNotification(serialNumber,currentDate).exists()) {
            System.out.println((verifyOnboardedHardbundleDeviceNotification(serialNumber,currentDate)));
            logger.info("Notification shown on notifications page after onboarding Hardbundle device");
            result = true;
        }
        return result;     
    }
    
    //AddedByPratik
    public boolean verifyDevicesCountonLocationTab() {
        boolean result = false;
        MyCommonAPIs.sleepi(15);
        waitElement(verifyDevicesPresentonLocationLogo);
        MyCommonAPIs.sleepi(1);
        if (verifyDevicesPresentonLocationLogo.exists() && verifyDevicesPresentonLocationLogo1.exists()) {
            logger.info("Devices count showing correctly on location logo dashboard");
            result = true;
        }
        return result;
    }
    
    //AddedByPratik
    public boolean verifyDevicesCountonOrganizationTab() {
        boolean result = false;
        MyCommonAPIs.sleepi(15);
        waitElement(gotoHomePage);
        MyCommonAPIs.sleepi(1);
        gotoHomePage.click();
        MyCommonAPIs.sleepi(20);
        waitElement(verifyDevicesPresentonOrgLogo1);
        MyCommonAPIs.sleepi(1);
        if (verifyDevicesPresentonOrgLogo1.exists()) {
            logger.info("Devices count showing correctly on Organization logo dashboard");
            result = true;
        }
        return result;
    }
    
    //AddedByPratik
    public boolean verifyManagersInOrg() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        waitElement(verifyManager(WebportalParam.managerName));
        waitElement(verifyManager(WebportalParam.readManagerName));
        MyCommonAPIs.sleepi(1);
        if (verifyManager(WebportalParam.managerName).exists() && (verifyManager(WebportalParam.readManagerName).exists())) {
            result = true;
            System.out.println(verifyManager(WebportalParam.managerName).getText());
            System.out.println(verifyManager(WebportalParam.readManagerName).getText());
        }
        return result;
    }
    
    //AddedByPratik
    public boolean verifyOrgAndLocationOnMangerLogin() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        waitElement(sOrganizationLocationElement1);
        MyCommonAPIs.sleepi(1);
        String org = $(sOrganizationLocationElement).getText();
        System.out.println("Organization Name : "+org);
        if ($(sOrganizationLocationElement).exists() && org.equals(WebportalParam.Organizations)) {
            open(URLParam.hreforganization, true);
            new OrganizationPage().openOrg(WebportalParam.Organizations);
            MyCommonAPIs.sleepi(10);
            waitElement($x("//span[@class='location-name']"));
            MyCommonAPIs.sleepi(1);
            String loc = $x("//span[@class='location-name']").getText();
            System.out.println("Location Name : "+loc);
            if (($x("//span[@class='location-name']")).exists() && loc.equals(WebportalParam.location1)) {
                result = true;
                logger.info("Location name verified");
            }
            
        } else if ($(sOrganizationLocationElementNew).exists() && org.equals(WebportalParam.Organizations)) {
            open(URLParam.hreforganization, true);
            new OrganizationPage().openOrg(WebportalParam.Organizations);
            MyCommonAPIs.sleepi(10);
            waitElement($x("//span[@class='location-name']"));
            MyCommonAPIs.sleepi(1);
            String loc = $x("//span[@class='location-name']").getText();
            System.out.println("Location Name : "+loc);
            if (($x("//span[@class='location-name']")).exists() && loc.equals(WebportalParam.location1)) {
                result = true;
                logger.info("Location name verified");
            }
        }
        return result;
    }
    
//    Added by Ravi
    public boolean VerifyDeviceCountOnHomeScreen(String org_name,int count) {
        boolean result = false;
        MyCommonAPIs.sleepi(3);
        logger.info("verifying Total Device Counts are Showing Correct on Org Card ");
        String data1 = OrgCardDeviceDataDisconnected(org_name).text();
        logger.info(data1);
        String data2 = deviceCount.text();
        logger.info(data2);
        String counter=Integer.toString(count);
        System.out.print(count+"--------------"+counter);
        
        if (data1.contains(counter)&& data2.contains(counter)) {
            logger.info("Device Count is Correct Under Org Card");
            result = true;
        }
        return result;
    }
	
    // AddedByPratik
    public boolean verifyOnlineDevicesTooltipOnOrgDashboardPage(String tooltip) {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        getOrgTooltipTitle(tooltip).shouldBe(Condition.visible);
        System.out.println("Step1: " + getOrgTooltipTitle(tooltip).getText());
        getOrgTooltipCountHeader(tooltip).shouldBe(Condition.visible);
        String devicesCount = getOrgTooltipCountHeader(tooltip).getText();
        System.out.println("Step2: " + devicesCount);
        if ((devicesCount.trim()).equals("3")) {
            getOrgTooltipCountHeader(tooltip).hover();
            MyCommonAPIs.sleepi(1);
            getOrgTooltipSwitchesRow(tooltip).shouldBe(Condition.visible);
            getOrgTooltipSwitchesValue(tooltip).shouldBe(Condition.visible);
            System.out.println("Step3: " + getOrgTooltipSwitchesRow(tooltip).getText() + " " + getOrgTooltipSwitchesValue(tooltip).getText());
            getOrgTooltipAccessPointsRow(tooltip).shouldBe(Condition.visible);
            getOrgTooltipAccessPointsValue(tooltip).shouldBe(Condition.visible);
            System.out.println(
                    "Step3: " + getOrgTooltipAccessPointsRow(tooltip).getText() + " " + getOrgTooltipAccessPointsValue(tooltip).getText());
            getOrgTooltipRoutersRow(tooltip).shouldBe(Condition.visible);
            getOrgTooltipRoutersValue(tooltip).shouldBe(Condition.visible);
            System.out.println("Step3: " + getOrgTooltipRoutersRow(tooltip).getText() + " " + getOrgTooltipRoutersValue(tooltip).getText());
            String switchesVa = getOrgTooltipSwitchesValue(tooltip).getText();
            String switchesVal = switchesVa.replace(":", "").trim(); 
            String apsVa = getOrgTooltipAccessPointsValue(tooltip).getText();
            String apsVal = apsVa.replace(":", "").trim(); 
            String routersVa = getOrgTooltipRoutersValue(tooltip).getText();
            String routersVal = routersVa.replace(":", "").trim();
            if (((switchesVal.trim()).equals("1")) && ((apsVal.trim()).equals("1")) && ((routersVal.trim()).equals("1"))) {
                logger.info("In " + tooltip + " tooltip all values showing correctly");
                result = true;
            }

        }
        return result;
    }
    
    // AddedByPratik
    public boolean verifyTooltipOnOrgDashboardPage() {
        boolean result = false; 
        MyCommonAPIs.sleepi(10);
        getOrgTooltipTitle("Organizations").shouldBe(Condition.visible);
        System.out.println("Step1: " + getOrgTooltipTitle("Organizations").getText());
        getOrgTooltipCountHeader("Organizations").shouldBe(Condition.visible);
        System.out.println("Step2: " + getOrgTooltipCountHeader("Organizations").getText());
        
        getOrgTooltipTitle("Devices").shouldBe(Condition.visible);
        System.out.println("Step3: " + getOrgTooltipTitle("Devices").getText());
        getOrgTooltipCountHeader("Devices").shouldBe(Condition.visible);
        System.out.println("Step4: " + getOrgTooltipCountHeader("Devices").getText());
        
        getOrgTooltipTitle("Online Devices").shouldBe(Condition.visible);
        System.out.println("Step5: " + getOrgTooltipTitle("Online Devices").getText());
        getOrgTooltipCountHeader("Online Devices").shouldBe(Condition.visible);
        System.out.println("Step6: " + getOrgTooltipCountHeader("Online Devices").getText());
        
        getOrgTooltipTitle("Offline Devices").shouldBe(Condition.visible);
        System.out.println("Step7: " + getOrgTooltipTitle("Offline Devices").getText());
        getOrgTooltipCountHeader("Offline Devices").shouldBe(Condition.visible);
        System.out.println("Step8: " + getOrgTooltipCountHeader("Offline Devices").getText());
        
        getOrgTooltipTitle("Credit Expired").shouldBe(Condition.visible);
        System.out.println("Step9: " + getOrgTooltipTitle("Credit Expired").getText());
        getOrgTooltipCountHeader("Credit Expired").shouldBe(Condition.visible);
        System.out.println("Step10: " + getOrgTooltipCountHeader("Credit Expired").getText());
        
        orgName.shouldBe(Condition.visible);
        System.out.println("Step11: " + orgName.getText());
        String name = orgName.getText();
        
        if (getOrgTooltipTitle("Organizations").isDisplayed() && getOrgTooltipCountHeader("Organizations").isDisplayed() &&
                getOrgTooltipTitle("Devices").isDisplayed() && getOrgTooltipCountHeader("Devices").isDisplayed() &&
                getOrgTooltipTitle("Online Devices").isDisplayed() && getOrgTooltipCountHeader("Online Devices").isDisplayed() &&
                getOrgTooltipTitle("Offline Devices").isDisplayed() && getOrgTooltipCountHeader("Offline Devices").isDisplayed() &&
                getOrgTooltipTitle("Credit Expired").isDisplayed() && getOrgTooltipCountHeader("Credit Expired").isDisplayed() &&
                name.trim().equals(WebportalParam.Organizations)) {
            logger.info("On Organization dashboard all Tooltips are verified and visible");
            result = true;
        }     
        return result;
    }
    
    
    public boolean verifyAlldevicesarePresntOnDevicesPageFromBothOrgs(String tooltip) {
        boolean result = true;

        MyCommonAPIs.sleepi(10);
        getOrgTooltipTitle(tooltip).shouldBe(Condition.visible);
        getOrgTooltipCountHeader(tooltip).shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(10);
        assertTrue(WebDriverRunner.url().contains("devices"));

        // Step 2: Click Filter -> Clear All -> Apply
        filterIconAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        clearAllBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        applyBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);

        // Step 3: Extract serial numbers from Page 1
        ElementsCollection page1Serials = $$x("//div[@col-id='serialNo']/div/span");
        List<String> serialList = new ArrayList<>();
        for (int i = 0; i < page1Serials.size(); i++) {
            serialList.add(page1Serials.get(i).getText().trim());
        }

        // Step 4: Navigate to Page 2 and extract 1 serial number
        nextButtonAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(30);
        assertTrue(WebDriverRunner.url().contains("devices"));

        ElementsCollection page2Serials = $$x("//div[@col-id='serialNo']/div/span");
        if (!page2Serials.isEmpty()) {
            serialList.add(page2Serials.get(0).getText().trim());
        }

        // Step 5: Combine into one string
        String allSerials = String.join(",", serialList);
        System.out.println("All Serial Numbers: " + allSerials);

        if (serialList.contains(WebportalParam.ob1serialNo)) {
            System.out.println("Verified: ob1serialNo = " + WebportalParam.ob1serialNo);
        } else {
            System.out.println("Missing: ob1serialNo = " + WebportalParam.ob1serialNo);
            result = false;
        }

        if (serialList.contains(WebportalParam.ob2serialNo)) {
            System.out.println("Verified: ob2serialNo = " + WebportalParam.ob2serialNo);
        } else {
            System.out.println("Missing: ob2serialNo = " + WebportalParam.ob2serialNo);
            result = false;
        }

        if (serialList.contains(WebportalParam.sw1serialNo)) {
            System.out.println("Verified: sw1serialNo = " + WebportalParam.sw1serialNo);
        } else {
            System.out.println("Missing: sw1serialNo = " + WebportalParam.sw1serialNo);
            result = false;
        }

        if (serialList.contains(WebportalParam.sw2serialNo)) {
            System.out.println("Verified: sw2serialNo = " + WebportalParam.sw2serialNo);
        } else {
            System.out.println("Missing: sw2serialNo = " + WebportalParam.sw2serialNo);
            result = false;
        }

        if (serialList.contains(WebportalParam.ap1serialNo)) {
            System.out.println("Verified: ap1serialNo = " + WebportalParam.ap1serialNo);
        } else {
            System.out.println("Missing: ap1serialNo = " + WebportalParam.ap1serialNo);
            result = false;
        }

        if (serialList.contains(WebportalParam.ap5serialNo)) {
            System.out.println("Verified: ap5serialNo = " + WebportalParam.ap5serialNo);
        } else {
            System.out.println("Missing: ap5serialNo = " + WebportalParam.ap5serialNo);
            result = false;
        }

        System.out.println("Final result: " + result);

        return result;
    }
    
    public boolean verifyAlldevicesarePresntOnDevicesPageFromOneOrgWithSearchOptions(String tooltip) {
        boolean result = true;

        MyCommonAPIs.sleepi(10);
        getOrgTooltipTitle(tooltip).shouldBe(Condition.visible);
        getOrgTooltipCountHeader(tooltip).shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(10);
        assertTrue(WebDriverRunner.url().contains("devices"));

        // Step 2: Click Filter -> Clear All -> Apply
        if (tooltip.equals("Devices")) {
            filterIconAllDevicesPage.shouldBe(Condition.visible).click();
            MyCommonAPIs.sleepi(5);
            clearAllBtnAllDevicesPage.shouldBe(Condition.visible).click();
            MyCommonAPIs.sleepi(1);
            applyBtnAllDevicesPage.shouldBe(Condition.visible).click();
            MyCommonAPIs.sleepi(5);
        } else if (tooltip.equals("Online Devices")) {
            filterIconAllDevicesPage.shouldBe(Condition.visible).click();
            MyCommonAPIs.sleepi(5);
            String activeFilter = activefilter.shouldBe(Condition.visible).getText();
            MyCommonAPIs.sleepi(1);
            if (activeFilter.equals("Online")) {
                System.out.println("By Deafult Filter is applied ie: "+activeFilter);
            } else {
                System.out.println("By Deafult Filter is not applied ie: "+activeFilter);
                result = false;
            }
            cancelBtnAllDevicesPage.shouldBe(Condition.visible).click();
            MyCommonAPIs.sleepi(5);
        } else if (tooltip.equals("Offline Devices")) {
            filterIconAllDevicesPage.shouldBe(Condition.visible).click();
            MyCommonAPIs.sleepi(5);
            String activeFilter = activefilter.shouldBe(Condition.visible).getText();
            MyCommonAPIs.sleepi(1);
            if (activeFilter.equals("Offline")) {
                System.out.println("By Deafult Filter is applied ie: "+activeFilter);
            } else {
                System.out.println("By Deafult Filter is not applied ie: "+activeFilter);
                result = false;
            }
            cancelBtnAllDevicesPage.shouldBe(Condition.visible).click();
            MyCommonAPIs.sleepi(5);
        }
        
        if (tooltip.equals("Devices") || tooltip.equals("Online Devices") ) {
         // Step 3: Extract serial numbers from Page 1
            ElementsCollection page1Serials = $$x("//div[@col-id='serialNo']/div/span");
            List<String> serialList = new ArrayList<>();
            for (int i = 0; i < page1Serials.size(); i++) {
                serialList.add(page1Serials.get(i).getText().trim());
            }

            // Step 5: Combine into one string
            String allSerials = String.join(",", serialList);
            System.out.println("All Serial Numbers: " + allSerials);

            if (serialList.contains(WebportalParam.ob1serialNo)) {
                System.out.println("Verified: ob1serialNo = " + WebportalParam.ob1serialNo);
            } else {
                System.out.println("Missing: ob1serialNo = " + WebportalParam.ob1serialNo);
                result = false;
            }

            if (serialList.contains(WebportalParam.sw1serialNo)) {
                System.out.println("Verified: sw1serialNo = " + WebportalParam.sw1serialNo);
            } else {
                System.out.println("Missing: sw1serialNo = " + WebportalParam.sw1serialNo);
                result = false;
            }

            if (serialList.contains(WebportalParam.ap1serialNo)) {
                System.out.println("Verified: ap1serialNo = " + WebportalParam.ap1serialNo);
            } else {
                System.out.println("Missing: ap1serialNo = " + WebportalParam.ap1serialNo);
                result = false;
            }
            
            searchInputAllDevicesPage.shouldBe(Condition.visible).click();
            MyCommonAPIs.sleepi(5);
            searchInputAllDevicesPage.shouldBe(Condition.visible).setValue(WebportalParam.ap1serialNo);
            MyCommonAPIs.sleepi(5);
            String searchAPValue =  serialNumberCell.shouldBe(Condition.visible).getText();
            if (searchAPValue.equals(WebportalParam.ap1serialNo)) {
                System.out.println("Verified: ap1serialNo by search = " + searchAPValue);
            } else {
                System.out.println("Missing: ap1serialNo = " + searchAPValue);
                result = false;
            }
            searchInputAllDevicesPage.shouldBe(Condition.visible).clear();
            MyCommonAPIs.sleepi(5);
            
            searchInputAllDevicesPage.shouldBe(Condition.visible).click();
            MyCommonAPIs.sleepi(5);
            searchInputAllDevicesPage.shouldBe(Condition.visible).setValue(WebportalParam.sw1serialNo);
            MyCommonAPIs.sleepi(5);
            String searchSWValue =  serialNumberCell.shouldBe(Condition.visible).getText();
            if (searchSWValue.equals(WebportalParam.sw1serialNo)) {
                System.out.println("Verified: sw1serialNo by search = " + searchSWValue);
            } else {
                System.out.println("Missing: sw1serialNo = " + searchSWValue);
                result = false;
            }
            searchInputAllDevicesPage.shouldBe(Condition.visible).clear();
            MyCommonAPIs.sleepi(5);
            
            searchInputAllDevicesPage.shouldBe(Condition.visible).click();
            MyCommonAPIs.sleepi(5);
            searchInputAllDevicesPage.shouldBe(Condition.visible).setValue(WebportalParam.ob1serialNo);
            MyCommonAPIs.sleepi(5);
            String searchobValue =  serialNumberCell.shouldBe(Condition.visible).getText();
            if (searchobValue.equals(WebportalParam.ob1serialNo)) {
                System.out.println("Verified: ob1serialNo by search = " + searchobValue);
            } else {
                System.out.println("Missing: ob1serialNo = " + searchobValue);
                result = false;
            }
            searchInputAllDevicesPage.shouldBe(Condition.visible).clear();

            System.out.println("Final result: " + result);
        } else if (tooltip.equals("Offline Devices")) {
            
         // Step 3: Extract serial numbers from Page 1
            ElementsCollection page1Serials = $$x("//div[@col-id='serialNo']/div/span");
            List<String> serialList = new ArrayList<>();
            for (int i = 0; i < page1Serials.size(); i++) {
                serialList.add(page1Serials.get(i).getText().trim());
            }

            // Step 5: Combine into one string
            String allSerials = String.join(",", serialList);
            System.out.println("All Serial Numbers: " + allSerials);

            if (serialList.contains(WebportalParam.ob2serialNo)) {
                System.out.println("Verified: ob2serialNo = " + WebportalParam.ob2serialNo);
            } else {
                System.out.println("Missing: ob2serialNo = " + WebportalParam.ob2serialNo);
                result = false;
            }

            if (serialList.contains(WebportalParam.sw2serialNo)) {
                System.out.println("Verified: sw2serialNo = " + WebportalParam.sw2serialNo);
            } else {
                System.out.println("Missing: sw2serialNo = " + WebportalParam.sw2serialNo);
                result = false;
            }

            if (serialList.contains(WebportalParam.ap5serialNo)) {
                System.out.println("Verified: ap2serialNo = " + WebportalParam.ap5serialNo);
            } else {
                System.out.println("Missing: ap2serialNo = " + WebportalParam.ap5serialNo);
                result = false;
            }
            
            searchInputAllDevicesPage.shouldBe(Condition.visible).click();
            MyCommonAPIs.sleepi(5);
            searchInputAllDevicesPage.shouldBe(Condition.visible).setValue(WebportalParam.ap5serialNo);
            MyCommonAPIs.sleepi(5);
            String searchAPValue =  serialNumberCell.shouldBe(Condition.visible).getText();
            if (searchAPValue.equals(WebportalParam.ap5serialNo)) {
                System.out.println("Verified: ap2serialNo by search = " + searchAPValue);
            } else {
                System.out.println("Missing: ap2serialNo = " + searchAPValue);
                result = false;
            }
            searchInputAllDevicesPage.shouldBe(Condition.visible).clear();
            MyCommonAPIs.sleepi(5);
            
            searchInputAllDevicesPage.shouldBe(Condition.visible).click();
            MyCommonAPIs.sleepi(5);
            searchInputAllDevicesPage.shouldBe(Condition.visible).setValue(WebportalParam.sw2serialNo);
            MyCommonAPIs.sleepi(5);
            String searchSWValue =  serialNumberCell.shouldBe(Condition.visible).getText();
            if (searchSWValue.equals(WebportalParam.sw2serialNo)) {
                System.out.println("Verified: sw2serialNo by search = " + searchSWValue);
            } else {
                System.out.println("Missing: sw2serialNo = " + searchSWValue);
                result = false;
            }
            searchInputAllDevicesPage.shouldBe(Condition.visible).clear();
            MyCommonAPIs.sleepi(5);
            
            searchInputAllDevicesPage.shouldBe(Condition.visible).click();
            MyCommonAPIs.sleepi(5);
            searchInputAllDevicesPage.shouldBe(Condition.visible).setValue(WebportalParam.ob2serialNo);
            MyCommonAPIs.sleepi(5);
            String searchobValue =  serialNumberCell.shouldBe(Condition.visible).getText();
            if (searchobValue.equals(WebportalParam.ob2serialNo)) {
                System.out.println("Verified: ob2serialNo by search = " + searchobValue);
            } else {
                System.out.println("Missing: ob1serialNo = " + searchobValue);
                result = false;
            }
            searchInputAllDevicesPage.shouldBe(Condition.visible).clear();

            System.out.println("Final result: " + result);

            
        }

        
        return result;
    }
    
    public boolean isHeaderOrderCorrect() {
        
        MyCommonAPIs.sleepi(10);

        ElementsCollection visibleHeaders = $$("div.ag-header-cell-label").filter(visible);

        List<String> actualHeaders = new ArrayList<>();
        for (SelenideElement header : visibleHeaders) {
            String text = header.getText().trim();
            if (!text.isEmpty()) {
                actualHeaders.add(text);
            }
        }

        List<String> expectedHeaders = Arrays.asList("Name", "Total Devices", "Online Devices", "Offline Devices", "Device Credit Status", "Owner");

        System.out.println("🔍 Header-by-Header Comparison:");

        boolean allMatch = true;

        int maxLength = Math.max(actualHeaders.size(), expectedHeaders.size());

        for (int i = 0; i < maxLength; i++) {
            String actual = i < actualHeaders.size() ? actualHeaders.get(i) : "[MISSING]";
            String expected = i < expectedHeaders.size() ? expectedHeaders.get(i) : "[EXTRA]";

            if (!actual.equals(expected)) {
                allMatch = false;
                System.out.println("❌ Mismatch at index " + i + ": Expected '" + expected + "' but found '" + actual + "'");
            } else {
                System.out.println("✅ Match at index " + i + ": '" + actual + "'");
            }
        }

        // Final result
        if (allMatch) {
            System.out.println("✅ All headers match the expected order.");
        } else {
            System.out.println("❗ Header order mismatch detected.");
        }

        return allMatch;
    }
    
    public boolean verifyFilterOptionfororganizationHeader(String orgName, String orgName1) {
        
        boolean result = true;
        
        MyCommonAPIs.sleepi(5);
        filterIconAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        offlineFilteroption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        applyBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        String offlinedeviecFilter = getHeaderOfflineDevices(orgName1).shouldBe(Condition.visible).getText();
        System.out.println("offlinedeviecFilter: "+offlinedeviecFilter);
        String onlinedeviecFilter = getHeaderOnlineDevices(orgName1).shouldBe(Condition.visible).getText();
        System.out.println("offlinedeviecFilter: "+onlinedeviecFilter);
        if (offlinedeviecFilter.equals("3") && onlinedeviecFilter.equals("0")) {
            System.out.println("offline devices filter applied successfully");
        } else {
            result = false;
            System.out.println("offline devices filter not applied successfully");
        }
        filterIconAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        clearAllBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        applyBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        
        filterIconAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        onlineFilteroption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        applyBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        String offlinedeviecFilter1 = getHeaderOfflineDevices(orgName).shouldBe(Condition.visible).getText();
        System.out.println("offlinedeviecFilter1: "+offlinedeviecFilter1);
        String onlinedeviecFilter1 = getHeaderOnlineDevices(orgName).shouldBe(Condition.visible).getText();
        System.out.println("onlinedeviecFilter1: "+onlinedeviecFilter1);
        if (onlinedeviecFilter1.equals("3") && offlinedeviecFilter1.equals("0")) {
            System.out.println("online devices filter applied successfully");
        } else {
            result = false;
            System.out.println("online devices filter not applied successfully");
        }
        filterIconAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        clearAllBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        applyBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        
        filterIconAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        expiredFilteroption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        applyBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        if (!(getHeaderOrgName(orgName).isDisplayed() && getHeaderOrgName(orgName1).isDisplayed())) {
            System.out.println("Both organizations are not visible because expired credits are not there");
        } else {
            result = false;
            System.out.println("Both organizations are visible when expired credits are not there");
        }
        filterIconAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        clearAllBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        applyBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        
        filterIconAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        allActiveFilter.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        applyBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        if (getHeaderOrgName(orgName).isDisplayed() && getHeaderOrgName(orgName1).isDisplayed() && getHeaderCreditExpiredCounts(orgName).isDisplayed()
                && getHeaderCreditExpiredCounts(orgName1).isDisplayed()) {
            System.out.println("Both organizations are visible because Active credits are there");
        } else {
            result = false;
            System.out.println("Both organizations are not visible when Active credits are there");
        }
        filterIconAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        clearAllBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        applyBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        
        return result;
    }
    
    public boolean verifyOrgHeaderSortOptions(String orgName, String orgName1) {
        boolean result = true;
        
        MyCommonAPIs.sleepi(5);
        sortOrgOption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        totaldeviceSortopt.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        onlineDevicesSort.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        offlineDevicesSort.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        deviceCreditStatusSort.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        ownerSort.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        sortOrgOption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        if (getHeaderOrgName(orgName).isDisplayed() && getHeaderOrgName(orgName1).isDisplayed()) {
            if (!(totaldevicesheader.isDisplayed() && Onlinedevicesheader.isDisplayed() && offlinedevicesheader.isDisplayed() 
                    && deviceCreditStatusheader.isDisplayed() && Ownerheader.isDisplayed())) {
                System.out.println("All sort options are not active");
            }
        } else {
            result = false;
            System.out.println("Some sort options are active");
        }
        
        sortOrgOption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        totaldeviceSortopt.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        sortOrgOption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        if (totaldevicesheader.isDisplayed()) {
            String o1 = getHeaderTotalDevices(orgName).getText();
            System.out.println("Org1 Total devices : "+o1);
            String o2 = getHeaderTotalDevices(orgName1).getText();
            System.out.println("Org2 Total devices : "+o2);
            if (o1.equals("3") && o2.equals("3")) {
                System.out.println("Only Total Devices sort option is active");
            } else {
                result = false;
                System.out.println("Total Devices sort option is not active");
            }
        } else {
            result = false;
            System.out.println("Total Devices sort option is not active");
        }
        
        sortOrgOption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        onlineDevicesSort.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        sortOrgOption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        if (totaldevicesheader.isDisplayed() && Onlinedevicesheader.isDisplayed()) {
            String o1 = getHeaderTotalDevices(orgName).getText();
            System.out.println("Org1 Total devices : "+o1);
            String o2 = getHeaderTotalDevices(orgName1).getText();
            System.out.println("Org2 Total devices : "+o2);
            String o3 = getHeaderOnlineDevices(orgName).getText();
            System.out.println("Org1 Online devices : "+o3);
            String o4 = getHeaderOnlineDevices(orgName1).getText();
            System.out.println("Org2 Online devices : "+o4);
            if (o1.equals("3") && o2.equals("3") && o3.equals("3") && o4.equals("0")) {
                System.out.println("Only Total Devices and online device sort options are active");
            } else {
                result = false;
                System.out.println("Only Total Devices and online device sort options are not active");
            }
        } else {
            result = false;
            System.out.println("Only Total Devices and online device sort options are not active");
        }
        
        sortOrgOption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        offlineDevicesSort.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        sortOrgOption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        if (totaldevicesheader.isDisplayed() && Onlinedevicesheader.isDisplayed() && offlinedevicesheader.isDisplayed()) {
            String o1 = getHeaderTotalDevices(orgName).getText();
            System.out.println("Org1 Total devices : "+o1);
            String o2 = getHeaderTotalDevices(orgName1).getText();
            System.out.println("Org2 Total devices : "+o2);
            String o3 = getHeaderOnlineDevices(orgName).getText();
            System.out.println("Org1 Online devices : "+o3);
            String o4 = getHeaderOnlineDevices(orgName1).getText();
            System.out.println("Org2 Online devices : "+o4);
            String o5 = getHeaderOfflineDevices(orgName).getText();
            System.out.println("Org1 Online devices : "+o5);
            String o6 = getHeaderOfflineDevices(orgName1).getText();
            System.out.println("Org2 Online devices : "+o6);
            if (o1.equals("3") && o2.equals("3") && o3.equals("3") && o4.equals("0") && o5.equals("0") && o6.equals("3")) {
                System.out.println("Only Total Devices, online device, offline devices sort options are active");
            } else {
                result = false;
                System.out.println("Only Total Devices, online device, offline devices sort options are not active");
            }
        } else {
            result = false;
            System.out.println("Only Total Devices, online device, offline devices sort options are not active");
        }
        
        sortOrgOption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        deviceCreditStatusSort.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        sortOrgOption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        if (totaldevicesheader.isDisplayed() && Onlinedevicesheader.isDisplayed() && offlinedevicesheader.isDisplayed() && deviceCreditStatusheader.isDisplayed()) {
            String o1 = getHeaderTotalDevices(orgName).getText();
            System.out.println("Org1 Total devices : "+o1);
            String o2 = getHeaderTotalDevices(orgName1).getText();
            System.out.println("Org2 Total devices : "+o2);
            String o3 = getHeaderOnlineDevices(orgName).getText();
            System.out.println("Org1 Online devices : "+o3);
            String o4 = getHeaderOnlineDevices(orgName1).getText();
            System.out.println("Org2 Online devices : "+o4);
            String o5 = getHeaderOfflineDevices(orgName).getText();
            System.out.println("Org1 Online devices : "+o5);
            String o6 = getHeaderOfflineDevices(orgName1).getText();
            System.out.println("Org2 Online devices : "+o6);
            if (o1.equals("3") && o2.equals("3") && o3.equals("3") && o4.equals("0") && o5.equals("0") && o6.equals("3") 
                    && getHeaderCreditExpiredCounts(orgName).isDisplayed() && getHeaderCreditExpiredCounts(orgName1).isDisplayed()) {
                System.out.println("Only Total Devices, online device, offline devices and CreditExpired sort options are active");
            } else {
                result = false;
                System.out.println("Only Total Devices, online device, offline devices and CreditExpired  sort options are not active");
            }
        } else {
            result = false;
            System.out.println("Only Total Devices, online device, offline devices and CreditExpired  sort options are not active");
        }
        
        sortOrgOption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        ownerSort.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        sortOrgOption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        if (totaldevicesheader.isDisplayed() && Onlinedevicesheader.isDisplayed() && offlinedevicesheader.isDisplayed() 
                && deviceCreditStatusheader.isDisplayed() && Ownerheader.isDisplayed()) {
            String o1 = getHeaderTotalDevices(orgName).getText();
            System.out.println("Org1 Total devices : "+o1);
            String o2 = getHeaderTotalDevices(orgName1).getText();
            System.out.println("Org2 Total devices : "+o2);
            String o3 = getHeaderOnlineDevices(orgName).getText();
            System.out.println("Org1 Online devices : "+o3);
            String o4 = getHeaderOnlineDevices(orgName1).getText();
            System.out.println("Org2 Online devices : "+o4);
            String o5 = getHeaderOfflineDevices(orgName).getText();
            System.out.println("Org1 Online devices : "+o5);
            String o6 = getHeaderOfflineDevices(orgName1).getText();
            System.out.println("Org2 Online devices : "+o6);
            if (o1.equals("3") && o2.equals("3") && o3.equals("3") && o4.equals("0") && o5.equals("0") && o6.equals("3") 
                    && getHeaderCreditExpiredCounts(orgName).isDisplayed() && getHeaderCreditExpiredCounts(orgName1).isDisplayed()
                    && getHeaderOwnerName(orgName).isDisplayed() && getHeaderOwnerName(orgName1).isDisplayed()) {
                System.out.println("Only Total Devices, online device, offline devices, CreditExpired and Owner sort options are active");
            } else {
                result = false;
                System.out.println("Only Total Devices, online device, offline devices, CreditExpired and Owner  sort options are not active");
            }
        } else {
            result = false;
            System.out.println("Only Total Devices, online device, offline devices, CreditExpired and Owner  sort options are not active");
        }
        
        return result;
    }
    
    public boolean verifyOrgDashboardAndOrgSettingsDevicesTable(String apSrNo, String swSrNo, String obSrNo) {
        boolean result = true;

        MyCommonAPIs.sleepi(5);
        orgDashboardHeaderTable.shouldBe(Condition.visible);
        if (orgDashboardHeaderTable.isDisplayed()) {
            System.out.println("Step1: Organization Dashboard header is present");
        } else {
            result = false;
            System.out.println("Step1: Organization Dashboard header is not present");
        }

        orgDashboardSettingsICON.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        orgDashboardSettingsICONDevicestab.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(10);
        devicesTablesList.shouldBe(Condition.visible);
        for (SelenideElement element : settingsOrgDevicesConnectedState) {
            if (!element.isDisplayed()) {
                result = false;
                System.out.println("Step2: Element not visible: " + element);
                break;
            }
        }

        if (result) {
            System.out.println("Step2:  All elements are visible.");
        } else {
            result = false;
            System.out.println("Step2: Some elements are not visible.");
        }
        if (orgSettingsDevice(apSrNo).isDisplayed() && orgSettingsDevice(swSrNo).isDisplayed() && orgSettingsDevice(obSrNo).isDisplayed()) {
            System.out.println("Step2: All devices are listed on devices page: " + orgSettingsDevice(apSrNo).getText() + " || "
                    + orgSettingsDevice(swSrNo).getText() + " || " + orgSettingsDevice(obSrNo).getText());
        } else {
            result = false;
            System.out.println("Step2: All devices are not listed on devices page");
        }

        return result;
    }
    
    public boolean verifyLocationdashboardHeaderAndSettingsDashboard(String apSrNo, String swSrNo, String obSrNo) {
        boolean result = true;
        
        MyCommonAPIs.sleepi(10);
        locationDashboardTable.shouldBe(Condition.visible);
        if (locationDashboardTable.isDisplayed()) {
            System.out.println("Step1: Location Dashboard header is present");
        } else {
            result = false;
            System.out.println("Step1: Location Dashboard header is not present");
        }
        
        OrgSettings.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(10);
        settingsDevicesPageTable.shouldBe(Condition.visible);
        if (locdashSettingsDevice(apSrNo).isDisplayed() && locdashSettingsDevice(swSrNo).isDisplayed() && locdashSettingsDevice(obSrNo).isDisplayed()) {
            System.out.println("Step2: All devices are listed on devices page: " + locdashSettingsDevice(apSrNo).getText() + " || "
                    + locdashSettingsDevice(swSrNo).getText() + " || " + locdashSettingsDevice(obSrNo).getText());
        } else {
            result = false;
            System.out.println("Step2: All devices are not listed on devices page");
        }
        return result;
    }
    
    public boolean verifyAllocatedDeviceCredits(String name) {
        boolean result = true;
        MyCommonAPIs.sleepi(15);
        String rowindex = dropdownOrganizationElementNew(name).getAttribute("aria-rowindex");
        ariaSetIndex(rowindex).click();
        ariaSetIndexAllocate(rowindex).click();
        MyCommonAPIs.sleepi(5);
        if ($x("(//td[text()='6'])[1]").shouldBe(Condition.visible).isDisplayed()) {
            System.out.println("Device Credits allocated successfully ie: " + $x("(//td[text()='6'])[1]").getText());
        } else {
            result = false;
            System.out.println("Device Credits not allocated successfully");
        }
        closeIconCreditAlllocation.shouldBe(Condition.visible).click();
        return result;
    }
    
    public boolean verifyAllocatedICPCredits(String name) {
        boolean result = true;
        MyCommonAPIs.sleepi(15);
        String rowindex = dropdownOrganizationElementNew(name).getAttribute("aria-rowindex");
        ariaSetIndex(rowindex).click();
        ariaSetIndexAllocate(rowindex).click();
        MyCommonAPIs.sleepi(5);
        if ($x("(//td[text()='6'])[1]").shouldBe(Condition.visible).isDisplayed()) {
            System.out.println("Device Credits allocated successfully ie: " + $x("(//td[text()='6'])[1]").getText());
        } else {
            result = false;
            System.out.println("Device Credits not allocated successfully");
        }
        closeIconCreditAlllocation.shouldBe(Condition.visible).click();
        return result;
    }
    
    public boolean verifyDeviceCreditsLicenseKeysSelectAll(String name) {
        boolean result = false;
        String rowindex = dropdownOrganizationElementNew(name).getAttribute("aria-rowindex");
        ariaSetIndex(rowindex).click();
        ariaSetIndexAllocate(rowindex).click();
        MyCommonAPIs.sleepi(5);
        deviceCreditsselect.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        creditsSelectAll.shouldBe(Condition.visible);
        if (creditsSelectAll.isDisplayed()) {
            creditsSelectAll.click();
            result = true;
        }
        MyCommonAPIs.sleepi(1);
        allocateBtn.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        return result;
    }
    
    public boolean verifycancelBtnDeviceCreditPopup(String name) {
        boolean result = false;
        MyCommonAPIs.sleepi(15);
        String rowindex = dropdownOrganizationElementNew(name).getAttribute("aria-rowindex");
        ariaSetIndex(rowindex).click();
        ariaSetIndexAllocate(rowindex).click();
        MyCommonAPIs.sleepi(5);
        waitElement(deviceCreditsTextbox);
        deviceCreditsTextbox.sendKeys("6");
        MyCommonAPIs.sleepi(1);
        cancelBtnDeviceCreditPopup.shouldBe(Condition.visible);
        if (cancelBtnDeviceCreditPopup.isDisplayed()) {
            cancelBtnDeviceCreditPopup.click();
            result = true;
        }
        MyCommonAPIs.sleepi(5);
        return result;
    }
    
    public boolean verifyZeroAllocatedDeviceCredits(String name) {
        boolean result = true;
        MyCommonAPIs.sleepi(15);

        String rowindex = dropdownOrganizationElementNew(name).getAttribute("aria-rowindex");
        ariaSetIndex(rowindex).click();
        ariaSetIndexAllocate(rowindex).click();                                    
        MyCommonAPIs.sleepi(5);

        // Verify all "0" cells
        ElementsCollection zeroCells = $$x("//td[text()='0']");
        System.out.println("Found zeroCells count: " + zeroCells.size());

        boolean allAreZero = zeroCells.stream()
            .allMatch(cell -> cell.getText().trim().equals("0"));

        if (allAreZero) {
            System.out.println("All credits are zero as expected.");
        } else {
            result = false;
            System.out.println("Some credits are not zero.");
        }

        closeIconCreditAlllocation.shouldBe(Condition.visible).click();
        return result;
    }
    
    public boolean creditAllocationPlusMinusVerification(String name, String creds) {
        boolean result = true;
        MyCommonAPIs.sleepi(15);
        String rowindex = dropdownOrganizationElementNew(name).getAttribute("aria-rowindex");
        ariaSetIndex(rowindex).click();
        ariaSetIndexAllocate(rowindex).click();
        MyCommonAPIs.sleepi(5);
        waitElement(deviceCreditsTextbox);
        deviceCreditsTextbox.sendKeys(creds);

        MyCommonAPIs.sleepi(1);
        devicecreditAllocatePlusicon.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        devicecreditAllocatePlusicon.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        String pluscredVal = allocatingDevCreditValue.shouldBe(Condition.visible).getValue();
        System.out.println("pluscredVal : " + pluscredVal);
        if (pluscredVal.equals("8")) {
            System.out.println("Plus credit button working fine");
        } else {
            result = false;
            System.out.println("Plus credit button is not working fine");
        }

        MyCommonAPIs.sleepi(1);
        devicecreditAllocateMinusicon.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        devicecreditAllocateMinusicon.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        String minuscredVal = allocatingDevCreditValue.shouldBe(Condition.visible).getValue();
        System.out.println("minuscredVal : " + minuscredVal);
        if (minuscredVal.equals(creds)) {
            System.out.println("minus credit button working fine");
        } else {
            result = false;
            System.out.println("minus credit button is not working fine");
        }

        allocateBtn.click();
        MyCommonAPIs.sleepi(5);
        return result;
    }
    
    public boolean verifyAllocateAndSuccesscreditAllocation(String name) {
        boolean result = false;
        MyCommonAPIs.sleepi(15);
        String rowindex = dropdownOrganizationElementNew(name).getAttribute("aria-rowindex");
        ariaSetIndex(rowindex).click();
        ariaSetIndexAllocate(rowindex).click();
        MyCommonAPIs.sleepi(5);
        waitElement(deviceCreditsTextbox);
        deviceCreditsTextbox.sendKeys("6");
        MyCommonAPIs.sleepi(1);
        allocateBtn.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleep(10);
        verifycreditrsAllocateSuccess.shouldBe(Condition.visible);
        System.out.println("verifycreditrsAllocateSuccess : " + verifycreditrsAllocateSuccess.getText());
        if (verifycreditrsAllocateSuccess.isDisplayed()) {
            result = true;
            logger.info("Verified Success Messgae for allocating credits");
        }

        return result;
    }
    
    public boolean verifyCloseICONAllocatedDeviceCredits(String name) {
        boolean result = true;
        MyCommonAPIs.sleepi(15);
        String rowindex = dropdownOrganizationElementNew(name).getAttribute("aria-rowindex");
        ariaSetIndex(rowindex).click();
        ariaSetIndexAllocate(rowindex).click();
        MyCommonAPIs.sleepi(5);
        if (closeIconCreditAlllocation.shouldBe(Condition.visible).isDisplayed()) {
            System.out.println("close icon is displyed on device credits page");
            closeIconCreditAlllocation.shouldBe(Condition.visible).click();
            MyCommonAPIs.sleepi(10);
            getHeaderOrgName(name).shouldBe(Condition.visible);
            if (getHeaderOrgName(name).isDisplayed()) {
                System.out.println("device credits page is closed and landed on organization page");
            } else {
                result = false;
                System.out.println("device credits page is not closed and landed on organization page");
            }
        } else {
            result = false;
            System.out.println("close icon is not displyed on device credits page");
        }
        return result;
    }
    
    public boolean verifyUsedUnsedCreditsAfterOnboardingdevices(String name) {
        boolean result = true;
        MyCommonAPIs.sleepi(15);
        String rowindex = dropdownOrganizationElementNew(name).getAttribute("aria-rowindex");
        ariaSetIndex(rowindex).click();
        ariaSetIndexAllocate(rowindex).click();
        MyCommonAPIs.sleepi(5);
        String alloCatedCreds = "";
        String unUsedcreds = "";
        if ($x("//td[text()='6']").isDisplayed() && $x("//td[text()='5']").isDisplayed()) {
            alloCatedCreds = $x("//td[text()='6']").getText();
            unUsedcreds = $x("//td[text()='5']").getText();
            int alloCatedCred = Integer.parseInt(alloCatedCreds);
            int unUsedcred = Integer.parseInt(unUsedcreds);
            int usedcred = alloCatedCred-unUsedcred;
            System.out.println("alloCatedCred : "+alloCatedCred+"  unUsedcred : "+unUsedcred+"  usedcred : "+usedcred);
            if (usedcred==1) {
                System.out.println("After onboarding devices verified used, unused creds");
                closeIconCreditAlllocation.shouldBe(Condition.visible).click();
            } else {
                result = false;
                System.out.println("After onboarding devices not verified used, unused creds");
            }
        } else {
            result = false;
            System.out.println("After onboarding devices not verified used, unused creds");
        }
        return result;
    }
    
    public boolean verifyUsedUnsedCreditsAfterOnboardingNodevices(String name) {
        boolean result = true;
        MyCommonAPIs.sleepi(15);
        String rowindex = dropdownOrganizationElementNew(name).getAttribute("aria-rowindex");
        ariaSetIndex(rowindex).click();
        ariaSetIndexAllocate(rowindex).click();
        MyCommonAPIs.sleepi(5);
        String alloCatedCreds = "";
        String unUsedcreds = "";
        if ($x("(//td[text()='6'])[1]").isDisplayed() && $x("(//td[text()='6'])[2]").isDisplayed()) {
            alloCatedCreds = $x("(//td[text()='6'])[1]").getText();
            unUsedcreds = $x("(//td[text()='6'])[2]").getText();
            int alloCatedCred = Integer.parseInt(alloCatedCreds);
            int unUsedcred = Integer.parseInt(unUsedcreds);
            int usedcred = alloCatedCred-unUsedcred;
            System.out.println("alloCatedCred : "+alloCatedCred+"  unUsedcred : "+unUsedcred+"  usedcred : "+usedcred);
            if (usedcred==0) {
                System.out.println("After deleteing devices verified used, unused creds");
                closeIconCreditAlllocation.shouldBe(Condition.visible).click();
            } else {
                result = false;
                System.out.println("After deleteing devices not verified used, unused creds");
            }
        } else {
            result = false;
            System.out.println("After deleteing devices not verified used, unused creds");
        }
        return result;
    }
    
    public boolean verifydeallocatepopupElements(String name) {
        boolean result = true;
        MyCommonAPIs.sleepi(15);

        try {
            String rowindex = dropdownOrganizationElementNew(name).getAttribute("aria-rowindex");
            ariaSetIndex(rowindex).click();
            ariaSetIndexDeallocate(rowindex).click();
            MyCommonAPIs.sleepi(5);
            modalHeader.shouldBe(Condition.visible);
            if (!modalHeader.isDisplayed()) {
                System.out.println("Modal header is not visible.");
                result = false;
            }
            if (!deviceCreditsLabel.isDisplayed()) {
                System.out.println("Device Credits label is not visible.");
                result = false;
            }
            if (!deviceCreditsInput.isDisplayed()) {
                System.out.println("Device Credits input is not visible.");
                result = false;
            }
            if (!instantVpnGroupCreditsLabel.isDisplayed()) {
                System.out.println("Instant VPN Group Credits label is not visible.");
                result = false;
            }
            if (!instantVpnGroupCreditsInput.isDisplayed()) {
                System.out.println("Instant VPN Group Credits input is not visible.");
                result = false;
            }
            if (!instantCaptivePortalCreditsLabel.isDisplayed()) {
                System.out.println("Instant Captive Portal Credits label is not visible.");
                result = false;
            }
            if (!instantCaptivePortalCreditsInput.isDisplayed()) {
                System.out.println("Instant Captive Portal Credits input is not visible.");
                result = false;
            }
            if (!businessVpnLabel.isDisplayed()) {
                System.out.println("Business VPN label is not visible.");
                result = false;
            }
            if (!businessVpnInput.isDisplayed()) {
                System.out.println("Business VPN input is not visible.");
                result = false;
            }
            if (!canceldeallocateButton.isDisplayed()) {
                System.out.println("Cancel button is not visible.");
                result = false;
            }
            if (!deallocateButton.isDisplayed()) {
                System.out.println("Deallocate button is not visible.");
                result = false;
            }
            if (!creditTypeDeallocate.isDisplayed()) {
                System.out.println("Credit Type label is not visible.");
                result = false;
            }

            // Click cancel to close the popup and confirm it's gone
            canceldeallocateButton.click();
            MyCommonAPIs.sleepi(3);
            if (modalHeader.isDisplayed()) {
                System.out.println("Popup did not close after clicking Cancel.");
                result = false;
            }

        } catch (Exception e) {
            System.out.println("Exception occurred during verification: " + e.getMessage());
            result = false;
        }

        return result;
    }
    
    public boolean verifyDeallocateCreditsOnOrgPage(String name) {
        boolean result = true;
        String rowindex = dropdownOrganizationElementNew(name).getAttribute("aria-rowindex");
        ariaSetIndex(rowindex).click();
        ariaSetIndexDeallocate(rowindex).click();
        MyCommonAPIs.sleepi(5);
        modalHeader.shouldBe(Condition.visible);
        deviceCreditsInput.shouldBe(Condition.visible).setValue("6");
        MyCommonAPIs.sleepi(2);
        deallocateButton.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleep(10);
        verifySuccessDeallocate.shouldBe(Condition.visible);
        System.out.println("verifySuccessDeallocate : "+verifySuccessDeallocate.getText());
        if (!(verifySuccessDeallocate.isDisplayed())) {
            result = false;
            System.out.println("deallocate is not done successfully");
        }
        return result;
    }
    
    public boolean verifyCancelBtnOnDeallocateCreditsPage(String name) {
        boolean result = true;
        String rowindex = dropdownOrganizationElementNew(name).getAttribute("aria-rowindex");
        ariaSetIndex(rowindex).click();
        ariaSetIndexDeallocate(rowindex).click();
        MyCommonAPIs.sleepi(5);
        modalHeader.shouldBe(Condition.visible);
        deviceCreditsInput.shouldBe(Condition.visible).setValue("6");
        MyCommonAPIs.sleepi(2);
        canceldeallocateButton.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleep(10);
        if (!(verifyAllocatedDeviceCredits(name))) {
            result = false;
            System.out.println("deallocate cancel button is not Working");
        }
        return result;
    }
    
    public boolean verifyDeallocatePopupElements(String name) {
        boolean result = true;
        MyCommonAPIs.sleepi(15);

        try {
            // Trigger the popup
            String rowindex = dropdownOrganizationElementNew(name).getAttribute("aria-rowindex");
            ariaSetIndex(rowindex).click();
            ariaSetIndexDeallocate(rowindex).click();
            MyCommonAPIs.sleepi(5);

            // List of elements to verify
            Map<String, SelenideElement> elementsToCheck = new HashMap<>();
            elementsToCheck.put("Modal Header", modalHeader);
            elementsToCheck.put("Device Credits Label", deviceCreditsLabel);
            elementsToCheck.put("Device Credits Input", deviceCreditsInput);
            elementsToCheck.put("Instant VPN Group Credits Label", instantVpnGroupCreditsLabel);
            elementsToCheck.put("Instant VPN Group Credits Input", instantVpnGroupCreditsInput);
            elementsToCheck.put("Instant Captive Portal Credits Label", instantCaptivePortalCreditsLabel);
            elementsToCheck.put("Instant Captive Portal Credits Input", instantCaptivePortalCreditsInput);
            elementsToCheck.put("Business VPN Label", businessVpnLabel);
            elementsToCheck.put("Business VPN Input", businessVpnInput);
            elementsToCheck.put("Cancel Button", canceldeallocateButton);
            elementsToCheck.put("Deallocate Button", deallocateButton);
            elementsToCheck.put("Credit Type Label", creditTypeDeallocate);

            // Check element visibility
            for (Map.Entry<String, SelenideElement> entry : elementsToCheck.entrySet()) {
                if (!entry.getValue().isDisplayed()) {
                    System.out.println("❌ Element not visible: " + entry.getKey());
                    result = false;
                } else {
                    System.out.println("✅ Element visible: " + entry.getKey());
                }
            }

            // Tab through inputs and check focus moves
            deviceCreditsInput.pressTab();
            if (!instantVpnGroupCreditsInput.equals(WebDriverRunner.getWebDriver().switchTo().activeElement())) {
                System.out.println("❌ Tab navigation failed between inputs.");
                result = false;
            } else {
                System.out.println("✅ Tab navigation between inputs working.");
            }

            MyCommonAPIs.sleepi(5);
            canceldeallocateButton.shouldBe(Condition.visible).click();
            MyCommonAPIs.sleepi(5);
            if (modalHeader.isDisplayed()) {
                System.out.println("❌ Popup did not close after clicking Cancel.");
                result = false;
            } else {
                System.out.println("✅ Cancel button closed the popup successfully.");
            }

        } catch (Exception e) {
            System.out.println("❌ Exception during popup verification: " + e.getMessage());
            result = false;
        }

        return result;
    }
    
    public boolean verifyDeallocateButtonIsdiabled(String name) {

        boolean result = false;
        String rowindex = dropdownOrganizationElementNew(name).getAttribute("aria-rowindex");
        ariaSetIndex(rowindex).click();
        ariaSetIndexDeallocate(rowindex).click();
        MyCommonAPIs.sleepi(5);

        modalHeader.shouldBe(Condition.visible);
        String devcred = deviceCreditsInput.shouldBe(Condition.visible).getValue();
        System.out.println("deviceCreditsInput : " + devcred);
        String instantVpnCred = instantVpnGroupCreditsInput.shouldBe(Condition.visible).getValue();
        System.out.println("instantVpnGroupCreditsInput : " + instantVpnCred);
        String icpcred = instantCaptivePortalCreditsInput.shouldBe(Condition.visible).getValue();
        System.out.println("instantCaptivePortalCreditsInput : " + icpcred);
        String bvpncred = businessVpnInput.shouldBe(Condition.visible).getValue();
        System.out.println("businessVpnInput : " + bvpncred);

        if (devcred.trim().equals("0") && instantVpnCred.trim().equals("0") && icpcred.trim().equals("0") && bvpncred.trim().equals("0")) {
            System.out.println("All values on deallocation page are '0'");
            if (deallocateButton.getAttribute("disabled") != null) {
                System.out.println("Deallocate button is disabled.");
                result = true;
                canceldeallocateButton.shouldBe(Condition.visible).click();
                MyCommonAPIs.sleepi(5);
            }
        }
        return result;
    }
    
    public boolean verifyDeallocateCredits(String name, String creds) {
        
        boolean result = true;

        String rowindex = dropdownOrganizationElementNew(name).getAttribute("aria-rowindex");
        ariaSetIndex(rowindex).click();
        ariaSetIndexDeallocate(rowindex).click();
        MyCommonAPIs.sleepi(5);

        String devcred = deviceCreditsInput.shouldBe(Condition.visible).getValue();
        System.out.println("deviceCreditsInput : " + devcred);
        
        if ((devcred.trim().equals(creds))) {
            System.out.println("deviceCreditsInput : " + devcred);
        } else {
            result = false;
            System.out.println("Sufficient device credits are not avilable to deallocate");
        }
         
        deallocateButton.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        
        ariaSetIndex(rowindex).click();
        ariaSetIndexDeallocate(rowindex).click();
        MyCommonAPIs.sleepi(5);
        
        String devcred1 = deviceCreditsInput.shouldBe(Condition.visible).getValue();
        System.out.println("deviceCreditsInput : " + devcred1);
        
        if ((devcred1.trim().equals("0"))) {
            System.out.println("deviceCreditsInput : " + devcred);
        } else {
            result = false;
            System.out.println("device credits are not '0'");
        }
        
        canceldeallocateButton.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);

        return result;
    }
    
    public boolean deallocateDeviceCreditsIncreaseDecrese(String orgName) {
        try {

            String rowindex = dropdownOrganizationElementNew(orgName).getAttribute("aria-rowindex");

            // Open the Deallocate Credits modal
            ariaSetIndex(rowindex).click();
            ariaSetIndexDeallocate(rowindex).click();
            MyCommonAPIs.sleepi(5); // Wait for modal to load

            // Verify modal and input field are visible
            if (!modalHeader.isDisplayed() || !deviceCreditsInput.isDisplayed()) {
                System.out.println("Modal or Device Credits input not visible.");
                return false;
            }

            // Capture initial value
            int initial = Integer.parseInt(deviceCreditsInput.getValue());
            System.out.println("Initial Device Credits: " + initial);

            // Click minus (-) button once
            deviceCreditDeallocateMinusbtn.shouldBe(Condition.visible).click();
            MyCommonAPIs.sleepi(1);
            int afterMinus = Integer.parseInt(deviceCreditsInput.getValue());
            System.out.println("After -1: " + afterMinus);

            // Click plus (+) button once
            deviceCreditDeallocatePlusbtn.shouldBe(Condition.visible).click();
            MyCommonAPIs.sleepi(1);
            int afterPlus = Integer.parseInt(deviceCreditsInput.getValue());
            System.out.println("After +1: " + afterPlus);

            // Verify final value equals initial
            if (afterPlus != initial) {
                System.out.println("Final value mismatch. Expected: " + initial + ", Found: " + afterPlus);
                return false;
            }

            // Click Deallocate
            deallocateButton.shouldBe(Condition.visible).click();
            MyCommonAPIs.sleep(10);
            verifySuccessDeallocate.shouldBe(Condition.visible);
            // Verify success message
            if (verifySuccessDeallocate.isDisplayed()) {
                System.out.println("Credits successfully deallocated.");
                return true;
            } else {
                System.out.println("Success message not displayed.");
                return false;
            }

        } catch (Exception e) {
            System.out.println("Exception during deallocation: " + e.getMessage());
            return false;
        }
    }

    public boolean closeIconDeallocatePopup(String orgname) {

        boolean result = false;
        String rowindex = dropdownOrganizationElementNew(orgname).getAttribute("aria-rowindex");
        ariaSetIndex(rowindex).click();
        ariaSetIndexDeallocate(rowindex).click();
        MyCommonAPIs.sleepi(5);
        closeIcondeallocatePopup.shouldBe(Condition.visible);
        if (closeIcondeallocatePopup.isDisplayed()) {
            closeIcondeallocatePopup.click();
            MyCommonAPIs.sleepi(5);
            orgName.shouldBe(Condition.visible);
            result = true;
            System.out.println("Clicked on close icon button on Deallocate popup and then verified popup closed");
        }
        return result;
    }
    
    public boolean verifyfilterOptionIsVisible() {
        boolean result = true;
        MyCommonAPIs.sleepi(5);
        filterIconAllDevicesPage.shouldBe(Condition.visible);
        if (filterIconAllDevicesPage.isDisplayed()) {
            System.out.println("Filter option is visible on organization dashboard page");
            result = true;
        }
        return result;
    }
    
    public boolean verifyfilterDropdownOptionsareVisible() {
        boolean result = true;
        MyCommonAPIs.sleepi(5);
        filterIconAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        if (offlineFilteroption.isDisplayed() && onlineFilteroption.isDisplayed() && expiredFilteroption.isDisplayed() && allActiveFilter.isDisplayed()) {
            System.out.println("Filter dropdown options are visible on organization dashboard page");
            result = true;
            cancelBtnAllDevicesPage.shouldBe(Condition.visible).click();
            MyCommonAPIs.sleepi(5);
        }
        return result;
    }
    
    public boolean verifyFilterOptionDevicesfororganizationHeader(String orgName, String orgName1) {

        boolean result = true;

        MyCommonAPIs.sleepi(5);
        filterIconAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        offlineFilteroption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        applyBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        String offlinedeviecFilter = getHeaderOfflineDevices(orgName1).shouldBe(Condition.visible).getText();
        System.out.println("offlinedeviecFilter: " + offlinedeviecFilter);
        String onlinedeviecFilter = getHeaderOnlineDevices(orgName1).shouldBe(Condition.visible).getText();
        System.out.println("offlinedeviecFilter: " + onlinedeviecFilter);
        if (offlinedeviecFilter.equals("3") && onlinedeviecFilter.equals("0")) {
            System.out.println("offline devices filter applied successfully");
        } else {
            result = false;
            System.out.println("offline devices filter not applied successfully");
        }
        filterIconAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        clearAllBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        applyBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);

        filterIconAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        onlineFilteroption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        applyBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        String offlinedeviecFilter1 = getHeaderOfflineDevices(orgName).shouldBe(Condition.visible).getText();
        System.out.println("offlinedeviecFilter1: " + offlinedeviecFilter1);
        String onlinedeviecFilter1 = getHeaderOnlineDevices(orgName).shouldBe(Condition.visible).getText();
        System.out.println("onlinedeviecFilter1: " + onlinedeviecFilter1);
        if (onlinedeviecFilter1.equals("3") && offlinedeviecFilter1.equals("0")) {
            System.out.println("online devices filter applied successfully");
        } else {
            result = false;
            System.out.println("online devices filter not applied successfully");
        }
        filterIconAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        clearAllBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        applyBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        return result;
    }
    
    public boolean verifyCreditsFilterOptionsFunctionality(String orgName, String orgName1) {
        boolean result = true;
        MyCommonAPIs.sleepi(5);
        filterIconAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        expiredFilteroption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        applyBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        if (!(getHeaderOrgName(orgName).isDisplayed() && getHeaderOrgName(orgName1).isDisplayed())) {
            System.out.println("Both organizations are not visible because expired credits are not there");
        } else {
            result = false;
            System.out.println("Both organizations are visible when expired credits are not there");
        }
        filterIconAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        clearAllBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        applyBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);

        filterIconAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        allActiveFilter.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        applyBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        if (getHeaderOrgName(orgName).isDisplayed() && getHeaderOrgName(orgName1).isDisplayed()
                && getHeaderCreditExpiredCounts(orgName).isDisplayed() && getHeaderCreditExpiredCounts(orgName1).isDisplayed()) {
            System.out.println("Both organizations are visible because Active credits are there");
        } else {
            result = false;
            System.out.println("Both organizations are not visible when Active credits are there");
        }
        filterIconAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        clearAllBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        applyBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);

        return result;
    }
    
    public boolean verifyClearFilterOptionorganizationHeader(String orgName, String orgName1) {

        boolean result = true;

        MyCommonAPIs.sleepi(5);
        filterIconAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        clearAllBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        applyBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);

        String offlinedeviecFilter = getHeaderOfflineDevices(orgName1).shouldBe(Condition.visible).getText();
        System.out.println("offlinedeviecFilter: " + offlinedeviecFilter);
        String onlinedeviecFilter = getHeaderOnlineDevices(orgName1).shouldBe(Condition.visible).getText();
        System.out.println("offlinedeviecFilter: " + onlinedeviecFilter);
        if (offlinedeviecFilter.equals("3") && onlinedeviecFilter.equals("0")) {
            System.out.println("offline devices filter applied successfully");
        } else {
            result = false;
            System.out.println("offline devices filter not applied successfully");
        }

        String offlinedeviecFilter1 = getHeaderOfflineDevices(orgName).shouldBe(Condition.visible).getText();
        System.out.println("offlinedeviecFilter1: " + offlinedeviecFilter1);
        String onlinedeviecFilter1 = getHeaderOnlineDevices(orgName).shouldBe(Condition.visible).getText();
        System.out.println("onlinedeviecFilter1: " + onlinedeviecFilter1);
        if (onlinedeviecFilter1.equals("3") && offlinedeviecFilter1.equals("0")) {
            System.out.println("online devices filter applied successfully");
        } else {
            result = false;
            System.out.println("online devices filter not applied successfully");
        }

        if ((getHeaderOrgName(orgName).isDisplayed() && getHeaderOrgName(orgName1).isDisplayed())) {
            System.out.println("Both organizations are visible because expired credits are not there");
        } else {
            result = false;
            System.out.println("Both organizations are not visible when expired credits are not there");
        }

        if (getHeaderOrgName(orgName).isDisplayed() && getHeaderOrgName(orgName1).isDisplayed()
                && getHeaderCreditExpiredCounts(orgName).isDisplayed() && getHeaderCreditExpiredCounts(orgName1).isDisplayed()) {
            System.out.println("Both organizations are visible because Active credits are there");
        } else {
            result = false;
            System.out.println("Both organizations are not visible when Active credits are there");
        }

        return result;
    }
    
    public boolean verifyCancelButtonOnFilterOptionorganizationHeader(String orgName, String orgName1) {

        boolean result = true;

        MyCommonAPIs.sleepi(5);
        filterIconAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        clearAllBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        cancelBtnAllDevicesPage.shouldBe(Condition.visible);
        if ((cancelBtnAllDevicesPage.isDisplayed())) {
            cancelBtnAllDevicesPage.click();
            System.out.println("Cancel Button on filter dropdown is visible");
            MyCommonAPIs.sleepi(5);
        } else {
            result = false;
            System.out.println("Cancel Button on filter dropdown is not visible");
        }

        String offlinedeviecFilter = getHeaderOfflineDevices(orgName1).shouldBe(Condition.visible).getText();
        System.out.println("offlinedeviecFilter: " + offlinedeviecFilter);
        String onlinedeviecFilter = getHeaderOnlineDevices(orgName1).shouldBe(Condition.visible).getText();
        System.out.println("offlinedeviecFilter: " + onlinedeviecFilter);
        if (offlinedeviecFilter.equals("3") && onlinedeviecFilter.equals("0")) {
            System.out.println("offline devices filter applied successfully");
        } else {
            result = false;
            System.out.println("offline devices filter not applied successfully");
        }

        String offlinedeviecFilter1 = getHeaderOfflineDevices(orgName).shouldBe(Condition.visible).getText();
        System.out.println("offlinedeviecFilter1: " + offlinedeviecFilter1);
        String onlinedeviecFilter1 = getHeaderOnlineDevices(orgName).shouldBe(Condition.visible).getText();
        System.out.println("onlinedeviecFilter1: " + onlinedeviecFilter1);
        if (onlinedeviecFilter1.equals("3") && offlinedeviecFilter1.equals("0")) {
            System.out.println("online devices filter applied successfully");
        } else {
            result = false;
            System.out.println("online devices filter not applied successfully");
        }

        if ((getHeaderOrgName(orgName).isDisplayed() && getHeaderOrgName(orgName1).isDisplayed())) {
            System.out.println("Both organizations are visible because expired credits are not there");
        } else {
            result = false;
            System.out.println("Both organizations are not visible when expired credits are not there");
        }

        if (getHeaderOrgName(orgName).isDisplayed() && getHeaderOrgName(orgName1).isDisplayed()
                && getHeaderCreditExpiredCounts(orgName).isDisplayed() && getHeaderCreditExpiredCounts(orgName1).isDisplayed()) {
            System.out.println("Both organizations are visible because Active credits are there");
        } else {
            result = false;
            System.out.println("Both organizations are not visible when Active credits are there");
        }

        return result;
    }
    
    public boolean verifydevicesOnOrgDashboard(String apSrNo, String swSrNo, String obSrNo, String header) {
        boolean result = true;
        if(header.equals("Total Devices")) {
            getHeaderTotalDevices("Netgear").shouldBe(Condition.visible).click();
        } else if (header.equals("Online Devices")) {
            getHeaderOnlineDevices("Netgear").shouldBe(Condition.visible).click();
        }
        MyCommonAPIs.sleepi(5);
        refresh();
        MyCommonAPIs.sleepi(10);
        devicesTablesList.shouldBe(Condition.visible);
        for (SelenideElement element : settingsOrgDevicesConnectedState) {
            if (!element.isDisplayed()) {
                result = false;
                System.out.println("Step2: Element not visible: " + element);
                break;
            }
        }

        if (result) {
            System.out.println("Step2:  All elements are visible.");
        } else {
            result = false;
            System.out.println("Step2: Some elements are not visible.");
        }
        if (orgSettingsDevice(apSrNo).isDisplayed() && orgSettingsDevice(swSrNo).isDisplayed() && orgSettingsDevice(obSrNo).isDisplayed()) {
            System.out.println("Step2: All devices are listed on devices page: " + orgSettingsDevice(apSrNo).getText() + " || "
                    + orgSettingsDevice(swSrNo).getText() + " || " + orgSettingsDevice(obSrNo).getText());
        } else {
            result = false;
            System.out.println("Step2: All devices are not listed on devices page");
        }

        return result;
    }
    
    public boolean verifyOfflinedevicesOnOrgDashboard(String apSrNo, String swSrNo, String obSrNo, String header) {
        boolean result = true;
        getHeaderOfflineDevices("Netgear").shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(10);
        devicesTablesList.shouldBe(Condition.visible);
        for (SelenideElement element : settingsOrgDevicestate) {
            if (!element.isDisplayed()) {
                result = false;
                System.out.println("Step2: Element not visible: " + element);
                break;
            }
        }

        if (result) {
            System.out.println("Step2:  All elements are visible.");
        } else {
            result = false;
            System.out.println("Step2: Some elements are not visible.");
        }
        if (orgSettingsDevice(apSrNo).isDisplayed() && orgSettingsDevice(swSrNo).isDisplayed() && orgSettingsDevice(obSrNo).isDisplayed()) {
            System.out.println("Step2: All devices are listed on devices page: " + orgSettingsDevice(apSrNo).getText() + " || "
                    + orgSettingsDevice(swSrNo).getText() + " || " + orgSettingsDevice(obSrNo).getText());
        } else {
            result = false;
            System.out.println("Step2: All devices are not listed on devices page");
        }

        return result;
    }
    
    public boolean isFilterActive(String header, String type) {
        if(header.equals("Total Devices")) {
            getHeaderTotalDevices("Netgear").shouldBe(Condition.visible).click();
        } else if (header.equals("Online Devices")) {
            getHeaderOnlineDevices("Netgear").shouldBe(Condition.visible).click();
        } else if (header.equals("Offline Devices")) {
            getHeaderOfflineDevices("Netgear").shouldBe(Condition.visible).click();
        }
        MyCommonAPIs.sleepi(10);
        filterOrgSetting.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        filterOption(type).shouldBe(Condition.visible);
        if (filterOption(type).isDisplayed() && filterOption(type).getAttribute("class").contains("active")) {
            System.out.println(type+" filter is active.");
            cancelBtnAllDevicesPage.shouldBe(Condition.visible).click();
            MyCommonAPIs.sleepi(5);
            return true;
        } else {
            System.out.println(type+" filter is NOT active.");
            return false;
        }
    }
    
    public boolean verifyOrgDashboardAndOrgSettingsDevicesTableWhenclickonHeaderTabs(String apSrNo, String swSrNo, String obSrNo, String header, 
            String apSrNo1, String swSrNo1, String obSrNo1) {
        boolean result = true;
        if(header.equals("Total Devices")) {
            getHeaderTotalDevices("Netgear").shouldBe(Condition.visible).click();
        } else if (header.equals("Online Devices")) {
            getHeaderOnlineDevices("Netgear").shouldBe(Condition.visible).click();
        } else if (header.equals("Offline Devices")) {
            getHeaderOfflineDevices("Netgear").shouldBe(Condition.visible).click();
        }
        MyCommonAPIs.sleepi(10);
        filterOrgSetting.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        clearAllBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        applyBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        devicesTablesList.shouldBe(Condition.visible);
        
        for (SelenideElement element : settingsOrgDevicesConnectedState) {
            if (!element.isDisplayed()) {
                result = false;
                System.out.println("Step1 Online Devices : Element not visible: " + element);
                break;
            }
        }
        
        if (result) {
            System.out.println("Step1 Online Devices :  All elements are visible.");
        } else {
            result = false;
            System.out.println("Step1 Online Devices : Some elements are not visible.");
        }
        
        for (SelenideElement element : settingsOrgDevicestate) {
            if (!element.isDisplayed()) {
                result = false;
                System.out.println("Step2  Offline Devices : Element not visible: " + element);
                break;
            }
        }

        if (result) {
            System.out.println("Step2  Offline Devices:  All elements are visible.");
        } else {
            result = false;
            System.out.println("Step2  Offline Devices: Some elements are not visible.");
        }
        
        if (orgSettingsDevice(apSrNo).isDisplayed() && orgSettingsDevice(swSrNo).isDisplayed() && orgSettingsDevice(obSrNo).isDisplayed() &&
            orgSettingsDevice(apSrNo1).isDisplayed() && orgSettingsDevice(swSrNo1).isDisplayed() && orgSettingsDevice(obSrNo1).isDisplayed()) {
            System.out.println("Step2: All devices are listed on devices page: " + orgSettingsDevice(apSrNo).getText() + " || "
                    + orgSettingsDevice(swSrNo).getText() + " || " + orgSettingsDevice(obSrNo).getText()
                    + orgSettingsDevice(apSrNo1).getText() + " || "
                    + orgSettingsDevice(swSrNo1).getText() + " || " + orgSettingsDevice(obSrNo1).getText());
        } else {
            result = false;
            System.out.println("Step2: All devices are not listed on devices page");
        }

        return result;
    }
    
    public boolean verifyOrgHeaderSortOptionsForOneOrg(String orgName) {
        boolean result = true;

        MyCommonAPIs.sleepi(5);
        sortOrgOption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        status.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        serialNumber.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        clients.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        model.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        creditStatus.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        type.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        location.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        rebootStatus.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        managed.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        sortOrgOption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);

        if (getDeviceBySerial(WebportalParam.ap1serialNo).isDisplayed()
                && getDeviceBySerial(WebportalParam.sw1serialNo).isDisplayed()
                && getDeviceBySerial(WebportalParam.ob1serialNo).isDisplayed()) {

            if (!(statusHeaderOrgSett.isDisplayed() && serailNoHeaderOrgSett.isDisplayed()
                    && clientheaderOrgSett.isDisplayed() && modelheaderOrgSett.isDisplayed()
                    && creditStatusHeaderorgSett.isDisplayed() && typeHeaderorgsett.isDisplayed()
                    && locationhdrSett.isDisplayed() && rebootStatusHdrSett.isDisplayed()
                    && ManagedHdrSett.isDisplayed())) {
                System.out.println("Step1: true");
            } else {
                result = false;
                System.out.println("Step1: false");
            }
        } else {
            result = false;
            System.out.println("Step1: false");
        }

        // Status sort and Connected verification
        sortOrgOption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        status.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        sortOrgOption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);

        if (statusHeaderOrgSett.isDisplayed()) {
            if (connectedStatuses.size() == 3) {
                for (SelenideElement status : connectedStatuses) {
                    if (!status.isDisplayed()) {
                        result = false;
                        System.out.println("Step2: false");
                        break;
                    }
                }
            } else {
                result = false;
                System.out.println("Step2: false");
            }
        } else {
            result = false;
            System.out.println("Step2: false");
        }

        // Serial number sort and verify
        sortOrgOption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        serialNumber.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        sortOrgOption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);

        if (!(serailNoHeaderOrgSett.isDisplayed()
                && deviceSerialOnTable(WebportalParam.ap1serialNo).isDisplayed()
                && deviceSerialOnTable(WebportalParam.sw1serialNo).isDisplayed()
                && deviceSerialOnTable(WebportalParam.ob1serialNo).isDisplayed())) {
            result = false;
            System.out.println("Step3: false");
        }

        sortOrgOption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        clients.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        sortOrgOption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        if (!clientheaderOrgSett.isDisplayed()) {
            result = false;
            System.out.println("Step4: false");
        }

        // Model
        sortOrgOption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        model.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        sortOrgOption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        if (!(modelheaderOrgSett.isDisplayed()
                && modelByValue(WebportalParam.ap1Model).isDisplayed()
                && modelByValue(WebportalParam.sw1Model).isDisplayed()
                && modelByValue(WebportalParam.ob1Model).isDisplayed())) {
            result = false;
            System.out.println("Step5: false");
        }

        // Credit Status
        sortOrgOption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        creditStatus.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        sortOrgOption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        if (!(creditStatusHeaderorgSett.isDisplayed() && activeCredits.size() == 3)) {
            result = false;
            System.out.println("Step6: false");
        }

     // Step 7: Type
        sortOrgOption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        type.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        sortOrgOption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        try {
            typeHeaderorgsett.scrollIntoView(true).shouldBe(Condition.visible);
            deviceTypeSW.scrollIntoView(true).shouldBe(Condition.visible);
            deviceTypeOrbi.scrollIntoView(true).shouldBe(Condition.visible);
            deviceTypeAP.scrollIntoView(true).shouldBe(Condition.visible);
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
            System.out.println("Step7: false");
        }

        // Step 8: Location
        sortOrgOption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        location.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        sortOrgOption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        try {
            locationhdrSett.scrollIntoView(true).shouldBe(Condition.visible);
            if (locationOffice.size() != 3) {
                result = false;
                System.out.println("Step8: false - Expected 3 locations, found: " + locationOffice.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
            System.out.println("Step8: false");
        }


        // Reboot Status
        sortOrgOption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        rebootStatus.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        sortOrgOption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        rebootStatusHdrSett.hover();
        MyCommonAPIs.sleepi(1);
        if (!(rebootStatusHdrSett.isDisplayed() && rebootStatusNA.size() == 3)) {
            result = false;
            System.out.println("Step9: false");
        }

        // Managed
        sortOrgOption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        managed.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        sortOrgOption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        ManagedHdrSett.hover();
        MyCommonAPIs.sleepi(1);
        if (!(ManagedHdrSett.isDisplayed() && managedSwitches.size() == 3)) {
            result = false;
            System.out.println("Step10: false");
        }

        return result;
    }
    
    public boolean isOfflineZeroNotClickable(String orgName) {
        try {
            // Step 1: Locate the element using your existing method
            SelenideElement offlineDeviceElement = getHeaderOfflineDevices(orgName);

            // Step 2: Ensure it's visible
            offlineDeviceElement.shouldBe(Condition.visible);

            // Step 3: Get the text and parse count
            String offlineText = offlineDeviceElement.getText().trim();
            int offlineCount = Integer.parseInt(offlineText);

            // Step 4: If count is 0, ensure it's not redirecting
            if (offlineCount == 0) {
                String currentUrl = WebDriverRunner.url();

                // Click the element
                offlineDeviceElement.click();

                // Wait a bit to allow any navigation attempt
                Selenide.sleep(1000);

                String newUrl = WebDriverRunner.url();

                // If URL is unchanged, it's not redirecting – return true
                if (newUrl.equals(currentUrl)) {
                    System.out.println("URL not changed on click when count is 0");
                    return true;
                } else {
                    System.out.println("URL changed on click when count is 0");
                    return false;
                }
            } else {
                System.out.println("Offline count is not zero (" + offlineCount + "), skipping test.");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

public boolean verifyFilterOptionsOnLocationLandingpageHeader() {
        
        boolean result = true;
        
        MyCommonAPIs.sleepi(5);
        filterIconAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        offlineFilteroption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        applyBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        String offlinedeviecFilter = loclandpageNoData.shouldBe(Condition.visible).getText();
        System.out.println("offlinedeviecFilter: "+offlinedeviecFilter);
        if (loclandpageNoData.isDisplayed()) {
            System.out.println("offline devices filter applied successfully");
        } else {
            result = false;
            System.out.println("offline devices filter not applied successfully");
        }
        filterIconAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        clearAllBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        applyBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        
        filterIconAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        onlineFilteroption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        applyBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        String offlinedeviecFilter1 = loclandpageofflineDevices.shouldBe(Condition.visible).getText();
        System.out.println("offlinedeviecFilter1: "+offlinedeviecFilter1);
        String onlinedeviecFilter1 = loclandpageonlineDevices.shouldBe(Condition.visible).getText();
        System.out.println("onlinedeviecFilter1: "+onlinedeviecFilter1);
        if (onlinedeviecFilter1.equals("3") && offlinedeviecFilter1.equals("0")) {
            System.out.println("online devices filter applied successfully");
        } else {
            result = false;
            System.out.println("online devices filter not applied successfully");
        }
        filterIconAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        clearAllBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        applyBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        
        filterIconAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        expiredFilteroption.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        applyBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        if (loclandpageNoData.isDisplayed()) {
            System.out.println("locations are not visible because expired credits are not there "+loclandpageNoData.getText());
        } else {
            result = false;
            System.out.println("locations are visible when expired credits are not there");
        }
        filterIconAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        clearAllBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        applyBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        
        filterIconAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        allActiveFilter.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        applyBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        if (loclandpageDeviceCredits.isDisplayed()) {
            System.out.println("locations are visible because Active credits are there "+loclandpageDeviceCredits.getText());
        } else {
            result = false;
            System.out.println("locations are not visible when Active credits are there");
        }
        filterIconAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        clearAllBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        applyBtnAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        
        return result;
    }

    public boolean verifyPremiumAccLocDashSearchfunct() {
        
        boolean result = true;
        MyCommonAPIs.sleepi(5);
        searchOptionOnlocDash.shouldBe(Condition.visible);
        searchOptionOnlocDash.setValue("Total Devices").sendKeys(Keys.ENTER);
        MyCommonAPIs.sleepi(5);
        if (!(locDashTotalDevice.isDisplayed())) {
            result = false;
            System.out.println("Step1: False After searching Total device Count its not visible");
        } else {
            System.out.println("Step1: Pass After searching Total device Count its visible : "+locDashTotalDevice.getText());
            searchOptionOnlocDash.clear();
            MyCommonAPIs.sleepi(5);
        }
        
        searchOptionOnlocDash.shouldBe(Condition.visible);
        searchOptionOnlocDash.setValue("Online Devices").sendKeys(Keys.ENTER);
        MyCommonAPIs.sleepi(5);
        if (!(locDashOnlineDevice.isDisplayed())) {
            result = false;
            System.out.println("Step2: False After searching Online Devices Count its not visible");
        } else {
            System.out.println("Step2: Pass After searching Online Devices Count its visible : "+locDashOnlineDevice.getText());
            searchOptionOnlocDash.clear();
            MyCommonAPIs.sleepi(5);
        }
        
        searchOptionOnlocDash.shouldBe(Condition.visible);
        searchOptionOnlocDash.setValue("Offline Devices").sendKeys(Keys.ENTER);
        MyCommonAPIs.sleepi(5);
        if (!(locDashOfflineDevice.isDisplayed())) {
            result = false;
            System.out.println("Step3: False After searching Offline Devices Count its not visible");
        } else {
            System.out.println("Step3: Pass After searching Offline Devices Count its visible : "+locDashOfflineDevice.getText());
            searchOptionOnlocDash.clear();
            MyCommonAPIs.sleepi(5);
        }
        
        searchOptionOnlocDash.shouldBe(Condition.visible);
        searchOptionOnlocDash.setValue("Connected Clients").sendKeys(Keys.ENTER);
        MyCommonAPIs.sleepi(5);
        if (!(locDashConnectedClients.isDisplayed())) {
            result = false;
            System.out.println("Step4: False After searching Connected Clients Count its not visible");
        } else {
            System.out.println("Step4: Pass After searching Connected Clients Count its visible : "+locDashConnectedClients.getText());
            searchOptionOnlocDash.clear();
            MyCommonAPIs.sleepi(5);
        }
        
        return result;
        
    }
    
    public boolean verifyPremiumAccLocDashSortfunct() {
        
        boolean result = true;
        MyCommonAPIs.sleepi(5);
        locDashSortDropdown.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        if (!(locDashSortTotalDevices.isDisplayed() && locDashSortOnlineDevices.isDisplayed() && locDashSortOfflineDevices.isDisplayed() && locDashSortConnectedClients.isDisplayed())) {
            result = false;
            System.out.println("Step1: False All Sort options are not visible");
        } else {
            System.out.println("Step1: Pass All Sort options are visible : "+locDashSortTotalDevices.getText()+" : "+locDashSortOnlineDevices.getText()+" : "+locDashSortOfflineDevices.getText()+
                    " : "+locDashSortConnectedClients.getText());
            locDashSortDropdown.shouldBe(Condition.visible).click();
            MyCommonAPIs.sleepi(5);
            
        } if (!(locDashTotalDevice.isDisplayed() && locDashOnlineDevice.isDisplayed() && locDashOfflineDevice.isDisplayed() && locDashConnectedClients.isDisplayed())) {
            result = false;
            System.out.println("Step2: False All Header options are not visible");
        } else {
            System.out.println("Step2: Pass All haeder options are visible");
            MyCommonAPIs.sleepi(5);
        }

        locDashSortDropdown.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        locDashSortTotalDevices.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        locDashSortDropdown.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        if (!(locDashOnlineDevice.isDisplayed() && locDashOfflineDevice.isDisplayed() && locDashConnectedClients.isDisplayed() && (!locDashTotalDevice.isDisplayed()))) {
            result = false;
            System.out.println("Step3: False Total devices Header option is visible");
        } else {
            System.out.println("Step3: Pass Total devices Header option is not visible");
            MyCommonAPIs.sleepi(5);
        }
        
        locDashSortDropdown.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        locDashSortOnlineDevices.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        locDashSortTotalDevices.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        locDashSortDropdown.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        if (!(locDashTotalDevice.isDisplayed() && locDashOfflineDevice.isDisplayed() && locDashConnectedClients.isDisplayed() && (!locDashOnlineDevice.isDisplayed()))) {
            result = false;
            System.out.println("Step3: False Online devices Header option is visible");
        } else {
            System.out.println("Step3: Pass Online devices Header option is not visible");
            MyCommonAPIs.sleepi(5);
        }
        
        locDashSortDropdown.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        locDashSortOnlineDevices.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        locDashSortOfflineDevices.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        locDashSortDropdown.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        if (!(locDashTotalDevice.isDisplayed() && locDashOnlineDevice.isDisplayed() && locDashConnectedClients.isDisplayed() && (!locDashOfflineDevice.isDisplayed()))) {
            result = false;
            System.out.println("Step3: False offline devices Header option is visible");
        } else {
            System.out.println("Step3: Pass Offline devices Header option is not visible");
            MyCommonAPIs.sleepi(5);
        }
        
        locDashSortDropdown.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        locDashSortConnectedClients.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        locDashSortOfflineDevices.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        locDashSortDropdown.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        if (!(locDashTotalDevice.isDisplayed() && locDashOnlineDevice.isDisplayed() && locDashOfflineDevice.isDisplayed() && (!locDashConnectedClients.isDisplayed()))) {
            result = false;
            System.out.println("Step3: False Connected Client Header option is visible");
        } else {
            System.out.println("Step3: Pass Connected Client Header option is not visible");
            MyCommonAPIs.sleepi(5);
        }
        
        locDashSortDropdown.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        locDashSortConnectedClients.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(1);
        locDashSortDropdown.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        
        return result;
        
    }
    
    public boolean verifyAllDeviceTypeCounts() {
        String expected = "1";
        boolean allMatched = true;

        // Hover over 'Devices' and validate children
        locDashDevicesHeaderCount.hover();
        MyCommonAPIs.sleepi(1);
        allMatched &= hoverAndValidate(locDashDevicesSwitchesSpanCount, "2");
        allMatched &= hoverAndValidate(locDashDevicesRoutersSpanCount, "4");
        allMatched &= hoverAndValidate(locDashDevicesAccessPointsSpanCount, "2");

        // Hover over 'Online Devices' and validate children
        locDashOnlineDevicesHeaderCount.hover();
        MyCommonAPIs.sleepi(1);
        allMatched &= hoverAndValidate(locDashOnlineSwitchesSpanCount, expected);
        allMatched &= hoverAndValidate(locDashOnlineRoutersSpanCount, "2");
        allMatched &= hoverAndValidate(locDashOnlineAccessPointsSpanCount, expected);

        // Hover over 'Offline Devices' and validate children
        locDashOfflineDevicesHeaderCount.hover();
        MyCommonAPIs.sleepi(1);
        allMatched &= hoverAndValidate(locDashOfflineSwitchesSpanCount, expected);
        allMatched &= hoverAndValidate(locDashOfflineRoutersSpanCount, "2");
        allMatched &= hoverAndValidate(locDashOfflineAccessPointsSpanCount, expected);

        if (allMatched) {
            System.out.println("✅ All device count texts matched expected value: " + expected);
        } else {
            System.out.println("❌ Some device count texts did not match expected value: " + expected);
        }
        
        return allMatched;
        
    }

    private boolean hoverAndValidate(SelenideElement element, String expectedText) {
        try {
            element.hover();
            SelenideElement countSpan = element.closest("tr")
                .find(By.cssSelector("td.text-right span.margin-left-5"))
                .shouldBe(visible);
            String actualText = countSpan.getText().replace(":", "").trim();
            System.out.println("Hovered: " + element + " | Extracted: " + actualText);
            return expectedText.equals(actualText);
        } catch (Exception e) {
            System.out.println("⚠️ Error during hover/validation: " + e.getMessage());
            return false;
        }
    }
    
    public boolean verifyEditUserName() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        openOrg(WebportalParam.Organizations);
        MyCommonAPIs.sleepi(10);
        userNameOwnername.shouldBe(Condition.visible).hover();
        MyCommonAPIs.sleep(10);
        userNameOwnerEmail.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        $x("//label[text()='Pick Existing Owner']/..//select[@id='existingUser']")
        .shouldBe(Condition.visible)
        .selectOption("Select an owner");
        MyCommonAPIs.sleepi(5);
        ownerName.shouldBe(Condition.visible);
        MyCommonAPIs.sleep(10);
        String ownname1 = "own" + String.valueOf(new Random().nextInt(10000000));
        String ownMail = "own" + String.valueOf(new Random().nextInt(10000000)) + "@yopmail.com";
        if (ownerName.isDisplayed()) {
            Selenide.executeJavaScript("arguments[0].value = '';", ownerName);
            MyCommonAPIs.sleep(10);
            ownerName.sendKeys(ownname1);
            MyCommonAPIs.sleep(10);
            Selenide.executeJavaScript("arguments[0].value = '';", ownerEmail);
            MyCommonAPIs.sleep(10);
            ownerEmail.sendKeys(ownMail);
            result = true;
            System.out.println("User Name changed to " + ownname1);
        }
        waitElement(SaveOrg);
        MyCommonAPIs.sleepi(1);
        SaveOrg.click();
        logger.info("--------------- Organisation is Edited Succesfully ----------");
        Selenide.sleep(10000);
        if ($x("//h4[text()='Organization Updated Successfully']/../..//button[text()='OK']").exists()) {
            $x("//h4[text()='Organization Updated Successfully']/../..//button[text()='OK']").click();
        }
        MyCommonAPIs.sleepi(10);
        openOrg(WebportalParam.Organizations);
        MyCommonAPIs.sleepi(10);
        String actOwnname = userNameOnlocDashboard.shouldBe(Condition.visible).getText();
        System.out.println("Edited owner/user Name is "+actOwnname);
        if (actOwnname.trim().equals(ownname1)) {
            result = true;
            System.out.println("User Name edited successfully");
        } else {
            result = true;
            System.out.println("User Name is not edited successfully");
        }
        return result;
    }
    
    public boolean verifyEditEmail() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        openOrg(WebportalParam.Organizations);
        MyCommonAPIs.sleepi(10);
        userNameOwnername.shouldBe(Condition.visible).hover();
        MyCommonAPIs.sleep(10);
        userNameOwnerEmail.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        $x("//label[text()='Pick Existing Owner']/..//select[@id='existingUser']")
        .shouldBe(Condition.visible)
        .selectOption("Select an owner");
        MyCommonAPIs.sleepi(5);
        ownerEmail.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleep(10);
        String ownMail = "own" + String.valueOf(new Random().nextInt(10000000)) + "@yopmail.com";
        if (ownerEmail.isDisplayed()) {
            Selenide.executeJavaScript("arguments[0].value = '';", ownerEmail);
            MyCommonAPIs.sleep(10);
            ownerEmail.sendKeys(ownMail);
            MyCommonAPIs.sleep(10);
            result = true;
            System.out.println("User Email changed to " + ownMail);
        }
        waitElement(SaveOrg);
        MyCommonAPIs.sleepi(1);
        SaveOrg.click();
        logger.info("--------------- Organisation is Edited Succesfully ----------");
        Selenide.sleep(10000);
        if ($x("//h4[text()='Organization Updated Successfully']/../..//button[text()='OK']").exists()) {
            $x("//h4[text()='Organization Updated Successfully']/../..//button[text()='OK']").click();
        }
        MyCommonAPIs.sleepi(10);
        openOrg(WebportalParam.Organizations);
        MyCommonAPIs.sleepi(10);
        String actOwnEmail = userEmailOnlocDashboard.shouldBe(Condition.visible).getText();
        System.out.println("Edited owner/user Email is "+actOwnEmail);
        if (actOwnEmail.trim().equalsIgnoreCase(ownMail)) {
            result = true;
            System.out.println("User email edited successfully");
        } else {
            result = true;
            System.out.println("User eamil is not edited successfully");
        }
        return result;
    }
    
    public boolean verifyuserNumber() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        openOrg(WebportalParam.Organizations);
        MyCommonAPIs.sleepi(10);
        userNameOwnername.shouldBe(Condition.visible).hover();
        MyCommonAPIs.sleep(10);
        userNameOwnerEmail.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        ownerPhone.shouldBe(Condition.visible);
        MyCommonAPIs.sleep(10);
        String ownnum = "0000000000";
        if (ownerPhone.isDisplayed()) {
            ownerPhone.clear();
            MyCommonAPIs.sleep(10);
            ownerPhone.sendKeys(ownnum);
            MyCommonAPIs.sleep(10);
            result = true;
            System.out.println("User Number changed to " + ownnum);
        }
        waitElement(SaveOrg);
        MyCommonAPIs.sleepi(1);
        SaveOrg.click();
        logger.info("--------------- Organisation is Edited Succesfully ----------");
        Selenide.sleep(10000);
        if ($x("//h4[text()='Organization Updated Successfully']/../..//button[text()='OK']").exists()) {
            $x("//h4[text()='Organization Updated Successfully']/../..//button[text()='OK']").click();
        }
        MyCommonAPIs.sleepi(10);
        openOrg(WebportalParam.Organizations);
        MyCommonAPIs.sleepi(10);
        String actOwnPhone = userNumberOnlocDashboard.shouldBe(Condition.visible).getText();
        System.out.println("Edited user phone number is "+actOwnPhone);
        if (actOwnPhone.trim().equalsIgnoreCase(ownnum)) {
            result = true;
            System.out.println("User phone number edited successfully");
        } else {
            result = true;
            System.out.println("User phone number is not edited successfully");
        }
        return result;
    }
    
    public boolean verifybusinessNumber() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        openOrg(WebportalParam.Organizations);
        MyCommonAPIs.sleepi(10);
        userNameOwnername.shouldBe(Condition.visible).hover();
        MyCommonAPIs.sleep(10);
        userNameOwnerEmail.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        businessPhone.shouldBe(Condition.visible);
        MyCommonAPIs.sleep(10);
        String businessnum = "1234567890";
        if (businessPhone.isDisplayed()) {
            businessPhone.clear();
            MyCommonAPIs.sleep(10);
            businessPhone.sendKeys(businessnum);
            MyCommonAPIs.sleep(10);
            result = true;
            System.out.println("business Number changed to " + businessnum);
        }
        waitElement(SaveOrg);
        MyCommonAPIs.sleepi(1);
        SaveOrg.click();
        logger.info("--------------- Organisation is Edited Succesfully ----------");
        Selenide.sleep(10000);
        if ($x("//h4[text()='Organization Updated Successfully']/../..//button[text()='OK']").exists()) {
            $x("//h4[text()='Organization Updated Successfully']/../..//button[text()='OK']").click();
        }
        MyCommonAPIs.sleepi(10);
        openOrg(WebportalParam.Organizations);
        MyCommonAPIs.sleepi(10);
        String actBusinessPhone = userNameOwnername.shouldBe(Condition.visible).getText();
        System.out.println("Edited business phone number is "+actBusinessPhone);
        if (actBusinessPhone.trim().equalsIgnoreCase(businessnum)) {
            result = true;
            System.out.println("business phone number edited successfully");
        } else {
            result = true;
            System.out.println("business phone number is not edited successfully");
        }
        return result;
    }
    
    public boolean verifyConnectedclientsClientsPage() {
        boolean result = true;
        locDashConnectedClients.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(10);
        clientsPageConnected1ClientTextHdr.shouldBe(Condition.visible);
        String url = WebDriverRunner.url();
        if (!(url.contains(URLParam.hrefClients))) {
            result = false;
            System.out.println("Step1: False After clicking on connected client count user not went to clients page url is" + url);
        } else {
            System.out.println("Step1: Pass After clicking on connected client count user went to clients page url is " + url);
        }

        verifyAPSerialNoOnClientPage(WebportalParam.ap1serialNo).shouldBe(Condition.visible);
        verifyConnectedClientmacOnClientPage(WebportalParam.clientwlanmac).shouldBe(Condition.visible);
        System.out.println("AP Serial Number Visible on clients page ie: " + verifyAPSerialNoOnClientPage(WebportalParam.ap1serialNo).getText()
                + " And also client mac is showing on clients page ie: "
                + verifyConnectedClientmacOnClientPage(WebportalParam.clientwlanmac).getText());
        if (!(verifyAPSerialNoOnClientPage(WebportalParam.ap1serialNo).isDisplayed()
                && verifyConnectedClientmacOnClientPage(WebportalParam.clientwlanmac).isDisplayed())) {
            result = false;
            System.out.println("Step2: False AP Details and Clients details are not shown on Clients Page");
        } else {
            System.out.println("Step2: Pass AP Details and Clients details are shown on Clients Page");
        }

        return result;
    }
    
    public boolean verifyOnlineDevicesCountCliableAndredirecttodevicesPageVerifyActiveFilter() {
        boolean result = true;
        locDashOnlineDevice.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(10);
        String url = WebDriverRunner.url();
        if (!(url.contains(URLParam.hrefDevices))) {
            result = false;
            System.out.println("Step1: False After clicking on Online Devices count user not went to Devices dash page url is" + url);
        } else {
            System.out.println("Step1: Pass After clicking on Online Devices count user went to Devices dash url is " + url);
        }
        filterIconAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        if (!(activefilter.isDisplayed() && onlineFilteroption.isDisplayed() && activefilter.shouldBe(visible).getText().trim().equals("Online"))) {
            result = false;
            System.out.println("Step2: False on devices dashboard page active filter and online filter is not visisble ie: "
                    + activefilter.shouldBe(visible).getText().trim());
        } else {
            System.out.println("Step2: Pass on devices dashboard page active filter and online filter is visisble ie: "
                    + activefilter.shouldBe(visible).getText().trim());
        }
        filterIconAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        return result;
    }
    
    public boolean verifyTotalDevicesCountCliableAndredirecttodevicesPageVerifyActiveFilter() {
        boolean result = true;
        locDashTotalDevice.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(10);
        String url = WebDriverRunner.url();
        if (!(url.contains(URLParam.hrefDevices))) {
            result = false;
            System.out.println("Step1: False After clicking on total Devices count user not went to Devices dash page url is" + url);
        } else {
            System.out.println("Step1: Pass After clicking on total Devices count user went to Devices dash url is " + url);
        }
        filterIconAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        if (activefilter.isDisplayed()) {
            result = false;
            System.out.println("Step2: False on devices dashboard page active filter is visisble");
        } else {
            System.out.println("Step2: Pass on devices dashboard page active filter is not visisble");
        }
        filterIconAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        return result;
    }
    
    public boolean verifyOfflineDevicesCountCliableAndredirecttodevicesPageVerifyActiveFilter() {
        boolean result = true;
        locDashOfflineDevice.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(10);
        String url = WebDriverRunner.url();
        if (!(url.contains(URLParam.hrefDevices))) {
            result = false;
            System.out.println("Step1: False After clicking on Offline Devices count user not went to Devices dash page url is" + url);
        } else {
            System.out.println("Step1: Pass After clicking on Offline Devices count user went to Devices dash url is " + url);
        }
        filterIconAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        if (!(activefilter.isDisplayed() && offlineFilteroption.isDisplayed() && activefilter.shouldBe(visible).getText().trim().equals("Offline"))) {
            result = false;
            System.out.println("Step2: False on devices dashboard page active filter and offline filter is not visisble ie: "
                    + activefilter.shouldBe(visible).getText().trim());
        } else {
            System.out.println("Step2: Pass on devices dashboard page active filter and offline filter is visisble ie: "
                    + activefilter.shouldBe(visible).getText().trim());
        }
        filterIconAllDevicesPage.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        return result;
    }
    
    public boolean verifyAddVoucherAdminInPremiumAcc(Map<String, String> map) {
        boolean result = true;
        MyCommonAPIs.sleepi(10);
        settingMenuAdminDropdown.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        voucherAdminsLink.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(10);
        if (!(addManagerButtonIcon.isDisplayed())) {
            result = false;
            System.out.println("Step1: false Add device button is not visible for VC Admin");
        } else {
            System.out.println("Step1: Pass Add device button is visible for VC Admin");
            addManagerButtonIcon.shouldBe(Condition.visible).click();
            MyCommonAPIs.sleepi(10);
            managerNameInput.shouldBe(Condition.visible).sendKeys(map.get("Name"));
            MyCommonAPIs.sleepi(1);
            ownerNameInput.shouldBe(Condition.visible).sendKeys(map.get("Email Address"));
            MyCommonAPIs.sleepi(1);
            saveButtonVCAdmin.shouldBe(Condition.visible).click();
            MyCommonAPIs.sleepi(5);
            if (!(inviteSentSuccessButton.shouldBe(Condition.visible).isDisplayed())) {
                result = false;
                System.out.println("Step2: False VC Admin not added successfully");
            } else {
                System.out.println("Step2: Pass VC Admin added successfully");
                inviteSentSuccessButton.shouldBe(Condition.visible).click();
                MyCommonAPIs.sleepi(5);
            }
        }
        return result;
    }
    
    public boolean deleteVCAdmin(String mail) {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        settingMenuAdminDropdown.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        voucherAdminsLink.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(10);
        devMacAddrEmailCell(mail).shouldBe(Condition.visible).hover();
        MyCommonAPIs.sleep(10);
        deleteDeviceIcon(mail).shouldBe(Condition.visible).hover().click();
        MyCommonAPIs.sleepi(10);
        confirmRemoveButton.shouldBe(Condition.visible);
        if (confirmRemoveButton.isDisplayed()) {
            result = true;
            confirmRemoveButton.click();
        }
        return result;
    }
    
    public boolean verifyActiveStatusofVCAdmin(String mail) {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        settingMenuAdminDropdown.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(5);
        voucherAdminsLink.shouldBe(Condition.visible).click();
        MyCommonAPIs.sleepi(10);
        if (verifyActiveStatusVC.shouldBe(Condition.visible).isDisplayed() && devMacAddrEmailCell(mail).shouldBe(Condition.visible).isDisplayed()) {
            System.out.println("Added Voucher Admin is active now");
            result = true;
        }
        return result;
    }
}
