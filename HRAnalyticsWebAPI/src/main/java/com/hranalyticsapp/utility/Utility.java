package com.hranalyticsapp.utility;

import com.hranalyticsapp.model.base.ErrorResponse;

public class Utility {

	public static ErrorResponse getErrorResponse(int errorCode, String errorMsg) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorCode(errorCode);
		errorResponse.setErrorMessage(errorMsg);
		return errorResponse;
	}

}
