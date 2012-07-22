package org.butler.johnj.supermarketpricing.domain;

import static org.butler.johnj.supermarketpricing.domain.CategoryTestUtils.*;

import org.junit.Ignore;

import com.google.common.collect.ImmutableList;

/**
 * Provides test utilities for Product.
 * 
 * @author John
 * 
 */
@Ignore
public class ProductTestUtils {

	public static final Product milkCarton = new Product("8873", "Milk", dairy,
			249);
	public static final Product toothBrush = new Product("1983", "Toothbrush",
			cosmetics, 199);
	public static final Product wine = new Product("0923", "Bogel Zinfandel",
			alcohol, 1549);
	public static final Product chips = new Product("6732", "Chips", grocery,
			249);
	public static final Product salsa = new Product("4900", "Salsa", grocery,
			349);

	public static final ImmutableList<Product> allProducts = ImmutableList.of(
			milkCarton, toothBrush, wine, chips, salsa);
}
