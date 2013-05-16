/**
 * @author Boris
 */

package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Workstation extends Model {

	Long id;
	String name;
	double machinecosts;
	double idletimecosts;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getMachinecosts() {
		return machinecosts;
	}

	public void setMachinecosts(double machinecosts) {
		this.machinecosts = machinecosts;
	}

	public double getIdletimecosts() {
		return idletimecosts;
	}

	public void setIdletimecosts(double idletimecosts) {
		this.idletimecosts = idletimecosts;
	}

}
