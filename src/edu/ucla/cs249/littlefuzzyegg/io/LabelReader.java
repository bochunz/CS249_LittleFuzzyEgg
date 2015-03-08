package edu.ucla.cs249.littlefuzzyegg.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LabelReader {
	public static List<String> ReadLabels(String filename) {
		List<String> labels = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader( new FileReader (new File(filename)));
		    String line = null;
		    StringBuilder stringBuilder = new StringBuilder();
		    String ls = System.getProperty("line.separator");
	
		    while( ( line = reader.readLine() ) != null ) {
		        labels.add(line);
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return labels;
	}

	public static void main(String[] argv) {
		System.out.println(ReadLabels("mytest_label.csv"));
	}
}
