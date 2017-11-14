/**
 * 
 */
package com.muxin.asus.arg.bean;

/**
 * @Author： Lianwei Bu
 * @Project_Name： IntelligentPastoral
 * @Date： 2016年5月18日
 * @Description:
 * 
 */
public class Constants {
	public static final int REQUEST_CODE_SENSOR = 0x10;
	public static final int RESULT_CODE_SENSOR = 0x11;
	public static final int REQUEST_CODE_MONITOR = 0x20;
	public static final int RESULT_CODE_MONITOR = 0x21;

	public static final int OK=0;
	public static final int PORT = 8080;

	public static String USER_TOKEN = "eyJhbGciOiJBMTI4S1ciLCJlbmMiOiJBMTI4Q0JDLUhTMjU2In0.necbQMaPmrwnoBob6i9xZEnvXGd-6xfpDkZdg3BoVk9dfQ1BJSy9dA.TGcX1R-NtPC6fojNsIZUyA.5B19LIDjFZtGahgS05ys2wH_H8qDc1KLdkNgENWGHu6puNN-vysEDfHYreMte4lrfo5Uki2RZqy-MYF3XsYQaw.zSKbtJhRSh4a-vS9O8fM8w";
	 public static final String BASE_URL = "127.0.0.1:" + PORT
	 + "/IOT/service/";
	public static final String URL_USER_LOGIN = BASE_URL + "admin/login";
	public static final String URL_GET_SENSOR = BASE_URL + "transducer/list";
	public static final String URL_GET_STATION = BASE_URL + "gateway/list";
	public static final String URL_GET_SENSOR_CURRENT_DATA = BASE_URL
			+ "data/current/list";
	public static final String URL_GET_MONITOR = "monitor/list";
	
	
	public static final String LIGHT = "光照强度";
	public static final String AIR_TEMP = "空气温度";
	public static final String AIR_HUMI = "空气湿度";
	public static final String SOIL_TEMP = "土壤湿度";



}
