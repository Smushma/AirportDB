package csulb.cecs323.model;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "airlines")
public class Airline {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	   private
	   Long id;
	
	private String airlineName;
	private String terminal;
	private String phoneNumber;
	private String airlineType;
	
	@OneToMany(cascade={CascadeType.REFRESH, CascadeType.REMOVE},mappedBy = "airline")
	private List<AirlineContract> airlineContracts;
	
	@OneToMany(cascade={CascadeType.REFRESH, CascadeType.REMOVE},mappedBy = "airline")
	private List<Plane> planes;

	public Airline()
	{
		
	}
	
	public Airline(String airlineName, String terminal, String phoneNumber, String airlineType) {
		this.airlineName = airlineName;
		this.terminal = terminal;
		this.phoneNumber = phoneNumber;
		this.airlineType = airlineType;
		airlineContracts = new LinkedList<AirlineContract>();
		planes = new LinkedList<Plane>();
	}

	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAirlineType() {
		return airlineType;
	}

	public void setAirlineType(String airlineType) {
		this.airlineType = airlineType;
	}

	public List<AirlineContract> getAirlineContracts() {
		return airlineContracts;
	}

	public void setAirlineContracts(List<AirlineContract> airlineContracts) {
		this.airlineContracts = airlineContracts;
	}

	public List<Plane> getPlanes() {
		return planes;
	}

	public void setPlanes(List<Plane> planes) {
		this.planes = planes;
	}
	
}
