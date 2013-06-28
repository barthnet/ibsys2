package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import logic.ApplicationLogic;
import logic.Crawler;
import logic.Parser;
import models.Capacity;
import models.DispositionManufacture;
import models.DispositionOrder;
import models.DistributionWish;
import models.Item;
import models.ItemTime;
import models.ProductionOrder;
import models.User;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;

import play.Logger;
import play.mvc.Controller;
import play.templates.Template;
import play.templates.TemplateLoader;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class Application extends Controller {

	public static void index() {
		render();
	}

	public static void test() {
		List<ItemTime> places = ItemTime.findAll();
		renderText(places);
	}

	public static void test2() {
		List<DispositionManufacture> disps = DispositionManufacture.find("byItem", "E26").fetch();
		renderJSON(disps);
	}

	public static void testLogin() {
		setHeader();
		renderJSON(true);
	}

	/**
	 * save disposition orders
	 */
	public static void postDispositionOrders() {
		setHeader();
		String body = getBodyAsString();
		ArrayList<DispositionOrder> orders = new JSONDeserializer<ArrayList<DispositionOrder>>().use("values", DispositionOrder.class).deserialize(body);
		DispositionOrder.merge(orders);
		ok();
	}

	/**
	 * save capacities
	 */
	public static void postCapacity() {
		setHeader();
		String body = getBodyAsString();
		// ApplicationLogic.calculateCapacity();
		ArrayList<Capacity> capacities = new JSONDeserializer<ArrayList<Capacity>>().use("values", Capacity.class).deserialize(body);
		Capacity.merge(capacities);
		ok();
	}

	/**
	 * save productionOrders
	 */
	public static void postProductionOrders() {
		setHeader();
		String body = getBodyAsString();
		ArrayList<ProductionOrder> orders = new JSONDeserializer<ArrayList<ProductionOrder>>().use("values", ProductionOrder.class).deserialize(body);
		if (orders != null && !orders.isEmpty()) {
			Logger.info("postProductionOrders: %s %s", ProductionOrder.findAll().size(), orders.size());
			ProductionOrder.deleteAll();
			ProductionOrder.saveAll(orders);
		}
		ApplicationLogic.calculateCapacity();
		ApplicationLogic.calculateDisposition();
		ok();
	}

	/**
	 * save productionplan
	 */
	public static void postProductionPlan() {
		setHeader();
		String body = getBodyAsString();
		ArrayList<DispositionManufacture> plan = new JSONDeserializer<ArrayList<DispositionManufacture>>().use("values", DispositionManufacture.class)
				.deserialize(body);
		DispositionManufacture.merge(plan);
		ApplicationLogic.planToOrder();
		ApplicationLogic.calculateCapacity();
		ApplicationLogic.calculateDisposition();
		ok();
	}

	/**
	 * save and merge posted deistributionWishs
	 */
	public static void postDistributionWishs() {
		setHeader();
		String body = getBodyAsString();
		// Logger.info("postDistributionWish: %S", body);
		ArrayList<DistributionWish> wishs = new JSONDeserializer<ArrayList<DistributionWish>>().use("values", DistributionWish.class).deserialize(body);
		DistributionWish.merge(wishs);
		ApplicationLogic.wishToPlan();
		ApplicationLogic.calcProductionPlan();
		ApplicationLogic.planToOrder();
		ApplicationLogic.calculateCapacity();
		ApplicationLogic.calculateDisposition();
		ok();
	}

	/**
	 * get disposition orders
	 */
	public static void getDispositionOrders() {
		setHeader();
		List<DispositionOrder> orders = DispositionOrder.findAll();
		renderJSON(new JSONSerializer().exclude("itemAsObject").serialize(orders));
	}

	/**
	 * get calculated capacities of all workplaces
	 */
	public static void getCapacity() {
		setHeader();
		// ApplicationLogic.calculateCapacity();
		List<Capacity> capacities = Capacity.findAll();
		renderJSON(new JSONSerializer().exclude("workplaceAsObject").serialize(capacities));
	}

	/**
	 * get all saved productionOrders
	 */
	public static void getProductionOrders() {
		setHeader();
		List<ProductionOrder> orders = ProductionOrder.find("order by orderNumber asc").fetch();
		// if (orders == null || orders.isEmpty()) {
		// ApplicationLogic.planToOrder();
		// orders = ProductionOrder.find("order by orderNumber asc").fetch();
		// }
		renderJSON(new JSONSerializer().exclude("itemAsObject").serialize(orders));
	}

	/**
	 * get productionplan
	 */
	public static void getProductionPlan() {
		setHeader();
		List<DispositionManufacture> disps = DispositionManufacture.findAll();
		// Logger.info("childs: %s", disps.get(0));
		renderJSON(new JSONSerializer().include("itemChilds").exclude("itemAsObject", "ItemChildsAsObject").serialize(disps));
	}

	/**
	 * deliver saved distributionWishs, if necessary create them first
	 */
	public static void getDistributenWishs() {
		setHeader();
		List<DistributionWish> wishs = DistributionWish.findAll();
		if (wishs == null || wishs.isEmpty()) {
			wishs = new ArrayList<>();
			List<Item> pItems = Item.find("byType", "P").fetch();
			for (Item item : pItems) {
				DistributionWish wish = new DistributionWish();
				wish.item = item.itemId;
				wish.save();
				wishs.add(wish);
			}
		}
		renderJSON(new JSONSerializer().exclude("itemAsObject").serialize(wishs));
	}
	
	public static void reset() {
		setHeader();
		ApplicationLogic.resetData();
	}

	/**
	 * Testmethod to develop xml parser with local xml file doesnt load
	 * everytime the xml from the scsim website
	 */
	public static void parseXML() {
		setHeader();
		Template template = TemplateLoader.load("229_9_7result.xml");
		String xmlFile = template.render();
		InputStream in = IOUtils.toInputStream(xmlFile);
		Parser p = new Parser(in);
		p.parseDoc();
		User user = new User();
		user.period = "7";
		user.save();
		ApplicationLogic.wishToPlan();
		ApplicationLogic.calcProductionPlan();
		ApplicationLogic.planToOrder();
		ApplicationLogic.calculateCapacity();
		ApplicationLogic.calculateDisposition();
		ok();
	}
	
	/**
	 * downloads the input.xml
	 */
	public static void downloadXML() {
		setHeader();
		response.setContentTypeIfNotSet("application/x-download");  
		response.setHeader("Content-disposition","attachment; filename=input.xml");
		
		Document doc = Parser.parseInputXML();
		renderXml(doc);
	}

	/**
	 * upload latest result xml file manually
	 * 
	 * @param file
	 */
	public static void uploadXML(File file) {
		setHeader();
		String body = getBodyAsString();
		Logger.info("uploadXML: %s", body);
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			Parser p = new Parser(in);
			p.parseDoc();
		} catch (FileNotFoundException | NullPointerException e) {
			e.printStackTrace();
			error("No file received.");
		}
		ok();
	}

	/**
	 * check if login data is correct
	 */
	public static void login() {
		setHeader();
		JSONObject json = getBodyAsJSON();
		String username = null, password = null;
		try {
			username = json.getString("username");
			password = json.getString("password");
		} catch (JSONException e) {
			error("Error on login check");
		}
		Crawler cr = new Crawler(username, password);
		renderJSON(cr.checkLogin());
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
		User user = new User();
		user.period = cr.period;
		user.save();
		Logger.info("file:\n%s", file);
		renderText(file);
	}

	/**
	 * set response header for CORS Ajax requests
	 */
	private static void setHeader() {
		response.setHeader("Access-Control-Allow-Origin", "*");
	}

	/**
	 * get request body as JSONObject
	 * 
	 * @return
	 */
	private static JSONObject getBodyAsJSON() {
		String body = params.get("body");
		JSONObject json = null;
		try {
			json = new JSONObject(body);
		} catch (JSONException e) {
			Logger.error("couldnt parse body to JSONObject: %s", body);
		}
		return json;
	}

	/**
	 * get request body as string
	 * 
	 * @return
	 */
	private static String getBodyAsString() {
		return params.get("body");
	}

}
