package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TimeSeries {
	
	Map<String, ArrayList<Float>> ts;
	ArrayList<String> atts;
	int dataRowSize;
	
	public TimeSeries(String csvFileName) {
		ts=new HashMap<>();
		atts=new ArrayList<>();
		try {
			BufferedReader in=new BufferedReader(new FileReader(csvFileName));
			String line=in.readLine();
			for(String att : line.split(",")) {
				atts.add(att);
				ts.put(att, new ArrayList<>());
			}
			while((line=in.readLine())!=null) {
				int i=0;
				for(String val : line.split(",")) {
					ts.get(atts.get(i)).add(Float.parseFloat(val));
					i++;
				}
			}
			dataRowSize=ts.get(atts.get(0)).size();
			
			in.close();
		}catch(IOException e) {}
	}
	
	public ArrayList<Float> getAttributeData(String name){
		return ts.get(name);
	}
	
	public ArrayList<String> getAttributes(){
		return atts;
	}
	
	public int getRowSize() {
		return dataRowSize;
	}
}
