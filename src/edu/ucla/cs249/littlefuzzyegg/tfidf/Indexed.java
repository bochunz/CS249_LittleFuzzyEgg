package edu.ucla.cs249.littlefuzzyegg.tfidf;

import java.util.*;

public class Indexed<T> {
	private final T key;
	private final BagOfTags bag;
	
	public Indexed(T key) {
		this.key = key;
		this.bag = new BagOfTags();
	}
	
	public void addCount(List<Tag> tags) {
		bag.addCount(tags);
	}
	
	public void addCount(List<Tag> tags, int c) {
		bag.addCount(tags, c);
	}
	
	public T getKey() {
		return key;
	}
	
	public List<Tag> getTags() {
		return bag.getTags();
	}
	
	public int getCount(Tag tag) {
		return bag.getCount(tag);
	}
	
	public BagOfTags getBag() {
		return bag;
	}
}
