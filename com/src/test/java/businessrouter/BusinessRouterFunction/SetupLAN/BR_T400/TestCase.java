package businessrouter.BusinessRouterFunction.SetupLAN.BR_T400;
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
import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class TestCase extends TestCaseBase implements Config {
    String                       tclname = getClass().getName();
    String                       tmpStr;
    final static Logger logger = Logger.getLogger("BR_T400");
    public String sTestStr = "BR_T400";
    public String sCurrentValue;
    public String sExpectedtValue;
   // public String sOldSW   = WebportalParam.sw1IPaddress;
    
        @Feature("Business Router.Setup LAN") // 必须要加，对应目录名
        @Story("BR_T400") // 对应testrunkey
        @Description("013-LAN setup - Change LAN subnet IP under PPPOE") // 对应用例名字
        @TmsLink("1455421") // 对应用例详情页的URL最后几位数字

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
        
        @Step("Test Step 2: change LAN subnet IP to WAN ip in the same subnet.")
        public void step2() {
   
            boolean Result = false;
            brlansetuppage.OpenBasicLanSetupPage();
            handle.sleepi(8); 
            LANIPInfo.replace("LAN IP", Brtmspage.GetNewIPAddress( WebportalParam.brwanclientip));
            brlansetuppage.ChangLanIpAndMask(LANIPInfo);
            //brlansetuppage.OpenBasicLanSetupPage();
            handle.sleepi(8); 
            Result = brlansetuppage.CheckLanIpAndMask(LANIPInfo);
            handle.sleepi(6);  
            if (Result == false ) {
                micResult =  true;
                assertTrue(micResult,"Pass:change LAN subnet IP to WAN ip in the same subnet unsuccessfully!"  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:change LAN subnet IP to WAN ip in the same subnet successfully!"  );
            }
         }
        @Step("Test Step 3: Able to change LAN subnet IP")
        public void step3() {
            boolean Result = false;
            //brlansetuppage.OpenBasicLanSetupPage();
            handle.sleepi(8); 
            String NewLanIp = Brtmspage.GetNewIPAddress(WebportalParam.brlanclientip);
            System.out.println(NewLanIp);
            LANIPInfo1.replace("LAN IP", NewLanIp);
            brlansetuppage.ChangLanIpAndMask(LANIPInfo1);
            handle.sleepi(20);
            //String ClientUrl = "window.open(\"http://" + NewLanIp + "\");";
            //logger.info("ClientUrl:" + ClientUrl);
            //Selenide.executeJavaScript(ClientUrl);
            brlogin.LoginWithNewLanIP(NewLanIp);
            //Selenide.open("https://192.168.1.9/unauth.cgi");
            //Selenide.open("https://192.168.1.9", "", "admin", "Netgear1@");
            handle.sleepi(10);
            brlansetuppage.OpenBasicLanSetupPage();
            handle.sleepi(8);
            Result = brlansetuppage.CheckLanIpAndMask(LANIPInfo1);
            handle.sleepi(6);  
     
            if (Result == true ) {
                micResult =  true;
                assertTrue(micResult,"Pass:Able to change LAN subnet IP successfully!"  );             
            } else {
                micResult =  false;
                assertTrue(micResult,"Failed:Able to change LAN subnet IP unsuccessfully!"  );
            }
         }
    
        @Step("Test Step 4: restore DUT configuration")
        public void step4() {
            boolean Result = false;
            //brlansetuppage.OpenBasicLanSetupPage();
            handle.sleepi(8); 
            brlansetuppage.ChangLanIpAndMask(LANIPInfodefault);
            CaseResult = true;
         }
        

}
