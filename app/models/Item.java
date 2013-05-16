/**
 * @author Boris
 */

package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Item extends Model {

	private Long id;
	private String name;
	private String name_en;
	private String type;
	private double itemcosts;
	private double ordercosts;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getItemcosts() {
		return itemcosts;
	}

	public void setItemcosts(double itemcosts) {
		this.itemcosts = itemcosts;
	}

	public double getOrdercosts() {
		return ordercosts;
	}

	public void setOrdercosts(double ordercosts) {
		this.ordercosts = ordercosts;
	}
}
