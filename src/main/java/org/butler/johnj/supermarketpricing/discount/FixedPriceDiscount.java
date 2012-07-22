package org.butler.johnj.supermarketpricing.discount;

import org.butler.johnj.supermarketpricing.domain.Product;

import com.google.common.base.Preconditions;

/**
 * Implements a discount where the sale price is a fixed value.
 * 
 * @author John
 * 
 */
public class FixedPriceDiscount implements Discount {

	/**
	 * the discounted price in cents
	 */
	private int priceInCents;

	public FixedPriceDiscount() {
	}

	/**
	 * @param priceInCents
	 *            the discounted price in cents
	 */
	public FixedPriceDiscount(int priceInCents) {
		Preconditions.checkArgument(priceInCents >= 0, "price must be >= 0");

		this.priceInCents = priceInCents;
	}

	@Override
	public int getDiscountedPriceInCents(Product product) {
		return priceInCents;
	}

	/**
	 * @param priceInCents
	 *            the discounted price in cents
	 */
	public void setPriceInCents(int priceInCents) {
		Preconditions.checkArgument(priceInCents >= 0, "price must be >= 0");

		this.priceInCents = priceInCents;
	}

}
