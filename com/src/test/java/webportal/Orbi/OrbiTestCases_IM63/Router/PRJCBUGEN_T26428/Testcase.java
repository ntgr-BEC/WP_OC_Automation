package webportal.Orbi.OrbiTestCases_IM63.Router.PRJCBUGEN_T26428;

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
import util.Javasocket;
import util.MyCommonAPIs;
import webportal.weboperation.WebportalLoginPage;
import webportal.param.WebportalParam;
import webportal.weboperation.SummaryPage;
import webportal.weboperation.DevicesOrbiSummaryPage;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.DevicesOrbiTroubleshootPage;
import webportal.weboperation.DevicesOrbiWifiNetworkPage;
import webportal.publicstep.UserManage;
import orbi.weboperation.OrbiLoginPage;
import orbi.webelements.DNIOrbiCommanElement;
import orbi.webelements.OrbiAllMenueElements;
import orbi.webelements.OrbiBasicWirelessElements;

/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("Orbi.OrbiTestCases_IM63") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T26428") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the VLAN profile functionality for the employee type SSID") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T26428") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("Restore SSID2 vlan to disabled");
        try {
            new WebportalLoginPage(true).defaultLogin();
            handle.gotoLoction();
            ddp.gotoPage();
            ddp.openOB2();
            DevicesOrbiWifiNetworkPage page = new DevicesOrbiWifiNetworkPage();
            page.editSsid(1);
            page.setSSIDVLANProfile(false);
            page.btnSave.click();
            MyCommonAPIs.sleepsyncorbi();
        } catch (Throwable e) {
            System.out.println("Failed to restore SSID2 vlan to disabled");
        }
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 1 / Go to Devices dash page")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
        ddp.gotoPage();
    }

    @Step("Test Step 2: Enter device page / Change SSID2 vlan")
    public void step2() {
        boolean checkpoint;
        ddp.openOB2();

        DevicesOrbiWifiNetworkPage page = new DevicesOrbiWifiNetworkPage();
        page.editSsid(1);
        page.setSsidName("PRJCBUGEN-T26428");
        page.setSsidSecurity(1, "12345678");
        page.setSSIDVLANProfile(true);
        page.setSsidVlanProfile("Employee");
        page.btnSave.click();
        MyCommonAPIs.sleepsyncorbi();

        UserManage userManage = new UserManage();
        userManage.logout();
    }

    @Step("Test Step 3: Check client can get wifi2 vlan ip")
    public void step3() {
        boolean checkpoint = true;
        String cmd = String.format("WAFconnect %s %s WPA2PSK aes", "PRJCBUGEN-T26428", "12345678");
        if (!new Javasocket().sendCommandToWinClient(WebportalParam.client2ip, WebportalParam.client2port, cmd).equals("true")) {
            checkpoint = false;
        }
        System.out.println(checkpoint);
        assertTrue(checkpoint, "checkpoint : client can connect to orbi's wifi2");
        
        String client_ip = new Javasocket().sendCommandToWinClient(WebportalParam.client2ip, WebportalParam.client2port, "WAFgetip Wi-Fi");
        String[] splited = client_ip.split("\\s+");
        client_ip = splited[1];
        assertTrue(client_ip.contains("192.168.20"), "checkpoint : client can get ip from employee vlan (vlan 20)");
    }

}
