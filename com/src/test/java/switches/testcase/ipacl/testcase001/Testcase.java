package switches.testcase.ipacl.testcase001;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import switches.param.GlobalParam;
import switches.param.SW1;
import switches.publiclibrary.SwitchesLogin;
import switches.weboperation.SwitchingPortsPortconfiguration;
import testbase.TestCaseBase;
import util.AllureReporterListener;
import util.Javasocket;
import util.RunCommand;

/**
 * 测试用例，从测试基类继承过来，测试基类有BeforeClass和AfterClass。未来用来指定测试浏览器，收集测试结果存入数据库
 * 
 * @author Administrator
 *
 */
@Listeners(AllureReporterListener.class)
public class Testcase extends TestCaseBase {
	Javasocket javasocket = new Javasocket();
	@Issue("ALR-123")
	@Feature("IP ACL")
	@Severity(SeverityLevel.CRITICAL)
	@Story("IP ACL-11111111111")
	@Test(alwaysRun=true)
	public void test() {
//		step1();
		step2();
		// 从TCL读取测试结果
		micResult = javasocket.readResultFromTclShell();
		// 用TestNG的assertTrue验证MicResult，它能标记这个case是否是正确的
		assertTrue(micResult);
	}
	@Step("step1,running dut")
	public void step1() {
	
		// 在DUT上执行脚本
		SW1 sw1 = new SW1();
		// 调用 公共方法开始登陆
		SwitchesLogin switchesLogin=new SwitchesLogin();
		
		switchesLogin.defaultLogin();
		// 进入 Switching Ports Portconfiguration 页面
		SwitchingPortsPortconfiguration switchingPortsPortconfiguration = new SwitchingPortsPortconfiguration();
		// linkup A01 端口
		switchingPortsPortconfiguration.setPortConfiguration(GlobalParam.A01);
		// 调用公共方法注销登陆
		switchesLogin.logout();
	}
	@Step("step2,running sw1")
	public void step2() {
		String tclRootPath = System.getProperty("user.dir").replace("\\", "/").replace("com", "")+"TCL_ROOT";
		String automationPath=System.getProperty("user.dir").replace("\\", "/").replace("com", "");
		System.out.println(tclRootPath);
		
		String tclname = this.getClass().getName();
		System.out.println(tclname);
		// 开始打流
		RunCommand command = new RunCommand();
		command.runTclShell(tclname);
		// 开始JAVA和TCL交互
		
		javasocket.wakeUpTclShell();
		javasocket.waitForTclShell();
		// 在SW1上执行脚本
		SW1 dut2 = new SW1();
		SwitchesLogin switchesLogin=new SwitchesLogin();
		switchesLogin.defaultLogin();		
		// 向TCL传递参数
		javasocket.sendParamToTclShell("portA", GlobalParam.A01);
		switchesLogin.logout();
	}
	
}
