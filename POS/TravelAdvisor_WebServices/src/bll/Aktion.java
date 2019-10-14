package bll;

public class Aktion {
	private String id;
	private String bezeichnung;
	private String punkte;
	private boolean aktiv;
	private Location location;
	
	public Aktion(String id, Location location, String bezeichnung, String punkte, boolean aktiv) {
		super();
		this.id = id;
		this.bezeichnung = bezeichnung;
		this.punkte = punkte;
		this.aktiv = aktiv;
		this.location = location;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBezeichnung() {
		return bezeichnung;
	}
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	public String getPunkte() {
		return punkte;
	}
	public void setPunkte(String punkte) {
		this.punkte = punkte;
	}
	public boolean isAktiv() {
		return aktiv;
	}
	public void setAktiv(boolean aktiv) {
		this.aktiv = aktiv;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	
	
}
