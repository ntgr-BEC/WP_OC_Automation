/**
 *
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;

import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

/**
 * @author lavi
 */
public class DevicesOrbiFirmwarePageElement extends MyCommonAPIs {
    Logger logger;
    
    public DevicesOrbiFirmwarePageElement() {
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
    }
    
    public String          sOutdoorTable = "#OutdoorSatellite tbody";
    public SelenideElement btnUpgrade    = $("#divStDetsFirmWare button");
}
