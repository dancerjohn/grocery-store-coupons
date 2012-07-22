package org.butler.johnj.supermarketpricing.domain;

import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Preconditions;

/**
 * Enumerates the categories of products.
 * 
 * @author John
 */
public class Category {
	private long id;
	@Nullable
	// is nullable because some DAO mechanisms require the default constructor
	private String name;

	/**
	 * Default constructor
	 */
	public Category() {
	}

	/**
	 * @param id
	 *            the category id
	 * @param name
	 *            the category name
	 */
	public Category(long id, @Nonnull String name) {
		Preconditions.checkNotNull(name, "name");

		this.id = id;
		this.name = name;
	}

	/**
	 * @return the category id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the category id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the category name
	 */
	@Nullable
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the category name
	 */
	public void setName(@Nonnull String name) {
		Preconditions.checkNotNull(name, "name");

		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name;
	}
}
