package csulb.cecs323.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "planes")
public class Plane {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	   private
	   Long id;
	
	private String tailNumber;
	
	private String model;
	
	private String gateNumber;
	
	private int passengerCap;
	
	@OneToMany(cascade={CascadeType.PERSIST},mappedBy = "plane")
	private List<PlaneRevenue> revenue;
	
	@ManyToOne
	private Airline airline;
	
	public Plane()
	{
		
	}

	public Plane(String tailNumber, String model, String gateNumber, int passengerCap) {
		this.tailNumber = tailNumber;
		this.model = model;
		this.gateNumber = gateNumber;
		this.passengerCap = passengerCap;
		revenue = new LinkedList<PlaneRevenue>();
	}
	
	public String getTailNumber() {
		return tailNumber;
	}

	public void setTailNumber(String tailNumber) {
		this.tailNumber = tailNumber;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getGateNumber() {
		return gateNumber;
	}

	public void setGateNumber(String gateNumber) {
		this.gateNumber = gateNumber;
	}

	public int getPassengerCap() {
		return passengerCap;
	}

	public void setPassengerCap(int passengerCap) {
		this.passengerCap = passengerCap;
	}

	public List<PlaneRevenue> getRevenue() {
		return revenue;
	}

	public void setRevenue(List<PlaneRevenue> revenue) {
		this.revenue = revenue;
	}

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}
	
	
}
