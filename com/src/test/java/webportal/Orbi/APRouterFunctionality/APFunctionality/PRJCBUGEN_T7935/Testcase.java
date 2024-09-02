package webportal.Orbi.APRouterFunctionality.APFunctionality.PRJCBUGEN_T7935;

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
import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.publicstep.PublicButton;
import webportal.weboperation.DevicesOrbiDeviceModePage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    PublicButton publicButton = new PublicButton();
    
    @Feature("APRouterFunctionality.APFunctionality") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T7935") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Test to verify the user is able to share logs via entering e-mail address in share diagnostics tile.")
    @TmsLink("PRJCBUGEN-T7935") // It's a testcase id/link from Jira Test Case.
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
        ddp.openOBDevice();
        new DevicesOrbiDeviceModePage(false).initDeviceMode(true);
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
        String mailTo = "tec.wpauto" + RandomStringUtils.randomNumeric(4) + "@mailinator.com";
        publicButton.ShareDiagnostics(mailTo);
        
        MyCommonAPIs.sleepi(120);
        
        MailHandler mailcatchPage = new MailHandler(mailTo);
        
        mailcatchPage.enterFirstEmail();
        String mailBody = mailcatchPage.getHTMLBody();
        if (mailBody.contains(WebportalParam.ob2deveiceName)) {
            micResult = true;
            
        }
        assertTrue(micResult, "check device name");
    }
}
