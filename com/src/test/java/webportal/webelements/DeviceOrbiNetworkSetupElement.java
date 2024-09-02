package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.logging.Logger;

import org.openqa.selenium.Keys;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.CommonDataType;
import webportal.param.WebportalParam;
import webportal.param.CommonDataType.OrbiNetworkInfoData;

public class DeviceOrbiNetworkSetupElement extends MyCommonAPIs {
    Logger logger = Logger.getLogger("DeviceOrbiNetworkSetupElement");
    
    public SelenideElement     btnAdd         = $("#divConRowSumm h3+div");
    public String              sNetworksTable = "table tbody";
    public String              sNetworkName   = "//tbody//td[contains(text(),'%s')]";
    public OrbiNetworkInfoData networkData    = null;
    
    public void initTestData() {
        networkData = new CommonDataType().dataOrbiNetworkInfo;
    }

    public void dumpInfo() {
        if ($(sNetworksTable).exists()) {
            getTexts(sNetworksTable + " tr");
        }
    }

    public SelenideElement aNetwork(String name) {
//        return $x("//td[text()='"+name+"']");
        return $x("//td[contains(text(),'"+name+"')]");
    }

    public SelenideElement btnNext        = $(".actionBtnRow button:last-child");
    public SelenideElement btnSkip        = $(".actionBtnRow .cancelBtn");
    public SelenideElement txtCurrentPage = $(".currentStep .Clickable");
    public SelenideElement imgSuccessful  = $("img[src*=success]");
    public SelenideElement imgWarning     = $("img[src*=warning]");
    
    public void clickConfirm() {
        btnNext.click();
        waitElement(imgSuccessful);
    }

    public void clickViewNetwork() {
        btnNext.click();
        waitReady();
    }

    public void clickNext() {
        takess();
        if (txtCurrentPage.exists()) {
            getText(txtCurrentPage);
        }
        btnNext.click();
        waitReady();
        if (txtCurrentPage.exists()) {
            getText(txtCurrentPage);
        }
    }

    // page 1
    public SelenideElement txtVlanName        = $("input[name*=vlanName]");
    public SelenideElement txtVlanId          = $("input[name*=vlanId]");
    public SelenideElement cbClientIsolation  = $("#clientIsoSt");
    public SelenideElement cbNetworkIsolation = $("#nwIsoSt");

    public void setPage1Vlan(String vlanName, String vlanId) {
        if (null != vlanName) {
            txtVlanName.setValue(vlanName);
        }
        if (txtVlanId.isEnabled()) {
            txtVlanId.setValue(vlanId);
        }
        clickNext();
    }
    
    public void setPage1Vlan(String vlanName, String vlanId, boolean clientIsolation, boolean networkIsolation) {
        if (null != vlanName) {
            txtVlanName.setValue(vlanName);
        }
        if (txtVlanId.isEnabled()) {
            txtVlanId.setValue(vlanId);
        }
        if(cbClientIsolation.exists()) {
            setSelected(cbClientIsolation, clientIsolation);
        }
        if(cbNetworkIsolation.exists()) {
            setSelected(cbNetworkIsolation, networkIsolation);
        }
        clickNext();
    }
    
    // page 2
    public SelenideElement    btnSelectAll     = $(".ChartAvalDetail div div button");
    public ElementsCollection btnDeviceButtons = $$(".ChartAvalDetail >div >button");

    public String sPortPath(String port) {
        return String.format("//div[@class='ChartAvalDetail']//li//span[text()='%s']", port);
    }
    
    public String sPortPath2(String port) {
        return String.format("//div[@class='ChartAvalDetail']//li//span[contains(text(),'%s')]", port);
    }
    
    public void clickPort(String port) {
        if($x(sPortPath(port)).exists()) {
            $x(sPortPath(port)).click(); // fix bug : css selector -> xpath 
        }
        else {
            $x(sPortPath2(port)).click();
        }
        
    }

    /**
     * @param mode
     *             0-access, 1-trunk
     */
    public void clickPortMode(int mode) {
        btnDeviceButtons.get(mode).click();
    }

    /**
     * @param portList
     * @param portMode
     *                 0-access, 1-trunk
     */
    public void setPage2PortMembers(String portList, int portMode) {
        logger.info(portList + "/" + portMode);
        if (!portList.isEmpty()) {
            for (String s : portList.split(",")) {
                clickPort(s);
                waitReady();
            }
            clickPortMode(portMode);
        }
        clickNext();
    }

    // page 3
    public void setPage3Ssid() {
        clickNext();
    }

    // page 4
    public SelenideElement txtIpaddress      = $("#inpTextIpAddrSwitchIpSett");
    public SelenideElement cbDhcpServers     = $("#inpCheckBoxSwitchIpSett");
    public SelenideElement txtIpaddressStart = $("#inpNameSwitchIpSett");
    public SelenideElement txtIpaddressEnd   = $("#inpIpDenServer1SwitchIpSett");

    public void setPage4DHCPServers(String ip, boolean enableDHCP, String ipStart, String ipEnd) {
        if (!ip.isEmpty()) {
            txtIpaddress.clear();
            txtIpaddress.sendKeys(ip);
        } else {
            if (txtIpaddress.getValue().isEmpty()) {
                btnSkip.click();
                return;
            }
        }
        
        setSelected(cbDhcpServers, enableDHCP);
        if (enableDHCP) {
            if (!ipStart.isEmpty()) {
                txtIpaddressStart.clear();
                txtIpaddressStart.setValue(ipStart);
            }
            if (!ipEnd.isEmpty()) {
                txtIpaddressEnd.clear();
                txtIpaddressEnd.setValue(ipEnd);
            }
        }
        clickNext();
    }

}
