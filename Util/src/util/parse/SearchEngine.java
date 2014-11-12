package util.parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchEngine {

	public static void main(String[] args) {
		search();
	}
	
	public static void search(){
		
		Pattern pattern = Pattern.compile("\"[a-zA-Z0-9.]*\\.[a-zA-Z0-9.]*\"");
		Matcher matcher = null;
		
		File directory = new File("/home/faisal/Documents/validators/");
		BufferedReader br = null;
		PrintWriter pw = null;
		String text = null;
		
		
		for(File file : directory.listFiles()){
			if(file.isFile() && file.getName().contains("Validator.java")){
				System.out.println("Extracting codes from "+file.getName());
				List<StringBuilder> codes = new ArrayList<StringBuilder>();
				try {
					
					//Reading codes
					br= new BufferedReader(new FileReader(file));
					while((text = br.readLine()) != null){
						matcher = pattern.matcher(text);
						while(matcher.find()){
							StringBuilder code = new StringBuilder(matcher.group());
							code.deleteCharAt(0).deleteCharAt(code.length()-1).append("=").append(code.subSequence(0, code.length()-1));
							codes.add(code);
						}
					}
					br.close();
					
					//Writing Codes
					StringBuilder targetFile = new StringBuilder(file.getName());
					targetFile.delete(targetFile.indexOf("."), targetFile.length()).append("Codes.txt");
							
					pw = new PrintWriter(new File(directory, targetFile.toString()));
					for(StringBuilder code : codes){
						pw.println(code);
					}
					pw.flush();
					pw.close();
					System.out.println("Codes are extracted to "+targetFile.toString());
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("Codes Extracted Successfully");
	}
}
