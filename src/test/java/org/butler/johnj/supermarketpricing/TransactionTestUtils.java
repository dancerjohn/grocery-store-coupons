package org.butler.johnj.supermarketpricing;

import org.butler.johnj.supermarketpricing.domain.Product;
import org.junit.Ignore;

/**
 * Test utilities for Transaction
 * 
 * @author John
 * 
 */
@Ignore
public class TransactionTestUtils {

	/**
	 * Creates a transaction containing the passed products
	 * 
	 * @param products
	 *            the products to add
	 * @return the transaction
	 */
	public static Transaction createTransaction(
			Product... products) {

		Transaction transaction = new Transaction();
		for (Product product : products) {
			transaction.addProduct(product);
		}

		return transaction;
	}
}
