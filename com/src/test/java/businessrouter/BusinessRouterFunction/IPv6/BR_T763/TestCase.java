package businessrouter.BusinessRouterFunction.IPv6.BR_T763;
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

    public String sTestStr = "BR_T763";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;
    String HistroyHandle;
    String DhcpServerHandle;
    
    final static Logger logger = Logger.getLogger("BR_T763");
    @Feature("Business Router.IPv6") // 必须要加，对应目录名
    @Story("BR_T763") // 对应testrunkey
    @Description("017-IPv6- Filtering config to secured or open") // 对应用例名A字
    @TmsLink("1474890") // 对应用例详情页的URL最后几位数字

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
          bripv6dhcpserverpage.AddPrefixForRADVD(RADVDPrefixInfo);
         }
         

       @Step("Test Step 3: Set IPv6 to \"Auto Detect\",Router connect to IPv6 DHCP server.")
        public void step3() {
           boolean Result = false;
           boolean Result1 = false;
           boolean Result2 = false;
           Brtmspage.ChangeBrowserHandles(HistroyHandle);
           bripv6page.OpenAdvancedIPv6Page();
           handle.sleepi(15);
           bripv6page.ChangeIPv6ConnectionType("Auto Detect");
           handle.sleepi(50);
           Result = bripv6page.CheckIPv6Info(Ipv6TypeAndInfo);
           System.out.print(Result);
           if (Result == true) {
               micResult =  true;
               assertTrue(micResult,"Pass:1.Router can detect to \"DHCP\" IPv6 connection mode.\r\n" + 
                       "2.WAN Port can get IPv6 address\r\n" + 
                       "3.Show Router's IPv6 Address On LAN!");  
           }else {
               micResult =  false;
               assertTrue(micResult,"Failed:1.Router can't detect to \"DHCP\" IPv6 connection mode.\r\n" + 
                       "Or WAN Port can't get IPv6 address\r\n" + 
                       "Or Router can't show IPv6 Address On LAN!"); 
           } 
           Result1 = bripv6page.ChangeIPv6FilterType("Open");
           if (Result1 == true) {
               micResult =  true;
               assertTrue(micResult,"Pass:Config IPv6 filtering to open successfully!");  
           }else {
               micResult =  false;
               assertTrue(micResult,"Failed:1.Config IPv6 filtering to open unsuccessfully!"); 
           } 
           Result2 = bripv6page.ChangeIPv6FilterType("Secured");
           if (Result2 == true) {
               micResult =  true;
               assertTrue(micResult,"Pass:Config IPv6 filtering to Secured successfully!");  
           }else {
               micResult =  false;
               assertTrue(micResult,"Failed:1.Config IPv6 filtering to Secured unsuccessfully!"); 
           } 
           
        }
       @Step("Test Step 4:Restore DUT configuration.")
       public void step4() {
           bripv6page.OpenAdvancedIPv6Page();
           handle.sleepi(15);
          bripv6page.ChangeIPv6ConnectionType("Disabled");
          Brtmspage.ChangeBrowserHandles(DhcpServerHandle);         
          bripv6dhcpserverpage.DeletePrefixForPrefixDelegation();
          bripv6dhcpserverpage.DeletePrefixForRADVD();
          CaseResult = true;
          
       }


}