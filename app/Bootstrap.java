/**
 * @author Boris 
 */

import play.Logger;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class Bootstrap extends Job {
	public void doJob() {
		Logger.info("Bootstrap");
		Fixtures.deleteAllModels();
		Fixtures.loadModels("initial-items.yml", "initial-distributions.yml");	
	}
}