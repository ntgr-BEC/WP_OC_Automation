package webportal.Orbi.SSID_WAN.SanityBusinessVPNFlow.PRJCBUGEN_T24756;

import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.RoutersBusinessVPNPage;
import webportal.weboperation.DevicesDashPage;
import webportal.param.URLParam;
import webportal.param.WebportalParam;


/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SSID_WAN.SanityBusinessVPNFlow") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T24756") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the Business VPN group in the Organization level in Insight Pro account.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T24756") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p200")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("Teardown : delete device in org2 / delete location in org2 / delete org2 / onboard the device in original location");
        try {
            handle.gotoOrganizationLoction("Organization2", "Location_1");
            new DevicesDashPage().deleteDeviceYes(WebportalParam.ob3serialNo);
            util.MyCommonAPIs.sleep(120 * 1000);
            // delete location and org (not complete)
            handle.gotoOrganizationLoction(WebportalParam.Organizations, WebportalParam.location2);
            new DevicesDashPage().addNewDevice(WebportalParam.ob3serialNo, WebportalParam.ob3deveiceName, WebportalParam.ob1deveiceMac);
            util.MyCommonAPIs.sleep(180 * 1000);
        } catch (Throwable e) {
            // TODO: handle exception
        }
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Delete OB3 in location2")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        String currentUrl = util.MyCommonAPIs.getCurrentUrl();
        boolean checkpoint = currentUrl.contains(URLParam.hreforganization);
        assertTrue(checkpoint,"checkpoint 1 : the accout is pro account");
        
        handle.gotoLoction(WebportalParam.location2);
        new DevicesDashPage().deleteDeviceYes(WebportalParam.ob3serialNo);
        util.MyCommonAPIs.sleep(120 * 1000);
    }
    
    @Step("Test Step 2: Add Organization2 with auto Location_1 / onboard OB3 in Location_1")
    public void step2() {
        // add organization and location (not complete)
        handle.gotoOrganizationLoction("Organization2","Location_1");
        new DevicesDashPage().addNewDevice(WebportalParam.ob3serialNo, WebportalParam.ob3deveiceName, WebportalParam.ob1deveiceMac);
        util.MyCommonAPIs.sleep(180 * 1000);
    }
    
    @Step("Test Step 3: Go to Location 1 where the central router is located")
    public void step3() {
        handle.gotoOrganizationLoction(WebportalParam.Organizations, WebportalParam.location1);
        //handle.gotoLoction();
    }

    @Step("Test Step 4: Go to Routers page / Create VPN group with routers in different locations / Check the group is added")
    public void step4() {
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup","TestDescription","1400");
        page.setPage2AddCentralRouter();
        
        page.clickAddRemoteRouter();
        page.SelectLocation("Location_1");
        checkpoint = page.SelectRouter(WebportalParam.ob3deveiceName);    
        page.modalClickCancel();
        
        assertFalse(checkpoint,"checkpoint 1 : user cannot add router in another organization");
    }
}
