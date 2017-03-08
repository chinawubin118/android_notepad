package com.lotte.android_notepad.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lotte.android_notepad.BaseActivity;
import com.lotte.android_notepad.R;
import com.lotte.android_notepad.model.Note;

import java.io.File;

/**
 * 备忘详情页面
 */
public class NoteDetailActivity extends BaseActivity {

    private TextView tvContent;//内容
    private TextView tvDelete, tvReturn;//取消,保存
    private ImageView ivContent;//图片内容

    private Note note;//备忘对象

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
        ivContent = (ImageView) findViewById(R.id.ivContent);

        tvDelete.setOnClickListener(this);
        tvReturn.setOnClickListener(this);
    }

    private void setDataToView() {
        tvContent.setText(note.getTitle());
        if (!TextUtils.isEmpty(note.getImagePath())) {
            ivContent.setVisibility(View.VISIBLE);
            Glide.with(this).load(new File(note.getImagePath())).into(ivContent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvReturn:
                onBackPressed();
                break;
            case R.id.tvDelete:
                onBackPressed();
                break;
        }
    }
}
