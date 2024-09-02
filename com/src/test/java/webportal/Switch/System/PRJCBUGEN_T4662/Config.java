package webportal.Switch.System.PRJCBUGEN_T4662;

import com.google.common.base.Strings;

/**
 *
 * @author zheli
 *
 */
public interface Config {
    String EXPECTED  = "Invalid password.";
    String PASSWORD1 = " ";
    String PASSWORD2 = "1Net@";
    String PASSWORD3 = Strings.repeat("1234567890", 12) + "1Net@1234";
}
