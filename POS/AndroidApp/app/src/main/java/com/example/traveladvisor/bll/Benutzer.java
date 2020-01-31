package com.example.traveladvisor.bll;

import java.util.UUID;

public class Benutzer {
	private String id;

	public Benutzer() {
	}

	public Benutzer(String id) {
		super();
		this.id = id;
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
			this.id = firebaseId;
		} catch (Exception e) {
			this.id = null;
		}
	}

}
