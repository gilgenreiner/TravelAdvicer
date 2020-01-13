package bll;

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
		return id.toString();
	}

	public void setId(String firebaseId) {
		this.id = firebaseId;
	}

}
