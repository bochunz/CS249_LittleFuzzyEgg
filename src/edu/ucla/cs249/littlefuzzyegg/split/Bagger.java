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
	private static final String NUMBER = "\\d\\w*";
	
	// this is for the termList building
	private static final Pattern TERM_SEPARATOR_PATTERN = Pattern.compile("[: \\+\\-\\(\\)]");
    private static final Splitter TERM_SPLITTER = Splitter.on(TERM_SEPARATOR_PATTERN)
    													  .omitEmptyStrings().trimResults();
	
    // this if for the phraseList building and get the abbreviation
    private static final Pattern PHRASE_SEPARATOR_PATTERN = Pattern.compile("[:\\+-/\\(\\)]");
    private static final Splitter PHRASE_SPLITTER = Splitter.on(PHRASE_SEPARATOR_PATTERN)
    														.omitEmptyStrings().trimResults();
    
    public static List<Tag> toBag(Order order, OrderHistory history, boolean isTraining) {
    	List<Tag> result = new ArrayList<Tag>();
    	String query = order.getQuery().toLowerCase();
    	String user = order.getUser();
    	long entered = order.getEntered();
    	// first put entered time as an DATA tag
    	result.add(Tag.date(entered));
    	
    	// add also
    	List<String> alsoList = history.getOrderHistory(order.getUser());
    	for (String alsoSku : alsoList) {
    		result.add(Tag.also(alsoSku));
    	}
    	
    	// add the acronym
    	result.addAll(Lists.newArrayList(toAcronym(query)));
    	
//    	System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
//    	for (Tag tag : result) {
//    		System.out.println(tag.getValue() + " " + tag.getType().toString() + " ");
//    	}
//    	
    	// then we do on the query
    	List<String> termList = Lists.newArrayList(TERM_SPLITTER.split(query));
    	String prev = null;
    	for (int i = 0; i < termList.size(); ++i) {
    		String current = termList.get(i);
    		
    		// if current is a number
    		// append with prev and we jump this term
    		if (current.matches(NUMBER)) {
    			if (prev == null)
    				result.add(Tag.word(prev + current));
    			continue;
    		}
    		
    		// here we try to correct the word
    		// if the checker returns a null, meaning we cannot use it to correct
    		// then the corrected is current
    		String corrected = null;
    		if (current.length() > Dictionary.ELIMINATE_LENGTH)
    			corrected = SpellChecker.getInstance().check(current);
    		
    		if (corrected == null)
    			corrected = current;
    		
    			
    		// concatenate the prev with the current corrected
    		String append = prev + corrected;
    		
    		// we should put the corrected into the list of tags
    		result.add(Tag.word(corrected));
    		prev = corrected;
    		
    		// we try to append the current with the prev and check if it is a word
    		String correctedAppend = SpellChecker.getInstance().check(append);
    		if (correctedAppend != null) {
    			result.add(Tag.word(correctedAppend));
    			prev = correctedAppend;
    		}
    	}
    	
    	
    	
    	if (isTraining)
    		result.add(Tag.word(order.getSku()));
    	return result;
    }
    
    
    
	public static List<Tag> toBag (Product product) {
		List<Tag> result = new ArrayList<Tag>();
		String productName = product.getName().toLowerCase();
		
		// handle the acronym
		List<Tag> acronyms = toAcronym(productName);
		
		// put into result
		result.addAll(acronyms);
		
		// put into the dictionary and return the terms in refined form
		List<String> termList = toDict(productName);
		
		String prev = null;
		for (int i = 0; i < termList.size(); ++i) {
			String current = termList.get(i);
			// if the current is a version number or year
			if (current.matches(NUMBER)) {
				if (prev != null)
					result.add(Tag.word(prev + current));
			} else
				result.add(Tag.word(current));
			prev = current;
		}
		result.add(Tag.word(product.getSku()));
		return result;
	}
	private static List<Tag> toAcronym(String productName) {
		List<Tag> acronymList = new ArrayList<Tag> ();
		
		// separate the productName to phrase and build the acronym
		for (String phrase : PHRASE_SPLITTER.split(productName)) {
			List<String> terms = Lists.newArrayList(TERM_SPLITTER.split(phrase));
			String acronym = "";
			for (String term : terms) {
				String temp = refineWord(term);
				if (temp.matches(NUMBER))
					acronym += temp;
				else
					acronym += temp.charAt(0);
			}
			
			// we don't want length one acronym
			if (acronym.length() > 1) {
				// get the acronym and put it as both word and acronym tag
				acronymList.add(Tag.acronym(acronym));
				acronymList.add(Tag.word(acronym));
			}
		}
		return acronymList;
	}
	
	
	private static List<String> toDict (String productName) {
		List<String> termList = Lists.newArrayList(TERM_SPLITTER.split(productName));
		for (int i = 0; i < termList.size(); ++i)
			termList.set(i, refineWord(termList.get(i)));
		// put them into dictionary
		Dictionary.getInstance().importWords(termList);
		return termList;
	}

	private static String refineWord (String word) {
		word = word.replaceAll("'s$", "").replace(".", "");
		if (word.matches("20\\d\\d"))
			word = word.substring(2);
		return word;
	}
	
	
	public static void main(String[] args) {
//		List<Tag> test = toAcronym("NCAA Football 12");
//		for (Tag tag : test) {
//			System.out.print(tag.getValue() + " " + tag.getType().toString() + " ");
//		}
//		Product product = new Product("Call Of Duty : Modern Warfare 2 - Xbox 360", "2670133", 123, 12);
//		List<Tag> test = toBag(product);
//		for (Tag tag : test) {
//			System.out.println(tag.getValue() + " " + tag.getType().toString() + " ");
//		}
//		
		Order order = new Order("", "Shaoxiang", 1318030911, "Call of duty");
		List<Tag> test = toBag(order, null, false);
		for (Tag tag : test) {
			System.out.println(tag.getValue() + " " + tag.getType().toString() + " ");
		}
	}
	
}
