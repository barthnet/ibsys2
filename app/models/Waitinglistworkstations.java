/**
 * @author Boris
 */

package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.jpa.GenericModel;

public class Waitinglistworkstations extends GenericModel {

	@Id
	private Long id;

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	private List<Workstation> workstationList;
	
	

}
