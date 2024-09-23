package webportal.SwitchManaged.SpanningTree.PRJCBUGEN_T6058;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import util.SwitchCLIUtils;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String tclname = getClass().getName();
    String tmpStr;
    
    public String sCurrentValue;
    public String sExpectedtValue;
    int           iMode;
    
    @Feature("Switch.SpanningTree") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T6058") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("008-Set port STP status on physical port and verify the function under RSTP protocol") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T6058") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p1") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        runTest(this);
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
//        wstp.gotoPage();
        wstp.enableRSTP();
    }
    
    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: Open Device")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage(true);
        webportalLoginPage.defaultLogin();
        
        handle.gotoLoction();
        handle.gotoLocationWireSettings();
        
        wstp.gotoPage();
    }
    
    @Step("Test Step 2: Change Spanning-Tree mode to RSTP via Web Portal;")
    public void step2() {
        if (!wstp.cbEnable.exists())
            throw new RuntimeException("Not support, must be > 4.5");
        iMode = 2;
        wstp.setSTPMode(iMode, false, false);
    }
    
    @Step("Test Step 3: checked by Web Portal and Web GUI;")
    public void step3() {
        assertTrue(wstp.getSTPMode() == iMode, "check rstp mode");
        
        handle.waitCmdReady("mode stp", true);
        assertTrue(SwitchCLIUtils.getSTPMode() == 2, "verify rstp is enabled");
    }
    
    @Step("Test Step 4: Enable port STP status, then check STP state;")
    public void step4() {
        wstp.setSTPMode(iMode, true, false);
        MyCommonAPIs.sleepsync();
        String port = null;
        if(WebportalParam.sw1Model.contains("M4350")) {
             port = "1/0/";
        }
        if(WebportalParam.sw1Model.contains("M4250")) {
            port = "0/";
       }else {
           port = "g";
       }
        
        System.out.println(port);
        MyCommonAPIs.sleepsync();
        assertTrue(SwitchCLIUtils.isPortLagSTPMode(port+"3"), "verify stp is enabled");
        assertFalse(SwitchCLIUtils.isPortLagSTPMode(port+"2"), "verify stp is not enabled");
    }
    
    @Step("Test Step 5: Change Spanning-Tree mode to Disable via Insight;")
    public void step5() {
        iMode = 0;
        wstp.setSTPMode(iMode, false, false);
    }
    
    @Step("Test Step 6: checked by Web Portal and Web GUI;")
    public void step6() {
        assertTrue(wstp.getSTPMode() == iMode, "check disable mode");
        MyCommonAPIs.sleepsync();
        assertTrue(SwitchCLIUtils.getSTPMode() == 0, "verify stp is disabled");
    }
    
}
