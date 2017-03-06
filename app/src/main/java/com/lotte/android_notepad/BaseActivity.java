package com.lotte.android_notepad;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lotte.android_notepad.db.MyNoteDBHelper;

/**
 * Created by wubin on 2017/3/6.
 */

public class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    protected MyNoteDBHelper myNoteDBHelper;
    protected SQLiteDatabase writableDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myNoteDBHelper = new MyNoteDBHelper(this);
        writableDB = myNoteDBHelper.getWritableDatabase();
    }

    @Override
    public void onClick(View v) {

    }
}
