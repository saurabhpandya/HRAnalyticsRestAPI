package com.hranalyticsapp.model.base;

public class BaseResponse<T> {

	T data;
	ErrorResponse error;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	
	
	public ErrorResponse getError() {
		return error;
	}

	public void setError(ErrorResponse error) {
		this.error = error;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BaseResponse [data=");
		builder.append(data);
		builder.append(", error=");
		builder.append(error);
		builder.append("]");
		return builder.toString();
	}

	

}
