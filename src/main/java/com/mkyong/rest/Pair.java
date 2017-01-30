package com.mkyong.rest;
public class Pair<T1,T2> {
	private T1 word;
	private T2 value;
	
	public Pair(T1 key, T2 value){
		this.word = key;
		this.value = value;
	}
	
	public T1 getKey(){
		return word;
	}
	
	public T2 getValue(){
		return value;
	}
	
	
}
