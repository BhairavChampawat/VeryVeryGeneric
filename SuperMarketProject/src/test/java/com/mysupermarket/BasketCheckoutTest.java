package com.mysupermarket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mysupermarket.BasketCheckoutIntImpl;
import com.mysupermarket.BasketCheckoutInt;
import com.mysupermarket.ItemCostConfig;
import com.mysupermarket.CheckOutException;

public class BasketCheckoutTest {
	static BasketCheckoutInt basketCheckout;

	@BeforeClass
	public static void setUp() {
		ItemCostConfig config = new ItemCostConfig();
		config.setItemCost("Banana", 0.90);
		config.setItemCost("Apple", 1.20);
		config.setItemCost("Orange", 0.50);
		config.setItemCost("Lemon", 0.30);
		config.setItemCost("Peach", 0.40);

		//Please uncomment the below if it is java 1.8 compiler
		/*if (System.getProperty("java.version").startsWith("1.8.")) {
			basketCheckout = new BasketCheckoutIntJava8Impl(config);
		} else {*/
			basketCheckout = new BasketCheckoutIntImpl(config);
		//}
	}

	@Test
	public void testEmptyBasket() {
		List<String> emptyBasket = new ArrayList<String>();
		try {
			assertEquals(0.0, basketCheckout.calculateBasketCost(emptyBasket), 0.0);
		} catch (CheckOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testKnownItemBasket() {
		List<String> knownBasket = new ArrayList<String>();
		knownBasket.addAll(Arrays.asList("Banana", "Orange", "Banana", "Peach", "Peach", "Banana", "Lemon"));
		try {
			assertEquals(4.3, basketCheckout.calculateBasketCost(knownBasket), 0.001);
		} catch (CheckOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testUnknownItemBasket() {
		List<String> unknownBasket = new ArrayList<String>();
		unknownBasket.addAll(Arrays.asList("Banana", "Orange", "Plum", "Lemon"));
		try {
			basketCheckout.calculateBasketCost(unknownBasket);
			fail("Expected Exception did not occur");
		} catch (CheckOutException e) {
			assertSame("Item cost not found", e.getMessage());
		} catch (RuntimeException e) {
			assertSame("Item cost not found", e.getMessage());
		}
	}

	@Test
	public void testNullConfig() {
		BasketCheckoutInt basketCheckoutWithoutConfig = new BasketCheckoutIntImpl(null);
		List<String> someBasket = new ArrayList<String>();
		someBasket.addAll(Arrays.asList("Banana", "Orange", "Lemon"));
		try {
			basketCheckoutWithoutConfig.calculateBasketCost(someBasket);
			fail("Expected Exception did not occur");
		} catch (CheckOutException e) {
			assertSame("Item mising config", e.getMessage());
		}
	}
}
