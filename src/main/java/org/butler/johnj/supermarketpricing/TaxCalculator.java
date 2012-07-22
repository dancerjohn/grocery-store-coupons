package org.butler.johnj.supermarketpricing;

import javax.annotation.Nonnull;

import org.butler.johnj.supermarketpricing.domain.Product;
import org.butler.johnj.supermarketpricing.domain.Tax;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

/**
 * Calculates taxes.
 * 
 * @author John
 * 
 */
public class TaxCalculator {

	/**
	 * @param tax
	 *            the tax to use
	 * @param transaction
	 *            the transaction to tax
	 * @return the total tax
	 */
	public static int calculateTax(@Nonnull Tax tax,
			@Nonnull Transaction transaction) {
		return new TaxCalculator(tax, transaction).calculateTax();
	}

	@Nonnull
	private final Tax tax;

	@Nonnull
	private final Transaction transaction;

	public TaxCalculator(@Nonnull Tax tax, @Nonnull Transaction transaction) {
		Preconditions.checkNotNull(tax);
		Preconditions.checkNotNull(transaction);

		this.tax = tax;
		this.transaction = transaction;
	}

	/**
	 * @return the tax on the transaction
	 */
	public int calculateTax() {
		Iterable<TransactionItem> items = getApplicableItems();
		int tax = addTax(items);
		return tax;
	}

	/**
	 * Matches products that are in the tax category list
	 */
	private Predicate<Product> inTaxList = new Predicate<Product>() {

		@Override
		public boolean apply(Product input) {
			return tax.getCategories().contains(input.getCategory());
		}
	};

	/**
	 * Matches products that are not in the tax category list
	 */
	private Predicate<Product> notInTaxList = new Predicate<Product>() {

		@Override
		public boolean apply(Product input) {
			return !tax.getCategories().contains(input.getCategory());
		}
	};

	/**
	 * @return the items that should be taxed
	 */
	private Iterable<TransactionItem> getApplicableItems() {
		if (tax.appliesToAllCategories()) {
			return transaction.getItemMap().values();
		} else if (tax.isCategoryListIsInclusive()) {
			Multimap<Product, TransactionItem> applicableItems =
					Multimaps.filterKeys(transaction.getItemMap(), inTaxList);
			return applicableItems.values();
		} else {
			Multimap<Product, TransactionItem> applicableItems =
					Multimaps
							.filterKeys(transaction.getItemMap(), notInTaxList);
			return applicableItems.values();
		}
	}

	/**
	 * Accumulates the tax for all items
	 * 
	 * @param items
	 *            the items to tax
	 * @return the total tax
	 */
	private int addTax(Iterable<TransactionItem> items) {
		int tax = 0;

		for (TransactionItem item : items) {
			int newTax = (int) Math.round(item.getPriceInCents()
					* (this.tax.getPercentage() / 100.0));
			tax += newTax;
		}
		return tax;
	}
}
