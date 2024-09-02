package webportal.Orbi.SXK30.PRJCBUGEN_T25015;

import static org.testng.Assert.assertTrue;

import org.apache.commons.lang.RandomStringUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MailHandler;
import webportal.publicstep.PublicButton;
import webportal.weboperation.DevicesOrbiDeviceModePage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author ann.fang
 */
public class Testcase extends TestCaseBase {
    PublicButton publicButton = new PublicButton();
    
    @Feature("APRouterFunctionality.RouterFunctionality") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25015") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Share Diagnostics")
    @TmsLink("PRJCBUGEN-T25015") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Login to WP/Select one Location/Click \"Device\" button")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        
        handle.gotoLoction();
        
        ddp.gotoPage();
    }
    
    @Step("Test Step 2: Choose online device, click \"Edit\" button")
    public void step2() {
        ddp.openOB2();
        new DevicesOrbiDeviceModePage(false).initDeviceMode(false);
    }
    
    @Step("Test Step 3: Input \"space\" in the Email area.")
    public void step3() {
        publicButton.ShareDiagnostics(" ");
        assertTrue(publicButton.shareError.getText().length() > 0, "See error on space");
        handle.refresh();
    }
    
    @Step("Test Step 4: Input \"ss\" in the Email area.")
    public void step4() {
        publicButton.ShareDiagnostics("ss");
        assertTrue(publicButton.shareError.getText().length() > 0, "See error on space");
        handle.refresh();
    }
    
    @Step("Test Step 5: Input \"shanshan.suo@netgear.com\" in the Email area")
    public void step5() {
        String mailTo = "njqa.wp" + RandomStringUtils.randomNumeric(4) + "@mailcatch.com";
        publicButton.ShareDiagnostics(mailTo);
        
        MailHandler mailcatchPage = new MailHandler(mailTo);
        
        mailcatchPage.enterFirstEmail();
        String mailBody = mailcatchPage.getHTMLBody();
        if (mailBody.contains("Device Name") && mailBody.contains("Network Name") && mailBody.contains("Serial No") && mailBody.contains("Model")
                && mailBody.contains("IP Address")) {
            micResult = true;
            
        }
        assertTrue(micResult, "check device name, network name, sno, model, ip");
    }
}
