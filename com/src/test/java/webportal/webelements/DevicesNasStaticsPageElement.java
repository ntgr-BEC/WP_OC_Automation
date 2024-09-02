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
public class DevicesNasStaticsPageElement {
    public SelenideElement diskTempature = $("#divZOvrNasStatics");
    public SelenideElement cpuTempature  = $("#hCpuTempNasStatics");
    public SelenideElement fanSpeed      = $("#divOvrFFanNasStatics");

}
