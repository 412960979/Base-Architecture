package com.base.android;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.base.loadingdialog.config.WNDialogConfig;
import com.base.loadingdialog.config.WNToastConfig;
import com.base.loadingdialog.dialog.WNProgressBarDialog;
import com.base.loadingdialog.dialog.WNProgressDialog;
import com.base.loadingdialog.dialog.WNStatusDialog;
import com.base.loadingdialog.dialog.WNToast;
import com.base.loadingdialog.listeners.OnDialogDismissListener;

public class GlobalDialogActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String text01 = "从前有坐山,山上有坐庙,庙里有个老和尚在讲故事,讲的什么啊,从前有座山,山里有座庙,庙里有个盆,盆里有个锅,锅里有个碗,碗里有个匙,匙里有个花生仁,我吃了,你谗了,我的故事讲完了.";
    private Context mContext;
    private Handler mHandler = new Handler();


    private WNProgressBarDialog mProgressBarDialog;

    private Button btn01;
    private Button btn02;
    private Button btn03;
    private Button btn04;
    private Button btn06;
    private Button btn07;
    private Button btn10;
    private Button btn11;
    private Button btn12;
    private Button btn13;
    private Button btn14;
    private Button btn15;
    private Button btn16;
    private Button btn17;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_dialog);

        mContext = this;

        initViews();
    }


    private void initViews() {
        btn01 = (Button) findViewById(R.id.btn01);
        btn02 = (Button) findViewById(R.id.btn02);
        btn03 = (Button) findViewById(R.id.btn03);
        btn04 = (Button) findViewById(R.id.btn04);
        btn06 = (Button) findViewById(R.id.btn06);
        btn07 = (Button) findViewById(R.id.btn07);
        btn10 = (Button) findViewById(R.id.btn10);
        btn11 = (Button) findViewById(R.id.btn11);
        btn12 = (Button) findViewById(R.id.btn12);
        btn13 = (Button) findViewById(R.id.btn13);
        btn14 = (Button) findViewById(R.id.btn14);
        btn15 = (Button) findViewById(R.id.btn15);
        btn16 = (Button) findViewById(R.id.btn16);
        btn17 = (Button) findViewById(R.id.btn17);

        btn01.setOnClickListener(this);
        btn02.setOnClickListener(this);
        btn03.setOnClickListener(this);
        btn04.setOnClickListener(this);
        btn06.setOnClickListener(this);
        btn07.setOnClickListener(this);
        btn10.setOnClickListener(this);
        btn11.setOnClickListener(this);
        btn12.setOnClickListener(this);
        btn13.setOnClickListener(this);
        btn14.setOnClickListener(this);
        btn15.setOnClickListener(this);
        btn16.setOnClickListener(this);
        btn17.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn01:
                WNProgressDialog.showProgress(this);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        WNProgressDialog.showProgress(GlobalDialogActivity.this, "加载中...");
                    }
                }, 1000);
                WNProgressDialog.showProgress(this);
                //延时关闭
                delayDismissProgressDialog();
                break;
            case R.id.btn02:
                WNProgressDialog.showProgress(this, text01);
                //延时关闭
                delayDismissProgressDialog();
                break;
            case R.id.btn03:
                WNDialogConfig mDialogConfig2 = new WNDialogConfig.Builder()
                        //全屏模式
                        .isWindowFullscreen(true)
                        .isCanceledOnTouchOutside(true)
                        .isCancelable(true)
                        .setProgressSize(30)
                        .setMinWidthAndHeight(50,50)
                        .setPadding(16,16,16,16) 
                        //dialog动画
                        .setAnimationID(R.style.animate_dialog_custom)
                        .setOnDialogDismissListener(new OnDialogDismissListener() {
                            @Override
                            public void onDismiss() {
                                mHandler.removeCallbacksAndMessages(null);
                            }
                        })
                        .build();
                WNProgressDialog.showProgress(this, mDialogConfig2);
                //延时关闭
                delayDismissProgressDialog();
                break;
            case R.id.btn04:
                WNDialogConfig mDialogConfig = new WNDialogConfig.Builder()
                        //全屏模式
                        .isWindowFullscreen(true)
                        //Progress大小（宽高）
                        .setProgressSize(60)
                        //点击外部是否可以取消
                        .isCanceledOnTouchOutside(true)
                        //物理返回键可以取消
                        .isCancelable(true)
                        //全屏背景窗体的颜色
                        .setBackgroundWindowColor(getMyColor(R.color.colorDialogWindowBg))
                        //View背景的颜色
                        .setBackgroundViewColor(getMyColor(R.color.colorDialogViewBg))
                        //View背景的圆角
                        .setCornerRadius(20)
                        //View 边框的颜色
                        .setStrokeColor(getMyColor(R.color.teal_200))
                        //View 边框的宽度
                        .setStrokeWidth(2)
                        //Progress 颜色
                        .setProgressColor(getMyColor(R.color.colorDialogProgressBarColor))
                        //Progress 宽度
                        .setProgressWidth(3)
                        //Progress 内圈颜色
                        .setProgressRimColor(Color.YELLOW)
                        //Progress 内圈宽度
                        .setProgressRimWidth(4)
                        //文字的颜色
                        .setTextColor(getMyColor(R.color.colorDialogTextColor))
                        //文字的大小
                        .setTextSize(15)
                        //ProgressBar 颜色
                        .setProgressColor(Color.GREEN)
                        //dialog动画
                        .setAnimationID(R.style.animate_dialog_custom)
                        //padding
                        .setPadding(40, 10, 40, 10)
                        //关闭的监听
                        .setOnDialogDismissListener(new OnDialogDismissListener() {
                            @Override
                            public void onDismiss() {
                                mHandler.removeCallbacksAndMessages(null);
                                WNToast.makeTextShort(mContext, "监听到了ProgressDialog关闭了");
                            }
                        })
                        .build();
                WNProgressDialog.showProgress(this, "数据上传中...", mDialogConfig);
                break;
            case R.id.btn06:
                showStatusDialog01();
                break;
            case R.id.btn07:
                showStatusDialog02();
                break;
            case R.id.btn10:
                showToast();
                break;
            case R.id.btn11:
                showToastCustom();
                break;
            case R.id.btn12:
                showToastCustom2();
                break;
            case R.id.btn13:
                showToastCustom3();
                break;
            case R.id.btn14:
                configProgressbarHorizontalDialog();
                startProgress(true);
                break;
            case R.id.btn15:
                configProgressbarHorizontalDialog2();
                startProgress(false);
                break;
            case R.id.btn16:
                configProgressbarCircleDialog();
                startProgress(true);
                break;
            case R.id.btn17:
                configProgressbarCircleDialog2();
                startProgress(false);
                break;
        }
    }


    /**
     * --------------------MProgressDialog start -------------------
     */

    private int getMyColor(int colorID) {
        return mContext.getResources().getColor(colorID);
    }


    private void delayDismissProgressDialog() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                WNProgressDialog.dismissProgress();
            }
        }, 3000);
    }


    /** --------------------MProgressDialog end ------------------- */


    /**
     * --------------------MToast start -------------------
     */

    private void showToastCustom3() {
        WNToastConfig config = new WNToastConfig.Builder()
                .setBackgroundStrokeColor(Color.YELLOW)
                .setBackgroundStrokeWidth(1)
                .setBackgroundCornerRadius(20)
                .build();
        WNToast.makeTextShort(mContext, text01, config);
    }

    private void showToastCustom2() {
        WNToastConfig config = new WNToastConfig.Builder()
                .setGravity(WNToastConfig.MToastGravity.CENTRE)
                .setTextColor(Color.MAGENTA)
                .setBackgroundColor(getMyColor(R.color.colorDialogTest))
                .setToastIcon(mContext.getResources().getDrawable(R.drawable.mn_icon_dialog_ok))
                .setBackgroundCornerRadius(10)
                .setTextSize(13)
                .setImgWidthAndHeight(40, 40)
                .setPadding(20, 20, 20, 20)
                .build();
        WNToast.makeTextShort(mContext, text01, config);
    }

    private void showToastCustom() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                WNToastConfig config = new WNToastConfig.Builder()
                        .setTextColor(getMyColor(R.color.white))
                        .setBackgroundColor(getMyColor(R.color.colorDialogTest))
                        .setToastIcon(mContext.getResources().getDrawable(R.drawable.mn_icon_dialog_ok))
                        .setTextSize(18)
                        .build();
                WNToast.makeTextShort(mContext, "欢迎使用自定义Toast-异步线程", config);
            }
        }).start();

    }

    private void showToast() {
        WNToast.makeTextShort(mContext, "我是默认Toast");
    }


    /** --------------------MToast start ------------------- */


    /**
     * --------------------MStatusDialog start -------------------
     */

    private WNStatusDialog mStatusDialog;

    private void showStatusDialog01() {
        mStatusDialog = new WNStatusDialog(this);
        mStatusDialog.show(
                "正在保存,请稍等..",
                mContext.getResources().getDrawable(R.drawable.mn_icon_dialog_ok),
                5000);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //关闭
                mStatusDialog.dismiss();
                new WNStatusDialog(mContext).show(
                        "保存成功",
                        mContext.getResources().getDrawable(R.drawable.mn_icon_dialog_ok));
            }
        }, 1000);
    }

    private void showStatusDialog02() {
        WNDialogConfig mDialogConfig = new WNDialogConfig.Builder()
                //全屏模式
                .isWindowFullscreen(true)
                //全屏背景窗体的颜色
                .setBackgroundWindowColor(getMyColor(R.color.colorDialogWindowBg))
                //View背景的颜色
                .setBackgroundViewColor(getMyColor(R.color.colorDialogViewBg2))
                //字体的颜色
                .setTextColor(getMyColor(R.color.teal_200))
                //文字大小
                .setTextSize(13)
                //View边框的颜色
                .setStrokeColor(getMyColor(R.color.white))
                //View边框的宽度
                .setStrokeWidth(2)
                //View圆角大小
                .setCornerRadius(10)
                //动画
                .setAnimationID(R.style.animate_dialog_custom)
                //图片的宽高dp
                .setImgWidthAndHeight(60, 60)
                //padding
                .setPadding(40, 10, 40, 10)
                //关闭的监听
                .setOnDialogDismissListener(new OnDialogDismissListener() {
                    @Override
                    public void onDismiss() {
                        WNToast.makeTextShort(mContext, "监听到了MStatusDialog关闭了");
                    }
                })
                .build();
        new WNStatusDialog(mContext, mDialogConfig)
                .show("恭喜你，签到成功\n积分+10",
                        mContext.getResources().getDrawable(R.drawable.icon_staues_test),
                        1500);
    }

    /** --------------------MStatusDialog end ------------------- */


    /**
     * --------------------MProgressBarDialog start -------------------
     */
    private void configProgressbarCircleDialog() {
        //新建一个Dialog
        mProgressBarDialog = new WNProgressBarDialog.Builder(mContext)
                .setStyle(WNProgressBarDialog.MProgressBarDialogStyle_Circle)
                .build();
    }

    private void configProgressbarCircleDialog2() {
        //新建一个Dialog
        mProgressBarDialog = new WNProgressBarDialog.Builder(mContext)
                //全屏模式
                .isWindowFullscreen(true)
                //样式
                .setStyle(WNProgressBarDialog.MProgressBarDialogStyle_Circle)
                //全屏背景窗体的颜色
                .setBackgroundWindowColor(getMyColor(R.color.colorDialogWindowBg))
                //View背景的颜色
                .setBackgroundViewColor(getMyColor(R.color.colorDialogViewBg2))
                //字体的颜色
                .setTextColor(getMyColor(R.color.teal_200))
                //View边框的颜色
                .setStrokeColor(getMyColor(R.color.white))
                //View边框的宽度
                .setStrokeWidth(2)
                //View圆角大小
                .setCornerRadius(10)
                //ProgressBar背景色
                .setProgressbarBackgroundColor(Color.BLUE)
                //ProgressBar 颜色
                .setProgressColor(Color.GREEN)
                //圆形内圈的宽度
                .setCircleProgressBarWidth(4)
                //圆形外圈的宽度
                .setCircleProgressBarBackgroundWidth(4)
                //dialog动画
                .setAnimationID(R.style.animate_dialog_custom)
                .build();
    }

    private void configProgressbarHorizontalDialog() {
        //新建一个Dialog
        mProgressBarDialog = new WNProgressBarDialog.Builder(mContext)
                .setStyle(WNProgressBarDialog.MProgressBarDialogStyle_Horizontal)
                .build();
    }

    private void configProgressbarHorizontalDialog2() {
        //新建一个Dialog
        mProgressBarDialog = new WNProgressBarDialog.Builder(mContext)
                .setStyle(WNProgressBarDialog.MProgressBarDialogStyle_Horizontal)
                //全屏背景窗体的颜色
                .setBackgroundWindowColor(getMyColor(R.color.colorDialogWindowBg))
                //View背景的颜色
                .setBackgroundViewColor(getMyColor(R.color.colorDialogViewBg2))
                //字体的颜色
                .setTextColor(getMyColor(R.color.teal_200))
                //View边框的颜色
                .setStrokeColor(getMyColor(R.color.white))
                //View边框的宽度
                .setStrokeWidth(2)
                //View圆角大小
                .setCornerRadius(10)
                //ProgressBar背景色
                .setProgressbarBackgroundColor(Color.BLUE)
                //ProgressBar 颜色
                .setProgressColor(Color.GREEN)
                //水平进度条Progress圆角
                .setProgressCornerRadius(0)
                //水平进度条的高度
                .setHorizontalProgressBarHeight(10)
                //dialog动画
                .setAnimationID(R.style.animate_dialog_custom)
                .build();
    }

    //当前进度
    private int currentProgress;
    //是否开启动画：平滑过度，默认开启
    private boolean animal = true;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (currentProgress < 100) {
                mProgressBarDialog.showProgress(currentProgress, "当前进度为：" + currentProgress + "%", animal);
                currentProgress += 5;
                mHandler.postDelayed(runnable, 200);
            } else {
                mHandler.removeCallbacks(runnable);
                currentProgress = 0;
                mProgressBarDialog.showProgress(100, "完成", animal);
                //关闭
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBarDialog.dismiss();
                    }
                }, 1000);
            }
        }
    };

    private void startProgress(boolean animal) {
        this.animal = animal;
        mHandler.post(runnable);
    }

    /**
     * --------------------MProgressBarDialog end -------------------
     */
}
