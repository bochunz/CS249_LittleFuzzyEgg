package edu.ucla.cs249.littlefuzzyegg;

import java.util.*;

import com.google.common.collect.Maps;

import edu.ucla.cs249.littlefuzzyegg.data.*;
import edu.ucla.cs249.littlefuzzyegg.tfidf.*;
import edu.ucla.cs249.littlefuzzyegg.io.*;
import edu.ucla.cs249.littlefuzzyegg.split.*;


public class Main {
	
	/*
	 * < sku - indexedProduct >
	 */
	private static Map<String, Indexed<Product>> indexedProductMap = Maps.newHashMap();
	private static int totalTest = 0;
	
	public final static int TOP_N = 5;
	
	public static void main(String[] args) {
		if (args.length != 5) {
			System.out.println("Usage: <product data filename> <product rank filename> "
								+ "<train set filename> <test set filename> "
								+ "<answer filename>");
			return;
		}
		String productSpecFile = args[0];
		String productRankFile = args[1];
		String trainFile = args[2];
		String testFile = args[3];
		String answerFile = args[4];
		
		/*
		 * 1.1 Read product data file, together with rank file
		 */
		
		Map<String, Integer> rankMap = RankReader.ReadRank(productRankFile);
		List<Product> productList = ProductReader.ReadProducts(productSpecFile, rankMap);
		
		/*
		 * 1.2 Read train file and test file
		 */
		
		List<Order> orderList = OrderReader.ReadTrainOrders(trainFile);
		List<Order> testList = OrderReader.ReadTestOrders(testFile);
		
		/*
		 * 1.3 Read answer file
		 */
		
		List<String> answerList = LabelReader.ReadLabels(answerFile);
		
		/*
		 * 2.1 Generate orderHistory
		 */
		
		OrderHistory orderHistory = new OrderHistory(orderList);
		System.out.println("2.1");
		/*
		 * 2.2 Generate List of Tags for each product and insert into indexedProductMap
		 */
		
		for (Product product : productList) {
			List<Tag> tagList = Bagger.toBag(product);
			Indexed<Product> p = new Indexed<Product>(product);
			p.addCount(tagList, true);
			indexedProductMap.put(product.getSku(), p);
		}
		System.out.println("2.1a");
		for (Order o : orderList) {
			//System.out.println(o.getUser()+" "+o.getSku());
			Indexed<Product> p = indexedProductMap.get(o.getSku());
			if (p != null) {
				List<Tag> tagList = Bagger.toBag(o, orderHistory, true);
				p.addCount(tagList, false);
			}
		}
		System.out.println("2.2");
		/*
		 * 2.3 Construct orderCount : < indexedProduct - number of orders on that product >
		 */
		
		Map<Indexed<Product>, Integer> orderCount = Maps.newHashMap();
		for (Indexed<Product> p : indexedProductMap.values()) {
			orderCount.put(p, 0);
		}
		for (Order o : orderList) {
			Indexed<Product> p = indexedProductMap.get(o.getSku());
			if (orderCount.get(p) != null) {
				orderCount.put(p, orderCount.get(p)+1);
			}
		}
		System.out.println("2.3");
		/*
		 * 3.1 Generate instance of TfIdf
		 */
		
		TfIdf tfIdf = new TfIdf(orderCount);
		System.out.println("3.1");
		/* 
		 * 3.2 For each testOrder, select top-N products
		 * 3.3 Compare with answer list and calculate accuracy
		 */
		int correctNumber = 0;
		for (Order testOrder : testList) {
			List<Tag> tagList = Bagger.toBag(testOrder, orderHistory, false);
			Indexed<Order> o = new Indexed<Order>(testOrder);
			o.addCount(tagList, false);
			List<Product> result = tfIdf.getPrediction(o, TOP_N);
			for (Product p : result) {
				if (answerList.get(totalTest).compareTo(p.getSku()) == 0) {
					correctNumber++;
					break;
				}
			}
			totalTest ++;
		}
		System.out.println("3.23");
		/*
		 * 4. Output Accuracy
		 */
		System.out.println("The accuracy is "+(double)correctNumber/(double)totalTest);
		System.out.println("4");
	}
	
	public void printPrediction(Order testOrder, List<Product> result) {
		System.out.println("------------------------------------------");
		System.out.println("Order query: " + testOrder.getQuery());
		int i = 0;
		for(Product p : result) {
			++i;
			System.out.println("rank " + i + ": " + p.getName());
		}
	}
}
