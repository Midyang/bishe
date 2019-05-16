package com.shop.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

public class JsonUtil {

	public static String strToJsonStr(String s) {
		if( ValidateValue.isEmpty(s) ) {
			return null;
		}
		/**
		 * 1:使用下面的方法对即将向前台输出的JSON字符串进行处理，可以解决键盘上能输入的所有特殊字符问题 
		 * 2:转义字符处理
		 **/
		StringBuffer buffer = new StringBuffer();
		for( int length = 0 ; length < s.length() ; length++ ) {
			char c = s.charAt(length);
			switch (c) {
			case '\"':
				buffer.append("\\\"");
				break;
			case '\'':
				buffer.append("\\\'");
				break;
			case '/':
				buffer.append("\\/");
				break;
			case '\b':
				buffer.append("\\b");
				break;
			case '\f':
				buffer.append("\\f");
				break;
			case '\n':
				buffer.append("\\n");
				break;
			case '\r':
				buffer.append("\\r");
				break;
			case '\t':
				buffer.append("\\t");
				break;
			default:
				buffer.append(c);
				break;
			}
		}
		return buffer.toString();
	}
	/**
	 * 把数据文本接入json数据字符串 之前使用，预先对文本做处理后再接入JSON
	 **/
	public static String strToJsonAgo(String str) {
		str = str == null ? "" : str;
		StringBuffer buffer = new StringBuffer(str);
		int i = 0;
		while(i<buffer.length()) {
			/**
			 * 如果说是一个'，\，/ 就在当前 符号前面加一个\,转义字符，避免在JSON在解析的时候解析出错
			 * []{}四个字符直接不要！用空串去掉！
			 **/
			/*if( buffer.charAt(i) == '\'' || buffer.charAt(i) == '\\' || buffer.charAt(i) == '\"') {
				buffer.insert(i, '\\');
				i +=2;
			}else*/ if( buffer.charAt(i) == '[' || buffer.charAt(i) == ']' || buffer.charAt(i) == '{' || buffer.charAt(i) == '}' || buffer.charAt(i) == '\'' || buffer.charAt(i) == '\\' || buffer.charAt(i) == '\"' ) {
				buffer.replace(i, i+1, "");
			}else {
				i +=1;
			}
		}
		return buffer.toString();
	}
	/*
	 * 将对象转为 字节数组，然后将字节数组 JSON 成 String
	 * UTF-8
	 * 如果可以还可以对数据进行加密处理（再写一个加密方法直接调用就好）
	 * */
	public static String objToByteStr(Object o){
		if(o==null){
			return null;
		}
		String jsonString1=null;
		try{
			jsonString1= JSON.toJSONString(o);
		}catch(Exception e){
			System.err.println("    LOG-parse:第一步，对象 转 JSON字符 失败");
			return null;
		}
		if(jsonString1 == null){
			return null;
		}
		byte[] bytes = null;
		try {
			bytes = jsonString1.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.err.println("    LOG-parse:第二步，JSON字符 转 字节数组 失败");
			return null;//当前状态为失败！
		}
		if(bytes!=null && bytes.length>0){
			try{
				ArrayList<Byte> arrayList = new ArrayList<Byte>(bytes.length);
				for (Byte byt : bytes) {
					arrayList.add(byt);
				}
				return JSON.toJSONString(arrayList);
			}catch(Exception e){
			}
		}else{
			System.err.println("    LOG-parse:得到的字节数组为空！");
		}
		System.err.println("    LOG-parse:对象转JSON失败");
		return null;//失败
	}
	/*
	 * 将字节字符串 还原成对象(Map集合专用)
	 * byteString	被转的对象
	 * clazz		转成什么对象
	 * */
	public static <T> T byteStrToObj(String byteString,Class<T> clazz){
		if(clazz == null || byteString == null){
			System.out.println("   LOG-parse: 将字节字符串 还原成对象时，传入参数有误！");
			return null;
		}
		List<Byte> parseArray = null;
		try{
			parseArray = JSON.parseArray(byteString, Byte.class);
		}catch(Exception e){
			parseArray = null;
			System.out.println("   LOG-parse: 传入参数转字节集合失败！请检查参数！");
		}
		if(parseArray==null || parseArray.size()<1){
			return null;
		}
		byte[] byt =  new byte[parseArray.size()];
		try{
			for (int i = 0; i < parseArray.size(); i++) {
				byt[i] = parseArray.get(i);
			}
		}catch(Exception e){
			System.out.println("   LOG-parse：字节集合转换到字节数组异常！");
			return null;
		}
		
		try {
			String string = new String(byt,"UTF-8");
			T parseObject = JSON.parseObject(string, clazz);
			return parseObject;
		} catch (Exception e) {
			System.out.println("   LOG-parse：JSON字符串到集合异常！");
			return null;
		}
		
	}
	/*
	 * 将字节字符串 还原成对象(list集合专用)
	 * byteString	被转的对象
	 * clazz		转成什么对象
	 * */
	public static <T> List<T> byteStrToObj2(String byteString,Class<T> clazz){
		if(clazz == null || byteString == null){//!ValidateValue.valiByte(byteString) || 该方法的使用会导致内存泄漏
			return null;
		}
		List<Byte> parseArray = null;
		try{
			parseArray = JSON.parseArray(byteString, Byte.class);
		}catch(Exception e){
			return null;
		}
		if(parseArray==null || parseArray.size()<1){
			return null;
		}
		byte[] byt =  new byte[parseArray.size()];
		try{
			for (int i = 0; i < parseArray.size(); i++) {
				byt[i] = parseArray.get(i);
			}
		}catch(Exception e){
			return null;
		}
		
		try {
			String string = new String(byt,"UTF-8");
			List<T> parseObject = JSON.parseArray(string, clazz);
			return parseObject;
		} catch (Exception e) {
			return null;
		}
	}
	
}
