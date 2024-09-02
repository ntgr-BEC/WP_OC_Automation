/**
 * 
 */
package webportal.webelements;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

/**
 * @author ronliu
 *
 */
public class DevicesNasAntivirusElement {
    public SelenideElement curfirmware     = $("#pCurrntFirmWare");
    public SelenideElement upfirmware      = $("#pUpdtFirmWare");
    public SelenideElement antivirusStatus = $("#plastScanAntiVrus");

}
