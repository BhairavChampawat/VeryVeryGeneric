package com.mysupermarket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysupermarket.CheckOutException;
import com.mysupermarket.ItemCostConfig;

public class BasketCheckoutIntImpl implements BasketCheckoutInt {

	private final ItemCostConfig config;
	private static final String ITEM_NOT_FOUND = "Item cost not found";
	private static final String ITEM_CONFIG_MISSING = "Item mising config";

	public BasketCheckoutIntImpl(ItemCostConfig config) {
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

		Map<String, Integer> fruitCountMap = new HashMap<String, Integer>();
		for (String item : basket) {
			// check for missing item in config
			if (Double.isNaN(config.getCost(item))) {
				throw new CheckOutException(ITEM_NOT_FOUND);
			}
			if (!fruitCountMap.containsKey(item)) {
				fruitCountMap.put(item, 1);
			} else {
				int current = fruitCountMap.get(item);
				fruitCountMap.put(item, ++current);
			}
		}

		for (String item : fruitCountMap.keySet()) {
			// calculate the cost of each type of items
			basketCost = basketCost + config.getCost(item) * fruitCountMap.get(item);
		}

		return basketCost;
	}

}
