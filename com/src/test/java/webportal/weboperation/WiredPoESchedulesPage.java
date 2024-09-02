/**
 *
 */
package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import util.SwitchCLIUtils;
import webportal.param.CommonDataType;
import webportal.param.CommonDataType.DateData;
import webportal.param.CommonDataType.TimeData;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.webelements.WiredPoESchedulesElement;

/**
 * @author Lavi
 */
public class WiredPoESchedulesPage extends WiredPoESchedulesElement {
    /**
     *
     */
    final static Logger logger = Logger.getLogger("WiredPoESchedulesPage");
    public TimeData     ddTime;
    public DateData     ddDate;

    public void initTestData() {
        ddTime = new CommonDataType().tdTimeData;
        ddDate = new CommonDataType().ddDateData;
    }

    public WiredPoESchedulesPage() {
        // TODO Auto-generated constructor stub
        open(URLParam.hrefPoESchedules, true);
        logger.info("init...");
    }

    public WiredPoESchedulesPage(boolean noPage) {
        logger.info("initex...");
    }

    public void gotoPage() {
        open(URLParam.hrefPoESchedules, true);
        if (WebportalParam.enaTwoSwitchMode && !WebportalParam.sw1Model.contains("P")) {
            WebportalParam.updateSwitchOneOption(true, WebportalParam.sw2IPaddress);
        }
    }

    public List<String> getPoEs() {
        if ($(ecPoEList).exists())
            return MyCommonAPIs.getTexts(ecPoEList);
        else
            return Collections.emptyList();
    }

    /**
     * TODO: open a item from list
     *
     * @param name
     *                 open this time schedule
     * @param btnIndex
     *                 See {@link #clickButton(int)}
     */
    public void openTimeSchedule(String name, int btnIndex) {
        logger.info(String.format(": %s-%s", name, btnIndex));
        int iPos = 0;
        for (String s : getPoEs()) {
            if (s.equals(name)) {
                String tocheck = String.format("%s%d", ecPoEListStaPre, iPos);
                String toclick = String.format("%s%d", ecPoEListBtnPre, iPos);
                if (!$(tocheck).getAttribute("class").toLowerCase().contains(" open")) {
                    $(toclick).click();
                }
                break;
            }
            iPos++;
        }
        clickButtons(btnIndex);
    }

    /**
     * @param btnIndex
     *                 0 to edit, 1 to edit port
     */
    public void clickButtons(int btnIndex) {
        logger.info(String.format(": %s", btnIndex));
        String sBtn = btnItemEditPort.getSearchCriteria();
        if (btnIndex == 0) {
            sBtn = btnItemEdit.getSearchCriteria();
        }

        for (SelenideElement se : $$(sBtn)) {
            if (se.isDisplayed()) {
                logger.info(String.format(": %s-%s", se, btnIndex));
                se.click();
                MyCommonAPIs.waitReady();
                break;
            }
        }
    }

    public boolean fullweek = false;

    /**
     * @param  day
     *             from 1 - 7
     * @return     true is active
     */
    public boolean getWeeklyDayStatus(int day) {
        String sts = $(String.format(staWeeklyDay, day)).getAttribute("class");
        logger.info(String.format(": %s-%s", day, sts));
        if (sts.toLowerCase().contains("active"))
            return true;
        else
            return false;
    }

    /**
     * All day and with 1 month day
     *
     * @param name
     *                  A string for schedule name
     * @param withport
     *                  0 no port, 1 to select port1
     * @param recurtype
     *                  None, Daily, Weekly (for weekly, please set {@link #fullweek}
     */
    public void addPoESchedule(String name, int withport, String recurtype) {
        logger.info(String.format(": %s-%s-%s", name, withport, recurtype));
        MyCommonAPIs.waitReady();
        click(btnAdd);
        waitReady();
        txtScheduleName.setValue(name);
        // avoid 2 min apart issue
        if (btnSliderTime.exists()) {
            setTimeRange();
        } else {
            txtStartTime.click();
            ddTime.hour = 4;
            ddTime.setTime();
            txtEndTime.click();
            ddTime.hour = 8;
            ddTime.setTime();
        }
        setSelected(cbAllDay, true, true);
        lbRecurrenceType.selectOption(WebportalParam.getLocText(recurtype));
        if (recurtype.contains("eek")) {
            if (fullweek) {
                for (int i = 1; i < 8; i++) {
                    $(String.format(cbWeeklyDay, i)).click();
                }
            } else {
                $(String.format(cbWeeklyDay, 1)).click();
                $(String.format(cbWeeklyDay, 7)).click();
            }
        }
        txtStartDate.click();
        ddDate.setDate(true);
        txtEndDate.click();
        ddDate.setDate(false);
        if (withport != 0) {
            clickSavePort();
            clickPortPoE(1);
        }
        
        
        //clickButton(0);
        clickSaveBtn();
        if(btnSave.isDisplayed()) {
            btnSave.click();
        }
        
        MyCommonAPIs.sleepi(10);
        System.out.println("addPoESchedule Done");
    }
    
    /**
     * @param name
     * @param recurtype
     *                  None, Daily, Weekly (for weekly, please set {@link #fullweek}
     * @param port
     *                  1~n
     * @param timeStart
     *                  TODO
     * @param timeEnd
     *                  TODO
     */
    public void addMidNightPoESchedule(String name, String recurtype, String port, String timeStart, String timeEnd) {
        logger.info(String.format(": %s-%s-%s", name, port, recurtype));
        MyCommonAPIs.waitReady();
        click(btnAdd);
        waitReady();
        txtScheduleName.setValue(name);
        txtStartTime.click();
        ddTime.hour = 9;
        ddTime.minute = 19;
        ddTime.isam = false;
        ddTime.setTime();
        txtEndTime.click();
        ddTime.hour = 6;
        ddTime.minute = 16;
        ddTime.isam = true;
        ddTime.setTime();

        lbRecurrenceType.selectOption(WebportalParam.getLocText(recurtype));
        txtStartDate.click();
        ddDate.setDate(true);
        txtEndDate.click();
        ddDate.setDate(false);
        takess("check data&time info");
        clickSavePort();
        clickAllPoEPort(port);
        clickButton(0);
        
    }

    /**
     * Delete all time schedule on GUI
     */
    public void deleteAll() {
        takess("delete All time schedule");
        try {
            refresh();
            MyCommonAPIs.sleepi(4);
            open(URLParam.hrefPoESchedules, true);
            MyCommonAPIs.sleepi(4);
            int ii = $$(ecPoEList).size();
            for (int i = 0; i < ii; i++) {
                logger.info(String.format("delete: %snd item", i + 1));
                if (!$(ecPoEListSta).getAttribute("class").toLowerCase().contains(" open")) {
                    $(ecPoEListBtn).click();
                }
                btnItemDelete.click();
                click(btnYes);
                MyCommonAPIs.sleepi(4);
            }
        } catch (Throwable e) {
            takess();
            e.printStackTrace();
        }
    }

    public void deleteAllCli() {
        logger.info("deleteAllCli");
        SwitchCLIUtils.cleanTimeSchdule();
    }
}
