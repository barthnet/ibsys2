package controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import logic.Crawler;
import logic.Parser;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import play.Logger;
import play.mvc.Controller;
import play.templates.Template;
import play.templates.TemplateLoader;

public class Application extends Controller {

	public static void index() {
		render();
	}
	
	public static void site() {
		render("test.html");
	}
	
	/**
	 * Testmethod to develop xml parser with local xml file
	 * doesnt load everytime the xml from the scsim website
	 */
	public static void parseXML() {
		Template template = TemplateLoader.load("229_9_7result.xml");
		String xmlFile = template.render();
		renderText(xmlFile);
	}

	/**
	 * upload latest result xml file manualy
	 * @param file
	 */
	public static void uploadXML(File file) {
		Document doc = null;
		try {
			doc = Parser.parseFromXml(file);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Parser.parseDoc(doc);
		renderText("");
	}

	/**
	 * http url looks like http://localhost:9000/login?username=p005&password=snake 
	 * @param username
	 * @param password
	 */
	public static void login(String username, String password, String callback) {
		// renderText("GET /login with params: username: " + username +
		// ", password: " + password);
		// renderText(Crawler.checkLogin(username, password));
		Logger.info("login callback: %S", callback);
		Crawler cr = new Crawler(username, password);
		// renderText(cr.checkLogin());
		// renderJSON(cr.checkLogin());
		// String test = "true) {console.log(\"schadcode\");} if (re";
		renderJSON(callback + "(" + cr.checkLogin() + ")");
		// renderJSON(callback+"("+test+")");

	}

	/**	
	 * crawling scsim.de to download the latest result xml file
	 * @param username
	 * @param password
	 */
	public static void loadXmlFromSite(String username, String password) {
		Crawler cr = new Crawler(username, password);
		String file = cr.importFileFromWeb();
		Logger.info("file:\n%s", file);
		renderText(file);
	}

}
