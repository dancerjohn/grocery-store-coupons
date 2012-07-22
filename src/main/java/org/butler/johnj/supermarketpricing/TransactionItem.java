package org.butler.johnj.supermarketpricing;

import static org.butler.johnj.supermarketpricing.PriceFormatter.*;

import javax.annotation.Nonnull;

import org.butler.johnj.supermarketpricing.discount.Discount;
import org.butler.johnj.supermarketpricing.domain.Product;

import com.google.common.base.Function;
import com.google.common.base.Optional;

public class TransactionItem {

	/**
	 * Converts the TransactionItem to it's it.
	 */
	public static final Function<TransactionItem, Integer> toId = new Function<TransactionItem, Integer>() {

		@Override
		public Integer apply(TransactionItem item) {
			return item.id;
		}
	};

	/**
	 * the transaction item id.
	 */
	private final int id;

	/**
	 * The product
	 */
	@Nonnull
	private final Product product;

	/**
	 * The current price in cents.
	 */
	private int priceInCents;

	/**
	 * The currently applied discount, if any.
	 */
	@Nonnull
	private Optional<Discount> discountApplied = Optional.absent();

	/**
	 * The savings of the current discount, if any.
	 */
	@Nonnull
	private Optional<Integer> savingsInCents = Optional.absent();

	/**
	 * @param id
	 *            the id of the item in the transaction
	 * @param product
	 *            the product
	 */
	public TransactionItem(int id, Product product) {
		this.id = id;
		this.product = product;
		this.priceInCents = product.getPriceInCents();
	}

	/**
	 * @return the priceInCents
	 */
	public int getPriceInCents() {
		return priceInCents;
	}

	/**
	 * @param priceInCents
	 *            the priceInCents to set
	 */
	public void setPriceInCents(int priceInCents) {
		this.priceInCents = priceInCents;
	}

	/**
	 * @return the discountApplied
	 */
	public Optional<Discount> getDiscountApplied() {
		return discountApplied;
	}

	/**
	 * @param discountApplied
	 *            the discountApplied to set
	 */
	public void setDiscountApplied(Discount discountApplied) {
		this.discountApplied = Optional.of(discountApplied);
	}

	/**
	 * @return the savingsInCents
	 */
	public Optional<Integer> getSavingsInCents() {
		return savingsInCents;
	}

	/**
	 * @param savingsInCents
	 *            the savingsInCents to set
	 */
	public void setSavingsInCents(Integer savingsInCents) {
		this.savingsInCents = Optional.of(savingsInCents);
	}

	/**
	 * Clears any currently applied discount.
	 */
	public void clearDiscount() {
		this.savingsInCents = Optional.absent();
		this.discountApplied = Optional.absent();
		this.priceInCents = product.getPriceInCents();
	}

	/**
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String firstLine = product + " " + format(priceInCents);
		String result = firstLine;

		if (discountApplied.isPresent() && savingsInCents.get() > 0) {
			result += "\n     Savings = (" + format(savingsInCents.get()) + ")";
		}

		return result;
	}
}
