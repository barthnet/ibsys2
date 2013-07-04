package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class User extends Model {

	public String period;
	public String name;
	public boolean isSimulatable = false;
	public String method = "recommended";
}
