package org.butler.johnj.supermarketpricing;

import static org.butler.johnj.supermarketpricing.domain.ProductTestUtils.*;
import static org.mockito.Mockito.*;

import org.butler.johnj.supermarketpricing.domain.TaxTestUtils;
import org.butler.johnj.supermarketpricing.domain.dao.ProductDAO;
import org.butler.johnj.supermarketpricing.domain.dao.ProductDAO.ProductNotFoundException;
import org.butler.johnj.supermarketpricing.domain.dao.PromotionDAO;
import org.butler.johnj.supermarketpricing.domain.dao.TaxDAO;
import org.butler.johnj.supermarketpricing.promotion.PromotionTestUtils;
import org.hamcrest.CoreMatchers;
import org.hamcrest.collection.IsIn;
import org.junit.Ignore;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.common.collect.Lists;

@Ignore
public class SchemeTestUtils {

	public static final ProductDAO productDAOMock;
	public static final TaxDAO taxDAOMock;
	public static final PromotionDAO promotionDAOMock;
	public static final MessageSystem messageSystemMock;

	static {
		productDAOMock = mock(ProductDAO.class);
		when(productDAOMock.findProductById(milkCarton.getId())).thenReturn(
				milkCarton);
		when(productDAOMock.findProductById(toothBrush.getId())).thenReturn(
				toothBrush);
		when(productDAOMock.findProductById(wine.getId())).thenReturn(wine);
		when(productDAOMock.findProductById(chips.getId())).thenReturn(chips);
		when(productDAOMock.findProductById(salsa.getId())).thenReturn(salsa);
		when(productDAOMock.findProductById(Mockito.argThat(
				CoreMatchers.not(
						IsIn.isIn(Lists.newArrayList(milkCarton.getId(),
								toothBrush.getId(), wine.getId(),
								chips.getId(), salsa.getId()))))))
				.thenThrow(new ProductNotFoundException("unknown id"));

		taxDAOMock = mock(TaxDAO.class);
		when(taxDAOMock.getTaxes()).thenReturn(
				Lists.newArrayList(TaxTestUtils.alcoholTax));

		promotionDAOMock = mock(PromotionDAO.class);
		when(promotionDAOMock.getPromotions()).thenReturn(
				Lists.newArrayList(PromotionTestUtils.chipsAndSalsaPromotion,
						PromotionTestUtils.toothBrushPromotion));

		messageSystemMock = mock(MessageSystem.class, new Answer<Object>() {

			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				String message = (String) invocation.getArguments()[0];
				System.out.println(message);
				return null;
			}
		});
	}

	public static Scheme createScheme() {
		Scheme result = new Scheme();
		result.setProductDAO(productDAOMock);
		result.setTaxDAO(taxDAOMock);
		result.setMessageSystem(messageSystemMock);
		result.setPromotionDAO(promotionDAOMock);
		result.initialize();
		return result;
	}
}
