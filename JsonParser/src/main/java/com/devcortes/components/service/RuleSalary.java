package com.devcortes.components.service;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.devcortes.components.entity.OfficialsSalary;
import com.devcortes.components.interfaces.IParsingRules;

public class RuleSalary implements IParsingRules {

	private static Logger log = Logger.getLogger(RuleWorkCategory.class);
	private static final String SITE_WITH_DECLARATIONS = "https://public.nazk.gov.ua/declaration/";
	private static final String ERROR_MESSAGE = "Cannot parse this page or cannot connect to it.";

	private OfficialsSalary officialsSalary;

	private OfficialsSalary parseDeclarationPage(String declarationId, String path) {

		Document doc = null;

		try {
			doc = findDocument(declarationId);
		} catch (ParsingException e) {

			log.error(ERROR_MESSAGE + " " + e.getMessage());
			throw new ParsingException(ERROR_MESSAGE + " " + e.getMessage());
		}

		String textMessage = doc.select(path).text();

		return new OfficialsSalary(Integer.parseInt(textMessage));

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

		officialsSalary = parseDeclarationPage(declarationId, path);
	}

	public OfficialsSalary getOfficialsSalary() {
		return officialsSalary;
	}

	public void setOfficialsSalary(OfficialsSalary officialsSalary) {
		this.officialsSalary = officialsSalary;
	}

}
