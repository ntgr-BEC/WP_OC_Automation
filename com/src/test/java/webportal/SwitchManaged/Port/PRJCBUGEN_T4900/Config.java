package webportal.SwitchManaged.Port.PRJCBUGEN_T4900;

/**
 *
 * @author zheli
 *
 */
public interface Config {
    public String portDesc      = "port1_desc";
    public String STROM_Restore = "100";
    public String STROM_Set     = "50";

    public static String PORT_SPEED  = "1000 Mbps";
    public static String DUPLEX_MODE = "Full";

    public static String PORT_SPEED_RESTORE  = "Auto";
    public static String DUPLEX_MODE_RESTORE = "Auto";

    public static String PORTSPEED_CLI  = "1000 full";
    public static String FRAMESIZE_CLI  = "mtu 9198";
    public static String DUPLEXMODE_CLI = "full-duplex";
    public static String STROM_Set_CLI  = "level " + STROM_Set;
}
