package csulb.cecs323.model;

import javax.persistence.*;


@Entity
@Table(name = "parkings")
public class Parking {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	   private
	   Long id;
	
	private String location;
	private double price;
	private int numOfSpaces;
	private String typeOfSpace;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Airport airport;
	
	public Parking()
	{
		
	}
	
	public Parking(String location, double price, int numOfSpaces, String typeOfSpace) {
		super();
		this.location = location;
		this.price = price;
		this.numOfSpaces = numOfSpaces;
		this.typeOfSpace = typeOfSpace;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getNumOfSpaces() {
		return numOfSpaces;
	}

	public void setNumOfSpaces(int numOfSpaces) {
		this.numOfSpaces = numOfSpaces;
	}

	public String getTypeOfSpace() {
		return typeOfSpace;
	}

	public void setTypeOfSpace(String typeOfSpace) {
		this.typeOfSpace = typeOfSpace;
	}

	public Airport getAirport() {
		return airport;
	}

	public void setAirport(Airport airport) {
		this.airport = airport;
	}
	
	
	
}
