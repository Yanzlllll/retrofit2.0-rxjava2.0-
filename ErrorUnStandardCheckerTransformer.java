package com.xiaoyan.chat.util.net;

import java.io.IOException;

import retrofit2.Response;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created on 2017/2/28.
 * Desc：非标准格式请求
 * Author：Eric.w
 */
public class ErrorUnStandardCheckerTransformer<T> implements Observable.Transformer<Response<T>, T> {

    @Override
    public Observable<T> call(Observable<Response<T>> responseObservable) {
        return responseObservable.map(new Func1<Response<T>, T>() {
            @Override
            public T call(Response<T> xiaoyanResponseResponse) {
                String errorMsg = null;
                //响应头不为200，或者响应体为null，则抛出异常
                if (xiaoyanResponseResponse.code() != 200) {
                    try {
                        errorMsg = xiaoyanResponseResponse.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //数据为空，提前处理
                    if (xiaoyanResponseResponse.body() == null)
                        errorMsg = "请求数据为空！";
                }

                if (errorMsg != null) {
                    try {
                        throw new ErrorResponseException(errorMsg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return xiaoyanResponseResponse.body();
            }
        });
    }

}
