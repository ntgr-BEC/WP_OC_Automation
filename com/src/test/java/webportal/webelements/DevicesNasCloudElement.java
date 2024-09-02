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
public class DevicesNasCloudElement {
    public SelenideElement nasCloudButton     = $("#cloud-tab");
    public SelenideElement nasSwitchButton      = $(".x-cloud-panel > div > div > div > div > div > div > table > tbody > tr > td[class='ui-buttononoff-small-btn']");
    public SelenideElement nasUsernameInput = $(".x-cloud-settings-rdcontent > tbody > tr > td > div > div > div > div > input");
    public SelenideElement nasPasswordInput = $(".x-cloud-settings-rdcontent > tbody > tr > td > div > div > div > div > input[type='password']");
}
