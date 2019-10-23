package bll;

public class Aktion {
	private String id;
	private String bezeichnung;
	private int punkte;
	private boolean aktiv;
	private String locationId;
	
	public Aktion(String id, String locationId, String bezeichnung, int punkte, boolean aktiv) {
		super();
		this.id = id;
		this.bezeichnung = bezeichnung;
		this.punkte = punkte;
		this.aktiv = aktiv;
		this.locationId = locationId;
	}
	
	public Aktion() {
		// TODO Auto-generated constructor stub
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
	public int getPunkte() {
		return punkte;
	}
	public void setPunkte(int punkte) {
		this.punkte = punkte;
	}
	public boolean isAktiv() {
		return aktiv;
	}
	public void setAktiv(boolean aktiv) {
		this.aktiv = aktiv;
	}
	public String getLocation() {
		return locationId;
	}
	public void setLocation(String locationId) {
		this.locationId = locationId;
	}
	
	
}
