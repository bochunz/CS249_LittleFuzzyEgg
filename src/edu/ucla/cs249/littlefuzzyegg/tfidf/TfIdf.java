package edu.ucla.cs249.littlefuzzyegg.tfidf;

import java.util.*;

import edu.ucla.cs249.littlefuzzyegg.data.*;
import edu.ucla.cs249.littlefuzzyegg.split.Dictionary;
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
		//System.out.println("tags size: " + tags.size());
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
		
		// TF_ij = f_ij / max_k{f_kj}
		/*double norm = tf;
		for(Tag t : product.getTags())
			norm = Math.max(norm, product.getCount(t));
		tf = 0.5 + 0.5 * tf / norm;
		//tf = tf / norm;*/
		
		// TF_ij = 1 + log(f_ij)
		//tf = tf == 0 ? 0 : 1 + Math.log(tf);

		// TF_ij = log(1 + f_ij)
		tf = Math.log(1 + tf);
		
		if (tag.getType() == Type.ALSO) tf *= 0.8;
		else if (tag.getType() == Type.ACRONYM) tf *= 0;
		if (Dictionary.getInstance().isImportant(tag))
			tf *= 2;

		// IDF_i = log(1 + N / n_i)
		//double idf = Math.log(indexedProducts.size()) - Math.log(tags.get(tag));
		double idf = Math.log(1 + indexedProducts.size() / tags.get(tag));
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
				if (this.count == other.count) {
					return Integer.valueOf(this.product.getKey().getRank()).compareTo(other.product.getKey().getRank());
				}
				else return (this.count< other.count ? 1 : -1);
			}
			return this.score < other.score ? 1 : -1;
		}
	}
}
