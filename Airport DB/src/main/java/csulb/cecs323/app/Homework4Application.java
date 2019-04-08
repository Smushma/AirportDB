/*
 * Licensed under the Academic Free License (AFL 3.0).
 *     http://opensource.org/licenses/AFL-3.0
 *
 *  This code is distributed to CSULB students in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE, other than educational.
 *
 *  2018 Alvaro Monge <alvaro.monge@csulb.edu>
 *
 */

package csulb.cecs323.app;

import csulb.cecs323.model.Airline;
import csulb.cecs323.model.AirlineContract;
import csulb.cecs323.model.Airport;
import csulb.cecs323.model.Parking;
import csulb.cecs323.model.Plane;
import csulb.cecs323.model.PlaneRevenue;
import csulb.cecs323.model.RetailShop;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;
import javax.persistence.Query;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * A simple application to demonstrate how to persist an object in JPA.
 *
 * This is for demonstration and educational purposes only.
 */

public class Homework4Application {

	private EntityManager entityManager;

	private static final Logger LOGGER = Logger.getLogger(Homework4Application.class.getName());

	public Homework4Application(EntityManager manager) {
		this.entityManager = manager;
	}

	public static void main(String[] args) {
		LOGGER.fine("Creating EntityManagerFactory and EntityManager");
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("homework4_PU");
		EntityManager manager = factory.createEntityManager();
		Homework4Application hw4application = new Homework4Application(manager);
		List output;
		// Any changes to the database need to be done within a transaction.
		// See: https://en.wikibooks.org/wiki/Java_Persistence/Transactions

		LOGGER.fine("Begin of Transaction");
		EntityTransaction tx = manager.getTransaction();

		tx.begin();

		hw4application.initializeAirportEntities();

		hw4application.initializeAirlineEntities();

		hw4application.initializeAirlineContractEntities();

		hw4application.initializeParkingEntities();

		hw4application.initializePlaneEntities();

		hw4application.initializePlaneRevenueEntities();

		hw4application.initializeRetailShopEntities();

		tx.commit();
		LOGGER.fine("End of Transaction");

		for (int i = 0; i < 50; ++i) System.out.println();

		//Console
		Scanner userInput = new Scanner(System.in);
		int input = 0;
		System.out.println("Console Commands: ");
		System.out.println("1: Selects airport with highest nonaviation revenue, considers airports without retail shops (null values)");
		System.out.println("2: Select airport having total revenue greater than $500000");
		System.out.println("3: Select plane and flight with the most empty seats");
		System.out.println("4: Insert Retail Shop");
		System.out.println("5: Remove Airline");
		System.out.println("6: Quit");

		boolean active = true;
		while(active)
		{	
			String query;
			System.out.print("> ");
			input = userInput.nextInt();
			switch(input)
			{
			case 1:
				query = "SELECT ap.AIRPORTNAME, (r.RENT + p.PRICE * p.NUMOFSPACES) AS 'NONAVIATION_REVENUE'\r\n" + 
						"  FROM airports ap LEFT OUTER JOIN retail_shops r on ap.ID = r.AIRPORT_ID\r\n" + 
						"                    INNER JOIN parkings p on ap.ID = p.AIRPORT_ID\r\n" + 
						"    WHERE r.RENT + p.PRICE * p.NUMOFSPACES =\r\n" + 
						"          (SELECT MAX(r.RENT + p.PRICE * p.NUMOFSPACES)\r\n" + 
						"            FROM airports ap LEFT OUTER JOIN retail_shops r on ap.ID = r.AIRPORT_ID\r\n" + 
						"                              INNER JOIN parkings p on ap.ID = p.AIRPORT_ID);";
				output = manager.createNativeQuery(query).getResultList();
				for(int i = 0; i < output.size(); i++)
				{
					for (Object column: (Object[])output.get(i)) {
			            System.out.print(column + ", ");
			        }
					System.out.println();
				}
				break;
			case 2:
				query = "SELECT a.AIRPORTNAME, (PRICE * NUMOFSPACES) + SUM(rs.rent) + SUM(pr.flowageFee + pr.landingFee + pr.feePerPassenger * p.PASSENGERCAP) AS 'REVENUE'\r\n" + 
						"  FROM airports a INNER JOIN plane_revenues pr ON a.ID = pr.AIRPORT_ID\r\n" + 
						"                  INNER JOIN planes p ON pr.PLANE_ID = p.ID\r\n" + 
						"                  INNER JOIN parkings prk ON a.ID = prk.AIRPORT_ID\r\n" + 
						"                  INNER JOIN retail_shops rs ON a.ID = rs.AIRPORT_ID\r\n" + 
						"                      GROUP BY a.AIRPORTNAME\r\n" + 
						"                        HAVING REVENUE > 500000;";
						output = manager.createNativeQuery(query).getResultList();
				for(int i = 0; i < output.size(); i++)
				{
					for (Object column: (Object[])output.get(i)) {
			            System.out.print(column + ", ");
			        }
					System.out.println();
				}
				break;
			case 3:
				query = "SELECT p.*, (p.PASSENGERCAP - r.PASSENGERCOUNT) AS 'EMPTY_SEATS'\r\n" + 
						"  FROM airlines a INNER JOIN planes p ON a.ID = p.AIRLINE_ID\r\n" + 
						"                  INNER JOIN plane_revenues r on p.ID = r.PLANE_ID\r\n" + 
						"                    WHERE p.PASSENGERCAP - r.PASSENGERCOUNT >= ALL(\r\n" + 
						"  SELECT (p2.PASSENGERCAP - r2.PASSENGERCOUNT)\r\n" + 
						"    FROM airlines a2 INNER JOIN planes p2 ON a2.ID = p2.AIRLINE_ID\r\n" + 
						"                    INNER JOIN plane_revenues r2 on p2.ID = r2.PLANE_ID);";
				output = manager.createNativeQuery(query).getResultList();
				for(int i = 0; i < output.size(); i++)
				{
					for (Object column: (Object[])output.get(i)) {
			            System.out.print(column + ", ");
			        }
					System.out.println();
				}
				break;
			case 4:
				String name;
				String type;
				Double rent;
				int airport;
				System.out.print("\nStore name (String): ");
				name = userInput.next();
				System.out.print("\nStore type (String): ");
				type = userInput.next();
				System.out.print("\nRent (double): ");
				rent = userInput.nextDouble();
				System.out.print("\nParent Airport ID (int): ");
				airport = userInput.nextInt();
				hw4application.createRetailShopEntity(name, type, rent, airport);
				break;
			case 5:
				int airline;
				System.out.print("\nAirline ID (int): ");
				airline = userInput.nextInt();
				System.out.println("Deleting this airline will remove the following children: ");
				for(int i = 0; i < INITIAL_AIRLINES[airline].getPlanes().size(); i++)
				{
					System.out.println("Plane: " + INITIAL_AIRLINES[airline].getPlanes().get(i).getTailNumber());
				}
				System.out.println("Press -1 to cancel, 1 to proceed");
				if(userInput.nextInt() == -1)
					break;
				else if(userInput.nextInt() == 1)
				{
					for(int i = 0; i < INITIAL_AIRLINES[airline].getPlanes().size(); i++)
						manager.remove(INITIAL_AIRLINES[airline].getPlanes().get(i));
				}
				
				manager.remove(INITIAL_AIRLINES[airline]);
				break;
			case 6:
				active = false;
				System.out.println("Exiting application...");
				break;
			}

		}

	}

	public void initializeAirportEntities() {
		LOGGER.fine("Creating Airport objects");

		for(int i = 0; i < AIRPORT_PLANEREVENUE.length; i++)
		{
			INITIAL_AIRPORTS[AIRPORT_PLANEREVENUE[i]].getPlaneRevenue().add(INITIAL_PLANEREVENUES[i]);
			INITIAL_PLANEREVENUES[i].setAirport(INITIAL_AIRPORTS[AIRPORT_PLANEREVENUE[i]]);
		}

		for(int i = 0; i < AIRPORT_AIRLINECONTRACTS.length; i++)
		{
			INITIAL_AIRPORTS[AIRPORT_AIRLINECONTRACTS[i]].getAirlineContracts().add(INITIAL_AIRLINECONTRACTS[i]);
			INITIAL_AIRLINECONTRACTS[i].setAirport(INITIAL_AIRPORTS[AIRPORT_AIRLINECONTRACTS[i]]);
		}

		for(int i = 0; i < AIRPORT_RETAILSHOPS.length; i++)
		{
			INITIAL_AIRPORTS[AIRPORT_RETAILSHOPS[i]].getRetailShops().add(INITIAL_RETAILSHOPS[i]);
			INITIAL_RETAILSHOPS[i].setAirport(INITIAL_AIRPORTS[AIRPORT_RETAILSHOPS[i]]);
		}

		for(int i = 0; i < INITIAL_AIRPORTS.length; i++)
		{
			Airport temp = INITIAL_AIRPORTS[i];
			temp.setParking(INITIAL_PARKING[i]);

			LOGGER.fine("Persisting Airport object to DB");
			this.entityManager.persist(temp);
		}
	}

	public void createAirportEntity(String code, String name, String address, String state, String country) {
		LOGGER.fine("Creating Airport object");

		Airport temp = new Airport(code, name, address, state, country);
		LOGGER.fine("Persisting Airport object to DB");
		this.entityManager.persist(temp);
	}

	public void initializeAirlineEntities()
	{
		LOGGER.fine("Creating Airline object");

		for(int i = 0; i < AIRLINES_PLANES.length; i++)
		{
			INITIAL_AIRLINES[AIRLINES_PLANES[i]].getPlanes().add(INITIAL_PLANES[i]);
			INITIAL_PLANES[i].setAirline(INITIAL_AIRLINES[AIRLINES_PLANES[i]]);
		}

		for(int i = 0; i < INITIAL_AIRLINES.length; i++)
		{
			LOGGER.fine("Persisting Airline object to DB");
			this.entityManager.persist(INITIAL_AIRLINES[i]);
		}
	}

	public void createAirlineEntity(String airlineName, String terminal, String phoneNumber, String airlineType) {
		LOGGER.fine("Creating Airline object");

		Airline temp = new Airline(airlineName, terminal, phoneNumber, airlineType);
		LOGGER.fine("Persisting Airline object to DB");
		this.entityManager.persist(temp);
	}

	public void initializeParkingEntities() {
		LOGGER.fine("Creating Parking object");

		for(int i = 0; i < INITIAL_PARKING.length; i++)
		{
			INITIAL_PARKING[i].setAirport(INITIAL_AIRPORTS[i]);
			LOGGER.fine("Persisting Parking object to DB");
			this.entityManager.persist(INITIAL_PARKING[i]);
		}
	}

	public void createParkingEntity(String location, double price, int numOfSpaces, String typeOfSpace) {
		LOGGER.fine("Creating Parking object");

		Parking temp = new Parking(location, price, numOfSpaces, typeOfSpace);
		LOGGER.fine("Persisting Parking object to DB");
		this.entityManager.persist(temp);
	}

	public void initializeAirlineContractEntities() {
		LOGGER.fine("Creating Airline Contract object");

		for(int i = 0; i < AIRLINECONTRACTS_AIRLINES.length; i++)
		{
			INITIAL_AIRLINES[AIRLINECONTRACTS_AIRLINES[i]].getAirlineContracts().add(INITIAL_AIRLINECONTRACTS[i]);
			INITIAL_AIRLINECONTRACTS[i].setAirline(INITIAL_AIRLINES[AIRLINECONTRACTS_AIRLINES[i]]);
		}


		for(int i = 0; i < INITIAL_AIRLINECONTRACTS.length; i++)
		{
			LOGGER.fine("Persisting Airline Contract object to DB");
			this.entityManager.persist(INITIAL_AIRLINECONTRACTS[i]);
		}
	}

	public void createAirlineContractEntity(Date startDate, Date endDate) {
		LOGGER.fine("Creating Airline Contract object");

		AirlineContract temp = new AirlineContract(startDate, endDate);
		LOGGER.fine("Persisting Airline Contract object to DB");
		this.entityManager.persist(temp);
	}

	public void initializePlaneEntities() {
		LOGGER.fine("Creating Plane object");

		for(int i = 0; i < PLANES_PLANEREVENUE.length; i++)
		{
			INITIAL_PLANES[PLANES_PLANEREVENUE[i]].getRevenue().add(INITIAL_PLANEREVENUES[i]);
			INITIAL_PLANEREVENUES[i].setPlane(INITIAL_PLANES[PLANES_PLANEREVENUE[i]]);
		}

		for(int i = 0; i < INITIAL_PLANES.length; i++)
		{
			LOGGER.fine("Persisting Plane object to DB");
			this.entityManager.persist(INITIAL_PLANES[i]);
		}
	}

	public void createPlaneEntity(String tailNumber, String model, String gateNumber, int passengerCap) {
		LOGGER.fine("Creating Plane object");

		Plane temp = new Plane(tailNumber, model, gateNumber, passengerCap);
		LOGGER.fine("Persisting Plane object to DB");
		this.entityManager.persist(temp);
	}

	public void initializePlaneRevenueEntities() {
		LOGGER.fine("Creating Plane Revenue object");

		for(int i = 0; i < INITIAL_PLANEREVENUES.length; i++)
		{
			LOGGER.fine("Persisting Plane Revenue object to DB");
			this.entityManager.persist(INITIAL_PLANEREVENUES[i]);
		}
	}

	public void createPlaneRevenueEntity(Date arrivalDate, Date departureDate, Time arrivalTime, Time departureTime, double flowageFee,
			double landingFee, int passengerCount, double feePerPassenger) {
		LOGGER.fine("Creating Plane Revenue object");

		PlaneRevenue temp = new PlaneRevenue(arrivalDate, departureDate, arrivalTime, departureTime, flowageFee,
				landingFee, passengerCount, feePerPassenger);
		LOGGER.fine("Persisting Plane Revenue object to DB");
		this.entityManager.persist(temp);
	}

	public void initializeRetailShopEntities() {
		LOGGER.fine("Creating Retail Shop object");

		for(int i = 0; i < INITIAL_RETAILSHOPS.length; i++)
		{
			LOGGER.fine("Persisting Retail Shop object to DB");
			this.entityManager.persist(INITIAL_RETAILSHOPS[i]);
		}
	}

	public void createRetailShopEntity(String shopName, String shopType, double rent, int airport) {
		LOGGER.fine("Creating Plane object");

		RetailShop temp = new RetailShop(shopName, shopType, rent);
		temp.setAirport(INITIAL_AIRPORTS[airport]);
		LOGGER.fine("Persisting Plane object to DB");
		this.entityManager.persist(temp);
	}



	private static final Airport[] INITIAL_AIRPORTS = new Airport[]{
			new Airport("LGB", "Long Beach Airport", "4100 Donald Douglas Dr", "Long Beach", "United States"),
			new Airport("SNA", "John Wayne Airport", "18601 Airport Way", "Santa Ana", "United States"),
			new Airport("LAX", "Los Angeles International Airport", "1 World Way", "Los Angeles", "United States"),
			new Airport("FUL", "Fullerton Minicipal Airport", "4011 W Commonwealth Ave", "Fullerton", "United States")
	};

	private static final Airline[] INITIAL_AIRLINES = new Airline[]{
			new Airline("JAL", "C", "623-555-5869", "International"),
			new Airline("Hawaiian Airlines", "A", "623-242-6839", "Domestic"),
			new Airline("United Airlines", "C", "714-333-1234", "Domestic"),
			new Airline("Air Canada", "A", "714-710-4444", "International"),
			new Airline("Emirates", "D", "714-420-9090", "International"),
			new Airline("Jet Blue", "A", "623-888-9999", "Domestic"),
			new Airline("KLM", "B", "623-354-3434", "International"),
	};

	private static final Parking[] INITIAL_PARKING = new Parking[]{
			new Parking("Donald Douglas Dr", 17.00, 575, "Garage"),
			new Parking("Thomas F. Riley Terminal", 20.00, 215, "Lot"),
			new Parking("W Century Blvd", 15.00, 350, "Lot"),
			new Parking("W Santa Fe Ave", 12.00, 435, "Garage")
	};

	private static final AirlineContract[] INITIAL_AIRLINECONTRACTS = new AirlineContract[]{
			new AirlineContract(new Date(112,3,23), new Date(113,3,23)),
			new AirlineContract(new Date(111,6,25),new Date(113,6,25)),
			new AirlineContract(new Date(110,7,15),new Date(111,7,15)),
			new AirlineContract(new Date(114,9,21),new Date(115,9,21)),
			new AirlineContract(new Date(117,10,15),new Date(118,10,15)),
			new AirlineContract(new Date(109,3,24),new Date(110,4,25)),
			new AirlineContract(new Date(105,2,28),new Date(106,2,28)),
			new AirlineContract(new Date(104,5,15),new Date(105,5,15)),
			new AirlineContract(new Date(103, 12,25), new Date(104,12,25)),
			new AirlineContract(new Date(102,11,11),new Date(103,11,11))
	};

	private static final Plane[] INITIAL_PLANES = new Plane[]{
			new Plane("A7434B", "BOEING 747", "C3", 200),
			new Plane("102VP","CESSNA 2002","A2",250),
			new Plane("1066E","PIPER 2006","B4",310),
			new Plane("107CH","BELL 1967","A5",300),
			new Plane("1072N","MOONEY 1981","B1",280),
			new Plane("10QL","FOSTER/BAKER 1985","C3",270),
			new Plane("10ZE","WALT SNYDER 2016","A2",310),
			new Plane("114DT","COMMANDER AIRCRAFT CO 1993","B6",170),
			new Plane("108GB","BEECH 1960","C8",100),
			new Plane("1082Z","GRUMAN AMERICAN AVN CORP 1976","A3",200),
			new Plane("20BDS", "BOEING 747", "C1", 200),
			new Plane("92POQ", "BOEING 747", "D4", 200)
	};

	private static final PlaneRevenue[] INITIAL_PLANEREVENUES = new PlaneRevenue[]{
			new PlaneRevenue(new Date(118,4,29), new Date(118,4,29), new Time(10,0,0), new Time(11,0,0), 1000.0, 800.0, 196, 23.00),
			new PlaneRevenue(new Date(118,5,19), new Date(118,5,19), new Time(11,1,1), new Time(12,4,2), 1220.0, 810.0, 210, 34.00),
			new PlaneRevenue(new Date(118,3,25), new Date(118,3,25), new Time(12,2,4), new Time(13,2,2), 1999.0, 550.0, 300, 12.00),
			new PlaneRevenue(new Date(118,1,30), new Date(118,1,30), new Time(13,1,5), new Time(14,1,3), 1245.0, 900.0, 250, 65.00),
			new PlaneRevenue(new Date(118,1,11), new Date(118,1,11), new Time(13,1,6), new Time(14,2,3), 1221.0, 910.0, 250, 54.00),
			new PlaneRevenue(new Date(118,2,12), new Date(118,2,12), new Time(11,1,6), new Time(15,5,3), 1277.0, 666.0, 170, 33.02),
			new PlaneRevenue(new Date(118,3,13), new Date(118,3,13), new Time(10,2,2), new Time(16,1,3), 6245.0, 500.0, 88, 29.10),
			new PlaneRevenue(new Date(118,4,14), new Date(118,4,14), new Time(13,1,5), new Time(17,4,3), 1235.0, 300.0, 80, 23.00),
			new PlaneRevenue(new Date(118,5,15), new Date(118,5,15), new Time(12,3,3), new Time(18,4,3), 2288.0, 200.0, 75, 67.21),
			new PlaneRevenue(new Date(118,6,16), new Date(118,6,16), new Time(12,1,5), new Time(19,3,3), 3145.0, 100.0, 100, 99.11),
			new PlaneRevenue(new Date(118,5,29), new Date(118,5,29), new Time(10,0,0), new Time(11,0,0), 5435.0, 655.0, 160, 62.00),
			new PlaneRevenue(new Date(118,6,19), new Date(118,6,19), new Time(11,1,1), new Time(12,4,2), 5345.0, 433.0, 140, 23.00),
			new PlaneRevenue(new Date(118,4,25), new Date(118,4,25), new Time(12,2,4), new Time(13,2,2), 3452.0, 733.0, 290, 54.00),
			new PlaneRevenue(new Date(118,7,30), new Date(118,7,30), new Time(13,1,5), new Time(14,1,3), 6534.0, 155.0, 247, 26.00),
			new PlaneRevenue(new Date(118,10,11), new Date(118,10,11), new Time(13,1,6), new Time(14,2,3), 6532.0, 844.0, 248, 72.00),
			new PlaneRevenue(new Date(118,5,12), new Date(118,5,12), new Time(11,1,6), new Time(15,5,3), 4352.0, 783.0, 198, 23.02),
			new PlaneRevenue(new Date(118,4,13), new Date(118,4,13), new Time(10,2,2), new Time(16,1,3), 2341.0, 744.0, 97, 52.10),
			new PlaneRevenue(new Date(118,7,14), new Date(118,7,14), new Time(13,1,5), new Time(17,4,3), 6543.0, 722.0, 92, 26.00),
			new PlaneRevenue(new Date(118,12,15), new Date(118,12,15), new Time(12,3,3), new Time(18,4,3), 1246.0, 888.0, 72, 43.21),
			new PlaneRevenue(new Date(118,11,16), new Date(118,11,16), new Time(12,1,5), new Time(19,3,3), 6542.0, 777.0, 96, 52.11)
	};

	private static final RetailShop[] INITIAL_RETAILSHOPS = new RetailShop[]{
			new RetailShop("Walmart", "Retail", 9000.01),
			new RetailShop("McDonalds", "Restaurant", 25000.22),
			new RetailShop("Burger King", "Restaurant", 39344.20),
			new RetailShop("Rolex", "Retail", 9999.99),
			new RetailShop("Hugo Boss", "Retail", 6666.66),
			new RetailShop("CNN", "Newstand", 34234.00),
			new RetailShop("Sunglass Hut", "Retail", 23432.22),
			new RetailShop("Brookstone", "Retail", 12452.25),
			new RetailShop("XpresSpa", "Service", 43532.99),
			new RetailShop("Inmotion", "Retail", 32236.66)
	};

	/**
	 * Indices of the X whose child is Y. X[0]'s child is Y[0]
	 */
	private static final int[] AIRPORT_PARKING = new int[]{
			0, 1, 2, 3
	};

	private static final int[] AIRPORT_AIRLINECONTRACTS = new int[]{
			0,0,1,1,2,2,2,3,3,3
	};

	private static final int[] AIRPORT_RETAILSHOPS = new int[]{
			0,0,0,1,1,1,2,2,2,2
	};

	private static final int[] AIRPORT_PLANEREVENUE = new int[]{
			0,0,0,0,0,1,1,1,1,1,2,2,2,2,2,3,3,3,3,3
	};

	private static final int[] AIRLINECONTRACTS_AIRLINES = new int[]{
			0,0,1,1,2,2,3,3,4,4
	};

	private static final int[] AIRLINES_PLANES = new int[]{
			0,0,0,1,1,1,2,3,4,5,6,6
	};

	private static final int[] PLANES_PLANEREVENUE = new int[]{
			0,0,1,1,2,2,3,3,4,4,5,5,6,6,7,7,8,8,9,9
	};
}
