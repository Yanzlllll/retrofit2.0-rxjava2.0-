package com.eric.net.util.net;

import android.content.Context;
import android.widget.Toast;

import com.eric.net.util.L;

import java.lang.ref.WeakReference;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rx.Subscriber;

/**
 * Created on 2017/3/1.
 * Desc：处理异常的Subscriber
 * Author：Eric.w
 */

public abstract class ErrorHandleSubscriber<T> extends Subscriber<T> {

    protected WeakReference<Context> mContext;

    public ErrorHandleSubscriber(Context context) {
        this.mContext = new WeakReference<>(context);
    }

    @Override
    public void onError(Throwable e) {
        Context context = mContext.get();
        if (context == null) return;
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(context, "请求超时！", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态！", Toast.LENGTH_SHORT).show();
        } else if (e instanceof UnknownHostException) {
            Toast.makeText(context, "网络错误，请检查您的网络状态！", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            L.e("rxjava", "error----------->" + e.toString());

        }
    }
}
