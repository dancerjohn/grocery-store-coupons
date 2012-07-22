package org.butler.johnj.supermarketpricing.matcher;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.butler.johnj.supermarketpricing.domain.Product;
import org.butler.johnj.supermarketpricing.domain.ProductTestUtils;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import com.google.common.collect.Iterables;

public class SingleProductMatcherTest {

	@Rule
	public ErrorCollector errorCollector = new ErrorCollector();

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private final Product matchProduct = ProductTestUtils.toothBrush;
	private final SingleProductMatcher matcher = new SingleProductMatcher(
			matchProduct);

	@Test
	public void testMatches() {
		for (Product product : ProductTestUtils.allProducts) {
			testMatches(product);
		}
	}

	private void testMatches(Product product) {
		// test
		boolean result = matcher.matches(product);

		// verify
		try {
			assertThat("with " + product, result,
					is(matchProduct.equals(product)));
		} catch (Throwable t) {
			errorCollector.addError(t);
		}
	}

	@Test
	public void testSingleProductMatcher_nullProduct() {
		// verify
		expectedException.expect(NullPointerException.class);

		// test
		new SingleProductMatcher(null);
	}

	@Test
	public void testSetProductToMatch() {
		// verify
		expectedException.expect(NullPointerException.class);

		// test
		matcher.setProductToMatch(null);
	}

	@Test
	public void testApply() {
		// test
		Iterable<Product> filtered =
				Iterables.filter(ProductTestUtils.allProducts, matcher);

		// verify
		assertThat(filtered,
				IsIterableContainingInOrder.contains(matchProduct));
	}
}
