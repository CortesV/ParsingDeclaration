package com.devcortes.components.interfaces;

/**
 * 
 * @author cortes
 *
 */
@FunctionalInterface
public interface IParsingRules {
	
	/***
	 * Method that return string result parsing of declaration
	 * 
	 * @param declarationId
	 *            declarationId - id of declaration of some official
	 * @param path
	 *            path - path in DOM tree to element that find
	 * @return return - result in string
	 */
	public void parse(String declarationId, String path);
}
