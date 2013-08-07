package Debug;

import java.sql.*;
import java.util.*;
public class SQLExec {
	
	private static Connection connect = null;
	private static Statement statement = null;
	private static ResultSet resultSet = null;
	
	public static void main(String[] args) throws Exception{
		Scanner in = new Scanner(System.in);
		String type,state;
		while(!(type = in.next()).equals("end")){
			if(type.equals("write")) writeDataBase(in.nextLine());
			else readDataBase(in.nextLine());
		}
	}
	public static void readDataBase(String state) throws Exception{
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_32","cs4400_Group_32","6ug5mecJ");
			if(!connect.isClosed()) System.out.println("Connected To Database....");
			statement = connect.createStatement();
			resultSet = statement.executeQuery(state);
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
	public static void writeDataBase(String state) throws Exception{
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connect = DriverManager.getConnection("jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_32","cs4400_Group_32","6ug5mecJ");
			if(!connect.isClosed()) System.out.println("Connected To Database....");
			statement = connect.createStatement();
			statement.executeUpdate(state);
			System.out.println("Database Updated");
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
	private static void writeMetaData(ResultSet resultSet) throws SQLException {
	    //   Now get some metadata from the database
	    // Result set get the result of the SQL query
	    
	    System.out.println("The columns in the table are: ");
	    
	    System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
	    for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
	    		System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
	    	}
	  }

	  private static void writeResultSet(ResultSet resultSet) throws SQLException {
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
