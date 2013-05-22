package controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import logic.Crawler;
import logic.Parser;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import play.Logger;
import play.mvc.Controller;
import utils.StringUtils;

public class Application extends Controller {

	public static void index() {
		render();
	}

	/**
	 * receives XML File
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
	 * http url looks like
	 * http://localhost:9000/login?username=test&password=testetst
	 * 
	 * @param username
	 * @param password
	 */
	public static void login(String username, String password, String callback) {
		// renderText("GET /login with params: username: " + username +
		// ", password: " + password);
		// renderText(Crawler.checkLogin(username, password));
		Logger.info("login callback: %S", callback);
		Crawler cr = new Crawler(username, password);
//		renderText(cr.checkLogin());
//		renderJSON(cr.checkLogin());
		renderJSON(callback+"("+cr.checkLogin()+")");
	}

	public static void loadXmlFromSite(String username, String password) {
		// Crawler.loadXML(username, password);
		Crawler cr = new Crawler(username, password);
		InputStream fileStream = cr.importFileFromWeb();
		
		
		String file = StringUtils.toString(fileStream);
		Logger.info("file:\n%s", file);
		renderText(file);
	}

}
