package edu.ucla.cs249.littlefuzzyegg.split;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.ucla.cs249.littlefuzzyegg.data.Product;

public class Dictionary {
	private static int ELIMINATE_LENGTH = 2;
	private Set<String> words;
	private static Dictionary instance = null;
	
	private Dictionary() {
		words = new HashSet<String>();
	}
	
	public static Dictionary getInstance() {
		if (instance == null)
			instance = new Dictionary();
		return instance;
	}
	
	public void importWords (List<String> wordList) {
		for (String word : wordList) {
			if (word.length() > ELIMINATE_LENGTH)
				words.add(word);
		}
	}
	
	public List<String> getList() {
		return new ArrayList<String> (words);
	}
	
}
