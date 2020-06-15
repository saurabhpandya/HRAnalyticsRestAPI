package com.hranalyticsapp.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "users_master")
public class UsersMster {
	@Id
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Users [id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
