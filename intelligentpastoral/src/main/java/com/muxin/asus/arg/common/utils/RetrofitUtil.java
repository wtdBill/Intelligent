package com.muxin.asus.arg.common.utils;

import com.google.gson.Gson;
import com.muxin.asus.arg.bean.Constants;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.functions.Func1;


public class RetrofitUtil {

    /**
     * 服务器地址
     */
    private static final String API_HOST = Constants.BASE_URL;

    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient = new OkHttpClient();

    public static <T> T getService(Class<T> cls) {
        return getRetrofit().create(cls);
    }

    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(new Gson());
            okHttpClient.setConnectTimeout(5, TimeUnit.SECONDS);
            okHttpClient.setReadTimeout(5, TimeUnit.SECONDS);
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_HOST)
                    .client(okHttpClient)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * 对网络接口返回的Response进行分割操作
     */

//    public static Observable<String> flatResponse(final ResponseData responseData) {
//        return Observable.create(new Observable.OnSubscribe<String>() {
//
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                if (responseData.getResult() == Constants.OK) {
//                    if (!subscriber.isUnsubscribed()) {
//                        subscriber.onNext(responseData.getMessage());
//                    }
//                } else {
//                    if (!subscriber.isUnsubscribed()) {
//                        subscriber.onError(new APIException(responseData.getResult()));
//                    }
//                    return;
//                }
//
//                if (!subscriber.isUnsubscribed()) {
//                    subscriber.onCompleted();
//                }
//
//            }
//        });
//    }
//
//
//    public static   <T> Observable<String> flatResponse(final ResponseData responseData) {
//        return Observable.create(new Observable.OnSubscribe<String>() {
//
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                if (responseData.getResult()==Constants.OK) {
//                    if (!subscriber.isUnsubscribed()) {
//                        subscriber.onNext(responseData.getMessage());
//                    }
//                } else {
//                    if (!subscriber.isUnsubscribed()) {
//                        subscriber.onError(new APIException(responseData.getResult()));
//                    }
//                    return;
//                }
//
//                if (!subscriber.isUnsubscribed()) {
//                    subscriber.onCompleted();
//                }
//
//            }
//        });
//    }
//


//    public    <T> Observable.Transformer<Response<T>, T> applySchedulers() {
//        return (Observable.Transformer<Response<T>, T>) transformer;
//    }
//
//    final   Observable.Transformer transformer = new Observable.Transformer() {
//        @Override
//        public Object call(Object observable) {
//            return ((Observable) observable).subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .flatMap(new Func1() {
//                        @Override
//                        public Object call(Object response) {
//                            return flatResponse((Response<Object>)response);
//                        }
//                    })
//                    ;
//        }
//    };

    class HttpResult<T> {

        /**
         * message : [{"channel1":35.5,"channel10":0,"channel11":0,"channel12":0,"channel13":0,"channel14":0,"channel15":0,"channel16":0,"channel2":48,"channel3":17.8,"channel4":1820,"channel5":0,"channel6":0,"channel7":0,"channel8":0,"channel9":0,"curpage":0,"dataid":50970,"fulldata":"01030040016301e000b200b67fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff7fff0000000000000000000000000000000000000000000000000000000000000000d562","gatewayid":"1001","pagesize":0,"pagestart":0,"period":0,"sortord":"","term":"","time":"2016-07-31 18:39:28"}]
         * result : 0
         * total : 20934
         */

        private T message;
        private int result;
        private int total;

        public T getMessage() {
            return message;
        }

        public void setMessage(T message) {
            this.message = message;
        }

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }
}

class APIException extends Exception {
    public int code;

    public APIException(int code) {
        this.code = code;
    }


}

class HttpResultFun<T> implements Func1<RetrofitUtil.HttpResult<T>, T> {

    @Override
    public T call(RetrofitUtil.HttpResult<T> httpResult) {
        if (httpResult.getResult() != 0) {
            new APIException(httpResult.getResult());
        }
        return httpResult.getMessage();
    }
}



