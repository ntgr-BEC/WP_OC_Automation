package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import util.BRUtils;
import util.MyCommonAPIs;

public class DeviceBRIPSecVpnElement extends MyCommonAPIs {
    Logger logger = Logger.getLogger("DeviceBRIPSecVpnElement");
    
    public String          txtAdvancedSettings = "#divConSecConnNeigh h3";
    public String          cbAdvancedSettings  = "#divConSecConnNeigh h3 span i+i";
    public SelenideElement txtPolicyName       = $(Selectors.byName("ruleName"));
    public SelenideElement cbPolicyStatus      = $x("//*[contains(@name, 'ruleStatus')]");
    public SelenideElement lbPolicyMode        = $(Selectors.byName("mode"));
    public SelenideElement txtRemoteGateway    = $(Selectors.byName("remoteGateway"));
    public SelenideElement txtRemoteSubnet     = $(Selectors.byName("remoteSubnet"));
    public SelenideElement txtRemoteMask       = $(Selectors.byName("remoteMask"));
    public SelenideElement txtLocalSubnet      = $(Selectors.byName("localSubnet"));
    public SelenideElement txtLocalMask        = $(Selectors.byName("localMask"));
    public SelenideElement txtSharedKey        = $(Selectors.byName("sharedKey"));
    
    public SelenideElement btnAdd1 = $("#divConSecConnNeigh .loginBtnSection");
    public SelenideElement btnAdd2 = $("#divConSecConnNeigh .locationBarIcons span");
    
    public String tablePolicy     = "tbody";
    public String tablePolicyName = "td:nth-of-type(1) span";
    public String tablePolicySta  = "td:nth-of-type(3) span";
    
    public void clickAdd() {
        if (btnAdd2.exists()) {
            btnAdd2.click();
        } else {
            btnAdd1.click();
        }
    }
    
    public List<String> getPolicyNameList() {
        if ($(tablePolicyName).exists())
            return getTexts(tablePolicyName);
        else
            return new ArrayList<String>();
    }
    
    public List<String> getPolicyStatusList() {
        return getTexts(tablePolicySta);
    }
    
    public void openPolicyName(String name) {
        editLine(tablePolicy, 1, name, 0);
    }
    
    public void deletePolicyName(String name) {
        editLine(tablePolicy, 1, name, 1);
        clickBoxLastButton();
        sleep(10, "wait refresh");
    }
    
    public void deletePolicyNames() {
        for (String name : getPolicyNameList()) {
            deletePolicyName(name);
        }
        new BRUtils().remoteIpSec();
    }
    
    public void openAdv() {
        setExpand(txtAdvancedSettings, cbAdvancedSettings);
    }
    
    /**
     * @param opt
     *            0 - v1, 1 - v2
     */
    public void setIKEVersion(int opt) {
        $$(Selectors.byXpath("//input[contains(@name, 'ikev')]/..")).get(opt).click();
    }
    
    public void setExchangeMode(int opt) {
        $$(Selectors.byXpath("//input[contains(@name, 'exchangeMode')]/..")).get(opt).click();
    }
    
    public void setNegotiationMode(int opt) {
        $$(Selectors.byXpath("//input[contains(@name, 'negMode')]/..")).get(opt).click();
    }
    
    public ElementsCollection lbProposal1                  = $$(Selectors.byName("proposal1"));
    public SelenideElement    txtph1SaLifetime             = $(Selectors.byName("ph1SaLifetime"));
    public SelenideElement    cbDPDStatus                  = $(Selectors.byName("dpdStatus"));
    public SelenideElement    txtDpdInterval               = $(Selectors.byName("dpdInterval"));
    public ElementsCollection lbProposal2                  = $$(Selectors.byName("proposal2"));
    public SelenideElement    lbPFS                        = $(Selectors.byName("pfs"));
    public SelenideElement    txtph2SaLifetime             = $(Selectors.byName("ph2SaLifetime"));
    public SelenideElement    btnCreatePolicyDeviceOffline = $(".successModalAddIPSec.in button");
    public SelenideElement    btnCreatePolicyCancel        = $(".actionBtnRow .cancelBtn");
    
    /**
     * @param index
     *              0 - 4
     * @param val
     */
    public void setProposal1(int index, String val) {
        logger.info(index + "/" + val);
        if (null != val) {
            lbProposal1.get(index).selectOption(val);
        }
    }
    
    public void setProposal2(int index, String val) {
        logger.info(index + "/" + val);
        if (null != val) {
            lbProposal2.get(index).selectOption(val);
        }
    }
    
    public SelenideElement btnCacel = $("#divConSecConnNeigh .cancelBtn");
    public SelenideElement btnSave  = $("#divConSecConnNeigh .saveBtn");
    
}
