package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.Rectangle;
import org.openqa.selenium.interactions.Actions;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class CustomReportElement extends MyCommonAPIs {
    Logger logger = Logger.getLogger("CustomReportElement");
    
    public SelenideElement btnAddReport    = $(".icon-add");
    public SelenideElement btnSearchReport = $(".icon-search");
    public SelenideElement txtSearchReport = $("input[name=searchText]");
    public SelenideElement btnSearchNow    = $(".SearchWrapper.pull-right li button:last-child");

    public void searchReport(String item) {
        btnSearchReport.click();
        txtSearchReport.setValue(item);
        btnSearchNow.click();
        waitReady();
    }

    // default data structure for report
    public String dataReportName    = "";
    public String dataReportDesc    = "this is a report description";
    public int    dataReportType    = 0;
    public int    dataRepeatType    = 0;
    public String dataTimePeriod    = "24";
    public int    dataTimeFrequency = 0;
    public String dataMailAddress   = "";
    public int    dataBrandingLogo  = 1;
    public int    dataUseNewFilter  = 0;
    public String dataFilterName    = "radom";
    public String dataOrgName       = "";
    
    // command elements
    public SelenideElement btnPageBack        = $(".actionBtnRow .cancelBtn");
    public SelenideElement btnPageNext        = $(".actionBtnRow .saveBtn");
    public String          txtReportList      = "tbody tr td:first-child span";
    public String          staCurrentStep     = ".currentStep span span";
    public String          toCurrentStep      = ".ReportTopPageBar ul li:nth-child(%s) span span";
    public SelenideElement txtCloneReportName = $("input[name=copyReportName]");
    public SelenideElement btnViewReport      = $(".MainAlert .pointerCursor:first-child");
    public SelenideElement btnDownloadReport  = $(".MainAlert .pointerCursor:last-child a");
    public SelenideElement txtPDFView         = $(".pdfContainer");
    public SelenideElement btnPDFClose        = $(".pdfContainer .pdfClose");
    
    /**
     * @return 1 - 5
     */
    public String getStep() {
        return getText(staCurrentStep);
    }

    public void goNext() {
        takess("check step");
        btnPageNext.click();
        waitReady();
    }
    
    // page 1 - report name
    public SelenideElement txtReportName     = $("input[name=reportName]");
    public SelenideElement txtReportDesc     = $("input[name=reportDes]");
    public String          cbReportType      = "input[name=ReportTemplateType]+i+p";
    public String          cbRepeatType      = "input[name=repeatType]+i+p";
    public String          cbBrandingLogo    = "input[name=brandingLogo]+i+p";
    public SelenideElement lbTimePeriod      = $("select[name=timePeriod]");
    public SelenideElement lbTimeFrequency   = $("select[name=frequency]");
    public SelenideElement txtReportMailAddr = $("div textarea");
    
    /**
     * @param item
     *             0 - custom, 1 - troubleshoot
     */
    public void selectReportType(int item) {
        $$(cbReportType).get(item).click();
    }

    /**
     * @param item
     *             0 - once, 1 - recurrence
     */
    public void selectRepeatType(int item) {
        $$(cbRepeatType).get(item).click();
    }

    /**
     * @param item
     *             0 - msp, 1 - org
     */
    public void selectBrandingLogo(int item) {
        $$(cbBrandingLogo).get(item).click();
    }
    
    /**
     * @param data
     *             24(hours), 7(days), 30(days)
     */
    public void selectTimePeriod(String item) {
        lbTimePeriod.selectOptionContainingText(item);
    }

    /**
     * @param item
     *             0 - week, 1 - month
     */
    public void selectTimeFrequency(int item) {
        lbTimeFrequency.selectOption(item);
    }
    
    // page 2 - report filter
    public String          cbNewOrExistFilter = "input[name=selectedFilter]+i+p";
    public SelenideElement txtFilterName      = $("input[name=filterName]");
    public String          lsFilterName       = "table tr p";
    public String          lsOrgName          = ".mt5";
    public String          lsLocationName     = ".SubsAccordianBlock tr td:first-child .DarkColor";

    /**
     * @param item
     *             0 - new, 1 - existed
     */
    public void selectFilterType(int item) {
        $$(cbNewOrExistFilter).get(item).click();
    }
    
    /**
     * @param name
     *             if not found, select first one
     */
    public void selectFilter(String name) {
        int pos = 0;
        for (String fn : getTexts(lsFilterName)) {
            if (fn.equals(name)) {
                break;
            }
            pos++;
        }
        $$(lsFilterName).get(pos).click();
    }
    
    public void selectAllOrg() {
        String cbAllOrg = ".SubsAccordianBlock h3 i label";
        String cbAllOrgSta = cbAllOrg + " input";
        for (int i = 0; i < $$(cbAllOrg).size(); i++) {
            if (!$$(cbAllOrgSta).get(i).is(Condition.checked)) {
                $$(cbAllOrg).get(i).click();
                $$(".SubsAccordianBlock th label").get(i).click();
            }
        }
    }
    
    // page 3 - select widget
    public String elementWidgetTable  = "div[class*=AddDeviceAcc] > div:nth-child(%s) ";
    public String btnExpandWidget     = elementWidgetTable + "i:first-child+i";
    public String staReportWidgetType = elementWidgetTable + "h3";
    public String cbWidgetSelector    = elementWidgetTable + "label i";
    public String txtWidgetSelector   = elementWidgetTable + "h6";
    public String txtAllWidgetNames   = ".WidgetsBlock h6";

    /**
     * @param widgetType
     *                   1-n
     */
    public void expandWidgetType(int widgetType) {
        String widgetTable = String.format(staReportWidgetType, widgetType);
        if (!$(widgetTable).getAttribute("class").contains(" open")) {
            $(String.format(btnExpandWidget, widgetType)).click();
        }
    }
    
    /**
     * @param widgetType
     *                   1-n
     * @param widgetName
     */
    public void selectAWidget(int widgetType, String widgetName) {
        List<String> txtWidgetNames = getTexts(String.format(txtWidgetSelector, widgetType));
        for (int i = 0; i < txtWidgetNames.size(); i++) {
            if (txtWidgetNames.get(i).equals(widgetName)) {
                setSelected($$(String.format(cbWidgetSelector, widgetType)).get(i), true);
                break;
            }
        }
    }
    
    /**
     * @param widgetType
     *                    1-n
     * @param widgetIndex
     *                    0-n
     */
    public void selectAWidget(int widgetType, int widgetIndex) {
        setSelected($$(String.format(cbWidgetSelector, widgetType)).get(widgetIndex), true);
    }

    /**
     * @param widgetType
     *                   1 - location, 2 - inventory, 3 - changelog
     * @param widgetName
     *                   like "Last 10 Configuration Changes"
     */
    public void selectWidgets(int widgetType, String[] widgetName) {
        expandWidgetType(widgetType);
        for (String wn : widgetName) {
            selectAWidget(widgetType, wn);
        }
    }

    /**
     * select each of 3rd widget for each type
     */
    public void selectOneWidgetInAllType() {
        for (int i = 1; i < 4; i++) {
            expandWidgetType(i);
            selectAWidget(i, 2); // all 3rd one which one should has locations for mail
        }
    }

    /**
     * @param widgetType
     *                   1 - select one widget under Inventory widgets
     *                   2 - select first 2 widgets under Troubleshooting
     *                   3 - select first 2 widgets under Location health
     *                   4 - select first 2 widget under ChangeLog
     *                   5 - select fw status under Inventory
     *                   6 - select 4 top widget
     *                   7 - Top 5 Ports by Utilization
     */
    public void selectWidgetInType(int widgetType) {
        if (widgetType == 1) {
            expandWidgetType(2);
            selectAWidget(2, 2); // locations for mail
        } else if ((widgetType == 2) || (widgetType == 3)) {
            expandWidgetType(1);
            selectAWidget(1, 1);
            selectAWidget(1, 2);
        } else if (widgetType == 4) {
            expandWidgetType(3);
            selectAWidget(3, 1);
            selectAWidget(3, 2);
        } else if (widgetType == 5) {
            expandWidgetType(2);
            selectAWidget(2, "Firmware Status");
        } else if (widgetType == 6) {
            expandWidgetType(1);
            selectAWidget(1, "Top 5 clients");
//            selectAWidget(1, "Top 5 NAS Devices");
            selectAWidget(1, "Top 5 APs");
            selectAWidget(1, "Top 5 Switches");
        } else if (widgetType == 7) {
            expandWidgetType(1);
            selectAWidget(1, "Top 5 Ports by Utilization");
        } else {
            logger.warning("FIXME");
        }
    }
    
    // page 4 - re-order
    public String btnDragWidget      = ".divTableRow img[src*=drag]";
    public String btnDeleteWidget    = ".divTableRow img[src*=del]";
    public String txtOrderWidgetName = ".divTableRow .divTableCell:first-child";
    
    public int getOrderWidgetNumber() {
        return $$(txtOrderWidgetName).size();
    }
    
    /**
     * drag first widget to be 2nd one
     */
    public void drag1stTo2nd() {
        takess("before drag");
        Actions act = new Actions(WebportalParam.curWebDriver);
        SelenideElement se1 = $$(txtOrderWidgetName).get(0);
        Rectangle rt1 = se1.getRect();
        se1.hover();
        SelenideElement se2 = $$(btnDragWidget).get(0);
        Rectangle rt2 = se2.getRect();
        act.dragAndDropBy(se2, 0, rt1.height).build().perform();
        takess("after drag");
        waitReady();
    }

    /**
     * @param pos
     *            0 - n
     */
    public void deleteWidget(int pos) {
        SelenideElement se = $$(txtOrderWidgetName).get(pos);
        se.hover();
        $$(btnDeleteWidget).get(pos).click();
        waitReady();
    }
    
    // page 5 - report review
    public String          txtReviewReportName = elementWidgetTable + "li:first-child h3";
    public String          txtReviewReportDesc = elementWidgetTable + "li:last-child h3";
    public String          txtReviewWidgetName = elementWidgetTable + "tr td:first-child span";
    public String          txtReviewOrgName    = elementWidgetTable + "td span:nth-child(2)";
    public SelenideElement btnReview           = $(".ReviewBlock .pointerCursor");
    public SelenideElement txtReviewHeader     = $(".GenrateReportChartBlock h6");

    public String getReviewReportName() {
        return getText(String.format(txtReviewReportName, "1"));
    }
    
    public String getReviewReportDesc() {
        return getText(String.format(txtReviewReportDesc, "1"));
    }
    
    public int getReviewWidgetNumber() {
        return $$(String.format(txtReviewWidgetName, "2")).size();
    }
    
    public List<String> getReviewWidgets() {
        return getTexts(String.format(txtReviewWidgetName, "2"));
    }

    public int getReviewOrgNumber() {
        return $$(String.format(txtReviewOrgName, "3")).size();
    }
}
