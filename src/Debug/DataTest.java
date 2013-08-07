package Debug;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataTest {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	public static void main(String[] args) throws Exception{
		DataTest data = new DataTest();
		String state = "SELECT r.VehicleSno, CarModel, Type, CarLocation, Color, HourlyRate, DailyRate, Seating_Capacity, Transmission_Type, BluetoothConnectivity, AuxiliaryCable "+
				"FROM reservation r, car c "+
				"WHERE NOT EXISTS (SELECT * FROM reservation r, car c WHERE c.VehicleSno = r.VehicleSno AND ((r.PickUpDateTime BETWEEN '2013-04-21 00:00:00.' AND '2013-04-21 00:00:00.' ) OR (r.ReturnDateTime BETWEEN '2013-05-21 00:00:00' AND '2013-05-21 00:00:00' )))"+
				"AND c.UnderMaintenanceFlag = 0 "+
				"AND c.CarLocation = 'CULC' "+
				"AND c.CarModel = 'Ford Focus' "+
				"AND c.Type = 'Sedan'";
		data.readDataBase(state);
	}
	public void readDataBase(String state) throws Exception{
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_32","cs4400_Group_32","6ug5mecJ");
			if(!connect.isClosed()) System.out.println("Connected To Database....");
			statement = connect.createStatement();
			resultSet = statement.executeQuery(state);
			//statement.executeUpdate(state);
			writeMetaData(resultSet);
			writeResultSet(resultSet);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally {
			try {
				if(connect != null)
					statement.close();
					connect.close();
					System.out.println("Close Database");
				} catch(SQLException e) {}
		}
	}
	private void writeMetaData(ResultSet resultSet) throws SQLException {
	    //   Now get some metadata from the database
	    // Result set get the result of the SQL query
	    
	    System.out.println("The columns in the table are: ");
	    
	    System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
	    for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
	    		System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
	    	}
	  }

	  private void writeResultSet(ResultSet resultSet) throws SQLException {
	    // ResultSet is initially before the first data set
		  int counter=1;
		  while (resultSet.next()) {
			  for(int i=1;i<=resultSet.getMetaData().getColumnCount();i++){
			      String str = resultSet.getString(i);
			      System.out.print("["+str+"]");
			  }System.out.println();
	    }
	  }
}
