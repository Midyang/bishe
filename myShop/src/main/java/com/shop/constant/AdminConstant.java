package com.shop.constant;

import java.awt.Color;
import java.util.Random;

/**
 * 
 * Admin 中经常使用的常量字符,数组等.
 * 
 * */

public class AdminConstant {
	/**
	 * 
	 * 用于验证码 中 随机 字体颜色
	 * 
	 * */
	public static final Color[] FONT_COLOR={Color.BLACK , Color.BLUE , Color.CYAN , Color.DARK_GRAY , Color.GRAY , Color.GREEN ,
			Color.PINK , Color.ORANGE , Color.LIGHT_GRAY , Color.RED , Color.YELLOW , Color.MAGENTA };
	/**
	 * 
	 * 随机数对象
	 * 
	 * */
	public static final Random RANDOM=new Random();
	/**
	 * 
	 * 初始x坐标
	 * 
	 * */
	public static final int X_LOCATION=18;
	/**
	 * 
	 * 初始y坐标
	 * 
	 * */
	public static final int Y_LOCATION=20;
	/**
	 * 
	 * 字体 大小 数组
	 * 
	 * */
	public static final int[] FONT_SIZE= {16,18,20};//字体大小
	/**
	 * 
	 * 字体类型 数组
	 * 
	 * */
	public static final String[] FONT_TYPE= {"宋体","TimesRoman","Courier","Arial"};//字体
	/**
	 * 
	 * 验证码图片长度
	 * 
	 * */
	public static final int WIDTH=120;
	/**
	 * 
	 * 验证码图片宽度
	 * 
	 * */
	public static final int HEIGHT=28;
	/**
	 * 
	 * 页面显示的信息条数
	 * 
	 * */
	public static final int PAGE_SIZE=8;
	
}