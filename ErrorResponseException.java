package com.eric.net.util.net;

import java.io.IOException;

/**
 * Created on 2017/2/28.
 * Desc：自定义的网络请求异常
 * Author：Eric.w
 */
public class ErrorResponseException extends IOException {
    public ErrorResponseException() {
        super();
    }

    public ErrorResponseException(String detailMessage) {
        super(detailMessage);
    }
}
