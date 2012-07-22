package org.butler.johnj.supermarketpricing.promotion;

import static org.butler.johnj.supermarketpricing.discount.DiscountTestUtils.*;
import static org.butler.johnj.supermarketpricing.domain.ProductTestUtils.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;

import org.butler.johnj.TestBase;
import org.butler.johnj.supermarketpricing.Transaction;
import org.butler.johnj.supermarketpricing.TransactionItem;
import org.butler.johnj.supermarketpricing.TransactionTestUtils;
import org.butler.johnj.supermarketpricing.discount.Discount;
import org.butler.johnj.supermarketpricing.matcher.ProductMatcher;
import org.butler.johnj.supermarketpricing.matcher.SingleProductMatcher;
import org.hamcrest.collection.IsCollectionWithSize;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class PromotionElementTest extends TestBase {

	private final ProductMatcher productMatcher = new SingleProductMatcher(
			toothBrush);
	private final DiscountElement firstTwo = new DiscountElement(noDiscount, 2);
	private final DiscountElement nextOne = new DiscountElement(freeDiscount, 1);

	private PromotionElement promotionElement;

	@Before
	public void setup() {
		promotionElement = new PromotionElement();
		promotionElement.setDiscountElements(Lists.newArrayList(firstTwo,
				nextOne));
		promotionElement.setMatcher(productMatcher);
		promotionElement.setTotalRequired(3);
	}

	@Test
	public void testCriteriaMet_false() {
		testCriteriaMet("one element",
				TransactionTestUtils.createTransaction(
						toothBrush),
				false);

		testCriteriaMet("two element",
				TransactionTestUtils.createTransaction(
						toothBrush, toothBrush),
				false);

		testCriteriaMet("three element",
				TransactionTestUtils.createTransaction(
						toothBrush, toothBrush, toothBrush),
				true, 1, 2, 3);

		testCriteriaMet("two brush, one milk",
				TransactionTestUtils.createTransaction(
						toothBrush, toothBrush, milkCarton),
				false);

		testCriteriaMet("three brush, one milk",
				TransactionTestUtils.createTransaction(
						toothBrush, milkCarton, toothBrush, toothBrush),
				true, 1, 3, 4);

		testCriteriaMet("four brush, one milk",
				TransactionTestUtils.createTransaction(
						toothBrush, milkCarton, toothBrush, chips, toothBrush,
						toothBrush),
				true, 1, 3, 5);
	}

	private void testCriteriaMet(String reason, Transaction transaction,
			boolean expected, Integer... ids) {
		// setup
		Set<Integer> alreadyClaimedProducts = Sets.newHashSet();

		// test
		boolean result = promotionElement.criteriaMet(transaction,
				alreadyClaimedProducts);

		// verify
		checkThat(reason, result, equalTo(expected));
		if (expected) {
			checkThat(reason, alreadyClaimedProducts,
					IsIterableContainingInAnyOrder.containsInAnyOrder(ids));
		} else {
			checkThat(reason, alreadyClaimedProducts,
					IsCollectionWithSize.hasSize(0));
		}
	}

	@Test
	public void testCriteriaMet_itemsInList() {
		// setup
		Transaction transaction = TransactionTestUtils.createTransaction(
				toothBrush, milkCarton, toothBrush, chips, toothBrush,
				toothBrush);
		Set<Integer> alreadyClaimedProducts = Sets.newHashSet(1, 3);

		// test
		boolean result = promotionElement.criteriaMet(transaction,
				alreadyClaimedProducts);

		// verify
		assertThat(result, is(false));
		assertThat(alreadyClaimedProducts,
				IsIterableContainingInAnyOrder.containsInAnyOrder(1, 3));
	}

	@Test
	public void testCriteriaMet_itemsInListButMore() {
		// setup
		Transaction transaction = TransactionTestUtils.createTransaction(
				toothBrush, milkCarton, toothBrush, chips, toothBrush,
				toothBrush, toothBrush);
		Set<Integer> alreadyClaimedProducts = Sets.newHashSet(1, 3);

		// test
		boolean result = promotionElement.criteriaMet(transaction,
				alreadyClaimedProducts);

		// verify
		assertThat(result, is(true));
		assertThat(alreadyClaimedProducts,
				IsIterableContainingInAnyOrder
						.containsInAnyOrder(1, 3, 5, 6, 7));
	}

	@Test
	public void testApplyPromotion_noDiscountsApplied() {
		// setup
		Transaction transaction = TransactionTestUtils.createTransaction(
				toothBrush, milkCarton, toothBrush, chips, toothBrush,
				toothBrush, toothBrush);

		// test
		promotionElement.applyPromotion(transaction);

		// verify
		List<TransactionItem> brushes = Lists.newArrayList(
				transaction.getItemMap().get(toothBrush));
		assertDiscountApplied("brush 1", brushes.get(0), true, noDiscount);
		assertDiscountApplied("brush 2", brushes.get(1), true, noDiscount);
		assertDiscountApplied("brush 3", brushes.get(2), true, freeDiscount);
		assertDiscountApplied("brush 4", brushes.get(3), false, null);
		assertDiscountApplied("brush 5", brushes.get(4), false, null);
	}

	private void assertDiscountApplied(String reason, TransactionItem item,
			boolean applied,
			Discount discount) {
		assertThat(reason, item.getDiscountApplied().isPresent(), is(applied));

		if (applied) {
			assertThat(reason, item.getDiscountApplied().get(),
					sameInstance(discount));
			assertThat(reason, item.getPriceInCents(),
					equalTo(discount.getDiscountedPriceInCents(item
							.getProduct())));
		} else {
			assertThat(reason, item.getPriceInCents(), equalTo(item
					.getProduct().getPriceInCents()));
		}
	}
}
