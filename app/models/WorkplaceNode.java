package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class WorkplaceNode extends Model {
	
	public String item;
	public int workplace;
}
