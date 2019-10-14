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
	

 	
 	public static Connection connect() {
	      try {
		      String myDriver = "oracle.jdbc.driver.OracleDriver";
		      String myUrl = "jdbc:oracle:thin:@192.168.128.152:1521:ora11g";
		      Class.forName(myDriver);
		      return DriverManager.getConnection(myUrl, "d5a17", "d5a");
		} catch (Exception e) {
			System.err.println("Got an exception when connecting to DB! ");
			System.err.println(e.getMessage());
			return null;
		}
	}
}
