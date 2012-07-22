package org.butler.johnj.supermarketpricing.discount;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.butler.johnj.supermarketpricing.domain.Product;
import org.butler.johnj.supermarketpricing.domain.ProductTestUtils;
import org.junit.Test;

public class NoDiscountTest {

	@Test
	public void testGetDiscountedPriceInCents() {
		// setup
		Product product = ProductTestUtils.milkCarton;
		Discount discount = new NoDiscount();
		
		// test
		int result = discount.getDiscountedPriceInCents(product);
		
		// verify
		assertThat(result, equalTo(product.getPriceInCents()));
	}
}
