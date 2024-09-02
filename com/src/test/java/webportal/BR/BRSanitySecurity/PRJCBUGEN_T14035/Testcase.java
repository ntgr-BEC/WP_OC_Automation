package webportal.BR.BRSanitySecurity.PRJCBUGEN_T14035;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String brNewName = "PRJCBUGENT14035";
    
    @Feature("BR.SecuritySanity") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T14035") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("On-boarding BR500 device via serial number") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T14035") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        handle.gotoLoction(WebportalParam.location1);
        ddp.gotoPage();
        ddp.editDeviceName(WebportalParam.br1serialNo, WebportalParam.br1deveiceName);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: IM APP discover and manage BR500")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
    }

    @Step("Test Step 2: 4. Enter serial number in Add device Screen and click on Go button")
    public void step2() {
        handle.gotoLoction(WebportalParam.location1);
        ddp.gotoPage();
        ddp.addNewDevice(WebportalParam.br1serialNo, WebportalParam.br1deveiceName, WebportalParam.br1deveiceMac);
        ddp.waitDevicesOnline(WebportalParam.br1serialNo);

    }
    
    @Step("Test Step 3: 7. Rename the device name and verify device name updated successfully.")
    public void step3() {
        handle.gotoLoction(WebportalParam.location1);
        ddp.gotoPage();
        ddp.editDeviceName(WebportalParam.br1serialNo, brNewName);
        assertTrue(ddp.getDeviceName(WebportalParam.br1serialNo).equals(brNewName), "The user should be successfully able to rename the device");
    }
}
