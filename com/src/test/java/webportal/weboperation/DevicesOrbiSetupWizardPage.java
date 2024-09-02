/**
 *
 */
package webportal.weboperation;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import util.MyCommonAPIs;
import webportal.param.WebportalParam;
import webportal.webelements.DeviceOrbiDay0SetupElement;

/**
 * @author bingke.xue
 *
 */
public class DevicesOrbiSetupWizardPage extends DeviceOrbiDay0SetupElement {
    Logger logger;
    String sConn = "Connected";

    /**
     *
     */
    public DevicesOrbiSetupWizardPage() {
        String pageName = this.getClass().getSimpleName();
        logger = Logger.getLogger(pageName);
        logger.info("Setup day 0 orbi device...");

    }


    //Device details_setup_router info:
    public final static Map<String, String> device_setup_router_Info = new HashMap<String, String>() {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        {
            put("Device_Mode", "Router");
            put("WifiNetworkName", WebportalParam.ob2WifiNetworkName1);
            put("WifiNetworkPwd", WebportalParam.ob2WifiNetworkPwd1);
            put("Serial_Number", WebportalParam.ob2serialNo);
        }
    };
    
    //Device details_setup_ap info:    
    public final static Map<String, String> device_setup_ap_Info = new HashMap<String, String>() {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        {
            put("Device_Mode", "AP");
            put("WifiNetworkName", WebportalParam.ob2WifiNetworkName1);
            put("WifiNetworkPwd", WebportalParam.ob2WifiNetworkPwd1);
            put("Serial_Number", WebportalParam.ob2serialNo);
        }
    };
    

    //add by bingke.xue at 2019/5/31 for add device for day 0 factory default orbi
    public void SetupOrbiDayODevice(Map<String, String> device_setup_info) {
//        new DevicesDashPage(false).gotoPage();
//        new DevicesDashPage().enterDevice(device_setup_info.get("Serial_Number"));
        DeviceOrbiDay0SetupElement day0setupelement = new DeviceOrbiDay0SetupElement();
        logger.info("click Next button on setup pending page.");
        day0setupelement.btnNext.click();//click Next button on setup pending page.
//        logger.info("click Next button on important update page.");
//        day0setupelement.btnNext.click();//click Next button on important update page.
        logger.info("Select device mode.");
        boolean deviceModerouterchecked = radioRouterModeInput.isSelected();
        logger.info("deviceModerouter checked is"+deviceModerouterchecked);
        if (device_setup_info.get("Device_Mode").equals("Router") && !deviceModerouterchecked) {
            logger.info("set device mode as router");
//            radioDeviceMode.selectRadio("0");
            radioRouterMode.click();
        }
        if (device_setup_info.get("Device_Mode").equals("AP") && deviceModerouterchecked) {
            logger.info("set device mode as AP");
            radioApMode.click();
//            radioDeviceMode.selectRadio("1");
        }
        logger.info("click Next button on setup device mode page.");
        day0setupelement.btnNext.click();//click Next button on setup device mode page.
        MyCommonAPIs.sleepi(3);
        if (device_setup_info.get("Device_Mode").equals("AP")) {
            MyCommonAPIs.sleepi(200);
        }
        
        // add by ann if no skip add satellite
        try {
            logger.info("click Skip button on add orbi pro satellites page.");
            day0setupelement.btnSkip.click();//click Skip button on add orbi pro satellites page.
        } catch(Throwable e) {
            logger.info("no Skip button for adding orbi pro satellites.");
        }
        
        logger.info("input wifinetwork name.");
        day0setupelement.inputWifiName.setValue(device_setup_info.get("WifiNetworkName")); //input wifinetwork name.
        logger.info("input wifinetwork password.");
        MyCommonAPIs.sleepi(1);
        day0setupelement.inputWifiPwd.setValue(device_setup_info.get("WifiNetworkPwd")); //input wifinetwork password.
        logger.info("click Next button on setup wifi network page.");
        MyCommonAPIs.sleepi(1);
        day0setupelement.btnNext.click();//click Next button on setup wifi network page.
        MyCommonAPIs.sleepi(3);
        logger.info("click Finish button on setup complete page.");
        day0setupelement.btnFinish.click();//click Finish button on setup complete page.        
        if (device_setup_info.get("Device_Mode").equals("AP")) {
            logger.info("sleep after finish AP mode setup.");
            MyCommonAPIs.sleepi(200);
        }
        MyCommonAPIs.sleepi(5);
    }
    
    public boolean SetupOrbiDayODeviceCheckLastMsg(Map<String, String> device_setup_info) {

      boolean result = false;
        DeviceOrbiDay0SetupElement day0setupelement = new DeviceOrbiDay0SetupElement();
      logger.info("click Next button on setup pending page.");
      day0setupelement.btnNext.click();//click Next button on setup pending page.

      logger.info("Select device mode.");
      boolean deviceModerouterchecked = radioRouterModeInput.isSelected();
      logger.info("deviceModerouter checked is"+deviceModerouterchecked);
      if (device_setup_info.get("Device_Mode").equals("Router") && !deviceModerouterchecked) {
          logger.info("set device mode as router");

          radioRouterMode.click();
      }
      if (device_setup_info.get("Device_Mode").equals("AP") && deviceModerouterchecked) {
          logger.info("set device mode as AP");
          radioApMode.click();

      }
      logger.info("click Next button on setup device mode page.");
      day0setupelement.btnNext.click();//click Next button on setup device mode page.
      MyCommonAPIs.sleepi(3);
      if (device_setup_info.get("Device_Mode").equals("AP")) {
          MyCommonAPIs.sleepi(200);
      }
      
      // add by ann if no skip add satellite
      try {
          logger.info("click Skip button on add orbi pro satellites page.");
          day0setupelement.btnSkip.click();//click Skip button on add orbi pro satellites page.
      } catch(Throwable e) {
          logger.info("no Skip button for adding orbi pro satellites.");
      }
      
      logger.info("input wifinetwork name.");
      day0setupelement.inputWifiName.setValue(device_setup_info.get("WifiNetworkName")); //input wifinetwork name.
      logger.info("input wifinetwork password.");
      MyCommonAPIs.sleepi(1);
      day0setupelement.inputWifiPwd.setValue(device_setup_info.get("WifiNetworkPwd")); //input wifinetwork password.
      logger.info("click Next button on setup wifi network page.");
      MyCommonAPIs.sleepi(1);
      day0setupelement.btnNext.click();//click Next button on setup wifi network page.
      MyCommonAPIs.sleepi(3);
      // Check msg
      if(popuplastmsg.exists()) {
          result = true;
      }
      logger.info("click Finish button on setup complete page.");
      day0setupelement.btnFinish.click();//click Finish button on setup complete page.        
      if (device_setup_info.get("Device_Mode").equals("AP")) {
          logger.info("sleep after finish AP mode setup.");
          MyCommonAPIs.sleepi(200);
      }
      MyCommonAPIs.sleepi(5);
      return result;
  }

}
