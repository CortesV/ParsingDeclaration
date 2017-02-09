package com.devcortes.components.entity;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Entity that store location of official
 * 
 * @author cortes
 *
 */

@JsonInclude(Include.NON_NULL)
public class DeclarantRegisteredAddress {

	private static final int LENGHT = 4;
	private static final String SUCCESSFUL_RESPONSE_STATUS = "Successful";

	private String city;
	private String district;
	private String region;
	private String country;
	private String responseStatus;

	public DeclarantRegisteredAddress(String responseStatus) {
		this.responseStatus = responseStatus;
	}

	/***
	 * Constructor with parameters that receive list with address of official
	 * and create object DeclarantRegisteredAddress
	 * 
	 * @param valuesOfRegisterAddress
	 *            valuesOfRegisterAddress list with address of official
	 */
	public DeclarantRegisteredAddress(List<String> valuesOfRegisterAddress) {

		int emptyCount = LENGHT - valuesOfRegisterAddress.size();
		String[] arrayEmptyCount = new String[emptyCount];
		valuesOfRegisterAddress.addAll(1, Arrays.asList(arrayEmptyCount));

		this.city = valuesOfRegisterAddress.get(0);
		this.district = valuesOfRegisterAddress.get(1);
		this.region = valuesOfRegisterAddress.get(2);
		this.country = valuesOfRegisterAddress.get(3);
		this.responseStatus = SUCCESSFUL_RESPONSE_STATUS;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}

}
