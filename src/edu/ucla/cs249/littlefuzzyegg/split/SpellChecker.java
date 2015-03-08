package edu.ucla.cs249.littlefuzzyegg.split;

import java.util.List;
import java.util.Vector;

public class SpellChecker {
	// for further data structures
	
	private static SpellChecker instance = null;
	
	private SpellChecker() {
		// for further hard-coding
	}
	
	public static SpellChecker getInstance() {
		if (instance == null)
			instance = new SpellChecker();
		return instance;
	}
	
	public String check (String checkWord) {
		List<String> dict = Dictionary.getInstance().getList();
		if (dict.contains(checkWord))
			return checkWord;
		int minScore = Integer.MAX_VALUE;
		String candidate = null;
		for (String word : dict) {
			int tempScore = minDistance(checkWord, word);
			if (tempScore < minScore) {
				minScore = tempScore;
				candidate = word;
			}
		}
		// if the minScore is bigger than 3, it means there is no word in dict to correct it
		// just return the original word
		if (minScore > 3)
			return checkWord;
		return candidate;
	}
	
	// edit distance score function
	private int minDistance(String word1, String word2) {
		int len1 = word1.length();
		int len2 = word2.length();
		int[][] dp = new int[len1 + 1][len2 + 1];
	 
		for (int i = 0; i <= len1; i++) {
			dp[i][0] = i;
		}
	 
		for (int j = 0; j <= len2; j++) {
			dp[0][j] = j;
		}
		//iterate though, and check last char
		for (int i = 0; i < len1; i++) {
			char c1 = word1.charAt(i);
			for (int j = 0; j < len2; j++) {
				char c2 = word2.charAt(j);
	 
				//if last two chars equal
				if (c1 == c2) {
					//update dp value for +1 length
					dp[i+1][j+1] = dp[i][j];
				} else {
					int replace = dp[i][j] + 1;
					int insert = dp[i][j+1] + 1;
					int delete = dp[i+1][j] + 1;
	 
					int min = replace > insert ? insert : replace;
					min = delete > min ? min : delete;
					dp[i+1][j+1] = min;
				}
			}
		}
		return dp[len1][len2];
	}
}
