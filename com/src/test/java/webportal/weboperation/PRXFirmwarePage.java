/**
 *
 */
package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

import java.time.Duration;
import java.util.logging.Logger;

import org.openqa.selenium.By;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;

import orbi.webelements.DNIOrbiCommanElement;
import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.PRXFirmwareElement;

/**
 * @author Anusha H
 */
public class PRXFirmwarePage extends PRXFirmwareElement {
    /**
     *
     */
    
    final static Logger logger = Logger.getLogger("PRXFirmwarePage");
    
    public PRXFirmwarePage() {
        // TODO Auto-generated constructor stub
        logger.info("init...");
    }
    
    public void gotoFirmwarePage() {
        logger.info(String.format("..."));
        open(URLParam.hrefFirmware, true);
        waitReady(); // for checkbox status
    }
    
    /**
     * @param  timeout
     * @return         true for device is updated, false to timeout or failed
     *                 TODO:
     *                 1. Find a short way to know that schedule is not happened as expected
     *                 2. Find a way to know that firmware updating is failed 4ever
     */
    
    
    public void enterDeviceYes(String serialNumber) {
        WebCheck.checkUrl(URLParam.hrefRouters);
        for (int i = 0; i < 2; i++) {
            if (checkApIsExist(serialNumber)) {
                logger.info("Enter device.");
                Selenide.executeJavaScript("arguments[0].removeAttribute('class')", editModule(serialNumber));
                MyCommonAPIs.sleep(3000);
                enterDevice(serialNumber).waitUntil(Condition.visible, 60 * 1000).click();
                MyCommonAPIs.sleep(5 * 1000);
                break;
            }
            refresh();
        }
    }
    
    public boolean checkApIsExist(String serialNumber) {
        waitReady();
        boolean result = false;
        String sElement = String.format("//td[text()='%s']", serialNumber);
        logger.info("on element: " + sElement);
        if (Selenide.$x(sElement).exists()) {
            result = true;
            logger.info("Ap SN:" + serialNumber + " is existed.");
        }
        return result;
    }
    
    public boolean FWUpgradeSwitch(String passwd, String ip) {
        logger.info("Login to Orbi admin UI with specified user and passwd");
       
            String url = "http://"+ ip+":49151";
            open(url);
        
            System.out.println("Open the URL"); 
            Selenide.forward();                   // switching the control from insight to prx local GUI
            MyCommonAPIs.sleepi(10);          
            PRXFirmwarePage.Switchname.sendKeys("admin");
            PRXFirmwarePage.Switchpassword.sendKeys(passwd);
            MyCommonAPIs.sleepi(5);
            PRXFirmwarePage.SwitchLogin.click();
            MyCommonAPIs.sleepi(10);
            FWUpload(); 
            MyCommonAPIs.sleepi(40);
            refresh();
    
        return true;
   
}
    
   public void FWUpload() {
       logger.info(" Browse fw file and upload");  
       MyCommonAPIs.sleepi(5);
       Maintenance.click();
       MyCommonAPIs.sleepi(5);
       Upgrade.click();
       MyCommonAPIs.sleepi(5);
       HTTPFileUpgrade.click();
       MyCommonAPIs.sleepi(5);
       String filename = WebportalParam.swimage;
       String filePath = "E:\\APUpdateimage\\" + filename;
       System.out.println(filePath);
       MyCommonAPIs.sleepi(5);

       $(By.xpath("//*[@id='maincontent']")).shouldBe(Condition.visible); // Waits for iframe to be visible
       Selenide.switchTo().frame($(By.xpath("//*[@id='maincontent']")));
       MyCommonAPIs.sleepi(5);
       Browse.sendKeys(filePath);
       System.out.println("browse done");
       MyCommonAPIs.sleepi(10);  
       Selenide.switchTo().defaultContent(); 
       apply.click();
       MyCommonAPIs.sleepi(300);
       
       $(By.xpath("//*[@id='maincontent']")).shouldBe(Condition.visible); // Waits for iframe to be visible
       Selenide.switchTo().frame($(By.xpath("//*[@id='maincontent']")));
       MyCommonAPIs.sleepi(5);
       String Fw= image.getText();
       System.out.println(Fw);
       String name;
       if(Fw.equals("image2")) {
           name = "image1";            
       }else {
           name = "image2";   
       }
       image.selectOption(name);
       MyCommonAPIs.sleepi(5);
       $(By.id("fakeInputForm")).shouldBe(Condition.visible, Condition.enabled);
       System.out.println("before browse done");
       Browse.sendKeys(filePath);
       System.out.println("browse done");
       MyCommonAPIs.sleepi(10);  
       Selenide.switchTo().defaultContent(); 
       apply.click();
       MyCommonAPIs.sleepi(300);
       
       Selenide.back();  
//       Selenide.back();          //switching back from prx local GUI to insight
       MyCommonAPIs.sleepi(10);
       refresh();
   
    }
}
