package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Path;

import bll.Besuch;
import bll.Branche;
import bll.Error404;
import bll.Location;
import bll.Point;
import service.LocationList;

public class BesuchDAL {
	public static List<Besuch> getAll(String besucherId, String locationId) throws SQLException, Error404 {
		Connection conn = Database.connect();

		String query = "SELECT * FROM Location_besuch ";
		if(besucherId != null) {
			query += " where id_besucher = " + besucherId + "' "; 
			if(locationId != null) {
				query += " and id_location = '" + locationId + "' "; 
			}
		}
		else if (locationId != null) {
			query += " where id_location = '" + locationId + "' "; 
		}
		
		Statement st = conn.createStatement();

		ResultSet rs = st.executeQuery(query);

		// iterate through the java resultset
		List<Besuch> besuche = new ArrayList<Besuch>();

		while (rs.next()) {

			String id = rs.getString("id_besuch");
			String besucherid = rs.getString("id_besucher");
			String locationid = rs.getString("id_location");

			Timestamp ts = rs.getTimestamp("zeitpunkt");
			
			Besuch b = new Besuch();
			b.setBesucherId(besucherid);
			b.setLocationId(locationid);
			b.setId(id);
			
			besuche.add(b);
		}
		
		st.close();
		conn.close();

		return besuche;
	}
	
	
	public static void create(Besuch new_besuch) throws Exception {
		try {
			Connection conn = Database.connect();

			String query = " insert into Location_besuch values (?, ?, ?, ?)";

			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, new_besuch.getId().toString());
			preparedStmt.setString(3, new_besuch.getBesucherId().toString());
			preparedStmt.setString(2, new_besuch.getLocationId().toString());
			preparedStmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
						
			preparedStmt.execute();

			
			conn.close();
		} catch (Exception e) {
			System.err.println("Ein Fehler ist aufgetreten!");
			System.err.println(e.getMessage());
			throw e;
		}
	}

}
