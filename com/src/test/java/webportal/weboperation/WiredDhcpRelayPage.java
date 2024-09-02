/**
 *
 */
package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertTrue;

import java.util.Map;
import java.util.logging.Logger;

import org.apache.poi.hssf.record.PageBreakRecord.Break;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import util.SwitchTelnet;
import util.SwitchCLIUtils;
import webportal.param.CommonDataType.EditLagData;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.WiredLAGPageElement;
import webportal.webelements.WiredDhcpRelayElement;

/**
 * @author Netgear
 */

public class WiredDhcpRelayPage extends WiredDhcpRelayElement {

    /**
    *
    */

    final static Logger logger = Logger.getLogger("WiredDhcpRelayPage");

    public WiredDhcpRelayPage() {
        // TODO Auto-generated constructor stub
        logger.info("init...");
    }

    /**
     * Go to the specified configuration page under DHCP Relay / DHCP Snooping
     */
    public String gotoDhcpConfigPage(SelenideElement dhcpL2RelayGlobalConfig) {
        click(dhcpL2RelayGlobalConfig);
        MyCommonAPIs.waitReady();
        sleep(5, "Opening " + dhcpL2RelayGlobalConfig + " Config page...");
        return portIdXpath;
    }

    /**
     * Provide Xpath for the toggle-switch, which need to be enable or disable for the specified button under the DHCP Relay/Snooping section
     * and then saves the configurations,
     * toggle-switch enable in the first call,
     * disable if calls this second time
     * 
     * Ex:- global Configuration AdminMode, globalConfig UserVlanAdminMode
     */
    public void enableOrDisableDhcpRelayconfigModes(SelenideElement el) {

        if (el.exists()) {
            if (!isEnable(el)) {
                click(el);
                click(saveGlobalConfiguration);
            }
        }

    }
    
    /**
     * 
     * @param el: provide Xpath for the Admin / truest mode on port 1
     * @return
     */
    public boolean checkPortConfigAdminOrTrustModeActivated(SelenideElement el) {
        
        if (el.exists()) {
            if (!isEnable(el)) {
                return true;
            }
        }
        return false;
    }
    /**
     * 
     * @param portIdPath: The Xpath for the specific port
     * @param adminMode: admin mode, null will ignore
     * @param trustMode: trustmode, null will ignore
     */

    public void enablePortConfigAdminModeOnPort(SelenideElement portIdPath, String adminMode, String trustMode) {
        click(portIdPath);
        if (adminMode != null) {
            click(dhcpRelayPortConfigAdminMode);
        }
        click(portIdPath);

        if (trustMode != null) {
            click(dhcpRelayPortConfigTrustMode);
        }
        click(dhcpRelayPortConfigSaveButton);
    }
    /**
     * 
     * @param portIdPath: The port Xpath which need to delete the configuration
     * 
     * Ex: adminmode / 82 option trustmode config
     */
    
    public void deletePortConfigAdminOrTrustModeOnPort(SelenideElement portIdPath) {
        click(portIdPath);
        click(deletePortConfig);
        click(dhcpRelayPortConfigSaveButton);
    }
        
}
