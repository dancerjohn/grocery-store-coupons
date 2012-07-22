package org.butler.johnj.supermarketpricing.promotion;

import static org.butler.johnj.supermarketpricing.promotion.PromotionElementTestUtils.*;

import org.junit.Ignore;

import com.google.common.collect.Lists;

/**
 * Test utilities for Promotions.
 * 
 * @author John
 * 
 */
@Ignore
public class PromotionTestUtils {

	public static final Promotion toothBrushPromotion;
	public static final Promotion chipsAndSalsaPromotion;

	static {
		toothBrushPromotion = new Promotion();
		toothBrushPromotion.setElements(Lists
				.newArrayList(toothBrushPromotionElement));
		toothBrushPromotion.setMaxPerTransaction(1);

		chipsAndSalsaPromotion = new Promotion();
		chipsAndSalsaPromotion.setElements(Lists
				.newArrayList(chipsPromotionElement, salsaPromotionElement));
	}
}
