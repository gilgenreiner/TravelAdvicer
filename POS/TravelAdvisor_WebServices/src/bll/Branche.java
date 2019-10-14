package bll;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Branche {
	
	private UUID id;
	private String bezeichnung;
	
	public Branche() {}
	
	public Branche(String id, String bezeichnung) {
		super();
		this.id = UUID.fromString(id);
		this.bezeichnung = bezeichnung;
	}
	
	public Branche(String bezeichnung) {
		super();
		this.bezeichnung = bezeichnung;
	}
	public String getId() {
		if(id == null)
			return "";
		return id.toString();
	}
	public void setId(String id) {
		this.id = UUID.fromString(id);
	}
	public String getBezeichnung() {
		return bezeichnung;
	}
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	public void generateUUID() {
		this.id = UUID.randomUUID();
	}
}
