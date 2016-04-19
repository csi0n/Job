package com.csi0n.searchjob.ui.widget.qr.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.csi0n.searchjob.Config;
import com.csi0n.searchjob.R;
import com.csi0n.searchjob.controller.CaptureController;
import com.csi0n.searchjob.ui.activity.BaseActivity;
import com.csi0n.searchjob.ui.activity.UserInforActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.csi0n.searchjob.ui.widget.qr.app.ScanParams;
import com.csi0n.searchjob.ui.widget.qr.dimenscode.ScanHelper;
import com.csi0n.searchjob.ui.widget.qr.utils.FileUtils;
import com.zxing.android.MessageIDs;
import com.zxing.android.camera.CameraManager;
import com.zxing.android.decoding.CaptureActivityHandler;
import com.zxing.android.decoding.InactivityTimer;
import com.zxing.android.view.ViewfinderView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Vector;

/**
 * Modify the original author Codes,thanks. 扫描二维码，条码的界面
 *
 * @author Kaming
 */
@ContentView(R.layout.aty_capture)
public class CaptureActivity extends BaseActivity implements Callback {
    private CaptureController mCaptureController;
    private CaptureActivityHandler handler;
    private boolean hasSurface;
    /**
     * 条形码编码集合
     */
    private Vector<BarcodeFormat> decodeFormats;
    /**
     * 字符编码
     */
    private String characterSet;
    /**
     * 限时关闭Activity
     */
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    /**
     * 是否播放声音(Bi...)
     */
    private boolean playBeep;
    // private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    /**
     * 相机管理类
     */
    private CameraManager cameraManager;
    /**
     * 图片路径
     */
    private String mPhotoPath;
    /**
     * 解析的图片
     */
    private Bitmap mScanBitmap;
    /**
     * 闪光灯标记
     */
    private boolean isFlash;

    @ViewInject(R.id.surfaceview)
    private SurfaceView surfaceView;
    @ViewInject(R.id.viewfinderview)
    private ViewfinderView viewfinderView;
    @ViewInject(R.id.tv_title)
    private TextView mTVTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTVTitle.setText(this.getString(R.string.str_qr_code_scan));
        (findViewById(R.id.iv_1)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseQRCodeScan();
            }
        });
        (findViewById(R.id.iv_2)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                switchLight();
            }
        });
        (findViewById(R.id.iv_3)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseGallery();
            }
        });
        (findViewById(R.id.tv_back)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                backHome();
            }
        });
        // 屏幕常亮
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
    }

    @Override
    protected void initWidget() {
        mCaptureController = new CaptureController(this);
        mCaptureController.initCapture();
    }

    /**
     * 根据传入的features，选择 进行二维码or条码 进行扫描
     *
     * @param features
     * 值可能为 Constant.QRCODE_FEATURES(二维码) or
     * Constant.BARCODE_FEATURES(条码)
     */
    private static final int DEFAULT_SCAN = -1;
    private int mFeatures;

    private void chooseWhichFeatures(int features) {
        switch (features) {
            case ScanParams.QRCODE_FEATURES:
                chooseQRCodeScan();
                break;
            case DEFAULT_SCAN:
                chooseQRCodeScan();
                break;
            default:
                break;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onResume() {
        super.onResume();
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        cameraManager = new CameraManager(getApplication());
        // 初始化扫描框
        chooseWhichFeatures(mFeatures);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            // 初始化相机
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;
        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        // 初始化BiBi..声音
        initBeepSound();
        vibrate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            // 关闭相机预览
            handler.quitSynchronously();
            handler = null;
        }
        // 关闭闪光灯
        cameraManager.offFlashLight();
        // 释放相机
        cameraManager.closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    /**
     * 初始化 打开相机 启动线程解析
     *
     * @param surfaceHolder
     */
    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            cameraManager.openDriver(surfaceHolder);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        // 初始化 Handler 并开启线程解析
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    /**
     * 当SurFace 创建完成后回调此函数
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    /**
     * 获取 相机管理
     *
     * @return CameraManger
     */
    public CameraManager getCameraManager() {
        return cameraManager;
    }

    /**
     * 获取 扫描框
     *
     * @return
     */
    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    /**
     * 获取 消息处理
     *
     * @return
     */
    public Handler getHandler() {
        return handler;
    }

    /**
     * 重绘
     */
    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    /**
     * 处理解析
     *
     * @param obj
     * @param barcode
     */
    public void handleDecode(Result obj, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        showResult(obj, barcode);
    }

    /**
     * 处理中文乱码问题
     *
     * @param text
     * @return
     */
    private String recode(String text) {
        String format = "";
        final boolean ISO = Charset.forName("ISO-8859-1").newEncoder()
                .canEncode(text);
        try {
            if (ISO) {
                // 编码转成 GB2312
                format = new String(text.getBytes("ISO-8859-1"), "GB2312");
            } else {
                format = text;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return format;
    }

    /**
     * 回调 onActivityResult
     *
     * @param rawResult 扫描结果
     * @param barcode
     */
    private void showResult(final Result rawResult, Bitmap barcode) {
        String result = recode(rawResult.getText());
        mCaptureController.getVerifyQRFromNet(result);
        /*Intent intent = new Intent();
        intent.putExtra(ScanParams.RESULT, result);
        setResult(ScanParams.RESULT_OK, intent);
        finish();*/
    }

    /**
     * 重置相机预览
     *
     * @param delayMS 延迟重置的时间，mm为单位
     */
    public void restartPreviewAfterDelay(long delayMS) {
        if (handler != null) {
            handler.sendEmptyMessageDelayed(MessageIDs.restart_preview, delayMS);
        }
    }

    /**
     * 初始化 声音
     */
    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);
            try {
                AssetFileDescriptor fileDescriptor = getAssets().openFd(
                        "qrbeep.ogg");
                this.mediaPlayer.setDataSource(
                        fileDescriptor.getFileDescriptor(),
                        fileDescriptor.getStartOffset(),
                        fileDescriptor.getLength());
                this.mediaPlayer.setVolume(0.1F, 0.1F);
                this.mediaPlayer.prepare();
            } catch (IOException e) {
                this.mediaPlayer = null;
            }
        }
    }

    /**
     * 震动时长
     */
    private static final long VIBRATE_DURATION = 200L;

    /**
     * 启动相册 Tag
     */
    private static final int SELECT_PICTURE = 0;

    /**
     * 播放声音并震动
     */
    private void playBeepSoundAndVibrate() {
        // 播放声音
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        // 震动
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean res = super.onKeyDown(keyCode, event);
        try {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                setResult(RESULT_CANCELED);
                finish();
                res = true;
            } else if (keyCode == KeyEvent.KEYCODE_FOCUS
                    || keyCode == KeyEvent.KEYCODE_CAMERA) {
                res = true;
            }
        } catch (Exception e) {
            Toast.makeText(this, "异常错误", Toast.LENGTH_SHORT).show();
            Log.e("Key Down ", "Key Down Error ");
        }
        return res;
    }

    /**
     * 退出
     */
    private void backHome() {
        finish();
    }

    /**
     * 闪光灯开关
     */
    @SuppressWarnings("deprecation")
    private void switchLight() {
        cameraManager.switchFlashLight();
        if (!isFlash) {
            isFlash = true;
        } else {
            isFlash = false;
        }
    }

    /**
     * 选择相册
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void chooseGallery() {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT < 19) {
            intent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        }
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "选择图片"),
                SELECT_PICTURE);
    }

    public static final int mQRCodeWidth = 300;
    public static final int mQRCodeHeight = 300;

    /**
     * 选择扫描
     */
    @SuppressWarnings("deprecation")
    private void chooseQRCodeScan() {
        // 设置 扫描框的大小
        cameraManager.setManualFramingRect(mQRCodeWidth, mQRCodeHeight);
        // 重新绘制 扫描线
        viewfinderView.reDraw();
        viewfinderView.setCameraManager(cameraManager);
        drawViewfinder();
    }

    /**
     * 得到选中图片 并解析
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SELECT_PICTURE) {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(data.getData(), proj,
                    null, null, null);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                mPhotoPath = cursor.getString(columnIndex);
                if (TextUtils.isEmpty(mPhotoPath)) {
                    mPhotoPath = FileUtils.getPath(this, data.getData());
                    Log.i("Photo path", "the path : " + mPhotoPath);
                }
                Log.i("Photo path", "the path : " + mPhotoPath);
            }
            cursor.close();
            // 解析
            startScanning();
        }
    }

    /**
     * 开启线程 解析图片
     */
    private void startScanning() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Result result = scanningImage(mPhotoPath);
                if (result == null) {
                    // 子线程 需要Looper才能更新主视图
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(), "图片格式有误",
                            Toast.LENGTH_LONG).show();
                    Looper.loop();
                } else {
                    Log.i("Result", "result : " + result.toString());
                    // 显示结果
                    showResult(result, null);
                }
            }
        }).start();
    }

    /**
     * 解析二维码图片 or 条形码图片
     *
     * @param path
     * @return
     */
    protected Result scanningImage(String path) {
        Result result;
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        BitmapFactory.Options options = getSampleOptions(path);
        mScanBitmap = BitmapFactory.decodeFile(path, options);
        Result resultBarCode = null;
        try {
            // 扫描条形码
            resultBarCode = ScanHelper.scanningBarCode(mScanBitmap);
            Log.i("Content", "content : " + resultBarCode.getText());
        } catch (NotFoundException e1) {
            e1.printStackTrace();
            Log.e("NotFoundException", "The error : " + e1);
        }
        Result resultQRCode = null;
        try {
            // 扫描二维码
            resultQRCode = ScanHelper.scanningQRCode(mScanBitmap);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        // 返回不为空的结果
        if (resultBarCode != null) {
            result = resultBarCode;
        } else if (resultQRCode != null) {
            result = resultQRCode;
        } else {
            result = null;
        }
        return result;
    }

    /**
     * 获得缩小比例的 options
     *
     * @param path 文件路径
     * @return
     */
    private BitmapFactory.Options getSampleOptions(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        mScanBitmap = BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false;
        int sampleSize = (int) (options.outHeight / (float) 200);
        if (sampleSize <= 0) {
            sampleSize = 1;
        }
        options.inSampleSize = sampleSize;
        return options;
    }

    public void startShowUserInfo(String uid) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(Config.MARK_USER_INFO_ACTIVITY_IS_FROM_CAPTURE, true);
        bundle.putString(Config.MARK_USER_INFO_ACTIVITY_USER_ID, uid);
        skipActivityWithBundle(this, UserInforActivity.class, bundle);
    }
}