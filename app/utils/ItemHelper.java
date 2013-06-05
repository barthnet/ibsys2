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

	public static int getSetupTime(int workplace, String item) {

		ItemTime itemTime = ItemTime.find("byWorkplaceAndItem", workplace, item).first();
		return itemTime.setupTime;
	}

	public static int getProcessTime(int workplace, String item) {

		ItemTime itemTime = ItemTime.find("byWorkplaceAndItem", workplace, item).first();
		return itemTime.processTime;
	}

	public static List<Workplace> getWorkplaces(String item) {
		List<Workplace> result = new ArrayList<Workplace>();

		// ItemTimes suchen anhand des Items aus den ItemTimes
		// die Workplaces zurückschicken

		List<ItemTime> times = ItemTime.find("byItem", item).fetch();
		for (ItemTime itemTime : times) {
			Workplace wp = Workplace.find("byWorkplaceId", itemTime.workplace).first();
			result.add(wp);
		}
		return result;
	}
}