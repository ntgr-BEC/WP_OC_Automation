/**
 *
 */
package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.AccountPageElement;

/**
 * @author zheli
 */
public class AccountPage extends AccountPageElement {
    final static Logger logger = Logger.getLogger("AccountPage");

    public AccountPage() {
        OrganizationPage page = new OrganizationPage();
        String sCheckURL = getCurrentUrl();
        if (sCheckURL.contains(URLParam.hreforganization)) {
            page.openOrg(WebportalParam.Organizations);
        }

        waitReady();
        sCheckURL = getCurrentUrl();
        if (!sCheckURL.contains(URLParam.hreforganizationDetail) && !sCheckURL.contains(URLParam.hrefaccount)) {
            WebCheck.checkUrl(URLParam.hrefaccount);
        }
        logger.info("init...");
    }

    public AccountPage(boolean noPage) {
        logger.info("initex...");
    }

    public void addNetwork(Map<String, String> map) {
        boolean exit = false;
        MyCommonAPIs.sleepi(20);
        // if (locationlist.getAttribute("aria-expanded").equals("false")) {
        // locationlist.click();
        // }
        if (locationName(map.get("Location Name")).exists()) {
            logger.info("Location is existed.");
            exit = true;
        }

        if (!exit) {
            logger.info("Create location.");
            for (String ss : map.keySet()) {
                logger.info(ss + ": " + map.get(ss));
            }
            if (addNetWorkButton.isDisplayed()) {
                addNetWorkButton.click();
            } else if (addNetWorkPro.exists()) {
                addNetWorkPro.click();
                addsinglelocation.click();
            }
            MyCommonAPIs.sleepi(35);
            waitElement(addNetLocationName);
            // timeZone.waitUntil(Condition.matchText("UTC"), 40 * 1000);
            // MyCommonAPIs.sleep(10000);
            addNetLocationName.sendKeys(map.get("Location Name"));
            MyCommonAPIs.sleepi(5);
            waitElement(addNetPassword);
            addNetPassword.sendKeys(map.get("Device Admin Password"));
            if (map.containsKey("Street")) {
                addNetStreet.setValue(map.get("Street"));
            }
            if (map.containsKey("City")) {
                addNetCity.setValue(map.get("City"));
            }
            if (map.containsKey("State")) {
                addNetState.setValue(map.get("State"));
            }
            addNetZipcode.sendKeys(map.get("Zip Code"));
            netCountryList.selectOption(map.get("Country"));
            if (map.containsKey("Time Zone")) {
                timeZone.selectOption(map.get("Time Zone"));
            }

            if (map.containsKey("Wireless Region")) {
                setWirelessRegion(map.get("Wireless Region"));
            }

            if (map.containsKey("image") && ImageIcon.exists()) {
                System.out.println("clickkkkkkkkkkkkkkkkkkkkk" + ImageIcon.getAttribute("type"));
                ImageIcon.sendKeys(map.get("image"));
                /**
                 * Autoit used if required
                 * // Runtime.getRuntime().exec(map.get("image"));
                 * //usage if req : locationInfo.put("image", "D:\\Automation_WP\\BIN\\Autoit\\autofirst.exe");
                 **/
            }
            // ButtonElements.SAVEBUTTON.click();
            MyCommonAPIs.sleepi(10);
            savebutton.click();

            MyCommonAPIs.sleepi(10);
            // if (locationlist.getAttribute("aria-expanded").equals("false")) {
            // locationlist.click();
            // }

            if (locationName(map.get("Location Name")).exists()) {
                logger.info("Location create successful");
            }
        }
    }

    public void addNewNetwork(Map<String, String> map) {
        boolean exit = false;
        MyCommonAPIs.sleepi(30);
        // if (locationlist.getAttribute("aria-expanded").equals("false")) {
        // locationlist.click();
        // }
        // if (locationName(map.get("Location Name")).exists()) {
        // logger.info("Location is existed.");
        // exit = true;
        // }

        if (!exit) {
            logger.info("Create location.");
            for (String ss : map.keySet()) {
                logger.info(ss + ": " + map.get(ss));
            }
            if (addNetWorkButton.exists()) {
                addNetWorkButton.click();
            } else if (addNetWorkPro.exists()) {
                addNetWorkPro.click();
                addsinglelocation.click();
            }
            timeZone.waitUntil(Condition.matchText("UTC"), 40 * 1000);
            // MyCommonAPIs.sleep(10000);
            addNetLocationName.sendKeys(map.get("Location Name"));
            MyCommonAPIs.sleepi(10);
            addNetPassword.sendKeys(map.get("Device Admin Password"));
            if (map.containsKey("Street")) {
                addNetStreet.setValue(map.get("Street"));
            }
            if (map.containsKey("City")) {
                addNetCity.setValue(map.get("City"));
            }
            if (map.containsKey("State")) {
                addNetState.setValue(map.get("State"));
            }
            addNetZipcode.sendKeys(map.get("Zip Code"));
            netCountryList.selectOption(map.get("Country"));
            if (map.containsKey("Time Zone")) {
                timeZone.selectOption(map.get("Time Zone"));
            }

            if (map.containsKey("Wireless Region")) {
                setWirelessRegion(map.get("Wireless Region"));
            }

            if (map.containsKey("image") && ImageIcon.exists()) {
                System.out.println("clickkkkkkkkkkkkkkkkkkkkk" + ImageIcon.getAttribute("type"));
                ImageIcon.sendKeys(map.get("image"));
                /**
                 * Autoit used if required
                 * // Runtime.getRuntime().exec(map.get("image"));
                 * //usage if req : locationInfo.put("image", "D:\\Automation_WP\\BIN\\Autoit\\autofirst.exe");
                 **/
            }
            // ButtonElements.SAVEBUTTON.click();
            MyCommonAPIs.sleepi(10);
            savebutton.click();

            MyCommonAPIs.sleepi(10);
            // if (locationlist.getAttribute("aria-expanded").equals("false")) {
            // locationlist.click();
            // }

            if (locationName(map.get("Location Name")).exists()) {
                logger.info("Location create successful");
            }
        }
    }

    public void addNetworkforLocationCheck(Map<String, String> map) {
        boolean exit = false;
        try {

            // if (locationlist.getAttribute("aria-expanded").equals("false")) {
            // locationlist.click();
            // }

            if (locationName(map.get("Location Name")).exists()) {
                logger.info("Location is existed.");
                exit = true;
            }

            if (!exit) {
                logger.info("Create location.");
                for (String ss : map.keySet()) {
                    logger.info(ss + ": " + map.get(ss));
                }
                MyCommonAPIs.sleepi(5);
                if (addNetWorkButton.exists()) {
                    addNetWorkButton.click();
                } else if (addNetWorkPro.exists()) {
                    addNetWorkPro.click();
                    addsinglelocation.click();
                }
                timeZone.waitUntil(Condition.matchText("UTC"), 20 * 1000);
                // MyCommonAPIs.sleep(10000);
                addNetLocationName.sendKeys(map.get("Location Name"));
                MyCommonAPIs.sleep(1000);
                addNetPassword.sendKeys(map.get("Device Admin Password"));
                if (map.containsKey("Street")) {
                    addNetStreet.setValue(map.get("Street"));
                }
                if (map.containsKey("City")) {
                    addNetCity.setValue(map.get("City"));
                }
                if (map.containsKey("State")) {
                    addNetState.setValue(map.get("State"));
                }
                addNetZipcode.sendKeys(map.get("Zip Code"));
                netCountryList.selectOption(map.get("Country"));
                if (map.containsKey("Time Zone")) {
                    timeZone.selectOption(map.get("Time Zone"));
                }

                if (map.containsKey("Wireless Region")) {
                    setWirelessRegion(map.get("Wireless Region"));
                }

                if (map.containsKey("image") && ImageIcon.exists()) {
                    System.out.println("clickkkkkkkkkkkkkkkkkkkkk" + ImageIcon.getAttribute("type"));
                    ImageIcon.sendKeys(map.get("image"));
                    /**
                     * Autoit used if required
                     * // Runtime.getRuntime().exec(map.get("image"));
                     * //usage if req : locationInfo.put("image", "D:\\Automation_WP\\BIN\\Autoit\\autofirst.exe");
                     **/
                }
                // ButtonElements.SAVEBUTTON.click();
                MyCommonAPIs.sleepi(2);
                savebutton.click();

                MyCommonAPIs.sleepi(3);
                // if (locationlist.getAttribute("aria-expanded").equals("false")) {
                // locationlist.click();
                // }

                if (locationName(map.get("Location Name")).exists()) {
                    logger.info("Location create successful");
                }

            }
        } catch (Exception e) {
            System.out.println("Exception => " + e.getMessage());
            System.out.println("Some error occured while Creation new location");
        }
    }

    /**
     * This API is used to add multiple network/location with default names
     *
     * @param map
     *            Usage :HashMap<String, String> locationInfo = new HashMap<String, String>();
     *            locationInfo.put("Number of Locations","2");
     *            locationInfo.put("Device Admin Password", WebportalParam.loginDevicePassword);
     *            locationInfo.put("Zip Code", "12345");
     *            locationInfo.put("Country", "United States of America");
     *            locationInfo.put("Time Zone", "UTC-07:00");
     *            new AccountPage().addMultipleNetwork(locationInfo);
     */
    public void addMultipleNetwork(Map<String, String> map) {
        boolean exit = false;

        // if (locationlist.getAttribute("aria-expanded").equals("false")) {
        // locationlist.click();
        // }

        if (locationName(map.get("Location Name")).exists()) {
            logger.info("Location is existed.");
            exit = true;
        }

        if (!exit) {
            logger.info("Create location.");
            for (String ss : map.keySet()) {
                logger.info(ss + ": " + map.get(ss));
            }
            addNetWorkButton1.click();

            MyCommonAPIs.sleep(1000);
            addMultiplelocations.click();
            MyCommonAPIs.sleep(10000);
            numberMultipleloc.sendKeys(map.get("Number of Locations"));
            MyCommonAPIs.sleep(1000);
            addPassword1.sendKeys(map.get("Device Admin Password"));
            if (map.containsKey("Country")) {
                netCountryList1.selectOption(map.get("Country"));
            }
            if (map.containsKey("Time Zone")) {
                timeZone1.selectOption(map.get("Time Zone"));
            }
            addNetZipcode1.sendKeys(map.get("Zip Code"));

            Next.click();
            MyCommonAPIs.sleepi(10);
            int Rows = 0;
            for (SelenideElement tr : LocTable) {
                Rows += 1;
            }
            MyCommonAPIs.sleepi(10);

            if (Rows == Integer.valueOf(map.get("Number of Locations"))) {
                savebutton1.click();
            }
            MyCommonAPIs.sleepi(10);
            int dialog = 0;
            for (SelenideElement elem : dialogbox1) {
                if ((dialog == 1) && elem.getText().equalsIgnoreCase("No")) {
                    elem.click();
                    logger.info("By default No is selected");
                }
                if (elem.getText().equalsIgnoreCase("All location has been created in the company")) {
                    dialog = 1;
                    logger.info(elem.getText());
                }

            }

        }
        clickYesNo(false);
        // Selenide.refresh();
    }

    public void Gotoaccountpage() {

        MyCommonAPIs.sleepi(3);
        lockscreen.click();

    }

    public AccountPage selectWirelessRegionByIndex(String locationName, int count) {
        Map<String, String> locationInfo = new HashMap<String, String>();
        if (locationName(locationName).exists()) {
            editNetwork(locationName);
            MyCommonAPIs.sleepi(3);

            logger.info("Select wireless region by index.");
            editwirelessregion.selectOption(count);
            MyCommonAPIs.sleepi(3);
            if ($x("//div[@class=\"modal fade myModalChangeRegion in\"]").exists()) {
                changeregionconfirm.click();
            }
            // ButtonElements.SAVEBUTTON.click();
            editsave.click();
        }
        return new AccountPage();

    }

    public AccountPage editLocation(String locationName, Map<String, String> map) {
        if (locationName(locationName).exists()) {
            editNetwork(locationName);
            MyCommonAPIs.sleepi(10);

            logger.info("Modify location.");
            for (String ss : map.keySet()) {
                logger.info(ss + ": " + map.get(ss));
            }
            waitElement(editname);
            // MyCommonAPIs.sleep(10000);
            if (map.containsKey("Location Name")) {
                editname.clear();
                editname.sendKeys(map.get("Location Name"));
            }
            MyCommonAPIs.sleep(1000);
            if (map.containsKey("Device Admin Password")) {
                editpassword.clear();
                editpassword.sendKeys(map.get("Device Admin Password"));
            }
            // if (map.containsKey("Street")) {
            // addNetStreet.setValue(map.get("Street"));
            // }
            // if (map.containsKey("City")) {
            // addNetCity.setValue(map.get("City"));
            // }
            // if (map.containsKey("State")) {
            // addNetState.setValue(map.get("State"));
            // }
            if (map.containsKey("Zip Code")) {
                editzipcode.clear();
                editzipcode.sendKeys(map.get("Zip Code"));
            }
            if (map.containsKey("Country")) {
                editcountry.selectOption(map.get("Country"));
            }
            if (map.containsKey("Time Zone")) {
                edittimezone.selectOption(map.get("Time Zone"));
            }
            if (map.containsKey("Wireless Region") && !editwirelessregion.getSelectedText().equals(map.get("Wireless Region"))) {
                editwirelessregion.selectOption(map.get("Wireless Region"));
            }
            MyCommonAPIs.sleepi(3);
            if ($x("//div[@class=\"modal fade myModalChangeRegion in\"]").exists()) {
                changeregionconfirm.click();
            }
            // ButtonElements.SAVEBUTTON.click();
            editsave.click();
        }
        return new AccountPage();
    }

    public AccountPage editSSID80211wLocation(String locationName, Map<String, String> map) {
        if (locationName(locationName).exists()) {
            editNetwork(locationName);
            MyCommonAPIs.sleepi(3);

            networksetup.click();
            addnetworksetupbtn.click();
            waitElement(networkname);
            MyCommonAPIs.sleepi(3);
            if (map.containsKey("Network Name")) {
                networkname.clear();
                networkname.setValue(map.get("Network Name"));
            }

            // networkname.setValue(map.get("Network Name"));

            networkvlanname.setValue(map.get("VLAN Name"));
            networkvlanid.setValue(map.get("VLAN ID"));
            nxtbtn.click();
            MyCommonAPIs.sleepi(3);
            skpbtn.click();
            MyCommonAPIs.sleepi(3);
            addnewssidwifi.click();
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
            // if(band5.isDisplayed()){
            // band5.click();
            // logger.info("Uncheck 5 band");
            // }
            // if(band24.isDisplayed()){
            // band24.click();
            // logger.info("Uncheck 24 band");
            // }
            security.selectOption(map.get("Security"));
            if (map.containsKey("Password")) {
                password.setValue(map.get("Password"));

            }
            MyCommonAPIs.sleepi(5);
            Mandatory.click();
            MyCommonAPIs.sleepi(5);
            addbtn.click();
            nxtbtn.click();
            nxtbtn.click();
            cnfrmbtn.click();
            nxtbtn.click();
        }
        return new AccountPage();
    }

    public AccountPage editdisableSSID80211wLocation(String locationName, Map<String, String> map) {
        if (locationName(locationName).exists()) {
            editNetwork(locationName);
            MyCommonAPIs.sleepi(3);

            networksetup.click();
            addnetworksetupbtn.click();
            if (VLANOK.exists()) {
                VLANOK.click();
            }
            waitElement(networkname);
            MyCommonAPIs.sleepi(3);
            if (map.containsKey("Network Name")) {
                networkname.clear();
                networkname.setValue(map.get("Network Name"));
            }

            // networkname.setValue(map.get("Network Name"));

            networkvlanname.setValue(map.get("VLAN Name"));
            networkvlanid.setValue(map.get("VLAN ID"));
            nxtbtn.click();
            MyCommonAPIs.sleepi(3);
            skpbtn.click();
            MyCommonAPIs.sleepi(3);
            addnewssidwifi.click();
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
            // if(band24.isDisplayed()){
            // band24.click();
            // logger.info("Uncheck 2.4 band");
            // }
            // if(band5.isDisplayed()){
            // band5.click();
            // logger.info("Uncheck 5 band");
            // }
            security.selectOption(map.get("Security"));
            if (map.containsKey("Password")) {
                password.setValue(map.get("Password"));

            }
            // SSID80211wbtn.click();
            MyCommonAPIs.sleepi(5);
            addbtn.click();
            nxtbtn.click();
            nxtbtn.click();
            cnfrmbtn.click();
            nxtbtn.click();
        }
        return new AccountPage();
    }

    public void deleteSsidVlan(String locationName, String vlan) {
        mainpage.click();
        // if (locationName(locationName).exists()) {
        // editNetwork(locationName);
        MyCommonAPIs.sleepi(3);

        if (locationName(locationName).exists()) {
            editNetwork(locationName);
            MyCommonAPIs.sleepi(3);

            networksetup.click();
            MyCommonAPIs.sleep(5 * 1000);

            logger.info("Delete ssid.");
            // executeJavaScript("arguments[0].removeAttribute('class')", delVlan(vlan));
            MyCommonAPIs.sleep(3000);
            // deleteSsid(vlan).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(3000);
            // deletessidyes.click();
            MyCommonAPIs.sleep(5 * 1000);

        }
    }
    // }

    public AccountPage ssidconfigbackupLocation(String locationName, Map<String, String> map) {
        // mainpage.click();
        // orgpage.click();
        MyCommonAPIs.sleepi(3);
        if (locationName(locationName).exists()) {
            System.out.println("Going to edit network");
            editNetwork(locationName);
            MyCommonAPIs.sleepi(3);
            System.out.println("Going to click config backup button");
            cnfgbckuprstr.click();
            waitReady();
            MyCommonAPIs.sleepi(5);
            crtbckup.click();
            MyCommonAPIs.sleepi(3);
            bckupkname.setValue(map.get("Name"));
            description.setValue(map.get("Description"));
            crtbckupbtn.click();
            MyCommonAPIs.sleepi(3);

            boolean result2 = true;
            if (checkSsidIsExist(map.get("Name"))) {
                System.out.println("config backup file created.");
                System.out.println("Config backup file created");
                result2 = true;
            } else {
                System.out.println("config backup file is not created");
                result2 = false;
            }
            MyCommonAPIs.sleepi(8);
            assertTrue(result2, "config backup file is not created");
            MyCommonAPIs.sleepi(3);
            logger.info("config file created successful.");
            System.out.println("Going back from config backup button");
        } else {
            logger.warning("checkSsidIsExist error");
        }
        return new AccountPage();
    }

    public String networktimezone(String locationName, Map<String, String> map) {
        // mainpage.click();
        MyCommonAPIs.sleepi(3);
        String time1 = "";
        if (locationName(locationName).exists()) {
            editNetwork(locationName);
            MyCommonAPIs.sleepi(3);
            headingtimezone.click();
            // System.out.println("Clicked on time zone heading");
            MyCommonAPIs.sleepi(3);
            time1 = timezonevalue.getText();

            MyCommonAPIs.sleepi(1);
            // System.out.println("Time Zone Value- "+time1);
            mainpage.click();
            MyCommonAPIs.sleepi(1);
            logger.info("time zone value checked");

        } else {
            logger.warning("location does not exist with same name.");
        }
        return time1;
    }

    public String updatenetworktimezone(String locationName, Map<String, String> map) {
        // mainpage.click();
        MyCommonAPIs.sleepi(3);
        String time1 = "";
        if (locationName(locationName).exists()) {
            editNetwork(locationName);
            MyCommonAPIs.sleepi(3);
            String wirelessreg = wirelessRegion.getText();
            System.out.print(wirelessreg);
            headingtimezone.click();
            // System.out.println("Clicked on time zone heading");
            edittimezone.click();
            MyCommonAPIs.sleepi(3);
            if (wirelessreg.equalsIgnoreCase("Germany") || wirelessreg.equalsIgnoreCase("Sri Lanka")) {
                firsttimezone.click();
            } else {
                secondtimezone.click();
            }
            editsave.click();
            MyCommonAPIs.sleepi(3);
            headingtimezone.click();
            time1 = timezonevalue.getText();

            MyCommonAPIs.sleepi(1);
            // System.out.println("Time Zone Value- "+time1);
            mainpage.click();
            MyCommonAPIs.sleepi(1);
            logger.info("time zone value updated");

        } else {
            logger.warning("location does not exist with same name.");
        }
        return time1;
    }

    public String setfirstnetworktimezonevalue(String locationName, Map<String, String> map) {
        // orgpage.click();
        MyCommonAPIs.sleepi(3);
        String time1 = "";
        if (locationName(locationName).exists()) {
            editNetwork(locationName);
            MyCommonAPIs.sleepi(3);
            headingtimezone.click();
            // System.out.println("Clicked on time zone heading");
            edittimezone.click();
            MyCommonAPIs.sleepi(3);
            firsttimezone.click();
            // secondtimezone.click();
            editsave.click();
            MyCommonAPIs.sleepi(3);
            headingtimezone.click();
            time1 = timezonevalue.getText();

            MyCommonAPIs.sleepi(1);
            // System.out.println("Time Zone Value- "+time1);
            mainpage.click();
            MyCommonAPIs.sleepi(1);
            logger.info("time zone value updated");

        } else {
            logger.warning("location does not exist with same name.");
        }
        return time1;
    }

    public void ssidtimezoneupdate(String locationName, Map<String, String> map) {
        mainpage.click();
        MyCommonAPIs.sleepi(3);
        if (locationName(locationName).exists()) {
            editNetwork(locationName);
            MyCommonAPIs.sleepi(3);

            timezonevalue.click();
            editsave.click();
            MyCommonAPIs.sleepi(1);
            mainpage.click();
            MyCommonAPIs.sleepi(1);
            logger.info("time zone value checked");
        } else {
            logger.warning("location does not exist with same name.");
        }
    }

    public AccountPage ssidconfigrestore(String locationName, Map<String, String> map) {
        mainpage.click();
        if (orgpage.exists()) {
            orgpage.click();
        }
        System.out.println("Inside ssid config restore, clicked on first organization");
        MyCommonAPIs.sleepi(3);
        if (locationName(locationName).exists()) {
            editNetwork(locationName);
            MyCommonAPIs.sleepi(3);

            cnfgbckuprstr.click();
            if (checkSsidIsExist(map.get("Name"))) {
                logger.info("Restore Config.");
                executeJavaScript("arguments[0].removeAttribute('class')", rstrconfgblock(map.get("Name")));
                MyCommonAPIs.sleep(3000);
                System.out.println("3");
                rstrconfg(map.get("Name")).waitUntil(Condition.visible, 60 * 1000).click();
                System.out.println("4");
                MyCommonAPIs.sleep(3 * 1000);
                try {
                    yesbtn.click();

                    System.out.println("5");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                MyCommonAPIs.sleep(8 * 1000);
            } else {
                logger.warning("checkSsidIsExist error");
            }
            mainpage.click();

        } else {
            logger.warning("checkSsidIsExist error");
        }
        return new AccountPage();
    }

    public boolean checkSsidIsExist(String SSID) {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        waitElement($("#CnfgBkupTable"));
        String sElement = String.format("//span[contains(text(),'%s')]", SSID);
        logger.info("on element:" + sElement);
        if ($x(sElement).exists()) {
            result = true;
            logger.info("Ssid:" + SSID + " is existed.");
        }
        return result;
    }

    public void backupEdit(String locationName, Map<String, String> map) {
        if (locationName(locationName).exists()) {
            editNetwork(locationName);
            MyCommonAPIs.sleepi(3);

            cnfgbckuprstr.click();
            MyCommonAPIs.sleepi(5);
            executeJavaScript("arguments[0].removeAttribute('class')", editConfg(map.get("oldName")));
            MyCommonAPIs.sleep(3000);
            editconfg(map.get("oldName")).waitUntil(Condition.visible, 60 * 1000).click();
            MyCommonAPIs.sleep(8 * 1000);
            editBackupName.setValue(map.get("Name"));
            editBackupDes.setValue(map.get("Description"));
            editBackupSave.click();
            MyCommonAPIs.sleepi(5);

        }
    }

    public boolean checkConfigurationBackupAndRestorePageElement(String locationName, String text) {
        boolean result = false;
        if (locationName(locationName).exists()) {
            editNetwork(locationName);
            MyCommonAPIs.sleepi(3);
            if (cnfgbckuprstr.exists()) {
                cnfgbckuprstr.click();
                MyCommonAPIs.sleepi(5);
                if (text.equals("default") && getText($(".InstantConfiguration")).contains(WebportalParam.getLocText("Location Backups"))
                        && crtbckupbutton.exists() && crtbckup.exists()) {
                    result = true;
                    logger.info("Page elements displayed correct.");
                } else if (text.equals("create and edit")) {
                    ElementsCollection tableHeads = $$x("//tr[@role='row']/th");
                    int i = 0;
                    boolean flag = true;
                    for (SelenideElement ele : tableHeads) {
                        if (flag) {
                            switch (i) {
                            case 0:
                                flag = getText(ele).equals(WebportalParam.getLocText("Name"));
                                break;
                            case 1:
                                flag = getText(ele).equals(WebportalParam.getLocText("Description"));
                                break;
                            case 2:
                                flag = getText(ele).equals(WebportalParam.getLocText("Created By"));
                                break;
                            case 3:
                                flag = getText(ele).equals(WebportalParam.getLocText("Created On"));
                                break;
                            case 4:
                                flag = getText(ele).equals(WebportalParam.getLocText("Status"));
                                break;
                            case 5:
                                break;
                            }
                            i += 1;
                        } else {
                            break;
                        }
                    }

                    if (flag && getText(backupStatus).equals("Completed")) {
                        result = true;
                        logger.info("Backup information table displayed correct.");
                    }
                }
            }
        }
        return result;
    }

    public void backupDelete(String locationName, Map<String, String> map) {
        if (locationName(locationName).exists()) {
            editNetwork(locationName);
            MyCommonAPIs.sleepi(3);

            cnfgbckuprstr.click();
            MyCommonAPIs.sleepi(5);
            String sElement = String.format("//span[contains(text(),'%s')]", map.get("Name"));
            if ($x(sElement).exists()) {
                logger.info("Delete backup.");
                executeJavaScript("arguments[0].removeAttribute('class')", editConfg(map.get("Name")));
                MyCommonAPIs.sleep(3000);
                delteconfg(map.get("Name")).waitUntil(Condition.visible, 60 * 1000).click();
                MyCommonAPIs.sleep(8 * 1000);
                deleteBackupYes.click();
                MyCommonAPIs.sleep(8 * 1000);
            }
            mainpage.click();

        } else {
            logger.warning("backupDelete error");
        }
    }

    public void restoreBackup(String locationName, String backupName) {
        if (locationName(locationName).exists()) {
            editNetwork(locationName);
            MyCommonAPIs.sleepi(3);
            cnfgbckuprstr.click();
            MyCommonAPIs.sleepi(5);
            String sElement = String.format("//span[contains(text(),'%s')]", backupName);
            if ($x(sElement).exists()) {
                executeJavaScript("arguments[0].removeAttribute('class')", editConfg(backupName));
                MyCommonAPIs.sleep(3000);
                rstrconfg(backupName).waitUntil(Condition.visible, 60 * 1000).click();
                MyCommonAPIs.sleep(8 * 1000);
                restoreBackupYes.click();
                MyCommonAPIs.sleep(8 * 1000);
                logger.info("Restore backup success.");
                MyCommonAPIs.sleepsync();
            }
        }
    }

    public boolean checkBackupIsExist(String locationName, String backupName) {
        boolean result = false;
        if (locationName(locationName).exists()) {
            editNetwork(locationName);
            MyCommonAPIs.sleepi(3);
            if (cnfgbckuprstr.exists()) {
                cnfgbckuprstr.click();
                MyCommonAPIs.sleepi(8);
                String sElement = String.format("//span[contains(text(),'%s')]", backupName);
                logger.info("on element:" + sElement);
                if ($x(sElement).exists()) {
                    result = true;
                    logger.info("Backup: " + backupName + " is existed.");
                }
            }
        }
        return result;
    }

    public String getCountry(String locationName) {
        // WebCheck.checkUrl(URLParam.hrefDevices);
        String Country = "";
        if (locationName(locationName).exists()) {
            editNetwork(locationName);
            waitReady();
            Country = editcountry.getSelectedText();
            MyCommonAPIs.sleepi(3);
            editcancel.click();
        }
        return Country;
    }

    public String getWirelessRegion(String locationName) {
        // WebCheck.checkUrl(URLParam.hrefDevices);
        String region = "";
        if (locationName(locationName).exists()) {
            editNetwork(locationName);
            waitReady();
            region = editwirelessregion.getSelectedText();
            MyCommonAPIs.sleepi(3);
            editcancel.click();
        }
        return region;
    }

    public NetworkEditNetworkPage enterEditNetworkPage() {
        // firstLocationMoreIcon.click();
        // firstLocationEditIcon.click();
        editLocation();
        return new NetworkEditNetworkPage();
    }

    /**
     * @param locationInfo
     */
    public DashboardLocationPage enterLocation(String locationName) {
        // TODO Auto-generated method stub
        // if (locationlist.getAttribute("aria-expanded").equals("false")) {
        // locationlist.click();
        // }
        MyCommonAPIs.sleepi(20);
        if (locationName(locationName).exists()) {
            click(locationName(locationName), true);
        } else {
            if (firstlocation.exists()) {
                firstlocation.click();
                logger.info("Cannot find location, click first location.");
            } else {
                logger.info("No create location.");
            }
        }
        return new DashboardLocationPage();
    }

    public void enterVoucherLocation(String orgName, String locationName) {
        if (locationName(orgName).exists()) {
            click(locationName(orgName), true);
        }
        if (locationName(locationName).exists()) {
            click(locationName(locationName), true);
        } else {
            logger.info("Cannot find location: " + locationName);
        }
    }

    public AccountPage deleteOneLocation(String locationName) {
        // if (locationlist.getAttribute("aria-expanded").equals("false")) {
        // locationlist.click();
        // }
        MyCommonAPIs.sleepi(20);
        new MyCommonAPIs().open(URLParam.hrefaccount, true);
        MyCommonAPIs.sleepi(20);
        MyCommonAPIs.sleepi(3);
        if (locationName(locationName).exists()) {
            deleteLocation(locationName);
            MyCommonAPIs.sleepi(10);
        } else {
            logger.info("Cannot find location: " + locationName);
        }
        return new AccountPage();
    }

    public void checkAccountPage() {
        // if ($(sOrganizationLocationElementNew).exists()) {
        // $(".gridViewIcon").click();
        // }
        String newElement = "";
        if ($(sOrganizationLocationElementNew).exists()) {
            $(sOrganizationLocationElementNew).click();
            MyCommonAPIs.sleepi(3);
        }
    }

    public boolean checkLocationNumber(String number) {
        boolean result = false;
        ElementsCollection elesTwo = $$(".location-name");
        System.out.println(elesTwo.size());
        System.out.println(Integer.valueOf(number));
        MyCommonAPIs.sleepi(5);
        if (elesTwo.size() == Integer.valueOf(number)) {
            result = true;
            logger.info("Location number: " + String.valueOf(elesTwo.size()));
        }
        return result;
    }

    // public String checkAccountLocked() {
    //
    // String Result = null;
    // new MyCommonAPIs().open(WebportalParam.serverUrl);
    //
    // String url = MyCommonAPIs.getCurrentUrl();
    //
    // if(url.contains("accountLocked") && lock.exists())
    // {
    // logger.info("Account is locked need more subscription to unlocak");
    //
    // Result = "Locked";
    // return Result;
    // }
    //
    // Result = "Unlocaked";
    // return Result;
    //
    // }

    public boolean checkMaximumNumberOfBackup(String locationName) {
        boolean result = false;
        MyCommonAPIs.sleepi(3);
        if (locationName(locationName).exists()) {
            System.out.println("Going to edit network");
            editNetwork(locationName);
            MyCommonAPIs.sleepi(3);
            System.out.println("Going to click config backup button");
            cnfgbckuprstr.click();
            waitReady();
            MyCommonAPIs.sleepi(5);
            crtbckup.click();
            MyCommonAPIs.sleepi(5);
            if (maximumnumberdialog.exists()) {
                if (getText(maximumnumberdialog)
                        .contains("The maximum number of backup files has been reached. Delete older backup files, and then try again")) {
                    result = true;
                    logger.info("Backup limit exceeded dialog displayed.");
                    clickBoxFirstButton();
                }
            }
        }
        return result;
    }

    // Added By Shoib
    public boolean VerifyCreatedLocations(List<String> loclist, int totalinput) {
        // TODO Auto-generated method stub
        boolean res = false;
        int count = 0;
        int totalcount = loclist.size();
        TotalLocationCountPath.scrollIntoView("{behavior: \"instant\", block: \"end\", inline: \"nearest\"}");
        String displaycount = TotalLocationCountPath.getText();
        System.out.println(displaycount);
        if (displaycount.contains("(" + Integer.toString(totalcount) + ")")) {
            System.out.println("Total Locations Count Created are Matching with the Available Location Count On Page");
            System.out.println("Printing Total Locations Count --->" + totalcount + displaycount);
            res = true;
        }

        else {
            System.out.println("Location Count Mismatching ----- Alert");
            System.out.println("Printing Total Locations Count --->" + totalcount + displaycount);
        }

        // for(String temp : loclist) {
        // if(locationName(temp).scrollIntoView("{behavior: \"instant\", block: \"end\", inline: \"nearest\"}") != null) {
        // System.out.println("Scrolled to Location");}
        // else {
        // locationName(temp).scrollIntoView("{behavior: \"instant\", block: \"end\", inline: \"nearest\"}");
        // }
        //
        // if (locationName(temp).exists()) {
        // logger.info(temp+"<----Location Exists[Happy]");
        // count = count+1;
        // }
        // else {
        // logger.info(temp+"<----Location Doesnot Exists[Sad]");
        // }
        // }

        return res;
    }

    // Added By Shoib
    public boolean VerifyCreatedOrganizatons(List<String> loclist, int totalinput) {
        // TODO Auto-generated method stub
        boolean res = false;
        int count = 0;
        if (loclist.size() == totalinput) {
            res = true;
            System.out.println("Total Input organizaion given matches with Total organizations available in the list");
        } else {
            res = false;
            System.out.println("Total requested Organization mismatches with the list count");
        }

        return res;

    }

    public void editAndChangePasswordPriaccLocation() {

        MyCommonAPIs.sleepi(1);
        editLocation.click();
        MyCommonAPIs.sleepi(1);
        editLocationbtn.click();
        MyCommonAPIs.sleepi(5);
        changePassword.clear();
        MyCommonAPIs.sleepi(1);
        changePassword.sendKeys("Netgear2@");
        viewPassword.click();
        MyCommonAPIs.sleepi(1);
        clkonSave.click();
        MyCommonAPIs.sleepi(2);
        clickOnHomePage.click();
        MyCommonAPIs.sleepi(5);

    }

    public boolean verifyLocPassword() {
        boolean result = false;
        MyCommonAPIs.sleepi(1);
        editLocation.click();
        MyCommonAPIs.sleepi(1);
        editLocationbtn.click();
        MyCommonAPIs.sleepi(1);
        viewPassword.click();
        MyCommonAPIs.sleepi(1);
        checkPassword.click();
        MyCommonAPIs.sleepi(1);
        if (checkPassword.isDisplayed()) {
            System.out.println("Location Password Changed to Netgear2@");
            result = true;
        }
        MyCommonAPIs.sleepi(1);
        clickOnHomePage.click();
        MyCommonAPIs.sleepi(1);
        return result;

    }

    public void editAndChangePasswordProaccLocation() {

        MyCommonAPIs.sleepi(5);
        editLocationpro.click();
        MyCommonAPIs.sleepi(2);
        editLocationbtnpro.click();
        MyCommonAPIs.sleepi(5);
        changePasswordpro.clear();
        MyCommonAPIs.sleepi(1);
        changePasswordpro.sendKeys("Netgear2@");
        viewPasswordpro.click();
        MyCommonAPIs.sleepi(1);
        clkonSavepro.click();
        MyCommonAPIs.sleepi(1);
        clickOnHomePagepro.click();
        MyCommonAPIs.sleepi(5);

    }

    public boolean verifyProLocPassword() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        editLocationpro.click();
        MyCommonAPIs.sleepi(1);
        editLocationbtnpro.click();
        MyCommonAPIs.sleepi(5);
        checkproPassword.click();
        MyCommonAPIs.sleepi(1);
        if (checkproPassword.isDisplayed()) {
            System.out.println("Pro Account Location Password Changed to Netgear2@");
            result = true;
        }
        MyCommonAPIs.sleepi(1);
        clickOnHomePagepro.click();
        MyCommonAPIs.sleepi(5);
        return result;

    }

    public void netgearUpgradetoProPage(Map<String, String> map) {

        MyCommonAPIs.sleepi(15);

        // if (upgradetoProText.isDisplayed()) {
        // System.out.println(upgradetoProText.getText());
        // //System.out.println("Step3: Fill The Upgrade to pro account form.");
        // }
        // MyCommonAPIs.sleepi(2);

        managedServiceProvider.click();

        MyCommonAPIs.sleepi(2);
        if (map.containsKey("Business Name")) {
            businessName.sendKeys(map.get("Business Name"));
        }
        MyCommonAPIs.sleepi(2);
        if (map.containsKey("Primary Address of Business")) {
            businessAddressName.sendKeys(map.get("Primary Address of Business"));
        }
        MyCommonAPIs.sleepi(2);
        if (map.containsKey("City")) {
            businessCity.sendKeys(map.get("City"));
        }
        MyCommonAPIs.sleepi(2);
        if (map.containsKey("State")) {
            businessState.sendKeys(map.get("State"));
        }
        MyCommonAPIs.sleepi(2);
        businesscityZipCode.sendKeys(map.get("Zip Code"));
        MyCommonAPIs.sleepi(2);
        businessCountry.selectOption(map.get("Country"));
        MyCommonAPIs.sleepi(2);
        if (map.containsKey("Business Phone Number")) {
            businessPhoneNumber.sendKeys(map.get("Business Phone Number"));
        }
        MyCommonAPIs.sleepi(10);
        businessUpgradeBtn.click();

        MyCommonAPIs.sleepi(10);

        if (businessAccSuccessMsg.isDisplayed()) {
            System.out.println("Premium to Pro Account Upgrade successful");
        }
    }

    public boolean verifyTextSuccessfullyaddedLocation() {

        boolean result = false;
        if (verifyText1.exists() && verifyText2.exists() && addDeviceBtn.exists() && addMultipleDevices.exists()) {
            result = true;
        }
        return result;
    }

    public void addMultipleDevices() {

        MyCommonAPIs.sleepi(10);
        addMultipleDevices.click();
        MyCommonAPIs.sleepi(5);

    }

    public boolean verifyLocName(Map<String, String> map) {
        boolean result = false;
        MyCommonAPIs.sleepi(1);
        editLocation.click();
        MyCommonAPIs.sleepi(1);
        editLocationbtn.click();
        MyCommonAPIs.sleepi(1);
        // viewPassword.click();
        // MyCommonAPIs.sleepi(1);
        // checkPassword.click();
        // MyCommonAPIs.sleepi(1);
        if (checkName.isDisplayed()) {
            System.out.println("Location Name Changed to OnboardingTestNew");
            result = true;
        }
        MyCommonAPIs.sleepi(1);
        clickOnHomePage.click();
        MyCommonAPIs.sleepi(1);
        return result;

    }

    // added by Vivek
    public void clickingUpgradeToProSubscription() {
        // MyCommonAPIs.sleepi(1);
        // refresh();
        // MyCommonAPIs.sleepi(15);
        // clickOnHomePage.click();
        // openPurchaseHistoryPage();
        MyCommonAPIs.sleepi(5);
        UpgradeToProLink.click();
        MyCommonAPIs.sleepi(5);
        UpgradeToProConfirmButton.click();
        MyCommonAPIs.sleepi(5);

    }

    // added by Vivek
    public void openDevicePage() {
        MyCommonAPIs.sleepi(1);
        new MyCommonAPIs().open(URLParam.hrefDevices, true);
        clickOnHomePage.click();

    }

    // added by Vivek
    public void openPurchaseHistoryPage() {
        MyCommonAPIs.sleepi(1);
        // new MyCommonAPIs().open(URLParam.hrefpurchaseHistory, true);

    }

    // added by Vivek
    public void openLocationEditPage() {
        MyCommonAPIs.sleepi(5);
        firstLocationMoreIcon.click();
        MyCommonAPIs.sleepi(1);
        firstLocationEditIcon.click();
    }

    // added by vivek
    public void addLocLogo() {
        MyCommonAPIs.sleepi(5);
        locAddLogo.sendKeys(
                "C:\\WebportalAutomation\\com\\src\\test\\java\\webportal\\WebPortalUsabilityImprovements\\PRJCBUGEN_T32195\\loc_icon.png");
        MyCommonAPIs.sleepi(2);
        editsave.click();
        MyCommonAPIs.sleepi(2);

    }

    // added by vivek
    public boolean GetLocIconUrlAndVerifyLogoIsUploaded() {
        boolean result = false;
        Selenide.refresh();
        MyCommonAPIs.sleepi(5);
        String url = LocHeaderIconUrl.getAttribute("src");
        System.out.println(url);
        if (url.contains("loc_icon.png")) {
            logger.info("Yes, Location Logo is Updated and Verified");
            result = true;
        }
        return result;
    }

    // added by Vivek
    public void editLocationandOpenRadiusSection() {
        MyCommonAPIs.sleepi(5);
        editLocationpro.click();
        MyCommonAPIs.sleepi(2);
        editLocationbtnpro.click();
        MyCommonAPIs.sleepi(5);
        radiusText.click();
        MyCommonAPIs.sleepi(1);
    }

    // added by Vivek
    public void enterTheServerIP() {
        MyCommonAPIs.sleepi(5);
        radiusServerInput.clear();
        MyCommonAPIs.sleepi(2);
        radiusServerInput.sendKeys("172.16.27.12");
        MyCommonAPIs.sleepi(2);
        radiusServerSecret.sendKeys("Netgear1@");
        MyCommonAPIs.sleepi(2);
        radiusSaveButton.click();
        MyCommonAPIs.sleepi(1);

    }

    // added by Vivek
    public void enableTheRadiusServer() {
        MyCommonAPIs.sleepi(15);
        radiusSwitchButton.click();
        MyCommonAPIs.sleepi(5);
    }

    // added by vivek
    public boolean VerifyRadiussucessMsg() {
        boolean result = false;
        if (radiusSuccessMsg.isDisplayed()) {
            logger.info("Yes, Radius is enable");
            result = true;
        }
        return result;
    }

    // added by Vivek
    public void openEditLocationFormforPro() {
        MyCommonAPIs.sleepi(2);
        editLocationpro.click();
        MyCommonAPIs.sleepi(1);
        editLocationbtnpro.click();
        MyCommonAPIs.sleepi(2);
    }

    // added by Vivek
    public void ChangeTheLocationPwd(String newPwd) {
        MyCommonAPIs.sleepi(2);
        locationPwd.clear();
        MyCommonAPIs.sleepi(2);
        locationPwd.sendKeys(newPwd);
        MyCommonAPIs.sleepi(2);
        LocSaveButton.click();

    }

    // added by Vivek
    public boolean verifySportUsrAbletoSeeLoCountOnLocScreen() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        if (locCount.exists() & locCount.text().contains("2")) {
            logger.info("Yes, Support user can see the location counts");
            result = true;
        }
        return result;
    }

    public boolean Namevalidation(String locationName, Map<String, String> map) {

        MyCommonAPIs.sleepi(3);
        if (locationName(locationName).exists()) {
            System.out.println("Going to edit network");
            editNetwork(locationName);
            MyCommonAPIs.sleepi(3);
            System.out.println("Going to click config backup button");
            cnfgbckuprstr.click();
            waitReady();
            MyCommonAPIs.sleepi(5);
            crtbckup.click();
            MyCommonAPIs.sleepi(3);
            bckupkname.setValue(map.get("Name"));

        }

        return false;
    }

    public boolean checkVLAN(String locationName, Map<String, String> map) {
        boolean result = false;

        if (locationName(locationName).exists()) {
            editNetwork(locationName);
            MyCommonAPIs.sleepi(3);
            networksetup.click();
        }

        if (checkVLAN(map.get("Network Name")).isDisplayed()) {
            System.out.println("network exits");
            result = true;
        }
        return result;
    }

    // Added by Pratik
    public void editLocationandOpenRadiusSectionPremiumAcc() {
        MyCommonAPIs.sleepi(5);
        editNetwork.click();
        MyCommonAPIs.sleepi(15);
        radiusText.click();
        MyCommonAPIs.sleepi(1);
    }
    
    public AccountPage deleteOneLocationFromProAcc(String locationName) {
        OrganizationPage OrganizationPage = new OrganizationPage();
        OrganizationPage.openOrg(WebportalParam.Organizations);
        MyCommonAPIs.sleepi(20);
        if (locationName(locationName).exists()) {
            deleteLocation(locationName);
            MyCommonAPIs.sleepi(10);
        } else {
            logger.info("Cannot find location: " + locationName);
        }
        return new AccountPage();
    }
    
    
}
