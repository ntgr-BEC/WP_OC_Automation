package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.LocationRoutingElement;

public class LocationRoutingPage extends LocationRoutingElement {
    Logger logger     = Logger.getLogger("LocationRoutingPage");
    String defGateway = "123.123.123.123";
    int    editIcon   = 0;

    public LocationRoutingPage() {
        logger.info("init...");
    }

    public void gotoPage() {
        WebCheck.checkUrl(URLParam.hrefRouting);
        if (!pageSource().contains(defGateway)) {
            setGateway(defGateway);
        }
        // if (!WebportalParam.skipIssueCheck)
        // throw new RuntimeException("PRJCBUGEN-26644");
    }

    /**
     * @param index
     *              0 - routing vlan, 1 - gateway, 2 - static route
     */
    public void openSection(int index) {
        if ((2 == index) || !$(lsSectionItem).isDisplayed()) {
            refresh();
        }
        setExpand(lsSectionItem, true, index);
    }

    public void openVlan(String vlanId) {
        openSection(0);
        $(vlanlistTable).hover();// move mouse before edit
        editLine(vlanlistTable, 2, vlanId, editIcon);
    }

    public void gotoStaticRoute(boolean secSw) {
        openSection(2);
        String devName = WebportalParam.sw1deveiceName;
        if (secSw) {
            devName = WebportalParam.sw2deveiceName;
        }

        for (SelenideElement se : $$(rbSwitch)) {
            if (getText(se).equals(devName)) {
                se.click();
                break;
            }
        }

        clickButton(3);
    }

    public void deleteVlanRoute(String vlanId) {
        openSection(0);
        try {
            editLine(vlanlistTable, 2, vlanId, editIcon);
            if (btnDelete.exists()) {
                clickButton(2);
            } else {
                clickBoxFirstButton();
            }
        } catch (Throwable e) {
            takess();
            e.printStackTrace();
        }
    }

    public void deleteAllVlanRoute() {
        takess("delete All data");
        openSection(0);
        int iLine = $$(vlanlistTable + " tr").size();
        for (int i = 1; i <= iLine; i++) {
            MyCommonAPIs.sleepi(6);
            editLine(vlanlistTable, i, editIcon);
            if (btnDelete.exists()) {
                clickButton(2);
            } else {
                btnClose.click();
            }
        }
    }

    public void deleteAllStaticRoute(boolean secSw) {
        takess("delete All data");
        gotoStaticRoute(secSw);
        try {
            int ii = $$(routeTableGw).size();
            for (int i = 0; i < ii; i++) {
                editLine(routeTable, routeTableGw, "", routeTableEdit);
                clickButton(2);
            }
        } catch (Throwable e) {
            takess();
            e.printStackTrace();
        }
    }

    public void deleteStaticRoute(boolean secSw, String gw) {
        gotoStaticRoute(secSw);
        editLine(routeTable, routeTableGw, gw, routeTableEdit);
        clickButton(2);
    }

    /**
     * @param  model
     *               true if sw is GS724 or GS716
     * @return
     */
    public boolean isRoutingDisabled(String model) {
        if (model.contains("GS724TP") || model.contains("GS716TP"))
            return true;
        return false;
    }
    
    public boolean isRoutingDisabled() {
        String model = WebportalParam.sw1Model;
        if (model.contains("GS724TP") || model.contains("GS716TP"))
            return true;
        return false;
    }

    public void addIpToVlan(String vlanId, String mask, String ip1, String ip2) {
        if (!seMask.isDisplayed()) {
            openSection(0);
            openVlan(vlanId);
        }
        setText(seMask, mask);
        if (!isRoutingDisabled(WebportalParam.sw1Model)) {
            setIpAddress(WebportalParam.sw1deveiceName, ip1);
        }
        if (WebportalParam.enaTwoSwitchMode && !isRoutingDisabled(WebportalParam.sw2Model)) {
            setIpAddress(WebportalParam.sw2deveiceName, ip2);
        }
        setOtherIpAddress(ip2);
        clickButton(0);
    }

    public void addIpToVlan(String vlanId, String mask, String ipStart) {
        if (!seMask.isDisplayed()) {
            openSection(0);
            openVlan(vlanId);
        }
        setText(seMask, mask);
        setOtherIpAddress(ipStart);
        clickButton(0);
    }

    public void setGateway(String gw) {
//         not support
//         return;
         openSection(1);
         if (getValue(sGateWay).equals(gw))
         return;
         setText(sGateWay, gw);
         clickButton(0);
    }

    /**
     * @param secSw
     *                 true to select 2nd device
     * @param bDefault
     *                 true for default route
     * @param ip
     * @param mask
     * @param gw
     */
    public void addStaticRoute(boolean secSw, boolean bDefault, String ip, String mask, String gw) {
        // if (bDefault && (WebportalParam.isRltkSW1 || WebportalParam.isRltkSW2))
        // return;

        if (!btnAdd.exists()) {
            gotoStaticRoute(secSw);
        }
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

    public List<String> getStaticRouteGw(boolean secSw) {
        List<String> lsRet = new ArrayList<String>();
        gotoStaticRoute(secSw);
        if ($(routeTableGw).exists()) {
            lsRet = getTexts(routeTableGw);
        }
        return lsRet;
    }
}
