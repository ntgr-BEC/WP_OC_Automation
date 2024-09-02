/**
 *
 */
package orbi.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

/**
 * @author bingke.xue
 *
 */
public class MyNetgearPageElement extends MyCommonAPIs {

    public static SelenideElement signInEmailInput = $("#MainContent_email");
    public static SelenideElement signInPwdInput = $("#MainContent_password");
    public static SelenideElement signInBtn = $("#MainContent_btnSubmit");
    public static SelenideElement signOutBtn = $x("//a[@href='/mynetgear/logout.aspx']");
    
    public SelenideElement DeviceInList(String serial) {
        SelenideElement haveDevice = $x("//a[@data-serial='"+serial+"']");
        return haveDevice;
    }
}
