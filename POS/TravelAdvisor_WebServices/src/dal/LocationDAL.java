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

import bll.Besitzer;
import bll.Branche;
import bll.Location;
import bll.Point;
import bll.UUIDParseException;
import bll.Location;

public class LocationDAL {

	public static List<Location> getAll() throws SQLException, UUIDParseException {
		Connection conn = Database.connect();

		String query = "SELECT id, id_besitzer, bezeichnung, beschreibung,  punkte, aktiv, tl.koordinaten.SDO_POINT.X as X, "
				+ "tl.koordinaten.SDO_POINT.Y as Y, UTL_RAW.CAST_TO_VARCHAR2(bild) as img FROM TravelLocation tl";
		Statement st = conn.createStatement();

		ResultSet rs = st.executeQuery(query);

		// iterate through the java resultset
		List<Location> Locationn = new ArrayList<Location>();

		while (rs.next()) {

			String id = rs.getString("id");
			String bezeichnung = rs.getString("bezeichnung");
			String beschreibung = rs.getString("beschreibung");

			String id_besitzer = rs.getString("id_besitzer");
			int punkte = rs.getInt("punkte");
			String aktiv = rs.getString("aktiv");
			double X = rs.getDouble("X");
			double Y = rs.getDouble("Y");
			String img = rs.getString("img");
			
			Location l = new Location();
			l.setId(id);
			// ToDO:
			l.setBesitzer(null);
			l.setBezeichnung(bezeichnung);
			l.setBeschreibung(beschreibung);
			l.setPunkte(punkte);
			if (aktiv.equals("J"))
				l.setAktiv(true);
			else
				l.setAktiv(false);

			l.setKoordinaten(new Point(X, Y));
			System.out.println(l.getId().toString());
			l.setBranchen(BrancheDAL.get(l));
			l.setImg(img);

			Locationn.add(l);
		}
		st.close();
		conn.close();

		return Locationn;
	}

	public static List<Location> test() throws UUIDParseException {

		List<Location> Locationn = new ArrayList<Location>();

		Location l = new Location();
		l.setId(UUID.randomUUID().toString());
		l.setBesitzer(new Besitzer(UUID.randomUUID().toString()));
		l.setBezeichnung("Raceres");
		l.setBeschreibung("Essen");
		l.setPunkte(9500);
		l.setAktiv(true);
		List<Branche> branchen = new ArrayList<Branche>();
		branchen.add(new Branche(UUID.randomUUID().toString(), "Gastronomie"));
		l.setBranchen(branchen);
		l.setKoordinaten(new Point(46.604887, 13.869746));

		Locationn.add(l);

		return Locationn;
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

			l.setBranchen(BrancheDAL.get(l));
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

			System.out.println("Besitzer: " + new_loc.getBesitzer().getId().toString());
			System.out.println("Bezeichnung: " + new_loc.getBezeichnung());
			System.out.println("Punkte: " + new_loc.getPunkte());

			String koordinaten = "SDO_GEOMETRY( " + "2001, " + "NULL," + "SDO_POINT_TYPE(?, ?, NULL)," + "NULL,"
					+ "NULL)";

			String query = "update TravelLocation set id_besitzer = ?, bezeichnung = ?, beschreibung = ?, punkte = ?, aktiv = ? where id = ?";

			PreparedStatement preparedStmt = conn.prepareStatement(query);

			preparedStmt.setString(1, new_loc.getBesitzer().getId().toString());
			preparedStmt.setString(2, new_loc.getBezeichnung());
			preparedStmt.setString(3, new_loc.getBeschreibung());
			preparedStmt.setInt(4, new_loc.getPunkte());
			if (new_loc.isAktiv())
				preparedStmt.setString(5, "J");
			else
				preparedStmt.setString(5, "N");
			// preparedStmt.setBigDecimal(5, new BigDecimal("10.000"));
			// preparedStmt.setBigDecimal(6, new BigDecimal("10.000"));

			preparedStmt.setString(6, id);

			result = preparedStmt.executeUpdate();

			conn.close();

			if (result == 0)
				throw new Exception("Fehler beim Updaten der Location mit der ID " + new_loc.getId());

			new_loc.setId(id);
			
			BrancheDAL.removeBranchen(id);

			for(Branche b : new_loc.getBranchen()) 
				LocationDAL.addBranche(new_loc, b);
			
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

			String query = " insert into TravelLocation values (?, ?, ?, ?, ?, ?, " + koordinaten + ", utl_raw.cast_to_raw(?))";

			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, new_loc.getId().toString());
			preparedStmt.setString(2, new_loc.getBesitzer().getId().toString());
			preparedStmt.setString(3, new_loc.getBezeichnung());
			preparedStmt.setString(4, new_loc.getBeschreibung());
			preparedStmt.setInt(5, new_loc.getPunkte());
			if (new_loc.isAktiv())
				preparedStmt.setString(6, "J");
			else
				preparedStmt.setString(6, "N");

			preparedStmt.setDouble(7, new_loc.getKoordinaten().getX());
			preparedStmt.setDouble(8, new_loc.getKoordinaten().getY());
			String img = new_loc.getImg();
			if(img != null)
				preparedStmt.setString(9, img);
			else
				preparedStmt.setString(9, "empty blob");
			
			preparedStmt.execute();

			
			conn.close();

			System.out.println("Location erstellt!");
			for (Branche b : new_loc.getBranchen()) {
				System.out.println("Branche " + b + " wird zur Location " + new_loc + " hinzugefügt . . . ");
				LocationDAL.addBranche(new_loc, b);
				
			}
		} catch (Exception e) {
			System.err.println("Ein Fehler ist aufgetreten!");
			System.err.println(e.getMessage());
		}
	}

	public static void addBranche(Location l, Branche branche) {
		try {
			Connection conn = Database.connect();

			String query = " insert into Location_Branche_Zuordnung values (?, ?)";

			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, l.getId().toString());
			preparedStmt.setString(2, branche.getId().toString());

			preparedStmt.execute();

			conn.close();
			System.out.println("Branche " + branche+ " erfolgreich zur Location " + l + " hinzugefügt!");
		} catch (Exception e) {
			System.err.println("Ein Fehler ist aufgetreten!");
			System.err.println(e.getMessage());
		}
	}

}
