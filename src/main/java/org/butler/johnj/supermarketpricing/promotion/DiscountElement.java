package org.butler.johnj.supermarketpricing.promotion;

import java.util.Iterator;

import javax.annotation.Nonnull;

import org.butler.johnj.supermarketpricing.TransactionItem;
import org.butler.johnj.supermarketpricing.discount.Discount;

import com.google.common.base.Preconditions;

/**
 * Maintains information about the discount that may be applied to a product as
 * part of a promotion.
 * 
 * @author John
 * 
 */
public class DiscountElement {
	/**
	 * The discount to apply.
	 */
	private Discount discount;

	/**
	 * The maximum number of products to which the discount may be applied in a
	 * transaction.
	 */
	private int maxApplicable;

	/**
	 * Default constructor
	 */
	public DiscountElement() {
	}

	/**
	 * @param discount
	 *            discount to apply
	 * @param maxApplicable
	 *            maximum number of products to which the discount may be
	 *            applied in a transaction
	 */
	public DiscountElement(@Nonnull Discount discount, int maxApplicable) {
		Preconditions.checkNotNull(discount);
		Preconditions.checkArgument(maxApplicable >= 0,
				"maxApplicable must be >= 0");

		this.discount = discount;
		this.maxApplicable = maxApplicable;
	}

	/**
	 * Applies the discount to the next {@code maxApplicable} elements in the
	 * passed iterable.
	 * 
	 * @param products
	 *            the products to update
	 */
	void applyDiscount(Iterator<TransactionItem> products) {
		int discountsAppliedCount = 0;
		while (products.hasNext() && discountsAppliedCount < maxApplicable) {

			TransactionItem item = products.next();
			item.setDiscountApplied(discount);
			item.setPriceInCents(discount.getDiscountedPriceInCents(item
					.getProduct()));
			item.setSavingsInCents(item.getProduct().getPriceInCents()
					- item.getPriceInCents());

			discountsAppliedCount++;
		}
	}

	/**
	 * @param discount
	 *            the discount to apply
	 */
	public void setDiscount(@Nonnull Discount discount) {
		Preconditions.checkNotNull(discount);

		this.discount = discount;
	}

	/**
	 * @param maxApplicable
	 *            the maximum number of products to which the discount may be
	 *            applied in a transaction
	 */
	public void setMaxApplicable(int maxApplicable) {
		Preconditions.checkArgument(maxApplicable >= 0,
				"maxApplicable must be >= 0");

		this.maxApplicable = maxApplicable;
	}

}
