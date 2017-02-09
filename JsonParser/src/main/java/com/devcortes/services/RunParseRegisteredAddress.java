package com.devcortes.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devcortes.components.entity.DeclarantRegisteredAddress;
import com.devcortes.components.service.ParsingException;
import com.devcortes.controller.JsonParseController;

/**
 * This service return register address of official in json format
 * 
 * @author cortes
 *
 */
@Service
public class RunParseRegisteredAddress {

	private static Logger log = Logger.getLogger(JsonParseController.class);

	@Autowired
	private ParsedService parsedService;

	/**
	 * Return register address of official in json format by GET request
	 * 
	 * @param declarationId
	 *            declarationId - id of declaration of some official
	 * @return return - location of some official
	 */
	public DeclarantRegisteredAddress runParser(String declarationId) {

		DeclarantRegisteredAddress declarantLocation = null;

		try {

			declarantLocation = parsedService.parseDeclarationPage(declarationId);

		} catch (ParsingException e) {

			declarantLocation = new DeclarantRegisteredAddress(e.getErrorMessage());
			log.error(e.getErrorMessage());
		}
System.out.println();
		return declarantLocation;
	}

}
