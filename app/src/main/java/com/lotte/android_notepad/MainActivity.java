package com.lotte.android_notepad;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lotte.android_notepad.adapter.NoteListAdapter;
import com.lotte.android_notepad.db.MyNoteDBHelper;
import com.lotte.android_notepad.model.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private Toolbar toolbar;
    private TextView tv_add;//右上角的添加
    private RecyclerView rcvNoteList;//备忘列表
    private NoteListAdapter mAdapter;
    private List<Note> noteList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        setToolbar();

//        insertData();
    }

    @Override
    protected void onResume() {
        super.onResume();

        noteList.clear();
        queryNotes();

        mAdapter = new NoteListAdapter(this, noteList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvNoteList.setLayoutManager(manager);
        rcvNoteList.setAdapter(mAdapter);
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_add = (TextView) findViewById(R.id.tv_add);
        rcvNoteList = (RecyclerView) findViewById(R.id.rcvNoteList);

        tv_add.setOnClickListener(this);
    }

    private void setToolbar() {
        //设置导航图标要在setSupportActionBar方法之后
        Utils.initToolbar(this, toolbar, "", "", 0, null);//不设置icon

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    //插入数据
    private void insertData() {
        ContentValues cv = new ContentValues();
        cv.put(MyNoteDBHelper.CONTENT, "this is the first content");
        cv.put(MyNoteDBHelper.TIME, Utils.getTimeStr());

        writableDB.insert(MyNoteDBHelper.TABLE_NAME, null, cv);
    }

    //查询出来已经添加的备忘
    private void queryNotes() {
        ContentValues cv = new ContentValues();
        cv.put(MyNoteDBHelper.CONTENT, "this is the first content");
        cv.put(MyNoteDBHelper.TIME, Utils.getTimeStr());

        Cursor cursor = writableDB.query(MyNoteDBHelper.TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String content = cursor.getString(cursor.getColumnIndex(MyNoteDBHelper.CONTENT));
            String time = cursor.getString(cursor.getColumnIndex(MyNoteDBHelper.TIME));

            Note note = new Note();
            note.setTitle(content);
            note.setTime(time);
            noteList.add(note);
        }
        cursor.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add:
                showAddMenuDialog();
                break;
        }
    }

    //显示添加新备忘的对话框
    private void showAddMenuDialog() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.show();
        View view = View.inflate(this, R.layout.alert_add_menu, null);

        TextView tv_text = (TextView) view.findViewById(R.id.tv_text);//文本类型
        TextView tv_image = (TextView) view.findViewById(R.id.tv_image);//图文类型
        TextView tv_video = (TextView) view.findViewById(R.id.tv_video);//视频类型
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);//取消

        tv_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivity(intent);
                alertDialog.dismiss();
            }
        });
        tv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        tv_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        Window window = alertDialog.getWindow();
        window.setContentView(view);
        window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.DialogBottomStyle);//添加动画

        WindowManager.LayoutParams layoutParams = alertDialog.getWindow().getAttributes();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
    }
}
