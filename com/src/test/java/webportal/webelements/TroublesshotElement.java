/**
 *
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$x;

import org.apache.log4j.Logger;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

/**
 * @author zheli
 *
 */
public class TroublesshotElement extends MyCommonAPIs {
	Logger logger = Logger.getLogger("TroublesshotElement");

    // locationBarIcons
    public static SelenideElement Networkconfigurationbutton     = $x("//*[@id=\"spnStDetSumm\"]/a/small");
    public static SelenideElement Quickviewexits     = $x("//*[@id=\"quickView\"]");
    public static SelenideElement Troubleshot     = $x("//a[contains(text(),'Troubleshoot')]");
    public static SelenideElement TroubleshotDeviceConfigurationAP     = $x("//p[contains(text(),'4XT27CE300180')]/a");
    public static SelenideElement NetworkConfiguration     = $x("//a[contains(text(),'Network Configuration')]");
    
}
