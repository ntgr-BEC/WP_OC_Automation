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
public class DNIOrbiCommanElement extends MyCommonAPIs {
    public static SelenideElement topIframe   = $("#topframe");
    public static SelenideElement formIframe    = $("#formframe");
    public static SelenideElement contentIframe = $("#content_frame");
    public static SelenideElement newarchcontentIframe = $("#contentframe");
    public static SelenideElement newarchloginusername = $("#login_username");
    public static SelenideElement newarchloginpassword = $("#login_password");
    public static SelenideElement newarchloginbtn = $("#login");
    
    public static SelenideElement saveBtn = $x("//*[@id='yes' or @id='save']"); //$("#save");
    public static SelenideElement yesBtn = $("#save");
    public static SelenideElement noBtn = $("#no");
    public static SelenideElement applyBtn = $x("//input[@name='Apply']");
    public static SelenideElement alertOKBtn = $x("//div[@id='msgDiv']//input[@value='OK']");
}
