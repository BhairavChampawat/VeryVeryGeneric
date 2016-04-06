package com.mysupermarket;

import java.util.List;

import com.mysupermarket.CheckOutException;

public interface BasketCheckoutInt {
	public double calculateBasketCost(List<String> basket) throws CheckOutException;
	}
