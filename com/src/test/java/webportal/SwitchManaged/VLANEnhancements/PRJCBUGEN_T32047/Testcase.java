package webportal.SwitchManaged.VLANEnhancements.PRJCBUGEN_T32047;

import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.Random;
import org.openqa.selenium.Keys;
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
import webportal.weboperation.WiredGroupPortConfigPage;
import webportal.weboperation.WiredQuickViewPage;
import webportal.weboperation.WiredVLANPage;
import webportal.weboperation.WiredVLANPageForVLANPage;


/**
 * @author annfang
 */
public class Testcase extends TestCaseBase implements Config {
    public boolean Result      = true;
    String         vlanId      = "25";
    String         vlanName    = "testvlan25";
    String         networkName = "testnet" + vlanId;

    @Feature("Switch.VLANEnhancements") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T32047") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Verify the character limit for VLAN ID and VLAN description during edit is same as creating New VLAN") // It's a testcase title from Jira Test
                                                                                                            // Case.
    @TmsLink("PRJCBUGEN-T32047") // It's a testcase id/link from Jira Test Case.
    @Test(alwaysRun = true, groups = "p2")
    public void test() throws Exception {
        runTest(this);
    }

    // Each step is a single test step from Jira Test Case
    @Step("Test Step 1: login to the latest Webportal")
    public void step1() {
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
    }

    @Step("Test Step 2: Verify the character limit for VLAN ID")
    public void step2() {
        netsp.gotoPage();
        netsp.clickAdd();
        
        handle.setText(netsp.txtNetName, networkName);
        handle.setText(netsp.txtNetDesc, "desc");
        handle.setText(netsp.txtvlanName, vlanName);
        handle.setText(netsp.txtvlanId, "4094");
        netsp.clickNextOrSkip(false);
        assertTrue(handle.getPageErrorMsg().length() > 1, "check error msg");
        
    }

    @Step("Test Step 3: Verify the character limit for VLAN Description")
    public void step3() {
        netsp.gotoPage();
        netsp.clickAdd();
        handle.setText(netsp.txtNetName, networkName);
        handle.setText(netsp.txtvlanName, vlanName);
        handle.setText(netsp.txtvlanId, "4093");
        String temp = "";
        for (int i =0;i<256;i++) {
            temp = temp + "1";
        }
        handle.setText(netsp.txtNetDesc, temp);
        netsp.clickNextOrSkip(false);
        assertTrue(handle.getPageErrorMsg().length() > 1, "check error msg 1");
        
        netsp.gotoPage();
        netsp.clickAdd();
        handle.setText(netsp.txtNetName, networkName);
        handle.setText(netsp.txtvlanName, vlanName);
        handle.setText(netsp.txtvlanId, "4093");
        
        temp = "!@##$%^&*()";
        handle.setText(netsp.txtNetDesc, temp);
        netsp.clickNextOrSkip(false);
        assertTrue(handle.getPageErrorMsg().length() > 1, "check error msg 2");
    }


    @AfterMethod(alwaysRun = true)
    public void restore() {
    }

}
