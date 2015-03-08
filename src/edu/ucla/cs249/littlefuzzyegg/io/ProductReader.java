package edu.ucla.cs249.littlefuzzyegg.io;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.sun.org.apache.bcel.internal.generic.NEW;

import edu.ucla.cs249.littlefuzzyegg.data.Product;

public class ProductReader {
	private static final String [] FILE_HEADER_MAPPING = {"sku","name","sdate"};
	
	public static List<Product> ReadProducts(String filename, Map<String, Integer> rankMap) {
		FileReader fileReader = null;
		CSVParser csvFileParser = null;
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING);
		
		List products = new ArrayList<Product>();
		try {
			fileReader = new FileReader(filename);
			csvFileParser = new CSVParser(fileReader, csvFileFormat);
			List csvRecords = csvFileParser.getRecords(); 
			for (int i = 1; i < csvRecords.size(); i++) {
                CSVRecord record = (CSVRecord) csvRecords.get(i); 
                int rank = Product.NO_RANK;
                if (rankMap.containsKey(record.get("sku"))) {
                	rank = rankMap.get(record.get("sku"));
                }
                long sdate = new SimpleDateFormat("yyyy-MM-dd").parse(record.get("sdate")).getTime();
                products.add(new Product(record.get("name"), record.get("sku"), sdate, rank));
            }

			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fileReader.close();
                csvFileParser.close();
            } catch (IOException e) {
                System.out.println("Error while closing fileReader/csvFileParser !!!");
                e.printStackTrace();
            }

		}
		return products;
	}
	
	public static void main(String[] argv) {
		System.out.print(ReadProducts("small_product_data.csv", RankReader.ReadRank("ranks.csv")));
	}
}
