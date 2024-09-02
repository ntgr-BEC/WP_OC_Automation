package webportal.SanityTestCase.ProdPostSanity;

import org.testng.annotations.Test;

import io.qameta.allure.Step;
import testbase.TestCaseBase;
import util.MyCommonAPIs;
import util.SwitchCLIUtils;
import webportal.param.WebportalParam;
import webportal.webelements.DevicesDashPageElements;
import webportal.weboperation.WebportalLoginPage;

public class Delete extends TestCaseBase implements Config {
    String checkPoint = "";
    String toCheck    = "";
    
    @Test(alwaysRun = true, groups = "p1")
    public void test() throws Exception {
        WebportalParam.enableBatch = true;
        WebportalLoginPage webportalLoginPage = new WebportalLoginPage();
        webportalLoginPage.defaultLogin();
        handle.gotoLoction();
        ddp.gotoPage();
        runTest(this);
    }

    @Step("delete vlan")
    public void step1() {
        wvp.gotoPage();
        wvp.deleteVlan(dataVlanID, true);
        MyCommonAPIs.sleep(10, "wait to avoid block");
        wvp.deleteVlan(aclVlanID, true);
        MyCommonAPIs.sleep(10, "wait to avoid block");
        wvp.deleteVlan("4088", true);
        MyCommonAPIs.sleep(10, "wait to avoid block");
    }
    
    @Step("delete poe schedule")
    public void step2() {
        wpsp.gotoPage();
        wpsp.deleteAll();
        MyCommonAPIs.sleep(10, "wait to avoid block");
    }

    @Step("delete lag")
    public void step3() {
        wlp.gotoLagPage();
        wlp.deleteLag();
        MyCommonAPIs.sleep(10, "wait to avoid block");
    }
    
    @Step("disable port mirror")
    public void step4() {
    }
    
    @Step("disable syslog")
    public void step5() {
    }
    
    @Step("disable samp")
    public void step6() {
        snmpp.gotoPage();
        snmpp.clearSnmp();
        MyCommonAPIs.sleep(10, "wait to avoid block");
    }
    
    @Step("disable radius")
    public void step7() {
        rcp.disableRadius(true);
        MyCommonAPIs.sleep(10, "wait to avoid block");
    }
    
    @Step("check vlan")
    public void step20() {
        MyCommonAPIs.sleep(120, "wait all configuration done");
        for (String sNo : DevicesDashPageElements.mapDeviceList.keySet()) {
            String sIp = DevicesDashPageElements.mapDeviceList.get(sNo).get("Ip");
            String sModel = DevicesDashPageElements.mapDeviceList.get(sNo).get("Model");

            SwitchCLIUtils.setSwitchInfo(sIp, sModel);
            try {
                toCheck = MyCommonAPIs.getCmdOutput("show vlan " + dataVlanID, false).toLowerCase();
                checkPoint = "check vlan is deleted: " + dataVlanID;
                WebportalParam.printResult(!toCheck.contains("vlan" + dataVlanID) ? true : false, sIp, sModel, checkPoint);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            MyCommonAPIs.sleep(10, "wait to avoid block");
        }
    }
    
    @Step("check poe")
    public void step21() {
        for (String sNo : DevicesDashPageElements.mapDeviceList.keySet()) {
            String sIp = DevicesDashPageElements.mapDeviceList.get(sNo).get("Ip");
            String sModel = DevicesDashPageElements.mapDeviceList.get(sNo).get("Model");
            
            SwitchCLIUtils.setSwitchInfo(sIp, sModel);
            try {
                checkPoint = "check poe schedule is deleted: " + poeName;
                toCheck = SwitchCLIUtils.getPoETimeRange(poeName);
                WebportalParam.printResult(toCheck.contains("does not") ? true : false, sIp, sModel, checkPoint);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            MyCommonAPIs.sleep(10, "wait to avoid block");
        }
    }
}
