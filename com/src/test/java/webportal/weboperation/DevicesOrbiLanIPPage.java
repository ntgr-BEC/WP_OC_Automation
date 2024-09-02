/**
 *
 */
package webportal.weboperation;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.Keys;

import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DevicesOrbiLANIPPageElement;

/**
 * @author lavi
 */
public class DevicesOrbiLanIPPage extends DevicesOrbiLANIPPageElement {
    Logger logger;
    
    /**
     *
     */
    public DevicesOrbiLanIPPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefOrbiLANIP);
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }
    
    public DevicesOrbiLanIPPage(boolean nopage) {
        // TODO Auto-generated constructor stub
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public void changeDHCPPool(int ipstart, int ipend) {
        setDHCP(true,true);
        txtIpStart.clear();
        txtIpStart.sendKeys(String.format("0%d", ipstart));
        txtIpEnd.clear();
        txtIpEnd.sendKeys(String.format("0%d", ipend));
        clickButton(0);
    }
    
    public List<String> changeDHCPPool() {
        List<String> toret = new ArrayList<String>();
        String ipstart = nextIP(getValue(txtIpStart));
        String ipend = getValue(txtIpEnd);
        toret.add(ipstart);
        toret.add(ipend);
        txtIpStart.sendKeys(Keys.BACK_SPACE);
        txtIpStart.sendKeys(Keys.BACK_SPACE);
        txtIpStart.sendKeys(Keys.BACK_SPACE);
        txtIpStart.sendKeys(ipstart.split("\\.")[3]);
        txtIpEnd.sendKeys(Keys.BACK_SPACE);
        txtIpEnd.sendKeys(Keys.BACK_SPACE);
        txtIpEnd.sendKeys(Keys.BACK_SPACE);
        txtIpEnd.sendKeys(ipend.split("\\.")[3]);
        clickButton(0);
        return toret;
    }
}
