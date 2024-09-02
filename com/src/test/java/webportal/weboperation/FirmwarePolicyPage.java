package webportal.weboperation;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.logging.Logger;

import webportal.param.CommonDataType;
import webportal.param.CommonDataType.DateData;
import webportal.param.CommonDataType.TimeData;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.FirmwarePolicyElement;

public class FirmwarePolicyPage extends FirmwarePolicyElement {
    Logger            logger         = Logger.getLogger("FirmwarePolicyPage");
    public TimeData   ddTime;
    public DateData   ddDate;
    public Recurrence ddRec;
    public int        nFirmwareSleep = 4;

    public FirmwarePolicyPage() {
        logger.info("init...");
    }

    public void initTestData() {
        ddTime = new CommonDataType().tdTimeData;
        ddDate = new CommonDataType().ddDateData;
        ddRec = new Recurrence();
        Sleep4Midnight();
    }

    public void gotoPage() {
        WebCheck.checkUrl(URLParam.hrefFirmware);
        waitReady(); // for checkbox status
    }

    public void disableAuto() {
        setSelected(cbAuto, false, true);
        waitReady();
    }

    public void enableAuto() {
        System.out.println(cbAuto.getCssValue("background-color"));
        setSelected(cbAuto, true, true);
        System.out.println(cbAuto.getCssValue("background-color"));
        waitReady();
    }

    public void Sleep4FirmwareSchedule() {
        sleep(nFirmwareSleep * 60, "sleep for pass to firmware schedule");
    }

    /**
     * @param needTZ
     *            true to set Bolivia tz
     * @param isLess1H
     *            true to set time less than 60m for error
     * @param isPassTime
     *            true to select passed time
     */
    public void SetDailyDateTime(boolean needTZ, boolean isLess1H, boolean isPassTime) {
        logger.info("needTZ: " + needTZ + " isLess1H: " + isLess1H + " isPassTime: " + isPassTime);
        DateData ddDateStart = new CommonDataType().ddDateData;
        TimeData ddTimeStart = new CommonDataType().tdTimeData;
        DateData ddDateEnd = new CommonDataType().ddDateData;
        TimeData ddTimeEnd = new CommonDataType().tdTimeData;

        Calendar cal;
        if (needTZ) {
            TimeZone tz = TimeZone.getTimeZone("America/La_Paz");
            cal = new GregorianCalendar();
            cal.setTimeZone(tz);
        } else {
            cal = Calendar.getInstance();
        }

        if (isPassTime) {
            cal.add(Calendar.MINUTE, -nFirmwareSleep);
        } else {
            cal.add(Calendar.MINUTE, nFirmwareSleep);
        }

        ddDateStart.year = cal.get(Calendar.YEAR);
        ddDateStart.month = String.format("%d", cal.get(Calendar.MONTH) + 1);
        ddDateStart.day = cal.get(Calendar.DATE);
        ddTimeStart.hour = cal.get(Calendar.HOUR);
        ddTimeStart.minute = cal.get(Calendar.MINUTE);
        ddTimeStart.isam = cal.get(Calendar.AM_PM) == 0 ? true : false;

        if (isLess1H) {
            cal.add(Calendar.MINUTE, 40);
        } else {
            cal.add(Calendar.HOUR, 4);
        }

        ddDateEnd.year = cal.get(Calendar.YEAR);
        ddDateEnd.month = String.format("%d", cal.get(Calendar.MONTH) + 1);
        ddDateEnd.day = cal.get(Calendar.DATE);
        ddTimeEnd.hour = cal.get(Calendar.HOUR);
        ddTimeEnd.minute = cal.get(Calendar.MINUTE);
        ddTimeEnd.isam = cal.get(Calendar.AM_PM) == 0 ? true : false;

        clickDateTime(0);
        ddDateStart.setDate();
        clickDateTime(2);
        ddDateEnd.setDate();

        clickDateTime(1);
        ddTimeStart.setTime();
        clickDateTime(3);
        ddTimeEnd.setTime();
    }

    /**
     * @param istoday
     *            true for today, false for next month
     * @param needRec
     *            check {@code ddRec}
     * @param needTZ
     *            true to set time with timezone (GTM-4) false to set GTM+8
     */
    public void addSchedule(boolean istoday, boolean needRec, boolean needTZ) {
        enableAuto();
        waitReady();

        // for start/end date/time for daily first because its only allowed to less than 24h
        SetDailyDateTime(needTZ, false, false);

        // update start date if need
        if (!istoday) {
            clickDateTime(0);
            ddDate.setDate(istoday);
        }

        // can be anything for end date if not daily
        if (!needRec || (ddRec.repeatType != 1)) {
            clickDateTime(2);
            ddDate.setDate(2);
        }

        // set recurrence
        if (needRec) {
            ddRec.setRecurrence();
        } else {
            lbRepeat.selectOption(WebportalParam.getLocText("None"));
        }

        clickButton(0);
    }
}
