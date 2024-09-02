/**
 * 
 */
package webportal.weboperation;

import java.util.Map;
import java.util.logging.Logger;

import com.codeborne.selenide.Selenide;

import webportal.publicstep.WebCheck;
import webportal.webelements.AccountPageElementNas;
import webportal.webelements.ButtonElements;

/**
 * @author zheli
 *
 */
public class AccountPageNas extends AccountPageElementNas {
    Logger logger;

    public AccountPageNas() {
        WebCheck.checkUrl("#/dashboard/account");
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
    }

    public void addNetwork(Map<String, String> map) {
        addNetWorkButton.click();
        addNetLocationName.sendKeys(map.get("Location Name"));
        Selenide.sleep(1000);
        addNetPassword.sendKeys(map.get("Device Admin Password"));
        addNetStreet.sendKeys(map.get("Street"));
        addNetCity.sendKeys(map.get("City"));
        addNetState.sendKeys(map.get("State"));
        addNetZipcode.sendKeys(map.get("Zip Code"));
        netCountryList.selectOption(map.get("Country"));
        timeZone.selectOption(map.get("Time Zone"));
        ButtonElements.SAVEBUTTON.click();
    }

    public NetworkEditNetworkPage enterEditNetworkPage() {
        firstLocationMoreIcon.click();
        firstLocationEditIcon.click();
        return new NetworkEditNetworkPage();
    }

    public NetworkEditNetworkPage enterEditNetworkPage2() {
        secondLocationMoreIcon.click();
        secondLocationEditIcon.click();
        return new NetworkEditNetworkPage();
    }

    public NetworkEditNetworkPage enterDeleteNetworkPage() {
        firstLocationMoreIcon.click();
        firstLocationDeleteIcon.click();
        confirmLocationDelete.click();
        return new NetworkEditNetworkPage();
    }

    public NetworkEditNetworkPage enterDeleteNetworkPage2() {
        secondLocationMoreIcon.click();
        secondLocationDeleteIcon.click();
        confirmLocationDelete.click();
        return new NetworkEditNetworkPage();
    }

    /**
     * @param locationInfo
     */
    public DashboardLocationPage enterLocation(String locationName) {
        // TODO Auto-generated method stub
        locationName(locationName).click();
        return new DashboardLocationPage();
    }
}
