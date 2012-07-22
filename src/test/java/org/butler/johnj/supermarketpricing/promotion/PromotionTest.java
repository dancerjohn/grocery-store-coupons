package org.butler.johnj.supermarketpricing.promotion;

import static org.butler.johnj.supermarketpricing.discount.DiscountTestUtils.*;
import static org.butler.johnj.supermarketpricing.domain.ProductTestUtils.*;
import static org.butler.johnj.supermarketpricing.promotion.PromotionTestUtils.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.List;

import org.butler.johnj.TestBase;
import org.butler.johnj.supermarketpricing.Transaction;
import org.butler.johnj.supermarketpricing.TransactionItem;
import org.butler.johnj.supermarketpricing.TransactionTestUtils;
import org.butler.johnj.supermarketpricing.discount.Discount;
import org.junit.Test;

import com.google.common.collect.Lists;

public class PromotionTest extends TestBase {

	@Test
	public void testDoesPromotionApply() {
		assertPromotionApplies("one element",
				toothBrushPromotion,
				TransactionTestUtils.createTransaction(
						toothBrush),
				false);

		assertPromotionApplies("two element",
				toothBrushPromotion,
				TransactionTestUtils.createTransaction(
						toothBrush, toothBrush),
				false);

		assertPromotionApplies("three element",
				toothBrushPromotion,
				TransactionTestUtils.createTransaction(
						toothBrush, toothBrush, toothBrush),
				true);

		assertPromotionApplies("two brush, one milk",
				toothBrushPromotion,
				TransactionTestUtils.createTransaction(
						toothBrush, toothBrush, milkCarton),
				false);

		assertPromotionApplies("three brush, one milk",
				toothBrushPromotion,
				TransactionTestUtils.createTransaction(
						toothBrush, milkCarton, toothBrush, toothBrush),
				true);

		assertPromotionApplies("four brush, one milk",
				toothBrushPromotion,
				TransactionTestUtils.createTransaction(
						toothBrush, milkCarton, toothBrush, chips, toothBrush,
						toothBrush),
				true);

		assertPromotionApplies("four brush, one milk, CP",
				chipsAndSalsaPromotion,
				TransactionTestUtils.createTransaction(
						toothBrush, milkCarton, toothBrush, chips, toothBrush,
						toothBrush),
				false);

		assertPromotionApplies("chips",
				chipsAndSalsaPromotion,
				TransactionTestUtils.createTransaction(
						chips),
				false);

		assertPromotionApplies("salsa",
				chipsAndSalsaPromotion,
				TransactionTestUtils.createTransaction(
						salsa),
				false);

		assertPromotionApplies("salsa & brush",
				chipsAndSalsaPromotion,
				TransactionTestUtils.createTransaction(
						salsa, toothBrush),
				false);

		assertPromotionApplies("salsa & chips",
				chipsAndSalsaPromotion,
				TransactionTestUtils.createTransaction(
						salsa, chips),
				true);

		assertPromotionApplies("chips & salsa",
				chipsAndSalsaPromotion,
				TransactionTestUtils.createTransaction(
						chips, salsa),
				true);

		assertPromotionApplies("lots",
				chipsAndSalsaPromotion,
				TransactionTestUtils.createTransaction(
						toothBrush, milkCarton, chips, toothBrush, milkCarton,
						salsa, milkCarton, toothBrush),
				true);

		assertPromotionApplies("lots2",
				toothBrushPromotion,
				TransactionTestUtils.createTransaction(
						toothBrush, milkCarton, chips, toothBrush, milkCarton,
						salsa, toothBrush),
				true);
	}

	private void assertPromotionApplies(String reason, Promotion promotion,
			Transaction transaction, boolean expected) {
		// test
		boolean result = promotion.doesPromotionApply(transaction);

		// verify
		checkThat(reason, result, is(expected));
	}

	@Test
	public void testApplyPromotion_chipsAndSalsaOnce() {
		// setup
		Transaction transaction = TransactionTestUtils.createTransaction(
				toothBrush, milkCarton, chips, toothBrush, milkCarton,
				salsa, toothBrush, chips, toothBrush);

		// test
		chipsAndSalsaPromotion.applyPromotion(transaction);

		// verify
		List<TransactionItem> brushes = Lists.newArrayList(
				transaction.getItemMap().get(toothBrush));
		assertDiscountApplied("brush 1", brushes.get(0), false, null);
		assertDiscountApplied("brush 2", brushes.get(1), false, null);
		assertDiscountApplied("brush 3", brushes.get(2), false, null);
		assertDiscountApplied("brush 4", brushes.get(3), false, null);
		List<TransactionItem> salsas = Lists.newArrayList(
				transaction.getItemMap().get(salsa));
		assertDiscountApplied("salsa 1", salsas.get(0), true, fixed299Discount);
		List<TransactionItem> chipsList = Lists.newArrayList(
				transaction.getItemMap().get(chips));
		assertDiscountApplied("chips 1", chipsList.get(0), true,
				fixed200Discount);
		assertDiscountApplied("chips 2", chipsList.get(1), false, null);
	}

	@Test
	public void testApplyPromotion_brushOnce() {
		// setup
		Transaction transaction = TransactionTestUtils.createTransaction(
				toothBrush, milkCarton, chips, toothBrush, milkCarton,
				salsa, toothBrush, chips, toothBrush);

		// test
		toothBrushPromotion.applyPromotion(transaction);

		// verify
		List<TransactionItem> brushes = Lists.newArrayList(
				transaction.getItemMap().get(toothBrush));
		assertDiscountApplied("brush 1", brushes.get(0), true, noDiscount);
		assertDiscountApplied("brush 2", brushes.get(1), true, noDiscount);
		assertDiscountApplied("brush 3", brushes.get(2), true, freeDiscount);
		assertDiscountApplied("brush 4", brushes.get(3), false, null);
		List<TransactionItem> salsas = Lists.newArrayList(
				transaction.getItemMap().get(salsa));
		assertDiscountApplied("salsa 1", salsas.get(0), false, null);
		List<TransactionItem> chipsList = Lists.newArrayList(
				transaction.getItemMap().get(chips));
		assertDiscountApplied("chips 1", chipsList.get(0), false, null);
	}

	@Test
	public void testApplyPromotion_bothOnce() {
		// setup
		Transaction transaction = TransactionTestUtils.createTransaction(
				toothBrush, milkCarton, chips, toothBrush, milkCarton,
				salsa, toothBrush, chips, toothBrush);

		// test
		chipsAndSalsaPromotion.applyPromotion(transaction);
		toothBrushPromotion.applyPromotion(transaction);

		// verify
		List<TransactionItem> brushes = Lists.newArrayList(
				transaction.getItemMap().get(toothBrush));
		assertDiscountApplied("brush 1", brushes.get(0), true, noDiscount);
		assertDiscountApplied("brush 2", brushes.get(1), true, noDiscount);
		assertDiscountApplied("brush 3", brushes.get(2), true, freeDiscount);
		assertDiscountApplied("brush 4", brushes.get(3), false, null);
		List<TransactionItem> salsas = Lists.newArrayList(
				transaction.getItemMap().get(salsa));
		assertDiscountApplied("salsa 1", salsas.get(0), true, fixed299Discount);
		List<TransactionItem> chipsList = Lists.newArrayList(
				transaction.getItemMap().get(chips));
		assertDiscountApplied("chips 1", chipsList.get(0), true,
				fixed200Discount);
		assertDiscountApplied("chips 2", chipsList.get(1), false, null);
	}

	@Test
	public void testApplyPromotion_bothMultiple() {
		// setup
		Transaction transaction = TransactionTestUtils.createTransaction(
				toothBrush, milkCarton, chips, toothBrush, milkCarton,
				salsa, toothBrush,
				toothBrush, toothBrush, toothBrush,
				chips, salsa,
				chips, salsa,
				chips);

		// test
		chipsAndSalsaPromotion.applyPromotion(transaction);
		toothBrushPromotion.applyPromotion(transaction);

		// verify
		List<TransactionItem> brushes = Lists.newArrayList(
				transaction.getItemMap().get(toothBrush));
		assertDiscountApplied("brush 1", brushes.get(0), true, noDiscount);
		assertDiscountApplied("brush 2", brushes.get(1), true, noDiscount);
		assertDiscountApplied("brush 3", brushes.get(2), true, freeDiscount);
		assertDiscountApplied("brush 4", brushes.get(3), false, null);
		assertDiscountApplied("brush 5", brushes.get(4), false, null);
		assertDiscountApplied("brush 6", brushes.get(5), false, null);
		List<TransactionItem> salsas = Lists.newArrayList(
				transaction.getItemMap().get(salsa));
		assertDiscountApplied("salsa 1", salsas.get(0), true, fixed299Discount);
		assertDiscountApplied("salsa 2", salsas.get(1), true, fixed299Discount);
		assertDiscountApplied("salsa 3", salsas.get(2), true, fixed299Discount);
		List<TransactionItem> chipsList = Lists.newArrayList(
				transaction.getItemMap().get(chips));
		assertDiscountApplied("chips 1", chipsList.get(0), true,
				fixed200Discount);
		assertDiscountApplied("chips 2", chipsList.get(1), true,
				fixed200Discount);
		assertDiscountApplied("chips 3", chipsList.get(2), true,
				fixed200Discount);
		assertDiscountApplied("chips 2", chipsList.get(3), false, null);
	}

	private void assertDiscountApplied(String reason, TransactionItem item,
			boolean applied,
			Discount discount) {
		checkThat(reason, item.getDiscountApplied().isPresent(), is(applied));

		if (applied) {
			checkThat(reason, item.getDiscountApplied().get(),
					sameInstance(discount));
			checkThat(reason, item.getPriceInCents(),
					equalTo(discount.getDiscountedPriceInCents(item
							.getProduct())));
		} else {
			checkThat(reason, item.getPriceInCents(), equalTo(item
					.getProduct().getPriceInCents()));
		}
	}
}
