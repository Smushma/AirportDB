package csulb.cecs323.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;

@Entity
@Table(name = "airports")
public class Airport {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	   private Long id;
	
	private String aiportCode;
	private String airportName;
	private String address;
	private String city;
	private String country;
	
	
	@OneToOne(fetch = FetchType.LAZY)
	private Parking parking;
	
	@OneToMany(cascade={CascadeType.REFRESH, CascadeType.REMOVE}, mappedBy = "airport")
	private List<RetailShop> retailShops;
	
	@OneToMany(cascade={CascadeType.PERSIST},mappedBy = "airport")
	private List<AirlineContract> airlineContracts;
	
	@OneToMany(cascade={CascadeType.PERSIST},mappedBy = "airport")
	private List<PlaneRevenue> planeRevenue;
	

	public Airport()
	{
		
	}
	
	public Airport(String aiportCode, String airportName, String address, String city, String country) {
		this.aiportCode = aiportCode;
		this.airportName = airportName;
		this.address = address;
		this.city = city;
		this.country = country;
		retailShops = new LinkedList<RetailShop>();
		airlineContracts = new LinkedList<AirlineContract>();
		planeRevenue = new LinkedList<PlaneRevenue>();
	}
	

	public String getAiportCode() {
		return aiportCode;
	}

	public void setAiportCode(String aiportCode) {
		this.aiportCode = aiportCode;
	}

	public String getAirportName() {
		return airportName;
	}

	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Parking getParking() {
		return parking;
	}

	public void setParking(Parking parking) {
		this.parking = parking;
	}

	public List<RetailShop> getRetailShops() {
		return retailShops;
	}

	public void setRetailShops(List<RetailShop> retailShops) {
		this.retailShops = retailShops;
	}

	public List<AirlineContract> getAirlineContracts() {
		return airlineContracts;
	}

	public void setAirlineContracts(List<AirlineContract> airlineContracts) {
		this.airlineContracts = airlineContracts;
	}

	public List<PlaneRevenue> getPlaneRevenue() {
		return planeRevenue;
	}

	public void setPlaneRevenue(List<PlaneRevenue> planeRevenue) {
		this.planeRevenue = planeRevenue;
	}
	
	
	
}
