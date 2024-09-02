/**
 *
 */
package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DevicesSwitchStatisticsPageElement;

/**
 * @author zheli
 */
public class DevicesSwitchStatisticsPage extends DevicesSwitchStatisticsPageElement {
    final static Logger logger = Logger.getLogger("DevicesSwitchStatisticsPage");
    List<String>        lsRet  = new ArrayList<String>();
    
    /**
     *
     */
    public DevicesSwitchStatisticsPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefDevicesSwitchStatistics);
        logger.info("init...");
    }
    
    public void clearCounters() {
        clearCountersButton.click();
        waitReady();
    }
    
    public String getTxData() {
        return MyCommonAPIs.getText(ptxDataSwitchStatics);
    }
    
    public String getRxData() {
        return MyCommonAPIs.getText(pRxDataSwitchStatics);
    }

    public List<String> getIPAddr() {
        if ($(sTable).exists())
            return getTextsTable(sTable, 3);
        else
            return lsRet;
    }
    
    public boolean checkStatistics() {
        String sEnv = getCmdOutput("show env", false);
        boolean result = true;
        if (!WebportalParam.isRltkSW1 && !sEnv.contains("No temperature sensors")) {
            if (ptempSwitchStatics.getText().contains("°C")) {
            } else {
                result = false;
            }
        }
        
        if (pcpu_usageSwitchStatics.getText().contains("%")) {
        } else {
            result = false;
        }
        if (ptxDataSwitchStatics.getText().contains("MB") || ptxDataSwitchStatics.getText().contains("GB")
                || ptxDataSwitchStatics.getText().contains("KB")) {
        } else {
            result = false;
        }
        
        if (pRxDataSwitchStatics.getText().contains("MB") || pRxDataSwitchStatics.getText().contains("GB")
                || pRxDataSwitchStatics.getText().contains("KB")) {
        } else {
            result = false;
        }
        
        if (!WebportalParam.isRltkSW1 && !sEnv.contains("No fans detected")) {
            if (pFanStateSwitchStatics.getText().equals("Active")|| pFanStateSwitchStatics.getText().equals("Operational")) {
            } else {
                result = false;
            }
        }
        return result;
    }
}
