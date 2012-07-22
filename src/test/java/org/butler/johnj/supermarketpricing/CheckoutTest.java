package org.butler.johnj.supermarketpricing;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.butler.johnj.TestBase;
import org.junit.Before;
import org.junit.Test;

public class CheckoutTest extends TestBase {

	private Scheme scheme = SchemeTestUtils.createScheme();
	private Checkout checkout;

	@Before
	public void setup() {
		reset(SchemeTestUtils.messageSystemMock);

		checkout = new Checkout(scheme);
	}

	@Test(expected = NullPointerException.class)
	public void testCheckout() {
		new Checkout(null);
	}

	@Test(expected = NullPointerException.class)
	public void testScan_null() {
		checkout.scan(null);
	}

	@Test
	public void testScan_notFound() {
		// test
		checkout.scan("999");

		// verify
		verify(SchemeTestUtils.messageSystemMock, times(1))
				.displayErrorMessage("No product found with id 999");
	}

	@Test
	public void testScan_found() {
		// test
		checkout.scan("8873");

		// verify
		verify(SchemeTestUtils.messageSystemMock, never())
				.displayErrorMessage("No product found with id 999");
		verify(SchemeTestUtils.messageSystemMock, times(1))
				.displayInfoMessage("Milk $2.49");
	}

	@Test
	public void testGetTotal_noElements() {
		// test
		int result = checkout.getTotal();

		// verify
		assertThat(result, is(0));
	}

	@Test
	public void testGetTotal_oneElementNoPromotions() {
		// setup
		checkout.scan("8873");

		// test
		int result = checkout.getTotal();

		// verify
		verify(SchemeTestUtils.messageSystemMock, times(1))
				.displayInfoMessage("Subtotal = $2.49");
		assertThat(result, is(249));
	}

	@Test
	public void testGetTotal_threeElementsWithPromotion() {
		// setup
		checkout.scan("1983"); // toothbrush
		checkout.scan("1983"); // toothbrush
		checkout.scan("1983"); // toothbrush

		// test
		int result = checkout.getTotal();

		// verify
		assertThat(result, is(398));
		verify(SchemeTestUtils.messageSystemMock, times(1))
				.displayInfoMessage("Subtotal = $3.98");
	}

	@Test
	public void testGetTotal_multiplePromotions() {
		// setup
		checkout.scan("1983"); // toothbrush
		checkout.scan("6732"); // chips
		checkout.scan("1983"); // toothbrush
		checkout.scan("4900"); // sals
		checkout.scan("1983"); // toothbrush

		// test
		int result = checkout.getTotal();

		// verify
		assertThat(result, is(897));
		verify(SchemeTestUtils.messageSystemMock, times(1))
				.displayInfoMessage("Subtotal = $8.97");
	}

	@Test
	public void testGetTotal_tax() {
		// setup
		checkout.scan("0923"); // wine

		// test
		int result = checkout.getTotal();

		// verify
		verify(SchemeTestUtils.messageSystemMock, times(1))
				.displayInfoMessage("Subtotal = $15.49");
		assertThat(result, is(1692));
		verify(SchemeTestUtils.messageSystemMock, times(1))
				.displayInfoMessage("Tax = $1.43");
		verify(SchemeTestUtils.messageSystemMock, times(1))
				.displayInfoMessage("Total = $16.92");
	}

	@Test
	public void testGetTotal() {
		// setup
		checkout.scan("1983"); // toothbrush
		checkout.scan("4900"); // salsa
		checkout.scan("8873"); // milk
		checkout.scan("6732"); // chips
		checkout.scan("0923"); // wine
		checkout.scan("1983"); // toothbrush
		checkout.scan("1983"); // toothbrush
		checkout.scan("1983"); // toothbrush

		// test
		int result = checkout.getTotal();

		// verify
		assertThat(result, is(3037));
		verify(SchemeTestUtils.messageSystemMock, times(1))
				.displayInfoMessage("Subtotal = $28.94");
		verify(SchemeTestUtils.messageSystemMock, times(1))
				.displayInfoMessage("Tax = $1.43");
		verify(SchemeTestUtils.messageSystemMock, times(1))
				.displayInfoMessage("Total = $30.37");
	}

}
