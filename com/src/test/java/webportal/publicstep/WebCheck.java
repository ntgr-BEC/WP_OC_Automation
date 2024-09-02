/**
 *
 */
package webportal.publicstep;

import static com.codeborne.selenide.Selenide.$;

import java.util.logging.Logger;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.weboperation.WebportalLoginPage;

/**
 * @author zheli
 */
public class WebCheck extends MyCommonAPIs {
    final static Logger logger = Logger.getLogger("WebCheck");
    
    /**
     *
     */
    public WebCheck() {
        // TODO Auto-generated constructor stub
    }
    
    public static void checkHrefIcon(String expectUrl) {
        logger.info(expectUrl);
        System.out.println(expectUrl);
       SelenideElement icon = $("[href=\"" + expectUrl + "\"]");
        if (icon.exists()) {
            try {
                icon.click();
                MyCommonAPIs.waitReady();
            } catch (Throwable e) {
                takess();
                e.printStackTrace();
                checkUrl(expectUrl);
                Selenide.refresh();
            }
        } else {
            checkUrl(expectUrl);
        }
        
        MyCommonAPIs.sleep(5 * 1000);
    }
    
    private static SelenideElement $x(String string) {
        // TODO Auto-generated method stub
        return null;
    }

    public static void checkUrl(String expectUrl) {
        logger.info("start to check: " + expectUrl);
        String url = WebportalParam.serverUrl + expectUrl;
        MyCommonAPIs.waitReady();
        String currentUrl = getCurrentUrl();
        
        if (currentUrl.contains("#/locked") || currentUrl.toLowerCase().contains("about:blank")) {
            try {
                new UserManage().logout();
            } catch (Throwable e) {
                e.printStackTrace();
            }
            
            new WebportalLoginPage(true).defaultLogin();
            MyCommonAPIs.waitReady();
        }
        
        for (int i = 0; i < 2; i++) {
            currentUrl = getCurrentUrl();
            if (!currentUrl.equals(url)) {
                logger.info("lets open url directly");
                Selenide.open(url);
            } else {
                logger.info("lets refresh url again");
                Selenide.refresh();
            }

            MyCommonAPIs.waitReady();
            currentUrl = getCurrentUrl();
            if (currentUrl.contains(url)) {
                break;
            } else {
                logger.info("not opened, try again!");
                Selenide.refresh();
            }
        }
        MyCommonAPIs.waitReady();
    }
    
    public String getPageSource() {
        return WebDriverRunner.source();
    }
}
