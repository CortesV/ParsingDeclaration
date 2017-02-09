package com.devcortes.components.service;

/**
 * Class that process exception when service cannot parse declarion page
 * 
 * @author cortes
 *
 */
public class ParsingException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String errorMessage;

	public ParsingException(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	

}
