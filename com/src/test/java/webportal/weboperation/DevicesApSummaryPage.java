package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.openqa.selenium.interactions.Actions;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DevicesApSummaryPageElement;

public class DevicesApSummaryPage extends DevicesApSummaryPageElement {
    String s2 = "100.8/5.24GHz";
    String s3 = "100/2.472GHz";
    final static Logger logger = Logger.getLogger("DevicesApSummaryPage");

    /**
     *
     */
    public DevicesApSummaryPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefDevicesApSummary);
        MyCommonAPIs.sleep(5 * 1000);
        logger.info("init...");
    }
    
    public DevicesApSummaryPage(boolean noPage) {
        // TODO Auto-generated constructor stub
        logger.info("init...");
    }

    public void clickReboot() {
        logger.info("Click reboot button.");
        Dropdown.click();
        MyCommonAPIs.sleepi(10);
        reboot.click();
        MyCommonAPIs.sleepi(30);
		waitElement(rebootconfirm);
        rebootconfirm.click();
        MyCommonAPIs.sleepi(10);
        getPageErrorMsg();
        if ($x("//div[text()='Waiting for additional details from the Device. Refresh to see the device details.']").exists()) {
            refresh();
            MyCommonAPIs.sleepsync();
            reboot.click();
            MyCommonAPIs.sleepi(3);
            rebootconfirm.click();
            MyCommonAPIs.sleepi(5);
        }
        // MyCommonAPIs.sleepsync();
    }

    public void clickReset() {
        logger.info("Click reset button.");
        WebCheck.checkHrefIcon(URLParam.hrefDevicesApSummary);
        reset.click();
        getPageErrorMsg();
        MyCommonAPIs.sleepi(3);
        if (resetconfirmnew.exists()) {
            resetconfirmnew.click();
        } else {
            resetconfirm.click();
        }
        MyCommonAPIs.sleepi(3);
    }

    public void shareEmail(String email) {
        logger.info("Click share button.");
        WebCheck.checkHrefIcon(URLParam.hrefDevicesApSummary);
        share.click();
        MyCommonAPIs.sleepi(5);
        shareemail.sendKeys(email);
        send.click();
        MyCommonAPIs.sleepi(2);
    }
    
    // Added By Shoib
    public void shareEmailBR(String email) {
        logger.info("Click share button.");
        WebCheck.checkHrefIcon(URLParam.hrefDevicesBRSummary);
        share.click();
        MyCommonAPIs.sleepi(3);
        shareemail.sendKeys(email);
        send.click();
        MyCommonAPIs.sleepi(3);
    }

    public void editName(String Name) {
        logger.info("Edit ap name.");
        WebCheck.checkHrefIcon(URLParam.hrefDevicesApSummary);
        name.click();
        MyCommonAPIs.sleepi(3);
        editname.clear();
        editname.sendKeys(Name);
        edityes.click();
        MyCommonAPIs.sleepi(3);
        logger.info("Edit ap name successful.");
    }

    public boolean checkAlertIsExist() {
        logger.info("Check alert is exist.");
        boolean result = false;
        if (alerttext.exists()) {
            if (alerttext.getText().equals("Your configuration has been applied. It may take some time to reflect")) {
                logger.info("Alert is existed.");
                result = true;
            } else if (alerttext.getText().equals("Diagnostic logs shared successfully")) {
                logger.info("Alert is existed.");
                result = true;
            }
        }
        return result;
    }

    public DevicesApPortConfiqSummaryPage enterPortConfigSummary(String port) {
        portChoice(port).click();
        return new DevicesApPortConfiqSummaryPage();
    }

    public void selectChannleUtilizationChart(String option) {
        selectChannelChart.selectOption(option);
        MyCommonAPIs.sleepi(3);
    }
    
    public boolean etractValuesFromChannelsfor24GHZ() {
        boolean result = false;
        MyCommonAPIs.sleepi(15);
        waitElement(radioAndChannelsOp);
        radioAndChannelsOp.click();
        MyCommonAPIs.sleepi(15);
        waitElement(channelChart24GHz);
        channelChart24GHz.click();
        MyCommonAPIs.sleepi(1);
        List<SelenideElement> options = channelChart24GHz.$$("option");   
        List<String> values = new ArrayList<>();
        for (SelenideElement option : options) {
            values.add(option.getText());
            System.out.println(option);
        }
        logger.info("All Channnels are shown at output for 2.4Ghz Band");
        for (String value : values) {
            System.out.println(value);
            String s1 = $x("//option[text()='13/2.472GHz']").getText();
            if (value.contains(s1)) {
                result = true;
                logger.info("One of the channel from 2.4 channels list is verified there or not");
                break;
            }
            
        }
        
        for (String value: values) {
            System.out.println(value);
            if ((value.contains(s3))!=true) {
                result = true;
                logger.info(s2+" this channel is not part of 2.4 GHz channel list");
            }
        }
        
        return result;
    }
    
    
    
    public boolean selectChannelsvaluefor24GHZ(Map<String, String> map) {
        boolean result = false;
        boolean result1 = false;
        boolean returnResult = false;
        MyCommonAPIs.sleepi(15);
        new WirelessQuickViewPage(false).RadioAndChannels.click();
        MyCommonAPIs.sleepi(15);
        waitElement(channelChart24GHz);
        List<SelenideElement> options = channelChart24GHz.$$("option");   
        List<String> values = new ArrayList<>();
        for (SelenideElement option : options) {
            values.add(option.getText());
            String s3 = option.getText();
            System.out.println(s3);
            MyCommonAPIs.sleepi(1);
        }
        for (String value : values) {
            System.out.println(value);  
            if (value.contains((map.get("Channel")))) {
                MyCommonAPIs.sleepi(1);
                System.out.println("Entered if loop");
                channelChart24GHz.selectOption(map.get("Channel"));
                MyCommonAPIs.sleepi(1);
                saveBtn.click();
                MyCommonAPIs.sleepi(1);
                if(successMessage.exists()) {
                    logger.info("Channel is selected and saved successfully.");
                    result = true;
                    break;
                }
            }
        }
        logger.info("All Channnels are shown at output for 2.4Ghz Band");
        for (String value: values) {
            System.out.println(value);
            if ((value.contains(s3))!=true) {
                result1 = true;
                logger.info(s2+" this channel is not part of 2.4 GHz channel list");
            }
        }   
        returnResult = ( result && result1 );
        return returnResult;
    }
   
    
    public boolean selectChannelsvaluefor5GHZ(Map<String, String> map) {
        boolean result = false;
        boolean result1 = false;
        boolean returnResult = false;
        MyCommonAPIs.sleepi(5);
        new WirelessQuickViewPage(false).RadioAndChannels.click();
        MyCommonAPIs.sleepi(5);
        select24Ghz.click();
        MyCommonAPIs.sleepi(2);
       // select5Ghz.scrollTo();
        select50Ghz.click();
       //new WirelessQuickViewPage(false).DropDown5GhzLowWireless.click();   
        MyCommonAPIs.sleepi(1);
        
        waitElement(channelChart5GHz);
        List<SelenideElement> options = channelChart5GHz.$$("option");   
        List<String> values = new ArrayList<>();
        for (SelenideElement option : options) {
            values.add(option.getText());
            String s3 = option.getText();
            System.out.println(s3);
            MyCommonAPIs.sleepi(1);
        }
        for (String value : values) {
            System.out.println(value);  
            if (value.contains((map.get("Channel")))) {
                MyCommonAPIs.sleepi(1);
                System.out.println("Entered if loop");
                channelChart5GHz.selectOption(map.get("Channel"));
                MyCommonAPIs.sleepi(1);
                saveBtn.click();
                MyCommonAPIs.sleepi(1);
                if(successMessage.exists()) {
                    logger.info("Channel is selected and saved successfully.");
                    result = true;
                    break;
                }
            }
        }
        logger.info("All Channnels are shown at output for 5Ghz Band");
        for (String value: values) {
            System.out.println(value);
            if ((value.contains(s3))!=true) {
                result1 = true;
                logger.info(s2+" this channel is not part of 5 GHz channel list");
            }
        }   
        returnResult = ( result && result1 );
        return returnResult;
    }
    
    
    public boolean etractValuesFromChannelsfor5GHZ() {
        boolean result = false;
        MyCommonAPIs.sleepi(15);
        waitElement(radioAndChannelsOp);
        radioAndChannelsOp.click();
        MyCommonAPIs.sleepi(15);
        waitElement(select5Ghz);
        select5Ghz.click();
        MyCommonAPIs.sleepi(1);
        List<SelenideElement> options = channelChart5GHz.$$("option");   
        List<String> values = new ArrayList<>();
        for (SelenideElement option : options) {
            values.add(option.getAttribute("value"));
            System.out.println(option);
        }
        logger.info("All Channnels are shown at output for 5Ghz Band");
        
        for (String value : values) {
            System.out.println(value);
            String s1 = $x("//option[text()='48/5.24GHz']").getText();
            if (value.contains(s1)) {
                result = true;
                logger.info("One of the channel from 5 channels list is verified there or not");
                break;
            }
        }
        
        for (String value: values) {
            System.out.println(value);
            if ((value.contains(s2))!=true) {
                result = true;
                logger.info(s2+" this channel is not part of 5 GHz channel list");
            }
        }
        
        return result;
    }
}
