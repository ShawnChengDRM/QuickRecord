package com.djxg.silence.quickrecord.decode;

import android.os.Handler;
import android.os.Looper;

import com.djxg.silence.quickrecord.ScannerActivity;

import java.util.concurrent.CountDownLatch;

/**
 * This thread does all the heavy lifting of decoding the images.
 */

public class DecodeThread extends Thread {

    private final ScannerActivity mActivity;
    private final CountDownLatch mHandlerInitLatch;
    private Handler mHandler;

    DecodeThread(ScannerActivity activity) {
        this.mActivity = activity;
        mHandlerInitLatch = new CountDownLatch(1);
    }

    Handler getHandler() {
        try {
            mHandlerInitLatch.await();
        } catch (InterruptedException ie) {
            // continue?
        }
        return mHandler;
    }

    @Override
    public void run() {
        Looper.prepare();
        mHandler = new DecodeHandler(mActivity);
        mHandlerInitLatch.countDown();
        Looper.loop();
    }
}
