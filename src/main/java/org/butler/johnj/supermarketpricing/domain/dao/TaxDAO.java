package org.butler.johnj.supermarketpricing.domain.dao;

import org.butler.johnj.supermarketpricing.domain.Tax;
import org.springframework.dao.DataAccessException;

/**
 * Tax Data Access Object.
 * 
 * @author John
 * 
 *         Am assuming that some DAO implementation (i.e. Hibernate) will be
 *         provided.
 */
public interface TaxDAO {

	/**
	 * @return a collection of all current taxes
	 */
	Iterable<Tax> getTaxes() throws DataAccessException;
}
