package com.devcortes.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.devcortes.components.interfaces.IParsingRules;
import com.devcortes.components.service.RuleWorkPlace;
import com.devcortes.components.service.RuleFindRegisteredAddress;
import com.devcortes.components.service.RuleSalary;
import com.devcortes.components.service.RuleWorkCategory;
import com.devcortes.components.service.RuleWorkType;
import com.devcortes.services.RunParsingByRules;

/**
 * Controller for run parsing
 * 
 * @author cortes
 *
 */
@RestController
@RequestMapping(value = "/registerAddress")
public class JsonParseController {
	
	private static Logger log = Logger.getLogger(RunParsingByRules.class);
	private static final String ERROR_MESSAGE = "Cannot parse this page or cannot connect to it.";


	@Autowired
	private RunParsingByRules runParsingByRules;

	/**
	 * Retun list of results parsing by http request GET
	 * 
	 * @param declarationId
	 *            declarationId - id of declaration of some official
	 * @param path
	 *            path - path in DOM tree to element that find
	 * @return return - list of result parsing
	 */
	@RequestMapping(value = "/parse/{declarationId}/{path}", method = RequestMethod.GET)
	public <T> T run(@PathVariable("declarationId") String declarationId,
			@PathVariable("path") String path) {
		
		List<IParsingRules> rules = new ArrayList<>();
		rules.add(new RuleFindRegisteredAddress());
		rules.add(new RuleWorkPlace());
		rules.add(new RuleWorkType());
		rules.add(new RuleWorkCategory());
		rules.add(new RuleSalary());
		
		try {
			runParsingByRules.getDeclarations(rules, declarationId, path);
		} catch (Exception e) {
			log.error(ERROR_MESSAGE + " " + e.getMessage());
		}
		
		return (T)rules;

	}	
}
