/**
 * @author Boris
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import logic.ApplicationLogic;
import models.Component;
import models.Item;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.templates.Template;
import play.templates.TemplateLoader;

@OnApplicationStart
public class Bootstrap extends Job {
	
	public void doJob() {
		ApplicationLogic.resetData();
	}
	
}
