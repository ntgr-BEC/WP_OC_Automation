/**
 *
 */
package webportal.publicstep;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.Selectors;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;

/**
 * @author Netgear
 */
public class UserManage extends MyCommonAPIs {
    /**
     * try to logout twice
     */
    void dologout() {
        String sCheck = "[alt=\"User Image\"]";
        if (getCurrentUrl().equals("data:,"))
            return;
        for (int i = 0; i < 2; i++) {
            MyCommonAPIs.waitReady();
            try {
                if (!$(sCheck).exists()) {
                    if (isInLoginPage()) {
                        break;
                    }

                    refresh();
                }
                System.out.println("try to do logout");
                $(sCheck).click();
                MyCommonAPIs.waitReady();
                $(Selectors.byCssSelector(".open ul li:last-child a")).click();
                System.out.println("user is logout");
                MyCommonAPIs.waitReady();
                break;
            } catch (Throwable e) {
                e.printStackTrace();
                takess();
                refresh();
            }
        }
    }

    public void logout() {
        if (WebportalParam.browserIsFailed)
            return;
        try {
            dologout();
        } catch (Throwable e) {
            takess();
            e.printStackTrace();
            if (WebportalParam.curWebDriver != null) {
                WebportalParam.curWebDriver.quit();
            }
        }
    }
}
