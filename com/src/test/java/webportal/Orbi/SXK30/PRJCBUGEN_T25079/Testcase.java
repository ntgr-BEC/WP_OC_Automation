package webportal.Orbi.SXK30.PRJCBUGEN_T25079;

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
    String []  predate;
    String []  curdate;
    
    @Feature("SXK30") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T25079") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Topology  -reboot for SXK30") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T25079") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown TestcaseStub");
    }

    // Each step covers the operation in one page
    @Step("Test Step 1: Login to WP / Go to Location 1 / Get device uptime")
    public void step1() {
        new WebportalLoginPage(true).defaultLogin();
        handle.gotoLoction();
        ddp.gotoPage();
        predate = ddp.getDeviceUptime(WebportalParam.ob2serialNo, false).split("\\s+");
    }

    @Step("Test Step 2: Go to topology page / Hover on the router / Click more details / Reboot and check if it reboots")
    public void step2() {
        boolean checkpoint = false;
        TopologyPage page = new TopologyPage();
        page.gotoPage();
        page.NodeImage(WebportalParam.ob2deveiceName).hover();
        MyCommonAPIs.sleepi(2);
        page.moredetail.click();
        MyCommonAPIs.sleepi(2);
        page.rebooticon.click();
        MyCommonAPIs.sleepi(2);
        
        page.btncontinue.click();
        MyCommonAPIs.sleepsyncorbi();
        
        ddp.gotoPage();
        curdate = ddp.getDeviceUptime(WebportalParam.ob2serialNo, false).split("\\s+");
        
        for(int i =0; i<8 ;i=i+2) {
            if(Integer.parseInt(curdate[i]) < Integer.parseInt(predate[i])) {
                checkpoint = true;
                break;
            }
        }
        
        assertTrue(checkpoint, "The device is rebooted");
        
    }
    
}