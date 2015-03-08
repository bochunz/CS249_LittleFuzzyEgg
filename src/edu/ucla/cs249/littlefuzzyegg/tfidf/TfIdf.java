package edu.ucla.cs249.littlefuzzyegg.tfidf;

import java.util.*;

import edu.ucla.cs249.littlefuzzyegg.data.*;
import edu.ucla.cs249.littlefuzzyegg.tfidf.Tag.Type;

public class TfIdf {
	private final Map<Indexed<Product>, Integer> indexedProducts;
	private final Map<Tag, Integer> tags;
	
	public TfIdf(Map<Indexed<Product>, Integer> products) {
		this.indexedProducts = new HashMap<Indexed<Product>, Integer>(products);
		this.tags = new HashMap<Tag, Integer>();
		for(Indexed<Product> product : products.keySet()) {
			for(Tag tag : product.getTags()) {
				int c = this.tags.containsKey(tag) ? this.tags.get(tag) + 1 : 1;
				this.tags.put(tag, c);
			}
		}
		System.out.println("tags size: " + tags.size());
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
		
		// TF_ij = f_ij / max_k{f_kj}
		double tf = product.getCount(tag);
		double norm = tf;
		for(Tag t : product.getTags())
			norm = Math.max(norm, product.getCount(t));
		if (norm > 0)
			tf /= norm;
		else
			tf = 0;
		if (tag.getType() == Type.ALSO) tf *= 0.8;
		else if (tag.getType() == Type.ACRONYM) tf *= 1.5;
		
		// IDF_i = log(N / n_i)
		double idf = Math.log(indexedProducts.size()) - Math.log(tags.get(tag));
		return tf * idf;
	}
	
	public List<Product> getPrediction(Indexed<Order> query, int k) {
		List<Score> scores = new ArrayList<Score>();
		for(Indexed<Product> product : indexedProducts.keySet()) {
			double tfIdf = getTfIdf(query.getBag(), product);
			scores.add(new Score(tfIdf, product));
		}
		Collections.sort(scores);
		List<Product> ret = new ArrayList<Product>();
		for(Score score : scores.subList(0, k)) {
			//System.out.println(score.score);
			ret.add(score.product.getKey());
		}
		return ret;
	}
	
	class Score implements Comparable<Score> {
		double score;
		Indexed<Product> product;
		int count;
		
		public Score(double score, Indexed<Product> product) {
			this.score = score;
			this.product = product;
			this.count = indexedProducts.get(product);
		}
		
		@Override
		public int compareTo(Score other) {
			if (this.score == other.score) {
				// choose the product which occurs more frequently in the train set  
				if (this.count == other.count) return 0;
				else return (this.count< other.count ? 1 : -1);
			}
			return this.score < other.score ? 1 : -1;
		}
	}
}
