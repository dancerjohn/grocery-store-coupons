package org.butler.johnj.supermarketpricing;

import static org.butler.johnj.supermarketpricing.domain.ProductTestUtils.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.hamcrest.collection.IsCollectionContaining;
import org.junit.Before;
import org.junit.Test;

public class TransactionTest {

	private Transaction transaction;

	@Before
	public void setup() {
		transaction = new Transaction();
	}

	@Test
	public void testAddProduct() {
		// test
		TransactionItem item = transaction
				.addProduct(milkCarton);

		// verify
		assertThat(item, notNullValue());
		assertThat(item.getId(), equalTo(1));
		assertThat(transaction.getItemMap().size(), equalTo(1));
		assertThat(transaction.getItemMap().get(milkCarton),
				IsCollectionContaining.hasItem(item));
	}

	@Test
	public void testAddProduct_multiple() {
		// setup
		TransactionItem item1 = transaction.addProduct(milkCarton);
		TransactionItem item2 = transaction.addProduct(toothBrush);

		// test
		TransactionItem item = transaction.addProduct(milkCarton);

		// verify
		assertThat(item, notNullValue());
		assertThat(item.getId(), equalTo(3));
		assertThat(transaction.getItemMap().size(), equalTo(3));
		assertThat(transaction.getItemMap().get(milkCarton),
				IsCollectionContaining.hasItems(item1, item));
		assertThat(transaction.getItemMap().get(toothBrush),
				IsCollectionContaining.hasItems(item2));
	}

}
