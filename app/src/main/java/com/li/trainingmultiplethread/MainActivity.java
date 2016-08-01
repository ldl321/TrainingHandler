package com.li.trainingmultiplethread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ImageView mImageView;
    private Button mLoadImageButton;
    private Button mToastButton;
    private ProgressBar mProgressBar;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mProgressBar.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    mProgressBar.setProgress((int)msg.obj);
                    break;
                case 2:
                    mImageView.setImageBitmap((Bitmap) msg.obj);
                    break;
            }
            //mProgressBar.setProgress();
        }
    } ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.activity_main_image_view);
        mLoadImageButton = (Button) findViewById(R.id.activity_main_load_image_button);
        mToastButton = (Button) findViewById(R.id.activity_main_toast_button);
        mProgressBar = (ProgressBar) findViewById(R.id.activity_main_progress_bar);

        mLoadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                Message msg = mHandler.obtainMessage();
                                msg.what = 0; //表示让progressbar显示出来
                                mHandler.sendMessage(msg);
                                for (int i = 1; i < 11; i++) {
                                    sleep();
                                   Message msg2 = mHandler.obtainMessage();
                                    msg2.what = 1; //表示让progressbar往前走
                                    msg2.obj = i * 10;
                                    mHandler.sendMessage(msg2);
                                }

                                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                                        R.drawable.ic_launcher);
                                Message msgBitMap = mHandler.obtainMessage();
                                msgBitMap.what = 2; //更新image
                                msgBitMap.obj = bitmap;
                                mHandler.sendMessage(msgBitMap);
                            }

                            private void sleep() {
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                ).start();
            }
        });

        mToastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "xxxxxxxxxxxxx", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
