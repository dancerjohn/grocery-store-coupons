package org.butler.johnj.supermarketpricing.matcher;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.butler.johnj.supermarketpricing.domain.Product;

import com.google.common.base.Preconditions;

/**
 * Implements a product matcher that matches a single Product.
 * 
 * @author John
 * 
 */
public class SingleProductMatcher extends ProductMatcher {

	/**
	 * The product to match
	 */
	@Nullable
	// is nullable because some DAO mechanisms require the default constructor
	private Product productToMatch;

	/**
	 * Default Constructor
	 */
	public SingleProductMatcher() {
		super();
	}

	/**
	 * @param productToMatch
	 *            The product to match
	 */
	public SingleProductMatcher(@Nonnull Product productToMatch) {
		Preconditions.checkNotNull(productToMatch, "productToMatch");

		this.productToMatch = productToMatch;
	}

	@Override
	public boolean matches(@Nonnull Product product) {
		return productToMatch.equals(product);
	}

	/**
	 * @param productToMatch
	 *            The product to match
	 */
	public void setProductToMatch(@Nonnull Product productToMatch) {
		Preconditions.checkNotNull(productToMatch, "productToMatch");

		this.productToMatch = productToMatch;
	}

}
