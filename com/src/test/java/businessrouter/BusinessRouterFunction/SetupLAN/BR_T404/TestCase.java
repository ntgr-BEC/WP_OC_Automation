package businessrouter.BusinessRouterFunction.SetupLAN.BR_T404;
import java.util.logging.Logger;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import businessrouter.testbase.TestCaseBase;
import businessrouter.webpageoperation.BrBasicLanSetupPage;
import businessrouter.webpageoperation.BrLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import util.MyCommonAPIs;

public class TestCase extends TestCaseBase implements Config {
    String                       tclname = getClass().getName();
    String                       tmpStr;
    final static Logger logger = Logger.getLogger("BR_T404");
    public String sTestStr = "BR_T404";
    public String sCurrentValue;
    public String sExpectedtValue;
   // public String sOldSW   = WebportalParam.sw1IPaddress;
    
        @Feature("Business Router.Setup LAN") // 必须要加，对应目录名
        @Story("BR_T404") // 对应testrunkey
        @Description("Delete all info from address reservation table") // 对应用例名字
        @TmsLink("1455409") // 对应用例详情页的URL最后几位数字

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
        
        @Step("Test Step 2: Add max entries for DHCP address reservation rules")
        public void step2() {
            BrBasicLanSetupPage BrBasicLanSetupPage = new BrBasicLanSetupPage();
            BrBasicLanSetupPage.OpenBasicLanSetupPage();
            for(int i =0; i < 4 ; i++){
                String tt = String.valueOf(i);
                String devicename = "test" + tt;
                IPReservation.replace("Device Name", devicename);
                int IPAddressNum = i + 3;
                String SIPAddress = String.valueOf(IPAddressNum);
                String ipreservation = IPReservation.get("IP Address");
                System.out.println(ipreservation);
                String []ipreservationlist = ipreservation.split("\\.");
                String Newdeip = ipreservationlist[0]+"." + ipreservationlist[1]+"."+ ipreservationlist[2]+"."+SIPAddress;
                
                IPReservation.replace("IP Address", Newdeip);
                int Gatewaylastnumber = i + 16;
                String MacLastvalue = Integer.toHexString(Gatewaylastnumber);
                String macaddress = IPReservation.get("MAC Address");
                String []macaddresslist = macaddress.split(":");
                String Newmacaddress = macaddresslist[0]+":" + macaddresslist[1]+":"+ macaddresslist[2]+":"+macaddresslist[3] + ":"+macaddresslist[4] + ":" + MacLastvalue;
                IPReservation.replace("MAC Address", Newmacaddress);                
                BrBasicLanSetupPage.AddIPReservation(IPReservation);
                MyCommonAPIs.sleep(20000);  
             }
            
         }
        @Step("Test Step 3: Delete max Firewall rules")
        public void step3() {
            BrLoginPage BrLoginPage = new BrLoginPage();
            //BrFirewallTrafficRulesPage BrFirewallTrafficRulesPage = new BrFirewallTrafficRulesPage();
            //brlansetuppage.OpenBasicLanSetupPage();
            handle.sleepi(10);
            for (int i = 0; i < 4; i++) {
                System.out.println("delete rules");
                String tt = String.valueOf(i);
                String devicename = "test" + tt;
                //IPReservation.replace("Device Name", devicename);
                brlansetuppage.DeleteIPReservation(devicename);
                handle.sleepi(10);
            }
            
            BrLoginPage.BrLogout();
            CaseResult = true;

        }

}
