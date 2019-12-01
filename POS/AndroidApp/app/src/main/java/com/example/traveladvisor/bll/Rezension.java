package com.example.traveladvisor.bll;

import java.sql.Timestamp;
import java.util.UUID;

public class Rezension {
	private UUID id;
	private UUID locationid;
	private UUID besucherid;
	private int bewertung;
	private Timestamp timestamp;
	
	public Rezension() {
		
	}
	
	public UUID getLocationid() {
		return locationid;
	}
	public void setLocationid(UUID locationid) {
		this.locationid = locationid;
	}
	public void setLocationid(String locationid) {
		this.locationid = UUID.fromString(locationid);
	}
	public UUID getBesucherid() {
		return besucherid;
	}
	public void setBesucherid(UUID besucherid) {
		this.besucherid = besucherid;
	}
	public void setBesucherid(String besucherid) {
		this.besucherid = UUID.fromString(besucherid);
	}
	public int getBewertung() {
		return bewertung;
	}
	public void setBewertung(int bewertung) {
		this.bewertung = bewertung;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public void generateId() {
		this.id = UUID.randomUUID();
	}
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public void setId(String id) {
		this.id = UUID.fromString(id);
	}
	
	
	
}	
