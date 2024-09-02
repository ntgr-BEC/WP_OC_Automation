package webportal.BR.BRBackupRestore.PRJCBUGEN_T16925;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.BRUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.DeviceBackupRestorePage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String vlanId        = "2925";
    String dhcpIp        = "192.168.125.1";
    String backStoreName = "BRdefaultconfig";
    String backStoreDesc = "BR default configuration";

    @Feature("BR.BRBackupRestore") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16925") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("001-Backup BR default configuration then restore") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T16925") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DeviceBackupRestorePage page = new DeviceBackupRestorePage();
        page.gotoPage();
        page.deleteBackups();
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login IM APP success;")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();

        handle.gotoLoction();
        wvp.gotoPage();
        wvp.deleteAllVlan();

        ddp.gotoPage();
        ddp.openBR1();
    }

    @Step("Test Step 2: Create a Backup,then backup")
    public void step2() {
        DeviceBackupRestorePage page = new DeviceBackupRestorePage();
        page.gotoPage();
        page.deleteBackups();
        
        page.createBackup(backStoreName, backStoreDesc);
    }
    
    @Step("Test Step 3: On BR local,change configuration -- add a vlan")
    public void step3() {
        new BRUtils().createDHCP(vlanId, dhcpIp);
        ddp.gotoPage();
        ddp.waitDevicesReConnected(WebportalParam.br1serialNo);
    }
    
    @Step("Test Step 4: On insight,restore the default configuraion")
    public void step4() {
        DeviceBackupRestorePage page = new DeviceBackupRestorePage();
        page.gotoPage("br");
        page.restoreBackup(backStoreName);
    }

    @Step("Test Step 5: On BR local,the configuration is changed to default")
    public void step5() {
        handle.waitRestReady(BRUtils.api_lan_subnet_stats, dhcpIp, true, 0);
        assertTrue(!new BRUtils().Dump().contains(dhcpIp), "vlan created on locall must be removed after restore");
    }
}
