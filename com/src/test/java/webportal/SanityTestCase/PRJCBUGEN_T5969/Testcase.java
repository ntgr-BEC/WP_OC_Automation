package webportal.SanityTestCase.PRJCBUGEN_T5969;

import static com.codeborne.selenide.Selenide.$;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Selenide;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import util.RunCommand;
import webportal.param.WebportalParam;
import webportal.publicstep.UserManage;
import webportal.webelements.HamburgerMenuElement;
import webportal.webelements.WebportalLoginPageElement;
import webportal.weboperation.DevicesDashPage;
import webportal.weboperation.HamburgerMenuPage;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author lavi
 */
public class Testcase extends TestCaseBase {
    String tclname = getClass().getName();
    String tmpStr;

    public int iHours = 48;

    @Feature("SanityTestCase") // It's a folder/component name to make test suite more readable from Jira Test Case.
    @Story("PRJCBUGEN_T5969") // It's a testcase id/link from Jira Test Case but replace - with _.
    @Description("Frequent login/logout") // It's a testcase title from Jira Test Case.
    @TmsLink("PRJCBUGEN-T5969") // It's a testcase id/link from Jira Test Case.

    @Test(alwaysRun = true) // Use p1/p2/p3 to high/normal/low on priority
    public void test() throws Exception {
        ArrayList<Method> ms = new ArrayList<Method>();
        for (Method m : Testcase.class.getDeclaredMethods()) {
            if (Pattern.matches("step\\d+", m.getName())) {
                ms.add(m);
            }
        }
        Collections.sort(ms, compareStep);
        for (Method m : ms) {
            System.out.printf("Start to running step: %s\n", m.getName());
            m.invoke(this, new Object[] {});
        }
    }

    @AfterMethod
    public void tearDown() {
    }

    // Each step is a single test step from Jira Test Case
    @Step("Frequetly login/logout Insight APP every 5 minutes (lasting time is configurable, could be 1 hours or more longer)")
    public void step1() {
        Long lSecs = System.currentTimeMillis();
        int iTotal = 0, iFail = 0, iCount = 0;
        while (((System.currentTimeMillis() - lSecs) / 1000) < ((iHours * 60 * 60) + 60)) {
            System.out.printf("Status of Login/out: Fail<%s>/Total<%s>/count<%s>\n", iFail, iTotal, iCount);
            try {
                iTotal++;               
                Selenide.open(WebportalParam.serverUrlLogin);
                MyCommonAPIs.sleepi(20);
                long t= System.currentTimeMillis();
                long end = t+12000;
                
                while(System.currentTimeMillis() < end) {
                    System.out.println("inside while");
                    Selenide.refresh();
                    if(new HamburgerMenuElement().loginNowButton.exists()) {
                        System.out.println("inside login buttopn");
                        new HamburgerMenuElement().loginNowButton.click();
                      break;
                    }else if(new WebportalLoginPageElement().loginEmailNew.exists()) {   
                        System.out.println("inside login page");
                        break;
                    }
                    MyCommonAPIs.sleepi(10);
                }    
                
                System.out.println("out of loop");
                
                new WebportalLoginPage().inputLogin(WebportalParam.loginName,WebportalParam.loginPassword);

                MyCommonAPIs.sleep(1 * 120, "wait 120 sec for webportal");
                              
//              check elements for upgrade account/add new location/org or loc list/account locked
                assertTrue($(".MobileDevice").exists() && $(".userNameBlock .fontSize12").exists() && $("#headerLocName").exists()&& $("#_divlocDiv0").exists());             
                
                handle.gotoLoction();
                MyCommonAPIs.sleep(1 * 120, "wait 30 sec for webportal");
                new DevicesDashPage().GoToDevicesDashPage();
                MyCommonAPIs.sleep(1 * 120, "wait 30 sec for webportal");
                assertTrue($("#_divAddDevLocDash").exists() && $("#ancUsrDropDwndevicesDash").exists());
                MyCommonAPIs.sleep(1 * 120, "wait 90 sec for webportal");
                UserManage userManage = new UserManage();
                userManage.logout();
                MyCommonAPIs.sleep(1 * 120, "wait 90 sec for webportal");
                iCount = 0;
            } catch (Throwable e) {
                e.printStackTrace();
                iFail++;
                iCount++;
                try {
                    String ss = MyCommonAPIs.takess();
                    System.out.println("file name is "+ss);
                    System.out.printf("Total<%s>/count<%s>\\n",iTotal,iCount);
                    if ((iTotal > 1) && (iCount > 0)) {
                        new RunCommand().SendeMailPython(false, 0, 0, ss);
                    }
                } catch (Throwable ee) {
                    ee.printStackTrace();
                    // break;
                }
            }
        }
        new RunCommand().SendeMailPython(true, iTotal, iFail, "");
    }

}
