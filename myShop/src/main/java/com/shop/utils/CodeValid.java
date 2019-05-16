package com.shop.utils;
/**
 * 
 * 验证码-value
 * 
 * */
import java.util.Random;

public class CodeValid {
	private static char[] ch="1234567890qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM".toCharArray();
	private static Random random=new Random();
	
	public static String getCode(int length){
		StringBuilder builder=new StringBuilder();
		for(int i=0;i<length;i++){
			int nextInt=random.nextInt(ch.length);
			builder.append(ch[nextInt]);
		}
		return builder.toString();
	}
	
}
