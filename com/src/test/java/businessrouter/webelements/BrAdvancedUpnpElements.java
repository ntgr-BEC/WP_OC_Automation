package businessrouter.webelements;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
public class BrAdvancedUpnpElements {
    public SelenideElement upnpenableordisable   = $("#upnp_turnOn");
    public SelenideElement upnpadverperiod   = $("#upnp_Period");
    public SelenideElement upnptimetolive  = $("#upnp_timeToLive");
    public SelenideElement upnprefreshbutton  = $("#upnp_button_refresh");
    public SelenideElement upnpcancelbutton  = $("#upnp_button_cancel");
    public SelenideElement upnpapplybutton  = $("#upnp_button_apply");
}
