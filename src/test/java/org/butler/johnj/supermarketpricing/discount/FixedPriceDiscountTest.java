package org.butler.johnj.supermarketpricing.discount;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.butler.johnj.supermarketpricing.domain.Product;
import org.butler.johnj.supermarketpricing.domain.ProductTestUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

public class FixedPriceDiscountTest {
	
	@Rule
	public ErrorCollector errorCollector = new ErrorCollector();
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Test(expected=IllegalArgumentException.class)
	public void testFixedPriceDiscount_negativeValue(){
		// test
		new FixedPriceDiscount(-1); 
	}
	
	@Test
	public void testSetPriceInCents_negative(){
		// setup
		FixedPriceDiscount discount = new FixedPriceDiscount(); 
		
		// verify
		expectedException.expect(IllegalArgumentException.class);
		
		// test
		discount.setPriceInCents(-1);
	}

	@Test
	public void testGetDiscountedPriceInCents() {
		// initial price is 249
		testGetDiscountedPriceInCents(230); 
		testGetDiscountedPriceInCents(0);
	}
	
	private void testGetDiscountedPriceInCents(int salePrice){
		// setup
		Product product = ProductTestUtils.milkCarton;
		Discount discount = new FixedPriceDiscount(salePrice); 
		
		// test
		int result = discount.getDiscountedPriceInCents(product);
		
		// verify
		try{
			assertThat("with price = " + salePrice, 
					result, equalTo(salePrice));
		}catch (Throwable t){
			errorCollector.addError(t);
		}
	}
}
