package switches.publiclibrary;

import switches.weboperation.CatlogPage;
import switches.weboperation.SwitchesLoginPage;

public class SwitchesLogin {
	public void defaultLogin() {
		SwitchesLoginPage switchesLoginPage = new SwitchesLoginPage();
		switchesLoginPage.sendLoginInfomation();
	}

	public void logout() {
		CatlogPage catlog = new CatlogPage();
		catlog.logout();
		

	}
}
