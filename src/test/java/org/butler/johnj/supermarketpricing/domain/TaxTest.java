package org.butler.johnj.supermarketpricing.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Test;

import com.google.common.collect.Lists;


public class TaxTest {

	@Test
	public void testTax() {
		// test
		Tax tax = new Tax();
		
		// verify
		assertThat(tax.getPercentage(), equalTo(0.0));
		assertThat(tax.appliesToAllCategories(), is(false));
		assertThat(tax.isCategoryListIsInclusive(), is(false));
		assertThat(tax.getCategories(), IsCollectionWithSize.hasSize(0));
	}

	@Test
	public void testTaxFloat() {
		// test
		Tax tax = new Tax(6.2);
		
		// verify
		assertThat(tax.getPercentage(), equalTo(6.2));
		assertThat(tax.appliesToAllCategories(), is(true));
		assertThat(tax.isCategoryListIsInclusive(), is(false));
		assertThat(tax.getCategories(), IsCollectionWithSize.hasSize(0));
	}

	@Test
	public void testTaxFloatBooleanIterableOfCategory() {
		// setup
		List<Category> cats = Lists.newArrayList(
				new Category(1, "produce"),
				new Category(2, "alcohol"));
		
		// test
		Tax tax = new Tax(6.2, true, cats);
		
		// verify
		assertThat(tax.getPercentage(), equalTo(6.2));
		assertThat(tax.appliesToAllCategories(), is(false));
		assertThat(tax.isCategoryListIsInclusive(), is(true));
		assertThat(tax.getCategories(), IsCollectionWithSize.hasSize(2));
	}

}
