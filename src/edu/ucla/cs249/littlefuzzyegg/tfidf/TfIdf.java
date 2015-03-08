package edu.ucla.cs249.littlefuzzyegg.tfidf;

import java.util.*;

import edu.ucla.cs249.littlefuzzyegg.data.*;

public class TfIdf {
	private final Map<Indexed<Product>, Integer> bags;
	
	public TfIdf(Map<Indexed<Product>, Integer> bags) {
		this.bags = new HashMap<Indexed<Product>, Integer>(bags);
	}
	
	public double getTfIdf(BagOfTags query, Indexed<Product> product) {
		double ret = 0;
		return ret;
	}
}
