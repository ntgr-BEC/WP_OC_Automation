package webportal.BR.BRBackupRestore.PRJCBUGEN_T16927;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import webportal.param.WebportalParam;
import webportal.weboperation.DeviceBackupRestorePage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    DeviceBackupRestorePage brPage;
    String                  invalid = "A~!@#$%^&*()";

    @Feature("BR.BRBackupRestore") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T16927") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("003-Test Name and Description") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T16927") // It's a testcase id/link from Jira Test Case.
    
    @Test(alwaysRun = true, groups = "p2") // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        brPage = new DeviceBackupRestorePage(WebportalParam.br1serialNo);
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
        ddp.gotoPage();
        ddp.openBR1();
    }
    
    @Step("Test Step 2: Create Backup,name is empty")
    public void step2() {
        brPage.gotoPage();
        brPage.clickCreate();
        brPage.txtCreateBackupDesc.setValue("this is a desc");
        handle.clickBoxLastButton();
        assertTrue(handle.getPageErrorMsg().length() > 10, "name must be 1-32 length");
    }

    @Step("Test Step 3: Create Backup,input incorrect name")
    public void step3() {
        brPage.txtCreateBackupName.setValue(invalid);
        handle.clickBoxLastButton();
        assertTrue(handle.getPageErrorMsg().length() > 10, "name must not be " + invalid);
    }

    @Step("Test Step 4: Input correct name and keep description is empty")
    public void step4() {
        brPage.txtCreateBackupName.setValue("new back up");
        brPage.txtCreateBackupDesc.clear();
        handle.clickBoxLastButton();
        assertTrue(handle.getPageErrorMsg().length() > 10, "desc must be 1-32 length");
    }

    @Step("Test Step 5: Input incorrect description ")
    public void step5() {
        brPage.txtCreateBackupName.setValue("new back up");
        brPage.txtCreateBackupDesc.setValue(invalid);
        handle.clickBoxLastButton();
        assertTrue(handle.getPageErrorMsg().length() > 10, "desc must not be " + invalid);
    }
}
