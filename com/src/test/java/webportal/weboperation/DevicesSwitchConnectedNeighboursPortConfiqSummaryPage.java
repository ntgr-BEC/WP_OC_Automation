/**
 *
 */
package webportal.weboperation;

import java.util.logging.Logger;

import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DevicesSwitchConnectedNeighboursPortConfiqSummaryPageElement;

/**
 * @author zheli
 *
 */
public class DevicesSwitchConnectedNeighboursPortConfiqSummaryPage extends DevicesSwitchConnectedNeighboursPortConfiqSummaryPageElement {
    Logger logger;

    /**
     *
     */
    public DevicesSwitchConnectedNeighboursPortConfiqSummaryPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefDevicesSwitchConnectedNeighborsPortConfigSummary);
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public DevicesSwitchConnectedNeighboursPortConfiqSettingsPage enterPortSettingPage() {
        return new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
    }

    public boolean checkSettingIcon() {
        boolean result = settings.isDisplayed();
        return result;
    }

    public float getByteSent() {
        return Float.parseFloat(BytesSent.getText());
    }

    public float getByteRecv() {
        return Float.parseFloat(BytesReceived.getText());
    }

    public String getVlanId() {
        return getText(vlanList);
    }
}
