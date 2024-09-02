/**
 *
 */
package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import java.util.Map;
import java.util.logging.Logger;

import com.codeborne.selenide.Condition;

import util.BRUtils;
import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.URLParam;
import webportal.param.CommonDataType.SSIDData;
import webportal.publicstep.WebCheck;
import webportal.webelements.DeviceOrbiWifiScheduleElement;

/**
 * @author bingke.xue
 */
public class DevicesOrbiWifiSchedulePage extends DeviceOrbiWifiScheduleElement {
    Logger          logger;
    public String   sGuestWifiName   = "";
    public String   sGuestCPWifiName = "";
    public SSIDData ssidData         = null;
    
    public void initTestData() {
        ssidData = new CommonDataType().dataSSID;
    }

    /**
     *
     */
    public DevicesOrbiWifiSchedulePage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefOrbiWifiSchedules);
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
        initTestData();
//        $(wifiListTable).scrollIntoView(true);
    }
    
    public DevicesOrbiWifiSchedulePage(boolean nopage) {
        // TODO Auto-generated constructor stub
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
        initTestData();
    }
    
    public void gotoPage() {
        WebCheck.checkUrl(URLParam.hrefOrbiWifiSchedules);
//        $(wifiListTable).scrollIntoView(true);
    }
    
    public void clickAdd() {
        addbutton.click();
        MyCommonAPIs.waitReady();
        MyCommonAPIs.sleepi(1);
    }
    
    public void clickCancel() {
        cancelbutton.click();
        MyCommonAPIs.waitReady();
        MyCommonAPIs.sleepi(1);
    }
    
    public void clickSave() {
        savebutton.click();
        MyCommonAPIs.waitReady();
        MyCommonAPIs.sleepi(5);
    }
    
    public void selectDayCircle(String day, boolean select) {
        boolean container_exist = baseContainer(day).exists();
        if(select != container_exist) {
            dayCircle(day).click();
            MyCommonAPIs.sleepi(1);
        }
    }
    
    public void expandDayTimePicker(String day, boolean start) {
        if(!dayTimePicker(day, start).exists()) {
            dayTime(day, start).click();
            MyCommonAPIs.sleepi(1);
        }
    }
    
    public void clickDayTimePickerOK(String day, boolean start) {
        dayTimePickerOK(day, start).click();
        MyCommonAPIs.sleepi(1);
    }
    
    public void setDayTime(String day, boolean start) {
        dayTimePickerOK(day, start).click();
        MyCommonAPIs.sleepi(1);
    }
    
    public void setScheduleName(String name) {
        schedulename.setValue(name);
        MyCommonAPIs.sleepi(1);
    }
    
    public void selectAmPm(String day, String ampm, boolean start) {
        dayTimePickerSelect(day, start).selectOptionByValue(ampm);
        MyCommonAPIs.sleepi(1);
    }
    
    public void setDayMinute(String day, boolean start, String min) {
        System.out.println("setDayMinute");
        for(int i =0; i<60; i++) {
            System.out.println(dayTimePickerMinuteText(day, start).innerText());
            if(dayTimePickerMinuteText(day, start).innerText().equals(min)) {
                break;
            }else {
                dayTimePickerMinuteUp(day, start).click();
                MyCommonAPIs.sleep(250);
            }
        }
        MyCommonAPIs.sleepi(1);
    }
    
    public void setDayHour(String day, boolean start, String hr) {
        System.out.println("setDayHour");
        for(int i =0; i<12; i++) {
            System.out.println(dayTimePickerHourText(day, start).innerText());
            if(dayTimePickerHourText(day, start).innerText().equals(hr)) {
                break;
            }else {
                dayTimePickerHourUp(day, start).click();
                MyCommonAPIs.sleep(250);
            }
        }
        MyCommonAPIs.sleepi(1);
    }
    
    public boolean deleteUsedWifiSchedule(String name) {
        boolean result = false;
        deleteIcon(name).click();
        deletebtn.click();
        MyCommonAPIs.sleepi(5);
        if (deletewarningmsg.isDisplayed()) {
            System.out.println("!!!!!!!warning msg displayed!!!!!!!!");
            result = true;
            deletewarningOK.click();
        }
        return result;
    }
    
    public void deleteWifiSchedule(String name) {
        deleteIcon(name).click();
        deletebtn.click();
        MyCommonAPIs.sleepi(5);
    }
    
    public void editWifiSchedule(String name) {
        editIcon(name).click();
        editwarningYes.click();
        MyCommonAPIs.sleepi(2);
    }
    
    public boolean checkWifiScheduleExists(String name) {
        boolean result = false;
        if(scheduleName(name).exists()) {
            result = true;
        }
        return result;
    }
    
}
