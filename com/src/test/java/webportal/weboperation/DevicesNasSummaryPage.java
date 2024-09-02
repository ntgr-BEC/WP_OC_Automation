/**
 * 
 */
package webportal.weboperation;

import java.util.logging.Logger;

import com.codeborne.selenide.Selenide;

import util.MyCommonAPIs;
import webportal.publicstep.WebCheck;

/**
 * @author zheli
 *
 */
public class DevicesNasSummaryPage extends webportal.webelements.DevicesNasSummaryPageElement {
    Logger logger;

    /**
     * 
     */
    public DevicesNasSummaryPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon("#/devices/nas/summary");
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
    }

    public String getDiskinfo() {
        return diskConsume.getText();
    }

    public String getnameinfo() {
        return nasName.getText();
    }

    public String getmodelinfo() {
        return nasModel.getText();
    }

    public String getserialnumberinfo() {
        return nasSerialNumber.getText();
    }

    public String getnasantivirusinfo() {
        return nasAntivirus.getText();
    }

    public String getfwinfo() {
        return nasFirmwareVersion.getText();
    }

    public String gettemp() {
        return nasTempatatureDisk.getText();
    }

    public String getsystemtemp() {
        return nasSystemTemp.getText();
    }

    public String getstorageused() {
        return nasStorageUsed.getText();
    }

    public boolean confirmnasimage() {
        return nasImage.exists();
    }

    public boolean confirmnascapacity() {
        return nasCapacity.exists();
    }

    public String rebootYes() {
        Selenide.sleep(5 * 1000);
        nasReboot.click();
        nasRebootYes.click();
        Selenide.sleep(10 * 1000);
        return rebootSucessDialog.getText();
    }

    public String deleteYes() {
        Selenide.sleep(5 * 1000);
        nasDelete.click();
        nasDeleteYes.click();
        Selenide.sleep(10 * 1000);
        return deleteSucess.getText();
    }

    public String confirmDiskDiscoveryPage() {
        return diskOverview.getText();
    }

    public String confirmDeviceDiscoveryPage() {
        return deviceDetails.getText();
    }

    public void nextUsbPage() {
        next.click();
        MyCommonAPIs.waitElement(usbInfo);
    }

    public String confirmShareInfo() {
        return shareInfo.getText();
    }
}
