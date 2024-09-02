/**
 *
 */
package orbi.webelements;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

/**
 * @author bingke.xue
 *
 */
public class DNIOrbiAdvancedInternetSetupPageElement extends MyCommonAPIs {
    public static SelenideElement firstNumWANIp  = $("#wpethr1");
    public static SelenideElement secondNumWANIp  = $("#wpethr2");
    public static SelenideElement thirdNumWANIp  = $("#wpethr3");
    public static SelenideElement forthNumWANIp  = $("#wpethr4");
}
