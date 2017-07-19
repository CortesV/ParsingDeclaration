package com.devcortes.components.service;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.devcortes.components.entity.WorkCategory;
import com.devcortes.components.entity.WorkType;
import com.devcortes.components.interfaces.IParsingRules;

public class RuleWorkCategory implements IParsingRules {

	private static Logger log = Logger.getLogger(RuleWorkCategory.class);
	private static final String SITE_WITH_DECLARATIONS = "https://public.nazk.gov.ua/declaration/";
	private static final String ERROR_MESSAGE = "Cannot parse this page or cannot connect to it.";

	private WorkCategory workCategory;

	private WorkCategory parseDeclarationPage(String declarationId, String path) {

		Document doc = null;

		try {
			doc = findDocument(declarationId);
		} catch (ParsingException e) {

			log.error(ERROR_MESSAGE + " " + e.getMessage());
			throw new ParsingException(ERROR_MESSAGE + " " + e.getMessage());
		}

		String textMessage = doc.select(path).text();
		String[] arrayMessage = textMessage.split(" ");

		return arrayMessage.length == 8 ? new WorkCategory(arrayMessage[7]) : null;

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

		workCategory = parseDeclarationPage(declarationId, path);
	}

	public WorkCategory getWorkCategory() {
		return workCategory;
	}

	public void setWorkCategory(WorkCategory workCategory) {
		this.workCategory = workCategory;
	}

}
