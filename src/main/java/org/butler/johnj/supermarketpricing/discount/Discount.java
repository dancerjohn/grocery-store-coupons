package org.butler.johnj.supermarketpricing.discount;

import org.butler.johnj.supermarketpricing.domain.Product;

/**
 * Implements a discount.
 * 
 * @author John
 * 
 */
public interface Discount {

	/**
	 * Calculates the discounted price for the product.
	 * 
	 * @param product
	 *            the product to be discounted
	 * @return the discounted price in cents
	 */
	public int getDiscountedPriceInCents(Product product);
}
