package webportal.weboperation;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import util.MailHandler;
import util.MyCommonAPIs;
import webportal.param.URLParam;
import webportal.param.WebportalParam;
import webportal.publicstep.WebCheck;
import webportal.webelements.DeviceBRVPNUsersElement;
import webportal.webelements.DeviceScreenNavigationElement;

public class DeviceScreenNavigationPage extends DeviceScreenNavigationElement {
    Logger logger = Logger.getLogger("DeviceScreenNavigationPage");
    boolean tabcaptiveportal = false;
    boolean tabWifiSchedules = false;
    boolean tabURLfiltering = false;
    boolean tabInstantWifi = false;
    boolean tabFastRoaming = false;
    boolean tabAirBridegGroups = false;
    boolean tabLoadBalancing =  false;
    boolean tabAdvancedWirelessSetting = false;
    boolean tabAdvancedNetworkSetting = false;
    boolean tabMeshSetting = false;
    boolean tabManagementLanSetting = false;
    
    
    public DeviceScreenNavigationPage() {
        logger.info("init...");
    }
    
    public boolean verifyWireless() {
        WebCheck.checkHrefIcon(URLParam.hrefWireless);
        MyCommonAPIs.sleepi(30); 
        boolean result = false;
        if(WirelessClient.isDisplayed() & WirelessTraffic.isDisplayed() & WirelessConnectedClients.isDisplayed() & WirelessDevices.isDisplayed() & WirelessClientList.isDisplayed()) {
            
            result = true;
        }
        
        return result;
    }
    
    
    public boolean WifiandCapitivePortalTab() {
        boolean tabcaptiveportal = false;
        WebCheck.checkHrefIcon(URLParam.hrefWireless);
        MyCommonAPIs.waitElement(WirelessSettingButton);
        logger.info(".Going to click Setting Button ===>.");
        WirelessSettingButton.click();
        MyCommonAPIs.waitElement(WifiandCapitivePortalTab);
        MyCommonAPIs.sleepi(8);
        if(WifiandCapitivePortalTab.isDisplayed())
        {
            WifiandCapitivePortalTab.click();
            MyCommonAPIs.waitElement(SSIDLabel);
            logger.info("captive path");
            if(SSIDLabel.isDisplayed() & StatusLabel.isDisplayed() & SecurityLabel.isDisplayed() & MacACLLabel.isDisplayed() & MacACLLabel.isDisplayed() & BandLabel.isDisplayed() & CaptivePortal.isDisplayed() & AddSSIDButton.isDisplayed() & Searchoption.isDisplayed())
            {      
                tabcaptiveportal = true;
                logger.info(".WifiandCapitivePortalTab ---> Passed.");
            }
            else
                logger.info("WifiandCapitivePortalTab Verification -----> Failed");
        }
        else
            logger.info("WifiandCapitivePortalTab Is Not Visible On UI -----> Failed");
        
        return tabcaptiveportal;
    }

    public boolean WifiSchedulesTab() {
        boolean tabWifiSchedules = false;
        // TODO Auto-generated method stub
        if(WifiSchedulesTab.isDisplayed())
        {
            WifiSchedulesTab.click();
            MyCommonAPIs.waitElement(WifiSchedulesLabel);
            MyCommonAPIs.sleepi(5);
            if(WifiSchedulesLabel.isDisplayed()  & WifiScheduleAddButton.isDisplayed() & WifiScheduleQuickViewButton.isDisplayed())
            {
                tabWifiSchedules = true;
                logger.info(".WifiSchedulesTab ---> Passed.");
            }
            else
                logger.info("WifiSchedulesTab Verification -----> Failed");
        }
        else
            logger.info("WifiSchedulesTab Is Not Visible On UI  -----> Failed");

        return tabWifiSchedules;
    }
    public boolean URLfilteringTab() {
        // TODO Auto-generated method stub
        boolean tabURLfiltering = false;
        if(URLfilteringTab.isDisplayed())
        {
            URLfilteringTab.click();
            MyCommonAPIs.waitElement(URLBlackListToggleSwitch);
            MyCommonAPIs.sleepi(5);
            if (URLBlackListToggleSwitch.isDisplayed())
            {
                tabURLfiltering = true;
                logger.info(".URLfilteringTab ---> Passed.");
            }
            else
                logger.info("URLfilteringTab Verification -----> Failed");
                
        }
        else
            logger.info("URLfilteringTab is Not visible On UI -----> Failed");
        return tabURLfiltering;
    }

    public boolean InstantWifiTab() {
        // TODO Auto-generated method stub
        boolean tabInstantWifi = false;
        if(InstantWifiTab.isDisplayed())
        {
            InstantWifiTab.click();
            MyCommonAPIs.waitElement(InstantWifiQuickkview);
            MyCommonAPIs.sleepi(5);
            if(InstantWifiQuickkview.isDisplayed())
            {
                tabInstantWifi = true;
                logger.info(".InstantWifiTab ---> Passed.");
            }
            else
                logger.info("InstantWifiTab Verification -----> Failed");
        }
        else
            logger.info("InstantWifiTab Is Not visible On -----> Failed");

        return tabInstantWifi;
    }

    public boolean FastRoamingTab() {
        // TODO Auto-generated method stub
        boolean tabFastRoaming = false;
        if(FastRoamingTab.isDisplayed())
        {
            FastRoamingTab.click();
            MyCommonAPIs.waitElement(FastRomingEnableToggleSwitch);
            MyCommonAPIs.sleepi(8);
            if(FastRomingEnableToggleSwitch.isDisplayed())
            {
                tabFastRoaming = true;
                logger.info(".FastRoamingTab ---> Passed.");
            }
            else
                logger.info("FastRoamingTab Verification -----> Failed");
        }
        else
            logger.info("FastRoamingTab is Not Visible On UI -----> Failed");

        return tabFastRoaming;
    }

    public boolean AirBridegGroupsTab() {
        // TODO Auto-generated method stub
        boolean tabAirBridegGroups = false;
        if(AirBridegGroupsTab.isDisplayed())
        {
            AirBridegGroupsTab.click();
            MyCommonAPIs.waitElement(AirBridgeNameLabel);
            MyCommonAPIs.sleepi(5);
            if(AirBridgeNameLabel.isDisplayed() & AirBridgeMasteLabel.isDisplayed() & AirBridgeSatelliteLabel.isDisplayed() & AirBridgeStatusLabel.isDisplayed() & CreateAirBirdgeGroupButton.isDisplayed() & AirBridgeQuickView.isDisplayed() & AirBridgeSearchBox.isDisplayed())
            {
                tabAirBridegGroups = true;
                logger.info(".AirBridegGroupsTab ---> Passed.");
            }
            else
                logger.info("AirBridegGroupsTab Verification -----> Failed");
        }
        else
            logger.info("AirBridegGroupsTab is Not visble on UI -----> Failed");

        return tabAirBridegGroups;
    }

    public boolean LoadBalancingTab() {
        boolean tabLoadBalancing = false;
        if(LoadBalancingTab.isDisplayed())
        {
            LoadBalancingTab.click();
            MyCommonAPIs.waitElement(LoadBalancingToggleLabel);
            MyCommonAPIs.waitElement(LoadBalancingCancelButton);
            MyCommonAPIs.sleepi(5);
            if(LoadBalancingToggleLabel.isDisplayed() & DisStickyClientsToggleLabel.isDisplayed() & LoadBalancingQuickView.isDisplayed() & LoadBalancingSaveButton.isDisplayed() & LoadBalancingCancelButton.isDisplayed())
            {
                tabLoadBalancing =true;
                logger.info(".LoadBalancingTab ---> Passed.");
            }
            else
                logger.info("LoadBalancingTab Verification -----> Failed");
        }
        else
            logger.info(".LoadBalancingTab is Not Visible on UI  ---> Failed.");

        // TODO Auto-generated method stub
        return tabLoadBalancing;
    }

    public boolean AdvancedWirelessSettingTab() {
           
        // TODO Auto-generated method stub
        boolean Ghz24 = false;
        boolean Ghz5Low = false;
        boolean Ghz5High = false;
        MyCommonAPIs.sleepi(3);
        if(AdvancedWirelessSettingTab.isDisplayed())
        {
            new WirelessQuickViewPage(false).GoToWirelessSettings();  
            MyCommonAPIs.sleepi(5);
            MyCommonAPIs.waitElement(AWS_Quickview);
            System.out.println(GHLZabel_24.isDisplayed());
            System.out.println(EnableRadioLabel_24.isDisplayed());
            System.out.println(RadioModeLabel_24.isDisplayed());
            System.out.println(RadioModeDropdown_24.isDisplayed());
            System.out.println(ChannelWidthLabel_24.isDisplayed());
            System.out.println(ChannelWidthDropdown_24.isDisplayed());
            if(AWS_Quickview.isDisplayed() & GHLZabel_24.isDisplayed() & EnableRadioLabel_24.isDisplayed() & RadioModeLabel_24.isDisplayed() & RadioModeDropdown_24.isDisplayed() & ChannelWidthLabel_24.isDisplayed() & ChannelWidthDropdown_24.isDisplayed())
            {
                logger.info("Verification done for 2.4GHz  ---> All Fields Present.");
                Ghz24 = true;
            }
//            else
//                logger.info(".AdvancedWirelessSettingTab [Unable to complete 2.4Ghz Details] ---> Failed.");
//            GhzExpandIcon5Low.click();
//            MyCommonAPIs.waitElement(EnableRadioLabel_5Low);
//            System.out.println(Ghz5LowLabel.isDisplayed());
//            System.out.println(EnableRadioLabel_5Low.isDisplayed());
//            System.out.println(EnableRadioButton_5Low.isDisplayed());
//            System.out.println(EnableRadioButton_24.isDisplayed());
//            System.out.println(RadioModeLabel_5Low.isDisplayed());
//            System.out.println(ChannelWidthLabel_5Low.isDisplayed());            
//            if(Ghz5LowLabel.isDisplayed() & EnableRadioLabel_5Low.isDisplayed() & EnableRadioButton_5Low.isDisplayed() & EnableRadioButton_24.isDisplayed() & RadioModeLabel_5Low.isDisplayed() & RadioModeDropdown_5Low.isDisplayed() & ChannelWidthLabel_5Low.isDisplayed())
//            {
//                logger.info("Verification done for 5GHz Low ---> All Fields Present.");
//                Ghz5Low = true;
//
//            }
//            else
//                logger.info(".AdvancedWirelessSettingTab [Unable to complete Ghz5Low Details] ---> Failed.");
//            GhzExpandIcon5High.click();
//            MyCommonAPIs.waitElement(GhzHighLabel_5High);
//            System.out.println(GhzHighLabel_5High.isDisplayed());            
//            System.out.println(RadioModeLabel_5High.isDisplayed());            
//            System.out.println(RadioModeDropdown_5High.isDisplayed());            
//            System.out.println(ChannelWidthDropdown_5High.isDisplayed());            
//            System.out.println(ChannelWidthLabel_5High.isDisplayed());            
//            System.out.println(CancelButton.isDisplayed());            
//            System.out.println(SaveButton.isDisplayed());            
//             if(GhzHighLabel_5High.isDisplayed() & RadioModeLabel_5High.isDisplayed() & RadioModeDropdown_5High.isDisplayed() & ChannelWidthDropdown_5High.isDisplayed() & ChannelWidthLabel_5High.isDisplayed() & CancelButton.isDisplayed() & SaveButton.isDisplayed())
//             {
//                logger.info("Verification done for 5GHz High ---> All Fields Present.");
//                Ghz5High = true;
//                logger.info(".AdvancedWirelessSettingTab ---> Passed.");
//             }
//             else
//                logger.info(".AdvancedWirelessSettingTab [Unable to complete Ghz5High Details] ---> Failed.");
//                                
//                       
        }
        else
            logger.info(".AdvancedNetworkSettingTab is Not Visible  ---> Failed.");

//        if (Ghz24 == true || Ghz5Low == true & Ghz5High == true)
        if(Ghz24 == true) 
        {
            return true;
        }
        else
            return false;
    }
    
    public boolean AdvancedNetworkSettingTab() {
        // TODO Auto-generated method stub
        boolean tabAdvancedNetworkSetting = false;
        MyCommonAPIs.sleepi(5);
        if(AdvancedNetworkSettingTab.isDisplayed())
        {
            
            new WirelessQuickViewPage(false).GoToNetworkSettings();  
            MyCommonAPIs.waitElement(ANS_QuichViewButton);
            MyCommonAPIs.sleep(5);
            System.out.println(ANS_QuichViewButton.isDisplayed());
            System.out.println(ANS_BroadCastLabel.isDisplayed());
            System.out.println(ANS_IGMPSnooping.isDisplayed());
            System.out.println(ANS_BroadcastToggleButton.isDisplayed());
            System.out.println(ANS_IGMPToggleButton.isDisplayed());
            System.out.println(ANS_CancelButton.isDisplayed());
            System.out.println(ANS_SaveButton.isDisplayed());
            if(ANS_QuichViewButton.isDisplayed() & ANS_BroadCastLabel.isDisplayed() & ANS_IGMPSnooping.isDisplayed() & ANS_BroadcastToggleButton.isDisplayed() & ANS_IGMPToggleButton.isDisplayed() & ANS_CancelButton.isDisplayed() & ANS_SaveButton.isDisplayed())
            {
                tabAdvancedNetworkSetting = true;
                logger.info(".AdvancedNetworkSettingTab ---> Passed.");
            }
            else
                logger.info(".AdvancedNetworkSettingTab ---> Failed.");
        }
        else
            logger.info(".AdvancedNetworkSettingTab is Not visible ---> Failed.");
        
        return tabAdvancedNetworkSetting;
    }

    public boolean MeshSettingTab() {
        boolean tabMeshSetting = false;
        // TODO Auto-generated method stub
        if (MeshSettingTab.isDisplayed())
        {
            new WirelessQuickViewPage(false).GoToMeshSettings();  
            MyCommonAPIs.waitElement(MS_QuicktabButton);
            MyCommonAPIs.sleepi(5);
            System.out.println(MS_QuicktabButton.isDisplayed());
            System.out.println(MeshSettingLabel.isDisplayed());
            System.out.println(MeshmodeLabel.isDisplayed());
            System.out.println(MeshModeAutoRadioButton.isDisplayed());
            System.out.println(MeshModeDisableMeshRadioButton.isDisplayed());
            System.out.println(MS_Cancel_Button.isDisplayed());

            if (MS_QuicktabButton.isDisplayed() & MeshSettingLabel.isDisplayed() & MeshmodeLabel.isDisplayed() & MeshModeAutoRadioButton.isDisplayed() & MeshModeDisableMeshRadioButton.isDisplayed() & MS_Cancel_Button.isDisplayed() & MS_Save_Button.isDisplayed())
            {
                tabMeshSetting = true;
                logger.info(".MeshSettingTab ---> Passed.");
            }
            else
                logger.info(".MeshSettingTab ---> Failed.");
        }
        else
            logger.info(".MeshSettingTab is Not visible ---> Failed.");

        return tabMeshSetting;
    }

    public boolean ManagementLanSetting() {
        // TODO Auto-generated method stub
        boolean tabManagementLanSetting = false;
        if (ManagementLanSetting.isDisplayed())
        {
            ManagementLanSetting.click();
            MyCommonAPIs.waitElement(MgVlanSettingQuickView);
            MyCommonAPIs.sleepi(5);
            System.out.println(MgVlanSettingQuickView.isDisplayed());
            System.out.println(MgVlanSettingLabel.isDisplayed());
            System.out.println(MgVlanDropdown.isDisplayed());
            System.out.println(AddCustom_VlanButton.isDisplayed());
            System.out.println(UntaggVlanLabel.isDisplayed());
            System.out.println(UntagAddCustom_VlanButton.isDisplayed());
            System.out.println(UntagVlanDropdown.isDisplayed());
            System.out.println(MgvlanCancelButton.isDisplayed());
            System.out.println(MgvlanSaveButton.isDisplayed());
            if (MgVlanSettingQuickView.isDisplayed() & MgVlanSettingLabel.isDisplayed() & MgVlanDropdown.isDisplayed() & AddCustom_VlanButton.isDisplayed() & UntaggVlanLabel.isDisplayed() & UntagAddCustom_VlanButton.isDisplayed() & UntagVlanDropdown.isDisplayed() & MgvlanCancelButton.isDisplayed() & MgvlanSaveButton.isDisplayed())
            {
                tabManagementLanSetting = true;
                logger.info(".ManagementLanSetting ---> Passed.");
            }
            else
                logger.info(".ManagementLanSetting ---> Failed.");
        }
        else
            logger.info(".MeshSettingTab is Not visible on UI ---> Failed.");

        return tabManagementLanSetting;
    }
    
    public boolean verifySummary() {
        
        MyCommonAPIs.sleepi(10); 
        WebCheck.checkHrefIcon(URLParam.hrefSummary);
        MyCommonAPIs.sleepi(5); 
        if(SummaryTab.isDisplayed()) {
            SummaryTab.click();
        }
        boolean result = false;
        System.out.println(SummaryProperties.isDisplayed());
        System.out.println(SummaryHealth.isDisplayed());
        System.out.println(SummaryWirelessClients.isDisplayed());
        System.out.println(SummaryPortUtilization.isDisplayed());
        System.out.println(SummaryNotification.isDisplayed());

        if(SummaryProperties.isDisplayed() & SummaryHealth.isDisplayed() & SummaryWirelessClients.isDisplayed() & SummaryPortUtilization.isDisplayed() & SummaryNotification.isDisplayed() 
                & SummarycaptivePortal.isDisplayed() & SummaryCF.isDisplayed() ) {
            
            result = true;
        }
        
        return result;
    }
    
    public boolean verifyRouter() {
        WebCheck.checkHrefIcon(URLParam.hrefRouters);
        MyCommonAPIs.sleepi(5); 
        boolean result = false;
        if(RoutersBVN.isDisplayed() & Routers.isDisplayed()) {
            
            result = true;
        }
        
        return result;
    }
    
    public boolean verifyMobile() {
        WebCheck.checkHrefIcon(URLParam.hrefMobile);
        MyCommonAPIs.sleepi(5); 
        boolean result = false;
        if(MobileDevices.isDisplayed() & MobileMUB.isDisplayed()) {
            
            result = true;
        }
        
        return result;
    }
    
    public boolean verifyWired() {
        WebCheck.checkHrefIcon(URLParam.hrefWiredQuickView);
        MyCommonAPIs.sleepi(5); 
        boolean result = false;
        if(WiredUsage.isDisplayed() & WiredPOEUsage.isDisplayed() &  WiredTraffic.isDisplayed() &  WiredDevices.isDisplayed()) {
            
            result = true;
        }
        
        return result;
    }
    
    public boolean verifyStorage() {
        WebCheck.checkHrefIcon(URLParam.hrefStorage);
        MyCommonAPIs.sleepi(5); 
        boolean result = false;
        if(Storageerror.isDisplayed() & StorageUsage.isDisplayed() &  StorageDevices.isDisplayed()) {
            
            result = true;
        }
        
        return result;
    }
    
    public boolean verifyFirmware() {
        WebCheck.checkHrefIcon(URLParam.hrefFirmware);
        MyCommonAPIs.sleepi(5); 
        boolean result = false;
        if(FirmwareScheduleUpdate.isDisplayed()) {
            
            result = true;
        }
        
        return result;
    }
    
    
    public boolean verifyclient() {
        WebCheck.checkHrefIcon(URLParam.hrefClients);
        MyCommonAPIs.sleepi(5); 
        boolean result = false;
        if(clientConnected.isDisplayed() & clientDisconnected.isDisplayed() & clientcaptiveportal.isDisplayed()) {
            
            result = true;
        }
        
        return result;
    }
    
    public boolean verifyTroubleshoot() {
        WebCheck.checkHrefIcon(URLParam.hrefNetworkTroubleShoot);
        MyCommonAPIs.sleepi(5); 
        boolean result = false;
        if(troubleshootPingTest.isDisplayed() & troubleshootDNSLookup.isDisplayed() & troubleshootOokla.isDisplayed() & troubleshoottracerouteTest.isDisplayed()) {
            
            result = true;
        }
        
        return result;
    }

    public boolean VLANNetworkSetupTab() {
        // TODO Auto-generated method stub
        boolean result = false;
        WiredSettingButton.click();
        MyCommonAPIs.waitElement(VLANNetworkSetupTab);
        if (VLANNetworkSetupTab.isDisplayed())
        {
            VLANNetworkSetupTab.click();
            MyCommonAPIs.waitElement(VLANNameLabel);
            System.out.println(VLANNameLabel.isDisplayed());
            System.out.println(VLANIDLabel.isDisplayed());
            System.out.println(NetworkTypeLabel.isDisplayed());
            System.out.println(SecondaryVLANTypeLabel.isDisplayed());
            System.out.println(quickViewButton.isDisplayed());
            System.out.println(AddVLANButton.isDisplayed());
            if (VLANNameLabel.isDisplayed() & VLANIDLabel.isDisplayed() & NetworkTypeLabel.isDisplayed() & SecondaryVLANTypeLabel.isDisplayed() & quickViewButton.isDisplayed() & AddVLANButton.isDisplayed())
            {
                result = true;
                logger.info(".VLANNetworkSetupTabVLANNetworkSetupTab ---> Passed.");
            }
        }
        
        return result;
    }

    public boolean GroupPortSettingTab() {
        // TODO Auto-generated method stub
        boolean result = false;
        System.out.println(GroupPortSettingsTab.isDisplayed());
        if (GroupPortSettingsTab.isDisplayed())
        {
            GroupPortSettingsTab.click();
            MyCommonAPIs.waitElement(quickViewButton);
            if (quickViewButton.isDisplayed())
            {
                result =  true;
                logger.info(".GroupPortSettingsTab ---> Passed.");
            }
            else
                logger.info(".GroupPortSettingsTab ---> Failed.");
        }
        else
            logger.info(".GroupPortSettingsTab Is Not Visible on UI ---> Failed.");

        return result;
    }

    public boolean LAGTab() {
        // TODO Auto-generated method st
        boolean result = false;
        if (LAGTab.isDisplayed())
        {
            LAGTab.click();
            MyCommonAPIs.waitElement(quickViewButton);
            System.out.println(AddLAGButton.isDisplayed());
            if (quickViewButton.isDisplayed() & AddLAGButton.isDisplayed())
            {
                result = true;
                logger.info(".LAGTab ---> Passed.");    
            }
            else
                logger.info(".LAGTab ---> Failed.");    

        }
        else
            logger.info(".LAGTab is Not Visible On UI ---> Failed.");    

        return result;
    }

    public boolean SpanningTreeTab() {
        // TODO Auto-generated method stub
        boolean result = false;
        if (SpanningTreeTab.isDisplayed())
        {
            SpanningTreeTab.click();
            MyCommonAPIs.waitElement(quickViewButton);
            if (quickViewButton.isDisplayed())
            {
                result = true;
                logger.info(".SpanningTreeTab ---> Passed.");   
            }
            else
                logger.info(".SpanningTreeTab ---> Failed.");   
        }
        else
            logger.info(".SpanningTreeTab is Not Visible on UI ---> Failed.");   
        return result;
    }

    public boolean PoEDowntimeSchedulesTab() {
        // TODO Auto-generated method stub
        boolean result = false;
        if (PoEDowntimeSchedulesTab.isDisplayed())
        {
            PoEDowntimeSchedulesTab.click();
            MyCommonAPIs.waitElement(AddPoppupwiredPoeSchButton);
            System.out.println(AddPoppupwiredPoeSchButton.isDisplayed());
            System.out.println(CreatePOEDowntimeScheduleButton.isDisplayed());
            if (AddPoppupwiredPoeSchButton.isDisplayed() & CreatePOEDowntimeScheduleButton.isDisplayed())
            {
                result = true;
                logger.info(".PoEDowntimeSchedulesTab ---> Passed.");   
            }
            else
                logger.info(".PoEDowntimeSchedulesTab ---> Failed.");   
        }
        else
            logger.info(".PoEDowntimeSchedulesTab is Not Visible On UI ---> Failed.");   
        return result;
    }

    public boolean RADIUSConfigurationTab() {
        // TODO Auto-generated method stub
        boolean result = false;
        if (RADIUSConfigurationTab.isDisplayed())
        {
            RADIUSConfigurationTab.click();
            MyCommonAPIs.waitElement(quickViewButton);
            if (quickViewButton.isDisplayed())
            {
                result = true;
                logger.info(".RADIUSConfigurationTab ---> Passed.");   
            }
            else
                logger.info(".RADIUSConfigurationTab ---> Failed.");   
        }
        else
            logger.info(".RADIUSConfigurationTab is not Visible On UI ---> Failed.");   
        return result;
    }

    public boolean LocationSettingsTab() {
        boolean result = false;
        System.out.println(LocationHamburgerButton.isDisplayed());
        if (LocationHamburgerButton.isDisplayed())
        {
            LocationHamburgerButton.click();
            MyCommonAPIs.waitElement(EditLocationButton);
            EditLocationButton.click();
            MyCommonAPIs.waitElement(LocationSettingsTab);
            System.out.println(LocationSettingsTab.isDisplayed());
            System.out.println(LocationNameLabel.isDisplayed());
            System.out.println(LocationNameField.isDisplayed());
            System.out.println(DeviceAdminPasswordLabel.isDisplayed());
            System.out.println(DeviceAdminPasswordField.isDisplayed());
            System.out.println(StreetLabel.isDisplayed());
            System.out.println(StreetField.isDisplayed());
            System.out.println(CityLabel.isDisplayed());
            System.out.println(CityField.isDisplayed());
            System.out.println(StateLabel.isDisplayed());
            System.out.println(StateField.isDisplayed());
            System.out.println(ZipcodeLabel.isDisplayed());
            System.out.println(ZipCodeField.isDisplayed());
            System.out.println(CountryLabel.isDisplayed());
            System.out.println(CountryDropdown.isDisplayed());
            System.out.println(TimeLabel.isDisplayed());
            System.out.println(TimeDropdown.isDisplayed());
            System.out.println(WirelessRegionLabel.isDisplayed());
            System.out.println(WirelessRegionDropdown.isDisplayed());
            System.out.println(LocationImageLabel.isDisplayed());
            System.out.println(LocationChooseButton.isDisplayed());
            System.out.println(LocEditCancelButton.isDisplayed());
            System.out.println(LocEditSaveButton.isDisplayed());
            if (LocationSettingsTab.isDisplayed() & LocationNameLabel.isDisplayed() & LocationNameField.isDisplayed() & DeviceAdminPasswordLabel.isDisplayed() & DeviceAdminPasswordField.isDisplayed() & StreetLabel.isDisplayed() & StreetField.isDisplayed() & CityLabel.isDisplayed() & CityField.isDisplayed() & StateLabel.isDisplayed() & StateField.isDisplayed() & ZipcodeLabel.isDisplayed() & ZipCodeField.isDisplayed() & CountryLabel.isDisplayed() & CountryDropdown.isDisplayed() & TimeLabel.isDisplayed() & TimeDropdown.isDisplayed() & WirelessRegionLabel.isDisplayed() & WirelessRegionDropdown.isDisplayed() & LocationImageLabel.isDisplayed() & LocationChooseButton.isDisplayed() & LocEditCancelButton.isDisplayed() & LocEditSaveButton.isDisplayed() )
            {
                result = true;
                logger.info(".LocationSettingsTab ---> Passed.");
            }
            else
                logger.info(".LocationSettingsTab ---> Failed.");
        }
        else
            logger.info(".LocationSettingsTab is not Visible on UI---> Passed.");

        
        // TODO Auto-generated method stub
        
        return result;
    }

    public boolean RADIUSTab() {
        // TODO Auto-generated method stub
        boolean result = false;
        if (RADIUSTab.isDisplayed())
        {
            RADIUSTab.click();
            MyCommonAPIs.waitElement(AccessAuthenticationLabel);
            System.out.println(AccessAuthenticationLabel.isDisplayed());
            System.out.println(AccessAuthenticationToggleSwitch.isDisplayed());
            System.out.println(PrimaryServerLabel.isDisplayed());
            System.out.println(AddressLabel.isDisplayed());
            System.out.println(AddressField.isDisplayed());
            System.out.println(PortNumberLabel.isDisplayed());
            System.out.println(PortNumberField.isDisplayed());
            System.out.println(SecretKeyLabel.isDisplayed());
            System.out.println(SecretKeyField.isDisplayed());
            System.out.println(SecondaryServer.isDisplayed());
            System.out.println(SecAddressLabel.isDisplayed());
            System.out.println(SecAddressField.isDisplayed());
            System.out.println(SecPortNumberLabel.isDisplayed());
            System.out.println(SecPortNumberField.isDisplayed());
            System.out.println(SecSecretKeyLabel.isDisplayed());
            System.out.println(SecSecretKeyField.isDisplayed());
            System.out.println(ReauthenticationTimeLabel.isDisplayed());
            System.out.println(AccountingLabel.isDisplayed());
            System.out.println(RadiusCancelButton.isDisplayed());
            System.out.println(RadiusSaveButton.isDisplayed());
            if (AccessAuthenticationLabel.isDisplayed() & AccessAuthenticationToggleSwitch.isDisplayed() & PrimaryServerLabel.isDisplayed() & AddressLabel.isDisplayed() & AddressField.isDisplayed() & PortNumberLabel.isDisplayed() & PortNumberField.isDisplayed() & SecretKeyLabel.isDisplayed() & SecretKeyField.isDisplayed() & SecondaryServer.isDisplayed() & SecAddressLabel.isDisplayed() & SecAddressField.isDisplayed() & SecPortNumberLabel.isDisplayed() & SecPortNumberField.isDisplayed() & SecSecretKeyLabel.isDisplayed() & SecSecretKeyField.isDisplayed() & ReauthenticationTimeLabel.isDisplayed() & AccountingLabel.isDisplayed() & RadiusCancelButton.isDisplayed() & RadiusSaveButton.isDisplayed())
            {
                result = true;
                logger.info(".RADIUSTab ---> Passed.");

            }
            else
                logger.info(".RADIUSTab ---> Failed.");
        }
        else
            logger.info(".RADIUSTab is Not Visible on UI---> Failed.");
       

        return result;
    }

    public boolean NetworkSetupTab() {
        // TODO Auto-generated method stub
        boolean result = false;
        if (NetworkSetupTab.isDisplayed())
        {
            NetworkSetupTab.click();
            MyCommonAPIs.waitElement(NS_VLANNameLAbel);
            MyCommonAPIs.sleepi(5);
            System.out.println(NS_VLANNameLAbel.isDisplayed());
            if (NS_VLANNameLAbel.isDisplayed() & NS_VLANId.isDisplayed() & NS_VLANNameLAbel.isDisplayed() & NS_VLANId.isDisplayed( ))
            {
                result = true;
                logger.info(".NetworkSetupTab ---> Passed.");

            }
            else
                logger.info(".NetworkSetupTab ---> Failed.");
        }
        else
            logger.info(".NetworkSetupTab is not visible on UI ---> Failed.");


        return result;
    }
    public boolean SyslogConfigurationTab() {
        // TODO Auto-generated method stub
        boolean result = false;
        if (SyslogConfigurationTab.isDisplayed())
        {
            SyslogConfigurationTab.click();
            MyCommonAPIs.waitElement(EnableSyslogLabel);
            System.out.println(EnableSyslogtoggleswitch.isDisplayed());
            System.out.println(SLC_CancelButton.isDisplayed());
            System.out.println(SLC_SaveButton.isDisplayed());
            if (EnableSyslogLabel.isDisplayed() & EnableSyslogtoggleswitch.isDisplayed() & SLC_CancelButton.isDisplayed() & SLC_SaveButton.isDisplayed())
            {
                result = true;
                logger.info(".SyslogConfigurationTab ---> Passed.");
            }
            else
                logger.info(".SyslogConfigurationTab ---> Failed.");
        }
        else
            logger.info(".SyslogConfigurationTab is not visible on UI ---> Failed.");


        return result;
    }

    public boolean LEDSettingsTab() {
        // TODO Auto-generated method stub
        boolean result = false;
        if (LEDSettingsTab.isDisplayed())
        {
            LEDSettingsTab.click();
            MyCommonAPIs.waitElement(LEDSettingLabel);
            if (LEDSettingLabel.isDisplayed() & LEDSettingDropdown.isDisplayed() & LEDSettingCancelButton.isDisplayed() & LEDSettingSaveButton.isDisplayed())
            {
                result = true;
                logger.info(".LEDSettingsTab ---> Passed.");
            }
            else
                logger.info(".LEDSettingsTab ---> Failed.");
        }
        else
            logger.info(".LEDSettingsTab is Not Visible on UI ---> Failed.");

        return result;
    }

    public boolean RoutingTab() {
        // TODO Auto-generated method stub
        boolean result = false;
        if (RoutingTab.isDisplayed())
        {
            RoutingTab.click();
            MyCommonAPIs.waitElement(RoutingVlanLabel);
            System.out.println(VlanNameLabel.isDisplayed());
            System.out.println(VlanIDLabel.isDisplayed());
            System.out.println(RoutingNetworkTypeLabel.isDisplayed());
            System.out.println(WirelessNetworkLabel.isDisplayed());
            System.out.println(IPAddressLabel.isDisplayed());
            System.out.println(GatewayLabel.isDisplayed());
            if (RoutingVlanLabel.isDisplayed() & VlanNameLabel.isDisplayed() & VlanIDLabel.isDisplayed() & RoutingNetworkTypeLabel.isDisplayed() & WirelessNetworkLabel.isDisplayed() & IPAddressLabel.isDisplayed() & GatewayLabel.isDisplayed())
            {
                result = true;
                logger.info(".RoutingTab ---> Passed.");
            }
            else
                logger.info(".RoutingTab ---> Failed.");
        }
        else
            logger.info(".RoutingTab is not visible on UI ---> Failed.");
        return result;
    }

    public boolean SNMPv12Tab() {
        // TODO Auto-generated method stub
        boolean result = false;
        if (SNMPv12Tab.isDisplayed())
        {
            SNMPv12Tab.click();
            MyCommonAPIs.waitElement(PageTitle);
            System.out.println(EnableSNMPTrap.isDisplayed());
            System.out.println(EnableSNMPTrapToggleSwitch.isDisplayed());
            System.out.println(SNMPIPaddressLabel.isDisplayed());
            System.out.println(SNMPIPaddressLabel.isDisplayed());
            System.out.println(SNMPIPaddressField.isDisplayed());
            System.out.println(CommunityStringLabel.isDisplayed());
            System.out.println(SNMPCancelButton.isDisplayed());
            System.out.println(SNMPClearButton.isDisplayed());
            System.out.println(SNMPSaveButton.isDisplayed());
            if (PageTitle.isDisplayed() & EnableSNMPTrap.isDisplayed() & EnableSNMPTrapToggleSwitch.isDisplayed() & SNMPIPaddressLabel.isDisplayed() & SNMPIPaddressField.isDisplayed() & CommunityStringLabel.isDisplayed() & CommunityStringField.isDisplayed() & SNMPCancelButton.isDisplayed() & SNMPClearButton.isDisplayed() & SNMPSaveButton.isDisplayed())
            {
                result = true;
                logger.info(".SNMPv12Tab ---> Passed.");
            }
            else
                logger.info(".SNMPv12Tab ---> Failed.");
        }
        else
            logger.info(".SNMPv12Tab is not visible on UI ---> Failed.");
        return result;
    }

    public boolean ConfigurationBackupRestoreTab() {
        // TODO Auto-generated method stub
        boolean result = false;
        if (ConfigurationBackupRestoreTab.isDisplayed())
        {
            ConfigurationBackupRestoreTab.click();
            MyCommonAPIs.waitElement(LocationBackupLabel);
            System.out.println(LocationBackupButton.isDisplayed());
            System.out.println(LocationBackupPlusButton.isDisplayed());
            if (LocationBackupLabel.isDisplayed() & LocationBackupButton.isDisplayed() & LocationBackupPlusButton.isDisplayed())
            {
                result = true;
                logger.info(".ConfigurationBackupRestoreTab ---> Passed.");
            }
            else
                logger.info(".ConfigurationBackupRestoreTab ---> Failed.");
        }
        else
            logger.info(".ConfigurationBackupRestoreTab is not Visible on UI ---> Failed.");
        return result;
    }
    
//   added by Anusha  
    public boolean verifyDeviceSummary() {
        MyCommonAPIs.sleepi(5); 
        boolean result = false;        
        System.out.println(ChannelUtilizationLabel.isDisplayed());
        System.out.println(ClientOSLabel .isDisplayed());
        System.out.println(DeviceDetailsLabel.isDisplayed());
        System.out.println(Label24Ghz.isDisplayed());
        System.out.println(Label1Week.isDisplayed());
        System.out.println(Label24Ghz1.isDisplayed());
        System.out.println(FirmwareVersion.isDisplayed());
        System.out.println(UpTime.isDisplayed());
        System.out.println(CountryRegion.isDisplayed());

        if(ChannelUtilizationLabel.isDisplayed() & ClientOSLabel.isDisplayed() & DeviceDetailsLabel.isDisplayed() & Label24Ghz.isDisplayed() & Label1Week.isDisplayed() 
                & Label24Ghz1.isDisplayed() & FirmwareVersion.isDisplayed()  & UpTime.isDisplayed()  & CountryRegion.isDisplayed()) {
            
            result = true;
        }
         
        return result;
    }

    public boolean verifyDeviceDetails(Map<String, String> map) {
        MyCommonAPIs.sleepi(5); 
        boolean result = false;
        String deviceName             = new DeviceScreenNavigationElement().getText(Name);
        String deviceserialNo         = new DeviceScreenNavigationElement().getText(SerialNo);
        String deviceModel            = new DeviceScreenNavigationElement().getText(Model);
        String deviceMacAddress       = new DeviceScreenNavigationElement().getText(MacAddress);
        String deviceIPAddress        = new DeviceScreenNavigationElement().getText(IPAddress);
        String deviceClients24        = new DeviceScreenNavigationElement().getText(Clients24);
        String deviceClients5         = new DeviceScreenNavigationElement().getText(Clients5);
//        String deviceClients6         = new DeviceScreenNavigationElement().getText(Clients6);
        
        if (((map.get("DeviceName").equals(deviceName) | (map.get("DeviceName").equals("null")) & (map.get("SerialNo").equals(deviceserialNo)) & (map.get("MacAddress").equals(deviceMacAddress)) & (map.get("Model").equals(deviceModel)) & (map.get("IPAddress").equals(deviceIPAddress)) & (deviceClients24=="0") & (deviceClients5=="0") ))) {
       
            result= true;   
        }
        return result;
    }
       
       public boolean verifyDeviceToplology() {
        MyCommonAPIs.sleepi(5); 
        TopologyPage.click();
        MyCommonAPIs.sleepi(2); 
        boolean result = false;       
        System.out.println(LabelISP.isDisplayed());
        System.out.println(Label3dots .isDisplayed());
        Label3dots.click();
        MyCommonAPIs.sleepi(2);
        System.out.println(Search.isDisplayed());
        System.out.println(Filter.isDisplayed());
        System.out.println(Legends.isDisplayed());
        System.out.println(Toplogyhelp.isDisplayed());
        System.out.println(Dowmloadimg.isDisplayed());

        if(LabelISP.isDisplayed() & Label3dots.isDisplayed() & Search.isDisplayed() & Filter.isDisplayed() & Legends.isDisplayed() 
                & Toplogyhelp.isDisplayed() & Dowmloadimg.isDisplayed()) {
            
            result = true;
        }
         
        return result;
    }
            
       public boolean verifyDeviceToplologyOption() {
           MyCommonAPIs.sleepi(5); 
           boolean result = false;
           TopologyPage.click();
           MyCommonAPIs.sleepi(5);  
           Label3dots.click();
           System.out.println("3 dots clicked");
           MyCommonAPIs.sleepi(5);
           Search.click();
           boolean a = Searchinside.isDisplayed();
           Filter.click();
           System.out.println(ShowClientsLabel.isDisplayed());
           Legends.click();
           boolean b = CollapsedState.isDisplayed();
           boolean c = ExpandededState.isDisplayed();
           boolean d = DeviceStatus.isDisplayed();
           boolean e = ConnectionType.isDisplayed();
           Toplogyhelp.click();
           MyCommonAPIs.sleepi(2);
           boolean f = LearnMoreButton.isDisplayed();
           boolean g = OKgotitButton.isDisplayed();
           boolean h = CloseButton.isDisplayed();
           CloseButton.click();
           MyCommonAPIs.sleepi(2);
           Dowmloadimg.click();
           MyCommonAPIs.sleepi(2);
           boolean i = DownloadimgButton.isDisplayed();
           if (a==b==c==d==e==f==g==h==i==true) { 
               result = true;
           }
            
           return result;
       }

       public boolean verifyDeviceClients() {
           MyCommonAPIs.sleepi(5); 
           ClientsPage.click();
           MyCommonAPIs.sleepi(2); 
           boolean result = false;       
           System.out.println(AllButton.isDisplayed());
           System.out.println(WiredButton .isDisplayed());
           System.out.println(WirelessButton.isDisplayed());

           if(AllButton.isDisplayed() & WiredButton.isDisplayed() & WirelessButton.isDisplayed()) {
               
               result = true;
           }
            
           return result;
       }
      
       public boolean verifyDeviceLeds() {
           WebCheck.checkHrefIcon(URLParam.hrefDeviceLeds);
           MyCommonAPIs.sleepi(5); 
           boolean result = false;       
           System.out.println(AllButton.isDisplayed());
           System.out.println(WiredButton .isDisplayed());
           System.out.println(WirelessButton.isDisplayed());

           if(AllButton.isDisplayed() & WiredButton.isDisplayed() & Search.isDisplayed() & WirelessButton.isDisplayed()) {
               
               result = true;
           }
            
           return result;
       }
    
       public boolean verifyDeviceNotifications() {
           WebCheck.checkHrefIcon(URLParam.hrefDeviceNotifications);
           MyCommonAPIs.sleepi(5); 
           boolean result = false;       
           System.out.println(AllButton.isDisplayed());
           System.out.println(WiredButton .isDisplayed());
           System.out.println(WirelessButton.isDisplayed());

           if(AllButton.isDisplayed() & WiredButton.isDisplayed() & Search.isDisplayed() & WirelessButton.isDisplayed()) {
               
               result = true;
           }
            
           return result;
       }
    
       public boolean verifyNotificationsBellIcon() {
           boolean result = false;
           MyCommonAPIs.sleepi(5);
           System.out.println(NotificationBellIcon.isDisplayed());
           if(NotificationNo.exists()) {
               NotificationBellIcon.click();
               MyCommonAPIs.sleepi(2); 
               System.out.println(NotificationNo.isDisplayed());
               System.out.println(SeeAllLabel.isDisplayed());  
               System.out.println(MarkAsAllRead.isDisplayed()); 
               MyCommonAPIs.sleepi(5); 
               MarkAsAllRead.click();
               MyCommonAPIs.sleepi(2);
               boolean a = NotificationNo.isDisplayed();
               if(a==false)
               {
                  System.out.println("All the notifications are read");  
               }
           }  

           if(NotificationBellIcon.isDisplayed()) {
               
               result = true;
           }
            
           return result;
       }   
               
       public boolean verifyPurchaseOrderHistory(String map) {
           WebCheck.checkHrefIcon(URLParam.hrefPurchaseHistory);
           MyCommonAPIs.sleepi(5); 
           boolean result = false;  
           boolean a = false;  
           a = PlusIcon.isDisplayed();
           System.out.println(QuestionMarkIcon .isDisplayed());
           System.out.println(Searchicon.isDisplayed());
           new InsightServicesPage(false).openInsightIncludedwithHardwareSection();
           MyCommonAPIs.sleepi(2);
           new WirelessQuickViewPage(false).checkApIsExist(map); 
       
           if(a==true & QuestionMarkIcon.isDisplayed() & Searchicon.isDisplayed() ) {
               
               System.out.println("The AP is shown in Purchase order history");
               result = true;
           }     
            
           return result;
       }         
          
       public boolean verifyCreditAllocation(String map) {
           WebCheck.checkHrefIcon(URLParam.hrefcreditAllocation);
           MyCommonAPIs.sleepi(5); 
           boolean result = false;       
           System.out.println(AutoAllocationLabel.isDisplayed());
           System.out.println(OrganizationName .isDisplayed());
           System.out.println(AutoAllocationToggle.isDisplayed());
           System.out.println(CreditAllocationButton.isDisplayed()); 
           System.out.println(DeallocateButton.isDisplayed()); 
           
           MyCommonAPIs.sleepi(8);
           String OrgName = OrganizationName.getText();

           if(AutoAllocationLabel.isDisplayed() & OrganizationName.isDisplayed() & AutoAllocationToggle.isDisplayed() & CreditAllocationButton.isDisplayed()  & DeallocateButton.isDisplayed() &  map.equals(OrgName)) {
               
               System.out.println("The Organisation name is shown in correct in Credit allocation page");
               result = true;
           }     
            
           return result;
       }         
      
       public boolean verifySubscriptionPage() {
           WebCheck.checkHrefIcon(URLParam.hrefSubscriptionpage);
           MyCommonAPIs.sleepi(5); 
           boolean result = false;       
           System.out.println(AddPurchaseConfKeyButton.isDisplayed());
           System.out.println(ActivateFreeTrialButton .isDisplayed()); //only if it is new account it will be present
           System.out.println(CurrentSubscriptionLabel.isDisplayed());
           
           if(AddPurchaseConfKeyButton.isDisplayed() | ActivateFreeTrialButton.isDisplayed() & CurrentSubscriptionLabel.isDisplayed()) {
               System.out.println("The elements are shown in correct in Subcription page");
               result = true;
           }     
            
           return result;
       }           
           
       public boolean verifyVPNServices() {
           WebCheck.checkHrefIcon(URLParam.hrefVPNServices);
           MyCommonAPIs.sleepi(5); 
           boolean result = false;       
           System.out.println(AddVPNButton.isDisplayed());
           System.out.println(OneIncludedFreeLabel .isDisplayed());
           System.out.println(NoOfTotalVPNCredits.isDisplayed());
           System.out.println(NoOfTotalAllocatedCredits.isDisplayed());
           System.out.println(NoOfAvailableCreditstoAllocate.isDisplayed()); 
           
           if(AddVPNButton.isDisplayed() & OneIncludedFreeLabel.isDisplayed() & NoOfTotalVPNCredits.isDisplayed() & NoOfTotalAllocatedCredits.isDisplayed() & NoOfAvailableCreditstoAllocate.isDisplayed() ) {
               System.out.println("The elements are shown in correct in VPN Services page");
               result = true;
           }     
            
           return result;
       }           
           
       public boolean verifyICPProServices() {
           WebCheck.checkHrefIcon(URLParam.hrefInstantCaptivePortalPro);
           MyCommonAPIs.sleepi(5); 
           boolean result = false;       
           System.out.println(AddICPButton.isDisplayed());
           System.out.println(NoOfTotalICPCredits .isDisplayed());
           System.out.println(NoOfTotalAllocatedCredits.isDisplayed());
           System.out.println(NoOfAvailableCreditstoAllocate.isDisplayed()); 
           
           if(AddICPButton.isDisplayed() & NoOfTotalICPCredits.isDisplayed() & NoOfTotalAllocatedCredits.isDisplayed() & NoOfAvailableCreditstoAllocate.isDisplayed()) {
               System.out.println("The elements are shown in correct in ICP page");
               result = true;
           }     
            
           return result;
       }           
           
       public boolean verifyMUBPage() {
           WebCheck.checkHrefIcon(URLParam.hrefUsageBasedBilling);
           MyCommonAPIs.sleepi(5); 
           boolean result = false;       
           System.out.println(BillingLabel.isDisplayed());
           System.out.println(UsageHistoryLabel .isDisplayed());
           System.out.println(MonthlyUsageBillingLabel.isDisplayed());
           System.out.println(CancellationPolicyPage.isDisplayed());
           System.out.println(CancellationPolicyImg.isDisplayed());
           
           if(BillingLabel.isDisplayed() & UsageHistoryLabel.isDisplayed() & MonthlyUsageBillingLabel.isDisplayed()
                   & CancellationPolicyPage.isDisplayed() & CancellationPolicyImg.isDisplayed()) {
               System.out.println("The elements are shown in correct in MUB page");
               result = true;
           }     
            
           return result;
       }           
           
       public boolean verifyManageNotifications() {
           WebCheck.checkHrefIcon(URLParam.hrefManageNotifications);
           MyCommonAPIs.sleepi(5); 
           boolean result = false;       
           System.out.println(GlobalSettingsLabel.isDisplayed());
           System.out.println(OrganizationSettingsLabel .isDisplayed());
           System.out.println(EventNotificationsLabel.isDisplayed());
           System.out.println(EnableNotificationsLabel .isDisplayed());
           System.out.println(EmailInputField.isDisplayed());
           
           if(GlobalSettingsLabel.isDisplayed() & OrganizationSettingsLabel.isDisplayed() & EventNotificationsLabel.isDisplayed()
                  & EnableNotificationsLabel.isDisplayed() & EmailInputField.isDisplayed()) {
               
               System.out.println("The elements are shown in correct in Manage Notifications page");
               result = true;
           }     
            
           return result;
       }  
       
       public boolean verifyDevicesDashboard(String password) {
           boolean result = false;       
           System.out.println(crosseye.isDisplayed());
           MyCommonAPIs.sleepi(2); 
           crosseye.click();
           MyCommonAPIs.sleepi(2); 
           password.equals(password1.getText());
           System.out.println("Admin password is right");
           MyCommonAPIs.sleepi(10);
           for(int i =0; i<5; i++) {
               if(!new WirelessQuickViewPage(false).LocatedeviceClick.exists()) {
                  System.out.println("locate device  exit");
                  break;
               }
               MyCommonAPIs.sleepi(60);
               System.out.println("locate device does not exit");
           }
           Locatedevice.click();
           MyCommonAPIs.sleepi(4); 
           Okpopup.click();
           MyCommonAPIs.sleepi(2);
           System.out.println("Got locate device pop up");
      
           if(Locatedev.isDisplayed() & Connectedlabel.isDisplayed() & LastRefreshed.isDisplayed()) {
               
               result = true;
           }     
            
           return result;
       }  
       
       public boolean verifyShareDiagnostics(String Serial) {
           ShareDiagnostics.click();
           MyCommonAPIs.sleepi(4); 
           boolean result = false;  
           String para="Send an email with diagnostics information for " + Serial +"." ;
           para.equals(Paragraph.getText());
           System.out.println(EmailSend.isDisplayed());
           System.out.println(CancelButtonInSD.isDisplayed());
           System.out.println(SendButtonInSD.isDisplayed());
           System.out.println(CrossButtonInSD.isDisplayed());
      
           if(EmailSend.isDisplayed() & CancelButtonInSD.isDisplayed() & SendButtonInSD.isDisplayed() & CrossButtonInSD.isDisplayed()) {
               
               result = true;
           }     
            
           return result;
       }  
       
       public boolean checkDevices(String Serial) {
           boolean result = false;
           WebCheck.checkHrefIcon(URLParam.hrefOrganizationDevices);
           MyCommonAPIs.sleepi(5);
           String SerialAP = String.format("//span[text()='%s']", Serial);

           logger.info("try to locate element: " + SerialAP);
           if ($x(SerialAP).exists()) {
              result = true;
              logger.info("Device with :" + SerialAP + " is found.");  
           }
           return result;
       }
       
       public boolean verifyCreditsPage() {
           WebCheck.checkHrefIcon(URLParam.hrefPurchaseHistory);
           MyCommonAPIs.sleepi(5); 
           boolean result = false;  
           System.out.println(TotaDeviceCredits.isDisplayed());
           System.out.println(InsightBundledDevices.isDisplayed());
           System.out.println(TotalICP.isDisplayed());
           System.out.println(TotalVPN.isDisplayed());
      
           if(TotaDeviceCredits.isDisplayed() & InsightBundledDevices.isDisplayed() & TotalICP.isDisplayed() & TotalVPN.isDisplayed()) {
               
               result = true;
           }     
            
           return result;
       }  
       
       public boolean verifyNeighoringAcccessPoints() {
           NAPPage.click();
           MyCommonAPIs.sleepi(15); 
           boolean result = false;  
           System.out.println(KnownAccessPoints.isDisplayed());
           System.out.println(UnknownAccessPoints.isDisplayed());
//           if(plus.exists()) {
//               plus.click(); 
//               MyCommonAPIs.sleepi(10);
//               
//           }
           System.out.println(RadioLabel.isDisplayed());
           System.out.println(DeviceLabel.isDisplayed());
           System.out.println(MACAddressLabel.isDisplayed());
           System.out.println(SsidLabel.isDisplayed());
           System.out.println(ChannelLabel.isDisplayed());
           System.out.println(RssiLabel.isDisplayed());
           System.out.println(TimestampLabel.isDisplayed());
       
           if(RadioLabel.isDisplayed() & DeviceLabel.isDisplayed() & MACAddressLabel.isDisplayed() & SsidLabel.isDisplayed() & ChannelLabel.isDisplayed() & RssiLabel.isDisplayed() & TimestampLabel.isDisplayed()) {
               
               result = true;
           }     
            
           return result;
       }  
       
       public boolean verifyTroubleShootPage(String Model) {
           TroubleShootPage.click();
           MyCommonAPIs.sleepi(6); 
           boolean result = false;  
           System.out.println(PingTest.isDisplayed());
           System.out.println(DNSLookup.isDisplayed());
           System.out.println(Traceroot.isDisplayed());
           if (!Model.equals("WAX618") || !Model.equals("WAX638E") || !Model.equals("WAX630E")) {
               System.out.println(OoklaSpeedtest.isDisplayed());
           }
       
           if(PingTest.isDisplayed() & DNSLookup.isDisplayed() & Traceroot.isDisplayed()) {
               
               result = true;
           }     
            
           return result;
       }  
       
       public boolean verifyDevicesLed() {
           LedSettings.click();
           MyCommonAPIs.sleepi(6); 
           boolean result = false;  
           System.out.println(DefaultOption.isDisplayed());
           DefaultOption.click();
           MyCommonAPIs.sleepi(3); 
           System.out.println(OffOption.isDisplayed());
           System.out.println(OffExceptPowerLed.isDisplayed());
          
       
           if(LedSettings.isDisplayed() & DefaultOption.isDisplayed()) {
               
               result = true;
           }     
            
           return result;
       }  
       
       public boolean verifyIPsettings(String Ip) {
           IPSettings.click();
           MyCommonAPIs.sleepi(6); 
           boolean result = false;  
           System.out.println(AssignIPLabel.isDisplayed());
           System.out.println(Toggle.isDisplayed());
           Toggle.click();
           MyCommonAPIs.sleepi(3); 
           System.out.println(IPaddressLabel.isDisplayed());
           System.out.println(IPAddr.isDisplayed());
           System.out.println(SubnetMaskLabel.isDisplayed());
           System.out.println(SubnetMask.isDisplayed());
           System.out.println(GatewayAddressLabel.isDisplayed());
           System.out.println(GatewayAddress.isDisplayed());
           System.out.println(PrimaryDNSLabel.isDisplayed());
           System.out.println(PrimaryDNS.isDisplayed());
           System.out.println(SecondaryDNSLabel.isDisplayed());
           System.out.println(SecondaryDNS.isDisplayed());
           
           Ip.equals(IPAddr.getText());
           String Subnet= "255.255.255.0";
           Subnet.equals(SubnetMask.getText());
           String Gateway = GatewayAddress.getText();
           Gateway.endsWith("1");
           String PrimDNS= "8.8.8.8";
           PrimDNS.equals(PrimaryDNS.getText());
           String SeconDNS= "4.2.2.1";
           SeconDNS.equals(SubnetMask.getText()); 
           
           Toggle.click();
           MyCommonAPIs.sleepi(3);
       
           if(IPaddressLabel.isDisplayed() & SubnetMaskLabel.isDisplayed() & GatewayAddressLabel.isDisplayed() & PrimaryDNSLabel.isDisplayed()  & SecondaryDNSLabel.isDisplayed() ) {
               
               result = true;
           }     
            
           return result;
       }  
       
       public boolean verifyRadioandChannel() {
           RadioandChannel.click();
           MyCommonAPIs.sleepi(6); 
           boolean result = false;  
           System.out.println(Label24ghz.isDisplayed());
           System.out.println(Label5ghz.isDisplayed());
           System.out.println(EnableRadio.isDisplayed());
           System.out.println(RadioMode.isDisplayed());
           System.out.println(Channel.isDisplayed());
           System.out.println(ChannelWidth.isDisplayed());
           System.out.println(OutputPower.isDisplayed());
           R11ng.click();
           MyCommonAPIs.sleepi(2); 
           System.out.println(R11ng.isDisplayed());
           System.out.println(R11bg.isDisplayed());
           System.out.println(R11b.isDisplayed());
           C20M.click();
           MyCommonAPIs.sleepi(2);
           System.out.println(C20M.isDisplayed());
           System.out.println(C40M.isDisplayed());
           System.out.println(D20_40.isDisplayed());
           Auto.click();
           MyCommonAPIs.sleepi(2); 
           System.out.println(Auto.isDisplayed());
           System.out.println(Full.isDisplayed());
           System.out.println(Half.isDisplayed());
           System.out.println(Quarter.isDisplayed());
           System.out.println(Eighth.isDisplayed());
           System.out.println(Minimum.isDisplayed());
       
           if(Label24ghz.isDisplayed() & Label5ghz.isDisplayed() & EnableRadio.isDisplayed() & EnableRadio.isDisplayed()  & Channel.isDisplayed()  & ChannelWidth.isDisplayed()  & OutputPower.isDisplayed()) {
               
               result = true;
           }     
            
           return result;
       } 
       
       public boolean verifyDiagnosticMode() {
           DiagnosticMode.click();
           MyCommonAPIs.sleepi(6); 
           boolean result = false;  
           System.out.println(DiagnosticModeLabel.isDisplayed()); 
           System.out.println(Para1.isDisplayed());
           System.out.println(Para2.isDisplayed());
           System.out.println(SecureDiagnosticsModeLabel.isDisplayed());
           System.out.println(ToggleInDM.isDisplayed());
           ToggleInDM.click();
           MyCommonAPIs.sleepi(2);
           OkInDMpopup.click();
           MyCommonAPIs.sleepi(2);
           System.out.println(SaveInDM.isDisplayed());
           System.out.println(PortNoLabel.isDisplayed());
           System.out.println(PortNo.isDisplayed());
           
       
           if(DiagnosticModeLabel.isDisplayed() & Para1.isDisplayed() & Para2.isDisplayed() & SecureDiagnosticsModeLabel.isDisplayed()  & ToggleInDM.isDisplayed()  & SaveInDM.isDisplayed()  & PortNoLabel.isDisplayed() & PortNo.isDisplayed()) {
               
               result = true;
           }     
            
           return result;
       }  
       
       public boolean verifyHideandShowWidgets() {
           IconFilter.click();
           MyCommonAPIs.sleepi(4); 
           boolean result = false;  
           System.out.println(HideandShowpara.isDisplayed()); 
           System.out.println(ChannelUti.isDisplayed());
           System.out.println(ClientOS.isDisplayed());
           System.out.println(SaveInIconFilter.isDisplayed());
           System.out.println("Hide the widgets");
           ChannelUti.click();
           MyCommonAPIs.sleepi(1);
           ClientOS.click();
           MyCommonAPIs.sleepi(1);
           SaveInIconFilter.click();
           MyCommonAPIs.sleepi(2);
           boolean a = !ChannelUtilizationLabel.isDisplayed();
           System.out.println("Channel utilization is hided");
           boolean b = !ClientOSLabel.isDisplayed();
           System.out.println("Client OS is hided");
           System.out.println("Show the widgets");
           IconFilter.click();
           MyCommonAPIs.sleepi(4);
           ChannelUti.click();
           MyCommonAPIs.sleepi(1);
           ClientOS.click();
           MyCommonAPIs.sleepi(1);
           SaveInIconFilter.click();
           MyCommonAPIs.sleepi(2);
           boolean c = ChannelUtilizationLabel.isDisplayed();
           System.out.println("Channel utilization is showed");
           boolean d = ClientOSLabel.isDisplayed();
           System.out.println("Cleint OS is showed");
       
           if(IconFilter.isDisplayed() & a==true & b==true & c==true  & d==true) {
               
               result = true;
           }     
            
           return result;
       }  
       
       public boolean verifyStatistics() {
           StasticsPage.click();
           MyCommonAPIs.sleepi(6); 
           boolean result = false;  
           System.out.println(SSIDLabelInSP.isDisplayed()); 
           System.out.println(ClientsLabelInSP.isDisplayed());
           System.out.println(BroadcastandMulticast.isDisplayed());
           System.out.println(Unicast.isDisplayed());
           System.out.println(BandLabelInSP.isDisplayed());
           System.out.println(TxData.isDisplayed());
           System.out.println(RxData.isDisplayed());
           System.out.println(Clearstats.isDisplayed());
           
       
           if(SSIDLabelInSP.isDisplayed() & ClientsLabelInSP.isDisplayed() & BroadcastandMulticast.isDisplayed() & Unicast.isDisplayed()  & RxData.isDisplayed()  & TxData.isDisplayed() 
                   & Clearstats.isDisplayed() ) {
               
               result = true;
           }     
            
           return result;
       }  
       
       public boolean verifyStarting() {
           MyCommonAPIs.sleepi(4); 
           boolean result = false; 
           System.out.println(Fullscreen.isDisplayed()); 
           System.out.println(Settings.isDisplayed());
           System.out.println(UserName.isDisplayed());
           System.out.println(NotificationBell.isDisplayed());
           System.out.println(Userimage.isDisplayed());
           Customize.click();
           MyCommonAPIs.sleepi(4);
           boolean a = CustomizeLabel.isDisplayed();
           boolean b = NavigationLabel.isDisplayed();
           boolean c = CloseInC.isDisplayed();
    
           if(Fullscreen.isDisplayed() & Settings.isDisplayed() & UserName.isDisplayed() & NotificationBell.isDisplayed() & Userimage.isDisplayed() & a==true & b==true & c==true )  {
               
               result = true;
           }     
            
           return result;
       }  
       
       public boolean verifyNotifications() {
           Notifications.click();
           MyCommonAPIs.sleepi(4);
           boolean result = false; 
           System.out.println(TypeLabelInN.isDisplayed()); 
           System.out.println(DetailsInN.isDisplayed());
           System.out.println(DateandTimeInN.isDisplayed());
    
           if(TypeLabelInN.isDisplayed() & DetailsInN.isDisplayed() & DateandTimeInN.isDisplayed()){
               
               result = true;
           }     
            
           return result;
       }
       
       public boolean verifyMeshTopology() {
           boolean result = false; 
           MeshTopology.click();
           MyCommonAPIs.sleepi(4);
           System.out.println(MeshSettingsLabel.isDisplayed()); 
           System.out.println(FAQ.isDisplayed());
           System.out.println(QuestionMarkInMT.isDisplayed());
           System.out.println(ListView.isDisplayed());
           System.out.println(imageInMT.isDisplayed());
           System.out.println(AdvannceSettingsInM.isDisplayed());
           QuestionMarkInMT.click();
           MyCommonAPIs.sleepi(3);
           boolean a = para1.isDisplayed();
           boolean b = para2.isDisplayed();
           boolean c = para3.isDisplayed();
           boolean d = para4.isDisplayed();
           CloseInMT.click();
           MyCommonAPIs.sleepi(2);
           AdvannceSettingsInM.click();
           MyCommonAPIs.sleepi(2);
           boolean e = AutoLabel.isDisplayed();
           boolean f = ExtenderOnlyLabel.isDisplayed();
           boolean g = RootOnlyLabel.isDisplayed();
           boolean h = DisableMeshlabel.isDisplayed(); 
           BackInMT.click();
           MyCommonAPIs.sleepi(2);
           
           if(MeshSettingsLabel.isDisplayed() & FAQ.isDisplayed() & QuestionMarkInMT.isDisplayed() & ListView.isDisplayed() & imageInMT.isDisplayed() & AdvannceSettingsInM.isDisplayed()
                   & a==true & b==true & c==true  & d==true & e==true & f==true & g==true  & h==true ){
               
               result = true;
           }     
            
           return result;
       }   
       
       public boolean verifyDashboard() {
           MyCommonAPIs.sleepi(1);
           boolean result = false; 
           System.out.println(SummaryTab.isDisplayed()); 
           System.out.println(TopologyTab.isDisplayed());
           System.out.println(RouterTab.isDisplayed());
           System.out.println(DeviceTab.isDisplayed());
           System.out.println(MobileTab.isDisplayed());
           System.out.println(WirlessTab.isDisplayed());
           System.out.println(WiredTab.isDisplayed()); 
           System.out.println(FirmwareTab.isDisplayed());
           System.out.println(ClientsTab.isDisplayed());
           System.out.println(Troubleshoot.isDisplayed());
           System.out.println(SettingsTab.isDisplayed());
           
        
           if(SummaryTab.isDisplayed() & TopologyTab.isDisplayed() & RouterTab.isDisplayed()  & DeviceTab.isDisplayed() & MobileTab.isDisplayed()  & WirelessTab.isDisplayed()
                   & WiredTab.isDisplayed() & FirmwareTab.isDisplayed() & ClientsTab.isDisplayed()  & Troubleshoot.isDisplayed() & SettingsTab.isDisplayed()){
               
               result = true;
           }     
            
           return result;
       }
       
       public boolean verifyVerticalDashboard() {
           MyCommonAPIs.sleepi(1);
           boolean result = false;
           ArrowOnLeft.click();
           MyCommonAPIs.sleepi(3);    
           System.out.println(SummartTabHor.isDisplayed()); 
           System.out.println(TopologyTabHor.isDisplayed());
           System.out.println(RouterTabHor.isDisplayed());
           System.out.println(MobileTabHor.isDisplayed());
           System.out.println(WirlessTabHor.isDisplayed()); 
           System.out.println(WiredTabHor.isDisplayed());
           System.out.println(DeviceTabHor.isDisplayed());
           System.out.println(FirmwareTabHor.isDisplayed());
           System.out.println(ClientsTabHor.isDisplayed());
           System.out.println(TroubleshootHor.isDisplayed());
           System.out.println(SettingsTarHor.isDisplayed());
   
           if(SummartTabHor.isDisplayed() & TopologyTabHor.isDisplayed() & RouterTabHor.isDisplayed()  & MobileTabHor.isDisplayed() & WirlessTabHor.isDisplayed() & WiredTabHor.isDisplayed()
                       & DeviceTabHor.isDisplayed() & FirmwareTabHor.isDisplayed() & ClientsTabHor.isDisplayed()  & TroubleshootHor.isDisplayed() & SettingsTarHor.isDisplayed()){
                   
                   result = true;
               }     
                
               return result;
           }

       public boolean verifySubVerticalDashboard() {
           MyCommonAPIs.sleepi(1);
           boolean result = false; 
           RouterTabHor.click();
           MyCommonAPIs.sleepi(4);
           SummaryInR.click();
           MyCommonAPIs.sleepi(4);
           String a1 = AllRouter.getText();
          
           All.click();
           MyCommonAPIs.sleepi(4);
           ArrowOnLeft.click();
           MyCommonAPIs.sleepi(4);
           MobileTabHor.click();
           MyCommonAPIs.sleepi(4);
           SummaryInM.click();
           MyCommonAPIs.sleepi(4);
           String a2 = AllRouter.getText();
          
           All.click();
           MyCommonAPIs.sleepi(4);
           ArrowOnLeft.click();
           MyCommonAPIs.sleepi(4);
           WirlessTabHor.click();
           MyCommonAPIs.sleepi(4);
           SummaryInWL.click();
           MyCommonAPIs.sleepi(4);
           String a3 = AllRouter.getText();
         
           All.click();
           MyCommonAPIs.sleepi(4);
           ArrowOnLeft.click();
           MyCommonAPIs.sleepi(4);
           WiredTabHor.click();
           MyCommonAPIs.sleepi(4);
           SummaryInW.click();
           MyCommonAPIs.sleepi(4);
           String a4 = AllRouter.getText();
         
           System.out.println(a1 + a2 + a3 + a4);
           
           if(a1.equals("AllRouter/VPN") & a2.equals("AllMobile") & a3.equals("AllWireless") & a4.equals("AllWired")){
               result = true;
           }    
           return result;
        
       }
       public boolean verifVerticalInnerDashboard() {
           MyCommonAPIs.sleepi(1);
           boolean result = false;
           ArrowOnLeft.click();
           MyCommonAPIs.sleepi(3); 
           RouterTabHor.click();
           MyCommonAPIs.sleepi(3);
           boolean a = SummaryInR.isDisplayed();
           boolean b = SettingsInR.isDisplayed();
           MobileTabHor.click();
           MyCommonAPIs.sleepi(3);
           boolean c = SummaryInM.isDisplayed();
           boolean d = SettingsInM.isDisplayed();
           WirlessTabHor.click();
           MyCommonAPIs.sleepi(3);
           boolean e = SummaryInWL.isDisplayed();
           boolean f = SettingsInWL.isDisplayed();
           WiredTabHor.click();
           MyCommonAPIs.sleepi(3);
           boolean g = SummaryInW.isDisplayed();
           boolean h = SettingsInW.isDisplayed(); 
          
           if( a==true & b==true & c==true  & d==true & e==true & f==true & g==true  & h==true ){
                   
                   result = true;
               }     
               return result;
           }
       
       public boolean verifySummaryQuickview() {
           MyCommonAPIs.sleepi(1);
           boolean result = false;
           ArrowOnLeft.click();
           MyCommonAPIs.sleepi(3); 
           System.out.println("Summary tab inside Router Tab");
           RouterTabHor.click();
           MyCommonAPIs.sleepi(3);
           SummaryInR.click();
           MyCommonAPIs.sleepi(5);
           String a1 = SummaryQuickview.getText();
           System.out.println("Summary tab inside Mobile Tab");
           All.click();
           MyCommonAPIs.sleepi(3);
           ArrowOnLeft.click();
           MyCommonAPIs.sleepi(3); 
           MobileTabHor.click();
           MyCommonAPIs.sleepi(3);
           SummaryInM.click();
           MyCommonAPIs.sleepi(5);
           String a2 = SummaryQuickview.getText();
           System.out.println("Summary tab inside Wirless Tab");
           All.click();
           MyCommonAPIs.sleepi(3);
           ArrowOnLeft.click();
           MyCommonAPIs.sleepi(3); 
           WirlessTabHor.click();
           MyCommonAPIs.sleepi(3);
           SummaryInWL.click();
           MyCommonAPIs.sleepi(5);
           String a3 = SummaryQuickview.getText();
           System.out.println("Summary tab inside Wired Tab");
           All.click();
           MyCommonAPIs.sleepi(3);
           ArrowOnLeft.click();
           MyCommonAPIs.sleepi(3); 
           WiredTabHor.click();
           MyCommonAPIs.sleepi(3);
           SummaryInW.click();
           MyCommonAPIs.sleepi(5);
           String a4 = SummaryQuickview.getText();
           System.out.println(a1 + a2 + a3 + a4);
          
           if (a1.equals("Summary") & a2.equals("Summary") & a3.equals("Summary") & a4.equals("Summary"))  {
               result= true;
               System.out.println(result);
              }
           return result;
}
       public boolean verifySettingsQuickview() {
           MyCommonAPIs.sleepi(1);
           boolean result = false;
           ArrowOnLeft.click();
           MyCommonAPIs.sleepi(3); 
           System.out.println("Summary tab inside Router Tab");
           RouterTabHor.click();
           MyCommonAPIs.sleepi(3);
           SettingsInR.click();
           MyCommonAPIs.sleepi(5);
           String a1 = SummaryQuickview.getText();
           System.out.println("Summary tab inside Mobile Tab");
           All.click();
           MyCommonAPIs.sleepi(3);
           ArrowOnLeft.click();
           MyCommonAPIs.sleepi(3); 
           MobileTabHor.click();
           MyCommonAPIs.sleepi(3);
           SettingsInM.click();
           MyCommonAPIs.sleepi(5);
           String a2 = SummaryQuickview.getText();
           System.out.println("Summary tab inside Wirless Tab");
           All.click();
           MyCommonAPIs.sleepi(3);
           ArrowOnLeft.click();
           MyCommonAPIs.sleepi(3); 
           WirlessTabHor.click();
           MyCommonAPIs.sleepi(3);
           SettingsInWL.click();
           MyCommonAPIs.sleepi(5);
           String a3 = SummaryQuickview.getText();
           System.out.println("Summary tab inside Wired Tab");
           All.click();
           MyCommonAPIs.sleepi(3);
           ArrowOnLeft.click();
           MyCommonAPIs.sleepi(3); 
           WiredTabHor.click();
           MyCommonAPIs.sleepi(3);
           SettingsInW.click();
           MyCommonAPIs.sleepi(5);
           String a4 = SummaryQuickview.getText();
           System.out.println(a1 + a2 + a3 + a4);
          
           if (a1.equals("Summary") & a2.equals("Summary") & a3.equals("Summary") & a4.equals("Summary"))  {
               result= true;
           }
           return result;
}
       
       public boolean verifySummaryInLoc() {
           MyCommonAPIs.sleepi(1);
           boolean result = false;
           ArrowOnLeft.click();
           MyCommonAPIs.sleepi(3); 
           System.out.println("Summary tab inside Router Tab");
           RouterTabHor.click();
           MyCommonAPIs.sleepi(3);
           SummaryInR.click();
           MyCommonAPIs.sleepi(5);
           String a1 = SummaryQuickview.getText();
           if (a1.equals("Summary")){ 
               result= true;
           }
           return result;
}
       
       public boolean verifyVerticalInsideDashboard() {
           MyCommonAPIs.sleepi(10);
           boolean result = false; 
           ArrowOnLeft1.click();
           MyCommonAPIs.sleepi(3); 
   
           String a1 = AllWirelessWifi.getText();
           System.out.println(a1);
           
           if (a1.equals("AllWirelessWiFi")){            
               result= true;
           }
           return result;
          
        
}
}


