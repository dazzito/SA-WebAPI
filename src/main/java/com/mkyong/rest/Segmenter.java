package com.mkyong.rest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.google.gson.Gson;

public class Segmenter {
	WordCache wordCache = new WordCache();
	ArrayList<ArrayList<String>> sentenceCombinationCache = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<Word>> result = new ArrayList<ArrayList<Word>>();
	ArrayList<String> temp = new ArrayList<String>();
	ArrayList<Word> words = new ArrayList<Word>();
	String inputString = "";
	
	public Segmenter() throws FileNotFoundException, IOException{
		wordCache.initializeCache();
	}
	
	private boolean isThai(String str){
		return !str.matches("[ ^A-Za-z0-9]");
	}
	
	private boolean validFirstWord(String input){
//		if(!isThai(input)){
//			return false;
//		}
		//System.out.println("Testing: " + input);
		for(int endIndex = 1; endIndex < input.length()-1; ++endIndex){
			String str = input.substring(0, endIndex);
			if(wordCache.containsKey(str)){
				//System.out.println("Next word: " + str);
				return true;
			}
		}
		System.out.println("Invalid next word, cannot rollback. Continuing...");
		return false;
	}
	
	public String cut( String input){
		input = input.replace(" ", ""); //trim spaces
		System.out.println(input);
		
		int incorrectCounter = 0;
		ArrayList<Integer> tempData = new ArrayList<Integer>(), output = new ArrayList<Integer>();
		boolean found = false, previousNotFound = false;
			for(int index = 0, endIndex = 1; index < input.length(); ++endIndex){
				
				//Skip symbols, numbers, and english alphabet
//				if(isThaiChar(input.charAt(index))){
					String str = input.substring(index, endIndex);
					if(wordCache.containsKey(str)){
						if(!found){ //1st match
							//create new sentence
							tempData.clear();
							output.add(endIndex - index);					
							System.out.print(output);
							System.out.print(input.substring(index, endIndex));
							found = true;
						} else { //2nd and onwards match
							tempData.add(output.get(output.size()-1)); // store data in case of roll-back
							output.set(output.size()-1, endIndex - index);
							System.out.print(" ---> " + output);
							System.out.print(input.substring(index, endIndex));
						}
					}
					
					if(endIndex == input.length()){
						
						if(!found){
							
							if(previousNotFound){
								output.set(output.size()-1, output.get(output.size()-1) + 1);
								System.out.print(" * ---> " + output);
								System.out.print(input.substring(index-output.get(output.size()-1) + 1, index + 1));								
								++index;
							} else {
								
								boolean validNextWord = false;
								if(!tempData.isEmpty()){ //check for alternative words in tempData array
									int tempData_index = -1;
									for(int i = 0; i < tempData.size(); ++i){
									//	System.out.println(input.substring(index-tempData.get(i)+1));
										if(validFirstWord(input.substring(index-tempData.get(i)+1))){
											tempData_index =  i;
										}
									}
									
									if(tempData_index != -1){
										System.out.print("R ---> " +input.substring(index-tempData.get(tempData_index) - tempData.get(tempData_index) + 1, index-tempData.get(tempData_index) + tempData.get(tempData_index) - 1));
										output.set(output.size()-1,tempData.get(tempData_index));
										index = index-tempData.get(tempData_index)+1;
									} else {
										output.add(1);
										previousNotFound = true;
										System.out.print(" *" + output);
										System.out.print(input.substring(index, index + output.get(output.size()-1)));
										++incorrectCounter;
										++index;
									}
								
									
								} else {
									output.add(1);
									previousNotFound = true;
									System.out.print(" *" + output);
									System.out.print(input.substring(index, index + output.get(output.size()-1)));
									++incorrectCounter;
									++index;
								}
							
								
							}
												
						} else {
							index += output.get(output.size()-1);
							previousNotFound = false;
							
						}
						
						System.out.println();
						
						endIndex = index;
						found = false;
					}
			//	}
				
			}
		
		int currentIndex = 0;
		ArrayList<Pair<String, Integer>> tempArray = new ArrayList<Pair<String, Integer>>();
		for(int i = 0; i < output.size(); ++i){
			System.out.print(input.substring(currentIndex, currentIndex + output.get(i)) + " | ");
			String str = input.substring(currentIndex, currentIndex + output.get(i));
			int value;
			if(wordCache.get(str) != null){
				value = wordCache.get(str);
			} else {
				value = 0;
			}
			
			tempArray.add(new Pair(str,value));
			currentIndex += (output.get(i));
			
		}
		double accuracy;
		accuracy =  (double)((double)(output.size()-incorrectCounter)/(double)(output.size()));
		System.out.println();
		System.out.println("Accuracy: " + accuracy + " ~ " + (output.size()-incorrectCounter) + " out of " + output.size() + " words (including english words and symbols)");
		
		String json = new Gson().toJson(tempArray);
		//System.out.println(json);
		return json;
	}
	

	
	
//	private void getSentence(int index, int _i, int _j, String OUTPUT){
//		int currentIndex = index;
//		String output = OUTPUT;
//		for(int i = _i; i < result.size(); ++i){
//			for(int j = _j; j < result.get(i).size(); ++j){
//				Word tempWord = result.get(i).get(j);
//				
//				if(tempWord.getStartIndex() > currentIndex){
//					//result.get(i).get(j).setMarked(true);
//					if(j+1 <  result.get(i).size()){
//						getSentence(currentIndex, i, j+1, output);
//					}
//					currentIndex = tempWord.getEndIndex()-1;
//					output+=tempWord.getWord()+"|";
//				}	
//			}
//			_j = 0;
//		}
//
//
//		//*****//
//		//if(output.replace("|", "").equals(inputString)){
//			System.out.println(output);
//		//}
//		//System.out.println(output);
//	}
//	
	
//	
//	private void getCombination(ArrayList<ArrayList<String>> sets ,int n, String prefix) {
//		
//		String temp1 = prefix.substring(0,prefix.length()).replace(" ", "");
//		if(!inputString.contains(temp1)){
//			return;
//		}
//		
//		
//		if(n >= sets.size()){
//			String temp = prefix.substring(0,prefix.length()-1).replace(" ", "");
//			if(temp.equals(inputString)){
//				System.out.println(prefix.substring(0,prefix.length()-1));
//			}
//			// System.out.println(prefix.substring(0,prefix.length()-1).replace(" ", ""));
//           // System.out.println(prefix.substring(0,prefix.length()-1));
//            return;
//        }
//        for(String s : sets.get(n)){
//            getCombination(sets, n+1, prefix+s+"|");
//        }
//	}
}
