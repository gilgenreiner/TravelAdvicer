package bll;

import java.sql.Date;
import java.sql.Timestamp;

public class Taetigkeit {


	
	private String beschreibung;
	private int punkte;
	private Timestamp zeitpunkt;
	
	public Taetigkeit() {}
	
	
	public String getBeschreibung() {
		return beschreibung;
	}
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}
	public int getPunkte() {
		return punkte;
	}
	public void setPunkte(int punkte) {
		this.punkte = punkte;
	}


	public Timestamp getZeitpunkt() {
		return zeitpunkt;
	}


	public void setZeitpunkt(Timestamp zeitpunkt) {
		this.zeitpunkt = zeitpunkt;
	}

	
	
}
