package com.muxin.asus.arg.stationlist;


import com.muxin.asus.arg.bean.RequestData;
import com.muxin.asus.arg.bean.ResponseData;
import com.muxin.asus.arg.bean.Constants;

import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import rx.Observable;

public interface IStationService {
	@Headers({ "Content-Type:application/json; charset=utf-8",
			"Accept: application/json, text/javascript, */*" })
	@POST(Constants.URL_GET_STATION)
	Observable<ResponseData> getStationList(@Header("X-Auth-Token") String token,
									  @Body RequestData body);
}
