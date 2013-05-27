package logic;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import models.Item;
import models.OpenOrder;
//import models.Order;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import play.Logger;

public class Parser {

	private Document document = null;

	public Parser(InputStream in) {
		try {
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			document = dBuilder.parse(in);
			document.normalize();
			Logger.info("test: %s", document.getElementsByTagName("order").getLength());
		} catch (ParserConfigurationException e) {
			Logger.error("Couldnt get DocumentBuilder", e);
		} catch (SAXException | IOException e) {
			Logger.error("Couldnt parse InputStream to Document", e);
		}
	}

	public void parseDoc() {
		parseArticles();
		parseOpenOrders();
	}

	private void parseArticles() {
		NodeList articles = document.getElementsByTagName("article");
		for (int i = 0, length = articles.getLength(); i < length; i++) {
			Node node = articles.item(i);
			NamedNodeMap att = node.getAttributes();
			Item item = Item.find("byItemId",getString(att, "id")).first();
			if (item == null) {
				item = new Item();
				item.itemId = getString(att, "id");
			}
			item.amount = getInteger(att, "amount");
			item.price = getDouble(att, "price");
			item = item.merge();
			item.save();
		}
	}
	
	private void parseOpenOrders() {
		NodeList openOrders = document.getElementsByTagName("futureinwardstockmovement");
		NodeList orders = openOrders.item(0).getChildNodes();
		for (int i = 0, length = orders.getLength(); i < length; i++) {
			Node node = orders.item(i);
			NamedNodeMap att = node.getAttributes();
			OpenOrder order = new OpenOrder();
			order.orderperiod = getInteger(att, "orderperiod");
			order.mode = getInteger(att, "mode");
			order.amount = getInteger(att, "amount");
			order.item = Item.find("byItemId", getInteger(att, "article")).first();
			order.save();
		}
	}

	private Double getDouble(NamedNodeMap node, String attribute) {
		try {
			NumberFormat format = NumberFormat.getInstance(Locale.GERMAN);
			return format.parse(node.getNamedItem(attribute).getNodeValue()).doubleValue();
		} catch (DOMException | ParseException e) {
			Logger.error("cant convert %s to double", node.getNamedItem(attribute).getNodeValue());
			return null;
		}
	}

	private int getInteger(NamedNodeMap node, String attribute) {
		return Integer.parseInt(node.getNamedItem(attribute).getNodeValue());
	}

	private Long getLong(NamedNodeMap node, String attribute) {
		return Long.parseLong(node.getNamedItem(attribute).getNodeValue());
	}

	private String getString(NamedNodeMap node, String attribute) {
		return node.getNamedItem(attribute).getNodeValue();
	}

}
