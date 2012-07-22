package org.butler.johnj.supermarketpricing.matcher;

import javax.annotation.Nonnull;

import org.butler.johnj.supermarketpricing.domain.Product;

import com.google.common.base.Predicate;

/**
 * Determines is a Product "matches".
 * 
 * @author John
 * 
 */
public abstract class ProductMatcher implements Predicate<Product> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.common.base.Predicate#apply(java.lang.Object)
	 */
	@Override
	public boolean apply(@Nonnull Product product) {
		return matches(product);
	}

	/**
	 * Determines if the passed product id matches this criteria.
	 * 
	 * @param productId
	 *            the product id to check
	 * @return if the product id matches this criteria
	 */
	public abstract boolean matches(@Nonnull Product product);
}
