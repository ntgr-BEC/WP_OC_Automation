/**
 *
 */
package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$x;

import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DevicesOrbiTroubleshootElement;

/**
 * @author lavi
 *
 */
public class DevicesOrbiTroubleshootPage extends DevicesOrbiTroubleshootElement {
    Logger logger;

    /**
     *
     */
    public DevicesOrbiTroubleshootPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefOrbiTroubleShoot);
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public DevicesOrbiTroubleshootPage(boolean nopage) {
        // TODO Auto-generated constructor stub
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    /**
     * 0 - upload, 1 - download, 2 - latency
     */
    public float getValue(int index) {
        String sVal = getTexts(txtResultValue).get(index);
        char[] charfield = sVal.toCharArray();
        String clean = "";
        for (int i = 0; i < charfield.length; i++) {
            if ((charfield[i] == '.') || Character.isDigit(charfield[i])) {
                clean += charfield[i];
            }
        }
        logger.info(clean);
        return Float.parseFloat(clean);
    }
    
    /////////////  new functions below  ////////////
    public boolean PingTest(String ip) {
        WebCheck.checkHrefIcon(URLParam.hrefOrbiTroubleShoot);
        gotoPage(0);
        inputHostName.setValue(ip);
        btnRun.click();
        for(int i =0; i<10;i++) {
            if(textSuccess.exists() || textFail.exists()) {
                break;
            }else {
                MyCommonAPIs.sleepi(10);
            }
        }
        
        if(textSuccess.exists()) {
            return true;
        }else if (textFail.exists()){
            return false;
        }else {
            return false;
        }
    }
    
    public boolean DNSLookup(String domainname) {
        WebCheck.checkHrefIcon(URLParam.hrefOrbiTroubleShoot);
        gotoPage(1);
        inputDNSLookupHost.setValue(domainname);
        btnRun.click();
        MyCommonAPIs.sleepi(10);
        
        SelenideElement textdomain = $x("//h6[text()='"+ domainname +"']");
        SelenideElement textipaddr = $x("//div[@class='dnsLookupSuccess']//th[text()='IP Address']/../../../tbody/tr/td");
        
        if(textdomain.exists() && textipaddr.text().contains(".")) {
            return true;
        }else {
            return false;
        }
    }
    
    public boolean Traceroute(String ip) {
        WebCheck.checkHrefIcon(URLParam.hrefOrbiTroubleShoot);
        gotoPage(3);
        inputHostName.setValue(ip);
        btnRun.click();
        for(int i =0; i<10;i++) {
            if(textSuccess.exists() || textFail.exists()) {
                break;
            }else {
                MyCommonAPIs.sleepi(10);
            }
        }
        
        if(textSuccess.exists()) {
            return true;
        }else if (textFail.exists()){
            return false;
        }else {
            return false;
        }
    }
    
}
