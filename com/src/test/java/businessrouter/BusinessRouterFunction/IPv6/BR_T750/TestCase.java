package businessrouter.BusinessRouterFunction.IPv6.BR_T750;
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

    public String sTestStr = "BR_T750";
    public String sCurrentValue;
    public String sExpectedtValue;
    // public String sOldSW = WebportalParam.sw1IPaddress;
    String HistroyHandle;
    String DhcpServerHandle;
    
    final static Logger logger = Logger.getLogger("BR_T750");
    @Feature("Business Router.IPv6") // 必须要加，对应目录名
    @Story("BR_T750") // 对应testrunkey
    @Description("004-IPv6-Connection type is auto config") // 对应用例名A字
    @TmsLink("1474877") // 对应用例详情页的URL最后几位数字

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
         

       @Step("Test Step 3: 2.Set IPv6 connection type to Auto Config.")
        public void step3() {
           boolean Result = false;
           Brtmspage.ChangeBrowserHandles(HistroyHandle);
           bripv6page.OpenAdvancedIPv6Page();
           handle.sleepi(15);
           bripv6page.ChangeIPv6ConnectionType("Auto Config");
           handle.sleepi(50);
           Result = bripv6page.CheckIPv6AutoConfigInfo(Ipv6TypeAndInfo);
           System.out.print(Result);
           if (Result == true) {
               micResult =  true;
               assertTrue(micResult,"Pass:1.WAN Port can get IPv6 address\r\n" + 
                       "2.Show Router's IPv6 Address On LAN!");  
           }else {
               micResult =  false;
               assertTrue(micResult,"Failed:1. WAN Port can't get IPv6 address\r\n" + 
                       "Or Router can't show IPv6 Address On LAN!"); 
           } 
          
        }
       @Step("Test Step 4:Restore DUT configuration.")
       public void step4() {
           bripv6page.OpenAdvancedIPv6Page();
           handle.sleepi(15);
          bripv6page.ChangeIPv6ConnectionType("Disabled");
          Brtmspage.ChangeBrowserHandles(DhcpServerHandle);
          //bripv6dhcpserverpage.EnableDHCPServerIP(Ipv6DHCPServerdefaultInfo);
          bripv6dhcpserverpage.DeletePrefixForPrefixDelegation();
          bripv6dhcpserverpage.DeletePrefixForRADVD();
          CaseResult = true;
          
       }


}