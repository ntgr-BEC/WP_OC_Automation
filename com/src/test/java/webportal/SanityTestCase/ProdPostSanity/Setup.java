package webportal.SanityTestCase.ProdPostSanity;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import testbase.TestCaseBase;
import util.MyCommonAPIs;
import util.SwitchCLIUtils;
import webportal.param.WebportalParam;
import webportal.webelements.DevicesDashPageElements;
import webportal.weboperation.DevicesSwitchConnectedNeighboursPortConfiqSettingsPage;
import webportal.weboperation.DevicesSwitchPortMirrorPage;
import webportal.weboperation.DevicesSwitchSummaryPage;
import webportal.weboperation.WebportalLoginPage;
import webportal.weboperation.WiredGroupPortConfigPage;
import webportal.weboperation.WiredVLANPage;

public class Setup extends TestCaseBase implements Config {
    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        WebportalParam.enableBatch = true;
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
        ddp.gotoPage();
        runStepTest(this);
        MyCommonAPIs.sleep(300, "wait all configuration done");
    }

    public void test_clean_data0() {
        for (String sNo : DevicesDashPageElements.mapDeviceList.keySet()) {
            String sIp = DevicesDashPageElements.mapDeviceList.get(sNo).get("Ip");
            String sModel = DevicesDashPageElements.mapDeviceList.get(sNo).get("Model");

            SwitchCLIUtils.setSwitchInfo(sIp, sModel);
            SwitchCLIUtils.cleanVlan();
            SwitchCLIUtils.cleanTimeSchdule();
            SwitchCLIUtils.cleanSNMP(false);
            SwitchCLIUtils.cleanIpRouter();
            SwitchCLIUtils.cleanMacACL();
            SwitchCLIUtils.cleanRadius();
            
        }
    }

    public void test_createVoiceVlan_setup_webportal1() {
        wvp.gotoPage();
        wvp.newVlan("Voice VLAN", "4088", 1);
        netsp.gotoPage();
        netsp.openNetwork("testnet4088");
        netsp.gotoStep(2);
        netsp.setSanityPortMembers(false);
        netsp.finishAllStep();
        MyCommonAPIs.sleep(100, "wait to avoid block");
    }

    // public void test_createVideoVlan_setup_webportal() {
    // netsp.gotoPage();
    // netsp.createNetwork("Video VLAN", 2, "", "");
    // }

    public void test_createDataVlan_setup_webportal1() {
        netsp.gotoPage();
        netsp.createNetwork("net" + dataVlanID, 0, "vlan" + dataVlanID, dataVlanID);
        netsp.gotoPage();
        netsp.openNetwork("net" + dataVlanID);
        netsp.gotoStep(2);
        netsp.setSanityPortMembers(true);
        netsp.finishAllStep();
        MyCommonAPIs.sleep(100, "wait to avoid block");
    }

    public void test_Radius_setup_webportal2() {
        rdp.gotoPage();
        rdp.enableAuth(radiusIp1, radiusIp2);
        rcp.gotoPage();
        for (String sNo : DevicesDashPageElements.mapDeviceList.keySet()) {
            String sName = DevicesDashPageElements.mapDeviceList.get(sNo).get("Name");
            String sModel = DevicesDashPageElements.mapDeviceList.get(sNo).get("Model");
            // "PRJCBUGEN-28956"
            rcp.setManagerPort(sName, sModel);
            rcp.setOtherPort(sName, "1", 2);
            rcp.setOtherPort(sName, "2", 0);
            rcp.setOtherPort(sName, "3", 1);
            rcp.setEnable(sName, true);
            MyCommonAPIs.sleep(100, "wait to avoid block");
        }
        handle.clickButton(0);
    }

    public void test_ACL_setup_webportal4() {
        netsp.gotoPage();
        netsp.mamData.devName = "test-macip";
        netsp.mamData.devMac = macACL;
        netsp.mamData.devIp = ipACL;
        netsp.createSanityACLNetwork("net" + aclVlanID, aclVlanID, 0);
        MyCommonAPIs.sleep(100, "wait to avoid block");
    }

    public void test_Routing_setup_webportal4() {
        netsp.gotoPage();
        netsp.openNetwork("net" + aclVlanID);
        netsp.gotoStep(4);
        netsp.setNetwork4Sanity(ipRouting);
        netsp.finishAllStep();
        MyCommonAPIs.sleep(100, "wait to avoid block");
    }

    public void test_LAG_setup_webportal2() {
        List<String> lsDevName = new ArrayList<String>();
        for (String sNo : DevicesDashPageElements.mapDeviceList.keySet()) {
            String sName = DevicesDashPageElements.mapDeviceList.get(sNo).get("Name");
            lsDevName.add(sName);
            MyCommonAPIs.sleep(100, "wait to avoid block");
        }
        if ((lsDevName.size() % 2) == 1) {
            lsDevName.add(null);
        }
        logger.info("All devices: " + lsDevName.toString());
        for (int i = 0; i < (lsDevName.size() / 2); i++) {
            wlp.gotoLagPage();
            wlp.addLag(lagName + i, lsDevName.get(2 * i), lsDevName.get((2 * i) + 1), lagPortId);
            MyCommonAPIs.sleep(100, "wait to avoid block");
        }
    }

    public void test_SNMP_setup_webportal2() {
        snmpp.gotoPage();
        snmpp.setSnmp(true, snmpIp, snmpCommunity, false);
        MyCommonAPIs.sleep(100, "wait to avoid block");
    }

    public void test_Port_setup_webportal3() {
        WiredVLANPage wiredVLANPage = new WiredVLANPage(false);
        WiredGroupPortConfigPage wiredGroupPortConfigPage = new WiredGroupPortConfigPage();
        wiredGroupPortConfigPage.setPortsWithRateLimitStormControl(groupPortId, rateLimit, stormControl);
        MyCommonAPIs.sleep(100, "wait to avoid block");
    }

    public void test_syslog_setup_webportal2() {
        sysp.gotoPage();
        sysp.setSyslog(syslogIp, syslogPort);
        MyCommonAPIs.sleep(100, "wait to avoid block");
    }

    public void test_portMirror_setup_webportal3() {
        boolean addbrcm = false;
        boolean addrltk = false;
        for (String sNo : DevicesDashPageElements.mapDeviceList.keySet()) {
            String sModel = DevicesDashPageElements.mapDeviceList.get(sNo).get("Model");
            if (!addrltk && WebportalParam.isRltkSW(sModel)) {
                addrltk = true;
                lsPortMirrorDevice.add(sNo);
                continue;
            }
            if (!addbrcm && !WebportalParam.isRltkSW(sModel)) {
                addbrcm = true;
                lsPortMirrorDevice.add(sNo);
                continue;
            }
            MyCommonAPIs.sleep(100, "wait to avoid block");
            
        }
        for (String sNo : lsPortMirrorDevice) {
            ddp.gotoPage();
            ddp.enterDevice(sNo);
            DevicesSwitchPortMirrorPage devicesSwitchPortMirrorPage = new DevicesSwitchPortMirrorPage();
            devicesSwitchPortMirrorPage.configPortMirrorPorts(true, portMirrorSrcports, portMirrorDstports);
            MyCommonAPIs.sleep(100, "wait to avoid block");
        }
    }

    public void test_PoESchedule_setup_webportal4() {
        wpsp.gotoPage();
        wpsp.addMidNightPoESchedule(poeName, "Daily", poePortId, poeTimeStart, poeTimeEnd);
        MyCommonAPIs.sleep(100, "wait to avoid block");
    }
    
    public void test_poeSetting_setup_webportal4() {
        boolean addbrcm = false;
        boolean addrltk = false;
        for (String sNo : DevicesDashPageElements.mapDeviceList.keySet()) {
            String sModel = DevicesDashPageElements.mapDeviceList.get(sNo).get("Model");
            if (sModel.contains("P") && !addrltk && WebportalParam.isRltkSW(sModel)) {
                addrltk = true;
                lsPoeSettingDevice.add(sNo);
                continue;
            }
            if (sModel.contains("P") && !addbrcm && !WebportalParam.isRltkSW(sModel)) {
                addbrcm = true;
                lsPoeSettingDevice.add(sNo);
                continue;
            }
            MyCommonAPIs.sleep(100, "wait to avoid block");
        }
        for (String sNo : lsPoeSettingDevice) {
            ddp.gotoPage();
            ddp.enterDevice(sNo);
            DevicesSwitchSummaryPage page1 = new DevicesSwitchSummaryPage();
            page1.portChoice(groupPortId).click();
            DevicesSwitchConnectedNeighboursPortConfiqSettingsPage page2 = new DevicesSwitchConnectedNeighboursPortConfiqSettingsPage();
            
            handle.setExpand(page2.btnPowerManagement, false);
            page2.setPoEStand("802.3af");
            page2.setPriority("Critical");
            page2.setClass("Class2");
            page2.clickSave();
            MyCommonAPIs.sleep(100, "wait to avoid block");
        }
    }
}
