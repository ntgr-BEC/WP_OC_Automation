/**
 *
 */
package webportal.weboperation;

import java.util.logging.Logger;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DashboardLocationPageElement;

/**
 * @author zheli
 *
 */
public class DashboardLocationPage extends DashboardLocationPageElement {
    Logger logger;

    /**
     *
     */
    public DashboardLocationPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkUrl(URLParam.hrefDevices);
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public void enterDevices() {
        WebCheck.checkUrl(URLParam.hrefDevices);
    }

    public String confirmHealthStatus() {
        return systemHealth.getText();
    }

    public String confirmStoreageVolume() {
        return storagevolume.getText();
    }

    public void refresh() {
        properitieswindow.hover();
        properties.click();
        refresh.click();
        MyCommonAPIs.waitElement(storagevolume);
    }

    public void refreshstorage() {
        storageutilizationwindow.hover();
        storagemenu.click();
        refreshstorage.click();
        MyCommonAPIs.waitElement(storageinfo);
    }
}
