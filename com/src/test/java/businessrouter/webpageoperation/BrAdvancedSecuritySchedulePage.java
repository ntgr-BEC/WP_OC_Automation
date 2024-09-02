package businessrouter.webpageoperation;

import java.util.Calendar;
import java.util.logging.Logger;

import businessrouter.webelements.BrAllMenueElements;
import businessrouter.webelements.BrSecurityScheduleElements;
//import webportal.weboperation.MyCommonAPIs;
import util.MyCommonAPIs;

public class BrAdvancedSecuritySchedulePage extends BrSecurityScheduleElements {
    final static Logger logger = Logger.getLogger("BrAdvancedSecuritySchedulePage");

    public void OpenSecuritySchedulePage() {
        BrAllMenueElements BrAllMenueElements = new BrAllMenueElements();
        logger.info("Open Security Schedule page");
        BrAllMenueElements.advanced.click();
        MyCommonAPIs.sleepi(5);
        BrAllMenueElements.Security.click();
        MyCommonAPIs.sleepi(5);
        BrAllMenueElements.Schedule.click();
    }

    public void checkboxChecked(String selected) {
        logger.info("Check checkbox.");
        if (selected.equals("Every Day")) {
            if (scheduleeverydaychecked.getAttribute("class").equals("ant-checkbox ant-checkbox-checked")) {
                logger.info("Disable every day checkbox.");
                scheduleeverydayenable.selectRadio("on");
            }
        } else if (selected.equals("All Day")) {
            if (schedulealldaychecked.getAttribute("class").equals("ant-checkbox ant-checkbox-checked")) {
                logger.info("Disable all day checkbox.");
                schedulealldaytime.selectRadio("on");
            }
        }
    }

    public void checkboxnotchecked(String selected) {
        logger.info("Check checkbox.");
        if (selected.equals("Every Day")) {
            if (scheduleeverydaychecked.getAttribute("class").equals("ant-checkbox")) {
                logger.info("Enable every day checkbox.");
                scheduleeverydayenable.selectRadio("on");
            }
        } else if (selected.equals("All Day")) {
            if (schedulealldaychecked.getAttribute("class").equals("ant-checkbox")) {
                logger.info("Enable all day checkbox.");
                schedulealldaytime.selectRadio("on");
            }
        }

    }

    public boolean checkAllDayadnEveryDay() {
        boolean result;
        // Selenide.refresh();
        MyCommonAPIs.sleepi(10);
        logger.info("Check AllDay and EveryDay.");
        if (scheduleeverydaychecked.getAttribute("class").equals("ant-checkbox ant-checkbox-checked")
                && schedulealldaychecked.getAttribute("class").equals("ant-checkbox ant-checkbox-checked")) {
            logger.info("All Day and Every Day was checked.");
            result = true;
        } else {
            logger.info("All Day and Every Day not checked.");
            result = false;
        }
        return result;
    }

    public void selectAllDayandEveryDay() {
        logger.info("Select AllDay and EveryDay.");
        checkboxnotchecked("All Day");
        checkboxnotchecked("Every Day");
        MyCommonAPIs.sleepi(5);
        scheduleapplybutton.click();
    }

    public void enabledaylight() {
        logger.info("Day light enable.");
        if (scheduledaylightenablecheck.getAttribute("class").equals("ant-checkbox")) {
            scheduledaylightenable.selectRadio("on");
            MyCommonAPIs.sleepi(3);
            scheduleapplybutton.click();
            MyCommonAPIs.sleepi(3);
        }
    }

    public void disabledaylight() {
        logger.info("Day light disable.");
        if (scheduledaylightenablecheck.getAttribute("class").equals("ant-checkbox ant-checkbox-checked")) {
            scheduledaylightenable.selectRadio("on");
            MyCommonAPIs.sleepi(3);
            scheduleapplybutton.click();
            MyCommonAPIs.sleepi(3);
        }
    }

    // public String getCurrentDate() {
    // logger.info("Get current date.");
    // String year = schedulecurrenttime.getText().substring(schedulecurrenttime.getText().length() - 13,
    // schedulecurrenttime.getText().length() - 9);
    // String month = schedulecurrenttime.getText().substring(schedulecurrenttime.getText().length() - 13,
    // schedulecurrenttime.getText().length() - 9);
    // String day = schedulecurrenttime.getText().substring(schedulecurrenttime.getText().length() - 17,
    // schedulecurrenttime.getText().length() - 15);
    // return day;
    // }

    public String getCurrentTime() {
        logger.info("Get current time.");
        String hour = schedulecurrenttime.getText().substring(schedulecurrenttime.getText().length() - 8,
                schedulecurrenttime.getText().length() - 6);
        return hour;
    }

    public String getCurrentTimeMin() {
        logger.info("Get current time minutes.");
        String minute = schedulecurrenttime.getText().substring(schedulecurrenttime.getText().length() - 5,
                schedulecurrenttime.getText().length() - 3);
        return minute;
    }

    public String switchweekday(String index) {
        logger.info("Get day of week name.");
        switch (index) {
        case "1":
            index = "Monday";
            break;
        case "2":
            index = "Tuesday";
            break;
        case "3":
            index = "Wednesday";
            break;
        case "4":
            index = "Thursday";
            break;
        case "5":
            index = "Friday";
            break;
        case "6":
            index = "Saturday";
            break;
        case "7":
            index = "Sunday";
            break;
        }
        return index;
    }

    public void TimeZoneSelect(String zone) {
        logger.info("Select time zone.");
        scheduletimezone.click();
        MyCommonAPIs.sleepi(3);
        timezoneselect(zone);
        MyCommonAPIs.sleepi(3);
        scheduleapplybutton.click();
    }

    public String getdayofweek() {
        logger.info("Get day of week.");
        //timezoneselect("Beijing");
        // scheduletimezone.click();
        // MyCommonAPIs.sleepi(3);
        // timezoneselect("Beijing");
        // MyCommonAPIs.sleepi(3);
        // scheduleapplybutton.click();
        Calendar cal = Calendar.getInstance();
        // System.out.println(getCurrentDate());
        // cal.set(2018, 07, 31);
        String dayofweek = String.valueOf(cal.get(Calendar.DAY_OF_WEEK) - 1);
        System.out.println(dayofweek);
        return dayofweek;
    }

    public void selectotherday() {
        logger.info("Select other day.");
        String dayofweek = getdayofweek();
        checkboxnotchecked("Every Day");
        MyCommonAPIs.sleepi(3);
        dayofweek = switchweekday(dayofweek);
        checkboxChecked("Every Day");
        MyCommonAPIs.sleepi(3);
        weekcheckboxselect(dayofweek, "other");
        MyCommonAPIs.sleepi(3);
        scheduleapplybutton.click();
    }

    public void selectdayofweek() {
        logger.info("Select day of week.");
        String dayofweek = getdayofweek();
        System.out.println(dayofweek);
        dayofweek = switchweekday(dayofweek);
        checkboxChecked("Every Day");
        MyCommonAPIs.sleepi(3);
        weekcheckboxselect(dayofweek, "today");
        MyCommonAPIs.sleepi(3);
        scheduleapplybutton.click();
    }

    public boolean setAllDays(String starthour, String startminute, String endhour, String endminute) {
        boolean result;

        logger.info("Disable All Day");
        checkboxChecked("All Day");
        MyCommonAPIs.sleepi(5);
        scheduleblockstarthour.clear();
        scheduleblockstarthour.sendKeys(starthour);
        MyCommonAPIs.sleepi(5);
        scheduleblockstartmin.clear();
        scheduleblockstartmin.sendKeys(startminute);
        if (!endhour.equals("")) {
            MyCommonAPIs.sleepi(5);
            scheduleblockendhour.clear();
            scheduleblockendhour.sendKeys(endhour);
        }
        MyCommonAPIs.sleepi(5);
        scheduleblockendmin.clear();
        scheduleblockendmin.sendKeys(endminute);
        MyCommonAPIs.sleepi(5);
        scheduleapplybutton.click();
        MyCommonAPIs.sleepi(5);
        if (!scheduledialog.exists() && schedulealldaychecked.getAttribute("class").equals("ant-checkbox")) {
            logger.info("Time configuration successful.");
            result = true;
        } else {
            logger.info("Time configuration unsuccessful.");
            result = false;
        }
        return result;
    }

    public boolean checkAllDaysError(String starthour, String startminute, String endhour, String endminute) {
        boolean result;

        logger.info("Check AllDay error.");
        checkboxChecked("All Day");
        MyCommonAPIs.sleepi(5);
        scheduleblockstarthour.clear();
        scheduleblockstarthour.sendKeys(starthour);
        MyCommonAPIs.sleepi(5);
        scheduleblockstartmin.clear();
        scheduleblockstartmin.sendKeys(startminute);
        if (!endhour.equals("")) {
            MyCommonAPIs.sleepi(5);
            scheduleblockendhour.clear();
            scheduleblockendhour.sendKeys(endhour);
        }
        MyCommonAPIs.sleepi(5);
        scheduleblockendmin.clear();
        scheduleblockendmin.sendKeys(endminute);
        MyCommonAPIs.sleepi(5);
        scheduleapplybutton.click();
        MyCommonAPIs.sleepi(2);
        String dialog = scheduledialog.text();
        System.out.println(dialog);
        if (dialog.equals("Invalid time input.") || dialog.equals("Start time should be early than End time in Time of day to block.")) {
            logger.info("Display error dialog.");
            result = true;
        } else {
            logger.info("Not display error dialog.");
            result = false;
        }
        MyCommonAPIs.sleepi(10);
        //checkboxnotchecked("All Day");
        //MyCommonAPIs.sleepi(5);
        //scheduleapplybutton.click();
        System.out.print(result);
        return result;
    }
}
