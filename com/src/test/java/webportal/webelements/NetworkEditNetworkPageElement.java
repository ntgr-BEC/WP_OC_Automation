/**
 *
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

/**
 * @author zheli
 *
 */
public class NetworkEditNetworkPageElement extends MyCommonAPIs {
    public SelenideElement save         = $("#buSaveBtn");
    public SelenideElement locationName = $("#editName");
    public SelenideElement password     = $("#editAdminPass");
    public SelenideElement passwordShow = $("#hAdminPasstext+label #iEyeIcon");
    public SelenideElement alert        = $(".alert");
    public SelenideElement seCountry    = $("#editCountry");
    public SelenideElement seTZ         = $("#editTimeZone");

    public void showPasswd() {
        if (passwordShow.getAttribute("class").contains("fa-eye-slash")) {
            passwordShow.click();
        }
    }
}
