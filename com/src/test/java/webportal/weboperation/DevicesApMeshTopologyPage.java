package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DevicesApMeshTopologyPageElement;

/**
 * @author Netgear
 */

public class DevicesApMeshTopologyPage extends DevicesApMeshTopologyPageElement {
    /**
    *
    */
    Logger logger;
    
    public DevicesApMeshTopologyPage() {
        // TODO Auto-generated constructor stub
        WebCheck.checkHrefIcon(URLParam.hrefSatelliteAP);
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }
    
    public DevicesApMeshTopologyPage(boolean noPage) {
        // TODO Auto-generated constructor stub
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("init...");
    }

    /**
     * 0 - list view, 1 - graph view
     */
    public int getMeshViewMode() {
        if ($(sMeshViewGrahButton).exists())
            return 0;
        return 1;
    }

    /**
     * @param iMode
     *              0 - list view, 1 - graph view
     */
    public void setMeshViewMode(int iMode) {
        if ((iMode == 0) && $(sMeshViewListButton).exists()) {
            $(sMeshViewListButton).click();
        }
        if ((iMode == 1) && $(sMeshViewGrahButton).exists()) {
            $(sMeshViewGrahButton).click();
        }
    }
    
    public List<String> getDevicesInfo() {
        return getTextsTable(sMeshListViewTable);
    }

    public String getHilightAP() {
        if ($(sMeshViewListButton).exists())
            return getAPNameGraph().get(getHilightAPIndexGraph());
        else
            return getAPNameList().get(getHilightAPIndexList());
    }

    public void clickMeshRootAp() {
        if (meshRootAp.exists()) {
            meshRootAp.click();
        }
        MyCommonAPIs.sleepi(5);
    }
    
    public void clickMeshExtenderAp() {
        if (meshExtenderAp.exists()) {
            meshExtenderAp.click();
        }
        MyCommonAPIs.sleepi(5);
    }
    
    public void setAdvanceMeshModeConfiguration(String mode) {
        advanceSettings.click();
        MyCommonAPIs.sleepi(3);
        if (!checkOptionSelected(mode)) {
            switch (mode) {
            case ("Auto"):
                $x(meshModeSelect + "/label[1]").click();
                break;
            case ("Extender Only"):
                $x(meshModeSelect + "/label[2]").click();
                break;
            case ("Root Only"):
                $x(meshModeSelect + "/label[3]").click();
                break;
            case ("Disable Mesh"):
                $x(meshModeSelect + "/label[4]").click();
                break;
            }
            MyCommonAPIs.sleepi(3);
            if (meshModeOk.isDisplayed()) {
                meshModeOk.click();
                MyCommonAPIs.sleepi(3);
            }
            meshSave.click();
            MyCommonAPIs.sleepi(3);
            if (meshConfirm.isDisplayed()) {
                meshConfirm.click();
                waitReady();
            }
            // MyCommonAPIs.sleepi(5 * 60);
        }
    }
    
    public void setRootApSelectionConfiguration(Map<String, String> map) {
        advanceSettings.click();
        MyCommonAPIs.sleepi(3);
        switch (map.get("Root Ap Selection")) {
        case ("Auto"):
            $x(rootApSelection + "/label[1]").click();
            break;
        case ("Manual"):
            $x(rootApSelection + "/label[2]").click();
            break;
        }
        MyCommonAPIs.sleepi(3);
        if (map.get("Root Ap Selection").equals("Manual")) {
            $x(primaryPreferredRootDev).click();
            $x(primaryPreferredRootDev + "/../..//span[contains(text(),'" + map.get("Primary Preferred Root Device") + "')]").click();
            primaryPreferredBackhaul.selectOption(map.get("Primary Preferred Backhaul"));
            MyCommonAPIs.sleepi(3);
            if (map.containsKey("Secondary Preferred Root Device")) {
                $x(secondaryPreferredRoot).click();
                $x(secondaryPreferredRoot + "/../..//span[contains(text(),'" + map.get("Secondary Preferred Root Device") + "')]").click();
                secondaryPreferredBackhaul.selectOption(map.get("Secondary Preferred Backhaul"));
            }
        }
        meshSave.click();
        MyCommonAPIs.sleepi(3);
        if (meshConfirm.isDisplayed()) {
            meshConfirm.click();
            waitReady();
        }
    }
    
    public boolean checkOptionSelected(String text) {
        boolean result = false;
        if ($x("//p[text()='" + WebportalParam.getLocText(text) + "']/../input").isSelected()) {
            result = true;
            logger.info(text + " have selected.");
        }
        return result;
    }
    
    public boolean checkExtenderApChNumAndRootAp(String devName, String option) {
        boolean result = false;
        if (extenderApChNum.exists()) {
            if (option.equals("2.4") && getText(extenderApChNum).contains("Ch : 6") && $x("//h6[text()='" + devName + "']").exists()) {
                result = true;
                logger.info("Root ap selection is correct.");
            } else if (option.equals("5high") && getText(extenderApChNum).contains("Ch : 161") && $x("//h6[text()='" + devName + "']").exists()) {
                result = true;
                logger.info("Root ap selection is correct.");
            } else if (option.equals("5low") && getText(extenderApChNum).contains("Ch : 40") && $x("//h6[text()='" + devName + "']").exists()) {
                result = true;
                logger.info("Root ap selection is correct.");
            }
        }
        return result;
        
    }
    
    public boolean checkMeshStatusAndAdvanceSettings(String option) {
        boolean result = false;
        if (meshRootAp.exists() && meshExtenderAp.exists() && option.equals("")) {
            advanceSettings.click();
            MyCommonAPIs.sleepi(3);
            if ($x(meshModeSelect).exists() && $x(backhaulSettingsSelect).exists()) {
                result = true;
                logger.info("Mesh mode and backhaul settings displayed.");
            }
        } else if (meshRootAp.exists() && meshExtenderAp.exists() && option.equals("help")) {
            advanceSettings.click();
            MyCommonAPIs.sleepi(3);
            meshModeHelp.click();
            MyCommonAPIs.sleepi(3);
            if (meshModeHelpWindow.exists()) {
                result = true;
                logger.info("Mesh mode help window displayed.");
                closeMeshModeHelpWindow.click();
            }
        } else if (meshRootAp.exists() && meshExtenderAp.exists() && option.equals("extender")) {
            advanceSettings.click();
            MyCommonAPIs.sleepi(3);
            if ($x(meshModeSelect).exists() && $x(backhaulSettingsSelect).exists() && $x(rootApSelection).exists()) {
                result = true;
                logger.info("Mesh mode, backhaul settings and root ap selection displayed.");
            }
        } else if (!meshRootAp.exists() && !meshExtenderAp.exists() && option.equals("disable")) {
            result = true;
            logger.info("Mesh mode disabled success.");
        }
        return result;
    }
    
    public String returnRootApSNo() {
        String serialNumber = "";
        clickMeshRootAp();
        serialNumber = getText($("#divSeNoSumm"));
        return serialNumber;
    }
}
