package webportal.Orbi.SSID_WAN.SanityBusinessVPNFlow.PRJCBUGEN_T24754;

import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;
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
import webportal.param.WebportalParam;


/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SSID_WAN.SanityBusinessVPNFlow") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T24754") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify device router across locations can be added to Business VPN group.") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T24754") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("Teardown : delete VPN group / restore OB3 to onboard at location2");
        try {
            RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
            page.gotoPage();
            page.DelVPNGroup("TestGroup");
            util.MyCommonAPIs.sleep(60 * 1000);
        } catch (Throwable e) {
            // TODO: handle exception
        }
        try {
            handle.gotoLoction(WebportalParam.location3);
            new DevicesDashPage().deleteDeviceYes(WebportalParam.ob2serialNo);
            util.MyCommonAPIs.sleep(120 * 1000);
            handle.gotoLoction(WebportalParam.location2);
            new DevicesDashPage().addNewDevice(WebportalParam.ob2serialNo, WebportalParam.ob2deveiceName, WebportalParam.ob2MAC_Address);
            util.MyCommonAPIs.sleep(180 * 1000);
        } catch (Throwable e) {
            // TODO: handle exception
        }
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 2 and delete OB2")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction(WebportalParam.location2);
        new DevicesDashPage().deleteDeviceYes(WebportalParam.ob2serialNo);
        util.MyCommonAPIs.sleep(120 * 1000);
    }
    
    @Step("Test Step 2: Go to Location 3 and onboard OB2")
    public void step2() {
        handle.gotoLoction(WebportalParam.location3);
        new DevicesDashPage().addNewDevice(WebportalParam.ob2serialNo, WebportalParam.ob2deveiceName, WebportalParam.ob2MAC_Address);
        util.MyCommonAPIs.sleep(180 * 1000);
    }
    
    @Step("Test Step 3: Login to WP / Go to Location 1 where the central router is located")
    public void step3() {
        handle.gotoLoction();
    }

    @Step("Test Step 4: Go to Routers page / Create VPN group with routers in different locations / Check the group is added")
    public void step4() {
        
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup","TestDescription","1400");
        page.setPage2AddCentralRouter();
        page.setPage2AddRemoteRouter(WebportalParam.location3, WebportalParam.ob2deveiceName);
        page.setPage2AddRemoteRouter(WebportalParam.location2, WebportalParam.ob3deveiceName);
        page.clickNext();
        page.setPage3WirelessSettings(true,"PRJCBUGEN-T24754",1,"12345678");
        page.clickNext();
        page.clickNext(); //click next (actual is save btn)
        page.successicon.waitUntil(Condition.appear, 30000, 5000); // wait business vpn group setup successfully
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi();
        util.MyCommonAPIs.sleepsyncorbi();
        page.gotoPage();
        checkpoint = page.GroupExist("TestGroup", "PRJCBUGEN-T24754", WebportalParam.ob1deveiceName, "Healthy");
        if(!checkpoint) {
            util.MyCommonAPIs.sleepsyncorbi();
            page.gotoPage();
            checkpoint = page.GroupExist("TestGroup", "PRJCBUGEN-T24754", WebportalParam.ob1deveiceName, "Healthy");
        }
        assertTrue(checkpoint,"checkpoint 1 : group exists");
    }
}
