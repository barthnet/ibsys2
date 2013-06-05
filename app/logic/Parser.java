package logic;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import models.DispositionManufacture;
import models.Item;
import models.OpenOrder;
import models.WaitingList;
import models.Workplace;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
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
		} catch (ParserConfigurationException e) {
			Logger.error("Couldnt get DocumentBuilder", e);
		} catch (SAXException | IOException e) {
			Logger.error("Couldnt parse InputStream to Document", e);
		}
	}

	public void parseDoc() {
		ApplicationLogic.resetData();
		parseArticles();
		parseOpenOrders();
		parseWaitingLists();
		parseOrderInWorks();
		setDependencies();
	}

	public static void setDependencies() {
		Logger.info("setDependencies");
		List<DispositionManufacture> disps = DispositionManufacture.findAll();
		DispositionManufacture parent = new DispositionManufacture();
		for (int i = 0, length = disps.size(); i < length; i++) {
			DispositionManufacture disp = disps.get(i);
			Item item = Item.find("byItemId", disp.item).first();
			if ("P".equals(item.type)) {
				parent = new DispositionManufacture();
			} else {
				disp.distributionWish = parent.production;
			}
			boolean mulitpleItem = item.itemNumber == 26 || item.itemNumber == 16 || item.itemNumber == 17 ? true : false;
			disp.stock = item.amount;
			// TODO in item model yml aufnehmen
			disp.safetyStock = disp.safetyStock > 0 ? disp.safetyStock : 100;
			disp.parentWaitingList = parent.waitingList;
			List<WaitingList> wL = WaitingList.find("byItem", disp.item).fetch();
			for (WaitingList waitingList : wL) {
				Workplace wP = Workplace.find("byWorkplaceId", waitingList.workplace).first();
				if (wP.inWork != null && wP.inWork.equals(waitingList.waitingListId)) {
					if (mulitpleItem) {
						disp.inWork += waitingList.amount / 3;
					} else {
						disp.inWork += waitingList.amount;
					}
				} else {
					if (mulitpleItem) {
						disp.waitingList += waitingList.amount / 3;
					} else {
						disp.waitingList += waitingList.amount;
					}
				}
			}
			disp.production = disp.distributionWish + disp.parentWaitingList + disp.safetyStock - disp.stock - disp.waitingList - disp.inWork;
			disp.save();
			if (disp.itemChilds != null && disp.itemChilds.length > 0) {
				parent = disp;
			}
		}
	}

	private void parseArticles() {
		NodeList articles = document.getElementsByTagName("article");
		Logger.info("parseArticles %s", articles.getLength());
		for (int i = 0, length = articles.getLength(); i < length; i++) {
			Node node = articles.item(i);
			NamedNodeMap att = node.getAttributes();
			Item item = Item.find("byItemNumber", getInteger(att, "id")).first();
			if (item == null) {
				item = new Item();
				item.itemNumber = getInteger(att, "id");
			}
			item.amount = getInteger(att, "amount");
			item.price = getDouble(att, "price");
			item.save();
		}
	}

	private void parseOpenOrders() {
		NodeList openOrders = document.getElementsByTagName("futureinwardstockmovement");
		NodeList orders = openOrders.item(0).getChildNodes();
		Logger.info("parseOpenOrders: %s", orders.getLength());
		for (int i = 0, length = orders.getLength(); i < length; i++) {
			Node node = orders.item(i);
			NamedNodeMap att = node.getAttributes();
			OpenOrder order = new OpenOrder();
			order.orderPeriod = getInteger(att, "orderperiod");
			order.mode = getInteger(att, "mode");
			order.amount = getInteger(att, "amount");
			Item item = Item.find("byItemNumber", getInteger(att, "article")).first();
			order.item = item.itemId;
			order.save();
		}
	}

	private void parseWaitingLists() {
		NodeList waitinglistworkstations = document.getElementsByTagName("waitinglistworkstations");
		NodeList workplaces = waitinglistworkstations.item(0).getChildNodes();
		Logger.info("parseWaitingLists %s", workplaces.getLength());
		for (int i = 0, lengthWP = workplaces.getLength(); i < lengthWP; i++) {
			NodeList waitingLists = workplaces.item(i).getChildNodes();
			Node workplace = workplaces.item(i);
			NamedNodeMap attWP = workplace.getAttributes();
			Workplace wO = Workplace.find("byWorkplaceId", getInteger(attWP, "id")).first();
			for (int j = 0, lengthWL = waitingLists.getLength(); j < lengthWL; j++) {
				Node waiting = waitingLists.item(j);
				NamedNodeMap attWL = waiting.getAttributes();
				WaitingList wList = new WaitingList();
				wList.period = getInteger(attWL, "period");
				wList.amount = getInteger(attWL, "amount");
				wList.orderNumber = getInteger(attWL, "order");
				wList.timeneed = getInteger(attWL, "timeneed");
				wList.workplace = wO.workplaceId;
				Item it = Item.find("byItemNumber", getInteger(attWL, "item")).first();
				wList.item = it.itemId;
				wList.save();
				wO.addWaitingList(wList);
			}
			wO.save();
		}
	}

	private void parseOrderInWorks() {
		NodeList ordersinwork = document.getElementsByTagName("ordersinwork");
		NodeList workplaces = ordersinwork.item(0).getChildNodes();
		Logger.info("parseOrderInWorks: %s", workplaces.getLength());
		for (int i = 0, lengthWP = workplaces.getLength(); i < lengthWP; i++) {
			Node workplace = workplaces.item(i);
			NamedNodeMap attWP = workplace.getAttributes();
			Workplace wO = Workplace.find("byWorkplaceId", getInteger(attWP, "id")).first();
			WaitingList wList = new WaitingList();
			wList.period = getInteger(attWP, "period");
			wList.amount = getInteger(attWP, "amount");
			wList.orderNumber = getInteger(attWP, "order");
			wList.timeneed = getInteger(attWP, "timeneed");
			wList.workplace = wO.workplaceId;
			Item it = Item.find("byItemNumber", getInteger(attWP, "item")).first();
			wList.item = it.itemId;
			wList.save();
			wO.inWork = wList.waitingListId;
			wO.save();
		}
	}

	@SuppressWarnings("all")
	private Double getDouble(NamedNodeMap node, String attribute) {
		try {
			NumberFormat format = NumberFormat.getInstance(Locale.GERMAN);
			return format.parse(node.getNamedItem(attribute).getNodeValue()).doubleValue();
		} catch (DOMException | ParseException e) {
			Logger.error("cant convert %s to double", node.getNamedItem(attribute).getNodeValue());
			return null;
		}
	}

	@SuppressWarnings("all")
	private int getInteger(NamedNodeMap node, String attribute) {
		return Integer.parseInt(node.getNamedItem(attribute).getNodeValue());
	}

	@SuppressWarnings("all")
	private Long getLong(NamedNodeMap node, String attribute) {
		return Long.parseLong(node.getNamedItem(attribute).getNodeValue());
	}

	@SuppressWarnings("all")
	private String getString(NamedNodeMap node, String attribute) {
		return node.getNamedItem(attribute).getNodeValue();
	}

}
