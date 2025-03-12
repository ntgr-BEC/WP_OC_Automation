package webportal.ProManagedSwitch.POE.PRJCBUGEN_T5030;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.param.CommonDataType;
import webportal.param.CommonDataType.DateData;
import webportal.param.CommonDataType.TimeData;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author sumanta
 *
 */
public class Testcase extends TestCaseBase {
    String tclname = getClass().getName();
    String tmpStr;

    public String sTestStr = "TSName5030";
    public String sCurrentValue;
    public String sExpectedtValue;
    TimeData      ddTime   = new CommonDataType().new TimeData();
    DateData      ddDate   = new CommonDataType().new DateData();

    @Feature("Switch.POE") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T5030") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("033-Time schedule config to non-PoE port") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T5030") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p3") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
        if (wpsp != null) {
            wpsp.deleteAll();
        }
        WebportalParam.updateSwitchOneOption(false, null);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open Device")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);

        handle.gotoLoction();
        handle.gotoLocationWireSettings();

        wpsp.gotoPage();
    }

    @Step("Test Step 2: Config poe time schedule")
    public void step2() {
        wpsp.addPoESchedule(sTestStr, 0, "None");
    }

    @Step("Test Step 3: Config poe time schedule to non-poe port")
    public void step3() {
        wpsp.openTimeSchedule(sTestStr, 1);
        int iSw;
        if (WebportalParam.sw1Model.toLowerCase().contains("p")) {
            iSw = 0;

        } else if (WebportalParam.sw1Model.toLowerCase().contains("p")) {
            iSw = 1;

        } else
            throw new RuntimeException("not poe device");

        handle.clickAllPort(iSw);
        String sGet = handle.getPortCls(handle.getPortNo(iSw), iSw);
        assertFalse(sGet.toLowerCase().contains("green"), "Last PoE port cannot be selected");
        sGet = handle.getPortCls(1, iSw);
        assertTrue(sGet.toLowerCase().contains("green"), "First PoE port can be selected");
        handle.click(wpsp.btnCancel);
    }

}
