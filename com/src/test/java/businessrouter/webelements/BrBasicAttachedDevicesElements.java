package businessrouter.webelements;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;
public class BrBasicAttachedDevicesElements {

        public SelenideElement accesscontrol   = $("#editDeviceName_input1");
        public SelenideElement accesscontrolstatus  = $x("//*[@id=\"root\"]/div/div/div[2]/div/div[2]/div/div/div/div[1]/div/div[2]/div/span[2]");
        public SelenideElement accesscontrolgenerlrule   = $x("//*[@id=\"root\"]/div/div/div[2]/div/div[2]/div/div/div/div[1]/div/div[2]/div/span[3]");
        public SelenideElement devicelist    = $x("//div[@id ='attachedDevices_listdevice_list1Item1']");
        public SelenideElement landevicename    = $("#attachedDevices_editdevice_deviceName");
        public SelenideElement invaliddevicenamewarning    = $x("//div[@class ='ant-form-explain']");
        public SelenideElement devicetype    = $x("//div[@class ='ant-select-selection__rendered']");
        public SelenideElement applybutton   = $("#attachedDevices_editdevice_buttapply");
        public SelenideElement devicelogger   =$x("(//*[@id=\"attachedDevices_listdevice_list1Item1Meta1\"]/div[1]/a/img)[2]");

    
}
