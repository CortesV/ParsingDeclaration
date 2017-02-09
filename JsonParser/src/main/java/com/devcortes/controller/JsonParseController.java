package com.devcortes.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.devcortes.components.entity.DeclarantRegisteredAddress;
import com.devcortes.services.RunParseRegisteredAddress;

@RestController
@RequestMapping(value = "/registerAddress")
public class JsonParseController {

	private static Logger log = Logger.getLogger(JsonParseController.class);

	@Autowired
	private RunParseRegisteredAddress runParseRegisteredAddress;

	@RequestMapping(value = "/parse/{declarationId}", method = RequestMethod.GET)
	public DeclarantRegisteredAddress run(@PathVariable("declarationId") String declarationId) {

		return runParseRegisteredAddress.runParser(declarationId);
	}

}
