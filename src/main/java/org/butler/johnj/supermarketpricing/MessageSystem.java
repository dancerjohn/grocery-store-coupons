package org.butler.johnj.supermarketpricing;

/**
 * User message system, GUI.
 * 
 * @author John
 * 
 */
public interface MessageSystem {

	/**
	 * Displays the passed informational message
	 * 
	 * @param message
	 *            the message
	 */
	void displayInfoMessage(String message);

	/**
	 * Displays the passed error message
	 * 
	 * @param errorMessage
	 *            the message
	 */
	void displayErrorMessage(String errorMessage);
}
