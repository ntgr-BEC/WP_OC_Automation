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
public class DNIOrbiAdvancedRouterApModePageElement extends MyCommonAPIs {
    public static SelenideElement radioNameRouterAPMode  = $x("//input[@name='operation_type']");
}
