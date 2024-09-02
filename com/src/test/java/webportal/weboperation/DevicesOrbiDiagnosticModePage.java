/**
 *
 */
package webportal.weboperation;

import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DevicesOrbiDiagnosticModePageElement;

/**
 * @author ann.fang
 */
public class DevicesOrbiDiagnosticModePage extends DevicesOrbiDiagnosticModePageElement {
    Logger logger;

    /**
     *
     */
    public DevicesOrbiDiagnosticModePage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefOrbiDiagnosticMode);
        MyCommonAPIs.sleepi(5);
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public DevicesOrbiDiagnosticModePage(boolean nopage) {
        // TODO Auto-generated constructor stub
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public void gotoPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefOrbiDiagnosticMode);
        MyCommonAPIs.sleepi(5);
    }
    
    public void EnableDiagMode() {
        if(!inputEnableDiagMod.isSelected()) {
            spanEnableDiagMod.click();
            btnOK.click();
            btnSave.click();
        }
    }
    
    public void DisableDiagMode() {
        if(inputEnableDiagMod.isSelected()) {
            spanEnableDiagMod.click();
            btnSave.click();
        }
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
