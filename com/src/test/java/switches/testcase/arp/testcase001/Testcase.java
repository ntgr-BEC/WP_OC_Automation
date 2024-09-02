package switches.testcase.arp.testcase001;

import org.testng.annotations.Test;

import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import switches.param.SW1;
import switches.publiclibrary.SwitchesLogin;
import switches.weboperation.SwitchingPortsPortconfiguration;
import testbase.TestCaseBase;
import util.RunCommand;

/**
 * 测试用例，从测试基类继承过来，测试基类有BeforeClass和AfterClass。未来用来指定测试浏览器，收集测试结果存入数据库
 * 
 * @author Administrator
 *
 */

public class Testcase extends TestCaseBase implements Config{
	String tclname = getClass().getName();
	@Feature("ARP")
	@Story("ARP-11111111111")
	@Severity(SeverityLevel.CRITICAL)
	@TmsLink("arp111")
	@Test(alwaysRun=true)
	public void test() {
		String tclname = this.getClass().getName();
		System.out.println(tclname);
		// 开始打流
		RunCommand command = new RunCommand();
		command.runTclShell(tclname);
		
		// 在SW1上执行脚本
		SW1 sw1 = new SW1();

		SwitchesLogin switchesLogin=new SwitchesLogin();
		switchesLogin.defaultLogin();
		step1();
	}
	@Step
	public void step1() {
		SwitchingPortsPortconfiguration switchingPortsPortconfiguration=new SwitchingPortsPortconfiguration();
		switchingPortsPortconfiguration.linkUpPort(IFLIST_SW1);
		SwitchesLogin switchesLogin=new SwitchesLogin();
		switchesLogin.logout();		
	}
}
