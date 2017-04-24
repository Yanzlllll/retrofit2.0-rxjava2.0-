package com.eric.net.util.net;

import com.eric.net.bean.XiaoyanResponse;

import java.io.IOException;

import retrofit2.Response;
import rx.Observable;
import rx.exceptions.Exceptions;
import rx.functions.Func1;

/**
 * Created on 2017/2/28.
 * Desc：标准格式处理请求
 * Author：Eric.w
 */
public class ErrorStandardCheckerTransformer<T> implements Observable.Transformer<Response<XiaoyanResponse<T>>, T> {

    @Override
    public Observable<T> call(Observable<Response<XiaoyanResponse<T>>> responseObservable) {
        return responseObservable.map(new Func1<Response<XiaoyanResponse<T>>, T>() {
            @Override
            public T call(Response<XiaoyanResponse<T>> response) {
                String errorMsg = null;
                //响应头不为200，或者响应体为null，则抛出异常
                if (response.code() != 200) {
                    try {
                        errorMsg = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (response.body() == null) {
                        errorMsg = "请求数据为空！";
                        //服务器 返回数据不为0，则数据请求出错
                    } else {
                        if (response.body().getCode() != 0) {
                            errorMsg = response.body().getMessage();
                            //返回的数据体为null,统一处理
                        } else if (response.body().getData() == null) {
                            errorMsg = "Responce is null";
                        }
                    }
                }
                if (errorMsg != null) {
                    try {
                        throw new ErrorResponseException(errorMsg);
                    } catch (ErrorResponseException e) {
                        throw Exceptions.propagate(e);
                    }
                }
                return response.body().getData();
            }
        });
    }
}
