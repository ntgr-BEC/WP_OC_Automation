package businessrouter.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

public class BrSecurityScheduleElements {
    public SelenideElement scheduleeverydayenable      = $("#FW_schedule_every");
    public SelenideElement scheduleweeklyday           = $x(
            "//*[@id=\"root\"]/div/div/div[2]/div/div[2]/div/div/div/div/div/div/div/div[3]/div[2]/div/label[#ex#]/span[1]/input");
    public SelenideElement schedulealldaytime          = $("#FW_schedule_AllDay");
    public SelenideElement scheduleblockstarthour      = $("#FW_schedule_startH");
    public SelenideElement scheduleblockstartmin       = $("#FW_schedule_startM");
    public SelenideElement scheduleblockendhour        = $("#FW_schedule_endH");
    public SelenideElement scheduleblockendmin         = $("#FW_schedule_endM");
    public SelenideElement scheduletimezone            = $x(
            "//*[@id=\"FW_schedule_autotime\"]/../../..//div[@class=\"ant-select-selection-selected-value\"]");
    public SelenideElement scheduledaylightenable      = $("#FW_schedule_autotime");
    public SelenideElement schedulecurrenttime         = $x("//div[@class=\"ant-col-24\"]/div[3]/b");
    public SelenideElement schedulecancelbutton        = $("#FW_schedule_cancel");
    public SelenideElement scheduleapplybutton         = $("#FW_schedule_apply");
    public SelenideElement scheduledialog              = $x("//div[@class=\"ant-message\"]/span");
    public SelenideElement scheduleeverydaychecked     = $x("//*[@id=\"FW_schedule_every\"]/..");
    public SelenideElement schedulealldaychecked       = $x("//*[@id=\"FW_schedule_AllDay\"]/..");
    public SelenideElement scheduledaylightenablecheck = $x("//div[@class=\"ant-col-24\"]//label/span[1]");

    public void weekcheckboxselect(String index, String condition) {
        String[] week = {
                "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
        };
        for (int i = 0; i < 7; i++) {
            String xpathoption = "//*[@id=\"FW_schedule_every\"]/../../../div[@class=\"ant-checkbox-group\"]/label[" + String.valueOf(i + 1)
                    + "]//input";
            String xpathtext = "//*[@id=\"FW_schedule_every\"]/../../../div[@class=\"ant-checkbox-group\"]/label[" + String.valueOf(i + 1) + "]";
            SelenideElement checkboxtext = $x(xpathtext);
            SelenideElement checkboxselect = $x(xpathoption);
            if (!checkboxtext.getText().equals(index) && condition.equals("today")) {
                checkboxselect.selectRadio(week[i]);
                MyCommonAPIs.sleepi(3);
            } else if (checkboxtext.getText().equals(index) && condition.equals("other")) {
                checkboxselect.selectRadio(week[i]);
                MyCommonAPIs.sleepi(3);
            }
        }
    }
    
    public void timezoneselect(String typetext) {
        String xpath = "//ul[@class=\"ant-select-dropdown-menu  ant-select-dropdown-menu-root ant-select-dropdown-menu-vertical\"]/li";
        for (int i = 1; i < 46; i++) {
            SelenideElement serviceblockingservicetypeselect = $x(xpath + "[" + String.valueOf(i) + "]");
            if (serviceblockingservicetypeselect.getText().indexOf(typetext) != -1) {
                serviceblockingservicetypeselect.click();
                break;
            }
        }
    }
}
