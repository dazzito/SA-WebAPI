package com.mkyong.rest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class MainTester {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		Segmenter segmenter = new Segmenter();
		//อาหาร@ร้านABC123นี้ไม่อร่อยรสชาติไม่!?ได้เรื่องq w e r t y. 
		String jsonString = segmenter.cut("ปีนี้ อาหาร@ร้านABACไม่อร่อยรสชาติไม่!ได้เรื่อง.");
		System.out.println(jsonString);
	}

}
