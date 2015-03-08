package edu.ucla.cs249.littlefuzzyegg.data;

public class Product {
	public static final int NO_RANK = 100000;
	
	private final String name;
    private final String sku;
    private final long startDate;
    private final int rank;
    
    public Product(String name, String sku, long startDate, int rank) {
		this.name = name;
		this.sku = sku;
		this.startDate = startDate;
		this.rank = rank;
	}

	public String getName() {
		return name;
	}

	public String getSku() {
		return sku;
	}

	public long getStartDate() {
		return startDate;
	}

	public int getRank() {
		return rank;
	}
	
	@Override
	public String toString() {
		return "(" + sku + "," + name + "," + startDate + "," + rank + ")";
	}
}
