package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bll.Branche;

public class BrancheDAL {

	public static List<Branche> getAll() throws SQLException {
		Connection conn = Database.connect();

		String query = "SELECT * FROM Branche";
		Statement st = conn.createStatement();

		ResultSet rs = st.executeQuery(query);

		// iterate through the java resultset
		List<Branche> branchen = new ArrayList<Branche>();

		while (rs.next()) {
			String id = rs.getString("id");
			String bezeichnung = rs.getString("Bezeichnung");

			Branche b = new Branche(id, bezeichnung);

			branchen.add(b);
		}
		st.close();
		conn.close();

		return branchen;
	}

	public static Branche getById(String id) throws Exception {
		Connection conn = Database.connect();

		String query = "SELECT * FROM Branche WHERE id = '" + id + "'";

		Statement st = conn.createStatement();

		ResultSet rs = st.executeQuery(query);

		// iterate through the java resultset
		Branche result = null;
		while (rs.next()) {
			String description = rs.getString("Bezeichnung");
			result = new Branche(id, description);
			// print the results
		}
		st.close();

		if(result == null)
			throw new Exception("Branche nicht gefunden");
		return result;
	}

	public static void update(String id, Branche new_bra) throws Exception {
		int result = 0;
		try {
			Connection conn = Database.connect();
			
			String query = "update Branche set Bezeichnung = ? " + " where id = ?";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, new_bra.getBezeichnung());

			preparedStmt.setString(2, id);

			result = preparedStmt.executeUpdate();

			conn.close();

			if (result == 0)
				throw new Exception("Fehler beim Updaten der Branche mit der ID " + new_bra.getId());
		} catch (Exception e) {
			System.err.println("Ein Fehler ist aufgetreten! ");
			System.err.println(e.getMessage());
			throw e;
		}

	}

	public static void delete(String id) {
		try {
			Connection conn = Database.connect();

			String query = "delete from Branche where id = ?";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, id);

			preparedStmt.execute();

			conn.close();
		} catch (Exception e) {
			System.err.println("Ein Fehler ist aufgetreten! ");
			System.err.println(e.getMessage());
		}
	}

	public static void create(Branche new_bra) {
		try {

			Connection conn = Database.connect();

			String query = " insert into Branche values (?, ?)";

			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, new_bra.getId());
			preparedStmt.setString(2, new_bra.getBezeichnung());

			preparedStmt.execute();

			conn.close();
		} catch (Exception e) {
			System.err.println("Ein Fehler ist aufgetreten!");
			System.err.println(e.getMessage());
		}
	}
}
