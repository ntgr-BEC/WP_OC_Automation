package webportal.Orbi.SSID_WAN.ConfigureBusinessVPN.GraphView.PRJCBUGEN_T25908;

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
import webportal.param.WebportalParam;


/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SSID_WAN.ConfigureBusinessVPN.GraphView") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25908") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify whether user able to see the different legends in Router -> Settings --> Business VPN page") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25908") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
        try {
            RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
            page.gotoPage();
            page.DelVPNGroup("TestGroup");
        } catch (Throwable e) {
            // TODO: handle exception
        }
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 1 where the central router is located")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 2: Go to Routers page / After adding a group, legends are shown in graph view")
    public void step2() {
        
        boolean checkpoint;
        RoutersBusinessVPNPage page = new RoutersBusinessVPNPage();
        page.gotoPage();
        page.cirbtnGraphView.click();
        page.clickAddVPNGroup();
        page.setPage1VPNGroupInfo("TestGroup","TestDescription","1400");
        page.setPage2AddCentralRouter();
        page.setPage2AddRemoteRouter();
        page.clickNext();
        page.setPage3WirelessSettings(true,"SSID-WAN-Auto-Test",1,"12345678");
        page.clickNext();
        page.clickNext(); //click next (actual is save btn)
        page.successicon.waitUntil(Condition.appear, 30000, 5000); // wait business vpn group setup successfully
        page.btnViewVPNGroup.click();
        util.MyCommonAPIs.waitReady();
        
        SelenideElement arrow = $x("//div[@class='toplogySettingCol']//img[contains(@src,'arrow.png')]");
        arrow.click();
        SelenideElement legend = $x("//p[text()='Legend']");
        SelenideElement device = $x("//p[text()='Device Connectivity to Cloud']");
        SelenideElement router = $x("//p[text()='Router Types']");
        SelenideElement connection = $x("//p[text()='VPN Connection']");
        checkpoint = legend.exists() && device.exists() && router.exists() && connection.exists();
        assertTrue(checkpoint, "checkpoint 1 : Texts exist");
        
        SelenideElement greenring = $x("//img[contains(@src,'legend-ring-connected')]");
        SelenideElement redring = $x("//img[contains(@src,'legend-ring-disconnected')]");
        SelenideElement centralrouter = $x("//img[contains(@src,'central-router-legend')]");
        SelenideElement remoterouter = $x("//img[contains(@src,'remote-router-legend')]");
        checkpoint = greenring.exists() && redring.exists() && centralrouter.exists() && remoterouter.exists();
        assertTrue(checkpoint, "checkpoint 2 : Images exist");
        
        SelenideElement linkup = $x("//p[text()='Link is Up']/../div[contains(@class,'backYellow')]");
        SelenideElement linkdown = $x("//p[text()='Link is Down']/../div[contains(@class,'bordercolorRed')]");
        SelenideElement linkinprogrss = $x("//p[text()='Link setup is in Progress']/../div[contains(@class,'STPBlocked')]");
        SelenideElement p2p = $x("//p[text()='Peer to Peer']/../div[contains(@class,'backGreen')]");
        checkpoint = linkup.exists() && linkdown.exists() && linkinprogrss.exists() && p2p.exists();
        assertTrue(checkpoint, "checkpoint 3 : VPN connection status match line color");
        
        util.MyCommonAPIs.sleepsyncorbi();
        
    }
}
