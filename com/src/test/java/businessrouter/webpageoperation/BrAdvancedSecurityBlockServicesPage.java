package businessrouter.webpageoperation;

import java.util.Map;
import java.util.logging.Logger;

import businessrouter.webelements.BrAllMenueElements;
import businessrouter.webelements.BrSecurityServiceBlockingElements;
//import webportal.weboperation.MyCommonAPIs;
import util.MyCommonAPIs;

public class BrAdvancedSecurityBlockServicesPage extends BrSecurityServiceBlockingElements {
    final static Logger logger = Logger.getLogger("BrAdvancedSecurityBlockServicesPage");

    public void OpenSecurityBlockServicesPage() {
        BrAllMenueElements BrAllMenueElements = new BrAllMenueElements();
        logger.info("Open Security Block Services page");
        BrAllMenueElements.advanced.click();
        MyCommonAPIs.sleepi(5);
        BrAllMenueElements.Security.click();
        MyCommonAPIs.sleepi(5);
        BrAllMenueElements.BlockServices.click();
    }

    public void checkboxchecked(String select) {
        logger.info("Check checkbox.");
        if (select.equals("alw")) {
            if (serviceblockingalwayschecked.getAttribute("class").equals("ant-radio ant-radio-checked")) {
                logger.info("Disable always checkbox.");
                serviceblockingalways.selectRadio("always");
            }
        } else if (select.equals("pre")) {
            if (serviceblockingprechecked.getAttribute("class").equals("ant-radio ant-radio-checked")) {
                logger.info("Disable per schedule checkbox.");
                serviceblockingpre.selectRadio("perschedule");
            }
        } else if (select.equals("nev")) {
            if (serviceblockingneverchecked.getAttribute("class").equals("ant-radio ant-radio-checked")) {
                logger.info("Disable never checkbox.");
                serviceblockingnever.selectRadio("never");
            }
        }
    }

    public void checkboxnotchecked(String select) {
        logger.info("Check checkbox.");
        if (select.equals("alw")) {
            if (serviceblockingalwayschecked.getAttribute("class").equals("ant-radio")) {
                logger.info("Enable always checkbox.");
                serviceblockingalways.selectRadio("always");
            }
        } else if (select.equals("pre")) {
            if (serviceblockingprechecked.getAttribute("class").equals("ant-radio")) {
                logger.info("Enable per schedule checkbox.");
                serviceblockingpre.selectRadio("perschedule");
            }
        } else if (select.equals("nev")) {
            if (serviceblockingneverchecked.getAttribute("class").equals("ant-radio")) {
                logger.info("Enable never checkbox.");
                serviceblockingnever.selectRadio("never");
            }
        }
    }

    public boolean checkblockserviceaddmax() {
        boolean result = false;
        logger.info("Check block service add max.");
        for (int i = 1; i < 22
                ; i++) {
            serviceblockingaddbutton.click();
            if (i > 20) {
                logger.info("Block service add max,check dialog.");
                result = checkdialog();
                System.out.print(result);
            }
            MyCommonAPIs.sleepi(3);
            if (serviceblockingstartingport.exists()) {
                logger.info("Block service add.");
                serviceblockingstartingport.sendKeys(String.valueOf(i));
                MyCommonAPIs.sleepi(3);
                serviceblockingendingport.sendKeys(String.valueOf(i + 1));
                MyCommonAPIs.sleepi(3);
                serviceblockingtypedefineduser.sendKeys(String.valueOf(i));
                MyCommonAPIs.sleepi(3);
                serviceblockingruleapply.click();
                MyCommonAPIs.sleepi(3);
            }
        }
        System.out.print(result);
        return result;
    }

    public boolean checkdialog() {
        boolean result = false;
        System.out.println("d@@@@@@@@@@##############$$$$$$$$$$$%%%%%%%%%%%");
        System.out.print(serviceblockingdialog.getText());
        //System.out.print(serviceblockingdialog2.exists());
        //if (serviceblockingdialog.exists() || serviceblockingdialog2.exists()) {
       //     result = true;  
        //}
       // System.out.print(result);                                           
        if (serviceblockingdialog.getText().equals("The router can support only 20 service rules.")
                || serviceblockingdialog.getText().equals("The blocked service already exists.")) {
            logger.info("Dialog display.");
            result = true;
        } else {
            logger.info("Dialog not display.");
            result = false;
        }
        return result;
    }

    public void SetOnlyIpAddress(String ip) {
        String[] ips = ip.split("\\.");
        InputOnlyIpAddress(ips);
    }

    public void SetIpAddressRange(String ip1, String ip2) {
        String[] ips1 = ip1.split("\\.");
        String[] ips2 = ip2.split("\\.");
        InputIpAddressRange(ips1, ips2);
    }

    public void blockserviceselect(Map<String, String> BlockServicesConfig) {
        logger.info("Add block service.");
        serviceblockingaddbutton.click();
        MyCommonAPIs.sleepi(3);
        serviceblockingservicetype.click();
        serviceblockingservicetypeselect(BlockServicesConfig.get("servicestype"));
        MyCommonAPIs.sleepi(3);
        if (BlockServicesConfig.containsKey("protocol") && !BlockServicesConfig.get("protocol").equals("")) {
            serviceblockingprotocol.click();
            serviceblockingprotocolselect(BlockServicesConfig.get("protocol"));
            MyCommonAPIs.sleepi(3);
        }
        if (BlockServicesConfig.containsKey("startingport") && !BlockServicesConfig.get("startingport").equals("")) {
            serviceblockingstartingport.sendKeys(BlockServicesConfig.get("startingport"));
            MyCommonAPIs.sleepi(3);
        }
        if (BlockServicesConfig.containsKey("endingport") && !BlockServicesConfig.get("endingport").equals("")) {
            serviceblockingendingport.sendKeys(BlockServicesConfig.get("endingport"));
            MyCommonAPIs.sleepi(3);
        }
        if (BlockServicesConfig.containsKey("userdsefined") && !BlockServicesConfig.get("userdsefined").equals("")) {
            serviceblockingtypedefineduser.sendKeys(BlockServicesConfig.get("userdsefined"));
            MyCommonAPIs.sleepi(3);
        }
        if (BlockServicesConfig.containsKey("onlyipaddress") && !BlockServicesConfig.get("onlyipaddress").equals("")) {
            if (!serviceonlyipaddresschecked.getAttribute("class").equals("ant-radio ant-radio-checked")) {
                serviceblockingonlyip.selectRadio("single");
            }
            MyCommonAPIs.sleepi(3);
            SetOnlyIpAddress(BlockServicesConfig.get("onlyipaddress"));
            MyCommonAPIs.sleepi(3);
        }
        if (BlockServicesConfig.containsKey("ipaddressfrom") && BlockServicesConfig.containsKey("ipaddressto")
                && !BlockServicesConfig.get("ipaddressfrom").equals("") && !BlockServicesConfig.get("ipaddressto").equals("")) {
            if (!serviceipaddressrange.getAttribute("class").equals("ant-radio ant-radio-checked")) {
                serviceblockingiprange.selectRadio("range_addr");
            }
            MyCommonAPIs.sleepi(3);
            SetIpAddressRange(BlockServicesConfig.get("ipaddressfrom"), BlockServicesConfig.get("ipaddressto"));
            MyCommonAPIs.sleepi(3);
        }
        serviceblockingruleapply.click();
        if (BlockServicesConfig.containsKey("option") && !BlockServicesConfig.get("option").equals("")) {
            MyCommonAPIs.sleepi(3);
            checkboxnotchecked(BlockServicesConfig.get("option"));
            MyCommonAPIs.sleepi(3);
            serviceblockingapply.click();
        }
        //System.out.print(serviceblockingdialog.exists());
        //System.out.print(serviceblockingdialog2.exists());
        logger.info("Add block service successful.");
    }

    public void blockserviceedit(Map<String, String> BlockServicesConfig) {
        logger.info("Edit block service.");
        serviceblockingtable1.selectRadio("on");
        serviceblockingeditbutton.click();
        MyCommonAPIs.sleepi(3);
        serviceblockingservicetype.click();
        serviceblockingservicetypeselect(BlockServicesConfig.get("servicestype"));
        MyCommonAPIs.sleepi(3);
        if (BlockServicesConfig.containsKey("protocol") && !BlockServicesConfig.get("protocol").equals("")) {
            serviceblockingprotocol.click();
            serviceblockingprotocolselect(BlockServicesConfig.get("protocol"));
            MyCommonAPIs.sleepi(3);
        }
        if (BlockServicesConfig.containsKey("startingport") && !BlockServicesConfig.get("startingport").equals("")) {
            serviceblockingstartingport.clear();
            MyCommonAPIs.sleepi(3);
            serviceblockingstartingport.sendKeys(BlockServicesConfig.get("startingport"));
            MyCommonAPIs.sleepi(3);
        }
        if (BlockServicesConfig.containsKey("endingport") && !BlockServicesConfig.get("endingport").equals("")) {
            serviceblockingendingport.clear();
            MyCommonAPIs.sleepi(3);
            serviceblockingendingport.sendKeys(BlockServicesConfig.get("endingport"));
            MyCommonAPIs.sleepi(3);
        }
        if (BlockServicesConfig.containsKey("userdsefined") && !BlockServicesConfig.get("userdsefined").equals("")) {
            serviceblockingtypedefineduser.clear();
            MyCommonAPIs.sleepi(3);
            serviceblockingtypedefineduser.sendKeys(BlockServicesConfig.get("userdsefined"));
            MyCommonAPIs.sleepi(3);
        }
        if (BlockServicesConfig.containsKey("onlyipaddress") && !BlockServicesConfig.get("onlyipaddress").equals("")) {
            if (!serviceonlyipaddresschecked.getAttribute("class").equals("ant-radio ant-radio-checked")) {
                serviceblockingonlyip.selectRadio("single");
            }
            MyCommonAPIs.sleepi(3);
            SetOnlyIpAddress(BlockServicesConfig.get("onlyipaddress"));
            MyCommonAPIs.sleepi(3);
        }
        if (BlockServicesConfig.containsKey("ipaddressfrom") && BlockServicesConfig.containsKey("ipaddressto")
                && !BlockServicesConfig.get("ipaddressfrom").equals("") && !BlockServicesConfig.get("ipaddressto").equals("")) {
            if (!serviceipaddressrange.getAttribute("class").equals("ant-radio ant-radio-checked")) {
                serviceblockingiprange.selectRadio("range_addr");
            }
            MyCommonAPIs.sleepi(3);
            SetIpAddressRange(BlockServicesConfig.get("ipaddressfrom"), BlockServicesConfig.get("ipaddressto"));
            MyCommonAPIs.sleepi(3);
        }
        serviceblockingruleapply.click();
        if (BlockServicesConfig.containsKey("option") && !BlockServicesConfig.get("option").equals("")) {
            MyCommonAPIs.sleepi(3);
            checkboxnotchecked(BlockServicesConfig.get("option"));
            MyCommonAPIs.sleepi(3);
            serviceblockingapply.click();
        }
        logger.info("Edit block service successful.");
    }

    public void blockservicesdeleteconfig() {
        logger.info("Delete block services config.");
        //if (serviceblockingtable1checked.getAttribute("class").equals("ant-radio")) {
            serviceblockingtable1.selectRadio("on");
        //}
        serviceblockingdeletebutton.click();
        MyCommonAPIs.sleepi(3);
        servicedelruleok.click();
       // serviceblockingdeleteokbutton.click();
       // MyCommonAPIs.sleepi(3);
        checkboxnotchecked("nev");
        MyCommonAPIs.sleepi(3);
        serviceblockingapply.click();
        
        logger.info("Delete block services config successful.");
    }

}
