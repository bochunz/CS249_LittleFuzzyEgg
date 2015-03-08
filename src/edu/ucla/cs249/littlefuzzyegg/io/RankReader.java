package edu.ucla.cs249.littlefuzzyegg.io;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.*;

public class RankReader {
	private static final String [] FILE_HEADER_MAPPING = {"sku","rank"};
	
	public static Map<String, Integer> ReadRank(String filename) {
		FileReader fileReader = null;
		CSVParser csvFileParser = null;
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING);
		
		Map rankMap = new HashMap<String, Integer>();
		try {
			fileReader = new FileReader(filename);
			csvFileParser = new CSVParser(fileReader, csvFileFormat);
			List csvRecords = csvFileParser.getRecords(); 
			for (int i = 1; i < csvRecords.size(); i++) {
                CSVRecord record = (CSVRecord) csvRecords.get(i); 
                rankMap.put(record.get("sku"), Integer.parseInt(record.get("rank")));
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
		return rankMap;
	}
	
	public static void main(String[] argv) {
		System.out.print(ReadRank("ranks.csv"));
	}
}
