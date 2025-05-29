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
import webportal.webelements.WiredDhcpSnoopingElement;
import webportal.webelements.WiredLAGPageElement;

/**
 * @author Netgear
 */

public class WiredDhcpSnoopingPage extends WiredDhcpSnoopingElement {

    /**
    *
    */

    final static Logger logger = Logger.getLogger("WiredDhcpSnoopingPage");

    public WiredDhcpSnoopingPage() {
        // TODO Auto-generated constructor stub
        logger.info("init...");
    }

    /**
     * Go to the specified configuration page under DHCP Snooping
     */
    public String gotoDhcpSnoopingConfigPage(SelenideElement dhcpSnoopingGlobalConfig) {
        click(dhcpSnoopingGlobalConfig);
        MyCommonAPIs.waitReady();
        sleep(5, "Opening " + dhcpSnoopingGlobalConfig + " Config page...");
        return portIdXpath;
    }

    /**
     * 
     * @param portNo:
     *            The portno(0 - (n-1)) which is select for the configuration
     * @param TrustMode:
     *            string to select trust mode, null to ignore
     * @param InvalidPackets:
     *            string to select invalid packets button, null to ignore
     */

    public void enablePortSpecificConfigOnPort(String portNo, String TrustMode, String InvalidPackets) {

        if (TrustMode != null) {
            click(txtPortSelection(WebportalParam.sw1deveiceName, portNo));
            click(selectButton("Trust Mode"));
        }

        if (InvalidPackets != null) {
            click(txtPortSelection(WebportalParam.sw1deveiceName, portNo));
            click(selectButton("Invalid Packets"));
        }
        click(saveGlobalConfiguration);
    }

    /**
     * 
     * @param portNo:
     *            The port index(0 to n-1) which need to delete the configuration
     * 
     *            Ex: trustmode / Invalid packets config
     */

    public void deletePortConfigTrustOrInvalidPackets(String portNo) {
        click(txtPortSelection(WebportalParam.sw1deveiceName,portNo));
        click(selectButton("Delete"));
        click(saveGlobalConfiguration);
    }

    public void enableOrDisableDhcpSnoopingconfigModes(SelenideElement el) {

        if (el.exists()) {
            if (!isEnable(el)) {
                click(el);
                MyCommonAPIs.sleepi(5);
                click(saveGlobalConfiguration);
                MyCommonAPIs.sleepi(5);
            }
        }

    }

}
