package csulb.cecs323.model;

import java.sql.Time;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "plane_revenues")
public class PlaneRevenue {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	   private
	   Long id;
	
	@Temporal(TemporalType.DATE)
	private Date arrivalDate;
	
	@Temporal(TemporalType.DATE)
	private Date departureDate;
	
	@Temporal(TemporalType.TIME)
	private Date arrivalTime;
	
	@Temporal(TemporalType.TIME)
	private Date departureTime;
	
	private double flowageFee;
	
	private double landingFee;
	
	private int passengerCount;
	
	private double feePerPassenger;
	
	@ManyToOne
	private Plane plane;
	
	@ManyToOne
	private Airport airport;
	
	public PlaneRevenue()
	{
		
	}

	public PlaneRevenue(Date arrivalDate, Date departureDate, Date arrivalTime, Date departureTime, double flowageFee,
			double landingFee, int passengerCount, double feePerPassenger) {
		this.arrivalDate = arrivalDate;
		this.departureDate = departureDate;
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
		this.flowageFee = flowageFee;
		this.landingFee = landingFee;
		this.passengerCount = passengerCount;
		this.feePerPassenger = feePerPassenger;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}





	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}





	public Date getDepartureDate() {
		return departureDate;
	}





	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}





	public double getFlowageFee() {
		return flowageFee;
	}

	public void setFlowageFee(double flowageFee) {
		this.flowageFee = flowageFee;
	}

	public double getLandingFee() {
		return landingFee;
	}

	public void setLandingFee(double landingFee) {
		this.landingFee = landingFee;
	}

	public double getFeePerPassenger() {
		return feePerPassenger;
	}

	public void setFeePerPassenger(double feePerPassenger) {
		this.feePerPassenger = feePerPassenger;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Time arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Time departureTime) {
		this.departureTime = departureTime;
	}



	public int getPassengerCount() {
		return passengerCount;
	}



	public void setPassengerCount(int passengerCount) {
		this.passengerCount = passengerCount;
	}

	public Plane getPlane() {
		return plane;
	}

	public void setPlane(Plane plane) {
		this.plane = plane;
	}

	public Airport getAirport() {
		return airport;
	}

	public void setAirport(Airport airport) {
		this.airport = airport;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}
	
	
	
	
}
