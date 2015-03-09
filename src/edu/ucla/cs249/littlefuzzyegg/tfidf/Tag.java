package edu.ucla.cs249.littlefuzzyegg.tfidf;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tag {
	private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd"); 
	private Type type;
	private String value;
	
	public enum Type {
		WORD,
		DATE,
		ALSO,
		ACRONYM
	}
	
	private Tag(Type type, String value) {
		this.type = type;
		this.value = value;
	}
	
	public static Tag word(String word) {
		return new Tag(Type.WORD, word);
	}
	
	public static Tag date(long date) {
		return new Tag(Type.DATE, DATE_FORMAT.format(date));
	}
	
	public static Tag also(String value) {
		return new Tag(Type.ALSO, value);
	}
	
	public static Tag acronym(String acronym) {
		return new Tag(Type.ACRONYM, acronym);
	}
	
	public Type getType() {
		return this.type;
	}
	
	public String getValue() {
		return this.value;
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other == null || this.getClass() !=other.getClass()) return false;
		Tag t = (Tag)other;
		return (this.type == t.type && this.value.equals(t.value));
	}
	
	@Override
	public int hashCode() {
		int ret = type != null ? type.hashCode() : 0;
		ret = value.hashCode() * 31 + ret;
		return ret;
	}
}
