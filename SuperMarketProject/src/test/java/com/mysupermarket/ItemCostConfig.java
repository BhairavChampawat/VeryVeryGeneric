package com.mysupermarket;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ItemCostConfig {
	private ConcurrentMap<String,Double> itemCostMap = new ConcurrentHashMap<String,Double>();
	
	public double getCost(String name) {
		if(itemCostMap.containsKey(name)){
			return itemCostMap.get(name);
		} else {
			return Double.NaN;
		}
		
	}
	public void setItemCost(String name,double cost) {
		 itemCostMap.putIfAbsent(name,cost);
	}
}
