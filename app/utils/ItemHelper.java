package utils;

import java.util.ArrayList;
import java.util.List;

import models.Item;
import models.ItemTime;
import models.Workplace;

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

	public static List<Workplace> getWorkplaces(Item item) {
		List<Workplace> result = new ArrayList<Workplace>();
		
		//ItemTimes suchen anhand des Items aus den ItemTimes
		//die Workplaces zurückschicken
		
		List<ItemTime> times = ItemTime.find("byItem", item).fetch();
		for (ItemTime itemTime : times) {
			result.add(itemTime.workplace);
		}
		
		result = Workplace.find("byItem", item).fetch();		
		return result;
	}
}