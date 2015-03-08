package edu.ucla.cs249.littlefuzzyegg.tfidf;

import java.util.*;

import edu.ucla.cs249.littlefuzzyegg.data.*;
import edu.ucla.cs249.littlefuzzyegg.tfidf.Tag.Type;

public class TfIdf {
	private final Map<Indexed<Product>, Integer> bags;
	private final Map<Tag, Integer> tags;
	
	public TfIdf(Map<Indexed<Product>, Integer> bags) {
		this.bags = new HashMap<Indexed<Product>, Integer>(bags);
		this.tags = new HashMap<Tag, Integer>();
		for(Indexed<Product> bag : bags.keySet()) {
			for(Tag tag : bag.getTags()) {
				int c = this.tags.containsKey(tag) ? this.tags.get(tag) + 1 : 1;
				this.tags.put(tag, c);
			}
		}
	}
	
	public double getTfIdf(BagOfTags query, Indexed<Product> product) {
		double ret = 0;
		for(Tag tag : query.getTags()) {
			ret += getTfIdf(tag, product);
		}
		return ret;
	}
	
	private double getTfIdf(Tag tag, Indexed<Product> product) {
		if (!tags.containsKey(tag)) return 0;
		double tf = product.getCount(tag);
		double norm = tf;
		for(Tag t : product.getTags())
			norm = Math.max(norm, product.getCount(t));
		tf /= norm;
		if (tag.getType() == Type.ALSO) tf *= 0.8;
		else if (tag.getType() == Type.ACRONYM) tf *= 1.5;
		double idf = Math.log(bags.size()) - Math.log(tags.get(tag));
		return tf * idf;
	}
	
	public List<Product> getPrediction(BagOfTags query, int k) {
		List<Score> scores = new ArrayList<Score>();
		for(Indexed<Product> product : bags.keySet()) {
			double tfIdf = getTfIdf(query, product);
			scores.add(new Score(tfIdf, product));
		}
		Collections.sort(scores);
		List<Product> ret = new ArrayList<Product>();
		for(Score score : scores.subList(0, k))
			ret.add(score.product.getKey());
		return ret;
	}
	
	class Score implements Comparable<Score> {
		double score;
		Indexed<Product> product;
		int count;
		
		public Score(double score, Indexed<Product> product) {
			this.score = score;
			this.product = product;
			this.count = bags.get(product);
		}
		
		@Override
		public int compareTo(Score other) {
			if (this.score == other.score) {				
				return this.count < other.count ? 1 : -1;
			}
			return this.score < other.score ? 1 : -1;
		}
	}
}
