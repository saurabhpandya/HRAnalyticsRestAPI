package com.hranalyticsapp.model.base;

public class CommonResponse {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CommonResponse [message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
