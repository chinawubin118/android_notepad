package com.lotte.android_notepad.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.lotte.android_notepad.BaseActivity;
import com.lotte.android_notepad.R;
import com.lotte.android_notepad.db.MyNoteDBHelper;
import com.lotte.android_notepad.model.Note;

import java.io.File;

/**
 * 备忘详情页面
 */
public class NoteDetailActivity extends BaseActivity implements View.OnTouchListener {

    private TextView tvContent;//内容
    private TextView tvDelete, tvReturn;//取消,保存
    private TextView tvPlay, tvPause;//播放,暂停
    private ImageView ivContent;//图片内容
    private VideoView vvContent;
    private LinearLayout llVideoPlayer;//视频播放器布局

    private Note note;//备忘对象

    GestureDetector mGesture;//手势识别器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        note = (Note) getIntent().getSerializableExtra("note");

        initViews();
        setDataToView();
    }

    private void initViews() {
        tvContent = (TextView) findViewById(R.id.tvContent);
        tvDelete = (TextView) findViewById(R.id.tvDelete);
        tvReturn = (TextView) findViewById(R.id.tvReturn);
        tvPlay = (TextView) findViewById(R.id.tvPlay);
        tvPause = (TextView) findViewById(R.id.tvPause);
        ivContent = (ImageView) findViewById(R.id.ivContent);
        vvContent = (VideoView) findViewById(R.id.vvContent);
        llVideoPlayer = (LinearLayout) findViewById(R.id.llVideoPlayer);

        tvDelete.setOnClickListener(this);
        tvReturn.setOnClickListener(this);
        tvPlay.setOnClickListener(this);
        tvPause.setOnClickListener(this);
        vvContent.setOnTouchListener(this);
    }

    private void setDataToView() {
        tvContent.setText(note.getTitle());
        if (!TextUtils.isEmpty(note.getImagePath())) {
            ivContent.setVisibility(View.VISIBLE);
            Glide.with(this).load(new File(note.getImagePath())).into(ivContent);
        }
        if (!TextUtils.isEmpty(note.getVideoPath())) {
            llVideoPlayer.setVisibility(View.VISIBLE);
            vvContent.setVideoPath(note.getVideoPath());
            vvContent.start();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvReturn:
                onBackPressed();
                break;
            case R.id.tvDelete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                AlertDialog alertDialog = builder.setTitle("提示").setMessage("是否删除该备忘?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteNote();
                                Toast.makeText(getApplicationContext(), "删除成功!", Toast.LENGTH_SHORT).show();
                                finish();
                                dialog.dismiss();
                            }
                        }).setNegativeButton("取消", null).create();
                alertDialog.show();
                break;
            case R.id.tvPlay:
                vvContent.start();
                break;
            case R.id.tvPause:
                vvContent.pause();
                break;
        }
    }

    private void deleteNote() {
        writableDB.delete(MyNoteDBHelper.TABLE_NAME, MyNoteDBHelper.ID + "=" + note.getId(), null);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mGesture == null) {
            mGesture = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDown(MotionEvent e) {
                    //返回false的话只能响应长摁事件
                    if (vvContent.isPlaying()) {
                        vvContent.pause();
                    } else {
                        vvContent.start();
                    }
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);
                }

                @Override
                public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                    return super.onScroll(e1, e2, distanceX, distanceY);
                }
            });
            mGesture.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    return true;
                }

                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    return true;
                }

                @Override
                public boolean onDoubleTapEvent(MotionEvent e) {
                    return false;
                }
            });
        }

        return mGesture.onTouchEvent(event);
    }
}
