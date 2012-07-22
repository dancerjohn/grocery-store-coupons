package org.butler.johnj.supermarketpricing;

import javax.annotation.Nonnull;

import org.butler.johnj.supermarketpricing.domain.Product;
import org.butler.johnj.supermarketpricing.domain.Tax;
import org.butler.johnj.supermarketpricing.domain.dao.ProductDAO.ProductNotFoundException;
import org.butler.johnj.supermarketpricing.promotion.Promotion;

import com.google.common.base.Preconditions;

/**
 * Implements a checkout system.
 * 
 * @author John
 * 
 */
public class Checkout {

	@Nonnull
	private final Scheme scheme;

	@Nonnull
	private final Transaction transaction;

	/**
	 * @param scheme
	 *            today's pricing scheme
	 */
	public Checkout(@Nonnull Scheme scheme) {
		Preconditions.checkNotNull(scheme);

		this.scheme = scheme;
		this.transaction = new Transaction();
	}

	/**
	 * Adds the product.
	 * 
	 * @param productId
	 *            the product to add
	 */
	public void scan(@Nonnull String productId) {
		Preconditions.checkNotNull(productId);

		try {
			Product product = scheme.getProductDAO().findProductById(productId);
			transaction.addProduct(product);
			scheme.getMessageSystem().displayInfoMessage(
					product + " "
							+ PriceFormatter.format(product.getPriceInCents()));
		} catch (ProductNotFoundException ex) {
			scheme.getMessageSystem().displayErrorMessage(
					"No product found with id " + productId);
		}
	}

	/**
	 * @return the total of the transaction
	 */
	public int getTotal() {
		scheme.getMessageSystem().displayInfoMessage("");
		scheme.getMessageSystem().displayInfoMessage("");
		scheme.getMessageSystem().displayInfoMessage(
				"-------------------------");
		scheme.getMessageSystem().displayInfoMessage("");

		applyPromotions();
		printTransaction();

		int subtotal = getSubtotal();
		int tax = calculateTax();

		int total = subtotal + tax;
		scheme.getMessageSystem().displayInfoMessage(
				"Total = " + PriceFormatter.format(total));
		return total;
	}

	private void printTransaction() {
		scheme.getMessageSystem().displayInfoMessage(
				"Transaction:");
		for (TransactionItem item : transaction.getItemMap().values()) {
			scheme.getMessageSystem().displayInfoMessage(item.toString());
		}
		scheme.getMessageSystem().displayInfoMessage("");
		scheme.getMessageSystem().displayInfoMessage("   -----    ");
		scheme.getMessageSystem().displayInfoMessage("");
	}

	/**
	 * @return the subtotal after all promotions
	 */
	private int getSubtotal() {
		int subTotal = 0;
		for (TransactionItem item : transaction.getItemMap().values()) {
			subTotal += item.getPriceInCents();
		}
		scheme.getMessageSystem().displayInfoMessage(
				"Subtotal = " + PriceFormatter.format(subTotal));
		return subTotal;
	}

	/**
	 * Applies promotions to the transaction
	 */
	private void applyPromotions() {
		for (Promotion pro : scheme.getPromotions()) {
			pro.applyPromotion(transaction);
		}
	}

	/**
	 * Calculates the tax on the transaction
	 * 
	 * @return the total tax on the transaction
	 */
	private int calculateTax() {
		int totalTax = 0;
		for (Tax tax : scheme.getTaxes()) {
			totalTax += TaxCalculator.calculateTax(tax, transaction);
		}
		scheme.getMessageSystem().displayInfoMessage(
				"Tax = " + PriceFormatter.format(totalTax));
		return totalTax;
	}
}
