package webportal.SanityTestCase.ProdPostSanity;

import org.testng.annotations.Test;

import testbase.TestCaseBase;
import util.MyCommonAPIs;
import util.SwitchCLIUtils;
import webportal.param.WebportalParam;
import webportal.webelements.DevicesDashPageElements;
import webportal.weboperation.WebportalLoginPage;

public class Check extends TestCaseBase implements Config {
    String checkPoint = "";
    String toCheck    = "";

    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        WebportalParam.enableBatch = true;
        runStepTest(this);
        
    }
    
    public void test_createVoiceVlan_check() {
        for (String sNo : DevicesDashPageElements.mapDeviceList.keySet()) {
            String sIp = DevicesDashPageElements.mapDeviceList.get(sNo).get("Ip");
            String sModel = DevicesDashPageElements.mapDeviceList.get(sNo).get("Model");
            
            SwitchCLIUtils.setSwitchInfo(sIp, sModel);
            try {
                toCheck = SwitchCLIUtils.getVoiceInfo(1, 0);
                checkPoint = "check voice class/cos value must be 7";
                WebportalParam.printResult(toCheck.toLowerCase().contains(" 7") ? true : false, sIp, sModel, checkPoint);
                
                checkPoint = "check port 1 is tagged in voice vlan";
                WebportalParam.printResult(SwitchCLIUtils.isTagPort(dataVlanPortId, "4088") ? true : false, sIp, sModel, checkPoint);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            MyCommonAPIs.sleep(10, "wait to avoid block");
        }
    }

    // public void test_createVideoVlan_check() {
    // for (String sNo : ddp.mapDeviceList.keySet()) {
    // String sModel = ddp.mapDeviceList.get(sNo).get("Model");
    // SwitchTelnet switchTelnet = new SwitchTelnet(WebportalParam.sw1IPaddress, false);
    // String result2 = switchTelnet.getCLICommand("show vlan 4089");
    // System.out.println(result2);
    // if (result2.contains("4089")) {
    // micResult = true;
    // } else {
    // micResult = false;
    // assertTrue(micResult, "----Check Point 1 Fail:show vlan 4089, cli is:" + result2);
    // }
    // }
    // }
    
    public void test_createDataVlan_check() {
        for (String sNo : DevicesDashPageElements.mapDeviceList.keySet()) {
            String sIp = DevicesDashPageElements.mapDeviceList.get(sNo).get("Ip");
            String sModel = DevicesDashPageElements.mapDeviceList.get(sNo).get("Model");
            
            SwitchCLIUtils.setSwitchInfo(sIp, sModel);
            try {
                checkPoint = "check port 1 is tagged in data vlan: " + dataVlanID;
                WebportalParam.printResult(SwitchCLIUtils.isTagPort(dataVlanPortId, dataVlanID) ? true : false, sIp, sModel, checkPoint);

                checkPoint = "check port 2 is untagged in data vlan: " + dataVlanID;
                WebportalParam.printResult(SwitchCLIUtils.isTagPort("g2", dataVlanID) ? false : true, sIp, sModel, checkPoint);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            MyCommonAPIs.sleep(10, "wait to avoid block");

        }
    }

    public void test_Radius_check() {
        for (String sNo : DevicesDashPageElements.mapDeviceList.keySet()) {
            String sIp = DevicesDashPageElements.mapDeviceList.get(sNo).get("Ip");
            String sModel = DevicesDashPageElements.mapDeviceList.get(sNo).get("Model");

            SwitchCLIUtils.setSwitchInfo(sIp, sModel);
            try {
                toCheck = SwitchCLIUtils.getRadiusInfo("g1");
                checkPoint = "check Radius server is configured";
                WebportalParam.printResult(SwitchCLIUtils.RadiusClass.isServerConfiged ? true : false, sIp, sModel, checkPoint);
                checkPoint = "check port 1 is force-unauth";
                WebportalParam.printResult(SwitchCLIUtils.RadiusClass.portStatus == 2 ? true : false, sIp, sModel, checkPoint);
                
                toCheck = SwitchCLIUtils.getRadiusInfo("g2");
                checkPoint = "check port 2 is auto-auth";
                WebportalParam.printResult(SwitchCLIUtils.RadiusClass.portStatus == 0 ? true : false, sIp, sModel, checkPoint);
                
                String upLink = "g" + WebportalParam.getSwitchUplinkPort(sModel);
                toCheck = SwitchCLIUtils.getRadiusInfo(upLink);
                checkPoint = "check uplink is force-auth: " + upLink;
                WebportalParam.printResult(SwitchCLIUtils.RadiusClass.portStatus == 1 ? true : false, sIp, sModel, checkPoint);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            MyCommonAPIs.sleep(10, "wait to avoid block");
        }
    }
    
    public void test_ACL_check() {
        for (String sNo : DevicesDashPageElements.mapDeviceList.keySet()) {
            String sIp = DevicesDashPageElements.mapDeviceList.get(sNo).get("Ip");
            String sModel = DevicesDashPageElements.mapDeviceList.get(sNo).get("Model");
            
            SwitchCLIUtils.setSwitchInfo(sIp, sModel);
            try {
                toCheck = SwitchCLIUtils.getIpMACACL(true, aclVlanID);
                checkPoint = "check ip acl is deny";
                WebportalParam.printResult(!SwitchCLIUtils.ACLClass.ispermitACL ? true : false, sIp, sModel, checkPoint);
                checkPoint = "check ip acl is created for: " + ipACL;
                WebportalParam.printResult(SwitchCLIUtils.ACLClass.aclResult.contains(ipACL) ? true : false, sIp, sModel, checkPoint);
                
                toCheck = SwitchCLIUtils.getIpMACACL(false, aclVlanID);
                checkPoint = "check mac acl is allow";
                WebportalParam.printResult(SwitchCLIUtils.ACLClass.ispermitACL ? true : false, sIp, sModel, checkPoint);
                checkPoint = "check mac acl is created for: " + macACL;
                WebportalParam.printResult(SwitchCLIUtils.ACLClass.aclResult.contains(macACL) ? true : false, sIp, sModel, checkPoint);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            MyCommonAPIs.sleep(10, "wait to avoid block");
        }
    }

    public void test_Routing_check() {
        String ipCheck = ipRouting.substring(0, ipRouting.lastIndexOf("."));
        for (String sNo : DevicesDashPageElements.mapDeviceList.keySet()) {
            String sIp = DevicesDashPageElements.mapDeviceList.get(sNo).get("Ip");
            String sModel = DevicesDashPageElements.mapDeviceList.get(sNo).get("Model");
            
            if (rtp.isRoutingDisabled(sModel)) {
                continue;
            }
            
            checkPoint = "check vlan route is created on vlan: " + aclVlanID;
            SwitchCLIUtils.setSwitchInfo(sIp, sModel);
            try {
                toCheck = SwitchCLIUtils.getVlanRoute(aclVlanID);
                WebportalParam.printResult(toCheck.contains(ipCheck) ? true : false, sIp, sModel, checkPoint);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            MyCommonAPIs.sleep(10, "wait to avoid block");
        }
    }

    public void test_LAG_check() {
        for (String sNo : DevicesDashPageElements.mapDeviceList.keySet()) {
            String sIp = DevicesDashPageElements.mapDeviceList.get(sNo).get("Ip");
            String sModel = DevicesDashPageElements.mapDeviceList.get(sNo).get("Model");
            
            checkPoint = "check port is lag port:" + lagPortId;
            SwitchCLIUtils.setSwitchInfo(sIp, sModel);
            try {
                WebportalParam.printResult(SwitchCLIUtils.isLagPort(WebportalParam.getSwitchPort(sModel, lagPortId)) ? true : false, sIp, sModel,
                        checkPoint);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            MyCommonAPIs.sleep(10, "wait to avoid block");
        }
    }

    public void test_SNMP_check() {
        for (String sNo : DevicesDashPageElements.mapDeviceList.keySet()) {
            String sIp = DevicesDashPageElements.mapDeviceList.get(sNo).get("Ip");
            String sModel = DevicesDashPageElements.mapDeviceList.get(sNo).get("Model");
            
            if (snmpp.isSnmpDisabled(sModel)) {
                continue;
            }

            checkPoint = "check snmp is created";
            SwitchCLIUtils.setSwitchInfo(sIp, sModel);
            try {
                toCheck = SwitchCLIUtils.getSNMPInfo();
                WebportalParam.printResult(SwitchCLIUtils.SNMPClass.snmpResult.contains(snmpIp) ? true : false, sIp, sModel, checkPoint);
                checkPoint = "check snmp trap is enabled";
                WebportalParam.printResult(SwitchCLIUtils.SNMPClass.isTrapEnable ? true : false, sIp, sModel, checkPoint);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            MyCommonAPIs.sleep(10, "wait to avoid block");
        }
    }

    public void test_Port_check() {
        for (String sNo : DevicesDashPageElements.mapDeviceList.keySet()) {
            String sIp = DevicesDashPageElements.mapDeviceList.get(sNo).get("Ip");
            String sModel = DevicesDashPageElements.mapDeviceList.get(sNo).get("Model");
            
            SwitchCLIUtils.setSwitchInfo(sIp, sModel);
            checkPoint = "check port engress rate is: " + rateLimit;
            toCheck = SwitchCLIUtils.getPortInfo(groupPortId);
            try {
                WebportalParam.printResult(SwitchCLIUtils.PortClass.sPortEgressRate.contains(rateLimit) ? true : false, sIp, sModel, checkPoint);
                checkPoint = "check port storm rate is: " + stormControl;
                WebportalParam.printResult(SwitchCLIUtils.PortClass.sPortStormControlRate.contains(stormControl) ? true : false, sIp, sModel,
                        checkPoint);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            MyCommonAPIs.sleep(10, "wait to avoid block");
        }
    }

    public void test_PoESchedule_check() {
        for (String sNo : DevicesDashPageElements.mapDeviceList.keySet()) {
            String sIp = DevicesDashPageElements.mapDeviceList.get(sNo).get("Ip");
            String sModel = DevicesDashPageElements.mapDeviceList.get(sNo).get("Model");
            if (!sModel.contains("P")) {
                continue;
            }
            
            SwitchCLIUtils.setSwitchInfo(sIp, sModel);
            try {
                checkPoint = "check port poe with name: " + poeName;
                toCheck = SwitchCLIUtils.getPoEInfo(poePortId);
                WebportalParam.printResult(toCheck.contains(poeName) ? true : false, sIp, sModel, checkPoint);
                checkPoint = "check poe time range from: " + poeTimeStart + " to: " + poeTimeEnd;
                toCheck = SwitchCLIUtils.getPoETimeRange(poeName);
                WebportalParam.printResult(toCheck.contains(poeTimeStart) && toCheck.contains(poeTimeEnd) ? true : false, sIp, sModel, checkPoint);
                
                String toMatch = "";
                if (WebportalParam.isRltkSW1) {
                    toMatch = "infinite";
                } else {
                    toMatch = "Frequency: 1";
                }
                checkPoint = "check poe time schedule must be daily";
                WebportalParam.printResult(toCheck.contains(toMatch) ? true : false, sIp, sModel, checkPoint);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            MyCommonAPIs.sleep(10, "wait to avoid block");
        }
    }

    public void test_syslog_check() {
        for (String sNo : DevicesDashPageElements.mapDeviceList.keySet()) {
            String sIp = DevicesDashPageElements.mapDeviceList.get(sNo).get("Ip");
            String sModel = DevicesDashPageElements.mapDeviceList.get(sNo).get("Model");
            
            SwitchCLIUtils.setSwitchInfo(sIp, sModel);
            try {
                toCheck = SwitchCLIUtils.getLogging();
                checkPoint = "check syslog with ip: " + syslogIp + " port: " + syslogPort;
                WebportalParam.printResult(toCheck.contains(syslogIp) && toCheck.contains("" + syslogPort) ? true : false, sIp, sModel, checkPoint);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            MyCommonAPIs.sleep(10, "wait to avoid block");
        }
    }

    public void test_portMirror_check() {
        String srcPort = "source interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, portMirrorSrcports[0]);
        String dstPort = "destination interface " + WebportalParam.getSwitchPort(WebportalParam.sw1Model, portMirrorDstports[0]);
        for (String sNo : lsPortMirrorDevice) {
            String sIp = DevicesDashPageElements.mapDeviceList.get(sNo).get("Ip");
            String sModel = DevicesDashPageElements.mapDeviceList.get(sNo).get("Model");

            SwitchCLIUtils.setSwitchInfo(sIp, sModel);
            try {
                toCheck = SwitchCLIUtils.getPortMirror();
                checkPoint = "check port mirror with: s" + portMirrorSrcports[0] + "/d" + portMirrorDstports[0];
                WebportalParam.printResult(toCheck.contains(srcPort) && toCheck.contains(dstPort) ? true : false, sIp, sModel, checkPoint);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            MyCommonAPIs.sleep(10, "wait to avoid block");
        }
    }

    public void test_poeSetting_check() {
        for (String sNo : lsPoeSettingDevice) {
            String sIp = DevicesDashPageElements.mapDeviceList.get(sNo).get("Ip");
            String sModel = DevicesDashPageElements.mapDeviceList.get(sNo).get("Model");
            if (!sModel.contains("P")) {
                continue;
            }
            String toMatch = "";
            SwitchCLIUtils.setSwitchInfo(sIp, sModel);
            try {
                if (WebportalParam.isRltkSW1) {
                    toMatch = "802.3af";
                } else {
                    toMatch = "no poe high-power";
                }
                
                toCheck = SwitchCLIUtils.getPoEInfo("g" + groupPortId);
                checkPoint = "check port poe setting to 802.3af: g" + groupPortId;
                WebportalParam.printResult(toCheck.contains(toMatch) ? true : false, sIp, sModel, checkPoint);
                
                if (WebportalParam.isRltkSW1) {
                    toMatch = "critical";
                } else {
                    toMatch = "poe priority Crit";
                }
                checkPoint = "check port poe setting to Critical: g" + groupPortId;
                WebportalParam.printResult(toCheck.contains(toMatch) ? true : false, sIp, sModel, checkPoint);
                
                toMatch = "7000";
                checkPoint = "check port poe setting to class2-7000: g" + groupPortId;
                WebportalParam.printResult(toCheck.contains(toMatch) ? true : false, sIp, sModel, checkPoint);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            MyCommonAPIs.sleep(10, "wait to avoid block");
        }
    }
}
