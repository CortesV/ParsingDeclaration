package com.devcortes.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import com.devcortes.components.entity.DeclarantRegisteredAddress;
import com.devcortes.components.service.ParsingException;

/**
 * Service that parse declaration of offial and return him location
 * 
 * @author cortes
 *
 */
@Service
public class ParsedService {

	private static Logger log = Logger.getLogger(ParsedService.class);
	private static final String SITE_WITH_DECLARATIONS = "https://public.nazk.gov.ua/declaration/";
	private static final String PATH_TO_REGISTER_ADDRESS_IN_PAGE = "body > div:nth-child(3) > fieldset > div:nth-child(4) > div:nth-child(1)";
	private static final String ERROR_MESSAGE = "Cannot parse this page or cannot connect to it.";

	/**
	 * Parse webpage of declaration, find location of official and return him
	 * location
	 * 
	 * @param declarationId
	 *            declarationId - id of declaration of some official
	 * @return return - location of some official
	 */
	public DeclarantRegisteredAddress parseDeclarationPage(String declarationId) {

		Document doc = null;
		
		try {
			doc = findDocument(declarationId);
		} catch (ParsingException e) {
			
			log.error(ERROR_MESSAGE + " " + e.getMessage());
			throw new ParsingException(ERROR_MESSAGE + " " + e.getMessage());
		}
		

		String textInRegisterAddress = doc.select(PATH_TO_REGISTER_ADDRESS_IN_PAGE).text();
		
		String[] parsedRegisterAddress = textInRegisterAddress.substring(textInRegisterAddress.indexOf(':') + 2, textInRegisterAddress.length()).split("/");
		
		List<String> valuesRegisterAddress = new ArrayList<String>(); 
		
		Collections.addAll(valuesRegisterAddress, parsedRegisterAddress);
		
		return new DeclarantRegisteredAddress(valuesRegisterAddress);
	}

	private Document findDocument(String declarationId) throws ParsingException {
		Document doc = null;
		try {

			String linkOfDeclaration = SITE_WITH_DECLARATIONS + declarationId;
			doc = Jsoup.connect(linkOfDeclaration).get();
			if (doc == null) {
				throw new ParsingException(ERROR_MESSAGE);
			}
		} catch (IOException e) {

			log.error(ERROR_MESSAGE + " " + e.getMessage());
			throw new ParsingException(ERROR_MESSAGE + " " + e.getMessage());
		}
		return doc;
	}
}
