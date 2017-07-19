package com.devcortes.components.service;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import com.devcortes.components.entity.WorkPlace;
import com.devcortes.components.interfaces.IParsingRules;
/**
 * Service that parse declaration of offcial and return work place of official
 * 
 * @author cortes
 *
 */
@Service
public class RuleWorkPlace implements IParsingRules {

	private static Logger log = Logger.getLogger(RuleWorkPlace.class);
	private static final String ERROR_MESSAGE = "Cannot parse this page or cannot connect to it.";
	private static final String SITE_WITH_DECLARATIONS = "https://public.nazk.gov.ua/declaration/";

	private WorkPlace jsonParsedMessage;
	
	/**
	 * Parse webpage of declaration, find location of official and re
	 * location
	 * 
	 * @param declarationId
	 *            declarationId - id of declaration of some official
	 * @return return - location of some official
	 */

	private WorkPlace parseDeclarationPage(String declarationId, String path) {

		Document doc = null;

		try {
			doc = findDocument(declarationId);
		} catch (ParsingException e) {

			log.error(ERROR_MESSAGE + " " + e.getMessage());
			throw new ParsingException(ERROR_MESSAGE + " " + e.getMessage());
		}

		String textMessage = doc.select(path).text();
		String textMessageWithoutColon = textMessage.substring(textMessage.indexOf(':') + 2, textMessage.length());

		return textMessage.contains(":") ? new WorkPlace(textMessageWithoutColon)
				: new WorkPlace(textMessage);

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

	@Override
	public void parse(String declarationId, String path) {
		
		jsonParsedMessage = parseDeclarationPage(declarationId, path);	
	}

	public WorkPlace getJsonParsedMessage() {
		return jsonParsedMessage;
	}

	public void setJsonParsedMessage(WorkPlace jsonParsedMessage) {
		this.jsonParsedMessage = jsonParsedMessage;
	}
	
	

}
