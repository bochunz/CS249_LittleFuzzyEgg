package edu.ucla.cs249.littlefuzzyegg.split;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;

import edu.ucla.cs249.littlefuzzyegg.data.Order;

public class OrderHistory {
	
	/*
	 * < UserId - sku >
	 */
	private HashMultimap<String, String> history = HashMultimap.create();
	
	public OrderHistory(List<Order> orderList) {
		for (Order o : orderList) {
			history.put(o.getUser(), o.getSku());
		}
	}
	
	public List<String> getOrderHistory(String userId) {
		return Lists.newArrayList(history.get(userId));
	}
}
