package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

import java.awt.Desktop.Action;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.interactions.Actions;

import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.SummaryElement;

public class SummaryPage extends SummaryElement {
    Logger logger = Logger.getLogger("SummaryPage");
    
    public SummaryPage() {
        logger.info("init...");
    }
    
    public void gotoPage(String loc) {
        refresh();
        WebCheck.checkUrl(URLParam.hrefSummary + loc);
        waitReady();
        MyCommonAPIs.sleepi(5);
    }
    
    // Added by Vivek
    public void openCustomizeSettings() {
        MyCommonAPIs.sleepi(5);
        customizeMenu.click();
        MyCommonAPIs.sleepi(2); 
    }
    
    // Added by Vivek
    public void enableVerticalMenu() {
        MyCommonAPIs.sleepi(2);
        verticalToggle.click();
        MyCommonAPIs.sleepi(2); 
        closeCustomizeMenu.click();
        MyCommonAPIs.sleepi(2); 
    }
    
    
    // Added by Vivek
    public void OpenleftCustomizeMenu() {
        MyCommonAPIs.sleepi(2);
        customizeMen.click();
        MyCommonAPIs.sleepi(2); 
    }
    
    // Added by Vivek
    public boolean verifyAllMenuItemsArePresent() {
        boolean result = false;
        MyCommonAPIs.sleepi(5);
        for (SelenideElement elem : leftMenuList) {
            if (elem.text().contains("Summary")||elem.text().contains("Topology")||elem.text().contains("Router/VPN")||elem.text().contains("Mobile")||elem.text().contains("Wireless")||elem.text().contains("Wired")||elem.text().contains("Firmware")||elem.text().contains("Devices")||elem.text().contains("Clients")||elem.text().contains("Troubleshoot")||elem.text().contains("Settings")); 
            {
                result=true;
            }      
    }
        return result;
    
        }
    
    
    // Added by Vivek
    public void closeTheMenuAndDisableTheVerticalMenuSwitch() {
        MyCommonAPIs.sleepi(5);
        openCustomizeSettings();
        enableVerticalMenu();
        System.out.println("Vertical Menu Togggle button is disabled");
    } 
    
    
    // Added by Vivek
    public boolean verifyRouter_VpnHaveCorrectSubMenu() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        routerVpnArrow.hover();
        MyCommonAPIs.sleepi(2);
        if (routerVpnSubMenusmry.isDisplayed() & routerVpnSubMenusting.isDisplayed());
        {
            result=true;
    }
        return result;
    }
    public boolean verifyNoOfClientsinDeviceSummaryPage(String loc) {
        boolean result = false;
        gotoPage(loc);
        MyCommonAPIs.sleepi(5);
        noOfconnectedClients.hover();
        waitElement(noOfconnectedClients);
               if (noOfconnectedClients.exists() && connectedClientsText.exists() ) {
                       result = true;
                   }
        return result; 
    } 
    
    // Added by Vivek
    public boolean verifyMobileHaveCorrectSubMenu() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        MobileArrow.hover();
        MyCommonAPIs.sleepi(2);
        if (MobileSubMenusmry.isDisplayed() & MobileSubMenusting.isDisplayed());
        {
            result=true;
    }
        return result;
}
    
    // Added by Vivek
    public boolean verifyWirelessHaveCorrectSubMenu() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        WirelessArrow.hover();
        MyCommonAPIs.sleepi(2);
        if (wirelessSubMenusmry.isDisplayed() & wirelessSubMenusting.isDisplayed());
        {
            result=true;
    }
        return result;
}
    
    // Added by Vivek
    public boolean verifyWiredHaveCorrectSubMenu() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        WiredArrow.hover();
        MyCommonAPIs.sleepi(2);
        if (wiredSubMenusmry.isDisplayed() & wiredSubMenusting.isDisplayed());
        {
            result=true;
    }
        return result;
}
   
    
    // Added by Vivek
    public boolean verifyUsershouldBeAbleToRedirectToTheRespectiveScreen() {
        boolean result = false;
        MyCommonAPIs.sleepi(2);
        WirelessArrow.hover();
        MyCommonAPIs.sleepi(2);
        wirelessSubMenusmry.click();
        MyCommonAPIs.sleepi(5);
        refresh();
        MyCommonAPIs.sleepi(10);
        if (wirelesssmrytxt.exists());
        {
            result=true;
    }
        return result;
    }
    
    //AddedByPratikMoveDevice
    public boolean verifynodevicesarepresentonSummaryPage() {
        boolean result = false;
        MyCommonAPIs.sleepi(10);
        waitElement(nodevicesAvailable);
        if (nodevicesAvailable.exists() && nodevicesAvailable1.exists()) {
            result = true;
            logger.info("Devices are not there on summary page");
        }
        return result;
    } 
 // Added by Anusha for Horizontal and Vertical Menu 
    
    public boolean verifyVerticalMenuToggleSwitch() {
        MyCommonAPIs.sleepi(1);
        boolean result = false;
        System.out.println(VerticalMenuToggle.isSelected());
        if (VerticalMenuToggle.isSelected());
        {
            result=true;
            System.out.println("Vertical menu Toggle button is enabled");
    }
        return result;
}
    
    public boolean verifyArrrowOnLeft() {
        MyCommonAPIs.sleepi(1);
        boolean result = false;
        if (ArrowOnLeft.isDisplayed());
        {
            result=true;
            System.out.println("Arrow On Left is displayed");
    }
        return result;
}   
}

