/**
 *
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.logging.Logger;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

/**
 * @author lavi
 *
 */
public class DevicesOrbiSatellitesPageElement extends MyCommonAPIs {
    Logger logger;

    public DevicesOrbiSatellitesPageElement() {
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
    }

    public static String sIndoorTable  = "#satelliteTable tbody";
    public static String sOutdoorTable = "#OutdoorSatellite tbody";

    public SelenideElement btnAdd    = $(".addIcon");
    public SelenideElement btnCancel = $(".DayZeroMode.in .modal-header button");
    public SelenideElement btnClose = $x("//button[@class='close']");
    public SelenideElement btnNextforexist   = $x("//button[contains(@class,'btn btn-primary')][text()='Next']");
    public SelenideElement btnNext   = $x("//div[contains(@style,'display: block')]//button[contains(@class,'btn btn-primary')][text()='Next']");
    public SelenideElement firstsatellite  = $("#satelliteTable tbody tr");
    public SelenideElement deleteicon  = $x("//img[contains(@src,'del.png')]");
    public SelenideElement deletebtn   = $x("//div[contains(@style,'display: block')]//button[@class='btn btn-danger']");

    /**
     * @param indoor
     *            true for indoor
     * @return
     */
    public boolean isTableHasData(boolean indoor) {
        int tdSize = 0;
        if (indoor) {
            tdSize = $(sIndoorTable).$$("td").size();
        } else {
            tdSize = $(sOutdoorTable).$$("td").size();
        }

        if (tdSize > 1)
            return true;
        return false;
    }

    public void setName(String toName) {
        $(sIndoorTable).$("input").setValue(toName);
        $(sIndoorTable).$("span .sendEditField").click();
        waitReady();
    }

    public static SelenideElement txtIpStart = $("#inpNameSwitchIpSett");
    public static SelenideElement txtIpEnd   = $("#inpIpDenServer1SwitchIpSett");

}
