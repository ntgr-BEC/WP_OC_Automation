package businessrouter.BusinessRouterFunction.VLAN.BR_T384;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrAdvancedBackupSettingsPage;
import businessrouter.webpageoperation.BrAdvancedVlanPage;
import businessrouter.webpageoperation.BrLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class TestCase extends TestCaseBase implements Config {
    String tclname = getClass().getName();
    String tmpStr;

    public String sTestStr    = "BR_T384";
    public String sCurrentValue;
    public String sExpectedtValue;
    BrLoginPage   BrLoginPage = new BrLoginPage();
    // public String sOldSW = WebportalParam.sw1IPaddress;

    @Feature("Business Router.VLAN") // 必须要加，对应目录名
    @Story("BR_T384") // 对应testrunkey
    @Description("018-Delete all physical ports from default VLAN") // 对应用例名字
    @TmsLink("1455403") // 对应用例详情页的URL最后几位数字

    @Test(alwaysRun = true, groups = "p2") // 标记测试用例
    public void test() throws Exception {
        runTest(this);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        System.out.println("start to do tearDown");
    }

    @Step("Test Step 1: Open Device")
    public void step1() {
        BrLoginPage BrLoginPage = new BrLoginPage();
        BrLoginPage.defaultLogin();
        MyCommonAPIs.sleepi(10);
    }

    @Step("Test Step 2: Remove all the physical ports from default VLAN 1,it must have a least one port in vlan.")
    public void step2() {
        boolean result = false;
        boolean result1 = false;
        BrAdvancedVlanPage BrAdvancedVlanPage = new BrAdvancedVlanPage();
        BrAdvancedVlanPage.OpenAdvancedVlanPage();
        MyCommonAPIs.sleepi(5);
       // for (int i = 1; i < 5; i++) {
            //VLAN.put("Ports", String.valueOf(i) + ":UNTAG");
          //  if (i == 2) {
        if (WebportalParam.DUTType.contentEquals("BR500")) {
            if (BrAdvancedVlanPage.DeleteVlanMember(VLANBR100)) {
                result1 = true;
                //break;
            }
        }else if(WebportalParam.DUTType.contentEquals("BR100")) {
            if (BrAdvancedVlanPage.DeleteVlanMember(VLANBR100)) {
                result1 = true;
                //break;
            }
            
        }
                
           // }
            //BrAdvancedVlanPage.DeleteVlanMember(VLAN);
        if (WebportalParam.DUTType.contentEquals("BR500")) {
            MyCommonAPIs.sleepi(15);
            if (!BrAdvancedVlanPage.CheckVlanMember(VLAN)) {
                result = true;
            }
        }else if(WebportalParam.DUTType.contentEquals("BR100")) {
            MyCommonAPIs.sleepi(15);
            if (!BrAdvancedVlanPage.CheckVlanMember(VLANBR100)) {
                result = true;
            }
            
        }
            
            //    continue;
            //} else {
            //    result = false;
            //    break;
            //}
        //}
            
            //logger.info(result);
			 //logger.info(String.valueOf(result));
            System.out.print(result);
        if ( result1 ) {
            micResult = true;
            System.out.println("!@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            assertTrue(micResult, "Pass:VLAN config is correct.");
        } else {
            micResult = false;
            assertTrue(micResult, "Failed:VLAN config is incorrect.");
        }
       System.out.println("!@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

       BrAdvancedBackupSettingsPage BrAdvancedBackupSettingsPage = new BrAdvancedBackupSettingsPage();
       BrAdvancedBackupSettingsPage.OpenBackupSettingsPage();
       MyCommonAPIs.sleepi(10);
       if (WebportalParam.DUTType.contentEquals("BR500")) {
           BrAdvancedBackupSettingsPage.RestoreSetting("C:\\tftpd32\\NETGEAR_BR500_default.cfg");
       }else if(WebportalParam.DUTType.contentEquals("BR100")) {
           BrAdvancedBackupSettingsPage.RestoreSetting("C:\\tftpd32\\NETGEAR_BR100_default.cfg");
           
       }
      
       MyCommonAPIs.sleepi(150);
    }

    @Step("Test Step 3: Return CaseResult")
    public void step3() {
        CaseResult = true;
    }

}
