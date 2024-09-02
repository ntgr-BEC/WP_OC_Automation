package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.codeborne.selenide.ElementsCollection;

import webportal.param.URLParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DevicesSwitchStaticRoutingElement;

public class DevicesSwitchStaticRoutingPage extends DevicesSwitchStaticRoutingElement {
    Logger logger = Logger.getLogger("DevicesSwitchStaticRoutingPage");

    public DevicesSwitchStaticRoutingPage() {
        logger.info("init...");
    }

    public void gotoPage() {
        WebCheck.checkUrl(URLParam.hrefStaticRouting);
    }

    public void deleteAllStaticRoute() {
        takess("delete All data");
        try {
            int ii = $$(routeTableGw).size();
            for (int i = 0; i < ii; i++) {
                editLine(routeTable, routeTableGw, "", routeTableEdit);
                clickButton(2);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            takess();
        }
    }

    public void deleteStaticRoute(boolean secSw, String gw) {
        editLine(routeTable, routeTableGw, gw, routeTableEdit);
        clickButton(2);
    }

    /**
     * @param bDefault
     *            true for default route
     * @param ip
     * @param mask
     * @param gw
     */
    public void addStaticRoute(boolean bDefault, String ip, String mask, String gw) {
        // if (bDefault && (WebportalParam.isRltkSW1 || WebportalParam.isRltkSW2))
        // return;

        btnAdd.click();
        setSelected(cbDefault, bDefault, true);
        if (bDefault && btnYes.exists()) {
            btnYes.click();
        }

        ElementsCollection ecs = $$(txtField);
        if (!bDefault) {
            setText(ecs.get(0), ip);
            setText(ecs.get(1), mask);
            setText(ecs.get(2), gw);
        } else {
            setText(ecs.get(0), gw);
        }
        clickButton(0);
    }

    public List<String> getStaticRouteGw() {
        List<String> lsRet = new ArrayList<String>();
        if ($(routeTableGw).exists()) {
            lsRet = getTexts(routeTableGw);
        }
        return lsRet;
    }
}
