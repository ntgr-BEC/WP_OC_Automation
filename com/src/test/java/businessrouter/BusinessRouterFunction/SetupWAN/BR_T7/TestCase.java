package businessrouter.BusinessRouterFunction.SetupWAN.BR_T7;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class TestCase extends TestCaseBase implements Config {
    String                       tclname = getClass().getName();
    String                       tmpStr;

    public String sTestStr = "BR_T7";
    public String sCurrentValue;
    public String sExpectedtValue;
    String HistroyHandle;
    String TmsPageHandle;
   // public String sOldSW   = WebportalParam.sw1IPaddress;
    
        @Feature("Business Router.Setup WAN") // 必须要加，对应目录名
        @Story("BR_T7") // 对应testrunkey
        @Description("003-Change WAN connect type use DHCP and static") // 对应用例名字
        @TmsLink("1455010") // 对应用例详情页的URL最后几位数字

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
            MyCommonAPIs.sleepi(20);
        }
        
        @Step("Test Step 2: Go to Basic->WAN page, Configure WAN interface use Static")
        public void step2() {
            boolean Result = false;
            brwanpage.OpenBasicWanSetupPage();
            MyCommonAPIs.sleepi(10);
            WanPortModeInfo.replace("WAN IP", Brtmspage.GetNewIPAddress(WebportalParam.brwanclientip));
            brwanpage.ChangeWanPortModeDhcpOrStatic(WanPortModeInfo);
            Result = brwanpage.CheckWanPortIpInfo(WanPorInfo);
            if (Result == true ) {
                micResult =  true;
                assertTrue(micResult,"Pass:configure WAN interface use Static normally."  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:configure WAN interface use Static abnormally."  );
            }
          
         }
        @Step("Test Step 3: lan pc can access wan side http")
        public void step3() {
            boolean TMSTCPResult =false;
            HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
            TmsPageHandle= Brtmspage.GetBrowserHandles();
        
            TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPCommands); 
            Brtmspage.BackTMSConfigPafe();
            if (TMSTCPResult == true ) {
                micResult =  true;
                assertTrue(micResult,"LAN pc can access WAN side http!"  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:LAN pc can't access WAN side http!"  );
            }
            
        }
        @Step("Test Step 4:  Go to Basic->WAN page, Configure WAN interface use DHCP")
        public void step4() {
            boolean Result = false;
            Brtmspage.ChangeBrowserHandles(HistroyHandle);
            brwanpage.OpenBasicWanSetupPage();
            MyCommonAPIs.sleepi(10);
            brwanpage.ChangeWanPortModeDhcpOrStatic(WanPortModeInfodefault);
            MyCommonAPIs.sleepi(10);
            Result = brwanpage.CheckWanPortIpInfo(WanPorInfodefault);
            if (Result == true ) {
                micResult =  true;
                assertTrue(micResult,"Pass:configure WAN interface use DHCP normally."  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:configure WAN interface use DHCP abnormally."  );
            }
        }
          @Step("Test Step 5: lan pc can access wan side http")
          public void step5() {
                boolean TMSTCPResult =false;
                //HistroyHandle= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
                Brtmspage.ChangeBrowserHandles(TmsPageHandle);
            
                TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPCommands);  
                Brtmspage.BackTMSConfigPafe();
                if (TMSTCPResult == true ) {
                    micResult =  true;
                    assertTrue(micResult,"LAN pc can access WAN side http!"  );             
                } else {
                    micResult =  false;
                    assertTrue(micResult,"Failed:LAN pc can't access WAN side http!"  );
                }
                
            }
          @Step("Test Step 6: Go to Basic->WAN page, Configure WAN interface use Static")
          public void step6() {
              boolean Result = false;
              Brtmspage.ChangeBrowserHandles(HistroyHandle);
              brwanpage.OpenBasicWanSetupPage();
              MyCommonAPIs.sleepi(10);
              WanPortModeInfo.replace("WAN IP", Brtmspage.GetNewIPAddress(WebportalParam.brwanclientip));
              brwanpage.ChangeWanPortModeDhcpOrStatic(WanPortModeInfo);
              Result = brwanpage.CheckWanPortIpInfo(WanPorInfo);
              if (Result == true ) {
                  micResult =  true;
                  assertTrue(micResult,"Pass:configure WAN interface use Static normally."  );             
              } else {
                  micResult =  false;
                  assertTrue(micResult,"Failed:configure WAN interface use Static abnormally."  );
              }
            
           }
          @Step("Test Step 7: lan pc can access wan side http")
          public void step7() {
              boolean TMSTCPResult =false;
              Brtmspage.ChangeBrowserHandles(TmsPageHandle);
          
              TMSTCPResult =  Brtmspage.UdpTcpTrafficPassOrFailed(TmsTCPCommands); 
              Brtmspage.BackTMSConfigPafe();
              if (TMSTCPResult == true ) {
                  micResult =  true;
                  assertTrue(micResult,"LAN pc can access WAN side http!"  );             
              } else {
                  micResult =  false;
                  assertTrue(micResult,"Failed:LAN pc can't access WAN side http!"  );
              }
              
          }
          @Step("Test Step 8:  Go to Basic->WAN page, Configure WAN interface use DHCP to restore DUT configuration")
          public void step8() {
              boolean Result = false;
              Brtmspage.ChangeBrowserHandles(HistroyHandle);
              brwanpage.OpenBasicWanSetupPage();
              MyCommonAPIs.sleepi(10);
              brwanpage.ChangeWanPortModeDhcpOrStatic(WanPortModeInfodefault);
              MyCommonAPIs.sleepi(10);
              Result = brwanpage.CheckWanPortIpInfo(WanPorInfodefault);
              if (Result == true ) {
                  micResult =  true;
                  assertTrue(micResult,"Pass:configure WAN interface use DHCP normally."  );             
              } else {
                  micResult =  false;
                  assertTrue(micResult,"Failed:configure WAN interface use DHCP abnormally."  );
              }
              MyCommonAPIs.sleepi(10);
              brwanpage.ChangeWanPortModeDhcpOrStatic(WanPortModeInfo);
              CaseResult = true;
              brlogin.BrLogout();
          }
         
        

}
