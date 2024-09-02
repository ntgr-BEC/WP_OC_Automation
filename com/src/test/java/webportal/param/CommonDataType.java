package webportal.param;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class CommonDataType {
    final static Logger logger    = Logger.getLogger("CommonDataType");
    public String       sMonthAll = "January,February,March,April,May,June,July,August,September,October,November,December";

    public void printMethod(Object obj) {
        @SuppressWarnings("rawtypes")
        Class cz = obj.getClass();
        System.out.println(cz.getName());
        for (java.lang.reflect.Field f : cz.getFields()) {
            try {
                Object value = f.get(obj);
                System.out.printf("\t <%s> --> <%s>\n", f.getName(), value);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public IPSecVPNData dataIPSec = new IPSecVPNData();

    public class IPSecVPNData {
        public String  policyName      = "ipsecname";
        public boolean policyStatus    = true;
        public String  mode            = "Net-to-Net";
        public String  remoteGw        = "192.168.160.1";
        public String  remoteLannet    = "192.168.160.0";
        public String  remoteLanmask   = "255.255.255.0";
        public String  localLannet     = "192.168.170.0";
        public String  localLanmask    = "255.255.255.0";
        public String  preShardkey     = "Netgear1@";
        public int     ikeVer          = 0;
        public String  phase1Proposal1 = "md5-3des-dh1";
        public String  phase1Proposal2 = null;
        public String  phase1Proposal3 = null;
        public String  phase1Proposal4 = null;
        public int     exchangeMode    = 0;
        public int     negotiationMode = 0;
        public int     ikeLifetime     = 600;
        public int     dpd             = 1;
        public String  phase2Proposal1 = "esp-sha1-aes256";
        public String  phase2Proposal2 = null;
        public String  phase2Proposal3 = null;
        public String  phase2Proposal4 = null;
        public String  phase2PFS       = "no";
        public int     saLifetime      = 600;

        public void Dump() {
            printMethod(this);
        }
    }

    public ManulAccessManagment dataMAM = new ManulAccessManagment();

    public class ManulAccessManagment {
        public String  devName    = "devicename";
        public String  devIp      = "";
        public String  devMask    = "";
        public String  devMac     = "";
        public boolean enableFrom = true;
        public boolean enableTo   = true;

        public void Dump() {
            printMethod(this);
        }
    }

    public CustomAccessManagment dataCAM = new CustomAccessManagment();

    public class CustomAccessManagment {
        public String  from       = "Mannual";
        public String  to         = "Mannual";
        public String  fromname   = "testfrom";
        public String  toname     = "testto";
        public String  fromip     = "";
        public String  toip       = "";
        public String  frommac    = "";
        public String  tomac      = "";
        public String  fromipmask = "";
        public String  toipmask   = "";
        public boolean enableAdd1 = false;
        public boolean enableAdd2 = false;

        public void Dump() {
            printMethod(this);
        }
    }

    public EditLagData dataLE = new EditLagData();

    public class EditLagData {
        public String  name      = "testlag";
        public boolean bEnable   = true;
        public boolean bStatic   = true;
        public boolean bPortLag1 = true;
        public boolean bPortLag2 = true;

        public void Dump() {
            printMethod(this);
        }
    }

    public TimeData tdTimeData = new TimeData();

    public class TimeData {
        public int     hour   = Calendar.getInstance().get(Calendar.HOUR);
        public int     minute = Calendar.getInstance().get(Calendar.MINUTE);
        public boolean isam   = Calendar.getInstance().get(Calendar.AM_PM) == 0 ? true : false;

        /**
         * Set exact time for hour/minute/a-pm
         */
        public void setTime() {
            printMethod(this);
            String tp = ".customtimepicker";
            String tpHour = String.format("%s li:nth-child(1) div:nth-child(3)", tp);
            String tpHourDown = String.format("%s li:nth-child(1) div:nth-child(2)", tp);
            String tpMintue = String.format("%s li:nth-child(2) div:nth-child(3)", tp);
            String tpMintueDown = String.format("%s li:nth-child(2) div:nth-child(2)", tp);
            String tpAMPM = String.format("%s select", tp);
            String tpOK = String.format("%s button", tp);
            int curVal;
            // set hour
            if (hour == 0) {
                hour = 12;
            }
            for (int i = 0; i < 25; i++) {
                curVal = Integer.parseInt($(tpHour).getText());
                logger.info(String.format("set hour with: %s-%s", hour, curVal));
                if (curVal != hour) {
                    $(tpHourDown + " i").click();
                    continue;
                }
                break;
            }
            // set minute
            for (int i = 0; i < 60; i++) {
                curVal = Integer.parseInt($(tpMintue).getText());
                logger.info(String.format("settime minute with: %s-%s", minute, curVal));
                if (curVal != minute) {
                    $(tpMintueDown + " i").click();
                    continue;
                }
                break;
            }
            // set am/pm
            if (isam) {
                $(tpAMPM).selectOption(1);
            } else {
                $(tpAMPM).selectOption(0);
            }

            $(tpOK).click();
        }
    }

    public DateData ddDateData = new DateData();

    public class DateData {
        public int     year    = Calendar.getInstance().get(Calendar.YEAR);
        public String  month   = String.format("%d", Calendar.getInstance().get(Calendar.MONTH) + 1);
        public int     day     = Calendar.getInstance().get(Calendar.DATE);
        public boolean isNewUI = false;

        // public UI
        public String dp      = "ui-datepicker-div";
        public String dpADay  = "//*[@id=\"%s\"]//a[text()=\"%s\"]";
        String        dpYear  = String.format("#%s .ui-datepicker-year", dp);
        String        dpMonth = String.format("#%s .ui-datepicker-month", dp);
        String        dpPrev  = String.format("#%s a[data-handler*=prev]", dp);
        String        dpNext  = String.format("#%s a[data-handler*=next]", dp);

        // old UI
        public String dp0      = "ui-datepicker-div";
        public String dpADay0  = "//*[@id=\"%s\"]//a[text()=\"%s\"]";
        String        dpYear0  = String.format("#%s .ui-datepicker-year", dp0);
        String        dpMonth0 = String.format("#%s .ui-datepicker-month", dp0);
        String        dpPrev0  = String.format("#%s a[data-handler*=prev]", dp0);
        String        dpNext0  = String.format("#%s a[data-handler*=next]", dp0);

        // new UI
        public String dp1         = "react-datepicker";
        public String dpADay1     = "//*[@class=\"%s\"]//div[text()=\"%s\"]";
        String        dpYear1     = String.format(".%s .ui-datepicker-year", dp1);
        String        dpMonth1    = String.format(".%s .ui-datepicker-month", dp1);
        String        dpPrev1     = String.format(".%s [class*=prev]", dp1);
        String        dpNext1     = String.format(".%s [class*=next]", dp1);
        String        dpMonthYear = String.format(".%s [class*=current-month]", dp1);

        private void updateUI() {
            String sDay = String.format(dpADay1, dp1, day);
            if ($x(sDay).exists()) {
                logger.info("is new ui");
                isNewUI = true;
                dp = dp1;
                dpADay = dpADay1;
                dpYear = dpYear1;
                dpMonth = dpMonth1;
                dpPrev = dpPrev1;
                dpNext = dpNext1;
            } else {
                logger.info("is old ui");
                isNewUI = false;
                dp = dp0;
                dpADay = dpADay0;
                dpYear = dpYear0;
                dpMonth = dpMonth0;
                dpPrev = dpPrev0;
                dpNext = dpNext0;
            }
        }

        /**
         * @param  isMonth
         *                 true to get month, false to get year
         * @return
         */
        public String getMonthYear(boolean isMonth) {
            logger.info("isMonth: " + isMonth);
            if (isNewUI) {
                if (isMonth)
                    return $(dpMonthYear).getText().split(" ")[0];
                else
                    return $(dpMonthYear).getText().split(" ")[1];
            } else {
                if (isMonth)
                    return $(dpMonth).getText();
                else
                    return $(dpYear).getText();
            }
        }

        /**
         * @param isToday
         *                true to select today of current month, false to select day 28 of next month
         */
        public void setDate(boolean isToday) {
            logger.info("isToday: " + isToday);
            printMethod(this);
            updateUI();
            String dpDay = String.format(dpADay, dp, 28);
            if (!isToday) {
                $(dpNext).click();
            } else {
                dpDay = String.format(dpADay, dp, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            }

            $$x(dpDay).last().click();
        }

        /**
         * @param month
         *              select how many months far away today
         */
        public void setDate(int month) {
            logger.info("month: " + month);
            printMethod(this);
            updateUI();
            String dpDay = String.format(dpADay, dp, 1);
            for (int i = 0; i < month; i++) {
                $(dpNext).click();
            }
            $x(dpDay).click();
        }

        /**
         * The exact year/month/day
         */
        public void setDate() {
            printMethod(this);
            // make year is right
            updateUI();
            for (int i = 0; i < 30; i++) {
                int curYear = Integer.parseInt(getMonthYear(false));
                logger.info(String.format("Set year to: %s from: %s", year, curYear));
                if (curYear < year) {
                    $(dpNext).click();
                    continue;
                }
                if (curYear > year) {
                    $(dpPrev).click();
                    continue;
                }
                break;
            }

            // make month is right
            List<String> lm = Arrays.asList(sMonthAll.split(","));
            for (int i = 0; i < 12; i++) {
                String s = getMonthYear(true);
                int tomonth = Integer.parseInt(month) - 1; // 0-base
                logger.info(String.format("Set month to: %s from: %s", tomonth, s));
                if (lm.indexOf(s) < tomonth) {
                    $(dpNext).click();
                    continue;
                }
                if (lm.indexOf(s) > tomonth) {
                    $(dpPrev).click();
                    continue;
                }
                break;
            }

            // select day
            String dpDay = String.format(dpADay, dp, day);
            $x(dpDay).click();
        }
    }

    // add by bingke.xue at 2019/5/29

    public Map<String, String> LOCATION_INFO = new HashMap<String, String>() {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        {
            put("Location Name", WebportalParam.location1);
            put("Device Admin Password", "Netgear1@");
            put("Street", "Street");
            put("City", "City");
            put("State", "State");
            put("Zip Code", "412200");
            put("Country", "China");
            put("Time Zone", "UTC+08:00 (Asia/Shanghai)");
        }
    };

    public Map<String, String> PAYMENT_INFO = new HashMap<String, String>() {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        {
            put("Street Address", "Street 4568 James Avenue");
            put("City", "INVERNESS");
            put("Zip", "32003");
            put("Country", "US");
            put("State", "Florida");
            put("Card Number", "4142621111111112");
            put("CVV Number", "123");
            put("Expiration Month", "May");
            put("Expiration Year", "2030");
        }
    };
    
    public SSIDData dataSSID = new SSIDData();

    public class SSIDData {
        public String  ssid                    = "wp-ssid-test";
        public boolean Broadcast               = true;
        public int     Band                    = 0;
        public boolean BandSteering802dot11k   = true;
        public int     SecurityOption          = 0;
        public String  Security                = "WPA2 Personal";
        public String  Password                = "Netgear1@";
        public boolean PMF802dot11w            = true;
        public boolean WirelessClientIsolation = true;
        public boolean vlanEnable              = false;
        public String  vlanName                = null;
        public String  vlanId                  = null;
        public boolean EnableSSIDSchedule      = false;
        public String  ScheduleName            = "Schedule Name";
        public String  Days                    = null;
        
        public boolean EnableMACAccessControl = false;
        public boolean EnableCaptivePortal    = false;
        public boolean EnableRateLimiting     = false;
        
        // for sxr80
        public boolean enableSsid        = false;
        public boolean enaSsidSeparation = false;
        /**
         * 0-none,1-WPA2-PSK,2-WPA-PSK/WPA2-PSK,3-WPA2-Enterprise,4-WPA3-Personal
         */
        public int     ssidSecurity      = 1;
        public boolean enaSsidVlan       = false;
        public String  ssidVlan          = "Employee";
        public boolean enaWifiSchedule   = false;
        public String  wifiSchedule      = "";
        public boolean enableCP          = false;
        public boolean enableRedirectURL = false;
        public String  RedirectURL       = "";
        public int     sessionTimeout    = 2;
        public String  CPLogoFilename    = "";

        public void Dump() {
            printMethod(this);
        }
    }

    public OrbiNetworkInfoData dataOrbiNetworkInfo = new OrbiNetworkInfoData();

    public class OrbiNetworkInfoData {
        public String  vlanName         = null;
        public String  vlanId           = "";
        public boolean ClientIsolation  = true;
        public boolean NetworkIsolation = true;
        public boolean setPort          = false;
        public String  portList         = "";
        public int     portMode         = 0;
        public String  DhcpIp           = "";
        public boolean DhcpMode         = false;
        public String  DhcpIpStart      = "";
        public String  DhcpIpEnd        = "";
        
        public void Dump() {
            printMethod(this);
        }
    }
    
    
    public Map<String, String> CARD_INFO = new HashMap<String, String>() {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        {
            put("Card Number", "4142621111111112");
            put("CVV Number", "123");
            put("Expiration Month", "May");
            put("Expiration Year", "2030");
        }
    };
    
}
