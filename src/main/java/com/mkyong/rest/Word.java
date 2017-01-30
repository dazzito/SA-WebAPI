package com.mkyong.rest;
public class Word {

	private String word;
	private int startIndex;
	private int endIndex;
	//private boolean marked = false;
	
	public Word(String word, int start, int end){
		this.word = word;
		this.startIndex = start;
		this.endIndex = end;
	}
	
//	public void setMarked(boolean a){
//		this.marked = a;
//	}
	
//	public boolean isMarked(){
//		return this.marked;
//	}
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public int getEndIndex() {
		return endIndex;
	}
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
	
	@Override
	public String toString(){
		return word;

	}
	
}
