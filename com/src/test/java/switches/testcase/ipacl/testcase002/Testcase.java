package switches.testcase.ipacl.testcase002;

import org.testng.annotations.Test;

import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
/**
 * 测试用例，从测试基类继承过来，测试基类有BeforeClass和AfterClass。未来用来指定测试浏览器，收集测试结果存入数据库
 * @author Administrator
 *
 */
public class Testcase{
	
	@Issue("ALR-123")
	@Feature("IP ACL")
	@Story("IP ACL-2222")
	@Severity(SeverityLevel.CRITICAL)
	@TmsLink("case-11")
	@Test(alwaysRun=true)
	public void test() {
		String tclname=this.getClass().getName();
		System.out.println(tclname);
		//在DUT上执行脚本
//		DUT1 dut = new DUT1();
//		//调用 公共方法开始登陆
//		Login.login();
//		//进入 Switching Ports Portconfiguration 页面
//		SwitchingPortsPortconfiguration switchingPortsPortconfiguration = new SwitchingPortsPortconfiguration();
//		//linkup A01 端口
//		switchingPortsPortconfiguration.setPortConfiguration(GlobalParam.A01);
//		//调用公共方法注销登陆
//		Login.logout();
		// 开始打流
	}
}
