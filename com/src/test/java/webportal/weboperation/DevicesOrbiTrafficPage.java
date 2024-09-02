/**
 *
 */
package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$$;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;

import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DevicesOrbiTrafficPageElement;

/**
 * @author lavi
 */
public class DevicesOrbiTrafficPage extends DevicesOrbiTrafficPageElement {
    Logger logger;

    /**
     *
     */
    public DevicesOrbiTrafficPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefOrbiTraffic);
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public DevicesOrbiTrafficPage(boolean nopage) {
        // TODO Auto-generated constructor stub
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public List<String> getUploads() {
        List<String> aList = new ArrayList();
        // return getTexts(sTrafficUploadValue);
        for (SelenideElement se : $$(sTrafficUploadValue)) {
            String toadd = se.getAttribute("val");
            logger.info(toadd);
            aList.add(toadd);
        }
        return aList;
    }

    public List<String> getDownloads() {
        List<String> aList = new ArrayList();
        for (SelenideElement se : $$(sTrafficDownloadValue)) {
            String toadd = se.getAttribute("val");
            logger.info(toadd);
            aList.add(toadd);
        }
        return aList;
    }
}
