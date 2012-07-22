package org.butler.johnj.supermarketpricing.domain;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

/**
 * Specifies a kind of tax.
 * 
 * @author John
 * 
 */
public class Tax {

	/**
	 * The amount of tax to calculate.
	 */
	private double percentage;

	/**
	 * If tax should be applied to all categories of products.
	 */
	private boolean appliesToAllCategories;

	/**
	 * If {@code appliesToAllCategories} if false, use {@code categories} to
	 * determine which categories of Products should be taxed.
	 * {@code categoryListIsInclusive} defines if {@code categories} is
	 * inclusive.
	 */
	private boolean categoryListIsInclusive = false;

	/**
	 * The categories to tax / not tax is {@code appliesToAllCategories} is
	 * false.
	 */
	private ImmutableSet<Category> categories = ImmutableSet.of();

	/**
	 * Default constructor.
	 */
	public Tax() {
	}

	/**
	 * Creates a tax that calculates against all categories.
	 * 
	 * @param percentage
	 *            the amount (%) of the tax.
	 */
	public Tax(double percentage) {
		this.percentage = percentage;
		this.appliesToAllCategories = true;
	}

	/**
	 * Creates a tax that calculates against a subset of the categories.
	 * 
	 * @param percentage
	 *            the amount (%) of the tax.
	 * @param categoryListIsInclusive
	 *            if collection of categories is inclusive or exclusive of the
	 *            tax
	 * @param categories
	 *            the categories to either tax or not tax
	 */
	public Tax(double percentage, boolean categoryListIsInclusive,
			@Nonnull Iterable<Category> categories) {
		Preconditions.checkNotNull(categories, "categories");

		this.percentage = percentage;
		this.appliesToAllCategories = false;
		this.categoryListIsInclusive = categoryListIsInclusive;
		this.categories = ImmutableSet.copyOf(categories);
	}

	/**
	 * @return the percentage
	 */
	public double getPercentage() {
		return percentage;
	}

	/**
	 * @param percentage
	 *            the percentage to set
	 */
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	/**
	 * @return the appliesToAllCategories
	 */
	public boolean appliesToAllCategories() {
		return appliesToAllCategories;
	}

	/**
	 * @param appliesToAllCategories
	 *            the appliesToAllCategories to set
	 */
	public void setAppliesToAllCategories(boolean appliesToAllCategories) {
		this.appliesToAllCategories = appliesToAllCategories;
	}

	/**
	 * @return the categoryListIsInclusive
	 */
	public boolean isCategoryListIsInclusive() {
		return categoryListIsInclusive;
	}

	/**
	 * @param categoryListIsInclusive
	 *            the categoryListIsInclusive to set
	 */
	public void setCategoryListIsInclusive(boolean categoryListIsInclusive) {
		this.categoryListIsInclusive = categoryListIsInclusive;
	}

	/**
	 * @return the categories
	 */
	public ImmutableSet<Category> getCategories() {
		return categories;
	}

	/**
	 * @param categories
	 *            the categories to set
	 */
	public void setCategories(ImmutableSet<Category> categories) {
		this.categories = categories;
	}
}
