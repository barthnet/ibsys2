/**
 * @author Boris 
 */

import models.Item;
import models.Workstation;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class Bootstrap extends Job {
	public void doJob() {

		if (Item.count() == 0) {
			Fixtures.loadModels("initial-items.yml");
		}

		if (Workstation.count() == 0) {
			Fixtures.loadModels("initial-workstations.yml");
		}
	}
}