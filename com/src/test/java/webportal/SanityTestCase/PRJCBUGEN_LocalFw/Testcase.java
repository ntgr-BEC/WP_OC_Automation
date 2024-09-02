package webportal.SanityTestCase.PRJCBUGEN_LocalFw;

import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.APUtils;
import util.BRUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    /**
     * fw1 old version, fw2 new version, both must have version number
     */
    String  fw1             = "http://172.16.255.12:8765/switch/beta/GS728_752TP_TPP_V21.32.42.1.bix";
    String  fw2             = "http://172.16.255.12:8765/switch/beta/GS728_752TP_TPP_V21.32.42.1.bix";
    /**
     * 0 - sw, 1 - ap, 2 - br, 3 - orbi
     */
    int     devType         = 0;
    boolean isTwoImage      = true;
    int     passUpdateCount = 0;
    int     failUpdateCount = 0;

    @Feature("SanityTestCase") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_LocalFW") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Repeatly do firmware update to check device can be online in cloud with local fw") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-LocalFW") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true) // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    public void updatefw(String fw) {
        boolean bUpdated;
        if (devType == 1) {
            bUpdated = new APUtils(WebportalParam.ap1IPaddress).updateFirmwareOld(fw);
        } else if (devType == 2) {
            bUpdated = new BRUtils().updateSystemFirmware(fw, 0);
        } else if (devType == 3) {
            bUpdated = new BRUtils().updateSystemFirmware(fw, 1);
        } else {
            bUpdated = new BRUtils().updateSystemFirmware(fw, 2);
        }

        if (bUpdated) {
            passUpdateCount++;
            System.out.println("device is downgraded via local - ok");
        }
    }

    // Each step is a single test step from Jira Test Case
    @Step("Repeatly do reboot to check device can be online in cloud")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
        
        String devSerialNo = WebportalParam.sw1serialNo;
        if (devType == 1) {
            devSerialNo = WebportalParam.ap1serialNo;
        } else if (devType == 2) {
            devSerialNo = WebportalParam.br1serialNo;
        } else if (devType == 3) {
            devSerialNo = WebportalParam.ob1serialNo;
        }
        
        while (true) {
            System.out.println(String.format("==Summary: update count(%s)/fail count(%s)", passUpdateCount, failUpdateCount));
            ddp.gotoPage();
            if (ddp.waitDevicesReConnected(devSerialNo)) {
                updatefw(fw1);
                if (isTwoImage) {
                    if (ddp.waitDevicesReConnected(devSerialNo)) {
                        updatefw(fw2);
                    } else {
                        failUpdateCount++;
                        System.out.println("device is offline before do firmware2, try again");
                    }
                }
            } else {
                failUpdateCount++;
                System.out.println("device is offline before do firmware1, try again");
            }
        }
    }
}
