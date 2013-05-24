package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import logic.Crawler;
import logic.Parser;
import models.Item;
import models.OpenOrder;

import org.apache.commons.io.IOUtils;

import play.Logger;
import play.mvc.Controller;
import play.templates.Template;
import play.templates.TemplateLoader;

public class Application extends Controller {

	public static void index() {
		render("index.html");
	}

	public static void doku() {
		render("Application/index.html");
	}

	/**
	 * Testmethod to develop xml parser with local xml file doesnt load
	 * everytime the xml from the scsim website
	 */
	public static void parseXML() {
		OpenOrder.deleteAll();
		Template template = TemplateLoader.load("229_9_7result.xml");
		String xmlFile = template.render();

		InputStream in = IOUtils.toInputStream(xmlFile);
		Parser p = new Parser(in);
		p.parseDoc();
		List<Item> items = Item.findAll();
		renderText(items.size() + " : " + items);
	}

	/**
	 * upload latest result xml file manualy
	 * 
	 * @param file
	 */
	public static void uploadXML(File file) {
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			Parser p = new Parser(in);
			p.parseDoc();
		} catch (FileNotFoundException| NullPointerException e) {
			e.printStackTrace();
			error(1, "No file received.");
		}	
		ok();
	}

	/**
	 * http url looks like
	 * http://localhost:9000/login?username=p005&password=snake
	 * 
	 * @param username
	 * @param password
	 */
	public static void login(String username, String password, String callback) {
		Logger.info("login callback: %S", callback);
		Crawler cr = new Crawler(username, password);
		renderJSON(callback + "(" + cr.checkLogin() + ")");
	}

	/**
	 * crawling scsim.de to download the latest result xml file
	 * 
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
