package com.muxin.asus.arg.common.inter;

import com.muxin.asus.arg.bean.RequestData;
import com.muxin.asus.arg.bean.ResponseData;
import com.muxin.asus.arg.bean.Station;
import com.muxin.asus.arg.bean.Constants;

import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import rx.Observable;

/**
 * Author:   Lianwei Bu
 * Date:     2016/6/20
 * Description:
 */
public interface IGetSensorFromNet {
    @Headers({"Content-Type:application/json; charset=utf-8",
            "Accept: application/json, text/javascript, */*"})
    @POST(Constants.URL_GET_SENSOR_CURRENT_DATA)
    Observable<ResponseData> getData(@Header("X-Auth-Token") String token, @Body Station station);

    @Headers({"Content-Type:application/json; charset=utf-8",
            "Accept: application/json, text/javascript, */*"})
    @POST(Constants.URL_GET_STATION)
    Observable<ResponseData> getStaionId(@Header("X-Auth-Token") String token, @Body RequestData requestData);

    @Headers({"Content-Type:application/json; charset=utf-8",
            "Accept: application/json, text/javascript, */*"})
    @POST(Constants.URL_GET_SENSOR)
    Observable<ResponseData> getSensor(@Header("X-Auth-Token") String token, @Body RequestData requestData);


}
