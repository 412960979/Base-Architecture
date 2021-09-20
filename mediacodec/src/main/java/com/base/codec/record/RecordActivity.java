package com.base.codec.record;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.base.codec.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;

public class RecordActivity extends AppCompatActivity implements SurfaceHolder.Callback, PreviewCallback {
    private FloatingActionButton fab;

    private SurfaceView surfaceview;
    private SurfaceHolder surfaceHolder;
    private Camera camera;
    private Parameters parameters;

    private int width = 1280;
    private int height = 720;
    private int framerate = 30;
    private int biterate = 8500 * 1000;
    private static final int yuvqueuesize = 10;

    //待解码视频缓冲队列，静态成员！
    public static ArrayBlockingQueue<byte[]> YUVQueue = new ArrayBlockingQueue<byte[]>(yuvqueuesize);
    private AvcEncoder avcCodec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        surfaceview = (SurfaceView) findViewById(R.id.surfaceview);
        surfaceHolder = surfaceview.getHolder();
        surfaceHolder.addCallback(this);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> RecordActivity.this.finish());
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        //将当前帧图像保存在队列中
        putYUVData(data, data.length);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        camera = getBackCamera();
        startCamera(camera);
        //创建AvEncoder对象
        avcCodec = new AvcEncoder(width, height, framerate, biterate);
        //启动编码线程
        avcCodec.StartEncoderThread();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        if (null != camera) {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
            avcCodec.StopThread();
        }
    }

    public void putYUVData(byte[] buffer, int length) {
        if (YUVQueue.size() >= 10) {
            YUVQueue.poll();
        }
        YUVQueue.add(buffer);
    }


    private void startCamera(Camera mCamera) {
        if (mCamera != null) {
            try {
                mCamera.setPreviewCallback(this);
                mCamera.setDisplayOrientation(90);
                if (parameters == null) {
                    parameters = mCamera.getParameters();
                }
                //获取默认的camera配置
                parameters = mCamera.getParameters();
                //设置预览格式
                parameters.setPreviewFormat(ImageFormat.NV21);
                //设置预览图像分辨率
                parameters.setPreviewSize(width, height);
                //配置camera参数
                mCamera.setParameters(parameters);
                //将完全初始化的SurfaceHolder传入到setPreviewDisplay(SurfaceHolder)中
                //没有surface的话，相机不会开启preview预览
                mCamera.setPreviewDisplay(surfaceHolder);
                //调用startPreview()用以更新preview的surface，必须要在拍照之前start Preview
                mCamera.startPreview();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Camera getBackCamera() {
        Camera c = null;
        try {
            //获取Camera的实例
            c = Camera.open(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //获取Camera的实例失败时返回null
        return c;
    }
}