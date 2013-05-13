/**
 * 
 */
package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

/**
 * @author Woda
 * 
 */
@Entity
public class Workplace extends Model {

	private int Id;
	private int idletime;
	private double machineIdleTimeCosts;
	private int setupEvents;
	private double wageCosts;
	private double wageIdleTimeCosts;
	private int timeNeed;

}
