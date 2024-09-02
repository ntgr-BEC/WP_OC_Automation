package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$x;

import java.util.logging.Logger;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.MyDevicesPageElement;

/**
 * @author Netgear
 *
 */

public class MyDevicesPage extends MyDevicesPageElement {
    /**
    *
    */
    Logger logger;

    public MyDevicesPage() {
        // TODO Auto-generated constructor stub
        if (managerdropdown.exists()) {
            managerdropdown.click();
        }
        WebCheck.checkHrefIcon(URLParam.hrefMyDevices);
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
        refresh();
    }

    public MyDevicesPage(boolean noPage) {
        // TODO Auto-generated constructor stub
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    public void filterSelectDevice(String[] devices) {
        if ($x(mydevicesbar).getAttribute("class").equals("nsaccord-head")) {
            plusmydevices.click();
        }
        filterdevices.click();
        MyCommonAPIs.sleepi(3);
        for (String device : devices) {
            switch (device) {
            case ("switch"):
                if (filterswitchnew.exists()) {
                    filterswitchnew.click();
                } else {
                    filterswitch.click();
                }
                MyCommonAPIs.sleepi(1);
                break;
            case ("nas"):
                if (filternasnew.exists()) {
                    filternasnew.click();
                } else {
                    filternas.click();
                }
                MyCommonAPIs.sleepi(1);
                break;
            case ("ap"):
                if (filterapnew.exists()) {
                    filterapnew.click();
                } else {
                    filterap.click();
                }
                MyCommonAPIs.sleepi(1);
                break;
            }
        }
        applyfilterdevice.click();
    }

    public boolean checkFilterFeature(String number) {
        boolean result = false;
        if ($x(mydevicesbar).getAttribute("class").equals("nsaccord-head")) {
            plusmydevices.click();
        }
        MyCommonAPIs.sleepi(5);
        if (checkdevicesno.exists()) {
            if ($x("//h5[text()='" + number + "']").exists()) {
                result = true;
                logger.info("Device existed.");
            }
        }
        return result;
    }

}
