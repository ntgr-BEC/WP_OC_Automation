package webportal.LongRunStability.PRJCBUGEN_T9019;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.FtpClient;
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
    final String    tcName       = "[T9019] PC download&upload files from NAS";
    String          stepInfo     = "";
    String          expectResult = "";
    String          actualResult = "";
    SQLiteStability dbHandle     = null;
    String          sNAS         = null;
    String          sNASIP       = null;

    @Feature("LongRunStability") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T9019") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description(tcName) // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T9019") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        dbHandle = new SQLiteStability();
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
    }

    @Step("Test Step 1: Log into Web Portal")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
    }

    @Step("Test Step 2: Locate one NAS IP")
    public void step2() {
        handle.gotoLoction(WebportalParam.location1);
        ddp.gotoPage();
        handle.gotoLoction(WebportalParam.location1);
        ddp.gotoPage();
        sNAS = ddp.getNASDevice();
        sNASIP = ddp.getDeviceIP(sNAS);
        stepInfo = "Check whether NAS: " + sNASIP + " is online.";
        expectResult = "Ip is pingable";
        actualResult = "Ip is not reachable";
        if (!RunCommand.isHostAlive(sNASIP, 80)) {
            dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
            throw new RuntimeException(sNASIP + " is " + actualResult);
        }
    }

    @Step("Test Step 3: Upload a file to NAS")
    public void step3() {
        stepInfo = "Check the file size of to upload a big file to NAS: " + sNASIP;
        FtpClient ftp = new FtpClient("admin", WebportalParam.loginDevicePassword, sNASIP);
        ftp.UploadFile();

        long localfile = ftp.getLocalFileSize(false);
        long remotefile = ftp.getRemoteFileSize();
        expectResult = String.format("Local file size is: %s", localfile);
        actualResult = String.format("Remote file size is: %s", remotefile);
        if (remotefile == 0) {
            actualResult = "Failed to upload file to NAS";
        }
        if (localfile == remotefile) {
            dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        } else {
            dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
        }
    }

    @Step("Test Step 4: Download a file from NAS")
    public void step4() {
        stepInfo = "Check the file size of to download a big file from NAS: " + sNASIP;
        FtpClient ftp = new FtpClient("admin", WebportalParam.loginDevicePassword, sNASIP);
        ftp.DownloadFile();

        long localfile = ftp.getLocalFileSize(true);
        long remotefile = ftp.getRemoteFileSize();
        expectResult = String.format("Local file size is: %s", localfile);
        actualResult = String.format("Remote file size is: %s", remotefile);
        if (remotefile == 0) {
            actualResult = "Failed to download file from NAS";
        }
        if (remotefile != 0 && localfile == remotefile) {
            dbHandle.writeResultPass(tcName, stepInfo, expectResult, actualResult);
        } else {
            dbHandle.writeResultFail(tcName, stepInfo, expectResult, actualResult);
        }
    }

}
