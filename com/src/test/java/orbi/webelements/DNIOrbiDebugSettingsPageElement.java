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
public class DNIOrbiDebugSettingsPageElement extends MyCommonAPIs {
    public static SelenideElement enableTelnetCheckBox  = $x("//input[@name=\"enable_telnet\"]");
    public static SelenideElement newarchTelnetInput  = $x("//input[@id='telnet']");
    public static SelenideElement newarchTelnetCheckBox  = $x("//input[@id='telnet']/../label");

}
