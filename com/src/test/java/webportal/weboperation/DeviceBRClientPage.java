package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DeviceBRClientElement;

public class DeviceBRClientPage extends DeviceBRClientElement {
    Logger logger = Logger.getLogger("DeviceBRClientPage");

    public DeviceBRClientPage() {
        logger.info("init...");
    }

    public void gotoPage() {
        WebCheck.checkUrl(URLParam.hrefBRattachedDevices);
    }

    public List<String> getClients() {
        List<String> clis = new ArrayList<String>();
        if ($(sClientName).exists())
            return getTexts(sClientName);
        return clis;
    }

    public boolean isAllClientValid() {
        if ($$(sExpand).size() == 0)
            return false;
        for (int i = 0; i < $$(sExpand).size(); i++) {
            setExpand(sExpand, true, i);
            if (getText($$(sClientName).get(i)).length() < 2)
                return false;
            if (getText($$(sClientMac).get(i)).length() < 12)
                return false;
            if (getText($$(sClientIp).get(i)).length() < 7)
                return false;
        }
        return true;
    }
}
