package webportal.LongRunStability.PRJCBUGEN_T9022;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import util.SQLiteStability;
import webportal.param.WebportalParam;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    final String    tcName       = "[T9022] Check BR500 Router device info on Cloud";
    String          stepInfo     = "";
    String          expectResult = "";
    String          actualResult = "";
    SQLiteStability dbHandle     = null;

    @Feature("LongRunStability") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T9022") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description(tcName) // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T9022") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        dbHandle = new SQLiteStability();
        runTest(this);
    }

    @Step("Test Step 1: Log into Web Portal")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
    }

    @Step("Test Step 2: Go to network1, check Router Status is connected;")
    public void step2() {
        String sPoESw = null;
        stepInfo = "Check Router Status is connected in 1st network";
        handle.gotoLoction(WebportalParam.location1);
        ddp.gotoPage();
        String apNo = ddp.getBRDevice();
        expectResult = "Connected";
        actualResult = "Disconnected";
        try {
            actualResult = ddp.getDeviceStatus(apNo);
        } catch (Throwable e) {
            e.printStackTrace();
            MyCommonAPIs.takess();
        }
        if (actualResult.equals(expectResult)) {
            dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        } else {
            dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
        }

        stepInfo = "Check Router uptime in 1st network";
        expectResult = "Not NA";
        actualResult = "NA";
        try {
            actualResult = ddp.getDeviceUptime(apNo, false);
        } catch (Throwable e) {
            e.printStackTrace();
            MyCommonAPIs.takess();
        }
        if (!actualResult.equals("NA")) {
            dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        } else {
            dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
        }

        stepInfo = "Check Router VLANs In Use info (vlan1) in 1st network";
        expectResult = "Management VLAN";
        actualResult = "empty vlan list";
        try {
            DevicesSwitchSummaryPage dssp = ddp.enterDevicesSwitchSummary(apNo, 1);
            brdvp.gotoPage();
            List<String> vlans = brdvp.getVlans();
            actualResult = vlans.toString();
        } catch (Throwable e) {
            e.printStackTrace();
            MyCommonAPIs.takess();
        }
        if (actualResult.contains(expectResult)) {
            dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        } else {
            dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
        }

        stepInfo = "Check Router VLANs In Use info (vlan2) in 1st network";
        expectResult = "vlan2";
        if (actualResult.toLowerCase().contains(expectResult)) {
            dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        } else {
            dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
        }

        stepInfo = "Check Router DHCP setting in 1st network";
        expectResult = "lan size > 0";
        actualResult = "0";
        try {
            brddchps.gotoPage();
            List<String> ips = brddchps.getListIP();
            actualResult = ips.toString();
            if (ips.size() > 0) {
                dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
            } else {
                dbHandle.writeResultFail(tcName, stepInfo, expectResult, "No dhcp lan found");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            MyCommonAPIs.takess();
        }

        stepInfo = "Check Router Client page in 1st network";
        List<String> clis = new ArrayList<String>();
        expectResult = "client size > 0";
        try {
            brdcp.gotoPage();
            clis = brdcp.getClients();
            actualResult = "client size = " + clis.size();
        } catch (Throwable e) {
            e.printStackTrace();
            MyCommonAPIs.takess();
        }
        if (clis.size() > 0) {
            dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        } else {
            dbHandle.writeResultFail(tcName, stepInfo, expectResult, "No client found");
        }

        // stepInfo = "Check Router VPN Group page in 1st network";
        // expectResult = "All links are healthy";
        // actualResult = "Some links are broken";
        // boolean vpnStatus = false;
        // try {
        // brdvgp.gotoPage();
        // vpnStatus = brdvgp.isAllLinkHealthy();
        // } catch (Throwable e) {
        // e.printStackTrace();
        // }
        // if (vpnStatus) {
        // dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        // } else {
        // dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
        // }
    }

    @Step("Test Step 3: Go to network2, check Router Status is connected;")
    public void step3() {
        String sPoESw = null;
        stepInfo = "Check Router Status is connected in 2nd network";
        handle.gotoLoction(WebportalParam.location2);
        ddp.gotoPage();
        String apNo = ddp.getBRDevice();
        expectResult = "Connected";
        actualResult = "Disconnected";
        try {
            actualResult = ddp.getDeviceStatus(apNo);
        } catch (Throwable e) {
            e.printStackTrace();
            MyCommonAPIs.takess();
        }
        if (actualResult.equals(expectResult)) {
            dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        } else {
            dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
        }

        stepInfo = "Check Router uptime in 2nd network";
        expectResult = "Not NA";
        actualResult = "NA";
        try {
            actualResult = ddp.getDeviceUptime(apNo, false);
        } catch (Throwable e) {
            e.printStackTrace();
            MyCommonAPIs.takess();
        }
        if (!actualResult.equals("NA")) {
            dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        } else {
            dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
        }

        stepInfo = "Check Router VLANs In Use info (vlan1) in 2nd network";
        expectResult = "Management VLAN";
        actualResult = "empty vlan list";
        try {
            DevicesSwitchSummaryPage dssp = ddp.enterDevicesSwitchSummary(apNo, 1);
            brdvp.gotoPage();
            List<String> vlans = brdvp.getVlans();
            actualResult = vlans.toString();
        } catch (Throwable e) {
            e.printStackTrace();
            MyCommonAPIs.takess();
        }
        if (actualResult.contains(expectResult)) {
            dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        } else {
            dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
        }

        stepInfo = "Check Router VLANs In Use info (vlan2) in 2nd network";
        expectResult = "vlan2";
        if (actualResult.toLowerCase().contains(expectResult)) {
            dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        } else {
            dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
        }

        stepInfo = "Check Router DHCP setting in 2nd network";
        expectResult = "lan size > 0";
        actualResult = "0";
        try {
            brddchps.gotoPage();
            List<String> ips = brddchps.getListIP();
            actualResult = ips.toString();
            if (ips.size() > 0) {
                dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
            } else {
                dbHandle.writeResultFail(tcName, stepInfo, expectResult, "No dhcp lan found");
            }
        } catch (Throwable e) {
            e.printStackTrace();
            MyCommonAPIs.takess();
        }

        stepInfo = "Check Router Client page in 2nd network";
        List<String> clis = new ArrayList<String>();
        expectResult = "client size > 0";
        try {
            brdcp.gotoPage();
            clis = brdcp.getClients();
            actualResult = "client size = " + clis.size();
        } catch (Throwable e) {
            e.printStackTrace();
            MyCommonAPIs.takess();
        }
        if (clis.size() > 0) {
            dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        } else {
            dbHandle.writeResultFail(tcName, stepInfo, expectResult, "No client found");
        }

        // stepInfo = "Check Router VPN Group page in 2nd network";
        // expectResult = "All links are healthy";
        // actualResult = "Some links are broken";
        // boolean vpnStatus = false;
        // try {
        // brdvgp.gotoPage();
        // vpnStatus = brdvgp.isAllLinkHealthy();
        // } catch (Throwable e) {
        // e.printStackTrace();
        // }
        // if (vpnStatus) {
        // dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        // } else {
        // dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
        // }
    }
}
