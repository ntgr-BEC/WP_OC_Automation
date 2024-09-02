package webportal.Orbi.APRouterFunctionality.APFunctionality.PRJCBUGEN_T7500;

import static com.codeborne.selenide.Selenide.$;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.webelements.DevicesOrbiLANIPPageElement;
import webportal.weboperation.DevicesOrbiDeviceModePage;
import webportal.weboperation.DevicesOrbiLanIPPage;
import webportal.weboperation.DevicesOrbiDHCPServersPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.param.WebportalParam;
import webportal.param.URLParam;

import static com.codeborne.selenide.Selenide.$x;


/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("APRouterFunctionality.APFunctionality") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7500") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify when we in AP mode then LAN IP option disabled or not.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T7500") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to WP/Select one Location/Click \"Device\" button")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();

        handle.gotoLoction();

        ddp.gotoPage();
    }

    @Step("Test Step 2: Choose online device, click \"Edit\" button")
    public void step2() {
        ddp.openOBDevice();
        new DevicesOrbiDeviceModePage(false).initDeviceMode(true);
    }

    @Step("Test Step 3: LAN IP Should be disabled when we in AP mode.")
    public void step3() {
        if(WebportalParam.ob2Model.contains("SXR")) { // SXR30, 50, 80
            SelenideElement dhcpserverbtn = $x(String.format("//a[@href='%s']", URLParam.hrefOrbidhcpServerOrbi)); 
            try {
                dhcpserverbtn.click();
                assertTrue(false);
                
            } catch(Throwable e) {
                assertTrue(true);
            }
        }
        else { // for 60
            SelenideElement lanipbtn = $x(String.format("//a[@href='%s']", URLParam.hrefOrbiLANIP)); 
            try {
                lanipbtn.click();
                assertTrue(false);
                
            } catch(Throwable e) {
                assertTrue(true);
            }
        }
    }
}
