package controllers;

import java.io.InputStream;
import java.io.StringWriter;
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
import models.Workplace;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;

import play.Logger;
import play.mvc.Controller;
import play.templates.Template;
import play.templates.TemplateLoader;

import com.sun.org.apache.xml.internal.security.utils.XMLUtils;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class Application extends Controller {

	public static void index() {
		render();
	}

	/**
	 * save disposition orders
	 */
	public static void postDispositionOrders(String userName) {
		setHeader();
		String body = getBodyAsString();
		ArrayList<DispositionOrder> orders = new JSONDeserializer<ArrayList<DispositionOrder>>().use("values", DispositionOrder.class).deserialize(body);
		Logger.info("postDispositionOrders: %s", orders.size());
		DispositionOrder.merge(orders);
		ok();
	}

	/**
	 * save capacities
	 */
	public static void postCapacity(String userName) {
		setHeader();
		String body = getBodyAsString();
		// ApplicationLogic.calculateCapacity();
		ArrayList<Capacity> capacities = new JSONDeserializer<ArrayList<Capacity>>().use("values", Capacity.class).deserialize(body);
		Logger.info("postCapacity: %s", capacities.size());
		Capacity.merge(capacities);
		ok();
	}

	/**
	 * save productionOrders
	 */
	public static void postProductionOrders(String userName) {
		setHeader();
		String body = getBodyAsString();
		ArrayList<ProductionOrder> orders = new JSONDeserializer<ArrayList<ProductionOrder>>().use("values", ProductionOrder.class).deserialize(body);
		if (orders != null && !orders.isEmpty()) {
			Logger.info("postProductionOrders: %s %s", ProductionOrder.find("byUser", userName).fetch().size(), orders.size());
			Workplace.deleteAllProductionPlanLists(orders.get(0).user);
			ProductionOrder.deleteAll(userName);
			ProductionOrder.saveAll(orders);
		}
		ApplicationLogic.calculateCapacity(userName);
		ApplicationLogic.calculateDisposition(userName);
		ok();
	}

	/**
	 * save productionplan
	 */
	public static void postProductionPlan(String userName) {
		setHeader();
		String body = getBodyAsString();
		ArrayList<DispositionManufacture> plan = new JSONDeserializer<ArrayList<DispositionManufacture>>().use("values", DispositionManufacture.class)
				.deserialize(body);
		Logger.info("postProductionPlan: %s", plan.size());
		DispositionManufacture.merge(plan);
		ApplicationLogic.planToOrder(userName);
		ApplicationLogic.calculateCapacity(userName);
		ApplicationLogic.calculateDisposition(userName);
		ok();
	}

	/**
	 * save and merge posted deistributionWishs
	 */
	public static void postDistributionWishs(String userName) {
		setHeader();
		String body = getBodyAsString();
		// Logger.info("postDistributionWish: %S", body);
		ArrayList<DistributionWish> wishs = new JSONDeserializer<ArrayList<DistributionWish>>().use("values", DistributionWish.class).deserialize(body);
		Logger.info("postDistributionWishs: %s", wishs.size());
		DistributionWish.merge(wishs);
		ApplicationLogic.wishToPlan(userName);
		ApplicationLogic.calcProductionPlan(userName);
		ApplicationLogic.planToOrder(userName);
		ApplicationLogic.calculateCapacity(userName);
		ApplicationLogic.calculateDisposition(userName);
		ok();
	}

	/**
	 * get disposition orders
	 */
	public static void getDispositionOrders(String userName) {
		setHeader();
		Logger.info("getDispositionOrders");
		List<DispositionOrder> orders = DispositionOrder.find("user = ? order by item asc", userName).fetch();
		renderJSON(new JSONSerializer().exclude("itemAsObject").serialize(orders));
	}

	/**
	 * get calculated capacities of all workplaces
	 */
	public static void getCapacity(String userName) {
		setHeader();
		// ApplicationLogic.calculateCapacity();
		Logger.info("getCapacity %s", userName);
		List<Capacity> capacities = Capacity.find("user = ? order by workplace asc", userName).fetch();
		renderJSON(new JSONSerializer().exclude("workplaceAsObject").serialize(capacities));
	}

	/**
	 * get all saved productionOrders
	 */
	public static void getProductionOrders(String userName) {
		setHeader();
		Logger.info("getProductionOrders");
		List<ProductionOrder> orders = ProductionOrder.find("user = ? order by orderNumber asc", userName).fetch();
		// if (orders == null || orders.isEmpty()) {
		// ApplicationLogic.planToOrder();
		// orders = ProductionOrder.find("order by orderNumber asc").fetch();
		// }
		renderJSON(new JSONSerializer().exclude("itemAsObject").serialize(orders));
	}

	/**
	 * get productionplan
	 */
	public static void getProductionPlan(String userName) {
		setHeader();
		Logger.info("getProductionPlan");
		List<DispositionManufacture> disps = DispositionManufacture.find("byUser", userName).fetch();
		// Logger.info("childs: %s", disps.get(0));
		renderJSON(new JSONSerializer().include("itemChilds").exclude("itemAsObject", "ItemChildsAsObject").serialize(disps));
	}

	/**
	 * deliver saved distributionWishs, if necessary create them first
	 */
	public static void getDistributenWishs(String userName) {
		setHeader();
		Logger.info("getDistributenWishs");
		List<DistributionWish> wishs = DistributionWish.find("byUser", userName).fetch();
		if (wishs == null || wishs.isEmpty()) {
			wishs = new ArrayList<>();
			List<Item> pItems = Item.find("byTypeAndUser", "P", userName).fetch();
			for (Item item : pItems) {
				DistributionWish wish = new DistributionWish();
				wish.item = item.itemId;
				wish.user = userName;
				wish.save();
				wishs.add(wish);
			}
		}
		renderJSON(new JSONSerializer().exclude("itemAsObject").serialize(wishs));
	}

	/**
	 * sets the method for expected delivery calculation
	 */
	public static void postUserMethod() {
		setHeader();
		Logger.info("postUserMethod");
		String body = getBodyAsString();
		User user = new JSONDeserializer<User>().deserialize(body);
		Logger.info("postUserMethod: %s", user.method);
		ok();
	}

	/**
	 * returns the method for expected delivery calculation
	 */
	public static void getUserMethod(String userName) {
		setHeader();
		Logger.info("getUserMethod");
		User user = User.find("byName", userName).first();
		renderJSON(user.method);
	}

	public static void reset(String userName) {
		setHeader();
		Logger.info("reset");
		ApplicationLogic.resetUserData(userName);
	}

	/**
	 * Testmethod to develop xml parser with local xml file doesnt load
	 * everytime the xml from the scsim website
	 */
	public static void parseXML(String userName) {
		setHeader();
		Logger.info("parseXML %s", userName);
		Template template = TemplateLoader.load("229_9_7result.xml");
		String xmlFile = template.render();
		InputStream in = IOUtils.toInputStream(xmlFile);
		Parser p = new Parser(in);
		p.parseDoc(userName);
		ApplicationLogic.wishToPlan(userName);
		ApplicationLogic.calcProductionPlan(userName);
		ApplicationLogic.planToOrder(userName);
		ApplicationLogic.calculateCapacity(userName);
		ApplicationLogic.calculateDisposition(userName);

		User user = User.find("byName", userName).first();
		user.isSimulatable = true;
		user.save();
		int actPeriod = Integer.valueOf(user.period);
		user.isSimulatable = true;
		user.save();
		renderJSON(actPeriod);
	}

	/**
	 * downloads the input.xml
	 */
	public static void downloadXML(String userName) {
		setHeader();
		Logger.info("downloadXML");
		response.setContentTypeIfNotSet("application/x-download");
		response.setHeader("Content-disposition", "attachment; filename=input.xml");

		Document doc = Parser.parseInputXML(userName);

		renderXml(doc);
	}

	public static void uploadToSite(String userName, String password) {
		setHeader();
		Logger.info("uploadToSite");
		try {
			User user = User.find("byName", userName).first();

			boolean erg = false;
			if (user != null && user.isSimulatable) {
				Document doc = Parser.parseInputXML(userName);

				Source source = new DOMSource(doc);
				StringWriter stringWriter = new StringWriter();
				Result result = new StreamResult(stringWriter);
				TransformerFactory factory = TransformerFactory.newInstance();
				Transformer transformer;
				String test = "";
				try {
					transformer = factory.newTransformer();
					transformer.transform(source, result);
				} catch (TransformerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				test = stringWriter.getBuffer().toString();

				Crawler cr = new Crawler(userName, password);
				erg = cr.exportFileToWeb(test);
			}
			renderJSON(erg);
		} catch (Exception e) {
			renderJSON(false);
		}
	}

	/**
	 * upload latest result xml file manually
	 * 
	 * @param file
	 */
	public static void uploadXML(String userName) {
		setHeader();
		Logger.info("uploadXML %s", userName);
		int actPeriod;
		try {
			String xml = getBodyAsString();
			InputStream in = IOUtils.toInputStream(xml);
			Parser p = new Parser(in);
			p.parseDoc(userName);

			ApplicationLogic.wishToPlan(userName);
			ApplicationLogic.calcProductionPlan(userName);
			ApplicationLogic.planToOrder(userName);
			ApplicationLogic.calculateCapacity(userName);
			ApplicationLogic.calculateDisposition(userName);
			User user = User.find("byName", userName).first();
			actPeriod = Integer.valueOf(user.period);
			user.isSimulatable = true;
			user.save();
			renderText("{\"result\":true,\"period\":" + actPeriod + "}");
		} catch (Exception e) {
			renderText("{\"result\":false}");
		}
	}

	public static void checkUser(String userName) {
		setHeader();
		Logger.info("check User %s", userName);
		User user = User.find("byName", userName).first();
		if (user != null && user.isSimulatable) {
			renderText("{\"result\":true,\"period\":" + user.period + "}");
		} else {
			renderText("{\"result\":false}");
			// renderJSON(false);
		}
	}

	/**
	 * check if login data is correct
	 */
	public static void login() {
		setHeader();
		Logger.info("login");
		JSONObject json = getBodyAsJSON();
		String username = null, password = null;
		try {
			username = json.getString("username");
			password = json.getString("password");

			Crawler cr = new Crawler(username, password);

			boolean check = cr.checkLogin();
			if (check) {
				User user = User.find("byName", username).first();
				if (user == null) {
					user = new User();
					user.name = username;
					user.save();
				}
			}
			renderText("{\"success\":true, \"result\":" + check + "}");

		} catch (Exception e) {
			renderText("{\"success\":false}");
		}

	}

	public static void offline(String userName) {
		setHeader();
		User user = User.find("byName", userName).first();
		if (user == null) {
			user = new User();
			user.name = userName;
			user.save();
		}
		ok();
	}

	/**
	 * crawling scsim.de to download the latest result xml file
	 * 
	 * @param username
	 * @param password
	 */
	public static void loadXmlFromSite(String userName, String password) {
		setHeader();
		Logger.info("loadXmlFromSite");
		try {
			Crawler cr = new Crawler(userName, password);
			String file = cr.importFileFromWeb();

			// renderText(file);
			file = file.trim();
			// Logger.info("file:\n%s", file);

			InputStream in = IOUtils.toInputStream(file);
			Parser p = new Parser(in);
			p.parseDoc(userName);

			ApplicationLogic.wishToPlan(userName);
			ApplicationLogic.calcProductionPlan(userName);
			ApplicationLogic.planToOrder(userName);
			ApplicationLogic.calculateCapacity(userName);
			ApplicationLogic.calculateDisposition(userName);
			User user = User.find("byName", userName).first();

			int actPeriod = Integer.valueOf(user.period);
			user.isSimulatable = true;
			user.save();
			renderText("{\"success\":true, \"result\":" + actPeriod + "}");
		} catch (Exception e) {
			renderText("{\"success\":false}");
		}
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
