package com.muxin.asus.arg.common.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_PICCFG_V30;
import com.hikvision.netsdk.NET_DVR_PREVIEWINFO;
import com.hikvision.netsdk.RealPlayCallBack;

import org.MediaPlayer.PlayM4.Player;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class PlaySurfaceView extends SurfaceView implements Callback {
    private String mChanName = "";
    private String mChan = "";
    //    private String
    private int mLogId;

    public int getLogId() {
        return mLogId;
    }

    public void setLogId(int logId) {
        mLogId = logId;
    }

    public String getChanName() {
        return mChanName;
    }

    public String getChan() {
        return mChan;
    }

    public void setChan(String chan) {
        mChan = chan;
    }

    public static List<Integer> list = new ArrayList<Integer>();
    private final String TAG = "PlaySurfaceView";
    private int m_iWidth = 0;
    private int m_iHeight = 0;
    public int m_iPreviewHandle = -1;
    private int m_iPort = -1;
    private boolean m_bSurfaceCreated = false;

    private Context mContext;

    public PlaySurfaceView(Activity context) {
        super((Context) context);
        // TODO Auto-generated constructor stub
        mContext = context;
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
        System.out.println("surfaceChanged");

    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        // TODO Auto-generated method stub
        m_bSurfaceCreated = true;
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        Log.e(TAG, "m_iPreviewHandle  " + m_iPreviewHandle);
        if (m_iPreviewHandle < 0) {
            Log.e(TAG, "NET_DVR_RealPlay is failed!Err:"
                    + HCNetSDK.getInstance().NET_DVR_GetLastError());
            Canvas canvas = getHolder().lockCanvas();
            Paint paint = new Paint();
            paint.setTextSize(50);
            paint.setColor(Color.DKGRAY);
            Rect rect = new Rect();
            String s = "没有视频";
            paint.getTextBounds(s, 0, s.length(), rect);
            canvas.drawColor(Color.BLACK);
            canvas.drawText(s, (getWidth() - rect.width()) / 2, (getHeight() + rect.height()) / 2, paint);
            getHolder().unlockCanvasAndPost(canvas);
        }
        if (-1 == m_iPort) {
            return;
        }

        Surface surface = arg0.getSurface();

        if (true == surface.isValid()) {
            if (false == Player.getInstance().setVideoWindow(m_iPort, 0, arg0)) {
                Log.e(TAG, "Player setVideoWindow failed!");
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        // TODO Auto-generated method stub
        m_bSurfaceCreated = false;
        if (-1 == m_iPort) {
            return;
        }
        if (true == arg0.getSurface().isValid()) {
            if (false == Player.getInstance().setVideoWindow(m_iPort, 0, null)) {
                Log.e(TAG, "Player setVideoWindow failed!");
            }
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.setMeasuredDimension(m_iWidth - 1, m_iHeight - 1);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    public void setParam(int width, int height, int count) {
//        if (count == 1) {
//            m_iWidth = width;
//            m_iHeight = height;
//        } else if (count % 2 == 0) {
//            m_iWidth = width / 2;
//            m_iHeight = (height * 2) / count;
//        } else {
//            m_iWidth = width / 2;
//            m_iHeight = (height * 2) / (count + 1);
//        }
        if (count == 1) {
            m_iWidth = width;
            m_iHeight = height;
        } else {
            handleSize(width, height, count);
        }

    }

    private void handleSize(int width, int height, int count) {
        for (int i = 1; ; i++) {
            if (d(i, count)) {
                m_iWidth = width / (i + 1);
                m_iHeight = height / (i + 1);
                Log.e("blw   handleSize", m_iWidth + "   ,  " + m_iHeight);
                break;
            }
        }
    }

    private boolean d(int num, int count) {
        return (num - 1) * (num - 1) < count && (num + 1) * (num + 1) >= count ? true
                : false;
    }

    public int getCurWidth() {
        return m_iWidth;
    }

    public int getCurHeight() {
        return m_iHeight;
    }


    public void startPreview(int iUserID, int iChan) {
        mChanName = Test_PicCfg(iUserID, iChan);
        Log.e(TAG, "startPreview:" + mChanName);
        mChan = iChan + "";
        mLogId = iUserID;
        RealPlayCallBack fRealDataCallBack = getRealPlayerCbf();
        if (fRealDataCallBack == null) {
            Log.e(TAG, "fRealDataCallBack object is failed!");
            return;
        }
        Log.i(TAG, "preview channel:" + iChan);

        NET_DVR_PREVIEWINFO previewInfo = new NET_DVR_PREVIEWINFO();
        previewInfo.lChannel = iChan;
        previewInfo.dwStreamType = 1; // substream
        previewInfo.bBlocked = 1;
        // HCNetSDK start preview
        m_iPreviewHandle = HCNetSDK.getInstance().NET_DVR_RealPlay_V40(iUserID,
                previewInfo, fRealDataCallBack);
        if (m_iPreviewHandle < 0) {
            Log.e(TAG, "NET_DVR_RealPlay is failed!Err:"
                    + HCNetSDK.getInstance().NET_DVR_GetLastError());

        }
        list.add(m_iPreviewHandle);
    }

    public void stopPreview() {
        HCNetSDK.getInstance().NET_DVR_StopRealPlay(m_iPreviewHandle);
        stopPlayer();
    }

    private void stopPlayer() {
        Player.getInstance().stopSound();
        // player stop play
        if (!Player.getInstance().stop(m_iPort)) {
            Log.e(TAG, "stop is failed!");
            return;
        }

        if (!Player.getInstance().closeStream(m_iPort)) {
            Log.e(TAG, "closeStream is failed!");
            return;
        }
        if (!Player.getInstance().freePort(m_iPort)) {
            Log.e(TAG, "freePort is failed!" + m_iPort);
            return;
        }
        m_iPort = -1;
    }

    private RealPlayCallBack getRealPlayerCbf() {
        RealPlayCallBack cbf = new RealPlayCallBack() {
            public void fRealDataCallBack(int iRealHandle, int iDataType,
                                          byte[] pDataBuffer, int iDataSize) {
                processRealData(1, iDataType, pDataBuffer, iDataSize,
                        Player.STREAM_REALTIME);
            }
        };
        return cbf;
    }

    private void processRealData(int iPlayViewNo, int iDataType,
                                 byte[] pDataBuffer, int iDataSize, int iStreamMode) {
        // Log.i(TAG, "iPlayViewNo:" + iPlayViewNo + ",iDataType:" + iDataType +
        // ",iDataSize:" + iDataSize);
        if (HCNetSDK.NET_DVR_SYSHEAD == iDataType) {
            if (m_iPort >= 0) {
                return;
            }
            m_iPort = Player.getInstance().getPort();
            if (m_iPort == -1) {
                Log.e(TAG, "getPort is failed with: "
                        + Player.getInstance().getLastError(m_iPort));
                return;
            }
            Log.i(TAG, "getPort succ with: " + m_iPort);
            if (iDataSize > 0) {
                if (!Player.getInstance().setStreamOpenMode(m_iPort,
                        iStreamMode)) // set stream mode
                {
                    Log.e(TAG, "setStreamOpenMode failed");
                    return;
                }
                if (!Player.getInstance().openStream(m_iPort, pDataBuffer,
                        iDataSize, 2 * 1024 * 1024)) // open stream
                {
                    Log.e(TAG, "openStream failed");
                    return;
                }
                while (!m_bSurfaceCreated) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Log.i(TAG, "wait 100 for surface, handle:" + iPlayViewNo);
                }

                if (!Player.getInstance().play(m_iPort, getHolder())) {
                    Log.e(TAG, "play failed,error:"
                            + Player.getInstance().getLastError(m_iPort));
                    return;
                }
                if (!Player.getInstance().playSound(m_iPort)) {
                    Log.e(TAG, "playSound failed with error code:"
                            + Player.getInstance().getLastError(m_iPort));
                    return;
                }
            }
        } else {
            if (!Player.getInstance()
                    .inputData(m_iPort, pDataBuffer, iDataSize)) {
                Log.e(TAG, "inputData failed with: "
                        + Player.getInstance().getLastError(m_iPort));
            }
        }
    }


    public String Test_PicCfg(int iUserID, int iChan) {
        NET_DVR_PICCFG_V30 struPiccfg = new NET_DVR_PICCFG_V30();
        if (!HCNetSDK.getInstance().NET_DVR_GetDVRConfig(iUserID, HCNetSDK.NET_DVR_GET_PICCFG_V30, iChan, struPiccfg)) {
            System.out.println("NET_DVR_GET_PICCFG_V30 faild!" + " err: " + HCNetSDK.getInstance().NET_DVR_GetLastError());
            return null;
        } else {
            try {
                System.out.println("NET_DVR_GET_PICCFG_V30 succ!" + new String(struPiccfg.sChanName, "GB2312").trim());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                return new String(struPiccfg.sChanName, "GB2312").trim();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
        }

    }
}
