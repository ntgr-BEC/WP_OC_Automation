package webportal.Orbi.SSID_WAN.ConfigureBusinessVPN.VLAN.PRJCBUGEN_T25409;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.RoutersBusinessVPNPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesOrbiLanIPPage;
import webportal.param.WebportalParam;


/**
 *
 * @author jim.xie
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SSID_WAN.ConfigureBusinessVPN.RemoteRouter") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25409") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether user able to edit DHCP SERVER / LAN IP after adding remote router to the VPN group as Employee Home Mode") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25409") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("Teardown : delete VPN group");
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.DelVPNGroup("TestGroup");
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 1 where the central router is located")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 2: Go to Routers page / User create a VPN group with router mode set as employee home.")
    public void step2() {
        
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup","Test Description","1400");
        page.setPage2AddCentralRouter();
        page.setPage2AddRemoteRouter();
        
        page.clickNext();
        page.setPage3WirelessSettings(true, "PRJCBUGEN-T25409", 1, "12345678");
        page.clickNext();
        page.clickNext(); //click next (actual is save btn)
        util.MyCommonAPIs.waitReady();
        util.MyCommonAPIs.sleep(5000); // wait business vpn group setup successfully
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.sleepsyncorbi();
    }
    
    @Step("Test Step 3: verify the toggle functionality the DHCP server button")
    public void step3() {
        boolean checkpoint1 = false;
        boolean checkpoint;
        
        handle.gotoLoction(WebportalParam.location2);
        
        DevicesDashPage ddp = new DevicesDashPage();
        ddp.openOB2();
        
        
        DevicesOrbiLanIPPage page = new DevicesOrbiLanIPPage();
        if (page.alterMsg.exists()) {
            if (MyCommonAPIs.getText(page.alterMsg).contains("This router is currently managed by the Business VPN. You cannot change the settings for Employee Home router mode.")) {
                checkpoint1 = true;
            }
        }
        assertTrue(checkpoint1, "checkpoint 1 : Employee mode should has alrer message."); 
        
        page.setDHCP(false, false);
        checkpoint = page.saveButton.has(Condition.attribute("disabled"));
        assertTrue(checkpoint, "checkpoint 2 : Employee mode LAN IP can't edit.");
        
    }
    
}
