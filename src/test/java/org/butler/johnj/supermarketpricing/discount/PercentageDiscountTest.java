package org.butler.johnj.supermarketpricing.discount;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.butler.johnj.supermarketpricing.domain.Product;
import org.butler.johnj.supermarketpricing.domain.ProductTestUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

public class PercentageDiscountTest {
	
	@Rule
	public ErrorCollector errorCollector = new ErrorCollector();

	@Test
	public void testGetDiscountedPriceInCents() {
		// initial price is 249
		testGetDiscountedPriceInCents(5.0, 237); 
		testGetDiscountedPriceInCents(10.5, 223);
	}
	
	private void testGetDiscountedPriceInCents(double discountPercentage, int expectedPrice){
		// setup
		Product product = ProductTestUtils.milkCarton;
		Discount discount = new PercentageDiscount(discountPercentage); 
		
		// test
		int result = discount.getDiscountedPriceInCents(product);
		
		// verify
		try{
			assertThat("with % = " + discountPercentage, 
					result, equalTo(expectedPrice));
		}catch (Throwable t){
			errorCollector.addError(t);
		}
	}
}
