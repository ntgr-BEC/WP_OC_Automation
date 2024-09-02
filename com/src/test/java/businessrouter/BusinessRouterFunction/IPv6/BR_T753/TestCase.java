package businessrouter.BusinessRouterFunction.IPv6.BR_T753;
import static org.testng.Assert.assertTrue;

import java.util.logging.Logger;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import webportal.param.WebportalParam;

public class TestCase extends TestCaseBase implements Config {
    String tclname = getClass().getName();
    String tmpStr;

    public String sTestStr = "BR_T753";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;
    String HistroyHandle;
    String DhcpServerHandle;
    String TmsPageHandle;
    String HistroyHandle2;
    
    final static Logger logger = Logger.getLogger("BR_T753");
    @Feature("Business Router.IPv6") // 必须要加，对应目录名
    @Story("BR_T753") // 对应testrunkey
    @Description("007-IPv6-Connection type is fix") // 对应用例名A字
    @TmsLink("1474880") // 对应用例详情页的URL最后几位数字

    @Test(alwaysRun = true, groups = "p1") // 标记测试用例
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
            handle.sleepi(20);
        }
        
      @Step("Test Step 2:Enable IPv6 DHCP server for WAN, add prefix delegation for LAN .")
        public void step2() {
          boolean Result = false;
          boolean Result1 = false;          
          HistroyHandle = brpptpl2tpserver.LoginPptpL2tpServer(WebportalParam.brwangateway);
          DhcpServerHandle = Brtmspage.GetBrowserHandles();
          Result = bripv6dhcpserverpage.EnableDHCPServerIP(Ipv6DHCPServerInfo);
          System.out.print(Result);
          if (Result == true) {
                micResult =  true;
                assertTrue(micResult,"Pass:Enable IPv6 DHCP server successfully !");  
            }else {
                assertTrue(micResult,"Failed:Enable IPv6 DHCP server unsuccessfully!"); 
            } 
          bripv6dhcpserverpage.AddPrefixForPrefixDelegation(Ipv6PrifexDelegationInfo); 
          System.out.print("dddddddd23333333");
          bripv6dhcpserverpage.EnableRADVD(RADVDInfo);
         }
         

       @Step("Test Step 3: On Router,set IPv6 connection type to fix,then input WAN IPv6 address.")
        public void step3() {
           boolean Result = false;
           Brtmspage.ChangeBrowserHandles(HistroyHandle);
           bripv6page.OpenAdvancedIPv6Page();
           handle.sleepi(15);
           Result = bripv6page.SetStaticIPv6ForWANPortAndLANPort(Ipv6StaticIPInfo);
           if (Result == true) {
               micResult =  true;
               assertTrue(micResult,"Pass:set IPv6 connection type to fix,then input WAN IPv6 address successfully!");  
           }else {
               micResult =  false;
               assertTrue(micResult,"Failed:set IPv6 connection type to fix,then input WAN IPv6 address unsuccessfully !"); 
           } 
          
        }
       @Step("Test Step 4: PC access IPv6 lan address,PC access wan side via IPv6.")
       public void step4() {
           boolean Result0 = false;
           boolean Result1 = false;
           HistroyHandle2= Brtmspage.LoginTMS(WebportalParam.brlanclientip);
           Brtmspage.RuncmdByTMS(TmsIPv6AddrCom);
           Brtmspage.BackTMSConfigPafe();
           Brtmspage.RuncmdByTMS(TmsIPv6RouterCom);
           Brtmspage.BackTMSConfigPafe();
           Result0 =  Brtmspage.CheckPingPassOrFailed(TmsPingLanInterfaceCommands);
           //Brtmspage.CloseTMSWindows();
           if (Result0 == true ) {
               micResult =  true;
               assertTrue(micResult,"Pass: ping LAN interface successfully."  );             
           } else {
               micResult =  false;
               assertTrue(micResult,"Failed:ping LAN interface unsuccessfully.");
           }
           Brtmspage.BackTMSConfigPafe();
           Result1 =  Brtmspage.CheckPingPassOrFailed(TmsPingWanInterfaceCommands);
           //Brtmspage.CloseTMSWindows();
           if (Result1 == true ) {
               micResult =  true;
               assertTrue(micResult,"Pass: ping WAN interface successfully."  );             
           } else {
               micResult =  false;
               assertTrue(micResult,"Failed:ping WAN interface unsuccessfully.");
           }
           Brtmspage.BackTMSConfigPafe();
           Brtmspage.RuncmdByTMS(TmsRebootCom);
         
       }
       @Step("Test Step 5:Restore DUT configuration.")
       public void step5() {
           Brtmspage.ChangeBrowserHandles(HistroyHandle);
           bripv6page.OpenAdvancedIPv6Page();
           handle.sleepi(15);
          bripv6page.ChangeIPv6ConnectionType("Disabled");
          Brtmspage.ChangeBrowserHandles(DhcpServerHandle);
          bripv6dhcpserverpage.DeletePrefixForPrefixDelegation();
          bripv6dhcpserverpage.DeletePrefixForRADVD();
          CaseResult = true;
          
       }


}