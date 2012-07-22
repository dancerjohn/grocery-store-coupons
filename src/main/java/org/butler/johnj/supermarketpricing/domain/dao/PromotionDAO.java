package org.butler.johnj.supermarketpricing.domain.dao;

import org.butler.johnj.supermarketpricing.promotion.Promotion;
import org.springframework.dao.DataAccessException;

/**
 * DAO for Promotions
 * 
 * @author John
 * 
 */
public interface PromotionDAO {

	/**
	 * @return a collection of all current Promotions
	 */
	Iterable<Promotion> getPromotions() throws DataAccessException;
}
