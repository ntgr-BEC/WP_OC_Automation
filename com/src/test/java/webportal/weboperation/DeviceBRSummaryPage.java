package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$$;

import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;

import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DeviceBRSummaryElement;

public class DeviceBRSummaryPage extends DeviceBRSummaryElement {
    Logger logger = Logger.getLogger("DeviceBRSummaryPage");

    public DeviceBRSummaryPage() {
        logger.info("init...");
    }

    public void gotoPage() {
        WebCheck.checkUrl(URLParam.hrefBRSummary);
    }

    /**
     * @param text
     *            1-4 for lan, 0 for wan
     * @return
     */
    public SelenideElement portChoice(String text) {
        SelenideElement port = $$("li .ethernet-block").get(Integer.parseInt(text));
        return port;
    }

    /**
     * @param iIndex
     *            from 0~5 (name,model,uptime,version,lanip,mac)
     */
    public String getFieldValue(int iIndex) {
        return getTexts(sFields).get(iIndex);
    }

}
