package org.butler.johnj.supermarketpricing.promotion;

import static org.butler.johnj.supermarketpricing.discount.DiscountTestUtils.*;
import static org.butler.johnj.supermarketpricing.domain.ProductTestUtils.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.Collection;
import java.util.Iterator;

import org.butler.johnj.TestBase;
import org.butler.johnj.supermarketpricing.Transaction;
import org.butler.johnj.supermarketpricing.TransactionItem;
import org.butler.johnj.supermarketpricing.TransactionTestUtils;
import org.butler.johnj.supermarketpricing.discount.Discount;
import org.butler.johnj.supermarketpricing.domain.Product;
import org.junit.Test;

public class DiscountElementTest extends TestBase {

	@Test
	public void testApplyDiscount_free1Element() {
		// setup
		DiscountElement discount = new DiscountElement(
				freeDiscount, 1);
		Transaction transaction = TransactionTestUtils.createTransaction(
				milkCarton, milkCarton, toothBrush);
		Collection<TransactionItem> items = transaction.getItemMap().values();

		// test
		discount.applyDiscount(items.iterator());

		// verify
		assertDiscountAssigned(items, freeDiscount, null, null);
	}

	@Test
	public void testApplyDiscount_free2Element() {
		// setup
		DiscountElement discount = new DiscountElement(
				freeDiscount, 2);
		Transaction transaction = TransactionTestUtils.createTransaction(
				milkCarton, milkCarton, toothBrush);
		Collection<TransactionItem> items = transaction.getItemMap().values();

		// test
		discount.applyDiscount(items.iterator());

		// verify
		assertDiscountAssigned(items, freeDiscount, freeDiscount, null);
	}

	@Test
	public void testApplyDiscount_free4Element() {
		// setup
		DiscountElement discount = new DiscountElement(
				freeDiscount, 4);
		Transaction transaction = TransactionTestUtils.createTransaction(
				milkCarton, milkCarton, toothBrush);
		Collection<TransactionItem> items = transaction.getItemMap().values();

		// test
		discount.applyDiscount(items.iterator());

		// verify
		assertDiscountAssigned(items, freeDiscount, freeDiscount, freeDiscount);
	}

	@Test
	public void testApplyDiscount_299_4Element() {
		// setup
		DiscountElement discount = new DiscountElement(
				fixed299Discount, 4);
		Transaction transaction = TransactionTestUtils.createTransaction(
				milkCarton, milkCarton, toothBrush);
		Collection<TransactionItem> items = transaction.getItemMap().values();

		// test
		discount.applyDiscount(items.iterator());

		// verify
		assertDiscountAssigned(items, fixed299Discount, fixed299Discount,
				fixed299Discount);
	}

	private void assertDiscountAssigned(
			Collection<TransactionItem> items,
			Discount... discounts) {
		int i = 0;
		Iterator<TransactionItem> iterator = items.iterator();
		while (iterator.hasNext()) {
			TransactionItem item = iterator.next();
			Discount discount = discounts[i];
			Product product = item.getProduct();

			checkThat("item promotion " + i,
					item.getDiscountApplied().orNull(),
					equalTo(discounts[i]));

			if (discount != null) {
				checkThat("item price " + i, item.getPriceInCents(),
						equalTo(discount.getDiscountedPriceInCents(product)));
				checkThat("item savings present " + i, item.getSavingsInCents()
						.isPresent(), is(true));
			} else {
				checkThat("item price " + i, item.getPriceInCents(),
						equalTo(product.getPriceInCents()));
			}

			i++;
		}
	}
}
