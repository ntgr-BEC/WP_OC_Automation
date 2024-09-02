package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.lang.RandomStringUtils;

import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.CustomReportElement;

public class CustomReportPage extends CustomReportElement {
    Logger logger = Logger.getLogger("CustomReportPage");
    
    public CustomReportPage() {
        logger.info("init...");
    }
    
    public void initTestData() {
        dataReportName = "";
        dataReportDesc = "this is a report description of " + RandomStringUtils.randomNumeric(5);
        dataReportType = 0;
        dataRepeatType = 0;
        dataTimePeriod = "24";
        dataTimeFrequency = 0;
        dataMailAddress = "";
        dataBrandingLogo = 1;
        dataUseNewFilter = 0;
        dataFilterName = "FilterName_" + RandomStringUtils.randomAlphabetic(5);
        dataOrgName = "";
    }
    
    public void gotoPage() {
        try {
            $(".AdminDropDown span").click();
            waitReady();
            $("ul a[href*=report]").click();
            waitReady();
        } catch (Throwable e) {
            e.printStackTrace();
            WebCheck.checkUrl(URLParam.hrefCustomReport);
        }
    }
    
    public List<String> getReportList() {
        if (!$(txtReportList).exists())
            return new ArrayList<String>();
        return getTexts(txtReportList);
    }
    
    public void deleteAll() {
        takess("delete All data");
        for (String s : getReportList()) {
            logger.info("delete report: " + s);
            doReportList(s, 4);
        }
    }
    
    /**
     * @param name
     * @param action
     *               0 - view, 1 - mail, 2 - clone, 3 - edit, 4 - delete
     */
    public void doReportList(String name, int action) {
        logger.info("name: " + name + ", action:" + action);
        int pos = 1;
        for (String s : getReportList()) {
            if (s.equals(name)) {
                break;
            }
            pos++;
        }

        String sRow = String.format("tbody tr:nth-child(%s)", pos);
        $(sRow).hover();
        $$(sRow + " img").get(action).click();
        waitReady();
        if ((action == 2) || (action == 4)) {
            clickBoxLastButton();
            waitReady();
            sleep(5);
        }
    }
    
    public void gotoStep(String iStep) {
        int ts = Integer.parseInt(iStep);
        int cs = Integer.parseInt(getStep());
        if (ts != cs) {
            logger.info("go to step " + iStep);
            $(String.format(toCurrentStep, iStep)).click();
            waitReady();
            sleep(5);
        }
    }

    /**
     * @param reportName
     * @param widgetType
     *                   0 - select one for all, 1 - select one for Inventory, see {@link selectWidgetInType}
     */
    public void setReport(String reportName, int widgetType) {
        if (!txtReportName.exists()) {
            if (getReportList().contains(reportName)) {
                doReportList(reportName, 3);
            } else {
                btnAddReport.click();
            }
        }
        
        // page 1
        txtReportName.clear();
        txtReportName.setValue(dataReportName);
        txtReportDesc.click();
        waitReady();
        sleepi(5);
        if ((getCheckPointStep() == 12))
            return;
        txtReportDesc.setValue(dataReportDesc);
        selectReportType(dataReportType);
        if (dataReportType == 0) {
            selectRepeatType(dataRepeatType);
            if (dataRepeatType == 0) {
                selectTimePeriod(dataTimePeriod);
            } else {
                selectTimeFrequency(dataTimeFrequency);
            }
        }
        
        txtReportMailAddr.setValue(dataMailAddress);
        selectBrandingLogo(dataBrandingLogo);
        if ((getCheckPointStep() == 11)) {
            goNext();
            return;
        }
        if ((getCheckPointStep() == 7)) {
            setCheckPointResult(lbTimeFrequency.getSelectedText().equals(WebportalParam.getLocText("Monthly")));
            return;
        }
        if ((getCheckPointStep() == 8)) {
            setCheckPointResult(lbTimePeriod.getSelectedText().contains(dataTimePeriod));
            return;
        }

        goNext();
        
        // page 2
        if ((getCheckPointStep() == 1) || (getCheckPointStep() == 2)) {
            selectFilterType(1);
            setCheckPointResult($$(lsFilterName).size() > 0);
            return;
        }
        if ((getCheckPointStep() == 3)) {
            selectFilterType(0);
            setCheckPointResult(getTexts(lsOrgName).contains(dataOrgName));
            return;
        }
        if ((getCheckPointStep() == 6)) {
            selectFilterType(0);
            setCheckPointResult(!getTexts(lsOrgName).contains(dataOrgName));
            return;
        }

        selectFilterType(dataUseNewFilter);
        if (dataUseNewFilter == 0) {
            txtFilterName.setValue(dataFilterName);
            selectAllOrg();
        } else {
            selectFilter(dataFilterName);
        }
        goNext();

        // page 3
        if (widgetType == 0) {
            selectOneWidgetInAllType();
        } else {
            selectWidgetInType(widgetType);
        }
        goNext();

        // page 4
        List<String> lsWidgets = null;
        if ((getCheckPointStep() == 4)) {
            lsWidgets = getTexts(txtOrderWidgetName);
            drag1stTo2nd();
        }
        if ((getCheckPointStep() == 5)) {
            lsWidgets = getTexts(txtOrderWidgetName);
            deleteWidget(0);
        }
        goNext();

        // page 5
        if ((getCheckPointStep() == 4)) {
            setCheckPointResult(!getReviewWidgets().equals(lsWidgets));
            return;
        }
        if ((getCheckPointStep() == 5)) {
            setCheckPointResult(getReviewWidgetNumber() < lsWidgets.size());
            return;
        }
        goNext();
        sleepsync();
    }
    
}
