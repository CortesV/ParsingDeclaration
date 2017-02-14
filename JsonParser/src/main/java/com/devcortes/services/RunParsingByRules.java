package com.devcortes.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.devcortes.components.entity.WorkPlace;
import com.devcortes.components.interfaces.IParsingRules;
import com.devcortes.components.service.RuleWorkPlace;
import com.devcortes.components.service.ParsingException;
import com.devcortes.components.service.RuleFindRegisteredAddress;
import com.devcortes.components.service.RuleWorkCategory;

/**
 * This service run parsing declaration of official by some rules
 * 
 * @author cortes
 *
 */
@Service
public class RunParsingByRules {

	private static Logger log = Logger.getLogger(RunParsingByRules.class);
	private static final String ERROR_MESSAGE = "Cannot parse this page or cannot connect to it.";

	/**
	 * Run parsing parsing declaration of official by some rules
	 * 
	 * @param declarationId
	 *            declarationId - id of declaration of some official
	 * @param path
	 *            path - path in DOM tree to element that find
	 * @return return - list of result parsing
	 */
	public void getDeclarations(List<IParsingRules> rules, String declarationId, String path) {

		try {
			
			for (IParsingRules rule : rules) {
				
				rule.parse(declarationId, path);
			}
			
		} catch (ParsingException e) {

			log.error(ERROR_MESSAGE + " " + e.getMessage());
			throw new ParsingException(ERROR_MESSAGE + " " + e.getMessage());
		}

	}

}
