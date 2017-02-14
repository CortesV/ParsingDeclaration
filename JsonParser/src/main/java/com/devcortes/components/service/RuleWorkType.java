package com.devcortes.components.service;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.devcortes.components.entity.WorkType;
import com.devcortes.components.interfaces.IParsingRules;

public class RuleWorkType implements IParsingRules {

	private static Logger log = Logger.getLogger(RuleWorkType.class);
	private static final String SITE_WITH_DECLARATIONS = "https://public.nazk.gov.ua/declaration/";
	private static final String ERROR_MESSAGE = "Cannot parse this page or cannot connect to it.";

	private WorkType workType;

	private WorkType parseDeclarationPage(String declarationId, String path) {

		Document doc = null;

		try {
			doc = findDocument(declarationId);
		} catch (ParsingException e) {

			log.error(ERROR_MESSAGE + " " + e.getMessage());
			throw new ParsingException(ERROR_MESSAGE + " " + e.getMessage());
		}

		String textMessage = doc.select(path).text();
		String[] arrayMessage = textMessage.split(" ");
		
		return arrayMessage.length == 8 ? new WorkType(arrayMessage[2] + " " + arrayMessage[3] + " " + arrayMessage[4]) : null;

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

		workType = parseDeclarationPage(declarationId, path);
	}

	public WorkType getWorkPost() {
		return workType;
	}

	public void setWorkPost(WorkType workPost) {
		this.workType = workType;
	}

}
