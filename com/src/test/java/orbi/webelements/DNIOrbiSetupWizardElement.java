/**
 *
 */
package orbi.webelements;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

/**
 * @author bingke.xue
 *
 */
public class DNIOrbiSetupWizardElement extends MyCommonAPIs {
    public static SelenideElement setupContinueBtn  = $x("//input[@name='enable_telnet']");

}
