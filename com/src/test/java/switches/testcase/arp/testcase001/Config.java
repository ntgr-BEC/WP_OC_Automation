package switches.testcase.arp.testcase001;

import java.util.Arrays;
import java.util.List;

import switches.param.GlobalParam;

public interface Config {
	List<String> IFLIST_DUT= Arrays.asList(GlobalParam.A01, GlobalParam.A02);
	List<String> IFLIST_SW1= Arrays.asList(GlobalParam.B01, GlobalParam.B02);
}
