/**
 * @author Boris
 */

package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.jpa.GenericModel;

@Entity
public class Workstation extends GenericModel {

	@Id
	private Long id;
	private String name;
	private String name_en;
	private double machinecosts;
	private double idletimecosts;

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
	
	public String getName_en() {
		return name_en;
	}

	public void setName_en(String name_en) {
		this.name_en = name_en;
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
