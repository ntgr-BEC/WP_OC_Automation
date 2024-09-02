package businessrouter.webpageoperation;
import java.util.Map;
import java.util.logging.Logger;

import org.openqa.selenium.Keys;

import com.codeborne.selenide.Selenide;

import businessrouter.webelements.BrIPv6DhcpServerElements;
public class BrIPv6DhcpServerPage extends BrIPv6DhcpServerElements{
    final static Logger logger = Logger.getLogger("BrIPv6DhcpServerPage");
    public BrIPv6DhcpServerPage() {
        // TODO Auto-generated constructor stub
    }
    public boolean EnableDHCPServerIP(Map<String, String> IPv6DHCPServerInfo) {
        boolean Result = false;
        networklink.click();
        lansettinglink.click();
        Selenide.sleep(2000);
        if (!ipv6page.isSelected()) {
            ipv6page.click();
        }
        if(IPv6DHCPServerInfo.get("IPv6 address") != null) {
            laninterfaceipv6addr.setValue(IPv6DHCPServerInfo.get("IPv6 address"));
        }else {
            logger.info("IPv6 address is null"); 
        }
        if(IPv6DHCPServerInfo.get("IPv6 address Len") != null) {
            laninterfaceipv6addrlen.setValue(IPv6DHCPServerInfo.get("IPv6 address Len"));
        }else {
            logger.info("IPv6 address Len is null"); 
        }
        if (IPv6DHCPServerInfo.get("DHCP Status") != null) {
            logger.info("dddddddddddddddddddddddddddd1");
            logger.info(IPv6DHCPServerInfo.get("DHCP Status"));
            ipv6dhcpstatus.selectOptionByValue(IPv6DHCPServerInfo.get("DHCP Status"));
        } 
        if (IPv6DHCPServerInfo.get("DHCP Mode") != null) {
            logger.info("dddddddddddddddddddddddddddd2");
            ipv6dhcpmode.selectOptionByValue(IPv6DHCPServerInfo.get("DHCP Mode"));
        }
        if (IPv6DHCPServerInfo.get("Prefix Delegation") != null) {
            logger.info("dddddddddddddddddddddddddddd3");
            if (IPv6DHCPServerInfo.get("Prefix Delegation").contentEquals("Enable")) {
                System.out.print(ipv6prifexdelegation.isSelected());
                if (!ipv6prifexdelegation.isSelected()) {
                    ipv6prifexdelegation.click();
                }
            } else if (IPv6DHCPServerInfo.get("Prefix Delegation").contentEquals("Disable")) {
                if (ipv6prifexdelegation.isSelected()) {
                    ipv6prifexdelegation.sendKeys(Keys.SPACE);
                }
            }
            
        } 
        logger.info("dddddddddddddddddddddddddddd4");
        ipv6applybutton.click();
        Selenide.sleep(2000);
        if (laninterfaceipv6addr.getValue().contentEquals(IPv6DHCPServerInfo.get("IPv6 address"))) {
            Result = true;
        }

       
        return Result;
    }
    public void AddPrefixForPrefixDelegation(Map<String, String> DelegationPrefixInfo) {
        boolean Result = false;
        networklink.click();
        lansettinglink.click();
        Selenide.sleep(2000);
        if (!ipv6page.isSelected()) {
            ipv6page.click();
        }
        ipv6addprifexbutton.click();
        if(DelegationPrefixInfo.get("IPv6 Prefix") != null) {
            logger.info(DelegationPrefixInfo.get("IPv6 Prefix")); 
            ipv6delegationprifex.setValue(DelegationPrefixInfo.get("IPv6 Prefix"));
        }else {
            logger.info("IPv6 Prefix is null"); 
        }
        if(DelegationPrefixInfo.get("IPv6 Prefix Length") != null) {
            logger.info(DelegationPrefixInfo.get("IPv6 Prefix Length")); 
            ipv6delegationprifexlen.setValue(DelegationPrefixInfo.get("IPv6 Prefix Length"));
        }else {
            logger.info("IPv6 Prefix Length"); 
        }
        
            
        ipv6delegationprifexapply.click();
    }
    public void DeletePrefixForPrefixDelegation() {
        boolean Result = false;
        networklink.click();
        lansettinglink.click();
        Selenide.sleep(2000);
        if (!ipv6page.isSelected()) {
            ipv6page.click();
        }
        ipv6selectallprifexbutton.click();
        ipv6deleteprifexbutton.click();
    }
    public void EnableRADVD(Map<String, String> RADVDInfo) {
        boolean Result = false;
        logger.info("REnableRADVD start"); 
        networklink.click();
        lansettinglink.click();
        Selenide.sleep(2000);
        if (!ipv6page.isSelected()) {
            ipv6page.click();
        }
        radvdlink.click();
        Selenide.sleep(2000);
        if(RADVDInfo.get("RADVD Status") != null) {
            radvdstatus.selectOptionByValue(RADVDInfo.get("RADVD Status"));
        }else {
            logger.info("RADVD Status is null"); 
        }
        if(RADVDInfo.get("Advertise Mode") != null) {
            advertismode.selectOptionByValue(RADVDInfo.get("Advertise Mode"));
        }else {
            logger.info("Advertise Mode is null"); 
        }
        if(RADVDInfo.get("RA Flags") != null) {
            raflag.selectOptionByValue(RADVDInfo.get("RA Flags"));
        }else {
            logger.info("RA Flags is null"); 
        }
            
        radvdapplybutton.click();
        logger.info("REnableRADVD End"); 
    }
    public void AddPrefixForRADVD(Map<String, String> RADVDPrefixInfo) {
        logger.info("AddPrefixForRADVD start"); 
        networklink.click();
        lansettinglink.click();
        Selenide.sleep(2000);
        if (!ipv6page.isSelected()) {
            ipv6page.click();
        }
        radvdlink.click();
        radvdprifexaddbutton.click();
        if(RADVDPrefixInfo.get("IPv6 Prefix Type") != null) {
            ipv6prefixtype.selectOptionByValue(RADVDPrefixInfo.get("IPv6 Prefix Type"));
        }else {
            logger.info("RADVD Status is null"); 
        }
        if(RADVDPrefixInfo.get("IPv6 Prefix") != null) {
            ipv6prefix.setValue(RADVDPrefixInfo.get("IPv6 Prefix"));
        }else {
            logger.info("IPv6 Prefix is null"); 
        }
        if(RADVDPrefixInfo.get("IPv6 Prefix Length") != null) {
            ipv6prefixlen.setValue(RADVDPrefixInfo.get("IPv6 Prefix Length"));
        }else {
            logger.info("IPv6 Prefix Length is null"); 
        }
        if(RADVDPrefixInfo.get("Prefix Lifetime") != null) {
            logger.info(RADVDPrefixInfo.get("Prefix Lifetime"));
            Selenide.sleep(2000);
            ipv6prefixlifetime.setValue("1200");
            //ipv6prefixlifetime.sendKeys("1200");
            //ipv6prefixlifetime.clear();
            //ipv6prefixlifetime.setValue(RADVDPrefixInfo.get("IPrefix Lifetime"));
            //ipv6prefixlifetime.setValue(RADVDPrefixInfo.get("IPrefix Lifetime"));
            //ipv6prefixlifetime.sendKeys(RADVDPrefixInfo.get("IPrefix Lifetime"));
            logger.info("DDD2222222222222222222222222222222");
        }else {
            logger.info("IPrefix Lifetime is null"); 
        }
               
        ipv6prefixapplybutton.click();
        logger.info("AddPrefixForRADVD End"); 
    }
public void DeletePrefixForRADVD() {
        
    networklink.click();
    lansettinglink.click();
    Selenide.sleep(2000);
    if (!ipv6page.isSelected()) {
        ipv6page.click();
    }
    radvdlink.click();
    Selenide.sleep(2000);
    radvdprifexselectallbutton.click();
    radvdprifexdeletebutton.click();
}
        
}
