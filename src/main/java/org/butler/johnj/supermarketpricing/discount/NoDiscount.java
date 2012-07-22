package org.butler.johnj.supermarketpricing.discount;

import org.butler.johnj.supermarketpricing.domain.Product;

/**
 * Implements a discount that does not change the price.
 * 
 * @author John
 * 
 */
public class NoDiscount implements Discount {

	@Override
	public int getDiscountedPriceInCents(Product product) {
		return product.getPriceInCents();
	}
}
