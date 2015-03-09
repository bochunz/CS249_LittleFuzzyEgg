package edu.ucla.cs249.littlefuzzyegg.tfidf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ucla.cs249.littlefuzzyegg.split.Dictionary;
import edu.ucla.cs249.littlefuzzyegg.tfidf.Tag.Type;

public class BagOfTags {
	private final static int PRODUCT_WEIGHT = 15;
	private final Map<Tag, Integer> count = new HashMap<Tag, Integer>();
	
	public void addCount(List<Tag> tags, boolean fromProduct) {
		int weight = fromProduct ? PRODUCT_WEIGHT : 1;
		addCount(tags, weight);
	}
	
	public void addCount(List<Tag> tags, int c) {
		for(Tag tag : tags) {
			count.put(tag, getCount(tag) + c);
		}
	}
	
	public List<Tag> getTags() {
		List<Tag> ret = new ArrayList<Tag>();
		ret.addAll(count.keySet());
		return ret;
	}
	
	public int getCount(Tag tag) {
		return count.containsKey(tag) ? count.get(tag) : 0;
	}
	
}
