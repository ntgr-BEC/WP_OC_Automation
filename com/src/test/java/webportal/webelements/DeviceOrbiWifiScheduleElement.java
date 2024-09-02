package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.List;
import java.util.logging.Logger;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class DeviceOrbiWifiScheduleElement extends MyCommonAPIs {
    Logger logger = Logger.getLogger("DeviceOrbiWifiScheduleElement");

    public static SelenideElement addbutton = $x("//*[@class='icon-add']");
    public static String daysection = "(//div[@id='schedule'])[%s]";
    public static SelenideElement cancelbutton = $x("//*[@id='cancelBtnAddSch']");
    public static SelenideElement savebutton = $x("//*[@id='saveBtnAddSch']");
    public static SelenideElement schedulename = $x("//*[text()='Schedule Name']/following-sibling::input");
    public static String tableschedulename = "//h6[text()='%s']";
    public static SelenideElement deletebtn = $x("//button[text()='Delete']");
    public static SelenideElement deletewarningmsg = $x(
        "//p[contains(text(), 'A WiFi Network is associated with the schedule')]"
    );
    public static SelenideElement deletewarningOK = $x("//div[contains(@class, 'deleteSSIDError')]//button[text()='OK']");
    
    public static SelenideElement editwarningYes = $x("//div[contains(@class, 'editScheduleOrbi')]//button[text()='Yes']");
  
    
    
    public SelenideElement deleteIcon(String name) {
        return $x(String.format(tableschedulename, name) + "/../..//img[contains(@src, 'del')]");
    }
    
    public SelenideElement editIcon(String name) {
        return $x(String.format(tableschedulename, name) + "/../..//img[contains(@src, 'edit')]");
    }
    
    public SelenideElement scheduleName(String name) {
        return $x(String.format(tableschedulename, name));
    }
    
    public SelenideElement dayCircle(String day) {
        return $x(String.format(daysection, day) + "//span");
    }
    
    public SelenideElement baseContainer(String day) {
        return $x(String.format(daysection, day) + "//div[contains(@class,'baseContainer')]");
    }
    
    public SelenideElement dayTimePicker(String day, boolean start) {
        if (start) {
            return $x(String.format(daysection, day) + "//p[text()='Start Time']/following-sibling::div/div[contains(@class,'customtimepicker')]");
        } else {
            return $x(String.format(daysection, day) + "//p[text()='End Time']/following-sibling::div/div[contains(@class,'customtimepicker')]");
        }
        
    }
    
    public SelenideElement dayTime(String day, boolean start) {
        if (start) {
            SelenideElement ele1 = $x(String.format(daysection, day) + "//p[text()='Start Time']/following-sibling::div/p[text()='Select Time']");
            SelenideElement ele2 = $x(String.format(daysection, day) + "//p[text()='Start Time']/following-sibling::div/input");
            if(ele1.exists()) {
                return ele1;
            } else {
                return ele2;
            }
//            return $x(String.format(daysection, day) + "//p[text()='Start Time']/following-sibling::div/input");
        } else {
            
            SelenideElement ele1 = $x(String.format(daysection, day) + "//p[text()='End Time']/following-sibling::div/p[text()='Select Time']");
            SelenideElement ele2 = $x(String.format(daysection, day) + "//p[text()='End Time']/following-sibling::div/input");
            if(ele1.exists()) {
                return ele1;
            } else {
                return ele2;
            }
//            return $x(String.format(daysection, day) + "//p[text()='End Time']/following-sibling::div/input");
        }
        
    }
    
    public SelenideElement dayTimePickerOK(String day, boolean start) {
        if (start) {
            return $x(String.format(daysection, day) + "//p[text()='Start Time']/following-sibling::div//button");
        } else {
            return $x(String.format(daysection, day) + "//p[text()='End Time']/following-sibling::div//button");
        }
        
    }
    
    public SelenideElement dayTimePickerSelect(String day, boolean start) {
        if (start) {
            return $x(String.format(daysection, day) + "//p[text()='Start Time']/following-sibling::div//select");
        } else {
            return $x(String.format(daysection, day) + "//p[text()='End Time']/following-sibling::div//select");
        }
        
    }
    
    public SelenideElement dayTimePickerHourUp(String day, boolean start) {
        String xpath_up = "/following-sibling::div/div[contains(@class,'customtimepicker')]//*[text()='Hour']/following-sibling::div/i[contains(@class,'up')]";
        if (start) {
            return $x(String.format(daysection, day) + "//p[text()='Start Time']" + xpath_up);
        } else {
            return $x(String.format(daysection, day) + "//p[text()='End Time']" + xpath_up);
        }
    }
    
    public SelenideElement dayTimePickerMinuteUp(String day, boolean start) {
        String xpath_up = "/following-sibling::div/div[contains(@class,'customtimepicker')]//*[text()='Minute']/following-sibling::div/i[contains(@class,'up')]";
        if (start) {
            return $x(String.format(daysection, day) + "//p[text()='Start Time']" + xpath_up);
        } else {
            return $x(String.format(daysection, day) + "//p[text()='End Time']" + xpath_up);
        }
    }
    
    public SelenideElement dayTimePickerHourText(String day, boolean start) {
        String xpath_text = "/following-sibling::div/div[contains(@class,'customtimepicker')]//*[text()='Hour']/following-sibling::div[@class='texttime']";
        if (start) {
            return $x(String.format(daysection, day) + "//p[text()='Start Time']" + xpath_text);
        } else {
            return $x(String.format(daysection, day) + "//p[text()='End Time']" + xpath_text);
        }
    }
    
    public SelenideElement dayTimePickerMinuteText(String day, boolean start) {
        String xpath_text = "/following-sibling::div/div[contains(@class,'customtimepicker')]//*[text()='Minute']/following-sibling::div[@class='texttime']";
        if (start) {
            return $x(String.format(daysection, day) + "//p[text()='Start Time']" + xpath_text);
        } else {
            return $x(String.format(daysection, day) + "//p[text()='End Time']" + xpath_text);
        }
    }
}
