package org.butler.johnj.supermarketpricing.promotion;

import java.util.Set;

import org.butler.johnj.supermarketpricing.Transaction;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

/**
 * Implements a promotion.
 * 
 * @author John
 * 
 */
public class Promotion {

	/**
	 * The criteria of the promotion
	 */
	private Iterable<PromotionElement> elements;

	/**
	 * Max times the promotion may be applied per transaction. 0 = unlimited
	 */
	private int maxPerTransaction = 0;

	/**
	 * Determines if the promotion is applicable to the transaction
	 * 
	 * @param transaction
	 *            the transaction
	 * @return if applicable
	 */
	boolean doesPromotionApply(Transaction transaction) {
		Set<Integer> claimedProducts = Sets.newHashSet();

		boolean result = Iterables.size(elements) > 0;
		for (PromotionElement element : elements) {
			result &= element.criteriaMet(transaction, claimedProducts);

			if (!result) {
				break;
			}
		}
		return result;
	}

	/**
	 * Applies the promotion to the transaction if appropriate.
	 * 
	 * @param transaction
	 *            the transaction
	 */
	public void applyPromotion(Transaction transaction) {
		int count = 0;
		while (maxPerTransaction == 0 ||
				count < maxPerTransaction) {

			if (doesPromotionApply(transaction)) {
				for (PromotionElement element : elements) {
					element.applyPromotion(transaction);
				}
				count++;
			} else {
				break;
			}
		}
	}

	/**
	 * @param elements
	 *            the promotion elements to set
	 */
	public void setElements(Iterable<PromotionElement> elements) {
		this.elements = elements;
	}

	/**
	 * @param maxPerTransaction
	 *            Max times the promotion may be applied per transaction. 0 =
	 *            unlimited
	 */
	public void setMaxPerTransaction(int maxPerTransaction) {
		this.maxPerTransaction = maxPerTransaction;
	}
}
