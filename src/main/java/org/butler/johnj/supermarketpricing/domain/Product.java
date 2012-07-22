package org.butler.johnj.supermarketpricing.domain;

import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;

/**
 * Defines a customer purchasable product.
 * 
 * @author John
 * 
 */
public class Product {

	/**
	 * Converts the passed Product to its ID.
	 */
	public static final Function<Product, String> toId = new Function<Product, String>() {

		@Override
		public String apply(Product product) {
			return product.id;
		}
	};

	@Nullable
	// is nullable because some DAO mechanisms require the default constructor
	private String id;

	@Nullable
	// is nullable because some DAO mechanisms require the default constructor
	private String name;

	@Nullable
	// is nullable because some DAO mechanisms require the default constructor
	private Category category;

	private int priceInCents;

	/**
	 * Default constructor
	 */
	public Product() {
	}

	/**
	 * @param id
	 *            the id
	 * @param name
	 *            the name
	 * @param category
	 *            the product's category
	 * @param priceInCents
	 *            the product's price in cents
	 */
	public Product(@Nonnull String id, @Nonnull String name,
			@Nonnull Category category, int priceInCents) {
		Preconditions.checkNotNull(id, "id");
		Preconditions.checkNotNull(name, "name");
		Preconditions.checkNotNull(category, "category");

		this.id = id;
		this.name = name;
		this.category = category;
		this.priceInCents = priceInCents;
	}

	public String getId() {
		return id;
	}

	public void setId(@Nonnull String id) {
		Preconditions.checkNotNull(id, "id");
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(@Nonnull String name) {
		Preconditions.checkNotNull(name, "name");
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(@Nonnull Category category) {
		Preconditions.checkNotNull(category, "category");
		this.category = category;
	}

	public int getPriceInCents() {
		return priceInCents;
	}

	public void setPriceInCents(int priceInCents) {
		this.priceInCents = priceInCents;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}
}
