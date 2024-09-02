/**
 * 
 */
package webportal.weboperation;

import java.util.logging.Logger;

import org.openqa.selenium.Keys;

import com.codeborne.selenide.Selenide;

import webportal.webelements.DevicesNasCloudElement;

/**
 * @author ronliu
 *
 */
public class DevicesNasCloudPage extends DevicesNasCloudElement {
    Logger logger;

    /**
     * 
     */
    public void turnOnReadyCloud(String username,String password) {  
        Selenide.sleep(5000);
        nasCloudButton.click();      
        Selenide.sleep(5000);
        nasSwitchButton.click();
        Selenide.sleep(5000);
        nasUsernameInput.sendKeys(username);
        nasPasswordInput.sendKeys(password);
        Selenide.sleep(2000);
        Selenide.actions().sendKeys(Keys.ENTER).build().perform(); 
        Selenide.sleep(10000);
        
    }
   
    }

