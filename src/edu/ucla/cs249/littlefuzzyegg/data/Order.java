package edu.ucla.cs249.littlefuzzyegg.data;

public class Order {
	private final String sku;
    private final String user;
    private final long entered;
    private final String query;
    
    public Order(String sku, String user, long entered, String query) {
    	this.sku = sku;
    	this.user = user;
    	this.entered = entered;
    	this.query = query;
    }

	public String getSku() {
		return sku;
	}

	public String getUser() {
		return user;
	}

	public long getEntered() {
		return entered;
	}

	public String getQuery() {
		return query;
	}
	
	@Override
	public String toString() {
		return "(" + sku + "," + user + "," + entered + "," + query + ")";
	}
}
