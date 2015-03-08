package edu.ucla.cs249.littlefuzzyegg.split;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import edu.ucla.cs249.littlefuzzyegg.data.Order;
import edu.ucla.cs249.littlefuzzyegg.data.Product;
import edu.ucla.cs249.littlefuzzyegg.tfidf.Tag;

public class Bagger {
	private static final String YEAR_OR_VERSION = "d+";
	
	// this is for the termList building
	private static final Pattern TERM_SEPARATOR_PATTERN = Pattern.compile("[: \\+-/\\(\\)]");
    private static final Splitter TERM_SPLITTER = Splitter.on(TERM_SEPARATOR_PATTERN)
    													  .omitEmptyStrings().trimResults();
	
    // this if for the phraseList building and get the abbreviation
    private static final Pattern PHRASE_SEPARATOR_PATTERN = Pattern.compile("[:\\+-/\\(\\)]");
    private static final Splitter PHRASE_SPLITTER = Splitter.on(PHRASE_SEPARATOR_PATTERN)
    														.omitEmptyStrings().trimResults();
    
	public static List<Tag> toBag (Product product) {
		List<Tag> result = new ArrayList<Tag>();
		String productName = product.getName();
		
		// handle the acronym
		List<Tag> acronyms = toAcronym(productName);
		
		List<String> termList = toDict(productName);
		
		
		return null;
	}
	private static List<Tag> toAcronym(String productName) {
		List<Tag> acronymList = new ArrayList<Tag> ();
		
		// separate the productName to phrase and build the acronym
		for (String phrase : PHRASE_SPLITTER.split(productName)) {
			List<String> terms = Lists.newArrayList(TERM_SPLITTER.split(phrase));
			
		}
		
		return acronymList;
	}
	
	
	private static List<String> toDict (String productName) {
		List<String> termList = Lists.newArrayList(TERM_SPLITTER.split(productName));
		// put them into dictionary
		Dictionary.getInstance().importWords(termList);
		return termList;
	}
	
	public static List<Tag> toBag (Order order) {
		return null;
	}
}
