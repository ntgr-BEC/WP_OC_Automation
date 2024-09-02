/**
 *
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

/**
 * @author lavi
 *
 */
public class DevicesOrbiConnectedClientsPageElement extends MyCommonAPIs {
    Logger logger;

    public DevicesOrbiConnectedClientsPageElement() {
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
    }
    
    public static String sConnectedClientTable  = "#connectedClientTable tbody";
    public static String sDisconnectedClientTable  = "#disconnectedClientTable tbody";
    public static SelenideElement tabConnected   = $x("//*[contains(@class,'clientConnectedTab')]");
    public static SelenideElement tabDisconnected   = $x("//*[contains(@class,'clientDisconnectedTab')]");
    public static String wiredimg   = "//*[text()='%s']/ancestor::tr/td[2]/p/img[contains(@src,'wired-connectiontype')]";
    public static String wirelessimg   = "//*[text()='%s']/ancestor::tr/td[2]/p/img[contains(@src,'instant-wi-fi')]";
    public static String ssidname   = "//*[text()='%s']/../../td[3]/span";
    public static String macaddress   = "//*[text()='%s']/../../td[6]/span";
    public static String ipaddress   = "//*[text()='%s']/../../td[7]";
    public static SelenideElement searchicon   = $x("//*[@class='icon-search']");
    public static SelenideElement searchinput   = $x("//*[@id='clientSearchInput']");
    //public static SelenideElement searchbtn   = $x("//*[contains(@class,'btn-primary')]");
    public static SelenideElement searchbtn   = $x("//button[@id='search']");
    public static SelenideElement cancelbtn   = $x("//*[contains(@style,'display: block')]//*[contains(@class,'btn-default')]");
    
    public boolean isTableHasData() {
        int tdSize = 0;
        tdSize = $(sConnectedClientTable).$$("td").size();
        
        if (tdSize > 1)
            return true;
        return false;
    }
    
    public boolean isWired(String devicename) {
        SelenideElement el = $x(String.format(wiredimg, devicename));
        if(el.exists()) {
            return true;
        }
        return false;
    }
    
    public boolean isWireless(String devicename) {
        SelenideElement el = $x(String.format(wirelessimg, devicename));
        if(el.exists()) {
            return true;
        }
        return false;
    }
    
    public String connectedSSID(String devicename) {
        SelenideElement el = $x(String.format(ssidname, devicename));
        if(el.exists()) {
            return el.text();
        }
        return "";
    }
    
    public String macAddr(String devicename) {
        SelenideElement el = $x(String.format(macaddress, devicename));
        if(el.exists()) {
            return el.text();
        }
        return "";
    }
    
    public String ipAddr(String devicename) {
        SelenideElement el = $x(String.format(ipaddress, devicename));
        if(el.exists()) {
            return el.text();
        }
        return "";
    }
    
    public SelenideElement pauseresumecheckbox(String devicename) {
        return $x("//span[text()='" + devicename + "']/../../td[9]//input");
    }
    
    public SelenideElement pauseresumeslide(String devicename) {
        return $x("//span[text()='" + devicename + "']/../../td[9]//input");
    }

}
