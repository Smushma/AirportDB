package csulb.cecs323.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "retail_shops")
public class RetailShop {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	   private
	   Long id;
	
	private String shopName;
	
	private String shopType;
	
	private double rent;
	
	@ManyToOne
	private Airport airport;
	
	public RetailShop()
	{
		
	}
	
	public RetailShop(String shopName, String shopType, double rent) {
		this.shopName = shopName;
		this.shopType = shopType;
		this.rent = rent;
	}
	
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopType() {
		return shopType;
	}
	public void setShopType(String shopType) {
		this.shopType = shopType;
	}
	public double getRent() {
		return rent;
	}
	public void setRent(double rent) {
		this.rent = rent;
	}

	public Airport getAirport() {
		return airport;
	}

	public void setAirport(Airport airport) {
		this.airport = airport;
	}
	
	
}
