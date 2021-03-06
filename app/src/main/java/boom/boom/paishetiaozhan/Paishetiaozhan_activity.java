package boom.boom.paishetiaozhan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Size;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;
import java.util.List;

import boom.boom.FontManager.FontManager;
import boom.boom.R;
import boom.boom.api.SysApplication;
import boom.boom.api.Utils;
import boom.boom.api.myVideoView;
import boom.boom.guizejieshao.Guizejieshao_activity;
import boom.boom.shangchuandengdai.Shangchuandengdai_activity;

/**
 * Created by 刘成英 on 2015/1/20.
 */
public class Paishetiaozhan_activity extends Activity implements SurfaceHolder.Callback{
    private RelativeLayout paishefanhui;
    private Button kaishipaishe;
    private Button fangqipaishe;
    private SurfaceView sv;
    private SurfaceHolder surfaceHolder;
    private Camera camera;
    private boolean previewRunning;
    private File StoreFile;
    private MediaRecorder mediaRecorder;
//    private final String tmpFilename = "tmpvideo";
//    private final int maxDurationInMs = 20000;
//    private final long maxFileSizeInBytes = 500000000;
//    private final int videoFramesPerSecond = 20;
    private String video_path = null;
    private ProgressBar mprogress;
    private int a = 0;
    private boolean onThreadStartStop = false;
    private String cl_id;
    private String cl_name;
    private boolean onStartStopState = false;
    private myVideoView vw;
    private boolean workingState;
    int paishexuanze=0;
    private RelativeLayout all_hight;
    private int yidongjuli;
    private int yidongjuki_hengxiang;



    //private Handler myMessageHandler;
    /*Handler myMessageHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            sss.setText(a+"s");
            super.handleMessage(msg);
        }
    };*/
    Handler myMessageHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            sss.setText(a+"s");
            }};
    Thread thread_progress = new Thread(new Runnable() {
        @Override
        public void run() {
            for (; a < 20; a++) {
                try {
                    Thread.sleep(1000);
                    mprogress.setProgress(a+1);
                    Message m = new Message();
                    m.what = 1;
                    Paishetiaozhan_activity.this.myMessageHandler.sendMessage(m);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });
    Handler on_thread_progress = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if (onThreadStartStop = !onThreadStartStop){
                onStartStopState = true;
                thread_progress.start();
            }else{
                a = 20;
                onStartStopState = false;
                thread_progress.interrupt();
            }
        }
    };
    Handler on_thread_start_stop = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        if (onStartStopState == false){}
                            else{
                                onStartStopState = false;
                                onStopRecording();
                                Log.e("Record", "Record was stopped.");
                                Message m = new Message();
                                m.what = 10000;
                                Paishetiaozhan_activity.this.on_thread_start_stop.sendMessage(m);
                            }
                        break;
                    case 2:
                        if (onStartStopState == true){}
                            else{
                                onStartStopState = true;
                                startRecording();
                            }
                        break;
                    case 10000:
                        sv.setVisibility(View.INVISIBLE);
                        vw.setVisibility(View.VISIBLE);
                        while (video_path == null);
                        vw.setVideoPath(video_path);
                        vw.start();
                        break;
                }
            }
    };

    private TextView sss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent = getIntent();
        if ((workingState = intent.getBooleanExtra("IfReturn", false)) == false) {
            int position = intent.getIntExtra("challenge_number", 1);
            cl_name = intent.getStringExtra("challenge_name");
            cl_id = intent.getStringExtra("challenge_id");
        }
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.paishetiaozhan);
        SysApplication.getInstance().addActivity(this);
        FontManager.changeFonts(FontManager.getContentView(this), this);//字体
        all_hight = (RelativeLayout) findViewById(R.id.video_btn);

        paishefanhui = (RelativeLayout) findViewById(R.id.paishefanhui);
        kaishipaishe = (Button) findViewById(R.id.kaishipaishe);
        fangqipaishe = (Button) findViewById(R.id.fangqipaishe);
        fangqipaishe.setRight(yidongjuki_hengxiang);
        fangqipaishe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msgbox();
            }
        });
        vw = (myVideoView) findViewById(R.id.on_surface_covered_view);
        vw.setVisibility(View.INVISIBLE);
        vw.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
            }
        });
        vw.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                vw.setVideoPath(video_path);
                vw.start();
            }
        });
        sv = (SurfaceView) findViewById(R.id.syncRecord_monitor);
        mprogress = (ProgressBar) findViewById(R.id.mprogress);
        sss= (TextView) findViewById(R.id.ssss);
        surfaceHolder = sv.getHolder();
        surfaceHolder.addCallback(this);

        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        kaishipaishe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                paishexuanze = 1;

                if (kaishipaishe.getText().equals("开始")) {
                    Message m = new Message();
                    m.what = 1;
                    Paishetiaozhan_activity.this.on_thread_progress.sendMessage(m);
                    kaishipaishe.setText("停止");
                    startRecording();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
//                                Message message = new Message();
//                                message.what = 1;
                                Thread.sleep(20000);
                                Message m = new Message();
                                m.what = 1;
                                Paishetiaozhan_activity.this.on_thread_start_stop.sendMessage(m);
                                Log.e("Record", "20s was ended.");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } else if (kaishipaishe.getText().equals("停止")) {
                    onStopRecording();
                    Message m = new Message();
                    m.what = 1;
                    Paishetiaozhan_activity.this.on_thread_progress.sendMessage(m);
                    Message mm = new Message();
                    mm.what = 10000;
                    Paishetiaozhan_activity.this.on_thread_start_stop.sendMessage(mm);
                } else if (kaishipaishe.getText().equals("上传")) {
                    Intent intent = new Intent();
                    intent.putExtra("file_path", StoreFile.getAbsolutePath());
                    intent.putExtra("elapsed", a);
                    if (!workingState) {
                        intent.putExtra("challenge_name", cl_name);
                        intent.putExtra("challenge_id", cl_id);
                        intent.putExtra("pf_iv", getIntent().getIntExtra("pf_iv", 0));
                        intent.setClass(Paishetiaozhan_activity.this, Shangchuandengdai_activity.class);
                        startActivity(intent);
                        Paishetiaozhan_activity.this.finish();
                    } else {
                        setResult(RESULT_OK, intent);
                        Paishetiaozhan_activity.this.finish();
                    }
                }
            }
        });

        paishefanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(paishexuanze ==1){
                    msgbox();
                }else {
                    finish();
                }
            }
        });

    }


    public void onStopRecording(){
        int hight = all_hight.getHeight();
        int hight2 = kaishipaishe.getHeight();
        int width = all_hight.getWidth();
        yidongjuki_hengxiang = (width/2)-(width/3);

        yidongjuli = (hight/2)-(hight2/2);
        mediaRecorder.setOnErrorListener(null);
        stopRecording();
        kaishipaishe.setText("上传");
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, -0.6f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f);
        translateAnimation.setDuration(1000);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                kaishipaishe.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        kaishipaishe.getLayoutParams());

                params.setMargins(yidongjuki_hengxiang,yidongjuli ,0,0);

                kaishipaishe.clearAnimation();
                kaishipaishe.setLayoutParams(params);
                kaishipaishe.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animationSet.addAnimation(translateAnimation);
        animationSet.setFillAfter(true);
        kaishipaishe.startAnimation(animationSet);
        fangqipaishe.setVisibility(View.VISIBLE);
        AnimationSet animationSet1 = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(-1, 1);
        alphaAnimation.setDuration(2000);
        animationSet1.addAnimation(alphaAnimation);
        animationSet1.setFillAfter(true);
        fangqipaishe.startAnimation(animationSet1);
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        camera = Camera.open();
        if (camera != null){
            Camera.Parameters params = camera.getParameters();
            List<Camera.Size> pictsizes = params.getSupportedPictureSizes();
            List<Camera.Size> pre = params.getSupportedPreviewSizes();
            camera.setParameters(params);
        }
        else {
            Toast.makeText(getApplicationContext(), "Camera not available!", Toast.LENGTH_LONG).show();
            finish();
        }
    }
    Camera.Size searchSizes(List<Camera.Size>sizes, double width , double height){
        Camera.Size rlt = sizes.get(0);
        double rltdiv = (double)rlt.height/(double)rlt.width;
        double olddiv = height/width;
        double min = Math.abs(rltdiv-olddiv);
        for (int i=1;i<sizes.size();i++){
            Camera.Size tmpsize = sizes.get(i);
            double tmpdiv = (double)tmpsize.height/(double)tmpsize.width;
            double tmpdiff = Math.abs(tmpdiv - olddiv);
            if(tmpdiff<min){
                rlt = tmpsize;
                min = tmpdiff;
            }
        }
        return rlt;
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (previewRunning){
            camera.stopPreview();
        }
        Camera.Parameters p = camera.getParameters();
        List<Camera.Size> pre = p.getSupportedPreviewSizes();
        Camera.Size size = searchSizes(pre,width,height);
        //p.setPreviewSize(size.width, size.height);
        //p.setPreviewFormat(PixelFormat.JPEG);
        camera.setDisplayOrientation(90);
        camera.setParameters(p);
        Camera.Size previewSize = p.getPreviewSize();
        Camera.Size picSize = p.getPictureSize();
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
            previewRunning = true;
        }
        catch (IOException e) {
            Log.e("CAMERA", "Camera meets internal error.");
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        previewRunning = false;
        camera.release();
    }

    public boolean startRecording(){
        try {
            camera.unlock();
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setCamera(camera);
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
//            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mediaRecorder.setOrientationHint(90);
            CamcorderProfile pro = CamcorderProfile.get(CamcorderProfile.QUALITY_480P);
            mediaRecorder.setProfile(pro);
//            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
//            mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
//            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
//            mediaRecorder.setMaxDuration(maxDurationInMs);
            File dirStorage = new File(Utils.getVideoPath());
            if (dirStorage == null){
                Log.e("CAMERA", "Unable to got the sdcard read access. Fall back to /data mode.");
                dirStorage = getCacheDir();
            }
            Log.d("CAMERA", "Store path: ==> " + dirStorage.getAbsolutePath());
            String random_name = Utils.getRandomName("mp4");
            Log.d("CAMERA", "Store file path ==> "+ dirStorage + "/" + random_name);
            this.video_path = dirStorage + "/" + random_name;
            StoreFile = new File(dirStorage, random_name);
            if (StoreFile.exists() == false){
                StoreFile.createNewFile();
            }
            Log.d("CAMERA", "File absolutely path ==> "+ StoreFile.getAbsolutePath());
            mediaRecorder.setOutputFile(StoreFile.getAbsolutePath());
//            mediaRecorder.setVideoFrameRate(videoFramesPerSecond);
//            mediaRecorder.setVideoEncodingBitRate(2 * 1024 * 1024);
//            mediaRecorder.setAudioEncodingBitRate(2 * 1024 * 1024);
//            mediaRecorder.setAudioSamplingRate(44100);
//            mediaRecorder.setVideoSize(sv.getWidth(), sv.getHeight());
//            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
//            mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);
//            mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
//            mediaRecorder.setMaxFileSize(maxFileSizeInBytes);
            mediaRecorder.prepare();
            mediaRecorder.start();
            return true;
        } catch (IllegalStateException e) {
            Log.e("CAMERA","Caught a illegal error.");
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            Log.e("CAMERA","Caught a I/O error.");
            e.printStackTrace();
            return false;
        }
    }

    public void stopRecording(){
        try {
            mediaRecorder.setOnErrorListener(null);
            mediaRecorder.stop();
            camera.setPreviewCallback(null);
            camera.lock();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
     private void msgbox(){
         final AlertDialog alertDialog=new AlertDialog.Builder(Paishetiaozhan_activity.this).create();
         alertDialog.show();
         alertDialog.setCancelable(false);
         Window window=alertDialog.getWindow();
         window.setContentView(R.layout.mbox_yesno);
         TextView ok_title=(TextView)window.findViewById(R.id.yn_title);
         TextView ok_text=(TextView)window.findViewById(R.id.yn_text);
         ok_title.setText("确定放弃");
         ok_text.setText("确定放弃本次挑战视频吗？");
         Button yes=(Button)window.findViewById(R.id.button_ok_yn);
         yes.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Paishetiaozhan_activity.this.finish();
                 stopRecording();
                 alertDialog.cancel();
             }
         });
         Button no = (Button)window.findViewById(R.id.button_cancel_yn);
         no.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 alertDialog.cancel();
             }
         });
     }
     }


