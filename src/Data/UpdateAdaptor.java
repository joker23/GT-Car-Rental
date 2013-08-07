package Data;
import java.sql.*;
import java.util.StringTokenizer;

/**
 * this class will handle table updates
 * 
 * DATE FORMAT: YYYY-MM-DD
 * DATE TIME FORMAT: YYYY-MM-DD HH:MM:SS
 */
public class UpdateAdaptor {
	private static Connection connect=null;
	private static Statement statement=null;
	/**
	 * makes a user
	 * @param username
	 * @param password
	 * @param emp
	 * @param adm
	 * @param mem
	 * @throws Exception
	 */
	public static void makeUser(String username,String password,int emp,int adm,int mem) throws Exception{
		update("INSERT INTO user VALUES ('"+username+"', '"+password+"', "+emp+", "+adm+", "+mem+")");
	}
	
	/**
	 * makes member info for the first time log in
	 * @param usn
	 * @param fn
	 * @param ln
	 * @param mi
	 * @param addr
	 * @param pn
	 * @param ema
	 * @param cardno
	 * @param dp
	 * @throws Exception
	 */
	public static void makeMemberInfo(String usn,String fn,String ln,String mi,String addr,String pn,
			String ema,String cardno,String dp) throws Exception{
		update("INSERT INTO member VALUES('"+usn+"', '"+fn+"', '"+ln+"', '"+mi+"', '"+addr+"', '"+pn+"', '"+ema+"', '"+
			cardno+"', '"+dp+"')");
	}
	
	/**
	 * update existing member information
	 * @param usn
	 * @param fn
	 * @param ln
	 * @param mi
	 * @param addr
	 * @param pn
	 * @param ema
	 * @param cardno
	 * @param dp
	 * @throws Exception
	 */
	public static void updateMemberInfo(String usn,String fn,String ln,String mi,String addr,String pn,
			String ema,String cardno,String dp) throws Exception{
		update("UPDATE member "+
				"SET FirstName = '"+fn+"', "+
				"LastName = '"+ln+"', "+
				"MiddleInit = '"+mi+"', "+
				"Address = '"+addr+"', "+
				"PhoneNo = '"+pn+"', "+
				"EmailAddress = '"+ema+"', "+
				"CardNo = '"+cardno+"', "+
				"DrivingPlan = '"+dp+"' "+
				"WHERE Username = '"+usn+"'");
	}
	
	/**
	 * create a credit card field
	 * @param CardNo
	 * @param name
	 * @param cvv
	 * @param exdate
	 * @param billingAddr
	 * @throws Exception
	 */
	public static void createCardInfo(String CardNo, String name, String cvv, String exdate,String billingAddr) throws Exception{
		update("INSERT INTO creditcard VALUES ('"+CardNo+
				"', '"+ name +
				"', '"+ cvv +
				"', '"+ exdate +
				"', '"+ billingAddr+"')");
	}
	
	/**
	 * 
	 * @param CardNo
	 * @param name
	 * @param cvv
	 * @param exdate
	 * @param billingAddr
	 * @throws Exception
	 */
	public static void updateCardInfo(String CardNo, String name, String cvv, String exdate,String billingAddr) throws Exception{
		update("UPDATE creditcard "+
				"SET Name = '"+name+"', "+
				"CVV = '"+cvv+"', "+
				"ExpiryDate = '"+exdate+"', "+
				"BillingAdd = '"+billingAddr+"' "+
				"WHERE CardNo = '"+CardNo+"'");
	}
	
	/*
	 * adds a car into the database;
	 */
	public static void addCar(String vsn,String aux,String tt, String sc, String bt, String dr, String hr, 
			String color, String type, String model, String loc) throws Exception{
		update("INSERT INTO car VALUES ('" +
				vsn+"', "+
				aux+", "+
				tt+", '"+
				sc+"', "+
				bt+", "+
				dr+", "+
				hr+", '"+
				color+"', '"+
				type+"', '"+
				model+"', "+
				0+", '"+
				loc+"')");
	}
	/**
	 * makes a problem
	 * @param vsn
	 * @param requestTime
	 * @param problem
	 * @throws Exception
	 */
	public static void makeProblem(String vsn, String requestTime, String problem) throws Exception{
		update("INSERT INTO maintenance_request_problems VALUES ('"+
				vsn +"', '"+
				requestTime+"', '"+
				problem+"')");
	}
	
	public static void changeCarLocation(String currLoc,String newloc, String model,String type,String color,
			String sc,String tt) throws Exception{
		update("UPDATE car SET "+
				"CarLocation = '"+newloc+"' "+
				"WHERE CarLocation = '" + currLoc+"' AND "+
				"CarModel = '" + model +"' AND "+
				"Type = '"+type+"' AND "+
				"Color = '"+color+"' AND "+
				"Seating_Capacity = '"+sc+"'");
	}
	
	public static void addMaintenanceRequest(String vsn, String dateTime,String usn) throws Exception{
		update("INSERT INTO maintenance_request VALUES ('"+
				vsn+"', '"+
				dateTime+"', '"+
				usn+"')");
	}
	
	/*
	 * set maintenanceFlag
	 */
	public static void setMaintenanceFlag(String vsn) throws Exception{
		update("UPDATE car SET UnderMaintenanceFlag = "+1);
	}
	
	public static void updateLateTime(int h,String rid) throws Exception{
		update("UPDATE reservation "+
				"SET LateFees = "+h+"*50, LateBy = '"+h+"', ReturnDateTime = ADDDATE(ReturnDateTime, "+h+") "+
				"WHERE ResID = '"+rid+"'");
	}
	
	public static void createReservation(String username, String pickUpDateTime, String returnDateTime, String location, String vehicleSno, String estimatedCost) throws Exception{
		update("INSERT INTO reservation VALUES (DEFAULT,'" +
				username+"', '"+
				pickUpDateTime+"', '"+
				returnDateTime+"', '"+
				"0"+"', 'N/A', '"+
				estimatedCost+"', '0', '"+
				location+"', '"+
				vehicleSno+"')");
	}
	
	public static void removeReservation(String rid) throws Exception{
		update("DELETE FROM reservation WHERE ResID = '"+rid+"'");
	}
	
	/*
	 * updates the return time for a car reservation and creates an entry in reservation_extended_time
	 * @param resID
	 * @param pastReturnDateTime
	 * @param newReturnDateTime
	 * @throws Exception
	 */
	public static void updateReservationReturnTime(String resID, String pastReturnDateTime, String newReturnDateTime) throws Exception{
		update("UPDATE reservation "+
				"SET ReturnDateTime = '"+newReturnDateTime+"' "+
				"WHERE ResID = '"+resID+"'");
		update("INSERT INTO reservation_extended_time VALUES ('"+
				resID+"', DATEDIFF('"+newReturnDateTime+"', '"+pastReturnDateTime+"'))");
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
	 * runs the update statement
	 * @param state
	 * @throws Exception
	 */
	private static void update(String state) throws Exception{
		try{
			statement = connect.createStatement();
			statement.executeUpdate(state);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
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
