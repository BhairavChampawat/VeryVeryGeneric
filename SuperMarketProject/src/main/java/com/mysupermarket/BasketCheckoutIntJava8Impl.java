package com.mysupermarket;

import java.util.List;

import com.mysupermarket.CheckOutException;

public class BasketCheckoutIntJava8Impl implements BasketCheckoutInt {

	private final ItemCostConfig config;
	private static final String ITEM_NOT_FOUND = "Item cost not found";
	private static final String ITEM_CONFIG_MISSING = "Item mising config";

	public BasketCheckoutIntJava8Impl(ItemCostConfig config) {
		this.config = config;
	}

	public double calculateBasketCost(List<String> basket) throws CheckOutException {

		double basketCost = 0.0;
		// Check for empty or null basket
		if (null == basket || basket.isEmpty()) {
			return basketCost;
		}
		// check for missing config or missing item in config
		if (null == config) {
			throw new CheckOutException(ITEM_CONFIG_MISSING);
		}
		basketCost = basket.stream().filter((item) -> {
			try {
				return isCountEligible(item);
			} catch (CheckOutException e) {
				throw new RuntimeException(e.getMessage());
			}
		}).mapToDouble((item) -> config.getCost(item)).sum();

		return basketCost;
	}

	private boolean isCountEligible(String item) throws CheckOutException {
		// check for missing item in config
		if (Double.isNaN(config.getCost(item))) {
			throw new CheckOutException(ITEM_NOT_FOUND);
		}
		return true;
	}
}
