package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bll.Besitzer;
import bll.Branche;

public class BesitzerDAL {
	public static List<Besitzer> getAll() throws SQLException {
		Connection conn = Database.connect();

		String query = "SELECT * FROM Besitzer";
		Statement st = conn.createStatement();

		ResultSet rs = st.executeQuery(query);

		List<Besitzer> list = new ArrayList<Besitzer>();

		while (rs.next()) {
			String id = rs.getString("id");

			Besitzer b = new Besitzer(id);

			list.add(b);
		}
		st.close();
		conn.close();

		return list;
	}

	public static void delete(String id) {
		try {
			Connection conn = Database.connect();

			String query = "delete from Besitzer where id = ?";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, id);

			preparedStmt.execute();

			conn.close();
		} catch (Exception e) {
			System.err.println("Ein Fehler ist aufgetreten! ");
			System.err.println(e.getMessage());
		}
	}

	public static void create(Besitzer new_bes) {
		try {
			Connection conn = Database.connect();

			String query = " insert into Besitzer " + " values (?)";

			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, new_bes.getId().toString());

			preparedStmt.execute();

			conn.close();
		} catch (Exception e) {
			System.err.println("Ein Fehler ist aufgetreten!");
			System.err.println(e.getMessage());
		}
	}
}
