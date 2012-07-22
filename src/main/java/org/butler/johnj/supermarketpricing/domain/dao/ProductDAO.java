package org.butler.johnj.supermarketpricing.domain.dao;

import org.butler.johnj.supermarketpricing.domain.Product;
import org.springframework.dao.DataAccessException;

/**
 * Product Data Access Object.
 * 
 * @author John
 * 
 *         Am assuming that some DAO implementation (i.e. Hibernate) will be
 *         provided.
 */
public interface ProductDAO {

	/**
	 * Exception thrown if product is not found for an id.
	 */
	public static class ProductNotFoundException extends RuntimeException {
		private final String id;

		public ProductNotFoundException(String id) {
			super("No product found with id = " + id);

			this.id = id;
		}

		public ProductNotFoundException(String id, String message) {
			super(message);

			this.id = id;
		}

		/**
		 * @return the product id that was not found
		 */
		public String getId() {
			return id;
		}

	}

	/**
	 * Finds the product associated with the passed product id.
	 * 
	 * @param id
	 *            the id to look for
	 * @return the product
	 * 
	 * @throws ProductNotFoundException
	 *             if no product was found with the passed id
	 * @throws DataAccessException
	 *             if failure occurred accessing data layer
	 */
	Product findProductById(String id) throws DataAccessException,
			ProductNotFoundException;
}
