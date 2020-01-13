package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class Database {
	//private final Books books = new Books();
	private static Database instance = null;
	private static Connection conn;
	
	private Database() {
	}

	public static Database newInstance() {
		if (instance == null) {
			instance = new Database();
		}
	
		return instance;
	}
	

 	
 	public static Connection connect() throws SQLException {
	      try {
		      String myDriver = "oracle.jdbc.driver.OracleDriver";
		      //212.152.179.117
		      //10.0.6.111
		      String myUrl = "jdbc:oracle:thin:@212.152.179.117:1521:ora11g";
		      Class.forName(myDriver);
		      conn = DriverManager.getConnection(myUrl, "d5a17", "d5a");
		      return conn;
		} catch (Exception e) {
			System.err.println("Got an exception when connecting to DB! ");
			System.err.println(e.getMessage());
			return null;
		} 
	}
}
