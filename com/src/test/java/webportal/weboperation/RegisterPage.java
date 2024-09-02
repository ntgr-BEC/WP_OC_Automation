/**
 *
 */
package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.WebDriverRunner;

import util.Pause;
import util.TimeUtils;
import webportal.param.WebportalParam;
import webportal.webelements.RegisterPageElement;

/**
 * @author zheli
 *
 */
public class RegisterPage extends RegisterPageElement {
    /**
     *
     */
    public RegisterPage() {
        // TODO Auto-generated constructor stub
        String currentUrl = WebDriverRunner.url();
        String url = WebportalParam.serverUrl + "#/register";
        if (currentUrl.contains(url)) {
        } else {
            open(url);
        }
        System.out.println("RegisterPage init...");
    }

    public void inputRegisterInfo(Map<String, String> infoMap) {
        Map<String, String> map = setRegisterInfoMap(infoMap);
        emailAdress.sendKeys(map.get("emailAdress"));
        password.sendKeys(map.get("password"));
        confirmPassword.sendKeys(map.get("confirmPassword"));
        firstname.sendKeys(map.get("firstname"));
        lastName.sendKeys(map.get("lastName"));
        countryCode.selectOption(map.get("countryCode"));
        subscription.click();
        termsCondition.click();
        signUpButton.click();
        Pause pause = new Pause();
        pause.seconds(1);
        agreeButton.click();
    }

    public Map<String, String> setRegisterInfoMap(Map<String, String> map) {
        Map<String, String> defaultmap = new HashMap<String, String>();
        String email = TimeUtils.getMillisecond() + "@mailcatch.com";
        defaultmap.put("emailAdress", email);
        defaultmap.put("password", "Netgear#1");
        defaultmap.put("confirmPassword", "Netgear#1");
        defaultmap.put("countryCode", "China");
        defaultmap.put("firstname", "firstname");
        defaultmap.put("lastName", "lastName");
        defaultmap.putAll(map);
        return defaultmap;
    }

    public void mailmatch(Map<String, String> map) {
        String url = "http://mailcatch.com/en/temporary-inbox?box=" + map.get("emailAdress");
        open(url);
        Pause pause = new Pause();
        for (int i = 0; i < 10; i++) {
            if ($(Selectors.byXpath("//*[@id=\"mailsList\"]/table/tbody/tr[2]/td[2]/strong/a")).exists()) {
                $(Selectors.byXpath("//*[@id=\"mailsList\"]/table/tbody/tr[2]/td[2]/strong/a")).click();
            } else {
                $(Selectors.byXpath("//*[@id=\"mailsList\"]/h2/a[2]/img")).click();
                System.out.println("not fonud email");
                pause.seconds(5);
            }
        }
        WebDriver webDriver = WebDriverRunner.getWebDriver();
        webDriver.switchTo().frame("emailframe");
        $(Selectors.byXpath("/html/body/table/tbody/tr/td/table/tbody/tr[3]/td/table/tbody/tr/td/table/tbody/tr/td/p[3]/span/a")).click();
    }
}
