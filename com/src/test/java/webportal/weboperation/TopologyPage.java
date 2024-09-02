package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.TopologyElement;

public class TopologyPage extends TopologyElement {
    Logger logger = Logger.getLogger("TopologyPage");
    
    public TopologyPage() {
        logger.info("init...");
    }
    
    public void gotoPage() {
        refresh();
        WebCheck.checkUrl(URLParam.hredTopology);
        waitReady();
        MyCommonAPIs.sleepi(5);
    }
    //AddedByPratikforMoveDevice
    public boolean verifyNoDevicesDataonTopologyPage() {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        if (noDeviceRelatedData.exists() || generatingTopology.exists()) {
            result = true;
            logger.info("Devices data is not there on Topology page");
        }
        return result;
    }
    
}
