package org.butler.johnj.supermarketpricing.matcher;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.butler.johnj.supermarketpricing.domain.Category;
import org.butler.johnj.supermarketpricing.domain.Product;

import com.google.common.base.Preconditions;

public class CategoryProductMatcher extends ProductMatcher {

	@Nullable
	// is nullable because some DAO mechanisms require the default constructor
	private Category categoryToMatch;

	/**
	 * Default constructor
	 */
	public CategoryProductMatcher() {
	}

	/**
	 * @param categoryToMatch
	 *            the category to match
	 */
	public CategoryProductMatcher(@Nonnull Category categoryToMatch) {
		Preconditions.checkNotNull(categoryToMatch);

		this.categoryToMatch = categoryToMatch;
	}

	@Override
	public boolean matches(@Nonnull Product product) {
		return categoryToMatch.equals(product.getCategory());
	}

	/**
	 * @param categoryToMatch
	 *            the categoryToMatch to set
	 */
	public void setCategoryToMatch(@Nonnull Category categoryToMatch) {
		Preconditions.checkNotNull(categoryToMatch);

		this.categoryToMatch = categoryToMatch;
	}

}
