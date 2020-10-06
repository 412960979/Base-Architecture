package com.base.loadingdialog.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.library.utils.SizeUtils;
import com.base.loadingdialog.R;
import com.base.loadingdialog.base.BaseDialog;
import com.base.loadingdialog.config.WNDialogConfig;
import com.base.loadingdialog.view.WNProgressWheel;


/**
 * LoadingDialog
 */

public class WNProgressDialog {

    private static BaseDialog mDialog;
    private static WNDialogConfig mDialogConfig;

    //布局
    private static RelativeLayout dialog_window_background;
    private static RelativeLayout dialog_view_bg;
    private static WNProgressWheel progress_wheel;
    private static TextView tv_show;


    private static void initDialog(Context mContext) {
        checkDialogConfig();
        try {
            View mProgressDialogView = LayoutInflater.from(mContext).inflate(R.layout.wn_progress_dialog_layout, null);
            mDialog = new BaseDialog(mContext, R.style.WNCustomDialog);
            mDialog.setContentView(mProgressDialogView);
            mDialog.initStatusBar(mDialogConfig.windowFullscreen);
            mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    //判断是不是有监听
                    if (mDialogConfig != null && mDialogConfig.onDialogDismissListener != null) {
                        mDialogConfig.onDialogDismissListener.onDismiss();
                        mDialogConfig.onDialogDismissListener = null;
                    }
                    releaseDialog();
                }
            });

            //布局相关
            dialog_window_background = (RelativeLayout) mProgressDialogView.findViewById(R.id.dialog_window_background);
            dialog_view_bg = (RelativeLayout) mProgressDialogView.findViewById(R.id.dialog_view_bg);
            progress_wheel = (WNProgressWheel) mProgressDialogView.findViewById(R.id.progress_wheel);
            tv_show = (TextView) mProgressDialogView.findViewById(R.id.tv_show);
            progress_wheel.spin();

            //配置相关
            configView(mContext);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(">>>MProgressDialog>>>", "MProgressDialog-initDialog异常:" + e.toString());
        }
    }

    private static void checkDialogConfig() {
        if (mDialogConfig == null) {
            mDialogConfig = new WNDialogConfig.Builder().build();
        }
    }

    private static void configView(Context mContext) {
        //设置动画
        if (mDialogConfig != null && mDialogConfig.animationID != 0 && mDialog.getWindow() != null) {
            mDialog.getWindow().setWindowAnimations(mDialogConfig.animationID);
        }

        //点击外部可以取消
        mDialog.setCanceledOnTouchOutside(mDialogConfig.canceledOnTouchOutside);
        //返回键取消
        mDialog.setCancelable(mDialogConfig.cancelable);
        //window背景色
        dialog_window_background.setBackgroundColor(mDialogConfig.backgroundWindowColor);
        //弹框背景
        GradientDrawable myGrad = new GradientDrawable();
        myGrad.setColor(mDialogConfig.backgroundViewColor);
        myGrad.setStroke(SizeUtils.dp2px(mContext, mDialogConfig.strokeWidth), mDialogConfig.strokeColor);
        myGrad.setCornerRadius(SizeUtils.dp2px(mContext, mDialogConfig.cornerRadius));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            dialog_view_bg.setBackground(myGrad);
        } else {
            dialog_view_bg.setBackgroundDrawable(myGrad);
        }
        dialog_view_bg.setPadding(
                SizeUtils.dp2px(mContext, mDialogConfig.paddingLeft),
                SizeUtils.dp2px(mContext, mDialogConfig.paddingTop),
                SizeUtils.dp2px(mContext, mDialogConfig.paddingRight),
                SizeUtils.dp2px(mContext, mDialogConfig.paddingBottom)
        );
        if (mDialogConfig.minWidth > 0 && mDialogConfig.minHeight > 0) {
            dialog_view_bg.setMinimumWidth(SizeUtils.dp2px(mContext, mDialogConfig.minWidth));
            dialog_view_bg.setMinimumHeight(SizeUtils.dp2px(mContext, mDialogConfig.minHeight));
        }

        //Progress设置
        progress_wheel.setBarColor(mDialogConfig.progressColor);
        progress_wheel.setBarWidth(SizeUtils.dp2px(mContext, mDialogConfig.progressWidth));
        progress_wheel.setRimColor(mDialogConfig.progressRimColor);
        progress_wheel.setRimWidth(mDialogConfig.progressRimWidth);
        ViewGroup.LayoutParams layoutParamsProgress = progress_wheel.getLayoutParams();
        layoutParamsProgress.width = SizeUtils.dp2px(mContext, mDialogConfig.progressSize);
        layoutParamsProgress.height = SizeUtils.dp2px(mContext, mDialogConfig.progressSize);
        progress_wheel.setLayoutParams(layoutParamsProgress);
        //文字颜色设置
        tv_show.setTextColor(mDialogConfig.textColor);
        tv_show.setTextSize(mDialogConfig.textSize);

        //点击事件
        dialog_window_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消Dialog
                if (mDialogConfig != null && mDialogConfig.canceledOnTouchOutside) {
                    dismissProgress();
                }
            }
        });
    }

    public static void showProgress(Context context) {
        showProgress(context, null,null);
    }

    public static void showProgress(Context context, CharSequence msg) {
        showProgress(context, msg, null);
    }

    public static void showProgress(Context context, WNDialogConfig mDialogConfig) {
        showProgress(context, null, mDialogConfig);
    }

    public static void showProgress(Context context, CharSequence msg, WNDialogConfig dialogConfig) {
        dismissProgress();
        try {
            //设置配置
            if (dialogConfig == null) {
                dialogConfig = new WNDialogConfig.Builder().build();
            }
            mDialogConfig = dialogConfig;

            initDialog(context);
            if (mDialog != null && tv_show != null) {
                if (TextUtils.isEmpty(msg)) {
                    tv_show.setVisibility(View.GONE);
                } else {
                    tv_show.setVisibility(View.VISIBLE);
                    tv_show.setText(msg);
                }
                mDialog.show();
            }
        } catch (Exception e) {
            Log.e(">>>MProgressDialog>>>", "MProgressDialog-showProgress异常:" + e.toString());
        }
    }

    public static void dismissProgress() {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                //判断是不是有监听
                if (mDialogConfig != null && mDialogConfig.onDialogDismissListener != null) {
                    mDialogConfig.onDialogDismissListener.onDismiss();
                    mDialogConfig.onDialogDismissListener = null;
                }
                mDialog.dismiss();
            }
        } catch (Exception e) {
            Log.e(">>>MProgressDialog>>>", "MProgressDialog-dismissProgress异常:" + e.toString());
        } finally {
            releaseDialog();
        }
    }

    private static void releaseDialog() {
        mDialogConfig = null;
        mDialog = null;
        dialog_window_background = null;
        dialog_view_bg = null;
        progress_wheel = null;
        tv_show = null;
    }

    public static boolean isShowing() {
        if (mDialog != null) {
            return mDialog.isShowing();
        }
        return false;
    }
}
