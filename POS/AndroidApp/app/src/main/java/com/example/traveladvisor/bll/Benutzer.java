package com.example.traveladvisor.bll;

import java.util.UUID;

public class Benutzer {
	private UUID id;

	public Benutzer() {
	}

	public Benutzer(String id) {
		super();
		this.id = UUID.fromString(id);
	}

	public String getId() throws Exception {
		try {
			return id.toString();
		}
		catch(Exception ex) {
			throw new Exception("Benutzer hat keine gülige UUID");
		}
	}

	public void setId(String firebaseId) {
		try {
			this.id = UUID.fromString(firebaseId);
		} catch (Exception e) {
			this.id = null;
		}
	}

	public void generateUUID() {
		this.id = UUID.randomUUID();
	}

}
