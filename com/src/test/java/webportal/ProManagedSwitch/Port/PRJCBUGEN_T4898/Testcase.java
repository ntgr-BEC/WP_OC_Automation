package webportal.ProManagedSwitch.Port.PRJCBUGEN_T4898;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import util.SwitchCLIUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesDashPageMNG;
import webportal.weboperation.DevicesSwitchConnectedNeighboursPortConfiqSettingsPage;
import webportal.weboperation.DevicesSwitchConnectedNeighboursPortConfiqSummaryPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredGroupPortConfigPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPage;

/**
 *
 * @author Sumanta
 *
 */
public class Testcase extends TestCaseBase implements Config {
    private static String expectRateLimitValue    = "";
    private static String expectStormControlValue = "";

    @Feature("Switch.Port") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T4898") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("021-Set rate limit for bcast,mcast,ucast at same time") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T4898") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    @Issue("PRJCBUGEN-14844")
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: enter setting page")
    public void step1() {
        // link up dut 1 port1
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.loginByUserPassword(WebportalParam.adminName,WebportalParam.adminPassword);
        handle.gotoLoction();
        WiredQuickViewPage wiredQuickViewPage = new WiredQuickViewPage();
    }

    @Step("Test Step 2: Set port1,port2 egress rate limit and storm control rate at random,port speed at 100")
    public void step2() {
        WiredVLANPage wiredVLANPage = new WiredVLANPage(false);
        WiredGroupPortConfigPage wiredGroupPortConfigPage = new WiredGroupPortConfigPage();
        wiredGroupPortConfigPage.multiSetting(SWITCH1_PORTARRAY, BATTCHSETTING1);
        expectRateLimitValue = wiredGroupPortConfigPage.rateLimitValue.replace("%", "").trim();
        expectStormControlValue = wiredGroupPortConfigPage.stromControlValue.replace("%", "").trim();
        System.out.println("expectRateLimitValue = "+ expectRateLimitValue );   
        System.out.println("expectStormControlValue = "+ expectStormControlValue );   
    }

    @Step("Test Step 3: Check configuration on webportal")
    public void step3() {
        // check sw1 on webportal
        MyCommonAPIs.sleepi(120);
        for (int i = 0; i < SW1PORT.length; i++) {
            DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
            DevicesSwitchSummaryPage devicesSwitchSummaryPage = devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
            DevicesSwitchConnectedNeighboursPortConfiqSummaryPage devicesSwitchConnectedNeighboursPortConfiqSummaryPage = devicesSwitchSummaryPage
                    .enterPortConfigSummary(SW1PORT[i]);
            DevicesSwitchConnectedNeighboursPortConfiqSettingsPage devicesSwitchConnectedNeighboursPortConfiqSettingsPage = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
            String egressRate = devicesSwitchConnectedNeighboursPortConfiqSettingsPage.getEgressRateValue();
            Selenide.refresh();
            String stormControlRate = devicesSwitchConnectedNeighboursPortConfiqSettingsPage.getStormControlRateValue();
            String portSpeed = devicesSwitchConnectedNeighboursPortConfiqSettingsPage.getPortSpeed(); 
            System.out.println("egressRate = "+ egressRate );   
            System.out.println("stormControlRate = "+ stormControlRate );            
            if (expectRateLimitValue.contains(egressRate) && expectStormControlValue.contains(stormControlRate)
                    && devicesSwitchConnectedNeighboursPortConfiqSettingsPage.cmpPortSpeed(PORT_SPEED, portSpeed)) {
                micResult = true;
            } else {
                micResult = false;
                assertTrue(micResult,
                        "actual egressRate is: " + egressRate + ", stormControlRate value is: " + stormControlRate + "port speed is: " + portSpeed);
            }
        }
    }

    @Step("Test Step 4: Check configuration on CLI")
    public void step4() {
        // check on sw1 CLI
        MyCommonAPIs.sleepi(180);
        for (int i = 0; i < SW1PORTCLI.length; i++) {
            String portall = SwitchCLIUtils.getPortInfo(SW1PORTCLI[i]);
            if (SwitchCLIUtils.PortClass.sPortEgressRate.contains(expectRateLimitValue)) {
                micResult = true;
            } else {
                micResult = false;
                assertTrue(micResult,
                        "actualRate is " + SwitchCLIUtils.PortClass.sPortEgressRate + ", expect egress value  is" + expectRateLimitValue);
            }
            if (SwitchCLIUtils.PortClass.sPortStormControlRate.contains(expectStormControlValue)) {
                micResult = true;
            } else {
                micResult = false;
                assertTrue(micResult, "actual Rate is " + SwitchCLIUtils.PortClass.sPortStormControlRate
                        + ",but expect strom Control Rate value  is" + expectStormControlValue);
            }
        }

        String portSpeed = SwitchCLIUtils.getPortInfo(WebportalParam.sw1LagPort1CLI);
        if (SwitchCLIUtils.PortClass.sPortSpeed.contains("100")) {
            micResult = true;
        } else {
            micResult = false;
            assertTrue(micResult, "actual Rate is " + portSpeed + ", expect portSpeed value  is: " + PORTSPEED_CLI);
        }
    }

    @Step("Test Step 5: change port  egress rate to Auto, Set port1 ,port2 ingress rate limit ,egress rate limit and storm control rate at 100%")
    public void step5() {
        WiredVLANPage wiredVLANPage = new WiredVLANPage(false);
        WiredGroupPortConfigPage wiredGroupPortConfigPage = new WiredGroupPortConfigPage();
        wiredGroupPortConfigPage.multiSetting(SWITCH1_PORTARRAY, BATTCHSETTING2);
        expectRateLimitValue = wiredGroupPortConfigPage.rateLimitValue.replace("%", "").trim();
        expectStormControlValue = wiredGroupPortConfigPage.stromControlValue.replace("%", "").trim();
    }

    @Step("Test Step 6: Check configuration on webportal")
    public void step6() {
        // check sw1 on webportal
        for (int i = 0; i < SW1PORT.length; i++) {
            DevicesDashPageMNG devicesDashPage = new DevicesDashPageMNG();
            DevicesSwitchSummaryPage devicesSwitchSummaryPage = devicesDashPage.enterDevicesSwitchSummary(WebportalParam.sw1serialNo);
            DevicesSwitchConnectedNeighboursPortConfiqSummaryPage devicesSwitchConnectedNeighboursPortConfiqSummaryPage = devicesSwitchSummaryPage
                    .enterPortConfigSummary(SW1PORT[i]);
            DevicesSwitchConnectedNeighboursPortConfiqSettingsPage devicesSwitchConnectedNeighboursPortConfiqSettingsPage = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
            String egressRate = devicesSwitchConnectedNeighboursPortConfiqSettingsPage.getEgressRateValue();
            Selenide.refresh();
            String stormControlRate = devicesSwitchConnectedNeighboursPortConfiqSettingsPage.getStormControlRateValue();
            String portSpeed = devicesSwitchConnectedNeighboursPortConfiqSettingsPage.getPortSpeed();
            if (expectRateLimitValue.contains(egressRate) && expectStormControlValue.contains(stormControlRate)
                    && portSpeed.equals(PORT_SPEED_RESTORE)) {
                micResult = true;
            } else {
                micResult = false;
                assertTrue(micResult,
                        "actual egressRate is" + egressRate + ", stormControlRate value is" + stormControlRate + "port speed is " + portSpeed);
            }
        }
    }

    @Step("Test Step 7: Check configuration on CLI")
    public void step7() {
        // check on sw1 CLI
        MyCommonAPIs.sleepi(180);
        for (int i = 0; i < SW1PORTCLI.length; i++) {
            String portall = SwitchCLIUtils.getPortInfo(SW1PORTCLI[i]);
            if (SwitchCLIUtils.PortClass.sPortEgressRate.contains(expectRateLimitValue)) {
                micResult = true;
            } else {
                micResult = false;
                assertTrue(micResult,
                        "actualRate is " + SwitchCLIUtils.PortClass.sPortEgressRate + ", expect egress value  is" + expectRateLimitValue);
            }

            if (SwitchCLIUtils.PortClass.sPortStormControlRate.contains(expectStormControlValue)) {
                micResult = true;
            } else {
                micResult = false;
                assertTrue(micResult, "actual Rate is " + SwitchCLIUtils.PortClass.sPortStormControlRate
                        + ",but expect strom Control Rate value  is" + expectStormControlValue);
            }
        }
    }

    @AfterMethod(alwaysRun = true)
    public void restore() {
        System.out.println("start to do restore");
        // Selenide.refresh();
        // MyCommonAPIs.sleep(3000);
        // DevicesSwitchConnectedNeighboursPortConfiqSettingsPage devicesSwitchConnectedNeighboursPortConfiqSettingsPage
        // = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
        // devicesSwitchConnectedNeighboursPortConfiqSettingsPage.modifyEgressRate(EGRESS_MAX);
    }
}
