package bll;

import java.util.ArrayList;
import java.util.List;

public class Besucher extends Benutzer{

	private List<Aktion> gekaufteOffeneAktionen;
	private List<Location> besuchteLocations;
	private int punkte;
	
	public Besucher() {
		super();
		gekaufteOffeneAktionen = new ArrayList<Aktion>();
		besuchteLocations = new ArrayList<Location>();
		// TODO Auto-generated constructor stub
	}

	public Besucher(String firebaseId) {
		super(firebaseId);
		gekaufteOffeneAktionen = new ArrayList<Aktion>();
		besuchteLocations = new ArrayList<Location>();
		// TODO Auto-generated constructor stub
	}

	public void addAktion(Aktion aktion) {
		this.gekaufteOffeneAktionen.add(aktion);
	}
	
	public List<Aktion> getAktionen() {
		return this.gekaufteOffeneAktionen;
	}
	
	public void locationBesucht(Location location) {
		this.besuchteLocations.add(location);
	}

	public int getPunkte() {
		return punkte;
	}

	public void setPunkte(int punkte) {
		this.punkte = punkte;
	}
	
	public void addPunkte(int punkte) {
		this.punkte += punkte;
	}
	
}
