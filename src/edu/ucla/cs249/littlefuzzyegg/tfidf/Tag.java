package edu.ucla.cs249.littlefuzzyegg.tfidf;

public class Tag {
	private Type type;
	private String value;
	
	public enum Type {
		WORD,
		DATE,
		ALSO,
		ACRONYM
	}
}
