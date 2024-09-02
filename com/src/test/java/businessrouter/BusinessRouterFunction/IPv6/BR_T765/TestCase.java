package businessrouter.BusinessRouterFunction.IPv6.BR_T765;
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

    public String sTestStr = "BR_T765";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;
    String HistroyHandle;
    String DhcpServerHandle;
    
    final static Logger logger = Logger.getLogger("BR_T765");
    @Feature("Business Router.IPv6") // 必须要加，对应目录名
    @Story("BR_T765") // 对应testrunkey
    @Description("019-IPv6-Verify \"Status refresh\" button can work") // 对应用例名A字
    @TmsLink("1474892") // 对应用例详情页的URL最后几位数字

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

       @Step("Test Step 2: 2.Set IPv6 connection type to Auto Config.")
        public void step2() {
           boolean Result = false;
           bripv6page.OpenAdvancedIPv6Page();
           handle.sleepi(15);
           bripv6page.ChangeIPv6ConnectionType("Auto Config");
           handle.sleepi(50);
          
        }
       @Step("Test Step 3:Enable IPv6 DHCP server for WAN, add prefix delegation for LAN .")
       public void step3() {
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
       //bripv6dhcpserverpage.AddPrefixForRADVD(RADVDPrefixInfo);
        }
       @Step("Test Step 4: 2.Set IPv6 connection type to Auto Config.")
       public void step4() {
          boolean Result = false;
          Brtmspage.ChangeBrowserHandles(HistroyHandle);
          //bripv6page.OpenAdvancedIPv6Page();
          //handle.sleepi(15);
          Result =bripv6page.CheckIPv6RefreshStatusButton(Ipv6TypeAndInfo);
          
          System.out.print(Result);
          if (Result == true) {
              micResult =  true;
              assertTrue(micResult,"Pass:On router,click status refresh button,display new IPv6 address");  
          }else {
              micResult =  false;
              assertTrue(micResult,"Failed:On router,click status refresh button,can't display new IPv6 address!"); 
          } 
         
       }
       @Step("Test Step 5:Restore DUT configuration.")
       public void step5() {
           bripv6page.OpenAdvancedIPv6Page();
           handle.sleepi(15);
          bripv6page.ChangeIPv6ConnectionType("Disabled");
          Brtmspage.ChangeBrowserHandles(DhcpServerHandle);
          bripv6dhcpserverpage.DeletePrefixForPrefixDelegation();
          bripv6dhcpserverpage.DeletePrefixForRADVD();
          CaseResult = true;
          
       }


}