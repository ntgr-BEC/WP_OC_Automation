package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$x;

import java.util.Map;
import java.util.logging.Logger;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.CopyConfigurationPageElement;

/**
 * @author Netgear
 *
 */

public class CopyConfigurationPage extends CopyConfigurationPageElement {
    /**
    *
    */
    Logger logger;

    public CopyConfigurationPage() {
        // TODO Auto-generated constructor stub
        if (managerdropdown.exists()) {
            managerdropdown.click();
        }
        WebCheck.checkHrefIcon(URLParam.hrefCopyConfiguration);
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public CopyConfigurationPage(boolean noPage) {
        // TODO Auto-generated constructor stub
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public void copyConfigurationToTarget(String target) {
        selectOrg.selectOption(WebportalParam.Organizations);
        MyCommonAPIs.sleepi(3);
        selectLct.selectOption(WebportalParam.location1);
        MyCommonAPIs.sleepi(3);
        $x(String.format(targetOrg, target)).click();
        MyCommonAPIs.sleepi(3);
        saveBtn.click();
        MyCommonAPIs.sleepi(5);
        clickBoxLastButton();
        MyCommonAPIs.sleepi(3);
    }

    public boolean checkSuccessfulMessage() {
        boolean result = false;
        if (successMsg.exists()) {
            result = true;
            logger.info("Successful message displayed.");
        } else if (failedMsg.exists()) {
            logger.info("An error occurred while processing the request. Try again.");
        }
        return result;
    }

    public boolean checkPageIsCorrect(Map<String, String> map) {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        String sourceEle = "//option[text()='%s']";
        String targetOrgEle = "//label[text()='%s']";
        String targetLctEle = "//p[text()='%s']";
        if ($x(String.format(sourceEle, map.get("organization1"))).exists() && $x(String.format(sourceEle, map.get("organization2"))).exists()) {
            selectOrg.selectOption(map.get("organization1"));
            MyCommonAPIs.sleepi(3);
            if ($x(String.format(sourceEle, map.get("location1"))).exists()) {
                selectOrg.selectOption(map.get("organization2"));
                MyCommonAPIs.sleepi(3);
                if ($x(String.format(sourceEle, map.get("location2"))).exists()
                        && $x(String.format(targetOrgEle, map.get("organization1"))).exists()
                        && $x(String.format(targetOrgEle, map.get("organization2"))).exists()
                        && $x(String.format(targetLctEle, map.get("location1"))).exists()
                        && $x(String.format(targetLctEle, map.get("location2"))).exists()) {
                    result = true;
                    logger.info("Page is correct.");
                }
            }
        }
        return result;
    }

}
