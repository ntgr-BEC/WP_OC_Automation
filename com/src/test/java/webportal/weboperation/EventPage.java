package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.webelements.EventElement;
import webportal.weboperation.NetworkSetupPage;

public class EventPage extends EventElement {
    Logger logger = Logger.getLogger("EventPage");
    
    public EventPage() {
        logger.info("init...");
    }
    
    public void showAllEvent() {
        Actions actions = new Actions(WebportalParam.curWebDriver);
        try {
            $(sTable).click();
        } catch (Throwable e) {
            takess();
            e.printStackTrace();
            return;
        }
        int maxSize = 0;
        int curSize = getEventCount();
        while (maxSize != curSize) {
            System.out.println("inside while loop");
            curSize = getEventCount();
            for (int i = 0; i < 30; i++) {
                actions.sendKeys(Keys.PAGE_DOWN).perform();
            }
            waitReady();
            sleep(5, "wait event table ready");
            maxSize = getEventCount();
        }
    }
    
    public void gotoPage() {
        open(URLParam.hrefEvent, true);  
        initFlag();
        showAllEvent();
    }
    
    public boolean isAllTypeEventShown() {
        List<String> lsEvent = getEventType();
        System.out.println("lsEvent"+ lsEvent );
        
        if (lsEvent.contains(sCritical) && lsEvent.contains(sWarning) && lsEvent.contains(sNotifications))
            return false;
        return true;
    }
    
    public void makeEvent(boolean toNew) {
        if (toNew || isAllTypeEventShown()) {
            // make event, create an existed vlan will make warning, reboot will make critical & info
            doSwitchCommand(1);
            refresh();
            showAllEvent();
        }
    }
    
    public int getNotificationCount() {
        if (txtCount.exists())
            return Integer.parseInt(getText(txtCount));
        return 0;
    }
    
    public int getEventCount() {
        if (!$(sRow).exists())
            return 0;
        int size = $$(sRow).size();
        logger.info(String.format("%d", size));
        return size;
    }
    
    /**
     * @return name & details
     */
    public List<String> getEventSummary() {
        List<String> lsRet = new ArrayList<String>();
        ElementsCollection ecs1 = getElements(sEventName);
        ElementsCollection ecs2 = getElements(sEventDesc);
        for (int i = 0; i < getEventCount(); i++) {
            String toAdd = ecs1.get(i).getText() + " -- " + ecs2.get(i).getText();
            logger.info(toAdd);
            lsRet.add(toAdd);
        }
        return lsRet;
    }
    
    public List<String> getEventDesc() {
        System.out.println(sEventDesc);
        return getTexts(sEventDesc);
    }
    
    public List<String> getEventType() {
        return getTexts(sEventType);
    }
    
    public void showCheckList() {
        if (!$(sCheckRow).isDisplayed()) {
            btnEditList.click();
        }
    }
    
    /**
     * @param type
     *             like warning...
     */
    public void selectOneEvent(String type) {
        logger.info("to find event: " + type);
        int iPos = 1;
        boolean bFind = false;
        for (String s : getEventType()) {
            if (s.equals(type)) {
                bFind = true;
                break;
            }
            iPos++;
        }
        
        assertTrue(bFind, "no event found");
        logger.info("find event at " + iPos);
        System.out.println(String.format(sCheckOneRow, sTable, iPos+1));
        click($(String.format(sCheckOneRow, sTable, iPos+1)), true);
        takess("selected one event");
    }
    
    public void deleteOneEvent(String type) {
        showCheckList();
        selectOneEvent(type);
        selectButton("2").click();
        sleep(10, "for refresh");
        MyCommonAPIs.waitReady();
    }
    
    /**
     * @param idx
     *                 0 - first event, 1 - second event, 2 - third event, ...
     */
    public void deleteOneEvent2(int idx) {
        showCheckList();
        notificationcheckbox(idx).click();
        selectButton("2").click();
        sleep(10, "for refresh");
        MyCommonAPIs.waitReady();
    }
    
    public void deleteAllEvent() {
        takess("delete All data");
        showCheckList();
        click(cbSelectAll, true);
        selectButton("2").click();
        sleep(10, "for refresh");
        MyCommonAPIs.waitReady();
    }
    
    public void selectSeverity(String alert) {
        for (SelenideElement se : $$(btnSeverity)) {
            if (WebportalParam.getNLocText(getText(se)).equals(alert)) {
                se.click();
                break;
            }
        }
    }
    
    public void selectLocation(String loc) {
        for (SelenideElement se : $$(btnLocation)) {
            if (getText(se).equals(loc)) {
                se.click();
                break;
            }
        }
    }
    
    public void Filter4Stability() {
        if (btnFilterNew.exists()) {
            btnFilterNew.click();
        } else {
            btnFilter.click();
        }
        // selectSeverity("Critical");
        selectLocation(WebportalParam.location1);
        selectLocation(WebportalParam.location2);
        if (btnApplyNew.exists()) {
            btnApplyNew.click();
        } else {
            btnApplyNew.click();
        }
        btnCritical.click();
    }
    
    // add by dallas.zhao 2019/12/27
    public void filterDropdown() {
        refresh();
        if (btnFilterNew.exists()) {
            btnFilterNew.click();
        } else {
            btnFilter.click();
        }
        MyCommonAPIs.sleepi(3);
    }
    
    public void selectDevice(String loc) {
        for (SelenideElement se : $$(btnDevice)) {
            if (getText(se).equals(loc)) {
                se.click();
                break;
            }
        }
        MyCommonAPIs.sleepi(3);
        if (btnApplyNew.exists()) {
            btnApplyNew.click();
        } else {
            btnApply.click();
        }
        MyCommonAPIs.sleepi(10);
    }
    
    public boolean checkDeviceTypeInList(String type) {
        boolean result = false;
        SelenideElement element = $x("//span[contains(text(),'" + type + "')]");
        SelenideElement elementNew = $x("//span[contains(text(),'" + type + "')]");
        if (element.exists()) {
            result = true;
            logger.info(type + " type existed.");
        } else if (elementNew.exists()) {
            result = true;
            logger.info(type + " type existed.");
        }
        return result;
    }
    
    public void makeCriticalEvent(boolean toNew, String status) {
        System.out.println("isAllTypeEventShown"+isAllTypeEventShown());
     
        if (toNew || isAllTypeEventShown()) {
            // make event, create an existed vlan and reboot will make warning, device disconnect will make critical & info
            if (status.equals("Disconnect")) {
                doSwitchCommandforDeviceDisconnect(1);
                refresh();
                new DevicesDashPage();
                waitReady();
                for(int i=0;i<8;i++) {
                    System.out.println("Inside for");
                    MyCommonAPIs.sleepi(60);
                    refresh();        
                    System.out.println("deviceStatusOnInsight"+ deviceStatusOnInsight);
                    if(deviceStatusOnInsight.getText().contains("Device is disconnected")) {
                      System.out.println("Device is disconnected via switch command");
                      break;
                }
                } 
                System.out.println("###################");
            }
            else if(status.equals("Connect")) {
                doSwitchCommandforDeviceDisconnect(0);         //status is Connected
                refresh();
                new DevicesDashPage();
                waitReady();
                for(int i=0;i<8;i++) {
                    MyCommonAPIs.sleepi(60);
                    refresh();
                    if(deviceStatusOnInsight.getText().contains("Connected")) {
                      System.out.println("Device is connected via switch command");
                        break;
                }
                }  
            }
                System.out.println("out of for loop");
            gotoPage();    //navigating to Notification page
            refresh();
            showAllEvent();
        }
       }
    
    public void makeInformationEvent(boolean toNew,String netName, int vlanType, String vlanName, String vlanId) {
        if (toNew || isAllTypeEventShown()) {
            // make event, create an vlan will make information, reboot will make waring & device disconnect will make Critical
//            doSwitchCommand(1);
            logger.info(netName + "/" + vlanType + "/" + vlanName + "/" + vlanId);
            new NetworkSetupPage().gotoPage();
            if (!(new NetworkSetupPage().getNetworks()).contains(netName)) {
                new NetworkSetupPage().clickAdd();
                new NetworkSetupPage().setNetwork1(netName, null, vlanType, vlanName, vlanId);
                new NetworkSetupPage().finishAllStep();
            }
            gotoPage();    //navigating to Notification page
            refresh();
            showAllEvent();
        }
    }

    public boolean notificationScheduleForSwitch(String Serialno, String Validation) {
        boolean result = false;
        if (NotificationText(Serialno).isDisplayed()) {
            String Notitext = NotificationText(Serialno).getText();
            System.out.println(Notitext);
            if (Notitext.contains(Validation)) {
                result = true;
            }
        }
        return result;
    }
}
