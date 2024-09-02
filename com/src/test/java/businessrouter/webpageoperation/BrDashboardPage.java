package businessrouter.webpageoperation;

import java.util.Map;
import java.util.logging.Logger;

import org.openqa.selenium.JavascriptExecutor;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;

import businessrouter.webelements.BrAllMenueElements;
import businessrouter.webelements.BrDashboardElemnets;
import webportal.param.WebportalParam;

public class BrDashboardPage extends BrDashboardElemnets {
    final static Logger logger = Logger.getLogger("BrDashboardPage");
    boolean             result = false;

    public void OpenDashboardPage() {
        // open(LoginURL);
        logger.info("Open Dashboard Page");
        BrAllMenueElements BrAllMenueElements = new BrAllMenueElements();
        if (WebportalParam.DUTType.contentEquals("BR500")) {
            BrAllMenueElements.advanced.click();
        }else if(WebportalParam.DUTType.contentEquals("BR100")) {
            BrAllMenueElements.basic.click();                      
        }
        
        BrAllMenueElements.Dashboard.click();
    }

    public void RebootDUT() {
        // open(LoginURL);
        Selenide.sleep(5000);
        logger.info("Open Dashboard Pagesddddddddddddddddddddd");
        rebootbutton.click();
        logger.info("Open Dashboard Pagesddddddddddddddddddddd333333");
        // if (rebootokbutton.exists()) {
        if (WebportalParam.DUTType.contentEquals("BR500")) {
            rebootokbutton.click();
        }else if(WebportalParam.DUTType.contentEquals("BR100")) {
            rebootokbuttonbr100.click();                      
        }
       
        // }
        Selenide.sleep(10000);
    }

    public void ReleaseOrRenew(String ReleaseOrRenew) {
        // open(LoginURL);
        logger.info("ReleaseOrRenew Start");
        Selenide.sleep(20000);
        wanconnectstatus.click();
        ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", wanreleasebutton);
        if (ReleaseOrRenew.contentEquals("Release")) {
            wanreleasebutton.click();
        } else if (ReleaseOrRenew.contentEquals("Renew")) {
            wanrenewbutton.click();
        }

        Selenide.sleep(10000);
        logger.info("ReleaseOrRenew End");
    }

    public boolean CheckinterWanStatus(Map<String, String> InterWanInfo) {
        boolean Result = true;
        logger.info("CheckinterWanStatus Start");
        // Selenide.sleep(20000);
        if (InterWanInfo.get("Link Connect") != null) {
            if(InterWanInfo.get("Link Connect").contentEquals("Disable")) {
            
            } else if (InterWanInfo.get("Link Connect").contentEquals("Enable")) {
                wanconnectstatus.click();
            }
        } else {
            wanconnectstatus.click();
        }
        
        Selenide.sleep(80000);
        logger.info("status:" + waninterconncetstatus.getText());

        // ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);",
        // wanreleasebutton);
        if (!waninterconncetstatus.getText().contentEquals(InterWanInfo.get("Status"))) {
            Result = false;
        }
        String WanIp = waninterip.getText();
        logger.info("WanIp:" + WanIp);
        String[] WANList = WanIp.split("\\.");
        String[] IPList = InterWanInfo.get("WAN IP").split("\\.");
        if ((WANList[0].contentEquals(IPList[0])) && (WANList[1].contentEquals(IPList[1])) && (WANList[2].contentEquals(IPList[2]))) {

        } else {
            Result = false;
        }
        if (!wanintermask.getText().contentEquals(InterWanInfo.get("Mask"))) {
            Result = false;
        }
        Selenide.sleep(2000);
        logger.info("CheckinterWanStatus End");
        return Result;
    }

    public void DisconnectOrConnectWan(String DisconnectOrConnect) {
        //open(LoginURL);
        logger.info("DisconnectOrConnectWan Start");
        Selenide.sleep(20000);
        //wanconnectstatus.click();
        
        if (DisconnectOrConnect.contentEquals("Disconnect")) {
            ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", wandisconnectebutton);
            wandisconnectebutton.click();
        } else if(DisconnectOrConnect.contentEquals("Connect")) {
            logger.info("122222222222222222222222333333333333333333334555555555555555");
            Selenide.sleep(2000);
            ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", wanconnectbutton);
            Selenide.sleep(5000);
            wanconnectbutton.click();
        }
        
   
        Selenide.sleep(10000);
        logger.info("DisconnectOrConnectWan End");
    }


    public String GetDeviceIp() {
        String deviceip = "5";
        if (devicestablename.getText().indexOf("PC") != -1) {
            deviceip = devicestableip.getText();
        }
        return deviceip;
    }

}

