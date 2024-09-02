package webportal.Orbi.IM69_Wifi_Schedule.PRJCBUGEN_T31365;

import static com.codeborne.selenide.Selenide.$;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.weboperation.WebportalLoginPage;
import webportal.param.WebportalParam;
import webportal.weboperation.SummaryPage;
import webportal.weboperation.DevicesOrbiWifiSchedulePage;
import webportal.publicstep.UserManage;


/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    
    @Feature("Orbi.IM69_Wifi_Schedule") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T31365") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify that scheduler time must be in 24 hours format.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T31365") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login using proper user name and password in latest Webportal")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
    }
    
    @Step("Test Step 2: Onboard Orbi SXR80 and SXR30  device")
    public void step2() {
        handle.gotoLoction();
        ddp.gotoPage();
        ddp.openOB2();
    }

    @Step(
        "Test Step 3: Goto Orbi --> Device Dashboard --> WiFi Schedule page " + 
        "Test Step 4: Click on (+) Add Schedule button " + 
        "Test Step 5: Verify the scheduler time is in 24 hours format (The newer version of Insight wifi schedule is 12 hr format)"
    )
    public void step3() {
        DevicesOrbiWifiSchedulePage page = new DevicesOrbiWifiSchedulePage();
        page.clickAdd();
        page.selectDayCircle("1", true);
        page.expandDayTimePicker("1", true);
        assertTrue(page.dayTimePickerSelect("1", true).exists(),"checkpoint : am/pm select element should exists");
        page.clickDayTimePickerOK("1", true);
    }
    
}