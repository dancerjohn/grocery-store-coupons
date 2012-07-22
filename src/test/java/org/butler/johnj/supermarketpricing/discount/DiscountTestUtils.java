package org.butler.johnj.supermarketpricing.discount;

import org.junit.Ignore;

/**
 * Test utilities for Discount elements.
 * 
 * @author John
 * 
 */
@Ignore
public class DiscountTestUtils {
	public static final Discount noDiscount = new NoDiscount();
	public static final Discount freeDiscount = new FixedPriceDiscount(0);
	public static final Discount fixed200Discount = new FixedPriceDiscount(200);
	public static final Discount fixed299Discount = new FixedPriceDiscount(299);
}
