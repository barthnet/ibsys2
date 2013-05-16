package controllers;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import logic.Parser;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import play.mvc.Controller;
import utils.StringUtils;

public class Application extends Controller {

	public static void index() {
		render();
	}

	public static void test() {
		
		renderText("Hello World");
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
	
	public static void loginFinal() {		
		renderText(StringUtils.stringify(request.body));
	}
	
	public static void login() {
		renderText("true");
	}

}