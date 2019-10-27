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

	public static Aktion getById(String id) throws Exception {
		Connection conn = Database.connect();

		//String query = "SELECT id_aktion, id_location, beschreibung, punkte, aktiv FROM Aktion";
		
		String query = "SELECT id_aktion, id_location, beschreibung, punkte, aktiv FROM Aktion WHERE id = '" + id + "'";

		Statement st = conn.createStatement();

		ResultSet rs = st.executeQuery(query);

		// iterate through the java resultset
		Aktion result = null;
		while (rs.next()) {
			String beschreibung = rs.getString("beschreibung");
			String id_location = rs.getString("id_location");
			int punkte = rs.getInt("punkte");
			String aktiv = rs.getString("aktiv");

			Aktion a = new Aktion();
			a.setId(id);
			a.setBezeichnung(beschreibung);
			a.setPunkte(punkte);
			if (aktiv.equals("J"))
				a.setAktiv(true);
			else
				a.setAktiv(false);


			result = a;
		}
		st.close();

		if (result == null)
			throw new Exception("Prämie nicht gefunden");
		return result;
	}

	public static void update(String id, Aktion new_akt) throws Exception {
		int result = 0;
		try {
			Connection conn = Database.connect();

			
			//String query = "SELECT id_aktion, id_location, beschreibung, punkte, aktiv FROM Aktion";


			String query = "update Aktion set id_location = ?, beschreibung = ?, punkte = ?, aktiv = ? where id = ?";
			
			
			PreparedStatement preparedStmt = conn.prepareStatement(query);

			preparedStmt.setString(1, new_akt.getLocation().toString());
			preparedStmt.setString(2, new_akt.getBezeichnung());
			preparedStmt.setInt(3, new_akt.getPunkte());
			if (new_akt.isAktiv())
				preparedStmt.setString(4, "J");
			else
				preparedStmt.setString(4, "N");

			preparedStmt.setString(5, new_akt.getId());
			

			result = preparedStmt.executeUpdate();

			conn.close();

			if (result == 0)
				throw new Exception("Fehler beim Updaten der Location mit der ID " + new_akt.getId());
		} catch (Exception e) {
			System.err.println("Ein Fehler ist aufgetreten! ");
			System.err.println(e.getMessage());
			throw e;
		}

	}

	public static void delete(String id) {
		try {
			Connection conn = Database.connect();

			String query = "delete from Aktion where id = ?";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, id);

			preparedStmt.execute();

			conn.close();
		} catch (Exception e) {
			System.err.println("Ein Fehler ist aufgetreten! ");
			System.err.println(e.getMessage());
		}
	}

	public static void create(Aktion new_akt) {
		try {
			//String query = "SELECT id_aktion, id_location, beschreibung, punkte, aktiv FROM Aktion";

			Connection conn = Database.connect();


			String query = " insert into Aktion values (?, ?, ?, ?, ?)";

			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, new_akt.getId());
			preparedStmt.setString(2, new_akt.getLocation().toString());
			preparedStmt.setString(3, new_akt.getBezeichnung());
			preparedStmt.setInt(4, new_akt.getPunkte());
			if (new_akt.isAktiv())
				preparedStmt.setString(5, "J");
			else
				preparedStmt.setString(5, "N");


			System.out.println(preparedStmt.toString());
			preparedStmt.execute();

			conn.close();
		} catch (Exception e) {
			System.err.println("Ein Fehler ist aufgetreten!");
			System.err.println(e.getMessage());
		}
	}
}
