package org.butler.johnj.supermarketpricing;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.butler.johnj.supermarketpricing.domain.ProductTestUtils;
import org.butler.johnj.supermarketpricing.domain.Tax;
import org.butler.johnj.supermarketpricing.domain.TaxTestUtils;
import org.junit.Test;

public class TaxCalculatorTest {

	private final Tax tax = TaxTestUtils.alcoholTax;

	@Test(expected = NullPointerException.class)
	public void testTaxCalculator_nullTax() {
		new TaxCalculator(null, new Transaction());
	}

	@Test(expected = NullPointerException.class)
	public void testTaxCalculator_nullTransaction() {
		new TaxCalculator(tax, null);
	}

	@Test
	public void testCalculateTax_noElements() {
		// setup
		TaxCalculator calculator = new TaxCalculator(tax, new Transaction());

		// test
		int result = calculator.calculateTax();

		// verify
		assertThat(result, is(0));
	}

	@Test
	public void testCalculateTax_noApplicableElements() {
		// setup
		Transaction transaction = TransactionTestUtils
				.createTransaction(ProductTestUtils.milkCarton);
		TaxCalculator calculator = new TaxCalculator(tax, transaction);

		// test
		int result = calculator.calculateTax();

		// verify
		assertThat(result, is(0));
	}

	@Test
	public void testCalculateTax_oneApplicableElements() {
		// setup
		Transaction transaction = TransactionTestUtils
				.createTransaction(ProductTestUtils.wine);
		TaxCalculator calculator = new TaxCalculator(tax, transaction);

		// test
		int result = calculator.calculateTax();

		// verify
		assertThat(result, is(143));
	}

	@Test
	public void testCalculateTax_multipleApplicableElements() {
		// setup
		Transaction transaction = TransactionTestUtils
				.createTransaction(ProductTestUtils.wine,
						ProductTestUtils.chips,
						ProductTestUtils.milkCarton,
						ProductTestUtils.salsa,
						ProductTestUtils.toothBrush,
						ProductTestUtils.wine);
		TaxCalculator calculator = new TaxCalculator(tax, transaction);

		// test
		int result = calculator.calculateTax();

		// verify
		assertThat(result, is(2 * 143));
	}
}
