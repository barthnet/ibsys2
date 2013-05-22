package logic;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import models.Item;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.jamonapi.utils.Logger;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class Parser {

	public static Document parseFromXml(File file) throws ParserConfigurationException, SAXException, IOException {
		InputStream in = new FileInputStream(file);
		in.close();		
		return parseFromXml(in);
	}
	
	public static Document parseFromXml(InputStream in) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = dBuilder.parse(in);
		return doc;
	}

	public static void parseDoc(Document doc) {
		NodeList nList = doc.getElementsByTagName("results");
		for (int i = 0; i < nList.getLength(); i++) {
			Node node = nList.item(i);
			Logger.log("node:" + node.getNodeName());
			Item test = new Item();
			
		}
	}

	public static void printXmlDocument(Document doc) {
		Logger.log(stringifyXml(doc));
	}

	public static String stringifyXml(Document doc) {
		OutputFormat format = new OutputFormat(doc);
		format.setIndenting(true);
		XMLSerializer serializer = new XMLSerializer(System.out, format);
		try {
			serializer.serialize(doc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return serializer.toString();
	}

}
