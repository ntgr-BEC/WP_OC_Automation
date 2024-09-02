/**
 *
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;

import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

/**
 * @author lavi
 */
public class DevicesOrbiTrafficPageElement extends MyCommonAPIs {
    Logger logger;
    
    public DevicesOrbiTrafficPageElement() {
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
    }
    
    public SelenideElement seLink                = $("#divSideBarSecEditVlan a[href*=traffic]");
    public String          sTrafficDownloadValue = "g[rel*='1'] path";
    public String          sTrafficUploadValue   = "g[rel*='2'] path";
    
    public SelenideElement txtDownload = $(".apexcharts-legend-series:nth-child(1) span:last-child");
    public SelenideElement txtUpload   = $(".apexcharts-legend-series:nth-child(1) span:last-child");

    public boolean isGray() {
        if (seLink.getAttribute("class").contains("disable"))
            return true;
        else
            return false;
    }
}
