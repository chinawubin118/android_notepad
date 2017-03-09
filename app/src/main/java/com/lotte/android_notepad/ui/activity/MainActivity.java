package com.lotte.android_notepad.ui.activity;

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

import com.lotte.android_notepad.BaseActivity;
import com.lotte.android_notepad.R;
import com.lotte.android_notepad.Utils;
import com.lotte.android_notepad.adapter.NoteListAdapter;
import com.lotte.android_notepad.db.MyNoteDBHelper;
import com.lotte.android_notepad.model.Note;
import com.lotte.android_notepad.support.GlideImageLoader;
import com.lotte.android_notepad.support.MyDecoration;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;

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

        initImagePicker();

        initViews();

        setToolbar();
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
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
        //添加分隔线
        rcvNoteList.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST));
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

    //查询出来已经添加的备忘
    private void queryNotes() {
        Cursor cursor = writableDB.query(MyNoteDBHelper.TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {

            int id = cursor.getInt(cursor.getColumnIndex(MyNoteDBHelper.ID));
            String content = cursor.getString(cursor.getColumnIndex(MyNoteDBHelper.CONTENT));
            String time = cursor.getString(cursor.getColumnIndex(MyNoteDBHelper.TIME));
            String imagePath = cursor.getString(cursor.getColumnIndex(MyNoteDBHelper.IMAGE_PATH));
            String videoPath = cursor.getString(cursor.getColumnIndex(MyNoteDBHelper.VIDEO_PATH));

            Note note = new Note();
            note.setId(id);
            note.setTitle(content);
            note.setTime(time);
            note.setImagePath(imagePath);
            note.setVideoPath(videoPath);
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

        TextView tv_text = (TextView) view.findViewById(R.id.tv_text);//添加新备忘
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);//取消

        tv_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivity(intent);
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
