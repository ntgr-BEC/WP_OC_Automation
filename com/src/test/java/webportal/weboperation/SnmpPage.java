package webportal.weboperation;

import java.util.logging.Logger;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.SnmpElement;

public class SnmpPage extends SnmpElement {
    Logger logger = Logger.getLogger("SnmpPage");
    
    public SnmpPage() {
        logger.info("init...");
    }
    
    public void gotoPage() {
        logger.info("SNMP page open");
        open(WebportalParam.serverUrl + URLParam.hreflocSNMP);
        if (!cbEnable.exists()) {
            WebCheck.checkUrl(URLParam.hrefSNMP);
        }
    }
    
    public void clickSave() {
        if (btnSave.isEnabled()) {
            btnSave.click();
            MyCommonAPIs.sleepi(10);

            clickBoxLastButton();
        }
        getPageErrorMsg();
    }
    
    public void clickSave1() {
        if (btnSave.isEnabled()) {
            btnSave.click();
            MyCommonAPIs.sleepi(10);
        }
        getPageErrorMsg();
    }
    
    public void clearSnmp() {
        if (btnClear.isEnabled()) {
            btnClear.click();
            clickBoxLastButton();
            clickBoxLastButton();
            MyCommonAPIs.sleepi(20);
        }
    }
    
    /**
     * @param  model
     *               true if sw is GC110 or GC510
     * @return
     */
    public boolean isSnmpDisabled(String model) {
        if (model.contains("GC110") || model.contains("GC510"))
            return true;
        return false;
    }
    
    /**
     * @return check whether sw1 has snmp feature
     */
    public boolean isSnmpDisabled() {
        String model = WebportalParam.sw1Model;
        if (model.contains("GC110") || model.contains("GC510"))
            return true;
        return false;
    }
    
    /**
     * @param enable
     *                    true to enable trap
     * @param ip
     * @param pw
     * @param clickCancel
     *                    true to click cancel
     */
    public void setSnmp(boolean enable, String ip, String pw, boolean clickCancel) {
        setSelected(cbEnable, enable, true);
        setText(txtIpAddress, ip);
        setText(txtCommunityString, pw);
        if (clickCancel) {
            btnCancel.click();
        } else {
            clickSave();
            MyCommonAPIs.sleepi(30);
        }
    }
}
