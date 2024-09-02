package webportal.webelements;

import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ElementsCollection;

public class DevicesSwitchConnectedNeighborsPageElement {
    public ElementsCollection ports = $$("[id*=iconmapRowConnNeigh]");
    public ElementsCollection sIPs  = $$("[id*=pIpmapRowConnNeigh]");
    public ElementsCollection sMacs = $$("[id*=spnMacRowConnNeigh]");
}
