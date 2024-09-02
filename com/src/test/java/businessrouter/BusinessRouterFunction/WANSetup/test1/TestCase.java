package businessrouter.BusinessRouterFunction.WANSetup.test1;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

//import webportal.weboperation.DevicesDashPage;
import businessrouter.webpageoperation.BrLoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import testbase.TestCaseBase;
import util.Javasocket;
import util.RunCommand;
import webportal.param.WebportalParam;

public class TestCase extends TestCaseBase {
	String                       tclname = getClass().getName();
	String                       tmpStr;

	public String sTestStr = "test1";
	public String sCurrentValue;
	public String sExpectedtValue;
	public String sOldSW   = WebportalParam.sw1IPaddress;
    
	    @Feature("SwitchFunctionality.POE") // 必须要加，对应目录名
	    @Story("PRJCBUGEN_T5016") // 对应testrunkey
	    @Description("019-Create 1 new time schedule,recurrence is Weekly") // 对应用例名字
	    @TmsLink("492792") // 对应用例详情页的URL最后几位数字

	    @Test(alwaysRun = true, groups = "p2") // 标记测试用例
	    public void test() throws Exception {
	        runTest(this);
	    }

	    @AfterMethod(alwaysRun = true)
	    public void tearDown() {
	        System.out.println("start to do tearDown");
	        wpsp.deleteAll();
	        WebportalParam.sw1IPaddress = sOldSW;
	    }

	    // step对应jira测试用例里面的步骤，括号里的是描述
	  @Step("Test Step 1: Open Device")
	  public void step1() {
		  BrLoginPage BrLoginPage = new BrLoginPage();
		  BrLoginPage.defaultLogin(); 
	  }
	   // step对应jira测试用例里面的步骤，括号里的是描述
		  
	 @Step("Test Step 2: Open Device")
	 public void step2() {
		   Javasocket javasocket = new Javasocket();
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
			//SW1 dut2 = new SW1();
			//SwitchesLogin switchesLogin=new SwitchesLogin();
			//switchesLogin.defaultLogin();		
			// 向TCL传递参数
			//javasocket.sendParamToTclShell("portA", GlobalParam.A01);
			//switchesLogin.logout();
		}
}
