package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import java.util.logging.Logger;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.WebportalParam;

public class FirmwarePolicyElement extends MyCommonAPIs {
    Logger logger = Logger.getLogger("FirmwarePolicyElement");
    
    public SelenideElement cbAuto = $(".cmnSwitch input");
    
    public SelenideElement    btnStartDate      = $("#startDate");
    public SelenideElement    btnStartTime      = $("#startTime");
    public String             sStartEndTime     = "form h5+p";
    public SelenideElement    btnStartEndTime   = $(sStartEndTime);
    public ElementsCollection btnStartEndTimes  = $$(sStartEndTime);
    public SelenideElement    btnEndDate        = $("#endDate");
    public SelenideElement    btnEndTime        = $("#endTime");
    public String             startenddatetime1 = ".react-datepicker__input-container";
    public String             startenddatetime2 = ".PoeSheduleEdit .clearfix h5+p";
    public String             startenddatetime3 = ".PoeSheduleEdit .clearfix h5+input";
    
    /**
     * @param btnIndex
     *                 0 - start date, 1 - start time, 2 - end date, 3 - end time
     */
    public void clickDateTime(int btnIndex) {
        logger.info("selected index: " + btnIndex);
        if (btnIndex == 0) {
            $$(startenddatetime1).get(0).click();
        } else if (btnIndex == 2) {
            $$(startenddatetime1).get(1).click();
        } else { // must first start time, then end time
            if (btnIndex == 1) {
                if ($(startenddatetime2).exists()) {
                    $$(startenddatetime2).get(0).click();
                } else {
                    $$(startenddatetime3).get(0).click();
                }
            }
            
            if (btnIndex == 3) {
                if ($(startenddatetime2).exists()) {
                    $$(startenddatetime2).get(0).click();
                } else {
                    $$(startenddatetime3).get(1).click();
                }
            }
        }
    }
    
    public SelenideElement lbRepeat        = $("#content select");
    public SelenideElement lbMonthlyRepeat = $(".MonthlyBlock select");
    public SelenideElement btnRecuDate     = $(".recurrence div input");
    public SelenideElement btnRecuTimes    = $("#recurrenceNumberField");
    public String          cbWeeklyList    = ".WeekList li label";
    public String          rbEnds          = ".RecurrenceBlock div:last-child label > input:first-child";
    
    public class Recurrence {
        /**
         * 0 - none, 1 - daily, 2 - weekly, 3 - monthly
         */
        public int repeatType = 0;
        /**
         * 0 - never, 1 - on date, 2 - times
         */
        public int endType    = 0;
        /**
         * how many times
         */
        public int recurrence = 10;
        
        public Recurrence() {
        }
        
        /**
         * for recurrence based on all values
         */
        public void setRecurrence() {
            logger.info("repeatType: " + repeatType + " endType: " + endType + " recurrence: " + recurrence);
            if ((2 == repeatType) && WebportalParam.getNLocText(lbRepeat.getSelectedText()).equals("Weekly")) {
                // to clear option for weekly
                lbRepeat.selectOption(WebportalParam.getLocText("None"));
                clickButton(0);
            }
            if (1 == repeatType) { // daily
                lbRepeat.selectOption(WebportalParam.getLocText("Daily"));
            } else if (2 == repeatType) { // weekly
                lbRepeat.selectOption(WebportalParam.getLocText("Weekly"));
                waitReady();
                for (SelenideElement se : $$(cbWeeklyList)) {
                    se.click();
                }
            } else if (3 == repeatType) { // monthly
                lbRepeat.selectOption(WebportalParam.getLocText("Monthly"));
            } else { // none
                lbRepeat.selectOption(WebportalParam.getLocText("None"));
            }
            
            $$(rbEnds).get(endType).click();
            if (1 == endType) { // date
                btnRecuDate.click();
                new CommonDataType().new DateData().setDate(2);
            } else if (2 == endType) { // times
                setText(btnRecuTimes, recurrence);
            }
        }
    }
}
