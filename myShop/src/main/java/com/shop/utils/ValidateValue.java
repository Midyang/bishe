package com.shop.utils;

import java.util.regex.Pattern;

/**
 *@ 字符串非空验证
 * 是否为数字验证
 * */
public class ValidateValue {
	
	private static final  Pattern PATTERN = Pattern.compile("[0-9]+");
	private static final  Pattern ID = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_$]{3,16}$");
	private static final  Pattern PWD = Pattern.compile("^[a-zA-Z0-9_$]{4,16}$");
	private static final  Pattern VALIDATECODE = Pattern.compile("^[a-zA-Z0-9_$]{4}$");
	private static final  Pattern INTEGER= Pattern.compile("^[1-9][0-9]*|0$");
	private static final  Pattern PHONE= Pattern.compile("^1(3|4|5|7|8)\\d{9}$");
	private static final  Pattern EMAIL= Pattern.compile("^\\w+((-\\w+)|(\\.\\w+))*\\@[0-9A-Za-z]+((\\.|-)[0-9A-Za-z]+)*\\.[A-Za-z0-9]+$");
	private static final  Pattern BYTE= Pattern.compile("^\\[(-|[0-9]*|,)*\\]$");
	
	/**
	 * null,"","   ",都返回 ture 
	 * 
	 * */
	public static boolean isEmpty(String value) {
		if(value == null || value.trim().length() == 0) {
			return true;
		}
		return false;
	}
	/**
	 * 判断是数字吗(包含小数,先进行了非空验证)
	 * 是返回true
	 * 否则返回false
	 * 负数返回为false(暂不支持负数)
	 * */
	public static boolean isNumber(String value) {
		
		if(ValidateValue.isEmpty(value)) {//为空返回为假
			return false;
		}else {
			value=value.trim();
		}
		
		int index = value.indexOf(".");
		if(index < 0) {
			return isInteger(value);
		}else {
			String substring1 = value.substring(0, index);
			String substring2 = value.substring(index+1);
			return isInteger(substring1) && PATTERN.matcher(substring2).matches();
		}
	}
	/**
	 *	账号验证 通过验证 返回true
	 * 
	 * */
	public static boolean valiUserName(String userName) {
		if(isEmpty(userName)) {
			return false;
		}
		return ID.matcher(userName).matches();
	}
	/**
	 *	pwd验证  通过验证 返回true
	 * */
	public static boolean valiPWD(String passWord) {
		if(isEmpty(passWord)) {
			return false;
		}
		return PWD.matcher(passWord).matches();
	}
	/**
	 *		验证码验证 通过验证 返回true
	 * */
	public static boolean valiCode(String valiCode) {
		if(isEmpty(valiCode)) {
			return false;
		}
		return VALIDATECODE.matcher(valiCode).matches();
	}
	/**
	 *	Integer验证 
	 *	null,"","   " return false
	 *	
	 * */
	public static boolean isInteger(String integer) {
		if(isEmpty(integer)) {
			return false;//如果为null "" "    "则返回False
		}
		return INTEGER.matcher(integer).matches();
	}
	/**
	 *	手机号格式验证 
	 *	不满足手机号格式  return false
	 *	
	 * */
	public static boolean valiPhone(String integer) {
		if(isEmpty(integer)) {//返回false
			return false;
		}
		return PHONE.matcher(integer).matches();
	}
	/**
	 *	邮箱格式验证 
	 *	不满足邮箱格式  return false
	 *	
	 * */
	public static boolean valiEmail(String integer) {
		if(isEmpty(integer)) {//返回false
			return false;
		}
		return EMAIL.matcher(integer).matches();
	}
	public static boolean valiByte(String str) {
		if(isEmpty(str)) {//返回false
			return false;
		}
		return BYTE.matcher(str).matches();
	}
}
