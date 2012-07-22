package org.butler.johnj.supermarketpricing;

import org.butler.johnj.supermarketpricing.domain.Tax;
import org.butler.johnj.supermarketpricing.domain.dao.ProductDAO;
import org.butler.johnj.supermarketpricing.domain.dao.PromotionDAO;
import org.butler.johnj.supermarketpricing.domain.dao.TaxDAO;
import org.butler.johnj.supermarketpricing.promotion.Promotion;

public class Scheme {

	private ProductDAO productDAO;
	private TaxDAO taxDAO;
	private PromotionDAO promotionDAO;
	private MessageSystem messageSystem;

	private Iterable<Promotion> promotions;
	private Iterable<Tax> taxes;

	/**
	 * @param productDAO
	 *            the productDAO to set
	 */
	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	/**
	 * @param taxDAO
	 *            the taxDAO to set
	 */
	public void setTaxDAO(TaxDAO taxDAO) {
		this.taxDAO = taxDAO;
	}

	/**
	 * @param promotionDAO
	 *            the promotionDAO to set
	 */
	public void setPromotionDAO(PromotionDAO promotionDAO) {
		this.promotionDAO = promotionDAO;
	}

	/**
	 * @param messageSystem
	 *            the messageSystem to set
	 */
	public void setMessageSystem(MessageSystem messageSystem) {
		this.messageSystem = messageSystem;
	}

	/**
	 * Initializes the system.
	 */
	public void initialize() {
		try {
			promotions = promotionDAO.getPromotions();
		} catch (Exception e) {
			messageSystem.displayErrorMessage("Error retrieving promotions");
		}

		try {
			taxes = taxDAO.getTaxes();
		} catch (Exception e) {
			messageSystem.displayErrorMessage("Error retrieving taxes");
		}
	}

	/**
	 * @return the productDAO
	 */
	public ProductDAO getProductDAO() {
		return productDAO;
	}

	/**
	 * @return the taxDAO
	 */
	public TaxDAO getTaxDAO() {
		return taxDAO;
	}

	/**
	 * @return the promotionDAO
	 */
	public PromotionDAO getPromotionDAO() {
		return promotionDAO;
	}

	/**
	 * @return the messageSystem
	 */
	public MessageSystem getMessageSystem() {
		return messageSystem;
	}

	/**
	 * @return the promotions
	 */
	public Iterable<Promotion> getPromotions() {
		return promotions;
	}

	/**
	 * @return the taxes
	 */
	public Iterable<Tax> getTaxes() {
		return taxes;
	}
}
