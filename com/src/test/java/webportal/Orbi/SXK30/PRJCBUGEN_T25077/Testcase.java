package webportal.Orbi.SXK30.PRJCBUGEN_T25077;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.apache.commons.lang.RandomStringUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MailHandler;
import util.MyCommonAPIs;
import webportal.weboperation.WebportalLoginPage;
import webportal.param.WebportalParam;
import webportal.weboperation.TopologyPage;



/**
 *
 * @author ann.fang
 *
 */
public class Testcase extends TestCaseBase {
    @Feature("SXK30") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25077") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Topology  -Share Diagnostics for SXK30") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25077") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 1")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 2: Go to topology page / Hover on the router / Click more details / Share diagnostics and check if received")
    public void step2() {
        
        TopologyPage page = new TopologyPage();
        page.gotoPage();
        page.NodeImage(WebportalParam.ob2deveiceName).hover();
        MyCommonAPIs.sleepi(2);
        page.moredetail.click();
        MyCommonAPIs.sleepi(2);
        page.shareicon.click();
        MyCommonAPIs.sleepi(2);
        
        String mailTo = "njqa.wp" + RandomStringUtils.randomNumeric(4) + "@mailcatch.com";
        page.inputemail.setValue(mailTo);
        page.btnsend.click();
        MyCommonAPIs.sleepi(10);
        
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