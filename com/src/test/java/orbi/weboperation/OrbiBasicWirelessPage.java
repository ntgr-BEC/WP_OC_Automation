package orbi.weboperation;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.codeborne.selenide.Selenide;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.SelenideElement;

import webportal.param.WebportalParam;
import orbi.webelements.OrbiAllMenueElements;
import orbi.webelements.OrbiBasicWirelessElements;
import util.MyCommonAPIs;

public class OrbiBasicWirelessPage extends OrbiBasicWirelessElements {

    final static Logger logger = Logger.getLogger("OrbiBasicWirelessPage");

    public void OpenBasicWirelessPage() {
        logger.info("Open Basic Wireless Page");
        OrbiAllMenueElements BrAllMenueElements = new OrbiAllMenueElements();
        if (!WebportalParam.arch.equals("new")) {
            Selenide.switchTo().frame("topframe");
        }
        BrAllMenueElements.basic.click();
        MyCommonAPIs.sleepi(5);
        if (!WebportalParam.arch.equals("new")) {
            Selenide.switchTo().parentFrame();
        }
        BrAllMenueElements.Wireless.click();
        MyCommonAPIs.sleepi(6);
        logger.info("OpenBasicWirelessPage Done");
    }

    public void ConfigWirelessInfor(Map<String, String> WirelessInfo) {
        logger.info("ConfigWirelessInfo");
        if (!WebportalParam.arch.equals("new")) {
            Selenide.switchTo().frame("formframe");
        }
        if (WirelessInfo.get("tab").equals("Wireless1")) {
            wireless1.click();
            wireless1ssidname.setValue(WirelessInfo.get("ssid"));

            if (WirelessInfo.get("security").equals("WAP2-PSK")) {
                if (wireless1wpa2aes.getAttribute("checked") != "checked") {
                    MyCommonAPIs.clickElementIdViaJs(wireless1wpa2aesid); // Change for compatibility

                }
            } else if (WirelessInfo.get("security").equals("WPA-PSK + WPA2-PSK")) {
                if (wireless1tkipandaes.getAttribute("checked") != "checked") {
                    MyCommonAPIs.clickElementIdViaJs(wireless1tkipandaesid); // Change for compatibility
                }

            } else if (WirelessInfo.get("security").equals("WPA2 Enterprise")) {
                if (wireless1wpaenterprise.getAttribute("checked") != "checked") {
                    MyCommonAPIs.clickElementIdViaJs(wireless1wpaenterpriseid); // Change for compatibility
                }
            } else if (WirelessInfo.get("security").equals("None")) {
                if (wireless1securitynone.getAttribute("checked") != "checked") {
                    MyCommonAPIs.clickElementIdViaJs(wireless1securitynoneid); // Change for compatibility
                }
            } else if (WirelessInfo.get("security").equals("WPA2 Personal + WPA3 Personal")) {
                if (wireless1wpa2wpa3.getAttribute("checked") != "checked") {
                    MyCommonAPIs.clickElementIdViaJs(wireless1wpa2wpa3id); // Change for compatibility
                }
            } else if (WirelessInfo.get("security").equals("WPA3 Enterprise")) {
                if (wireless1wpa3enterprise.getAttribute("checked") != "checked") {
                    MyCommonAPIs.clickElementIdViaJs(wireless1wpa3enterpriseid); // Change for compatibility
                }
            } 

            if (WirelessInfo.containsKey("password")) {
                wireless1password.setValue(WirelessInfo.get("password"));
            }
            

        } else if (WirelessInfo.get("tab").equals("Wireless2")) {
            wireless2.click();
            if (!wireless2enable.isSelected()) {
                MyCommonAPIs.clickElementIdViaJs(wireless2enableid); // Change for compatibility
            }
            wireless2ssidname.clear();
            wireless2ssidname.sendKeys(WirelessInfo.get("ssid"));
            if (WirelessInfo.get("security").equals("WAP2-PSK")) {
                if (!wireless2wpa2aes.isSelected()) {
                    MyCommonAPIs.clickElementIdViaJs(wireless2wpa2aesid); // Change for compatibility
                }
            } else if (WirelessInfo.get("security").equals("WPA-PSK + WPA2-PSK")) {
                if (!wireless2tkipandaes.isSelected()) {
                    MyCommonAPIs.clickElementIdViaJs(wireless2tkipandaesid); // Change for compatibility
                }

            } else if (WirelessInfo.get("security").equals("WPA2 Enterprise")) {
                if (!wireless2wpaenterprise.isSelected()) {
                    MyCommonAPIs.clickElementIdViaJs(wireless2wpaenterpriseid); // Change for compatibility
                }
            } else if (WirelessInfo.get("security").equals("None")) {
                if (!wireless2securitynone.isSelected()) {
                    MyCommonAPIs.clickElementIdViaJs(wireless2securitynoneid); // Change for compatibility
                }
            } else if (WirelessInfo.get("security").equals("WPA2 Personal + WPA3 Personal")) {
                if (wireless1wpa2wpa3.getAttribute("checked") != "checked") {
                    MyCommonAPIs.clickElementIdViaJs(wireless1wpa2wpa3id); // Change for compatibility
                }
            } else if (WirelessInfo.get("security").equals("WPA3 Enterprise")) {
                if (wireless1wpa3enterprise.getAttribute("checked") != "checked") {
                    MyCommonAPIs.clickElementIdViaJs(wireless1wpa3enterpriseid); // Change for compatibility
                }
            } 
            if (WirelessInfo.containsKey("password")) {
                wireless2password.setValue(WirelessInfo.get("password"));
            }
            if (WirelessInfo.get("vlan") != null) {
                if (!wireless2vlan.isSelected()) {
                    MyCommonAPIs.clickElementIdViaJs(wireless2vlanId); // Change for compatibility
                }
                wireless2vlanid.click();
                String VlanidAndName = "//*[contains(text(),'" + WirelessInfo.get("vlan") + "')]"; // Change for compatibility
                System.out.println(VlanidAndName);
                SelenideElement vlanidandname = $x(VlanidAndName);
                vlanidandname.click();
            }
            
        } else if (WirelessInfo.get("tab").equals("Wireless3")) {
            wireless3.click();
            if (!wireless3enable.isSelected()) {
                MyCommonAPIs.clickElementIdViaJs(wireless3enableid); // Change for compatibility
            }
            wireless3ssidname.clear();
            wireless3ssidname.setValue(WirelessInfo.get("ssid"));
            if (WirelessInfo.get("security").equals("WAP2-PSK")) {
                if (!wireless3wpa2aes.isSelected()) {
                    MyCommonAPIs.clickElementIdViaJs(wireless3wpa2aesid); // Change for compatibility
                }
            } else if (WirelessInfo.get("security").equals("WPA-PSK + WPA2-PSK")) {
                if (!wireless3tkipandaes.isSelected()) {
                    MyCommonAPIs.clickElementIdViaJs(wireless3tkipandaesid); // Change for compatibility
                }

            } else if (WirelessInfo.get("security").equals("WPA2 Enterprise")) {
                if (!wireless3wpaenterprise.isSelected()) {
                    MyCommonAPIs.clickElementIdViaJs(wireless3wpaenterpriseid); // Change for compatibility
                }
            } else if (WirelessInfo.get("security").equals("None")) {
                if (!wireless3securitynone.isSelected()) {
                    MyCommonAPIs.clickElementIdViaJs(wireless3securitynoneid); // Change for compatibility
                }
            } else if (WirelessInfo.get("security").equals("WPA2 Personal + WPA3 Personal")) {
                if (wireless1wpa2wpa3.getAttribute("checked") != "checked") {
                    MyCommonAPIs.clickElementIdViaJs(wireless1wpa2wpa3id); // Change for compatibility
                }
            } else if (WirelessInfo.get("security").equals("WPA3 Enterprise")) {
                if (wireless1wpa3enterprise.getAttribute("checked") != "checked") {
                    MyCommonAPIs.clickElementIdViaJs(wireless1wpa3enterpriseid); // Change for compatibility
                }
            } 
            if (WirelessInfo.containsKey("password")) {
                wireless2password.setValue(WirelessInfo.get("password"));
            }
            
        }
        if (WirelessInfo.containsKey("radiusip")) {
            String[] RadiusIpList = WirelessInfo.get("radiusip").split("\\.");
            radiusIp1.setValue(RadiusIpList[0]);
            radiusIp2.setValue(RadiusIpList[1]);
            radiusIp3.setValue(RadiusIpList[2]);
            radiusIp4.setValue(RadiusIpList[3]);
        }
        if (WirelessInfo.containsKey("radiussecret")) {
            radiusSecret.setValue(WirelessInfo.get("radiussecret"));
        }
        
        wireless1applybutton.click();
        
        int i = 0;
        while (i < 30) {
            MyCommonAPIs.sleepi(10);
            if (wirelessapplypercentage.exists()) {
                logger.info("Wait the configure apply success ....");
                System.out.println("Wait the configure apply success ....");
            } else {
                logger.info("ConfigWirelessInforWithSsidType Done");
                break;
            }
            i += 1;
        }
        if (!WebportalParam.arch.equals("new")) {
            Selenide.switchTo().parentFrame();
        }
        logger.info("ConfigWirelessInfo Done");
    }

    public void ConfigWirelessInforWithSsidType(Map<String, String> WirelessInfo) {
        logger.info("ConfigWirelessInforWithSsidType");
        if (!WebportalParam.arch.equals("new")) {
            Selenide.switchTo().frame("formframe");
        }
        if (WirelessInfo.get("tab").equals("Wireless1")) {
            wireless1.click();
            wireless1ssidname.setValue(WirelessInfo.get("ssid"));
            if (WirelessInfo.get("security").equals("WAP2-PSK")) {
                if (!wireless1wpa2aes.isSelected()) {
                    // wireless1wpa2aes.click();
                    MyCommonAPIs.clickElementIdViaJs(wireless1wpa2aesid); // Change for compatibility
                }
            } else if (WirelessInfo.get("security").equals("WPA-PSK + WPA2-PSK")) {
                if (!wireless1tkipandaes.isSelected()) {
                    // wireless1tkipandaes.click();
                    MyCommonAPIs.clickElementIdViaJs(wireless1tkipandaesid); // Change for compatibility
                }

            } else if (WirelessInfo.get("security").equals("WPA2 Enterprise")) {
                if (!wireless1wpaenterprise.isSelected()) {
                    // wireless1wpaenterprise.click();
                    MyCommonAPIs.clickElementIdViaJs(wireless1wpaenterpriseid); // Change for compatibility
                }
            } else if (WirelessInfo.get("security").equals("None")) {
                if (!wireless1securitynone.isSelected()) {
                    // wireless1securitynone.click();
                    MyCommonAPIs.clickElementIdViaJs(wireless1securitynoneid); // Change for compatibility
                }
            }
            if (WirelessInfo.containsKey("password")) {
                wireless1password.setValue(WirelessInfo.get("password"));
            }
            wireless1applybutton.click();
            String dialog = Selenide.confirm();
        } else if (WirelessInfo.get("tab").equals("Wireless2")) {
            wireless2.click();
            System.out.println(wireless2enable.isSelected());
            if (!wireless2enable.isSelected()) {
                MyCommonAPIs.sleepi(10);
                // wireless2enable.click();
                MyCommonAPIs.clickElementIdViaJs(wireless2enableid); // Change for compatibility
                // wireless2enable.setSelected(true);
                // wireless2enable.setValue("1");
                // wireless2enable.sendKeys("1");
                System.out.println(wireless2enable.isSelected());
            }
            if (WirelessInfo.containsKey("ssid")) {
                wireless2ssidname.clear();
                wireless2ssidname.sendKeys(WirelessInfo.get("ssid"));
            }
            if (WirelessInfo.containsKey("g2.4name")) {
                if (!wireless2ssidseperation.isSelected()) {
                    // wireless2ssidseperation.click();
                    MyCommonAPIs.clickElementIdViaJs(wireless2ssidseperationid); // Change for compatibility
                }
                wireless2ssid24g.clear();
                wireless2ssid24g.sendKeys(WirelessInfo.get("g2.4name"));

            }

            if (WirelessInfo.containsKey("g5name")) {
                if (!wireless2ssidseperation.isSelected()) {
                    // wireless2ssidseperation.click();
                    MyCommonAPIs.clickElementIdViaJs(wireless2ssidseperationid); // Change for compatibility
                }
                wireless2ssi5g.clear();
                wireless2ssi5g.sendKeys(WirelessInfo.get("g5name"));

            }

            if (WirelessInfo.containsKey("security")) {
                if (WirelessInfo.get("security").equals("WAP2-PSK")) {
                    if (!wireless2wpa2aes.isSelected()) {
                        // wireless2wpa2aes.click();
                        MyCommonAPIs.clickElementIdViaJs(wireless2wpa2aesid); // Change for compatibility
                    }
                } else if (WirelessInfo.get("security").equals("WPA-PSK + WPA2-PSK")) {
                    if (!wireless2tkipandaes.isSelected()) {
                        // wireless2tkipandaes.click();
                        MyCommonAPIs.clickElementIdViaJs(wireless2tkipandaesid); // Change for compatibility
                    }

                } else if (WirelessInfo.get("security").equals("WPA2 Enterprise")) {
                    if (!wireless2wpaenterprise.isSelected()) {
                        // wireless2wpaenterprise.click();
                        MyCommonAPIs.clickElementIdViaJs(wireless2wpaenterpriseid); // Change for compatibility
                    }
                } else if (WirelessInfo.get("security").equals("None")) {
                    if (!wireless2securitynone.isSelected()) {
                        // wireless2securitynone.click();
                        MyCommonAPIs.clickElementIdViaJs(wireless2securitynoneid); // Change for compatibility
                    }
                }
            }

            if (WirelessInfo.containsKey("security5g")) {
                if (WirelessInfo.get("security5g").equals("WAP2-PSK")) {
                    if (!wireless2wpa2aes5g.isSelected()) {
                        // wireless2wpa2aes5g.click();
                        MyCommonAPIs.clickElementIdViaJs(wireless2wpa2aes5gid); // Change for compatibility
                    }
                } else if (WirelessInfo.get("security5g").equals("WPA-PSK + WPA2-PSK")) {
                    if (!wireless2tkipandaes5g.isSelected()) {
                        // wireless2tkipandaes5g.click();
                        MyCommonAPIs.clickElementIdViaJs(wireless2tkipandaes5gid); // Change for compatibility
                    }

                } else if (WirelessInfo.get("security5g").equals("WPA2 Enterprise")) {
                    if (!wireless2wpaenterprise5g.isSelected()) {
                        // wireless2wpaenterprise5g.click();
                        MyCommonAPIs.clickElementIdViaJs(wireless2wpaenterprise5gid); // Change for compatibility
                    }
                } else if (WirelessInfo.get("security5g").equals("None")) {
                    if (!wireless2securitynone5g.isSelected()) {
                        // wireless2securitynone5g.click();
                        MyCommonAPIs.clickElementIdViaJs(wireless2securitynone5gid); // Change for compatibility
                    }
                }
            }
            if (WirelessInfo.containsKey("password")) {
                wireless2password.setValue(WirelessInfo.get("password"));
            }
            if (WirelessInfo.containsKey("password5g")) {
                wireless2passsword5.setValue(WirelessInfo.get("password5g"));
            }
            MyCommonAPIs.sleepi(2);
            wireless2applybutton.click();
            // String dialog = Selenide.confirm();
        } else if (WirelessInfo.get("tab").equals("Wireless3")) {
            wireless3.click();
            if (!wireless3enable.isSelected()) {
                MyCommonAPIs.sleepi(10);
                // wireless3enable.click();
                MyCommonAPIs.clickElementIdViaJs(wireless3enableid); // Change for compatibility
            }
            if (WirelessInfo.containsKey("ssid")) {
                wireless3ssidname.clear();
                wireless3ssidname.sendKeys(WirelessInfo.get("ssid"));
            }
            if (WirelessInfo.containsKey("security")) {
                if (WirelessInfo.get("security").equals("WAP2-PSK")) {
                    if (!wireless3wpa2aes.isSelected()) {
                        // wireless3wpa2aes.click();
                        MyCommonAPIs.clickElementIdViaJs(wireless3wpa2aesid); // Change for compatibility
                    }
                } else if (WirelessInfo.get("security").equals("WPA-PSK + WPA2-PSK")) {
                    if (!wireless3tkipandaes.isSelected()) {
                        // wireless3tkipandaes.click();
                        MyCommonAPIs.clickElementIdViaJs(wireless3tkipandaesid); // Change for compatibility
                    }

                } else if (WirelessInfo.get("security").equals("WPA2 Enterprise")) {
                    if (!wireless3wpaenterprise.isSelected()) {
                        // wireless3wpaenterprise.click();
                        MyCommonAPIs.clickElementIdViaJs(wireless3wpaenterpriseid); // Change for compatibility
                    }
                } else if (WirelessInfo.get("security").equals("None")) {
                    if (!wireless3securitynone.isSelected()) {
                        // wireless3securitynone.click();
                        MyCommonAPIs.clickElementIdViaJs(wireless3securitynoneid); // Change for compatibility
                    }
                }
            }
            if (WirelessInfo.containsKey("g2.4name")) {
                if (!wireless3ssidseperation.isSelected()) {
                    // wireless3ssidseperation.click();
                    MyCommonAPIs.clickElementIdViaJs(wireless3ssidseperationid); // Change for compatibility
                }
                wireless3ssid24g.clear();
                wireless3ssid24g.sendKeys(WirelessInfo.get("g2.4name"));

            }
            if (WirelessInfo.containsKey("g5name")) {
                if (!wireless3ssidseperation.isSelected()) {
                    // wireless3ssidseperation.click();
                    MyCommonAPIs.clickElementIdViaJs(wireless3ssidseperationid); // Change for compatibility
                }
                wireless3ssi5g.clear();
                wireless3ssi5g.sendKeys(WirelessInfo.get("g5name"));

            }
            if (WirelessInfo.containsKey("security5g")) {
                if (WirelessInfo.get("security5g").equals("WAP2-PSK")) {
                    if (!wireless3wpa2aes5g.isSelected()) {
                        // wireless3wpa2aes5g.click();
                        MyCommonAPIs.clickElementIdViaJs(wireless3wpa2aes5gid); // Change for compatibility
                    }
                } else if (WirelessInfo.get("security5g").equals("WPA-PSK + WPA2-PSK")) {
                    if (!wireless3tkipandaes5g.isSelected()) {
                        // wireless3tkipandaes5g.click();
                        MyCommonAPIs.clickElementIdViaJs(wireless3tkipandaes5gid); // Change for compatibility
                    }

                } else if (WirelessInfo.get("security5g").equals("WPA2 Enterprise")) {
                    if (!wireless3wpaenterprise5g.isSelected()) {
                        // wireless3wpaenterprise5g.click();
                        MyCommonAPIs.clickElementIdViaJs(wireless3wpaenterprise5gid); // Change for compatibility
                    }
                } else if (WirelessInfo.get("security5g").equals("None")) {
                    if (!wireless3securitynone5g.isSelected()) {
                        // wireless3securitynone5g.click();
                        MyCommonAPIs.clickElementIdViaJs(wireless3securitynone5gid); // Change for compatibility
                    }
                }
            }
            if (WirelessInfo.containsKey("password")) {
                wireless2password.setValue(WirelessInfo.get("password"));
            }
            if (WirelessInfo.containsKey("password5g")) {
                wireless3passsword5.setValue(WirelessInfo.get("password5g"));
            }
            // MyCommonAPIs.sleepi(100);
            wireless1applybutton.click();
            // String dialog = Selenide.confirm();
        }
        int i = 0;
        while (i < 30) {
            MyCommonAPIs.sleepi(10);
            if (wirelessapplypercentage.exists()) {
                logger.info("Wait the configure apply success ....");
                System.out.println("Wait the configure apply success ....");
            } else {
                logger.info("ConfigWirelessInforWithSsidType Done");
                break;
            }
            i += 1;
        }

        if (!WebportalParam.arch.equals("new")) {
            Selenide.switchTo().parentFrame();
        }
        logger.info("ConfigWirelessInforWithSsidType Done");

    }

    public Map<String, String> GetWirelessInforChannel() {
        logger.info("GetWirelessInforChannel Start");
        if (!WebportalParam.arch.equals("new")) {
            Selenide.switchTo().frame("formframe");
        }
        Map<String, String> Channels = new HashMap<String, String>();
        System.out.println(channel24gcontent.text());
        Channels.put("g24channel", channel24gcontent.text());
        Channels.put("g5channel", channel5gcontent.getValue());
        if (channel52gcontent.exists() && channel52gcontent.isEnabled()) {
            Channels.put("g52channel", channel52gcontent.getValue());
        }
        if (!WebportalParam.arch.equals("new")) {
            Selenide.switchTo().parentFrame();
        }
        logger.info("GetWirelessInforChannel Done");
        return Channels;

    }

    public void DisableWireless(String WirelessType) {
        logger.info("DisableWireless Start");
        if (!WebportalParam.arch.equals("new")) {
            Selenide.switchTo().frame("formframe");
            if (WirelessType.equals("Wireless2")) {
                wireless2.click();
                if (wireless2enable.isSelected()) {
                    wireless2enable.click();
                }
                wireless2applybutton.click();
            } else if (WirelessType.equals("Wireless3")) {
                wireless3.click();
                if (wireless3enable.isSelected()) {
                    wireless3enable.click();
                }
                wireless2applybutton.click();
            }
            Selenide.switchTo().parentFrame();
        } else {
            if (WirelessType.equals("Wireless2")) {
                wireless2.click();
                MyCommonAPIs.sleepi(1);
                if (wireless2enable.isSelected()) {
                    MyCommonAPIs.clickElementIdViaJs(wireless2enableid);
                    MyCommonAPIs.sleepi(1);
                }
            } else if (WirelessType.equals("Wireless3")) {
                wireless3.click();
                MyCommonAPIs.sleepi(1);
                if (wireless3enable.isSelected()) {
                    MyCommonAPIs.clickElementIdViaJs(wireless3enableid);
                    MyCommonAPIs.sleepi(1);
                }
            }
        }
        wireless2applybutton.click();
        MyCommonAPIs.sleepi(120);
        logger.info("DisableWireless Done");
    }
}
