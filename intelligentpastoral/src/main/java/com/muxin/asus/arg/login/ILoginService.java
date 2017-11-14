package com.muxin.asus.arg.login;


import com.muxin.asus.arg.bean.ResponseData;
import com.muxin.asus.arg.bean.LoginRequest;
import com.muxin.asus.arg.bean.Constants;

import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import rx.Observable;

/**
 *	@Author： 		Lianwei Bu
 *	@Project_Name：	IntelligentPastoral
 *	@Date：	2016年6月14日
 *	@Description: 
 * 
 */
public interface ILoginService {
	
	/**用户登录接口
	 * @param token user-token
	 * @param body  请求实体
	 * @return
	 */
	@Headers({ "Content-Type:application/json; charset=utf-8",
			"Accept: application/json, text/javascript, */*" })
	@POST(Constants.URL_USER_LOGIN)
	Observable<ResponseData> login(@Header("X-Auth-Token") String token,
							 @Body LoginRequest body);
}
