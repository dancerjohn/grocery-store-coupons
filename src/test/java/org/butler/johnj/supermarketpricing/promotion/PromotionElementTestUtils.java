package org.butler.johnj.supermarketpricing.promotion;

import static org.butler.johnj.supermarketpricing.discount.DiscountTestUtils.*;
import static org.butler.johnj.supermarketpricing.domain.ProductTestUtils.*;

import org.butler.johnj.supermarketpricing.matcher.ProductMatcher;
import org.butler.johnj.supermarketpricing.matcher.SingleProductMatcher;
import org.junit.Ignore;

import com.google.common.collect.Lists;

/**
 * Test utilities for PromotionElements.
 * 
 * @author John
 * 
 */
@Ignore
public class PromotionElementTestUtils {

	public static final PromotionElement toothBrushPromotionElement;
	public static final PromotionElement chipsPromotionElement;
	public static final PromotionElement salsaPromotionElement;

	static {
		ProductMatcher toothBrushProductMatcher = new SingleProductMatcher(
				toothBrush);
		DiscountElement toothBrushFirstTwo = new DiscountElement(
				noDiscount, 2);
		DiscountElement toothBrushFreeOne = new DiscountElement(
				freeDiscount, 1);

		toothBrushPromotionElement = new PromotionElement();
		toothBrushPromotionElement.setDiscountElements(Lists.newArrayList(
				toothBrushFirstTwo,
				toothBrushFreeOne));
		toothBrushPromotionElement.setMatcher(toothBrushProductMatcher);
		toothBrushPromotionElement.setTotalRequired(3);

		ProductMatcher chipsProductMatcher = new SingleProductMatcher(
				chips);
		DiscountElement chipsDiscount = new DiscountElement(
				fixed200Discount, 1);
		chipsPromotionElement = new PromotionElement();
		chipsPromotionElement.setDiscountElements(Lists
				.newArrayList(chipsDiscount));
		chipsPromotionElement.setMatcher(chipsProductMatcher);
		chipsPromotionElement.setTotalRequired(1);

		ProductMatcher salsaProductMatcher = new SingleProductMatcher(
				salsa);
		DiscountElement salsaDiscount = new DiscountElement(
				fixed299Discount, 1);
		salsaPromotionElement = new PromotionElement();
		salsaPromotionElement.setDiscountElements(Lists
				.newArrayList(salsaDiscount));
		salsaPromotionElement.setMatcher(salsaProductMatcher);
		salsaPromotionElement.setTotalRequired(1);
	}
}
