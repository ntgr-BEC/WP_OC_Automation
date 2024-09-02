package switches.param;

import util.XMLManagerForTest;

public class GlobalParam {

	public static String ConfigBy;
	public static String A01;
	public static String A02;
	public static String A03;
	public static String A04;
	public static String BrowserType;
	public static String B01;
	public static String B02;
	public static String B03;
	public static String B04;

	public GlobalParam() {
		XMLManagerForTest xmlManager = new XMLManagerForTest();
		A01 = xmlManager.getValueFromPortXml("//A01");
		A02 = xmlManager.getValueFromPortXml("//A02");
		A03 = xmlManager.getValueFromPortXml("//A03");
		ConfigBy=xmlManager.getValueFromPortXml("//ConfigBy");
		BrowserType=xmlManager.getValueFromPortXml("//BrowserType");
		B01 = xmlManager.getValueFromPortXml("//B01");
		B02 = xmlManager.getValueFromPortXml("//B02");
		B03 = xmlManager.getValueFromPortXml("//B03");
		B04 = xmlManager.getValueFromPortXml("//B04");
	}

}
