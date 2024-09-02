package webportal.LongRunStability.PRJCBUGEN_T9018;

import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import util.RunCommand;
import util.SQLiteStability;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 *
 * @author lavi
 *
 */
public class Testcase extends TestCaseBase {
    final String    tcName       = "[T9018] Verify PC can reach all devices through Insight VPN";
    String          stepInfo     = "";
    String          expectResult = "";
    String          actualResult = "";
    SQLiteStability dbHandle     = null;
    RunCommand      cmd          = null;
    int             pingVal      = 5, iVal;
    String          sNO, sIP;

    @Feature("LongRunStability") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T9018") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description(tcName) // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T9018") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        dbHandle = new SQLiteStability();
        cmd = new RunCommand();
        runTest(this);
    }

    @Step("Test Step 1: Log into Web Portal")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
    }

    @Step("Test Step 2: Go to network1, check Ping all Devices")
    public void step2() {
        handle.gotoLoction(WebportalParam.location1);
        ddp.gotoPage();

        expectResult = String.format("less than %s%% packet loss", pingVal);
        stepInfo = "Ping NAS device: <unknown>";
        try {
            sNO = ddp.getSWDevice();
            sIP = ddp.getDeviceIP(sNO);
            stepInfo = "Ping NAS device: " + sIP;
            iVal = cmd.RunPing(sIP, 3, null);
            actualResult = "=" + iVal;
        } catch (Throwable e) {
            e.printStackTrace();
            MyCommonAPIs.takess();
            iVal = 100;
        }
        if (iVal < pingVal) {
            dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        } else {
            dbHandle.writeResultFail(tcName, stepInfo, expectResult, String.format("lost rate is: %s%%", iVal));
        }

        stepInfo = "Ping AP device: <unknown>";
        try {
            sNO = ddp.getAPDevice();
            sIP = ddp.getDeviceIP(sNO);
            stepInfo = "Ping AP device: " + sIP;
            iVal = cmd.RunPing(sIP, 3, null);
            actualResult = "=" + iVal;
        } catch (Throwable e) {
            e.printStackTrace();
            MyCommonAPIs.takess();
            iVal = 100;
        }
        if (iVal < pingVal) {
            dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        } else {
            dbHandle.writeResultFail(tcName, stepInfo, expectResult, String.format("lost rate is: %s%%", iVal));
        }

        stepInfo = "Ping Switch device: <unknown>";
        try {
            sNO = ddp.getSWDevice();
            sIP = ddp.getDeviceIP(sNO);
            stepInfo = "Ping Switch device: " + sIP;
            iVal = cmd.RunPing(sIP, 3, null);
            actualResult = "=" + iVal;
        } catch (Throwable e) {
            e.printStackTrace();
            MyCommonAPIs.takess();
            iVal = 100;
        }
        if (iVal < pingVal) {
            dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        } else {
            dbHandle.writeResultFail(tcName, stepInfo, expectResult, String.format("lost rate is: %s%%", iVal));
        }

        sIP = "192.168.11.1";// ddp.getDeviceIP(sNO);
        stepInfo = "Ping BR500 device: " + sIP;
        try {
            sNO = ddp.getBRDevice();
            iVal = cmd.RunPing(sIP, 3, null);
            actualResult = "=" + iVal;
        } catch (Throwable e) {
            e.printStackTrace();
            MyCommonAPIs.takess();
            iVal = 100;
        }
        if (iVal < pingVal) {
            dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        } else {
            dbHandle.writeResultFail(tcName, stepInfo, expectResult, String.format("lost rate is: %s%%", iVal));
        }
    }

    @Step("Test Step 3: Go to network2, check Ping all Devices")
    public void step3() {
        handle.gotoLoction(WebportalParam.location2);
        ddp.gotoPage();

        stepInfo = "Ping Vpn side Switch device: <unknown>";
        try {
            sNO = ddp.getSWDevice();
            sIP = ddp.getDeviceIP(sNO);
            stepInfo = "Ping Vpn side Switch device: " + sIP;
            iVal = cmd.RunPing(sIP, 3, null);
            actualResult = "=" + iVal;
        } catch (Throwable e) {
            e.printStackTrace();
            MyCommonAPIs.takess();
            iVal = 100;
        }
        if (iVal < pingVal) {
            dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        } else {
            dbHandle.writeResultFail(tcName, stepInfo, expectResult, String.format("lost rate is: %s%%", iVal));
        }
        
        stepInfo = "Ping Vpn side AP device: <unknown>";
        try {
            sNO = ddp.getAPDevice();
            sIP = ddp.getDeviceIP(sNO);
            stepInfo = "Ping Vpn side AP device: " + sIP;
            iVal = cmd.RunPing(sIP, 3, null);
            actualResult = "=" + iVal;
        } catch (Throwable e) {
            e.printStackTrace();
            MyCommonAPIs.takess();
            iVal = 100;
        }
        if (iVal < pingVal) {
            dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        } else {
            dbHandle.writeResultFail(tcName, stepInfo, expectResult, String.format("lost rate is: %s%%", iVal));
        }

        // gateway has some issue
        // sIP = "192.168.133.1";// ddp.getDeviceIP(sNO);
        // stepInfo = "Ping Vpn side BR500 device: " + sIP;
        // try {
        // sNO = ddp.getBRDevice();
        // iVal = cmd.RunPing(sIP, 3, null);
        // actualResult = "=" + iVal;
        // } catch (Throwable e) {
        // e.printStackTrace();
        // MyCommonAPIs.takess();
        // iVal = 100;
        // }
        // if (iVal < pingVal) {
        // dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        // } else {
        // dbHandle.writeResultFail(tcName, stepInfo, expectResult, String.format("lost rate is: %s%%", iVal));
        // }
    }

    @Step("Test Step 4: check Ping vpn Devices")
    public void step4() {
        if (!sIP.contains("133.")) {
            sIP = "192.168.133.2";
        }
        stepInfo = "Ping VPN Site2Site Device: " + sIP + " in 10 minutes";
        if (RunCommand.isHostAlive(sIP, 80)) {
            iVal = cmd.RunPing(sIP, 60 * 10, null);
            actualResult = "=" + iVal;
        } else {
            iVal = 100;
        }

        if (iVal < pingVal) {
            dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        } else {
            dbHandle.writeResultFail(tcName, stepInfo, expectResult, String.format("lost rate is: %s%%", iVal));
        }
    }

}
