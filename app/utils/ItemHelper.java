package utils;

import models.Item;
import models.Workplace;
import models.ItemTime;

import play.db.jpa.Model;

/**
 * 
 * @author sven
 *
 */
public class ItemHelper {
	
	public static int getSetupTime(Workplace workplace, Item item) {
		
		ItemTime itemTime = ItemTime.find("byWorkplaceAndItem", workplace, item).first();
		return itemTime.setupTime;
	}
	
	public static int getProcessTime(Workplace workplace, Item item) {
		
		ItemTime itemTime = ItemTime.find("byWorkplaceAndItem", workplace, item).first();
		return itemTime.processTime;
	}

}
