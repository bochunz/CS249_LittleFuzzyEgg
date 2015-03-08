package edu.ucla.cs249.littlefuzzyegg.split;

import java.util.ArrayList;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.sun.tools.javac.util.List;

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
	
	public ArrayList<String> getOrderHistory(String userId) {
		return Lists.newArrayList(history.get(userId));
	}
}
