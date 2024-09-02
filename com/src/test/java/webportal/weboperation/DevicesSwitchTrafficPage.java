/**
 *
 */
package webportal.weboperation;

import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.codeborne.selenide.WebDriverRunner;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DevicesSwitchTrafficPageElement;

/**
 * @author zheli
 *
 */
public class DevicesSwitchTrafficPage extends DevicesSwitchTrafficPageElement {
    final static Logger logger = Logger.getLogger("DevicesSwitchTrafficPage");

    /**
     *
     */
    public DevicesSwitchTrafficPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefDevicesSwitchTraffic);
        MyCommonAPIs.sleep(5 * 1000);
        logger.info("init...");
    }

    public boolean checkExist() {
        boolean result = true;
        if (!selectWeek.isDisplayed()) {
            logger.info("failed -- 1");
            result = false;
        }
        WebDriver driver = WebDriverRunner.getWebDriver();
        Select level = new Select(driver.findElement(By.id("selWeekSwitchTrf")));
        List<WebElement> webElements = level.getOptions();
        for (WebElement webElement : webElements) {
            String sText = webElement.getText();
            if (!(sText.equals(WebportalParam.getLocText("switchTraffic", "week"))
                    || sText.equals(WebportalParam.getLocText("switchTraffic", "hours"))
                    || sText.equals(WebportalParam.getLocText("switchTraffic", "hours8"))
                    || sText.equals(WebportalParam.getLocText("switchTraffic", "month1")))) {
                logger.info("failed -- 2: " + sText);
                result = false;
            }
        }
        if (!swichWeekrange.isDisplayed()) {
            logger.info("failed -- 3");
            result = false;
        }
        if (!smalltxt.isDisplayed() || !smalltxt.getText().contains(WebportalParam.getLocText("consumed by Switch"))) {
            logger.info("failed -- 4");
            result = false;
        }
        return result;
    }
}
