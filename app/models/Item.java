/**
 * @author Boris
 */

package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.jpa.GenericModel;

@Entity
public class Item extends GenericModel {

	@Id
	private Long id;
	private String type;
	private String name;
	private String name_en;
	private double itemcosts;
	private double ordercosts;

	public Item(Long id, String type, String name) {
		super();

		this.id = id;
		this.type = type;
		this.name = name;
	}

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
