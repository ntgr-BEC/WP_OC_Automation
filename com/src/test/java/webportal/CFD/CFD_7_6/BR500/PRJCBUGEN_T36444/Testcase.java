package webportal.CFD.CFD_7_6.BR500.PRJCBUGEN_T36444;


import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.weboperation.*;

/**
 *
 * @author RaviShankar
 *
 */
public class Testcase extends TestCaseBase {

    @Feature("Onboarding BR500") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T36444") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to add BR device with a valid serial number into a Pro Account") // It's a testcase                                                                                                                           // Test Case.
    @TmsLink("PRJCBUGEN_T36444") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }


    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("Starting tearDown after test execution.");
        new DevicesDashPage(false).deleteDeviceNo(WebportalParam.br1deveiceMac);
    }

    @Step("Step 1: Login into Insight Pro account as admin")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName, WebportalParam.adminPassword);
    }

    @Step("Step 2: Navigate to Organization and open Location")
    public void step2() {
        new OrganizationPage(false).openOrg(WebportalParam.Organizations);
        new MyCommonAPIs().gotoLoction(WebportalParam.location1);
    }

    @Step("Step 3: Add BR device with a valid serial number into a location")
    public void step3() {
        HashMap<String, String> deviceInfo = new HashMap<>();
        deviceInfo.put("MAC Address1", WebportalParam.br1deveiceMac);
        deviceInfo.put("Serial Number1", WebportalParam.br1serialNo);
        new DevicesDashPage().addNewdummyDevice(deviceInfo);
        new DevicesDashPage().GoToDevicesDashPage();
        SelenideElement deviceElement = $$x("//*[text()='" + WebportalParam.br1serialNo + "']").first();
        deviceElement.shouldBe(visible); // Waits until the element is visible
        assertTrue(GenericMethods.checkVisibleElements($$x("//*[text()='" + WebportalParam.br1serialNo + "']")),
                "BR device with serial number not found");
    }



}
