package com.base.codec.record;

import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;
import java.nio.ByteBuffer;

import android.media.MediaCodec.BufferInfo;
import android.view.Surface;

/**
 * Created by zhangxd on 2018/7/6.
 */

public class AvcDecoder {

    private static final String TAG = AvcDecoder.class.getSimpleName();

    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test1.h264";

    private static AvcDecoder instance;

    private MediaCodec mediaCodec;

    private MediaFormat mediaFormat;

    private long frameIndex;

    private volatile boolean isDecodeFinish = false;

    private MediaExtractor mediaExtractor;

    private SpeedManager mSpeedController = new SpeedManager();

    private DecoderMP4Thread mDecodeMp4Thread;

    private DecoderH264Thread mDecodeH264Thread;

    private Surface surface1;

    private AvcDecoder(Surface surface) {
        surface1 = surface;
    }

    public static AvcDecoder getInstance(Surface surface) {
        if (instance == null) {
            instance = new AvcDecoder(surface);
        }
        return instance;
    }

    /**
     * * Synchronized callback decoding
     */
    private void initMediaCodecSys(String path) {
        try {
            mediaCodec = MediaCodec.createDecoderByType("video/avc");
            mediaFormat = MediaFormat.createVideoFormat("video/avc", 1280, 720);
            mediaExtractor = new MediaExtractor();
            //MP4 文件存放位置
            mediaExtractor.setDataSource(path);
            Log.d(TAG, "getTrackCount: " + mediaExtractor.getTrackCount());
            for (int i = 0; i < mediaExtractor.getTrackCount(); i++) {
                MediaFormat format = mediaExtractor.getTrackFormat(i);
                String mime = format.getString(MediaFormat.KEY_MIME);
                Log.d(TAG, "mime: " + mime);
                if (mime.startsWith("video")) {
                    mediaFormat = format;
                    mediaExtractor.selectTrack(i);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Surface surface = surface1;
        mediaCodec.configure(mediaFormat, surface, null, 0);
        mediaCodec.start();
    }

    /**
     * Play the MP4 file Thread
     */
    private class DecoderMP4Thread extends Thread {
        long pts = 0;

        @Override
        public void run() {
            super.run();
            while (!isDecodeFinish) {
                int inputIndex = mediaCodec.dequeueInputBuffer(-1);
                Log.d(TAG, " inputIndex: " + inputIndex);
                if (inputIndex >= 0) {
                    ByteBuffer byteBuffer = mediaCodec.getInputBuffer(inputIndex);
                    //读取一片或者一帧数据
                    int sampSize = mediaExtractor.readSampleData(byteBuffer, 0);
                    //读取时间戳
                    long time = mediaExtractor.getSampleTime();
                    if (sampSize > 0 && time > 0) {
                        mediaCodec.queueInputBuffer(inputIndex, 0, sampSize, time, 0);
                        //读取一帧后必须调用，提取下一帧
                        //控制帧率在30帧左右
                        mSpeedController.preRender(time);
                        mediaExtractor.advance();
                    }
                }
                BufferInfo bufferInfo = new BufferInfo();
                int outIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, 0);
                if (outIndex >= 0) {
                    mediaCodec.releaseOutputBuffer(outIndex, true);
                }
            }
        }

    }

    /**
     * 解析播放H264码流
     */
    private class DecoderH264Thread extends Thread {
        long pts = 0;

        @Override
        public void run() {
            super.run();
            long startTime = System.nanoTime();
            while (!isDecodeFinish) {
                if (mediaCodec != null) {
                    int inputIndex = mediaCodec.dequeueInputBuffer(-1);
                    if (inputIndex >= 0) {
                        ByteBuffer byteBuffer = mediaCodec.getInputBuffer(inputIndex);
                        int sampSize = DecodeH264File.getInstance(path).readSampleData(byteBuffer);
                        long time = (System.nanoTime() - startTime) / 1000;
                        if (sampSize > 0 && time > 0) {
                            mediaCodec.queueInputBuffer(inputIndex, 0, sampSize, time, 0);
                            mSpeedController.preRender(time);
                        }

                    }
                }
                BufferInfo bufferInfo = new BufferInfo();
                int outIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, 0);
                if (outIndex >= 0) {
                    mediaCodec.releaseOutputBuffer(outIndex, true);
                }
            }
        }

    }


    public void close(String path) {
        try {
            Log.d(TAG, "close start");
            if (mediaCodec != null) {
                isDecodeFinish = true;
                try {
                    if (mDecodeMp4Thread != null) {
                        mDecodeMp4Thread.join(2000);
                    }
                    if (mDecodeH264Thread != null) {
                        mDecodeH264Thread.join(2000);
                    }
                } catch (InterruptedException e) {
                    Log.e(TAG, "InterruptedException " + e);
                }
                boolean isAlive = mDecodeMp4Thread.isAlive();
                Log.d(TAG, "close end isAlive :" + isAlive);
                mediaCodec.stop();
                mediaCodec.release();
                mediaCodec = null;
                mSpeedController.reset();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        DecodeH264File.getInstance(path).close();
        instance = null;
    }


    public void startMP4Decode(String path) {
        initMediaCodecSys(path);
        mDecodeMp4Thread = new DecoderMP4Thread();
        mDecodeMp4Thread.setName("DecoderMP4Thread");
        mDecodeMp4Thread.start();

    }

    public void startH264Decode(String path) {
        initMediaCodecSys(path);
        mDecodeH264Thread = new DecoderH264Thread();
        mDecodeH264Thread.setName("DecoderH264Thread");
        mDecodeH264Thread.start();
    }

}