package models;

import play.db.jpa.Model;

public abstract class AbstractPart extends Model{

	private Long id;
	private String name;
	private double value;
	
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public double getValue() {
		return value;
	}
	
	
}
