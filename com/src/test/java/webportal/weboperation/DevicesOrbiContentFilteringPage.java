/**
 *
 */
package webportal.weboperation;

import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DevicesOrbiContentFilteringPageElement;

/**
 * @author ann.fang
 */
public class DevicesOrbiContentFilteringPage extends DevicesOrbiContentFilteringPageElement {
    Logger logger;

    /**
     *
     */
    public DevicesOrbiContentFilteringPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefOrbiContentFiltering);
        MyCommonAPIs.sleepi(5);
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public DevicesOrbiContentFilteringPage(boolean nopage) {
        // TODO Auto-generated constructor stub
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public void gotoPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefOrbiContentFiltering);
        MyCommonAPIs.sleepi(5);
    }
    
    
    /*
    public boolean isIPformat(SelenideElement se) {
        String val = getValue(se);
        if (val.matches("\\d+\\.\\d+\\.\\d+\\.\\d+"))
            return true;
        return false;
    }
    */
}
