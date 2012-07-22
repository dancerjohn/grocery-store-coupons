package org.butler.johnj.supermarketpricing.discount;

import org.butler.johnj.supermarketpricing.domain.Product;

/**
 * Implements a discount that is a percentage reduction of the product price.
 * 
 * @author John
 * 
 */
public class PercentageDiscount implements Discount {

	/**
	 * the percentage discount to apply
	 */
	private double discountPercentage;

	/**
	 * Default constructor
	 */
	public PercentageDiscount() {
	}

	/**
	 * Constructor
	 * 
	 * @param discountPercentage
	 *            the percentage discount to apply
	 */
	public PercentageDiscount(double discountPercentage) {
		super();
		this.discountPercentage = discountPercentage;
	}

	@Override
	public int getDiscountedPriceInCents(Product product) {

		return (int) Math.round(product.getPriceInCents()
				* (1.0 - (discountPercentage / 100.0)));
	}

	/**
	 * @param discountPercentage
	 *            the percentage discount to apply
	 */
	public void setDiscountPercentage(double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

}
