/**
 *
 */
package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import util.APUtils;
import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.webelements.DeviceScreenNavigationElement;
import webportal.webelements.FirmwareElement;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Lavi
 */
public class FirmwarePage extends FirmwareElement {
    /**
     *
     */
    
    final static Logger logger = Logger.getLogger("FirmwarePage");
    
    public FirmwarePage() {
        // TODO Auto-generated constructor stub
        logger.info("init...");
    }
    
    public void gotoFirmwarePage() {
        logger.info(String.format("..."));
        open(URLParam.hrefFirmware, true);
        waitReady(); // for checkbox status
    }
    
    /**
     * @param  timeout
     * @return         true for device is updated, false to timeout or failed
     *                 TODO:
     *                 1. Find a short way to know that schedule is not happened as expected
     *                 2. Find a way to know that firmware updating is failed 4ever
     */
    public boolean isUpdateDone(int timeout) {
        boolean isOk = false;
        timerStart(timeout);
        int seeUpdates = 0;
        while (timerRunning()) {
            logger.info("Current online devices count: " + $$(sUptoDateRow).size());
            if (!deviceHasUpdate()) {
                logger.info("all devices are updated");
                isOk = true;
                break;
            }

            if (txtUpdatesAvailable.exists()) {
                logger.info("Number of available fw: " + txtUpdatesAvailable.getText());
                seeUpdates++;
            } else {
                seeUpdates = 0;
            }

            if (seeUpdates > 5) {
                logger.info("failed to do updated because event is dismissed more than many times");
                break;
            }

            try {
                while (true) {
                    if (txtUpdateStatus.exists()) {
                        String sStatus = WebportalParam.getNLocText(txtUpdateStatus.getText());
                        // stuck here to reproduce issue
                        if (sStatus.toLowerCase().contains("queue")) {
                            sleep(60 * 5, "isUpdateDone - wait for user");
                            continue;
                        } else {
                            if (sStatus.length() > 1) {
                                logger.info("current update status: " + sStatus);
                                if (txtUpdateProgress.exists()) {
                                    logger.info("current update progress " + txtUpdateProgress.getAttribute("style"));
                                }
                            }
                        }
                    }
                    break;
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }

            refresh();
            sleep(30, "isUpdateDone - wait for refresh");
        }
        return isOk;
    }
    
    /**
     * @return wait up to 15m for update button, true for found, false for not
     */
    public boolean waitForUpdateAvailable() {
        logger.info("checking start");
        timerStart(15 * 60);
        boolean noFound = false;
        int trycount = 10;
        while (timerRunning() && (trycount > 0)) {
            if (btnUpdate.isDisplayed()) {
                if (txtUpdatesAvailable.exists()) {
                    logger.info("number of updates available: " + getText(txtUpdatesAvailable));
                }
                noFound = true;
                break;
            }
            
            // if all devices are up to date, return now
            if (!deviceHasUpdate() && !$(sOfflineRow).exists()) {
                logger.info("no device need to update");
                noFound = false;
                trycount--; // PRJCBUGEN-23605
            }
            
            refresh();
            sleep(30, "waitForUpdateAvailable - wait for refresh");
        }
        if (!noFound) {
            logger.info("checking timeout");
            takess();
        }
        return noFound;
    }
    
    /**
     * @return wait up to 40m for update done via automatically, true for found, false for not
     */
    public boolean waitForAutoUpdateDone() {
        logger.info("start");
        boolean checked = isUpdateDone(40 * 60); // schedule could be 2m after
        if (checked) {
            sleep(120, "waitForAutoUpdateDone - wait for device reboot after updated");
        } else {
            logger.info("checking timeout or failed");
            takess();
        }
        return checked;
    }
    
    /**
     * wait up to 35m for update done status
     *
     * @param  updateAll
     *                   true to 2*times of timeout
     * @return           true for found, false for not
     */
    public boolean doUpdate(boolean updateAll) {
        boolean checked = false;
        for (int i = 0; i < 2; i++) {
            sleep(20, "doUpdate - wait for status to be updated - PRJCBUGEN-23605");
            if (btnUpdateAll.isDisplayed()) {
                break;
            }
            refresh();
        }
        if (btnUpdateAll.isDisplayed()) {
            btnUpdateAll.click();
            waitReady();
            btnYesAll.click();
            MyCommonAPIs.sleepi(30);
            waitReady();
            int tomore = 1;
            if (updateAll) {
                tomore = 2;
            }
            checked = isUpdateDone(tomore * 36 * 60);
            if (checked) {
                sleep(900, "doUpdate - wait for device reboot after updated");
            } else {
                logger.info("checking timeout or failed");
                takess();
            }
        } else {
            logger.info("no update need");
        }
        return checked;
    }
    
    
    public boolean Schedule(boolean updateAll) {
        boolean checked = false;
        for (int i = 0; i < 2; i++) {
            sleep(20, "doUpdate - wait for status to be updated - PRJCBUGEN-23605");
            if (btnUpdateAll.isDisplayed()) {
                break;
            }
            refresh();
        }
        
        String s = new APUtils(WebportalParam.ap1IPaddress).getDate();                     
        DateTimeFormatter d=DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss z yyyy");
        LocalDateTime Time = LocalDateTime.parse(s, d);
        int day = Time.getDayOfMonth();
        int hours = Time.getHour();
        int minutes = Time.getMinute();

        int beforehours = hours;
        int beforeminutes = minutes+2;
        int afterhours = hours+1;
        int afterminutes = beforeminutes+1;
        if (btnUpdateAll.isDisplayed()) {
            setScheduleUpdate(beforehours, beforeminutes,afterhours,afterminutes);
        } else {
            logger.info("no update need");
        }
        return checked;
    }
    
    public boolean ManualUpdate(boolean updateAll) {
        System.out.println("Manual update"); 
        boolean checked = false;
        for (int i = 0; i < 2; i++) {
            sleep(20, "doUpdate - wait for status to be updated - PRJCBUGEN-23605");
            if (btnUpdate.isDisplayed()) {
                break;
            }
            refresh();
        }
        if (btnUpdate.isDisplayed()) {
            btnUpdate.click();
            waitReady();
            btnYes.click();
            MyCommonAPIs.sleepi(30);
            waitReady();
            int tomore = 1;
            if (updateAll) {
                tomore = 2;
            }
            checked = isUpdateDone(tomore * 36 * 60);
            if (checked) {
                sleep(900, "doUpdate - wait for device reboot after updated");
            } else {
                logger.info("checking timeout or failed");
                takess();
            }
        } else {
            logger.info("no update need");
        }
        return checked;
    }
    
    public void setScheduleUpdate(int beforehours, int beforeminutes, int afterhours, int afterminutes) {
        // enable schedule update
        spanEnable.click();
        // pick start date (select today)
        divStartDate.click();
        today.click();
        // pick start time (use default : current time)
        divStartTime.click();
        
        String  exitinghr = Exhourup.getText();
        int convertedhr = Integer.parseInt(exitinghr);  
        String  exitingmin = Exminuteup.getText();
        int convertedmin = Integer.parseInt(exitingmin);  
        
        while(beforehours!=convertedhr)
        {
            hourup.click();
            exitinghr = Exhourup.getText();
            convertedhr = Integer.parseInt(exitinghr); 
        }
        
        while(beforeminutes!=convertedmin)
        {
            hourup.click();
            exitingmin = Exminuteup.getText();
            convertedmin = Integer.parseInt(exitingmin);  
        }
             
        btnOK.click();
        // pick end date (select today)
        divEndDate.click();
        today.click();
        // pick end time (use current time + n mins)
        divEndTime.click();
        
        while(beforehours!=convertedhr)
        {
            hourup.click();
            exitinghr = Exhourup.getText();
            convertedhr = Integer.parseInt(exitinghr); 
        }
        
        int convertedminnew =beforeminutes+1;
        
        while(convertedminnew!=convertedmin)
        {
            hourup.click();
            exitingmin = Exminuteup.getText();
            convertedmin = Integer.parseInt(exitingmin);  
        }
//        for(int i =0; i<afterhours; i++) {
//            hourup.click();
//        }
//        for(int i =0; i<afterminutes; i++) {
//            minuteup.click();
//        }
        btnOK.click();
        // click save
        btnSave.click();
    }
    

    public boolean ApModelsInTargetFirmware() {
        boolean result=false;
        setTargetFW.click();
        MyCommonAPIs.sleepi(10);    
        String collections = table.getText();
        System.out.println(collections);
        if (collections.contains("Model") && collections.contains("Select Target Firmware") && collections.contains("WAC505") && collections.contains("WAC510") && collections.contains("WAC540")
                && collections.contains("WAC564") && collections.contains("WAC505") && collections.contains("WAX608Y") && collections.contains("WAX610") && collections.contains("WAX610Y")
                && collections.contains("WAX615") && collections.contains("WAX618") && collections.contains("WAX620") && collections.contains("WAX625") && collections.contains("WAX628")
                && collections.contains("WAX630") && collections.contains("WAX630E") && collections.contains("WAX638E") && collections.contains("WBE710") && collections.contains("WBE718")
                && collections.contains("WBE750") && collections.contains("WBE758")) {
                result = true;
                logger.info("All the AP models are present in the set target Fw page");
            
        }
        return result;
        }

    public boolean elementsInFirmwarePage() {
        boolean result=false;
        MyCommonAPIs.sleepi(5); 
        System.out.println(setTargetFW.isDisplayed());
        System.out.println(UpdatesAvailabletext.isDisplayed());
        System.out.println(NoUpdatesAvailabletext.isDisplayed());
        System.out.println(offline.isDisplayed());
        System.out.println(ScheduleUpdatetext.isDisplayed());
       
        
        if (setTargetFW.isDisplayed() && UpdatesAvailabletext.isDisplayed() && NoUpdatesAvailabletext.isDisplayed() && offline.isDisplayed()
                && ScheduleUpdatetext.isDisplayed()) {
            result = true;
            logger.info("All the elemnts in the FW page are correct");
            
        }
        return result;
        }

    public boolean elementsInUpdateTable(String serialNo, String model, String version) {
        boolean result=false;   
        MyCommonAPIs.sleepi(4); 
        System.out.println(btnUpdateAll.isDisplayed());
        System.out.println(btnUpdate.isDisplayed());
        
        System.out.println(elemntsInUpdateAvailableTable.getText().contains("Name"));
        System.out.println(elemntsInUpdateAvailableTable.getText().contains("Current Version"));
        System.out.println(elemntsInUpdateAvailableTable.getText().contains("Model"));
        
        System.out.println(APserialNo.getText().equals(serialNo));
        System.out.println(APcurrrentVersion.getText().equals(version));
        System.out.println(APmodel.getText().contains(model));
        
        boolean a= elemntsInUpdateAvailableTable.getText().contains("Name");
        boolean b= elemntsInUpdateAvailableTable.getText().contains("Current Version");
        boolean c =elemntsInUpdateAvailableTable.getText().contains("Model");
        boolean d =elemntsInUpdateAvailableTable.getText().contains("Target Version");
        boolean e = APserialNo.getText().equals(serialNo);
        boolean f = APcurrrentVersion.getText().equals(version);
        boolean g = APmodel.getText().contains(model);
        if (btnUpdateAll.isDisplayed() && btnUpdate.isDisplayed() && a==true && b==true && c==true && d==true && e==true && f==true && g==true ) {
            result= true;
            
        }
        return result;
}

    public boolean Autoupdate(boolean updateAll) {
        System.out.println("Autoupdate"); 
        boolean checked = false;
        for (int i = 0; i < 2; i++) {
            sleep(20, "doUpdate - wait for status to be updated - PRJCBUGEN-23605");
            if (btnUpdateAll.isDisplayed()) {
                break;
            }
            refresh();
        }
        
        if (btnUpdateAll.isDisplayed()) {
            new WirelessQuickViewPage().deleteDeviceYes(WebportalParam.ap1serialNo);
            MyCommonAPIs.sleepi(20);
            Map<String, String> devInfo = new HashMap<String, String>();
            devInfo.put("Serial Number", WebportalParam.ap1serialNo);
            devInfo.put("MAC Address1", WebportalParam.ap1macaddress);
            new DevicesDashPage(false).addNewDevice(devInfo);
            
        } else {
            logger.info("no update need");
        }
        int tomore = 1;
        checked = isUpdateDone(tomore * 36 * 60);
        if (checked) {
            sleep(900, "doUpdate - wait for device reboot after updated");
        } else {
            logger.info("checking timeout or failed");
            takess();
        }        
        return checked;
    }
     
    public void setTargetFW() {
        boolean result=false;   
        setTargetFW.click();
        MyCommonAPIs.sleepi(4); 
        SelectTargetFW(WebportalParam.ap1Model,WebportalParam.ap1TargetFirmware).click();
        MyCommonAPIs.sleepi(3); 
        ApplyTargetFW.click();
        MyCommonAPIs.sleepi(3); 
        if (APtargetVersion.getText().equals(WebportalParam.ap1TargetFirmware)) {
           logger.info("Target firmware is set");
        }
      }
   
    
    public void setScheduleUpdatenew(int beforehours, int beforeminutes, int afterhours, int afterminutes) {
        
        int newbeforehours = beforehours;
        spanEnable.click();
        MyCommonAPIs.sleepi(5);
        divStartDate.click();
        MyCommonAPIs.sleepi(5);
        today.click();
        // pick start time (use default : current time)
        MyCommonAPIs.sleepi(5);
        divStartTime.click();
        MyCommonAPIs.sleepi(5);
        
        String  exitinghr = Exhourup.getText();
        int convertedhr = Integer.parseInt(exitinghr);  
        String  exitingmin = Exminuteup.getText();
        int convertedmin = Integer.parseInt(exitingmin);  
       
        if(beforehours>12) {
             newbeforehours = beforehours-12;             
        }
        if(beforehours==0) {
            
            newbeforehours = 12;
        }
              
        System.out.println(newbeforehours);
        System.out.println(convertedhr);
        
        
        
        while(newbeforehours!=convertedhr)
        {
            System.out.println("eneterd while loop for start Hr");
            hourup.click();            
            exitinghr = Exhourup.getText();
            convertedhr = Integer.parseInt(exitinghr); 
           
        }
        
        
        
        while(beforeminutes!=convertedmin)
        {
            System.out.println("eneterd while loop for start min");
            minuteup.click();
            exitingmin = Exminuteup.getText();
            convertedmin = Integer.parseInt(exitingmin);  
        }
        
        
        
        if(beforehours>12) {
            Amselect.selectOption("p.m.");         
       } else {
           Amselect.selectOption("a.m.");
       }if(beforehours==00) {
           Amselect.selectOption("a.m.");
       }
        btnOK.click();
        MyCommonAPIs.sleepi(5);
        divEndDate.click();
        today.click();
        MyCommonAPIs.sleepi(5);
        divEndTime.click();
        MyCommonAPIs.sleepi(5);
        
        
        while(newbeforehours!=convertedhr)
        {
            System.out.println("eneterd while loop for end hr");
            hourup.click();
            exitinghr = Exhourup.getText();
            convertedhr = Integer.parseInt(exitinghr); 
        }
        
        int convertedminnew =beforeminutes+1;
        
        while(convertedminnew!=convertedmin)
        {
            System.out.println("eneterd while loop for end min");
            minuteup.click();
            exitingmin = Exminuteup.getText();
            convertedmin = Integer.parseInt(exitingmin);  
        }
        if(beforehours>12) {
            Amselect.selectOption("p.m.");         
       } else {
           Amselect.selectOption("a.m.");
       }
        btnOK.click();
        MyCommonAPIs.sleepi(10);
        btnSave.click();
    }
    
    
    public boolean TargetFWonFWPage() {
        boolean result=false;   
        setTargetFW.click();
        MyCommonAPIs.sleepi(4); 
        SelectTargetFW(WebportalParam.ap1Model,WebportalParam.ap1TargetFirmware).click();
        MyCommonAPIs.sleepi(3); 
        ApplyTargetFW.click();
        MyCommonAPIs.sleepi(3); 
        if (APtargetVersion.getText().equals(WebportalParam.ap1TargetFirmware) && targetFWsetSuccessMsg.getText().equals("Target firmware details saved successfully.")) {
            result=true;
           logger.info("Target firmware is set");
           
        }
       return result;
    }
    
    
}
    
    
    

