package edu.ucla.cs249.littlefuzzyegg.tfidf;

import java.util.*;

public class Indexed<T> {
	private final T key;
	private final BagOfTags bag;
	
	public Indexed(T key) {
		this.key = key;
		this.bag = new BagOfTags();
	}
	
	public void setWeights(List<Tag> tags) {
		bag.setWeights(tags);
	}
	
	public void setWeights(List<Tag> tags, int weight) {
		bag.setWeights(tags, weight);
	}
	
	public T getKey() {
		return key;
	}
	
	public List<Tag> getTags() {
		return bag.getTags();
	}
	
	public int getWeight(Tag tag) {
		return bag.getWeight(tag);
	}
}
