package edu.ucla.cs249.littlefuzzyegg.tfidf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BagOfTags {
	private final Map<Tag, Integer> weights = new HashMap<Tag, Integer>();
	
	public void setWeights(List<Tag> tags) {
		setWeights(tags, 1);
	}
	
	public void setWeights(List<Tag> tags, int weight) {
		for(Tag tag : tags) {
			weights.put(tag, weight);
		}
	}
	
	public List<Tag> getTags() {
		List<Tag> ret = new ArrayList<Tag>();
		ret.addAll(weights.keySet());
		return ret;
	}
}
