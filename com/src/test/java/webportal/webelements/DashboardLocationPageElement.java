/**
 *
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import webportal.param.WebportalParam;

/**
 * @author zheli
 *
 */
public class DashboardLocationPageElement {

    /**
     *
     */
    public DashboardLocationPageElement() {
        // TODO Auto-generated constructor stub

    }

    public static SelenideElement devicesLabel             = $(Selectors.byText(WebportalParam.getLocText("Devices")));
    public SelenideElement        systemHealth             = $("#_hhltyLoc");
    public SelenideElement        storagevolume            = $("#_liScrlProp2");
    public SelenideElement        properties               = $("#_ancLiDropToggProp");
    public SelenideElement        refresh                  = $("#_pRefProp");
    public SelenideElement        properitieswindow        = $("#_divPannelHeadProp");
    public SelenideElement        storageutilizationwindow = $("#_divPanlStrge");
    public SelenideElement        storagemenu              = $("#_ancToggDropDownStrge");
    public SelenideElement        refreshstorage           = $("#_pRefStrge");
    public SelenideElement        storageinfo              = $("#_ulDataAvlStrge0");
}
