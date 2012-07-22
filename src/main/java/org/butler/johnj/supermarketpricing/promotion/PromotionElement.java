package org.butler.johnj.supermarketpricing.promotion;

import java.util.Iterator;
import java.util.Set;

import org.butler.johnj.supermarketpricing.Transaction;
import org.butler.johnj.supermarketpricing.TransactionItem;
import org.butler.johnj.supermarketpricing.domain.Product;
import org.butler.johnj.supermarketpricing.matcher.ProductMatcher;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

/**
 * Maintains information about an element / product of a promotion and what
 * discounts should be applied to the product.
 * 
 * @author John
 * 
 */
public class PromotionElement {

	/**
	 * The product matcher
	 */
	private ProductMatcher matcher;

	/**
	 * The total number of products of this type required to match the
	 * promotion.
	 */
	private int totalRequired;

	/**
	 * The discounts to be applied to the products
	 */
	private Iterable<DiscountElement> discountElements;

	/**
	 * Constructor
	 */
	public PromotionElement() {
	}

	/**
	 * Predicate for items that do not have a discount applied.
	 */
	private static final Predicate<TransactionItem> noDiscountApplied = new Predicate<TransactionItem>() {

		@Override
		public boolean apply(TransactionItem product) {
			return !product.getDiscountApplied().isPresent();
		}

	};

	/**
	 * Retrieves transaction items whose products match the matcher and that do
	 * not have a discount applied.
	 * 
	 * @param transaction
	 *            the transaction from which to retrieve the items
	 * @return items whose products match the matcher and that do not have a
	 *         discount applied
	 */
	private Multimap<Product, TransactionItem> retrieveMatchingNonDiscountedProducts(
			Transaction transaction) {
		// get products that match this Promotion element
		Multimap<Product, TransactionItem> matchingProducts = Multimaps
				.filterKeys(transaction.getItemMap(), matcher);

		// get products that have not already had a promotion applied
		Multimap<Product, TransactionItem> noPromotionsAppliedProducts = Multimaps
				.filterValues(matchingProducts, noDiscountApplied);
		return noPromotionsAppliedProducts;
	}

	/**
	 * Calculates if the promotion criteria has been met. This method will add
	 * any reserved products to {@code alreadyClaimedProducts} if the criteria
	 * has been met.
	 * 
	 * @param transaction
	 *            the transaction in question
	 * @param alreadyClaimedProducts
	 *            a collection of the item ids that have been reserved for this
	 *            promotion
	 * @return if the criteria has been met.
	 */
	boolean criteriaMet(Transaction transaction,
			Set<Integer> alreadyClaimedProducts) {
		// get products that may be discounted via this promotion
		Multimap<Product, TransactionItem> applicableProducts = retrieveMatchingNonDiscountedProducts(transaction);

		// find the transaction item keys that have not already been reserved as
		// matching this promotion
		Iterable<Integer> newKeys = Iterables.filter(
				Iterables.transform(applicableProducts.values(),
						TransactionItem.toId),
				Predicates.not(Predicates.in(alreadyClaimedProducts)));

		boolean result = Iterables.size(newKeys) >= totalRequired;
		if (result) {
			// mark the products as being reserved for this promotion, this
			// prevents the next PromotionElement
			// from attempting to reuse the same product to determine if the
			// criteria has been met
			alreadyClaimedProducts.addAll(
					Lists.newArrayList(
							Iterables.limit(newKeys, totalRequired)));
		}
		return result;
	}

	/**
	 * Applies the promotion to the transaction
	 * 
	 * @param transaction
	 *            the transaction to which to apply the promotion
	 */
	void applyPromotion(Transaction transaction) {
		// get products that may be discounted via this promotion
		Multimap<Product, TransactionItem> applicableProducts = retrieveMatchingNonDiscountedProducts(transaction);
		Iterator<TransactionItem> matchingProducts = applicableProducts
				.values().iterator();

		// apply discounts to products
		for (DiscountElement discount : discountElements) {
			discount.applyDiscount(matchingProducts);
		}
	}

	/**
	 * @param matcher
	 *            the matcher to set
	 */
	public void setMatcher(ProductMatcher matcher) {
		this.matcher = matcher;
	}

	/**
	 * @param totalRequired
	 *            the totalRequired to set
	 */
	public void setTotalRequired(int totalRequired) {
		this.totalRequired = totalRequired;
	}

	/**
	 * @param discountElements
	 *            the discountElements to set
	 */
	public void setDiscountElements(Iterable<DiscountElement> discountElements) {
		this.discountElements = discountElements;
	}
}
