package org.butler.johnj.supermarketpricing;

import org.butler.johnj.supermarketpricing.domain.Product;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

/**
 * Maintains informtion about a transaction.
 * 
 * @author John
 * 
 */
public class Transaction {

	/**
	 * Map of the items in the transaction
	 */
	private final Multimap<Product, TransactionItem> itemMapByProductId = LinkedListMultimap
			.create();

	private int lastItemId = 0;

	/**
	 * Creates a new transaction.
	 */
	public Transaction() {
	}

	/**
	 * Adds the passed product to the transaction.
	 * 
	 * @param product
	 *            the product to add
	 * @return the added item
	 */
	public TransactionItem addProduct(Product product) {
		TransactionItem item = new TransactionItem(++lastItemId, product);
		itemMapByProductId.put(product, item);
		return item;
	}

	/**
	 * Present for testing.
	 * 
	 * @return the transaction item map
	 */
	public ImmutableMultimap<Product, TransactionItem> getItemMap() {
		return ImmutableMultimap.copyOf(itemMapByProductId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("Transaction:\n");

		for (TransactionItem item : itemMapByProductId.values()) {
			builder.append(item.toString() + "\n");
		}
		return builder.toString();
	}
}
