package webportal.SwitchManaged.Port.PRJCBUGEN_T4910;

/**
 *
 * @author zheli
 *
 */
public interface Config {
    public String portDesc      = "port1_desc";
    public String STROM_Restore = "100";
    public String STROM_Set     = "50";

    public static String PORT_SPEED   = "1000 Mbps";
    public static String PORT_SPEEDSM   = "100 Mbps";
    public static String DUPLEX_MODE1 = "Full";
    public static String DUPLEX_MODE2 = "Half";

    public static String PORT_SPEED_RESTORE  = "Auto";
    public static String DUPLEX_MODE_RESTORE = "Auto";

    public static String PORTSPEED_CLI   = "speed 1000";
    public static String DUPLEXMODE1_CLI = "full-duplex";
    public static String DUPLEXMODE2_CLI = "half-duplex";
}
