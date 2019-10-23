package dal;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import bll.Aktion;
import bll.Besitzer;
import bll.Branche;
import bll.Location;
import bll.Point;
import bll.UUIDParseException;

public class PraemienDAL {

	public static List<Aktion> getAll() throws SQLException, UUIDParseException {
		Connection conn = Database.connect();

		System.out.println("connected");
		String query = "SELECT id_aktion, id_location, beschreibung, punkte, aktiv FROM Aktion";
		Statement st = conn.createStatement();

		ResultSet rs = st.executeQuery(query);

		// iterate through the java resultset
		List<Aktion> aktionen = new ArrayList<Aktion>();

		while (rs.next()) {
			// Bezeichnung
			String id = rs.getString("id_aktion");
			String bezeichnung = rs.getString("beschreibung");
			String id_location = rs.getString("id_location");
			int punkte = rs.getInt("punkte");
			String aktiv = rs.getString("aktiv");

			Aktion a = new Aktion();
			a.setId(id);
			a.setLocation(id_location);
			a.setBezeichnung(bezeichnung);
			a.setPunkte(punkte);
			if (aktiv.equals("J"))
				a.setAktiv(true);
			else
				a.setAktiv(false);


			aktionen.add(a);
		}
		st.close();
		conn.close();

		return aktionen;
	}

	public static Location getById(String id) throws Exception {
		Connection conn = Database.connect();

		String query = "SELECT  id, id_besitzer, bezeichnung, punkte, aktiv, tl.koordinaten.SDO_POINT.X as X, "
				+ "tl.koordinaten.SDO_POINT.Y as Y FROM TravelLocation tl WHERE tl.id = '" + id + "'";

		Statement st = conn.createStatement();

		ResultSet rs = st.executeQuery(query);

		// iterate through the java resultset
		Location result = null;
		while (rs.next()) {
			String bezeichnung = rs.getString("Bezeichnung");
			String id_besitzer = rs.getString("id_besitzer");
			int punkte = rs.getInt("punkte");
			String aktiv = rs.getString("aktiv");
			double X = rs.getDouble("X");
			double Y = rs.getDouble("Y");

			Location l = new Location();
			l.setId(id);
			// ToDO:
			l.setBesitzer(null);
			l.setBezeichnung(bezeichnung);
			l.setPunkte(punkte);
			if (aktiv.equals("J"))
				l.setAktiv(true);
			else
				l.setAktiv(false);

			l.setKoordinaten(new Point(X, Y));

			result = l;
		}
		st.close();

		if (result == null)
			throw new Exception("Branche nicht gefunden");
		return result;
	}

	public static void update(String id, Location new_loc) throws Exception {
		int result = 0;
		try {
			Connection conn = Database.connect();

			System.out.println("X: " + new_loc.getKoordinaten().getX());
        	System.out.println("Y: " + new_loc.getKoordinaten().getY());
        	
			String koordinaten = "SDO_GEOMETRY( " + "2001, " + "NULL," + "SDO_POINT_TYPE(?, ?, NULL)," + "NULL,"
					+ "NULL)";

			String query = "update TravelLocation set id_besitzer = ?, bezeichnung = ?, punkte = ?, aktiv = ?, "
					+ " set koordinaten = " + koordinaten + " where id = ?";
			
			
			PreparedStatement preparedStmt = conn.prepareStatement(query);

			preparedStmt.setString(1, new_loc.getBesitzer().getId().toString());
			preparedStmt.setString(2, new_loc.getBezeichnung());
			preparedStmt.setInt(3, new_loc.getPunkte());
			if (new_loc.isAktiv())
				preparedStmt.setString(4, "J");
			else
				preparedStmt.setString(4, "N");
			preparedStmt.setBigDecimal(5, new BigDecimal("10.000"));
			preparedStmt.setBigDecimal(6, new BigDecimal("10.000"));

			preparedStmt.setString(7, new_loc.getId());
			

			result = preparedStmt.executeUpdate();

			conn.close();

			if (result == 0)
				throw new Exception("Fehler beim Updaten der Location mit der ID " + new_loc.getId());
		} catch (Exception e) {
			System.err.println("Ein Fehler ist aufgetreten! ");
			System.err.println(e.getMessage());
			throw e;
		}

	}

	public static void delete(String id) {
		try {
			Connection conn = Database.connect();

			String query = "delete from TravelLocation where id = ?";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, id);

			preparedStmt.execute();

			conn.close();
		} catch (Exception e) {
			System.err.println("Ein Fehler ist aufgetreten! ");
			System.err.println(e.getMessage());
		}
	}

	public static void create(Location new_loc) {
		try {
			Connection conn = Database.connect();

			String koordinaten = "SDO_GEOMETRY( " + "2001, " + "NULL," + "SDO_POINT_TYPE(?, ?, NULL)," + "NULL,"
					+ "NULL)";

			String query = " insert into TravelLocation values (?, ?, ?, ?, ?, " + koordinaten + ")";

			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, new_loc.getId());
			preparedStmt.setString(2, new_loc.getBesitzer().getId().toString());
			preparedStmt.setString(3, new_loc.getBezeichnung());
			preparedStmt.setInt(4, new_loc.getPunkte());
			if (new_loc.isAktiv())
				preparedStmt.setString(5, "J");
			else
				preparedStmt.setString(5, "N");

			preparedStmt.setDouble(6, new_loc.getKoordinaten().getX());
			preparedStmt.setDouble(7, new_loc.getKoordinaten().getY());

			System.out.println(preparedStmt.toString());
			preparedStmt.execute();

			conn.close();
		} catch (Exception e) {
			System.err.println("Ein Fehler ist aufgetreten!");
			System.err.println(e.getMessage());
		}
	}
}
