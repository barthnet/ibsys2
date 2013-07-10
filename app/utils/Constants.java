package utils;

import java.net.URI;

import play.test.Fixtures;
import sun.security.jca.GetInstance.Instance;

/**
 * 
 * @author mopa
 *
 */
public class Constants {

	/**
	 * Constants for ScSim Web Crawler
	 */
	public String baseUrl = "http://www.iwi.hs-karlsruhe.de/scs/";
	public String startPage = baseUrl + "result/resultinfo.jsp";
	public String authUrl = baseUrl + "result/j_security_check?j_username=";
	public String xmlFile = baseUrl + "result/output.jsp?financial=false&lang=DE";
	public String simulate = baseUrl + "simulate";
	public String redirect = baseUrl + "/result/index.jsp";
	
	private static Constants instance = new Constants();
	
	public static Constants getInstance() {
		if (instance == null) {
			instance = new Constants();
		}
		return instance;
	}
	
	private Constants() {
		ConstantSettings settings = Fixtures.loadYaml("settings.yml", ConstantSettings.class);
		if (settings != null) {
			this.baseUrl = settings.baseUrl;
		}
	}
	
	public class ConstantSettings {
		public String baseUrl;
	}
	
}
