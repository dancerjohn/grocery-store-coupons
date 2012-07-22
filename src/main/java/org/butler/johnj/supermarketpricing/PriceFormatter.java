package org.butler.johnj.supermarketpricing;

/**
 * Formats prices
 * 
 * @author John
 * 
 */
public class PriceFormatter {

	/**
	 * Formats prices
	 * 
	 * @param priceInCents
	 *            the price in cents
	 * @return the formatted price
	 */
	public static String format(int priceInCents) {
		return "$" + priceInCents / 100 + "." +
				String.format("%02d", priceInCents % 100);
	}
}
