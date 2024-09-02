package webportal.webelements;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;

import util.MyCommonAPIs;

public class CopyConfigurationPageElement extends MyCommonAPIs {
    public SelenideElement managerdropdown = $x("//img[contains(@src,'icon-setting')]");

    public SelenideElement selectOrg  = $x("(//select[contains(@class,'form-control input')])[2]");
    public SelenideElement selectLct  = $x("(//select[contains(@class,'form-control input')])[3]");
    public String          targetOrg  = "//label[text()='%s']/../i/label/i";
    public SelenideElement saveBtn    = $x("//button[contains(@class,'saveBtn')]");
    public SelenideElement successMsg = $x("//a[@class='customeSuccessClose']");
    public SelenideElement failedMsg  = $x("//a[@class='customeErrorClose']");

}
