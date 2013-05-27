package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Workplace extends Model {
	
	public int workplaceId;
	public String name;
	
	

}
