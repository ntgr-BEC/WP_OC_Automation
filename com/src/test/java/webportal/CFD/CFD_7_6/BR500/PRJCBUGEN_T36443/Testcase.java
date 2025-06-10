package webportal.CFD.CFD_7_6.BR500.PRJCBUGEN_T36443;


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
    @Story("PRJCBUGEN_T36443") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to add BR device with a valid serial number into a location") // It's a testcase                                                                                                                           // Test Case.
    @TmsLink("PRJCBUGEN_T36443") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

  @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("Starting tearDown after test execution.");
        new DevicesDashPage(false).deleteDeviceNo(WebportalParam.br1deveiceMac);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM WP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();

    }

    @Step("Onboard Dummy BR500 and validate")
    public void step2() {
        HashMap <String,String> firststdevInfo= new HashMap<String, String>();
        firststdevInfo.put("MAC Address1", WebportalParam.br1deveiceMac);
        firststdevInfo.put("Serial Number1", WebportalParam.br1deveiceName);
        new DevicesDashPage().addNewdummyDevice(firststdevInfo);
        new DevicesDashPage().GoToDevicesDashPage();

    }

    @Step("Test Step3: Assert BR device is added")
    public void step3() {
        SelenideElement deviceElement = $$x("//*[text()='" + WebportalParam.br1serialNo + "']").first();
        deviceElement.shouldBe(visible); // Waits until the element is visible
        assertTrue(GenericMethods.checkVisibleElements($$x("//*[text()='" + WebportalParam.br1serialNo + "']")),
                "BR device with serial number not found");
    }




}
