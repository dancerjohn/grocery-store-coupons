package org.butler.johnj.supermarketpricing.domain;

import org.junit.Ignore;

import com.google.common.collect.Lists;

/**
 * Provides test utilities for Tax.
 * 
 * @author John
 * 
 */
@Ignore
public class TaxTestUtils {

	public static final Tax alcoholTax = new Tax(9.25, true,
			Lists.newArrayList(CategoryTestUtils.alcohol));
}
