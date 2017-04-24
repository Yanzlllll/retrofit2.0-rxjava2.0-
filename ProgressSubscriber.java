package com.eric.net.util.net;

import android.content.Context;
import android.os.Message;

/**
 * Created on 2017/2/28.
 * Desc：封装进度条的订阅者
 * Author：Eric.w
 */
public abstract class ProgressSubscriber<T> extends ErrorHandleSubscriber<T> implements ProgressCancelListener {

    private ProgressDialogHandler mProgressDialogHandler;
    private String message;

    public ProgressSubscriber(Context context, String message) {
        super(context);
        this.message = message;
        mProgressDialogHandler = new ProgressDialogHandler(mContext.get(), this, true);
    }

    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            Message msg = mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG);
            msg.obj = message;
            msg.sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    @Override
    public void onStart() {
        showProgressDialog();
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        dismissProgressDialog();
    }

    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}
