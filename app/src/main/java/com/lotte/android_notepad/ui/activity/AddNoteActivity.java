package com.lotte.android_notepad.ui.activity;

import android.content.ContentValues;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lotte.android_notepad.BaseActivity;
import com.lotte.android_notepad.R;
import com.lotte.android_notepad.Utils;
import com.lotte.android_notepad.db.MyNoteDBHelper;

public class AddNoteActivity extends BaseActivity {

    private EditText etContent;//内容
    private TextView tvCancel, tvSave;//取消,保存

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        initViews();
    }

    private void initViews() {
        etContent = (EditText) findViewById(R.id.etContent);
        tvCancel = (TextView) findViewById(R.id.tvCancel);
        tvSave = (TextView) findViewById(R.id.tvSave);

        tvCancel.setOnClickListener(this);
        tvSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCancel://取消该备忘
                onBackPressed();
                break;
            case R.id.tvSave:
                String content = etContent.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(getApplicationContext(), "请输入内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                insertData(content);
                Toast.makeText(getApplicationContext(), "保存成功!", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    //插入数据
    private void insertData(String content) {
        ContentValues cv = new ContentValues();
        cv.put(MyNoteDBHelper.CONTENT, content);
        cv.put(MyNoteDBHelper.TIME, Utils.getTimeStr());

        writableDB.insert(MyNoteDBHelper.TABLE_NAME, null, cv);
    }
}
