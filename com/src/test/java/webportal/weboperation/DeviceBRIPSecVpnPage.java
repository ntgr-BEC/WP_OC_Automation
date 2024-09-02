package webportal.weboperation;

import java.util.logging.Logger;

import webportal.param.CommonDataType;
import webportal.param.CommonDataType.IPSecVPNData;
import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DeviceBRIPSecVpnElement;

public class DeviceBRIPSecVpnPage extends DeviceBRIPSecVpnElement {
    Logger logger = Logger.getLogger("DeviceBRIPSecVpnPage");

    public IPSecVPNData testData = null;

    public DeviceBRIPSecVpnPage() {
        logger.info("init...");
    }

    public void initTestData() {
        testData = new CommonDataType().dataIPSec;
    }

    public void gotoPage() {
        WebCheck.checkUrl(URLParam.hrefBRIPSecVpn);
    }

    public void editPolicy(String name) {
        openPolicyName(name);
        addPolicy();
    }

    /**
     * add a policy with all default values
     * 
     * @return
     */
    public String addPolicy() {
        testData.Dump();
        if (!txtPolicyName.exists()) {
            clickAdd();
        }

        setText(txtPolicyName, testData.policyName);
        setSelected(cbPolicyStatus, testData.policyStatus, true);

        setText(txtRemoteGateway, testData.remoteGw);
        setText(txtRemoteSubnet, testData.remoteLannet);
        setText(txtRemoteMask, testData.remoteLanmask);
        setText(txtLocalSubnet, testData.localLannet);
        setText(txtLocalMask, testData.localLanmask);
        txtSharedKey.clear();
        txtSharedKey.sendKeys(testData.preShardkey);
        setIKEVersion(testData.ikeVer);

        openAdv();
        setProposal1(0, testData.phase1Proposal1);
        setProposal1(1, testData.phase1Proposal2);
        setProposal1(2, testData.phase1Proposal3);
        setProposal1(3, testData.phase1Proposal4);
        setExchangeMode(testData.exchangeMode);
        setNegotiationMode(testData.negotiationMode);
        setText(txtph1SaLifetime, testData.ikeLifetime);
        setText(txtDpdInterval, testData.dpd);
        setProposal2(0, testData.phase2Proposal1);
        setProposal2(1, testData.phase2Proposal2);
        setProposal2(2, testData.phase2Proposal3);
        setProposal2(3, testData.phase2Proposal4);
        setText(txtph2SaLifetime, testData.saLifetime);
        if (lbPFS.exists()) {
            lbPFS.selectOption(testData.phase2PFS);
        }

        takess("check all arugments for adding policy");
        btnSave.click();

        String toret = getPageErrorMsg();
        if (btnCreatePolicyDeviceOffline.exists()) {
            btnCreatePolicyDeviceOffline.click();
        }
        if (btnCreatePolicyCancel.exists()) {
            btnCreatePolicyCancel.click();
        }
        sleepsync();
        return toret;
    }

    public String addPolicy(String name) {
        testData.policyName = name;
        return addPolicy();
    }

    public void openPolicy(String name) {
        openPolicyName(name);
    }
}
