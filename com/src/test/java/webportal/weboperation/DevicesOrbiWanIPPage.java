/**
 *
 */
package webportal.weboperation;

import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;

import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DevicesOrbiWANIPPageElement;

/**
 * @author lavi
 */
public class DevicesOrbiWanIPPage extends DevicesOrbiWANIPPageElement {
    Logger logger;

    /**
     *
     */
    public DevicesOrbiWanIPPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefOrbiWANIP);
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public DevicesOrbiWanIPPage(boolean nopage) {
        // TODO Auto-generated constructor stub
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public boolean isIPformat(SelenideElement se) {
        String val = getValue(se);
        if (val.matches("\\d+\\.\\d+\\.\\d+\\.\\d+"))
            return true;
        return false;
    }
}
