package businessrouter.webpageoperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.openqa.selenium.JavascriptExecutor;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import businessrouter.webelements.BrAdvancedVlanElements;
import businessrouter.webelements.BrAllMenueElements;
//import webportal.weboperation.MyCommonAPIs;
import util.MyCommonAPIs;
import webportal.param.WebportalParam;

public class BrAdvancedVlanPage extends BrAdvancedVlanElements {
    final static Logger logger = Logger.getLogger("BrAdvancedVlanPage");

    public void OpenAdvancedVlanPage() {
        // open(LoginURL);
        logger.info("Open Advanced Vlan Page");
        BrAllMenueElements BrAllMenueElements = new BrAllMenueElements();
        BrAllMenueElements.advanced.click();
        util.MyCommonAPIs.sleep(5000);
        BrAllMenueElements.VLAN.click();
    }

    public void AddVlan(Map<String, String> vlan) {
        // open(LoginURL)
        vlanaddbutton.click();
        util.MyCommonAPIs.sleep(100);

        if (vlan.get("VLAN ID") != null) {
            vlanid.setValue(vlan.get("VLAN ID"));
        } else {
            logger.info("VLAN ID is null");

        }
        if (vlan.get("Name") != null) {
            vlanname.setValue(vlan.get("Name"));
        } else {
            logger.info("Name  is null");
        }
        ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(true);", vlanname);
        if (vlan.get("Ports") != null) {
            String[] portlist = vlan.get("Ports").split(";");
            for (int i = 0; i < portlist.length; i++) {
                System.out.println(portlist[i]);
                String[] portnumandinf = portlist[i].split(":");
                logger.info(portnumandinf[0]);
                logger.info(portnumandinf[1]);
                if (portnumandinf[0].contentEquals("1")) {
                    if (WebportalParam.DUTType.contentEquals("BR500")) {
                        vlanlanport1.selectRadio("on");
                        vlanlanporttaguntag1.click();
                    } else if(WebportalParam.DUTType.contentEquals("BR100")) {
                        br100vlanlanport1.selectRadio("on");
                        vlanlanporttaguntag1.click();
                    }
                    MyCommonAPIs.sleepi(3);
                    if (portnumandinf[1].contentEquals("TAG")) {

                        vlanlanporttagvalue.click();

                    } else if (portnumandinf[1].contentEquals("UNTAG")) {
                        vlanlanportuntagvalue.click();
                    }

                    // vlanlanporttaguntag1.selectOptionContainingText("");
                } else if (portnumandinf[0].contentEquals("2")) {
                    if (WebportalParam.DUTType.contentEquals("BR500")) {
                        vlanlanport2.selectRadio("on");
                        vlanlanporttaguntag2.click();
                    } else if(WebportalParam.DUTType.contentEquals("BR100")) {
                        System.out.println("7777777777777777777777777777");
                        ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", vlanaddvlanapplybutton);
                        br100vlanlanport2.selectRadio("on");
                        vlanlanporttaguntag2.click();
                    }
                    
                    MyCommonAPIs.sleepi(3);
                    if (portnumandinf[1].contentEquals("TAG")) {

                        vlanlanporttagvalue.click();

                    } else if (portnumandinf[1].contentEquals("UNTAG")) {
                        vlanlanportuntagvalue.click();
                    }
                } else if (portnumandinf[0].contentEquals("3")) {
                    ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);",
                            vlanaddvlanapplybutton);
                    vlanlanport3.selectRadio("on");
                    vlanlanporttaguntag3.click();
                    MyCommonAPIs.sleepi(3);
                    if (portnumandinf[1].contentEquals("TAG")) {
                        vlanlanporttagvalue.click();

                    } else if (portnumandinf[1].contentEquals("UNTAG")) {
                        vlanlanportuntagvalue.click();
                    }
                } else if (portnumandinf[0].contentEquals("4")) {
                    ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);",
                            vlanaddvlanapplybutton);
                    vlanlanport4.selectRadio("on");
                    vlanlanporttaguntag4.click();
                    MyCommonAPIs.sleepi(3);
                    if (portnumandinf[1].contentEquals("TAG")) {

                        vlanlanporttagvalue.click();

                    } else if (portnumandinf[1].contentEquals("UNTAG")) {
                        vlanlanportuntagvalue.click();
                    }
                }

            }

        } else {
            logger.info("Ports  are  null");

        }
        if (vlan.get("Description") != null) {
            vlandescription.setValue(vlan.get("Description"));
        }
        vlanaddvlanapplybutton.click();
    }

    public void DeleteVLAN(String vlanID) {
        String vlanidoftable = "//tr[@class ='ant-table-row  ant-table-row-level-0']/td[@class ='ant-table-column-has-filters'][2]";
        String Checkboxnumber = "(//input[@type =\"checkbox\"])";
        // (//input[@type =\"checkbox\"])";

        logger.info(String.format("get Vlan list..."));
        MyCommonAPIs.waitReady();
        List<String> lsvlanid = MyCommonAPIs.getTexts(vlanidoftable);
        int i = 1;
        for (String name : lsvlanid) {

            i = i + 1;
            System.out.print(name.indexOf(vlanID));
            if (name.indexOf(vlanID) != -1) {
                Checkboxnumber = Checkboxnumber + "[" + String.valueOf(i) + "]";
                vlanselectone = $x(Checkboxnumber);
                MyCommonAPIs.sleep(4000);
                logger.info(Checkboxnumber);
                logger.info(String.format(": %s", name));
                vlanselectone.selectRadio("on");

            }
        }
        vlandeletebutton.click();
        vlanokbutton.click();
    }

    public boolean DeleteVlanMember(Map<String, String> Vlan) {
        boolean result =false;
        logger.info("Delete vlan member start.");

        String tablevlanid = "//tr[@class ='ant-table-row  ant-table-row-level-0']";
        String vlanmember = "#addVlan_PanelView_6_checkbox_";
        String vlanlist=tablevlanid + "[" + Vlan.get("VLAN ID") + "]" + "/td[7]//a[1]";
        System.out.println(vlanlist);
        SelenideElement vlantableedit = $x(tablevlanid + "[" + Vlan.get("VLAN ID") + "]" + "/td[7]//a[1]");
        vlantableedit.click();
        MyCommonAPIs.sleepi(3);
        String[] ports = Vlan.get("Ports").split(";");
        for (String s : ports) {
            s = s.substring(0, s.indexOf(":"));
            if (WebportalParam.DUTType.contentEquals("BR500")) {
                SelenideElement vlanlanporttaguntag = $(vlanmember + String.valueOf(Integer.valueOf(s) - 1));
                if (vlanlanporttaguntag.isSelected()) {
                    vlanlanporttaguntag.selectRadio("on");
                    System.out.println("eeeeeeeeeeeeeeeeeeeeeee");
                    if (!vlanlanporttaguntag.isSelected()) {
                        result = true;
                    } else {
                        result = false;
                        
                    }
                   
                }
            } else if(WebportalParam.DUTType.contentEquals("BR100")) {
                SelenideElement vlanlanporttaguntag = $(vlanmember + String.valueOf(Integer.valueOf(s) + 2));
                if (vlanlanporttaguntag.isSelected()) {
                    vlanlanporttaguntag.selectRadio("on");
                    System.out.println("eeeeeeeeeeeeeeeeeeeeeee");
                    if (!vlanlanporttaguntag.isSelected()) {
                        result = true;
                    } else {
                        result = false;
                        
                    }
                   
                }
            }
            
            MyCommonAPIs.sleepi(3);
        }
        MyCommonAPIs.sleepi(5);
        vlanaddvlanapplybutton.click();
        logger.info("Delete vlan member done.");
        System.out.print(result);
       return result;
    }

    public void DeleteallVlans() {
        if (vlanselectall.exists()) {
            vlanselectall.selectRadio("on");
            vlandeletebutton.click();
            vlanokbutton.click();
        }

    }

    public void AddVlanMember(Map<String, String> Vlan) {
        logger.info("Add vlan member start.");

        String tablevlanid = "//tr[@class ='ant-table-row  ant-table-row-level-0']";
        String vlanmember = "#addVlan_PanelView_6_checkbox_";
        String porttagselect = "(//div[@class ='ant-select-selection__rendered'])[";

        SelenideElement vlantableedit = $x(tablevlanid + "[" + Vlan.get("VLAN ID") + "]" + "/td[7]//a[1]");
        vlantableedit.click();
        MyCommonAPIs.sleepi(3);
        String[] ports = Vlan.get("Ports").split(";");
        for (String s : ports) {
            String[] tag = s.split(":");
            SelenideElement vlanlanporttaguntag = $(vlanmember + String.valueOf(Integer.valueOf(tag[0]) - 1));
            if (!vlanlanporttaguntag.isSelected()) {
                vlanlanporttaguntag.selectRadio("on");
            }
            MyCommonAPIs.sleepi(3);
            SelenideElement vlanlanportselect = $x(porttagselect + tag[0] + "]");
            vlanlanportselect.click();
            ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", vlanaddvlanapplybutton);
            if (tag[1].contentEquals("TAG")) {

                vlanlanporttagvalue.click();

            } else if (tag[1].contentEquals("UNTAG")) {
                vlanlanportuntagvalue.click();
            }
        }
        vlanaddvlanapplybutton.click();
        logger.info("Add vlan member done.");
    }

    public void ChangePortTagOrUntag(Map<String, String> Vlan) {
        // String vlanidoftable = vlantablevlanid.getSearchCriteria();
        String vlanidoftable = "//tr[@class ='ant-table-row  ant-table-row-level-0']/td[@class ='ant-table-column-has-filters'][2]";
        // String EditButtonLocation = vlaneditbutton.getSearchCriteria();
        String EditButtonLocation = "#vlan_list_4_";
        logger.info(vlanidoftable);
        logger.info(EditButtonLocation);
        logger.info(String.format("get Vlan list..."));
        MyCommonAPIs.waitReady();
        List<String> lsvlanid = MyCommonAPIs.getTexts(vlanidoftable);
        int i = 0;
        for (String name : lsvlanid) {
            i = i + 1;
            logger.info("name:" + name);

            if (name.contentEquals(Vlan.get("VLAN ID"))) {
                EditButtonLocation = EditButtonLocation + String.valueOf(i) + "_1";
                logger.info(EditButtonLocation);
                vlaneditbutton = $(EditButtonLocation);
                MyCommonAPIs.sleep(4000);
                vlaneditbutton.click();
                MyCommonAPIs.sleep(4000);
            }
        }

        if (Vlan.get("Ports") != null) {
            String[] PortAndStatusList = Vlan.get("Ports").split(";");
            for (int j = 0; j < PortAndStatusList.length; j++) {
                String[] portnumandinf = PortAndStatusList[j].split(":");
                logger.info(portnumandinf[0]);
                logger.info(portnumandinf[1]);
                if (portnumandinf[0].contentEquals("1")) {
                    logger.info("11111111222222222222");
                    if (WebportalParam.DUTType.contentEquals("BR500")) {
                        vlanlanport1.selectRadio("on");
                     
                    } else if(WebportalParam.DUTType.contentEquals("BR100")) {
                        System.out.println("7777777777777777777777777777");
                        br100vlanlanport1.selectRadio("on");
                    }
                    vlanlanporttaguntag1.click();
                    if (portnumandinf[1].contentEquals("TAG")) {

                        vlanlanporttagvalue.click();

                    } else if (portnumandinf[1].contentEquals("UNTAG")) {
                        vlanlanportuntagvalue.click();
                    }

                } else if (portnumandinf[0].contentEquals("2")) {
                    logger.info("1111111122222222222233");
                    
                    if (WebportalParam.DUTType.contentEquals("BR500")) {
                        vlanlanport2.selectRadio("on");
                     
                    } else if(WebportalParam.DUTType.contentEquals("BR100")) {
                        System.out.println("7777777777777777777777777777");
                        br100vlanlanport2.selectRadio("on");
                        ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);", vlanaddvlanapplybutton);
                       
                    }
                    vlanlanporttaguntag2.click();
                    if (portnumandinf[1].contentEquals("TAG")) {

                        vlanlanporttagvalue.click();

                    } else if (portnumandinf[1].contentEquals("UNTAG")) {
                        System.out.println("7777777777777777777777777777999");
                        vlanlanportuntagvalue.click();
                        System.out.println("7777777777777777777777777777999");
                        MyCommonAPIs.sleep(10000);
                    }
                } else if (portnumandinf[0].contentEquals("3")) {
                    logger.info("1111111122222222222255");
                    ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);",
                            vlanaddvlanapplybutton);
                    Selenide.sleep(2000);
                    vlanlanporttaguntag3.click();
                    Selenide.sleep(2000);
                    if (portnumandinf[1].contentEquals("TAG")) {
                        logger.info("11111111222222222222551");
                        vlanlanporttagvalue.click();

                    } else if (portnumandinf[1].contentEquals("UNTAG")) {
                        logger.info("11111111222222222222552");
                        vlanlanportuntagvalue.click();
                    }
                } else if (portnumandinf[0].contentEquals("4")) {
                    logger.info("1111111122222222222266");
                    ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(false);",
                            vlanaddvlanapplybutton);
                    Selenide.sleep(2000);
                    vlanlanporttaguntag4.click();
                    Selenide.sleep(2000);
                    if (portnumandinf[1].contentEquals("TAG")) {

                        vlanlanporttagvalue.click();

                    } else if (portnumandinf[1].contentEquals("UNTAG")) {
                        vlanlanportuntagvalue.click();
                    }
                }
            }
        }
        vlanaddvlanapplybutton.click();
        Selenide.sleep(20000);
        logger.info("ChangePortTagOrUntag done!");
    }

    public boolean CheckVlanMember(Map<String, String> Vlan) {
        boolean result = false;

        logger.info("Check vlan memeber start.");
        String tablevlanid = "//tr[@class ='ant-table-row  ant-table-row-level-0']";
        System.out.println(tablevlanid + "[" + Vlan.get("VLAN ID") + "]" + "/td[@class ='ant-table-column-has-filters'][4]");
        SelenideElement vlantablevlanmember = $x(tablevlanid + "[" + Vlan.get("VLAN ID") + "]" + "/td[@class ='ant-table-column-has-filters'][4]");

        String[] ports = Vlan.get("Ports").split(";");
        for (String s : ports) {
            s = s.replace(":", "(");
           System.out.print(vlantablevlanmember.innerText().indexOf(s));
            if (vlantablevlanmember.innerText().indexOf(s) != -1) {
                result = true;
            } else {
                result = false;
                break;
            }
        }

        logger.info("Check vlan memeber done.");
        System.out.print(result);
        logger.info(String.valueOf(result));
        return result;
    }

    public boolean CheckVlanConfig(Map<String, String> Vlan) {
        boolean result;
        logger.info("Check vlan config start.");

        String tablevlanid = "//tr[@class ='ant-table-row  ant-table-row-level-0']";

        vlantablevlanid = $x(tablevlanid + "[" + Vlan.get("VLAN ID") + "]" + "/td[@class ='ant-table-column-has-filters'][2]");
        vlantablelist = $x(tablevlanid + "[" + Vlan.get("VLAN ID") + "]");

        if (Integer.valueOf(vlantablevlanid.getText()) == Integer.valueOf(Vlan.get("VLAN ID"))
                && vlantablelist.getText().toLowerCase().indexOf(Vlan.get("Name")) != -1) {
            result = true;
        } else {
            result = false;
        }
        logger.info("Check vlan config done.");
        System.out.print(result);
        return result;
    }

}
