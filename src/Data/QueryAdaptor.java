package Data;

import java.sql.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;
/**
 * This class will handle all of the sql queries 
 */
public class QueryAdaptor{
	private static Connection connect=null;
	private static Statement statement=null;
	private static ResultSet resultSet=null;
	private static ResultSet resultSet2=null;

	/**
	 * finds if the user exists in the table
	 * @param usn
	 * @return whether the user exists or not
	 * @throws Exception
	 */
	public static boolean findUser(String usn) throws Exception{
		ResultSet test = query("SELECT Username FROM user WHERE Username = '"+usn+"'");
		return test.first();
	}

	/**
	 * finds if there the username matches the password
	 * @param usn
	 * @param pwd
	 * @return whether the user is mapped to the password
	 * @throws Exception
	 */
	public static boolean matchPassword(String usn, String pwd) throws Exception{
		ResultSet test = query("SELECT Username,Password FROM user WHERE Username = '"+usn+"' AND " +
				"Password = '"+pwd+"'");
		return test.first();
	}

	public static String[] getMemberInfo(String usn) throws Exception{
		String[] ret = new String[9];
		Arrays.fill(ret, " ");
		ResultSet resultSet = query("SELECT * FROM member WHERE Username = '"+usn+"'");
		resultSet.first();
		for(int i=1;i<=resultSet.getMetaData().getColumnCount();i++){
			ret[i-1] = resultSet.getString(i);
		}return ret;
	}

	/**
	 * obtains the usertype form the username 
	 * map:
	 * 	1  Employee
	 *  2  Admin
	 *  3  Member
	 * @param usn
	 * @return
	 * @throws Exception
	 */
	public static char getMemberType(String usn) throws Exception{
		ResultSet test = query("SELECT GTCR_Employee_Flag,GTCR_Admin_Flag,GTCR_Member FROM user WHERE Username = '"+usn+"'");
		test.first(); // should only have one truple
		if(test.getInt(1)==1) return '1';
		else if(test.getInt(2)==1) return '2';
		else return '3';
	}

	/**
	 * tests if the use has a card
	 * @param UserName
	 * @return
	 * @throws Exception 
	 */
	public static boolean hasCard(String UserName) throws Exception{
		ResultSet test = query("SELECT CardNo FROM member WHERE Username = '"+UserName+"'");
		return test.first();
	}

	/*
	 * test whether a car model exists in a specific location 
	 * if true then we can't add it there
	 */
	public static boolean carExists(String loc, String carModel) throws Exception{
		resultSet = query("SELECT * FROM car WHERE CarLocation = '"+loc+"' AND CarModel = '"+carModel+"'");
		return resultSet.first();
	}

	/*
	 * gets the credit card info
	 */
	public static String[] pullCardInfo(String CardNo) throws Exception{
		resultSet = query("Select Name,CVV,ExpiryDate,BillingAdd FROM creditcard WHERE CardNo = '"+CardNo+"'");
		resultSet.first();
		String[] ret = new String[resultSet.getMetaData().getColumnCount()];
		for(int i=1;i<=resultSet.getMetaData().getColumnCount();i++){
			ret[i-1] = resultSet.getString(i);
		}return ret;
	}

	/**
	 * gets all the location names
	 * @return
	 * @throws Exception
	 */
	public static String[] getLocationName() throws Exception{
		String[] ret;
		LinkedList<String> store = new LinkedList<String>();
		resultSet = query("SELECT LocationName FROM location");
		for (;resultSet.next();) {
			for(int i=1;i<=resultSet.getMetaData().getColumnCount();i++){
				store.add(resultSet.getString(i));
			}
		} ret = new String[store.size()];
		for(int i=0;!store.isEmpty();i++){
			ret[i] = store.poll();
		} return ret;
	}

	/**
	 * returns all the car types on the car table
	 * @return
	 * @throws Exception
	 */
	public static String[] getCarTypeList() throws Exception{
		String[] ret;
		LinkedList<String> store = new LinkedList<String>();
		resultSet = query("SELECT Type FROM car");
		for (;resultSet.next();) {
			for(int i=1;i<=resultSet.getMetaData().getColumnCount();i++){
				store.add(resultSet.getString(i));
			}
		} ret = new String[store.size()];
		for(int i=0;!store.isEmpty();i++){
			ret[i] = store.poll();
		} return ret;
	}

	/**
	 * @param loc
	 * @param carModel
	 * @return list of all cars available according to location and carModel
	 * @throws Exception
	 */
	public static String[] getCarTypeList(String loc, String carModel) throws Exception{
		String[] ret;
		LinkedList<String> store = new LinkedList<String>();
		resultSet = query("SELECT DISTINCT Type FROM car WHERE CarLocation = '"+loc+"' AND CarModel = '"+carModel+"'");
		for (;resultSet.next();) {
			for(int i=1;i<=resultSet.getMetaData().getColumnCount();i++){
				store.add(resultSet.getString(i));
			}
		} ret = new String[store.size()];
		for(int i=0;!store.isEmpty();i++){
			ret[i] = store.poll();
		} return ret;
	}


	/**
	 * returns all the cars on the car table
	 * @return
	 * @throws Exception
	 */
	public static String[] getCarList() throws Exception{
		String[] ret;
		LinkedList<String> store = new LinkedList<String>();
		resultSet = query("SELECT DISTINCT CarModel FROM car");
		for (;resultSet.next();) {
			for(int i=1;i<=resultSet.getMetaData().getColumnCount();i++){
				store.add(resultSet.getString(i));
			}
		} ret = new String[store.size()];
		for(int i=0;!store.isEmpty();i++){
			ret[i] = store.poll();
		} return ret;
	}

	/**
	 * return cars in a specific location
	 * @param loc
	 * @return
	 * @throws Exception
	 */
	public static String[] getCarList(String loc) throws Exception{
		String[] ret;
		LinkedList<String> store = new LinkedList<String>();
		resultSet = query("SELECT DISTINCT CarModel FROM car WHERE CarLocation = '"+loc+"'");
		for (;resultSet.next();) {
			for(int i=1;i<=resultSet.getMetaData().getColumnCount();i++){
				store.add(resultSet.getString(i));
			}
		} ret = new String[store.size()];
		for(int i=0;!store.isEmpty();i++){
			ret[i] = store.poll();
		} return ret;
	}


	/**
	 * returns list of available cars given the following filtering parameters
	 * @param loc The location of the car
	 * @param model The model of the car.PickUpDateTime BETWEEN '"+startDate+" "+startTime+"' AND '"returnDate+" "+returnTime+"' ) OR (r.ReturnDateTime BETWEEN '"+startDate+" "+startTime+"' AND '2013-05-21 00:00:00
	 * @param type The type of the car
	 * @param startDate The starting rental date of the potential car
	 * @param startTime The starting time of the potential car
	 * @param returnDate The returning rental date of the potential car
	 * @param returnTime The returning rental time of the potential car
	 * @return 2-D list of all available cars with their full information in each inner array
	 * @throws Exception
	 */
	public static String[][] getCarAvailabilityList(String username, String loc, String model, String type, String startDate, String startTime, String returnDate, String returnTime) throws Exception{
		resultSet = query("SELECT DISTINCT c.VehicleSno, CarModel, Type, CarLocation, Color, HourlyRate, DailyRate, Seating_Capacity, Transmission_Type, BluetoothConnectivity, AuxiliaryCable "+
				"FROM car c "+
				"WHERE c.VehicleSno NOT IN (SELECT r.VehicleSno FROM reservation r WHERE ((r.PickUpDateTime BETWEEN '"+startDate+" "+startTime+"' AND '"+returnDate+" "+returnTime+"' ) OR (r.ReturnDateTime BETWEEN '"+startDate+" "+startTime+"' AND '"+returnDate+" "+returnTime+"' ))) "+
		//		"AND c.UnderMaintenanceFlag = 0 "+
				"AND c.CarLocation = '"+loc+"' "+
				"AND c.CarModel = '"+model+"'");
		LinkedList<String[]> temp = new LinkedList<String[]>();
		int currind = 0;
		while(resultSet.next()){
			temp.add(new String[resultSet.getMetaData().getColumnCount()]);
			for(int i=1;i<=resultSet.getMetaData().getColumnCount();i++){
				temp.get(currind)[i-1] = (resultSet.getString(i));
			}currind++;
		}
		ResultSet plan = query("SELECT Discount,MonthlyPayment,AnnualFees FROM drivingplan");
		int drivingPlan[][] = new int[3][3],i;
		for(i = 0,plan.next();i<drivingPlan.length;i++,plan.next()){
			for(int j=0;j<drivingPlan.length;j++){
				drivingPlan[i][j] = Integer.parseInt(plan.getString(j+1));
			}
		}ResultSet userPlan = query("SELECT DrivingPlan FROM member WHERE Username = '"+username+"'");
		userPlan.first();
		StringTokenizer st = new StringTokenizer(userPlan.getString(1));
		String usnplan = st.nextToken();
		int planID;
		if(usnplan.equals("Occasional")) planID = 0;
		else if(usnplan.equals("Frequent")) planID = 1;
		else planID = 2;
		String[][] ret = new String[temp.size()][temp.get(0).length+3];
		for(i=0;i<ret.length;i++)
			for(int j = 0;j<6;j++)
				ret[i][j] = temp.get(i)[j];
		for(i=0;i<ret.length;i++){
			ret[i][6]= Double.toString((.15*Double.parseDouble(temp.get(i)[5])));
			ret[i][7] = Double.toString(.1*Double.parseDouble(temp.get(i)[5]));
		} for(i=0;i<ret.length;i++){
			for(int j=0;j<5;j++){
				ret[i][8+j] = temp.get(i)[temp.get(i).length-1-5+j];
			}
		}int totHours = Util.getDifference(returnDate, startDate, returnTime, startTime);
		for(i=0;i<ret.length;i++){
			ret[i][ret[i].length-1] = ""+((totHours/24)*Double.parseDouble(ret[i][8]) + (totHours%24)*Double.parseDouble(ret[i][5+planID]));
		}

		return ret;
	}
	
	public static String[][] getOtherCarAvailabilityList(String username, String loc, String model, String type, String startDate, String startTime, String returnDate, String returnTime) throws Exception{
		resultSet = query("SELECT DISTINCT c.VehicleSno, CarModel, Type, CarLocation, Color, HourlyRate, DailyRate, Seating_Capacity, Transmission_Type, BluetoothConnectivity, AuxiliaryCable "+
				"FROM car c "+
				"WHERE c.VehicleSno NOT IN (SELECT r.VehicleSno FROM reservation r WHERE ((r.PickUpDateTime BETWEEN '"+startDate+" "+startTime+"' AND '"+returnDate+" "+returnTime+"' ) OR (r.ReturnDateTime BETWEEN '"+startDate+" "+startTime+"' AND '"+returnDate+" "+returnTime+"' ))) "+
				"AND (c.CarLocation <> '"+loc+"' "+
				"OR c.CarModel <> '"+model+"')");
		LinkedList<String[]> temp = new LinkedList<String[]>();
		int currind = 0;
		while(resultSet.next()){
			temp.add(new String[resultSet.getMetaData().getColumnCount()]);
			for(int i=1;i<=resultSet.getMetaData().getColumnCount();i++){
				temp.get(currind)[i-1] = (resultSet.getString(i));
			}currind++;
		}
		ResultSet plan = query("SELECT Discount,MonthlyPayment,AnnualFees FROM drivingplan");
		int drivingPlan[][] = new int[3][3],i;
		for(i = 0,plan.next();i<drivingPlan.length;i++,plan.next()){
			for(int j=0;j<drivingPlan.length;j++){
				drivingPlan[i][j] = Integer.parseInt(plan.getString(j+1));
			}
		}ResultSet userPlan = query("SELECT DrivingPlan FROM member WHERE Username = '"+username+"'");
		userPlan.first();
		StringTokenizer st = new StringTokenizer(userPlan.getString(1));
		String usnplan = st.nextToken();
		int planID;
		if(usnplan.equals("Occasional")) planID = 0;
		else if(usnplan.equals("Frequent")) planID = 1;
		else planID = 2;
		String[][] ret = new String[temp.size()][temp.get(0).length+3];
		for(i=0;i<ret.length;i++)
			for(int j = 0;j<6;j++)
				ret[i][j] = temp.get(i)[j];
		for(i=0;i<ret.length;i++){
			ret[i][6]= Float.toString(((float).15*Float.parseFloat(temp.get(i)[5])));
			ret[i][7] = Float.toString((float).1*Float.parseFloat(temp.get(i)[5]));
		} for(i=0;i<ret.length;i++){
			for(int j=0;j<5;j++){
				ret[i][8+j] = temp.get(i)[temp.get(i).length-1-5+j];
			}
		}int totHours = Util.getDifference(returnDate, startDate, returnTime, startTime);
		for(i=0;i<ret.length;i++){
			ret[i][ret[i].length-1] = ""+((totHours/24)*Double.parseDouble(ret[i][8]) + (totHours%24)*Double.parseDouble(ret[i][5+planID]));
		}
		return ret;
	}


	/**
	 * finds the vsn from the VehicleSno and location
	 * @param loc
	 * @param ModelName
	 * @return
	 * @throws Exception
	 */
	public static String findVsn(String loc, String ModelName) throws Exception{
		resultSet = query("SELECT DISTINCT VehicleSno FROM car WHERE CarLocation = '"+loc+"' AND CarModel = '"+ModelName+"'");
		resultSet.first();
		String ret = resultSet.getString(1);
		return ret;
	}

	/**
	 * see if there is a maintenance request 
	 */
	public static boolean hasReservation(String usn) throws Exception{
		resultSet = query("SELECT * FROM reservation WHERE Username = '"+usn+"'");
		return resultSet.first();
	}

	//NOTE: This doesn't take into account that a user can have multiple reservations, so it will just return
	//info for the first reservation in the resultSet
	public static String[] getReservationInfo(String usn) throws Exception{
		resultSet = query("SELECT CarModel, CarLocation, ReturnDateTime "+
				"FROM reservation r, car c "+
				"WHERE r.VehicleSno = c.VehicleSno "+
				"AND r.Username = '"+usn+"' "+
				"AND r.PickupDateTime<NOW() "+
				"AND r.ReturnDateTime>ADDTIME(NOW(),'-02:00:00')");
		if(!resultSet.first())
			return null;
		String[] ret = new String[resultSet.getMetaData().getColumnCount()];
		for(int i=1;i<=ret.length;i++){
			ret[i-1] = resultSet.getString(i);
		} return ret;
	}


	/**
	 * This queries for the current reservation of a user and formats it for the Rental Info Screen
	 * @param usn String username of person who made reservation
	 * @return 2D string of curr reservations
	 * @throws Exception
	 */
	public static String[][] getCurrReservationInfo(String usn) throws Exception{
		resultSet = query("SELECT r.ReturnDateTime, r.PickUpDateTime, c.CarModel, r.ReservationLocation, r.EstimatedCost, r.ResID "+
				"FROM reservation r, car c "+
				"WHERE r.VehicleSno = c.VehicleSno "+
				"AND r.Username = '"+usn+"' "+
				"AND r.PickupDateTime<NOW() "+
				"AND r.ReturnDateTime>ADDTIME(NOW(),'-02:00:00')");
		if(!resultSet.first())
			return null;
		int numRows=1;
		while(resultSet.next()){
			numRows++;
		}
		resultSet.first();
		String[][]ret= new String[numRows][resultSet.getMetaData().getColumnCount()];
		String[] firstRow = new String[resultSet.getMetaData().getColumnCount()];
		for(int i=1;i<=firstRow.length;i++){
			firstRow[i-1] = resultSet.getString(i);
			if (i==firstRow.length){
				firstRow[1]= firstRow[1]+"-"+firstRow[0];
			}
		}
		ret[0] = firstRow;
		int index=1;
		while(resultSet.next()){
			String[] row = new String[resultSet.getMetaData().getColumnCount()];
			for(int i=1;i<=row.length;i++){
				row[i-1] = resultSet.getString(i);
				if (i==row.length){
					row[1]= row[1]+"-"+row[0];
				}
			}
			ret[index]=row;
			index++;
		}
		return ret;
	}


	/**
	 * This queries for the current reservation of a user and formats it for the Rental Info Screen
	 * @param usn String username of person who made reservation
	 * @return 2D array of all past reservations
	 * @throws Exception
	 */
	public static String[][] getPastReservationInfo(String usn) throws Exception{
		resultSet = query("SELECT r.ReturnDateTime, r.PickUpDateTime, c.CarModel, r.ReservationLocation, r.EstimatedCost, r.ResID "+
				"FROM reservation r, car c "+
				"WHERE r.VehicleSno = c.VehicleSno "+
				"AND r.Username = '"+usn+"' "+
				"AND r.ReturnDateTime<ADDTIME(NOW(),'-02:00:00')");
		if(!resultSet.first())
			return null;
		int numRows=1;
		while(resultSet.next()){
			numRows++;
		}
		resultSet.first();
		String[][]ret= new String[numRows][resultSet.getMetaData().getColumnCount()];
		String[] firstRow = new String[resultSet.getMetaData().getColumnCount()];
		for(int i=1;i<=firstRow.length;i++){
			firstRow[i-1] = resultSet.getString(i);
			if (i==firstRow.length){
				firstRow[1]= firstRow[1]+"-"+firstRow[0];
			}
		}
		ret[0] = firstRow;
		int index=1;
		while(resultSet.next()){
			String[] row = new String[resultSet.getMetaData().getColumnCount()];
			for(int i=1;i<=row.length;i++){
				row[i-1] = resultSet.getString(i);
				if (i==row.length){
					row[1]= row[1]+"-"+row[0];
				}
			}
			ret[index]=row;
			index++;
		}
		return ret;
	}


	public static String[][] getRevenueGenerated() throws Exception{
		resultSet = query("SELECT reservation.VehicleSno, car.type, car.CarModel, SUM(EstimatedCost) , SUM(LateFees) FROM reservation, car WHERE car.VehicleSno = reservation.VehicleSno AND PickUpDateTime >=  \"2013-01-01\" AND PickUpDateTime <=  \"2013-03-31\" GROUP BY reservation.VehicleSno");
		LinkedList<String[]> ret = new LinkedList<String[]>();
		int currind = 0;
		while(resultSet.next()){
			ret.add(new String[resultSet.getMetaData().getColumnCount()]);
			for(int i=1;i<=resultSet.getMetaData().getColumnCount();i++){
				ret.get(currind)[i-1] = resultSet.getString(i);
			}currind++;
		}String[][] ans = new String[ret.size()][resultSet.getMetaData().getColumnCount()];
		for(int i = 0;i<ans.length;i++){
			ans[i] = ret.poll();
		}return ans;
	}
	public static String[][] getMaintenanceHistoryReport() throws Exception{
//        resultSet = query("SELECT m.VehicleSno, m.RequestDateTime, m.Username, p.Problem "+
//                "FROM maintenance_request m, maintenance_request_problems p "+
//                "WHERE m.VehicleSno = p.VehicleSno");
		
		resultSet = query("SELECT CarModel, maintenance_request.RequestDateTime, maintenance_request.Username, maintenance_request_problems.Problem FROM maintenance_request, car, maintenance_request_problems "+
				"WHERE maintenance_request_problems.VehicleSno = maintenance_request.VehicleSno "+
				"AND maintenance_request.RequestDateTime = maintenance_request_problems.RequestDateTime "+
				" AND car.VehicleSno = maintenance_request.VehicleSno "+
				"ORDER BY maintenance_request_problems.`VehicleSno` , maintenance_request_problems.Problem");
        LinkedList<String[]> ret = new LinkedList<String[]>();
        int currind = 0;
        while(resultSet.next()){
            ret.add(new String[resultSet.getMetaData().getColumnCount()]);
            for(int i=1;i<=resultSet.getMetaData().getColumnCount();i++){
                ret.get(currind)[i-1] = resultSet.getString(i);
            }currind++;
        }String[][] ans = new String[ret.size()][resultSet.getMetaData().getColumnCount()];
        for(int i = 0;i<ans.length;i++){
            ans[i] = ret.poll();
        }return ans;
    }

    public static String[][] getFrequentUsersReport() throws Exception{
        resultSet = query("Select member.Username, member.DrivingPlan, COUNT(ResID)/3 AS NoResPerMonth " +
                "FROM member, reservation " +
                "WHERE member.Username = reservation.Username " +
                "AND PickUpDateTime >=  '2013-01-01' "+
                "AND PickUpDateTime <=  '2013-03-31' "+
                "GROUP BY member.Username "+
                "ORDER BY NoResPerMonth DESC "+
                "LIMIT 5");
        LinkedList<String[]> ret = new LinkedList<String[]>();
        int currind = 0;
        while(resultSet.next()){
            ret.add(new String[resultSet.getMetaData().getColumnCount()]);
            for(int i=1;i<=resultSet.getMetaData().getColumnCount();i++){
                ret.get(currind)[i-1] = resultSet.getString(i);
            }currind++;
        }String[][] ans = new String[ret.size()][resultSet.getMetaData().getColumnCount()];
        for(int i = 0;i<ans.length;i++){
            ans[i] = ret.poll();
        }return ans;
    }
	
    public static String[][] getLocationPreferenceReport() throws Exception{
        resultSet = query("SELECT MONTH(  `PickUpDateTime` ) AS " +
                "MONTH ,  `ReservationLocation` , COUNT( * ) , SUM( HOUR( TIMEDIFF(  `ReturnDateTime` ,  `PickUpDateTime` ) ) ) " +
                "FROM reservation " +
                "WHERE PickUpDateTime >=  '2013-01-01' " +
                "AND PickUpDateTime <=  '2013-03-31' "+
                "GROUP BY MONTH "+
                "ORDER BY MONTH");
        LinkedList<String[]> ret = new LinkedList<String[]>();
        int currind = 0;
        while(resultSet.next()){
            ret.add(new String[resultSet.getMetaData().getColumnCount()]);
            for(int i=1;i<=resultSet.getMetaData().getColumnCount();i++){
                ret.get(currind)[i-1] = resultSet.getString(i);
            }currind++;
        }String[][] ans = new String[ret.size()][resultSet.getMetaData().getColumnCount()];
        for(int i = 0;i<ans.length;i++){
            ans[i] = ret.poll();
        }return ans;
    }
    
    public static String getResID(String usn,String vsn) throws Exception{
    	resultSet = query("SELECT ResID FROM reservation WHERE Username = '"+usn+"' AND "+
    			"VehicleSno = '"+vsn+"'");
    	System.out.println(usn+" "+vsn);
    	if(!resultSet.first())
    		return "";
    	return resultSet.getString(1);
    }
    

    public static String[] getUserAffected(String vsn,String urt,String name) throws Exception{
    	resultSet = query("SELECT m.Username, r.PickupDateTime, r.ReturnDateTime, m.EmailAddress, m.PhoneNo "+
    	 "FROM reservation r, member m "+
    	 "WHERE r.Username = m.Username "+
    	 "AND r.PickupDateTime < '"+urt+"' "+
    	 "AND r.Username <> '"+name+"' "+
    	 "AND r.VehicleSno = '"+vsn+"'");
    	if(!resultSet.first())
    		return null;
    	String ret[] = new String[resultSet.getMetaData().getColumnCount()];
    	for(int i=1;i<=resultSet.getMetaData().getColumnCount();i++){
    		ret[i-1] = resultSet.getString(i);
    	} return ret;
    }
  


	/**********************************back end data access methods****************************/
	/**
	 * connects us to the server
	 */
	public static void connect(){
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_32","cs4400_Group_32","6ug5mecJ");
			if(!connect.isClosed()) System.out.println("Connected To Database....");
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	/**
	 * queries the sql statements
	 * @param state
	 * @return the raw result set
	 * @throws Exception
	 */
	private static ResultSet query(String state) throws Exception{
		try{
			statement = connect.createStatement();
			resultSet = statement.executeQuery(state);
		}catch(Exception e){
			System.out.println(e.getMessage());
		} return resultSet;
	}
	/**
	 * closes the connection and statement 
	 */
	public static void close(){
		try {
			if(connect != null)
				statement.close();
			connect.close();
			System.out.println("Close Database");
		} catch(SQLException e) {}
	}
}
