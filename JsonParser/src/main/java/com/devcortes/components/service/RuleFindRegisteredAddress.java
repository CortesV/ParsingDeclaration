package com.devcortes.components.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import com.devcortes.components.entity.DeclarantRegisteredAddress;
import com.devcortes.components.entity.WorkPlace;
import com.devcortes.components.interfaces.IParsingRules;

/**
 * Service that parse declaration of offcial and return him location
 * 
 * @author cortes
 *
 */
@Service
public class RuleFindRegisteredAddress implements IParsingRules {

	private static Logger log = Logger.getLogger(RuleFindRegisteredAddress.class);
	private static final String SITE_WITH_DECLARATIONS = "https://public.nazk.gov.ua/declaration/";
	private static final String ERROR_MESSAGE = "Cannot parse this page or cannot connect to it.";

	private DeclarantRegisteredAddress declarantRegisteredAddress;

	/**
	 * Parse webpage of declaration, find location of official and return him
	 * location
	 * 
	 * @param declarationId
	 *            declarationId - id of declaration of some official
	 * @param path
	 *            path - path to element in DOM tree
	 * @return
	 * 
	 */
	@SuppressWarnings("unchecked")
	private DeclarantRegisteredAddress parseDeclarationPage(String declarationId, String path) {

		Document doc = null;

		try {
			doc = findDocument(declarationId);
		} catch (ParsingException e) {

			log.error(ERROR_MESSAGE + " " + e.getMessage());
			throw new ParsingException(ERROR_MESSAGE + " " + e.getMessage());
		}

		String textInRegisterAddress = doc.select(path).text();

		String[] parsedRegisterAddress = textInRegisterAddress
				.substring(textInRegisterAddress.indexOf(':') + 2, textInRegisterAddress.length()).split("/");

		List<String> valuesRegisterAddress = new ArrayList<String>();

		Collections.addAll(valuesRegisterAddress, parsedRegisterAddress);

		return new DeclarantRegisteredAddress(valuesRegisterAddress);
	}

	/**
	 * 
	 * @param declarationId
	 *            declarationId - id of declaration of some official
	 * @return return - all html text from declaration
	 * @throws ParsingException
	 */
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

	@Override
	public void parse(String declarationId, String path) {

		this.declarantRegisteredAddress = parseDeclarationPage(declarationId, path);
	}

	public DeclarantRegisteredAddress getDeclarantRegisteredAddress() {
		return declarantRegisteredAddress;
	}

	public void setDeclarantRegisteredAddress(DeclarantRegisteredAddress declarantRegisteredAddress) {
		this.declarantRegisteredAddress = declarantRegisteredAddress;
	}

}
