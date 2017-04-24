package com.eric.net.util.net;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created on 2017/2/28.
 * Desc：可直接处理String返回值，添加置GsonConverter类前
 * Author：Eric.w
 */
public class StringConverter implements Converter<ResponseBody, String> {

    public static final StringConverter INSTANCE = new StringConverter();

    @Override
    public String convert(ResponseBody value) throws IOException {
        return value.string();
    }
}
