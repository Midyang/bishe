package com.shop.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyDateTime {
	private DateTimeFormatter dateTimeFor = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private DateTimeFormatter dateFor = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private DateTimeFormatter timeFor = DateTimeFormatter.ofPattern("HH:mm:ss");
	private LocalDateTime localDateTime;

	public MyDateTime() {
		localDateTime=LocalDateTime.now();
	}
	/*
	 * ��ȡ LocalDateTime ����
	 * */
	public LocalDateTime getLocalDateTime() {

		return localDateTime;
	}

	public String getDateTime(String yyyy_MM_dd_HH_mm_ss){

		return localDateTime.format(DateTimeFormatter.ofPattern(yyyy_MM_dd_HH_mm_ss));
	}
	public String getDateTime() {

		return localDateTime.format(dateTimeFor);
	}

	public String getDate(String yyyy_MM_dd){

		return localDateTime.toLocalDate().format(DateTimeFormatter.ofPattern(yyyy_MM_dd));
	}
	public String getDate() {

		return localDateTime.toLocalDate().format(dateFor);
	}

	public String getTime(String HH_mm_ss){

		return localDateTime.toLocalTime().format(DateTimeFormatter.ofPattern(HH_mm_ss));
	}

	public String getTime() {

		return localDateTime.toLocalTime().format(timeFor);
	}
	public void now(){
		localDateTime=LocalDateTime.now();
	}
}
