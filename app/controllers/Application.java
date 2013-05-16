package controllers;

import play.*;
import play.data.Upload;
import play.mvc.*;
import utils.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import logic.Parser;
import models.*;

public class Application extends Controller {

	public static void index() {
		render();
	}

	public static void test() {
		
		Item test = Item.findById(21);
		String name = test.getName();

		renderText(name);
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